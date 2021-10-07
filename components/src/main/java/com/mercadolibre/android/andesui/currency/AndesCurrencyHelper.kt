package com.mercadolibre.android.andesui.currency

import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency

/**
 * AndesCurrencyHelper is a util object that contains the configuration of every [AndesMoneyAmountCurrency] and
 * [AndesCountry] in order to display AndesMoneyAmount components correctly.
 */
object AndesCurrencyHelper {

    lateinit var currentCountry: AndesCountry
    private lateinit var currencies: Map<AndesMoneyAmountCurrency, AndesCurrencyInfo>
    private lateinit var countries: Map<AndesCountry, AndesCountryInfo>

    private fun currenciesAndCountriesNotInitialized() = !this::countries.isInitialized && !this::currencies.isInitialized

    /**
     * Setup AndesMoneyAmount configurations. This function can be called only once in your application.
     */
    fun configure(
        currencies: Map<AndesMoneyAmountCurrency, AndesCurrencyInfo>,
        countries: Map<AndesCountry, AndesCountryInfo>,
        country: AndesCountry
    ) {
        if (currenciesAndCountriesNotInitialized()) {
            this.countries = countries
            this.currencies = currencies
            this.currentCountry = country
        }
    }

    @JvmStatic
    fun getCountry(country: AndesCountry): AndesCountryInfo {
        return countries[country] ?: DEFAULT_COUNTRY
    }

    @JvmStatic
    fun getCurrency(currency: AndesMoneyAmountCurrency): AndesCurrencyInfo {
        return currencies[currency] ?: DEFAULT_CURRENCY
    }

    private val DEFAULT_COUNTRY = AndesCountryInfo(',', '.')

    private val DEFAULT_CURRENCY = AndesCurrencyInfo(
            symbol = "$",
            decimalPlaces = 2,
            singularDescription = R.string.andes_currency_peso_singular,
            pluralDescription = R.string.andes_currency_peso_plural,
            decimalSingularDescription = R.string.andes_currency_centavo_singular,
            decimalPluralDescription = R.string.andes_currency_centavo_plural
    )
}
