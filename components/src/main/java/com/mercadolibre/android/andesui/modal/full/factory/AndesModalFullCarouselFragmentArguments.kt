package com.mercadolibre.android.andesui.modal.full.factory

import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalFullContentVariation
import com.mercadolibre.android.andesui.modal.full.builder.ModalFullAction

@Suppress("LongParameterList")
internal data class AndesModalFullCarouselFragmentArguments(
    val isDismissible: Boolean = true,
    val buttonGroupCreator: AndesButtonGroupCreator? = null,
    val onDismissCallback: ModalFullAction? = null,
    val onModalShowCallback: ModalFullAction? = null,
    val contentVariation: AndesModalFullContentVariation = AndesModalFullContentVariation.NONE,
    val isHeaderFixed: Boolean = false,
    val pageSelectedCallback: ((position: Int) -> Unit)? = null,
    val contentList: ArrayList<AndesModalContent>? = null
)
