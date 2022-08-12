package com.mercadolibre.android.andesui.thumbnail.factory

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import com.mercadolibre.android.andesui.thumbnail.state.AndesThumbnailState
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailType

/**
 * The data class that contains the public components of the thumbnail.
 */
internal data class AndesThumbnailAttrs(
    val andesThumbnailAccentColor: AndesColor,
    val andesThumbnailHierarchy: AndesThumbnailHierarchy,
    val andesThumbnailImage: Drawable,
    val andesThumbnailType: AndesThumbnailType,
    val andesThumbnailSize: AndesThumbnailSize,
    val andesThumbnailState: AndesThumbnailState,
    val andesThumbnailScaleType: ImageView.ScaleType
)

/**
 * This object parse the attribute set and return an instance of AndesThumbnailAttrs to be used by AndesThumbnail
 */
internal object AndesThumbnailAttrsParser {
    private const val ANDES_THUMBNAIL_HIERARCHY_DEFAULT = 1000
    private const val ANDES_THUMBNAIL_HIERARCHY_LOUD = 1001
    private const val ANDES_THUMBNAIL_HIERARCHY_QUIET = 1002
    private const val NO_HIERARCHY = -1

    private const val ANDES_THUMBNAIL_TYPE_ICON = 2000
    private const val ANDES_THUMBNAIL_TYPE_IMAGE_CIRCLE = 2001
    private const val ANDES_THUMBNAIL_TYPE_IMAGE_SQUARE = 2002
    private const val NO_TYPE = -1

    private const val ANDES_THUMBNAIL_SIZE_24 = 3000
    private const val ANDES_THUMBNAIL_SIZE_32 = 3001
    private const val ANDES_THUMBNAIL_SIZE_40 = 3002
    private const val ANDES_THUMBNAIL_SIZE_48 = 3003
    private const val ANDES_THUMBNAIL_SIZE_56 = 3004
    private const val ANDES_THUMBNAIL_SIZE_64 = 3005
    private const val ANDES_THUMBNAIL_SIZE_72 = 3006
    private const val ANDES_THUMBNAIL_SIZE_80 = 3007
    private const val NO_SIZE = -1

    private const val ANDES_THUMBNAIL_STATE_DISABLED = 4000
    private const val ANDES_THUMBNAIL_STATE_ENABLED = 4001
    private const val NO_STATE = -1

    private const val ANDES_THUMBNAIL_SCALE_TYPE_CENTER = 5000
    private const val ANDES_THUMBNAIL_SCALE_TYPE_CENTER_CROP = 5001
    private const val ANDES_THUMBNAIL_SCALE_TYPE_CENTER_INSIDE = 5002
    private const val ANDES_THUMBNAIL_SCALE_TYPE_FIT_CENTER = 5003
    private const val ANDES_THUMBNAIL_SCALE_TYPE_FIT_END = 5004
    private const val ANDES_THUMBNAIL_SCALE_TYPE_FIT_START = 5005
    private const val ANDES_THUMBNAIL_SCALE_TYPE_FIT_XY = 5006
    private const val ANDES_THUMBNAIL_SCALE_TYPE_MATRIX = 5007
    private const val ANDES_THUMBNAIL_SCALE_NO_SCALE_TYPE = -1


    fun parse(context: Context, attr: AttributeSet?): AndesThumbnailAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesThumbnail)
        val color = typedArray.getResourceId(R.styleable.AndesThumbnail_andesThumbnailAccentColor, 0)

        return AndesThumbnailAttrs(
                andesThumbnailHierarchy = getHierarchy(typedArray),
                andesThumbnailType = getType(typedArray),
                andesThumbnailSize = getSize(typedArray),
                andesThumbnailState = getState(typedArray),
                andesThumbnailAccentColor = AndesColor(color),
                andesThumbnailImage = getImage(typedArray),
                andesThumbnailScaleType = getScaleType(typedArray)
        ).also { typedArray.recycle() }
    }

    private fun getScaleType(typedArray: TypedArray): ImageView.ScaleType {
        return when (typedArray.getInt(R.styleable.AndesThumbnail_andesThumbnailScaleType, ANDES_THUMBNAIL_SCALE_NO_SCALE_TYPE)) {
            ANDES_THUMBNAIL_SCALE_TYPE_CENTER -> ImageView.ScaleType.CENTER
            ANDES_THUMBNAIL_SCALE_TYPE_CENTER_CROP -> ImageView.ScaleType.CENTER_CROP
            ANDES_THUMBNAIL_SCALE_TYPE_CENTER_INSIDE -> ImageView.ScaleType.CENTER_INSIDE
            ANDES_THUMBNAIL_SCALE_TYPE_FIT_CENTER -> ImageView.ScaleType.FIT_CENTER
            ANDES_THUMBNAIL_SCALE_TYPE_FIT_END -> ImageView.ScaleType.FIT_END
            ANDES_THUMBNAIL_SCALE_TYPE_FIT_START -> ImageView.ScaleType.FIT_START
            ANDES_THUMBNAIL_SCALE_TYPE_FIT_XY -> ImageView.ScaleType.FIT_XY
            ANDES_THUMBNAIL_SCALE_TYPE_MATRIX -> ImageView.ScaleType.MATRIX
            else -> ImageView.ScaleType.CENTER_CROP
        }
    }

    private fun getImage(typedArray: TypedArray): Drawable {
        val image: Drawable
        val imageParameter = typedArray.getDrawable(R.styleable.AndesThumbnail_andesThumbnailImage)
        if (imageParameter == null) {
            throw IllegalArgumentException("Wrong andesThumbnailImage passed, check your layout")
        } else {
            image = imageParameter
        }
        return image
    }

    private fun getSize(typedArray: TypedArray): AndesThumbnailSize =
        when (typedArray.getInt(R.styleable.AndesThumbnail_andesThumbnailSize, NO_SIZE)) {
            ANDES_THUMBNAIL_SIZE_24 -> AndesThumbnailSize.SIZE_24
            ANDES_THUMBNAIL_SIZE_32 -> AndesThumbnailSize.SIZE_32
            ANDES_THUMBNAIL_SIZE_40 -> AndesThumbnailSize.SIZE_40
            ANDES_THUMBNAIL_SIZE_48 -> AndesThumbnailSize.SIZE_48
            ANDES_THUMBNAIL_SIZE_56 -> AndesThumbnailSize.SIZE_56
            ANDES_THUMBNAIL_SIZE_64 -> AndesThumbnailSize.SIZE_64
            ANDES_THUMBNAIL_SIZE_72 -> AndesThumbnailSize.SIZE_72
            ANDES_THUMBNAIL_SIZE_80 -> AndesThumbnailSize.SIZE_80
            else -> AndesThumbnailSize.SIZE_48
        }

    private fun getHierarchy(typedArray: TypedArray): AndesThumbnailHierarchy =
        when (typedArray.getInt(R.styleable.AndesThumbnail_andesThumbnailHierarchy, NO_HIERARCHY)) {
            ANDES_THUMBNAIL_HIERARCHY_DEFAULT -> AndesThumbnailHierarchy.DEFAULT
            ANDES_THUMBNAIL_HIERARCHY_LOUD -> AndesThumbnailHierarchy.LOUD
            ANDES_THUMBNAIL_HIERARCHY_QUIET -> AndesThumbnailHierarchy.QUIET
            else -> AndesThumbnailHierarchy.DEFAULT
        }

    private fun getState(typedArray: TypedArray): AndesThumbnailState =
        when (typedArray.getInt(R.styleable.AndesThumbnail_andesThumbnailState, NO_STATE)) {
            ANDES_THUMBNAIL_STATE_DISABLED -> AndesThumbnailState.DISABLED
            ANDES_THUMBNAIL_STATE_ENABLED -> AndesThumbnailState.ENABLED
            else -> AndesThumbnailState.ENABLED
        }

    private fun getType(typedArray: TypedArray): AndesThumbnailType =
        when (typedArray.getInt(R.styleable.AndesThumbnail_andesThumbnailType, NO_TYPE)) {
            ANDES_THUMBNAIL_TYPE_ICON -> AndesThumbnailType.ICON
            ANDES_THUMBNAIL_TYPE_IMAGE_CIRCLE -> AndesThumbnailType.IMAGE_CIRCLE
            ANDES_THUMBNAIL_TYPE_IMAGE_SQUARE -> AndesThumbnailType.IMAGE_SQUARE
            else -> AndesThumbnailType.ICON
        }
}
