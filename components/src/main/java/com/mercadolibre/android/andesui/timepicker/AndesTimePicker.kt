package com.mercadolibre.android.andesui.timepicker

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.dropdown.AndesDropDownForm
import com.mercadolibre.android.andesui.dropdown.AndesDropDownItem
import com.mercadolibre.android.andesui.dropdown.utils.AndesDropdownDelegate
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.timepicker.factory.AndesTimePickerAttrs
import com.mercadolibre.android.andesui.timepicker.factory.AndesTimePickerConfiguration
import com.mercadolibre.android.andesui.timepicker.factory.AndesTimePickerConfigurationFactory
import com.mercadolibre.android.andesui.timepicker.factory.AndesTimePickerParser
import com.mercadolibre.android.andesui.timepicker.minutesInterval.AndesTimePickerInterval
import com.mercadolibre.android.andesui.timepicker.state.AndesReadOnlyTimePickerState
import com.mercadolibre.android.andesui.timepicker.state.AndesTimePickerState
import com.mercadolibre.android.andesui.timepicker.type.AndesTimePickerType

@Suppress("TooManyFunctions")
class AndesTimePicker : ConstraintLayout, AndesDropdownDelegate {

    private lateinit var andesDropDown: AndesDropDownForm
    private lateinit var container: View
    private var onTimeSelectedListener: OnTimeSelectedListener? = null
    private var items: ArrayList<AndesDropDownItem> = arrayListOf()

    /**
     * Getter and setter for [label].
     */
    var label: String?
        get() = andesTimePickerAttrs.andesTimePickerLabel
        set(value) {
            andesTimePickerAttrs = andesTimePickerAttrs.copy(andesTimePickerLabel = value)
            setupLabelComponent(createConfig())
        }

    /**
     * Getter and setter for [helper].
     */
    var helper: String?
        get() = andesTimePickerAttrs.andesTimePickerHelper
        set(value) {
            andesTimePickerAttrs = andesTimePickerAttrs.copy(andesTimePickerHelper = value)
            setupHelperComponent(createConfig())
        }

    /**
     * Getter and setter for [currentTime].
     * If no time was selected, it returns null.
     * The string format should be "hh:mm", and the value should be consistent with
     * the [minutesInterval] value (for the 30 minutes interval, we should not pass
     * values like "13:05"
     */
    var currentTime: String?
        get() = andesTimePickerAttrs.andesTimePickerCurrentTime
        set(value) {
            andesTimePickerAttrs = andesTimePickerAttrs.copy(andesTimePickerCurrentTime = value)
            setupCurrentTimeComponent(createConfig())
        }

    /**
     * Getter and setter for [state].
     */
    var state: AndesTimePickerState
        get() = andesTimePickerAttrs.andesTimePickerState
        set(value) {
            andesTimePickerAttrs = andesTimePickerAttrs.copy(andesTimePickerState = value)
            setupStateComponent(createConfig())
        }

    /**
     * Getter and setter for [state].
     */
    var type: AndesTimePickerType
        get() = andesTimePickerAttrs.andesTimePickerType
        set(value) {
            andesTimePickerAttrs = andesTimePickerAttrs.copy(andesTimePickerType = value)
            setupDropDown(createConfig())
        }

    /**
     * Getter and setter for [minutesInterval]
     */
    var minutesInterval: AndesTimePickerInterval
        get() = andesTimePickerAttrs.andesTimePickerInterval
        set(value) {
            andesTimePickerAttrs = andesTimePickerAttrs.copy(andesTimePickerInterval = value)
            setupDropDown(createConfig())
        }

    /**
     * Setter [OnClickListener].
     */
    fun setupCallback(callback: OnTimeSelectedListener?) {
        if (this.onTimeSelectedListener != callback) {
            this.onTimeSelectedListener = callback
        }
    }

    interface OnTimeSelectedListener {
        fun onTimeSelected(currentTime: String)
        fun onTimePeriodSelected()
    }

    private lateinit var andesTimePickerAttrs: AndesTimePickerAttrs

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        label: String?,
        helper: String?,
        currentTime: String?,
        state: AndesTimePickerState = AndesTimePickerState.IDLE,
        type: AndesTimePickerType = AndesTimePickerType.TIME_INTERVAL,
        interval: AndesTimePickerInterval = AndesTimePickerInterval.MINUTES_30
    ) : super(context) {
        initAttrs(label, helper, currentTime, state, type, interval)
    }

    /**
     * Sets the proper [config] for this TimePicker based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesTimePickerAttrs = AndesTimePickerParser.parse(context, attrs)
        val config = AndesTimePickerConfigurationFactory.create(andesTimePickerAttrs)
        setupComponents(config)
    }

    @Suppress("LongParameterList")
    private fun initAttrs(
        label: String?,
        helper: String?,
        currentTime: String?,
        state: AndesTimePickerState,
        type: AndesTimePickerType,
        interval: AndesTimePickerInterval
    ) {
        andesTimePickerAttrs = AndesTimePickerAttrs(
            andesTimePickerLabel = label,
            andesTimePickerHelper = helper,
            andesTimePickerCurrentTime = currentTime,
            andesTimePickerState = state,
            andesTimePickerType = type,
            andesTimePickerInterval = interval
        )
        val config = AndesTimePickerConfigurationFactory.create(andesTimePickerAttrs)
        setupComponents(config)
    }

    /**
     * Responsible for setting up all properties of each component that is part of this TimePicker.
     * Is like a choreographer ;)
     */
    private fun setupComponents(config: AndesTimePickerConfiguration) {
        initComponents()
        setupId()
        setupDropDown(config)
        setupLabelComponent(config)
        setupHelperComponent(config)
        setupStateComponent(config)
        setupCurrentTimeComponent(config)
    }

    private fun setupId() {
        if (id == NO_ID) {
            id = View.generateViewId()
        }
    }

    /**
     * The if check is needed to determine if the [minutesInterval] value selected matches with
     * a developed variant. Soon, when the short interval variant is developed, this check should be removed
     */
    private fun setupDropDown(config: AndesTimePickerConfiguration) {
        if (config.interval == AndesTimePickerInterval.MINUTES_30.interval ||
            config.interval == AndesTimePickerInterval.MINUTES_60.interval) {
            andesDropDown.delegate = this
            andesDropDown.placeholder = DROPDOWN_PLACEHOLDER_DEFAULT_VALUE
            items = config.dropdownItemsList
            andesDropDown.setItems(items)
        } else {
            Log.d("AndesTimePicker", "The AndesTimePicker interval variant selected is not developed yet")
        }
    }

    /**
     * Creates all the views that are part of this TimePicker.
     * After a view is created then a view id is added to it.
     */
    private fun initComponents() {
        container = LayoutInflater.from(context).inflate(R.layout.andes_layout_timepicker, this)
        andesDropDown = container.findViewById(R.id.andes_layout_timepicker_dropdown)
    }

    /**
     * Gets data from the config and sets to the TimePicker Label component.
     */
    private fun setupLabelComponent(config: AndesTimePickerConfiguration) {
        andesDropDown.label = config.label
    }

    /**
     * Gets data from the config and sets to the TimePicker Helper component.
     */
    private fun setupHelperComponent(config: AndesTimePickerConfiguration) {
        andesDropDown.helper = config.helper
    }

    /**
     * Gets data from the config and sets to the TimePicker current time component.
     */
    private fun setupCurrentTimeComponent(config: AndesTimePickerConfiguration) {
        if (!config.currentTime.isNullOrEmpty()) {
            val timeList = config.interval.full()
            val timePositionInList = timeList.indexOf(config.currentTime)

            if (timePositionInList != TIME_NOT_IN_LIST) {
                andesDropDown.selectItem(timePositionInList)
            } else {
                throw IllegalArgumentException("Invalid format, the valid format is \"hh:mm\"")
            }
        }
    }

    /**
     * Gets data from the config and sets to the TimePicker State component.
     * When the state is READONLY, calls the method that configures the internal
     * dropdown to avoid having both role and onClick behavior.
     */
    private fun setupStateComponent(config: AndesTimePickerConfiguration) {
        andesDropDown.state = config.state.state.dropDownState()

        if (config.state.state is AndesReadOnlyTimePickerState) {
            andesDropDown.setReadOnly()
        }
    }

    override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
        currentTime = items[position].title
        currentTime?.let { time ->
            onTimeSelectedListener?.onTimeSelected(time)
        }
    }

    private fun createConfig() = AndesTimePickerConfigurationFactory.create(andesTimePickerAttrs)

    companion object {
        private const val DROPDOWN_PLACEHOLDER_DEFAULT_VALUE = "00:00"
        private const val TIME_NOT_IN_LIST = -1
    }
}
