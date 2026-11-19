# Repository instructions

## Scope
- This repository is a legitimate Free Fire companion utility.
- Do not add game injection, memory/process modification, automated aiming, anti-cheat bypass, ban evasion, or claims of ban immunity.
- Do not collect Garena credentials or request overlay, accessibility, root, or broad storage permissions.
- Keep sensitivity profiles local and clearly describe them as user-entered suggestions; do not claim they are written into the game.

## Engineering workflow
- Read this file, README.md, and docs/SESSION_CHECKPOINT.md before continuing work.
- Work on a feature/fix branch; do not commit feature work directly to main.
- Prefer Kotlin, Jetpack Compose, AndroidX, and platform APIs.
- Keep dependencies minimal and explain new permissions/dependencies.
- Add unit tests for pure logic and update docs when behavior changes.
- Run tests and assembleDebug in CI. Never report success without a passing run.
- Avoid destructive device operations, task-killers, hidden background services, and unverified performance claims.
