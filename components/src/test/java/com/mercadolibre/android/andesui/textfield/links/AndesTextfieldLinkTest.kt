package com.mercadolibre.android.andesui.textfield.links

import android.os.Build
import android.text.SpannableString
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class AndesTextfieldLinkTest {

    @Test
    fun `given a start index lower than 0 when ask if is valid range then returns false`() {

        // GIVEN
        val link = AndesTextfieldLink(startIndex = -1, endIndex = 0)

        // WHEN
        val isValid = link.isValidRange(SpannableString("anything"))

        // THEN
        assertThat(isValid, `is`(false))
    }

    @Test
    fun `given an end index lower than 0 when ask if is valid range then returns false`() {

        // GIVEN
        val link = AndesTextfieldLink(startIndex = 0, endIndex = -1)

        // WHEN
        val isValid = link.isValidRange(SpannableString("anything"))

        // THEN
        assertThat(isValid, `is`(false))
    }

    @Test
    fun `given an end index lower than start index when ask if is valid range then returns false`() {

        // GIVEN
        val link = AndesTextfieldLink(startIndex = 10, endIndex = 5)

        // WHEN
        val isValid = link.isValidRange(SpannableString("anything"))

        // THEN
        assertThat(isValid, `is`(false))
    }

    @Test
    fun `given an end index greater than text length when ask if is valid range then returns false`() {

        // GIVEN
        val link = AndesTextfieldLink(startIndex = 4, endIndex = 50)

        // WHEN
        val isValid = link.isValidRange(SpannableString("anything"))

        // THEN
        assertThat(isValid, `is`(false))
    }

    @Test
    fun `given indexes in text length when ask if is valid range then returns true`() {

        // GIVEN
        val link = AndesTextfieldLink(startIndex = 4, endIndex = 6)

        // WHEN
        val isValid = link.isValidRange(SpannableString("anything"))

        // THEN
        assertThat(isValid, `is`(true))
    }
}
