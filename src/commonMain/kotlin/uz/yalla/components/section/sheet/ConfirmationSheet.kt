package uz.yalla.components.section.sheet

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uz.yalla.components.primitive.button.PrimaryButton
import uz.yalla.design.theme.System

/**
 * Data for [ConfirmationSheet].
 *
 * @param sheetName Optional header title displayed at top
 * @param image Illustration image
 * @param title Main title text
 * @param description Description text
 * @param actionText Primary action button text
 */
data class ConfirmationSheetData(
    val sheetName: String?,
    val image: Painter,
    val title: String,
    val description: String,
    val actionText: String,
)

/**
 * Effects emitted by [ConfirmationSheet].
 */
sealed interface ConfirmationSheetEffect {
    /** User dismissed the sheet. */
    data object Dismiss : ConfirmationSheetEffect

    /** User confirmed the action. */
    data object Confirm : ConfirmationSheetEffect
}

/**
 * Confirmation sheet with image, title, description, and action.
 *
 * Use for confirmations, success messages, or informational modals.
 *
 * ## Usage
 *
 * ```kotlin
 * ConfirmationSheet(
 *     isVisible = state.showSuccess,
 *     data = ConfirmationSheetData(
 *         sheetTitle = "Order Status",
 *         image = painterResource(Res.drawable.img_success),
 *         title = "Order Placed",
 *         description = "Your driver is on the way.",
 *         actionText = "Got it",
 *     ),
 *     onEffect = { effect ->
 *         when (effect) {
 *             ConfirmationSheetEffect.Dismiss -> viewModel.dismissSheet()
 *             ConfirmationSheetEffect.Confirm -> viewModel.confirmOrder()
 *         }
 *     },
 * )
 * ```
 *
 * @param isVisible Whether sheet is visible
 * @param data Sheet content data
 * @param onEffect Callback for sheet effects (dismiss, confirm)
 * @param modifier Applied to sheet
 * @param closeButton Optional close button slot
 * @param shape Sheet corner shape
 * @param colors Color configuration
 *
 * @see ConfirmationSheetData for content configuration
 * @see ConfirmationSheetEffect for available effects
 * @see ConfirmationSheetDefaults for default values
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationSheet(
    isVisible: Boolean,
    data: ConfirmationSheetData,
    onEffect: (ConfirmationSheetEffect) -> Unit,
    modifier: Modifier = Modifier,
    closeButton: (@Composable () -> Unit)? = null,
    shape: Shape = ConfirmationSheetDefaults.Shape,
    colors: ConfirmationSheetDefaults.Colors = ConfirmationSheetDefaults.colors(),
) {
    var contentVisible by remember { mutableStateOf(false) }

    val contentAlpha by animateFloatAsState(
        targetValue = if (contentVisible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium,
        ),
        label = "contentAlpha",
    )

    val contentOffsetY by animateFloatAsState(
        targetValue = if (contentVisible) 0f else 24f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium,
        ),
        label = "contentOffset",
    )

    LaunchedEffect(isVisible) {
        if (!isVisible) {
            contentVisible = false
        }
    }

    Sheet(
        isVisible = isVisible,
        onDismissRequest = { onEffect(ConfirmationSheetEffect.Dismiss) },
        modifier = modifier,
        shape = shape,
        colors = SheetDefaults.colors(
            container = colors.container,
        ),
        dragHandle = null,
        onFullyExpanded = { contentVisible = true },
    ) {
        Column(modifier = Modifier.background(colors.container)) {
            Spacer(Modifier.height(ConfirmationSheetDefaults.HeaderTopPadding))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ConfirmationSheetDefaults.HeaderHorizontalPadding),
            ) {
                data.sheetName?.let { title ->
                    Text(
                        text = title,
                        style = System.font.body.large.medium,
                        color = colors.title,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                if (closeButton != null) {
                    closeButton()
                }
            }

            Spacer(Modifier.height(ConfirmationSheetDefaults.ContentTopPadding))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = ConfirmationSheetDefaults.ContentHorizontalPadding)
                    .alpha(contentAlpha)
                    .offset { IntOffset(0, contentOffsetY.toInt()) },
            ) {
                Image(
                    painter = data.image,
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .fillMaxWidth(ConfirmationSheetDefaults.ImageWidthFraction)
                        .aspectRatio(1f),
                )

                Spacer(Modifier.height(ConfirmationSheetDefaults.ImageBottomSpacing))

                Text(
                    text = data.title,
                    style = System.font.title.base,
                    color = colors.title,
                    textAlign = TextAlign.Center,
                )

                Spacer(Modifier.height(ConfirmationSheetDefaults.TitleDescriptionSpacing))

                Text(
                    text = data.description,
                    style = System.font.body.base.medium,
                    color = colors.description,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(Modifier.height(ConfirmationSheetDefaults.ActionTopSpacing))

            PrimaryButton(
                text = data.actionText,
                onClick = { onEffect(ConfirmationSheetEffect.Confirm) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ConfirmationSheetDefaults.ActionHorizontalPadding)
                    .alpha(contentAlpha),
            )

            Spacer(Modifier.height(ConfirmationSheetDefaults.ActionBottomSpacing))

            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

/**
 * Default values for [ConfirmationSheet].
 */
object ConfirmationSheetDefaults {

    val Shape: Shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    val HeaderTopPadding: Dp = 10.dp
    val HeaderHorizontalPadding: Dp = 10.dp
    val ContentTopPadding: Dp = 44.dp
    val ContentHorizontalPadding: Dp = 36.dp
    const val ImageWidthFraction: Float = 0.6f
    val ImageBottomSpacing: Dp = 36.dp
    val TitleDescriptionSpacing: Dp = 12.dp
    val ActionTopSpacing: Dp = 64.dp
    val ActionHorizontalPadding: Dp = 20.dp
    val ActionBottomSpacing: Dp = 12.dp

    /**
     * Color configuration for [ConfirmationSheet].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val title: Color,
        val description: Color,
    )

    @Composable
    fun colors(
        container: Color = System.color.backgroundBase,
        title: Color = System.color.textBase,
        description: Color = System.color.textSubtle,
    ): Colors = Colors(
        container = container,
        title = title,
        description = description,
    )
}

