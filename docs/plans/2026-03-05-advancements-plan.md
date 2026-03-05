# Advancements Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Add a 21-advancement tree that guides players through S3's storage tiers, feature blocks, ports, and tools.

**Architecture:** Pure data-driven — JSON advancement files, lang entries, and one background texture. No Java code. All advancements use `minecraft:recipe_unlocked` triggers referencing existing `s3:*` recipes.

**Tech Stack:** Minecraft 1.21.1 data pack JSON, NeoForge resource loading

**Design doc:** `docs/plans/2026-03-05-advancements-design.md`

---

### Task 1: Create background texture

**Files:**
- Create: `core/src/main/resources/assets/s3/textures/gui/advancements_background.png`

**Step 1: Create the texture**

Create a 16x16 dark tiling PNG for the advancement tab background. Use a simple dark gray stone-like texture (similar to vanilla's `minecraft:textures/gui/advancements/backgrounds/stone.png`). A solid dark gray (#2A2A2A) tile is acceptable as a starting point.

**Step 2: Verify the file exists**

Run: `ls -la core/src/main/resources/assets/s3/textures/gui/advancements_background.png`
Expected: file exists, non-zero size

**Step 3: Commit**

```bash
git add core/src/main/resources/assets/s3/textures/gui/advancements_background.png
git commit -m "Add advancement tab background texture (#43)"
```

---

### Task 2: Create root advancement

**Files:**
- Create: `core/src/main/resources/data/s3/advancement/root.json`

**Step 1: Create the root advancement JSON**

```json
{
  "display": {
    "icon": {
      "id": "s3:storage_core"
    },
    "title": {
      "translate": "advancement.s3.root.title"
    },
    "description": {
      "translate": "advancement.s3.root.description"
    },
    "frame": "task",
    "show_toast": true,
    "announce_to_chat": true,
    "background": "s3:textures/gui/advancements_background.png"
  },
  "criteria": {
    "has_recipe": {
      "trigger": "minecraft:recipe_unlocked",
      "conditions": {
        "recipe": "s3:storage_core"
      }
    }
  }
}
```

**Step 2: Validate JSON is well-formed**

Run: `python3 -c "import json; json.load(open('core/src/main/resources/data/s3/advancement/root.json'))"`
Expected: no output (valid JSON)

**Step 3: Commit**

```bash
git add core/src/main/resources/data/s3/advancement/root.json
git commit -m "Add root advancement for Storage Core (#43)"
```

---

### Task 3: Create storage tier advancements

**Files:**
- Create: `core/src/main/resources/data/s3/advancement/storage_box.json`
- Create: `core/src/main/resources/data/s3/advancement/condensed_storage_box.json`
- Create: `core/src/main/resources/data/s3/advancement/compressed_storage_box.json`
- Create: `core/src/main/resources/data/s3/advancement/super_storage_box.json`
- Create: `core/src/main/resources/data/s3/advancement/ultra_storage_box.json`
- Create: `core/src/main/resources/data/s3/advancement/hyper_storage_box.json`
- Create: `core/src/main/resources/data/s3/advancement/ultimate_storage_box.json`

**Step 1: Create all 7 tier advancement JSONs**

Each follows the same template (non-root advancements omit `background`):

```json
{
  "display": {
    "icon": { "id": "s3:<block_id>" },
    "title": { "translate": "advancement.s3.<id>.title" },
    "description": { "translate": "advancement.s3.<id>.description" },
    "frame": "task",
    "show_toast": true,
    "announce_to_chat": true
  },
  "parent": "s3:<parent_id>",
  "criteria": {
    "has_recipe": {
      "trigger": "minecraft:recipe_unlocked",
      "conditions": { "recipe": "s3:<block_id>" }
    }
  }
}
```

Chain: `root` → `storage_box` → `condensed_storage_box` → `compressed_storage_box` → `super_storage_box` → `ultra_storage_box` → `hyper_storage_box` → `ultimate_storage_box`

`ultimate_storage_box` uses `"frame": "challenge"` instead of `"task"`.

**Step 2: Validate all JSONs**

Run: `for f in core/src/main/resources/data/s3/advancement/*storage_box.json; do python3 -c "import json; json.load(open('$f'))" && echo "OK: $f"; done`
Expected: OK for all 7 files

**Step 3: Commit**

```bash
git add core/src/main/resources/data/s3/advancement/*storage_box.json
git commit -m "Add storage tier advancements (#43)"
```

---

### Task 4: Create feature block advancements

**Files:**
- Create: `core/src/main/resources/data/s3/advancement/search_box.json`
- Create: `core/src/main/resources/data/s3/advancement/sort_box.json`
- Create: `core/src/main/resources/data/s3/advancement/crafting_box.json`
- Create: `core/src/main/resources/data/s3/advancement/security_box.json`
- Create: `core/src/main/resources/data/s3/advancement/key.json`
- Create: `core/src/main/resources/data/s3/advancement/blank_box.json`

**Step 1: Create all 6 feature advancement JSONs**

Same template as Task 3. Parent chains:
- `search_box` → parent: `root`
- `sort_box` → parent: `search_box`
- `crafting_box` → parent: `root`
- `security_box` → parent: `root`
- `key` → parent: `security_box`
- `blank_box` → parent: `root`

All use `"frame": "task"`. The `key` advancement uses `"id": "s3:key"` (item, not block).

**Step 2: Validate all JSONs**

Run: `for f in search_box sort_box crafting_box security_box key blank_box; do python3 -c "import json; json.load(open('core/src/main/resources/data/s3/advancement/${f}.json'))" && echo "OK: $f"; done`
Expected: OK for all 6 files

**Step 3: Commit**

```bash
git add core/src/main/resources/data/s3/advancement/{search_box,sort_box,crafting_box,security_box,key,blank_box}.json
git commit -m "Add feature block advancements (#43)"
```

---

### Task 5: Create utility and port advancements

**Files:**
- Create: `core/src/main/resources/data/s3/advancement/access_terminal.json`
- Create: `core/src/main/resources/data/s3/advancement/storage_interface.json`
- Create: `core/src/main/resources/data/s3/advancement/input_port.json`
- Create: `core/src/main/resources/data/s3/advancement/extract_port.json`
- Create: `core/src/main/resources/data/s3/advancement/eject_port.json`

**Step 1: Create all 5 utility/port advancement JSONs**

Parent chains:
- `access_terminal` → parent: `root`
- `storage_interface` → parent: `access_terminal`
- `input_port` → parent: `root`
- `extract_port` → parent: `input_port`
- `eject_port` → parent: `extract_port`

All use `"frame": "task"`.

**Step 2: Validate all JSONs**

Run: `for f in access_terminal storage_interface input_port extract_port eject_port; do python3 -c "import json; json.load(open('core/src/main/resources/data/s3/advancement/${f}.json'))" && echo "OK: $f"; done`
Expected: OK for all 5 files

**Step 3: Commit**

```bash
git add core/src/main/resources/data/s3/advancement/{access_terminal,storage_interface,input_port,extract_port,eject_port}.json
git commit -m "Add utility and port advancements (#43)"
```

---

### Task 6: Create tool advancements

**Files:**
- Create: `core/src/main/resources/data/s3/advancement/dolly.json`
- Create: `core/src/main/resources/data/s3/advancement/super_dolly.json`

**Step 1: Create both tool advancement JSONs**

Parent chains:
- `dolly` → parent: `root`
- `super_dolly` → parent: `dolly`

Both use `"frame": "task"`. Icon IDs: `s3:dolly` and `s3:dolly_super`. Recipe IDs: `s3:dolly` and `s3:dolly_super`.

Note: the advancement file is `super_dolly.json` but the item/recipe ID is `dolly_super`.

**Step 2: Validate both JSONs**

Run: `for f in dolly super_dolly; do python3 -c "import json; json.load(open('core/src/main/resources/data/s3/advancement/${f}.json'))" && echo "OK: $f"; done`
Expected: OK for both files

**Step 3: Commit**

```bash
git add core/src/main/resources/data/s3/advancement/{dolly,super_dolly}.json
git commit -m "Add tool advancements (#43)"
```

---

### Task 7: Add translation keys

**Files:**
- Modify: `core/src/main/resources/assets/s3/lang/en_us.json`

**Step 1: Add all 42 advancement translation keys**

Add the following entries after the existing `"warning.s3.break_storage"` line (before the closing `}`):

```json
  "advancement.s3.root.title": "Storage Core",
  "advancement.s3.root.description": "Craft the heart of your storage system",
  "advancement.s3.storage_box.title": "Storage Box",
  "advancement.s3.storage_box.description": "Craft a basic Storage Box",
  "advancement.s3.condensed_storage_box.title": "Condensed Storage",
  "advancement.s3.condensed_storage_box.description": "Pack more items into less space",
  "advancement.s3.compressed_storage_box.title": "Compressed Storage",
  "advancement.s3.compressed_storage_box.description": "Further compress your storage capacity",
  "advancement.s3.super_storage_box.title": "Super Storage",
  "advancement.s3.super_storage_box.description": "Upgrade to super-sized storage",
  "advancement.s3.ultra_storage_box.title": "Ultra Storage",
  "advancement.s3.ultra_storage_box.description": "Push your storage even further",
  "advancement.s3.hyper_storage_box.title": "Hyper Storage",
  "advancement.s3.hyper_storage_box.description": "Achieve massive storage capacity",
  "advancement.s3.ultimate_storage_box.title": "Ultimate Storage",
  "advancement.s3.ultimate_storage_box.description": "Reach the pinnacle of storage technology",
  "advancement.s3.search_box.title": "Search Box",
  "advancement.s3.search_box.description": "Find items instantly with search",
  "advancement.s3.sort_box.title": "Sort Box",
  "advancement.s3.sort_box.description": "Keep your storage organized",
  "advancement.s3.crafting_box.title": "Crafting Box",
  "advancement.s3.crafting_box.description": "Craft directly from your storage",
  "advancement.s3.security_box.title": "Security Box",
  "advancement.s3.security_box.description": "Lock down your storage system",
  "advancement.s3.key.title": "Key",
  "advancement.s3.key.description": "Craft the key to your security",
  "advancement.s3.blank_box.title": "Blank Box",
  "advancement.s3.blank_box.description": "Craft a blank canvas",
  "advancement.s3.access_terminal.title": "Access Terminal",
  "advancement.s3.access_terminal.description": "Access your storage remotely",
  "advancement.s3.storage_interface.title": "Storage Interface",
  "advancement.s3.storage_interface.description": "Connect your storage to other mods",
  "advancement.s3.input_port.title": "Input Port",
  "advancement.s3.input_port.description": "Automate item insertion",
  "advancement.s3.extract_port.title": "Extract Port",
  "advancement.s3.extract_port.description": "Automate item extraction",
  "advancement.s3.eject_port.title": "Eject Port",
  "advancement.s3.eject_port.description": "Push items to adjacent inventories",
  "advancement.s3.dolly.title": "Dolly",
  "advancement.s3.dolly.description": "Move your storage blocks with ease",
  "advancement.s3.super_dolly.title": "Super Dolly",
  "advancement.s3.super_dolly.description": "A more durable way to move blocks"
```

**Step 2: Validate en_us.json is well-formed**

Run: `python3 -c "import json; json.load(open('core/src/main/resources/assets/s3/lang/en_us.json'))"`
Expected: no output (valid JSON)

**Step 3: Commit**

```bash
git add core/src/main/resources/assets/s3/lang/en_us.json
git commit -m "Add advancement translation keys (#43)"
```

---

### Task 8: Build and verify

**Step 1: Run the full build**

Run: `./gradlew build`
Expected: BUILD SUCCESSFUL — confirms all JSON is valid and resources are packaged correctly.

**Step 2: Verify advancement files are in the JAR**

Run: `jar tf neoforge/build/libs/s3-*.jar | grep advancement | sort`
Expected: 21 advancement JSON files listed under `data/s3/advancement/`

**Step 3: Verify file count**

Run: `ls core/src/main/resources/data/s3/advancement/*.json | wc -l`
Expected: 21
