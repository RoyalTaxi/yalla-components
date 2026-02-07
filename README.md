# Yalla Components

Kotlin Multiplatform component library for Yalla apps. Provides reusable UI components built with Compose Multiplatform.

## Installation

Add the dependency to your `build.gradle.kts`:

```kotlin
implementation("uz.yalla:components:1.1.0")
```

Configure GitHub Packages repository:

```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/RoyalTaxi/yalla-components")
        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}
```

## Components

### Primitive Components

| Component | Description |
|-----------|-------------|
| `PrimaryButton` | Main action button with loading state |
| `SecondaryButton` | Secondary action button |
| `TextButton` | Minimal text-only button |
| `IconButton` | Icon-only button with size variants |
| `NavigationButton` | Back navigation button |
| `SensitiveButton` | Hold-to-confirm button for destructive actions |
| `TopBar` | Standard top bar with navigation and actions |
| `LargeTopBar` | Large top bar with prominent title |
| `LoadingDialog` | Modal loading indicator |
| `DotsIndicator` | Page indicator dots |
| `LoadingIndicator` | Circular loading spinner |

### Section Components

| Component | Description |
|-----------|-------------|
| `LocationPoint` | Location marker with label |
| `VehiclePlate` | Vehicle plate number display |
| `RouteView` | Route visualization |
| `EmptyState` | Empty state placeholder |
| `ActionItem` | Tappable action row |
| `ListItem` | Standard list item |
| `ServiceItem` | Service selection item |
| `TariffItem` | Tariff display item |
| `LocationItem` | Location search result item |

### Card Components

| Component | Description |
|-----------|-------------|
| `ContentCard` | Basic content container |
| `BonusCard` | Bonus/cashback display card |
| `AddressCard` | Address display card |
| `SelectableCard` | Multi-select option card |
| `PromotionCard` | Promotional banner card |
| `ProfileCard` | User profile summary card |
| `SwitchCard` | Toggle switch card |
| `NavigableCard` | Card with navigation arrow |
| `PlaceCard` | Place/location card |
| `NotificationCard` | Notification display card |
| `HistoryCard` | Order history card |

### Sheet Components

| Component | Description |
|-----------|-------------|
| `Sheet` | Bottom sheet container |
| `ActionSheet` | Action selection sheet |
| `ConfirmationSheet` | Confirmation dialog sheet |

### Foundation

| Component | Description |
|-----------|-------------|
| `BaseViewModel` | Base ViewModel with loading and error handling |
| `LoadingController` | Smart loading state with debounce |
| `ObserveAsEvents` | Lifecycle-aware event collection |
| `MaskFormatter` | Input mask formatting |

## Usage Examples

### Buttons

```kotlin
PrimaryButton(
    text = "Continue",
    onClick = { /* action */ },
    loading = isLoading,
)

SensitiveButton(
    text = "Delete Account",
    onConfirm = { viewModel.deleteAccount() },
)
```

### Top Bars

```kotlin
TopBar(
    title = "Settings",
    onNavigationClick = onBack,
    actions = {
        IconButton(onClick = onEdit) {
            Icon(Icons.Default.Edit, null)
        }
    }
)

LargeTopBar(
    title = "Order History",
    onNavigationClick = onBack,
)
```

### Sheets

```kotlin
ConfirmationSheet(
    isVisible = state.showConfirmation,
    data = ConfirmationSheetData(
        sheetTitle = "Confirm Order",
        image = painterResource(Res.drawable.img_success),
        title = "Order Placed!",
        description = "Your driver is on the way.",
        actionText = "Got it",
    ),
    onEffect = { effect ->
        when (effect) {
            ConfirmationSheetEffect.Dismiss -> viewModel.dismiss()
            ConfirmationSheetEffect.Confirm -> viewModel.confirm()
        }
    },
)
```

### Loading Controller

```kotlin
class MyViewModel : BaseViewModel() {
    fun loadData() {
        safeScope.launchWithLoading {
            repository.fetchData()
                .onSuccess { /* handle */ }
                .onFailure { handleError(it) }
        }
    }

    // With custom timing
    fun quickLoad() {
        safeScope.launchWithLoading(
            showAfter = 200.milliseconds,
            minDisplayTime = 100.milliseconds,
        ) {
            repository.quickFetch()
        }
    }
}
```

## Platform Support

- Android (API 26+)
- iOS (16.6+)

## Dependencies

- Compose Multiplatform 1.10+
- Kotlin 2.3+
- yalla-design (design system)
- yalla-resources (shared resources)
- yalla-platform (platform utilities)

## License

Proprietary - RoyalTaxi
