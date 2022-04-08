# AndesButton

AndesButton is a container that display an image, logo or icon to complement content and reinforce meaning.
[See Andes UI component in frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/components/button-1584453489)

```kotlin
class AndesButton : ConstraintLayout
```

Basic Sample Programatically

```kotlin
AndesButton(context)
```

Basic Sample XML

```xml
    <com.mercadolibre.android.andesui.button.AndesButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:andesButtonText="@string/awesome_button"/>
```

<br/>

## XML Attributes
| Property | Summary |
| -------- | ------- |
| app:andesButtonHierarchy | Determines hierarchy style: **quiet**, **loud**, **transparent** |
| app:andesButtonSize | Button display size: **small**, **medium**, **large** |
| app:andesButtonLeftIconPath | Icon at the left of the text. It must be icon string name.|
| app:andesButtonRightIconPath | Icon at the right of the text. It must be icon string name. |
| app:andesButtonText | Button display text. Must be a string resource. |
| app:andesButtonProgressLoadingText | Button display text when the progress loading is in process. Must be a string resource. |
| app:andesButtonEnabled | Button active or inactive style: **true**, **false** |
| app:andesButtonIsLoading | Shows progress drawable instead of text: **true**, **false** |

<br/>

## Constructors
| Summary |
| ------- |
| AndesButton(context: Context) |
| AndesButton(context: Context, attrs: AttributeSet?) |
| AndesButton(context: Context, attrs: AttributeSet?, defStyleAttr: Int) |
| [AndesButton](#andesbuttoncontext-context-buttonsize-andesbuttonsize-buttonhierarchy-andesbuttonhierarchy-buttonicon-andesbuttonicon-buttontext-string)(context: Context, buttonSize: [AndesButtonSize](#andesbuttonsize), buttonHierarchy: [AndesButtonHierarchy](#andesbuttonhierarchy), buttonIcon: [AndesButtonIcon](#andesbuttonicon)?, buttonText: String?) |

<br/>

##### AndesButton(context: Context, buttonSize: AndesButtonSize, buttonHierarchy: AndesButtonHierarchy, buttonIcon: AndesButtonIcon?, buttonText: String?) 
| Parameter | Description |
| -------- | ------- |
| context | **Context**|
| buttonSize | **[AndesButtonSize](#andesbuttonsize)**: set the button display size. Default value is **LARGE** |
| buttonHierarchy | **[AndesButtonHierarchy](#andesbuttonhierarchy)**: set button hierarchy style. Default value is **LOUD** |
| buttonIcon | **[AndesButtonIcon](#andesbuttonicon)?**: set button icon and position. |
| buttonText | **String?**: set button display text. Default value is **Button text** |

<br/>

## Properties
| Property | Summary |
| -------- | ------- |
| leftIconComponent: SimpleDraweeView | **get():** retrieves left icon view. |
| rightIconComponent: SimpleDraweeView | **get():** retrieves right icon view. |
| text: String? | **get():** retrieves button displayed text. <br/> **set(value: Drawable):** updates button displayed text. |
| progressLoadingText: String? | **get():** retrieves button displayed text when the progress loading is in process. <br/> **set(value: Drawable):** updates button displayed text when the progress loading is in process. |
| hierarchy: [AndesButtonHierarchy](#andesbuttonhierarchy) | **get():** retrieves button hierarchy style. <br/> **set(value: AndesButtonHierarchy):** updates button hierarchy style. |
| size: [AndesButtonSize](#andesbuttonsize) | **get():** retrieves button size value. <br/> **set(value: AndesButtonSize):** updates button size. |
| isLoading: Boolean | **get():** retrieves if button is in loading state. <br/> **set(value: Boolean):** updates button loading state. |
| progressStatus: AndesButtonProgressAction | **get():** retrieves the actual button loading progress status. <br/> **set(value: Boolean):** updates the button loading progress status. |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| Unit | **setEnabled(enabled: Boolean)**<br/> Changes button active state and style. |
| Unit | **setIconDrawable(drawable: Drawable, orientation: [AndesButtonIconOrientation](#andesbuttoniconorientation))**<br/> Set icon drawable to the button at left or right of the text |
| Unit | **loadCustomButtonIcon(pipelineDraweeControllerBuilder: PipelineDraweeControllerBuilder, leftIconPosition: Boolean)**<br/> loads icon drawable using fresco PipelineDraweeControllerBuilder. The icon is loaded in left position by default |
| Int | **getProgressIndicatorValue()**<br/> Get the actual progress loading indicator value of the button |
| Unit | **setProgressIndicatorFrom(from: Int)**<br/> Set the start of the progress loading indicator value of the button |
| Unit | **setProgressIndicatorTo(to: Int)**<br/> Set the end of the progress loading indicator value of the button |
| Unit | **setProgressIndicatorDuration(duration: Int)**<br/> Set the duration of the progress loading animation of the button |

<br/>

## Related Classes

### AndesButtonHierarchy
Defines the possible color palettes that button can take.
```kotlin
enum class AndesButtonHierarchy
```
| Enum Values | Description |
| ----------- | ----------- |
| TRANSPARENT | Button text tinted with main color and background transparent<br/><img src="resources/button/transparentButtonExample.png" height="48"/> |
| QUIET | Button text tinted with main color and background with a lighter one<br/><img src="resources/button/quietButtonExample.png" height="48"/> |
| LOUD | Button text tinted with white and background with main color<br/><img src="resources/button/loudButtonExample.png" height="48"/> |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesButtonHierarchy | **fromString(value: String)**<br/> Retrieves an AndesButtonHierarchy that matches the string value |

<br/>

### AndesButtonSize
Defines the possible sizes that button can take.
```kotlin
enum class AndesButtonSize
```
| Enum Values | Description |
| ----------- | ----------- |
| SMALL | Button height 24dp<br/><img src="resources/button/smallSizeButtonExample.png" height="24"/> |
| MEDIUM | Button height 32dp<br/><img src="resources/button/mediumSizeButtonExample.png" height="32"/> |
| LARGE | Button height 48dp<br/><img src="resources/button/loudButtonExample.png" height="48"/> |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesButtonSize | **fromString(value: String)**<br/> Retrieves an AndesButtonSize that matches the string value |

<br/>

### AndesButtonIcon
Class used to define [AndesButton](#andesbutton) local drawable path and orientation inside the container view.
```kotlin
class AndesButtonIcon
```

#### Constructors
| Summary |
| ------- |
| AndesButtonIcon(icon: String?, orientation: [AndesButtonIconOrientation](#andesbuttoniconorientation)) |

<br/>

##### AndesButtonIcon(icon: String?, orientation: [AndesButtonIconOrientation](#andesbuttoniconorientation))
| Parameter | Description |
| --------- | ----------- |
| icon | **String?**: local icon asset name |
| orientation | **[AndesButtonIconOrientation](#andesbuttoniconorientation)**: icon position left or right |


<br/>

#### Properties
| Property | Summary |
| -------- | ------- |
| icon: String? | **get():** local icon asset name. |

<br/>

### AndesButtonIconOrientation
Defines the possible positions that button icon can take.
```kotlin
enum class AndesButtonIconOrientation
```
| Enum Values | Description |
| ----------- | ----------- |
| LEFT | Icon position at the left of the button text<br/><img src="resources/button/leftIconButtonExample.png" height="48"/> |
| RIGHT | Icon position at the right of the button text<br/><img src="resources/button/rightIconButtonExample.png" height="48"/> |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesButtonIconOrientation | **fromString(value: String)**<br/> Retrieves an AndesButtonIconOrientation that matches the string value |

<br/>

### AndesButtonProgressAction
Defines the possible actions of the loading progress that button can perform.
```kotlin
enum class AndesButtonProgressAction
```
| Enum Values | Description |
| ----------- | ----------- |
| IDLE | Loading progress IDLE |
| START | Loading progress starts to animate |
| PAUSE | Loading progress pauses the animation if it was already started |
| RESUME | Loading progress resumes the animation if it was already pause |
| CANCEL | Loading progress cancels if it was already started |

<br/>

## Screenshots
<img src="resources/button/buttonExample.png" width="300">