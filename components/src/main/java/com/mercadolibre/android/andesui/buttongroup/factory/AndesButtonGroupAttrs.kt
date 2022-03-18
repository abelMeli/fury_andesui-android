package com.mercadolibre.android.andesui.buttongroup.factory

import android.content.Context
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.buttongroup.align.AndesButtonGroupAlign
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistribution
import com.mercadolibre.android.andesui.buttongroup.type.AndesButtonGroupType

/**
 * The data class that contains the public components of the button group.
 */

internal data class AndesButtonGroupAttrs(
    val andesButtonGroupDistribution: AndesButtonGroupDistribution = AndesButtonGroupDistribution.HORIZONTAL,
    val andesButtonGroupType: AndesButtonGroupType = AndesButtonGroupType.FullWidth
)

internal object AndesButtonGroupAttrsParser {

    private const val ANDES_BUTTONGROUP_DISTRIBUTION_HORIZONTAL = 2000
    private const val ANDES_BUTTONGROUP_DISTRIBUTION_VERTICAL = 2001
    private const val ANDES_BUTTONGROUP_DISTRIBUTION_AUTO = 2002
    private const val ANDES_BUTTONGROUP_DISTRIBUTION_MIXED = 2003

    private const val ANDES_BUTTONGROUP_TYPE_FULL_WIDTH = 1000
    private const val ANDES_BUTTONGROUP_TYPE_RESPONSIVE = 1001

    private const val ANDES_BUTTONGROUP_ALIGN_LEFT = 3000
    private const val ANDES_BUTTONGROUP_ALIGN_RIGHT = 3001
    private const val ANDES_BUTTONGROUP_ALIGN_CENTER = 3002


    fun parse(context: Context, attr: AttributeSet?): AndesButtonGroupAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesButtonGroupStyleable)

        val distribution = when (typedArray.getInt(
            R.styleable.AndesButtonGroupStyleable_andesButtonGroupDistribution,
            ANDES_BUTTONGROUP_DISTRIBUTION_HORIZONTAL)
        ) {
            ANDES_BUTTONGROUP_DISTRIBUTION_HORIZONTAL -> AndesButtonGroupDistribution.HORIZONTAL
            ANDES_BUTTONGROUP_DISTRIBUTION_VERTICAL -> AndesButtonGroupDistribution.VERTICAL
            ANDES_BUTTONGROUP_DISTRIBUTION_AUTO -> AndesButtonGroupDistribution.AUTO
            ANDES_BUTTONGROUP_DISTRIBUTION_MIXED -> AndesButtonGroupDistribution.MIXED
            else -> AndesButtonGroupDistribution.HORIZONTAL
        }

        val align = when (typedArray.getInt(
            R.styleable.AndesButtonGroupStyleable_andesButtonGroupAlign,
            ANDES_BUTTONGROUP_ALIGN_LEFT)
        ) {
            ANDES_BUTTONGROUP_ALIGN_LEFT -> AndesButtonGroupAlign.LEFT
            ANDES_BUTTONGROUP_ALIGN_RIGHT -> AndesButtonGroupAlign.RIGHT
            ANDES_BUTTONGROUP_ALIGN_CENTER -> AndesButtonGroupAlign.CENTER
            else -> AndesButtonGroupAlign.LEFT
        }

        val type = when (typedArray.getInt(
            R.styleable.AndesButtonGroupStyleable_andesButtonGroupType,
            ANDES_BUTTONGROUP_TYPE_FULL_WIDTH)
        ) {
            ANDES_BUTTONGROUP_TYPE_FULL_WIDTH -> AndesButtonGroupType.FullWidth
            ANDES_BUTTONGROUP_TYPE_RESPONSIVE -> AndesButtonGroupType.Responsive(align)
            else -> AndesButtonGroupType.FullWidth
        }

        return AndesButtonGroupAttrs(
            andesButtonGroupDistribution = distribution,
            andesButtonGroupType = type
        ).also { typedArray.recycle() }
    }
}
