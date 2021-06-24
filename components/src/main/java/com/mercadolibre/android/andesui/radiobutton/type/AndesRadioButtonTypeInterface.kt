package com.mercadolibre.android.andesui.radiobutton.type

import android.content.Context
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.radiobutton.status.AndesRadioButtonStatus
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor

internal interface AndesRadioButtonTypeInterface {
    fun borderColor(context: Context, status: AndesRadioButtonStatus): AndesColor
    fun iconColor(context: Context, status: AndesRadioButtonStatus): AndesColor
    fun backgroundColor(context: Context, status: AndesRadioButtonStatus): AndesColor
    fun textColor(context: Context): AndesColor
}

internal object AndesRadioButtonTypeIdle : AndesRadioButtonTypeInterface {
    override fun borderColor(context: Context, status: AndesRadioButtonStatus): AndesColor {
        return if (status == AndesRadioButtonStatus.UNSELECTED) {
            R.color.andes_gray_250_solid.toAndesColor()
        } else {
            R.color.andes_accent_color_500.toAndesColor()
        }
    }
    override fun iconColor(context: Context, status: AndesRadioButtonStatus) = R.color.andes_white.toAndesColor()
    override fun backgroundColor(context: Context, status: AndesRadioButtonStatus): AndesColor {
        return if (status == AndesRadioButtonStatus.UNSELECTED) {
            R.color.andes_white.toAndesColor()
        } else {
            R.color.andes_accent_color_500.toAndesColor()
        }
    }
    override fun textColor(context: Context) = R.color.andes_text_color_primary.toAndesColor()
}

internal object AndesRadioButtonTypeDisabled : AndesRadioButtonTypeInterface {
    override fun borderColor(context: Context, status: AndesRadioButtonStatus) = R.color.andes_gray_100_solid.toAndesColor()
    override fun iconColor(context: Context, status: AndesRadioButtonStatus) = R.color.andes_gray_250_solid.toAndesColor()
    override fun backgroundColor(context: Context, status: AndesRadioButtonStatus): AndesColor {
        return if (status == AndesRadioButtonStatus.UNSELECTED) {
            R.color.andes_white.toAndesColor()
        } else {
            R.color.andes_gray_100_solid.toAndesColor()
        }
    }
    override fun textColor(context: Context) = R.color.andes_gray_250_solid.toAndesColor()
}

internal object AndesRadioButtonTypeError : AndesRadioButtonTypeInterface {
    override fun borderColor(context: Context, status: AndesRadioButtonStatus) = R.color.andes_red_500.toAndesColor()
    override fun iconColor(context: Context, status: AndesRadioButtonStatus) = R.color.andes_white.toAndesColor()
    override fun backgroundColor(context: Context, status: AndesRadioButtonStatus) = R.color.andes_white.toAndesColor()
    override fun textColor(context: Context) = R.color.andes_text_color_primary.toAndesColor()
}
