package com.mercadolibre.android.andesui.modal.card.configfactory

import com.mercadolibre.android.andesui.modal.card.builder.Action
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalCardContentVariation

@Suppress("LongParameterList")
internal data class AndesModalCardDefaultFragmentArguments(
    val isDismissible: Boolean = true,
    val isButtonGroupFixed: Boolean = false,
    val buttonGroupCreator: AndesButtonGroupCreator? = null,
    val onDismissCallback: Action? = null,
    val onModalShowCallback: Action? = null,
    val contentVariation: AndesModalCardContentVariation = AndesModalCardContentVariation.NONE,
    val isHeaderFixed: Boolean = false,
    val content: AndesModalContent? = null
)
