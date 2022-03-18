package com.mercadolibre.android.andesui.buttongroup.distribution

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.buttongroup.utils.AndesButtonGroupUtils
import com.mercadolibre.android.andesui.buttongroup.utils.DEFAULT_WEIGHT
import com.mercadolibre.android.andesui.buttongroup.utils.FIRST_BUTTON
import com.mercadolibre.android.andesui.buttongroup.utils.LARGE_WEIGHT
import com.mercadolibre.android.andesui.buttongroup.utils.ONE_BUTTON
import com.mercadolibre.android.andesui.buttongroup.utils.SECOND_BUTTON
import com.mercadolibre.android.andesui.buttongroup.utils.THIRD_BUTTON
import com.mercadolibre.android.andesui.buttongroup.utils.THREE_BUTTON
import com.mercadolibre.android.andesui.buttongroup.utils.TWO_BUTTON
import com.mercadolibre.android.andesui.tooltip.extensions.displaySize
import com.mercadolibre.android.andesui.utils.endToEndOf
import com.mercadolibre.android.andesui.utils.endToStartOf
import com.mercadolibre.android.andesui.utils.startToEndOf
import com.mercadolibre.android.andesui.utils.startToStartOf
import com.mercadolibre.android.andesui.utils.topToBottomOf
import com.mercadolibre.android.andesui.utils.topToTopOf

internal interface AndesButtonGroupDistributionInterface {
    fun getInstructions(context: Context, andesButtonGroup: AndesButtonGroup) : ConstraintSet.() -> Unit
}

internal object AndesButtonGroupDistributionHorizontal : AndesButtonGroupDistributionInterface {

    override fun getInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup
    ) : ConstraintSet.() -> Unit = {
        val screenWidth = context.displaySize().x
        val limitView = screenWidth / andesButtonGroup.childCount

        if (andesButtonGroup.childCount > ONE_BUTTON) {
            for (i in 0 until andesButtonGroup.childCount) {
                andesButtonGroup.getChildAt(i).measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
                val weight = if (andesButtonGroup.getChildAt(i).measuredWidth > limitView) {
                    LARGE_WEIGHT
                } else {
                    DEFAULT_WEIGHT
                }
                setHorizontalWeight(
                    andesButtonGroup.getChildAt(i).id,
                    weight
                )
            }
        }
    }
}

internal object AndesButtonGroupDistributionVertical : AndesButtonGroupDistributionInterface {

    override fun getInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup
    ) : ConstraintSet.() -> Unit = {
        for (i in 0 until andesButtonGroup.childCount) {
            if (i == 0) {
                (andesButtonGroup.getChildAt(0).id topToTopOf ConstraintSet.PARENT_ID)()
            } else {
                (andesButtonGroup.getChildAt(i).id topToBottomOf andesButtonGroup.getChildAt(i-1).id)()
            }
        }
    }
}

internal object AndesButtonGroupDistributionAuto : AndesButtonGroupDistributionInterface {
    override fun getInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup
    ) : ConstraintSet.() -> Unit = {
        val screenWidth = context.displaySize().x
        val buttonList = AndesButtonGroupUtils.getButtonlist(andesButtonGroup)

        if (AndesButtonGroupUtils.canHorizontal(context, buttonList, screenWidth)) {
            AndesButtonGroupDistributionHorizontal.getInstructions(context, andesButtonGroup)()
        } else {
            AndesButtonGroupDistributionVertical.getInstructions(context, andesButtonGroup)()
        }
    }
}

internal object AndesButtonGroupDistributionMixed : AndesButtonGroupDistributionInterface {

    override fun getInstructions(context: Context, andesButtonGroup: AndesButtonGroup): ConstraintSet.() -> Unit = {

        when (andesButtonGroup.childCount) {
            ONE_BUTTON -> {
                (andesButtonGroup.getChildAt(FIRST_BUTTON).id startToStartOf ConstraintSet.PARENT_ID)()
                (andesButtonGroup.getChildAt(FIRST_BUTTON).id endToEndOf ConstraintSet.PARENT_ID)()
            }
            TWO_BUTTON -> {
                AndesButtonGroupDistributionAuto.getInstructions(context, andesButtonGroup)()
            }
            THREE_BUTTON -> {
                val screenWidth = context.displaySize().x
                (andesButtonGroup.getChildAt(FIRST_BUTTON).id startToStartOf ConstraintSet.PARENT_ID)()
                (andesButtonGroup.getChildAt(FIRST_BUTTON).id endToEndOf ConstraintSet.PARENT_ID)()

                val buttonList = AndesButtonGroupUtils.getButtonlist(andesButtonGroup)
                buttonList.toMutableList().removeAt(FIRST_BUTTON)
                if (AndesButtonGroupUtils.canHorizontal(context, buttonList , screenWidth)) {
                    setConstraintHorizontal(andesButtonGroup)()
                } else {
                    setConstraintVertical(andesButtonGroup)()
                }
            }
        }
    }

    private fun setConstraintHorizontal(andesButtonGroup: AndesButtonGroup) : ConstraintSet.() -> Unit = {
        (andesButtonGroup.getChildAt(SECOND_BUTTON).id topToBottomOf andesButtonGroup.getChildAt(FIRST_BUTTON).id)()
        (andesButtonGroup.getChildAt(SECOND_BUTTON).id startToStartOf ConstraintSet.PARENT_ID)()
        (andesButtonGroup.getChildAt(SECOND_BUTTON).id endToStartOf andesButtonGroup.getChildAt(THIRD_BUTTON).id)()
        (andesButtonGroup.getChildAt(THIRD_BUTTON).id topToBottomOf andesButtonGroup.getChildAt(FIRST_BUTTON).id)()
        (andesButtonGroup.getChildAt(THIRD_BUTTON).id endToEndOf ConstraintSet.PARENT_ID)()
        (andesButtonGroup.getChildAt(THIRD_BUTTON).id startToEndOf andesButtonGroup.getChildAt(SECOND_BUTTON).id)()
    }

    private fun setConstraintVertical(andesButtonGroup: AndesButtonGroup) : ConstraintSet.() -> Unit = {
        (andesButtonGroup.getChildAt(FIRST_BUTTON).id startToStartOf ConstraintSet.PARENT_ID)()
        (andesButtonGroup.getChildAt(FIRST_BUTTON).id endToEndOf ConstraintSet.PARENT_ID)()
        (andesButtonGroup.getChildAt(SECOND_BUTTON).id startToStartOf ConstraintSet.PARENT_ID)()
        (andesButtonGroup.getChildAt(SECOND_BUTTON).id endToEndOf ConstraintSet.PARENT_ID)()
        (andesButtonGroup.getChildAt(THIRD_BUTTON).id startToStartOf ConstraintSet.PARENT_ID)()
        (andesButtonGroup.getChildAt(THIRD_BUTTON).id endToEndOf ConstraintSet.PARENT_ID)()
        (andesButtonGroup.getChildAt(SECOND_BUTTON).id topToBottomOf andesButtonGroup.getChildAt(FIRST_BUTTON).id)()
        (andesButtonGroup.getChildAt(THIRD_BUTTON).id topToBottomOf andesButtonGroup.getChildAt(SECOND_BUTTON).id)()
    }
}
