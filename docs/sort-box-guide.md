# Sort Box - Quick Reference

## What is the Sort Box?

The Sort Box is a multiblock component that adds sorting functionality to your Storage Core system. When present in the multiblock, it enables a sort button in the GUI that lets you organize your items in different ways.

## Crafting Recipe

```
W C W
C S C
W C W

W = Wood Log (any type)
C = Comparator
S = Storage Box
```

## How to Use

1. **Build your multiblock** - Place Storage Core and Storage Boxes
2. **Add Sort Box** - Place it adjacent to any block in the multiblock
3. **Open GUI** - Right-click the Storage Core
4. **Click Sort Button** - Located below the storage grid, shows current mode
5. **Cycle Modes** - Each click switches to the next sorting mode

## Sorting Modes

| Mode | Description | Example |
|------|-------------|---------|
| **Count Down** ⬇️ | Most items first, then A-Z | 1000 Cobblestone, 500 Dirt, 100 Stone |
| **Count Up** ⬆️ | Fewest items first, then Z-A | 1 Diamond, 5 Iron, 100 Cobblestone |
| **Name A-Z** 🔤 | Alphabetical order | Apple, Bread, Carrot, Diamond |
| **Name Z-A** 🔤 | Reverse alphabetical | Zombie Head, Wood, Stone, Apple |
| **Mod A-Z** 📦 | By mod name, A-Z | Minecraft items, then Mod A, then Mod B |
| **Mod Z-A** 📦 | By mod name, Z-A | Mod Z items, then Mod A, then Minecraft |

## Tips

- **Persistent**: Your selected sort mode is saved and remembered
- **Works with Search**: Search results are displayed in sorted order
- **JEI Compatible**: Recipe lookup works with sorted items
- **No Performance Impact**: Sorting is efficient even with thousands of items

## Multiblock Example

```
[Storage Box] [Storage Box] [Storage Box]
[Storage Box] [Storage Core] [Sort Box]
[Storage Box] [Storage Box] [Storage Box]
```

The Sort Box can be placed anywhere adjacent to the multiblock structure.

## Visual Indicators

When a Sort Box is present:
- ✅ Sort button appears in GUI (below storage grid)
- ✅ Button shows current mode name (e.g., "Count Down")
- ✅ Items automatically arrange according to selected mode

When no Sort Box is present:
- ❌ No sort button in GUI
- ❌ Items appear in insertion order

## Combining with Other Features

### Sort Box + Search Box
- Search results are displayed in sorted order
- Very useful for finding items in large storage systems

### Sort Box + Crafting Box
- Crafting ingredients are pulled from sorted storage
- Makes it easier to see what you have available

### Sort Box + Extract Port
- Extracted items follow the storage order
- Useful for automation setups

## Troubleshooting

**Q: Sort button doesn't appear**
- A: Make sure the Sort Box is adjacent to the multiblock
- A: Break and replace the Storage Core to re-scan the multiblock

**Q: Items don't stay sorted**
- A: Sorting is visual only - items are sorted when displayed
- A: The sort mode persists across GUI opens/closes

**Q: Can I have multiple Sort Boxes?**
- A: Yes, but only one is needed - multiple Sort Boxes don't add extra functionality

**Q: Does sorting affect performance?**
- A: No, sorting is very efficient and only happens when displaying items
