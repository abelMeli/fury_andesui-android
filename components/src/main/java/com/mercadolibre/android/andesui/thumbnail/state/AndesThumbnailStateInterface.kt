package com.mercadolibre.android.andesui.thumbnail.state

import android.content.Context
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy

/**
 * Defines all style related properties that an AndesThumbnail needs to be drawn properly.
 * Those properties change depending on the style of the message.
 *
 */
internal interface AndesThumbnailStateInterface {
    fun backgroundColor(context: Context, hierarchy: AndesThumbnailHierarchy, accentColor: AndesColor): AndesColor
    fun iconColor(context: Context, hierarchy: AndesThumbnailHierarchy, accentColor: AndesColor): AndesColor
}

internal class AndesDisabledThumbnailState : AndesThumbnailStateInterface {
    override fun backgroundColor(context: Context, hierarchy: AndesThumbnailHierarchy, accentColor: AndesColor):
        AndesColor {
        return if (hierarchy == AndesThumbnailHierarchy.DEFAULT) {
            R.color.andes_white.toAndesColor()
        } else {
            R.color.andes_gray_100_solid.toAndesColor()
        }
    }

    override fun iconColor(context: Context, hierarchy: AndesThumbnailHierarchy, accentColor: AndesColor): AndesColor =
        R.color.andes_gray_250_solid.toAndesColor()
}

internal class AndesEnabledThumbnailState : AndesThumbnailStateInterface {
    override fun backgroundColor(context: Context, hierarchy: AndesThumbnailHierarchy, accentColor: AndesColor):
        AndesColor = when (hierarchy) {
            AndesThumbnailHierarchy.DEFAULT -> R.color.andes_white.toAndesColor()
            AndesThumbnailHierarchy.LOUD -> accentColor
            AndesThumbnailHierarchy.QUIET -> {
                val color = accentColor.copy()
                color.alpha = ALPHA_10
                color
            }
    }

    override fun iconColor(context: Context, hierarchy: AndesThumbnailHierarchy, accentColor: AndesColor): AndesColor =
        when (hierarchy) {
        AndesThumbnailHierarchy.DEFAULT -> R.color.andes_gray_900_solid.toAndesColor()
        AndesThumbnailHierarchy.LOUD -> R.color.andes_white.toAndesColor()
        AndesThumbnailHierarchy.QUIET -> accentColor
    }

    companion object {
        const val ALPHA_10 = 0.1f
    }
}
