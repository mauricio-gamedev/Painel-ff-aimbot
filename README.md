# FF Companion

A legitimate Android companion dashboard for Free Fire players, built with Kotlin and Jetpack Compose.

## Project scope

This application provides local sensitivity profiles, game launch shortcuts, device settings shortcuts, and manual performance guidance. It does **not** inject into Free Fire, modify game memory or files, automate aiming, bypass anti-cheat, evade bans, or claim ban immunity.

## Target

- Android 16 (API 36)
- Kotlin
- Jetpack Compose
- Minimum Android version: 8.0 (API 26)

## Build

Open the repository in Android Studio, install Android SDK Platform 36, sync Gradle, then run the `:app` configuration.

Command-line build (after a Gradle wrapper is added):
```bash
./gradlew testDebugUnitTest assembleDebug
```

## Privacy

Sensitivity profiles are stored locally using Android SharedPreferences. The app requests no network, overlay, accessibility, storage, or root permissions.

## Status

Initial repository bootstrap. See [docs/SESSION_CHECKPOINT.md](docs/SESSION_CHECKPOINT.md) for the implementation checkpoint once the scaffold lands.
