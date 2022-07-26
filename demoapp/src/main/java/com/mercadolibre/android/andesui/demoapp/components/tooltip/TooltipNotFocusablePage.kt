package com.mercadolibre.android.andesui.demoapp.components.tooltip

import android.content.Context
import android.view.View
import android.widget.Toast
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticTooltipNotFocusableBinding
import com.mercadolibre.android.andesui.tooltip.AndesTooltip
import com.mercadolibre.android.andesui.tooltip.actions.AndesTooltipAction
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocation
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipStyle

class TooltipNotFocusablePage {

    private lateinit var binding: AndesuiStaticTooltipNotFocusableBinding
    private lateinit var salesTooltip: AndesTooltip

    fun create(container: View) {
        binding = AndesuiStaticTooltipNotFocusableBinding.bind(container)
        setupValues()
        setupTooltip(container.context)
    }

    private fun setupValues() {
        binding.creditScoreLayout.contentDescription = "${binding.creditScoreLabel.text} ${binding.creditScoreValue.text}"
        binding.avgScoreLayout.contentDescription = "${binding.avgScoreLabel.text} ${binding.avgScoreValue.text}"
        binding.salesScoreLayout.contentDescription = "${binding.salesScoreLabel.text} ${binding.salesScoreValue.text}"
    }

    fun showTooltip() {
        salesTooltip.show(binding.salesScoreLayout)
    }

    fun dismissTooltip() {
        salesTooltip.dismiss()
    }

    private fun setupTooltip(context: Context) {
        salesTooltip = AndesTooltip(
            context = context,
            style = AndesTooltipStyle.LIGHT,
            body = "This value comes from comparing the last year sales and the expected value for the market",
            isDismissible = true,
            tooltipLocation = AndesTooltipLocation.BOTTOM,
            mainAction = AndesTooltipAction(
                "More info",
                AndesButtonHierarchy.LOUD
            ) { _, tooltip ->
                Toast.makeText(context.applicationContext, "Click en More Info", Toast.LENGTH_SHORT).show()
                tooltip.dismiss()
            },
            secondaryAction = AndesTooltipAction(
                "Got it!",
                AndesButtonHierarchy.QUIET
            ) { _, tooltip ->
                tooltip.dismiss()
            },
            shouldGainA11yFocus = false
        )
    }
}
