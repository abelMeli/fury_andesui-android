package com.mercadolibre.android.andesui.demoapp.components.tooltip

import android.content.Context
import android.view.View
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticTooltipFocusableBinding
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.andesui.tooltip.AndesTooltip
import com.mercadolibre.android.andesui.tooltip.actions.AndesTooltipAction
import com.mercadolibre.android.andesui.tooltip.actions.AndesTooltipLinkAction
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipStyle
import kotlin.random.Random

class TooltipFocusablePage {

    private lateinit var binding: AndesuiStaticTooltipFocusableBinding
    private lateinit var dniTooltip: AndesTooltip
    private lateinit var creditTooltip: AndesTooltip
    private lateinit var avgTooltip: AndesTooltip

    fun create(container: View) {
        binding = AndesuiStaticTooltipFocusableBinding.bind(container)
        setupValues()
        setupTextfield()
        setupTooltips(container.context)
        setupTooltipTriggers()
    }

    private fun setupValues() {
        binding.creditScoreLayout.contentDescription = "${binding.creditScoreLabel.text} ${binding.creditScoreValue.text}"
        binding.avgScoreLayout.contentDescription = "${binding.avgScoreLabel.text} ${binding.avgScoreValue.text}"
    }

    private fun setupTooltipTriggers() {
        binding.dniTooltipImage.setOnClickListener {
            dniTooltip.show(it)
        }
        binding.creditTooltipImage.setOnClickListener {
            creditTooltip.show(it)
        }
        binding.avgTooltipImage.setOnClickListener {
            avgTooltip.show(it)
        }
    }

    private fun setupTextfield() {
        binding.dniTextfield.apply {
            label = "Dni"
            placeholder = "Enter your DNI number"
            setAction(text = "Check") {
                validate(this) {
                    generateScores()
                    setupValues()
                }
            }
        }
    }

    private fun validate(textfield: AndesTextfield, action: () -> Unit) {
        if (textfield.text.isNullOrBlank()) {
            textfield.state = AndesTextfieldState.ERROR
            textfield.helper = "Complete field"
            return
        } else {
            textfield.state = AndesTextfieldState.IDLE
            textfield.helper = null
            action()
        }
    }

    private fun generateScores() {
        generateCreditScore()
        generateAvgScore()
    }

    private fun generateAvgScore() {
        val avgScore = generateRandomNumber()
        val textColor = when {
            avgScore < 700 -> AndesTextViewColor.Negative
            avgScore < 900 -> AndesTextViewColor.Caution
            else -> AndesTextViewColor.Positive
        }
        binding.avgScoreValue.apply {
            text = avgScore.toString()
            setTextColor(textColor)
        }
    }

    private fun generateCreditScore() {
        val creditScore = generateRandomNumber()
        val textColor = when {
            creditScore < 700 -> AndesTextViewColor.Negative
            creditScore < 900 -> AndesTextViewColor.Caution
            else -> AndesTextViewColor.Positive
        }
        binding.creditScoreValue.apply {
            text = creditScore.toString()
            setTextColor(textColor)
        }
    }

    private fun setupTooltips(context: Context) {
        dniTooltip = AndesTooltip(
            context = context,
            style = AndesTooltipStyle.LIGHT,
            title = "Why do we need this data?",
            body = "We use the DNI number to search in our database",
            isDismissible = false,
            mainAction = AndesTooltipAction(
                "Got it!",
                AndesButtonHierarchy.LOUD
            ) { _, tooltip -> tooltip.dismiss() },
            shouldGainA11yFocus = true
        )
        creditTooltip = AndesTooltip(
            context = context,
            style = AndesTooltipStyle.LIGHT,
            title = "How do we calculate the credit?",
            body = "We combine the last year sales data with the sales index from the country",
            isDismissible = true,
            linkAction = AndesTooltipLinkAction(
                "More info"
            ) { _, tooltip -> tooltip.dismiss() }
        ).also {
            it.shouldGainA11yFocus = true
        }
        avgTooltip = AndesTooltip(
            context = context,
            style = AndesTooltipStyle.LIGHT,
            title = "How do we calculate the average?",
            body = "We take an average value from your credit score from your last five years",
            isDismissible = true,
            mainAction = AndesTooltipAction(
                "More info",
                AndesButtonHierarchy.LOUD
            ) { _, tooltip -> tooltip.dismiss() },
            secondaryAction = AndesTooltipAction(
                "Got it!",
                AndesButtonHierarchy.QUIET
            ) { _, tooltip -> tooltip.dismiss() },
            shouldGainA11yFocus = true
        )
    }

    private fun generateRandomNumber() = Random.nextInt(
        RANDOM_FROM,
        RANDOM_UNTIL
    )

    private companion object {
        const val RANDOM_FROM = 500
        const val RANDOM_UNTIL = 1200
    }
}
