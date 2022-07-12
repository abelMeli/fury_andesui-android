package com.mercadolibre.android.andesui.modal.card.configfactory

import android.view.View
import com.mercadolibre.android.andesui.modal.card.builder.Action
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator

@Suppress("LongParameterList")
internal data class AndesModalCardViewFragmentArguments(
    val isDismissible: Boolean = true,
    val isHeaderFixed: Boolean = false,
    val buttonGroupCreator: AndesButtonGroupCreator? = null,
    val onDismissCallback: Action? = null,
    val onModalShowCallback: Action? = null,
    val customView: View? = null,
    val modalDescription: String? = null,
    val headerTitle: String? = null
)
