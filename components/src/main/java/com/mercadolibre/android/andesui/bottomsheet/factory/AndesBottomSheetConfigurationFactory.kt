package com.mercadolibre.android.andesui.bottomsheet.factory

import com.mercadolibre.android.andesui.bottomsheet.state.AndesBottomSheetState
import com.mercadolibre.android.andesui.bottomsheet.title.AndesBottomSheetTitleAlignment

internal data class AndesBottomSheetConfiguration(
    val peekHeight: Int,
    val state: AndesBottomSheetState,
    val titleText: String?,
    val titleAlignment: AndesBottomSheetTitleAlignment?,
    val fitContent: Boolean
)

internal object AndesBottomSheetConfigurationFactory {

    fun create(andesBottomSheetAttrs: AndesBottomSheetAttrs): AndesBottomSheetConfiguration {
        return AndesBottomSheetConfiguration(
                peekHeight = andesBottomSheetAttrs.andesBottomSheetPeekHeight,
                state = andesBottomSheetAttrs.andesBottomSheetState,
                titleText = andesBottomSheetAttrs.andesBottomSheetTitleText,
                titleAlignment = andesBottomSheetAttrs.andesBottomSheetTitleAlignment,
                fitContent = andesBottomSheetAttrs.andesBottomSheetFitContent
        )
    }
}
