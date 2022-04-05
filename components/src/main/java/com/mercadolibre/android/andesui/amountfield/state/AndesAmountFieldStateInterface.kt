package com.mercadolibre.android.andesui.amountfield.state

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.amountfield.utils.AndesAmountFieldEditText
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.andesui.utils.AnimationsUtils.shake

internal interface AndesAmountFieldStateInterface {
    fun getHelperTextColor(): AndesTextViewColor
    fun getIconVisibility(): Int
    fun getHelperTextStyle(): Int
    fun getActions(): (ViewGroup) -> Unit
    fun getExceededHelperText(context: Context): String? = null
}

internal object AndesAmountFieldIdleState : AndesAmountFieldStateInterface {
    override fun getHelperTextColor() = AndesTextViewColor.Secondary
    override fun getIconVisibility() = View.GONE
    override fun getHelperTextStyle() = Typeface.NORMAL
    override fun getActions() = { _: ViewGroup ->
        // no-op
    }
}

internal open class AndesAmountFieldErrorState : AndesAmountFieldStateInterface {
    override fun getHelperTextColor() = AndesTextViewColor.Negative
    override fun getIconVisibility() = View.VISIBLE
    override fun getHelperTextStyle() = Typeface.BOLD
    override fun getActions() = { viewParent: ViewGroup ->
        val initialCurrencyColor = getInitialCurrencyColor(viewParent)
        setErrorColorToEachView(viewParent)
        viewParent.shake {
            setIdleColorToEachView(viewParent, initialCurrencyColor)
        }
    }

    private fun getInitialCurrencyColor(viewParent: ViewGroup): AndesTextViewColor {
        val currencyTextView: AndesTextView = viewParent.findViewById(R.id.amount_field_currency_symbol)
        return currencyTextView.getTextColor()
    }

    private fun setErrorColorToEachView(viewParent: ViewGroup) {
        val editText: AndesAmountFieldEditText = viewParent.findViewById(R.id.amount_field_edit_text)
        val currencyTextView: AndesTextView = viewParent.findViewById(R.id.amount_field_currency_symbol)
        val suffixTextView: AndesTextView = viewParent.findViewById(R.id.amount_field_suffix_text)

        val errorColor = ContextCompat.getColor(viewParent.context, R.color.andes_text_color_negative)

        with(errorColor) {
            editText.setTextColor(this)
            editText.setHintTextColor(this)
            currencyTextView.setTextColor(this)
            suffixTextView.setTextColor(this)
        }
    }

    private fun setIdleColorToEachView(viewParent: ViewGroup, oldCurrencyColor: AndesTextViewColor) {
        val editText: AndesAmountFieldEditText = viewParent.findViewById(R.id.amount_field_edit_text)
        val currencyTextView: AndesTextView = viewParent.findViewById(R.id.amount_field_currency_symbol)
        val suffixTextView: AndesTextView = viewParent.findViewById(R.id.amount_field_suffix_text)

        val primaryColor = ContextCompat.getColor(viewParent.context, R.color.andes_text_color_primary)
        val secondaryColor = ContextCompat.getColor(viewParent.context, R.color.andes_text_color_secondary)

        editText.setTextColor(primaryColor)
        editText.setHintTextColor(secondaryColor)
        currencyTextView.setTextColor(oldCurrencyColor)
        suffixTextView.setTextColor(secondaryColor)
    }
}

/**
 * this object is equal to the error object, but with a custom helper text.
 */
internal object AndesAmountFieldAmountExceededState : AndesAmountFieldErrorState() {
    override fun getExceededHelperText(context: Context) = context.resources.getString(R.string.andes_amount_field_exceeded_text)
}
