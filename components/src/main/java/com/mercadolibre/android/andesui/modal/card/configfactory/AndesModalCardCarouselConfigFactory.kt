package com.mercadolibre.android.andesui.modal.card.configfactory

import android.view.View
import android.view.ViewOutlineProvider
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.modal.card.builder.Action
import com.mercadolibre.android.andesui.modal.card.corners.AndesModalCorners
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupData
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.AndesModalInterface
import com.mercadolibre.android.andesui.modal.common.ScrollAction
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalCardContentVariation
import com.mercadolibre.android.andesui.stickyscrollview.listener.AndesStickyScrollListener

@Suppress("LongParameterList")
internal data class AndesModalCardCarouselConfig(
    val scrollViewOutlineProvider: ViewOutlineProvider,
    val isDismissible: Boolean,
    val closeButtonVisibility: Int,
    val buttonGroup: AndesButtonGroup?,
    val contentList: List<AndesModalContent>?,
    val mainAction: Int?,
    val contentVariation: AndesModalCardContentVariation,
    val isHeaderFixed: Boolean,
    val onDismissCallback: Action?,
    val onModalShowCallback: Action?,
    val onPageSelectedCallback: ((position: Int) -> Unit)?,
    val scrollListener: ((ScrollAction) -> AndesStickyScrollListener)?
)

internal object AndesModalCardCarouselConfigFactory {

    fun create(
        fragmentArguments: AndesModalCardCarouselFragmentArguments,
        modalInterface: AndesModalInterface
    ): AndesModalCardCarouselConfig {
        val isDismissible = fragmentArguments.isDismissible
        val buttonGroupData = resolveButtonGroupData(fragmentArguments, modalInterface)
        return with(fragmentArguments) {
            AndesModalCardCarouselConfig(
                scrollViewOutlineProvider = resolveOutlineProvider(),
                isDismissible = isDismissible,
                closeButtonVisibility = resolveCloseButtonVisibility(isDismissible),
                buttonGroup = resolveButtonGroup(buttonGroupData),
                contentList = contentList,
                mainAction = buttonGroupData?.mainAction,
                contentVariation = contentVariation,
                isHeaderFixed = isHeaderFixed,
                onDismissCallback = onDismissCallback,
                onModalShowCallback = onModalShowCallback,
                onPageSelectedCallback = pageSelectedCallback,
                scrollListener = resolveScrollListener(isHeaderFixed)
            )
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
        fragmentArguments: AndesModalCardCarouselFragmentArguments,
        modalInterface: AndesModalInterface
    ) = fragmentArguments.buttonGroupCreator?.create(modalInterface)

    private fun resolveCloseButtonVisibility(isDismissible: Boolean) =
        if (isDismissible) {
            View.VISIBLE
        } else {
            View.GONE
        }

    private fun resolveOutlineProvider() =
        AndesModalCorners.TOP_CORNERS.corners.getOutlineProvider()
}
