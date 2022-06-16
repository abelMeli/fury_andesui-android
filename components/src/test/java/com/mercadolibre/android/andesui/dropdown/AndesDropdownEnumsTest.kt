package com.mercadolibre.android.andesui.dropdown

import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.dropdown.size.AndesDropdownSize
import com.mercadolibre.android.andesui.dropdown.state.AndesDropdownState
import com.mercadolibre.android.andesui.dropdown.type.AndesDropdownMenuType
import java.lang.IllegalArgumentException
import org.junit.Assert
import org.junit.Test

class AndesDropdownEnumsTest {

    @Test
    fun `AndesDropdownMenuType fromString parse valid`() {
        // WHEN
        val bottomsheet = AndesDropdownMenuType.fromString("bottomsheet")
        val floatingmenu = AndesDropdownMenuType.fromString("floatingmenu")

        // THEN
        bottomsheet assertEquals AndesDropdownMenuType.BOTTOMSHEET
        floatingmenu assertEquals AndesDropdownMenuType.FLOATINGMENU
    }

    @Test
    fun `AndesDropdownMenuType fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesDropdownMenuType.fromString("floating_menu")
        }
    }

    @Test
    fun `AndesDropdownState fromString parse valid`() {
        // WHEN
        val disabled = AndesDropdownState.fromString("disabled")
        val enabled = AndesDropdownState.fromString("enabled")
        val error = AndesDropdownState.fromString("error")

        // THEN
        disabled assertEquals AndesDropdownState.DISABLED
        enabled assertEquals AndesDropdownState.ENABLED
        error assertEquals AndesDropdownState.ERROR
    }

    @Test
    fun `AndesDropdownState fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesDropdownState.fromString("enable")
        }
    }

    @Test
    fun `AndesDropdownSize fromString parse valid`() {
        // WHEN
        val large = AndesDropdownSize.fromString("large")
        val medium = AndesDropdownSize.fromString("medium")
        val small = AndesDropdownSize.fromString("small")

        // THEN
        large assertEquals AndesDropdownSize.LARGE
        medium assertEquals AndesDropdownSize.MEDIUM
        small assertEquals AndesDropdownSize.SMALL
    }

    @Test
    fun `AndesDropdownSize fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesDropdownSize.fromString("extra_small")
        }
    }
}
