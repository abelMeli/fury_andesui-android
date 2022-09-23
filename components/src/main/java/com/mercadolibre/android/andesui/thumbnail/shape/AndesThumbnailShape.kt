package com.mercadolibre.android.andesui.thumbnail.shape

/**
 * This sealed class represent all the possibles shapes that a thumbnail can have.
 */
sealed class AndesThumbnailShape (internal val shape: AndesThumbnailShapeInterface){

    /**
     * This object return an Circle AndesThumbnailShape.
     */
    object Circle : AndesThumbnailShape(AndesCircleShapeThumbnail())

    /**
     * This object return an Square AndesThumbnailShape.
     */
    object Square : AndesThumbnailShape(AndesSquareShapeThumbnail())


}