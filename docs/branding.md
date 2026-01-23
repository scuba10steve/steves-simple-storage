# Branding Guide - Steve's Simple Storage

## Status: Rebranding Complete ✅

The mod has been successfully rebranded from **EZStorage 2** to **Steve's Simple Storage (S3)**. All package structures, mod IDs, and asset directories have been updated.

## Brand Identity

**Name**: Steve's Simple Storage  
**Abbreviation**: S3  
**Version**: 0.1.0-beta  
**Tagline**: TBD

## Inspiration

The name is inspired by AWS S3 (Simple Storage Service), creating a fun parallel between cloud storage and Minecraft storage systems. The personal connection (developer's name is also Steve) adds authenticity to the Minecraft Steve reference.

## Branding Decisions

### Core Identity
- **Mod Name**: Steve's Simple Storage
- **Mod ID**: `s3` ✅ (updated)
- **Display Name**: Steve's Simple Storage ✅ (updated)
- **Short Name**: S3 Storage
- **Package Structure**: `io.github.scuba10steve.s3` ✅ (updated)

### Tagline Options
1. "Simple Storage for Minecraft"
2. "Steve's favorite storage solution"
3. "Storage made simple"
4. "Your simple storage solution"

**Selected**: _[To be decided]_

### Technical Naming
- **Keep or Rebrand Block Names?**
  - Option A: Keep technical names (Storage Core, Storage Box, etc.)
  - Option B: Rebrand to S3 theme (S3 Core, S3 Box, Simple Storage Core, etc.)
  
**Selected**: _[To be decided]_

### Java Class Naming
- **Current Convention**: Mix of "EZ" prefix (EZStorage, EZConfig, EZInventory, EZBlock, etc.) and "Storage" prefix
- **Options**:
  - Option A: Keep "EZ" prefix for backward compatibility and historical reference
  - Option B: Rename all "EZ" prefixed classes to "S3" prefix (S3Storage, S3Config, S3Inventory, etc.)
  - Option C: Rename all "EZ" prefixed classes to "Storage" prefix (StorageConfig, StorageInventory, etc.)
  
**Selected**: _[To be decided]_

**Special Cases**:
- **Main Mod Class**: `EZStorage` → `StevesSimpleStorage` (explicit full name)
- **Mod Name References**: `ezstorage` → `steves-simple-storage` (kebab-case for consistency)

**Classes to Consider**:
- `EZStorage` → `StevesSimpleStorage` (main mod class) ⭐ **Priority**
- `EZConfig` (configuration)
- `EZInventory` (storage inventory)
- `EZBlock` (base block class)
- `EZBlockEntity` (base block entity)
- `EZItem` (base item class)
- `EZTab` (creative tab)
- `EZNetwork` (networking)
- `EZStorageJEIPlugin` (JEI integration)

### Package Structure
- **Current**: `io.github.scuba10steve.s3` ✅ (updated from `io.github.scuba10steve.ezstorage`)
- **Decision**: Completed - Full package rename for consistency

## Rebranding Checklist

### High Priority - COMPLETED ✅
- [x] `gradle.properties` - Updated mod_name and mod_description
- [x] `src/main/resources/META-INF/neoforge.mods.toml` - Updated displayName and description
- [x] `src/main/resources/assets/s3/lang/en_us.json` - Updated mod name and descriptions
- [x] Package structure - Renamed from `io.github.scuba10steve.ezstorage` to `io.github.scuba10steve.s3`
- [x] Mod ID - Changed from `ezstorage` to `s3`
- [x] Asset directories - Renamed from `assets/ezstorage/` to `assets/s3/`
- [x] Data directories - Renamed from `data/ezstorage/` to `data/s3/`

### Medium Priority
- [x] Creative tab display name - Updated to "Steve's Simple Storage"
- [ ] In-game tooltips and descriptions
- [ ] JEI integration display names
- [ ] Java class names - Rename "EZ" prefixed classes (EZStorage, EZConfig, EZInventory, etc.) to "S3" or "Storage" prefix

### Low Priority
- [ ] Logo/icon design (128x128 for mod icon)
- [ ] Banner image for mod pages
- [ ] Social media graphics

### Optional/Future
- [ ] Custom texture overlays with S3 branding
- [ ] Themed block names
- [ ] Easter eggs referencing AWS services

## Visual Identity

### Color Scheme
- **Primary**: TBD
- **Secondary**: TBD
- **Accent**: TBD

### Logo Concepts
- S3 text with storage box icon
- Minecraft chest with "S3" label
- Steve character with storage blocks
- Cloud storage + Minecraft blocks hybrid

**Selected**: _[To be decided]_

## Messaging

### Elevator Pitch
"Steve's Simple Storage (S3) is a Minecraft mod that provides massive, scalable storage through an intuitive multiblock system. Inspired by AWS S3, it makes storing millions of items simple and accessible."

### Key Features to Highlight
1. **Simple** - Easy to use, intuitive interface
2. **Scalable** - Grows with your needs (10K to 2.5M+ items)
3. **Smart** - JEI integration, automatic organization
4. **Stable** - Reliable, well-tested core functionality

### Target Audience
- Players who need massive storage solutions
- Modpack creators looking for reliable storage mods
- Technical Minecraft players who appreciate the AWS reference
- Anyone tired of chest clutter

## Implementation Notes

### Files Requiring Updates

**Configuration Files:**
- `gradle.properties`
- `src/main/resources/META-INF/neoforge.mods.toml`

**Language Files:**
- `src/main/resources/assets/ezstorage/lang/en_us.json`

**Documentation:**
- `README.md`
- `CHANGELOG.md`
- `docs/README.md`
- `docs/port-overview.md`
- All other docs/*.md files

**Code (Optional):**
- Creative tab name in `EZCreativeTabs.java`
- Display strings in various classes

### Migration Strategy
1. Update configuration and metadata files first
2. Update language files for in-game text
3. Update all documentation
4. Test in-game to verify all changes
5. Commit with clear message about rebranding

## Future Branding Opportunities

### Expansion Names (Future Features)
When implementing additional features, consider AWS-themed names:
- **Crafting Box** → "Lambda Crafter" (serverless computing)
- **Search Box** → "CloudSearch Box" (AWS CloudSearch)
- **Security Box** → "IAM Box" (Identity and Access Management)
- **Sort Box** → "Kinesis Sorter" (data streaming/sorting)
- **Access Terminal** → "CloudFront Terminal" (content delivery)

### Community Engagement
- Emphasize the AWS connection in mod descriptions
- Create comparison charts (S3 features vs mod features)
- Use AWS terminology in documentation where appropriate
- Engage with both Minecraft and tech communities

## Notes

- Keep mod ID as `ezstorage` to avoid breaking changes
- Package names remain unchanged for stability
- Focus on user-facing branding (names, descriptions, docs)
- Technical internals can reference original EZStorage for continuity
