package uz.yalla.components.section.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.tooling.preview.Preview
import uz.yalla.components.primitive.transformation.MaskFormatter
import uz.yalla.design.theme.System
import uz.yalla.resources.Res
import uz.yalla.resources.img_no_pfp

/**
 * User profile card with avatar, name, and phone.
 *
 * Displays user information with optional action buttons.
 *
 * ## Usage
 *
 * ```kotlin
 * ProfileCard(
 *     imageUrl = user.avatar,
 *     name = "${user.firstName} ${user.lastName}",
 *     phone = user.phone,
 *     leadingAction = { NavigationButton(onClick = onBack) },
 *     trailingAction = { IconButton(onClick = onEdit) { Icon(...) } },
 * )
 * ```
 *
 * @param name User display name
 * @param phone User phone number
 * @param modifier Applied to component
 * @param imageUrl Optional avatar URL
 * @param phoneMask Phone number display mask
 * @param leadingAction Optional left action button
 * @param trailingAction Optional right action button
 * @param colors Color configuration
 *
 * @see ProfileCardDefaults for default values
 */
@Composable
fun ProfileCard(
    name: String,
    phone: String,
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    phoneMask: String = ProfileCardDefaults.PhoneMask,
    leadingAction: (@Composable () -> Unit)? = null,
    trailingAction: (@Composable () -> Unit)? = null,
    colors: ProfileCardDefaults.Colors = ProfileCardDefaults.colors(),
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth(),
    ) {
        if (leadingAction != null) {
            leadingAction()
        } else {
            Spacer(Modifier.size(ProfileCardDefaults.ActionButtonSize))
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                placeholder = painterResource(Res.drawable.img_no_pfp),
                error = painterResource(Res.drawable.img_no_pfp),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(ProfileCardDefaults.AvatarSize),
            )

            Spacer(Modifier.height(ProfileCardDefaults.NameTopSpacing))

            Text(
                text = name,
                style = System.font.title.large,
                color = colors.name,
            )

            Spacer(Modifier.height(ProfileCardDefaults.PhoneTopSpacing))

            Text(
                text = MaskFormatter.format(
                    text = phone,
                    mask = phoneMask,
                ),
                style = System.font.body.small.medium,
                color = colors.phone,
            )

            Spacer(Modifier.height(ProfileCardDefaults.BottomSpacing))
        }

        if (trailingAction != null) {
            trailingAction()
        } else {
            Spacer(Modifier.size(ProfileCardDefaults.ActionButtonSize))
        }
    }
}

/**
 * Default values for [ProfileCard].
 */
object ProfileCardDefaults {

    val AvatarSize: Dp = 80.dp
    val ActionButtonSize: Dp = 40.dp
    val NameTopSpacing: Dp = 10.dp
    val PhoneTopSpacing: Dp = 4.dp
    val BottomSpacing: Dp = 10.dp
    const val PhoneMask: String = "____ (__) ___-__-__"

    /**
     * Color configuration for [ProfileCard].
     */
    @Immutable
    data class Colors(
        val name: Color,
        val phone: Color,
    )

    @Composable
    fun colors(
        name: Color = System.color.textBase,
        phone: Color = System.color.textBase,
    ): Colors = Colors(
        name = name,
        phone = phone,
    )
}

@Preview
@Composable
private fun ProfileCardPreview() {
    ProfileCard(
        name = "John Doe",
        phone = "998901234567",
    )
}

