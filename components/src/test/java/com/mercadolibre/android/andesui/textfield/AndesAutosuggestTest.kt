package com.mercadolibre.android.andesui.textfield

import android.os.Build
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import androidx.test.core.app.ApplicationProvider
import com.facebook.drawee.backends.pipeline.Fresco
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu
import com.mercadolibre.android.andesui.utils.getPrivateField
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.AndesListViewItem
import com.mercadolibre.android.andesui.list.AndesListViewItemSimple
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.utils.setPrivateField
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldRightContent
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class AndesAutosuggestTest {

    private lateinit var autosuggest: AndesAutosuggest
    private lateinit var textfield: AndesTextfield
    private lateinit var floatingMenu: AndesFloatingMenu

    @Before
    fun setup() {
        Fresco.initialize(ApplicationProvider.getApplicationContext())
        MockitoAnnotations.initMocks(this)
        autosuggest = AndesAutosuggest(ApplicationProvider.getApplicationContext(), attrs = null)
        textfield = spy(autosuggest.getPrivateField<AndesTextfield>("textfield"))
        autosuggest.setPrivateField("textfield", textfield)
        floatingMenu = spy(autosuggest.getPrivateField<AndesFloatingMenu>("andesFloatingMenu"))
        autosuggest.setPrivateField("andesFloatingMenu", floatingMenu)
    }

    @Test
    fun `given properties values when construct with those then text field is constructed with the given properties`() {

        // GIVEN
        val label = "some label"
        val helper = "some helper"
        val placeholder = "some placeholder"
        val state = AndesTextfieldState.ERROR

        // WHEN
        autosuggest = AndesAutosuggest(
            context = ApplicationProvider.getApplicationContext(),
            label = label,
            helper = helper,
            placeholder = placeholder,
            state = state
        )

        // THEN
        assertThat(autosuggest.label, `is`(label))
        assertThat(autosuggest.helper, `is`(helper))
        assertThat(autosuggest.placeholder, `is`(placeholder))
        assertThat(autosuggest.state, `is`(state))
    }

    @Test
    fun `given text when set text then it's set to text field`() {

        // GIVEN
        val text = "text"

        // WHEN
        autosuggest.text = text

        // THEN
        assertThat(textfield.text, `is`(text))
        assertThat(autosuggest.text, `is`(text))
    }

    @Test
    fun `given label when set label then it's set to text field`() {

        // GIVEN
        val label = "label"

        // WHEN
        autosuggest.label = label

        // THEN
        assertThat(textfield.label, `is`(label))
        assertThat(autosuggest.label, `is`(label))
    }

    @Test
    fun `given helper when set helper then it's set to text field`() {

        // GIVEN
        val helper = "helper"

        // WHEN
        autosuggest.helper = helper

        // THEN
        assertThat(textfield.helper, `is`(helper))
        assertThat(autosuggest.helper, `is`(helper))
    }

    @Test
    fun `given placeholder when set placeholder then it's set to text field`() {

        // GIVEN
        val placeholder = "placeholder"

        // WHEN
        autosuggest.placeholder = placeholder

        // THEN
        assertThat(textfield.placeholder, `is`(placeholder))
        assertThat(autosuggest.placeholder, `is`(placeholder))
    }

    @Test
    fun `given state when set state then it's set to text field`() {

        // GIVEN
        val state = AndesTextfieldState.DISABLED

        // WHEN
        autosuggest.state = state

        // THEN
        assertThat(textfield.state, `is`(state))
        assertThat(autosuggest.state, `is`(state))
    }

    @Test
    fun `given textFilter when set textFilter then it's set to text field`() {

        // GIVEN
        val textFilter = mock<InputFilter>()

        // WHEN
        autosuggest.textFilter = textFilter

        // THEN
        assertThat(textfield.textFilter, `is`(textFilter))
        assertThat(autosuggest.textFilter, `is`(textFilter))
    }

    @Test
    fun `given inputType when set inputType then it's set to text field`() {

        // GIVEN
        val inputType = InputType.TYPE_CLASS_NUMBER

        // WHEN
        autosuggest.inputType = inputType

        // THEN
        assertThat(textfield.inputType, `is`(inputType))
        assertThat(autosuggest.inputType, `is`(inputType))
    }

    @Test
    fun `given rightContent when set rightContent then it's set to text field`() {

        // GIVEN
        val rightContent = AndesTextfieldRightContent.INDETERMINATE

        // WHEN
        autosuggest.rightContent = rightContent

        // THEN
        assertThat(textfield.rightContent, `is`(rightContent))
        assertThat(autosuggest.rightContent, `is`(rightContent))
    }

    @Test
    fun `given textWatcher when set textWatcher then it's set to text field`() {

        // GIVEN
        val textWatcher = mock<TextWatcher>()

        // WHEN
        autosuggest.textWatcher = textWatcher

        // THEN
        assertThat(textfield.textWatcher, `is`(textWatcher))
        assertThat(autosuggest.textWatcher, `is`(textWatcher))
    }

    @Test
    fun `given onTouch when set onTouch then it's set to text field`() {

        // GIVEN
        val onTouch = mock<(MotionEvent) -> Unit>()

        // WHEN
        autosuggest.onTouch = onTouch

        // THEN
        assertThat(textfield.onTouch, `is`(onTouch))
        assertThat(autosuggest.onTouch, `is`(onTouch))
    }

    @Test
    fun `given suggestions delegate when item click then invoke suggestion delegate item click`() {

        // GIVEN
        val suggestionsDelegate = mock<AndesListDelegate>()
        autosuggest.suggestionsDelegate = suggestionsDelegate
        val andesList = mock<AndesList>()
        val position = 12

        // WHEN
        autosuggest.onItemClick(andesList, position)

        // THEN
        assertThat(autosuggest.suggestionsDelegate, `is`(suggestionsDelegate))
        verify(suggestionsDelegate).onItemClick(andesList, position)
    }

    @Test
    fun `given suggestions delegate when bind then invoke suggestion delegate bind`() {

        // GIVEN
        val suggestionsDelegate = mock<AndesListDelegate>()
        autosuggest.suggestionsDelegate = suggestionsDelegate
        val andesList = mock<AndesList>()
        val view = mock<View>()
        val position = 12
        val expectedResult: AndesListViewItem = mock<AndesListViewItemSimple>()
        whenever(suggestionsDelegate.bind(andesList, view, position)).thenReturn(expectedResult)

        // WHEN
        val result = autosuggest.bind(andesList, view, position)

        // THEN
        assertThat(result, `is`(expectedResult))
    }

    @Test
    fun `given suggestions delegate when get data set size then invoke suggestion delegate get data set size`() {

        // GIVEN
        val suggestionsDelegate = mock<AndesListDelegate>()
        autosuggest.suggestionsDelegate = suggestionsDelegate
        val andesList = mock<AndesList>()
        val expectedResult = 12
        whenever(suggestionsDelegate.getDataSetSize(andesList)).thenReturn(expectedResult)

        // WHEN
        val result = autosuggest.getDataSetSize(andesList)

        // THEN
        assertThat(result, `is`(expectedResult))
    }

    @Test
    fun `given not visible floating menu when show suggestions then show floating menu`() {

        // GIVEN
        whenever(floatingMenu.isShowing).thenReturn(false)

        // WHEN
        autosuggest.showSuggestions()

        // THEN
        verify(floatingMenu).show(textfield.textContainer)
    }

    @Test
    fun `given visible floating menu when show suggestions then update floating menu`() {

        // GIVEN
        whenever(floatingMenu.isShowing).thenReturn(true)

        // WHEN
        autosuggest.showSuggestions()

        // THEN
        verify(floatingMenu).update(textfield.textContainer)
    }

    @Test
    fun `when hide suggestions then dismiss floating menu`() {

        // WHEN
        autosuggest.hideSuggestions()

        // THEN
        verify(floatingMenu).dismiss()
    }

    @Test
    fun `given index when set selection then invoke textfield set selection`() {

        // GIVEN
        val index = 0

        // WHEN
        autosuggest.setSelection(index)

        // THEN
        verify(textfield).setSelection(index)
    }

    @Test
    fun `when set indeterminate then invoke textfield set indeterminate`() {

        // WHEN
        autosuggest.setIndeterminate()

        // THEN
        verify(textfield).setIndeterminate()
    }

    @Test
    fun `when set right icon then invoke textfield set right icon`() {

        // GIVEN
        val iconPath = "bla bla"
        val listener = mock<View.OnClickListener>()
        val colorIcon = R.color.andes_accent_color_800
        val hideWhenType = true
        doAnswer { }.whenever(textfield).setRightIcon(iconPath, listener, colorIcon, hideWhenType)

        // WHEN
        autosuggest.setRightIcon(iconPath, listener, colorIcon, hideWhenType)

        // THEN
        verify(textfield).setRightIcon(iconPath, listener, colorIcon, hideWhenType)
    }

    @Test
    fun `when set on show listener then set it to floating menu`() {

        // GIVEN
        val onShowListener = mock<AndesFloatingMenu.OnShowListener>()

        // WHEN
        autosuggest.setOnShowListener(onShowListener)

        // THEN
        verify(floatingMenu).setOnShowListener(onShowListener)
    }

    @Test
    fun `when set on dismiss listener then set it to floating menu`() {

        // GIVEN
        val onDismissListener = mock<AndesFloatingMenu.OnDismissListener>()

        // WHEN
        autosuggest.setOnDismissListener(onDismissListener)

        // THEN
        verify(floatingMenu).setOnDismissListener(onDismissListener)
    }

    @Test
    fun `when set on touch listener then set it to textfield`() {

        // GIVEN
        val onTouchListener = mock<(MotionEvent) -> Unit>()

        // WHEN
        autosuggest.onTouch = onTouchListener

        // THEN
        verify(textfield).onTouch = onTouchListener
    }
}
