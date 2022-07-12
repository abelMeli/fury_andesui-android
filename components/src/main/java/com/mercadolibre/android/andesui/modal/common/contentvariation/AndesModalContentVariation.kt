package com.mercadolibre.android.andesui.modal.common.contentvariation

import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalCardContentVariationImage
import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalCardContentVariationMediumIllustration
import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalCardContentVariationNone
import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalCardContentVariationSmallIllustration
import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalCardContentVariationThumbnail
import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalContentVariationInterface
import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalFullContentVariationLargeIllustration
import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalFullContentVariationMediumIllustration
import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalFullContentVariationNone
import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalFullContentVariationSmallIllustration
import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalFullContentVariationThumbnail

/**
 * Enum for the different asset configurations for the card modal.
 * To see the specs, please refer to the [figma docs](https://www.figma.com/file/ma8IQUYi9IS8zc8C0rAzEB/Components-specifications?node-id=3797%3A480618)
 */
enum class AndesModalCardContentVariation(internal val variation: AndesModalContentVariationInterface) {
    NONE(AndesModalCardContentVariationNone),
    IMAGE(AndesModalCardContentVariationImage),
    SMALL_ILLUSTRATION(AndesModalCardContentVariationSmallIllustration),
    MEDIUM_ILLUSTRATION(AndesModalCardContentVariationMediumIllustration),
    THUMBNAIL(AndesModalCardContentVariationThumbnail)
}

/**
 * Enum for the different asset configurations for the full modal.
 * To see the specs, please refer to the [figma docs](https://www.figma.com/file/ma8IQUYi9IS8zc8C0rAzEB/Components-specifications?node-id=3929%3A464700)
 */
enum class AndesModalFullContentVariation(internal val variation: AndesModalContentVariationInterface) {
    NONE(AndesModalFullContentVariationNone),
    SMALL_ILLUSTRATION(AndesModalFullContentVariationSmallIllustration),
    MEDIUM_ILLUSTRATION(AndesModalFullContentVariationMediumIllustration),
    LARGE_ILLUSTRATION(AndesModalFullContentVariationLargeIllustration),
    THUMBNAIL(AndesModalFullContentVariationThumbnail)
}
