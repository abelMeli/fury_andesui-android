package com.mercadolibre.android.andesui.timepicker.factory

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.timepicker.minutesInterval.AndesTimePickerInterval
import com.mercadolibre.android.andesui.timepicker.state.AndesTimePickerState
import com.mercadolibre.android.andesui.timepicker.type.AndesTimePickerType

/**
 * The data class that contains the public components of the timepicker.
 */
internal data class AndesTimePickerAttrs(
    val andesTimePickerLabel: String?,
    val andesTimePickerHelper: String?,
    val andesTimePickerCurrentTime: String?,
    val andesTimePickerState: AndesTimePickerState,
    val andesTimePickerType: AndesTimePickerType,
    val andesTimePickerInterval: AndesTimePickerInterval
)

/**
 * This object parse the attribute set and return an instance of AndesTimePickerAttrs to be used by AndesTimePicker
 */
internal object AndesTimePickerParser {

    private const val ANDES_TIME_PICKER_STATE_IDLE = "1000"
    private const val ANDES_TIME_PICKER_STATE_ERROR = "1001"
    private const val ANDES_TIME_PICKER_STATE_DISABLED = "1002"
    private const val ANDES_TIME_PICKER_STATE_READONLY = "1003"

    private const val ANDES_TIME_PICKER_TYPE_TIME_INTERVAL = "2000"

    private const val ANDES_TIME_PICKER_INTERVAL_5_MINUTES = "3000"
    private const val ANDES_TIME_PICKER_INTERVAL_10_MINUTES = "3001"
    private const val ANDES_TIME_PICKER_INTERVAL_15_MINUTES = "3002"
    private const val ANDES_TIME_PICKER_INTERVAL_30_MINUTES = "3003"
    private const val ANDES_TIME_PICKER_INTERVAL_60_MINUTES = "3004"

    fun parse(context: Context, attr: AttributeSet?): AndesTimePickerAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesTimePicker)
        return AndesTimePickerAttrs(
                andesTimePickerLabel = typedArray.getString(R.styleable.AndesTimePicker_andesTimePickerLabel),
                andesTimePickerHelper = typedArray.getString(R.styleable.AndesTimePicker_andesTimePickerHelper),
                andesTimePickerCurrentTime = typedArray.getString(R.styleable.AndesTimePicker_andesTimePickerCurrentTime),
                andesTimePickerState = getState(typedArray),
                andesTimePickerType = getType(typedArray),
                andesTimePickerInterval = getInterval(typedArray)
        ).also { typedArray.recycle() }
    }

    private fun getState(typedArray: TypedArray): AndesTimePickerState =
        when (typedArray.getString(R.styleable.AndesTimePicker_andesTimePickerState)) {
            ANDES_TIME_PICKER_STATE_IDLE -> AndesTimePickerState.IDLE
            ANDES_TIME_PICKER_STATE_ERROR -> AndesTimePickerState.ERROR
            ANDES_TIME_PICKER_STATE_DISABLED -> AndesTimePickerState.DISABLED
            ANDES_TIME_PICKER_STATE_READONLY -> AndesTimePickerState.READONLY
            else -> AndesTimePickerState.IDLE
        }

    private fun getType(typedArray: TypedArray): AndesTimePickerType =
        when (typedArray.getString(R.styleable.AndesTimePicker_andesTimePickerType)) {
            ANDES_TIME_PICKER_TYPE_TIME_INTERVAL -> AndesTimePickerType.TIME_INTERVAL
            else -> AndesTimePickerType.TIME_INTERVAL
        }

    private fun getInterval(typedArray: TypedArray): AndesTimePickerInterval =
        when (typedArray.getString(R.styleable.AndesTimePicker_andesTimePickerInterval)) {
            ANDES_TIME_PICKER_INTERVAL_5_MINUTES -> AndesTimePickerInterval.MINUTES_5
            ANDES_TIME_PICKER_INTERVAL_10_MINUTES -> AndesTimePickerInterval.MINUTES_10
            ANDES_TIME_PICKER_INTERVAL_15_MINUTES -> AndesTimePickerInterval.MINUTES_15
            ANDES_TIME_PICKER_INTERVAL_30_MINUTES -> AndesTimePickerInterval.MINUTES_30
            ANDES_TIME_PICKER_INTERVAL_60_MINUTES -> AndesTimePickerInterval.MINUTES_60
            else -> AndesTimePickerInterval.MINUTES_30
        }
}
