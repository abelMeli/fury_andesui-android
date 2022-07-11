package com.mercadolibre.android.andesui.tag

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.assertIsNull
import com.mercadolibre.android.andesui.databinding.AndesLayoutSimpleTagBinding
import com.mercadolibre.android.andesui.shadows.AndesShadowGradientDrawable
import com.mercadolibre.android.andesui.tag.leftcontent.LeftContent
import com.mercadolibre.android.andesui.tag.leftcontent.LeftContentDot
import com.mercadolibre.android.andesui.tag.size.AndesTagSize
import com.mercadolibre.android.andesui.tag.type.AndesTagType
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadow.api.Shadow.extract
import org.robolectric.shadows.ShadowPorterDuffColorFilter

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesTagSimpleTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun `Default TagSimple creation`() {
        // WHEN
        val tagSimple = AndesTagSimple(context)

        // THEN
        val binding = AndesLayoutSimpleTagBinding.bind(tagSimple)
        with(tagSimple) {
            binding.andesTagContainer().visibility assertEquals View.GONE
            type assertEquals AndesTagType.NEUTRAL
            size assertEquals AndesTagSize.LARGE
            text assertIsNull true
            binding.simpleTagText.text.toString() assertEquals ""
            isDismissable assertEquals false
            binding.rightContent.childCount assertEquals 0
            leftContent assertIsNull true
            binding.leftContent.childCount assertEquals 0
        }
    }

    @Test
    fun `TagSimple creation with text`() {
        // GIVEN
        val tagText = "Test"

        // WHEN
        val tagSimple = AndesTagSimple(context = context, text = tagText)

        // THEN
        val binding = AndesLayoutSimpleTagBinding.bind(tagSimple)
        with(tagSimple) {
            binding.andesTagContainer().visibility assertEquals View.VISIBLE
            type assertEquals AndesTagType.NEUTRAL
            size assertEquals AndesTagSize.LARGE
            text assertEquals tagText
            binding.simpleTagText.text.toString() assertEquals tagText
            isDismissable assertEquals false
            binding.rightContent.childCount assertEquals 0
            leftContent assertIsNull true
            binding.leftContent.childCount assertEquals 0
        }
    }

    @Test
    fun `TagSimple creation with text, left content`() {
        // GIVEN
        val tagText = "Test"
        val dotColor = "#FFEC2B"
        val tagLeftContent = LeftContent(dot = LeftContentDot(dotColor))

        // WHEN
        val tagSimple = AndesTagSimple(context = context, text = tagText)
        tagSimple.leftContent = tagLeftContent

        // THEN
        val binding = AndesLayoutSimpleTagBinding.bind(tagSimple)
        with(tagSimple) {
            binding.andesTagContainer().visibility assertEquals View.VISIBLE
            type assertEquals AndesTagType.NEUTRAL
            size assertEquals AndesTagSize.LARGE
            text assertEquals tagText
            binding.simpleTagText.text.toString() assertEquals tagText
            isDismissable assertEquals false
            binding.rightContent.childCount assertEquals 0
            leftContent assertEquals tagLeftContent
            binding.leftContent.childCount assertEquals 1
        }
    }

    @Test
    fun `TagSimple creation with text, dismissable`() {
        // GIVEN
        val tagText = "Test"
        val mockDismissCallback = mock(View.OnClickListener::class.java)

        // WHEN
        val tagSimple = AndesTagSimple(context = context, text = tagText)
        tagSimple.isDismissable = true
        tagSimple.setupDismsissableCallback(mockDismissCallback)

        // THEN
        val binding = AndesLayoutSimpleTagBinding.bind(tagSimple)
        with(tagSimple) {
            binding.andesTagContainer().visibility assertEquals View.VISIBLE
            type assertEquals AndesTagType.NEUTRAL
            size assertEquals AndesTagSize.LARGE
            text assertEquals tagText
            binding.simpleTagText.text.toString() assertEquals tagText
            isDismissable assertEquals true
            binding.rightContent.childCount assertEquals 1
            leftContent assertIsNull true
            binding.leftContent.childCount assertEquals 0
        }
    }

    @Test
    fun `TagSimple dismiss callback called`() {
        // GIVEN
        val tagText = "Test"
        val mockDismissCallback = mock(View.OnClickListener::class.java)
        val tagSimple = AndesTagSimple(context = context, text = tagText)
        tagSimple.isDismissable = true
        tagSimple.setupDismsissableCallback(mockDismissCallback)

        // WHEN
        tagSimple.dismiss()

        // THEN
        val binding = AndesLayoutSimpleTagBinding.bind(tagSimple)
        with(tagSimple) {
            binding.andesTagContainer().visibility assertEquals View.GONE
            isDismissable assertEquals true
            binding.rightContent.childCount assertEquals 1
            verify(mockDismissCallback).onClick(any())
        }
    }

    @Test
    @Config(shadows = [AndesShadowGradientDrawable::class])
    fun `TagSimple type change colors`() {
        // GIVEN
        val tagText = "Test"
        val tagSimple = AndesTagSimple(context = context, text = tagText, type = AndesTagType.NEUTRAL)
        tagSimple.isDismissable = true

        // WHEN
        val newType = AndesTagType.HIGHLIGHT
        tagSimple.type = newType

        // THEN
        val binding = AndesLayoutSimpleTagBinding.bind(tagSimple)
        with(newType.type) {
            val tagBackground: AndesShadowGradientDrawable = extract(tagSimple.background as GradientDrawable)
            tagBackground.lastSetColor assertEquals backgroundColor().colorInt(context)
            tagBackground.lastSetStrokeColor assertEquals borderColor().colorInt(context)

            binding.simpleTagText.textColors.defaultColor assertEquals textColor().colorInt(context)

            val colorFilter: ShadowPorterDuffColorFilter =
                extract(((binding.rightContent.getChildAt(0) as ImageView).drawable as BitmapDrawable).colorFilter)
            colorFilter.color assertEquals dismissColor().colorInt(context)
        }
    }

    /**
     * Workaround made to check view visibility. For some reason binding returns always View.VISIBLE.
     */
    private fun AndesLayoutSimpleTagBinding.andesTagContainer(): ConstraintLayout =
        root.findViewById(R.id.andes_tag_container)

    private fun AndesTagSimple.dismiss() {
        val binding = AndesLayoutSimpleTagBinding.bind(this)
        binding.rightContent.getChildAt(0).performClick()
    }
}
