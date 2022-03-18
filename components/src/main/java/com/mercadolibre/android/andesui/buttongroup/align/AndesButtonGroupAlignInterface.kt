package com.mercadolibre.android.andesui.buttongroup.align

import android.content.Context
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistribution
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistributionHorizontal
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistributionVertical
import com.mercadolibre.android.andesui.buttongroup.utils.AndesButtonGroupUtils
import com.mercadolibre.android.andesui.buttongroup.utils.FIRST_BUTTON
import com.mercadolibre.android.andesui.utils.endToEndOf
import com.mercadolibre.android.andesui.utils.endToStartOf
import com.mercadolibre.android.andesui.utils.startToEndOf
import com.mercadolibre.android.andesui.utils.startToStartOf

internal interface AndesButtonGroupAlignInterface {

    fun getInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup,
        andesDistribution: AndesButtonGroupDistribution
    ) : ConstraintSet.() -> Unit
}

internal object AndesButtonGroupLeft : AndesButtonGroupAlignInterface {
    override fun getInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup,
        andesDistribution: AndesButtonGroupDistribution
    ) : ConstraintSet.() -> Unit = {

        if (andesDistribution.distribution == AndesButtonGroupDistributionVertical) {
            for (i in 0 until andesButtonGroup.childCount) {
                (andesButtonGroup.getChildAt(i).id startToStartOf ConstraintSet.PARENT_ID)()
            }
        }

        if (andesDistribution.distribution == AndesButtonGroupDistributionHorizontal) {
            for (i in 0 until andesButtonGroup.childCount) {
                if (i == FIRST_BUTTON) {
                    (andesButtonGroup.getChildAt(i).id startToStartOf ConstraintSet.PARENT_ID)()
                } else {
                    (andesButtonGroup.getChildAt(i).id startToEndOf andesButtonGroup.getChildAt(i-1).id)()
                }
            }
        }
    }

}

internal object AndesButtonGroupRight: AndesButtonGroupAlignInterface {
    override fun getInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup,
        andesDistribution: AndesButtonGroupDistribution
    ) : ConstraintSet.() -> Unit = {

        if (andesDistribution.distribution == AndesButtonGroupDistributionVertical) {
            for (i in 0 until andesButtonGroup.childCount) {
                (andesButtonGroup.getChildAt(i).id endToEndOf ConstraintSet.PARENT_ID)()
            }
        }

        if (andesDistribution.distribution  == AndesButtonGroupDistributionHorizontal) {
            for (i in andesButtonGroup.childCount-1 downTo FIRST_BUTTON) {
                if (i == andesButtonGroup.childCount-1) {
                    (andesButtonGroup.getChildAt(i).id endToEndOf ConstraintSet.PARENT_ID)()
                } else {
                    (andesButtonGroup.getChildAt(i).id endToStartOf andesButtonGroup.getChildAt(i+1).id)()
                }
            }
        }
    }

}

internal object AndesButtonGroupCenter : AndesButtonGroupAlignInterface {
    override fun getInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup,
        andesDistribution: AndesButtonGroupDistribution
    ) : ConstraintSet.() -> Unit = {

        if (andesDistribution.distribution == AndesButtonGroupDistributionVertical) {
            for (i in FIRST_BUTTON until andesButtonGroup.childCount) {
                (andesButtonGroup.getChildAt(i).id endToEndOf ConstraintSet.PARENT_ID)()
                (andesButtonGroup.getChildAt(i).id startToStartOf ConstraintSet.PARENT_ID)()
            }
        }

        if (andesDistribution.distribution == AndesButtonGroupDistributionHorizontal) {
            AndesButtonGroupUtils.centerHorizontalButtons(andesButtonGroup)()
        }
    }

}
