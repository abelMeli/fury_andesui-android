package com.mercadolibre.android.andesui.modal.full.factory

import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.ScrollAction
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalFullContentVariation
import com.mercadolibre.android.andesui.modal.full.builder.ModalFullAction
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderStatus
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderType
import com.mercadolibre.android.andesui.stickyscrollview.listener.AndesStickyScrollListener

@Suppress("LongParameterList")
internal data class AndesModalFullDefaultConfig(
    val isButtonGroupFixed: Boolean,
    val isHeaderFixed: Boolean,
    val buttonGroupCreator: AndesButtonGroupCreator?,
    val contentVariation: AndesModalFullContentVariation,
    val content: AndesModalContent?,
    val isDismissible: Boolean,
    val onDismissCallback: ModalFullAction?,
    val onModalShowCallback: ModalFullAction?,
    val headerType: AndesModalFullHeaderType,
    val headerTextStatus: AndesModalFullHeaderStatus,
    val isTitleTextCentered: Boolean,
    val scrollListener: ((ScrollAction) -> AndesStickyScrollListener)?
)

internal object AndesModalFullDefaultConfigFactory {

    fun create(fragmentArguments: AndesModalFullDefaultFragmentArguments): AndesModalFullDefaultConfig {
        val isButtonGroupFixed = fragmentArguments.isButtonGroupFixed
        val isDismissible = fragmentArguments.isDismissible
        val variation = fragmentArguments.contentVariation
        return with(fragmentArguments) {
            AndesModalFullDefaultConfig(
                isButtonGroupFixed = isButtonGroupFixed,
                isHeaderFixed = isHeaderFixed,
                buttonGroupCreator = buttonGroupCreator,
                contentVariation = variation,
                content = content,
                isDismissible = isDismissible,
                onDismissCallback = onDismissCallback,
                onModalShowCallback = onModalShowCallback,
                headerType = resolveHeaderType(variation, isDismissible),
                headerTextStatus = resolveHeaderStatus(variation),
                isTitleTextCentered = resolveIsTitleTextCentered(variation),
                scrollListener = resolveScrollListener(isHeaderFixed)
            )
        }
    }

    private fun resolveIsTitleTextCentered(variation: AndesModalFullContentVariation) =
        variation != AndesModalFullContentVariation.NONE

    private fun resolveHeaderStatus(variation: AndesModalFullContentVariation) =
        if (variation != AndesModalFullContentVariation.NONE) {
            AndesModalFullHeaderStatus.COLLAPSED
        } else {
            AndesModalFullHeaderStatus.EXPANDED
        }

    /**
     * When the variation is NONE, we need to always show the title. So it will be TITLE + CLOSE or
     * TITLE when is not dismissible.
     *
     * Otherwise, we never show the title, so it will be CLOSE or NONE
     */
    private fun resolveHeaderType(
        variation: AndesModalFullContentVariation,
        isDismissible: Boolean
    ) =
        if (variation == AndesModalFullContentVariation.NONE) {
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
