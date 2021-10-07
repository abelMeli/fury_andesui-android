# AndesMoneyAmount

AndesMoneyAmountDiscount is used to refer to the value of a discount.
[See Andes UI component in frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/components/money-amount)

```kotlin
class AndesMoneyAmountDiscount : androidx.appcompat.widget.AppCompatTextView
```

Basic Sample Programatically

```kotlin
AndesMoneyAmount(
    context = this,
    discount = 10,
    size = AndesMoneyAmountSize.SIZE_24
)
```

Basic Sample XML

```xml
<com.mercadolibre.android.andesui.moneyamount.AndesMoneyAmountDiscount
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:andesMoneyAmountDiscountSize="size_24"
    app:andesMoneyDiscount="10" />

```

<br/>

## XML Attributes
| Property | Summary |
| -------- | ------- |
| app:andesMoneyAmountDiscountSize | Text size: **size_12**, **size_14**, **size_16**, **size_18**, **size_20**, **size_24**, **size_28**, **size_32**, **size_36**, **size_40**, **size_44**, **size_48**, **size_52**, **size_56**, **size_60** |
| app:andesMoneyDiscount | Represents discount percentage. It must be an integer between 0 and 100. |

<br/>

## Constructors
| Summary |
| --- |
| AndesMoneyAmount(context: Context, attrs: AttributeSet?)  |
| AndesMoneyAmount(context: Context, discount: Int, size: AndesMoneyAmountSize) |

<br/>

##### AndesMoneyAmount(context: Context, discount: Int, size: AndesMoneyAmountSize)
| Parameter | Description |
| -------- | ------- |
| context | **Context** |
| discount | **Int**: amount to display. It must be a number between 0 and 100. |
| size | **[AndesMoneyAmountSize](#andesmoneyamountsize)**: discount text size. |

<br/>

## Properties
| Property | Summary |
| -------- | ------- |
| discount: Int | **get():** retrieves discount value.. <br/> **set(value: Double):** updates discount number displayed. |
| size: [AndesMoneyAmountSize](#andesmoneyamountsize) | **get():** retrieves text size. <br/> **set(value: AndesMoneyAmountSize):** updates text size. |

<br/>

## Related Classes

### AndesMoneyAmountSize
Defines the text sizes [AndesMoneyAmount](#andesmoneyamount) can take.
```kotlin
enum class AndesMoneyAmountSize
```
| Enum Values | Description |
| ----------- | ----------- |
| SIZE_12 | 12sp component size |
| SIZE_14 | 14sp component size |
| SIZE_16 | 16sp component size |
| SIZE_18 | 18sp component size |
| SIZE_20 | 20sp component size |
| SIZE_24 | 24sp component size |
| SIZE_28 | 28sp component size |
| SIZE_32 | 32sp component size |
| SIZE_36 | 36sp component size |
| SIZE_40 | 40sp component size |
| SIZE_44 | 44sp component size |
| SIZE_48 | 48sp component size |
| SIZE_52 | 52sp component size |
| SIZE_56 | 56sp component size |
| SIZE_60 | 60sp component size |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesMoneyAmountSize | **fromString(value: String)**<br/> Retrieves an AndesMoneyAmountSize that matches the string value |

<br/>

## Screenshots
<img src="../resources/moneyamount/moneyAmountExample.png" width="300">