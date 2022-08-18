package com.mercadolibre.android.andesui.demoapp.components.tooltip

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicTooltipPositionBinding
import com.mercadolibre.android.andesui.dropdown.AndesDropDownForm
import com.mercadolibre.android.andesui.dropdown.AndesDropDownItem
import com.mercadolibre.android.andesui.dropdown.utils.AndesDropdownDelegate
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.tooltip.AndesTooltip
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocation
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipSize
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipStyle

/**
 * Dynamic page for testing tooltip positions
 */
class TooltipDynamicPositionPage {

    /**
     * Method in charge of creating the page and add its behavior.
     */
    fun create(container: View) {
        val binding = AndesuiDynamicTooltipPositionBinding.bind(container)
        setupComponents(binding)
    }

    private fun setupComponents(binding: AndesuiDynamicTooltipPositionBinding) {
        setTriggerConstraints(binding)

        setTextWatcherToTextfield(binding.andesTooltipDynamicX) { setTriggerConstraints(binding) }
        setTextWatcherToTextfield(binding.andesTooltipDynamicY) { setTriggerConstraints(binding) }

        val tooltip = setupTooltip(binding.root.context)

        setTooltipLocationDropdown(binding.andesTooltipDynamicDropdown) { option ->
            tooltip.location = when (option) {
                "Bottom" -> AndesTooltipLocation.BOTTOM
                "Top" -> AndesTooltipLocation.TOP
                "Right" -> AndesTooltipLocation.RIGHT
                "Left" -> AndesTooltipLocation.LEFT
                else -> AndesTooltipLocation.BOTTOM
            }
        }

        binding.andesTooltipDynamicTrigger.setOnClickListener { tooltip.show(it) }
    }

    private fun setupTooltip(context: Context) = AndesTooltip(
        context = context,
        isDismissible = false,
        style = AndesTooltipStyle.HIGHLIGHT,
        title = "Tooltip Test",
        body = "Tooltip\nlocation\nand\narrow\ntest",
        tooltipLocation = AndesTooltipLocation.BOTTOM,
        andesTooltipSize = AndesTooltipSize.DYNAMIC
    )

    private fun setTooltipLocationDropdown(
        dropdownForm: AndesDropDownForm,
        onItemSelected: (String) -> Unit
    ) {
        dropdownForm.context.resources.getStringArray(R.array.andes_tooltip_location_spinner).map {
            AndesDropDownItem().apply { title = it }
        }.also { list ->
            with(dropdownForm) {
                label = "Tooltip Location"
                placeholder = "Desired position"
                setItems(list, 0)
                delegate = object : AndesDropdownDelegate {
                    override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
                        onItemSelected.invoke(list[position].title)
                    }
                }
            }
        }
    }

    private fun setTextWatcherToTextfield(textfield: AndesTextfield, onTextChanged: () -> Unit) {
        textfield.textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                onTextChanged.invoke()
            }

            override fun afterTextChanged(p0: Editable?) = Unit
        }
    }

    private fun setTriggerConstraints(binding: AndesuiDynamicTooltipPositionBinding) {
        ConstraintSet().apply {
            clone(binding.andesTooltipContainer)
            setHorizontalBias(
                binding.andesTooltipDynamicTrigger.id,
                binding.andesTooltipDynamicX.text.toFloatOrDefault().div(HUNDRED_PERCENTAGE)
            )
            setVerticalBias(
                binding.andesTooltipDynamicTrigger.id,
                binding.andesTooltipDynamicY.text.toFloatOrDefault().div(HUNDRED_PERCENTAGE)
            )
            applyTo(binding.andesTooltipContainer)
        }
    }

    private fun String?.toFloatOrDefault(default: Float = 0f): Float {
        return this?.toFloatOrNull() ?: default
    }

    companion object {
        private const val HUNDRED_PERCENTAGE: Float = 100f
    }
}
