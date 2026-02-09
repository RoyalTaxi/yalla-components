package uz.yalla.components.primitive.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * Modal loading dialog with centered spinner.
 *
 * Use to block interaction during critical operations.
 *
 * ## Usage
 *
 * ```kotlin
 * if (state.isLoading) {
 *     LoadingDialog()
 * }
 * ```
 *
 * @param modifier Applied to dialog content.
 * @param onDismissRequest Called when user tries to dismiss (optional).
 * @param dismissOnBackPress Whether back press dismisses dialog.
 * @param dismissOnClickOutside Whether clicking outside dismisses dialog.
 * @param colors Color configuration, defaults to [LoadingDialogDefaults.colors].
 * @param dimens Dimension configuration, defaults to [LoadingDialogDefaults.dimens].
 *
 * @see LoadingDialogDefaults for default values
 */
@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    dismissOnBackPress: Boolean = false,
    dismissOnClickOutside: Boolean = false,
    colors: LoadingDialogDefaults.LoadingDialogColors = LoadingDialogDefaults.colors(),
    dimens: LoadingDialogDefaults.LoadingDialogDimens = LoadingDialogDefaults.dimens(),
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside,
        ),
    ) {
        Box(
            modifier = modifier
                .size(dimens.size)
                .background(
                    color = colors.container,
                    shape = dimens.shape,
                )
                .padding(dimens.contentPadding),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(dimens.indicatorSize),
                color = colors.indicator,
                strokeWidth = dimens.strokeWidth,
            )
        }
    }
}

/**
 * Default configuration values for [LoadingDialog].
 *
 * Provides theme-aware defaults for [colors] and [dimens] that can be overridden.
 */
object LoadingDialogDefaults {

    /**
     * Color configuration for [LoadingDialog].
     *
     * @param container Background color.
     * @param indicator Spinner color.
     */
    data class LoadingDialogColors(
        val container: Color,
        val indicator: Color,
    )

    @Composable
    fun colors(
        container: Color = System.color.backgroundBase,
        indicator: Color = System.color.buttonActive,
    ) = LoadingDialogColors(
        container = container,
        indicator = indicator,
    )

    /**
     * Dimension configuration for [LoadingDialog].
     *
     * @param size Dialog box size.
     * @param indicatorSize Spinner size.
     * @param strokeWidth Spinner stroke width.
     * @param cornerRadius Corner radius.
     * @param contentPadding Padding inside dialog.
     * @param shape Dialog shape.
     */
    data class LoadingDialogDimens(
        val size: Dp,
        val indicatorSize: Dp,
        val strokeWidth: Dp,
        val cornerRadius: Dp,
        val contentPadding: Dp,
        val shape: Shape,
    )

    @Composable
    fun dimens(
        size: Dp = 100.dp,
        indicatorSize: Dp = 48.dp,
        strokeWidth: Dp = 4.dp,
        cornerRadius: Dp = 16.dp,
        contentPadding: Dp = 24.dp,
        shape: Shape = RoundedCornerShape(cornerRadius),
    ) = LoadingDialogDimens(
        size = size,
        indicatorSize = indicatorSize,
        strokeWidth = strokeWidth,
        cornerRadius = cornerRadius,
        contentPadding = contentPadding,
        shape = shape,
    )
}


@Preview
@Composable
private fun LoadingDialogContentPreview() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            strokeWidth = 4.dp,
        )
    }
}
