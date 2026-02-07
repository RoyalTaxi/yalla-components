package uz.yalla.components.primitive.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * Navigation back button for screen headers.
 *
 * Use in top bars to navigate back to the previous screen.
 *
 * ## Usage
 *
 * ```kotlin
 * NavigationButton(onClick = onBack)
 * ```
 *
 * ## With Custom Icon
 *
 * ```kotlin
 * NavigationButton(
 *     onClick = onClose,
 *     icon = Icons.Default.Close,
 * )
 * ```
 *
 * @param onClick Invoked on click
 * @param modifier Applied to button
 * @param icon Icon to display. Defaults to back arrow.
 * @param contentDescription Accessibility description
 * @param colors Color configuration
 *
 * @see NavigationButtonDefaults for default values
 */
@Composable
fun NavigationButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    contentDescription: String? = "Navigate back",
    colors: NavigationButtonDefaults.Colors = NavigationButtonDefaults.colors(),
) {
    Surface(
        onClick = onClick,
        modifier = modifier.size(NavigationButtonDefaults.Size),
        shape = NavigationButtonDefaults.Shape,
        color = colors.container,
        contentColor = colors.content,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(NavigationButtonDefaults.IconSize),
            )
        }
    }
}

/**
 * Default values for [NavigationButton].
 */
object NavigationButtonDefaults {

    val Size: Dp = 40.dp
    val IconSize: Dp = 24.dp
    val CornerRadius: Dp = 12.dp
    val Shape: Shape = RoundedCornerShape(CornerRadius)

    /**
     * Color configuration for [NavigationButton].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val content: Color,
    )

    @Composable
    fun colors(
        container: Color = System.color.backgroundTertiary,
        content: Color = System.color.iconBase,
    ): Colors = Colors(
        container = container,
        content = content,
    )
}

@Preview
@Composable
private fun NavigationButtonPreview() {
    NavigationButton(onClick = {})
}

