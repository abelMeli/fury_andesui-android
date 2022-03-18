package com.mercadolibre.android.andesui.buttongroup.type

import android.content.Context
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.buttongroup.align.AndesButtonGroupAlign
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistribution
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistributionHorizontal
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistributionVertical
import com.mercadolibre.android.andesui.buttongroup.utils.FIRST_BUTTON
import com.mercadolibre.android.andesui.buttongroup.utils.ONE_BUTTON
import com.mercadolibre.android.andesui.utils.endToEndOf
import com.mercadolibre.android.andesui.utils.endToStartOf
import com.mercadolibre.android.andesui.utils.startToEndOf
import com.mercadolibre.android.andesui.utils.startToStartOf

internal interface AndesButtonGroupTypeInterface {
    val buttonGroupAlign: AndesButtonGroupAlign

    fun getInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup,
        andesDistribution: AndesButtonGroupDistribution
    ) : ConstraintSet.() -> Unit
}

internal object AndesButtonGroupFullWidth: AndesButtonGroupTypeInterface {

    override val buttonGroupAlign = AndesButtonGroupAlign.CENTER

    override fun getInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup,
        andesDistribution: AndesButtonGroupDistribution
    ) : ConstraintSet.() -> Unit = {

        when(andesDistribution.distribution) {
            AndesButtonGroupDistributionVertical -> {
                for (i in FIRST_BUTTON until andesButtonGroup.childCount) {
                    oneButtonFullWidth(i, andesButtonGroup)()
                }
            }
            AndesButtonGroupDistributionHorizontal -> {
                for (index in FIRST_BUTTON until andesButtonGroup.childCount) {
                    if (andesButtonGroup.childCount == ONE_BUTTON) {
                        oneButtonFullWidth(FIRST_BUTTON, andesButtonGroup)()
                    } else {
                        when (index) {
                            FIRST_BUTTON -> {
                                (andesButtonGroup.getChildAt(index).id startToStartOf ConstraintSet.PARENT_ID)()
                                (andesButtonGroup.getChildAt(index).id endToStartOf andesButtonGroup.getChildAt(index+1).id)()
                            }
                            andesButtonGroup.childCount-1 -> {
                                (andesButtonGroup.getChildAt(index).id endToEndOf ConstraintSet.PARENT_ID)()
                                (andesButtonGroup.getChildAt(index).id startToEndOf andesButtonGroup.getChildAt(index-1).id)()
                            }
                            else -> {
                                (andesButtonGroup.getChildAt(index).id startToEndOf andesButtonGroup.getChildAt(index-1).id)()
                                (andesButtonGroup.getChildAt(index).id endToStartOf andesButtonGroup.getChildAt(index+1).id)()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun oneButtonFullWidth(index: Int, andesButtonGroup: AndesButtonGroup): ConstraintSet.() -> Unit = {
        (andesButtonGroup.getChildAt(index).id startToStartOf ConstraintSet.PARENT_ID)()
        (andesButtonGroup.getChildAt(index).id endToEndOf  ConstraintSet.PARENT_ID)()
    }

}

internal class AndesButtonGroupResponsive(override val buttonGroupAlign: AndesButtonGroupAlign): AndesButtonGroupTypeInterface {
    override fun getInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup,
        andesDistribution: AndesButtonGroupDistribution
    ): ConstraintSet.() -> Unit = {
        buttonGroupAlign.align.getInstructions(context, andesButtonGroup, andesDistribution)()
    }
}
