package com.mercadolibre.android.andesui.tag

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.assertIsNull
import com.mercadolibre.android.andesui.databinding.AndesLayoutSimpleTagBinding
import com.mercadolibre.android.andesui.tag.choice.AndesTagChoiceCallback
import com.mercadolibre.android.andesui.tag.choice.mode.AndesTagChoiceMode
import com.mercadolibre.android.andesui.tag.choice.state.AndesTagChoiceState
import com.mercadolibre.android.andesui.tag.leftcontent.LeftContent
import com.mercadolibre.android.andesui.tag.leftcontent.LeftContentDot
import com.mercadolibre.android.andesui.tag.size.AndesTagSize
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesTagChoiceTest {
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun `Default TagChoice creation`() {
        // WHEN
        val tagChoice = AndesTagChoice(context)

        // THEN
        val binding = AndesLayoutSimpleTagBinding.bind(tagChoice)
        with(tagChoice) {
            binding.andesTagContainer().visibility assertEquals View.GONE
            mode assertEquals AndesTagChoiceMode.SIMPLE
            size assertEquals AndesTagSize.LARGE
            text assertIsNull true
            binding.simpleTagText.text.toString() assertEquals ""
            state assertEquals AndesTagChoiceState.IDLE
            binding.rightContent.visibility assertEquals View.GONE
            leftContent assertIsNull true
            binding.leftContent.childCount assertEquals 0
        }
    }

    @Test
    fun `TagChoice creation with leftcontent description`() {
        //GIVEN
        val leftcontent = LeftContent(
            dot = null,
            icon = null,
            image = null,
            assetContentDescription = "Test"
        )

        //WHEN
        val tagchoice = AndesTagChoice(context = context, state = AndesTagChoiceState.SELECTED)
        tagchoice.leftContent = leftcontent


        //THEN
        val binding = AndesLayoutSimpleTagBinding.bind(tagchoice)
        with(tagchoice){
            state assertEquals AndesTagChoiceState.SELECTED
            leftContent?.assetContentDescription assertEquals leftcontent.assetContentDescription
        }
    }

    @Test
    fun `TagChoice creation with text`() {
        // GIVEN
        val tagText = "Test"

        // WHEN
        val tagChoice = AndesTagChoice(context = context, text = tagText)

        // THEN
        val binding = AndesLayoutSimpleTagBinding.bind(tagChoice)
        with(tagChoice) {
            binding.andesTagContainer().visibility assertEquals View.VISIBLE
            mode assertEquals AndesTagChoiceMode.SIMPLE
            size assertEquals AndesTagSize.LARGE
            text assertEquals tagText
            binding.simpleTagText.text.toString() assertEquals tagText
            state assertEquals AndesTagChoiceState.IDLE
            binding.rightContent.visibility assertEquals View.GONE
            leftContent assertIsNull true
            binding.leftContent.childCount assertEquals 0
        }
    }

    @Test
    fun `TagChoice creation with text, left content, selected`() {
        // GIVEN
        val tagText = "Test"
        val dotColor = "#FFEC2B"
        val tagLeftContent = LeftContent(dot = LeftContentDot(dotColor))

        // WHEN
        val tagChoice =
            AndesTagChoice(context = context, text = tagText, state = AndesTagChoiceState.SELECTED)
        tagChoice.leftContent = tagLeftContent

        // THEN
        val binding = AndesLayoutSimpleTagBinding.bind(tagChoice)
        with(tagChoice) {
            binding.andesTagContainer().visibility assertEquals View.VISIBLE
            mode assertEquals AndesTagChoiceMode.SIMPLE
            size assertEquals AndesTagSize.LARGE
            text assertEquals tagText
            binding.simpleTagText.text.toString() assertEquals tagText
            state assertEquals AndesTagChoiceState.SELECTED
            binding.rightContent.visibility assertEquals View.VISIBLE
            leftContent assertEquals tagLeftContent
            binding.leftContent.childCount assertEquals 1
        }
    }

    @Test
    fun `TagChoice click callback IDLE to SELECTED`() {
        // GIVEN
        val tagText = "Test"
        val spiedCallback = spy(object : AndesTagChoiceCallback {
            override fun shouldSelectTag(andesTagChoice: AndesTagChoice) = true
        })
        val tagChoice = AndesTagChoice(context = context, text = tagText)
        tagChoice.callback = spiedCallback

        // WHEN
        tagChoice.click()

        // THEN
        val binding = AndesLayoutSimpleTagBinding.bind(tagChoice)
        with(tagChoice) {
            state assertEquals AndesTagChoiceState.SELECTED
            binding.rightContent.visibility assertEquals View.VISIBLE
            verify(spiedCallback).shouldSelectTag(tagChoice)
        }
    }

    @Test
    fun `TagChoice click callback SELECTED to IDLE`() {
        // GIVEN
        val tagText = "Test"
        val spiedCallback = spy(object : AndesTagChoiceCallback {
            override fun shouldSelectTag(andesTagChoice: AndesTagChoice) = true
        })
        val tagChoice =
            AndesTagChoice(context = context, text = tagText, state = AndesTagChoiceState.SELECTED)
        tagChoice.callback = spiedCallback

        // WHEN
        tagChoice.performClick()

        // THEN
        val binding = AndesLayoutSimpleTagBinding.bind(tagChoice)
        with(tagChoice) {
            state assertEquals AndesTagChoiceState.IDLE
            binding.rightContent.visibility assertEquals View.GONE
            verify(spiedCallback).shouldSelectTag(tagChoice)
        }
    }

    /**
     * Workaround made to check view visibility. For some reason binding returns always View.VISIBLE.
     */
    private fun AndesLayoutSimpleTagBinding.andesTagContainer(): ConstraintLayout =
        root.findViewById(R.id.andes_tag_container)

    private fun AndesTagChoice.click() {
        val binding = AndesLayoutSimpleTagBinding.bind(this)
        binding.root.performClick()
    }
}
