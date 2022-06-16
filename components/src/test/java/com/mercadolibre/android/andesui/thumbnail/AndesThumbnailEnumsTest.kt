package com.mercadolibre.android.andesui.thumbnail

import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import com.mercadolibre.android.andesui.thumbnail.state.AndesThumbnailState
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailType
import java.lang.IllegalArgumentException
import org.junit.Assert
import org.junit.Test

class AndesThumbnailEnumsTest {

    @Test
    fun `AndesThumbnailHierarchy fromString parse valid`() {
        // WHEN
        val default = AndesThumbnailHierarchy.fromString("default")
        val loud = AndesThumbnailHierarchy.fromString("loud")
        val quiet = AndesThumbnailHierarchy.fromString("quiet")

        // THEN
        default assertEquals AndesThumbnailHierarchy.DEFAULT
        loud assertEquals AndesThumbnailHierarchy.LOUD
        quiet assertEquals AndesThumbnailHierarchy.QUIET
    }

    @Test
    fun `AndesThumbnailHierarchy fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesThumbnailHierarchy.fromString("qiet")
        }
    }

    @Test
    fun `AndesThumbnailState fromString parse valid`() {
        // WHEN
        val enabled = AndesThumbnailState.fromString("enabled")
        val disabled = AndesThumbnailState.fromString("disabled")

        // THEN
        enabled assertEquals AndesThumbnailState.ENABLED
        disabled assertEquals AndesThumbnailState.DISABLED
    }

    @Test
    fun `AndesThumbnailState fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesThumbnailState.fromString("enable")
        }
    }

    @Test
    fun `AndesThumbnailType fromString parse valid`() {
        // WHEN
        val icon = AndesThumbnailType.fromString("icon")
        val circle = AndesThumbnailType.fromString("image_circle")
        val square = AndesThumbnailType.fromString("image_square")

        // THEN
        icon assertEquals AndesThumbnailType.ICON
        circle assertEquals AndesThumbnailType.IMAGE_CIRCLE
        square assertEquals AndesThumbnailType.IMAGE_SQUARE
    }

    @Test
    fun `AndesThumbnailType fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesThumbnailType.fromString("imagesquare")
        }
    }

    @Test
    fun `AndesThumbnailSize fromString parse valid`() {
        // WHEN
        val size24 = AndesThumbnailSize.fromString("size_24")
        val size32 = AndesThumbnailSize.fromString("size_32")
        val size40 = AndesThumbnailSize.fromString("size_40")
        val size48 = AndesThumbnailSize.fromString("size_48")
        val size56 = AndesThumbnailSize.fromString("size_56")
        val size64 = AndesThumbnailSize.fromString("size_64")
        val size72 = AndesThumbnailSize.fromString("size_72")
        val size80 = AndesThumbnailSize.fromString("size_80")

        size24 assertEquals AndesThumbnailSize.SIZE_24
        size32 assertEquals AndesThumbnailSize.SIZE_32
        size40 assertEquals AndesThumbnailSize.SIZE_40
        size48 assertEquals AndesThumbnailSize.SIZE_48
        size56 assertEquals AndesThumbnailSize.SIZE_56
        size64 assertEquals AndesThumbnailSize.SIZE_64
        size72 assertEquals AndesThumbnailSize.SIZE_72
        size80 assertEquals AndesThumbnailSize.SIZE_80
    }

    @Test
    fun `AndesThumbnailSize fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesThumbnailSize.fromString("small")
        }
    }
}
