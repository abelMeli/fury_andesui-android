package com.mercadolibre.android.andesui.utils

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.bullet.AndesBulletSpannable
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

internal fun setupSpannableBodyLink(
    context: Context,
    spannableString: SpannableStringBuilder,
    andesBodyLinks: AndesBodyLinks,
    bodyLinkIsUnderline: Boolean,
    bodyLinkTextColor: AndesColor
) {
    andesBodyLinks.links.forEachIndexed { linkIndex, andesBodyLink ->
        if (andesBodyLink.isValidRange(SpannableString.valueOf(spannableString))) {
            val clickableSpan = setupClickableSpan(
                context,
                andesBodyLinks,
                linkIndex,
                bodyLinkIsUnderline,
                bodyLinkTextColor
            )

            spannableString.setSpan(
                clickableSpan,
                andesBodyLink.startIndex,
                andesBodyLink.endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } else {
            Log.d(
                context.resources.getString(R.string.andes_body_link_tag),
                context.resources.getString(
                    R.string.andes_body_link_range_incorrect,
                    andesBodyLink.startIndex,
                    andesBodyLink.endIndex
                )
            )
        }
    }
}

private fun setupClickableSpan(
    context: Context,
    andesBodyLinks: AndesBodyLinks,
    linkIndex: Int,
    bodyLinkIsUnderline: Boolean,
    bodyLinkTextColor: AndesColor
): ClickableSpan {
    return object : ClickableSpan() {
        override fun onClick(view: View) {
            andesBodyLinks.listener(linkIndex)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = bodyLinkIsUnderline
            ds.color = bodyLinkTextColor.colorInt(context)
        }
    }
}

internal fun setupSpannableBullet(
    spannableString: SpannableStringBuilder,
    bulletsText: String,
    bulletGapWith: Int,
    bulletColor: Int,
    bulletRadius: Int
) {
    spannableString.setSpan(
        AndesBulletSpannable(
            bulletGapWith,
            bulletColor,
            bulletRadius
        ),
        0,
        bulletsText.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

/**
 * Span that allows setting semibold style to the text it's attached to.
 */
internal class AndesBoldSpan(context: Context) : StyleSpan(
    context.getFontOrDefault(R.font.andes_font_semibold, Typeface.DEFAULT_BOLD).style
)