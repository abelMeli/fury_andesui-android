package com.mercadolibre.android.andesui.slider.factory

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.slider.AndesSlider.Companion.DEFAULT_MAX_VALUE
import com.mercadolibre.android.andesui.slider.AndesSlider.Companion.DEFAULT_VALUE
import com.mercadolibre.android.andesui.slider.state.AndesSliderState
import com.mercadolibre.android.andesui.slider.steps.AndesSliderSteps
import com.mercadolibre.android.andesui.slider.type.AndesSliderType

internal data class AndesSliderAttrs(
    val andesSliderTitle: String?,
    val andesSliderState: AndesSliderState,
    val andesSliderMin: Float,
    val andesSliderMax: Float,
    val andesSliderValue: Float,
    val andesSliderSteps: AndesSliderSteps,
    val andesSliderType: AndesSliderType,
    val andesSliderA11ySuffix: String
)

internal object AndesSliderAttrsParser {

    private const val ANDES_SLIDER_STEPS_NONE = 5000

    private const val ANDES_SLIDER_STATE_IDLE = 3000
    private const val ANDES_SLIDER_STATE_DISABLED = 3001

    private const val ANDES_SLIDER_TYPE_SIMPLE = 1000
    private const val ANDES_SLIDER_TYPE_ICON = 1001
    private const val ANDES_SLIDER_TYPE_LIMIT = 1002

    private const val EMPTY_STRING = ""

    fun parse(context: Context, attr: AttributeSet?): AndesSliderAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesSlider)
        val andesSliderMin = typedArray.getFloat(
            R.styleable.AndesSlider_andesSliderMin,
            DEFAULT_VALUE
        )
        return AndesSliderAttrs(
            andesSliderTitle = typedArray.getString(R.styleable.AndesSlider_andesSliderTitle),
            andesSliderState = parseState(typedArray),
            andesSliderMin = andesSliderMin,
            andesSliderMax = typedArray.getFloat(
                R.styleable.AndesSlider_andesSliderMax,
                DEFAULT_MAX_VALUE
            ),
            andesSliderValue = typedArray.getFloat(
                R.styleable.AndesSlider_andesSliderValue,
                andesSliderMin
            ),
            andesSliderSteps = parseSteps(typedArray),
            andesSliderType = parseType(typedArray),
            andesSliderA11ySuffix = typedArray.getString(
                R.styleable.AndesSlider_andesSliderAccessibilityContentSuffix
            ) ?: EMPTY_STRING
        ).also { typedArray.recycle() }
    }

    private fun parseSteps(typedArray: TypedArray): AndesSliderSteps {
        return when (val steps = typedArray.getInteger(
            R.styleable.AndesSlider_andesSliderSteps,
            ANDES_SLIDER_STEPS_NONE
        )) {
            ANDES_SLIDER_STEPS_NONE -> AndesSliderSteps.None
            else -> AndesSliderSteps.Custom(steps)
        }
    }

    private fun parseState(typedArray: TypedArray): AndesSliderState {
        return when (typedArray.getInteger(
            R.styleable.AndesSlider_andesSliderState,
            ANDES_SLIDER_STATE_IDLE
        )) {
            ANDES_SLIDER_STATE_DISABLED -> AndesSliderState.DISABLED
            else -> AndesSliderState.IDLE
        }
    }

    private fun parseType(typedArray: TypedArray): AndesSliderType {
        return when (typedArray.getInteger(
            R.styleable.AndesSlider_andesSliderType,
            ANDES_SLIDER_TYPE_SIMPLE
        )) {
            ANDES_SLIDER_TYPE_SIMPLE -> AndesSliderType.Simple
            ANDES_SLIDER_TYPE_LIMIT -> AndesSliderType.Limits(
                textLeft = getLimit(typedArray, R.styleable.AndesSlider_andesSliderLeftContent),
                textRight = getLimit(typedArray, R.styleable.AndesSlider_andesSliderRightContent)
            )
            ANDES_SLIDER_TYPE_ICON -> AndesSliderType.Icon(
                iconLeft = getImage(typedArray, R.styleable.AndesSlider_andesSliderLeftContent),
                iconRight = getImage(typedArray, R.styleable.AndesSlider_andesSliderRightContent)
            )
            else -> AndesSliderType.Simple
        }
    }

    private fun getLimit(typedArray: TypedArray, styleable: Int): String? =
        typedArray.getString(styleable)

    private fun getImage(typedArray: TypedArray, styleable: Int): Drawable =
        typedArray.getDrawable(styleable)
            ?: throw IllegalArgumentException("Wrong andesSliderImage passed, check your layout")

}
