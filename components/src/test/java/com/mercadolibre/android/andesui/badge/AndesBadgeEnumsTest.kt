package com.mercadolibre.android.andesui.badge

import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.badge.border.AndesBadgePillBorder
import com.mercadolibre.android.andesui.badge.hierarchy.AndesBadgeIconHierarchy
import com.mercadolibre.android.andesui.badge.hierarchy.AndesBadgePillHierarchy
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType
import java.lang.IllegalArgumentException
import org.junit.Assert
import org.junit.Test

class AndesBadgeEnumsTest {

    @Test
    fun `AndesBadgePillBorder fromString parse valid`() {
        // WHEN
        val corner = AndesBadgePillBorder.fromString("corner")
        val rounded = AndesBadgePillBorder.fromString("rounded")
        val standard = AndesBadgePillBorder.fromString("standard")
        val cornerLeft = AndesBadgePillBorder.fromString("corner_left")

        // THEN
        corner assertEquals AndesBadgePillBorder.CORNER
        rounded assertEquals AndesBadgePillBorder.ROUNDED
        standard assertEquals AndesBadgePillBorder.STANDARD
        cornerLeft assertEquals AndesBadgePillBorder.CORNER_LEFT
    }

    @Test
    fun `AndesBadgePillBorder fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesBadgePillBorder.fromString("estandar")
        }
    }

    @Test
    fun `AndesBadgePillHierarchy fromString parse valid`() {
        // WHEN
        val quiet = AndesBadgePillHierarchy.fromString("quiet")
        val loud = AndesBadgePillHierarchy.fromString("loud")

        // THEN
        quiet assertEquals AndesBadgePillHierarchy.QUIET
        loud assertEquals AndesBadgePillHierarchy.LOUD
    }

    @Test
    fun `AndesBadgePillHierarchy fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesBadgePillHierarchy.fromString("transparent")
        }
    }

    @Test
    fun `AndesBadgePillSize fromString parse valid`() {
        // WHEN
        val large = AndesBadgePillSize.fromString("large")
        val small = AndesBadgePillSize.fromString("small")

        // THEN
        large assertEquals AndesBadgePillSize.LARGE
        small assertEquals AndesBadgePillSize.SMALL
    }

    @Test
    fun `AndesBadgePillSize fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesBadgePillSize.fromString("extra_small")
        }
    }

    @Test
    fun `AndesBadgeType fromString parse valid`() {
        // WHEN
        val neutral = AndesBadgeType.fromString("neutral")
        val highlight = AndesBadgeType.fromString("highlight")
        val success = AndesBadgeType.fromString("success")
        val warning = AndesBadgeType.fromString("warning")
        val error = AndesBadgeType.fromString("error")

        // THEN
        neutral assertEquals AndesBadgeType.NEUTRAL
        highlight assertEquals AndesBadgeType.HIGHLIGHT
        success assertEquals AndesBadgeType.SUCCESS
        warning assertEquals AndesBadgeType.WARNING
        error assertEquals AndesBadgeType.ERROR
    }

    @Test
    fun `AndesBadgeType fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesBadgeType.fromString("high_light")
        }
    }

    @Test
    fun `AndesBadgeIconHierarchy fromString parse valid`() {
        // WHEN
        val loud = AndesBadgeIconHierarchy.fromString("loud")
        val secondary = AndesBadgeIconHierarchy.fromString("secondary")

        // THEN
        loud assertEquals AndesBadgeIconHierarchy.LOUD
        secondary assertEquals AndesBadgeIconHierarchy.SECONDARY
    }

    @Test
    fun `AndesBadgeIconHierarchy fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesBadgeIconHierarchy.fromString("secondari")
        }
    }

    @Test
    fun `AndesBadgeIconType fromString parse valid`() {
        // WHEN
        val highlight = AndesBadgeIconType.fromString("highlight")
        val success = AndesBadgeIconType.fromString("success")
        val warning = AndesBadgeIconType.fromString("warning")
        val error = AndesBadgeIconType.fromString("error")

        // THEN
        highlight assertEquals AndesBadgeIconType.HIGHLIGHT
        success assertEquals AndesBadgeIconType.SUCCESS
        warning assertEquals AndesBadgeIconType.WARNING
        error assertEquals AndesBadgeIconType.ERROR
    }

    @Test
    fun `AndesBadgeIconType fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesBadgeIconType.fromString("high_light")
        }
    }
}
