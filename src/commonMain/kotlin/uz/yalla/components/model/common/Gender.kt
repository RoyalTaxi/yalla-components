package uz.yalla.components.model.common

import org.jetbrains.compose.resources.StringResource
import uz.yalla.resources.Res
import uz.yalla.resources.register_gender_female
import uz.yalla.resources.register_gender_male

/**
 * Gender selection options.
 *
 * @property raw Raw string value for API/serialization
 * @property resource String resource for display, null for NOT_SELECTED
 */
enum class Gender(
    val raw: String,
    val resource: StringResource?
) {
    MALE("MALE", Res.string.register_gender_male),
    FEMALE("FEMALE", Res.string.register_gender_female),
    NOT_SELECTED("NOT_SELECTED", null),
}
