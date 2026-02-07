package uz.yalla.components.primitive.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

/**
 * Secondary action button for alternative or less prominent actions.
 *
 * Use for secondary actions on a screen, such as "Cancel", "Skip", or "Later".
 * For the main action, use [PrimaryButton].
 *
 * ## Usage
 *
 * ```kotlin
 * SecondaryButton(
 *     text = "Cancel",
 *     onClick = onCancel,
 * )
 * ```
 *
 * @param text Button label.
 * @param onClick Invoked on click.
 * @param modifier Applied to button container.
 * @param enabled When false, button is disabled.
 * @param loading When true, shows spinner.
 * @param size Button size variant.
 * @param colors Color configuration.
 * @param leadingIcon Optional icon before text.
 * @param trailingIcon Optional icon after text.
 *
 * @see PrimaryButton for primary actions
 * @see SecondaryButtonDefaults for default values
 */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    size: ButtonSize = ButtonSize.Medium,
    colors: SecondaryButtonDefaults.Colors = SecondaryButtonDefaults.colors(),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val isInteractive = enabled && !loading

    Surface(
        onClick = onClick,
        modifier = modifier.defaultMinSize(
            minWidth = SecondaryButtonDefaults.minWidth(size),
            minHeight = SecondaryButtonDefaults.minHeight(size),
        ),
        enabled = isInteractive,
        shape = SecondaryButtonDefaults.Shape,
        color = colors.containerColor(isInteractive),
        contentColor = colors.contentColor(isInteractive),
    ) {
        Row(
            modifier = Modifier.padding(SecondaryButtonDefaults.contentPadding(size)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(SecondaryButtonDefaults.IconSize),
                    strokeWidth = 2.dp,
                    color = LocalContentColor.current,
                )
            } else {
                leadingIcon?.let { icon ->
                    Box(
                        modifier = Modifier.size(SecondaryButtonDefaults.IconSize),
                        contentAlignment = Alignment.Center,
                    ) { icon() }
                    Spacer(Modifier.width(SecondaryButtonDefaults.IconSpacing))
                }

                Text(
                    text = text,
                    style = SecondaryButtonDefaults.textStyle(size),
                )

                trailingIcon?.let { icon ->
                    Spacer(Modifier.width(SecondaryButtonDefaults.IconSpacing))
                    Box(
                        modifier = Modifier.size(SecondaryButtonDefaults.IconSize),
                        contentAlignment = Alignment.Center,
                    ) { icon() }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SecondaryButtonPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        SecondaryButton(
            text = "Cancel",
            onClick = {},
        )
    }
}
