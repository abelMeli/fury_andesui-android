package com.mercadolibre.android.andesui.thumbnail.utils

import android.content.Context
import com.mercadolibre.android.andesui.thumbnail.factory.AndesThumbnailConfigurationFactory
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSizeInterface
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailTypeInterface

/**
 * This is intended to serve different implementations by the current
 * device's Api Level.
 *
 * @property resolveIsImageType : Always returns <code>false</code> to set scale with fitCenter.
 * @property resolveIconSize : Always return <code>size.iconSize</code> to keep it smaller.
 */
internal interface AndesThumbnailConfigurationApiLevelResolver {
    fun resolveIsImageType(type: AndesThumbnailTypeInterface) = false
    fun resolveIconSize(
        context: Context,
        size: AndesThumbnailSizeInterface,
        type: AndesThumbnailTypeInterface
    ) = size.iconSize(context).toInt()
}

/**
 * Gets the implementation for API21 & higher
 * We can modify this values to get the desired behaviour on the <code>imageView</code>
 *
 * @property resolveIsImageType : resolves by <code>AndesThumbnailTypeInterface.isImageType</code>
 * @property resolveIconSize : resolves by <code>AndesThumbnailTypeInterface.isIconType</code>
 *                             if it uses <code>size.iconSize</code> or <code>size.diameter</code>
 *
 */
private object ResolverApiLevel21 : AndesThumbnailConfigurationApiLevelResolver {
    override fun resolveIsImageType(type: AndesThumbnailTypeInterface) = type.isImageType
    override fun resolveIconSize(
        context: Context,
        size: AndesThumbnailSizeInterface,
        type: AndesThumbnailTypeInterface
    ) = if (type.isIconType) super.resolveIconSize(context, size, type) else size.diameter(context).toInt()
}

internal val AndesThumbnailConfigurationFactory.resolverByApiLevel:
        AndesThumbnailConfigurationApiLevelResolver by lazy { ResolverApiLevel21 }
