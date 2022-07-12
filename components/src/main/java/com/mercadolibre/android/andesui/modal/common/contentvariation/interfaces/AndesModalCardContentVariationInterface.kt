package com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces

import android.content.Context
import android.view.View
import com.mercadolibre.android.andesui.R

internal object AndesModalCardContentVariationNone : AndesModalContentVariationInterface {
    override val bannerVisibility: Int = View.GONE
    override val thumbnailVisibility: Int = View.GONE
}

internal object AndesModalCardContentVariationImage : AndesModalContentVariationInterface {
    override val bannerVisibility: Int = View.VISIBLE
    override val thumbnailVisibility: Int = View.GONE
}


internal object AndesModalCardContentVariationSmallIllustration :
    AndesModalContentVariationInterface {
    override fun getHeight(context: Context) =
        context.resources.getDimension(R.dimen.andes_modal_image_small_height).toInt()

    override fun getWidth(context: Context) =
        context.resources.getDimension(R.dimen.andes_modal_image_width).toInt()

    override fun getMarginTop(context: Context) =
        context.resources.getDimension(R.dimen.andes_modal_margin_24).toInt()

    override val bannerVisibility: Int = View.VISIBLE
    override val thumbnailVisibility: Int = View.GONE
}

internal object AndesModalCardContentVariationMediumIllustration :
    AndesModalContentVariationInterface {
    override fun getHeight(context: Context) =
        context.resources.getDimension(R.dimen.andes_modal_image_medium_height).toInt()

    override fun getWidth(context: Context) =
        context.resources.getDimension(R.dimen.andes_modal_image_width).toInt()

    override fun getMarginTop(context: Context) =
        context.resources.getDimension(R.dimen.andes_modal_margin_24).toInt()

    override val bannerVisibility: Int = View.VISIBLE
    override val thumbnailVisibility: Int = View.GONE
}

internal object AndesModalCardContentVariationThumbnail : AndesModalContentVariationInterface {
    override fun getHeight(context: Context) =
        context.resources.getDimension(R.dimen.andes_modal_image_thumbnail_size).toInt()

    override fun getWidth(context: Context) =
        context.resources.getDimension(R.dimen.andes_modal_image_thumbnail_size).toInt()

    override fun getMarginTop(context: Context) =
        context.resources.getDimension(R.dimen.andes_modal_margin_24).toInt()

    override val bannerVisibility: Int = View.GONE
    override val thumbnailVisibility: Int = View.VISIBLE
}
