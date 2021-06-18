# AndesSwitch

AndesSwitch allows the user to immediately turn ON or OFF a preference or functionality.
[See Andes UI component in frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/components/switch)

```kotlin
class AndesSwitch : ConstraintLayout
```

Basic Sample Programatically

```kotlin
AndesSwitch(context)
```
Basic Sample XML

```xml
<com.mercadolibre.android.andesui.switch.AndesSwitch
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```
<br/>

## XML Attributes
| Property | Summary |
| -------- | ------- |
| app:andesSwitchText | Text to display with the switch. It must be a string resource. |
| app:andesSwitchAlign | Set the switch position at the left or right of the **andesSwitchText**. Possible values: **left**, **right** |
| app:andesSwitchType | Set a type style for the switch: **enabled**, **disabled** |
| app:andesSwitchStatus | Set switch initial status: **checked**, **unckecked** |

<br/>

## Constructors
| Summary |
| --- |
| AndesSwitch(context: Context, attrs: AttributeSet?) |
| [AndesSwitch](#andesswitchcontext-context-text-string-align-andesswitchalign-type-andesswitchtype-status-andesswitchstatus)(context: Context, text: String, align: [AndesSwitchAlign](#andesswitchalign), type: [AndesSwitchType](#andesswitchtype), status: [AndesSwitchStatus](#andesswitchstatus))|

<br/>

##### AndesSwitch(context: Context, text: String, align: AndesSwitchAlign, type: AndesSwitchType, status: AndesSwitchStatus)
| Parameter | Description |
| -------- | ------- |
| context | **Context**|
| text | **String**: switch companion text. |
| align | **[AndesSwitchAlign](#andesswitchalign)**: set the switch position at the left or right of the text. Default alignment is **RIGHT** |
| type | **[AndesSwitchType](#andesswitchtype)**: set the switch type style. Default type is **ENABLED** |
| status | **[AndesSwitchStatus](#andesswitchstatus)**: set the switch initial status. Default status is **UNCHECKED** |

<br/>

## Properties
| Property | Summary |
| -------- | ------- |
| text: String? | **get():** retrieves switch companion text displayed. <br/> **set(value: String?):** updates component text displayed. |
| align: AndesSwitchAlign | **get():** retrieves switch position. <br/> **set(value: AndesSwitchAlign):** updates switch position at the left or right of the text. |
| status: AndesSwitchStatus | **get():** retrieves switch current status. <br/> **set(value: AndesSwitchStatus):** updates switch status. |
| type: AndesSwitchType | **get():** retrieves switch type. <br/> **set(value: AndesSwitchType):** updates component type. |

<br/>

## Related Classes

### AndesSwitchAlign
Defines the possible positions that the switch can take.
```kotlin
enum class AndesSwitchAlign
```
| Enum Values | Description |
| ----------- | ----------- |
| LEFT | Aligns switch to the left of the displayed text<br/><img src="https://user-images.githubusercontent.com/81258246/122062026-6e6fbc80-cdc5-11eb-896b-4850cdd04edd.jpg" height="24"/> |
| RIGHT | Aligns switch to the right of the displayed text<br/><img src="https://user-images.githubusercontent.com/81258246/122062027-6e6fbc80-cdc5-11eb-8a81-abbf0ff4bc5b.jpg" height="24"/> |

<br/>

### AndesSwitchStatus
Defines the possible status that the switch can take.
```kotlin
enum class AndesSwitchStatus
```
| Enum Values | Description |
| ----------- | ----------- |
| CHECKED | Status ON <br/><img src="https://user-images.githubusercontent.com/81258246/122062024-6dd72600-cdc5-11eb-965a-1473c6a7ded6.jpg" height="24"/> |
| UNCHECKED | Status OFF <br/><img src="https://user-images.githubusercontent.com/81258246/122062022-6dd72600-cdc5-11eb-995a-d1ebb4310bbe.jpg" height="24"/> |

<br/>

### AndesSwitchType
Defines the possible type styles that the checkbox can take.
```kotlin
enum class AndesSwitchType
```
| Enum Values | Description |
| ----------- | ----------- |
| ENABLED | Default type of the switch, available for modifications <br/><img src="https://user-images.githubusercontent.com/81258246/122062019-6d3e8f80-cdc5-11eb-824d-0c190f466363.jpg" height="24"/> |
| DISABLED | Switch cannot be modified<br/><img src="https://user-images.githubusercontent.com/81258246/122062015-6d3e8f80-cdc5-11eb-88a8-54d3ef22d81d.jpg" height="24"/> |

<br/>

## Screenshots
<img src="https://user-images.githubusercontent.com/81258246/122062012-6b74cc00-cdc5-11eb-88ba-876bb4d061bd.jpg" width="300">