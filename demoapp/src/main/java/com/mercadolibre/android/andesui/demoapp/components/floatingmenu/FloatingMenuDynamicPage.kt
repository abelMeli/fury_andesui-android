package com.mercadolibre.android.andesui.demoapp.components.floatingmenu

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.ArrayRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicFloatingmenuBinding
import com.mercadolibre.android.andesui.demoapp.utils.getInDp
import com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu
import com.mercadolibre.android.andesui.floatingmenu.orientation.AndesFloatingMenuOrientation
import com.mercadolibre.android.andesui.floatingmenu.rows.AndesFloatingMenuRows
import com.mercadolibre.android.andesui.floatingmenu.width.AndesFloatingMenuWidth
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.AndesListViewItem
import com.mercadolibre.android.andesui.list.AndesListViewItemSimple
import com.mercadolibre.android.andesui.list.size.AndesListViewItemSize
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.textfield.AndesTextfield

@Suppress("TooManyFunctions")
class FloatingMenuDynamicPage {

    private lateinit var floatingMenu: AndesFloatingMenu
    private lateinit var andesList: AndesList

    fun create(context: Context, container: View) {
        val binding = AndesuiDynamicFloatingmenuBinding.bind(container)
        setupConfigComponents(context, binding)
        setupAndesList(context, binding)
        setupFloatingMenu(context, binding)
        setupActionButtons(context, binding)
    }

    private fun setupAndesList(context: Context, binding: AndesuiDynamicFloatingmenuBinding) {
        andesList = AndesList(context).apply {
            delegate = object : AndesListDelegate {
                var selected = UNSELECTED_POSITION
                override fun onItemClick(andesList: AndesList, position: Int) {
                    selected = position
                    floatingMenu.dismiss()
                }

                override fun bind(
                    andesList: AndesList,
                    view: View,
                    position: Int
                ): AndesListViewItem {
                    return AndesListViewItemSimple(
                        context = context,
                        title = "Option ${position + 1}",
                        size = AndesListViewItemSize.SMALL,
                        itemSelected = selected == position && binding.floatingMenuSelectable.status == AndesCheckboxStatus.SELECTED
                    )
                }

                override fun getDataSetSize(andesList: AndesList): Int =
                    binding.floatingMenuListSizeEditText.text?.toIntOrNull() ?: DEFAULT_LIST_SIZE
            }
        }
    }

    private fun setupActionButtons(context: Context, binding: AndesuiDynamicFloatingmenuBinding) {
        binding.changeButton.setOnClickListener {
            with(floatingMenu) {
                rows = retrieveSelectedRows(binding.floatingMenuRowsSpinner)
                orientation = retrieveSelectedOrientation(binding.floatingMenuOrientationSpinner)
                width = retrieveSelectedWidth(binding)
                setOnDismissListener(retrieveDismissListener(context, binding.floatingMenuCallback))
                setOnShowListener(retrieveShowListener(context, binding.floatingMenuCallback))
            }
            updateTriggerPosition(context, binding)
        }
        binding.clearButton.setOnClickListener {
            resetSpinners(binding)
            resetSelectableCheck(binding.floatingMenuSelectable)
            resetCallbackCheck(binding.floatingMenuCallback)
            updateTriggerPosition(context, binding)
            resetAndesList(context, binding)
            resetComponent(context, binding)
        }
    }

    private fun retrieveDismissListener(context: Context, checkbox: AndesCheckbox): AndesFloatingMenu.OnDismissListener? {
        return when (checkbox.status) {
            AndesCheckboxStatus.SELECTED -> object : AndesFloatingMenu.OnDismissListener {
                override fun onDismiss() {
                    Toast.makeText(context.applicationContext, "Dismissed", LENGTH_SHORT).show()
                }
            }
            else -> null
        }
    }

    private fun retrieveShowListener(context: Context, checkbox: AndesCheckbox): AndesFloatingMenu.OnShowListener? {
        return when (checkbox.status) {
            AndesCheckboxStatus.SELECTED -> object : AndesFloatingMenu.OnShowListener {
                override fun onShow() {
                    Toast.makeText(context.applicationContext, "Showed", LENGTH_SHORT).show()
                }
            }
            else -> null
        }
    }

    private fun resetComponent(context: Context, binding: AndesuiDynamicFloatingmenuBinding) {
        with(floatingMenu) {
            orientation = AndesFloatingMenuOrientation.Left
            rows = AndesFloatingMenuRows.Medium
            width = AndesFloatingMenuWidth.Fixed
            setOnDismissListener(retrieveDismissListener(context, binding.floatingMenuCallback))
            setOnShowListener(retrieveShowListener(context, binding.floatingMenuCallback))
        }
    }

    private fun resetSpinners(binding: AndesuiDynamicFloatingmenuBinding) = with(binding) {
        floatingMenuRowsSpinner.setSelection(1)
        floatingMenuOrientationSpinner.setSelection(0)
        floatingMenuWidthSpinner.setSelection(0)
        floatingMenuTriggerPositionSpinner.setSelection(0)
    }

    private fun resetSelectableCheck(checkbox: AndesCheckbox) {
        checkbox.status = AndesCheckboxStatus.UNSELECTED
    }

    private fun resetCallbackCheck(checkbox: AndesCheckbox) {
        checkbox.status = AndesCheckboxStatus.UNSELECTED
    }

    private fun updateTriggerPosition(
        context: Context,
        binding: AndesuiDynamicFloatingmenuBinding
    ) = with(binding) {
        val params = andesFloatingmenuTrigger.layoutParams as ConstraintLayout.LayoutParams

        when (floatingMenuTriggerPositionSpinner.getItemAtPosition(floatingMenuTriggerPositionSpinner.selectedItemPosition)) {
            "Left" -> setConstraints(
                params = params,
                mStartToStart = floatingMenuConstraintLayout.id,
                mMarginStart = context.getInDp(SIDE_MARGIN)
            )
            "Right" -> setConstraints(
                params = params,
                mEndToEnd = floatingMenuConstraintLayout.id,
                mMarginEnd = context.getInDp(SIDE_MARGIN)
            )
            "Center" -> setConstraints(
                params = params,
                mStartToStart = floatingMenuConstraintLayout.id,
                mEndToEnd = floatingMenuConstraintLayout.id
            )
        }
        binding.andesFloatingmenuTrigger.layoutParams = params
        binding.andesFloatingmenuTrigger.requestLayout()
    }

    private fun setConstraints(
        params: ConstraintLayout.LayoutParams,
        mStartToStart: Int = ConstraintLayout.LayoutParams.UNSET,
        mEndToEnd: Int = ConstraintLayout.LayoutParams.UNSET,
        mMarginStart: Int = DEFAULT_MARGIN,
        mMarginEnd: Int = DEFAULT_MARGIN
    ) = params.apply {
        startToStart = mStartToStart
        endToEnd = mEndToEnd
        marginStart = mMarginStart
        marginEnd = mMarginEnd
    }

    private fun resetAndesList(context: Context, binding: AndesuiDynamicFloatingmenuBinding) {
        binding.floatingMenuListSizeEditText.text = "$DEFAULT_LIST_SIZE"
        setupAndesList(context, binding)
    }

    private fun retrieveSelectedWidth(
        binding: AndesuiDynamicFloatingmenuBinding
    ): AndesFloatingMenuWidth = with(binding) {
        when (floatingMenuWidthSpinner.getItemAtPosition(floatingMenuWidthSpinner.selectedItemPosition)) {
            "Fixed" -> AndesFloatingMenuWidth.Fixed
            "Custom" -> AndesFloatingMenuWidth.Custom(
                floatingMenuWidthEditText.text?.toIntOrNull() ?: DEFAULT_WIDTH
            )
            else -> AndesFloatingMenuWidth.Fixed
        }
    }

    private fun retrieveSelectedRows(spinner: Spinner): AndesFloatingMenuRows {
        return when (spinner.getItemAtPosition(spinner.selectedItemPosition)) {
            "Small" -> AndesFloatingMenuRows.Small
            "Medium" -> AndesFloatingMenuRows.Medium
            "Max" -> AndesFloatingMenuRows.Max
            else -> AndesFloatingMenuRows.Medium
        }
    }

    private fun retrieveSelectedOrientation(spinner: Spinner): AndesFloatingMenuOrientation {
        return when (spinner.getItemAtPosition(spinner.selectedItemPosition)) {
            "Left" -> AndesFloatingMenuOrientation.Left
            "Right" -> AndesFloatingMenuOrientation.Right
            else -> AndesFloatingMenuOrientation.Left
        }
    }

    private fun setupConfigComponents(
        context: Context,
        binding: AndesuiDynamicFloatingmenuBinding
    ) = with(binding) {
        setupSpinnerComponent(
            context,
            floatingMenuRowsSpinner,
            R.array.andes_floatingmenu_rows_spinner
        ).also {
            floatingMenuRowsSpinner.setSelection(1)
        }
        setupSpinnerComponent(
            context,
            floatingMenuOrientationSpinner,
            R.array.andes_floatingmenu_orientation_spinner
        )
        setupSpinnerComponent(
            context,
            floatingMenuWidthSpinner,
            R.array.andes_floatingmenu_width_spinner
        )
        setupSpinnerComponent(
            context,
            floatingMenuTriggerPositionSpinner,
            R.array.andes_floatingmenu_trigger_position_spinner
        )
        setupWidthCustomComponent(this)
        setupListSizeComponent(floatingMenuListSizeEditText)
    }

    private fun setupListSizeComponent(textfield: AndesTextfield) {
        textfield.text = DEFAULT_LIST_SIZE.toString()
    }

    private fun setupWidthCustomComponent(binding: AndesuiDynamicFloatingmenuBinding) = with(binding) {
        floatingMenuWidthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (floatingMenuWidthSpinner.getItemAtPosition(position)) {
                    "Custom" -> floatingMenuWidthEditText.visibility = View.VISIBLE
                    else -> floatingMenuWidthEditText.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // no-op
            }
        }
        floatingMenuWidthEditText.text = DEFAULT_WIDTH.toString()
    }

    private fun setupFloatingMenu(context: Context, binding: AndesuiDynamicFloatingmenuBinding) {
        floatingMenu = AndesFloatingMenu(context, andesList)
        binding.andesFloatingmenuTrigger.setOnClickListener {
            floatingMenu.show(it)
        }
    }

    private fun setupSpinnerComponent(context: Context, spinner: Spinner, @ArrayRes content: Int) {
        ArrayAdapter.createFromResource(
            context,
            content,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    companion object {
        private const val DEFAULT_WIDTH = 400
        private const val UNSELECTED_POSITION = -1
        private const val DEFAULT_LIST_SIZE = 10
        private const val SIDE_MARGIN = 16f
        private const val DEFAULT_MARGIN = 0
    }
}
