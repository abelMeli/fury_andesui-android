package com.mercadolibre.android.andesui.slider

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.Slider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesLayoutSliderBinding
import com.mercadolibre.android.andesui.slider.accessibility.AndesSliderAccessibilityDelegate
import com.mercadolibre.android.andesui.slider.accessibility.AndesSliderAccessibilityEventDispatcher
import com.mercadolibre.android.andesui.slider.factory.AndesSliderAttrs
import com.mercadolibre.android.andesui.slider.factory.AndesSliderAttrsParser
import com.mercadolibre.android.andesui.slider.factory.AndesSliderConfiguration
import com.mercadolibre.android.andesui.slider.factory.AndesSliderConfigurationFactory
import com.mercadolibre.android.andesui.slider.state.AndesSliderState
import com.mercadolibre.android.andesui.slider.steps.AndesSliderSteps
import com.mercadolibre.android.andesui.slider.type.AndesSliderType
import com.mercadolibre.android.andesui.tooltip.extensions.visible

/**
 * A view that allows picking a value within a given range by sliding a thumb along a horizontal line.
 */
@Suppress("TooManyFunctions")
class AndesSlider : ConstraintLayout {

    private lateinit var andesSliderAttrs: AndesSliderAttrs
    private var onValueChangeListener: OnValueChangedListener? = null
    private val a11yEventDispatcher by lazy { AndesSliderAccessibilityEventDispatcher() }
    private val binding by lazy {
        AndesLayoutSliderBinding.inflate(LayoutInflater.from(context), this)
    }
    private var formatter: LabelFormatter? = null

    /**
     * Getter and setter for [text].
     */
    var text: String?
        get() = andesSliderAttrs.andesSliderTitle
        set(value) {
            andesSliderAttrs = andesSliderAttrs.copy(andesSliderTitle = value)
            setupTitleComponent(createConfig())
        }

    /**
     * Getter and setter for [state].
     */
    var state: AndesSliderState
        get() = andesSliderAttrs.andesSliderState
        set(value) {
            andesSliderAttrs = andesSliderAttrs.copy(andesSliderState = value)
            val config = createConfig()
            setupStateComponent(config)
            setupBackgroundComponent(config)
        }

    /**
     * Getter and setter for [min].
     */
    var min: Float
        get() = andesSliderAttrs.andesSliderMin
        set(value) {
            andesSliderAttrs = andesSliderAttrs.copy(andesSliderMin = value)
            setupLimitsComponent(createConfig())
        }

    /**
     * Getter and setter for [max].
     */
    var max: Float
        get() = andesSliderAttrs.andesSliderMax
        set(value) {
            andesSliderAttrs = andesSliderAttrs.copy(andesSliderMax = value)
            setupLimitsComponent(createConfig())
        }

    /**
     * Getter and setter for [max].
     */
    var value: Float
        get() = andesSliderAttrs.andesSliderValue
        set(value) {
            andesSliderAttrs = andesSliderAttrs.copy(andesSliderValue = value)
            setupLabelValueComponent(createConfig())
        }

    /**
     * Getter and setter for [steps].
     */
    var steps: AndesSliderSteps
        get() = andesSliderAttrs.andesSliderSteps
        set(value) {
            andesSliderAttrs = andesSliderAttrs.copy(andesSliderSteps = value)
            setupStepsComponent(createConfig())
        }

    /**
     * Getter and setter for [type].
     */
    var type: AndesSliderType
        get() = andesSliderAttrs.andesSliderType
        set(value) {
            andesSliderAttrs = andesSliderAttrs.copy(andesSliderType = value)
            setupTypeComponent(createConfig())
        }

    /**
     * Getter and setter for [accessibilityContentSuffix]. Used to give context to talkback
     * contentDescription announcements.
     */
    var accessibilityContentSuffix: String
        get() = andesSliderAttrs.andesSliderA11ySuffix
        set(value) {
            andesSliderAttrs = andesSliderAttrs.copy(andesSliderA11ySuffix = value)
        }

    /**
     * Interface definition for a callback invoked when a [AndesSlider]'s value is changed.
     */
    interface OnValueChangedListener {
        fun onValueChanged(value: Float)
    }

    /**
     * Set a handler to listen when a [AndesSlider]'s value is changed.
     */
    fun setOnValueChangedListener(listener: OnValueChangedListener) {
        onValueChangeListener = listener
    }

    @Suppress("unused")
    private constructor(context: Context) : super(context) {
        throw IllegalStateException(
            "Constructor without parameters in Andes Slider is not allowed. You must provide some attributes."
        )
    }

    @Suppress("LongParameterList")
    constructor(
        context: Context,
        min: Float,
        max: Float,
        value: Float = min,
        text: String? = TEXT_DEFAULT,
        type: AndesSliderType = DEFAULT_TYPE,
        numberOfSteps: AndesSliderSteps = DEFAULT_STEPS,
        state: AndesSliderState = DEFAULT_STATE,
        accessibilityContentSuffix: String = EMPTY_STRING
    ) : super(context) {
        initAttrs(text, min, max, value, numberOfSteps, type, state, accessibilityContentSuffix)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    private fun setupTypeComponent(config: AndesSliderConfiguration) {
        setupLeftContent(config)
        setupRightContent(config)
    }

    private fun setupLeftContent(config: AndesSliderConfiguration) {
        if (config.leftComponent != null) {
            binding.andesSliderLeftComponent.removeAllViews()
            binding.andesSliderLeftComponent.addView(config.leftComponent)
            binding.andesSliderLeftComponent.visibility = View.VISIBLE
        } else {
            binding.andesSliderLeftComponent.visibility = View.GONE
        }
    }

    private fun setupRightContent(config: AndesSliderConfiguration) {
        if (config.rightComponent != null) {
            binding.andesSliderRightComponent.removeAllViews()
            binding.andesSliderRightComponent.addView(config.rightComponent)
            binding.andesSliderRightComponent.visibility = View.VISIBLE
        } else {
            binding.andesSliderRightComponent.visibility = View.GONE
        }
    }

    private fun initAttrs(
        text: String?,
        min: Float,
        max: Float,
        value: Float,
        steps: AndesSliderSteps,
        type: AndesSliderType,
        state: AndesSliderState,
        accessibilityContentSuffix: String
    ) {
        andesSliderAttrs = AndesSliderAttrs(text, state, min, max, value, steps, type, accessibilityContentSuffix)
        val config = AndesSliderConfigurationFactory.create(andesSliderAttrs, context)
        setupComponents(config)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        andesSliderAttrs = AndesSliderAttrsParser.parse(context, attrs)
        val config = AndesSliderConfigurationFactory.create(andesSliderAttrs, context)
        setupComponents(config)
    }

    private fun setupComponents(config: AndesSliderConfiguration) {
        setupViewId()
        setupTitleComponent(config)
        setupLimitsComponent(config)
        setupTypeComponent(config)
        setupLabelValueComponent(config)
        setupStepsComponent(config)
        setupBackgroundComponent(config)
        setupSliderWatcher(config)
        setupA11y()
    }

    private fun setupA11y() {
        isFocusable = true
        accessibilityDelegate = AndesSliderAccessibilityDelegate(this)
    }

    private fun setupViewId() {
        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }
    }

    private fun setupTitleComponent(config: AndesSliderConfiguration) {
        with(binding.andesSliderTitle) {
            if (!config.text.isNullOrEmpty()) {
                text = config.text
                visible(true)
            } else {
                visible(false)
            }
        }
    }

    private fun setupLabelValueComponent(config: AndesSliderConfiguration) {
        binding.andesSlider.value = config.value
        resolveFormatter(config.value)
        binding.andesSlider.post { updateValueLabelPosition() }
    }

    private fun setupLimitsComponent(config: AndesSliderConfiguration) {
        binding.andesSlider.valueFrom = config.min
        binding.andesSlider.valueTo = config.max
        binding.andesSlider.value = config.value
    }

    private fun setupStateComponent(config: AndesSliderConfiguration) {
        binding.andesSlider.isEnabled = config.state.type.isEnabled()
    }

    private fun setupStepsComponent(config: AndesSliderConfiguration) {
        binding.andesSlider.stepSize = config.jumpSteps
        binding.andesSlider.isTickVisible = config.isTickVisible
    }

    private fun setupBackgroundComponent(config: AndesSliderConfiguration) {
        binding.andesSliderLabelValue.setTextColor(config.state.type.textColor(context).colorInt(context))
        binding.andesSlider.haloTintList = ColorStateList.valueOf(config.state.type.haloColor(context).colorInt(context))
        binding.andesSlider.trackActiveTintList = ColorStateList.valueOf(config.state.type.trackActiveColor(context).colorInt(context))
        binding.andesSlider.trackInactiveTintList = ColorStateList.valueOf(config.state.type.trackInactiveColor(context).colorInt(context))
        binding.andesSlider.thumbTintList = ColorStateList.valueOf(config.state.type.thumbColor(context).colorInt(context))
        binding.andesSlider.thumbStrokeColor = ColorStateList.valueOf(config.state.type.thumbStrokeColor(context).colorInt(context))
    }

    private fun setupSliderWatcher(config: AndesSliderConfiguration) {
        binding.andesSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                binding.andesSliderLabelValue.visibility = INVISIBLE
                slider.thumbStrokeColor =
                    ColorStateList.valueOf(config.state.type.thumbColor(context).colorInt(context))
            }

            override fun onStopTrackingTouch(slider: Slider) {
                slider.thumbStrokeColor = ColorStateList.valueOf(
                    config.state.type.thumbStrokeColor(context).colorInt(context)
                )
                resolveFormatter(slider.value)
                binding.andesSliderLabelValue.visibility = VISIBLE
                a11yEventDispatcher.notifyA11yChangedValue(
                    this@AndesSlider,
                    value,
                    accessibilityContentSuffix
                )
            }
        })

        binding.andesSlider.addOnChangeListener { _, value, _ ->
            this.value = value
            onValueChangeListener?.onValueChanged(value)
            if (binding.andesSlider.isPressed.not()) {
                a11yEventDispatcher.notifyA11yChangedValue(
                    this@AndesSlider,
                    value,
                    accessibilityContentSuffix
                )
            }
        }
    }

    private fun updateValueLabelPosition() {
        val valueRangeSize = binding.andesSlider.valueTo - binding.andesSlider.valueFrom
        val valuePercentStart = (binding.andesSlider.value - binding.andesSlider.valueFrom) / valueRangeSize
        val valuePercentEnd = (binding.andesSlider.valueTo - binding.andesSlider.value) / valueRangeSize

        val start = (valuePercentStart * binding.andesSlider.trackWidth).toInt()
        val end = (valuePercentEnd * binding.andesSlider.trackWidth).toInt()

        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(
            R.id.andes_slider_label_value, ConstraintSet.START,
            binding.andesSlider.id, ConstraintSet.START,
            start
        )
        constraintSet.connect(
            R.id.andes_slider_label_value, ConstraintSet.END,
            binding.andesSlider.id, ConstraintSet.END,
            end
        )
        constraintSet.applyTo(this)
    }

    /**
     * Set a custom format to current displayed value.
     */
    fun setValueLabelFormatter(labelFormatter: LabelFormatter) {
        formatter = labelFormatter
        binding.andesSlider.setLabelFormatter(formatter)
        resolveFormatter(binding.andesSlider.value)
    }

    private fun resolveFormatter(value: Float) {
        binding.andesSliderLabelValue.text = formatter?.getFormattedValue(value) ?: value.toInt().toString()
    }

    override fun getAccessibilityClassName(): CharSequence {
        return SeekBar::class.java.name
    }

    private fun createConfig() = AndesSliderConfigurationFactory.create(andesSliderAttrs, context)

    internal companion object {
        internal const val DEFAULT_VALUE = 0F
        internal const val DEFAULT_MAX_VALUE = 100F
        internal val DEFAULT_STEPS = AndesSliderSteps.None
        private val DEFAULT_TYPE = AndesSliderType.Simple
        private val DEFAULT_STATE = AndesSliderState.IDLE
        private val TEXT_DEFAULT = null
        private const val EMPTY_STRING = ""
    }
}
