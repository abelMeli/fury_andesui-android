package com.mercadolibre.android.andesui.card

import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.card.bodyPadding.AndesCardBodyPadding
import com.mercadolibre.android.andesui.card.hierarchy.AndesCardHierarchy
import com.mercadolibre.android.andesui.card.padding.AndesCardPadding
import com.mercadolibre.android.andesui.card.style.AndesCardStyle
import com.mercadolibre.android.andesui.card.type.AndesCardType
import java.lang.IllegalArgumentException
import org.junit.Assert
import org.junit.Test

class AndesCardEnumsTest {

    @Test
    fun `AndesCardHierarchy fromString parse valid`() {
        // WHEN
        val primary = AndesCardHierarchy.fromString("primary")
        val secondary = AndesCardHierarchy.fromString("secondary")
        val secondaryDark = AndesCardHierarchy.fromString("secondary_dark")

        // THEN
        primary assertEquals AndesCardHierarchy.PRIMARY
        secondary assertEquals AndesCardHierarchy.SECONDARY
        secondaryDark assertEquals AndesCardHierarchy.SECONDARY_DARK
    }

    @Test
    fun `AndesCardHierarchy fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesCardHierarchy.fromString("secondarydark")
        }
    }

    @Test
    fun `AndesCardBodyPadding fromString parse valid`() {
        // WHEN
        val none = AndesCardBodyPadding.fromString("none")
        val small = AndesCardBodyPadding.fromString("small")
        val medium = AndesCardBodyPadding.fromString("medium")
        val large = AndesCardBodyPadding.fromString("large")
        val xlarge = AndesCardBodyPadding.fromString("xlarge")

        // THEN
        none assertEquals AndesCardBodyPadding.NONE
        small assertEquals AndesCardBodyPadding.SMALL
        medium assertEquals AndesCardBodyPadding.MEDIUM
        large assertEquals AndesCardBodyPadding.LARGE
        xlarge assertEquals AndesCardBodyPadding.XLARGE
    }

    @Test
    fun `AndesCardBodyPadding fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesCardBodyPadding.fromString("x_large")
        }
    }

    @Test
    fun `AndesCardStyle fromString parse valid`() {
        // WHEN
        val elevated = AndesCardStyle.fromString("elevated")
        val outline = AndesCardStyle.fromString("outline")

        // THEN
        elevated assertEquals AndesCardStyle.ELEVATED
        outline assertEquals AndesCardStyle.OUTLINE
    }

    @Test
    fun `AndesCardStyle fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesCardStyle.fromString("out_line")
        }
    }

    @Test
    fun `AndesCardPadding fromString parse valid`() {
        // WHEN
        val none = AndesCardPadding.fromString("none")
        val small = AndesCardPadding.fromString("small")
        val medium = AndesCardPadding.fromString("medium")
        val large = AndesCardPadding.fromString("large")
        val xlarge = AndesCardPadding.fromString("xlarge")

        // THEN
        none assertEquals AndesCardPadding.NONE
        small assertEquals AndesCardPadding.SMALL
        medium assertEquals AndesCardPadding.MEDIUM
        large assertEquals AndesCardPadding.LARGE
        xlarge assertEquals AndesCardPadding.XLARGE
    }

    @Test
    fun `AndesCardPadding fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesCardPadding.fromString("x_large")
        }
    }

    @Test
    fun `AndesCardType fromString parse valid`() {
        // WHEN
        val none = AndesCardType.fromString("none")
        val highlight = AndesCardType.fromString("highlight")
        val error = AndesCardType.fromString("error")
        val success = AndesCardType.fromString("success")
        val warning = AndesCardType.fromString("warning")

        // THEN
        none assertEquals AndesCardType.NONE
        highlight assertEquals AndesCardType.HIGHLIGHT
        error assertEquals AndesCardType.ERROR
        success assertEquals AndesCardType.SUCCESS
        warning assertEquals AndesCardType.WARNING
    }

    @Test
    fun `AndesCardType fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesCardType.fromString("HIGH_LIGHT")
        }
    }
}
