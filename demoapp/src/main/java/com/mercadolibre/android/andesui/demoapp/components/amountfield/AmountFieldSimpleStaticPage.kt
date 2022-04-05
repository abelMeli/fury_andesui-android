package com.mercadolibre.android.andesui.demoapp.components.amountfield

import android.view.View
import com.mercadolibre.android.andesui.amountfield.listener.OnTextChangeListener
import com.mercadolibre.android.andesui.amountfield.state.AndesAmountFieldState
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticAmountfieldBinding

class AmountFieldSimpleStaticPage {

    private lateinit var binding: AndesuiStaticAmountfieldBinding

    fun create(containerView: View) {
        setupBinding(containerView)
        setupAmountButtons()
        setupAmountField()
        setupContinueButton()
    }

    private fun setupAmountButtons() {
        binding.staticAmountFieldButtonFifty.apply {
            text = "$ 50"
            setOnClickListener {
                binding.staticAmountField.value = "50.00"
            }
        }
        binding.staticAmountFieldButtonHundred.apply {
            text = "$ 100"
            setOnClickListener {
                binding.staticAmountField.value = "100.00"
            }
        }
        binding.staticAmountFieldButtonFiveHundred.apply {
            text = "$ 500"
            setOnClickListener {
                binding.staticAmountField.value = "500.00"
            }
        }
        binding.staticAmountFieldButtonThousand.apply {
            text = "$ 1000"
            setOnClickListener {
                binding.staticAmountField.value = "1000.00"
            }
        }
    }

    private fun setupAmountField() {
        binding.staticAmountField.apply {
            helperText = "Dispones de un máximo de $ 1500"
            onTextChangedListener = object : OnTextChangeListener {
                override fun onTextChanged(newText: String?) {
                    binding.staticAmountFieldButtonContinue.isEnabled =
                        binding.staticAmountField.state == AndesAmountFieldState.Idle
                }
            }
        }
    }

    private fun setupContinueButton() {
        binding.staticAmountFieldButtonContinue.apply {
            text = "Continuar"
            //  manejar estado
            setOnClickListener {
                // ver si agregamos un feedback screen o algo asi
            }
        }
    }

    private fun setupBinding(containerView: View) {
        binding = AndesuiStaticAmountfieldBinding.bind(containerView)
    }
}