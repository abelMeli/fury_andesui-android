package com.mercadolibre.android.andesui.textfield

import android.os.Build
import com.facebook.soloader.SoLoader
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesEditTextTest {

    private var context = RuntimeEnvironment.application
    private lateinit var andesEditText: AndesEditText
    @Mock private lateinit var textContextMenuItemListener: TextContextMenuItemListener

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            SoLoader.setInTestMode()
        }
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        andesEditText = AndesEditText(context)
    }

    @Test
    fun `when paste text`() {
        andesEditText.setTextContextMenuItemListener(textContextMenuItemListener)

        `when`(textContextMenuItemListener.onPaste()).thenReturn(true)
        val expected = true
        val actual = andesEditText.onTextContextMenuItem(android.R.id.paste)

        assertEquals(expected, actual)
        verify(textContextMenuItemListener).onPaste()
    }

    @Test
    fun `when copy text`() {
        andesEditText.setTextContextMenuItemListener(textContextMenuItemListener)
        `when`(textContextMenuItemListener.onCopy()).thenReturn(true)
        val expected = true
        val actual = andesEditText.onTextContextMenuItem(android.R.id.copy)

        assertEquals(expected, actual)
        verify(textContextMenuItemListener).onCopy()
    }

    @Test
    fun `when cut text`() {
        andesEditText.setTextContextMenuItemListener(textContextMenuItemListener)
        `when`(textContextMenuItemListener.onCut()).thenReturn(true)
        val expected = true
        val actual = andesEditText.onTextContextMenuItem(android.R.id.cut)

        assertEquals(expected, actual)
        verify(textContextMenuItemListener).onCut()
    }

    @Test
    fun `when OnTextContextMenuItemListener not implemented`() {
        val mock = mock<TextContextMenuItemListener>()
        andesEditText.setTextContextMenuItemListener(mock)
        var actual = andesEditText.onTextContextMenuItem(android.R.id.paste)

        assertEquals(true, actual)
        actual = verify(mock).onPaste()
        assertEquals(false, actual)

        actual = andesEditText.onTextContextMenuItem(android.R.id.copy)

        assertEquals(true, actual)
        actual = verify(mock).onCopy()
        assertEquals(false, actual)

        actual = andesEditText.onTextContextMenuItem(android.R.id.cut)

        assertEquals(true, actual)
        actual = verify(mock).onCut()
        assertEquals(false, actual)
    }

    @Test
    fun `when setTextContextMenuItemListener not performed then use default behaviour`() {
        val copyPerformed = andesEditText.onTextContextMenuItem(android.R.id.copy)
        assertTrue(copyPerformed)

        val pastePerformed = andesEditText.onTextContextMenuItem(android.R.id.paste)
        assertTrue(pastePerformed)

        val cutPerformed  = andesEditText.onTextContextMenuItem(android.R.id.cut)
        assertTrue(cutPerformed)
    }
}
