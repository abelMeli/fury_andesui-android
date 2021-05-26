package com.mercadolibre.android.andesui.textfield

import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldLeftContent
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldRightContent
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.times
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesTextfieldTest {
    private var context = RuntimeEnvironment.application
    private lateinit var textfield: AndesTextfield

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            SoLoader.setInTestMode()
        }
    }

    @Before
    fun setUp() {
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
            // other setters
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
        textfield = AndesTextfield(context)
    }

    @Test
    fun `set left icon`() {
        textfield.setLeftIcon("andes_navegacion_categorias_24")
        assertEquals(textfield.leftContent, AndesTextfieldLeftContent.ICON)
    }

    @Test
    fun `set right icon`() {
        textfield.setRightIcon("andes_navegacion_categorias_24", hideWhenType = false)
        textfield.text = "1149778767"
        assertEquals(textfield.rightContent, AndesTextfieldRightContent.ICON)
    }

    @Test
    fun `set right icon hide true`() {
        textfield.setRightIcon("andes_navegacion_categorias_24", hideWhenType = true)
                textfield.text = "1149778767"
        assertEquals(textfield.rightContent, AndesTextfieldRightContent.ICON)
    }

    @Test
    fun `set right icon hide true but show`() {
        textfield.setRightIcon(
            "andes_navegacion_categorias_24",
            colorIcon = R.color.andes_accent_color_100,
            hideWhenType = true
        )
        textfield.text = "11"
        assertEquals(textfield.rightContent, AndesTextfieldRightContent.ICON)
    }

    @Test
    fun `set right icon with color listener`() {
        textfield.setRightIcon(
            "andes_navegacion_categorias_24",
            colorIcon = null,
            listener = View.OnClickListener {},
            hideWhenType = true
        )
        textfield.text = "1134"
        assertEquals(textfield.rightContent, AndesTextfieldRightContent.ICON)
    }

    @Test
    fun `textfield with textComponentFocusChangedListener`() {
        val focusListener = View.OnFocusChangeListener { _, _ -> }
        textfield.onFocusChangeListener = focusListener
        assertNotNull(textfield.textComponentFocusChangedListener)
    }

    @Test
    fun `textfield without textComponentFocusChangedListener`() {
        assertNull(textfield.textComponentFocusChangedListener)
    }

    @Test
    fun `set prefix`() {
        textfield.setPrefix("prefix")
        assertEquals(textfield.leftContent, AndesTextfieldLeftContent.PREFIX)
    }

    @Test
    fun `set suffix`() {
        textfield.setSuffix("suffix")
        assertEquals(textfield.rightContent, AndesTextfieldRightContent.SUFFIX)
    }

    @Test
    fun `set action`() {
        textfield.setAction("action", View.OnClickListener { })
        assertEquals(textfield.rightContent, AndesTextfieldRightContent.ACTION)
    }

    @Test
    fun `set checkbox`() {
        textfield.setCheckbox("Checkbox", View.OnClickListener { /* IGNORE */ })
        assertEquals(textfield.rightContent, AndesTextfieldRightContent.CHECKBOX)
    }

    @Test
    fun `set checkbox default checked`() {
        textfield.text = "Random text"
        textfield.setCheckbox("Checkbox", startingState = AndesCheckboxStatus.SELECTED)
        assertEquals(textfield.rightContent, AndesTextfieldRightContent.CHECKBOX)
        assertEquals("", textfield.text)
    }

    @Test
    fun `set indeterminate`() {
        val spyTextfield = spy(textfield)
        doNothing().`when`(spyTextfield).startProgressAnimation(any())
        spyTextfield.setIndeterminate()
        assertEquals(AndesTextfieldRightContent.INDETERMINATE, spyTextfield.rightContent)
    }

    @Test
    fun `set mask`() {
        textfield.setTextFieldMask("###--##,")
        assertEquals(textfield.counter, 5)
    }

    @Test
    fun `get text with mask`() {
        textfield.setTextFieldMask("(##) ####-####")
        textfield.text = "1149778767"
        assertEquals(textfield.text, "(11) 4977-8767")
    }

    @Test
    fun `get text without maskWatcher`() {
        textfield.text = "1149778767"
        assertEquals(textfield.text, "1149778767")
    }

    @Test
    fun `textfield with watcher`() {
        val watcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Empty method
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Empty method
            }

            override fun afterTextChanged(s: Editable) {
                // Empty method
            }
        }
        textfield.textWatcher = watcher
        assertNotNull(textfield.textWatcher)
    }

    @Test
    fun `textfield without watcher`() {
        assertNull(textfield.textWatcher)
    }

    @Test
    fun `clear textWatcher is null when rightContent is not CLEAR`() {
        assertNull(textfield.clearTextWatcher)
    }

    @Test
    fun `removeClearTextWatcher and setupClear are called when resetting rightContent`() {
        val spiedTextfield = spy(textfield)
        assertNull(spiedTextfield.clearTextWatcher)

        spiedTextfield.rightContent = AndesTextfieldRightContent.CLEAR
        assertNotNull(spiedTextfield.clearTextWatcher)

        spiedTextfield.rightContent = AndesTextfieldRightContent.CLEAR
        verify(spiedTextfield, times(2)).removeClearTextWatcher()
    }
}
