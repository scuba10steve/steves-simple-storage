# Search & Sorting

Find and organize items quickly in your storage.

## Search Box

The Search Box adds a **search bar** to the top of the storage GUI. You must place a **Search Box** block in your
multiblock to enable it.

### Placing the Search Box

Place a Search Box adjacent to your Storage Core (directly or through other storage blocks). The search bar appears in
the GUI automatically when you open it.

### How to Search

Click the search bar at the top of the storage GUI and type. Items are filtered in real time as you type — no need to
press Enter.

### Search Modes

The Search Box supports four different search modes, selected by prefix:

| Prefix   | Mode           | Example                                                  |
|----------|----------------|----------------------------------------------------------|
| *(none)* | Standard text  | `diamond` matches items containing "diamond"             |
| `$`      | Ore dictionary | `$ingot` matches all ingot-type items                    |
| `@`      | Mod name       | `@minecraft` matches only vanilla Minecraft items        |
| `%`      | Tooltip        | `%enchant` matches items with "enchant" in their tooltip |

You can combine prefixes: `@minecraft$diamond` would match vanilla diamond items.

### Search Behavior

- Search is **case-insensitive**
- Partial matches work — `iron` matches `iron_ingot`, `iron_block`, etc.
- The search filters the 54 displayed slots; all matching items are shown
- Sort mode (if enabled) applies to search results too

## Sort Box

The Sort Box adds a **sort button** to the storage GUI. You must place a **Sort Box** block in your multiblock to enable
it.

### Placing the Sort Box

Place a Sort Box adjacent to your Storage Core. The sort button appears in the GUI header.

### Sort Modes

Click the sort button to cycle through these modes:

| Mode           | Description                                               |
|----------------|-----------------------------------------------------------|
| **Count Down** | Highest item counts first, then alphabetical              |
| **Count Up**   | Lowest item counts first, then reverse alphabetical       |
| **Name A–Z**   | Alphabetical by item name, then by descending count       |
| **Name Z–A**   | Reverse alphabetical, then by ascending count             |
| **Mod A–Z**    | Alphabetical by mod name, then by descending count        |
| **Mod Z–A**    | Reverse alphabetical by mod name, then by ascending count |

### How Sorting Works

- Clicking the sort button rotates through modes in order: Count Down → Count Up → Name A–Z → Name Z–A → Mod A–Z → Mod
  Z–A → back to Count Down
- The current mode persists when you close and reopen the GUI
- Sorting affects the order items appear in the storage slots, JEI queries from storage, and search results
- Sorting is applied to the display order — it does not change the underlying item storage

### Sync Behavior

Search mode and sort mode are synced to the server when changed, and the updated list is broadcast to all players
viewing the storage. If two players open the same storage simultaneously, each sees their own search/sort preferences.

## Combining Search and Sort

Search and Sort work together:

1. Set your sort mode (e.g., "Name A–Z")
2. Type in the search bar to filter items
3. The filtered results are sorted according to your current sort mode

## Config Option

Advanced search modes (`$`, `@`, `%`) can be disabled server-side if desired:

```toml
[features]
    enableSearchModes = true   # false = only standard text search works
```
