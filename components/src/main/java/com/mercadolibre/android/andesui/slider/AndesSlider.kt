package com.mercadolibre.android.andesui.slider

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.slider.Slider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.slider.accessibility.AndesSliderAccessibilityDelegate
import com.mercadolibre.android.andesui.slider.accessibility.AndesSliderAccessibilityEventDispatcher
import com.mercadolibre.android.andesui.slider.factory.AndesSliderAttrs
import com.mercadolibre.android.andesui.slider.factory.AndesSliderAttrsParser
import com.mercadolibre.android.andesui.slider.factory.AndesSliderConfiguration
import com.mercadolibre.android.andesui.slider.factory.AndesSliderConfigurationFactory
import com.mercadolibre.android.andesui.slider.state.AndesSliderState
import com.mercadolibre.android.andesui.slider.steps.AndesSliderSteps
import com.mercadolibre.android.andesui.slider.type.AndesSliderType
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.tooltip.extensions.visible

/**
 * A view that allows picking a value within a given range by sliding a thumb along a horizontal line.
 */
@Suppress("TooManyFunctions")
class AndesSlider : ConstraintLayout {

    private lateinit var sliderTitle: AndesTextView
    private lateinit var labelValue: AndesTextView
    private lateinit var slider: Slider
    private lateinit var leftContent: FrameLayout
    private lateinit var rightContent: FrameLayout
    private lateinit var andesSliderAttrs: AndesSliderAttrs
    private var onValueChangeListener: OnValueChangedListener? = null
    private val a11yEventDispatcher by lazy { AndesSliderAccessibilityEventDispatcher() }

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
            leftContent.removeAllViews()
            leftContent.addView(config.leftComponent)
            leftContent.visibility = View.VISIBLE
        } else {
            leftContent.visibility = View.GONE
        }
    }

    private fun setupRightContent(config: AndesSliderConfiguration) {
        if (config.rightComponent != null) {
            rightContent.removeAllViews()
            rightContent.addView(config.rightComponent)
            rightContent.visibility = View.VISIBLE
        } else {
            rightContent.visibility = View.GONE
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
        initComponents()
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

    private fun initComponents() {
        val container = LayoutInflater.from(context).inflate(R.layout.andes_layout_slider, this)
        slider = container.findViewById(R.id.andes_slider)
        sliderTitle = container.findViewById(R.id.andes_slider_title)
        labelValue = container.findViewById(R.id.andes_slider_label_value)
        leftContent = container.findViewById(R.id.andes_slider_left_component)
        rightContent = container.findViewById(R.id.andes_slider_right_component)
    }

    private fun setupTitleComponent(config: AndesSliderConfiguration) {
        with(sliderTitle) {
            if (!config.text.isNullOrEmpty()) {
                text = config.text
                visible(true)
            } else {
                visible(false)
            }
        }
    }

    private fun setupLabelValueComponent(config: AndesSliderConfiguration) {
        slider.value = config.value
        labelValue.text = config.value.toInt().toString()
        slider.post { updateValueLabelPosition() }
    }

    private fun setupLimitsComponent(config: AndesSliderConfiguration) {
        slider.valueFrom = config.min
        slider.valueTo = config.max
    }

    private fun setupStateComponent(config: AndesSliderConfiguration) {
        slider.isEnabled = config.state.type.isEnabled()
    }

    private fun setupStepsComponent(config: AndesSliderConfiguration) {
        slider.stepSize = config.jumpSteps
        slider.isTickVisible = config.isTickVisible
    }

    private fun setupBackgroundComponent(config: AndesSliderConfiguration) {
        labelValue.setTextColor(config.state.type.textColor(context).colorInt(context))
        slider.haloTintList = ColorStateList.valueOf(config.state.type.haloColor(context).colorInt(context))
        slider.trackActiveTintList = ColorStateList.valueOf(config.state.type.trackActiveColor(context).colorInt(context))
        slider.trackInactiveTintList = ColorStateList.valueOf(config.state.type.trackInactiveColor(context).colorInt(context))
        slider.thumbTintList = ColorStateList.valueOf(config.state.type.thumbColor(context).colorInt(context))
        slider.thumbStrokeColor = ColorStateList.valueOf(config.state.type.thumbStrokeColor(context).colorInt(context))
    }

    private fun setupSliderWatcher(config: AndesSliderConfiguration) {
        slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                labelValue.visibility = INVISIBLE
                slider.thumbStrokeColor =
                    ColorStateList.valueOf(config.state.type.thumbColor(context).colorInt(context))
            }

            override fun onStopTrackingTouch(slider: Slider) {
                slider.thumbStrokeColor = ColorStateList.valueOf(
                    config.state.type.thumbStrokeColor(context).colorInt(context)
                )
                labelValue.text = slider.value.toInt().toString()
                labelValue.visibility = VISIBLE
            }
        })

        slider.addOnChangeListener { _, value, _ ->
            this.value = value
            onValueChangeListener?.onValueChanged(value)
            a11yEventDispatcher.notifyA11yChangedValue(
                this@AndesSlider,
                value,
                accessibilityContentSuffix
            )
        }
    }

    private fun updateValueLabelPosition() {
        val valueRangeSize = slider.valueTo - slider.valueFrom
        val valuePercentStart = (slider.value - slider.valueFrom) / valueRangeSize
        val valuePercentEnd = (slider.valueTo - slider.value) / valueRangeSize

        val start = (valuePercentStart * slider.trackWidth).toInt()
        val end = (valuePercentEnd * slider.trackWidth).toInt()

        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(
            R.id.andes_slider_label_value, ConstraintSet.START,
            slider.id, ConstraintSet.START,
            start
        )
        constraintSet.connect(
            R.id.andes_slider_label_value, ConstraintSet.END,
            slider.id, ConstraintSet.END,
            end
        )
        constraintSet.applyTo(this)
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
