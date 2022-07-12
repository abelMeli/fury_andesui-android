package com.mercadolibre.android.andesui.modal.card.configfactory

import android.view.View
import android.view.ViewOutlineProvider
import com.mercadolibre.android.andesui.modal.card.builder.Action
import com.mercadolibre.android.andesui.modal.card.corners.AndesModalCorners
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.common.ScrollAction
import com.mercadolibre.android.andesui.stickyscrollview.listener.AndesStickyScrollListener

@Suppress("LongParameterList")
internal data class AndesModalCardViewConfig(
    val viewOutlineProvider: ViewOutlineProvider,
    val isDismissible: Boolean,
    val isHeaderFixed: Boolean,
    val closeButtonVisibility: Int,
    val buttonGroupCreator: AndesButtonGroupCreator?,
    val buttonGroupVisibility: Int,
    val customView: View?,
    val onDismissCallback: Action?,
    val onModalShowCallback: Action?,
    val headerTitle: String?,
    val modalDescription: String?,
    val scrollListener: ((ScrollAction) -> AndesStickyScrollListener)?,
    val shouldSetupHeader: Boolean,
    val titleVisibility: Int
)

internal object AndesModalCardViewConfigFactory {

    fun create(fragmentArguments: AndesModalCardViewFragmentArguments): AndesModalCardViewConfig {
        val isDismissible = fragmentArguments.isDismissible
        val buttonGroupCreator = fragmentArguments.buttonGroupCreator
        return with(fragmentArguments) {
            AndesModalCardViewConfig(
                viewOutlineProvider = resolveViewOutlineProvider(buttonGroupCreator),
                isDismissible = isDismissible,
                isHeaderFixed = isHeaderFixed,
                closeButtonVisibility = resolveCloseButtonVisibility(isDismissible),
                buttonGroupCreator = buttonGroupCreator,
                buttonGroupVisibility = resolveButtonGroupVisibility(buttonGroupCreator),
                customView = customView,
                onDismissCallback = onDismissCallback,
                onModalShowCallback = onModalShowCallback,
                headerTitle = headerTitle,
                modalDescription = modalDescription,
                scrollListener = resolveScrollListener(isHeaderFixed),
                shouldSetupHeader = resolveShouldSetupHeader(isHeaderFixed, headerTitle),
                titleVisibility = resolveTitleVisibility(headerTitle)
            )
        }
    }

    private fun resolveButtonGroupVisibility(buttonGroupCreator: AndesButtonGroupCreator?) =
        buttonGroupCreator?.let { View.VISIBLE } ?: View.GONE

    private fun resolveTitleVisibility(headerTitle: String?) = if (headerTitle.isNullOrEmpty()) {
        View.GONE
    } else {
        View.VISIBLE
    }

    private fun resolveShouldSetupHeader(isHeaderFixed: Boolean, headerTitle: String?) =
        isHeaderFixed && !headerTitle.isNullOrEmpty()

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

    private fun resolveViewOutlineProvider(buttonGroupCreator: AndesButtonGroupCreator?) =
        if (buttonGroupCreator != null) {
            AndesModalCorners.TOP_CORNERS.corners.getOutlineProvider()
        } else {
            AndesModalCorners.ALL_CORNERS.corners.getOutlineProvider()
        }

    private fun resolveCloseButtonVisibility(isDismissible: Boolean) =
        if (isDismissible) {
            View.VISIBLE
        } else {
            View.GONE
        }
}
