package com.mercadolibre.android.andesui.utils

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBolds
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.andesui.textview.moneyamount.AndesTextViewMoneyAmount
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

/**
 * Takes the original charSequence and adds the links passed as argument to it.
 *
 * Since the SpannableString class implements CharSequence, this returned value can be directly
 * assigned into a TextView text without any other modification.
 *
 * In order to the clickable links to behave fully as clickable when inside a textView, we need
 * to set the textView.movementMethod = LinkMovementMethod.getInstance()
 *
 * [context]: needed to retrieve the color value from the resources.
 *
 * [andesBodyLinks]: the list of indexes for the links and the listener to invoke on click
 *
 * [isLinkColorInverted]: the value to determine if the link text color should be accent or white. This
 * value also sets if the link will be underlined or not (links are only underlined when color
 * inverted)
 *
 */
internal fun CharSequence?.toSpannableWithLinks(
    context: Context,
    andesBodyLinks: AndesBodyLinks?,
    isLinkColorInverted: Boolean
): CharSequence {
    val spannableString = this as? SpannableString ?: SpannableString(this)
    val textColor = AndesTextViewColor.Link(isLinkColorInverted).color.getColor(context)
    andesBodyLinks?.links?.forEachIndexed { linkIndex, andesBodyLink ->
        if (andesBodyLink.isValidRange(spannableString)) {
            val clickableSpan = object : ClickableSpanWithText(this?.substring(andesBodyLink.startIndex, andesBodyLink.endIndex)) {
                override fun onClick(view: View) {
                    andesBodyLinks.listener(linkIndex)
                    (view as? AndesTextView)?.shouldCallOnClickListener = false
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = isLinkColorInverted
                    ds.color = textColor
                }
            }
            spannableString.setSpan(clickableSpan,
                andesBodyLink.startIndex, andesBodyLink.endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else {
            Log.d("AndesStringUtils", "Body link range incorrect: " +
                    "${andesBodyLink.startIndex}, ${andesBodyLink.endIndex}")
        }
    }
    return spannableString
}

/**
 * Takes the original charSequence and adds bold segments to it.
 *
 * Since the SpannableString class implements CharSequence, this returned value can be directly
 * assigned into a TextView text without any other modification.
 *
 * [context]: needed to retrieve the font bold from the resources.
 * [andesBodyBolds]: the list of indexes for the bold sections.
 *
 */
internal fun CharSequence?.toSpannableWithBolds(
    context: Context,
    andesBodyBolds: AndesBodyBolds?
): CharSequence {
    val spannableString = this as? SpannableString ?: SpannableString(this)
    val boldTypeface = context.getFontOrDefault(R.font.andes_font_semibold, Typeface.DEFAULT_BOLD)
    andesBodyBolds?.segments?.forEach { bodyBold ->
        if (bodyBold.isValidRange(spannableString)) {
            val boldSpan = StyleSpan(boldTypeface.style)
            spannableString.setSpan(boldSpan, bodyBold.startIndex, bodyBold.endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else {
            Log.d("AndesStringUtils", "Body bold range incorrect: " +
                    "${bodyBold.startIndex}, ${bodyBold.endIndex}")
        }
    }
    return spannableString
}

internal fun CharSequence?.toSpannableWithMoneyAmounts(
    andesMoneyAmounts: AndesTextViewMoneyAmount?
): CharSequence {
    val spannableString = this as? SpannableString ?: SpannableString(this)
    val moneyAmounts = SpannableStringBuilder(spannableString)
    andesMoneyAmounts?.let { moneyAmount ->
        moneyAmounts.append(moneyAmount.infoProvider.provideText(moneyAmount.color))
    }
    return moneyAmounts
}

/**
 * Takes the original spannable string and removes the ClickableSpans.
 * Use this method to clean old spans when passing a null bodyLinks object to the text.
 */
internal fun CharSequence?.removeLinkSpans(): CharSequence {
    val spannableString = this as? SpannableString ?: SpannableString(this)
    val spanList = spannableString.getSpans(0, spannableString.length, ClickableSpanWithText::class.java)
    spanList.forEach {
        spannableString.removeSpan(it)
    }
    return spannableString
}

/**
 * Takes the original spannable string and removes the StyleSpans.
 * Use this method to clean old spans when passing a null bodyBolds object to the text.
 */
internal fun CharSequence?.removeBoldSpans(): CharSequence {
    val spannableString = this as? SpannableString ?: SpannableString(this)
    val spanList = spannableString.getSpans(0, spannableString.length, StyleSpan::class.java)
    spanList.forEach {
        spannableString.removeSpan(it)
    }
    return spannableString
}

/**
 * Return the list of strings which will be clickable in a spannable string.
 * If the caller is empty or is not a spannable string, it returns an empty list.
 */
internal fun CharSequence?.getLinkTextList(): List<String?> {
    if (this !is SpannableString) return emptyList()
    val spans = getSpans(0, length, ClickableSpanWithText::class.java)
    return spans.map { it.getText() }
}

/**
 * Return if the text is uppercase.
 */
internal fun CharSequence?.isUpperCase(): Boolean {
    this?.forEach {
        if (!it.isUpperCase()) {
            return false
        }
    }
    return true
}

internal fun CharSequence?.getRange(substring: String): IntRange? {
    val start = this?.indexOf(substring) ?: -1
    var range: IntRange? = null
    if (start > -1) {
        val end = start + substring.length
        range = IntRange(start, end)
    }
    return range
}

internal fun CharSequence.isNotValidRange(range: IntRange): Boolean =
    range.first < 0 || range.last > this.length
