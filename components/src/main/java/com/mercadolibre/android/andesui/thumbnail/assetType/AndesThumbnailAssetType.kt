package com.mercadolibre.android.andesui.thumbnail.assetType

import android.graphics.drawable.Drawable

/**
 * This sealed class represent all the possibles types of asset that a thumbnail can be created.
 */
sealed class AndesThumbnailAssetType(internal val type: AndesThumbnailAssetTypeInterface) {

    /**
     * This data class receives a drawable to return a thumbnail of type Icon.
     * @param image: Drawable
     */
    data class Icon(val image: Drawable) :
        AndesThumbnailAssetType(AndesIconThumbnailAssetType(image))

    /**
     * This data class receives a drawable to return a thumbnail of type Image.
     * @param image: Drawable
     */
    data class Image(val image: Drawable) :
        AndesThumbnailAssetType(AndesImageThumbnailAssetType(image))

    /**
     * This data class receives a string to return a thumbnail of type Text.
     * @param text: String
     */
    data class Text(val text: String) :
        AndesThumbnailAssetType(AndesTextThumbnailAssetType(text))

    internal fun createWith(image: Drawable): AndesThumbnailAssetType {
        return when (this) {
            is Icon -> Icon(image)
            is Image -> Image(image)
            is Text -> this
        }
    }
}