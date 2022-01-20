package com.mercadolibre.android.andesui.slider.type

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.IMPORTANT_FOR_ACCESSIBILITY_NO
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.textview.style.AndesTextViewStyle

internal interface AndesSliderTypeInterface {
    fun leftComponent(context: Context): View?
    fun rightComponent(context: Context): View?
}

internal object AndesSliderSimple : AndesSliderTypeInterface {
    override fun leftComponent(context: Context): View? = null // not necesary for simple

    override fun rightComponent(context: Context): View? = null // not necesary for simple
}

internal class AndesSliderIcons(private val leftIcon: Drawable?, private val rightIcon: Drawable?) :
    AndesSliderTypeInterface {

    override fun leftComponent(context: Context): View? = createIconView(context, leftIcon)

    override fun rightComponent(context: Context): View? = createIconView(context, rightIcon)

    private fun createIconView(context: Context, icon: Drawable?): View? =
        icon?.let {
            val size = context.resources.getDimensionPixelSize(R.dimen.andes_slider_image_view_size)
            ImageView(context).apply {
                layoutParams = ViewGroup.LayoutParams(size, size)
                setImageDrawable(it)
                setColorFilter(
                    ContextCompat.getColor(context, R.color.andes_gray_900_solid),
                    PorterDuff.Mode.SRC_IN
                )
                importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
            }
        }
}

internal class AndesSliderLimits(private val textLeft: String?, private val textRight: String?) :
    AndesSliderTypeInterface {
    override fun leftComponent(context: Context): View? = createLimitView(context, textLeft)

    override fun rightComponent(context: Context): View? = createLimitView(context, textRight)

    private fun createLimitView(context: Context, label: String?): View? =
        label?.let {
            AndesTextView(context).apply {
                style = AndesTextViewStyle.BodyS
                text = label
                importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
            }
        }
}
