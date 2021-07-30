# AndesTimePicker

AndesTimePicker is a component that allows the user the selection of time or time ranges.
[See Andes UI component in frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/components/time-picker)

```kotlin
class AndesTimePicker : ConstraintLayout
```

Basic Sample Programatically

```kotlin
AndesTimePicker(
    context = context,
    label = null,
    helper = null,
    currentTime = null
)
```

Basic Sample XML

```xml
    <com.mercadolibre.android.andesui.timepicker.AndesTimePicker
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

<br/>

## XML Attributes
| Property | Summary |
| -------- | ------- |
| app:andesTimePickerState | Determines state for the component: **idle**, **error**, **disabled**, **readonly** |
| app:andesTimePickerType | Determines the type of the component: **time_interval**, **granular** |
| app:andesTimePickerInterval | When using time_interval type, determines minutes interval: **5_minutes**, **10_minutes**, **15_minutes**, **30_minutes**, **60_minutes** |
| app:andesTimePickerLabel | Sets initial text value to the label of the component. It must be a string resource. |
| app:andesTimePickerHelper | Sets initial text value to the helper of the component. It must be a string resource. |
| app:andesTimePickerCurrentTime | Sets initial time value to the component. It must be a string resource with format "hh:mm". |

<br/>

## Constructors
| Summary |
| --- |
| AndesTimePicker(context: Context, attrs: AttributeSet?) |
| [AndesTimePicker](#andestimepickercontext-context-label-string-helper-string-currentTime-string-state-andestimepickerstate-type-andestimepickertype-interval-andestimepickerinterval)(context: Context, label: String?, helper: String?, currentTime: String?, state: [AndesTimePickerState](#andestimepickerstate), type: [AndesTimePickerType](#andestimepickertype), interval: [AndesTimePickerInterval](#andestimepickerinterval))|

<br/>

##### AndesTimePicker(context: Context, label: String?, helper: String?, currentTime: String?, state: AndesTimePickerState, type: AndesTimePickerType, interval: AndesTimePickerInterval)
| Parameter | Description |
| -------- | ------- |
| context | **Context**|
| label | **String?**: component label text. Default value is **null** |
| helper | **String?**: component helper text. Default value is **null** |
| currentTime | **String?**: initial hh:mm value set in the component. Default value is **null** |
| state | **[AndesTimePickerState](#andestimepickerstate)**: set the timepicker initial state. Default status is **IDLE** |
| type | **[AndesTimePickerType](#andestimepickertype)**: set the type for the component. Default value is **TIME_INTERVAL** |
| interval | **[AndesTimePickerInterval](#andestimepickerinterval)**: set the interval value for the component. Default value is **MINUTES_30** |

<br/>

## Properties
| Property | Summary |
| -------- | ------- |
| label: String? | **get():** retrieves component label text displayed. <br/> **set(value: String?):** updates component label text displayed. |
| helper: String? | **get():** retrieves component helper text displayed. <br/> **set(value: String?):** updates component helper text displayed. |
| currentTime: String? | **get():** retrieves the time selected as string value, or null if is not set/selected. <br/> **set(value: String?):** updates selected time in the component. |
| state: AndesTimePickerState | **get():** retrieves component current state. <br/> **set(value: AndesSwitchAlign):** updates component state. |
| type: AndesTimePickerType | **get():** retrieves component current type. <br/> **set(value: AndesSwitchStatus):** updates component type. |
| minutesInterval: AndesTimePickerInterval | **get():** retrieves the selected variant for interval. <br/> **set(value: AndesSwitchType):** updates the values of the displayed list of available hours for selection. |

## Related Classes

### AndesTimePickerState
Defines the different states the timepicker can take.
```kotlin
enum class AndesTimePickerState
```
| Enum Values | Description |
| ----------- | ----------- |
| IDLE | Standard value for the timepicker. Allows the user to interact with the component and select a time from the displayed list.<br/><img src="https://user-images.githubusercontent.com/81258246/127500627-c5181cac-f4a6-4f2d-a618-caaf9ec978c3.png" height="64"/> |
| ERROR | Error value for the timepicker. Announces an error occured in the description. Should include a helper text establishing the reason for the error.<br/><img src="https://user-images.githubusercontent.com/81258246/127500625-3fc3778b-131c-4148-9d65-331eb840bccc.png" height="64"/> |
| DISABLED | Disabled value for the timepicker. Does not allow interaction for the user.<br/><img src="https://user-images.githubusercontent.com/81258246/127500624-dad406b8-b7e7-44d0-a643-0f2ff64702d2.png" height="64"/> |
| READONLY | Read only value for the timepicker. Does not allow interaction for the user.<br/><img src="https://user-images.githubusercontent.com/81258246/127500623-a45c9913-63f9-4545-a5b4-eb88454c39e7.png" height="64"/> |

<br/>

### AndesTimePickerType
Defines the different types the timepicker can take.
```kotlin
enum class AndesTimePickerType
```
| Enum Values | Description |
| ----------- | ----------- |
| TIME_INTERVAL | Standard value for the timepicker. This value implies the component will have a list of available hours for the user to select. |

<br/>

### AndesTimePickerInterval
Defines the different time intervals the timepicker can take.
```kotlin
enum class AndesTimePickerInterval
```
| Enum Values | Description |
| ----------- | ----------- |
| MINUTES_5 | This value implies that the list of available hours for the user to select will be displayed in five minutes interval. Also, this list will be separated in an individual floating menu for the hours value and another one for the minutes value. |
| MINUTES_10 | This value implies that the list of available hours for the user to select will be displayed in ten minutes interval. Also, this list will be separated in an individual floating menu for the hours value and another one for the minutes value. |
| MINUTES_15 | This value implies that the list of available hours for the user to select will be displayed in fifteen minutes interval. Also, this list will be separated in an individual floating menu for the hours value and another one for the minutes value. |
| MINUTES_30 | This value implies that the list of available hours for the user to select will be displayed in thirty minutes interval. Also, this list will be displayed as a single dropdown with a floating menu.<br/><img src="https://user-images.githubusercontent.com/81258246/127500616-7d0320e9-8f22-43b5-b1df-22fff33ecd60.png" width="100"/> |
| MINUTES_60 | This value implies that the list of available hours for the user to select will be displayed in sixty minutes interval. Also, this list will be displayed as a single dropdown with a floating menu.<br/><img src="https://user-images.githubusercontent.com/81258246/127500621-dc323e2d-b190-47f1-8596-d5e627c8018f.png" width="100"/> |

<br/>

### OnTimeSelectedListener
Interface used to inform the dev that the user has selected a value. Also, it will inform the current time of the component.

| Return type | Method |
| ----------- | ----------- |
| Unit | **onTimeSelected(currentTime: String)**<br/> Method invoked when the user selects a value |
| Unit | **onTimePeriodSelected()**<br/> Method invoked when the user selects a time range (currently not in use, will be implemented with the TIME_SLOTS type variant) |

## Screenshots
<img src="https://user-images.githubusercontent.com/81258246/127501718-3fd04fde-281b-41de-9d9a-e74d0684b369.png" width="250">