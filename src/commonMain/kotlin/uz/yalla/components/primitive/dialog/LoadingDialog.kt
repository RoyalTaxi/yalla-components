package uz.yalla.components.primitive.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
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
 * @param colors Color configuration.
 *
 * @see LoadingDialogDefaults for default values
 */
@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    dismissOnBackPress: Boolean = false,
    dismissOnClickOutside: Boolean = false,
    colors: LoadingDialogDefaults.Colors = LoadingDialogDefaults.colors(),
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
                .size(LoadingDialogDefaults.Size)
                .background(
                    color = colors.container,
                    shape = LoadingDialogDefaults.Shape,
                )
                .padding(LoadingDialogDefaults.ContentPadding),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(LoadingDialogDefaults.IndicatorSize),
                color = colors.indicator,
                strokeWidth = LoadingDialogDefaults.StrokeWidth,
            )
        }
    }
}

/**
 * Default values for [LoadingDialog].
 */
object LoadingDialogDefaults {

    val Size: Dp = 100.dp
    val IndicatorSize: Dp = 48.dp
    val StrokeWidth: Dp = 4.dp
    val CornerRadius: Dp = 16.dp
    val Shape: Shape = RoundedCornerShape(CornerRadius)
    val ContentPadding: Dp = 24.dp

    /**
     * Color configuration for [LoadingDialog].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val indicator: Color,
    )

    @Composable
    fun colors(
        container: Color = System.color.backgroundBase,
        indicator: Color = System.color.buttonActive,
    ): Colors = Colors(
        container = container,
        indicator = indicator,
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
