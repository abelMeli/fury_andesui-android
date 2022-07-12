package com.mercadolibre.android.andesui.demoapp.components.modal

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalComponentsProvider.provideButtonGroupCreator
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalComponentsProvider.provideCardContent
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalComponentsProvider.provideCardLargeContent
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalComponentsProvider.provideContentList
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalComponentsProvider.provideCustomViewFromCode
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalComponentsProvider.provideLargeContentList
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicModalCardBinding
import com.mercadolibre.android.andesui.dropdown.AndesDropDownItem
import com.mercadolibre.android.andesui.dropdown.utils.AndesDropdownDelegate
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.modal.AndesModal
import com.mercadolibre.android.andesui.modal.card.dialogfragment.AndesDialogFragment
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalCardContentVariation

class ModalCardDynamicPage {

    private lateinit var binding: AndesuiDynamicModalCardBinding
    private val modalTypeList by lazy {
        listOf(TYPE_DEFAULT, TYPE_CAROUSEL, TYPE_CUSTOM).map {
            AndesDropDownItem().apply {
                title = it
            }
        }
    }
    private val buttonsAmountList by lazy {
        listOf(
            NO_BUTTONS,
            ONE_BUTTON,
            TWO_BUTTONS,
            THREE_BUTTONS
        ).map { AndesDropDownItem().apply { title = it } }
    }
    private val contentVariationList by lazy {
        AndesModalCardContentVariation.values()
            .map { AndesDropDownItem().apply { title = it.name } }
    }
    private val pagesAmountList by lazy {
        listOf(TWO_PAGES, THREE_PAGES, FOUR_PAGES).map { AndesDropDownItem().apply { title = it } }
    }
    private var selectedType = TYPE_DEFAULT
    private var buttonsAmount = NO_BUTTONS
    private var selectedContentVariation: AndesModalCardContentVariation =
        AndesModalCardContentVariation.NONE
    private var pagesAmount = TWO_PAGES
    private var isHeaderFixed = false
    private var isButtonGroupFixed = false
    private var isDismissible = true
    private var displayLargeContent = false
    private var modalDescription: String? = null

    fun create(containerView: View, cardModalContainer: CardModalContainer) {
        bindView(containerView)
        setupDropdowns()
        setupCheckboxes()
        setupShowButton(containerView.context, cardModalContainer)
        setupAndesTextField()
        setupHeaderTitleTextfield()
    }

    private fun setupCheckboxes() {
        binding.buttonGroupFixedCheckbox.setupCallback(View.OnClickListener {
            isButtonGroupFixed =
                binding.buttonGroupFixedCheckbox.status == AndesCheckboxStatus.SELECTED
        })
        binding.dismissibleCheckbox.setupCallback(View.OnClickListener {
            isDismissible = binding.dismissibleCheckbox.status == AndesCheckboxStatus.SELECTED
        })
        binding.headerFixedCheckbox.setupCallback(View.OnClickListener {
            isHeaderFixed = binding.headerFixedCheckbox.status == AndesCheckboxStatus.SELECTED
        })
        binding.largeContentCheckbox.setupCallback(View.OnClickListener {
            displayLargeContent = binding.largeContentCheckbox.status == AndesCheckboxStatus.SELECTED
        })
    }

    private fun setupDropdowns() {
        setupTypeDropdown()
        setupButtonsDropdown()
        setupContentVariationDropdown()
        setupPagesAmountDropdown()
    }

    private fun setupPagesAmountDropdown() {
        binding.pagesAmountDropdown.apply {
            label = "Amount of pages"
            setItems(pagesAmountList, 0)
            delegate = object : AndesDropdownDelegate {
                override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
                    pagesAmount = pagesAmountList[position].title
                }
            }
        }
    }

    private fun setupContentVariationDropdown() {
        binding.contentVariationDropdown.apply {
            label = "Content variation"
            setItems(contentVariationList, 0)
            delegate = object : AndesDropdownDelegate {
                override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
                    selectedContentVariation =
                        AndesModalCardContentVariation.valueOf(contentVariationList[position].title)
                }
            }
        }
    }

    private fun setupButtonsDropdown() {
        binding.buttonsAmountDropdown.apply {
            label = "Number of buttons"
            setItems(buttonsAmountList, 0)
            delegate = object : AndesDropdownDelegate {
                override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
                    buttonsAmount = buttonsAmountList[position].title
                }
            }
        }
    }

    private fun setupTypeDropdown() {
        binding.modalTypeDropdown.apply {
            label = "Modal type"
            setItems(modalTypeList, 0)
            delegate = object : AndesDropdownDelegate {
                override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
                    when (position) {
                        TYPE_DEFAULT_POSITION -> {
                            binding.contentVariationDropdown.visibility = View.VISIBLE
                            binding.pagesAmountDropdown.visibility = View.GONE
                            binding.headerFixedCheckbox.visibility = View.VISIBLE
                            binding.buttonGroupFixedCheckbox.visibility = View.VISIBLE
                            binding.largeContentCheckbox.visibility = View.VISIBLE
                            binding.modalDescriptionTextfield.visibility = View.GONE
                        }
                        TYPE_CAROUSEL_POSITION -> {
                            binding.contentVariationDropdown.visibility = View.VISIBLE
                            binding.pagesAmountDropdown.visibility = View.VISIBLE
                            binding.headerFixedCheckbox.visibility = View.VISIBLE
                            binding.buttonGroupFixedCheckbox.visibility = View.GONE
                            binding.largeContentCheckbox.visibility = View.VISIBLE
                            binding.modalDescriptionTextfield.visibility = View.GONE
                        }
                        TYPE_CUSTOM_POSITION -> {
                            binding.contentVariationDropdown.visibility = View.GONE
                            binding.pagesAmountDropdown.visibility = View.GONE
                            binding.buttonGroupFixedCheckbox.visibility = View.GONE
                            binding.largeContentCheckbox.visibility = View.VISIBLE
                            binding.modalDescriptionTextfield.visibility = View.VISIBLE
                            binding.headerTitleTf.visibility = View.VISIBLE
                            binding.headerFixedCheckbox.visibility = View.VISIBLE
                        }
                    }
                    selectedType = modalTypeList[position].title
                }
            }
        }
    }

    private fun setupHeaderTitleTextfield() {
        binding.headerTitleTf.apply {
            label = "Title (optional)"
            placeholder = "Enter a title..."
        }
    }

    private fun setupAndesTextField() {
        binding.modalDescriptionTextfield.textWatcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                modalDescription = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // no-op
            }

            override fun afterTextChanged(s: Editable) {
                // no-op
            }
        }
    }

    private fun setupShowButton(context: Context, cardModalContainer: CardModalContainer) {
        binding.modalTriggerButton.setOnClickListener {
            val buttonGroupCreator = provideButtonGroupCreator(context, buttonsAmount.toInt())
            val modalBuilder = when (selectedType) {
                TYPE_DEFAULT -> {
                    val content = if (displayLargeContent) {
                        provideCardLargeContent(context)
                    } else {
                        provideCardContent(context)
                    }
                    val modalBuilder = AndesModal.cardBuilder(content)
                        .withContentVariation(selectedContentVariation)
                        .withIsButtonGroupFixed(isButtonGroupFixed)
                        .withIsHeaderFixed(isHeaderFixed)
                        .withIsDismissible(isDismissible)
                        .withOnDismissCallback {
                            Toast.makeText(
                                context.applicationContext,
                                "Modal Default Dismissed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .withOnModalShowCallback {
                            Toast.makeText(
                                context.applicationContext,
                                "Modal Default Shown",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    if (buttonGroupCreator != null) {
                        modalBuilder.withButtonGroupCreator(buttonGroupCreator)
                    }

                    modalBuilder
                }
                TYPE_CAROUSEL -> {
                    val contentList = if (displayLargeContent) {
                        provideLargeContentList(context, pagesAmount.toInt())
                    } else {
                        provideContentList(context, pagesAmount.toInt())
                    }
                    val modalBuilder =
                        AndesModal.cardBuilder(contentList)
                            .withContentVariation(selectedContentVariation)
                            .withIsHeaderFixed(isHeaderFixed)
                            .withOnPageSelectedCallback { page ->
                                Toast.makeText(
                                    context.applicationContext,
                                    "page $page selected",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .withOnModalShowCallback {
                                Toast.makeText(
                                    context.applicationContext,
                                    "Modal Carousel Shown",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .withOnDismissCallback {
                                Toast.makeText(
                                    context.applicationContext,
                                    "Modal Carousel Dismissed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .withIsDismissible(isDismissible)

                    if (buttonGroupCreator != null) {
                        modalBuilder.withButtonGroupCreator(buttonGroupCreator)
                    }

                    modalBuilder
                }
                else -> {
                    val modalBuilder = AndesModal.cardBuilder(provideCustomViewFromCode(context, displayLargeContent))
                        .withHeaderTitle(binding.headerTitleTf.text.orEmpty())
                        .withIsHeaderFixed(isHeaderFixed)
                        .withDescriptionForA11y(modalDescription.orEmpty())
                        .withIsDismissible(isDismissible)
                        .withOnModalShowCallback {
                            Toast.makeText(
                                context.applicationContext,
                                "Modal Custom Shown",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .withOnDismissCallback {
                            Toast.makeText(
                                context.applicationContext,
                                "Modal Custom Dismissed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    if (buttonGroupCreator != null) {
                        modalBuilder.withButtonGroupCreator(buttonGroupCreator)
                    }

                    modalBuilder
                }
            }

            cardModalContainer.showCardModal(modalBuilder.build())
        }
    }

    private fun bindView(containerView: View) {
        binding = AndesuiDynamicModalCardBinding.bind(containerView)
    }

    private companion object {
        const val NO_BUTTONS = "0"
        const val ONE_BUTTON = "1"
        const val TWO_BUTTONS = "2"
        const val THREE_BUTTONS = "3"
        const val TYPE_DEFAULT_POSITION = 0
        const val TYPE_CAROUSEL_POSITION = 1
        const val TYPE_CUSTOM_POSITION = 2
        const val TWO_PAGES = "2"
        const val THREE_PAGES = "3"
        const val FOUR_PAGES = "4"
        const val TYPE_DEFAULT = "Default"
        const val TYPE_CAROUSEL = "Carousel"
        const val TYPE_CUSTOM = "Custom"
    }
}

/**
 * Interface needed to pass the built modal to the activity, where it will be shown
 */
interface CardModalContainer {
    fun showCardModal(modal: AndesDialogFragment)
}
