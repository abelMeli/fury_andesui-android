package com.mercadolibre.android.andesui.linearprogress.factory

import android.content.Context
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.linearprogress.AndesLinearProgressIndicatorDeterminate.Companion.IS_SPLIT_DEFAULT
import com.mercadolibre.android.andesui.linearprogress.AndesLinearProgressIndicatorDeterminate.Companion.NUMBER_OF_STEPS_DEFAULT
import com.mercadolibre.android.andesui.linearprogress.AndesLinearProgressIndicatorDeterminate.Companion.TINT_DEFAULT
import com.mercadolibre.android.andesui.linearprogress.size.AndesLinearProgressSize

/**
 * The data class that contains the public components of the linear progress.
 */
internal data class AndesLinearProgressAttrs(
    val andesLinearProgressSize: AndesLinearProgressSize,
    val indicatorTint: Int,
    val trackTint: Int,
    val isSplit: Boolean,
    val numberOfSteps: Int
)

internal object AndesLinearProgressAttrsParser {

    private const val ANDES_LINEAR_PROGRESS_SIZE_LARGE = "200"
    private const val ANDES_LINEAR_PROGRESS_SIZE_SMALL = "201"

    fun parse(context: Context, attr: AttributeSet?): AndesLinearProgressAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesLinearProgress)

        val size =
            when (typedArray.getString(R.styleable.AndesLinearProgress_andesLinearProgressSize)) {
                ANDES_LINEAR_PROGRESS_SIZE_LARGE -> AndesLinearProgressSize.LARGE
                ANDES_LINEAR_PROGRESS_SIZE_SMALL -> AndesLinearProgressSize.SMALL
                else -> AndesLinearProgressSize.SMALL
            }

        return AndesLinearProgressAttrs(
            andesLinearProgressSize = size,
            indicatorTint = typedArray.getColor(
                R.styleable.AndesLinearProgress_andesLinearProgressIndicatorTint,
                TINT_DEFAULT
            ),
            trackTint = typedArray.getColor(
                R.styleable.AndesLinearProgress_andesLinearProgressTrackTint,
                TINT_DEFAULT
            ),
            isSplit = typedArray.getBoolean(
                R.styleable.AndesLinearProgress_andesLinearProgressIsSplit,
                IS_SPLIT_DEFAULT
            ),
            numberOfSteps = typedArray.getInteger(R.styleable.AndesLinearProgress_andesLinearProgressNumberOfSteps,
                NUMBER_OF_STEPS_DEFAULT
            )
        ).also { typedArray.recycle() }
    }
}
