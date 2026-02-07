# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
