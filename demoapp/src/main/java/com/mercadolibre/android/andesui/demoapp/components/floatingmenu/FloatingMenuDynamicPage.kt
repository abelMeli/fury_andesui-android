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
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
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
    private lateinit var rowsSpinner: Spinner
    private lateinit var orientationSpinner: Spinner
    private lateinit var widthSpinner: Spinner
    private lateinit var triggerPositionSpinner: Spinner
    private lateinit var updateButton: AndesButton
    private lateinit var triggerButton: AndesButton
    private lateinit var clearButton: AndesButton
    private lateinit var andesList: AndesList
    private lateinit var selectableCheckbox: AndesCheckbox
    private lateinit var callbackCheckbox: AndesCheckbox
    private lateinit var constraintContainer: ConstraintLayout
    private lateinit var listSizeTextField: AndesTextfield
    private lateinit var widthCustomPixels: AndesTextfield

    fun create(context: Context, container: View) {
        initComponents(container)

        setupConfigComponents(context)
        setupAndesList(context)
        setupFloatingMenu(context)
        setupActionButtons(context)
    }

    private fun setupAndesList(context: Context) {
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
                        itemSelected = selected == position && selectableCheckbox.status == AndesCheckboxStatus.SELECTED
                    )
                }

                override fun getDataSetSize(andesList: AndesList): Int =
                    listSizeTextField.text?.toIntOrNull() ?: DEFAULT_LIST_SIZE
            }
        }
    }

    private fun setupActionButtons(context: Context) {
        updateButton.setOnClickListener {
            with(floatingMenu) {
                rows = retrieveSelectedRows()
                orientation = retrieveSelectedOrientation()
                width = retrieveSelectedWidth()
                setOnDismissListener(retrieveDismissListener(context))
                setOnShowListener(retrieveShowListener(context))
            }
            updateTriggerPosition(context)
        }
        clearButton.setOnClickListener {
            resetSpinners()
            resetSelectableCheck()
            resetCallbackCheck()
            updateTriggerPosition(context)
            resetAndesList(context)
            resetComponent(context)
        }
    }

    private fun retrieveDismissListener(context: Context): AndesFloatingMenu.OnDismissListener? {
        return when (callbackCheckbox.status) {
            AndesCheckboxStatus.SELECTED -> object : AndesFloatingMenu.OnDismissListener {
                override fun onDismiss() {
                    Toast.makeText(context.applicationContext, "Dismissed", LENGTH_SHORT).show()
                }
            }
            else -> null
        }
    }

    private fun retrieveShowListener(context: Context): AndesFloatingMenu.OnShowListener? {
        return when (callbackCheckbox.status) {
            AndesCheckboxStatus.SELECTED -> object : AndesFloatingMenu.OnShowListener {
                override fun onShow() {
                    Toast.makeText(context.applicationContext, "Showed", LENGTH_SHORT).show()
                }
            }
            else -> null
        }
    }

    private fun resetComponent(context: Context) {
        with(floatingMenu) {
            orientation = AndesFloatingMenuOrientation.Left
            rows = AndesFloatingMenuRows.Medium
            width = AndesFloatingMenuWidth.Fixed
            setOnDismissListener(retrieveDismissListener(context))
            setOnShowListener(retrieveShowListener(context))
        }
    }

    private fun resetSpinners() {
        rowsSpinner.setSelection(1)
        orientationSpinner.setSelection(0)
        widthSpinner.setSelection(0)
        triggerPositionSpinner.setSelection(0)
    }

    private fun resetSelectableCheck() {
        selectableCheckbox.status = AndesCheckboxStatus.UNSELECTED
    }

    private fun resetCallbackCheck() {
        callbackCheckbox.status = AndesCheckboxStatus.UNSELECTED
    }

    private fun updateTriggerPosition(context: Context) {
        val params = triggerButton.layoutParams as ConstraintLayout.LayoutParams

        when (triggerPositionSpinner.getItemAtPosition(triggerPositionSpinner.selectedItemPosition)) {
            "Left" -> setConstraints(
                params = params,
                mStartToStart = constraintContainer.id,
                mMarginStart = context.getInDp(SIDE_MARGIN)
            )
            "Right" -> setConstraints(
                params = params,
                mEndToEnd = constraintContainer.id,
                mMarginEnd = context.getInDp(SIDE_MARGIN)
            )
            "Center" -> setConstraints(
                params = params,
                mStartToStart = constraintContainer.id,
                mEndToEnd = constraintContainer.id
            )
        }
        triggerButton.layoutParams = params
        triggerButton.requestLayout()
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

    private fun resetAndesList(context: Context) {
        listSizeTextField.text = "$DEFAULT_LIST_SIZE"
        setupAndesList(context)
    }

    private fun retrieveSelectedWidth(): AndesFloatingMenuWidth {
        return when (widthSpinner.getItemAtPosition(widthSpinner.selectedItemPosition)) {
            "Fixed" -> AndesFloatingMenuWidth.Fixed
            "Custom" -> AndesFloatingMenuWidth.Custom(
                widthCustomPixels.text?.toIntOrNull() ?: DEFAULT_WIDTH
            )
            else -> AndesFloatingMenuWidth.Fixed
        }
    }

    private fun retrieveSelectedRows(): AndesFloatingMenuRows {
        return when (rowsSpinner.getItemAtPosition(rowsSpinner.selectedItemPosition)) {
            "Small" -> AndesFloatingMenuRows.Small
            "Medium" -> AndesFloatingMenuRows.Medium
            "Max" -> AndesFloatingMenuRows.Max
            else -> AndesFloatingMenuRows.Medium
        }
    }

    private fun retrieveSelectedOrientation(): AndesFloatingMenuOrientation {
        return when (orientationSpinner.getItemAtPosition(orientationSpinner.selectedItemPosition)) {
            "Left" -> AndesFloatingMenuOrientation.Left
            "Right" -> AndesFloatingMenuOrientation.Right
            else -> AndesFloatingMenuOrientation.Left
        }
    }

    private fun initComponents(container: View) {
        constraintContainer = container.findViewById(R.id.floatingMenuConstraintLayout)
        rowsSpinner = container.findViewById(R.id.floatingMenuRowsSpinner)
        orientationSpinner = container.findViewById(R.id.floatingMenuOrientationSpinner)
        widthSpinner = container.findViewById(R.id.floatingMenuWidthSpinner)
        widthCustomPixels = container.findViewById(R.id.floatingMenuWidthEditText)
        triggerPositionSpinner = container.findViewById(R.id.floatingMenuTriggerPositionSpinner)
        callbackCheckbox = container.findViewById(R.id.floatingMenuCallback)
        selectableCheckbox = container.findViewById(R.id.floatingMenuSelectable)
        listSizeTextField = container.findViewById(R.id.floatingMenuListSizeEditText)
        updateButton = container.findViewById(R.id.change_button)
        triggerButton = container.findViewById(R.id.andes_floatingmenu_trigger)
        clearButton = container.findViewById(R.id.clear_button)
    }

    private fun setupConfigComponents(context: Context) {
        setupSpinnerComponent(context, rowsSpinner, R.array.andes_floatingmenu_rows_spinner).also {
            rowsSpinner.setSelection(1)
        }
        setupSpinnerComponent(
            context,
            orientationSpinner,
            R.array.andes_floatingmenu_orientation_spinner
        )
        setupSpinnerComponent(context, widthSpinner, R.array.andes_floatingmenu_width_spinner)
        setupSpinnerComponent(
            context,
            triggerPositionSpinner,
            R.array.andes_floatingmenu_trigger_position_spinner
        )
        setupWidthCustomComponent()
        setupListSizeComponent()
    }

    private fun setupListSizeComponent() {
        listSizeTextField.text = DEFAULT_LIST_SIZE.toString()
    }

    private fun setupWidthCustomComponent() {
        widthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (widthSpinner.getItemAtPosition(position)) {
                    "Custom" -> widthCustomPixels.visibility = View.VISIBLE
                    else -> widthCustomPixels.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // no-op
            }
        }
        widthCustomPixels.text = DEFAULT_WIDTH.toString()
    }

    private fun setupFloatingMenu(context: Context) {
        floatingMenu = AndesFloatingMenu(context, andesList)
        triggerButton.setOnClickListener {
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
