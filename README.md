# Job Board App (Android)

A native Android job board app that mirrors the Christian Contracting (Herkey) crew app:
dark ink background with a gold accent, a live **Job board** of open requests with
Emergency / Soon / Routine priority pills and one-tap **Claim**, a **My jobs** list,
a **Today** schedule view, and a per-job detail page with the
Requested → Accepted → Scheduled → In Progress → Completed status tracker.

## Customizable

The **Settings** tab lets you change the app title, company name, and accent color
(Gold, Amber, Blue, Green, Red, Violet). Branding and theme apply app-wide instantly
and persist across restarts (DataStore). Claimed jobs and their status also persist.

## Build the APK

The APK is built by GitHub Actions (`.github/workflows/build-apk.yml`):

```
./gradlew :app:assembleDebug    # local, needs JDK 17 + Android SDK 34
```

On every push to `main` or a `vibe/**` branch, CI produces:

- `jobboard-debug-apk` — installable debug APK (`app/build/outputs/apk/debug/app-debug.apk`)
- `jobboard-release-apk` — unsigned release APK

Download them from the workflow run's **Artifacts**, or from the repo's **Releases**.

## Stack

Kotlin, Jetpack Compose (Material 3), Navigation Compose, DataStore Preferences.
minSdk 26, targetSdk 34. Sample data mirrors the Christian Contracting seed data.
