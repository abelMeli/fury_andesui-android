package com.mercadolibre.android.andesui.tag

import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.tag.choice.state.AndesTagChoiceState
import com.mercadolibre.android.andesui.tag.leftcontent.AndesTagLeftContent
import com.mercadolibre.android.andesui.tag.rightcontent.AndesTagRightContent
import com.mercadolibre.android.andesui.tag.size.AndesTagSize
import com.mercadolibre.android.andesui.tag.type.AndesTagType
import java.lang.IllegalArgumentException
import org.junit.Assert
import org.junit.Test

class AndesTagEnumsTest {

    @Test
    fun `AndesTagRightContent fromString parse valid`() {
        // WHEN
        val check = AndesTagRightContent.fromString("Check")
        val none = AndesTagRightContent.fromString("none")
        val dropdown = AndesTagRightContent.fromString("dropdown")
        val dismiss = AndesTagRightContent.fromString("dismiss")

        // THEN
        check assertEquals AndesTagRightContent.CHECK
        none assertEquals AndesTagRightContent.NONE
        dropdown assertEquals AndesTagRightContent.DROPDOWN
        dismiss assertEquals AndesTagRightContent.DISMISS
    }

    @Test
    fun `AndesTagRightContent fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesTagRightContent.fromString("Ch_eck")
        }
    }

    @Test
    fun `AndesTagSize fromString parse valid`() {
        // WHEN
        val small = AndesTagSize.fromString("small")
        val large = AndesTagSize.fromString("large")

        // THEN
        small assertEquals AndesTagSize.SMALL
        large assertEquals AndesTagSize.LARGE
    }

    @Test
    fun `AndesTagSize fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesTagSize.fromString("medium")
        }
    }

    @Test
    fun `AndesTagLeftContent fromString parse valid`() {
        // WHEN
        val dot = AndesTagLeftContent.fromString("dot")
        val icon = AndesTagLeftContent.fromString("icon")
        val image = AndesTagLeftContent.fromString("image")
        val none = AndesTagLeftContent.fromString("none")

        // THEN
        dot assertEquals AndesTagLeftContent.DOT
        icon assertEquals AndesTagLeftContent.ICON
        image assertEquals AndesTagLeftContent.IMAGE
        none assertEquals AndesTagLeftContent.NONE
    }

    @Test
    fun `AndesTagLeftContent fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesTagLeftContent.fromString("other")
        }
    }

    @Test
    fun `AndesTagType fromString parse valid`() {
        // WHEN
        val neutral = AndesTagType.fromString("neutral")
        val highlight = AndesTagType.fromString("highlight")
        val success = AndesTagType.fromString("success")
        val warning = AndesTagType.fromString("warning")
        val error = AndesTagType.fromString("error")

        // THEN
        neutral assertEquals AndesTagType.NEUTRAL
        highlight assertEquals AndesTagType.HIGHLIGHT
        success assertEquals AndesTagType.SUCCESS
        warning assertEquals AndesTagType.WARNING
        error assertEquals AndesTagType.ERROR
    }

    @Test
    fun `AndesTagType fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesTagType.fromString("red")
        }
    }

    @Test
    fun `AndesTagChoiceState fromString parse valid`() {
        // WHEN
        val idle = AndesTagChoiceState.fromString("idle")
        val selected = AndesTagChoiceState.fromString("selected")

        // THEN
        idle assertEquals AndesTagChoiceState.IDLE
        selected assertEquals AndesTagChoiceState.SELECTED
    }

    @Test
    fun `AndesTagChoiceState fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesTagChoiceState.fromString("iddle")
        }
    }
}
