package com.mercadolibre.android.andesui.demoapp.components.textview

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicTextviewBinding
import com.mercadolibre.android.andesui.dropdown.AndesDropDownForm
import com.mercadolibre.android.andesui.dropdown.AndesDropDownItem
import com.mercadolibre.android.andesui.dropdown.utils.AndesDropdownDelegate
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBold
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBolds
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.andesui.textview.style.AndesTextViewStyle

@Suppress("TooManyFunctions")
class TextViewDynamicPage {

    private lateinit var textView: AndesTextView
    private lateinit var textFieldText: AndesTextfield
    private lateinit var dropdownStyle: AndesDropDownForm
    private lateinit var dropdownColor: AndesDropDownForm
    private lateinit var updateButton: AndesButton
    private lateinit var clearButton: AndesButton
    private lateinit var styleList: MutableList<AndesDropDownItem>
    private lateinit var colorList: MutableList<AndesDropDownItem>
    private lateinit var textFieldLinkFrom: AndesTextfield
    private lateinit var textFieldLinkTo: AndesTextfield
    private lateinit var textFieldBoldFrom: AndesTextfield
    private lateinit var textFieldBoldTo: AndesTextfield
    private lateinit var checkboxInvertedLink: AndesCheckbox
    private var selectedStyle: AndesTextViewStyle = AndesTextViewStyle.BodyL
    private var selectedColor: AndesTextViewColor = AndesTextViewColor.Primary
    private var bodyLink: AndesBodyLinks? = null
    private var bodyBold: AndesBodyBolds? = null

    fun create(context: Context, containerView: View) {
        initComponents(containerView)
        setupStyleList()
        setupColorList()
        setupStyleDropdown()
        setupColorDropdown()
        setupUpdateButton(context)
        setupClearButton()
    }

    private fun setupBodyLink(context: Context) {
        val from = textFieldLinkFrom.text.takeIf { !it.isNullOrEmpty() }?.toInt() ?: NO_INDEX_VALUE
        val to = textFieldLinkTo.text.takeIf { !it.isNullOrEmpty() }?.toInt() ?: NO_INDEX_VALUE

        bodyLink = if (!(from == NO_INDEX_VALUE && to == NO_INDEX_VALUE)) {
            AndesBodyLinks(
                links = listOf(
                    AndesBodyLink(from, to)
                ),
                listener = {
                    Log.d("TextViewDynamicPage", it.toString())
                    Toast.makeText(context.applicationContext, "Click on link", Toast.LENGTH_SHORT).show()
                }
            )
        } else {
            null
        }
    }

    private fun setupBodyBold() {
        val from = textFieldBoldFrom.text.takeIf { !it.isNullOrEmpty() }?.toInt() ?: NO_INDEX_VALUE
        val to = textFieldBoldTo.text.takeIf { !it.isNullOrEmpty() }?.toInt() ?: NO_INDEX_VALUE

        bodyBold = if (!(from == NO_INDEX_VALUE && to == NO_INDEX_VALUE)) {
            AndesBodyBolds(
                listOf(
                    AndesBodyBold(from, to)
                )
            )
        } else {
            null
        }
    }

    private fun resetValues() {
        selectedColor = AndesTextViewColor.Primary
        selectedStyle = AndesTextViewStyle.BodyL
        textFieldText.text = ""
        textFieldLinkFrom.text = ""
        textFieldLinkTo.text = ""
        textFieldBoldFrom.text = ""
        textFieldBoldTo.text = ""
        bodyLink = null
        bodyBold = null
        dropdownColor.setItems(colorList, DEFAULT_POSITION_DROPDOWN_COLOR)
        dropdownStyle.setItems(styleList, DEFAULT_POSITION_DROPDOWN_STYLE)
    }

    private fun setTextValues() {
        textView.apply {
            text = textFieldText.text.takeIf { !it.isNullOrEmpty() } ?: DEFAULT_TEXT
            style = selectedStyle
            setTextColor(selectedColor)
            bodyLinks = bodyLink
            bodyBolds = bodyBold
            if (selectedColor is AndesTextViewColor.Inverted) {
                setBackgroundColor(resources.getColor(R.color.andes_accent_color))
            } else {
                setBackgroundColor(resources.getColor(R.color.andes_transparent))
            }
            checkboxInvertedLink.status
            isLinkColorInverted = checkboxInvertedLink.status == AndesCheckboxStatus.SELECTED
        }
    }

    private fun setupClearButton() {
        clearButton.setOnClickListener {
            resetValues()
            setTextValues()
        }
    }

    private fun setupUpdateButton(context: Context) {
        updateButton.setOnClickListener {
            setupBodyLink(context)
            setupBodyBold()
            setTextValues()
        }
    }

    private fun setupColorDropdown() {
        val colorDelegate = object : AndesDropdownDelegate {
            override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
                selectedColor = when (colorList[position].title) {
                    "Primary" -> AndesTextViewColor.Primary
                    "Secondary" -> AndesTextViewColor.Secondary
                    "Disabled" -> AndesTextViewColor.Disabled
                    "Inverted" -> AndesTextViewColor.Inverted
                    "Negative" -> AndesTextViewColor.Negative
                    "Caution" -> AndesTextViewColor.Caution
                    "Positive" -> AndesTextViewColor.Positive
                    else -> AndesTextViewColor.Primary
                }
            }
        }
        dropdownColor.label = "Color"
        dropdownColor.setItems(colorList, DEFAULT_POSITION_DROPDOWN_COLOR)
        dropdownColor.delegate = colorDelegate
    }

    private fun setupStyleDropdown() {
        val styleDelegate = object : AndesDropdownDelegate {
            override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
                selectedStyle = when (styleList[position].title) {
                    "TitleXl" -> AndesTextViewStyle.TitleXl
                    "TitleL" -> AndesTextViewStyle.TitleL
                    "TitleM" -> AndesTextViewStyle.TitleM
                    "TitleS" -> AndesTextViewStyle.TitleS
                    "TitleXs" -> AndesTextViewStyle.TitleXs
                    "BodyL" -> AndesTextViewStyle.BodyL
                    "BodyM" -> AndesTextViewStyle.BodyM
                    "BodyS" -> AndesTextViewStyle.BodyS
                    "BodyXs" -> AndesTextViewStyle.BodyXs
                    else -> AndesTextViewStyle.BodyL
                }
            }
        }
        dropdownStyle.label = "Style"
        dropdownStyle.setItems(styleList, DEFAULT_POSITION_DROPDOWN_STYLE)
        dropdownStyle.delegate = styleDelegate
    }

    private fun initComponents(containerView: View) {
        val binding = AndesuiDynamicTextviewBinding.bind(containerView)
        textView = binding.dynamicPageTextview
        textFieldText = binding.dynamicPageTextviewTfText
        dropdownStyle = binding.dynamicPageTextviewDdStyle
        dropdownColor = binding.dynamicPageTextviewDdColor
        updateButton = binding.dynamicPageTextviewUpdateButton
        clearButton = binding.dynamicPageTextviewClearButton
        textFieldLinkFrom = binding.dynamicPageTextviewTfLinkFrom
        textFieldLinkTo = binding.dynamicPageTextviewTfLinkTo
        textFieldBoldFrom = binding.dynamicPageTextviewTfBoldFrom
        textFieldBoldTo = binding.dynamicPageTextviewTfBoldTo
        checkboxInvertedLink = binding.dynamicPageTextviewCheckboxLinkInverted
    }

    private fun setupStyleList() {
        styleList = mutableListOf()
        val styles = listOf(
            "TitleXl",
            "TitleL",
            "TitleM",
            "TitleS",
            "TitleXs",
            "BodyL",
            "BodyM",
            "BodyS",
            "BodyXs"
        )
        styles.forEach {
            styleList.add(createStyleItem(it))
        }
    }

    private fun setupColorList() {
        colorList = mutableListOf()
        val colors = listOf(
            "Primary",
            "Secondary",
            "Disabled",
            "Inverted",
            "Negative",
            "Caution",
            "Positive"
        )
        colors.forEach {
            colorList.add(createColorItem(it))
        }
    }

    private fun createStyleItem(style: String): AndesDropDownItem {
        return AndesDropDownItem().apply {
            title = style
        }
    }

    private fun createColorItem(color: String): AndesDropDownItem {
        return AndesDropDownItem().apply {
            title = color
        }
    }

    private companion object {
        private const val DEFAULT_TEXT = "Andes TextView"
        private const val NO_INDEX_VALUE = 0
        private const val DEFAULT_POSITION_DROPDOWN_COLOR = 0
        private const val DEFAULT_POSITION_DROPDOWN_STYLE = 5
    }
}
