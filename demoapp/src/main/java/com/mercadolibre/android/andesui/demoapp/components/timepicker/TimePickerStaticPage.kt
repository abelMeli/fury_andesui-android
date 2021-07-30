package com.mercadolibre.android.andesui.demoapp.components.timepicker

import android.content.Context
import android.view.View
import android.widget.Toast
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.timepicker.AndesTimePicker

class TimePickerStaticPage {

    private lateinit var timePickerIdle: AndesTimePicker
    private lateinit var timePickerError: AndesTimePicker
    private lateinit var timePickerDisabled: AndesTimePicker
    private lateinit var timePickerReadonly: AndesTimePicker

    fun create(context: Context, container: View) {
        initComponents(container)
        setupListeners(context)
    }

    private fun initComponents(container: View) {
        timePickerIdle = container.findViewById(R.id.static_timepicker_idle)
        timePickerError = container.findViewById(R.id.static_timepicker_error)
        timePickerDisabled = container.findViewById(R.id.static_timepicker_disabled)
        timePickerReadonly = container.findViewById(R.id.static_timepicker_readonly)
    }

    private fun setupListeners(context: Context) {
        timePickerIdle.setupCallback(object : AndesTimePicker.OnTimeSelectedListener {
            override fun onTimeSelected(currentTime: String) {
                Toast.makeText(context, "Time selected: $currentTime", Toast.LENGTH_SHORT).show()
            }

            override fun onTimePeriodSelected() {
                // not used
            }
        })

        timePickerError.setupCallback(object : AndesTimePicker.OnTimeSelectedListener {
            override fun onTimeSelected(currentTime: String) {
                Toast.makeText(context, "Time selected: $currentTime", Toast.LENGTH_SHORT).show()
            }

            override fun onTimePeriodSelected() {
                // not used
            }
        })
    }
}
