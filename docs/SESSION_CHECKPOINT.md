# Session checkpoint

## Current state
- Repository was initially empty.
- Initial scope README was committed to main to create the repository's first commit.
- Feature branch: feature/ff-companion-android16
- Android scaffold added on the feature branch.

## Product boundary
Build FF Companion as a legitimate Android companion. Do not implement injection, automated aiming, process or memory manipulation, anti-cheat bypass, anti-ban/ban evasion, or claims of ban immunity.

## Stack
- Kotlin 2.3.21
- Android Gradle Plugin 8.13.2
- Gradle 8.13
- Jetpack Compose BOM 2026.09.00
- Android compileSdk/targetSdk 36
- minSdk 26
- Java 17

## Implemented
- Black/crimson Jetpack Compose dashboard.
- Local sensitivity profiles stored in SharedPreferences.
- Normal Free Fire / Free Fire MAX launcher shortcut.
- Shortcuts to Android Display, Battery Saver, and app-info settings.
- Manual performance checklist and privacy/scope information.
- Unit tests for preset ranges and clamping.
- GitHub Actions workflow for unit tests and debug APK assembly.

## Validation
Not yet run. Check the GitHub Actions run before marking the project build or tests as passing.

## Next steps
1. Review the Actions run and fix any compile/test errors.
2. Add a Gradle wrapper before expecting command-line builds outside CI.
3. Verify launch behavior on an Android 16 device, including both Free Fire package variants.
4. Refine the UI from device screenshots and accessibility checks.
