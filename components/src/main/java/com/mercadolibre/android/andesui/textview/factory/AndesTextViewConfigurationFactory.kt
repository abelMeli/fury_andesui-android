package com.mercadolibre.android.andesui.textview.factory

import android.content.Context
import android.graphics.Typeface
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColorInterface
import com.mercadolibre.android.andesui.textview.style.AndesTextViewStyle
import com.mercadolibre.android.andesui.utils.removeBoldSpans
import com.mercadolibre.android.andesui.utils.removeLinkSpans
import com.mercadolibre.android.andesui.utils.toSpannableWithBolds
import com.mercadolibre.android.andesui.utils.toSpannableWithLinks
import com.mercadolibre.android.andesui.utils.toSpannableWithMoneyAmounts

internal data class AndesTextViewConfiguration(
    val color: Int,
    val textSize: Float,
    val textWeight: Int,
    val textFont: Typeface,
    val textStyle: Int,
    val lineHeight: Int,
    val spannedText: CharSequence,
    private val text: CharSequence?
)

internal object AndesTextViewConfigurationFactory {

	fun create(context: Context, andesTextViewAttrs: AndesTextViewAttrs, text: CharSequence? = null): AndesTextViewConfiguration {
		return with(andesTextViewAttrs) {
			AndesTextViewConfiguration(
                color = resolveColor(context, andesTextViewColor.color),
				textSize = resolveSize(context, andesTextViewStyle),
				textWeight = andesTextViewStyle.style.weightValue,
				textFont = resolveTypeface(context, andesTextViewStyle),
				textStyle = andesTextViewCustomStyle,
				lineHeight = resolveLineHeight(context, andesTextViewStyle),
				spannedText = resolveSpannedText(context, this, text),
				text = text
			)
		}
	}

	private fun resolveColor(context: Context, color: AndesTextViewColorInterface): Int {
		return color.getColor(context)
	}

	/**
	 * When the dev passes a new value for the bodyBolds or bodyLinks, the text value from the component
	 * will be transformed into a SpannableString with the corresponding spans for the bold and link
	 * sections needed.
	 */
	private fun resolveSpannedText(context: Context, attrs: AndesTextViewAttrs, text: CharSequence?): CharSequence {
		var returnText = text
		returnText = clearOldSpans(returnText)

		return returnText.let {
			val textWithLinks = it.toSpannableWithLinks(
				context,
				attrs.andesTextViewBodyLinks,
				attrs.isLinkColorInverted
			)
			textWithLinks.toSpannableWithBolds(context, attrs.andesTextViewBodyBolds)
				.toSpannableWithMoneyAmounts(attrs.andesTextViewTextViewMoneyAmount)
		}.apply {
			attrs.andesTextViewTextViewMoneyAmount = null
		}
	}

	private fun clearOldSpans(text: CharSequence?): CharSequence {
		return text.removeBoldSpans().removeLinkSpans()
	}

	private fun resolveSize(context: Context, style: AndesTextViewStyle): Float {
		return style.style.getTextSize(context)
	}

	private fun resolveTypeface(context: Context, style: AndesTextViewStyle): Typeface {
		return style.style.getFont(context)
	}

	private fun resolveLineHeight(context: Context, style: AndesTextViewStyle): Int {
		return style.style.getLineHeight(context)
	}
}
