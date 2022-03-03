package com.mercadolibre.android.andesui.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.util.TypedValue
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.bullet.AndesBullet
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.textview.AndesTextView

internal fun configureBullet(
    context: Context,
    bullet: AndesBullet,
    bulletGapWith: Int,
    bulletColor: Int,
    bulletDotSize: Int,
    bulletTextSize: Float,
    bulletTypeFace: Typeface?,
    bulletMarginTop: Int,
    bodyLinkIsUnderline: Boolean,
    bodyLinkTextColor: AndesColor
): AndesTextView {
    val andesBulletView = AndesTextView(context)
    val spannableString = SpannableStringBuilder(bullet.text)
    val params: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    params.topMargin = (bulletMarginTop * Resources.getSystem().displayMetrics.density).toInt()
    setupSpannableBullet(
        spannableString,
        bullet.text,
        bulletGapWith,
        bulletColor,
        bulletDotSize
    )
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
    andesBulletView.layoutParams = params
    andesBulletView.text = spannableString
    andesBulletView.setTextSize(TypedValue.COMPLEX_UNIT_PX, bulletTextSize)
    andesBulletView.setTextColor(bulletColor)
    andesBulletView.typeface = bulletTypeFace
    return andesBulletView
}