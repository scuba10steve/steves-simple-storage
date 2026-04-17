# AI Agent Guidelines

This document outlines the guidelines for AI agents interacting with this repository. Adhering to these principles ensures consistent, safe, and efficient collaboration.

## Purpose

The primary purpose of an AI agent in this repository is to assist human developers by automating tasks, providing insights, and maintaining code quality, while strictly following established project conventions and best practices.

## Documentation

Before working on any area of the codebase, read the relevant doc in `docs/dev/`:

| Area | Document |
|------|----------|
| Storage system architecture | [docs/dev/storage-system.md](docs/dev/storage-system.md) |
| GUI system and menus | [docs/dev/gui-system.md](docs/dev/gui-system.md) |
| Build system and Gradle setup | [docs/dev/build-system.md](docs/dev/build-system.md) |
| Configuration options | [docs/dev/configuration.md](docs/dev/configuration.md) |
| Port blocks (Input/Extract/Eject) | [docs/dev/port-overview.md](docs/dev/port-overview.md) |
| Translation/i18n contributions | [docs/dev/translations.md](docs/dev/translations.md) |
| Dependency versions | [docs/dev/dependencies.md](docs/dev/dependencies.md) |
| Troubleshooting common issues | [docs/dev/troubleshooting.md](docs/dev/troubleshooting.md) |

## Project Structure

```text
core/                 Platform-agnostic code (vanilla MC classes via neoFormVersion)
  src/main/java/io/github/scuba10steve/s3/
    block/            Block classes and multiblock components
    blockentity/      Block entity implementations (core storage logic)
    gui/              Shared menu/screen logic and UI helpers
    platform/         Platform abstraction interfaces and helpers
  src/main/resources/
    assets/s3/lang/   Localization files (en_us.json is the reference)
    data/s3/          Recipes, tags, loot tables, advancements

neoforge/s3/          NeoForge-specific runtime module
  src/main/java/io/github/scuba10steve/s3/
    init/             DeferredRegister holders (blocks, items, entities, menus)
    client/           Client-only screen/event registration
    config/           NeoForge config bridge
    network/          Packet registration and handlers
    jei/              JEI integration classes

gametest/s3/          NeoForge GameTest module and data generation entry points

docs/                 Technical docs (architecture, integrations, troubleshooting)
scripts/              Local dev helpers (server, copy jar, fetch logs)
```

## Build And Verification Commands

```bash
# Build all modules
./gradlew build

# Build key modules directly
./gradlew :core:build :neoforge:s3:build :gametest:s3:build

# Unit tests + checks (includes core:validateLang through check)
./gradlew test check

# Run NeoForge GameTests (full game test server startup)
./gradlew :gametest:s3:runGameTestServer

# Local manual test helpers
bash scripts/server.sh
bash scripts/copy.sh
bash scripts/get_logs.sh
```

**After code changes, run at least `./gradlew :neoforge:s3:build` before claiming completion.**

## Key Codebase Conventions

- **Multi-module boundaries:** Keep cross-platform logic in `core`; keep NeoForge-only APIs and registrations in `neoforge/s3`.
- **Platform abstraction:** Prefer existing abstraction points (`S3Platform`, config/network helpers) instead of introducing direct loader coupling into `core`.
- **Localization parity:** `core/src/main/resources/assets/s3/lang/en_us.json` is the key-set source of truth; `validateLang` enforces parity for other locales.
- **Registration flow:** New blocks/items/entities/menus should follow existing DeferredRegister patterns in `neoforge/s3/src/main/java/io/github/scuba10steve/s3/init/`.
- **Automation and integration:** Changes touching storage transfer, recipes, or UI interactions should account for JEI integration and GameTest coverage where applicable.

## Core Mandates

1.  **Adherence to Conventions:** Always prioritize and rigorously adhere to existing project conventions (code style, naming, architecture, documentation) when reading or modifying code. Analyze surrounding code, tests, and configuration files (e.g., `package.json`, `Cargo.toml`, `requirements.txt`, `build.gradle`) to understand established patterns.
2.  **Library/Framework Verification:** Never assume a library or framework is available or appropriate. Verify its established usage within the project by checking imports, configuration files, or observing neighboring files before employing it.
3.  **Style & Structure Mimicry:** Mimic the style (formatting, naming), structure, framework choices, typing, and architectural patterns of existing code in the project.
4.  **Idiomatic Changes:** Understand the local context (imports, functions/classes) to ensure changes integrate naturally and idiomatically.
5.  **Comments:** Add code comments sparingly. Focus on *why* something is done, especially for complex logic, rather than *what* is done. Do not edit comments separate from the code being changed.
6.  **Proactiveness:** Fulfill user requests thoroughly, including adding tests for new features or bug fixes. Consider all created files (especially tests) as permanent artifacts unless instructed otherwise.
7.  **Confirm Ambiguity/Expansion:** Do not take significant actions beyond the clear scope of the request without confirming with the user.
8.  **Security:** Prioritize security best practices. Never introduce code that exposes sensitive information.
9.  **No Unrequested Commits:** **NEVER** stage or commit changes unless explicitly instructed by the user.
10.  **No Attribution on Commits:** **NEVER** add a Co-Authored section to a commit.


## Tool Usage

*   **File System Operations:** Use `read_file`, `write_file`, and `replace` tools for file manipulation. Always read a file before modifying it to ensure accuracy.
*   **Code Search:** Utilize `search_file_content` for efficient codebase searching.
*   **Shell Commands:** Use `run_shell_command` for executing shell commands. Always explain the purpose and potential impact of commands that modify the file system or system state before execution. Prefer non-interactive commands.
*   **Efficient Output:** When using `run_shell_command`, prefer flags that reduce output verbosity. Redirect large outputs to temporary files if necessary.

## Code Transformations (OpenRewrite)

*   **Prefer OpenRewrite recipes** for bulk code transformations such as import ordering, unused import removal, code style fixes, and API migrations. Run `./gradlew rewriteRun` before attempting manual changes for these categories of work.
*   **Dry-run first** when unsure of impact: `./gradlew rewriteDryRun` previews changes without modifying files.
*   **Discover available recipes** with `./gradlew rewriteDiscover`.
*   Only handle manually what OpenRewrite recipes cannot cover.
*   **On-demand workflow:** Trigger `gh workflow run openrewrite.yml` to run recipes in CI and auto-open a PR. Use `-f dry_run=true` to preview changes as a downloadable artifact without committing.

## Issue Creation

*   **Always use issue templates.** This repository has issue templates in `.github/ISSUE_TEMPLATE/`. When creating issues, use `gh issue create --template bug_report.yml` for bugs or `gh issue create --template feature_request.yml` for feature requests. Never create issues with plain `--body` that bypasses the template structure.
*   **Bug reports** must include: mod version, Minecraft version, description, expected behavior, and steps to reproduce.
*   **Feature requests** must include: description and motivation. Alternatives considered is optional but encouraged.
*   **Project board:** All new issues must be added to the "Kanban" board in the "Steve's Simple Storage" GitHub project so they are tracked and visible on the board.

## Version & Release Management

*   **Version Bumps:** Never manually edit `mod_version` in `gradle.properties`. Use the `bump-version` GitHub Actions workflow (`gh workflow run bump-version.yml --ref main -f part=<patch|minor|major>`).
*   **Releases:** Trigger the `release` GitHub Actions workflow (`gh workflow run release.yml --ref main -f release_type=<beta|release>`). This builds, creates a GitHub Release, and publishes to Modrinth and CurseForge. **Always use `release_type=beta`** unless the user explicitly requests a full release.
*   **Workflow Order (CRITICAL):** Always trigger `bump-version` first, wait for it to complete successfully (check with `gh run view <run-id> --json status,conclusion`), then trigger `release`. Running them in parallel causes the release to use the old version number.

## Workflow Example (Software Engineering Tasks)

1.  **Understand:** Analyze the request and context using search and file reading tools.
2.  **Plan:** Formulate a coherent plan. Break down complex tasks. Share the plan if beneficial for user understanding. Include testing in the plan.
3.  **Implement:** Execute the plan using available tools, adhering to conventions.
4.  **Verify (Tests):** Run relevant tests. See [TESTING.md](TESTING.md) for test commands.
5.  **Verify (Standards):** Run build, linting, and type-checking commands.
6.  **Finalize:** Await further instructions.
