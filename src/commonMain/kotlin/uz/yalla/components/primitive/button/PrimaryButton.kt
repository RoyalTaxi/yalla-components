package uz.yalla.components.primitive.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

/**
 * Primary action button for main user interactions.
 *
 * Use for the single most important action on a screen, such as "Confirm",
 * "Continue", or "Submit". For secondary actions, use [SecondaryButton].
 *
 * ## Usage
 *
 * ```kotlin
 * PrimaryButton(
 *     text = "Confirm Order",
 *     onClick = viewModel::confirmOrder,
 * )
 * ```
 *
 * ## With Loading State
 *
 * ```kotlin
 * PrimaryButton(
 *     text = "Submit",
 *     onClick = onSubmit,
 *     loading = state.isLoading,
 * )
 * ```
 *
 * ## With Icons
 *
 * ```kotlin
 * PrimaryButton(
 *     text = "Add to Cart",
 *     onClick = onAddToCart,
 *     leadingIcon = { Icon(Icons.Default.Add, null) },
 * )
 * ```
 *
 * @param text Button label. Keep concise (1-3 words).
 * @param onClick Invoked on click. Not called when disabled or loading.
 * @param modifier Applied to button container.
 * @param enabled When false, button appears disabled and is not clickable.
 * @param loading When true, shows spinner and disables interaction.
 * @param size Button size variant. Defaults to [ButtonSize.Medium].
 * @param colors Color configuration. Use [PrimaryButtonDefaults.colors] to customize.
 * @param leadingIcon Optional composable before text.
 * @param trailingIcon Optional composable after text.
 *
 * @see SecondaryButton for secondary actions
 * @see TextButton for low-emphasis actions
 * @see PrimaryButtonDefaults for default values
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    size: ButtonSize = ButtonSize.Medium,
    colors: PrimaryButtonDefaults.Colors = PrimaryButtonDefaults.colors(),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val isInteractive = enabled && !loading

    ButtonContainer(
        onClick = onClick,
        modifier = modifier.defaultMinSize(
            minWidth = PrimaryButtonDefaults.minWidth(size),
            minHeight = PrimaryButtonDefaults.minHeight(size),
        ),
        enabled = isInteractive,
        shape = PrimaryButtonDefaults.Shape,
        containerColor = colors.containerColor(isInteractive),
        contentColor = colors.contentColor(isInteractive),
        contentPadding = PrimaryButtonDefaults.contentPadding(size),
    ) {
        ButtonContent(
            text = text,
            textStyle = PrimaryButtonDefaults.textStyle(size),
            loading = loading,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
        )
    }
}

/**
 * Container surface for button with click handling.
 */
@Composable
private fun ButtonContainer(
    onClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    shape: Shape,
    containerColor: Color,
    contentColor: Color,
    contentPadding: PaddingValues,
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
    ) {
        Row(
            modifier = Modifier.padding(contentPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }
}

/**
 * Button content with text, optional icons, and loading state.
 */
@Composable
private fun RowScope.ButtonContent(
    text: String,
    textStyle: TextStyle,
    loading: Boolean,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
) {
    if (loading) {
        LoadingIndicator()
    } else {
        TextWithIcons(
            text = text,
            textStyle = textStyle,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
        )
    }
}

/**
 * Text content with optional leading and trailing icons.
 */
@Composable
private fun TextWithIcons(
    text: String,
    textStyle: TextStyle,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
) {
    leadingIcon?.let { icon ->
        IconSlot(content = icon)
        Spacer(Modifier.width(PrimaryButtonDefaults.IconSpacing))
    }

    Text(text = text, style = textStyle)

    trailingIcon?.let { icon ->
        Spacer(Modifier.width(PrimaryButtonDefaults.IconSpacing))
        IconSlot(content = icon)
    }
}

/**
 * Sized container for button icons.
 */
@Composable
private fun IconSlot(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.size(PrimaryButtonDefaults.IconSize),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

/**
 * Loading spinner for button loading state.
 */
@Composable
private fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.size(PrimaryButtonDefaults.IconSize),
        strokeWidth = 2.dp,
        color = LocalContentColor.current,
    )
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        PrimaryButton(
            text = "Continue",
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun PrimaryButtonLoadingPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        PrimaryButton(
            text = "Loading",
            onClick = {},
            loading = true,
        )
    }
}
