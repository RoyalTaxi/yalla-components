package uz.yalla.components.composite.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * Data for snackbar display.
 *
 * @param message Message text.
 * @param isSuccess Whether this is a success or error message.
 */
data class SnackbarData(
    val message: String,
    val isSuccess: Boolean = true,
)

/**
 * Snackbar host for displaying transient messages.
 *
 * Shows snackbars at the top of the screen with status bar padding.
 *
 * ## Usage
 *
 * ```kotlin
 * val snackbarHostState = remember { SnackbarHostState() }
 *
 * AppSnackbarHost(
 *     data = currentSnackbar,
 *     hostState = snackbarHostState,
 *     successIcon = painterResource(Res.drawable.ic_check_circle),
 *     errorIcon = painterResource(Res.drawable.ic_warning),
 *     dismissIcon = painterResource(Res.drawable.ic_x),
 *     onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() },
 * )
 *
 * // Show snackbar
 * LaunchedEffect(event) {
 *     snackbarHostState.showSnackbar("Message")
 * }
 * ```
 *
 * @param data Current snackbar data.
 * @param hostState Material snackbar host state.
 * @param successIcon Icon for success variant.
 * @param errorIcon Icon for error variant.
 * @param dismissIcon Dismiss button icon.
 * @param onDismiss Called when dismiss clicked.
 * @param modifier Applied to host.
 * @param dimens Dimension configuration, defaults to [SnackbarHostDefaults.dimens].
 *
 * @see SnackbarHostDefaults for default values
 */
@Composable
fun AppSnackbarHost(
    data: SnackbarData?,
    hostState: SnackbarHostState,
    successIcon: Painter,
    errorIcon: Painter,
    dismissIcon: Painter,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    dimens: SnackbarHostDefaults.SnackbarHostDimens = SnackbarHostDefaults.dimens(),
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier
            .statusBarsPadding()
            .padding(top = dimens.topPadding)
            .padding(horizontal = dimens.horizontalPadding),
    ) {
        if (data != null) {
            Snackbar(
                state = SnackbarState(
                    message = data.message,
                    variant = if (data.isSuccess) SnackbarVariant.Success else SnackbarVariant.Error,
                    icon = if (data.isSuccess) successIcon else errorIcon,
                    dismissIcon = dismissIcon,
                ),
                onDismiss = onDismiss,
            )
        }
    }
}

/**
 * Default values for [AppSnackbarHost].
 *
 * Provides defaults for [dimens] that can be overridden.
 */
object SnackbarHostDefaults {

    /**
     * Dimension configuration for [AppSnackbarHost].
     *
     * @param topPadding Top padding.
     * @param horizontalPadding Horizontal padding.
     */
    data class SnackbarHostDimens(
        val topPadding: Dp,
        val horizontalPadding: Dp,
    )

    @Composable
    fun dimens(
        topPadding: Dp = 8.dp,
        horizontalPadding: Dp = 24.dp,
    ): SnackbarHostDimens = SnackbarHostDimens(
        topPadding = topPadding,
        horizontalPadding = horizontalPadding,
    )
}

@Preview
@Composable
private fun SnackbarHostPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Snackbar Preview",
            style = System.font.body.base.medium,
        )
    }
}
