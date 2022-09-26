# AndesThumbnailBadge

AndesThumbnailBadge is a container that displays an image, logo or icon  with a badge pill, icon or dot to complement content and reinforce meaning.
[See Andes UI component in frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/components/thumbnail-1589997379)

```kotlin
class AndesThumbnailBadge : ConstraintLayout
```

Basic Sample Programatically

```kotlin
AndesThumbnailBadge(
    context = this,
    image = drawable,
    badge = AndesThumbnailBadgeComponent.IconPill(color = AndesBadgeIconType.HIGHLIGHT)
)
```

Basic Sample XML

```xml
<com.mercadolibre.android.andesui.thumbnail.AndesThumbnailBadge
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:andesThumbnailBadgeImage="@drawable/my_drawable"
    app:andesThumbnailBadgeComponentType="success"
    app:andesThumbnailBadgeComponent="icon_pill"
    app:andesThumbnailBadgeComponentPillSize="size_64" />
```

<br/>

## XML Attributes
| Property | Summary |
| -------- | ------- |
| app:andesThumbnailBadgeImage | Drawable resource to display. |
| app:andesThumbnailBadgeType | Thumbnail design type: **icon**, **image_circle** |
| app:andesThumbnailBadgeComponent | Thumbnail badge view type: **pill**, **icon_pill**, **dot** |
| app:andesThumbnailBadgeComponentType | Thumbnail tint, badge color and outline: **highlight**, **success**, **warning**, **error** |
| app:andesThumbnailBadgeComponentDotSize | Thumbnail display size in dp for dot badge: **size_24**, **size_32** |
| app:andesThumbnailBadgeComponentPillSize | Thumbnail display size in dp for pill and icon_pill badge: **size_40**, **size_48**, **size_56**, **size_64**, **size_72**, **size_80** |
| app:andesThumbnailBadgePillText | Pill text content. |
| app:andesThumbnailBadgePillDefaultTextStyle | Sets pill text in uppercase: **true**, **false**. Default value: **true** |
| app:andesThumbnailBadgeText | Thumbnail text content. |

<br/>

## Constructors
| Summary |
| --- |
| AndesThumbnailBadge(context: Context, attrs: AttributeSet?)  |
| AndesThumbnailBadge(context: Context, image: Drawable, badge: AndesThumbnailBadgeComponent, thumbnailType: AndesThumbnailBadgeType) |
| AndesThumbnailBadge(context: Context, badge: AndesThumbnailBadgeComponent, thumbnailType: AndesThumbnailBadgeType, text: String) |
<br/>

##### AndesThumbnailBadge(context: Context, image: Drawable, badgeComponent: AndesThumbnailBadgeComponent, thumbnailType: AndesThumbnailBadgeType)
| Parameter | Description |
| -------- | ------- |
| context | **Context** |
| image | **Drawable**: thumbnail image content. |
| badgeComponent | **[AndesThumbnailBadgeComponent](#andesthumbnailbadgecomponent)**: badge view to show over the thumbnail. |
| type | **[AndesThumbnailBadgeType](#andesthumbnailbadgetype)**: thumbnail design type. Default value: AndesThumbnailBadgeType.Icon |
| text | **String**: thumbnail text content. |

<br/>

## Properties
| Property | Summary |
| -------- | ------- |
| image: Drawable | **get():** retrieves thumbnail displayed drawable without tint. <br/> **set(value: Drawable):** updates thumbnail drawable. |
| type: [AndesThumbnailBadgeType](#andesthumbnailbadgetype) | **get():** retrieves thumbnail design type. <br/> **set(value: AndesThumbnailBadgeType):** updates thumbnail design type. |
| badgeComponent: [AndesThumbnailBadgeComponent](#andesthumbnailbadgecomponent) | **get():** retrieves thumbnail badge component. <br/> **set(value: AndesThumbnailBadgeComponent):** updates thumbnail badge component. |

<br/>

## Related Classes

### AndesThumbnailBadgeType
Defines the possible types an [AndesThumbnailBadge](#andesthumbnailbadge) thumbnail can take.
```kotlin
sealed class AndesThumbnailBadgeType
```
| Values | Description |
| ----------- | ----------- |
| Icon | Default rounded image with a tinted icon inside<br/><img src="resources/thumbnail/iconIconPillHighlight.png" height="48"/> |
| ImageCircle | Rounded image without tint<br/><img src="resources/thumbnail/imageCircleIconPillHightlight.png" height="48"/> |
| Text | Rounded thumbnail with initials inside <br/>|
<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesThumbnailBadgeType | **fromString(value: String)**<br/> Retrieves an AndesThumbnailBadgeType that matches the string value |


### AndesThumbnailBadgeComponent
Defines the possible badges an [AndesThumbnailBadge](#andesthumbnailbadge) can show over the thumbnail.
```kotlin
sealed class AndesThumbnailBadgeComponent
```
| Values | Description |
| ----------- | ----------- |
| Pill | **Pill(color: AndesBadgeIconType, text: String?, textStyleDefault: Boolean, thumbnailSize: [AndesThumbnailBadgePillSize](#andesthumbnailbadgepillsize))**: <br/>- color: badge, outline and thumbnail tint color.<br/>- text: pill text content. Default value: **null**<br/>- textStyleDefault: sets pill text in uppercase: **true**, **false**. Default value: **true**.<br/>- thumbnailSize: defines thumbnail display size in dp. Default value: **SIZE_64**<br/><img src="resources/thumbnail/iconPillHighlight.png" height="48"/> |
| Dot |**Dot(color: AndesBadgeIconType, thumbnailSize: [AndesThumbnailBadgeDotSize](#andesthumbnailbadgedotSize))**:<br/>- color: badge, outline and thumbnail tint color.<br/>- thumbnailSize: defines thumbnail display size in dp. Default value: **SIZE_24**<br/><img src="resources/thumbnail/iconDotHighlight.png" height="48"/> |
| IconPill | **Dot(color: AndesBadgeIconType, thumbnailSize: [AndesThumbnailBadgePillSize](#andesthumbnailbadgepillsize))**:<br/>- color: badge, outline and thumbnail tint color.<br/>- thumbnailSize: defines thumbnail display size in dp. Default value: **SIZE_64**<br/><img src="resources/thumbnail/iconIconPillHighlight.png" height="48"/> |

<br/>


### AndesThumbnailBadgePillSize
Defines the possible sizes in dp that thumbnail can take when [AndesThumbnailBadgeComponent](#andesthumbnailbadgecomponent) **Pill** or **IconPill** is used.
```kotlin
enum class AndesThumbnailBadgePillSize
```
| Enum Values | Description |
| ----------- | ----------- |
| SIZE_40 | 40dp component size |
| SIZE_48 | 48dp component size |
| SIZE_56 | 56dp component size |
| SIZE_64 | 64dp component size |
| SIZE_72 | 72dp component size |
| SIZE_80 | 80dp component size |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesThumbnailBadgePillSize | **fromString(value: String)**<br/> Retrieves an AndesThumbnailBadgePillSize that matches the string value |

<br/>

### AndesThumbnailBadgeDotSize
Defines the possible sizes in dp that thumbnail can take when [AndesThumbnailBadgeComponent](#andesthumbnailbadgecomponent) **Dot** is used.
```kotlin
enum class AndesThumbnailBadgeDotSize
```
| Enum Values | Description |
| ----------- | ----------- |
| SIZE_24 | 24dp component size |
| SIZE_32 | 32dp component size |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesThumbnailBadgePillSize | **fromString(value: String)**<br/> Retrieves an AndesThumbnailBadgePillSize that matches the string value |

<br/>

## Screenshots
<img src="resources/thumbnail/thumbnailBadgeExample.png" width="300">