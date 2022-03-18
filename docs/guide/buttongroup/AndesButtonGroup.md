# AndesButtonGroup

AndesButtomGroup is a container for AndesButton. They allow you to add 1 to 3 buttons distributed horizontally, vertically, auto and mixed..
[See Andes UI component in frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/components/button-1584453489/variantes)
<br/>
<br/>
```kotlin
class AndesButtonGroup : ConstraintLayout
```

Basic Sample Programatically

```kotlin
AndesButtonGroup(context)
```

Basic Sample XML

```xml
    <com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
        android:id="@+id/button_group"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```

<br/>

## XML Attributes
| Property | Summary |
| -------- | ------- |
| app:andesButtonGroupDistribution | Determines the distribution of the buttons: **horizontal**, **vertical**, **auto**, **mixed** |
| app:andesButtonGroupType | Set a type style for the button group: **fullwidth**, **responsive** |

<br/>

## Constructors
| Summary |
| ------- |
| AndesButtonGroup(context: Context, attrs: AttributeSet?) |
| [AndesButtonGroup](#andesbuttongroupcontext-context-buttonlist-list-grouptype-andesbuttongrouptype-distribution-andesbuttongroupdistribution-align-andesbuttongroupalign)(context: Context, buttonList: List<AndesButton>, type: AndesButtonGroupType, distribution: AndesButtonGroupDistribution, align: AndesButtonGroupAlign) |
<br/>

##### AndesButtonGroup(context: Context, buttonList: List<AndesButton>, groupType: AndesButtonGroupType, distribution: AndesButtonGroupDistribution, align: AndesButtonGroupAlign)
| Parameter | Description |
| -------- | ------- |
| context | **Context**|
| buttonList | **List<[AndesButton](#buttonList)\>**: set the andes button list. |
| type | **[AndesButtonGroupType](#andesbuttongrouptype)**: set button group type. Default value is **FullWidth** |
| distribution | **[AndesButtonGroupDistribution](#andesbuttongroupdistribution)**: set button group distribution. Default value is **HORIZONTAL** |

<br/>

## Properties
| Property | Summary |
| -------- | ------- |
| type: [AndesButtonGroupType](#andesbuttongrouptype) | **get():** retrieves button group type. <br/> **set(value: AndesButtonGroupType):** updates button group type. |
| distribution: [AndesButtonGroupDistribution](#andesbuttongroupdistribution) | **get():** retrieves button group distribution. <br/> **set(value: AndesButtonGroupDistribution):** updates button group distribution. |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| Unit | **addButtons(buttonList: List<AndesButton>)**<br/> Set the list of andes button. |
| Unit | **setAlign(align: [AndesButtonGroupAlign])**<br/> Set the align of andes button group. |

<br/>

## Related Classes

### AndesButtonGroupType
Defines the possible type that button can take.
```kotlin
sealed class AndesButtonGroupType
```
| Values | Description |
| ----------- | ----------- |
| FullWidth | Gives fullwidth type with zero width dp <br/><img src="https://user-images.githubusercontent.com/97186235/158424670-13ad05bc-7375-48ef-b576-b6f610fb0cc8.png" height="48"/> |
| Responsive(val align: AndesButtonGroupAlign) | Gives Responsive type with the provider align (in this case with the WRAP_CONTENT width) <br/><img src="https://user-images.githubusercontent.com/97186235/158424760-0da2adb8-c959-4501-aaf9-c73396951298.png" height="48"/> |

<br/>

### AndesButtonGroupDistribution
Defines the possible sizes that button can take.
```kotlin
enum class AndesButtonGroupDistribution
```
| Enum Values | Description |
| ----------- | ----------- |
| HORIZONTAL | Gives the buttons distributed horizontally<br/><img src="https://user-images.githubusercontent.com/97186235/158424670-13ad05bc-7375-48ef-b576-b6f610fb0cc8.png" height="48"/> |
| VERTICAL |  Gives the buttons distributed vertically<br/><img src="https://user-images.githubusercontent.com/97186235/158428507-7333d9c2-d1b3-403c-8173-801605464857.png" height="80"/> |
| AUTO |  Gives the buttons distributed automatically. if the button texts are not too long, then they are distributed horizontally, otherwise vertically.<br/><img src="https://user-images.githubusercontent.com/97186235/158428836-6d76208a-d9ab-4827-baa3-99d0ca8d3e83.png" height="48"/><img src="https://user-images.githubusercontent.com/97186235/158428985-2daf78f9-4981-4846-8294-1a6bc7ffeae4.png" height="80"/> |
| MIXED |  Gives the buttons distributed in a mixed way otherwise the buttons will be distributed automatically.<br/><img src="https://user-images.githubusercontent.com/97186235/158429575-aca1093b-5011-4cc4-9690-6d23dec1e213.png" height="90"/><img src="https://user-images.githubusercontent.com/97186235/158429815-d8fbc9b1-df97-4a60-bce1-5cae794a9ed8.png" height="130"/> |

### AndesButtonGroupAlign
Defines the possible align that button can take. (These alignments are only applied when the type is Responsive).
```kotlin
enum class AndesButtonGroupAlign
```
| Enum Values | Description |
| ----------- | ----------- |
| LEFT | Gives the buttons aligned to left.<br/><img src="https://user-images.githubusercontent.com/97186235/158435899-a7b64c7b-0afc-47f1-ae89-6a12a964f075.png" height="80"/> |
| RIGHT |  Gives the buttons aligned to right.<br/><img src="https://user-images.githubusercontent.com/97186235/158436024-f9c6b016-b226-4555-92b7-34a1d425024f.png" height="80"/> |
| CENTER |  Gives the buttons aligned to center.<br/><img src="https://user-images.githubusercontent.com/97186235/158436350-ef392362-4acf-4de5-b892-e817fb5b268f.png" height="80"/> |

## Screenshots
<br/>
<img src="https://camo.githubusercontent.com/c0c6a27afd87c1c01d02055ce7ea6e5ab06878b536179be4d94466bf31ecde64/68747470733a2f2f6d656469612e67697068792e636f6d2f6d656469612f6f556f6b7353756f716f75354a5473706b772f67697068792e676966" />