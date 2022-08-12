package com.mercadolibre.android.andesui.thumbnail.factory

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSizeInterface
import com.mercadolibre.android.andesui.thumbnail.state.AndesThumbnailStateInterface
import com.mercadolibre.android.andesui.thumbnail.type.AndesImageSquareThumbnailType
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailTypeInterface
import com.mercadolibre.android.andesui.thumbnail.utils.getHierarchy
import com.mercadolibre.android.andesui.thumbnail.utils.isIconType
import com.mercadolibre.android.andesui.thumbnail.utils.resolverByApiLevel

internal data class AndesThumbnailConfiguration(
    val backgroundColor: AndesColor,
    val borderColor: AndesColor,
    val hasBorder: Boolean,
    val iconColor: AndesColor,
    val iconSize: Int,
    val image: Drawable,
    val size: Float,
    val cornerRadius: Float,
    val isImageType: Boolean,
    val hasTint: Boolean,
    val clipToOutline: Boolean,
    val scaleType: ImageView.ScaleType
)

@Suppress("TooManyFunctions")
internal object AndesThumbnailConfigurationFactory {

    fun create(context: Context, andesThumbnailAttrs: AndesThumbnailAttrs): AndesThumbnailConfiguration {
        val isImageType = resolveIsImageType(andesThumbnailAttrs.andesThumbnailType.type)
        return with(andesThumbnailAttrs) {
            AndesThumbnailConfiguration(
                backgroundColor = resolveBackgroundColor(context, andesThumbnailState.state, andesThumbnailHierarchy,
                    andesThumbnailAccentColor, andesThumbnailType.type),
                borderColor = resolveBorderColor(),
                hasBorder = resolveHasBorder(andesThumbnailHierarchy, andesThumbnailType.type),
                iconColor = resolveIconColor(context, andesThumbnailState.state, andesThumbnailHierarchy,
                    andesThumbnailAccentColor, andesThumbnailType.type),
                iconSize = resolveIconSize(context, andesThumbnailSize.size, andesThumbnailType.type),
                image = resolveImage(andesThumbnailImage),
                size = resolveSize(context, andesThumbnailSize.size),
                cornerRadius = resolveCornerRadius(context, andesThumbnailSize.size, andesThumbnailType.type),
                isImageType = isImageType,
                hasTint = resolveHasTint(andesThumbnailType.type),
                clipToOutline = resolveClipToOutline(isImageType),
                scaleType = resolveScaleType(isImageType, andesThumbnailScaleType)
            )
        }
    }

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
        type: AndesThumbnailTypeInterface
    ) = state.backgroundColor(context, type.getHierarchy(hierarchy), accentColor)
    private fun resolveBorderColor(): AndesColor = R.color.andes_gray_070_solid.toAndesColor()
    private fun resolveHasBorder(
        hierarchy: AndesThumbnailHierarchy,
        type: AndesThumbnailTypeInterface
    ): Boolean = type.getHierarchy(hierarchy).hierarchy.hasBorder()
    private fun resolveIconColor(
        context: Context,
        state: AndesThumbnailStateInterface,
        hierarchy: AndesThumbnailHierarchy,
        accentColor: AndesColor,
        type: AndesThumbnailTypeInterface
    ) = state.iconColor(context, type.getHierarchy(hierarchy), accentColor)
    private fun resolveImage(image: Drawable) = image
    private fun resolveSize(context: Context, size: AndesThumbnailSizeInterface) = size.diameter(context)
    private fun resolveIconSize(
        context: Context,
        size: AndesThumbnailSizeInterface,
        type: AndesThumbnailTypeInterface
    ) = resolverByApiLevel.resolveIconSize(context, size, type)
    private fun resolveIsImageType(type: AndesThumbnailTypeInterface) = resolverByApiLevel.resolveIsImageType(type)
    private fun resolveHasTint(type: AndesThumbnailTypeInterface) = type.isIconType
    private fun resolveCornerRadius(
        context: Context,
        size: AndesThumbnailSizeInterface,
        type: AndesThumbnailTypeInterface
    ) = if (type is AndesImageSquareThumbnailType) size.radiusSize(context) else size.diameter(context)
}
