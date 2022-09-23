package com.mercadolibre.android.andesui.thumbnail.factory

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.thumbnail.assetType.AndesTextThumbnailAssetType
import com.mercadolibre.android.andesui.thumbnail.assetType.AndesThumbnailAssetType
import com.mercadolibre.android.andesui.thumbnail.assetType.AndesThumbnailAssetTypeInterface
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.thumbnail.shape.AndesSquareShapeThumbnail
import com.mercadolibre.android.andesui.thumbnail.shape.AndesThumbnailShapeInterface
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSizeInterface
import com.mercadolibre.android.andesui.thumbnail.state.AndesThumbnailStateInterface
import com.mercadolibre.android.andesui.thumbnail.utils.*
import com.mercadolibre.android.andesui.thumbnail.utils.getHierarchy
import com.mercadolibre.android.andesui.thumbnail.utils.isIconType

internal data class AndesThumbnailConfiguration(
    val backgroundColor: AndesColor,
    val borderColor: AndesColor,
    val hasBorder: Boolean,
    val iconColor: AndesColor,
    val image: Drawable,
    val size: Float,
    val cornerRadius: Float,
    val isImageType: Boolean,
    val hasTint: Boolean,
    val clipToOutline: Boolean,
    val scaleType: ImageView.ScaleType,
    val heightSize: Int,
    val widthSize: Int
)

@Suppress("TooManyFunctions")
internal object AndesThumbnailConfigurationFactory {

    fun create(
        context: Context,
        andesThumbnailAttrs: AndesThumbnailAttrs
    ): AndesThumbnailConfiguration {
        val isImageType = resolveIsImageType(andesThumbnailAttrs.andesThumbnailAssetType.type)
        return with(andesThumbnailAttrs) {
            AndesThumbnailConfiguration(
                backgroundColor = resolveBackgroundColor(
                    context, andesThumbnailState.state, andesThumbnailHierarchy,
                    andesThumbnailAccentColor, andesThumbnailAssetType.type
                ),
                borderColor = resolveBorderColor(),
                hasBorder = resolveHasBorder(andesThumbnailHierarchy, andesThumbnailAssetType.type),
                iconColor = resolveIconColor(
                    context, andesThumbnailState.state, andesThumbnailHierarchy,
                    andesThumbnailAccentColor, andesThumbnailAssetType.type
                ),
                image = resolveImage(
                    context,
                    andesThumbnailSize.size,
                    andesThumbnailAssetType.type
                ),
                size = resolveSize(context, andesThumbnailSize.size),
                cornerRadius = resolveCornerRadius(
                    context,
                    andesThumbnailSize.size,
                    andesThumbnailShape.shape
                ),
                isImageType = isImageType,
                hasTint = resolveHasTint(andesThumbnailAssetType.type),
                clipToOutline = resolveClipToOutline(isImageType),
                scaleType = resolveScaleType(isImageType, andesThumbnailScaleType),
                widthSize = resolveWidth(context, andesThumbnailSize, andesThumbnailAssetType),
                heightSize = resolveHeight(context, andesThumbnailSize, andesThumbnailAssetType)
            )
        }
    }

    /**
     * this function resolve the height of the asset regarding the thumbnail type and size.
     */
    private fun resolveHeight(
        context: Context,
        thumbnailSize: AndesThumbnailSize,
        assetType: AndesThumbnailAssetType
    ): Int =
        assetType.type.getHeight(context, thumbnailSize.size).toInt()

    /**
     * this function resolve the width of the asset regarding the thumbnail type and size.
     */
    private fun resolveWidth(
        context: Context,
        thumbnailSize: AndesThumbnailSize,
        assetType: AndesThumbnailAssetType
    ): Int =
        assetType.type.getWidth(context, thumbnailSize.size).toInt()

    /**
     * Only the image types are allowed to modify the scale type. For the icon type we always need
     * the FIT_CENTER scale type.
     */
    private fun resolveScaleType(
        isImageType: Boolean,
        andesThumbnailScaleType: ImageView.ScaleType
    ): ImageView.ScaleType {
        if (!isImageType) return ImageView.ScaleType.FIT_CENTER
        return andesThumbnailScaleType
    }

    /**
     * The thumbnail content should only be clipped when the type is image because when
     * the type is icon the image will be always inside (the image dimensions are always smaller than
     * the component).
     */
    private fun resolveClipToOutline(isImageType: Boolean) = isImageType

    private fun resolveBackgroundColor(
        context: Context,
        state: AndesThumbnailStateInterface,
        hierarchy: AndesThumbnailHierarchy,
        accentColor: AndesColor,
        type: AndesThumbnailAssetTypeInterface
    ) = state.backgroundColor(context, type.getHierarchy(hierarchy), accentColor)

    private fun resolveBorderColor(): AndesColor = R.color.andes_gray_070_solid.toAndesColor()

    private fun resolveHasBorder(
        hierarchy: AndesThumbnailHierarchy,
        type: AndesThumbnailAssetTypeInterface
    ): Boolean = type.getHierarchy(hierarchy).hierarchy.hasBorder()

    private fun resolveIconColor(
        context: Context,
        state: AndesThumbnailStateInterface,
        hierarchy: AndesThumbnailHierarchy,
        accentColor: AndesColor,
        type: AndesThumbnailAssetTypeInterface
    ) = state.iconColor(context, type.getHierarchy(hierarchy), accentColor)

    private fun resolveSize(context: Context, size: AndesThumbnailSizeInterface) =
        size.diameter(context)

    private fun resolveIsImageType(type: AndesThumbnailAssetTypeInterface) =
        type.isImageType

    private fun resolveHasTint(type: AndesThumbnailAssetTypeInterface) =
        type.isIconType || type.isTextType

    /**
     * This function has the logic to return a drawable according with the type, if type is text must
     * have a valid String, not blank or empty.
     */
    private fun resolveImage(
        context: Context,
        size: AndesThumbnailSizeInterface,
        type: AndesThumbnailAssetTypeInterface
    ): Drawable {
        if (type is AndesTextThumbnailAssetType && type.text.isBlank()) {
            throw IllegalArgumentException(
                "The thumbnail of type Text requires a valid String"
            )
        }
        return type.getAssetImage(context, size)
    }

    private fun resolveCornerRadius(
        context: Context,
        size: AndesThumbnailSizeInterface,
        shape: AndesThumbnailShapeInterface
    ) = if (shape is AndesSquareShapeThumbnail) size.radiusSize(context) else size.diameter(context)
}
