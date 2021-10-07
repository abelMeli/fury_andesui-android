package com.mercadolibre.android.andesui.country

enum class AndesCountry {

    AR, BR, CL, CO, MX, CR, PE,
    EC, PA, DO, UY, VE, BO, PY,
    GT, HN, NI, SV, PR, CU;

    companion object {
        fun fromString(value: String): AndesCountry = valueOf(value.toUpperCase())
    }
}
