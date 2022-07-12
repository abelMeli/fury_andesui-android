package com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces

import android.content.Context
import android.view.View
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalContentVariationInterface.Companion.VERTICAL_TOP_VALUE_BIAS

internal object AndesModalFullContentVariationNone : AndesModalContentVariationInterface {
    override val bannerVisibility: Int = View.GONE
    override val thumbnailVisibility: Int = View.GONE
    override fun getVerticalBias(): Float = VERTICAL_TOP_VALUE_BIAS
    override fun getTitleVisibility(): Int = View.GONE
}

internal object AndesModalFullContentVariationSmallIllustration :
    AndesModalContentVariationInterface {
    override val bannerVisibility: Int = View.VISIBLE
    override val thumbnailVisibility: Int = View.GONE

    override fun getHeight(context: Context) =
        context.resources.getDimension(R.dimen.andes_modal_image_small_height).toInt()

    override fun getWidth(context: Context) =
        context.resources.getDimension(R.dimen.andes_modal_full_image_width).toInt()

    override fun getSubtitleTextAlignment(): Int = View.TEXT_ALIGNMENT_CENTER
}

internal object AndesModalFullContentVariationMediumIllustration :
    AndesModalContentVariationInterface {
    override val bannerVisibility: Int = View.VISIBLE
    override val thumbnailVisibility: Int = View.GONE

    override fun getHeight(context: Context) =
        context.resources.getDimension(R.dimen.andes_modal_image_medium_height).toInt()

    override fun getWidth(context: Context) =
        context.resources.getDimension(R.dimen.andes_modal_full_image_width).toInt()

    override fun getSubtitleTextAlignment(): Int = View.TEXT_ALIGNMENT_CENTER
}

internal object AndesModalFullContentVariationLargeIllustration :
    AndesModalContentVariationInterface {
    override val bannerVisibility: Int = View.VISIBLE
    override val thumbnailVisibility: Int = View.GONE

    override fun getHeight(context: Context): Int =
        context.resources.getDimension(R.dimen.andes_modal_full_large_image_height).toInt()

    override fun getWidth(context: Context): Int =
        context.resources.getDimension(R.dimen.andes_modal_full_image_width).toInt()

    override fun getSubtitleTextAlignment(): Int = View.TEXT_ALIGNMENT_CENTER
}

internal object AndesModalFullContentVariationThumbnail : AndesModalContentVariationInterface {
    override val bannerVisibility: Int = View.GONE
    override val thumbnailVisibility: Int = View.VISIBLE

    override fun getHeight(context: Context) =
        context.resources.getDimension(R.dimen.andes_modal_image_thumbnail_size).toInt()

    override fun getWidth(context: Context) =
        context.resources.getDimension(R.dimen.andes_modal_image_thumbnail_size).toInt()

    override fun getSubtitleTextAlignment(): Int = View.TEXT_ALIGNMENT_CENTER
}
