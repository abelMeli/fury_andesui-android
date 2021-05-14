package com.mercadolibre.android.andesui.dropdown.factory

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.dropdown.size.AndesDropdownSize
import com.mercadolibre.android.andesui.dropdown.state.AndesDropdownState
import com.mercadolibre.android.andesui.dropdown.type.AndesDropdownMenuType

internal data class AndesDropdownAttrs(
    val andesDropdownMenuType: AndesDropdownMenuType,
    val andesDropdownLabel: String?,
    val andesDropdownHelper: String?,
    val andesDropdownPlaceHolder: String?,
    val andesDropdownSize: AndesDropdownSize = AndesDropdownSize.MEDIUM,
    val andesDropdownState: AndesDropdownState
)

/**
 * This object parse the attribute set and return an instance of AndesListAttrs
 * to be used by AndesList
 */
internal object AndesDropdownAttrParser {

    private const val ANDES_DROPDOWN_SIZE_SMALL = "10000"
    private const val ANDES_DROPDOWN_SIZE_MEDIUM = "10001"
    private const val ANDES_DROPDOWN_SIZE_LARGE = "10002"

    private const val ANDES_DROPDOWN_MENU_TYPE_BOTTOMSHEET = "9002"
    private const val ANDES_DROPDOWN_MENU_TYPE_FLOATINGMENU = "9003"

    private const val ANDES_DROPDOWN_STATE_ENABLED = "10003"
    private const val ANDES_DROPDOWN_STATE_ERROR = "10004"
    private const val ANDES_DROPDOWN_STATE_DISABLED = "10005"

    fun parse(context: Context, attr: AttributeSet?): AndesDropdownAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesList)
        val andesDropdownState = getState(typedArray)
        val andesDropdownMenuType = when (typedArray.getString(R.styleable.AndesDropdown_AndesDropdownMenuType)) {
            ANDES_DROPDOWN_MENU_TYPE_BOTTOMSHEET -> AndesDropdownMenuType.BOTTOMSHEET
            ANDES_DROPDOWN_MENU_TYPE_FLOATINGMENU -> AndesDropdownMenuType.FLOATINGMENU
            else -> AndesDropdownMenuType.BOTTOMSHEET
        }

        val andesDropdownSize = when (typedArray.getString(R.styleable.AndesDropdown_andesDropdownSize)) {
            ANDES_DROPDOWN_SIZE_SMALL -> AndesDropdownSize.SMALL
            ANDES_DROPDOWN_SIZE_MEDIUM -> AndesDropdownSize.MEDIUM
            ANDES_DROPDOWN_SIZE_LARGE -> AndesDropdownSize.LARGE
            else -> AndesDropdownSize.MEDIUM
        }

        val andesDropdownLabel = typedArray.getString(R.styleable.AndesDropdown_andesDropdownLabel)
        val andesDropdownHelper = typedArray.getString(R.styleable.AndesDropdown_andesDropdownHelper)
        val andesDropdownPlaceHolder = typedArray.getString(R.styleable.AndesDropdown_andesDropdownPlaceHolder)

        return AndesDropdownAttrs(
                andesDropdownMenuType = andesDropdownMenuType,
                andesDropdownLabel = andesDropdownLabel,
                andesDropdownHelper = andesDropdownHelper,
                andesDropdownPlaceHolder = andesDropdownPlaceHolder,
                andesDropdownSize = andesDropdownSize,
                andesDropdownState = andesDropdownState
        ).also { typedArray.recycle() }
    }

    private fun getState(typedArray: TypedArray): AndesDropdownState =
            when (typedArray.getString(R.styleable.AndesTextfield_andesTextfieldState)) {
                ANDES_DROPDOWN_STATE_ENABLED -> AndesDropdownState.ENABLED
                ANDES_DROPDOWN_STATE_ERROR -> AndesDropdownState.ERROR
                ANDES_DROPDOWN_STATE_DISABLED -> AndesDropdownState.DISABLED
                else -> AndesDropdownState.ENABLED
            }
}
