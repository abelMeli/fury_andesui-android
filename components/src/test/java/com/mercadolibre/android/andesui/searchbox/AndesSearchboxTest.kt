package com.mercadolibre.android.andesui.searchbox

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.facebook.drawee.backends.pipeline.Fresco
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.assertIsNull
import com.mercadolibre.android.andesui.utils.emulateSearchKeyPressed
import com.mercadolibre.android.andesui.utils.emulateTypingWithKeyboard
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertTrue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesSearchboxTest : AndesSearchbox.OnSearchListener, AndesSearchbox.OnTextChangedListener {

    private lateinit var context: Context
    private lateinit var andesSearchbox: AndesSearchbox
    private var onSearchCalled = false
    private var onTextChangedCalled = false

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        Fresco.initialize(context)
        MockitoAnnotations.initMocks(this)
        andesSearchbox = AndesSearchbox(context)
    }

    @Test
    fun `test searchbox constructor only context parameter`() {

        // GIVEN
        val placeholder = context.resources.getString(R.string.andes_searchbox_placeholder_default)

        // WHEN
        andesSearchbox = AndesSearchbox(
                context = context
        )

        // THEN
        assertThat(andesSearchbox.placeholder, Matchers.`is`(placeholder))
    }

    @Test
    fun `test searchbox constructor with placeholder parameter`() {

        // GIVEN
        val placeholder = "some placeholder"

        // WHEN
        andesSearchbox = AndesSearchbox(
                context = context,
                placeholder = placeholder
        )

        // THEN
        assertThat(andesSearchbox.placeholder, Matchers.`is`(placeholder))
    }

    @Test
    fun `test searchbox constructor with all parameters default from xml`() {

        // GIVEN
        val attrs = Robolectric.buildAttributeSet().build()
        val placeholder = context.resources.getString(R.string.andes_searchbox_placeholder_default)

        // WHEN
        andesSearchbox = AndesSearchbox(
                context = context,
                attrs = attrs
        )

        // THEN
        assertThat(andesSearchbox.placeholder, Matchers.`is`(placeholder))
    }

    @Test
    fun `when set placeholder property`() {

        // GIVEN
        val placeholder = "some placeholder"

        // WHEN
        andesSearchbox.placeholder = placeholder

        // THEN
        assertThat(andesSearchbox.placeholder, Matchers.`is`(placeholder))
    }

    @Test
    fun `when set on text change listener`() {

        // GIVEN
        val onTextChangedListener = mock<AndesSearchbox.OnTextChangedListener>()

        // WHEN
        andesSearchbox.onTextChangedListener = onTextChangedListener

        // THEN
        assertThat(andesSearchbox.onTextChangedListener, Matchers.`is`(onTextChangedListener))
    }

    @Test
    fun `when set on search listener`() {

        // GIVEN
        val onSearchListener = mock<AndesSearchbox.OnSearchListener>()

        // WHEN
        andesSearchbox.onSearchListener = onSearchListener

        // THEN
        assertThat(andesSearchbox.onSearchListener, Matchers.`is`(onSearchListener))
    }

    @Test
    fun `when search listener called`() {

        // GIVEN
        andesSearchbox.onSearchListener = this

        // WHEN
        andesSearchbox.onSearchListener?.onSearch("test text")

        // THEN
        assertTrue(onSearchCalled)
    }

    @Test
    fun `when text listener called`() {

        // GIVEN
        andesSearchbox.onTextChangedListener = this

        // WHEN
        andesSearchbox.onTextChangedListener?.onTextChanged("test text")

        // THEN
        assertTrue(onTextChangedCalled)
    }

    @Test
    fun `given component with textchange callback, when entering text, callback is called correctly`() {

        // GIVEN
        val textChangeCallback = provideTextChangeCallback()
        andesSearchbox.onTextChangedListener = textChangeCallback

        // WHEN
        andesSearchbox.emulateTypingWithKeyboard("123")

        // THEN
        andesSearchbox.onTextChangedListener assertIsNull false
        verify(textChangeCallback, times(3)).onTextChanged(any())
    }

    @Test
    fun `given component with search callback, when pressed action key, callback is called correctly`() {

        // GIVEN
        val searchCallback = provideSearchCallback()
        andesSearchbox.onSearchListener = searchCallback

        // WHEN
        andesSearchbox.emulateSearchKeyPressed()

        // THEN
        andesSearchbox.onSearchListener assertIsNull false
        verify(searchCallback, times(1)).onSearch(any())
    }

    private fun provideTextChangeCallback(): AndesSearchbox.OnTextChangedListener {
        return spy(
                object : AndesSearchbox.OnTextChangedListener {
                    override fun onTextChanged(text: String) {
                        // no-op
                    }
                }
        )
    }

    private fun provideSearchCallback(): AndesSearchbox.OnSearchListener {
        return spy(
                object : AndesSearchbox.OnSearchListener {
                    override fun onSearch(text: String) {
                        // no-op
                    }
                }
        )
    }

    override fun onSearch(text: String) {
        onSearchCalled = true
    }

    override fun onTextChanged(text: String) {
        onTextChangedCalled = true
    }
}