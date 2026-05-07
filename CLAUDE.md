# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Agriconnect is an open-source Android app for agricultural producers and local market consumers, enabling producers to list products and consumers to discover/purchase local fresh produce. The app is currently in Google Play Store internal testing.

## Build & Development Commands

All commands use Gradle via the wrapper:

```bash
# Build debug APK
./gradlew assembleDebug

# Build release AAB (for Play Store)
./gradlew bundleRelease

# Run unit tests
./gradlew test

# Run a single unit test class
./gradlew testDebugUnitTest --tests "com.example.careiroapp.SomeTest"

# Run instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# Sync dependencies
./gradlew dependencies
```

Normal development workflow: open in Android Studio → sync Gradle → run on emulator or device.

## Architecture

MVVM + Clean Architecture with a single-Activity pattern.

**Two navigation layers:**
- `AppNavHost` — top-level navigator managing Login ↔ Main ↔ Bag ↔ Checkout. Start destination is determined at launch by `LoginCadastroViewModel.checkAuthStatus()`, which reads the stored JWT.
- `TapBarNavHost` — inner navigator inside `BaseView` (the main shell), handling Home, Products, Feiras, Associações, Profile, and detail screens.

**`BaseView`** is the main shell composable: it hosts the `AppHeader`, `AppFooter`, `AppDrawer`, and the inner `TapBarNavHost` inside a `ModalNavigationDrawer` + `Scaffold`.

**Feature modules** follow this internal structure:
```
<feature>/
  data/
    datasource/   ← Retrofit API calls
    models/       ← API request/response models
    repositories/ ← bridge between datasource and domain
  domain/
    usecases/     ← single-responsibility business logic classes
  ui/
    components/   ← reusable Compose composables for this feature
    viewmodel/    ← ViewModel, UiState, UiEvents
    <Feature>View.kt
    Single<Feature>View.kt  (detail screens)
```

Feature modules: `loginCadastro`, `products`, `feiras`, `associacoes`, `bag`, `home`, `profile`.

**Global shared layer** (`data/`):
- `data/network/` — Retrofit setup via `RetrofitModule`. Three OkHttpClient qualifiers: `@PublicClient` (no auth), `@AuthenticatedClient` (adds access token via `AccessTokenInterceptor`, auto-refreshes via `AuthAuthenticator`), `@TokenRefreshClient` (used only for token refresh calls).
- `data/dataStore/` — `JwtDataStore` persists access and refresh JWTs via DataStore Preferences.
- `data/room/` — `AppDatabase` with `CartDao` (bag items) and `UserDao` (cached user profile).

**Auth flow:** JWT tokens stored in DataStore. On 401, `AuthAuthenticator` uses the refresh token to get new tokens silently. On logout, tokens and local user data are cleared.

**URL configuration:** `BASE_URL` is a `BuildConfig` field — `http://10.0.2.2:3000/` for debug (Android emulator localhost), `https://projetocareirobackend.onrender.com` for release.

**State management pattern:** Each ViewModel exposes a `StateFlow<XUiState>` for UI state and a `StateFlow<XUiEvents>` (sealed class) for one-shot events like navigation or error toasts.

**DI:** Hilt with KSP. All ViewModels use `@HiltViewModel`. Retrofit services, Room DAOs, and DataStore instances are provided as singletons in their respective `@Module` classes.

**ViewModel sharing for detail screens:** Detail views (e.g., `SingleProductView`) retrieve the same ViewModel instance from `navController.previousBackStackEntry` to share state loaded in the list screen, avoiding a redundant API call.
