# AndesBadgePill

AndesBadgePill is a small unit of information that allow you to indicate the status of an item or differentiate several similar items from each other.  
[See Andes UI component in frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/components/badge/pill)

```kotlin
class AndesBadgePill : CardView
```

Basic Sample Programatically

```kotlin
AndesBadgePill(context)
```
Basic Sample XML

```xml
 <com.mercadolibre.android.andesui.badge.AndesBadgePill
     android:layout_width="wrap_content"
     android:layout_height="wrap_content"
     app:andesBadgePillText="@string/foo" />
```
<br/>

## XML Attributes
| Property | Summary |
| -------- | ------- |
| app:andesBadgePillHierarchy | Determines hierarchy style: **loud**, **quiet** |
| app:andesBadgePillType | Set a color palette for the badge: **neutral**, **highlight**, **success**, **warning**, **error** |
| app:andesBadgePillSize | Badge display size: **large**, **small** |
| app:andesBadgePillBorder | Badge border style: **corner**, **rounded**, **standard** |
| app:andesBadgePillText | Text to display. It must be a string resource. |

<br/>

## Constructors
| Summary |
| --- |
| AndesBadgePill(context: Context, attrs: AttributeSet?) |
| [AndesBadgePill](#andesbadgepillcontext-context-pillhierarchy-andesbadgepillhierarchy-type-andesbadgetype-pillborder-andesbadgepillborder-pillsize-andesbadgepillsize-text-string)(context: Context, pillHierarchy: [AndesBadgePillHierarchy](#andesbadgepillhierarchy), type: [AndesBadgeType](#andesbadgetype), pillBorder: [AndesBadgePillBorder](#andesbadgepillborder), pillSize: [AndesBadgePillSize](#andesbadgepillsize), text: String?)|

<br/>

##### AndesBadgePill(context: Context, pillHierarchy: AndesBadgePillHierarchy, type: AndesBadgeType, pillBorder: AndesBadgePillBorder, pillSize: AndesBadgePillSize, text: String?)
| Parameter | Description |
| -------- | ------- |
| context | **Context**|
| pillHierarchy | **[AndesBadgePillHierarchy](#andesbadgepillhierarchy)**: this value can be null. Default hierarchy is **LOUD** |
| type | **[AndesBadgeType](#andesbadgetype)**: this value can be null. Default type is **NEUTRAL** |
| pillBorder | **[AndesBadgePillBorder](#andesbadgepillborder)**: this value can be null. Default border is **ROUNDED** |
| pillSize | **[AndesBadgePillSize](#andesbadgepillsize)**: this value can be null. Default size is **SMALL** |
| text | **String**: Pill content text. This value can be null. |

<br/>

## Properties
| Property | Summary |
| -------- | ------- |
| pillHierarchy: [AndesBadgePillHierarchy](#andesbadgepillhierarchy) | **get():** retrieves badge hierarchy style <br/> **set(value: [AndesBadgePillHierarchy](#andesbadgepillhierarchy)):** updates badge hierarchy style |
| type: [AndesBadgeType](#andesbadgetype) | **get():** retrieves badge color palette <br/> **set(value: [AndesBadgeType](#andesbadgetype)):** updates badge color palette |
| pillBorder: [AndesBadgePillBorder](#andesbadgepillborder) | **get():** retrieves badge border style <br/> **set(value: [AndesBadgePillBorder](#andesbadgepillborder)):** updates badge border style |
| pillSize: [AndesBadgePillSize](#andesbadgepillsize) | **get():** retrieves badge displayed size <br/> **set(value: [AndesBadgePillSize](#andesbadgepillsize)):** updates badge display size |
| text: String?| **get():** retrieves badge text displayed. Can retrieve a null value. <br/> **set(value: String?):** updates text to display. Can be set as null. |

<br/>

## Related Classes

### AndesBadgePillHierarchy
Defines the possible hierarchies [AndesBadgePill](#andesbadgepill) can take.
```kotlin
enum class AndesBadgePillHierarchy
```
| Enum Values | Description |
| ----------- | ----------- |
| QUIET | Gives main color to the displayed text and lighter color to the background<br/><img src="resources/badge/pill/quietExample.png" height="30"/> |
| LOUD | Gives white color to the displayed text and main color to the background<br/><img src="resources/badge/pill/loudExample.png" height="30"/> |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesBadgePillHierarchy | **fromString(value: String)**<br/> Retrieves an AndesBadgePillHierarchy that matches the string value |

<br/>

### AndesBadgeType
Defines the possible styles [AndesBadgePill](#andesbadgepill) can take.
```kotlin
enum class AndesBadgeType
```
| Enum Values | Description |
| --------- | ------------- |
| NEUTRAL | Gives a gray color style to the component<br/><img src="resources/badge/pill/loudExample.png" height="30"/> <img src="resources/badge/pill/quietExample.png" height="30"/> |
| HIGHLIGHT | Gives a blue color style to the component<br/><img src="resources/badge/pill/highlightExample.png" height="30"/> <img src="resources/badge/pill/highlightQuietExample.png" height="30"/> |
| SUCCESS | Gives a green color style to the component<br/><img src="resources/badge/pill/successExample.png" height="30"/> <img src="resources/badge/pill/successQuietExample.png" height="30"/> |
| WARNING | Gives an orange color style to the component<br/><img src="resources/badge/pill/warningExample.png" height="30"/> <img src="resources/badge/pill/warningQuietExample.png" height="30"/> |
| ERROR | Gives a red color style to the component<br/><img src="resources/badge/pill/errorExample.png" height="30"/> <img src="resources/badge/pill/errorQuietExample.png" height="30"/> |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesBadgeType | **fromString(value: String)**<br/> Retrieves an AndesBadgeType that matches the string value |

<br/>

### AndesBadgePillBorder
Defines the possible border styles [AndesBadgePill](#andesbadgepill) can take.
```kotlin
enum class AndesBadgePillBorder
```
| Enum Values | Description |
| ----------- | ----------- |
| CORNER | Top-left and bottom-right corners are square, top-right and bottom-left corners are rounded<br/><img src="resources/badge/pill/cornerExample.png" height="30"/> |
| ROUNDED | Only bottom-left corner is rounded<br/><img src="resources/badge/pill/roundedExample.png" height="30"/> |
| STANDARD | All badge corners are rounded<br/><img src="resources/badge/pill/highlightExample.png" height="30"/> |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesBadgePillBorder | **fromString(value: String)**<br/> Retrieves an AndesBadgePillBorder that matches the string value |

<br/>

### AndesBadgePillSize
Defines the possible sizes [AndesBadgePill](#andesbadgepill) can take.
```kotlin
enum class AndesBadgePillSize
```
| Enum Values | Description |
| ----------- | ----------- |
| SMALL | Small badge size<br/><img src="resources/badge/pill/smallExample.png" height="30"/> |
| LARGE | Large badge size<br/><img src="resources/badge/pill/quietExample.png" height="30"/> |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesBadgePillSize | **fromString(value: String)**<br/> Retrieves an AndesBadgePillSize that matches the string value |

<br/>

## Screenshots
<img src="resources/badge/pill/BadgeExamples.png" width="300">