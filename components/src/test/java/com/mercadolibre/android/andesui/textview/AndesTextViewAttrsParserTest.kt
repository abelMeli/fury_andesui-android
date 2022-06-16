package com.mercadolibre.android.andesui.textview

import android.content.Context
import android.graphics.Typeface
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.assertIsNull
import com.mercadolibre.android.andesui.utils.buildAttributeSet
import com.mercadolibre.android.andesui.color.toColor
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.andesui.textview.factory.AndesTextViewAttrs
import com.mercadolibre.android.andesui.textview.factory.AndesTextViewAttrsParser
import com.mercadolibre.android.andesui.textview.style.AndesTextViewStyle
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesTextViewAttrsParserTest {

    private lateinit var context: Context
    private lateinit var andesTextViewAttrs: AndesTextViewAttrs

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `parsing with no custom values, color primary, style title_xl`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesTextViewTextColor, "1000")
            addAttribute(R.attr.andesTextViewStyle, "2000")
        }

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewColor assertEquals AndesTextViewColor.Primary
        andesTextViewAttrs.andesTextViewStyle assertEquals AndesTextViewStyle.TitleXl
        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.NORMAL
        andesTextViewAttrs.andesTextViewBodyBolds assertIsNull true
        andesTextViewAttrs.andesTextViewBodyLinks assertIsNull true
        andesTextViewAttrs.isLinkColorInverted assertEquals false
        andesTextViewAttrs.andesTextViewTextViewMoneyAmount assertIsNull true
    }

    @Test
    fun `parsing with no custom values, color primary, style title_l`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesTextViewTextColor, "1000")
            addAttribute(R.attr.andesTextViewStyle, "2001")
        }

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewColor assertEquals AndesTextViewColor.Primary
        andesTextViewAttrs.andesTextViewStyle assertEquals AndesTextViewStyle.TitleL
        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.NORMAL
        andesTextViewAttrs.andesTextViewBodyBolds assertIsNull true
        andesTextViewAttrs.andesTextViewBodyLinks assertIsNull true
        andesTextViewAttrs.isLinkColorInverted assertEquals false
        andesTextViewAttrs.andesTextViewTextViewMoneyAmount assertIsNull true
    }

    @Test
    fun `parsing with no custom values, color primary, style title_m`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesTextViewTextColor, "1000")
            addAttribute(R.attr.andesTextViewStyle, "2002")
        }

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewColor assertEquals AndesTextViewColor.Primary
        andesTextViewAttrs.andesTextViewStyle assertEquals AndesTextViewStyle.TitleM
        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.NORMAL
        andesTextViewAttrs.andesTextViewBodyBolds assertIsNull true
        andesTextViewAttrs.andesTextViewBodyLinks assertIsNull true
        andesTextViewAttrs.isLinkColorInverted assertEquals false
        andesTextViewAttrs.andesTextViewTextViewMoneyAmount assertIsNull true
    }

    @Test
    fun `parsing with no custom values, color primary, style title_s`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesTextViewTextColor, "1000")
            addAttribute(R.attr.andesTextViewStyle, "2003")
        }

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewColor assertEquals AndesTextViewColor.Primary
        andesTextViewAttrs.andesTextViewStyle assertEquals AndesTextViewStyle.TitleS
        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.NORMAL
        andesTextViewAttrs.andesTextViewBodyBolds assertIsNull true
        andesTextViewAttrs.andesTextViewBodyLinks assertIsNull true
        andesTextViewAttrs.isLinkColorInverted assertEquals false
        andesTextViewAttrs.andesTextViewTextViewMoneyAmount assertIsNull true
    }

    @Test
    fun `parsing with no custom values, color primary, style title_xs`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesTextViewTextColor, "1000")
            addAttribute(R.attr.andesTextViewStyle, "2004")
        }

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewColor assertEquals AndesTextViewColor.Primary
        andesTextViewAttrs.andesTextViewStyle assertEquals AndesTextViewStyle.TitleXs
        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.NORMAL
        andesTextViewAttrs.andesTextViewBodyBolds assertIsNull true
        andesTextViewAttrs.andesTextViewBodyLinks assertIsNull true
        andesTextViewAttrs.isLinkColorInverted assertEquals false
        andesTextViewAttrs.andesTextViewTextViewMoneyAmount assertIsNull true
    }

    @Test
    fun `parsing with no custom values, color primary, style body_l`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesTextViewTextColor, "1000")
            addAttribute(R.attr.andesTextViewStyle, "2005")
        }

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewColor assertEquals AndesTextViewColor.Primary
        andesTextViewAttrs.andesTextViewStyle assertEquals AndesTextViewStyle.BodyL
        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.NORMAL
        andesTextViewAttrs.andesTextViewBodyBolds assertIsNull true
        andesTextViewAttrs.andesTextViewBodyLinks assertIsNull true
        andesTextViewAttrs.isLinkColorInverted assertEquals false
        andesTextViewAttrs.andesTextViewTextViewMoneyAmount assertIsNull true
    }

    @Test
    fun `parsing with no custom values, color primary, style body_m`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesTextViewTextColor, "1000")
            addAttribute(R.attr.andesTextViewStyle, "2006")
        }

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewColor assertEquals AndesTextViewColor.Primary
        andesTextViewAttrs.andesTextViewStyle assertEquals AndesTextViewStyle.BodyM
        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.NORMAL
        andesTextViewAttrs.andesTextViewBodyBolds assertIsNull true
        andesTextViewAttrs.andesTextViewBodyLinks assertIsNull true
        andesTextViewAttrs.isLinkColorInverted assertEquals false
        andesTextViewAttrs.andesTextViewTextViewMoneyAmount assertIsNull true
    }

    @Test
    fun `parsing with no custom values, color primary, style body_s`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesTextViewTextColor, "1000")
            addAttribute(R.attr.andesTextViewStyle, "2007")
        }

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewColor assertEquals AndesTextViewColor.Primary
        andesTextViewAttrs.andesTextViewStyle assertEquals AndesTextViewStyle.BodyS
        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.NORMAL
        andesTextViewAttrs.andesTextViewBodyBolds assertIsNull true
        andesTextViewAttrs.andesTextViewBodyLinks assertIsNull true
        andesTextViewAttrs.isLinkColorInverted assertEquals false
        andesTextViewAttrs.andesTextViewTextViewMoneyAmount assertIsNull true
    }

    @Test
    fun `parsing with no custom values, color primary, style body_xs`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesTextViewTextColor, "1000")
            addAttribute(R.attr.andesTextViewStyle, "2008")
        }

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewColor assertEquals AndesTextViewColor.Primary
        andesTextViewAttrs.andesTextViewStyle assertEquals AndesTextViewStyle.BodyXs
        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.NORMAL
        andesTextViewAttrs.andesTextViewBodyBolds assertIsNull true
        andesTextViewAttrs.andesTextViewBodyLinks assertIsNull true
        andesTextViewAttrs.isLinkColorInverted assertEquals false
        andesTextViewAttrs.andesTextViewTextViewMoneyAmount assertIsNull true
    }

    @Test
    fun `parsing with no custom values, color secondary, style title_xl`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesTextViewTextColor, "1001")
            addAttribute(R.attr.andesTextViewStyle, "2000")
        }

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewColor assertEquals AndesTextViewColor.Secondary
        andesTextViewAttrs.andesTextViewStyle assertEquals AndesTextViewStyle.TitleXl
        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.NORMAL
        andesTextViewAttrs.andesTextViewBodyBolds assertIsNull true
        andesTextViewAttrs.andesTextViewBodyLinks assertIsNull true
        andesTextViewAttrs.isLinkColorInverted assertEquals false
        andesTextViewAttrs.andesTextViewTextViewMoneyAmount assertIsNull true
    }

    @Test
    fun `parsing with no custom values, color disabled, style title_xl`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesTextViewTextColor, "1002")
            addAttribute(R.attr.andesTextViewStyle, "2000")
        }

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewColor assertEquals AndesTextViewColor.Disabled
        andesTextViewAttrs.andesTextViewStyle assertEquals AndesTextViewStyle.TitleXl
        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.NORMAL
        andesTextViewAttrs.andesTextViewBodyBolds assertIsNull true
        andesTextViewAttrs.andesTextViewBodyLinks assertIsNull true
        andesTextViewAttrs.isLinkColorInverted assertEquals false
        andesTextViewAttrs.andesTextViewTextViewMoneyAmount assertIsNull true
    }

    @Test
    fun `parsing with no custom values, color inverted, style title_xl`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesTextViewTextColor, "1003")
            addAttribute(R.attr.andesTextViewStyle, "2000")
        }

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewColor assertEquals AndesTextViewColor.Inverted
        andesTextViewAttrs.andesTextViewStyle assertEquals AndesTextViewStyle.TitleXl
        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.NORMAL
        andesTextViewAttrs.andesTextViewBodyBolds assertIsNull true
        andesTextViewAttrs.andesTextViewBodyLinks assertIsNull true
        andesTextViewAttrs.isLinkColorInverted assertEquals false
        andesTextViewAttrs.andesTextViewTextViewMoneyAmount assertIsNull true
    }

    @Test
    fun `parsing with no custom values, color negative, style title_xl`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesTextViewTextColor, "1004")
            addAttribute(R.attr.andesTextViewStyle, "2000")
        }

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewColor assertEquals AndesTextViewColor.Negative
        andesTextViewAttrs.andesTextViewStyle assertEquals AndesTextViewStyle.TitleXl
        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.NORMAL
        andesTextViewAttrs.andesTextViewBodyBolds assertIsNull true
        andesTextViewAttrs.andesTextViewBodyLinks assertIsNull true
        andesTextViewAttrs.isLinkColorInverted assertEquals false
        andesTextViewAttrs.andesTextViewTextViewMoneyAmount assertIsNull true
    }

    @Test
    fun `parsing with no custom values, color caution, style title_xl`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesTextViewTextColor, "1005")
            addAttribute(R.attr.andesTextViewStyle, "2000")
        }

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewColor assertEquals AndesTextViewColor.Caution
        andesTextViewAttrs.andesTextViewStyle assertEquals AndesTextViewStyle.TitleXl
        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.NORMAL
        andesTextViewAttrs.andesTextViewBodyBolds assertIsNull true
        andesTextViewAttrs.andesTextViewBodyLinks assertIsNull true
        andesTextViewAttrs.isLinkColorInverted assertEquals false
        andesTextViewAttrs.andesTextViewTextViewMoneyAmount assertIsNull true
    }

    @Test
    fun `parsing with no custom values, color positive, style title_xl`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesTextViewTextColor, "1006")
            addAttribute(R.attr.andesTextViewStyle, "2000")
        }

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewColor assertEquals AndesTextViewColor.Positive
        andesTextViewAttrs.andesTextViewStyle assertEquals AndesTextViewStyle.TitleXl
        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.NORMAL
        andesTextViewAttrs.andesTextViewBodyBolds assertIsNull true
        andesTextViewAttrs.andesTextViewBodyLinks assertIsNull true
        andesTextViewAttrs.isLinkColorInverted assertEquals false
        andesTextViewAttrs.andesTextViewTextViewMoneyAmount assertIsNull true
    }

    @Test
    fun `parsing with custom color, text style, text size, text line height`() {
        val attrs = buildAttributeSet {
            setStyleAttribute(STYLE_FOR_TEST)
        }

        /*
        <item name="android:textColor">@color/andes_accent_color</item>
        <item name="android:textStyle">bold</item>
        <item name="android:textSize">60sp</item>
        <item name="lineHeight">30dp</item>
         */

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewColor assertEquals AndesTextViewColor.Custom(R.color.andes_accent_color.toColor(context))
        andesTextViewAttrs.andesTextViewStyle assertEquals AndesTextViewStyle.Custom(0, 30F, 60F)
        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.BOLD
    }

    @Test
    fun `parsing with text style italic returns typeface normal`() {
        val attrs = buildAttributeSet {
            setStyleAttribute(STYLE_FOR_TEST_WITH_ITALIC)
        }

        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)

        andesTextViewAttrs.andesTextViewCustomStyle assertEquals Typeface.NORMAL
    }

    private companion object {
        /**
         * Style created only to test the attrs parser.
         *
         * We use this approach since the Robolectric AttributeSetBuilder does not let us pass
         * the textColor and textStyle values directly if they are not part of a style.
         *
         * This style passes the accent color and a style BOLD.
         * To see the overridden values, see [R.style.Andes_Test]
         */
        private const val STYLE_FOR_TEST = "@style/Andes.Test"

        /**
         * Style created only to test the attrs parser.
         *
         * We use this approach since the Robolectric AttributeSetBuilder does not let us pass
         * the textColor and textStyle values directly if they are not part of a style.
         *
         * This style passes a style ITALIC.
         * To see the overridden values, see [R.style.Andes_Test_2]
         */
        private const val STYLE_FOR_TEST_WITH_ITALIC = "@style/Andes.Test.2"
    }
}
