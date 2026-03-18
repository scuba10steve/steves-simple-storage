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
