package com.mercadolibre.android.andesui.checkbox.factory

import android.content.Context
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.checkbox.align.AndesCheckboxAlign
import com.mercadolibre.android.andesui.checkbox.type.AndesCheckboxType
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks

internal data class AndesCheckboxAttrs(
    val andesCheckboxAlign: AndesCheckboxAlign,
    val andesCheckboxText: String?,
    val andesCheckboxStatus: AndesCheckboxStatus,
    val andesCheckboxType: AndesCheckboxType,
    val andesCheckboxTitleNumberOfLine: Int = AndesCheckbox.DEFAULT_LINES,
    val andesCheckboxBodyLinks: AndesBodyLinks? = null
)

/**
 * This object parse the attribute set and return an instance of AndesSimpleTagAttrs to be used by AndesCheckbox
 */
internal object AndesCheckboxAttrParser {

    private const val ANDES_CHECKBOX_ALIGN_LEFT = "1000"
    private const val ANDES_CHECKBOX_ALIGN_RIGHT = "1001"

    private const val ANDES_CHECKBOX_STATUS_SELECTED = "2000"
    private const val ANDES_CHECKBOX_STATUS_UNSELECTED = "2001"
    private const val ANDES_CHECKBOX_STATUS_UNDEFINED = "2002"

    private const val ANDES_CHECKBOX_TYPE_IDLE = "3000"
    private const val ANDES_CHECKBOX_TYPE_DISABLED = "3001"
    private const val ANDES_CHECKBOX_TYPE_ERROR = "3002"

    fun parse(context: Context, attr: AttributeSet?): AndesCheckboxAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesCheckbox)

        val align = when (typedArray.getString(R.styleable.AndesCheckbox_andesCheckboxAlign)) {
            ANDES_CHECKBOX_ALIGN_LEFT -> AndesCheckboxAlign.LEFT
            ANDES_CHECKBOX_ALIGN_RIGHT -> AndesCheckboxAlign.RIGHT
            else -> AndesCheckboxAlign.LEFT
        }

        val status = when (typedArray.getString(R.styleable.AndesCheckbox_andesCheckboxStatus)) {
            ANDES_CHECKBOX_STATUS_SELECTED -> AndesCheckboxStatus.SELECTED
            ANDES_CHECKBOX_STATUS_UNSELECTED -> AndesCheckboxStatus.UNSELECTED
            ANDES_CHECKBOX_STATUS_UNDEFINED -> AndesCheckboxStatus.UNDEFINED
            else -> AndesCheckboxStatus.UNSELECTED
        }

        val type = when (typedArray.getString(R.styleable.AndesCheckbox_andesCheckboxType)) {
            ANDES_CHECKBOX_TYPE_IDLE -> AndesCheckboxType.IDLE
            ANDES_CHECKBOX_TYPE_DISABLED -> AndesCheckboxType.DISABLED
            ANDES_CHECKBOX_TYPE_ERROR -> AndesCheckboxType.ERROR
            else -> AndesCheckboxType.IDLE
        }

        val validatedStatus = if (type == AndesCheckboxType.ERROR) {
            AndesCheckboxStatus.UNSELECTED
        } else {
            status
        }

        return AndesCheckboxAttrs(
                andesCheckboxAlign = align,
                andesCheckboxText = typedArray.getString(R.styleable.AndesCheckbox_andesCheckboxText),
                andesCheckboxStatus = validatedStatus,
                andesCheckboxType = type,
                andesCheckboxTitleNumberOfLine =
                typedArray.getInt(R.styleable.AndesCheckbox_andesCheckboxTitleNumberOfLines, AndesCheckbox.DEFAULT_LINES),
                andesCheckboxBodyLinks = null
        ).also { typedArray.recycle() }
    }
}
