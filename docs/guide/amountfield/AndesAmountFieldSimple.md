# AndesAmountFieldSimple

This component allows the user to enter and edit money and percentage values in a styled field.

[See the component definition in Frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/components/amount-field)

[See the component specs in Figma](https://www.figma.com/file/ma8IQUYi9IS8zc8C0rAzEB/Components-specifications?node-id=4050%3A476413)

```kotlin
class AndesAmountFieldSimple : ConstraintLayout
```

Basic Sample Programatically

```kotlin
AndesAmountFieldSimple(context)
```
Basic Sample XML

```xml
<com.mercadolibre.android.andesui.amountfield.AndesAmountFieldSimple
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```
<br/>

## XML Attributes
| Property | Summary |
| -------- | ------- |
| app:andesAmountFieldState | Value that defines the component state (idle or error) |
| app:andesAmountFieldEntryMode | Value that defines how the input will work. With this property we choose if the input will be from the integer or the decimal part |
| app:andesAmountFieldEntryType | Value that defines if the component will take amount or percentage values |
| app:andesAmountFieldNumberOfDecimals | Value that defines if the component will have a custom number of decimal places |
| app:andesAmountFieldCurrency | Value that defines the currency to show when the component is used with type money |
| app:andesAmountFieldShowCurrencyAsIsoValue | Value that defines if the currency component should show the currency symbol or the ISO value |
| app:andesAmountFieldCountry | Value that defines the country needed to determine decimal/thousand separators for the field |
| app:andesAmountFieldInitialValue | Value that defines if the component will have an initial value at construction |
| app:andesAmountFieldHelperText | Value that defines the helper text to show at the bottom of the component |
| app:andesAmountFieldSuffixText | Value that defines the suffix text to add to the right of the component |
| app:andesAmountFieldSuffixA11yText | Value that defines the text the Talkback should read instead of the suffix value |
| app:andesAmountFieldMaxValue | Value that defines if the component will have a max numeric value to display |
| app:andesAmountFieldIsEditable | Value that defines if the component is enabled |

<br/>

## Constructors
| Summary |
| --- |
| AndesAmountFieldSimple(context: Context, attrs: AttributeSet?) |
| [AndesAmountFieldSimple](#context-context-state-andesamountfieldstate-entrymode-andesamountfieldentrymode-entrytype-andesamountfieldentrytype-currency-andesmoneyamountcurrency-showcurrencyasisovalue-boolean-country-andescountry-numberofdecimals-int-initialvalue-string-helpertext-charsequence-suffix-charsequence-suffixa11ytext-string-maxvalue-string)(context: Context, state: [AndesAmountFieldState](#andesamountfieldstate), entryMode: [AndesAmountFieldEntryMode](#andesamountfieldentrymode), entryType: [AndesAmountFieldEntryType](#andesamountfieldentrytype), currency: [AndesMoneyAmountCurrency](https://github.com/mercadolibre/fury_andesui-android/blob/master/docs/guide/moneyamount/AndesMoneyAmount.md#andesmoneyamountcurrency), showCurrencyAsIsoValue: Boolean, country: [AndesCountry](https://github.com/mercadolibre/fury_andesui-android/blob/master/docs/guide/moneyamount/AndesMoneyAmount.md#andescountry), numberOfDecimals: Int?, initialValue: String?, helperText: CharSequence?, suffix: CharSequence?, suffixA11yText: String?, maxValue: String?, isEditable: Boolean)|

<br/>

##### AndesAmountFieldSimple(context: Context, state: AndesAmountFieldState, entryMode: AndesAmountFieldEntryMode, entryType: AndesAmountFieldEntryType, currency: AndesMoneyAmountCurrency, showCurrencyAsIsoValue: Boolean, country: AndesCountry, numberOfDecimals: Int?, initialValue: String?, helperText: CharSequence?, suffix: CharSequence?, suffixA11yText: String?, maxValue: String?, isEditable: Boolean)
| Parameter | Description |
| -------- | ------- |
| context | **Context**|
| state | **[AndesAmountFieldState](#andesamountfieldstate)**: component initial state. Default value is **AndesAmountFieldState.Idle** |
| entryMode | **[AndesAmountFieldEntryMode](#andesamountfieldentrymode)**: component entry mode. Default value is **null** |
| entryType | **[AndesAmountFieldEntryType](#andesamountfieldentrytype)**: component entry type. Default value is **AndesAmountFieldEntryType.MONEY** |
| currency | **[AndesMoneyAmountCurrency](https://github.com/mercadolibre/fury_andesui-android/blob/master/docs/guide/moneyamount/AndesMoneyAmount.md#andesmoneyamountcurrency)**: component currency to display. Default value is **AndesMoneyAmountCurrency.ARS** |
| showCurrencyAsIsoValue | **Boolean**: Value that defines if the currency component should show the currency symbol or the ISO value. Default value is **false** |
| country | **[AndesCountry](https://github.com/mercadolibre/fury_andesui-android/blob/master/docs/guide/moneyamount/AndesMoneyAmount.md#andescountry)**: country to determine. Default value is **AndesCountry.AR** |
| numberOfDecimals | **Int?**: number of decimal places the component can take. Default value is **null** |
| initialValue | **String?**: component initial value. Default value is **null** |
| helperText | **CharSequence?**: helper text to show at the bottom of the component. Default value is **null** |
| suffix | **CharSequence?**: suffix text to show at the right of the component. Default value is **null** |
| suffixA11yText | **String?**: Value that defines the text the Talkback should read instead of the suffix value. Default value is **null** |
| maxValue | **String?**: max numeric value the component can take. Default value is **null**. To see more info about the data type, see [here](#about-the-amount-data-types) |
| isEditable | **Boolean**: value that defines if component is enabled. Default value is **True** |

<br/>

## Properties
| Property | Summary |
| -------- | ------- |
| onTextPasteListener: OnTextPasteListener | **get():** retrieves the corresponding onpaste listener, or null if not set. <br/> **set(value: OnTextPasteListener):** adds new onPaste listener |
| onTextChangeListener: OnTextChangeListener | **get():** retrieves the corresponding onchange listener, or null if not set. <br/> **set(value: OnTextChangeListener):** adds new onChange listener |
| value: String? | **get():** returns current component value formatted as number, only with comma decimal separator. <br/> **set(value: String?):** sets a new value to the component. When passing **null**, clears the component. Throws NumberFormatException when passing a string that is not formatted as double. To see more info about the data type, see [here](#about-the-amount-data-types) |
| state: AndesAmountFieldState | **get():** retrieves the corresponding state of the component. <br/> **set(value: AndesAmountFieldState):** updates component state |
| entryMode: AndesAmountFieldEntryMode? | **get():** retrieves the corresponding entry mode of the component, or null if not set. <br/> **set(value: AndesAmountFieldEntryMode):** updates component with new entry mode |
| entryType: AndesAmountFieldEntryType | **get():** retrieves the corresponding entry type of the component. <br/> **set(value: AndesAmountFieldEntryType):** updates component with new entry type |
| currency: AndesMoneyAmountCurrency | **get():** retrieves the corresponding currency of the component. <br/> **set(value: AndesMoneyAmountCurrency):** updates component currency with new value. According to the selected value, the component will modify the currency symbol and the default decimal places for the corresponding currency |
| showCurrencyAsIsoValue: Boolean | **get():** retrieves wether the currency shown is the ISO value or not. <br/> **set(value: Boolean):** updates currency component with new value. According to the selected value, the component will show the currency symbol or the ISO value. Example: for the brazilian Real, when passing true the component will show "BRL", and when passing false, will show "R$" |
| country: AndesCountry | **get():** retrieves the corresponding country of the component. <br/> **set(value: AndesCountry):** updates component country with new value. According to the selected value, the component will set different thousands/decimal separators, and a default entry mode for the corresponding country |
| helperText: CharSequence? | **get():** retrieves the previously set helper text, or null if not set. <br/> **set(value: CharSequence?):** adds the new text value to the helper component, at the bottom of the view, or hides the helper component if the passed value is null |
| suffixText: CharSequence? | **get():** retrieves the previously set suffix text, or null if not set. <br/> **set(value: CharSequence?):** adds the new text value to the suffix component, at the right of the view, or hides the suffix component if the passed value is null |
| suffixA11yText: String? | **get():** retrieves the text to be read by the Talkback instead of the suffix text, or null if not set. <br/> **set(value: String?):** keeps value as new text to read, or marks the suffix text as value to be read when passing null |
| maxValue: | **get():** returns the max value the component can take, or null if not set. <br/> **set(value: String?):** updates component max value, or removes max limit if the passed value is null. Throws NumberFormatException when passing a string that is not formatted as double. To see more info about the data type, see [here](#about-the-amount-data-types) |
| numberOfDecimals: Int? | **get():** returns the decimal places the component can show, or null if not set. <br/> **set(value: Int?):** updates decimal places the component can take, or sets the default value for the selected currency if the passed value is null |
| isEditable: Boolean | **get():** returns if component is enabled. <br/> **set(value: Boolean):** updates if component is enabled/disabled, the component can show it enabled/disabled|
| focus: Boolean | **get():** returns if component has focus. <br/> **set(value: Boolean):** updates focus component|

<br/>

## Related Classes

### OnTextPasteListener
Interface used to detect when a paste event occurs over the component.
```kotlin
interface OnTextPasteListener {
    fun onTextPaste(pastedText: String?)
}
```

### OnTextChangeListener
Interface used to detect when the value of the component changes.
```kotlin
interface OnTextChangeListener {
    fun onTextChange(newText: String?)
}
```

### AndesAmountFieldState
State value the component can take. This states manage different visual attributes, such as the helper text color, the
presence/absence of the warning icon in the helper component, the error animations, among others.
```kotlin
sealed class AndesAmountFieldState
```
| Possible Values | Description |
| ----------- | ----------- |
| Idle | Idle state. Default component state |
| Error | Error state. The component does a "shake" animation when selecting this value. Also, the helper text color is changed to feedback error color (red) |

<br/>

### Sample gifs

| Idle state | Error state |
| - | - |
| ![idle_state](https://user-images.githubusercontent.com/81258246/160486595-68f47234-ed62-43ef-b72f-6aa483b3c0c1.gif) | ![error_state](https://user-images.githubusercontent.com/81258246/160486590-84f8c35a-e7e6-4e32-bca2-c2fae87af2ca.gif) |

<br/>

### AndesAmountFieldEntryMode
This values allow the dev to choose the different entry modes the component can take. When choosing
the [INT] option, the writing will occur from left to right, starting from the integer part of the
value, meanwhile the [DECIMAL] option will configure the writing from right to left, starting from
the decimal values.
```kotlin
enum class AndesAmountFieldEntryMode
```
| Possible Values | Description |
| ----------- | ----------- |
| INT | The writing will occur from left to right. At writing, using the decimal part of the component will be optional. |
| DECIMAL | The writing will occur from right to left. Also, at writing, the decimal places (when set) will be always displayed. |

<br/>

### Sample gifs

| INT entry mode | DECIMAL entry mode |
| - | - |
| ![int_entrymode](https://user-images.githubusercontent.com/81258246/160486599-d49f3fad-9f11-4442-9316-f4f121cfacd7.gif) | ![decimal_entrymode](https://user-images.githubusercontent.com/81258246/160486565-dc7a1189-6c48-49d3-8b57-06f4e6f8e9e9.gif) |

<br/>

### AndesAmountFieldEntryType
This enum contains the variants of entry type for the component. According to the selected value, the component will show/hide
the currency component, and change the value for the suffix
```kotlin
enum class AndesAndesAmountFieldEntryType
```
| Possible Values | Description |
| ----------- | ----------- |
| MONEY | The component will display amount values. The currency symbol is always shown, and the suffix text is editable. |
| PERCENTAGE | The component will display percentage values. The currency symbol is always hidden, ant the suffix text is set to be always the percentage symbol ("%") |

<br/>

### Sample gifs

| MONEY entry type | PERCENTAGE entry type |
| - | - |
| ![money_entrytype](https://user-images.githubusercontent.com/81258246/160486586-431b60d5-c80d-464b-81ad-2f94f5ed1890.gif) | ![percentage_entrytype](https://user-images.githubusercontent.com/81258246/160486577-707b649e-6b40-4dce-9db2-3ab7888a89c6.gif) |

<br/>

### AndesMoneyAmountCurrency
This enum contains data for the money version of the component, such as the decimal places, the currency symbol, the currency accessibility
texts and the icon (if present), among others.
```kotlin
enum class AndesMoneyAmountCurrency
```
To see more info, click [here](https://github.com/mercadolibre/fury_andesui-android/blob/master/docs/guide/moneyamount/AndesMoneyAmount.md#andesmoneyamountcurrency)

### AndesCountry
This enum contains data for the thousand/decimal places the component should take by default. Also, the country has a default value
for the entry mode. Both values can be overwritten if needed, but when not set, the component will take the values from here.
```kotlin
enum class AndesCountry
```

To see more info, click [here](https://github.com/mercadolibre/fury_andesui-android/blob/master/docs/guide/moneyamount/AndesMoneyAmount.md#andescountry)
<br/>

## Functions
| Return type | Method |
| -------- | ------- |
| Unit | **closeKeyboard()**<br/> Closes the soft keyboard. Note that this method does not interfere 
with the internal edit text focus status.  |

## About the amount data types
You may wonder why in this component we chose to handle all the numeric values as strings instead of, for example, doubles or floats.
This is because, by the nature of the component, we need to parse from a regular number value ("1234567.89") to an amount-formatted value
("1.234.567,89") many times. Also, we need to be able to compare two numbers, so we need a way to reliably transform from string
to a number-type class, and vice-versa.
Because of the way the rounding and parsing between strings and numbers works in Java, from certain decimal places onwards the parsing from
double (or float) to string and vice-versa starts to malfunction.
The only reliable number type we have to do this kind of operations is the BigDecimal type. The parsing from string to number is easy since
this class has a constructor that takes only the string, and the parsing to string (with regular or amount format) is relatively easy with
a helper class like DecimalFormat.

This is why we handle the `value` and `maxValue` properties as strings representations of numbers: no thousand separators, and only
with a dot decimal separator if needed (a value like "12345" without decimal places still works).

## About the accessibility label
As you may see, this component does not have a label that brings context to sight-impaired users when
the focus is placed over the internal `editText` component.
From UX, the recommendation is to add a headline in the screen this component is placed, but we
need a way to link this text with the text field.
To improve this experience for talkback users, from version 5.13.0 onwards, the [`AndesTextView`](https://furydocs.io/andesui-android/latest/guide/#/textview/AndesTextView?id=the-androidlabelfor-property)
will be able to be set as an accessibility label for this component. This will be particularly useful when the use-case for the amountField is to be focused as soon as the screen is opened.
We show examples of this behavior before and after this feature.

| Component without a11y label (before) | Component with a11y label (now) |
| - | - |
| ![no_a11y_label](https://user-images.githubusercontent.com/81258246/187286636-21005077-bd92-4bab-a914-4253a44a34b8.gif) | ![with_label_for](https://user-images.githubusercontent.com/81258246/187286619-8c8cf692-1831-446a-821c-3733560b87dc.gif) |
