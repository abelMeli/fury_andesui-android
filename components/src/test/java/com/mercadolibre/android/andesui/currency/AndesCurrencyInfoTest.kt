package com.mercadolibre.android.andesui.currency

import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.assertIsNull
import org.junit.Test

class AndesCurrencyInfoTest {

    @Test
    fun `Currency Info drawable 0 turns to icon null`() {
        // GIVEN WHEN
        val currencyInfo = AndesCurrencyInfo(
            "ARS",
            2,
            null,
            null,
            null,
            null,
            0,
            false
        )

        // THEN
        currencyInfo.icon assertIsNull true
    }

    @Test
    fun `Currency Info drawable icon set correctly`() {
        // GIVEN WHEN
        val currencyInfo = AndesCurrencyInfo(
            "BRL",
            2,
            null,
            null,
            null,
            null,
            R.drawable.andes_comunicacion_answer_16,
            false
        )

        // THEN
        currencyInfo.icon assertEquals R.drawable.andes_comunicacion_answer_16
    }

    @Test
    fun `Currency Info drawable icon set correctly after creation`() {
        // GIVEN
        val currencyInfo = AndesCurrencyInfo(
            "BRL",
            2,
            null,
            null,
            null,
            null,
            null,
            false
        )

        // WHEN
        currencyInfo.icon = R.drawable.andes_comunicacion_answer_16

        // THEN
        currencyInfo.icon assertEquals R.drawable.andes_comunicacion_answer_16
    }
}
