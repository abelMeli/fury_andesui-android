package com.mercadolibre.android.andesui.snackbar.type

import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor

/**
 * Defines all types related properties that an [AndesSnackbar] needs to be drawn properly.
 * Those properties change depending on the style of the tag.
 */
internal interface AndesSnackbarTypeInterface {

    /**
     * Returns a [AndesColor] that contains the primary color data for the tag.
     *
     * @return a [AndesColor] that contains the primary color data for the tag.
     */
    fun primaryColor(): AndesColor

    /**
     * Returns a [AndesColor] that contains the secondary color data for the tag.
     *
     * @return a [AndesColor] that contains the secondary color data for the tag.
     */
    fun secondaryColor(): AndesColor
}

internal class AndesSnackbarNeutralType : AndesSnackbarTypeInterface {
    override fun primaryColor() = R.color.andes_gray_900_solid.toAndesColor()
    override fun secondaryColor() = R.color.andes_gray_070_solid.toAndesColor()
}

internal class AndesSnackbarSuccessType : AndesSnackbarTypeInterface {
    override fun primaryColor() = R.color.andes_green_500.toAndesColor()
    override fun secondaryColor() = R.color.andes_gray_070_solid.toAndesColor()
}

internal class AndesSnackbarErrorType : AndesSnackbarTypeInterface {
    override fun primaryColor() = R.color.andes_red_500.toAndesColor()
    override fun secondaryColor() = R.color.andes_gray_070_solid.toAndesColor()
}
