package com.mercadolibre.android.andesui.slider.state

import android.content.Context
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor

internal interface AndesSliderStateInterface {
    fun textColor(context: Context): AndesColor
    fun haloColor(context: Context): AndesColor
    fun trackActiveColor(context: Context): AndesColor
    fun trackInactiveColor(context: Context): AndesColor
    fun thumbColor(context: Context): AndesColor
    fun thumbStrokeColor(context: Context): AndesColor
    fun isEnabled(): Boolean
}

internal object AndesSliderStateIdle : AndesSliderStateInterface {
    override fun textColor(context: Context) = R.color.andes_text_color_primary.toAndesColor()
    override fun haloColor(context: Context) = R.color.andes_accent_color_300.toAndesColor()
    override fun trackActiveColor(context: Context) = R.color.andes_accent_color_500.toAndesColor()
    override fun trackInactiveColor(context: Context) = R.color.andes_gray_070.toAndesColor()
    override fun thumbColor(context: Context) = R.color.andes_white.toAndesColor()
    override fun thumbStrokeColor(context: Context) = R.color.andes_accent_color_500.toAndesColor()
    override fun isEnabled() = true
}

internal object AndesSliderStateDisabled : AndesSliderStateInterface {
    override fun textColor(context: Context) = R.color.andes_text_color_disabled.toAndesColor()
    override fun haloColor(context: Context) = R.color.andes_gray_250_solid.toAndesColor()
    override fun trackActiveColor(context: Context) = R.color.andes_gray_250_solid.toAndesColor()
    override fun trackInactiveColor(context: Context) = R.color.andes_gray_070.toAndesColor()
    override fun thumbColor(context: Context) = R.color.andes_white.toAndesColor()
    override fun thumbStrokeColor(context: Context) = R.color.andes_gray_250_solid.toAndesColor()
    override fun isEnabled() = false
}
