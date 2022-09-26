package com.mercadolibre.android.andesui.thumbnail.factory

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.thumbnail.assetType.AndesThumbnailAssetType
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.thumbnail.shape.AndesThumbnailShape
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import com.mercadolibre.android.andesui.thumbnail.state.AndesThumbnailState
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailType

/**
 * The data class that contains the public components of the thumbnail.
 */
internal data class AndesThumbnailAttrs(
    val andesThumbnailAccentColor: AndesColor,
    val andesThumbnailHierarchy: AndesThumbnailHierarchy,
    @Deprecated("Use andesThumbnailAssetType and andesThumbnailShape")
    val andesThumbnailType: AndesThumbnailType,
    val andesThumbnailSize: AndesThumbnailSize,
    val andesThumbnailState: AndesThumbnailState,
    val andesThumbnailScaleType: ImageView.ScaleType,
    val andesThumbnailAssetType: AndesThumbnailAssetType,
    val andesThumbnailShape: AndesThumbnailShape
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

    private const val ANDES_THUMBNAIL_ASSET_ICON = 6000
    private const val ANDES_THUMBNAIL_ASSET_IMAGE = 6001
    private const val ANDES_THUMBNAIL_ASSET_TEXT = 6002
    private const val NO_ASSET = -1

    private const val ANDES_THUMBNAIL_CIRCLE = 7000
    private const val ANDES_THUMBNAIL_SQUARE = 7001
    private const val NO_SHAPE = -1


    fun parse(context: Context, attr: AttributeSet?): AndesThumbnailAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesThumbnail)
        val color =
            typedArray.getResourceId(R.styleable.AndesThumbnail_andesThumbnailAccentColor, 0)
        val inputText = getInputText(typedArray)
        val inputImage = getInputImage(typedArray)
        val type = getType(typedArray)
        return AndesThumbnailAttrs(
            andesThumbnailHierarchy = getHierarchy(typedArray),
            andesThumbnailType = type,
            andesThumbnailSize = getSize(typedArray),
            andesThumbnailState = getState(typedArray),
            andesThumbnailAccentColor = AndesColor(color),
            andesThumbnailScaleType = getScaleType(typedArray),
            andesThumbnailAssetType = getAssetType(typedArray, type, inputImage, inputText),
            andesThumbnailShape = getShape(typedArray)
        ).also { typedArray.recycle() }
    }

    private fun getScaleType(typedArray: TypedArray): ImageView.ScaleType {
        return when (typedArray.getInt(
            R.styleable.AndesThumbnail_andesThumbnailScaleType,
            ANDES_THUMBNAIL_SCALE_NO_SCALE_TYPE
        )) {
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

    private fun getInputImage(typedArray: TypedArray): Drawable?
         = typedArray.getDrawable(R.styleable.AndesThumbnail_andesThumbnailImage)

    private fun getInputText(typedArray: TypedArray): String =
        typedArray.getString(R.styleable.AndesThumbnail_andesThumbnailText) ?: ""

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

    private fun getAssetType(
        typedArray: TypedArray,
        type: AndesThumbnailType,
        inputImage: Drawable?,
        inputText: String
    ): AndesThumbnailAssetType =
        when (typedArray.getInt(R.styleable.AndesThumbnail_andesThumbnailAssetType, NO_ASSET)) {
            ANDES_THUMBNAIL_ASSET_ICON -> AndesThumbnailAssetType.Icon(
                inputImage
                    ?: throw IllegalArgumentException("You need to provide an image to use this asset type")
            )
            ANDES_THUMBNAIL_ASSET_IMAGE -> AndesThumbnailAssetType.Image(
                inputImage
                    ?: throw IllegalArgumentException("You need to provide an image to use this asset type")
            )
            ANDES_THUMBNAIL_ASSET_TEXT -> AndesThumbnailAssetType.Text(
                inputText.takeIf { it.isNotBlank() }
                    ?: throw IllegalArgumentException("You need to provide a valid string to use this asset type")
            )
            else -> type.toAssetType(
                inputImage
                    ?: throw IllegalArgumentException("Wrong andesThumbnailImage passed, check your layout")
            )
        }

    private fun getShape(typedArray: TypedArray): AndesThumbnailShape =
        when (typedArray.getInt(R.styleable.AndesThumbnail_andesThumbnailShape, NO_SHAPE)) {
            ANDES_THUMBNAIL_CIRCLE -> AndesThumbnailShape.Circle
            ANDES_THUMBNAIL_SQUARE -> AndesThumbnailShape.Square
            else ->
                when (typedArray.getInt(R.styleable.AndesThumbnail_andesThumbnailType, NO_TYPE)) {
                    ANDES_THUMBNAIL_TYPE_ICON -> AndesThumbnailShape.Circle
                    ANDES_THUMBNAIL_TYPE_IMAGE_CIRCLE -> AndesThumbnailShape.Circle
                    ANDES_THUMBNAIL_TYPE_IMAGE_SQUARE -> AndesThumbnailShape.Square
                    else -> AndesThumbnailShape.Circle
                }
        }
}
