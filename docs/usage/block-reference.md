# Block Reference

Every block and item in Steve's Simple Storage, explained.

## Storage Blocks

### Storage Core
**ID:** `s3:storage_core`

The heart of your storage system. Right-click to open the storage GUI. The Storage Core scans for all adjacent storage blocks and calculates your total capacity.

- Must be placed to start a new storage system
- Only one Storage Core per multiblock
- Right-click to access the GUI

### Storage Box (Basic)
**ID:** `s3:storage_box`
**Capacity:** +10,000 items

The entry-level storage block. Place adjacent to a Storage Core to add capacity.

### Condensed Storage Box
**ID:** `s3:condensed_storage_box`
**Capacity:** +40,000 items

A step up from the basic Storage Box. 4× the capacity.

### Compressed Storage Box
**ID:** `s3:compressed_storage_box`
**Capacity:** +80,000 items

2× the capacity of Condensed.

### Super Storage Box
**ID:** `s3:super_storage_box`
**Capacity:** +160,000 items

2× the capacity of Compressed.

### Ultra Storage Box
**ID:** `s3:ultra_storage_box`
**Capacity:** +640,000 items

4× the capacity of Super.

### Hyper Storage Box
**ID:** `s3:hyper_storage_box`
**Capacity:** +2,560,000 items

4× the capacity of Ultra. A significant upgrade.

### Ultimate Storage Box
**ID:** `s3:ultimate_storage_box`
**Capacity:** +10,240,000 items

The highest tier. 4× the capacity of Hyper. Built with Netherite.

## Feature Blocks

All feature blocks must be placed adjacent to the Storage Core (directly or through other storage blocks) to be detected.

### Blank Box
**ID:** `s3:blank_box`

Cosmetic-only block. Use it to fill gaps in your multiblock or to shape the multiblock without adding capacity.

### Crafting Box
**ID:** `s3:crafting_box`

Adds a 3×3 crafting grid to the storage GUI. Ingredients are pulled from and results are pushed to your storage automatically. Shift-click a JEI recipe to auto-fill ingredients.

Requires: placed in multiblock.

### Search Box
**ID:** `s3:search_box`

Adds a search bar to the top of the storage GUI. Enables real-time filtering of items. See [Search & Sorting](search-sorting.md) for search modes.

Requires: placed in multiblock.

### Sort Box
**ID:** `s3:sort_box`

Adds a sort button to the storage GUI. Click it to reorder items in the display. See [Search & Sorting](search-sorting.md) for sort modes.

Requires: placed in multiblock.

### Statistics Box
**ID:** `s3:statistics_box`

Adds a detailed metrics panel to the storage GUI showing:
- Total item count
- Number of unique item types
- Most common item type
- Capacity percentage

Requires: placed in multiblock.

### Security Box
**ID:** `s3:security_box`

Enables the security system. Lets you lock your storage so only you (or players on a whitelist) can access it. See [Security](security.md) for full details.

Requires: placed in multiblock.

### Access Terminal
**ID:** `s3:access_terminal`

A remote interface block. Place up to 64 blocks away from the Storage Core. Right-click to open the same storage GUI as the Storage Core. See [Security](security.md) for details.

Requires: placed within 64 blocks of a Storage Core.

## Automation Blocks

All automation blocks must be placed adjacent to the Storage Core. They expose the storage system via NeoForge's `IItemHandler` capability, making them compatible with hoppers and most pipe/network mods.

### Input Port
**ID:** `s3:input_port`

Attaches to the face of a block. Automatically pulls items from the inventory in front of it and inserts them into storage.

- Place the port on the side of a container (chest, barrel, etc.)
- Items flow from that container into your storage
- Does not require redstone signal

See [Automation](automation.md) for details.

### Extract Port
**ID:** `s3:extract_port`

Attaches to the face of a block. Automatically extracts items from your storage and places them into the inventory in front of it.

- Place the port on the side of a container
- Items flow from your storage into that container
- Supports multiple extraction modes (round-robin, single-item, etc.)
- Does not require redstone signal

See [Automation](automation.md) for details.

### Eject Port
**ID:** `s3:eject_port`

The inverse of an Input Port. Automatically pushes items out of the inventory in front of it. Useful for feeding items into other systems or voiding unwanted items.

- Place the port on the side of a container
- Items in that container are pushed out into the world (as item entities)
- Does not require redstone signal

See [Automation](automation.md) for details.

### Storage Interface
**ID:** `s3:storage_interface`

Exposes your storage to any pipe or item network mod that uses NeoForge's `IItemHandler` capability. Mekanism, Refined Storage, Create logistics, and most other pipe mods can interact with this.

- Place adjacent to the Storage Core
- Other mods' pipes can connect and move items in/out
- Does not require redstone signal

See [Automation](automation.md) for details.

## Items

### Key
**ID:** `s3:key`

Used to lock and unlock your storage system. When a Key is used on a Security Box, the system becomes locked. Only the player holding the Key (or players on the whitelist) can access the storage.

See [Security](security.md) for full details.

### Dolly
**ID:** `s3:dolly`

A handheld item for moving your entire storage multiblock without breaking it.

- Right-click the Storage Core to attach the whole multiblock to the Dolly
- Walk to the new location and right-click to place
- Can move up to 6 blocks worth of storage (core + attachments)
- Breaking the Dolly mid-move drops all blocks

### Super Dolly
**ID:** `s3:dolly_super`

An upgraded Dolly that can move up to 16 blocks. Required for moving large storage systems.
