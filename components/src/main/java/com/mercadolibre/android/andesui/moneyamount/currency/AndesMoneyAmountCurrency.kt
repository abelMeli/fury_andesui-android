package com.mercadolibre.android.andesui.moneyamount.currency

enum class AndesMoneyAmountCurrency {

    BRL, UYU, CLP, MXN, DOP, PAB, COP,
    VES, VEF, PEN, CRC, ARS, CLF, USD,
    BOB, PYG, GTQ, HNL, NIO, EUR, CUC;

    companion object {
        fun fromString(value: String): AndesMoneyAmountCurrency = valueOf(value.toUpperCase())
    }
}
