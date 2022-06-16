package com.mercadolibre.android.andesui.utils

import android.content.Context
import android.widget.EditText
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class EditTextUtilsTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `When EditText editable is null, return 0 as default`() {
        // GIVEN
        val et = spy(EditText(context))

        `when`(et.text).thenReturn(null)
        // WHEN - THEN
        et.inputLength() assertEquals 0
    }

    @Test
    fun `When EditText editable is null, return given default value`() {
        // GIVEN
        val et = spy(EditText(context))
        `when`(et.text).thenReturn(null)

        // WHEN - THEN
        et.inputLength(-1) assertEquals -1
    }

    @Test
    fun `When EditText has no text, return 0`() {
        // GIVEN
        val et = EditText(context)

        // WHEN - THEN
        et.inputLength() assertEquals 0
    }

    @Test
    fun `When EditText has no text, return text length`() {
        // GIVEN
        val et = EditText(context)
        et.setText("1234")

        // WHEN - THEN
        et.inputLength() assertEquals 4
    }
}
