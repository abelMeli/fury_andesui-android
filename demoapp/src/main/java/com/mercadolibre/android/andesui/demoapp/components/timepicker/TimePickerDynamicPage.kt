package com.mercadolibre.android.andesui.demoapp.components.timepicker

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicTimepickerBinding
import com.mercadolibre.android.andesui.demoapp.utils.Constants.TIMEPICKER_DEFAULT_HELPER
import com.mercadolibre.android.andesui.demoapp.utils.Constants.TIMEPICKER_DEFAULT_LABEL
import com.mercadolibre.android.andesui.demoapp.utils.Constants.TIMEPICKER_DIGITS
import com.mercadolibre.android.andesui.demoapp.utils.Constants.TIMEPICKER_MASK
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import com.mercadolibre.android.andesui.timepicker.AndesTimePicker
import com.mercadolibre.android.andesui.timepicker.minutesInterval.AndesTimePickerInterval
import com.mercadolibre.android.andesui.timepicker.state.AndesTimePickerState

@Suppress("TooManyFunctions")
class TimePickerDynamicPage {

    private lateinit var andesTimePicker: AndesTimePicker
    private lateinit var textFieldLabel: AndesTextfield
    private lateinit var textFieldHelper: AndesTextfield
    private lateinit var textFieldCurrentTime: AndesTextfield
    private lateinit var stateSpinner: Spinner
    private lateinit var intervalSpinner: Spinner
    private lateinit var clearButton: AndesButton
    private lateinit var updateButton: AndesButton

    fun create(context: Context, container: View) {
        initComponents(container)
        setupTextfieldCurrentTime()
        setupSpinners(context)
        setupButtons(context)
        setupListeners(context)
    }

    private fun initComponents(view: View) {
        val binding = AndesuiDynamicTimepickerBinding.bind(view)
        andesTimePicker = binding.dynamicTimepicker
        textFieldLabel = binding.dynamicTimepickerTfLabel
        textFieldHelper = binding.dynamicTimepickerTfHelper
        textFieldCurrentTime = binding.dynamicTimepickerTfCurrentTime
        stateSpinner = binding.dynamicTimepickerSpinnerState
        intervalSpinner = binding.dynamicTimepickerSpinnerInterval
        clearButton = binding.dynamicTimepickerButtonClear
        updateButton = binding.dynamicTimepickerButtonUpdate
    }

    private fun setupTextfieldCurrentTime() {
        textFieldCurrentTime.setTextFieldMask(TIMEPICKER_MASK, TIMEPICKER_DIGITS, null)
    }

    private fun setupSpinners(context: Context) {
        setupStateSpinner(context)
        setupIntervalSpinner(context)
    }

    private fun setupStateSpinner(context: Context) {
        ArrayAdapter.createFromResource(
            context,
            R.array.andes_timepicker_spinner_state,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            stateSpinner.adapter = adapter
        }
    }

    private fun setupIntervalSpinner(context: Context) {
        ArrayAdapter.createFromResource(
            context,
            R.array.andes_timepicker_spinner_interval,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            intervalSpinner.adapter = adapter
        }
    }

    private fun setupButtons(context: Context) {
        setupUpdateButton(context)
        setupClearButton()
    }

    private fun setupClearButton() {
        clearButton.setOnClickListener {
            resetSetterWidgets()
            andesTimePicker.apply {
                label = TIMEPICKER_DEFAULT_LABEL
                helper = TIMEPICKER_DEFAULT_HELPER
                state = AndesTimePickerState.IDLE
                minutesInterval = AndesTimePickerInterval.MINUTES_30
                currentTime = null
            }
        }
    }

    private fun setupUpdateButton(context: Context) {
        updateButton.setOnClickListener {
            val newLabel = textFieldLabel.text.orEmpty()
            val newHelper = textFieldHelper.text.orEmpty()
            val newInterval = resolveInterval(intervalSpinner.selectedItem.toString())
            val newState = stateSpinner.selectedItem.toString()
            val newCurrentTime = textFieldCurrentTime.text.orEmpty()

            try {
                andesTimePicker.apply {
                    label = newLabel
                    helper = newHelper
                    minutesInterval = newInterval
                    state = AndesTimePickerState.fromString(newState)
                    currentTime = newCurrentTime
                }
                textFieldCurrentTime.apply {
                    state = AndesTextfieldState.IDLE
                    helper = ""
                }
            } catch (exception: IllegalArgumentException) {
                exception.printStackTrace()
                textFieldCurrentTime.apply {
                    state = AndesTextfieldState.ERROR
                    helper = context.resources.getString(R.string.andes_timepicker_tf_current_time_error_text)
                }
            }
        }
    }

    private fun setupListeners(context: Context) {
        andesTimePicker.setupCallback(object : AndesTimePicker.OnTimeSelectedListener {
            override fun onTimeSelected(currentTime: String) {
                Toast.makeText(context.applicationContext, "Time selected: $currentTime", Toast.LENGTH_SHORT).show()
            }

            override fun onTimePeriodSelected() {
                // not used
            }
        })
    }

    private fun resolveInterval(newInterval: String): AndesTimePickerInterval {
        return when (newInterval) {
            "30 minutes" -> AndesTimePickerInterval.MINUTES_30
            "60 minutes" -> AndesTimePickerInterval.MINUTES_60
            else -> AndesTimePickerInterval.MINUTES_60
        }
    }

    private fun resetSetterWidgets() {
        textFieldLabel.text = ""
        textFieldHelper.text = ""
        textFieldCurrentTime.text = null
        intervalSpinner.setSelection(0)
        stateSpinner.setSelection(0)
    }
}
