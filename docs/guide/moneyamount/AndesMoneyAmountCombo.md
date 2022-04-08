# AndesMoneyAmountCombo

AndesMoneyAmountCombo is used to refer to the value of an item or to a specific amount of money.
[See Andes UI component in frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/components/money-amount)

```kotlin
class AndesMoneyAmountCombo : androidx.appcompat.widget.AppCompatTextView
```

Basic Sample Programatically

```kotlin
AndesMoneyAmountCombo(
    context = this,
    amount = 100.0,
    currency = AndesMoneyAmountCurrency.ARS
)
```

Basic Sample XML

```xml
 <com.mercadolibre.android.andesui.moneyamount.AndesMoneyAmountCombo
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:AndesMoneyAmountCombo="1234.56"
        app:andesMoneyAmountCurrency="EUR"
        app:andesMoneyAmountSize="size_20"
        app:andesMoneyAmountStyle="normal"
        app:andesMoneyAmountType="negative"
        app:andesShowZerosDecimal="true" />

```

<br/>

## XML Attributes
| Property | Summary |
| -------- | ------- |
| app:andesMoneyAmountComboSize | Text size: **size_24**, **size_36** |
| app:andesMoneyAmountComboCurrency | Represents the currency of the amounts that will be shown: **BRL**, **UYU**, **CLP**, **CLF**, **MXN**, **DOP**, **PAB**, **COP**, **VEF**, **EUR**, **PEN**, **CRC**, **ARS**, **USD**, **BOB**, **GTQ**, **PYG**, **HNL**, **NIO**, **CUC**, **VES** |
| app:andesMoneyAmountComboCountry | Represents the country decimal and thousand separator style: **AR**, **BR**, **CL**, **CO**, **MX**, **CR**, **PE**, **EC**, **PA**, **DO**, **UY**, **VE**, **BO**, **PY**, **GT**, **HN**, **NI**, **SV**, **PR**, **CU** |
| app:andesMoneyAmountComboAmount | Amount number to be shown (float) |
| app:andesMoneyAmountComboPreviousAmount | Represents amount without the discount  |
| app:andesMoneyAmountComboDiscount | Discount amount, must be a number between 0 and 100 |

<br/>

## Constructors
| Summary |
| --- |
| AndesMoneyAmountCombo(context: Context, attrs: AttributeSet?)  |
| AndesMoneyAmountCombo(context: Context, amount: Double, currency: AndesMoneyAmountCurrency, country: AndesCountry, previousAmount: Double, discount: Int, size: AndesMoneyAmountComboSize) |

<br/>

##### AndesMoneyAmountCombo(context: Context, amount: Double, currency: AndesMoneyAmountCurrency, country: AndesCountry, previousAmount: Double, discount: Int, size: AndesMoneyAmountComboSize)
| Parameter | Description |
| -------- | ------- |
| context | **Context** |
| amount | **Double**: amount number to be shown. |
| currency | **[AndesMoneyAmountCurrency](/moneyamount/AndesMoneyAmount?id=andesmoneyamountcurrency)**: represents the currency of the amounts that will be shown. |
| country | **[AndesCountry](/moneyamount/AndesMoneyAmount?id=andescountry)**: represents the country decimal and thousand separator style. |
| previousAmount | **Double**: represents amount without applying the discount. |
| discount | **Int**: discount amount, must be a number between 0 and 100. |
| size | **[AndesMoneyAmountComboSize](#andesmoneyamountcombosize)**: Decimal part style. |

<br/>

## Properties
| Property | Summary |
| -------- | ------- |
| amount: Double | **get():** retrieves number shown. <br/> **set(value: Double):** updates amount to be shown. |
| previousAmount: Double | **get():** retrieves amount without applying discount. <br/> **set(value: Double):** updates amount without applying discount. |
| discount: Int | **get():** retrieves the discount value.  <br/> **set(value: Int):** updates the discount value (must be between 0 and 100). |
| size: [AndesMoneyAmountComboSize](#andesmoneyamountcombosize) | **get():** retrieves combo text sizes. <br/> **set(value: AndesMoneyAmountSize):** updates combo text sizes. |
| currency: [AndesMoneyAmountCurrency](/moneyamount/AndesMoneyAmount?id=andesmoneyamountcurrency) | **get():** retrieves the currency of the amount that will be shown. <br/> **set(value: AndesMoneyAmountCurrency):** updates the currency of the amount that will be shown. |
| country: [AndesCountry](/moneyamount/AndesMoneyAmount?id=andescountry) | **get():** retrieves the country decimal and thousand separator style. <br/> **set(value: AndesCountry):** updates country to change decimal and thousand separator style. |

<br/>

## Related Classes

### AndesMoneyAmountComboSize
Defines the text sizes [AndesMoneyAmountCombo](#andesmoneyamountcombo) can take.
```kotlin
enum class AndesMoneyAmountComboSize
```
| Enum Values | Description |
| ----------- | ----------- |
| SIZE_24 | 24sp amount size relation |
| SIZE_36 | 36sp amount size telation |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesMoneyAmountComboSize | **fromString(value: String)**<br/> Retrieves an AndesMoneyAmountComboSize that matches the string value |

<br/>

## Screenshots
<img src="resources/moneyamount/moneyAmountExample.png" width="300">