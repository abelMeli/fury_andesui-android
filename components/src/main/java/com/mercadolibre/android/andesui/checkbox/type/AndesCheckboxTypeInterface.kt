package com.mercadolibre.android.andesui.checkbox.type

import android.content.Context
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor

internal interface AndesCheckboxTypeInterface {
    fun borderColor(context: Context, status: AndesCheckboxStatus): AndesColor
    fun iconColor(context: Context, status: AndesCheckboxStatus): AndesColor
    fun backgroundColor(context: Context, status: AndesCheckboxStatus): AndesColor
    fun textColor(): AndesTextViewColor
}

internal object AndesCheckboxTypeIdle : AndesCheckboxTypeInterface {
    override fun borderColor(context: Context, status: AndesCheckboxStatus): AndesColor {
        return if (status == AndesCheckboxStatus.UNSELECTED) {
            R.color.andes_gray_250_solid.toAndesColor()
        } else {
            R.color.andes_accent_color_500.toAndesColor()
        }
    }
    override fun iconColor(context: Context, status: AndesCheckboxStatus) = R.color.andes_white.toAndesColor()
    override fun backgroundColor(context: Context, status: AndesCheckboxStatus): AndesColor {
        return if (status == AndesCheckboxStatus.UNSELECTED) {
            R.color.andes_white.toAndesColor()
        } else {
            R.color.andes_accent_color_500.toAndesColor()
        }
    }
    override fun textColor() = AndesTextViewColor.Primary
}

internal object AndesCheckboxTypeDisabled : AndesCheckboxTypeInterface {
    override fun borderColor(context: Context, status: AndesCheckboxStatus) = R.color.andes_gray_100_solid.toAndesColor()
    override fun iconColor(context: Context, status: AndesCheckboxStatus) = R.color.andes_gray_250_solid.toAndesColor()
    override fun backgroundColor(context: Context, status: AndesCheckboxStatus): AndesColor {
        return if (status == AndesCheckboxStatus.UNSELECTED) {
            R.color.andes_white.toAndesColor()
        } else {
            R.color.andes_gray_100_solid.toAndesColor()
        }
    }
    override fun textColor() = AndesTextViewColor.Disabled
}

internal object AndesCheckboxTypeError : AndesCheckboxTypeInterface {
    override fun borderColor(context: Context, status: AndesCheckboxStatus) = R.color.andes_red_500.toAndesColor()
    override fun iconColor(context: Context, status: AndesCheckboxStatus) = R.color.andes_white.toAndesColor()
    override fun backgroundColor(context: Context, status: AndesCheckboxStatus) = R.color.andes_white.toAndesColor()
    override fun textColor() = AndesTextViewColor.Primary
}
