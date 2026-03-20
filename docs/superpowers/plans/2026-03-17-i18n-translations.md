# i18n / Translation Support Implementation Plan

> **For agentic workers:** REQUIRED: Use superpowers:subagent-driven-development (if subagents available) or superpowers:executing-plans to implement this plan. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add community-driven translation support with a Gradle key-parity validator, an `es_es` skeleton, a contributor guide, and an updated issue #44 checklist.

**Architecture:** A `validateLang` Groovy DSL task in each module's `build.gradle` enforces that every non-`en_us` lang file has exactly the same key set as `en_us.json`. The task hooks into the `check` lifecycle so it runs automatically in CI. Contributor-facing artifacts (skeleton, docs, PR template) lower the barrier to opening a translation PR.

**Tech Stack:** Groovy DSL (Gradle), `groovy.json.JsonSlurper`, GitHub Markdown, `gh` CLI for issue update.

**Spec:** `docs/superpowers/specs/2026-03-17-i18n-design.md`

---

## Chunk 1: validateLang Gradle Task

### Task 1: Add validateLang to core/build.gradle

**Files:**
- Modify: `core/build.gradle`

- [ ] **Step 1: Open `core/build.gradle` and append the task after the `artifacts` block**

Add the following at the end of the file:

```groovy
tasks.register('validateLang') {
    group = 'verification'
    description = 'Validates all lang files have the same key set as en_us.json'

    def langDir = file('src/main/resources/assets/s3/lang')
    inputs.dir(langDir)
    outputs.upToDateWhen { true }

    doLast {
        def reference = new groovy.json.JsonSlurper().parse(new File(langDir, 'en_us.json'))
        def referenceKeys = reference.keySet()

        langDir.listFiles()
            ?.findAll { it.name.endsWith('.json') && it.name != 'en_us.json' }
            ?.each { langFile ->
                def keys = new groovy.json.JsonSlurper().parse(langFile).keySet()
                def missing = referenceKeys - keys
                def extra = keys - referenceKeys
                if (missing || extra) {
                    def msg = "${langFile.name} key mismatch:"
                    if (missing) msg += "\n  Missing: ${missing}"
                    if (extra) msg += "\n  Extra:   ${extra}"
                    throw new GradleException(msg)
                }
            }
    }
}

tasks.named('check') { dependsOn('validateLang') }
```

- [ ] **Step 2: Run validateLang with no extra lang files — expect PASS**

```bash
./gradlew :core:validateLang
```

Expected: `BUILD SUCCESSFUL` (no non-`en_us` files exist yet, nothing to check).

- [ ] **Step 3: Verify the task catches errors — create a deliberately broken lang file**

Create `core/src/main/resources/assets/s3/lang/xx_xx.json` with one key missing and one extra:

```json
{
  "block.s3.storage_core": "Storage Core",
  "block.s3.EXTRA_KEY_THAT_DOES_NOT_EXIST": "oops"
}
```

- [ ] **Step 4: Run validateLang — expect FAIL with a clear message**

```bash
./gradlew :core:validateLang
```

Expected: `BUILD FAILED` with output containing `xx_xx.json key mismatch:` and listing both missing and extra keys.

- [ ] **Step 5: Delete the test file**

```bash
rm core/src/main/resources/assets/s3/lang/xx_xx.json
```

- [ ] **Step 6: Run validateLang again — expect PASS**

```bash
./gradlew :core:validateLang
```

Expected: `BUILD SUCCESSFUL`.

- [ ] **Step 7: Commit**

```bash
git add core/build.gradle
git commit -m "build(core): add validateLang task to enforce lang key-set parity"
```

---

### Task 2: Add validateLang to neoforge/s3-advanced/build.gradle

**Files:**
- Modify: `neoforge/s3-advanced/build.gradle`

- [ ] **Step 1: Open `neoforge/s3-advanced/build.gradle` and append the task after the `dependencies` block**

Add the following at the end of the file:

```groovy
tasks.register('validateLang') {
    group = 'verification'
    description = 'Validates all lang files have the same key set as en_us.json'

    def langDir = file('src/main/resources/assets/s3_advanced/lang')
    inputs.dir(langDir)
    outputs.upToDateWhen { true }

    doLast {
        def reference = new groovy.json.JsonSlurper().parse(new File(langDir, 'en_us.json'))
        def referenceKeys = reference.keySet()

        langDir.listFiles()
            ?.findAll { it.name.endsWith('.json') && it.name != 'en_us.json' }
            ?.each { langFile ->
                def keys = new groovy.json.JsonSlurper().parse(langFile).keySet()
                def missing = referenceKeys - keys
                def extra = keys - referenceKeys
                if (missing || extra) {
                    def msg = "${langFile.name} key mismatch:"
                    if (missing) msg += "\n  Missing: ${missing}"
                    if (extra) msg += "\n  Extra:   ${extra}"
                    throw new GradleException(msg)
                }
            }
    }
}

tasks.named('check') { dependsOn('validateLang') }
```

- [ ] **Step 2: Run validateLang — expect PASS**

```bash
./gradlew :neoforge:s3-advanced:validateLang
```

Expected: `BUILD SUCCESSFUL`.

- [ ] **Step 3: Verify the full build still passes**

```bash
./gradlew build
```

Expected: `BUILD SUCCESSFUL`. Confirms `validateLang` is wired into `check` and does not break the build.

> **If `build` does not trigger `validateLang`:** The `net.neoforged.moddev` plugin may not wire `build → check` the same way the plain `java` plugin does. If so, add `build.dependsOn(check)` explicitly in both build files and note this finding.

- [ ] **Step 4: Commit**

```bash
git add neoforge/s3-advanced/build.gradle
git commit -m "build(neoforge/s3-advanced): add validateLang task to enforce lang key-set parity"
```

---

## Chunk 2: Spanish Skeleton Files

### Task 3: Create es_es.json skeleton for s3

**Files:**
- Create: `core/src/main/resources/assets/s3/lang/es_es.json`

- [ ] **Step 1: Create the skeleton file**

Copy all keys from `core/src/main/resources/assets/s3/lang/en_us.json`, prefix each value with `[TRANSLATE] ` to mark it as untranslated. The file must have exactly the same keys as `en_us.json`.

```json
{
  "itemGroup.s3": "[TRANSLATE] Steve's Simple Storage",

  "block.s3.storage_core": "[TRANSLATE] Storage Core",
  "block.s3.storage_box": "[TRANSLATE] Storage Box",
  "block.s3.condensed_storage_box": "[TRANSLATE] Condensed Storage Box",
  "block.s3.compressed_storage_box": "[TRANSLATE] Compressed Storage Box",
  "block.s3.super_storage_box": "[TRANSLATE] Super Storage Box",
  "block.s3.ultra_storage_box": "[TRANSLATE] Ultra Storage Box",
  "block.s3.hyper_storage_box": "[TRANSLATE] Hyper Storage Box",
  "block.s3.ultimate_storage_box": "[TRANSLATE] Ultimate Storage Box",
  "block.s3.crafting_box": "[TRANSLATE] Crafting Box",
  "block.s3.search_box": "[TRANSLATE] Search Box",
  "block.s3.sort_box": "[TRANSLATE] Sort Box",
  "block.s3.blank_box": "[TRANSLATE] Blank Box",
  "block.s3.input_port": "[TRANSLATE] Input Port",
  "block.s3.extract_port": "[TRANSLATE] Extract Port",
  "block.s3.eject_port": "[TRANSLATE] Eject Port",
  "block.s3.security_box": "[TRANSLATE] Security Box",
  "block.s3.access_terminal": "[TRANSLATE] Access Terminal",
  "block.s3.storage_interface": "[TRANSLATE] Storage Interface",

  "container.s3.storage_core": "[TRANSLATE] Storage Core",
  "container.s3.extract_port": "[TRANSLATE] Extract Port",
  "container.s3.security_box": "[TRANSLATE] Security Box",

  "item.s3.key": "[TRANSLATE] Storage Key",
  "item.s3.dolly": "[TRANSLATE] Dolly",
  "item.s3.dolly_super": "[TRANSLATE] Super Dolly",

  "gui.s3.search": "[TRANSLATE] Search...",
  "gui.s3.clear": "[TRANSLATE] Clear",
  "warning.s3.break_storage": "[TRANSLATE] Warning! %s stored items will be lost! Use a Dolly to preserve items.",

  "advancement.s3.root.title": "[TRANSLATE] Storage Core",
  "advancement.s3.root.description": "[TRANSLATE] Craft the heart of your storage system",
  "advancement.s3.storage_box.title": "[TRANSLATE] Storage Box",
  "advancement.s3.storage_box.description": "[TRANSLATE] Craft a basic Storage Box",
  "advancement.s3.condensed_storage_box.title": "[TRANSLATE] Condensed Storage",
  "advancement.s3.condensed_storage_box.description": "[TRANSLATE] Pack more items into less space",
  "advancement.s3.compressed_storage_box.title": "[TRANSLATE] Compressed Storage",
  "advancement.s3.compressed_storage_box.description": "[TRANSLATE] Further compress your storage capacity",
  "advancement.s3.super_storage_box.title": "[TRANSLATE] Super Storage",
  "advancement.s3.super_storage_box.description": "[TRANSLATE] Upgrade to super-sized storage",
  "advancement.s3.ultra_storage_box.title": "[TRANSLATE] Ultra Storage",
  "advancement.s3.ultra_storage_box.description": "[TRANSLATE] Push your storage even further",
  "advancement.s3.hyper_storage_box.title": "[TRANSLATE] Hyper Storage",
  "advancement.s3.hyper_storage_box.description": "[TRANSLATE] Achieve massive storage capacity",
  "advancement.s3.ultimate_storage_box.title": "[TRANSLATE] Ultimate Storage",
  "advancement.s3.ultimate_storage_box.description": "[TRANSLATE] Reach the pinnacle of storage technology",
  "advancement.s3.search_box.title": "[TRANSLATE] Search Box",
  "advancement.s3.search_box.description": "[TRANSLATE] Find items instantly with search",
  "advancement.s3.sort_box.title": "[TRANSLATE] Sort Box",
  "advancement.s3.sort_box.description": "[TRANSLATE] Keep your storage organized",
  "advancement.s3.crafting_box.title": "[TRANSLATE] Crafting Box",
  "advancement.s3.crafting_box.description": "[TRANSLATE] Craft directly from your storage",
  "advancement.s3.security_box.title": "[TRANSLATE] Security Box",
  "advancement.s3.security_box.description": "[TRANSLATE] Lock down your storage system",
  "advancement.s3.key.title": "[TRANSLATE] Key",
  "advancement.s3.key.description": "[TRANSLATE] Craft the key to your security",
  "advancement.s3.blank_box.title": "[TRANSLATE] Blank Box",
  "advancement.s3.blank_box.description": "[TRANSLATE] Craft a blank canvas",
  "advancement.s3.access_terminal.title": "[TRANSLATE] Access Terminal",
  "advancement.s3.access_terminal.description": "[TRANSLATE] Access your storage remotely",
  "advancement.s3.storage_interface.title": "[TRANSLATE] Storage Interface",
  "advancement.s3.storage_interface.description": "[TRANSLATE] Connect your storage to other mods",
  "advancement.s3.input_port.title": "[TRANSLATE] Input Port",
  "advancement.s3.input_port.description": "[TRANSLATE] Automate item insertion",
  "advancement.s3.extract_port.title": "[TRANSLATE] Extract Port",
  "advancement.s3.extract_port.description": "[TRANSLATE] Automate item extraction",
  "advancement.s3.eject_port.title": "[TRANSLATE] Eject Port",
  "advancement.s3.eject_port.description": "[TRANSLATE] Push items to adjacent inventories",
  "advancement.s3.dolly.title": "[TRANSLATE] Dolly",
  "advancement.s3.dolly.description": "[TRANSLATE] Move your storage blocks with ease",
  "advancement.s3.super_dolly.title": "[TRANSLATE] Super Dolly",
  "advancement.s3.super_dolly.description": "[TRANSLATE] A more durable way to move blocks",

  "block.s3.statistics_box": "[TRANSLATE] Statistics Box",
  "container.s3.statistics_box": "[TRANSLATE] Statistics Box",
  "advancement.s3.statistics_box.title": "[TRANSLATE] Statistics Box",
  "advancement.s3.statistics_box.description": "[TRANSLATE] View detailed storage system metrics"
}
```

- [ ] **Step 2: Run validateLang to confirm the skeleton passes**

```bash
./gradlew :core:validateLang
```

Expected: `BUILD SUCCESSFUL`. If it fails, the skeleton has a missing or extra key — diff it against `en_us.json` and fix.

- [ ] **Step 3: Commit**

```bash
git add core/src/main/resources/assets/s3/lang/es_es.json
git commit -m "feat(core): add es_es.json translation skeleton for Spanish"
```

---

### Task 4: Create es_es.json skeleton for s3_advanced

**Files:**
- Create: `neoforge/s3-advanced/src/main/resources/assets/s3_advanced/lang/es_es.json`

- [ ] **Step 1: Create the skeleton file**

Copy all keys from `neoforge/s3-advanced/src/main/resources/assets/s3_advanced/lang/en_us.json` and prefix each value with `[TRANSLATE] `:

```json
{
  "itemGroup.s3_advanced.tab": "[TRANSLATE] Steve's Advanced Storage",

  "block.s3_advanced.advanced_storage_core": "[TRANSLATE] Advanced Storage Core",
  "block.s3_advanced.solar_generator": "[TRANSLATE] Solar Generator",
  "block.s3_advanced.coal_generator": "[TRANSLATE] Coal Generator"
}
```

- [ ] **Step 2: Run validateLang to confirm the skeleton passes**

```bash
./gradlew :neoforge:s3-advanced:validateLang
```

Expected: `BUILD SUCCESSFUL`.

- [ ] **Step 3: Commit**

```bash
git add neoforge/s3-advanced/src/main/resources/assets/s3_advanced/lang/es_es.json
git commit -m "feat(neoforge/s3-advanced): add es_es.json translation skeleton for Spanish"
```

---

## Chunk 3: Contributor Workflow

### Task 5: Create docs/translations.md

**Files:**
- Create: `docs/translations.md`

- [ ] **Step 1: Create the contributor guide**

```markdown
# Contributing Translations

Steve's Simple Storage supports Minecraft's standard localization system. All user-facing strings use `Component.translatable()` keys defined in `en_us.json`. Adding a new language is a matter of creating one JSON file and opening a PR.

## Before You Start

Find your locale code in [Minecraft's locale list](https://minecraft.wiki/w/Language). Examples: `de_de` (German), `fr_fr` (French), `zh_cn` (Chinese Simplified), `pt_br` (Portuguese Brazil).

## Steps

### 1. Copy en_us.json to your locale file

For the base mod (required):

```
core/src/main/resources/assets/s3/lang/en_us.json
→ copy to →
core/src/main/resources/assets/s3/lang/<locale>.json
```

If you also want to translate the Advanced Storage add-on (optional):

```
neoforge/s3-advanced/src/main/resources/assets/s3_advanced/lang/en_us.json
→ copy to →
neoforge/s3-advanced/src/main/resources/assets/s3_advanced/lang/<locale>.json
```

> **Important:** Copy from `en_us.json`, not from another translated file (e.g., `es_es.json`). Copying from a translated file carries over the wrong language's values.

### 2. Translate the values

Replace each English value with your translation. Do not change the keys (the part before the `:`).

Example:

```json
"block.s3.storage_core": "Núcleo de almacenamiento"
```

The `warning.s3.break_storage` key contains a `%s` placeholder — keep it in your translation where it makes grammatical sense:

```json
"warning.s3.break_storage": "¡Advertencia! Se perderán %s objetos almacenados. Usa un carro para conservarlos."
```

### 3. Validate your file locally

```bash
./gradlew validateLang
```

This checks that your file has exactly the same keys as `en_us.json` — no missing or extra keys. Fix any reported mismatches before opening a PR.

### 4. Open a PR

Open a pull request to the `main` branch. Use the PR template checklist. A maintainer will review for completeness and merge.

## Notes

- **Machine translations** are accepted as a starting point but must be disclosed in the PR description.
- **Partial translations** are not accepted — all keys must be translated.
- The `[TRANSLATE]` prefix you may see in skeleton files (e.g., `es_es.json`) marks untranslated stubs. Do not include this prefix in your finished translation.
- If you are fluent in a language not yet listed in [issue #44](https://github.com/scuba10steve/steves-simple-storage/issues/44), feel free to add it — leave a comment on the issue so others know it is in progress.

## Future

If translation volume grows, we may adopt [Crowdin](https://crowdin.com/) — a web-based platform where contributors can translate without touching git. Files submitted via Crowdin must still pass `./gradlew validateLang`.
```

- [ ] **Step 2: Commit**

```bash
git add docs/translations.md
git commit -m "docs: add translation contributor guide"
```

---

### Task 6: Create GitHub PR template

**Files:**
- Create: `.github/pull_request_template.md`

- [ ] **Step 1: Create the template**

```markdown
## Description

<!-- What does this PR do? -->

## Type of change

- [ ] Bug fix
- [ ] New feature
- [ ] Translation (new or updated lang file)
- [ ] Documentation
- [ ] Build / CI
- [ ] Other

---

## Translation checklist

_Complete this section only if this PR adds or updates a lang file. Delete it otherwise._

- [ ] File is named with the correct Minecraft locale code (e.g., `de_de.json`, not `de.json`)
- [ ] All keys from `en_us.json` are present — no missing or extra keys
- [ ] `./gradlew validateLang` passes locally
- [ ] Translation method: <!-- human / machine-assisted / machine-only -->
- [ ] Tested in-game with the target locale (encouraged, not required)
```

- [ ] **Step 2: Commit**

```bash
git add .github/pull_request_template.md
git commit -m "ci: add PR template with translation checklist"
```

---

## Chunk 4: Issue Update

### Task 7: Update issue #44 with the language checklist

- [ ] **Step 1: Update the issue body**

```bash
gh issue edit 44 --repo scuba10steve/steves-simple-storage --body "$(cat <<'EOF'
## Context

All user-facing strings in the mod use \`Component.translatable()\` with keys in \`en_us.json\`, which is the correct pattern for Minecraft localization. However, only English translations exist currently.

## Desired Behavior

Add translation files for additional languages so non-English players see localized block names, GUI labels, and warning messages.

## How to Contribute

See [docs/translations.md](docs/translations.md) for the full contribution guide. Copy \`en_us.json\` to your locale file, translate the values, run \`./gradlew validateLang\`, and open a PR.

The \`es_es.json\` skeleton file is already committed as a starting point for Spanish contributors.

## Translation Progress

**Priority**
- [ ] \`es_es\` — Spanish (Spain) ⭐ first target

**Romance** (\`es_es\` listed under Priority above)
- [ ] \`es_mx\` — Spanish (Mexico)
- [ ] \`pt_br\` — Portuguese (Brazil)
- [ ] \`pt_pt\` — Portuguese (Portugal)
- [ ] \`fr_fr\` — French
- [ ] \`it_it\` — Italian

**Germanic**
- [ ] \`de_de\` — German
- [ ] \`nl_nl\` — Dutch
- [ ] \`sv_se\` — Swedish
- [ ] \`nb_no\` — Norwegian

**Slavic**
- [ ] \`ru_ru\` — Russian
- [ ] \`pl_pl\` — Polish
- [ ] \`uk_ua\` — Ukrainian

**CJK**
- [ ] \`zh_cn\` — Chinese (Simplified)
- [ ] \`zh_tw\` — Chinese (Traditional)
- [ ] \`ja_jp\` — Japanese
- [ ] \`ko_kr\` — Korean

**Other**
- [ ] \`tr_tr\` — Turkish
- [ ] \`ar_sa\` — Arabic
EOF
)"
```

- [ ] **Step 2: Verify the issue updated correctly**

```bash
gh issue view 44
```

Confirm the checklist and contribution guide link appear in the output.

- [ ] **Step 3: Commit the plan file (no code changes needed for this task)**

```bash
git add docs/superpowers/plans/2026-03-17-i18n-translations.md docs/superpowers/specs/2026-03-17-i18n-design.md
git commit -m "docs: add i18n spec and implementation plan"
```
