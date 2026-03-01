# Design: Extract Game Tests to `neoforge-gametest` Subproject

**Issue:** #31
**Date:** 2026-02-28

## Goal

Move all game test code and data generation for test structures out of the `neoforge` module into a dedicated `neoforge-gametest` Gradle subproject. The neoforge module retains zero game-test-related code.

## Directory Structure

```
neoforge-gametest/
├── build.gradle
└── src/
    └── main/
        ├── java/
        │   └── io/github/scuba10steve/s3/gametest/
        │       ├── StorageGameTests.java
        │       ├── GameTestStructureProvider.java
        │       └── S3GameTestDataGeneration.java
        └── resources/
            (empty — generated resources written to src/generated/resources/)
```

## Files Moved

| From (neoforge) | To (neoforge-gametest) |
|---|---|
| `src/main/java/.../gametest/StorageGameTests.java` | `src/main/java/.../gametest/StorageGameTests.java` |
| `src/main/java/.../datagen/GameTestStructureProvider.java` | `src/main/java/.../gametest/GameTestStructureProvider.java` |
| `src/main/java/.../datagen/S3DataGeneration.java` | `src/main/java/.../gametest/S3GameTestDataGeneration.java` (renamed) |

## Files Deleted from Neoforge

- `neoforge/src/main/java/.../gametest/StorageGameTests.java`
- `neoforge/src/main/java/.../datagen/GameTestStructureProvider.java`
- `neoforge/src/main/java/.../datagen/S3DataGeneration.java`

## Build Configuration

### `neoforge-gametest/build.gradle`

- Applies `net.neoforged.moddev` plugin (same version as other modules)
- `neoForge` block with `version = neoforge_version`
- Run configs:
  - `gameTestServer` — type `gameTestServer`, `neoforge.enabledGameTestNamespaces = 's3'`
  - `data` — data generation with `--output` pointing to gametest's `src/generated/resources/`
- Mod source set linking: `mods { s3 { sourceSet sourceSets.main; sourceSet project(':core').sourceSets.main; sourceSet project(':neoforge').sourceSets.main } }`
- Dependencies: `implementation project(':core')`, `implementation project(':neoforge')`
- `sourceSets.main.resources.srcDir 'src/generated/resources'`

### `neoforge/build.gradle` Changes

- Remove `gameTestServer` run config
- Remove `data` run config
- Remove `sourceSets.main.resources.srcDir 'src/generated/resources'`

### `settings.gradle` Changes

- Add `include 'neoforge-gametest'`

## Mod Identity

The gametest subproject uses NeoForge moddev source set linking (`mods { s3 { ... } }`) to include its classes as part of the `s3` mod. No separate `mods.toml` is needed — the game test classes are loaded under the main mod's identity at runtime.

## Data Generation

`S3GameTestDataGeneration` (renamed from `S3DataGeneration`) subscribes to `GatherDataEvent` on the mod bus with `modid = "s3"`. It registers `GameTestStructureProvider` to generate NBT structure files into `neoforge-gametest/src/generated/resources/data/s3/structure/`.

Both classes move to the `io.github.scuba10steve.s3.gametest` package since they exist solely to support game tests.

## CI Workflow Changes

`.github/workflows/game-tests.yml` updates Gradle task prefixes:

```yaml
- name: Generate data (structures for game tests)
  run: ./gradlew :neoforge-gametest:runData
- name: Run game tests
  run: ./gradlew :neoforge-gametest:runGameTestServer
```

## What Stays in Neoforge

- `client` and `server` run configs (development)
- All production code (blocks, screens, packets, config, JEI, ports, registration)
- `mods.toml` and mod metadata
- `jar` task producing the installable mod JAR (with core classes bundled)
