package com.mercadolibre.android.andesui.tag.type

import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor

/**
 * Defines all types related properties that an [AndesTag] needs to be drawn properly.
 * Those properties change depending on the style of the tag.
 */
internal interface AndesSimpleTagTypeInterface {

    /**
     * @return an [AndesColor] that contains the background color data for the tag.
     */
    fun backgroundColor(): AndesColor

    /**
     * @return an [AndesColor] that contains the border color data for the tag.
     */
    fun borderColor(): AndesColor

    /**
     * @return an [AndesColor] that contains the text color data for the tag.
     */
    fun textColor(): AndesColor

    /**
     * @return an [AndesColor] that contains the dismiss color data for the tag.
     */
    fun dismissColor(): AndesColor
}

internal class AndesNeutralTagType : AndesSimpleTagTypeInterface {
    override fun backgroundColor() = R.color.andes_transparent.toAndesColor()
    override fun borderColor() = R.color.andes_gray_250_solid.toAndesColor()
    override fun textColor() = R.color.andes_text_color_primary.toAndesColor()
    override fun dismissColor() = R.color.andes_gray_550_solid.toAndesColor()
}

internal class AndesHighlightTagType : AndesSimpleTagTypeInterface {
    override fun backgroundColor() = R.color.andes_accent_color_100.toAndesColor()
    override fun borderColor() = R.color.andes_accent_color_500.toAndesColor()
    override fun textColor() = R.color.andes_accent_color_500.toAndesColor()
    override fun dismissColor() = R.color.andes_accent_color_500.toAndesColor()
}

internal class AndesSuccessTagType : AndesSimpleTagTypeInterface {
    override fun backgroundColor() = R.color.andes_green_100.toAndesColor()
    override fun borderColor() = R.color.andes_green_500.toAndesColor()
    override fun textColor() = R.color.andes_green_500.toAndesColor()
    override fun dismissColor() = R.color.andes_green_500.toAndesColor()
}

internal class AndesWarningTagType : AndesSimpleTagTypeInterface {
    override fun backgroundColor() = R.color.andes_orange_100.toAndesColor()
    override fun borderColor() = R.color.andes_orange_500.toAndesColor()
    override fun textColor() = R.color.andes_orange_500.toAndesColor()
    override fun dismissColor() = R.color.andes_orange_500.toAndesColor()
}

internal class AndesErrorTagType : AndesSimpleTagTypeInterface {
    override fun backgroundColor() = R.color.andes_red_100.toAndesColor()
    override fun borderColor() = R.color.andes_red_500.toAndesColor()
    override fun textColor() = R.color.andes_red_500.toAndesColor()
    override fun dismissColor() = R.color.andes_red_500.toAndesColor()
}
