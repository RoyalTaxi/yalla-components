package uz.yalla.components.section.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.yalla.components.primitive.button.PrimaryButton
import uz.yalla.components.primitive.button.SecondaryButton
import uz.yalla.design.theme.System

/**
 * UI state for [ActionSheet].
 *
 * @param title Sheet title.
 * @param message Optional description text.
 * @param primaryAction Primary button text.
 * @param secondaryAction Optional secondary button text.
 */
data class ActionSheetState(
    val title: String,
    val message: String? = null,
    val primaryAction: String,
    val secondaryAction: String? = null,
)

/**
 * Action sheet with title, message, and action buttons.
 *
 * Use for prompting user actions with primary/secondary options.
 *
 * ## Usage
 *
 * ```kotlin
 * ActionSheet(
 *     isVisible = showDelete,
 *     state = ActionSheetState(
 *         title = "Delete Card",
 *         message = "Are you sure you want to delete this card?",
 *         primaryAction = "Delete",
 *         secondaryAction = "Cancel",
 *     ),
 *     onPrimaryClick = { viewModel.deleteCard() },
 *     onSecondaryClick = { viewModel.cancelDelete() },
 *     onDismiss = { viewModel.dismissSheet() },
 * )
 * ```
 *
 * @param isVisible Whether sheet is visible.
 * @param state Sheet content state.
 * @param onPrimaryClick Called when primary button clicked.
 * @param onDismiss Called when sheet dismissed.
 * @param modifier Applied to sheet.
 * @param onSecondaryClick Called when secondary button clicked.
 * @param shape Sheet corner shape.
 * @param colors Color configuration.
 *
 * @see ActionSheetDefaults for default values
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionSheet(
    isVisible: Boolean,
    state: ActionSheetState,
    onPrimaryClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    onSecondaryClick: (() -> Unit)? = null,
    shape: Shape = ActionSheetDefaults.Shape,
    colors: ActionSheetDefaults.Colors = ActionSheetDefaults.colors(),
) {
    Sheet(
        isVisible = isVisible,
        onDismissRequest = onDismiss,
        modifier = modifier,
        shape = shape,
        colors = SheetDefaults.colors(
            container = colors.container,
        ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(ActionSheetDefaults.ContentPadding),
        ) {
            Spacer(Modifier.height(ActionSheetDefaults.TopSpacing))

            Text(
                text = state.title,
                style = System.font.title.base,
                color = colors.title,
                textAlign = TextAlign.Center,
            )

            if (state.message != null) {
                Spacer(Modifier.height(ActionSheetDefaults.TitleMessageSpacing))

                Text(
                    text = state.message,
                    style = System.font.body.base.medium,
                    color = colors.message,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(Modifier.height(ActionSheetDefaults.ContentButtonsSpacing))

            HorizontalDivider(
                color = colors.divider,
                thickness = 1.dp,
            )

            Spacer(Modifier.height(ActionSheetDefaults.DividerButtonSpacing))

            PrimaryButton(
                text = state.primaryAction,
                onClick = onPrimaryClick,
                modifier = Modifier.fillMaxWidth(),
            )

            if (state.secondaryAction != null && onSecondaryClick != null) {
                Spacer(Modifier.height(ActionSheetDefaults.ButtonSpacing))

                SecondaryButton(
                    text = state.secondaryAction,
                    onClick = onSecondaryClick,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Spacer(Modifier.height(ActionSheetDefaults.BottomSpacing))

            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

/**
 * Default values for [ActionSheet].
 */
object ActionSheetDefaults {

    val Shape: Shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    val ContentPadding: PaddingValues = PaddingValues(horizontal = 20.dp)
    val TopSpacing: Dp = 16.dp
    val TitleMessageSpacing: Dp = 8.dp
    val ContentButtonsSpacing: Dp = 24.dp
    val DividerButtonSpacing: Dp = 16.dp
    val ButtonSpacing: Dp = 8.dp
    val BottomSpacing: Dp = 12.dp

    /**
     * Color configuration for [ActionSheet].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val title: Color,
        val message: Color,
        val divider: Color,
    )

    @Composable
    fun colors(
        container: Color = System.color.backgroundBase,
        title: Color = System.color.textBase,
        message: Color = System.color.textSubtle,
        divider: Color = System.color.borderDisabled,
    ): Colors = Colors(
        container = container,
        title = title,
        message = message,
        divider = divider,
    )
}

@Preview
@Composable
private fun ActionSheetContentPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(ActionSheetDefaults.ContentPadding),
        ) {
            Text(
                text = "Delete Card",
                style = System.font.title.base,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Are you sure you want to delete this card?",
                style = System.font.body.base.medium,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(16.dp))
            PrimaryButton(
                text = "Delete",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))
            SecondaryButton(
                text = "Cancel",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
