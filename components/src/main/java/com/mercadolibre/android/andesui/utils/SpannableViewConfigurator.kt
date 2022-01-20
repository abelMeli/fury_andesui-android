package com.mercadolibre.android.andesui.utils

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import com.mercadolibre.android.andesui.bullet.AndesBullet
import com.mercadolibre.android.andesui.bullet.AndesBulletSpannable
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks

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
            Log.e("AndesBodyLink", "Body link range incorrect: " +
                "${andesBodyLink.startIndex}, ${andesBodyLink.endIndex}")
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

