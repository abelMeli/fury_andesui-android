package com.mercadolibre.android.andesui.linearprogress.size

import android.content.Context
import com.mercadolibre.android.andesui.R

/**
 * Defines all size related properties that an [AndesLinearProgressSize] needs to be drawn properly.
 */
internal interface AndesLinearProgressSizeInterface {

    fun height(context: Context): Float

    fun splitSize(context: Context): Float

    fun cornerRadius(context: Context): Float
}

internal class AndesSmallLinearProgressSize : AndesLinearProgressSizeInterface {
    override fun height(context: Context): Float =
        context.resources.getDimension(R.dimen.andes_linear_progress_height_small)

    override fun splitSize(context: Context): Float =
        context.resources.getDimension(R.dimen.andes_linear_progress_split_size_small)

    override fun cornerRadius(context: Context): Float =
        context.resources.getDimension(R.dimen.andes_linear_progress_corner_radius_small)
}

internal class AndesLargeLinearProgressSize : AndesLinearProgressSizeInterface {
    override fun height(context: Context): Float =
        context.resources.getDimension(R.dimen.andes_linear_progress_height_large)

    override fun splitSize(context: Context): Float =
        context.resources.getDimension(R.dimen.andes_linear_progress_split_size_large)

    override fun cornerRadius(context: Context): Float =
        context.resources.getDimension(R.dimen.andes_linear_progress_corner_radius_large)
}
