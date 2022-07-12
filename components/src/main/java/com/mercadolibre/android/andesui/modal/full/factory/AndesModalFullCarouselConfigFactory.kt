package com.mercadolibre.android.andesui.modal.full.factory

import android.view.View
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupData
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.AndesModalInterface
import com.mercadolibre.android.andesui.modal.common.ScrollAction
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalFullContentVariation
import com.mercadolibre.android.andesui.modal.full.builder.ModalFullAction
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderStatus
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderType
import com.mercadolibre.android.andesui.stickyscrollview.listener.AndesStickyScrollListener

@Suppress("LongParameterList")
internal data class AndesModalFullCarouselConfig(
    val isDismissible: Boolean,
    val closeButtonVisibility: Int,
    val buttonGroup: AndesButtonGroup?,
    val mainAction: Int?,
    val contentList: List<AndesModalContent>?,
    val contentVariation: AndesModalFullContentVariation,
    val isHeaderFixed: Boolean,
    val onDismissCallback: ModalFullAction?,
    val onModalShowCallback: ModalFullAction?,
    val onPageSelectedCallback: ((position: Int) -> Unit)?,
    val headerType: AndesModalFullHeaderType,
    val headerTextStatus: AndesModalFullHeaderStatus,
    val isTitleTextCentered: Boolean,
    val scrollListener: ((ScrollAction) -> AndesStickyScrollListener)?
)

internal object AndesModalFullCarouselConfigFactory {

    fun create(
        fragmentArguments: AndesModalFullCarouselFragmentArguments,
        modalInterface: AndesModalInterface
    ): AndesModalFullCarouselConfig {
        val isDismissible = fragmentArguments.isDismissible
        val variation = fragmentArguments.contentVariation
        val buttonGroupData = resolveButtonGroupData(fragmentArguments, modalInterface)
        return with(fragmentArguments) {
            AndesModalFullCarouselConfig(
                isDismissible = isDismissible,
                closeButtonVisibility = resolveCloseButtonVisibility(isDismissible),
                buttonGroup = resolveButtonGroup(buttonGroupData),
                mainAction = buttonGroupData?.mainAction,
                contentList = contentList,
                contentVariation = contentVariation,
                isHeaderFixed = isHeaderFixed,
                onDismissCallback = onDismissCallback,
                onModalShowCallback = onModalShowCallback,
                onPageSelectedCallback = pageSelectedCallback,
                headerType = resolveHeaderType(variation, isDismissible),
                headerTextStatus = resolveHeaderStatus(variation),
                isTitleTextCentered = resolveIsTitleCentered(variation),
                scrollListener = resolveScrollListener(isHeaderFixed)
            )
        }
    }

    private fun resolveButtonGroup(buttonGroupData: AndesButtonGroupData?): AndesButtonGroup? {
        return buttonGroupData?.let { data ->
            if (data.mainAction != null) {
                val buttonGroup = data.buttonGroup
                val mainActionButton = buttonGroup.getButton(data.mainAction)
                mainActionButton.hierarchy = AndesButtonHierarchy.QUIET
                buttonGroup
            } else {
                data.buttonGroup
            }
        }
    }

    private fun resolveButtonGroupData(
        fragmentArguments: AndesModalFullCarouselFragmentArguments,
        modalInterface: AndesModalInterface
    ) = fragmentArguments.buttonGroupCreator?.create(modalInterface)

    private fun resolveIsTitleCentered(variation: AndesModalFullContentVariation) =
        variation != AndesModalFullContentVariation.NONE

    private fun resolveCloseButtonVisibility(isDismissible: Boolean) =
        if (isDismissible) {
            View.VISIBLE
        } else {
            View.GONE
        }

    private fun resolveHeaderType(
        variation: AndesModalFullContentVariation,
        isDismissible: Boolean
    ): AndesModalFullHeaderType {
        return if (variation == AndesModalFullContentVariation.NONE) {
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

    private fun resolveHeaderStatus(variation: AndesModalFullContentVariation) =
        if (variation != AndesModalFullContentVariation.NONE) {
            AndesModalFullHeaderStatus.COLLAPSED
        } else {
            AndesModalFullHeaderStatus.EXPANDED
        }

    private fun resolveScrollListener(isHeaderFixed: Boolean): ((ScrollAction) -> AndesStickyScrollListener)? {
        val lambdaToReturn: (ScrollAction) -> AndesStickyScrollListener = { scrollAction ->
            object : AndesStickyScrollListener {
                override fun onScrollChanged(
                    mScrollX: Int,
                    mScrollY: Int,
                    oldX: Int,
                    oldY: Int,
                    isHeaderStickiedToTop: Boolean
                ) {
                    scrollAction.invoke(mScrollY)
                }

                override fun onScrollStopped(stoppedY: Boolean) = Unit
            }
        }
        return lambdaToReturn.takeIf { isHeaderFixed }
    }
}
