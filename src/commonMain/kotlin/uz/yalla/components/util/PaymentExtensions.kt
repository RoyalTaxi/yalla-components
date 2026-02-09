package uz.yalla.components.util

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import uz.yalla.components.model.payment.PaymentType
import uz.yalla.resources.Res
import uz.yalla.resources.ic_cash
import uz.yalla.resources.ic_humo
import uz.yalla.resources.ic_uzcard
import uz.yalla.resources.payment_card_humo_format
import uz.yalla.resources.payment_card_uzcard_format
import uz.yalla.resources.payment_type_cash

fun PaymentType.getDrawableResource(): DrawableResource =
    when (this) {
        PaymentType.CASH -> Res.drawable.ic_cash

        is PaymentType.CARD -> {
            if (cardId.length == 16) {
                Res.drawable.ic_humo
            } else {
                Res.drawable.ic_uzcard
            }
        }
    }

fun PaymentType.getStringResource(): StringResource =
    when (this) {
        PaymentType.CASH -> Res.string.payment_type_cash

        is PaymentType.CARD -> {
            if (cardId.length == 16) {
                Res.string.payment_card_humo_format
            } else {
                Res.string.payment_card_uzcard_format
            }
        }
    }
