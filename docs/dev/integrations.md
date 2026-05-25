# Integrations

This document tracks integration status for Steve's Simple Storage (S3): what is already implemented, what is generic
interoperability, and what integrations are good candidates next.

## Field Notes (ATM10)

Current real-world testing context from active pack usage:

- Target modpack context: **All The Mods 10 (ATM10)**
- **Mekanism compatibility**: mostly working based on in-pack playtesting
- **Functional Storage**: not directly tested yet
- **Tom's Simple Storage**: currently low priority (not in ATM10 and not a familiar target)

How this affects priorities:

- Move item-logistics validation from "unknown" to "stabilize and document"
- Keep Functional Storage as a concrete validation target
- De-emphasize Tom's Simple Storage unless user demand appears

## Current Integrations

### JEI (Just Enough Items) - Implemented

Status: **Implemented and enabled by default**

What is currently integrated:

- Recipe transfer from JEI into S3 crafting menus
- Ingredient lookup (R/U workflow) from S3 storage GUI entries
- Runtime bridge so S3 can open JEI recipe UI from S3 screens

Where it is wired:

- `neoforge/s3/src/main/java/io/github/scuba10steve/s3/jei/StorageJEIPlugin.java`
- `neoforge/s3/src/main/java/io/github/scuba10steve/s3/jei/StorageRecipeTransferHandler.java`
- `neoforge/s3/src/main/java/io/github/scuba10steve/s3/jei/StorageGuiContainerHandler.java`
- `neoforge/s3/src/main/java/io/github/scuba10steve/s3/jei/JEIIntegration.java`

Configuration:

- `integration.jeiIntegration` in `config/s3-common.toml`

Dependency type:

- `runtimeOnly` in `neoforge/s3/build.gradle`

---

### Polymorph - Implemented (Optional)

Status: **Implemented, loaded only when present**

What is currently integrated:

- Crafting recipe conflict resolution in S3 crafting context
- Client-side Polymorph recipe-selection widget on S3 crafting screen

Where it is wired:

- Runtime detection and registration in `neoforge/s3/src/main/java/io/github/scuba10steve/s3/StevesSimpleStorage.java`
- Client widget registration in `neoforge/s3/src/main/java/io/github/scuba10steve/s3/client/ClientEvents.java`
- Compatibility adapter in `neoforge/s3/src/main/java/io/github/scuba10steve/s3/compat/PolymorphCompat.java`

Dependency type:

- `compileOnly` in `neoforge/s3/build.gradle` with `ModList.get().isLoaded("polymorph")` guards

---

### Generic Item Automation / Cross-Mod Interop - Implemented

Status: **Implemented as generic NeoForge capability interoperability**

What is currently integrated:

- `Capabilities.ItemHandler.BLOCK` exposure for:
    - Input Port
    - Extract Port
    - Storage Interface
- Allows hopper/pipe/item-network mods to insert/extract through standard item handler APIs

Where it is wired:

- Capability registration: `neoforge/s3/src/main/java/io/github/scuba10steve/s3/init/ModCapabilities.java`
- Storage Interface handler:
  `neoforge/s3/src/main/java/io/github/scuba10steve/s3/blockentity/StorageInterfaceBlockEntity.java`

Notes:

- This is not a single hard dependency on another mod; it is baseline compatibility with any mod using NeoForge item
  handler capabilities.

## Integration Gaps / Candidates

The following options are listed in rough priority order, balancing user impact and implementation complexity.

### High-Value Near-Term

1. **EMI support**
    - Why: growing adoption as a JEI alternative; similar player expectations around recipe transfer/lookup
    - Scope: plugin + transfer handlers + GUI interaction hooks (parallel to JEI architecture)

2. **REI support (if NeoForge support target is stable enough)**
    - Why: additional recipe viewer ecosystem coverage
    - Scope: equivalent to JEI/EMI feature parity for transfer and lookup

3. **Jade / The One Probe overlays**
    - Why: quick visibility into storage stats and multiblock state without opening GUI
    - Scope: provider overlays showing item count, capacity, lock status, and linked core details

### Medium-Term

4. **CraftTweaker / KubeJS recipe scripting hooks**
    - Why: modpack creators want recipe and progression control
    - Scope: expose S3 recipes/components in script APIs; document supported script operations

5. **Curios integration (if wearable/wireless features expand)**
    - Why: enables wearable terminal/key use cases for QoL and progression
    - Scope: optional item slot behavior for S3 utility items

### Longer-Term / Feature-Dependent

6. **Applied Energistics 2 / Refined Storage bridge**
    - Why: major ecosystem interoperability request for late-game packs
    - Scope: likely requires deliberate design boundary to avoid replacing either system's identity

7. **CC: Tweaked automation API**
    - Why: programmable access appeals to technical automation players
    - Scope: peripheral or API surface for querying and moving items

8. **Energy and fluid ecosystem integrations**
    - Why: aligns with planned advanced-storage direction (power/fluid components)
    - Scope: expose and consume FE/fluid capabilities when those systems are introduced

## Additional Candidate Options

These are extra options worth considering after the core roadmap above.

### Storage and Inventory Ecosystem

9. **Tom's Simple Storage compatibility layer**
    - Why: conceptually similar user base; useful for migration packs and side-by-side systems
    - Scope: import/export adapter recipes or bridge block behavior where feasible
    - Priority note: currently lower priority for ATM10-focused work

10. **Sophisticated Backpacks / Sophisticated Storage**
- Why: common in modpacks; players expect portable storage workflows to connect smoothly
- Scope: recipe lookup and item transfer UX tuning, plus optional whitelist/blacklist rules

11. **Functional Storage**
- Why: high pack adoption; drawer-style bulk storage complements S3 core storage
- Scope: documented capability interoperability and optional QoL hooks for bulk transfers

### Automation and Item Logistics

12. **Pipez / Mekanism logistical transport / modular pipes**

- Why: broad automation mod usage; users care about predictable extraction/insertion behavior
- Scope: compatibility validation matrix + side-aware behavior docs for ports/interfaces
- Current status note: Mekanism appears mostly compatible in ATM10; next step is formal validation/documentation

13. **Create logistics adjacency support**

- Why: Create-heavy packs are common and benefit from smooth mechanical automation interop
- Scope: test and document belt/funnel/chute interactions with S3 ports and interface

### Information and UI Integrations

14. **Catalogue / Configured / Mod Menu equivalents (where available)**

- Why: easier in-game configuration discoverability
- Scope: config screen entry points and category descriptions for S3 settings

15. **FTB Quests / Better Questing helper integration**

- Why: improves pack onboarding; storage progression is often quest-driven
- Scope: documented detection hooks and optional helper tags for quest objectives

### Server and Tooling Integrations

16. **Spark profiling playbook integration**

- Why: large storage systems can become performance-sensitive on servers
- Scope: ship recommended profiling commands and S3-specific interpretation guidance

17. **Server utility mods (e.g., chunk loaders / claim mods) compatibility notes**

- Why: common source of "it works in singleplayer but not server" reports
- Scope: known-behavior matrix for chunk-loading, permissions, and automation edge cases

## Selection Heuristics

To choose what to build first:

- Prefer integrations that improve **core crafting and retrieval UX** (recipe viewers, overlays)
- Next prioritize integrations that reduce **modpack support friction** (automation compatibility docs)
- Defer deep bridges (AE2/RS-level) until there is a clear **design boundary** and maintenance budget

## Suggested Next Steps

If choosing one immediate target for **new** integration work, **EMI** is likely the highest-impact next integration
after JEI/Polymorph.

Recommended rollout:

1. Add `docs` design note for shared recipe-viewer abstraction (JEI already exists; avoid duplicated logic)
2. Implement minimal viewer parity (lookup + transfer)
3. Add manual verification checklist in `TESTING.md` for cross-viewer behavior
4. Optional: add gametest or integration smoke hooks where feasible

## Immediate Validation Backlog (ATM10-Oriented)

1. Verify and document Mekanism edge cases (sided IO, extraction pacing, redstone disable behavior)
2. Run direct compatibility pass for Functional Storage (drawer insert/extract behavior through S3 interfaces)
3. Add a short "Known Working in ATM10" section once the above is confirmed
