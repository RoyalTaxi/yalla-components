package uz.yalla.components.section.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uz.yalla.design.theme.System

/**
 * Snackbar variant for success/error states.
 */
enum class SnackbarVariant {
    Success,
    Error,
}

/**
 * Snackbar message with icon, text, and dismiss action.
 *
 * Use for transient feedback messages.
 *
 * ## Usage
 *
 * ```kotlin
 * Snackbar(
 *     message = "Card added successfully",
 *     variant = SnackbarVariant.Success,
 *     icon = painterResource(Res.drawable.ic_check_circle),
 *     dismissIcon = painterResource(Res.drawable.ic_x),
 *     onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() },
 * )
 * ```
 *
 * @param message Snackbar text
 * @param variant Success or error variant
 * @param icon Leading icon
 * @param dismissIcon Dismiss button icon
 * @param onDismiss Called when dismiss clicked
 * @param modifier Applied to snackbar
 * @param shape Snackbar corner shape
 * @param colors Color configuration
 *
 * @see SnackbarDefaults for default values
 */
@Composable
fun Snackbar(
    message: String,
    variant: SnackbarVariant,
    icon: Painter,
    dismissIcon: Painter,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = SnackbarDefaults.Shape,
    colors: SnackbarDefaults.Colors = SnackbarDefaults.colors(variant),
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = colors.container,
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(SnackbarDefaults.ContentSpacing),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = SnackbarDefaults.VerticalPadding,
                    horizontal = SnackbarDefaults.HorizontalPadding,
                ),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.background(
                    color = colors.iconBackground,
                    shape = RoundedCornerShape(SnackbarDefaults.IconBackgroundRadius),
                ),
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = colors.icon,
                    modifier = Modifier
                        .padding(SnackbarDefaults.IconPadding)
                        .size(SnackbarDefaults.IconSize),
                )
            }

            Text(
                text = message,
                style = System.font.body.small.medium,
                color = colors.text,
                modifier = Modifier.weight(1f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            IconButton(onClick = onDismiss) {
                Icon(
                    painter = dismissIcon,
                    contentDescription = null,
                    tint = colors.dismissIcon,
                    modifier = Modifier.size(SnackbarDefaults.DismissIconSize),
                )
            }
        }
    }
}

/**
 * Default values for [Snackbar].
 */
object SnackbarDefaults {

    val Shape: Shape = RoundedCornerShape(16.dp)
    val ContentSpacing: Dp = 8.dp
    val VerticalPadding: Dp = 12.dp
    val HorizontalPadding: Dp = 16.dp
    val IconSize: Dp = 24.dp
    val IconPadding: Dp = 6.dp
    val IconBackgroundRadius: Dp = 36.dp
    val DismissIconSize: Dp = 20.dp

    /**
     * Color configuration for [Snackbar].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val iconBackground: Color,
        val icon: Color,
        val text: Color,
        val dismissIcon: Color,
    )

    @Composable
    fun colors(
        variant: SnackbarVariant,
    ): Colors = when (variant) {
        SnackbarVariant.Success -> Colors(
            container = System.color.buttonActive,
            iconBackground = Color.White.copy(alpha = 0.2f),
            icon = System.color.iconWhite,
            text = System.color.iconWhite,
            dismissIcon = System.color.iconWhite,
        )
        SnackbarVariant.Error -> Colors(
            container = System.color.borderError,
            iconBackground = Color.White.copy(alpha = 0.2f),
            icon = System.color.iconWhite,
            text = System.color.iconWhite,
            dismissIcon = System.color.iconWhite,
        )
    }
}

