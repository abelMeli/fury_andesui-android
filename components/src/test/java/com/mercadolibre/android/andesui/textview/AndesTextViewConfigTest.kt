package com.mercadolibre.android.andesui.textview

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.SpannableString
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.color.toColor
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.andesui.textview.factory.AndesTextViewAttrs
import com.mercadolibre.android.andesui.textview.factory.AndesTextViewConfigurationFactory
import com.mercadolibre.android.andesui.textview.style.AndesTextViewStyle
import com.mercadolibre.android.andesui.typeface.getFontOrDefault
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesTextViewConfigTest {

    private lateinit var context: Context
    private val configFactory = AndesTextViewConfigurationFactory
    private lateinit var attrs: AndesTextViewAttrs

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `color primary, style title_xl`() {
        attrs = AndesTextViewAttrs(
            AndesTextViewColor.Primary,
            AndesTextViewStyle.TitleXl
        )

        val config = configFactory.create(context, attrs, "")

        config.color assertEquals R.color.andes_text_color_primary.toColor(context)
        config.lineHeight assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_title_line_height_xl)
        config.spannedText assertEquals SpannableString("")
        config.textFont assertEquals context.getFontOrDefault(R.font.andes_font_semibold)
        config.textSize assertEquals context.resources.getDimension(R.dimen.andes_title_font_size_xl)
        config.textStyle assertEquals Typeface.NORMAL
        config.textWeight assertEquals R.dimen.andes_title_font_weight
    }

    @Test
    fun `color secondary, style title_xl`() {
        attrs = AndesTextViewAttrs(
            AndesTextViewColor.Secondary,
            AndesTextViewStyle.TitleXl
        )

        val config = configFactory.create(context, attrs, "")

        config.color assertEquals R.color.andes_text_color_secondary.toColor(context)
        config.lineHeight assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_title_line_height_xl)
        config.spannedText assertEquals SpannableString("")
        config.textFont assertEquals context.getFontOrDefault(R.font.andes_font_semibold)
        config.textSize assertEquals context.resources.getDimension(R.dimen.andes_title_font_size_xl)
        config.textStyle assertEquals Typeface.NORMAL
        config.textWeight assertEquals R.dimen.andes_title_font_weight
    }

    @Test
    fun `color disabled, style title_xl`() {
        attrs = AndesTextViewAttrs(
            AndesTextViewColor.Disabled,
            AndesTextViewStyle.TitleXl
        )

        val config = configFactory.create(context, attrs, "")

        config.color assertEquals R.color.andes_text_color_disabled.toColor(context)
        config.lineHeight assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_title_line_height_xl)
        config.spannedText assertEquals SpannableString("")
        config.textFont assertEquals context.getFontOrDefault(R.font.andes_font_semibold)
        config.textSize assertEquals context.resources.getDimension(R.dimen.andes_title_font_size_xl)
        config.textStyle assertEquals Typeface.NORMAL
        config.textWeight assertEquals R.dimen.andes_title_font_weight
    }

    @Test
    fun `color inverted, style title_xl`() {
        attrs = AndesTextViewAttrs(
            AndesTextViewColor.Inverted,
            AndesTextViewStyle.TitleXl
        )

        val config = configFactory.create(context, attrs, "")

        config.color assertEquals R.color.andes_text_color_white.toColor(context)
        config.lineHeight assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_title_line_height_xl)
        config.spannedText assertEquals SpannableString("")
        config.textFont assertEquals context.getFontOrDefault(R.font.andes_font_semibold)
        config.textSize assertEquals context.resources.getDimension(R.dimen.andes_title_font_size_xl)
        config.textStyle assertEquals Typeface.NORMAL
        config.textWeight assertEquals R.dimen.andes_title_font_weight
    }

    @Test
    fun `color negative, style title_xl`() {
        attrs = AndesTextViewAttrs(
            AndesTextViewColor.Negative,
            AndesTextViewStyle.TitleXl
        )

        val config = configFactory.create(context, attrs, "")

        config.color assertEquals R.color.andes_text_color_negative.toColor(context)
        config.lineHeight assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_title_line_height_xl)
        config.spannedText assertEquals SpannableString("")
        config.textFont assertEquals context.getFontOrDefault(R.font.andes_font_semibold)
        config.textSize assertEquals context.resources.getDimension(R.dimen.andes_title_font_size_xl)
        config.textStyle assertEquals Typeface.NORMAL
        config.textWeight assertEquals R.dimen.andes_title_font_weight
    }

    @Test
    fun `color caution, style title_xl`() {
        attrs = AndesTextViewAttrs(
            AndesTextViewColor.Caution,
            AndesTextViewStyle.TitleXl
        )

        val config = configFactory.create(context, attrs, "")

        config.color assertEquals R.color.andes_text_color_caution.toColor(context)
        config.lineHeight assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_title_line_height_xl)
        config.spannedText assertEquals SpannableString("")
        config.textFont assertEquals context.getFontOrDefault(R.font.andes_font_semibold)
        config.textSize assertEquals context.resources.getDimension(R.dimen.andes_title_font_size_xl)
        config.textStyle assertEquals Typeface.NORMAL
        config.textWeight assertEquals R.dimen.andes_title_font_weight
    }

    @Test
    fun `color positive, style title_xl`() {
        attrs = AndesTextViewAttrs(
            AndesTextViewColor.Positive,
            AndesTextViewStyle.TitleXl
        )

        val config = configFactory.create(context, attrs, "")

        config.color assertEquals R.color.andes_text_color_positive.toColor(context)
        config.lineHeight assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_title_line_height_xl)
        config.spannedText assertEquals SpannableString("")
        config.textFont assertEquals context.getFontOrDefault(R.font.andes_font_semibold)
        config.textSize assertEquals context.resources.getDimension(R.dimen.andes_title_font_size_xl)
        config.textStyle assertEquals Typeface.NORMAL
        config.textWeight assertEquals R.dimen.andes_title_font_weight
    }

    @Test
    fun `color primary, style title_l`() {
        attrs = AndesTextViewAttrs(
            AndesTextViewColor.Primary,
            AndesTextViewStyle.TitleL
        )

        val config = configFactory.create(context, attrs, "")

        config.color assertEquals R.color.andes_text_color_primary.toColor(context)
        config.lineHeight assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_title_line_height_l)
        config.spannedText assertEquals SpannableString("")
        config.textFont assertEquals context.getFontOrDefault(R.font.andes_font_semibold)
        config.textSize assertEquals context.resources.getDimension(R.dimen.andes_title_font_size_l)
        config.textStyle assertEquals Typeface.NORMAL
        config.textWeight assertEquals R.dimen.andes_title_font_weight
    }

    @Test
    fun `color primary, style title_m`() {
        attrs = AndesTextViewAttrs(
            AndesTextViewColor.Primary,
            AndesTextViewStyle.TitleM
        )

        val config = configFactory.create(context, attrs, "")

        config.color assertEquals R.color.andes_text_color_primary.toColor(context)
        config.lineHeight assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_title_line_height_m)
        config.spannedText assertEquals SpannableString("")
        config.textFont assertEquals context.getFontOrDefault(R.font.andes_font_semibold)
        config.textSize assertEquals context.resources.getDimension(R.dimen.andes_title_font_size_m)
        config.textStyle assertEquals Typeface.NORMAL
        config.textWeight assertEquals R.dimen.andes_title_font_weight
    }

    @Test
    fun `color primary, style title_s`() {
        attrs = AndesTextViewAttrs(
            AndesTextViewColor.Primary,
            AndesTextViewStyle.TitleS
        )

        val config = configFactory.create(context, attrs, "")

        config.color assertEquals R.color.andes_text_color_primary.toColor(context)
        config.lineHeight assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_title_line_height_s)
        config.spannedText assertEquals SpannableString("")
        config.textFont assertEquals context.getFontOrDefault(R.font.andes_font_semibold)
        config.textSize assertEquals context.resources.getDimension(R.dimen.andes_title_font_size_s)
        config.textStyle assertEquals Typeface.NORMAL
        config.textWeight assertEquals R.dimen.andes_title_font_weight
    }

    @Test
    fun `color primary, style title_xs`() {
        attrs = AndesTextViewAttrs(
            AndesTextViewColor.Primary,
            AndesTextViewStyle.TitleXs
        )

        val config = configFactory.create(context, attrs, "")

        config.color assertEquals R.color.andes_text_color_primary.toColor(context)
        config.lineHeight assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_title_line_height_xs)
        config.spannedText assertEquals SpannableString("")
        config.textFont assertEquals context.getFontOrDefault(R.font.andes_font_semibold)
        config.textSize assertEquals context.resources.getDimension(R.dimen.andes_title_font_size_xs)
        config.textStyle assertEquals Typeface.NORMAL
        config.textWeight assertEquals R.dimen.andes_title_font_weight
    }

    @Test
    fun `color primary, style body_l`() {
        attrs = AndesTextViewAttrs(
            AndesTextViewColor.Primary,
            AndesTextViewStyle.BodyL
        )

        val config = configFactory.create(context, attrs, "")

        config.color assertEquals R.color.andes_text_color_primary.toColor(context)
        config.lineHeight assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_body_line_height_l)
        config.spannedText assertEquals SpannableString("")
        config.textFont assertEquals context.getFontOrDefault(R.font.andes_font_regular)
        config.textSize assertEquals context.resources.getDimension(R.dimen.andes_body_font_size_l)
        config.textStyle assertEquals Typeface.NORMAL
        config.textWeight assertEquals R.dimen.andes_body_font_weight
    }

    @Test
    fun `color primary, style body_m`() {
        attrs = AndesTextViewAttrs(
            AndesTextViewColor.Primary,
            AndesTextViewStyle.BodyM
        )

        val config = configFactory.create(context, attrs, "")

        config.color assertEquals R.color.andes_text_color_primary.toColor(context)
        config.lineHeight assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_body_line_height_m)
        config.spannedText assertEquals SpannableString("")
        config.textFont assertEquals context.getFontOrDefault(R.font.andes_font_regular)
        config.textSize assertEquals context.resources.getDimension(R.dimen.andes_body_font_size_m)
        config.textStyle assertEquals Typeface.NORMAL
        config.textWeight assertEquals R.dimen.andes_body_font_weight
    }

    @Test
    fun `color primary, style body_s`() {
        attrs = AndesTextViewAttrs(
            AndesTextViewColor.Primary,
            AndesTextViewStyle.BodyS
        )

        val config = configFactory.create(context, attrs, "")

        config.color assertEquals R.color.andes_text_color_primary.toColor(context)
        config.lineHeight assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_body_line_height_s)
        config.spannedText assertEquals SpannableString("")
        config.textFont assertEquals context.getFontOrDefault(R.font.andes_font_regular)
        config.textSize assertEquals context.resources.getDimension(R.dimen.andes_body_font_size_s)
        config.textStyle assertEquals Typeface.NORMAL
        config.textWeight assertEquals R.dimen.andes_body_font_weight
    }

    @Test
    fun `color primary, style body_xs`() {
        attrs = AndesTextViewAttrs(
            AndesTextViewColor.Primary,
            AndesTextViewStyle.BodyXs
        )

        val config = configFactory.create(context, attrs, "")

        config.color assertEquals R.color.andes_text_color_primary.toColor(context)
        config.lineHeight assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_body_line_height_xs)
        config.spannedText assertEquals SpannableString("")
        config.textFont assertEquals context.getFontOrDefault(R.font.andes_font_regular)
        config.textSize assertEquals context.resources.getDimension(R.dimen.andes_body_font_size_xs)
        config.textStyle assertEquals Typeface.NORMAL
        config.textWeight assertEquals R.dimen.andes_body_font_weight
    }

    @Test
    fun `custom color`() {
        attrs = AndesTextViewAttrs(
            AndesTextViewColor.Custom(R.color.andes_accent_color_800.toColor(context)),
            AndesTextViewStyle.BodyXs
        )

        val config = configFactory.create(context, attrs, "")

        config.color assertEquals R.color.andes_accent_color_800.toColor(context)
        config.lineHeight assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_body_line_height_xs)
        config.spannedText assertEquals SpannableString("")
        config.textFont assertEquals context.getFontOrDefault(R.font.andes_font_regular)
        config.textSize assertEquals context.resources.getDimension(R.dimen.andes_textview_body_font_size_xs)
        config.textStyle assertEquals Typeface.NORMAL
        config.textWeight assertEquals R.dimen.andes_body_font_weight
    }
}
