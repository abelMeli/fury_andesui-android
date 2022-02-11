package com.mercadolibre.android.andesui.demoapp.mock

import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.currency.AndesCountryInfo
import com.mercadolibre.android.andesui.currency.AndesCurrencyHelper
import com.mercadolibre.android.andesui.currency.AndesCurrencyInfo
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency

internal object MockConfigProvider {

    fun configure() {
        AndesCurrencyHelper.configure(
            currencies = mockCurrencies(),
            countries = mockCountries(),
            country = mockCurrentCountry()
        )
    }

    private fun mockCurrencies(): HashMap<AndesMoneyAmountCurrency, AndesCurrencyInfo> {
        val currencies = hashMapOf<AndesMoneyAmountCurrency, AndesCurrencyInfo>()
        currencies[AndesMoneyAmountCurrency.ARS] = AndesCurrencyInfo("$", 2, R.string.currency_peso_singular,
                R.string.currency_peso_plural, R.string.currency_centavo_singular, R.string.currency_centavo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.BRL] = AndesCurrencyInfo("R$", 2, R.string.currency_real_singular,
                R.string.currency_real_plural, R.string.currency_centavo_singular, R.string.currency_centavo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.CLP] = AndesCurrencyInfo("$", 0, R.string.currency_peso_singular,
                R.string.currency_peso_plural, R.string.currency_centavo_singular, R.string.currency_centavo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.COP] = AndesCurrencyInfo("$", 0, R.string.currency_peso_singular,
                R.string.currency_peso_plural, R.string.currency_centavo_singular, R.string.currency_centavo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.CRC] = AndesCurrencyInfo("¢", 2, R.string.currency_colon_singular,
                R.string.currency_colon_plural, R.string.currency_centimo_singular, R.string.currency_centimo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.DOP] = AndesCurrencyInfo("$", 2, R.string.currency_peso_singular,
                R.string.currency_peso_plural, R.string.currency_centavo_singular, R.string.currency_centavo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.EUR] = AndesCurrencyInfo("€", 2, R.string.currency_euro_singular,
                R.string.currency_euro_plural, R.string.currency_cent_singular, R.string.currency_cent_plural, null, false)
        currencies[AndesMoneyAmountCurrency.MXN] = AndesCurrencyInfo("$", 2, R.string.currency_peso_singular,
                R.string.currency_peso_plural, R.string.currency_centavo_singular, R.string.currency_centavo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.PAB] = AndesCurrencyInfo("B", 2, R.string.currency_balboa_singular,
                R.string.currency_balboa_plural, R.string.currency_centesimo_singular, R.string.currency_centesimo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.PEN] = AndesCurrencyInfo("S", 2, R.string.currency_sol_singular,
                R.string.currency_sol_plural, R.string.currency_centimo_singular, R.string.currency_centimo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.USD] = AndesCurrencyInfo("USD", 2, R.string.currency_dollar_singular,
                R.string.currency_dollar_plural, R.string.currency_centavo_singular, R.string.currency_centavo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.UYU] = AndesCurrencyInfo("$", 2, R.string.currency_peso_singular,
                R.string.currency_peso_plural, R.string.currency_centavo_singular, R.string.currency_centavo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.VEF] = AndesCurrencyInfo("Bs", 2, R.string.currency_bolivar_singular,
                R.string.currency_bolivar_plural, R.string.currency_centavo_singular, R.string.currency_centavo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.VES] = AndesCurrencyInfo("Bs", 2, R.string.currency_bolivar_singular,
                R.string.currency_bolivar_plural, R.string.currency_centavo_singular, R.string.currency_centavo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.CLF] = AndesCurrencyInfo("UF", 2, R.string.currency_fomento_singular,
                R.string.currency_fomento_plural, R.string.currency_centavo_singular, R.string.currency_centavo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.BOB] = AndesCurrencyInfo("Bs", 2, R.string.currency_boliviano_singular,
                R.string.currency_boliviano_plural, R.string.currency_centavo_singular, R.string.currency_centavo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.PYG] = AndesCurrencyInfo("₲", 0, R.string.currency_guarani_singular,
                R.string.currency_guarani_plural, R.string.currency_centimo_singular, R.string.currency_centimo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.GTQ] = AndesCurrencyInfo("Q", 2, R.string.currency_quetzal_singular,
                R.string.currency_quetzal_plural, R.string.currency_centavo_singular, R.string.currency_centavo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.HNL] = AndesCurrencyInfo("L", 0, R.string.currency_lempira_singular,
                R.string.currency_lempira_plural, R.string.currency_centavo_singular, R.string.currency_centavo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.NIO] = AndesCurrencyInfo("C", 0, R.string.currency_cordoba_singular,
                R.string.currency_cordoba_plural, R.string.currency_centavo_singular, R.string.currency_centavo_plural, null, false)
        currencies[AndesMoneyAmountCurrency.CUC] = AndesCurrencyInfo("$", 2, R.string.currency_cubano_singular,
                R.string.currency_cubano_plural, R.string.currency_centavo_singular, R.string.currency_centavo_plural, null, false)

        currencies[AndesMoneyAmountCurrency.BTC] = AndesCurrencyInfo("BTC", 8, null,
            null, null, R.string.currency_btc, R.drawable.ic_logo_btc, true)
        currencies[AndesMoneyAmountCurrency.ETH] = AndesCurrencyInfo("ETH", 8, null,
            null, null, R.string.currency_etc, R.drawable.ic_logo_eth, true)
        currencies[AndesMoneyAmountCurrency.MCN] = AndesCurrencyInfo("MCN", 8, null,
            null, null, R.string.currency_meli, R.drawable.ic_logo_meli, true)
        currencies[AndesMoneyAmountCurrency.USDP] = AndesCurrencyInfo("USDP", 8, null,
            null, null, R.string.currency_usdp, R.drawable.ic_logo_usdp, true)

        return currencies
    }

    private fun mockCountries(): HashMap<AndesCountry, AndesCountryInfo> {
        val countries = hashMapOf<AndesCountry, AndesCountryInfo>()
        countries[AndesCountry.AR] = AndesCountryInfo(',', '.')
        countries[AndesCountry.BR] = AndesCountryInfo(',', '.')
        countries[AndesCountry.CL] = AndesCountryInfo(',', '.')
        countries[AndesCountry.CO] = AndesCountryInfo(',', '.')
        countries[AndesCountry.MX] = AndesCountryInfo('.', ',')
        countries[AndesCountry.CR] = AndesCountryInfo('.', ',')
        countries[AndesCountry.PE] = AndesCountryInfo('.', ',')
        countries[AndesCountry.EC] = AndesCountryInfo(',', '.')
        countries[AndesCountry.PA] = AndesCountryInfo('.', ',')
        countries[AndesCountry.DO] = AndesCountryInfo('.', ',')
        countries[AndesCountry.UY] = AndesCountryInfo(',', '.')
        countries[AndesCountry.VE] = AndesCountryInfo(',', '.')
        countries[AndesCountry.BO] = AndesCountryInfo(',', '.')
        countries[AndesCountry.PY] = AndesCountryInfo(',', '.')
        countries[AndesCountry.GT] = AndesCountryInfo('.', ',')
        countries[AndesCountry.HN] = AndesCountryInfo('.', ',')
        countries[AndesCountry.NI] = AndesCountryInfo(',', '.')
        countries[AndesCountry.SV] = AndesCountryInfo('.', ',')
        countries[AndesCountry.PR] = AndesCountryInfo(',', '.')
        countries[AndesCountry.CU] = AndesCountryInfo(',', '.')
        return countries
    }

    private fun mockCurrentCountry(): AndesCountry {
        return AndesCountry.AR
    }
}
