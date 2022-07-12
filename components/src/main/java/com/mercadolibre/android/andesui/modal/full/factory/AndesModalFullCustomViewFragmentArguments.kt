package com.mercadolibre.android.andesui.modal.full.factory

import android.view.View
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.full.builder.ModalFullAction

@Suppress("LongParameterList")
internal data class AndesModalFullCustomViewFragmentArguments(
    val isDismissible: Boolean = true,
    val isHeaderFixed: Boolean = false,
    val headerTitle: String? = null,
    val buttonGroupCreator: AndesButtonGroupCreator? = null,
    val onDismissCallback: ModalFullAction? = null,
    val onModalShowCallback: ModalFullAction? = null,
    val customView: View? = null,
    val modalDescription: String? = null
)
