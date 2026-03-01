# Extract Game Tests to `neoforge-gametest` Subproject — Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Move all game test code and data generation out of the `neoforge` module into a dedicated `neoforge-gametest` Gradle subproject.

**Architecture:** Create a new `neoforge-gametest` Gradle subproject that applies the `net.neoforged.moddev` plugin, depends on both `core` and `neoforge`, and uses source set linking to load its classes as part of the `s3` mod. The `gameTestServer` and `data` run configs move from `neoforge/build.gradle` to the new subproject's `build.gradle`.

**Tech Stack:** Gradle (Groovy DSL), NeoForge moddev plugin 2.0.139, Java 21

**Design doc:** `docs/plans/2026-02-28-gametest-subproject-design.md`

---

### Task 1: Create `neoforge-gametest/build.gradle`

**Files:**
- Create: `neoforge-gametest/build.gradle`

**Step 1: Create the build file**

```groovy
plugins {
    id 'net.neoforged.moddev' version '2.0.139'
}

neoForge {
    version = neoforge_version

    runs {
        gameTestServer {
            type = "gameTestServer"
            systemProperty 'neoforge.enabledGameTestNamespaces', 's3'
        }
        data {
            data()
            programArguments.addAll(
                '--mod', 's3',
                '--all',
                '--output', file('src/generated/resources/').getAbsolutePath(),
                '--existing', file('src/main/resources/').getAbsolutePath(),
                '--existing', rootProject.file('core/src/main/resources/').getAbsolutePath(),
                '--existing', rootProject.file('neoforge/src/main/resources/').getAbsolutePath()
            )
        }
    }

    mods {
        s3 {
            sourceSet sourceSets.main
            sourceSet project(':core').sourceSets.main
            sourceSet project(':neoforge').sourceSets.main
        }
    }
}

sourceSets.main.resources.srcDir 'src/generated/resources'

dependencies {
    implementation project(':core')
    implementation project(':neoforge')
}
```

**Step 2: Verify syntax**

Run: `./gradlew :neoforge-gametest:tasks --dry-run`
Expected: Task list printed without errors (will fail until settings.gradle is updated — that's Task 2)

---

### Task 2: Register subproject in `settings.gradle`

**Files:**
- Modify: `settings.gradle:17`

**Step 1: Add the include**

Add `include 'neoforge-gametest'` after the existing `include 'neoforge'` line (line 17).

After edit, `settings.gradle` lines 15-18 should read:

```groovy
rootProject.name = 'steves-simple-storage'
include 'core'
include 'neoforge'
include 'neoforge-gametest'
```

**Step 2: Verify Gradle recognizes the new subproject**

Run: `./gradlew projects`
Expected: Output includes `:neoforge-gametest` in the project listing

---

### Task 3: Move source files to `neoforge-gametest`

**Files:**
- Create: `neoforge-gametest/src/main/java/io/github/scuba10steve/s3/gametest/StorageGameTests.java` (copy from `neoforge/src/main/java/io/github/scuba10steve/s3/gametest/StorageGameTests.java` — no changes needed, package is already `io.github.scuba10steve.s3.gametest`)
- Create: `neoforge-gametest/src/main/java/io/github/scuba10steve/s3/gametest/GameTestStructureProvider.java` (moved from `neoforge/src/main/java/io/github/scuba10steve/s3/datagen/GameTestStructureProvider.java` — package changes from `datagen` to `gametest`)
- Create: `neoforge-gametest/src/main/java/io/github/scuba10steve/s3/gametest/S3GameTestDataGeneration.java` (moved+renamed from `neoforge/src/main/java/io/github/scuba10steve/s3/datagen/S3DataGeneration.java` — package changes from `datagen` to `gametest`, class renamed)

**Step 1: Create the directory structure**

```bash
mkdir -p neoforge-gametest/src/main/java/io/github/scuba10steve/s3/gametest
mkdir -p neoforge-gametest/src/main/resources
```

**Step 2: Copy StorageGameTests.java (no changes needed)**

Copy `neoforge/src/main/java/io/github/scuba10steve/s3/gametest/StorageGameTests.java` to `neoforge-gametest/src/main/java/io/github/scuba10steve/s3/gametest/StorageGameTests.java`.

The file is unchanged — package is already `io.github.scuba10steve.s3.gametest`.

**Step 3: Move GameTestStructureProvider.java (update package)**

Copy `neoforge/src/main/java/io/github/scuba10steve/s3/datagen/GameTestStructureProvider.java` to `neoforge-gametest/src/main/java/io/github/scuba10steve/s3/gametest/GameTestStructureProvider.java`.

Change the package declaration on line 1:

```java
// FROM:
package io.github.scuba10steve.s3.datagen;

// TO:
package io.github.scuba10steve.s3.gametest;
```

No other changes needed — this file has no imports from the `datagen` package.

**Step 4: Move and rename S3DataGeneration.java → S3GameTestDataGeneration.java (update package, class name, imports)**

Copy `neoforge/src/main/java/io/github/scuba10steve/s3/datagen/S3DataGeneration.java` to `neoforge-gametest/src/main/java/io/github/scuba10steve/s3/gametest/S3GameTestDataGeneration.java`.

Changes required:
1. Package declaration (line 1): `package io.github.scuba10steve.s3.datagen;` → `package io.github.scuba10steve.s3.gametest;`
2. Remove import lines 3-4 (`import io.github.scuba10steve.s3.datagen.GameTestStructureProvider.*`) — `GameTestStructureProvider` is now in the same package
3. Class name (line 14): `S3DataGeneration` → `S3GameTestDataGeneration`

Final file contents:

```java
package io.github.scuba10steve.s3.gametest;

import io.github.scuba10steve.s3.gametest.GameTestStructureProvider.BlockPlacement;
import io.github.scuba10steve.s3.gametest.GameTestStructureProvider.StructureContent;
import io.github.scuba10steve.s3.ref.RefStrings;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = RefStrings.MODID, bus = EventBusSubscriber.Bus.MOD)
public class S3GameTestDataGeneration {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        GameTestStructureProvider provider = new GameTestStructureProvider(
            event.getGenerator().getPackOutput(), RefStrings.MODID);

        // Generate a core + storage block structure for each tier
        addCoreWithBlock(provider, "storage_box");
        addCoreWithBlock(provider, "condensed_storage_box");
        addCoreWithBlock(provider, "compressed_storage_box");
        addCoreWithBlock(provider, "super_storage_box");
        addCoreWithBlock(provider, "ultra_storage_box");
        addCoreWithBlock(provider, "hyper_storage_box");
        addCoreWithBlock(provider, "ultimate_storage_box");

        // Large multiblock: 10x10x5 filled with ultimate boxes + core at center
        addLargeUltimateStructure(provider);

        event.getGenerator().addProvider(event.includeServer(), provider);
    }

    private static void addLargeUltimateStructure(GameTestStructureProvider provider) {
        List<BlockPlacement> blocks = new ArrayList<>();
        // Core at center of the base layer
        blocks.add(new BlockPlacement("s3:storage_core", 5, 0, 5));
        // Fill every other position with ultimate storage boxes
        for (int x = 0; x < 10; x++) {
            for (int z = 0; z < 10; z++) {
                for (int y = 0; y < 5; y++) {
                    if (x == 5 && y == 0 && z == 5) continue; // skip core position
                    blocks.add(new BlockPlacement("s3:ultimate_storage_box", x, y, z));
                }
            }
        }
        provider.add("large_ultimate_multiblock", 10, 5, 10, new StructureContent(
            blocks, List.of(), List.of()
        ));
    }

    private static void addCoreWithBlock(GameTestStructureProvider provider, String blockName) {
        provider.add("core_with_" + blockName, 3, 3, 3, new StructureContent(
            List.of(
                new BlockPlacement("s3:storage_core", 1, 0, 1),
                new BlockPlacement("s3:" + blockName, 2, 0, 1)
            ),
            List.of(),
            List.of()
        ));
    }
}
```

---

### Task 4: Delete source files from `neoforge`

**Files:**
- Delete: `neoforge/src/main/java/io/github/scuba10steve/s3/gametest/StorageGameTests.java`
- Delete: `neoforge/src/main/java/io/github/scuba10steve/s3/datagen/GameTestStructureProvider.java`
- Delete: `neoforge/src/main/java/io/github/scuba10steve/s3/datagen/S3DataGeneration.java`

**Step 1: Delete the files**

```bash
rm neoforge/src/main/java/io/github/scuba10steve/s3/gametest/StorageGameTests.java
rm neoforge/src/main/java/io/github/scuba10steve/s3/datagen/GameTestStructureProvider.java
rm neoforge/src/main/java/io/github/scuba10steve/s3/datagen/S3DataGeneration.java
```

**Step 2: Remove empty directories**

```bash
rmdir neoforge/src/main/java/io/github/scuba10steve/s3/gametest
rmdir neoforge/src/main/java/io/github/scuba10steve/s3/datagen
```

**Step 3: Clean generated resources from neoforge**

```bash
rm -rf neoforge/src/generated/resources/data/s3/structure
```

Remove any now-empty parent dirs under `neoforge/src/generated/resources/` if nothing else lives there.

---

### Task 5: Update `neoforge/build.gradle`

**Files:**
- Modify: `neoforge/build.gradle:12-33` (runs block), `neoforge/build.gradle:48` (srcDir)

**Step 1: Remove `gameTestServer` and `data` run configs**

Replace lines 12-33 (the entire `runs` block) with just client and server:

```groovy
    runs {
        client {
            client()
        }
        server {
            server()
        }
    }
```

**Step 2: Remove generated resources srcDir**

Delete line 48: `sourceSets.main.resources.srcDir 'src/generated/resources'`

**Step 3: Verify neoforge still compiles**

Run: `./gradlew :neoforge:compileJava`
Expected: BUILD SUCCESSFUL

---

### Task 6: Update CI workflow

**Files:**
- Modify: `.github/workflows/game-tests.yml:29-32`

**Step 1: Update Gradle task prefixes**

Change line 29:
```yaml
        run: ./gradlew :neoforge:runData
```
to:
```yaml
        run: ./gradlew :neoforge-gametest:runData
```

Change line 32:
```yaml
        run: ./gradlew :neoforge:runGameTestServer
```
to:
```yaml
        run: ./gradlew :neoforge-gametest:runGameTestServer
```

---

### Task 7: Verify the full build

**Step 1: Compile the gametest subproject**

Run: `./gradlew :neoforge-gametest:compileJava`
Expected: BUILD SUCCESSFUL

**Step 2: Compile the neoforge subproject (confirm no broken references)**

Run: `./gradlew :neoforge:compileJava`
Expected: BUILD SUCCESSFUL

**Step 3: Run data generation from the new subproject**

Run: `./gradlew :neoforge-gametest:runData`
Expected: BUILD SUCCESSFUL, generates `.nbt` files under `neoforge-gametest/src/generated/resources/data/s3/structure/`

**Step 4: Run game tests from the new subproject**

Run: `./gradlew :neoforge-gametest:runGameTestServer`
Expected: All 11 game tests pass

**Step 5: Run the full build**

Run: `./gradlew build`
Expected: BUILD SUCCESSFUL — all subprojects compile, unit tests pass, mod JAR produced

---

### Task 8: Commit

**Step 1: Stage all changes**

```bash
git add neoforge-gametest/
git add settings.gradle
git add neoforge/build.gradle
git add .github/workflows/game-tests.yml
git add -u neoforge/src/main/java/io/github/scuba10steve/s3/gametest/
git add -u neoforge/src/main/java/io/github/scuba10steve/s3/datagen/
```

**Step 2: Verify staged changes look correct**

```bash
git diff --cached --stat
```

Expected:
- New files in `neoforge-gametest/`
- Deleted files from `neoforge/src/main/java/.../gametest/` and `neoforge/src/main/java/.../datagen/`
- Modified `settings.gradle`, `neoforge/build.gradle`, `.github/workflows/game-tests.yml`

**Step 3: Commit**

```
Move game tests to dedicated neoforge-gametest subproject

Closes #31
```
