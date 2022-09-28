package com.mercadolibre.android.andesui.thumbnail.assetType

import android.content.Context
import android.graphics.drawable.Drawable
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSizeInterface
import com.mercadolibre.android.andesui.thumbnail.utils.AndesThumbnailParseDrawable

internal interface AndesThumbnailAssetTypeInterface {
    fun getWidth(context: Context, size: AndesThumbnailSizeInterface): Float
    fun getHeight(context: Context, size: AndesThumbnailSizeInterface): Float
    fun getAssetImage(context: Context, size: AndesThumbnailSizeInterface): Drawable
}

internal class AndesIconThumbnailAssetType(val image: Drawable) : AndesThumbnailAssetTypeInterface {
    override fun getWidth(context: Context, size: AndesThumbnailSizeInterface) =
        size.iconSize(context)

    override fun getHeight(context: Context, size: AndesThumbnailSizeInterface) =
        size.iconSize(context)

    override fun getAssetImage(context: Context, size: AndesThumbnailSizeInterface): Drawable =
        image

}

internal class AndesImageThumbnailAssetType(val image: Drawable) :
    AndesThumbnailAssetTypeInterface {
    override fun getWidth(context: Context, size: AndesThumbnailSizeInterface) =
        size.diameter(context)

    override fun getHeight(context: Context, size: AndesThumbnailSizeInterface) =
        size.diameter(context)

    override fun getAssetImage(context: Context, size: AndesThumbnailSizeInterface): Drawable =
        image

}

internal class AndesTextThumbnailAssetType(val text: String) : AndesThumbnailAssetTypeInterface {
    override fun getWidth(context: Context, size: AndesThumbnailSizeInterface) =
        size.textWidth(context)

    override fun getHeight(context: Context, size: AndesThumbnailSizeInterface) =
        size.textHeight(context)

    override fun getAssetImage(context: Context, size: AndesThumbnailSizeInterface): Drawable =
        AndesThumbnailParseDrawable.getImageFromText(
            text, context, size.textSize(context),
            size.textHeight(context).toInt(),
            size.textWidth(context).toInt()
        )
}
