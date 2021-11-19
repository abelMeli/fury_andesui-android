package com.mercadolibre.android.andesui.textview.factory

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBolds
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.andesui.textview.style.AndesTextViewStyle

internal data class AndesTextViewAttrs(
    val andesTextViewColor: AndesTextViewColor,
    val andesTextViewStyle: AndesTextViewStyle,
    val andesTextViewCustomStyle: Int = Typeface.NORMAL,
    val andesTextViewBodyBolds: AndesBodyBolds? = null,
    val andesTextViewBodyLinks: AndesBodyLinks? = null,
    val isLinkColorInverted: Boolean = false
)

/**
 * This object parse the attribute set and return an instance of AndesTextViewAttrs
 * to be used by AndesTextView
 */
internal object AndesTextViewAttrsParser {

    private const val ANDES_TEXT_VIEW_COLOR_PRIMARY = 1000
    private const val ANDES_TEXT_VIEW_COLOR_SECONDARY = 1001
    private const val ANDES_TEXT_VIEW_COLOR_DISABLED = 1002
    private const val ANDES_TEXT_VIEW_COLOR_INVERTED = 1003
    private const val ANDES_TEXT_VIEW_COLOR_NEGATIVE = 1004
    private const val ANDES_TEXT_VIEW_COLOR_CAUTION = 1005
    private const val ANDES_TEXT_VIEW_COLOR_POSITIVE = 1006

    private const val ANDES_TEXT_VIEW_STYLE_TITLE_XL = 2000
    private const val ANDES_TEXT_VIEW_STYLE_TITLE_L = 2001
    private const val ANDES_TEXT_VIEW_STYLE_TITLE_M = 2002
    private const val ANDES_TEXT_VIEW_STYLE_TITLE_S = 2003
    private const val ANDES_TEXT_VIEW_STYLE_TITLE_XS = 2004
    private const val ANDES_TEXT_VIEW_STYLE_BODY_L = 2005
    private const val ANDES_TEXT_VIEW_STYLE_BODY_M = 2006
    private const val ANDES_TEXT_VIEW_STYLE_BODY_S = 2007
    private const val ANDES_TEXT_VIEW_STYLE_BODY_XS = 2008
    private const val NO_STYLE_DEFINED = 2009

    private const val DEFAULT_TEXT_COLOR_VALUE = 0
    private const val DEFAULT_TEXT_SIZE_VALUE = 0F
    private const val DEFAULT_TEXT_FONT_VALUE = 0
    private const val DEFAULT_TEXT_STYLE_VALUE = 0
    private const val DEFAULT_TEXT_LINE_HEIGHT_VALUE = 0F

    private const val NORMAL_TEXT_STYLE = 0
    private const val BOLD_TEXT_STYLE = 1
    private const val ITALIC_TEXT_STYLE = 2

    @Suppress("ComplexMethod")
    fun parse(context: Context, attr: AttributeSet?): AndesTextViewAttrs {
		val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesTextView)

        /**
         * Value that represents a custom text color passed by xml as android:textColor.
         * If the attribute is not set, the default value is the ColorRes resource for the
         * [R.color.andes_text_color_primary].
         */
        val textColorFromXml = typedArray.getColor(R.styleable.AndesTextView_android_textColor, DEFAULT_TEXT_COLOR_VALUE)

        /**
         * Value that represents a custom text size passed by xml as android:textSize.
         * If the attribute is not set, the default value is 0F.
         */
        val textSizeFromXml = typedArray.getDimension(R.styleable.AndesTextView_android_textSize, DEFAULT_TEXT_SIZE_VALUE)

        /**
         * Value that represents the resource id of a custom text font passed by xml as android:fontFamily.
         * If the attribute is not set, the default value is 0.
         */
        val textFontFromXml = typedArray.getResourceId(R.styleable.AndesTextView_android_fontFamily, DEFAULT_TEXT_FONT_VALUE)

        /**
         * Value that represents a custom text style passed by xml as android:textStyle. If the attribute
         * is not set, the default value is [Typeface.NORMAL].
         *
         * As requested by UX, we do not accept [Typeface.ITALIC].
         */
        val textStyleFromXml = when (typedArray.getInt(R.styleable.AndesTextView_android_textStyle, DEFAULT_TEXT_STYLE_VALUE)) {
            NORMAL_TEXT_STYLE -> Typeface.NORMAL
            BOLD_TEXT_STYLE -> Typeface.BOLD
            ITALIC_TEXT_STYLE -> Typeface.NORMAL
            else -> Typeface.NORMAL
        }

        /**
         * Value that represents a custom text line height passed by xml as lineHeight. If the attribute
         * is not set, the default value is 0F.
         */
        val textLineHeightFromXml = typedArray.getDimension(R.styleable.AndesTextView_lineHeight, DEFAULT_TEXT_LINE_HEIGHT_VALUE)

        val isLinkColorInverted = typedArray.getBoolean(R.styleable.AndesTextView_andesTextViewIsLinkColorInverted, false)

        /**
         * When this value is not set by xml, it will fall to the default value which will be the color
         * retrieved from the android:textColor attribute.
         */
        val color = when (typedArray.getInt(R.styleable.AndesTextView_andesTextViewTextColor, textColorFromXml)) {
            ANDES_TEXT_VIEW_COLOR_PRIMARY -> AndesTextViewColor.Primary
            ANDES_TEXT_VIEW_COLOR_SECONDARY -> AndesTextViewColor.Secondary
            ANDES_TEXT_VIEW_COLOR_DISABLED -> AndesTextViewColor.Disabled
            ANDES_TEXT_VIEW_COLOR_INVERTED -> AndesTextViewColor.Inverted
            ANDES_TEXT_VIEW_COLOR_NEGATIVE -> AndesTextViewColor.Negative
            ANDES_TEXT_VIEW_COLOR_CAUTION -> AndesTextViewColor.Caution
            ANDES_TEXT_VIEW_COLOR_POSITIVE -> AndesTextViewColor.Positive
            textColorFromXml -> AndesTextViewColor.Custom(textColorFromXml)
            else -> AndesTextViewColor.Primary
        }

        /**
         * When this value is not set by xml, it will take the eventual custom values passed (text font,
         * line height and text size) and use them to create a custom style.
         */
        val style = when (typedArray.getInt(R.styleable.AndesTextView_andesTextViewStyle, NO_STYLE_DEFINED)) {
            ANDES_TEXT_VIEW_STYLE_TITLE_XL -> AndesTextViewStyle.TitleXl
            ANDES_TEXT_VIEW_STYLE_TITLE_L -> AndesTextViewStyle.TitleL
            ANDES_TEXT_VIEW_STYLE_TITLE_M -> AndesTextViewStyle.TitleM
            ANDES_TEXT_VIEW_STYLE_TITLE_S -> AndesTextViewStyle.TitleS
            ANDES_TEXT_VIEW_STYLE_TITLE_XS -> AndesTextViewStyle.TitleXs
            ANDES_TEXT_VIEW_STYLE_BODY_L -> AndesTextViewStyle.BodyL
            ANDES_TEXT_VIEW_STYLE_BODY_M -> AndesTextViewStyle.BodyM
            ANDES_TEXT_VIEW_STYLE_BODY_S -> AndesTextViewStyle.BodyS
            ANDES_TEXT_VIEW_STYLE_BODY_XS -> AndesTextViewStyle.BodyXs
            NO_STYLE_DEFINED -> AndesTextViewStyle.Custom(textFontFromXml, textLineHeightFromXml, textSizeFromXml)
            else -> AndesTextViewStyle.BodyS
        }

        return AndesTextViewAttrs(
            andesTextViewColor = color,
            andesTextViewStyle = style,
            andesTextViewCustomStyle = textStyleFromXml,
            isLinkColorInverted = isLinkColorInverted
        ).also { typedArray.recycle() }
    }
}
