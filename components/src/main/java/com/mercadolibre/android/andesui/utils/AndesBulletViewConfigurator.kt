package com.mercadolibre.android.andesui.utils

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.bullet.AndesBullet
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.textview.AndesTextView

class AndesBulletViewConfigurator(context: Context) {

    private var bulletComponent: AndesTextView = AndesTextView(context)

    fun configure(
        context: Context,
        bullet: AndesBullet,
        bulletGapWith: Int,
        bulletColor: Int,
        bulletDotSize: Int,
        bulletTextSize: Float,
        bulletTypeFace: Typeface?,
        bodyLinkIsUnderline: Boolean,
        bodyLinkTextColor: AndesColor
    ): AndesTextView {
        val spannableString = SpannableStringBuilder(bullet.text)
        bullet.textLinks?.let {
            setupSpannableBodyLink(
                context,
                spannableString,
                it,
                bodyLinkIsUnderline,
                bodyLinkTextColor
            )
            bulletComponent.movementMethod = LinkMovementMethod.getInstance()
        }

        setupSpannableBullet(
            spannableString,
            bullet.text,
            bulletGapWith,
            bulletColor,
            bulletDotSize
        )
        bulletComponent.text = spannableString
        bulletComponent.setTextSize(TypedValue.COMPLEX_UNIT_PX, bulletTextSize)
        bulletComponent.setTextColor(bulletColor)
        bulletComponent.typeface = bulletTypeFace

        return bulletComponent
    }

}
