package com.mercadolibre.android.andesui.demoapp.components.timepicker

import android.content.Context
import android.view.View
import android.widget.Toast
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticTimepickerBinding
import com.mercadolibre.android.andesui.timepicker.AndesTimePicker

class TimePickerStaticPage {

    fun create(context: Context, container: View) {
        setupListeners(context, container)
    }

    private fun setupListeners(context: Context, view: View) {
        val binding = AndesuiStaticTimepickerBinding.bind(view)
        binding.staticTimepickerIdle.setupCallback(object : AndesTimePicker.OnTimeSelectedListener {
            override fun onTimeSelected(currentTime: String) {
                Toast.makeText(context.applicationContext, "Time selected: $currentTime", Toast.LENGTH_SHORT).show()
            }

            override fun onTimePeriodSelected() {
                // not used
            }
        })

        binding.staticTimepickerError.setupCallback(object : AndesTimePicker.OnTimeSelectedListener {
            override fun onTimeSelected(currentTime: String) {
                Toast.makeText(context.applicationContext, "Time selected: $currentTime", Toast.LENGTH_SHORT).show()
            }

            override fun onTimePeriodSelected() {
                // not used
            }
        })
    }
}
