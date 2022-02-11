package com.mercadolibre.android.andesui.thumbnail.factory

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
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
    val andesThumbnailState: AndesThumbnailState
)

/**
 * This object parse the attribute set and return an instance of AndesThumbnailAttrs to be used by AndesThumbnail
 */
internal object AndesThumbnailAttrsParser {
    private const val ANDES_THUMBNAIL_HIERARCHY_DEFAULT = "1000"
    private const val ANDES_THUMBNAIL_HIERARCHY_LOUD = "1001"
    private const val ANDES_THUMBNAIL_HIERARCHY_QUIET = "1002"

    private const val ANDES_THUMBNAIL_TYPE_ICON = "2000"
    private const val ANDES_THUMBNAIL_TYPE_IMAGE_CIRCLE = "2001"
    private const val ANDES_THUMBNAIL_TYPE_IMAGE_SQUARE = "2002"

    private const val ANDES_THUMBNAIL_SIZE_24 = "3000"
    private const val ANDES_THUMBNAIL_SIZE_32 = "3001"
    private const val ANDES_THUMBNAIL_SIZE_40 = "3002"
    private const val ANDES_THUMBNAIL_SIZE_48 = "3003"
    private const val ANDES_THUMBNAIL_SIZE_56 = "3004"
    private const val ANDES_THUMBNAIL_SIZE_64 = "3005"
    private const val ANDES_THUMBNAIL_SIZE_72 = "3006"
    private const val ANDES_THUMBNAIL_SIZE_80 = "3007"

    private const val ANDES_THUMBNAIL_STATE_DISABLED = "4000"
    private const val ANDES_THUMBNAIL_STATE_ENABLED = "4001"

    fun parse(context: Context, attr: AttributeSet?): AndesThumbnailAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesThumbnail)
        val color = typedArray.getResourceId(R.styleable.AndesThumbnail_andesThumbnailAccentColor, 0)

        return AndesThumbnailAttrs(
                andesThumbnailHierarchy = getHierarchy(typedArray),
                andesThumbnailType = getType(typedArray),
                andesThumbnailSize = getSize(typedArray),
                andesThumbnailState = getState(typedArray),
                andesThumbnailAccentColor = AndesColor(color),
                andesThumbnailImage = getImage(typedArray)
        ).also { typedArray.recycle() }
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
        when (typedArray.getString(R.styleable.AndesThumbnail_andesThumbnailSize)) {
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
        when (typedArray.getString(R.styleable.AndesThumbnail_andesThumbnailHierarchy)) {
            ANDES_THUMBNAIL_HIERARCHY_DEFAULT -> AndesThumbnailHierarchy.DEFAULT
            ANDES_THUMBNAIL_HIERARCHY_LOUD -> AndesThumbnailHierarchy.LOUD
            ANDES_THUMBNAIL_HIERARCHY_QUIET -> AndesThumbnailHierarchy.QUIET
            else -> AndesThumbnailHierarchy.DEFAULT
        }

    private fun getState(typedArray: TypedArray): AndesThumbnailState =
        when (typedArray.getString(R.styleable.AndesThumbnail_andesThumbnailState)) {
            ANDES_THUMBNAIL_STATE_DISABLED -> AndesThumbnailState.DISABLED
            ANDES_THUMBNAIL_STATE_ENABLED -> AndesThumbnailState.ENABLED
            else -> AndesThumbnailState.ENABLED
        }

    private fun getType(typedArray: TypedArray): AndesThumbnailType =
        when (typedArray.getString(R.styleable.AndesThumbnail_andesThumbnailType)) {
            ANDES_THUMBNAIL_TYPE_ICON -> AndesThumbnailType.ICON
            ANDES_THUMBNAIL_TYPE_IMAGE_CIRCLE -> AndesThumbnailType.IMAGE_CIRCLE
            ANDES_THUMBNAIL_TYPE_IMAGE_SQUARE -> AndesThumbnailType.IMAGE_SQUARE
            else -> AndesThumbnailType.ICON
        }
}
