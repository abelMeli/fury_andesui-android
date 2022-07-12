package com.mercadolibre.android.andesui.modal.full.factory

import android.view.View
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.full.builder.ModalFullAction
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderStatus
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderType

@Suppress("LongParameterList")
internal data class AndesModalFullCustomViewConfig(
    val isDismissible: Boolean,
    val isHeaderFixed: Boolean,
    val closeButtonVisibility: Int,
    val buttonGroupCreator: AndesButtonGroupCreator?,
    val buttonGroupVisibility: Int,
    val customView: View?,
    val onDismissCallback: ModalFullAction?,
    val onModalShowCallback: ModalFullAction?,
    val headerTextStatus: AndesModalFullHeaderStatus,
    val headerType: AndesModalFullHeaderType,
    val headerTitle: String,
    val modalDescription: String?
)

internal object AndesModalFullCustomViewConfigFactory {
    fun create(fragmentArguments: AndesModalFullCustomViewFragmentArguments): AndesModalFullCustomViewConfig {
        val buttonGroupCreator = fragmentArguments.buttonGroupCreator
        val isDismissible = fragmentArguments.isDismissible
        val headerTitle = fragmentArguments.headerTitle.orEmpty()
        return with(fragmentArguments) {
            AndesModalFullCustomViewConfig(
                isDismissible = isDismissible,
                isHeaderFixed = isHeaderFixed,
                closeButtonVisibility = resolveCloseButtonVisibility(isDismissible),
                buttonGroupCreator = buttonGroupCreator,
                buttonGroupVisibility = resolveButtonGroupVisibility(buttonGroupCreator),
                customView = customView,
                onDismissCallback = onDismissCallback,
                onModalShowCallback = onModalShowCallback,
                headerTextStatus = AndesModalFullHeaderStatus.EXPANDED,
                headerType = resolveHeaderType(headerTitle, isDismissible),
                headerTitle = headerTitle,
                modalDescription = modalDescription
            )
        }
    }

    private fun resolveButtonGroupVisibility(buttonGroupCreator: AndesButtonGroupCreator?) =
        buttonGroupCreator?.let { View.VISIBLE } ?: View.GONE

    private fun resolveCloseButtonVisibility(isDismissible: Boolean) =
        if (isDismissible) {
            View.VISIBLE
        } else {
            View.GONE
        }

    private fun resolveHeaderType(title: String, isDismissible: Boolean) =
        if (title.isNotEmpty()) {
            if (isDismissible) {
                AndesModalFullHeaderType.TITLE_CLOSE
            } else {
                AndesModalFullHeaderType.ONLY_TITLE
            }
        } else {
            if (isDismissible) {
                AndesModalFullHeaderType.ONLY_CLOSE
            } else {
                AndesModalFullHeaderType.HEADER_NONE
            }
        }
}
