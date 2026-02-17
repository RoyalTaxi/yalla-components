# Yalla Components

A Kotlin Multiplatform component library for Yalla apps, built with Compose Multiplatform. Provides a comprehensive set of reusable, themeable UI components following a consistent **State/Defaults pattern** for type safety and easy customization.

## Features

- **Consistent API**: All components follow the same State/Defaults pattern
- **Type-Safe**: Strongly typed state classes prevent runtime errors
- **Themeable**: Every component supports colors, styles, and dimensions customization
- **Cross-Platform**: Works on Android and iOS with Compose Multiplatform
- **Well-Documented**: KDoc comments with usage examples for every component

## Installation

Add the dependency to your `build.gradle.kts`:

```kotlin
implementation("uz.yalla:components:2.3.0")
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

## Architecture

### State Pattern

Every component with multiple data properties uses a dedicated `State` data class. This bundles all display data into a single, type-safe object:

```kotlin
// State class bundles all data properties
data class PrimaryButtonState(
    val text: String,
    val enabled: Boolean = true,
    val loading: Boolean = false,
    val size: ButtonSize = ButtonSize.Medium
)

// Component takes state as first parameter
@Composable
fun PrimaryButton(
    state: PrimaryButtonState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: PrimaryButtonDefaults.PrimaryButtonColors = PrimaryButtonDefaults.colors(),
    // ...
)
```

### Defaults Pattern

Every component has a companion `Defaults` object with three configuration classes:

```kotlin
object PrimaryButtonDefaults {
    // Color configuration
    data class PrimaryButtonColors(
        val container: Color,
        val content: Color,
        val disabledContainer: Color,
        val disabledContent: Color
    )

    @Composable
    fun colors(
        container: Color = System.color.buttonActive,
        content: Color = System.color.textWhite,
        // ...
    ): PrimaryButtonColors

    // Text style configuration
    data class PrimaryButtonStyle(...)
    @Composable fun style(...): PrimaryButtonStyle

    // Dimension configuration
    data class PrimaryButtonDimens(...)
    @Composable fun dimens(...): PrimaryButtonDimens
}
```

### Effect Pattern for Sheets

Sheet components use sealed `Effect` interfaces for user interactions:

```kotlin
sealed interface ConfirmationSheetEffect {
    data object Dismiss : ConfirmationSheetEffect
    data object Confirm : ConfirmationSheetEffect
}

@Composable
fun ConfirmationSheet(
    state: ConfirmationSheetState,
    onEffect: (ConfirmationSheetEffect) -> Unit,
    // ...
)
```

## Component Catalog

### Primitive Components

Basic building blocks for UI composition.

#### Buttons

| Component | Description | State Class |
|-----------|-------------|-------------|
| `PrimaryButton` | Main action button with loading state | `PrimaryButtonState` |
| `SecondaryButton` | Secondary/alternative action button | `SecondaryButtonState` |
| `TextButton` | Minimal text-only button | - |
| `IconButton` | Icon-only button with size variants | - |
| `NavigationButton` | Back navigation button | - |
| `GenderButton` | Gender selection toggle | `GenderButtonState` |
| `SensitiveButton` | Hold-to-confirm for destructive actions | - |

#### Indicators

| Component | Description | State Class |
|-----------|-------------|-------------|
| `DotsIndicator` | Animated page indicator dots | `DotsIndicatorState` |
| `LoadingIndicator` | Circular loading spinner | - |
| `StripedProgressbar` | Animated striped progress bar | - |
| `SplashOverlay` | Full-screen splash overlay | - |

#### Input

| Component | Description | State Class |
|-----------|-------------|-------------|
| `PinRow` | OTP/PIN input row | `PinRowState` |
| `PinView` | Single digit PIN input | `PinViewState` |
| `DateField` | Date picker field | `DateFieldState` |

#### Navigation

| Component | Description |
|-----------|-------------|
| `TopBar` | Standard top bar with navigation |
| `LargeTopBar` | Large top bar for profile screens |

#### Map

| Component | Description | State Class |
|-----------|-------------|-------------|
| `LocationPin` | Animated map pin with address label | `LocationPinState` |
| `SearchPin` | Search location pin indicator | - |

### Composite Components

Higher-level components composed of primitives.

#### Cards

| Component | Description | State Class |
|-----------|-------------|-------------|
| `AddressCard` | Compact address display | `AddressCardState` |
| `BonusCard` | Bonus/cashback display | `BonusCardState` |
| `EnableBonusCard` | Bonus toggle card | `EnableBonusCardState` |
| `HistoryCard` | Order history item | `HistoryCardState` |
| `NotificationCard` | Notification display | `NotificationCardState` |
| `ProfileCard` | User profile summary | `ProfileCardState` |
| `SwitchCard` | Toggle switch card | `SwitchCardState` |
| `ContentCard` | Basic content container | - |
| `NavigableCard` | Card with navigation arrow | - |

#### Items

| Component | Description | State Class |
|-----------|-------------|-------------|
| `LocationItem` | Location selector with flow layout | `LocationItemState` |
| `RadioItem` | Radio-style selectable item | `RadioItemState` |
| `SelectableItem` | Generic selectable item | `SelectableItemState<T>` |
| `ServiceItem` | Service toggle item | `ServiceItemState` |
| `ActionItem` | Tappable action row | - |

#### Views

| Component | Description | State Class |
|-----------|-------------|-------------|
| `EmptyState` | Empty state placeholder | `EmptyStateState` |
| `RouteView` | Route visualization | `RouteViewState` |
| `LocationPoint` | Location marker with label | `LocationPointState` |
| `CarNumber` | Vehicle plate display | `CarNumberState` |
| `VehiclePlate` | Vehicle plate with region | `VehiclePlateState` |

#### Sheets

| Component | Description | State Class | Effect Interface |
|-----------|-------------|-------------|------------------|
| `Sheet` | Base bottom sheet container | - | - |
| `ActionSheet` | Action selection sheet | `ActionSheetState` | `ActionSheetEffect` |
| `ActionPickerSheet` | Multi-action picker | `ActionPickerSheetState` | `ActionPickerSheetEffect` |
| `ConfirmationSheet` | Confirmation dialog | `ConfirmationSheetState` | `ConfirmationSheetEffect` |
| `DatePickerSheet` | Date picker sheet | `DatePickerSheetState` | `DatePickerSheetEffect` |

#### Snackbar

| Component | Description | State Class |
|-----------|-------------|-------------|
| `Snackbar` | Toast notification message | `SnackbarState` |
| `AppSnackbarHost` | Snackbar host container | `AppSnackbarHostState` |

### Foundation

Utilities and base classes for building features.

| Component | Description |
|-----------|-------------|
| `LoadingController` | Smart loading state with debounce |
| `ObserveAsEvents` | Lifecycle-aware event collection |
| `MaskFormatter` | Input mask formatting |

## Usage Examples

### Buttons

```kotlin
// Primary button with loading state
PrimaryButton(
    state = PrimaryButtonState(
        text = "Confirm Order",
        loading = isLoading,
        enabled = isValid,
    ),
    onClick = viewModel::confirmOrder,
    modifier = Modifier.fillMaxWidth(),
)

// Custom colors
PrimaryButton(
    state = PrimaryButtonState(text = "Delete"),
    onClick = onDelete,
    colors = PrimaryButtonDefaults.colors(
        container = System.color.borderError,
    ),
)
```

### Cards

```kotlin
// Profile card
ProfileCard(
    state = ProfileCardState(
        avatar = avatarUrl,
        name = "John Doe",
        phone = "+998 90 123 45 67",
    ),
    onAvatarClick = { openAvatarPicker() },
    onClick = { navigateToProfile() },
)

// History card with route
HistoryCard(
    state = HistoryCardState(
        time = "10:30 AM",
        price = "25,000 sum",
        status = HistoryCardStatus.Completed,
    ),
    onClick = { viewDetails(order) },
    route = {
        RouteView(
            state = RouteViewState(
                locations = order.locations,
                originIcon = originPainter,
                destinationIcon = destinationPainter,
            ),
        )
    },
)
```

### Sheets

```kotlin
// Confirmation sheet with effect handling
ConfirmationSheet(
    state = ConfirmationSheetState(
        isVisible = showConfirmation,
        title = "Cancel Order?",
        message = "Your driver is already on the way.",
        confirmText = "Yes, Cancel",
        cancelText = "Keep Order",
    ),
    onEffect = { effect ->
        when (effect) {
            ConfirmationSheetEffect.Dismiss -> hideSheet()
            ConfirmationSheetEffect.Confirm -> cancelOrder()
        }
    },
)

// Action picker sheet
ActionPickerSheet(
    state = ActionPickerSheetState(
        isVisible = showActions,
        title = "Change Photo",
        items = listOf(
            ActionPickerItem(
                title = "Take Photo",
                icon = painterResource(Res.drawable.ic_camera),
                onClick = { takePhoto() },
            ),
            ActionPickerItem(
                title = "Choose from Gallery",
                icon = painterResource(Res.drawable.ic_gallery),
                onClick = { openGallery() },
            ),
        ),
    ),
    onEffect = { effect ->
        when (effect) {
            ActionPickerSheetEffect.Dismiss -> hideActions()
        }
    },
)
```

### Indicators

```kotlin
// Dots indicator for pager
DotsIndicator(
    state = DotsIndicatorState(
        pageCount = pages.size,
        currentPage = pagerState.currentPage,
    ),
)

// Location pin with address
LocationPin(
    state = LocationPinState(
        address = currentAddress,
        timeout = eta,
        jumping = isSearching,
    ),
)
```

### Customization

```kotlin
// Custom dimensions
PrimaryButton(
    state = PrimaryButtonState(text = "Large Button"),
    onClick = { },
    dimens = PrimaryButtonDefaults.dimens(
        mediumMinHeight = 64.dp,
        shape = RoundedCornerShape(24.dp),
    ),
)

// Custom text styles
RadioItem(
    state = RadioItemState(
        text = "Option A",
        selected = isSelected,
        checkedIcon = checkedPainter,
        uncheckedIcon = uncheckedPainter,
    ),
    onClick = { select() },
    style = RadioItemDefaults.style(
        text = System.font.title.base,
    ),
)
```

## Platform Support

| Platform | Minimum Version |
|----------|-----------------|
| Android | API 26 (Android 8.0) |
| iOS | 16.6 |

## Dependencies

| Dependency | Version | Purpose |
|------------|---------|---------|
| Compose Multiplatform | 1.10+ | UI framework |
| Kotlin | 2.3+ | Language |
| yalla-design | 1.1+ | Design system tokens |
| yalla-resources | 1.5+ | Shared resources |
| yalla-platform | 2.0+ | Platform utilities |

## Migration from 1.x

See the [CHANGELOG.md](CHANGELOG.md) for detailed migration instructions.

### Quick Migration Checklist

1. Update imports from `section.*` to `composite.*`
2. Wrap component parameters in State classes
3. Replace multiple sheet callbacks with single `onEffect` handler
4. Update any custom Defaults usage to new structure

## License

Proprietary - RoyalTaxi
