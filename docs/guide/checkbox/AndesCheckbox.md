# AndesCheckbox

AndesCheckbox allow you to make multiple selections from a list of options, unlike radio buttons that only allow you to choose a single option. 
[See Andes UI component in frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/components/checkbox)

```kotlin
class AndesCheckbox : ConstraintLayout
```

Basic Sample Programatically

```kotlin
AndesCheckbox(context)
```
Basic Sample XML

```xml
<com.mercadolibre.android.andesui.checkbox.AndesCheckbox
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```
<br/>

## XML Attributes
| Property | Summary |
| -------- | ------- |
| app:andesCheckboxText | Text to display with the checkbox. It must be a string resource. |
| app:andesCheckboxAlign | Set the checkbox position at the left or right of the **andesCheckboxText**. Possible values: **left**, **right** |
| app:andesCheckboxStatus | Set checkbox initial status: **selected**, **unselected**, **undefined** |
| app:andesCheckboxType | Set a type style for the checkbox: **idle**, **error**, **disabled** |
| app:andesCheckboxTitleNumberOfLines | Set a quantity for the desired number of lines for the companion text. |

<br/>

## Constructors
| Summary |
| --- |
| AndesCheckbox(context: Context, attrs: AttributeSet?) |
| [AndesCheckbox](#andescheckboxcontext-context-text-string-align-andescheckboxalign-status-andescheckboxstatus-type-andescheckboxtype)(context: Context, text: String, align: [AndesCheckboxAlign](#andescheckboxalign), status: [AndesCheckboxStatus](#andescheckboxstatus), type: [AndesCheckboxType](#andescheckboxtype))|

<br/>

##### AndesCheckbox(context: Context, text: String, align: AndesCheckboxAlign, status: AndesCheckboxStatus, type: AndesCheckboxType)
| Parameter | Description |
| -------- | ------- |
| context | **Context**|
| text | **String**: checkbox companion text. |
| align | **[AndesCheckboxAlign](#andescheckboxalign)**: set the checkbox position at the left or right of the text. Default alignment is **LEFT** |
| status | **[AndesCheckboxStatus](#andescheckboxstatus)**: set checkbox initial status. Default status is **UNSELECTED** |
| type | **[AndesCheckboxType](#andescheckboxtype)**: set the checkbox type style. Default type is **IDLE** |

<br/>

## Properties
| Property | Summary |
| -------- | ------- |
| text: String? | **get():** retrieves checkbox companion text displayed. <br/> **set(value: String?):** updates component text displayed. |
| align: AndesCheckboxAlign | **get():** retrieves checkbox position. <br/> **set(value: AndesCheckboxAlign):** updates checkbox position at the left or right of the text. |
| status: AndesCheckboxStatus | **get():** retrieves checkbox current status. <br/> **set(value: AndesCheckboxStatus):** updates checkbox status. |
| type: AndesCheckboxType | **get():** retrieves checkbox type. <br/> **set(value: AndesCheckboxType):** updates component type. |
| titleNumberOfLines: Int | **get():** retrieves number of lines of the companion textView. <br/> **set(value: Int):** updates the amount of lines the companion text can show. |
| bodyLinks: AndesBodyLinks? | **get():** retrieves the available AndesBodyLinks. <br/> **set(value: AndesCheckboxType):** updates the companion text with the links provided from the AndesBodyLinks indexes. |

### Links in companion text
To have a better understanding of the links in text feature, we recommend checking the [AndesBodyLinks](https://github.com/mercadolibre/fury_andesui-android/blob/master/docs/guide/message/AndesMessage.md#andesbodylinks) docs.

<br/>

## Related Classes

### AndesCheckboxAlign
Defines the possible positions that the checkbox can take.
```kotlin
enum class AndesCheckboxAlign
```
| Enum Values | Description |
| ----------- | ----------- |
| LEFT | Aligns checkbox to the left of the displayed text<br/><img src="resources/checkbox/checkedCheckboxExample.png" height="24"/> |
| RIGHT | Aligns checkbox to the right of the displayed text<br/><img src="resources/checkbox/checkboxRightExample.png" height="24"/> |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesCheckboxAlign | **fromString(value: String)**<br/> Retrieves an AndesCheckboxAlign that matches the string value |

<br/>

### AndesCheckboxStatus
Defines the possible status that the checkbox can take.
```kotlin
enum class AndesCheckboxStatus
```
| Enum Values | Description |
| ----------- | ----------- |
| SELECTED | Checkbox filled with a check sign<br/><img src="resources/checkbox/checkedCheckboxExample.png" height="24"/> |
| UNSELECTED | Checkbox unfilled<br/><img src="resources/checkbox/uncheckedCheckboxExample.png" height="24"/> |
| UNDEFINED | Checkvox filled with a minus sign<br/><img src="resources/checkbox/undefinedCheckboxExample.png" height="24"/> |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesCheckboxStatus | **fromString(value: String)**<br/> Retrieves an AndesCheckboxStatus that matches the string value |

<br/>

### AndesCheckboxType
Defines the possible type styles that the checkbox can take.
```kotlin
enum class AndesCheckboxType
```
| Enum Values | Description |
| ----------- | ----------- |
| IDLE | Default status of the checkbox<br/><img src="resources/checkbox/uncheckedCheckboxExample.png" height="24"/> |
| DISABLED | Checkbox can not be modified<br/><img src="resources/checkbox/checkboxDisabledExample.png" height="24"/> |
| ERROR | Checkbox with red color until user selects it<br/><img src="resources/checkbox/checkboxErrorExample.png" height="24"/> |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesCheckboxType | **fromString(value: String)**<br/> Retrieves an AndesCheckboxType that matches the string value |

<br/>

## Screenshots
<img src="https://user-images.githubusercontent.com/58984116/111320751-10a4ca80-8646-11eb-92b7-b15f125bd444.png" width="300">