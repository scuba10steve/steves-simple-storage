# Changelog

All notable changes to Steve's Simple Storage (S3) for Minecraft 1.21.1 will be documented in this file.

## [Unreleased]

### Added
- Dolly and Super Dolly functionality: pick up chests and storage cores (with contents), place them back down, with durability and dynamic item textures
- Crafting Box with 3x3 crafting grid connected to storage system
- Shift-click crafting with automatic ingredient repopulation from storage
- JEI recipe/usage lookup (R/U keys) for items in storage grids
- Hover highlight effect for storage items
- Shared `AbstractStorageScreen` base class for consistent GUI behavior
- Search Box enabling item filtering in Storage Core GUI
- Real-time search with 4 modes: standard (name/tooltip), `$` (tags), `@` (mod ID), `%` (item name)
- Sort Box with 6 sorting modes: Count Down, Count Up, Name A-Z, Name Z-A, Mod A-Z, Mod Z-A
- Sort button in Storage Core GUI when Sort Box is present in multiblock
- Automatic sorting of storage items based on selected mode
- Eject Port for automatically pushing items from storage into inventories above it
- Redstone control for Eject Port (stops ejecting when powered)
- Security Box with player whitelist management and Key item integration
- Security event system blocking unauthorized multiblock interactions
- Operator override with configurable notifications for security bypass
- Security sync/player packets for client-server whitelist management
- Access Terminal for remote Storage Core GUI access via multiblock network

### Fixed
- JEI hotkeys (R/U) not working on storage items (was only recipe transfer)

---

## [0.1.0-beta] - 2026-01-14

### Status
**Beta Release** - Core storage functionality complete and stable. Advanced features planned for future releases.

### Added
- Complete port of Steve's Simple Storage core functionality to Minecraft 1.21.1
- Modern NeoForge 21.1.218 support with ModDevGradle 2.0.139
- Storage Core block with GUI interface
- Seven tiers of storage blocks (Basic, Condensed, Compressed, Super, Ultra, Hyper, Ultimate)
- Multiblock structure system with automatic detection
- Dynamic capacity scaling based on connected blocks
- JEI integration for recipe transfer (v19.27.0.336)
- Custom click handling for storage interactions
- Real-time UI synchronization
- Comprehensive TOML configuration system
- Debug logging throughout the system
- Unit tests for core constants

### Fixed
- UI not displaying items on first interaction
- Left-click extraction not updating UI
- Right-click extracting half-stack then depositing 1 item
- Drag-and-drop not working for item placement
- Missing textures for tiered storage blocks
- Storage capacity not updating when blocks added/removed
- Storage persistence and GUI refresh issues
- Item retrieval and display synchronization

### Changed
- Package structure from `com.zerofall.ezstorage` to `io.github.scuba10steve.s3`
- Updated NeoForge from 21.1.77 to 21.1.218
- Updated ModDevGradle from 2.0.46-beta to 2.0.139 (stable)
- Updated JEI from 19.19.6.233 to 19.27.0.336
- Updated JUnit Jupiter from 5.10.1 to 5.11.4
- Removed StorageSlot instances in favor of pure custom packet handling
- Simplified block models to use cube_all parent
- Updated blockstate format to modern Minecraft standards

### Technical Details
- Minecraft Version: 1.21.1
- NeoForge Version: 21.1.218
- ModDevGradle Version: 2.0.139
- Java Version: 21
- Gradle Version: 8.10.2

---

## Version History

### Version 0.1.0-beta - Initial Beta Release
First beta release of Steve's Simple Storage for Minecraft 1.21.1 with complete core storage functionality. Advanced features (Crafting Box, Search Box, etc.) planned for future releases.
