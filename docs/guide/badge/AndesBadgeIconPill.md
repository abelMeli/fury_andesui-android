# AndesBadgeIconPill

AndesBadgeIconPill is a small unit of information that allow you to indicate the status of an item or differentiate several similar items from each other.  
[See Andes UI component in frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/components/badge/pill)

```kotlin
class AndesBadgeIconPill : ConstraintLayout
```

Basic Sample Programatically

```kotlin
AndesBadgeIconPill(context)
```
Basic Sample XML

```xml
<com.mercadolibre.android.andesui.badge.AndesBadgeIconPill
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:andesBadgeIconPillSize="large"
    app:andesBadgeIconPillHierarchy="loud"
    app:andesBadgeIconPillType="highlight" />
```
<br/>

## XML Attributes
| Property | Summary |
| -------- | ------- |
| app:andesBadgeIconPillType | Set a color palette for the badge: **highlight**, **success**, **warning**, **error** |
| app:andesBadgeIconPillSize | Badge display size: **large**, **small** |
| app:andesBadgeIconPillHierarchy | Badge background color hierarchy: **loud**, **secondary** |

<br/>

## Constructors
| Summary |
| --- |
| AndesBadgeIconPill(context: Context, attrs: AttributeSet?) |
| AndesBadgeIconPill(context: Context, type: [AndesBadgeIconType](#andesbadgeicontype), size: [AndesBadgePillSize](#andesbadgepillsize), hierarchy: [AndesBadgeIconHierarchy](#andesbadgeiconhierarchy))|

<br/>

##### AndesBadgeIconPill(context: Context, type: AndesBadgeIconType, size: AndesBadgePillSize, hierarchy: AndesBadgeIconHierarchy)
| Parameter | Description |
| -------- | ------- |
| context | **Context**|
| type | **[AndesBadgeIconType](#andesbadgeicontype)**: badge color palette. Default type is **HIGHLIGHT** |
| size | **[AndesBadgePillSize](#andesbadgepillsize)**: badge display size. Default size is **SMALL** |
| hierarchy | **[AndesBadgeIconHierarchy](#andesbadgeiconhierarchy)**: badge background color hierarchy. Default hierarchy is **LOUD** |

<br/>

## Properties
| Property | Summary |
| -------- | ------- |
| type: [AndesBadgeIconType](#andesbadgeicontype) | **get():** retrieves badge color palette <br/> **set(value: [AndesBadgeIconType](#andesbadgeicontype)):** updates badge color palette |
| size: [AndesBadgePillSize](#andesbadgepillsize) | **get():** retrieves badge displayed size <br/> **set(value: [AndesBadgePillSize](#andesbadgepillsize)):** updates badge display size |
| hierarchy: [AndesBadgeIconHierarchy](#andesbadgeiconhierarchy) | **get():** retrieves badge background color hierarchy <br/> **set(value: [AndesBadgeIconHierarchy](#andesbadgeiconhierarchy)):** updates badge background color hierarchy |

<br/>

## Related Classes

### AndesBadgeIconType
Defines the possible styles [AndesBadgeIconPill](#andesbadgeiconpill) can take.
```kotlin
enum class AndesBadgeIconType
```
| Enum Values | Description |
| --------- | ------------- |
| HIGHLIGHT | Gives a blue color style to the component<br/><img src="../resources/badge/iconpill/highlightLarge.png" height="24"/> |
| SUCCESS | Gives a green color style to the component<br/><img src="../resources/badge/iconpill/successLarge.png" height="24"/> |
| WARNING | Gives an orange color style to the component<br/><img src="../resources/badge/iconpill/warningLarge.png" height="24"/> |
| ERROR | Gives a red color style to the component<br/><img src="../resources/badge/iconpill/errorLarge.png" height="30"/> |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesBadgeIconType | **fromString(value: String)**<br/> Retrieves an AndesBadgeIconType that matches the string value |

<br/>

### AndesBadgePillSize
Defines the possible sizes [AndesBadgeIconPill](#andesbadgeiconpill) can take.
```kotlin
enum class AndesBadgePillSize
```
| Enum Values | Description |
| ----------- | ----------- |
| SMALL | Small badge size<br/><img src="../resources/badge/iconpill/highlightSmall.png" height="18"/> |
| LARGE | Large badge size<br/><img src="../resources/badge/iconpill/highlightLarge.png" height="24"/> |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesBadgePillSize | **fromString(value: String)**<br/> Retrieves an AndesBadgePillSize that matches the string value |

<br/>

### AndesBadgeIconHierarchy
Defines the possible hierarchies [AndesBadgeIconHierarchy](#andesbadgeiconhierarchy) can take.
```kotlin
enum class AndesBadgeIconHierarchy
```
| Enum Values | Description |
| --------- | ------------- |
| LOUD | Gives the primary color specified in badge type style<br/><img src="../resources/badge/iconpill/errorLoud.png" height="24"/> |
| SECONDARY | Gives the primary variant color specified in badge type style<br/><img src="../resources/badge/iconpill/errorSecondary.png" height="24"/> |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesBadgeIconHierarchy | **fromString(value: String)**<br/> Retrieves an AndesBadgeIconHierarchy that matches the string value |

<br/>

## Screenshots
<img src="../resources/badge/iconpill/badgeIconPillExample.png" width="300">