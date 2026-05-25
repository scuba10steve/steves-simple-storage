# Security

Lock and protect your storage system so only you (or players you trust) can access it.

## How It Works

The security system is built around two items:

| Item             | Purpose                                        |
|------------------|------------------------------------------------|
| **Security Box** | Enables security when placed in the multiblock |
| **Key**          | Used to lock and unlock the system             |

## Setting Up Security

### Step 1 — Place a Security Box

Place a **Security Box** block adjacent to your Storage Core. The system will detect it automatically.

Once a Security Box is in the multiblock, the storage system starts **unlocked** — anyone can access it.

### Step 2 — Lock with a Key

Right-click the **Key** item on the Storage Core (or any Security Box) to **lock** the system.

When locked:

- Only the player holding the Key can access the storage
- Other players see the storage GUI but cannot interact with it
- The Key is bound to your account

### Step 3 — Unlock (if needed)

If you need to unlock the system, right-click the Key on the Storage Core again.

## Whitelist Mode

By default, only the player holding the Key can access the storage. You can also add specific players to a **whitelist**
to allow them access.

To manage the whitelist, open the storage GUI (you must be holding the Key) and look for the security options panel.
From there you can:

- Add a player to the whitelist by name
- Remove a player from the whitelist
- Toggle whitelist mode on or off

When whitelist mode is on, only players on the list (plus the Key holder) can access the storage.

## Operator Override

Server operators (players with `/op`) can always access locked storage, regardless of the whitelist. This can be
disabled in the config:

```toml
[features]
    enableOpOverride = true   # true = ops can override; false = ops are blocked too
```

## Access Terminal

The **Access Terminal** provides remote access to your locked storage system.

- Place it up to **64 blocks** away from the Storage Core
- Right-click to open the same storage GUI as the core itself
- Access is subject to the same security rules — if you're locked out of the core, you're locked out of the terminal

This is useful for:

- Putting the terminal in your base while the storage is elsewhere
- Sharing access through a wall or floor
- Multiple access points for the same storage system

## Breaking a Locked System

If you lose your Key, you can break the Security Box to remove security. This does **not** unlock the storage — it
removes the security layer entirely, making the storage accessible to everyone again.

Note: If `enableOpOverride` is `true` on the server, an operator can simply access the storage directly without needing
the Key.
