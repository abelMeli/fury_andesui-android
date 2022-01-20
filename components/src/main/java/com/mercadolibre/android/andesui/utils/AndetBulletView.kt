package com.mercadolibre.android.andesui.utils

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.bullet.AndesBullet
import com.mercadolibre.android.andesui.color.AndesColor

class AndesBulletView : ConstraintLayout {

    private lateinit var bulletComponent: TextView

    constructor(context: Context) : super(context) {
        setupComponents()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setupComponents()
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setupComponents()
    }

    private fun setupComponents() {
        initComponents()
    }

    private fun initComponents() {
        val container = LayoutInflater.from(context).inflate(R.layout.andes_layout_bullet_item, this, true)
        bulletComponent = container.findViewById(R.id.andes_bullet)
    }

    fun configure(
        bullet: AndesBullet,
        bulletGapWith: Int,
        bulletColor: Int,
        bulletDotSize: Int,
        bulletTextSize: Float,
        bulletTypeFace: Typeface?,
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
    }

}
