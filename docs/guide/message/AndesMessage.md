# Introduction

The AndesMessage is a highlighted message block with context info.
[See Andes UI component in frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/components/message)

```kotlin
class AndesMessage : CardView
```

Basic Sample Programatically

```kotlin
AndesMessage(
    context = context,
    hierarchy = AndesMessageHierarchy.QUIET,
    type = AndesMessageType.WARNING,
    body = "body text",
    title = "title text",
    isDismissable = false,
    bodyLinks = null,
    thumbnail = null
)
```

Basic Sample XML

```xml
<...xmlns:app="http://schemas.android.com/apk/res-auto".../>

<com.mercadolibre.android.andesui.message.AndesMessage
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="@dimen/message_margin_vertical"
            app:andesMessageBodyText="@string/body_text"
            app:andesMessageDismissable="true"
            app:andesMessageHierarchy="quiet"
            app:andesMessageType="warning"
            app:andesMessageTitleText="@string/title_text"
            app:andesMessageThumbnail="@drawable/icon" />
```
<br/>

## XML Attributes
| Property | Summary |
| -------- | ------- |
| app:andesMessageBodyText | Text to display in the body of the message. |
| app:andesMessageDismissable | boolean value used to indicate if the component can be dismissed by clicking the top-right corner X |
| app:andesMessageHierarchy | property for handling the message background style. Possible values: **quiet**, **loud** |
| app:andesMessageType | property for handling the feedback needed through color. Possible values: **neutral**, **success**, **warning**, **error** |
| app:andesMessageTitleText | text to display in the title of the message. |
| app:andesMessageThumbnail | drawable to display in the message. |

<br/>

## Constructors
| Summary |
| --- |
| AndesMessage(context: Context, attrs: AttributeSet?) |
| AndesMessage(context: Context, attrs: AttributeSet?, defStyleAttr: Int) |
| [AndesMessage](#andesmessagecontext-context-hierarchy-andesmessagehierarchy-type-andesmessagetype-body-string-title-string-isDismissable-boolean-bodyLinks-andesbodylinks-thumbnail-drawable)(context: Context, hierarchy: [AndesMessageHierarchy](#andesmessagehierarchy), type: [AndesMessageType](#andesmessagetype), body: String, title: String?, isDismissable: Boolean, bodyLinks: [AndesBodyLinks](#andesbodylinks), thumbnail: Drawable?)|

<br/>

##### AndesMessage(context: Context, hierarchy: AndesMessageHierarchy, type: AndesMessageType, body: String, title: String?, isDismissable: Boolean, bodyLinks: AndesBodyLinks, thumbnail: Drawable?)
| Parameter | Description |
| -------- | ------- |
| context | **Context**|
| hierarchy | **[AndesMessageHierarchy](#andesmessagehierarchy)**: set the message hierarchy. Default hierarchy is **LOUD**. |
| type | **[AndesMessageType](#andesmessagetype)**: set the message type according to the color and the feedback needed. Default type is **NEUTRAL** |
| body | **String**: set the body text for the message. This value cannot be null. |
| title | **String?**: set the title text for the message. Default type is **null** |
| isDismissable | **Boolean**: sets the dismiss check in the top right corner. Default type is **false** |
| bodyLinks | **[AndesBodyLinks](#andesbodylinks)**: sets the links for the AndesMessage. Default type is **null** |
| thumbnail | **Drawable?**: sets the drawable to show in the component body. Default type is **null** |

<br/>

## Properties
| Property | Summary |
| -------- | ------- |
| hierarchy: AndesMessageHierarchy | **get():** retrieves hierarchy value. <br/> **set(value: AndesMessageHierarchy):** updates component hierarchy. |
| type: AndesMessageType | **get():** retrieves component type value. <br/> **set(value: AndesMessageType):** updates component type value. |
| body: String | **get():** retrieves body text value. <br/> **set(value: String):** updates body text value. |
| title: String? | **get():** retrieves title string, or null if is not set. <br/> **set(value: String?):** updates component title text. |
| isDismissable: Boolean | **get():** retrieves true or false according if the component can be dismissed or not. <br/> **set(value: Boolean):** updates component. |
| bodyLinks: AndesBodyLinks| **get():** retrieves body links, or null if is not set. <br/> **set(value: AndesBodyLinks):** updates link values. |
| thumbnail: Drawable? | **get():** retrieves the drawable object shown in the component body, or null if is not set. <br/> **set(value: Drawable?):** updates component drawable shown in the body. |

<br/>

## Other methods
| Method | Summary |
| -------- | ------- |
| setupPrimaryAction(text: String, onClickListener: OnClickListener) | adds a loud hierarchy AndesButton to the message with the text and listener action provided. <br/><img src="https://user-images.githubusercontent.com/81258246/122607629-40061180-d051-11eb-882c-bf240a6e59da.jpg" height="100"/> |
| hidePrimaryAction() | hides the primary action AndesButton (and the secondary action AndesButton if is present). |
| setupSecondaryAction(text: String, onClickListener: OnClickListener) | adds a quiet hierarchy AndesButton to the message with the text and listener action provided. This method is only available if there is already a primary action set. <br/><img src="https://user-images.githubusercontent.com/81258246/122607630-409ea800-d051-11eb-8ac1-c0bd5be4acb5.jpg" height="100"/> |
| hideSecondaryAction() | hides the secondary action AndesButton. |
| setupLinkAction(text: String, onClickListener: OnClickListener) | adds a clickable link with the text and listener action provided to the bottom of the message. This method is only available if there is not a primary action already set. <br/><img src="https://user-images.githubusercontent.com/81258246/122607628-3f6d7b00-d051-11eb-95a1-b1eeba8b09d3.jpg" height="100"/> |
| hideLinkAction() | hides the clickable link. |
| setupDismissableCallback(onClickListener: OnClickListener) | sets a callback to be called when the message is dismissed. <br/><img src="https://user-images.githubusercontent.com/81258246/122607627-3f6d7b00-d051-11eb-9685-2cf578e353c7.jpg" height="100"/>|
| setupThumbnail(thumbnailImage: Drawable?)| sets a new drawable resource to the thumbnail. |

## Related Classes

### AndesMessageHierarchy
Defines the backgrounds the component can use.
```kotlin
enum class AndesMessageHierarchy
```
| Enum Values | Description |
| ----------- | ----------- |
| QUIET | Changes the background to the quiet variant<br/><img src="https://user-images.githubusercontent.com/81258246/122607625-3ed4e480-d051-11eb-928c-9260831f2b2e.jpg" height="100"/> |
| LOUD | Changes the background to the loud variant<br/><img src="https://user-images.githubusercontent.com/81258246/122607624-3e3c4e00-d051-11eb-9aa5-359de7041d44.jpg" height="100"/> |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesMessageHierarchy | **fromString(value: String)**<br/> Retrieves an AndesMessageHierarchy that matches the string value |

<br/>

### AndesMessageType
Defines the possible feedback colors the component can take.
```kotlin
enum class AndesMessageType
```
| Enum Values | Description |
| ----------- | ----------- |
| NEUTRAL | Changes the feedback colors to the neutral version<br/><img src="https://user-images.githubusercontent.com/81258246/122607625-3ed4e480-d051-11eb-928c-9260831f2b2e.jpg" height="100"/><img src="https://user-images.githubusercontent.com/81258246/122607624-3e3c4e00-d051-11eb-9aa5-359de7041d44.jpg" height="100"/> |
| SUCCESS | Changes the feedback colors to the success version<br/><img src="https://user-images.githubusercontent.com/81258246/122607602-3aa8c700-d051-11eb-9967-78117f827a85.jpg" height="100"/><img src="https://user-images.githubusercontent.com/81258246/122607620-3da3b780-d051-11eb-9f95-8bc504c24762.jpg" height="100"/> |
| WARNING | Changes the feedback colors to the warning version<br/><img src="https://user-images.githubusercontent.com/81258246/122607605-3bd9f400-d051-11eb-9a6a-2625ec155678.jpg" height="100"/><img src="https://user-images.githubusercontent.com/81258246/122607618-3da3b780-d051-11eb-9f9a-1c235c4521d3.jpg" height="100"/>|
| ERROR | Changes the feedback colors to the error version<br/><br/><img src="https://user-images.githubusercontent.com/81258246/122607608-3c728a80-d051-11eb-9af2-e79787e2213c.jpg" height="100"/><img src="https://user-images.githubusercontent.com/81258246/122607615-3d0b2100-d051-11eb-8268-37ade0a63653.jpg" height="100"/> |

<br/>

#### Functions
| Return type | Method |
| -------- | ------- |
| AndesMessageType | **fromString(value: String)**<br/> Retrieves an AndesMessageType that matches the string value |

<br/>

### AndesBodyLinks
class used to keep all body link values needed and the listener which will be fired in the onClick event.
```kotlin
enum class AndesBodyLinks
```
| Property | Description |
| ----------- | ----------- |
| links: List<AndesBodyLink> | list of available body links |
| listener: (index: Int) -> Unit | function type value |

<br/>

### AndesBodyLink
class used to set correctly the clickable text in the message body.
```kotlin
enum class AndesBodyLink
```
| Property | Description |
| ----------- | ----------- |
| startIndex: Int | start index for the link text |
| endIndex: Int | end index for the link text |

#### Functions
| Return type | Method |
| -------- | ------- |
| Boolean | **isValidRange(text: SpannableString)**<br/> method to evaluate if the current values of the AndesBodyLink are correct to set the spannableString. |


## Screenshots
<img width="448" alt="Andes Message Image" src="https://user-images.githubusercontent.com/51792499/73974714-9878bb00-4903-11ea-86f0-ba48211fd25c.png">
<img width="451" alt="Andes Message Image" src="https://user-images.githubusercontent.com/51792499/73974742-a8909a80-4903-11ea-9a11-c02b0206521f.png">
