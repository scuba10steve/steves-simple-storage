# Advancements Design

Issue: #43

## Overview

Add a JSON-only advancement tree using vanilla `recipe_unlocked` triggers. One tab, one branching tree rooted at the Storage Core. Every registered block and item gets an advancement. No custom Java triggers or milestone-based criteria.

## Tree Structure

Single tab ("Steve's Simple Storage") with the Storage Core as root. Branches:

```
Storage Core (root)
├── Storage Box → Condensed → Compressed → Super → Ultra → Hyper → Ultimate [challenge]
├── Search Box → Sort Box
├── Crafting Box
├── Security Box → Key
├── Blank Box
├── Access Terminal → Storage Interface
├── Input Port → Extract Port → Eject Port
└── Dolly → Super Dolly
```

Total: 21 advancements.

## Trigger

All advancements use `minecraft:recipe_unlocked` with the corresponding `s3:*` recipe.

## Frames

- `task` for all advancements except Ultimate Storage Box
- `challenge` for Ultimate Storage Box

## File Structure

Advancement JSONs live in `core/src/main/resources/data/s3/advancement/`.

Each file follows this format:

```json
{
  "display": {
    "icon": { "id": "s3:<item>" },
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
      "conditions": {
        "recipe": "s3:<item>"
      }
    }
  }
}
```

The root advancement omits `parent` and includes a `background` field pointing to `s3:textures/gui/advancements_background.png`.

## Translation Keys

Added to `core/src/main/resources/assets/s3/lang/en_us.json`:

| Key | Value |
|---|---|
| `advancement.s3.root.title` | Storage Core |
| `advancement.s3.root.description` | Craft the heart of your storage system |
| `advancement.s3.storage_box.title` | Storage Box |
| `advancement.s3.storage_box.description` | Craft a basic Storage Box |
| `advancement.s3.condensed_storage_box.title` | Condensed Storage |
| `advancement.s3.condensed_storage_box.description` | Pack more items into less space |
| `advancement.s3.compressed_storage_box.title` | Compressed Storage |
| `advancement.s3.compressed_storage_box.description` | Further compress your storage capacity |
| `advancement.s3.super_storage_box.title` | Super Storage |
| `advancement.s3.super_storage_box.description` | Upgrade to super-sized storage |
| `advancement.s3.ultra_storage_box.title` | Ultra Storage |
| `advancement.s3.ultra_storage_box.description` | Push your storage even further |
| `advancement.s3.hyper_storage_box.title` | Hyper Storage |
| `advancement.s3.hyper_storage_box.description` | Achieve massive storage capacity |
| `advancement.s3.ultimate_storage_box.title` | Ultimate Storage |
| `advancement.s3.ultimate_storage_box.description` | Reach the pinnacle of storage technology |
| `advancement.s3.search_box.title` | Search Box |
| `advancement.s3.search_box.description` | Find items instantly with search |
| `advancement.s3.sort_box.title` | Sort Box |
| `advancement.s3.sort_box.description` | Keep your storage organized |
| `advancement.s3.crafting_box.title` | Crafting Box |
| `advancement.s3.crafting_box.description` | Craft directly from your storage |
| `advancement.s3.security_box.title` | Security Box |
| `advancement.s3.security_box.description` | Lock down your storage system |
| `advancement.s3.key.title` | Key |
| `advancement.s3.key.description` | Craft the key to your security |
| `advancement.s3.blank_box.title` | Blank Box |
| `advancement.s3.blank_box.description` | Craft a blank canvas |
| `advancement.s3.access_terminal.title` | Access Terminal |
| `advancement.s3.access_terminal.description` | Access your storage remotely |
| `advancement.s3.storage_interface.title` | Storage Interface |
| `advancement.s3.storage_interface.description` | Connect your storage to other mods |
| `advancement.s3.input_port.title` | Input Port |
| `advancement.s3.input_port.description` | Automate item insertion |
| `advancement.s3.extract_port.title` | Extract Port |
| `advancement.s3.extract_port.description` | Automate item extraction |
| `advancement.s3.eject_port.title` | Eject Port |
| `advancement.s3.eject_port.description` | Push items to adjacent inventories |
| `advancement.s3.dolly.title` | Dolly |
| `advancement.s3.dolly.description` | Move your storage blocks with ease |
| `advancement.s3.super_dolly.title` | Super Dolly |
| `advancement.s3.super_dolly.description` | A more durable way to move blocks |

## Background Texture

A 16x16 tiling PNG at `core/src/main/resources/assets/s3/textures/gui/advancements_background.png`. Simple dark texture similar to vanilla stone.

## No Java Code Required

Everything is data-driven: JSON advancement files, lang entries, and one texture. No custom triggers, criteria, or registration code.
