package com.mercadolibre.android.andesui.demoapp.components.inputstepper

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicInputstepperBinding
import com.mercadolibre.android.andesui.inputstepper.AndesInputStepperEvent
import com.mercadolibre.android.andesui.inputstepper.AndesInputStepperTextDataSource
import com.mercadolibre.android.andesui.inputstepper.AndesInputStepperValueListener
import com.mercadolibre.android.andesui.inputstepper.enabledstate.AndesInputStepperEnabledState
import com.mercadolibre.android.andesui.inputstepper.size.AndesInputStepperSize
import com.mercadolibre.android.andesui.inputstepper.status.AndesInputStepperStatus

class InputStepperSimpleDynamicPage {

    private lateinit var binding: AndesuiDynamicInputstepperBinding

    fun create(containerView: View) {
        setupBinding(containerView)
    }

    private fun setupBinding(containerView: View) {
        binding = AndesuiDynamicInputstepperBinding.bind(containerView)

        binding.run {
            inputStepperStep.text = baseInputStepper.step.toString()
            inputStepperValue.text = baseInputStepper.value.toString()
            inputStepperMin.text = baseInputStepper.minValue.toString()
            inputStepperMax.text = baseInputStepper.maxValue.toString()

            inputStepperStatusGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.input_stepper_status_loading -> baseInputStepper.status = AndesInputStepperStatus.Loading
                    R.id.input_stepper_status_disabled -> baseInputStepper.status = AndesInputStepperStatus.Disabled
                    R.id.input_stepper_status_enabled -> baseInputStepper.status = AndesInputStepperStatus.Enabled
                }
            }

            inputStepperSizeGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.input_stepper_size_large -> baseInputStepper.size = AndesInputStepperSize.Large
                    R.id.input_stepper_size_small -> baseInputStepper.size = AndesInputStepperSize.Small
                }
            }

            baseInputStepper.valueListener = object : AndesInputStepperValueListener {
                override fun onValueSelected(
                    sender: AndesInputStepperEvent?,
                    value: Int,
                    state: AndesInputStepperEnabledState,
                ) {
                    val msg = "sender: ${sender?.name} value: ${value} state: ${state.javaClass.simpleName}"
                    Toast.makeText(containerView.context, msg, Toast.LENGTH_SHORT).show()
                    inputStepperValue.text = value.toString()
                }

            }

            inputStepperStep.textWatcher = ValueTextHelper { baseInputStepper.step = it }
            inputStepperValue.textWatcher = ValueTextHelper {
                if (it != baseInputStepper.value) {
                    baseInputStepper.setValue(it)
                }
            }
            inputStepperMin.textWatcher = ValueTextHelper { baseInputStepper.minValue = it }
            inputStepperMax.textWatcher = ValueTextHelper { baseInputStepper.maxValue = it }

            inputStepperMask.setAction("Aplicar") {
                baseInputStepper.dataSource = object : AndesInputStepperTextDataSource {
                    override fun getText(value: Int): CharSequence {
                        return inputStepperMask.text + " " + value
                    }
                }
                baseInputStepper.setValue(baseInputStepper.value)
            }
        }
    }

    class ValueTextHelper(val func: (value: Int) -> Unit) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                if (it.isDigitsOnly() && it.isNotEmpty()) {
                    func(Integer.parseInt(it.toString()))
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {
        }

    }

}