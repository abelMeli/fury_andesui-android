package com.mercadolibre.android.andesui.radiobutton

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesLayoutRadiobuttonBinding
import com.mercadolibre.android.andesui.radiobutton.accessibility.AndesRadioButtonAccessibilityDelegate
import com.mercadolibre.android.andesui.radiobutton.align.AndesRadioButtonAlign
import com.mercadolibre.android.andesui.radiobutton.factory.AndesRadioButtonAttrParser
import com.mercadolibre.android.andesui.radiobutton.factory.AndesRadioButtonAttrs
import com.mercadolibre.android.andesui.radiobutton.factory.AndesRadioButtonConfiguration
import com.mercadolibre.android.andesui.radiobutton.factory.AndesRadioButtonConfigurationFactory
import com.mercadolibre.android.andesui.radiobutton.status.AndesRadioButtonStatus
import com.mercadolibre.android.andesui.radiobutton.type.AndesRadioButtonType
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

@Suppress("TooManyFunctions")
class AndesRadioButton : ConstraintLayout {

    /**
     * Getter and setter for [text].
     */
    var text: String?
        get() = andesRadioButtonAttrs.andesRadioButtonText
        set(value) {
            andesRadioButtonAttrs = andesRadioButtonAttrs.copy(andesRadioButtonText = value)
            setupTitleComponent(createConfig())
        }

    /**
     * Getter and setter for [align].
     */
    var align: AndesRadioButtonAlign
        get() = andesRadioButtonAttrs.andesRadioButtonAlign
        set(value) {
            andesRadioButtonAttrs = andesRadioButtonAttrs.copy(andesRadioButtonAlign = value)
            setupAlignComponent(createConfig())
        }

    /**
     * Getter and setter for [AndesRadioButtonStatus].
     */
    var status: AndesRadioButtonStatus
        get() = andesRadioButtonAttrs.andesRadioButtonStatus
        set(value) {
            andesRadioButtonAttrs = andesRadioButtonAttrs.copy(andesRadioButtonStatus = value)
            val config = createConfig()
            setupBackgroundComponent(config)
            setupEnabled(config)
        }

    /**
     * Getter and setter for [type].
     */
    var type: AndesRadioButtonType
        get() = andesRadioButtonAttrs.andesRadioButtonType
        set(value) {
            andesRadioButtonAttrs = andesRadioButtonAttrs.copy(andesRadioButtonType = value)
            setupBackgroundComponent(createConfig())
        }

    /**
     * Setter [OnClickListener].
     */
    fun setupCallback(@Nullable listener: OnClickListener) {
        if (this.privateListener != listener) {
            this.privateListener = listener
        }
    }

    private var privateListener: OnClickListener? = null
    private lateinit var andesRadioButtonAttrs: AndesRadioButtonAttrs
    private val binding by lazy {
        AndesLayoutRadiobuttonBinding.inflate(LayoutInflater.from(context), this, true)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        text: String,
        align: AndesRadioButtonAlign = ANDES_ALIGN_DEFAULT_VALUE,
        status: AndesRadioButtonStatus = ANDES_STATUS_DEFAULT_VALUE,
        type: AndesRadioButtonType = ANDES_TYPE_DEFAULT_VALUE
    ) : super(context) {
        initAttrs(text, align, status, type)
    }

    /**
     * Sets the proper [config] for this message based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesRadioButtonAttrs = AndesRadioButtonAttrParser.parse(context, attrs)
        val config = AndesRadioButtonConfigurationFactory.create(andesRadioButtonAttrs)
        setupComponents(config)
    }

    private fun initAttrs(
        text: String,
        align: AndesRadioButtonAlign = ANDES_ALIGN_DEFAULT_VALUE,
        status: AndesRadioButtonStatus = ANDES_STATUS_DEFAULT_VALUE,
        type: AndesRadioButtonType = ANDES_TYPE_DEFAULT_VALUE
    ) {
        andesRadioButtonAttrs = AndesRadioButtonAttrs(align, text, status, type)
        val config = AndesRadioButtonConfigurationFactory.create(andesRadioButtonAttrs)
        setupComponents(config)
    }

    private fun setupComponents(config: AndesRadioButtonConfiguration) {
        initComponents()

        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }

        setupTitleComponent(config)
        setupAlignComponent(config)
        setupBackgroundComponent(config)
        setupEnabled(config)
        setupA11yDelegate()
    }

    private fun setupA11yDelegate() {
        accessibilityDelegate = AndesRadioButtonAccessibilityDelegate(this)
    }

    /**
     * Creates all the views that are part of this checkbox.
     * After a view is created then a view id is added to it.
     */
    private fun initComponents() {
        onCheckedChangeListener()
    }

    private fun onCheckedChangeListener() {
        binding.root.setOnClickListener {
            when (type) {
                AndesRadioButtonType.ERROR -> {
                    type = AndesRadioButtonType.IDLE
                    status = AndesRadioButtonStatus.SELECTED
                    privateListener?.onClick(this)
                    setupBackgroundComponent(createConfig())
                }
                AndesRadioButtonType.IDLE -> {
                    status = when (status) {
                        AndesRadioButtonStatus.SELECTED -> AndesRadioButtonStatus.UNSELECTED
                        AndesRadioButtonStatus.UNSELECTED -> AndesRadioButtonStatus.SELECTED
                    }
                    privateListener?.onClick(this)
                    setupBackgroundComponent(createConfig())
                }
            }
        }
    }

    /**
     * Gets data from the config and sets to the title component of this radiobutton.
     */
    private fun setupTitleComponent(config: AndesRadioButtonConfiguration) {
        with(binding.checkboxText) {
            text = config.text
            typeface = context.getFontOrDefault(R.font.andes_font_regular)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.andes_radiobutton_text_size))
            setTextColor(config.type.type.textColor(context).colorInt(context))
        }
    }

    /**
     * Gets data from the config and sets to the align component of this radiobutton.
     */
    private fun setupAlignComponent(config: AndesRadioButtonConfiguration) {
        when (config.align) {
            AndesRadioButtonAlign.LEFT -> {
                binding.leftRadioButton.visibility = View.VISIBLE
                binding.rightRadioButton.visibility = View.GONE
            }
            AndesRadioButtonAlign.RIGHT -> {
                binding.leftRadioButton.visibility = View.GONE
                binding.rightRadioButton.visibility = View.VISIBLE
            }
        }
    }

    /**
     * Draw radiobutton.
     */
    private fun setupBackgroundComponent(config: AndesRadioButtonConfiguration) {
        // Border
        val shapeBorder = GradientDrawable()
        shapeBorder.cornerRadius = resources.getDimension(R.dimen.andes_radiobutton_radius)
        shapeBorder.setStroke(
                resources.getDimension(R.dimen.andes_radiobutton_stroke_width).toInt(),
                config.type.type.borderColor(context, config.status).colorInt(context)
        )
        binding.leftRadioButtonBorder.background = shapeBorder
        binding.rightRadioButtonBorder.background = shapeBorder

        // Background
        val shapeBackground = config.status.status.icon(
                context,
                config.type.type.backgroundColor(context, config.status)
        )
        binding.leftRadioButtonIcon.background = shapeBackground
        binding.rightRadioButtonIcon.background = shapeBackground
    }

    private fun setupEnabled(config: AndesRadioButtonConfiguration) {
        isEnabled = config.type != AndesRadioButtonType.DISABLED
    }

    private fun createConfig() = AndesRadioButtonConfigurationFactory.create(andesRadioButtonAttrs)

    override fun getAccessibilityClassName(): CharSequence {
        return RadioButton::class.java.name
    }

    companion object {
        private val ANDES_ALIGN_DEFAULT_VALUE = AndesRadioButtonAlign.LEFT
        private val ANDES_STATUS_DEFAULT_VALUE = AndesRadioButtonStatus.UNSELECTED
        private val ANDES_TYPE_DEFAULT_VALUE = AndesRadioButtonType.IDLE
    }
}
