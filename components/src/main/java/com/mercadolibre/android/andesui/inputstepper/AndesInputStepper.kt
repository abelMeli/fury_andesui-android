package com.mercadolibre.android.andesui.inputstepper

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesInputstepperBinding
import com.mercadolibre.android.andesui.inputstepper.accessibility.AndesInputStepperAccessibilityEventDispatcher
import com.mercadolibre.android.andesui.inputstepper.enabledstate.AndesInputStepperEnabledState
import com.mercadolibre.android.andesui.inputstepper.factory.AndesInputStepperAttrs
import com.mercadolibre.android.andesui.inputstepper.factory.AndesInputStepperAttrsParser
import com.mercadolibre.android.andesui.inputstepper.factory.AndesInputStepperContentFactory
import com.mercadolibre.android.andesui.inputstepper.size.AndesInputStepperSize
import com.mercadolibre.android.andesui.inputstepper.status.AndesInputStepperStatus

class AndesInputStepper : ConstraintLayout {

    private val binding by lazy {
        AndesInputstepperBinding.inflate(LayoutInflater.from(context), this, true)
    }
    private val a11yEventDispatcher = AndesInputStepperAccessibilityEventDispatcher()

    var valueListener: AndesInputStepperValueListener? = null

    var dataSource: AndesInputStepperTextDataSource? = null

    var size: AndesInputStepperSize
        set(value) {
            field = value
            post { value.applyConfig(binding) }
        }

    var status: AndesInputStepperStatus
        set(value) {
            field = value
            post { value.applyConfig(binding) }
        }

    var value: Int = 0
        private set

    var step: Int
        set(value) {
            field = value
            setupContentDescription(value)
        }

    var maxValue: Int
        set(value) {
            field = value
            setValue(this.value)
        }

    var minValue: Int
        set(value) {
            field = value
            setValue(this.value)
        }

    init {
        initComponents()
    }

    constructor(
        context: Context,
        size: AndesInputStepperSize,
        status: AndesInputStepperStatus,
        value: Int,
        maxValue: Int,
        minValue: Int,
        step: Int,
    ) : super(context) {
        this.minValue = minValue
        this.maxValue = maxValue
        this.step = step
        this.size = size
        this.status = status
        setValue(value)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs).let {
            this.minValue = it.minValue
            this.maxValue = it.maxValue
            this.step = it.step
            this.size = it.size
            this.status = it.status
            setValue(it.value)
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs) {
        initAttrs(attrs).let {
            this.minValue = it.minValue
            this.maxValue = it.maxValue
            this.step = it.step
            this.size = it.size
            this.status = it.status
            setValue(it.value)
        }
    }

    private fun initAttrs(attrs: AttributeSet?): AndesInputStepperAttrs =
        AndesInputStepperAttrsParser.parse(context, attrs)

    private fun initComponents() {
        binding.apply {
            andesInputstepperActionAdd.setOnClickListener {
                performEvent(AndesInputStepperEvent.NEXT)
            }

            andesInputstepperActionRemove.setOnClickListener {
                performEvent(AndesInputStepperEvent.PREVIOUS)
            }
        }
    }

    fun setValue(value: Int) {
        performValue(value)
    }

    fun getText(): String {
        return binding.andesInputstepperPointer.text.toString()
    }

    fun performEvent(event: AndesInputStepperEvent) {
        when (event) {
            AndesInputStepperEvent.PREVIOUS -> value - step
            AndesInputStepperEvent.NEXT -> value + step
        }.let { performValue(it, event) }
    }

    private fun setupContentDescription(stepValue: Int) {
        val addDes = String.format(resources.getString(R.string.andes_inputstepper_add_content_description), stepValue)
        val removeDes =
            String.format(resources.getString(R.string.andes_inputstepper_remove_content_description), stepValue)

        binding.andesInputstepperActionRemove.contentDescription = removeDes
        binding.andesInputstepperActionAdd.contentDescription = addDes
    }

    private fun performValue(requestedValue: Int, event: AndesInputStepperEvent? = null) {
        val content = AndesInputStepperContentFactory.create(
            currentValue = value,
            requestedValue = requestedValue,
            maxValue = maxValue,
            minValue = minValue,
            dataSource = dataSource
        )

        setupState(content.state)
        setupText(content.text)

        if (content.mustNotify) {
            notifyOnValueSelected(content.value, content.state, event)
            notifyA11yValueChanged(content.text.toString())
        }
    }

    private fun setupState(state: AndesInputStepperEnabledState) {
        state.applyConfig(binding)
    }

    private fun setupText(text: CharSequence) {
        binding.andesInputstepperPointer.text = text
    }

    private fun notifyOnValueSelected(
        value: Int,
        state: AndesInputStepperEnabledState,
        event: AndesInputStepperEvent?,
    ) {
        this.value = value
        valueListener?.onValueSelected(
            sender = event,
            value = value,
            state = state
        )
    }

    private fun notifyA11yValueChanged(text: String) {
        a11yEventDispatcher.notifyA11ySpinnerSetValue(binding.andesInputstepperPointer, text)
    }
}

enum class AndesInputStepperEvent {
    PREVIOUS, NEXT
}

interface AndesInputStepperTextDataSource {
    fun getText(value: Int): CharSequence?
}

interface AndesInputStepperValueListener {
    fun onValueSelected(sender: AndesInputStepperEvent?, value: Int, state: AndesInputStepperEnabledState)
}



