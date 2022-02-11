package com.mercadolibre.android.andesui.textfield

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.facebook.drawee.view.SimpleDraweeView
import com.mercadolibre.android.andesui.BuildConfig
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.textfield.factory.AndesTextareaAttrs
import com.mercadolibre.android.andesui.textfield.factory.AndesTextareaAttrsParser
import com.mercadolibre.android.andesui.textfield.factory.AndesTextfieldConfiguration
import com.mercadolibre.android.andesui.textfield.factory.AndesTextfieldConfigurationFactory
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

@Suppress("TooManyFunctions")
class AndesTextarea : ConstraintLayout {

    /**
     * Getter and setter for [text].
     */
    var text: String?
        get() = textComponent.text.toString()
        set(value) {
            textComponent.setText(value)
        }

    /**
     * Getter and setter for [label].
     */
    var label: String?
        get() = andesTextareaAttrs.label
        set(value) {
            andesTextareaAttrs = andesTextareaAttrs.copy(label = value)
            setupLabelComponent(createConfig())
        }

    /**
     * Getter and setter for [helper].
     */
    var helper: String?
        get() = andesTextareaAttrs.helper
        set(value) {
            andesTextareaAttrs = andesTextareaAttrs.copy(helper = value)
            setupHelperComponent(createConfig())
        }

    /**
     * Getter and setter for [placeholder].
     */
    var placeholder: String?
        get() = andesTextareaAttrs.placeholder
        set(value) {
            andesTextareaAttrs = andesTextareaAttrs.copy(placeholder = value)
            setupPlaceHolderComponent(createConfig())
        }

    /**
     * Getter and setter for [counter].
     */
    var counter: Int
        get() = andesTextareaAttrs.counter
        set(value) {
            andesTextareaAttrs = andesTextareaAttrs.copy(counter = value)
            setupCounterComponent(createConfig())
        }

    /**
     * Getter and setter for the state of [EditText].
     */
    var state: AndesTextfieldState
        get() = andesTextareaAttrs.state
        set(value) {
            andesTextareaAttrs = andesTextareaAttrs.copy(state = value)
            val config = createConfig()
            setupEnabledView()
            setupTextComponent(config)
            setupColorComponents(config)
            setupHelperComponent(config)
            setupCounterComponent(config)
        }

    var maxLines: Int?
        get() = andesTextareaAttrs.maxLines
        set(value) {
            andesTextareaAttrs = andesTextareaAttrs.copy(maxLines = value)
            setupMaxlines(createConfig())
        }

    /**
     * Getter and setter for the [textWatcher].
     */
    var textWatcher: TextWatcher?
        get() = andesTextareaAttrs.textWatcher
        set(value) {
            andesTextareaAttrs = andesTextareaAttrs.copy(textWatcher = value)
            setupTextWatcher()
        }

    private lateinit var andesTextareaAttrs: AndesTextareaAttrs
    private lateinit var textareaContainer: ConstraintLayout
    private lateinit var textContainer: ConstraintLayout
    private lateinit var labelComponent: TextView
    private lateinit var helperComponent: TextView
    private lateinit var counterComponent: TextView
    private lateinit var textComponent: EditText
    private lateinit var iconComponent: SimpleDraweeView

    @Suppress("unused", "LongParameterList")
    private constructor(context: Context) : super(context) {
        initAttrs(LABEL_DEFAULT, HELPER_DEFAULT, PLACEHOLDER_DEFAULT, COUNTER_DEFAULT, STATE_DEFAULT, MAXLINES_DEFAULT)
    }

    constructor(
        context: Context,
        label: String? = LABEL_DEFAULT,
        helper: String? = HELPER_DEFAULT,
        placeholder: String? = PLACEHOLDER_DEFAULT,
        counter: Int = COUNTER_DEFAULT,
        state: AndesTextfieldState = STATE_DEFAULT,
        maxLines: Int? = MAXLINES_DEFAULT
    ) : super(context) {
        initAttrs(label, helper, placeholder, counter, state, maxLines)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs) {
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        andesTextareaAttrs = AndesTextareaAttrsParser.parse(context, attrs)
        val config = AndesTextfieldConfigurationFactory.create(context, andesTextareaAttrs)
        setupComponents(config)
    }

    @Suppress("LongParameterList")
    private fun initAttrs(
        label: String?,
        helper: String?,
        placeholder: String?,
        counter: Int,
        state: AndesTextfieldState,
        maxLines: Int?
    ) {
        andesTextareaAttrs = AndesTextareaAttrs(label, helper, placeholder, counter, state, maxLines)
        val config = AndesTextfieldConfigurationFactory.create(context, andesTextareaAttrs)
        setupComponents(config)
    }

    private fun setupComponents(config: AndesTextfieldConfiguration) {
        initComponents()
        setupViewId()
        setupViewAsClickable()
        setupEnabledView()
        setupLabelComponent(config)
        setupHelperComponent(config)
        setupCounterComponent(config)
        setupPlaceHolderComponent(config)
        setupColorComponents(config)
        setupMaxlines(config)
        setupTextWatcher()
        setupTextComponent(config)
    }

    /**
     * Creates all the views that are part of this textfield.
     * After a view is created then a view id is added to it.
     *
     */
    private fun initComponents() {
        val container = LayoutInflater.from(context).inflate(R.layout.andes_layout_textarea, this, true)

        textareaContainer = container.findViewById(R.id.andes_textarea_container)
        textContainer = container.findViewById(R.id.andes_textarea_text_container)
        labelComponent = container.findViewById(R.id.andes_textarea_label)
        helperComponent = container.findViewById(R.id.andes_textarea_helper)
        counterComponent = container.findViewById(R.id.andes_textarea_counter)
        iconComponent = container.findViewById(R.id.andes_textarea_icon)
        textComponent = container.findViewById(R.id.andes_textarea_edittext)
    }

    private fun setupViewId() {
        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }
    }

    private fun setupViewAsClickable() {
        isFocusable = true
        textContainer.isClickable = true
        textContainer.isFocusable = true
    }

    private fun setupEnabledView() {
        if (state == AndesTextfieldState.DISABLED || state == AndesTextfieldState.READONLY) {
            isEnabled = false
            textComponent.isEnabled = isEnabled
            textContainer.isEnabled = isEnabled
            textareaContainer.isEnabled = isEnabled
        } else {
            isEnabled = true
            textComponent.isEnabled = isEnabled
            textContainer.isEnabled = isEnabled
            textareaContainer.isEnabled = isEnabled
        }
    }

    /**
     * Set up the text component.
     */
    private fun setupTextComponent(config: AndesTextfieldConfiguration) {
        setupMarginStartTextComponent()
        textComponent.typeface = config.typeface
    }

    /**
     * Set the TextWatcher of the edit text.
     */
    private fun setupTextWatcher() {
        if (andesTextareaAttrs.textWatcher != null) {
            textComponent.addTextChangedListener(andesTextareaAttrs.textWatcher)
        }
    }

    /**
     * Gets data from the config to sets the color of the components regarding to the state.
     *
     */
    private fun setupColorComponents(config: AndesTextfieldConfiguration) {
        textContainer.background = config.background
        iconComponent.setImageDrawable(config.icon)
        if (config.icon != null && state != AndesTextfieldState.READONLY) {
            if (!config.helperText.isNullOrEmpty()) {
                iconComponent.visibility = View.VISIBLE
            }
        } else {
            iconComponent.visibility = View.GONE
        }

        helperComponent.setTextColor(config.helperColor.colorInt(context))
        helperComponent.typeface = config.helperTypeface

        labelComponent.setTextColor(config.labelColor.colorInt(context))
        labelComponent.typeface = config.typeface

        counterComponent.setTextColor(config.counterColor.colorInt(context))
        counterComponent.typeface = config.typeface

        textComponent.setHintTextColor(config.placeHolderColor.colorInt(context))
    }

    /**
     * Gets data from the config and sets to the Label component.
     *
     */
    private fun setupLabelComponent(config: AndesTextfieldConfiguration) {
        if (config.labelText == null || config.labelText.isEmpty()) {
            labelComponent.visibility = View.GONE
        } else {
            labelComponent.visibility = View.VISIBLE
            labelComponent.text = config.labelText
            labelComponent.setTextSize(TypedValue.COMPLEX_UNIT_PX, config.labelSize)
        }
    }

    /**
     * Gets data from the config and sets to the Helper component.
     *
     */
    private fun setupHelperComponent(config: AndesTextfieldConfiguration) {
        if (config.helperText == null || config.helperText.isEmpty() || state == AndesTextfieldState.READONLY) {
            helperComponent.visibility = View.GONE
        } else {
            helperComponent.visibility = View.VISIBLE
            helperComponent.text = config.helperText
            helperComponent.setTextSize(TypedValue.COMPLEX_UNIT_PX, config.helperSize)
        }
    }

    /**
     * Gets data from the config and sets to the Counter component.
     *
     */
    private fun setupCounterComponent(config: AndesTextfieldConfiguration) {
        if (config.counterLength != 0 && state != AndesTextfieldState.READONLY) {
            counterComponent.visibility = View.VISIBLE
            counterComponent.setTextSize(TypedValue.COMPLEX_UNIT_PX, config.counterSize)
            counterComponent.text = resources.getString(R.string.andes_textfield_counter_text, 0, config.counterLength)
            textComponent.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(config.counterLength))
        } else {
            counterComponent.visibility = View.GONE
        }

        textComponent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                // Do nothing.
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                counterComponent.text = resources.getString(R.string.andes_textfield_counter_text, charSequence!!.length, config.counterLength)
            }
        })
    }

    /**
     * Gets data from the config and sets to the Place Holder component.
     *
     */
    private fun setupPlaceHolderComponent(config: AndesTextfieldConfiguration) {
        textComponent.hint = config.placeHolderText
        textComponent.typeface = config.typeface
    }

    private fun setupMaxlines(config: AndesTextfieldConfiguration) {
        if (config.maxLines != null) {
            if (config.maxLines >= textComponent.minLines) {
                textComponent.maxLines = config.maxLines
            } else {
                when {
                    BuildConfig.DEBUG -> throw IllegalStateException("Max Lines cant be minor than min lines")
                    else -> Log.d("AndesTextarea", "Max Lines cant be minor than min lines")
                }
            }
        }
    }

    private fun setupMarginStartTextComponent() {
        val params = textComponent.layoutParams as LayoutParams
        if (state == AndesTextfieldState.READONLY) {
            params.marginStart = context.resources.getDimension(R.dimen.andes_textfield_label_paddingLeft).toInt()
        } else {
            params.marginStart = context.resources.getDimension(R.dimen.andes_textfield_margin).toInt()
        }
        textComponent.layoutParams = params
    }

    private fun createConfig() = AndesTextfieldConfigurationFactory.create(context, andesTextareaAttrs)

    /**
     * Default values for AndesTextfield basic properties
     */
    companion object {
        private val LABEL_DEFAULT = null
        private val HELPER_DEFAULT = null
        private val PLACEHOLDER_DEFAULT = null
        private const val COUNTER_DEFAULT = 0
        private val STATE_DEFAULT = AndesTextfieldState.IDLE
        private val MAXLINES_DEFAULT = null
    }
}
