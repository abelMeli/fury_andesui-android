package com.mercadolibre.android.andesui.utils

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.util.TypedValue
import com.mercadolibre.android.andesui.bullet.AndesBullet
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.textview.AndesTextView

internal fun configureBullet(
    andesBulletView: AndesTextView,
    bulletText: String,
    bulletGapWith: Int,
    bulletColor: Int,
    bulletDotSize: Int,
    bulletTextSize: Float,
    bulletTypeFace: Typeface?
) {
    val spannableString = SpannableStringBuilder(bulletText)
    setupSpannableBullet(
        spannableString,
        bulletText,
        bulletGapWith,
        bulletColor,
        bulletDotSize
    )
    andesBulletView.text = spannableString
    andesBulletView.setTextSize(TypedValue.COMPLEX_UNIT_PX, bulletTextSize)
    andesBulletView.setTextColor(bulletColor)
    andesBulletView.typeface = bulletTypeFace
}

internal fun configureBulletBodyLink(
    andesBulletView: AndesTextView,
    context: Context,
    bullet: AndesBullet,
    bodyLinkIsUnderline: Boolean,
    bodyLinkTextColor: AndesColor
) {
    val spannableString = SpannableStringBuilder(bullet.text)
    bullet.textLinks?.let {
        setupSpannableBodyLink(
            context,
            spannableString,
            it,
            bodyLinkIsUnderline,
            bodyLinkTextColor
        )
        andesBulletView.movementMethod = LinkMovementMethod.getInstance()
    }
    andesBulletView.text = spannableString
}
