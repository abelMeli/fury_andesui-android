package com.mercadolibre.android.andesui.buttongroup.margin

import android.content.Context
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.buttongroup.utils.AndesButtonGroupUtils
import com.mercadolibre.android.andesui.buttongroup.utils.FIRST_BUTTON
import com.mercadolibre.android.andesui.buttongroup.utils.ONE_BUTTON
import com.mercadolibre.android.andesui.buttongroup.utils.SECOND_BUTTON
import com.mercadolibre.android.andesui.buttongroup.utils.THIRD_BUTTON
import com.mercadolibre.android.andesui.buttongroup.utils.THREE_BUTTON
import com.mercadolibre.android.andesui.buttongroup.utils.TWO_BUTTON
import com.mercadolibre.android.andesui.tooltip.extensions.displaySize
import com.mercadolibre.android.andesui.utils.marginEndOf
import com.mercadolibre.android.andesui.utils.marginStartOf
import com.mercadolibre.android.andesui.utils.marginTopOf

internal interface AndesButtonGroupMarginInterface {
    fun getInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup,
        marginValue: Int
    ) : ConstraintSet.() -> Unit
}

internal object AndesButtonGroupMarginHorizontal : AndesButtonGroupMarginInterface {
    override fun getInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup,
        marginValue: Int
    ): ConstraintSet.() -> Unit = {
        val margin = marginValue / 2
        if (andesButtonGroup.childCount > ONE_BUTTON) {
            for (i in 0 until andesButtonGroup.childCount) {
                when (i) {
                    FIRST_BUTTON -> (andesButtonGroup.getChildAt(i).id marginEndOf margin)()
                    andesButtonGroup.childCount-1 -> (andesButtonGroup.getChildAt(i).id marginStartOf margin)()
                    else -> {
                        (andesButtonGroup.getChildAt(i).id marginEndOf margin)()
                        (andesButtonGroup.getChildAt(i).id marginStartOf margin)()
                    }
                }
            }
        }
    }
}

internal object AndesButtonGroupMarginVertical : AndesButtonGroupMarginInterface {
    override fun getInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup,
        marginValue: Int
    ): ConstraintSet.() -> Unit = {
        if (andesButtonGroup.childCount > ONE_BUTTON) {
            for (i in 0..andesButtonGroup.childCount - 1) {
                if (i > FIRST_BUTTON) {
                    (andesButtonGroup.getChildAt(i).id marginTopOf marginValue)()
                }
            }
        }
    }
}

internal object AndesButtonGroupMarginAuto : AndesButtonGroupMarginInterface {
    override fun getInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup,
        marginValue: Int
    ): ConstraintSet.() -> Unit = {
        if (andesButtonGroup.childCount > ONE_BUTTON) {
            val screenWidth = context.displaySize().x
            val buttonList = AndesButtonGroupUtils.getButtonlist(andesButtonGroup)

            if (AndesButtonGroupUtils.canHorizontal(context, buttonList, screenWidth)) {
                AndesButtonGroupMarginHorizontal.getInstructions(context, andesButtonGroup, marginValue)()
            } else {
                AndesButtonGroupMarginVertical.getInstructions(context, andesButtonGroup, marginValue)()
            }
        }
    }
}

internal object AndesButtonGroupMarginMixed : AndesButtonGroupMarginInterface {
    override fun getInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup,
        marginValue: Int
    ): ConstraintSet.() -> Unit = {
        if (andesButtonGroup.childCount == TWO_BUTTON) {
            AndesButtonGroupMarginAuto.getInstructions(context, andesButtonGroup, marginValue)()
        }
        if (andesButtonGroup.childCount == THREE_BUTTON) {
            val screenWidth = context.displaySize().x
            val buttonList = AndesButtonGroupUtils.getButtonlist(andesButtonGroup)
            buttonList.toMutableList().removeAt(FIRST_BUTTON)
            if (AndesButtonGroupUtils.canHorizontal(context, buttonList, screenWidth)) {
                (andesButtonGroup.getChildAt(SECOND_BUTTON).id marginEndOf marginValue / 2)()
                (andesButtonGroup.getChildAt(THIRD_BUTTON).id marginStartOf marginValue / 2)()
            }

            (andesButtonGroup.getChildAt(SECOND_BUTTON).id marginTopOf marginValue)()
            (andesButtonGroup.getChildAt(THIRD_BUTTON).id marginTopOf marginValue)()
        }
    }
}
