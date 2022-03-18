package com.mercadolibre.android.andesui.buttongroup.utils

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistribution
import com.mercadolibre.android.andesui.tooltip.extensions.displaySize
import com.mercadolibre.android.andesui.utils.endToEndOf
import com.mercadolibre.android.andesui.utils.endToStartOf
import com.mercadolibre.android.andesui.utils.startToEndOf
import com.mercadolibre.android.andesui.utils.startToStartOf

internal object AndesButtonGroupUtils {

    fun getAutoDistribution(
        context: Context,
        andesButtonGroup: AndesButtonGroup
    ): AndesButtonGroupDistribution {
        var distribution: AndesButtonGroupDistribution

        val screenWidth = context.displaySize().x
        val buttonList = getButtonlist(andesButtonGroup)
        if (canHorizontal(context, buttonList, screenWidth)) {
            distribution = AndesButtonGroupDistribution.HORIZONTAL
        } else {
            distribution = AndesButtonGroupDistribution.VERTICAL
        }

        return distribution
    }

    fun canHorizontal(context: Context, andesButton: List<View>, screenSize: Int): Boolean {
        var buttonSizes = 0
        var paddingSize = context.resources.getDimensionPixelSize(R.dimen.andes_button_margin_small) * 2
        andesButton.map { button ->
            button.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            buttonSizes += button.measuredWidth + paddingSize
        }
        return buttonSizes <= screenSize
    }

    fun getButtonlist(andesButtonGroup: AndesButtonGroup, startIndex: Int = 0): List<View> {
        val list = arrayListOf<View>()
        if (startIndex <= andesButtonGroup.childCount - 1)
            for (i in startIndex until andesButtonGroup.childCount) {
                list.add(andesButtonGroup.getChildAt(i))
            }
        return list
    }

    fun centerHorizontalButtons(andesButtonGroup: AndesButtonGroup) : ConstraintSet.() -> Unit = {
        when (andesButtonGroup.childCount) {
            ONE_BUTTON -> {
                (andesButtonGroup.getChildAt(FIRST_BUTTON).id startToStartOf ConstraintSet.PARENT_ID)()
                (andesButtonGroup.getChildAt(FIRST_BUTTON).id endToEndOf ConstraintSet.PARENT_ID)()
            }
            TWO_BUTTON -> {
                (andesButtonGroup.getChildAt(FIRST_BUTTON).id startToStartOf ConstraintSet.PARENT_ID)()
                (andesButtonGroup.getChildAt(FIRST_BUTTON).id endToStartOf andesButtonGroup.getChildAt(SECOND_BUTTON).id)()
                (andesButtonGroup.getChildAt(SECOND_BUTTON).id startToEndOf andesButtonGroup.getChildAt(FIRST_BUTTON).id)()
                (andesButtonGroup.getChildAt(SECOND_BUTTON).id endToEndOf ConstraintSet.PARENT_ID)()
            }
            THREE_BUTTON -> {
                (andesButtonGroup.getChildAt(FIRST_BUTTON).id startToStartOf ConstraintSet.PARENT_ID)()
                (andesButtonGroup.getChildAt(FIRST_BUTTON).id endToStartOf andesButtonGroup.getChildAt(SECOND_BUTTON).id)()
                (andesButtonGroup.getChildAt(SECOND_BUTTON).id startToEndOf andesButtonGroup.getChildAt(FIRST_BUTTON).id)()
                (andesButtonGroup.getChildAt(SECOND_BUTTON).id endToStartOf andesButtonGroup.getChildAt(THIRD_BUTTON).id)()
                (andesButtonGroup.getChildAt(THIRD_BUTTON).id startToEndOf andesButtonGroup.getChildAt(SECOND_BUTTON).id)()
                (andesButtonGroup.getChildAt(THIRD_BUTTON).id endToEndOf ConstraintSet.PARENT_ID)()
            }
        }
    }
}
