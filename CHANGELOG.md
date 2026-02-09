# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.0.1] - 2026-02-09

### Changed

- **AppSnackbarHost** - Now uses `AppSnackbarHostState` to bundle data and icons into a single state object:
  ```kotlin
  // Before (2.0.0)
  AppSnackbarHost(
      data = snackbarData,
      hostState = snackbarHostState,
      successIcon = painterResource(Res.drawable.ic_check_circle),
      errorIcon = painterResource(Res.drawable.ic_warning),
      dismissIcon = painterResource(Res.drawable.ic_x),
      onDismiss = { ... },
  )

  // After (2.0.1)
  AppSnackbarHost(
      state = AppSnackbarHostState(
          data = snackbarData,
          successIcon = painterResource(Res.drawable.ic_check_circle),
          errorIcon = painterResource(Res.drawable.ic_warning),
          dismissIcon = painterResource(Res.drawable.ic_x),
      ),
      hostState = snackbarHostState,
      onDismiss = { ... },
  )
  ```

## [2.0.0] - 2026-02-09

### Breaking Changes

This is a major release with breaking API changes. All components now follow a consistent **State/Defaults pattern** for improved type safety, discoverability, and consistency.

#### State Pattern

All components with 2+ data properties now use a dedicated `State` data class:

```kotlin
// Before (1.x)
PrimaryButton(
    text = "Continue",
    enabled = true,
    loading = false,
    onClick = { /* action */ },
)

// After (2.0)
PrimaryButton(
    state = PrimaryButtonState(
        text = "Continue",
        enabled = true,
        loading = false,
    ),
    onClick = { /* action */ },
)
```

#### Effect Pattern for Sheets

All sheets now use sealed `Effect` interfaces instead of multiple callbacks:

```kotlin
// Before (1.x)
ConfirmationSheet(
    isVisible = showSheet,
    onDismiss = { hideSheet() },
    onConfirm = { confirm() },
)

// After (2.0)
ConfirmationSheet(
    state = ConfirmationSheetState(
        isVisible = showSheet,
        title = "Confirm",
        message = "Are you sure?",
    ),
    onEffect = { effect ->
        when (effect) {
            ConfirmationSheetEffect.Dismiss -> hideSheet()
            ConfirmationSheetEffect.Confirm -> confirm()
        }
    },
)
```

### Added

#### New State Classes

| Component | State Class |
|-----------|-------------|
| `PrimaryButton` | `PrimaryButtonState` |
| `SecondaryButton` | `SecondaryButtonState` |
| `GenderButton` | `GenderButtonState` |
| `DotsIndicator` | `DotsIndicatorState` |
| `PinRow` | `PinRowState` |
| `PinView` | `PinViewState` |
| `LocationPin` | `LocationPinState` |
| `AddressCard` | `AddressCardState` |
| `BonusCard` | `BonusCardState` |
| `EnableBonusCard` | `EnableBonusCardState` |
| `HistoryCard` | `HistoryCardState` |
| `NotificationCard` | `NotificationCardState` |
| `ProfileCard` | `ProfileCardState` |
| `SwitchCard` | `SwitchCardState` |
| `LocationItem` | `LocationItemState` |
| `RadioItem` | `RadioItemState` |
| `SelectableItem` | `SelectableItemState<T>` |
| `ServiceItem` | `ServiceItemState` |
| `ActionSheet` | `ActionSheetState` |
| `ActionPickerSheet` | `ActionPickerSheetState` |
| `ConfirmationSheet` | `ConfirmationSheetState` |
| `DatePickerSheet` | `DatePickerSheetState` |
| `EmptyState` | `EmptyStateState` |
| `CarNumber` | `CarNumberState` |
| `VehiclePlate` | `VehiclePlateState` |
| `LocationPoint` | `LocationPointState` |
| `RouteView` | `RouteViewState` |
| `Snackbar` | `SnackbarState` |

#### New Effect Interfaces

| Sheet | Effect Interface |
|-------|------------------|
| `ActionSheet` | `ActionSheetEffect` |
| `ActionPickerSheet` | `ActionPickerSheetEffect` |
| `ConfirmationSheet` | `ConfirmationSheetEffect` |
| `DatePickerSheet` | `DatePickerSheetEffect` |

#### New Defaults Objects

Every component now has a `ComponentNameDefaults` object with:
- `Colors` data class with `@Composable colors()` factory
- `Style` data class with `@Composable style()` factory
- `Dimens` data class with `@Composable dimens()` factory

### Changed

- **Package Structure**: Reorganized from `section/` to `composite/` for better semantic clarity
  - `section/card/` → `composite/card/`
  - `section/item/` → `composite/item/`
  - `section/sheet/` → `composite/sheet/`
  - `section/view/` → `composite/view/`
  - `feedback/` → Distributed to appropriate packages

- **Sheet Components**: All sheets now have `isVisible` inside their State class instead of as a separate parameter

- **Snackbar**: Renamed from `PrimarySnackBar` to `Snackbar` with new `SnackbarState`

### Removed

- Deprecated dual-API patterns (old parameter-based APIs removed in favor of State-only APIs)
- `feedback/` package (contents moved to `composite/` and `primitive/`)
- `section/` package (renamed to `composite/`)

### Migration Guide

1. **Update imports**: Change `section.*` to `composite.*`
2. **Wrap parameters in State**: Bundle data parameters into the component's State class
3. **Use Effect handlers**: Replace multiple callbacks with single `onEffect` handler for sheets
4. **Check Defaults**: Use the new Defaults objects for customization

## [1.3.0] - 2026-02-09

### Added

- **DataError** - Sealed class hierarchy for network/data layer errors
- **BaseViewModel** - Added `DataError` to `StringResource` mapping, `currentErrorMessageId`, and `failure` flow

### Changed

- **BaseViewModel** - Now uses `StringResource` for error messages instead of plain strings

## [1.2.2] - 2026-02-08

### Changed

- **ExpandableSheetState** - Exposed `anchoredDraggableState`, `snapAnimationSpec`, `positionalThreshold`, `contentHeightPx`, `updateHeights()`, and `settle()` as public API for custom sheet implementations

## [1.2.1] - 2026-02-08

### Changed

- **ExpandableSheetState** - Added `positionalThreshold` parameter for customizing fling snap behavior

## [1.2.0] - 2026-02-08

### Added

- **ActionPickerSheet** - New sheet component for selecting from multiple action items with icons. Use for action menus, option pickers, and context actions.

## [1.1.1] - 2026-02-08

### Fixed

- **ConfirmationSheetData** - Renamed `sheetTitle` to `sheetName` to match YallaClient usage

## [1.1.0] - 2026-02-08

### Changed

- **TopBar** - `actions` parameter changed from `RowScope.() -> Unit` to `() -> Unit` for simpler usage
- **LargeTopBar** - Made `title` nullable (`String?`) to support screens without titles; `actions` parameter changed to `() -> Unit`
- **ConfirmationSheet** - Migrated to MVI pattern with `ConfirmationSheetData` and `ConfirmationSheetEffect`
- **LoadingController** - `withLoading()` now accepts optional `showAfter` and `minDisplayTime` parameters per call

### Fixed

- **LoadingController** - Changed from `Clock.System` to `TimeSource.Monotonic` for more accurate timing measurements

## [1.0.0] - 2026-02-07

### Added

- Initial stable release of yalla-components library
- **Primitive Components**
  - `PrimaryButton` - Main action button with loading state
  - `SecondaryButton` - Secondary action button
  - `TextButton` - Minimal text-only button
  - `IconButton` - Icon-only button with size variants
  - `NavigationButton` - Back navigation button
  - `SensitiveButton` - Hold-to-confirm button for destructive actions
- **Form Components**
  - `DateField` - Date picker field
- **Section Components**
  - `TopBar` - Standard top bar with navigation
  - `LargeTopBar` - Large top bar for profile screens
  - `LocationPoint` - Location marker with label
  - `VehiclePlate` - Vehicle plate number display
  - `ActionItem` - Tappable action row
  - `ListItem` - Standard list item
  - `ServiceItem` - Service selection item
  - `TariffItem` - Tariff display item
  - `LocationItem` - Location search result item
- **Card Components**
  - `ContentCard` - Basic content container
  - `BonusCard` - Bonus/cashback display card
  - `AddressCard` - Address display card
  - `SelectableCard` - Multi-select option card
  - `PromotionCard` - Promotional banner card
  - `ProfileCard` - User profile summary card
  - `SwitchCard` - Toggle switch card
  - `NavigableCard` - Card with navigation arrow
  - `PlaceCard` - Place/location card
  - `NotificationCard` - Notification display card
  - `HistoryCard` - Order history card
- **Feedback Components**
  - `EmptyState` - Empty state placeholder
  - `Snackbar` - Toast notification
- **Sheet Components**
  - `Sheet` - Bottom sheet container
  - `ActionSheet` - Action selection sheet
  - `ConfirmationSheet` - Confirmation dialog sheet
