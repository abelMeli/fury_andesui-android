package com.mercadolibre.android.andesui.switch.factory

import android.content.Context
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.switch.align.AndesSwitchAlign
import com.mercadolibre.android.andesui.switch.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.switch.type.AndesSwitchType

internal data class AndesSwitchAttrs(
    val andesSwitchAlign: AndesSwitchAlign,
    val andesSwitchStatus: AndesSwitchStatus,
    val andesSwitchType: AndesSwitchType,
    val andesSwitchText: String?
)

internal object AndesSwitchAttrsParser {

    private const val ANDES_SWITCH_ALIGN_LEFT = "1000"
    private const val ANDES_SWITCH_ALIGN_RIGHT = "1001"

    private const val ANDES_SWITCH_STATUS_CHECKED = "2000"
    private const val ANDES_SWITCH_STATUS_UNCHECKED = "2001"

    private const val ANDES_SWITCH_TYPE_ENABLED = "3000"
    private const val ANDES_SWITCH_TYPE_DISABLED = "3001"

    fun parse(context: Context, attr: AttributeSet?): AndesSwitchAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesSwitch)

        val align = when (typedArray.getString(R.styleable.AndesSwitch_andesSwitchAlign)) {
            ANDES_SWITCH_ALIGN_LEFT -> AndesSwitchAlign.LEFT
            ANDES_SWITCH_ALIGN_RIGHT -> AndesSwitchAlign.RIGHT
            else -> AndesSwitchAlign.RIGHT
        }

        val status = when (typedArray.getString(R.styleable.AndesSwitch_andesSwitchStatus)) {
            ANDES_SWITCH_STATUS_CHECKED -> AndesSwitchStatus.CHECKED
            ANDES_SWITCH_STATUS_UNCHECKED -> AndesSwitchStatus.UNCHECKED
            else -> AndesSwitchStatus.UNCHECKED
        }

        val type = when (typedArray.getString(R.styleable.AndesSwitch_andesSwitchType)) {
            ANDES_SWITCH_TYPE_ENABLED -> AndesSwitchType.ENABLED
            ANDES_SWITCH_TYPE_DISABLED -> AndesSwitchType.DISABLED
            else -> AndesSwitchType.ENABLED
        }

        val text = typedArray.getString(R.styleable.AndesSwitch_andesSwitchText)

        return AndesSwitchAttrs(align, status, type, text)
                .also { typedArray.recycle() }
    }
}
