package com.mercadolibre.android.andesui.buttongroup.factory

import android.content.Context
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.button.size.AndesButtonSize
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistribution
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistributionMixed
import com.mercadolibre.android.andesui.buttongroup.margin.AndesButtonGroupMargin
import com.mercadolibre.android.andesui.buttongroup.type.AndesButtonGroupType
import com.mercadolibre.android.andesui.buttongroup.utils.AndesButtonGroupUtils
import com.mercadolibre.android.andesui.buttongroup.utils.FIRST_BUTTON
import com.mercadolibre.android.andesui.buttongroup.utils.THREE_BUTTON
import com.mercadolibre.android.andesui.buttongroup.utils.TWO_BUTTON

internal data class AndesButtonGroupConfiguration(
    val instructions: List<ConstraintSet.() -> Unit>,
    val layoutWidth: Int
)

internal object AndesButtonGroupConfigurationFactory {

    private const val NO_WIDTH = 0

    fun create(
        context: Context,
        andesButtonGroupAttrs: AndesButtonGroupAttrs,
        andesButtonGroupComponent: AndesButtonGroup
    ): AndesButtonGroupConfiguration {
        val margin = getMargin(context, andesButtonGroupComponent.getChildAt(FIRST_BUTTON) as AndesButton)
        val buttonGroupDistribution = getDistribution(
            context,
            andesButtonGroupAttrs.andesButtonGroupDistribution,
            andesButtonGroupComponent
        )
        val buttonGroupType = getType(
            andesButtonGroupAttrs.andesButtonGroupType,
            andesButtonGroupAttrs.andesButtonGroupDistribution
        )
        return AndesButtonGroupConfiguration(
                instructions = getInstructions(
                    context,
                    andesButtonGroupComponent,
                    buttonGroupDistribution,
                    buttonGroupType,
                    margin
                ),
                layoutWidth = getLayoutWidth(
                    buttonGroupDistribution,
                    andesButtonGroupAttrs.andesButtonGroupType,
                    andesButtonGroupComponent.childCount
                )
            )
    }

    private fun getDistribution(
        context: Context,
        distribution: AndesButtonGroupDistribution,
        andesButtonGroupComponent: AndesButtonGroup
    ): AndesButtonGroupDistribution {
        return if (distribution == AndesButtonGroupDistribution.AUTO ||
            distribution == AndesButtonGroupDistribution.MIXED && andesButtonGroupComponent.childCount <= TWO_BUTTON) {
            AndesButtonGroupUtils.getAutoDistribution(context, andesButtonGroupComponent)
        } else {
            distribution
        }
    }

    private fun getType(
        andesButtonGroupType: AndesButtonGroupType,
        andesButtonGroupDistribution: AndesButtonGroupDistribution
    ): AndesButtonGroupType {
        return if (andesButtonGroupDistribution.distribution == AndesButtonGroupDistributionMixed) {
            AndesButtonGroupType.FullWidth
        } else {
            andesButtonGroupType
        }
    }

    private fun getInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup,
        buttonDistribution: AndesButtonGroupDistribution,
        buttonType: AndesButtonGroupType,
        margin: Int
    ) : List<ConstraintSet.() -> Unit> {
        val instructionsList = mutableListOf<ConstraintSet.() -> Unit>()
        instructionsList.add(buttonDistribution.distribution.getInstructions(context, andesButtonGroup))
        instructionsList.add(buttonType.type.getInstructions(context, andesButtonGroup, buttonDistribution))
        instructionsList.addAll(getMarginInstructions(context, andesButtonGroup, buttonDistribution, margin))
        return instructionsList
    }

    internal fun getMargin(context: Context, andesButton: AndesButton) : Int {
        return when (andesButton.size) {
            AndesButtonSize.LARGE ->
                context.resources.getDimensionPixelSize(R.dimen.andes_button_group_margin_large)
            AndesButtonSize.MEDIUM ->
                context.resources.getDimensionPixelSize(R.dimen.andes_button_group_margin_medium)
            else -> context.resources.getDimensionPixelSize(R.dimen.andes_button_group_margin_small)
        }
    }

    private fun getMarginInstructions(
        context: Context,
        andesButtonGroup: AndesButtonGroup,
        andesDistribution: AndesButtonGroupDistribution,
        marginValue: Int
    ) : List<ConstraintSet.() -> Unit> {
        val instructionsList = mutableListOf<ConstraintSet.() -> Unit>()
        when (andesDistribution) {
            AndesButtonGroupDistribution.HORIZONTAL -> instructionsList.add(
                AndesButtonGroupMargin.HORIZONTAL.margin.getInstructions(
                    context,
                    andesButtonGroup,
                    marginValue
                ))
            AndesButtonGroupDistribution.VERTICAL -> instructionsList.add(
                AndesButtonGroupMargin.VERTICAL.margin.getInstructions(
                    context,
                    andesButtonGroup,
                    marginValue
                ))
            AndesButtonGroupDistribution.AUTO -> instructionsList.add(
                AndesButtonGroupMargin.AUTO.margin.getInstructions(
                    context,
                    andesButtonGroup,
                    marginValue
                ))
            else -> instructionsList.add(AndesButtonGroupMargin.MIXED.margin.getInstructions(
                context, andesButtonGroup, marginValue
            ))
        }

        return instructionsList
    }

    private fun getLayoutWidth(
        buttonGroupDistribution: AndesButtonGroupDistribution,
        buttonGroupType: AndesButtonGroupType,
        buttonSize: Int
    ): Int {
        return when {
            buttonGroupDistribution == AndesButtonGroupDistribution.MIXED && buttonSize == THREE_BUTTON ||
            buttonGroupType == AndesButtonGroupType.FullWidth -> NO_WIDTH
            else -> ViewGroup.LayoutParams.WRAP_CONTENT
        }
    }
}
