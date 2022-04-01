# AndesTextView

The AndesTextView is a component that inherits from the OS counterpart (intends to be an immediate replacement for the native TextView)
[See Andes UI component in frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/foundations/typography)

```kotlin
class AndesTextView : AppCompatTextView
```

Basic Sample Programatically

```kotlin
AndesTextView(context)
```
Basic Sample XML

```xml
<com.mercadolibre.android.andesui.textview.AndesTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Some text" />
```
<br/>

## XML Attributes
| Property | Summary |
| -------- | ------- |
| app:andesTextViewTextColor | Value that defines the component text color |
| app:andesTextViewStyle | Value that defines the text size, line height and text style (regular or semibold) |
| app:andesTextViewIsLinkColorInverted | Value that defines if the link will have the color inverted (white) or regular accent color |

<br/>

## Constructors
| Summary |
| --- |
| AndesTextView(context: Context, attrs: AttributeSet?) |
| [AndesTextView](#andestextviewcontext-context-color-andestextviewcolor-style-andestextviewstyle)(context: Context, color: [AndesTextViewColor](#andestextviewcolor), style: [AndesTextViewStyle](#andestextviewstyle))|

<br/>

##### AndesTextView(context: Context, color: AndesTextViewColor, style: AndesTextViewStyle)
| Parameter | Description |
| -------- | ------- |
| context | **Context**|
| color | **[AndesTextViewColor](#andestextviewcolor)**: component text color. Default value is **AndesTextViewColor.Primary** |
| style | **[AndesTextViewStyle](#andestextviewstyle)**: value that sets text size, line height and text style. Default value is **AndesTextViewStyle.BODY_S** |

<br/>

## Properties
| Property | Summary |
| -------- | ------- |
| style: AndesTextViewStyle | **get():** retrieves the corresponding enum for the style value. <br/> **set(value: AndesTextViewStyle):** updates component text size, line height and style (regular/semibold). |
| bodyLinks: [AndesBodyLinks](https://github.com/mercadolibre/fury_andesui-android/blob/master/docs/guide/message/AndesMessage.md#andesbodylinks)? | **get():** retrieves the previously set bodyLinks values, or **null** if they were not set. <br/> **set(value: AndesBodyLinks?):** adds clickable segments in the text according to the ranges passed in the object. |
| bodyBolds: [AndesBodyBolds](#andesbodybolds)? | **get():** retrieves the previously set bodyBolds values, or **null** if they were not set. <br/> **set(value: AndesBodyBolds?):** adds bold style segments in the text according to the ranges passed in the object. |
| isLinkColorInverted: Boolean | **get():** returns **true** if the links in the text should be white colored and underlined, and **false** if the links should have the accent color. <br/> **set(value: Boolean):** updates link text color and underline value. |
| accessibilityText: String | **get():** retrieves the accessibility text corresponding to the **text** attribute|

<br/>

## Methods
| Method | Description |
| -- | -- |
| setTextColor(color: [AndesTextViewColor](#andestextviewcolor))| Set a new color value to the text component from the Andes color enum. |
| append(moneyAmount: AndesMoneyAmount, color: Int?)| Appends a money amount text to this text view with the given color int. |
| append(moneyAmount: AndesMoneyAmount, color: AndesColor)| Appends a money amount text to this text view with the given Andes Color. |
| updateColor(substring: String, color: Int)| Sets the given int color to a substring in the text view text. |
| updateColor(substring: String, color: AndesColor)| Sets the given Andes Color to a substring in the text view text. |
| updateColor(range: IntRange, color: Int)| Sets the given int Color to a range in the text view text. |
| updateColor(substringRange: IntRange, color: AndesColor)| Sets the given Andes Color to a range in the text view text. |
| clear()| Clears the text and the accessibilityText of the text view. |
| append(text: String, color: Int?)| Appends a text at the end of the text view text with the given int color. |
| append(text: String, color: AndesColor)| Appends a text at the end of the text view text with the given Andes color. |

<br/>

## Related Classes

### AndesTextViewColor
Defines the possible colors that the text can take.
```kotlin
sealed class AndesTextViewColor
```
| Possible Values | Description |
| ----------- | ----------- |
| Primary | Primary text color (gray_900) |
| Secondary | Secondary text color (gray_550) |
| Disabled | Disabled text color (gray_250) |
| Inverted | Primary text color (white) |
| Negative | Primary text color (red_500) |
| Caution | Primary text color (orange_500) |
| Positive | Primary text color (green_500) |

<br/>

| Sample image |
| - |
| ![Screen Shot 2021-11-01 at 5 37 25 PM](https://user-images.githubusercontent.com/81258246/139738636-afe74f97-5346-447c-9fa7-68263f6fc6bf.png) |


<br/>

### AndesTextViewStyle
Defines the possible styles that the text can take.
```kotlin
sealed class AndesTextViewStyle
```
| Possible Values | Description |
| ----------- | ----------- |
| TitleXl | Title extra large size (32sp), semibold style |
| TitleL | Title large size (28sp), semibold style |
| TitleM | Title medium size (24sp), semibold style |
| TitleS | Title small size (20sp), semibold style |
| TitleXs | Title extra small size (18sp), semibold style |
| BodyL | Body large size (18sp), regular style |
| BodyM | Body madium size (16sp), regular style |
| BodyS | Body small size (14sp), regular style |
| BodyXs | Body extra small size (12sp), regular style |

Since this styles do not cover all the style combinations the dev may need, both regular textView setters for the size
(`android:textSize`) and the style (`android:textStyle`) are available to use in combination with all the
AndesTextViewStyle values. For example, to get a text with size of `14sp` and `bold` style, we can setup the
component with `app:andesTextViewStyle=BodyS` and `android:textStyle=bold`.

<br/>

| Sample image |
| - |
| ![Screen Shot 2021-11-01 at 5 35 12 PM](https://user-images.githubusercontent.com/81258246/139738428-330bfb81-3536-49a6-9616-4c78216358fe.png) |

<br/>

### AndesBodyBolds
data class that contains the list of indexes for the bold style segments in text
```kotlin
data class AndesBodyBolds
```
| Property | Description |
| ----------- | ----------- |
| boldSegments: List&lt;AndesBodyBold> | List of segments that the text will take as bold segments |

### AndesBodyBold
data class that represent the bold style segment in text. It has a start index and an end index.
```kotlin
data class AndesBodyBold
```
| Property | Description |
| ----------- | ----------- |
| startIndex: Int | start index value of the bold segment |
| endIndex: Int | end index value of the bold segment |

<br/>

| Ranges Sample image (link + bold) |
| - |
| ![Screen Shot 2021-11-01 at 5 42 07 PM](https://user-images.githubusercontent.com/81258246/139739241-d23bc6af-5b80-4d76-83a1-941c56738056.png) |


## Screenshots
| Accessibility | No Accessibility |
| ----------- | ----------- |
| ![ACCESSIBILITY TEXTVIEW 2](https://user-images.githubusercontent.com/70920226/158864407-112dbce2-4168-4458-b30d-9121db77b990.gif) | ![TEXT VIEW 2](https://user-images.githubusercontent.com/70920226/158864446-c703818f-06fd-4fd6-b621-8714cdc0cf53.gif) |
