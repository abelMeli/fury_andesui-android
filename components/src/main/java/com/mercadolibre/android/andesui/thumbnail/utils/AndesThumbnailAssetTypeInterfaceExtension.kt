package com.mercadolibre.android.andesui.thumbnail.utils

import com.mercadolibre.android.andesui.thumbnail.assetType.AndesIconThumbnailAssetType
import com.mercadolibre.android.andesui.thumbnail.assetType.AndesImageThumbnailAssetType
import com.mercadolibre.android.andesui.thumbnail.assetType.AndesTextThumbnailAssetType
import com.mercadolibre.android.andesui.thumbnail.assetType.AndesThumbnailAssetTypeInterface
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy

internal val AndesThumbnailAssetTypeInterface.isImageType: Boolean
    get() = this is AndesImageThumbnailAssetType

internal val AndesThumbnailAssetTypeInterface.isIconType: Boolean
    get() = this is AndesIconThumbnailAssetType

internal val AndesThumbnailAssetTypeInterface.isTextType: Boolean
    get() = this is AndesTextThumbnailAssetType

internal fun AndesThumbnailAssetTypeInterface.getHierarchy(
    hierarchy: AndesThumbnailHierarchy
): AndesThumbnailHierarchy = if (isImageType) AndesThumbnailHierarchy.DEFAULT else hierarchy
