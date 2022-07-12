package com.mercadolibre.android.andesui.modal.card.builder

import android.view.View
import com.mercadolibre.android.andesui.modal.card.dialogfragment.AndesDialogFragment
import com.mercadolibre.android.andesui.modal.card.dialogfragment.AndesModalCardCarouselFragment
import com.mercadolibre.android.andesui.modal.card.dialogfragment.AndesModalCardDefaultFragment
import com.mercadolibre.android.andesui.modal.card.dialogfragment.AndesModalCardViewFragment
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalCardContentVariation

/**
 * Base class for the card builders.
 * This class contains the values present in all card variants.
 */
abstract class AndesModalCardBuilder internal constructor() {
    protected var isDismissible: Boolean = DEFAULT_IS_DISMISSIBLE
    protected var buttonGroupCreator: AndesButtonGroupCreator? = null
    protected var onDismissCallback: Action? = null
    protected var onModalShowCallback: Action? = null

    fun withIsDismissible(isDismissible: Boolean) = apply { this.isDismissible = isDismissible }
    fun withButtonGroupCreator(buttonGroupCreator: AndesButtonGroupCreator) = apply { this.buttonGroupCreator = buttonGroupCreator }
    fun withOnDismissCallback(onDismissCallback: Action) = apply { this.onDismissCallback = onDismissCallback }
    fun withOnModalShowCallback(onModalShowCallback: Action) = apply { this.onModalShowCallback = onModalShowCallback }
    abstract fun build(): AndesDialogFragment

    private companion object {
        private const val DEFAULT_IS_DISMISSIBLE = true
    }
}

/**
 * Typealias used to represent a method called in the dismiss/show events for the modal.
 */
typealias Action = () -> Unit

class AndesModalCardCarouselBuilder internal constructor(
    private val contentList: ArrayList<AndesModalContent>
) : AndesModalCardBuilder() {
    private var contentVariation = DEFAULT_CONTENT_VARIATION
    private var isHeaderFixed = DEFAULT_IS_HEADER_FIXED
    private var onPageSelectedCallback: ((position: Int) -> Unit)? = null

    fun withContentVariation(contentVariation: AndesModalCardContentVariation) = apply {
        this.contentVariation = contentVariation
    }

    fun withIsHeaderFixed(isHeaderFixed: Boolean) = apply { this.isHeaderFixed = isHeaderFixed }

    fun withOnPageSelectedCallback(onPageSelectedCallback: (position: Int) -> Unit) = apply {
        this.onPageSelectedCallback = onPageSelectedCallback
    }

    override fun build() = AndesModalCardCarouselFragment.newInstance(
        isDismissible,
        buttonGroupCreator,
        onDismissCallback,
        onModalShowCallback,
        contentVariation,
        isHeaderFixed,
        onPageSelectedCallback,
        contentList
    )

    private companion object {
        private val DEFAULT_CONTENT_VARIATION = AndesModalCardContentVariation.NONE
        private const val DEFAULT_IS_HEADER_FIXED = false
    }
}

class AndesModalCardDefaultBuilder internal constructor(
    private val content: AndesModalContent
) : AndesModalCardBuilder() {
    private var isButtonGroupFixed: Boolean = DEFAULT_IS_BUTTON_GROUP_FIXED
    private var contentVariation = DEFAULT_CONTENT_VARIATION
    private var isHeaderFixed = DEFAULT_IS_HEADER_FIXED

    fun withIsButtonGroupFixed(isButtonGroupFixed: Boolean) =
        apply { this.isButtonGroupFixed = isButtonGroupFixed }

    fun withContentVariation(contentVariation: AndesModalCardContentVariation) = apply {
        this.contentVariation = contentVariation
    }

    fun withIsHeaderFixed(isHeaderFixed: Boolean) = apply { this.isHeaderFixed = isHeaderFixed }

    override fun build() = AndesModalCardDefaultFragment.newInstance(
        isDismissible,
        isButtonGroupFixed,
        buttonGroupCreator,
        onDismissCallback,
        onModalShowCallback,
        contentVariation,
        isHeaderFixed,
        content
    )

    private companion object {
        private const val DEFAULT_IS_BUTTON_GROUP_FIXED = false
        private val DEFAULT_CONTENT_VARIATION = AndesModalCardContentVariation.NONE
        private const val DEFAULT_IS_HEADER_FIXED = false
    }
}

class AndesModalCardViewBuilder internal constructor(
    private val customView: View
) : AndesModalCardBuilder() {

    private var headerTitle = ""

    private var modalDescription: String? = null

    private var isHeaderFixed = DEFAULT_IS_HEADER_FIXED

    fun withHeaderTitle(title: String) = apply { this.headerTitle = title }

    fun withIsHeaderFixed(isHeaderFixed: Boolean) = apply { this.isHeaderFixed = isHeaderFixed }

    /**
     * Set the description of the modal when we do not have a title in the modal
     * to give more context to the user with accessibility of what is being displayed.
     */
    fun withDescriptionForA11y(description: String) = apply { this.modalDescription = description }

    override fun build() = AndesModalCardViewFragment.newInstance(
        isDismissible,
        isHeaderFixed,
        buttonGroupCreator,
        onDismissCallback,
        onModalShowCallback,
        customView,
        modalDescription,
        headerTitle
    )

    private companion object {
        private const val DEFAULT_IS_HEADER_FIXED = true
    }
}
