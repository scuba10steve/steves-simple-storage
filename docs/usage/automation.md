# Automation

Connect your storage system to item pipelines, hoppers, and pipe networks from other mods.

## Overview

All automation blocks expose the storage system via NeoForge's `IItemHandler` capability. This means any mod that uses
standard item handler APIs can interact with them — including hoppers, Mekanism pipes, Refined Storage, Create
logistics, Pipez, and most other pipe/network mods.

Automation blocks must be placed **adjacent** to the Storage Core to be detected.

## Ports

### Input Port

**ID:** `s3:input_port`

Pulls items from the inventory **in front of** the port and inserts them into your storage.

```
  [inventory]
  [input_port] ← placed here, facing the inventory
  [storage core]
```

The Input Port has a **hopper** pointing into it. Items sitting in the adjacent inventory will be automatically pulled
in.

**How it works:**

1. Place the Input Port on the face of any container (chest, barrel, etc.)
2. The port's hopper side faces the container
3. Items in that container are pulled into your storage automatically

**Use cases:**

- Feed items from an auto-fisher, mob farm, or farm into storage
- Load items from a processing system into storage

### Extract Port

**Output:** `s3:extract_port`

Extracts items from your storage and places them into the inventory **in front of** the port.

```
[storage core]
[extract_port] ← placed here, facing the inventory
  [inventory]
```

**How it works:**

1. Place the Extract Port on the face of any container
2. Items are automatically pulled from your storage into that container

**Use cases:**

- Unload items from storage into a chest for processing
- Feed items into an auto-crafter or other processing system

**Right-click the Extract Port** to open its GUI and configure extraction behavior.

#### Extract Port Modes

Right-click the Extract Port to set how it extracts items:

| Mode               | Behavior                                                                    |
|--------------------|-----------------------------------------------------------------------------|
| **Round Robin**    | Distributes items evenly across all matching slots in the target inventory  |
| **Single Item**    | Extracts one item at a time (slowest, most precise)                         |
| **Match Template** | Extracts items matching a specific item you place in the port's filter slot |

#### Extract Interval

By default, the Extract Port attempts to extract every 8 game ticks. This can be adjusted in `config/s3-common.toml`:

```toml
[automation]
    extractPortInterval = 8   # 1–100 ticks. Lower = faster extraction but more server load.
```

### Eject Port

**ID:** `s3:eject_port`

The inverse of an Input Port. Pushes items **out of** the inventory in front of it as item entities into the world.

**How it works:**

1. Place the Eject Port on the face of any container
2. Items in that container are ejected into the world as drops

**Use cases:**

- Void unwanted items
- Dispense items from a container into the world

## Storage Interface

**ID:** `s3:storage_interface`

The most flexible automation option. Exposes your entire storage inventory to any mod that uses `IItemHandler`.

```
[pipe from any mod]
  [storage_interface]
  [storage core]
```

Any pipe, conduit, or cable that supports item handlers can connect to it. This includes:

- Mekanism logistical transporters
- Refined Storage cables
- Create mechanical pumps and belts (with appropriate placer)
- Pipez pipes
- And most other pipe/network mods

**How it works:**

1. Place the Storage Interface adjacent to the Storage Core
2. Connect a pipe/cable from any compatible mod to the Storage Interface
3. The mod can push items into or pull items out of your storage through the pipe

**Tip:** Use the Storage Interface on its own side — don't combine it with an Input or Extract Port on the same face, as
they may conflict.

## Common Setups

### Basic Hopper Setup

```
[chest with items] ← hopper feeds into → [input_port] → [storage_core]
```

Place a chest with items. A vanilla hopper between the chest and an Input Port will pull items from the chest and feed
them into the port.

### Auto-Unload Setup

```
[storage_core] → [extract_port] → [chest]
```

Items automatically flow from your storage into a nearby chest for pickup or further processing.

### Pipe Network Setup

```
[mekanism/pipez/refined storage pipe]
      [storage_interface]
      [storage_core]
```

Connect your pipe network directly to the Storage Interface. Use filter settings in your pipe mod to control what gets
extracted.

## Notes

- All automation is **tick-based**. The Extract Port checks for items to extract every few ticks. On fast servers, lower
  `extractPortInterval`. On busy servers, increase it to reduce load.
- Automation blocks only work when placed **adjacent** to the Storage Core. If you break and move the core, automation
  blocks need to be repositioned.
- `minSyncInterval` in the config (default: 2 ticks) controls how often the storage GUI syncs to clients. Setting this
  to 0 can make automation appear more responsive but increases network traffic.
