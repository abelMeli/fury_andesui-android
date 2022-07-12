package com.mercadolibre.android.andesui.demoapp.components.modal

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalComponentsProvider.provideButtonGroupCreator
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalComponentsProvider.provideCustomViewFromXml
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalComponentsProvider.provideFullContent
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalComponentsProvider.provideFullContentList
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalComponentsProvider.provideFullLargeContent
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalComponentsProvider.provideFullLargeContentList
import com.mercadolibre.android.andesui.demoapp.databinding.ActivityModalFullShowcaseBinding
import com.mercadolibre.android.andesui.modal.AndesModal
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalFullContentVariation

/**
 * Activity created exclusively to show full modals.
 * It consists of only one frame layout to be used as fragment container.
 */
class ModalFullShowcaseActivity : FragmentActivity() {

    private lateinit var binding: ActivityModalFullShowcaseBinding

    companion object {
        const val MODAL_TYPE = "MODAL_TYPE"
        const val BUTTONS_AMOUNT = "BUTTONS_AMOUNT"
        const val HEADER_TITLE = "HEADER_TITLE"
        const val CONTENT_VARIATION = "CONTENT_VARIATION"
        const val IS_HEADER_FIXED = "IS_HEADER_FIXED"
        const val IS_BUTTONGROUP_FIXED = "IS_BUTTONGROUP_FIXED"
        const val IS_DISMISSIBLE = "IS_DISMISSIBLE"
        const val PAGES_AMOUNT = "PAGES_AMOUNT"
        const val DISPLAY_LARGE_CONTENT = "DISPLAY_LARGE_CONTENT"
        const val MODAL_DESCRIPTION = "MODAL_DESCRIPTION"

        private const val DEFAULT_MODAL = "Default"
        private const val CAROUSEL_MODAL = "Carousel"
        private const val CUSTOM_VIEW_MODAL = "Custom"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModalFullShowcaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
    }

    private fun getIntentData() {
        val bundle = intent.extras

        when (bundle?.getString(MODAL_TYPE)) {
            DEFAULT_MODAL -> {
                showDefaultModal(bundle)
            }
            CAROUSEL_MODAL -> {
                showCarouselModal(bundle)
            }
            CUSTOM_VIEW_MODAL -> {
                showCustomViewModal(bundle)
            }
        }
    }

    private fun showCustomViewModal(bundle: Bundle) {
        val headerTitle = bundle.getString(HEADER_TITLE)
        val buttonsAmount = bundle.getString(BUTTONS_AMOUNT) ?: "0"
        val isDismissible = bundle.getBoolean(IS_DISMISSIBLE, true)
        val isHeaderFixed = bundle.getBoolean(IS_HEADER_FIXED, false)
        val modalDescription = bundle.getString(MODAL_DESCRIPTION)

        val buttonGroupCreator = provideButtonGroupCreator(this, buttonsAmount.toInt())

        val modalBuilder = AndesModal.fullBuilder(provideCustomViewFromXml(this))

        if (headerTitle != null) {
            modalBuilder.withHeaderTitle(headerTitle)
        }

        if (buttonGroupCreator != null) {
            modalBuilder.withButtonGroupCreator(buttonGroupCreator)
        }

        if (modalDescription != null) {
            modalBuilder.withDescriptionForA11y(modalDescription)
        }

        modalBuilder
            .withIsHeaderFixed(isHeaderFixed)
            .withOnModalShowCallback {
                Toast.makeText(applicationContext, "Modal shown", Toast.LENGTH_SHORT).show()
            }
            .withOnDismissCallback {
                Toast.makeText(applicationContext, "Modal dismissed", Toast.LENGTH_SHORT).show()
            }
            .withIsDismissible(isDismissible)

        modalBuilder.build().show(this, binding.contentFrame.id)
    }

    private fun showCarouselModal(bundle: Bundle) {
        val buttonsAmount = bundle.getString(BUTTONS_AMOUNT) ?: "0"
        val contentVariation =
            bundle.getParcelable(CONTENT_VARIATION)
                ?: AndesModalFullContentVariation.NONE
        val pagesAmount = bundle.getString(PAGES_AMOUNT) ?: "2"
        val isHeaderFixed = bundle.getBoolean(IS_HEADER_FIXED, false)
        val isDismissible = bundle.getBoolean(IS_DISMISSIBLE, true)
        val displayLargeContent = bundle.getBoolean(DISPLAY_LARGE_CONTENT, false)

        val buttonGroupCreator = provideButtonGroupCreator(this, buttonsAmount.toInt())

        val contentList = if (displayLargeContent) {
            provideFullLargeContentList(this, pagesAmount.toInt())
        } else {
            provideFullContentList(this, pagesAmount.toInt())
        }

        val modalBuilder = AndesModal.fullBuilder(contentList)
            .withContentVariation(contentVariation)
            .withOnPageSelectedCallback { position ->
                Toast.makeText(applicationContext, "Page $position selected", Toast.LENGTH_SHORT)
                    .show()
            }
            .withIsHeaderFixed(isHeaderFixed)
            .withIsDismissible(isDismissible)
            .withOnDismissCallback {
                Toast.makeText(applicationContext, "Modal dismissed", Toast.LENGTH_SHORT).show()
            }
            .withOnModalShowCallback {
                Toast.makeText(applicationContext, "Modal shown", Toast.LENGTH_SHORT).show()
            }

        if (buttonGroupCreator != null) {
            modalBuilder.withButtonGroupCreator(buttonGroupCreator)
        }

        modalBuilder.build().show(this, binding.contentFrame.id)
    }

    private fun showDefaultModal(bundle: Bundle) {
        val buttonsAmount = bundle.getString(BUTTONS_AMOUNT) ?: "0"
        val contentVariation =
            bundle.getParcelable(CONTENT_VARIATION)
                ?: AndesModalFullContentVariation.NONE
        val isHeaderFixed = bundle.getBoolean(IS_HEADER_FIXED, false)
        val isButtonGroupFixed = bundle.getBoolean(IS_BUTTONGROUP_FIXED, false)
        val isDismissible = bundle.getBoolean(IS_DISMISSIBLE, true)
        val displayLargeContent = bundle.getBoolean(DISPLAY_LARGE_CONTENT, false)

        val buttonGroupCreator = provideButtonGroupCreator(this, buttonsAmount.toInt())

        val content = if (displayLargeContent) {
            provideFullLargeContent(this)
        } else {
            provideFullContent(this)
        }

        val modalBuilder = AndesModal.fullBuilder(content)
            .withContentVariation(contentVariation)
            .withIsButtonGroupFixed(isButtonGroupFixed)
            .withIsHeaderFixed(isHeaderFixed)
            .withIsDismissible(isDismissible)
            .withOnDismissCallback {
                Toast.makeText(applicationContext, "Modal dismissed", Toast.LENGTH_SHORT).show()
            }
            .withOnModalShowCallback {
                Toast.makeText(applicationContext, "Modal shown", Toast.LENGTH_SHORT).show()
            }

        if (buttonGroupCreator != null) {
            modalBuilder.withButtonGroupCreator(buttonGroupCreator)
        }

        modalBuilder.build().show(this, binding.contentFrame.id)
    }
}
