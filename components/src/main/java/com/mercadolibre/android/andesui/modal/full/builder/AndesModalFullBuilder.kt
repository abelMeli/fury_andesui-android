package com.mercadolibre.android.andesui.modal.full.builder

import android.view.View
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalFullContentVariation
import com.mercadolibre.android.andesui.modal.full.fragment.AndesModalBaseFragment
import com.mercadolibre.android.andesui.modal.full.fragment.AndesModalFullCarouselFragment
import com.mercadolibre.android.andesui.modal.full.fragment.AndesModalFullDefaultFragment
import com.mercadolibre.android.andesui.modal.full.fragment.AndesModalFullCustomViewFragment

/**
 * Base class for the full builders.
 * This class contains the values present in all full variants.
 */
abstract class AndesModalFullBuilder internal constructor() {
    protected var isDismissible: Boolean = DEFAULT_IS_DISMISSIBLE
    protected var buttonGroupCreator: AndesButtonGroupCreator? = null
    protected var onDismissCallback: ModalFullAction? = null
    protected var onModalShowCallback: ModalFullAction? = null

    fun withIsDismissible(isDismissible: Boolean) = apply { this.isDismissible = isDismissible }
    fun withButtonGroupCreator(buttonGroupCreator: AndesButtonGroupCreator) =
        apply { this.buttonGroupCreator = buttonGroupCreator }

    fun withOnDismissCallback(onDismissCallback: ModalFullAction) =
        apply { this.onDismissCallback = onDismissCallback }

    fun withOnModalShowCallback(onModalShowCallback: ModalFullAction) =
        apply { this.onModalShowCallback = onModalShowCallback }

    abstract fun build(): AndesModalBaseFragment<*>

    private companion object {
        private const val DEFAULT_IS_DISMISSIBLE = true
    }
}

/**
 * Typealias used to represent a method called in the dismiss/show events for the modal.
 */
typealias ModalFullAction = () -> Unit

class AndesModalFullDefaultBuilder(val content: AndesModalContent) : AndesModalFullBuilder() {

    private var contentVariation = DEFAULT_CONTENT_VARIATION
    private var isHeaderFixed = DEFAULT_IS_HEADER_FIXED

    private var isButtonGroupFixed: Boolean = DEFAULT_IS_BUTTON_GROUP_FIXED

    fun withIsButtonGroupFixed(isButtonGroupFixed: Boolean) =
        apply { this.isButtonGroupFixed = isButtonGroupFixed }

    fun withContentVariation(contentVariation: AndesModalFullContentVariation) = apply {
        this.contentVariation = contentVariation
    }

    fun withIsHeaderFixed(isHeaderFixed: Boolean) = apply { this.isHeaderFixed = isHeaderFixed }

    override fun build(): AndesModalBaseFragment<*> {
        return AndesModalFullDefaultFragment.newInstance(
            isDismissible,
            isButtonGroupFixed,
            buttonGroupCreator,
            onDismissCallback,
            onModalShowCallback,
            contentVariation,
            isHeaderFixed,
            content
        )
    }

    private companion object {
        private val DEFAULT_CONTENT_VARIATION = AndesModalFullContentVariation.NONE
        private const val DEFAULT_IS_HEADER_FIXED = false
        private const val DEFAULT_IS_BUTTON_GROUP_FIXED = false
    }
}

class AndesModalFullCarouselBuilder internal constructor(private val contentList: ArrayList<AndesModalContent>) : AndesModalFullBuilder() {
    private var contentVariation = DEFAULT_CONTENT_VARIATION
    private var isHeaderFixed = DEFAULT_IS_HEADER_FIXED
    private var onPageSelectedCallback: ((position: Int) -> Unit)? = null

    fun withContentVariation(contentVariation: AndesModalFullContentVariation) = apply {
        this.contentVariation = contentVariation
    }

    fun withIsHeaderFixed(isHeaderFixed: Boolean) = apply { this.isHeaderFixed = isHeaderFixed }

    fun withOnPageSelectedCallback(onPageSelectedCallback: (position: Int) -> Unit) = apply {
        this.onPageSelectedCallback = onPageSelectedCallback
    }

    override fun build(): AndesModalBaseFragment<*> {
        return AndesModalFullCarouselFragment.newInstance(
            isDismissible,
            buttonGroupCreator,
            onDismissCallback,
            onModalShowCallback,
            contentVariation,
            isHeaderFixed,
            onPageSelectedCallback,
            contentList
        )
    }

    private companion object {
        private val DEFAULT_CONTENT_VARIATION = AndesModalFullContentVariation.NONE
        private const val DEFAULT_IS_HEADER_FIXED = false
    }
}

class AndesModalFullCustomViewBuilder internal constructor(private val view: View) :
    AndesModalFullBuilder() {

    private var isHeaderFixed = DEFAULT_IS_HEADER_FIXED
    private var headerTitle = ""
    private var modalDescription: String? = null

    fun withIsHeaderFixed(isHeaderFixed: Boolean) = apply { this.isHeaderFixed = isHeaderFixed }

    fun withHeaderTitle(title: String) = apply { this.headerTitle = title }

    fun withDescriptionForA11y(description: String) = apply { this.modalDescription = description }

    override fun build(): AndesModalFullCustomViewFragment {
        return AndesModalFullCustomViewFragment.newInstance(
            isDismissible,
            isHeaderFixed,
            headerTitle,
            buttonGroupCreator,
            onDismissCallback,
            onModalShowCallback,
            view,
            modalDescription
        )
    }

    private companion object {
        private const val DEFAULT_IS_HEADER_FIXED = true
    }
}
