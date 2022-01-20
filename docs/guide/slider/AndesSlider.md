# AndesSlider

AndesSlider is a control that allows you to make a quick selection within a defined range of values.
[See Andes UI component in frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/components/slider)

```kotlin
class AndesSlider : ConstraintLayout 
```

Basic Sample Programatically

```kotlin
AndesSlider(context, 0, 100)
```

Basic Sample XML

```xml
    <com.mercadolibre.android.andesui.slider.AndesSlider
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:andesSliderTitle="Limits type"
        app:andesSliderType="limits"
        app:andesSliderLeftContent="@string/andes_slider_limit_left"
        app:andesSliderRightContent="@string/andes_slider_limit_right"
        app:andesSliderMin="0"
        app:andesSliderMax="100"
        app:andesSliderValue="50"
        app:andesSliderAccessibilityContentSuffix="%"
        />
```

<br/>

## XML Attributes
| Property | Summary |
| -------- | ------- |
| app:andesSliderTitle | Determines slider label value. Default value: **null**. |
| app:andesSliderValue | Determines slider selected value. Default value is **andesSliderMin**. |
| app:andesSliderMin | Determines slider minimum value. Default value is **0**. |
| app:andesSliderMax | Determines slider maximum value. Default value is **100**. |
| app:andesSliderSteps | Determines the number of steps of the slider. Must be a number between 3 and 11. Default value is **null**. |
| app:andesSliderState | Determines slider state style: **idle**, **disabled**. Default value is **idle**. |
| app:andesSliderType | Determines slider left and right content type: **simple**, **icon**, **limits**. Default value is **simple**. |
| app:andesSliderLeftContent | Determines slider left content. It must be a string or drawable depending on **andesSliderType**. Default value is **null**. |
| app:andesSliderRightContent | Determines slider right content. It must be a string or drawable depending on **andesSliderType**. Default value is **null**. |
| app:andesSliderAccessibilityContentSuffix | Determines slider suffix for talkback content announcement. It is used to give context of the value eg: "percentage". Default value is **null**. |

<br/>

## Constructors
| Summary |
| ------- |
| AndesSlider(context: Context, attrs: AttributeSet?) |
| [AndesSlider](#andesslidercontext-context-min-float-max-float-value-float-text-string-type-andesslidertype-numberofsteps-andesslidersteps-state-andessliderstate-accessibilitycontentsuffix-string)(context: Context, min: Float, max: Float, value: Float, text: String?, type: AndesSliderType, numberOfSteps: AndesSliderSteps, state: AndesSliderState, accessibilityContentSuffix: String) |

<br/>

##### AndesSlider(context: Context, min: Float, max: Float, value: Float, text: String?, type: AndesSliderType, numberOfSteps: AndesSliderSteps, state: AndesSliderState, accessibilityContentSuffix: String)
| Parameter | Description |
| -------- | ------- |
| context | **Context**|
| min | **Float**: slider minimum value. |
| max | **Float**: slider maximum value. |
| value | **Float**: slider selected value. Default value: **min** |
| text | **String?**: slider component label. Default value: **null**. |
| type | **[AndesSliderType](#andesslidertype)**: slider left and right content type. Default value: **[AndesSliderType.Simple](#andesslidertype)**. |
| numberOfSteps | **[AndesSliderSteps](#andesslidersteps)**: slider number of steps divides the component in parts and only numberOfSteps values can be selected. Default value: **[AndesSliderSteps.None](#andesslidersteps)**. |
| state | **[AndesSliderState](#andessliderstate)**: slider state style and behaviour. Default value: **[AndesSliderState.IDLE](#andessliderstate)**. |
| accessibilityContentSuffix | **String**: suffix text used by talkback contentDescription announcements. It is used to give context of the value eg: "percentage". Default value is **""**. |

<br/>

## Properties
| Property | Summary |
| -------- | ------- |
| text: String? | **get():** retrieves slider component label. <br/> **set(value: String?):** updates slider component label. |
| state: [AndesSliderState](#andessliderstate) | **get():** retrives slider state style and behaviour. <br/> **set(value: [AndesSliderState](#andessliderstate)):** updates slider state style and behaviour. |
| min: Float | **get():** retrives slider minimum range value. <br/> **set(value: Float):** updates slider minimum range value. |
| max: Float | **get():** retrives slider maximum range value. <br/> **set(value: Float):** updates slider maximum range value. |
| value: Float | **get():** retrives slider current value. <br/> **set(value: Float):** updates slider current value. |
| steps: [AndesSliderSteps](#andesslidersteps) | **get():** retrives slider number of steps. <br/> **set(value: [AndesSliderSteps](#andesslidersteps)):** updates slider number of steps. |
| type: [AndesSliderType](#andeslidertype) | **get():** retrives slider left and right content style. <br/> **set(value: [AndesSliderType](#andeslidertype)):** updates slider left and right content style. |
| accessibilityContentSuffix: String | **get():** retrives slider suffix used by talkback contentDescription announcements. <br/> **set(value: String):** updates slider suffix used by talkback. |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| Unit | **setOnValueChangedListener(listener: [OnValueChangedListener](#onvaluechangedlistener)**<br/> Set a callback to listen when a [AndesSlider](#andesslider)'s value is changed. |

<br/>

## Related Classes

### AndesSliderState
Defines the possible state styles that the [AndesSlider](#andesslider) can take.

```kotlin
enum class AndesSliderState
```
| Values | Description |
| ----------- | ----------- |
| IDLE | Default status of the slider<br/><img src="https://user-images.githubusercontent.com/58984116/150181549-32ccd501-d0ca-478c-9658-a3771eacda8b.png" height="60"/> |
| DISABLED | Slider can not be modified<br/><img src="https://user-images.githubusercontent.com/58984116/150181557-2fe05e94-5800-4fb5-86df-46bd18487f27.png" height="60"/> |

<br/>

### AndesSliderSteps
Defines the two possible step styles an [AndesSlider](#andesslider) can take: None, Custom.

```kotlin
sealed class AndesSliderSteps
```

| Values | Description |
| ----------- | ----------- |
| None | Default slider with none steps. <br/><img src="https://user-images.githubusercontent.com/58984116/150183708-6c40d0d2-ede9-4f4e-88b1-a80f115d6809.png" height="90"/> |
| Custom(numberOfSteps: Int) | Slider divided in numberOfSteps selectable values. <br/><img src="https://user-images.githubusercontent.com/58984116/150183713-fbbcb3bb-512b-4986-a8ed-587e243ff5be.png" height="90"/> |

<br/>

### AndesSliderType
Defines the possible left and right content an [AndesSlider](#andesslider) can have

```kotlin
sealed class AndesSliderType
```

| Values | Description |
| ----------- | ----------- |
| Simple | None content to the right and left of the slider. <br/><img src="https://user-images.githubusercontent.com/58984116/150183970-1594a99b-f9d3-4452-ae56-c6c7a05340b6.png" height="90"/> |
| Icon(iconLeft: Drawable?, iconRight: Drawable?) | Drawable icons to the right and left of the slider. <br/><img src="https://user-images.githubusercontent.com/58984116/150183972-c8ca8740-500f-4449-af78-5fb3ad516a48.png" height="90"/> |
| Limits(textLeft: String?, textRight: String?) | String text to the right and left of the slider. <br/><img src="https://user-images.githubusercontent.com/58984116/150183975-5024a335-5034-4c72-afb9-f968cf4071ef.png" height="90"/> |

<br/>

### OnValueChangedListener
Callback interface to listen when a [AndesSlider](#andesslider)'s value is changed.
```kotlin
interface OnValueChangedListener
```

#### Functions
| Return type | Method |
| -------- | ------- |
| Unit | **onValueChanged(value: Float):** This method will be invoked when a new value becomes selected. |

<br/>

## Screenshots
<img src="https://user-images.githubusercontent.com/58984116/150184166-b745c14a-eb49-4c04-9faa-ae40f55d6147.png" width="300">