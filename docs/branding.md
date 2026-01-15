# Branding Guide - Steve's Simple Storage

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
- **Mod ID**: `ezstorage` (keeping for compatibility, no code changes needed)
- **Display Name**: Steve's Simple Storage
- **Short Name**: S3 Storage

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

### Package Structure
- **Current**: `io.github.scuba10steve.ezstorage`
- **Decision**: Keep as-is (no breaking changes)

## Rebranding Checklist

### High Priority
- [ ] `gradle.properties` - Update mod_name and mod_description
- [ ] `src/main/resources/META-INF/neoforge.mods.toml` - Update displayName and description
- [ ] `src/main/resources/assets/ezstorage/lang/en_us.json` - Update mod name and descriptions
- [ ] `README.md` - Update project title and description
- [ ] `CHANGELOG.md` - Update project references
- [ ] `docs/` - Update all documentation files

### Medium Priority
- [ ] Creative tab display name
- [ ] In-game tooltips and descriptions
- [ ] JEI integration display names

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
