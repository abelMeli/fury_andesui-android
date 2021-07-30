package com.mercadolibre.android.andesui.linearprogress.factory

import android.content.Context
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.linearprogress.size.AndesLinearProgressSizeInterface

internal data class AndesLinearProgressConfiguration(
    val height: Float,
    val indicatorTint: Int,
    val trackTint: Int,
    val isSplit: Boolean,
    val splitSize: Float,
    val cornerRadius: Float,
    val numberOfSteps: Int
)

internal object AndesLinearProgressConfigurationFactory {

    private const val MAX_NUMBER_OF_STEPS = 20
    private const val MIN_NUMBER_OF_STEPS = 2
    private const val EXCEPTION_MESSAGE =
        "Value between $MIN_NUMBER_OF_STEPS and $MAX_NUMBER_OF_STEPS"

    fun create(
        context: Context,
        andesLinearProgressAttrs: AndesLinearProgressAttrs
    ): AndesLinearProgressConfiguration {

        return AndesLinearProgressConfiguration(
            height = resolveHeight(context, andesLinearProgressAttrs.andesLinearProgressSize.size),
            indicatorTint = resolveIndicatorTint(context, andesLinearProgressAttrs.indicatorTint),
            trackTint = resolveTrackTint(context, andesLinearProgressAttrs.trackTint),
            isSplit = andesLinearProgressAttrs.isSplit,
            splitSize = resolveSplitSize(
                context,
                andesLinearProgressAttrs.andesLinearProgressSize.size
            ),
            cornerRadius = resolveCornerRadius(
                context,
                andesLinearProgressAttrs.andesLinearProgressSize.size
            ),
            numberOfSteps = resolveNumberOfSteps(andesLinearProgressAttrs.numberOfSteps)
        )
    }

    private fun resolveHeight(
        context: Context,
        andesLinearProgressSize: AndesLinearProgressSizeInterface
    ) = andesLinearProgressSize.height(context)

    private fun resolveIndicatorTint(context: Context, tint: Int): Int {
        if (tint == 0) {
            return context.resources.getColor(R.color.andes_blue_ml_500)
        }
        return tint
    }

    private fun resolveTrackTint(context: Context, tint: Int): Int {
        if (tint == 0) {
            return context.resources.getColor(R.color.andes_gray_100)
        }
        return tint
    }

    private fun resolveSplitSize(
        context: Context,
        andesLinearProgressSize: AndesLinearProgressSizeInterface
    ) = andesLinearProgressSize.splitSize(context)

    private fun resolveCornerRadius(
        context: Context,
        andesLinearProgressSize: AndesLinearProgressSizeInterface
    ) = andesLinearProgressSize.cornerRadius(context)

    /**
     * @throws IllegalArgumentException Throw IllegalArgumentException if the given value is not
     * between [MIN_NUMBER_OF_STEPS] and [MAX_NUMBER_OF_STEPS]
     */
    private fun resolveNumberOfSteps(numberOfSteps: Int): Int =
        if (numberOfSteps in MIN_NUMBER_OF_STEPS..MAX_NUMBER_OF_STEPS) {
            numberOfSteps
        } else throw IllegalArgumentException(EXCEPTION_MESSAGE)
}
