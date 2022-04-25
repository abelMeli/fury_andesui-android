package com.mercadolibre.android.andesui.demoapp.components.textfield

import android.content.ClipboardManager
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicTextareaBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicTextcodeBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicTextfieldBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticTextfieldBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.textfield.AndesTextfieldCode
import com.mercadolibre.android.andesui.textfield.TextContextMenuItemListener
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldLeftContent
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldRightContent
import com.mercadolibre.android.andesui.textfield.links.AndesTextfieldLink
import com.mercadolibre.android.andesui.textfield.links.AndesTextfieldLinks
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldCodeState
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import com.mercadolibre.android.andesui.textfield.style.AndesTextfieldCodeStyle
import java.util.Locale
import kotlin.collections.ArrayList

object InflateTextfieldHelper {

    @Suppress("LongMethod")
    // Method used to inflate a textfield usecase for test app
    fun inflateAndesTextfield(context: Context): View {
        val binding = AndesuiDynamicTextfieldBinding.inflate(LayoutInflater.from(context))

        val textfield = binding.andesuiTextfield
        val button = binding.changeButton
        val clearButton = binding.clearButton
        val label = binding.labelText
        val helper = binding.helperText
        val helperLinkFrom = binding.helperLinkFrom
        val helperLinkTo = binding.helperLinkTo
        val placeholder = binding.placeholderText
        val counter = binding.counter
        val mask = binding.mask
        val checkBoxHideIcon = binding.checkboxHideIcon

        counter.setText(COUNTER_DEFAULT_TEXT)
        textfield.counter = COUNTER_DEFAULT

        val inputTypeSpinner = binding.textTypeSpinner
        val typeAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, getInputTypesArray())
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inputTypeSpinner.adapter = typeAdapter

        val stateSpinner = binding.stateSpinner
        val stateAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                context.resources.getStringArray(R.array.andes_textfield_state_2_spinner)
        )
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stateSpinner.adapter = stateAdapter

        val preffixSpinner = binding.prefixSpinner
        val preffixAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                context.resources.getStringArray(R.array.andes_textfield_prefix_spinner)
        )
        preffixAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        preffixSpinner.adapter = preffixAdapter

        val suffixSpinner = binding.suffixSpinner
        val suffixAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                context.resources.getStringArray(R.array.andes_textfield_suffix_spinner)
        )
        suffixAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        suffixSpinner.adapter = suffixAdapter

        button.setOnClickListener {
            textfield.text = ""
            textfield.label = label.text.toString()
            textfield.helper = helper.text.toString()
            val linksFrom = helperLinkFrom.text?.toIntOrNull()
            val linksTo = helperLinkTo.text?.toIntOrNull()
            textfield.helperLinks = if (linksFrom != null || linksTo != null) {
                AndesTextfieldLinks(
                    links = listOf(AndesTextfieldLink(linksFrom ?: 0, linksTo ?: textfield.helper?.length ?: 0)),
                    listener = { Toast.makeText(context.applicationContext, "Clicked on link!", Toast.LENGTH_SHORT).show() })
            } else {
                null
            }
            textfield.placeholder = placeholder.text.toString()

            textfield.counter = counter.text.toString().toIntOrNull() ?: 0

            textfield.state = AndesTextfieldState.valueOf(stateSpinner.selectedItem.toString().toUpperCase())

            if (preffixSpinner.selectedItem.toString().toUpperCase() == NONE) {
                textfield.leftContent = null
            } else {
                textfield.leftContent = AndesTextfieldLeftContent.fromString(preffixSpinner.selectedItem.toString())
            }

            if (suffixSpinner.selectedItem.toString().toUpperCase() == NONE) {
                textfield.rightContent = null
            } else {
                textfield.rightContent = AndesTextfieldRightContent.fromString(
                        suffixSpinner.selectedItem.toString()
                )
                if (textfield.rightContent == AndesTextfieldRightContent.ACTION) {
                    textfield.setAction(
                            "Button",
                            View.OnClickListener {
                                Toast.makeText(context.applicationContext, "Right action pressed", Toast.LENGTH_LONG).show()
                            }
                    )
                } else if (textfield.rightContent == AndesTextfieldRightContent.CHECKBOX) {
                    textfield.setCheckbox(
                            "Checkbox", View.OnClickListener { v ->
                        val checkbox = v as AndesCheckbox
                        Toast.makeText(context.applicationContext, "Status: ${checkbox.status}", Toast.LENGTH_SHORT).show()
                    }
                    )
                } else if (textfield.rightContent == AndesTextfieldRightContent.INDETERMINATE) {
                    textfield.setIndeterminate()
                }
            }

            mask.text.takeIf { !it.isNullOrEmpty() }?.apply {
                textfield.setTextFieldMask(it.toString())
            }

            if (!mask.text.isNullOrEmpty()) {
                textfield.setTextFieldMask(mask.text.toString())
            }

            val selectedInputType = getInputTypesArray().single {
                it.name == inputTypeSpinner.selectedItem.toString()
            }.value
            textfield.inputType = selectedInputType

            if (checkBoxHideIcon.isChecked) {
                textfield.setRightIcon("andes_ui_helper_16", hideWhenType = true)
            }
        }

        clearButton.setOnClickListener {
            // reset UI
            label.text = null
            placeholder.placeholder = context.resources.getString(R.string.andes_textfield_placeholder_text)
            placeholder.text = null
            helper.text = null
            counter.setText(COUNTER_DEFAULT_TEXT)
            mask.text = ""
            stateSpinner.setSelection(0)
            inputTypeSpinner.setSelection(0)
            preffixSpinner.setSelection(0)
            suffixSpinner.setSelection(0)

            // reset AndesTextfield's properties.
            textfield.text = ""
            textfield.label = null
            textfield.placeholder = null
            textfield.helper = null
            textfield.counter = COUNTER_DEFAULT
            textfield.state = AndesTextfieldState.IDLE
            textfield.inputType = InputType.TYPE_CLASS_DATETIME
            textfield.leftContent = null
            textfield.rightContent = null
            checkBoxHideIcon.isChecked = false

            textfield.clearMask()
        }

        return binding.root
    }

    fun inflateAndesTextfieldArea(context: Context): View {
        val binding = AndesuiDynamicTextareaBinding.inflate(LayoutInflater.from(context))
        val textarea = binding.andesuiTextarea
        val button = binding.changeButton
        val clearButton = binding.clearButton
        val label = binding.labelText
        val helper = binding.helperText
        val placeholder = binding.placeholderText
        val counter = binding.counter
        counter.setText(COUNTER_DEFAULT_TEXT)
        textarea.counter = COUNTER_DEFAULT

        val stateSpinner = binding.stateSpinner
        val stateAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                context.resources.getStringArray(R.array.andes_textfield_state_2_spinner)
        )
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stateSpinner.adapter = stateAdapter

        button.setOnClickListener {
            textarea.text = ""
            textarea.label = label.text.toString()
            textarea.helper = helper.text.toString()
            textarea.placeholder = placeholder.text.toString()
            textarea.counter = counter.text.toString().toIntOrNull() ?: 0
            textarea.state = AndesTextfieldState.valueOf(stateSpinner.selectedItem.toString().toUpperCase())
        }

        clearButton.setOnClickListener {
            // reset UI
            label.text = null
            placeholder.placeholder = context.resources.getString(R.string.andes_textfield_placeholder_text)
            placeholder.text = null
            helper.text = null
            counter.setText(COUNTER_DEFAULT_TEXT)
            stateSpinner.setSelection(0)

            // reset AndesTextfield's properties.
            textarea.text = ""
            textarea.label = null
            textarea.placeholder = null
            textarea.helper = null
            textarea.counter = COUNTER_DEFAULT
            textarea.state = AndesTextfieldState.IDLE
        }

        return binding.root
    }

    fun inflateAndesTextfieldCode(context: Context): View {
        val binding = AndesuiDynamicTextcodeBinding.inflate(LayoutInflater.from(context))

        val textfieldCode = binding.andesuiTextfieldCode
        val updateButton = binding.changeButton
        val clearButton = binding.clearButton
        val stateSpinner = binding.stateSpinner
        val styleSpinner = binding.styleSpinner
        val text = binding.text
        val label = binding.labelText
        val helper = binding.helperText

        configureStateAdapter(stateSpinner)
        configureStyleAdapter(styleSpinner)
        addAndesTextfieldCodeListener(textfieldCode)

        updateButton.setOnClickListener {
            val currentState = textfieldCode.state
            val newState = AndesTextfieldCodeState.valueOf(stateSpinner.selectedItem.toString().toUpperCase(Locale.getDefault()))
            if (currentState != newState) {
                textfieldCode.state = newState
            }
            val currentStyle = textfieldCode.style
            val newStyle = AndesTextfieldCodeStyle.valueOf(styleSpinner.selectedItem.toString().toUpperCase(Locale.getDefault()))
            if (currentStyle != newStyle) {
                textfieldCode.style = newStyle
            }

            textfieldCode.text = text.text
            textfieldCode.label = label.text
            textfieldCode.helper = helper.text
        }

        clearButton.setOnClickListener {
            // reset UI
            text.text = null
            label.text = null
            helper.text = null
            stateSpinner.setSelection(0)
            styleSpinner.setSelection(0)

            // reset AndesTextfieldCode's properties.
            textfieldCode.text = ""
            textfieldCode.label = null
            textfieldCode.helper = null
            textfieldCode.state = AndesTextfieldCodeState.IDLE
            textfieldCode.style = AndesTextfieldCodeStyle.THREESOME
        }

        return binding.root
    }

    fun inflateStaticTextfieldLayout(context: Context): View {
        val binding = AndesuiStaticTextfieldBinding.inflate(LayoutInflater.from(context))

        // Set action clear
        val textfield1 = binding.andesTextfield1
        textfield1.text = context.resources.getString(R.string.andes_textfield_placeholder)
        textfield1.textWatcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Toast.makeText(context.applicationContext, "Text changed: $s", Toast.LENGTH_SHORT).show()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // no-op
            }

            override fun afterTextChanged(s: Editable) {
                // no-op
            }
        }

        // Set text
        val textfield2 = binding.andesTextfield2
        textfield2.text = context.resources.getString(R.string.andes_textfield_placeholder)

        // Set action
        val textfield3 = binding.andesTextfield3
        textfield3.setAction(
                "Button",
                View.OnClickListener {
                    Toast.makeText(context.applicationContext, "Action pressed", Toast.LENGTH_LONG).show()
                }
        )

        // Set text
        val textfield4 = binding.andesTextfield4
        textfield4.text = context.resources.getString(R.string.andes_textfield_placeholder)

        // Set setTextContextMenuItemListener with default paste behaviour
        val textfield5 = binding.andesTextfield5
        textfield5.setTextContextMenuItemListener(object : TextContextMenuItemListener {
            override fun onPaste(): Boolean {
                Toast.makeText(context.applicationContext, "default paste performed", Toast.LENGTH_SHORT).show()
                return false
            }
        })

        // Set setTextContextMenuItemListener with custom paste behaviour
        val textfield6 = binding.andesTextfield6
        textfield6.setTextContextMenuItemListener(object : TextContextMenuItemListener {
            override fun onPaste(): Boolean {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                val textToPaste = clipboard?.primaryClip?.getItemAt(0)?.text?.trim()
                val formattedText =  textToPaste.toString().toUpperCase(Locale.getDefault())
                textfield6.text = formattedText
                textfield6.setSelection(formattedText.length)
                Toast.makeText(context.applicationContext, "custom paste performed", Toast.LENGTH_SHORT).show()
                return true
            }
        })

        // Set left icon
        val textViewLeftIcon = binding.textViewLeftIcon
        textViewLeftIcon.setLeftIcon("andes_navegacion_buscar_24")

        ContextCompat.getDrawable(context, com.mercadolibre.android.andesui.R.drawable.andes_navegacion_ajustes)

        binding.andesuiDemoappAndesSpecsButton.setOnClickListener {
            launchSpecs(context, AndesSpecs.TEXTFIELD)
        }

        return binding.root
    }

    private fun configureStateAdapter(stateSpinner: Spinner) {
        val stateAdapter = ArrayAdapter(
                stateSpinner.context,
                android.R.layout.simple_spinner_item,
                stateSpinner.resources.getStringArray(R.array.andes_textfield_code_state_spinner)
        )
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stateSpinner.adapter = stateAdapter
    }

    private fun configureStyleAdapter(styleSpinner: Spinner) {
        val styleAdapter = ArrayAdapter(
                styleSpinner.context,
                android.R.layout.simple_spinner_item,
                styleSpinner.resources.getStringArray(R.array.andes_textfield_code_style_spinner)
        )
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        styleSpinner.adapter = styleAdapter
    }

    private fun addAndesTextfieldCodeListener(textfieldCode: AndesTextfieldCode) {
        textfieldCode.setOnTextChangeListener(object : AndesTextfieldCode.OnTextChangeListener {
            override fun onChange(text: String) {
                Log.i("ANDES", "TEXT CHANGE: $text")
            }
        })

        textfieldCode.setOnCompleteListener(object : AndesTextfieldCode.OnCompletionListener {
            override fun onComplete(isFull: Boolean) {
                if (isFull) {
                    Log.i("ANDES", "TEXT COMPLETE: ${textfieldCode.text}")
                }
            }
        })
    }

    @Suppress("LongMethod")
    private fun getInputTypesArray(): ArrayList<InputTypeItem> {
        val inputTypes = ArrayList<InputTypeItem>()

        inputTypes.add(
                InputTypeItem("date", InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_DATE)
        )
        inputTypes.add(
                InputTypeItem("datetime", InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_NORMAL)
        )
        inputTypes.add(InputTypeItem("none", InputType.TYPE_NULL))
        inputTypes.add(
                InputTypeItem("number", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL)
        )
        inputTypes.add(
                InputTypeItem("numberDecimal", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
        )
        inputTypes.add(
                InputTypeItem("numberPassword", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD)
        )
        inputTypes.add(
                InputTypeItem("numberSigned", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED)
        )
        inputTypes.add(InputTypeItem("phone", InputType.TYPE_CLASS_PHONE))
        inputTypes.add(InputTypeItem("text", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL))
        inputTypes.add(InputTypeItem("textAutoComplete", InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE))
        inputTypes.add(InputTypeItem("textAutoCorrect", InputType.TYPE_TEXT_FLAG_AUTO_CORRECT))
        inputTypes.add(InputTypeItem("textCapCharacters", InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS))
        inputTypes.add(InputTypeItem("textCapSentences", InputType.TYPE_TEXT_FLAG_CAP_SENTENCES))
        inputTypes.add(InputTypeItem("textCapWords", InputType.TYPE_TEXT_FLAG_CAP_WORDS))
        inputTypes.add(
                InputTypeItem(
                        "textEmailAddress",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                )
        )
        inputTypes.add(
                InputTypeItem(
                        "textEmailSubject",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT
                )
        )
        inputTypes.add(InputTypeItem("textFilter", InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE))
        inputTypes.add(
                InputTypeItem(
                        "textLongMessage",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE
                )
        )
        inputTypes.add(InputTypeItem("textMultiLine", InputType.TYPE_TEXT_FLAG_MULTI_LINE))
        inputTypes.add(InputTypeItem("textNoSuggestions", InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS))
        inputTypes.add(
                InputTypeItem("textPassword", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        )
        inputTypes.add(
                InputTypeItem("textPersonName", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
        )
        inputTypes.add(
                InputTypeItem("textPhonetic", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PHONETIC)
        )
        inputTypes.add(
                InputTypeItem(
                        "textPostalAddress",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS
                )
        )
        inputTypes.add(
                InputTypeItem(
                        "textShortMessage",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE
                )
        )
        inputTypes.add(InputTypeItem("textUri", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_URI))
        inputTypes.add(
                InputTypeItem(
                        "textVisiblePassword",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                )
        )
        inputTypes.add(
                InputTypeItem(
                        "textWebEditText",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT
                )
        )
        inputTypes.add(
                InputTypeItem(
                        "textWebEmailAddress",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
                )
        )
        inputTypes.add(
                InputTypeItem(
                        "textWebPassword",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD
                )
        )
        inputTypes.add(
                InputTypeItem("time", InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_TIME)
        )

        return inputTypes
    }

    internal class InputTypeItem(val name: String, val value: Int) {
        override fun toString(): String {
            return this.name
        }
    }

    private const val NONE = "NONE"
    private const val COUNTER_DEFAULT_TEXT = "50"
    private const val COUNTER_DEFAULT = 50
}
