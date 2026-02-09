package uz.yalla.components.model.payment

/**
 * Payment method types.
 *
 * @property typeName The payment type identifier
 */
sealed class PaymentType(val typeName: String) {
    data object CASH : PaymentType("cash")

    data class CARD(
        val cardId: String,
        val cardNumber: String
    ) : PaymentType("card")

    companion object {
        fun fromTypeName(
            typeName: String,
            cardId: String? = null,
            cardNumber: String? = null
        ): PaymentType = when (typeName) {
            "cash" -> CASH
            "card" -> CARD(cardId.orEmpty(), cardNumber.orEmpty())
            else -> CASH
        }
    }

    fun matches(cardId: String): Boolean = this is CARD && this.cardId == cardId
}
