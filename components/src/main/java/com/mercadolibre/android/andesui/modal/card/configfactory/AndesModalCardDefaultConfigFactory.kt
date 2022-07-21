package com.mercadolibre.android.andesui.modal.card.configfactory

import android.view.View
import android.view.ViewOutlineProvider
import com.mercadolibre.android.andesui.modal.card.builder.Action
import com.mercadolibre.android.andesui.modal.card.corners.AndesModalCorners
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.ScrollAction
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalCardContentVariation
import com.mercadolibre.android.andesui.stickyscrollview.listener.AndesStickyScrollListener

@Suppress("LongParameterList")
internal data class AndesModalCardDefaultConfig(
    val isButtonGroupFixed: Boolean,
    val scrollViewOutlineProvider: ViewOutlineProvider,
    val isHeaderFixed: Boolean,
    val buttonGroupCreator: AndesButtonGroupCreator?,
    val contentVariation: AndesModalCardContentVariation,
    val content: AndesModalContent?,
    val isDismissible: Boolean,
    val closeButtonVisibility: Int,
    val onDismissCallback: Action?,
    val onModalShowCallback: Action?,
    val scrollListener: ((ScrollAction) -> AndesStickyScrollListener)?
)

internal object AndesModalCardDefaultConfigFactory {

    fun create(fragmentArguments: AndesModalCardDefaultFragmentArguments): AndesModalCardDefaultConfig {
        val isButtonGroupFixed = fragmentArguments.isButtonGroupFixed
        val isDismissible = fragmentArguments.isDismissible
        return with(fragmentArguments) {
            AndesModalCardDefaultConfig(
                isButtonGroupFixed = isButtonGroupFixed,
                scrollViewOutlineProvider = resolveOutlineProvider(
                    isButtonGroupFixed,
                    buttonGroupCreator
                ),
                isHeaderFixed = isHeaderFixed,
                buttonGroupCreator = buttonGroupCreator,
                contentVariation = contentVariation,
                content = content,
                isDismissible = isDismissible,
                closeButtonVisibility = resolveCloseButtonVisibility(isDismissible),
                onDismissCallback = onDismissCallback,
                onModalShowCallback = onModalShowCallback,
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

    private fun resolveCloseButtonVisibility(isDismissible: Boolean) =
        if (isDismissible) {
            View.VISIBLE
        } else {
            View.GONE
        }

    private fun resolveOutlineProvider(
        isButtonGroupFixed: Boolean,
        buttonGroupCreator: AndesButtonGroupCreator?
    ): ViewOutlineProvider {
        val cornerType = when {
            buttonGroupCreator == null -> AndesModalCorners.ALL_CORNERS
            isButtonGroupFixed -> AndesModalCorners.TOP_CORNERS
            else -> AndesModalCorners.ALL_CORNERS
        }
        return cornerType.corners.getOutlineProvider()
    }
}
