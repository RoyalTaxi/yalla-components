package uz.yalla.components.primitive.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * Icon-only button for compact actions.
 *
 * Use for actions represented by icons, like navigation, menu, or close buttons.
 *
 * ## Usage
 *
 * ```kotlin
 * IconButton(
 *     onClick = onClose,
 * ) {
 *     Icon(Icons.Default.Close, contentDescription = "Close")
 * }
 * ```
 *
 * @param onClick Invoked on click
 * @param modifier Applied to button
 * @param enabled When false, button is disabled
 * @param size Button size
 * @param colors Color configuration
 * @param content Icon content
 *
 * @see IconButtonDefaults for default values
 */
@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: IconButtonSize = IconButtonSize.Medium,
    colors: IconButtonDefaults.Colors = IconButtonDefaults.colors(),
    content: @Composable () -> Unit,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.size(IconButtonDefaults.size(size)),
        enabled = enabled,
        shape = IconButtonDefaults.Shape,
        color = colors.containerColor(enabled),
        contentColor = colors.contentColor(enabled),
    ) {
        Box(contentAlignment = Alignment.Center) {
            content()
        }
    }
}

/**
 * Size variants for [IconButton].
 */
enum class IconButtonSize {
    Small,
    Medium,
    Large,
}

/**
 * Default values for [IconButton].
 */
object IconButtonDefaults {

    val CornerRadius: Dp = 12.dp
    val Shape: Shape = RoundedCornerShape(CornerRadius)

    fun size(size: IconButtonSize): Dp = when (size) {
        IconButtonSize.Small -> 36.dp
        IconButtonSize.Medium -> 44.dp
        IconButtonSize.Large -> 52.dp
    }

    fun iconSize(size: IconButtonSize): Dp = when (size) {
        IconButtonSize.Small -> 18.dp
        IconButtonSize.Medium -> 22.dp
        IconButtonSize.Large -> 26.dp
    }

    @Composable
    fun colors(
        container: Color = System.color.backgroundTertiary,
        content: Color = System.color.iconBase,
        disabledContainer: Color = System.color.backgroundTertiary.copy(alpha = 0.5f),
        disabledContent: Color = System.color.iconDisabled,
    ): Colors = Colors(
        container = container,
        content = content,
        disabledContainer = disabledContainer,
        disabledContent = disabledContent,
    )

    @Composable
    fun filledColors(
        container: Color = System.color.buttonActive,
        content: Color = System.color.iconWhite,
        disabledContainer: Color = System.color.buttonDisabled,
        disabledContent: Color = System.color.iconWhite,
    ): Colors = Colors(
        container = container,
        content = content,
        disabledContainer = disabledContainer,
        disabledContent = disabledContent,
    )

    /**
     * Color configuration for [IconButton].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val content: Color,
        val disabledContainer: Color,
        val disabledContent: Color,
    ) {
        fun containerColor(enabled: Boolean): Color =
            if (enabled) container else disabledContainer

        fun contentColor(enabled: Boolean): Color =
            if (enabled) content else disabledContent
    }
}

@Preview
@Composable
private fun IconButtonPreview() {
    IconButton(onClick = {}) {
        Box(Modifier.size(22.dp))
    }
}

