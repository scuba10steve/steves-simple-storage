# Design: Remove `evaluationDependsOn` with Lazy APIs and Custom Configurations

## Goals

- Remove all `evaluationDependsOn` calls (bad Gradle practice, breaks project isolation)
- Do not introduce `gradle.projectsEvaluated` (same class of problem)
- Be configuration-cache compatible
- Use proper Gradle lazy APIs throughout

## Context

No `evaluationDependsOn` lines currently exist in the build files — they were removed manually. The goal is to replace the eager cross-project `sourceSets` accesses that made them necessary in the first place, so none would ever be needed.

Three subprojects reference other projects' `sourceSets` at configuration time:

- `neoforge/s3/build.gradle` — references `project(':core').sourceSets.main`
- `neoforge/s3-advanced/build.gradle` — references `project(':core').sourceSets.main`
- `gametest/s3-advanced/build.gradle` — references `project(':core').sourceSets.main`, `project(':neoforge:s3').sourceSets.main`, and `project(':neoforge:s3-advanced').sourceSets.main`

Without evaluation ordering guarantees, Gradle may evaluate the consumer before the producer, causing `sourceSets` to be uninitialized.

There are two distinct use cases that need different solutions.

## Solution 1: `neoForge.mods.*.sourceSet()` — Lazy `ListProperty.add(Provider<SourceSet>)`

`ModModel.getModSourceSets()` is a `ListProperty<SourceSet>`. NeoForge MDG only reads it inside a lazy `Provider` chain in `RunUtils.buildModFolders` — it is never accessed eagerly during project evaluation.

`ListProperty` has `add(Provider<? extends T>)`, which defers evaluation of the provider until the list is first read. By wrapping the cross-project sourceSet access in `providers.provider { }`, evaluation is deferred until after all projects are configured.

**Before:**
```groovy
neoForge {
    mods {
        s3 {
            sourceSet sourceSets.main
            sourceSet project(':core').sourceSets.main   // eager — needs evaluationDependsOn
        }
    }
}
```

**After (example: `neoforge/s3/build.gradle`):**
```groovy
neoForge {
    mods {
        s3 {
            sourceSet sourceSets.main
            modSourceSets.add(providers.provider { project(':core').sourceSets.main })
        }
    }
}
```

**After (example: `neoforge/s3-advanced/build.gradle` — mod block name is `'s3-advanced'` with a hyphen):**
```groovy
neoForge {
    mods {
        's3-advanced' {
            sourceSet sourceSets.main
            modSourceSets.add(providers.provider { project(':core').sourceSets.main })
        }
    }
}
```

**After (example: `gametest/s3-advanced/build.gradle` — two separate mod blocks):**

Note: the `s3` mod block in this file has no local `sourceSets.main` — it only contains the two cross-project calls. The `'s3_advanced'` block (underscore, distinct from `neoforge/s3-advanced`'s hyphenated block name) keeps its local `sourceSets.main`.

```groovy
neoForge {
    mods {
        s3 {
            // No local sourceSets.main in this block — only cross-project calls
            modSourceSets.add(providers.provider { project(':core').sourceSets.main })
            modSourceSets.add(providers.provider { project(':neoforge:s3').sourceSets.main })
        }
        's3_advanced' {
            // Keeps its own local sourceSets.main; only the cross-project call goes lazy
            sourceSet sourceSets.main
            modSourceSets.add(providers.provider { project(':neoforge:s3-advanced').sourceSets.main })
        }
    }
}
```

This is configuration-cache compatible because MDG ultimately stores the resolved file paths from `sourceSet.getOutput()` as task inputs, not the `SourceSet` objects themselves.

> **Accepted risk:** This approach relies on MDG's internal implementation — specifically that `RunUtils.buildModFolders` is the only consumer of `getModSourceSets()`, and that it is invoked lazily after all projects are configured. If a future MDG version reads the list eagerly during project evaluation, the lazy `providers.provider { }` wrapper will fail again. Track the MDG changelog for changes to `ModModel` or `RunUtils`.

## Solution 2: `jar { from project(':core').sourceSets.main.output }` — Custom Configurations

The `jar` task in `neoforge/s3/build.gradle` bundles `:core`'s compiled classes into the final mod JAR. Instead of accessing `sourceSets` directly, declare an explicit outgoing configuration on `:core` that exposes its compiled output, and consume it from `neoforge/s3` with a matching resolvable configuration.

This is the proper "custom configurations" pattern — no cross-project model access, no dependency on plugin-published variants.

**`core/build.gradle` — add outgoing configuration:**
```groovy
configurations {
    coreOutput {
        canBeConsumed = true
        canBeResolved = false
    }
}
artifacts {
    coreOutput(compileJava.destinationDirectory) { builtBy compileJava }
    coreOutput(processResources.destinationDirectory) { builtBy processResources }
}
```

**`neoforge/s3/build.gradle` — consume it:**

```groovy
import org.gradle.api.attributes.LibraryElements

configurations {
    coreClasses {
        canBeConsumed = false
        canBeResolved = true
    }
}

dependencies {
    coreClasses project(path: ':core', configuration: 'coreOutput')
}

jar {
    from configurations.coreClasses
}
```

No cross-project model access at all — just named configuration resolution. The `configuration: 'coreOutput'` syntax in the dependency notation intentionally selects the configuration by name, bypassing Gradle's variant-aware resolution entirely. Do not add `attributes { }` to `coreClasses` — it is not needed and would change the resolution strategy.

`neoforge/s3-advanced/build.gradle` does not need this change — it has no `jar { from }` block, because it declares `implementation project(':neoforge:s3')`, and the `:neoforge:s3` JAR already bundles `:core`'s classes. Adding a redundant `coreOutput` consumer there would double-include core classes in the artifact.

## Files to Change

| File | Change |
|------|--------|
| `core/build.gradle` | Add `coreOutput` outgoing configuration and wire `compileJava`/`processResources` output as artifacts |
| `neoforge/s3/build.gradle` | Replace `sourceSet project(':core').sourceSets.main` with lazy `modSourceSets.add(...)`; replace `jar { from ... }` with `coreClasses` resolvable configuration |
| `neoforge/s3-advanced/build.gradle` | Replace `sourceSet project(':core').sourceSets.main` with lazy `modSourceSets.add(...)` |
| `gametest/s3-advanced/build.gradle` | Replace three cross-project `sourceSet` calls across both `s3` and `s3_advanced` mod blocks with lazy `modSourceSets.add(...)` |

## What Does Not Change

- `s3.neoforge-mod.gradle` convention plugin — does not contain cross-project sourceSet access
- `gametest/s3/build.gradle` — no cross-project sourceSet access in its `neoForge.mods` block
- `neoforge/s3-advanced/build.gradle` does not need Solution 2 (no `jar { from ... }` block). It depends on `implementation project(':neoforge:s3')`, whose JAR already bundles `:core`'s classes, so no additional bundling is needed.

## Testing

- `./gradlew :neoforge:s3:build` — verifies JAR is correctly assembled; follow up with `jar tf neoforge/s3/build/libs/s3-*.jar | grep "io/github/scuba10steve/s3"` to confirm `:core` classes are present
- `./gradlew :neoforge:s3-advanced:build` — verifies s3-advanced builds correctly
- `./gradlew :gametest:s3-advanced:runGameTestServer` — verifies run configuration resolves source sets correctly
- `./gradlew --configuration-cache build` — verifies configuration cache compatibility
- `./gradlew build --info 2>&1 | grep -i "sourceSets"` — confirms no cross-project sourceSet access occurs during the configuration phase (validates that lazy providers in Solution 1 are not forced early by any MDG internal code path)
