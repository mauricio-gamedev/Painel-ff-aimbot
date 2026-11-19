# FF Companion

A dark black/crimson Android companion dashboard for Free Fire players, built with Kotlin and Jetpack Compose.

## Features

- Local sensitivity profiles: Balanced, Rush, Sniper, and Custom.
- Normal launcher shortcut for Free Fire and Free Fire MAX when installed.
- Android Display, Battery Saver, and app-info settings shortcuts.
- Manual device-performance checklist.
- Device and app version information.

## Platform and stack

- Android 16: compileSdk 36, targetSdk 36
- Minimum Android 8.0 (API 26)
- Kotlin 2.3.21
- Android Gradle Plugin 8.13.2
- Gradle 8.13
- Jetpack Compose BOM 2026.09.00
- Java 17

## Build and validation

The GitHub Actions workflow installs Gradle 8.13 directly and runs:

```bash
gradle --no-daemon testDebugUnitTest
gradle --no-daemon assembleDebug
```

A Gradle Wrapper is not included yet. For local builds, open the project in Android Studio with Gradle 8.13 configured, or install Gradle 8.13 and execute the commands above.

Build and test status is reported by the **Android CI** workflow. Do not treat this scaffold as validated until that workflow has completed successfully.

## Privacy

Sensitivity profiles are stored locally in app-private SharedPreferences. The app declares no Internet, overlay, accessibility, broad storage, or root permissions. Package visibility is limited to the two Free Fire package IDs used by the launcher shortcut.

## Scope and limitations

This app does **not** inject into Free Fire, modify game memory or files, automate aiming, bypass anti-cheat, evade bans, or guarantee account protection. Sensitivity values are user-managed notes and are not applied to the game automatically.

See [docs/PROJECT_SCOPE.md](docs/PROJECT_SCOPE.md) and [docs/SESSION_CHECKPOINT.md](docs/SESSION_CHECKPOINT.md).
