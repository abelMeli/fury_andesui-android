package com.mercadolibre.android.andesui.amountfield.utils

import android.content.Context
import android.widget.EditText
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.amountfield.AndesAmountFieldSimple
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.assertIsNull
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.MockConfigProvider
import com.mercadolibre.android.andesui.utils.emulateTypingWithKeyboard
import com.mercadolibre.android.andesui.utils.getInternalEditTextComponent
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.any
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.times
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesAmountFieldEditTextTest {

    private lateinit var context: Context
    private lateinit var amountField: AndesAmountFieldSimple
    private lateinit var internalEditText: AndesAmountFieldEditText

    init {
        MockConfigProvider.configure()
    }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        amountField = AndesAmountFieldSimple(context)
    }

    @Test
    fun `Given amountField created by code, when getting paste listener from internal edittext, then the value is not null`() {
        internalEditText = amountField.getInternalEditTextComponent()

        val internalPasteListener = internalEditText.onTextPasteCallback

        internalPasteListener assertIsNull false
    }

    @Test
    fun `Given internal edit text, when selecting item from context menu different than paste, then component behaves correctly`() {
        internalEditText = amountField.getInternalEditTextComponent()
        val spiedListener = spy(internalEditText.onTextPasteCallback)
        val copyAction = android.R.id.copy

        internalEditText.onTextContextMenuItem(copyAction)

        verify(spiedListener, times(0))?.onTextPaste(any())
    }

    @Test
    fun `Given internal edit text, when changing cursor selection, then cursor position is correct`() {
        amountField.emulateTypingWithKeyboard("123123")
        internalEditText = amountField.getInternalEditTextComponent()

        internalEditText.setSelection(3)

        internalEditText.isCursorAtTheEndOfTheField() assertEquals true
    }

    @Test
    fun `Given internal edit text, when changing focus to true, then cursor position is correct`() {
        internalEditText = amountField.getInternalEditTextComponent()

        internalEditText.requestFocus()

        internalEditText.isCursorAtTheEndOfTheField() assertEquals true
    }
}

private fun EditText.isCursorAtTheEndOfTheField(): Boolean {
    return (selectionStart == selectionEnd) && selectionEnd == text.length
}
