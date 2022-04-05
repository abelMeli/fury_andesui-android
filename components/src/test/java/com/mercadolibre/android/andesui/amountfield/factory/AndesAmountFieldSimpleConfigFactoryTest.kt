package com.mercadolibre.android.andesui.amountfield.factory

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.view.View
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.amountfield.entrymode.AndesAmountFieldEntryMode
import com.mercadolibre.android.andesui.amountfield.entrymode.AndesAmountFieldEntryModeInt
import com.mercadolibre.android.andesui.amountfield.entrytype.AndesAmountFieldEntryType
import com.mercadolibre.android.andesui.amountfield.size.AndesAmountFieldSize
import com.mercadolibre.android.andesui.amountfield.state.AndesAmountFieldAmountExceededState
import com.mercadolibre.android.andesui.amountfield.state.AndesAmountFieldErrorState
import com.mercadolibre.android.andesui.amountfield.state.AndesAmountFieldIdleState
import com.mercadolibre.android.andesui.amountfield.state.AndesAmountFieldState
import com.mercadolibre.android.andesui.amountfield.utils.AmountListener
import com.mercadolibre.android.andesui.amountfield.utils.AndesAmountFieldDecimalFormatter
import com.mercadolibre.android.andesui.amountfield.utils.AndesAmountFieldIntFormatter
import com.mercadolibre.android.andesui.amountfield.utils.ResizingListener
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.assertIsNull
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.andesui.utils.MockConfigProvider
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesAmountFieldSimpleConfigFactoryTest {

    private lateinit var context: Context
    private val configFactory = AndesAmountFieldSimpleMoneyConfigFactory
    private lateinit var attrs: AndesAmountFieldSimpleMoneyAttrs

    init {
        MockConfigProvider.configure()
    }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `idle, null, money, large, ARS, true, AR, null, null, null, null, null, null`() {
        attrs = AndesAmountFieldSimpleMoneyAttrs(
            AndesAmountFieldState.Idle,
            null,
            AndesAmountFieldEntryType.MONEY,
            AndesAmountFieldSize.LARGE,
            AndesMoneyAmountCurrency.ARS,
            true,
            AndesCountry.AR,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val config = configFactory.create(
            context,
            provideResizingListener(),
            provideAmountListener(),
            attrs
        )

        (config.formatter is AndesAmountFieldIntFormatter) assertEquals true
        config.maxValue assertIsNull true
        config.numberOfDecimals assertEquals 2
        config.decimalSeparator assertEquals ','
        config.textAlignment assertEquals View.TEXT_ALIGNMENT_TEXT_START
        config.initialValue assertIsNull true
        config.placeholder assertEquals "0"
        config.suffixText assertIsNull true
        config.suffixTextColor assertEquals AndesTextViewColor.Secondary
        config.stateActions assertEquals AndesAmountFieldIdleState.getActions()
        config.helperText assertIsNull true
        config.helperTextColor assertEquals AndesTextViewColor.Secondary
        config.helperTextStyle assertEquals Typeface.NORMAL
        config.helperIconVisibility assertEquals View.GONE
        config.currencySymbol assertEquals "ARS"
        config.currencyVisibility assertEquals View.VISIBLE
        config.editTextSize assertEquals context.resources.getDimension(R.dimen.andes_amount_field_size_large_amount)
        config.currencyTextSize assertEquals context.resources.getDimension(R.dimen.andes_amount_field_size_large_currency)
        config.suffixTextSize assertEquals context.resources.getDimension(R.dimen.andes_amount_field_size_large_suffix)
        config.resizableComponentsHorizontalMargin assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_amount_field_size_large_horizontal_margin)
    }

    @Test
    fun `error, from_decimal, percentage, medium, MXN, false, MX, 3, 1000, helper, suffix, suffix for a11y, 3000`() {
        val errorState = AndesAmountFieldState.Error
        attrs = AndesAmountFieldSimpleMoneyAttrs(
            errorState,
            AndesAmountFieldEntryMode.DECIMAL,
            AndesAmountFieldEntryType.PERCENTAGE,
            AndesAmountFieldSize.MEDIUM,
            AndesMoneyAmountCurrency.MXN,
            false,
            AndesCountry.MX,
            3,
            "1000.00",
            "helper",
            "suffix",
            "suffix for a11y",
            "3000.00"
        )

        val config = configFactory.create(
            context,
            provideResizingListener(),
            provideAmountListener(),
            attrs
        )

        (config.formatter is AndesAmountFieldDecimalFormatter) assertEquals true
        config.maxValue assertEquals "3000.00"
        config.numberOfDecimals assertEquals 3
        config.decimalSeparator assertEquals '.'
        config.textAlignment assertEquals View.TEXT_ALIGNMENT_TEXT_END
        config.initialValue assertEquals "1000.00"
        config.placeholder assertEquals "0.000"
        config.suffixText assertEquals "%"
        config.suffixTextColor assertEquals AndesTextViewColor.Primary
        Assertions.assertThat(config.stateActions).usingRecursiveComparison().isEqualTo(errorState.state.getActions())
        config.helperText assertEquals "helper"
        config.helperTextColor assertEquals AndesTextViewColor.Negative
        config.helperTextStyle assertEquals Typeface.BOLD
        config.helperIconVisibility assertEquals View.VISIBLE
        config.currencySymbol assertEquals "$"
        config.currencyVisibility assertEquals View.GONE
        config.editTextSize assertEquals context.resources.getDimension(R.dimen.andes_amount_field_size_medium_amount)
        config.currencyTextSize assertEquals context.resources.getDimension(R.dimen.andes_amount_field_size_medium_currency)
        config.suffixTextSize assertEquals context.resources.getDimension(R.dimen.andes_amount_field_size_medium_suffix)
        config.resizableComponentsHorizontalMargin assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_amount_field_size_medium_horizontal_margin)
    }

    @Test
    fun `amount_exceeded, null, money, small, MXN, true, MX, null, 1000, helper, suffix, suffix for a11y, 3000`() {
        val state = AndesAmountFieldState.AmountExceeded
        attrs = AndesAmountFieldSimpleMoneyAttrs(
            state,
            null,
            AndesAmountFieldEntryType.MONEY,
            AndesAmountFieldSize.MEDIUM,
            AndesMoneyAmountCurrency.MXN,
            true,
            AndesCountry.MX,
            null,
            "1000.00",
            "helper",
            "suffix",
            "suffix for a11y",
            "3000.00"
        )

        val config = configFactory.create(
            context,
            provideResizingListener(),
            provideAmountListener(),
            attrs
        )

        (config.formatter is AndesAmountFieldIntFormatter) assertEquals true
        config.maxValue assertEquals "3000.00"
        config.numberOfDecimals assertEquals 2
        config.decimalSeparator assertEquals '.'
        config.textAlignment assertEquals View.TEXT_ALIGNMENT_TEXT_START
        config.initialValue assertEquals "1000.00"
        config.placeholder assertEquals "0"
        config.suffixText assertEquals "suffix"
        config.suffixTextColor assertEquals AndesTextViewColor.Secondary
        Assertions.assertThat(config.stateActions).usingRecursiveComparison().isEqualTo(state.state.getActions())
        config.helperText assertEquals "You have exceeded the maximum allowed limit"
        config.helperTextColor assertEquals AndesTextViewColor.Negative
        config.helperTextStyle assertEquals Typeface.BOLD
        config.helperIconVisibility assertEquals View.VISIBLE
        config.currencySymbol assertEquals "MXN"
        config.currencyVisibility assertEquals View.VISIBLE
        config.editTextSize assertEquals context.resources.getDimension(R.dimen.andes_amount_field_size_medium_amount)
        config.currencyTextSize assertEquals context.resources.getDimension(R.dimen.andes_amount_field_size_medium_currency)
        config.suffixTextSize assertEquals context.resources.getDimension(R.dimen.andes_amount_field_size_medium_suffix)
        config.resizableComponentsHorizontalMargin assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_amount_field_size_medium_horizontal_margin)
    }

    @Test
    fun `amount_exceeded, null, money, small, MXN, false, MX, 0, 1000, helper, suffix, suffix for a11y, 3000`() {
        attrs = AndesAmountFieldSimpleMoneyAttrs(
            AndesAmountFieldState.AmountExceeded,
            null,
            AndesAmountFieldEntryType.MONEY,
            AndesAmountFieldSize.MEDIUM,
            AndesMoneyAmountCurrency.MXN,
            false,
            AndesCountry.MX,
            0,
            "1000.00",
            "helper",
            "suffix",
            "suffix for a11y",
            "3000.00"
        )

        val config = configFactory.create(
            context,
            provideResizingListener(),
            provideAmountListener(),
            attrs
        )

        (config.formatter is AndesAmountFieldIntFormatter) assertEquals true
        config.maxValue assertEquals "3000.00"
        config.numberOfDecimals assertEquals 0
        config.decimalSeparator assertEquals '.'
        config.textAlignment assertEquals View.TEXT_ALIGNMENT_TEXT_START
        config.initialValue assertEquals "1000.00"
        config.placeholder assertEquals "0"
        config.suffixText assertEquals "suffix"
        config.suffixTextColor assertEquals AndesTextViewColor.Secondary
        config.helperText assertEquals "You have exceeded the maximum allowed limit"
        config.helperTextColor assertEquals AndesTextViewColor.Negative
        config.helperTextStyle assertEquals Typeface.BOLD
        config.helperIconVisibility assertEquals View.VISIBLE
        config.currencySymbol assertEquals "$"
        config.currencyVisibility assertEquals View.VISIBLE
        config.editTextSize assertEquals context.resources.getDimension(R.dimen.andes_amount_field_size_medium_amount)
        config.currencyTextSize assertEquals context.resources.getDimension(R.dimen.andes_amount_field_size_medium_currency)
        config.suffixTextSize assertEquals context.resources.getDimension(R.dimen.andes_amount_field_size_medium_suffix)
        config.resizableComponentsHorizontalMargin assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_amount_field_size_medium_horizontal_margin)
    }

    @Test
    fun `amount_exceeded, null, percentage, small, BRL, false, BR, null, 1000, helper, suffix, null, 3000`() {
        attrs = AndesAmountFieldSimpleMoneyAttrs(
            AndesAmountFieldState.AmountExceeded,
            null,
            AndesAmountFieldEntryType.PERCENTAGE,
            AndesAmountFieldSize.MEDIUM,
            AndesMoneyAmountCurrency.BRL,
            false,
            AndesCountry.BR,
            null,
            "1000.00",
            "helper",
            "suffix",
            null,
            "3000.00"
        )

        val config = configFactory.create(
            context,
            provideResizingListener(),
            provideAmountListener(),
            attrs
        )

        (config.formatter is AndesAmountFieldDecimalFormatter) assertEquals true
        config.maxValue assertEquals "3000.00"
        config.numberOfDecimals assertEquals 2
        config.decimalSeparator assertEquals ','
        config.textAlignment assertEquals View.TEXT_ALIGNMENT_TEXT_END
        config.initialValue assertEquals "1000.00"
        config.placeholder assertEquals "0,00"
        config.suffixText assertEquals "%"
        config.suffixTextColor assertEquals AndesTextViewColor.Primary
        config.helperText assertEquals "You have exceeded the maximum allowed limit"
        config.helperTextColor assertEquals AndesTextViewColor.Negative
        config.helperTextStyle assertEquals Typeface.BOLD
        config.helperIconVisibility assertEquals View.VISIBLE
        config.currencySymbol assertEquals "R$"
        config.currencyVisibility assertEquals View.GONE
        config.editTextSize assertEquals context.resources.getDimension(R.dimen.andes_amount_field_size_medium_amount)
        config.currencyTextSize assertEquals context.resources.getDimension(R.dimen.andes_amount_field_size_medium_currency)
        config.suffixTextSize assertEquals context.resources.getDimension(R.dimen.andes_amount_field_size_medium_suffix)
        config.resizableComponentsHorizontalMargin assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_amount_field_size_medium_horizontal_margin)
    }

    private fun provideResizingListener(): ResizingListener {
        return object : ResizingListener {
            override fun resizeComponentIfNeeded() {
                // no-op
            }
        }
    }

    private fun provideAmountListener(): AmountListener {
        return object : AmountListener {
            override fun onAmountChanged(isExceeded: Boolean) {
                // no-op
            }
        }
    }
}
