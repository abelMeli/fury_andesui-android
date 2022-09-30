# AndesFeedbackScreenView

AndesFeedbackScreenView is a full-screen component that informs about the state or outcome of certain tasks or flows.
[See Andes UI component in frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/patterns/feedback-screens)

```kotlin
class AndesFeedbackScreenView
```

Basic Sample Programatically

```kotlin
AndesFeedbackScreenView(
    context = context,
    header = header
)
```

<br/>

## Constructors
| Summary |
| --- |
| [AndesFeedbackScreenView](#andesfeedbackscreencontext-context-type-andesfeedbackscreentype-header-andesfeedbackscreenheader-body-view-actions-andesfeedbackscreenactions)(context: Context, header: [AndesFeedbackScreenHeader](#andesfeedbackscreenheader), type: [AndesFeedbackScreenType](#andesfeedbackscreentype) = AndesFeedbackScreenType.Congrats, body: View? = null, actions: [AndesFeedbackScreenActions](#andesfeedbaclscreenactions)? = null) |
<br/>

##### AndesFeedbackScreenView(context: Context, header: AndesFeedbackScreenHeader, type: AndesFeedbackScreenType, body: View?, actions: AndesFeedbackScreenActions?)
| Parameter | Description |
| -------- | ------- |
| context | **Context**|
| header: **[AndesFeedbackScreenHeader](#andesfeedbackscreenheader)** | class that contains the header texts and the asset to show in the thumbnail. |
| type: **[AndesFeedbackScreenType](#andesfeedbackscreentype)** | class that defines if the component will be Simple or Congrats. Default value is **AndesFeedbackScreenType.Congrats**. |
| body: **View?** | View to display in the body of the component. Default value is **null**.  |
| actions: **[AndesFeedbackScreenActions](#andesfeedbackscreenactions)?** | parameter that according to its value defines the two possible actionable views the component can take: the top-right closing button and the bottom button. Default value is **null**. |

<br/>

## Functions
| Return type | Method |
| -------- | ------- |
| Unit | **setFeedbackScreenAsset(feedbackImage: AndesFeedbackScreenAsset?)**<br/> Replaces current feedback image with the new [feedbackImage] asset. If you select an AndesThumbnailBadgeType.Icon, the [drawable] will be colorized as the previously selected status (green for SUCCESS, orange for WARNING, red for ERROR). |
| Unit | **setFeedbackScreenHeader(header: AndesFeedbackScreenHeader)**<br/> Replaces current feedback header with the new [feedbackScreenHeader]. |
| Unit | **setFeedbackScreenActions(actions: AndesFeedbackScreenActions)**<br/> Replaces current feedback actions with the new [feedbackScreenActions]. |
| Unit | **setFeedbackScreenType(type: AndesFeedbackScreenType)**<br/> Replaces current feedback type with the new [feedbackScreenType]. |
| Unit | **setFeedbackScreenBody(body: View?)**<br/> Replaces current feedback body with the new [View]. |
<br/>

## About use in viewPagers
Keep in mind that in some cases, this component sets a custom color in the statusBar. Since this config is run when the component is attached to the window (see: [onAttachedToWindow](https://developer.android.com/reference/android/view/View#onAttachedToWindow()) official documentation), and the viewPager can attach its views before showing it (see: viewPager [offscreenPageLimit](https://developer.android.com/reference/android/support/v4/view/ViewPager#setOffscreenPageLimit(int)) official documentation), we recommend NOT to use this component inside a viewpager.

<br/>

## Related Classes

### [AndesFeedbackScreenHeader](#andesfeedbackscreenheader)
This class contains the text values and the image the header can take.
```kotlin
data class AndesFeedbackScreenHeader
```
| Parameter | Description |
| --------- | ------------- |
| feedbackText: **[AndesFeedbackScreenText](#andesfeedbackscreentext)** | contains the four texts the header can display: title, highlight, description, overline. |
| feedbackImage: **[AndesFeedbackScreenAsset](#andesfeedbackscreenasset)** | contains the drawable image and the thumbnail badge type to display in the header. |

<br/>

### [AndesFeedbackScreenText](#andesfeedbackscreentext)
Contains the string values of the texts in the header.
The only obligatory parameter is the title.
The header can take two different text configurations: **overline + title** or **title + highlight + description**.
This configuration is automatically set by choosing the corresponding constructor.
```kotlin
data class AndesFeedbackScreenText
```
Basic samples
```kotlin
AndesFeedbackScreenText(
    title = title,
    overline = overline
)
```
```kotlin
AndesFeedbackScreenText(
    title = title,
    description = description,
    highlight = highlight
)
```
| Parameter | Description |
| --------- | ------------- |
| title: **String** | String resource that will be displayed as title in the header. |
| description: **[AndesFeedbackScreenTextDescription](#andesfeedbackscreentextdescription)?** | Contains the text to be displayed as description, and the optional body links to add to the text. Default value is **null**. |
| highlight: **String?** | String resource that will be displayed as highlight in the header. Default value is **null**. |
| overline: **String?** | String resource that will be displayed as overline in the header. Default value is **null**. |

#### Text Examples

| Config with overline | Config with description |
| --------- | ------------- |
| ![header-text-overline](https://user-images.githubusercontent.com/81258246/133441438-d72d6708-3d7a-4d5d-8f81-b2082f71c198.png) | ![header-text-with-description](https://user-images.githubusercontent.com/81258246/133441757-5061a969-bb86-4dfa-87d3-bf8132048d72.png) |

<br/>

### [AndesFeedbackScreenTextDescription](#andesfeedbackscreentextdescription)
Contains the text to be displayed as description, and the optional body links to add to the text.
```kotlin
data class AndesFeedbackScreenTextDescription
```
| Parameter | Description |
| --------- | ------------- |
| text: **String** | String resource that will be displayed as description in the header. |
| links: **AndesBodyLinks?** | Object that contains the list of indexes that will be taken as links in the text. Default value is **null**. |

<br/>

### [AndesFeedbackScreenAsset](#andesfeedbackscreenasset)
Defines the possible view assets the header can take.
```kotlin
sealed class AndesFeedbackScreenAsset
```
| Value | Description |
| --------- | ------------- |
| Thumbnail(val image: Drawable, val badgeType: AndesThumbnailBadgeType) | Gives a Thumbnail asset with the provided drawable and badge type |
| Illustration(val image: Drawable, val size: AndesFeedbackScreenIllustrationSize) | Gives a Illustration asset with the provided drawable and size |
| TextThumbnail(val text: String) | Gives a Thumbnail asset with letters instead of an image.
<br/>

### [AndesFeedbackScreenType](#andesfeedbackscreentype)
Defines the possible types the component can take.
```kotlin
sealed class AndesFeedbackScreenType
```
When there is no body to display, the **Congrats** type is similar to the **Simple** variant with **SUCCESS** status.

| Value | Description |
| --------- | ------------- |
| Congrats | Gives congrats type. |
| Simple(val color: [AndesFeedbackScreenColor](#andesfeedbackscreencolor)) | Gives simple type with the provided color. |

#### Type Examples

| Simple - Success - no body | Simple - Success - with body | Congrats - no body | Congrats - with body |
| --------- | ------------- | ------------- | ------------- |
| ![simple-success-nobody](https://user-images.githubusercontent.com/81258246/133445155-dd1e6020-1aa1-4a07-86f4-4e56b2ed8e5d.png) | ![simple-success-withbody](https://user-images.githubusercontent.com/81258246/133445153-ba78bb16-78cb-428e-b8d5-8ad30e8c6b90.png) | ![congrats-nobody](https://user-images.githubusercontent.com/81258246/133445150-5d56d806-291f-4874-b1ac-3147c0f47960.png) | ![congrats-body](https://user-images.githubusercontent.com/81258246/133445140-87c0c280-7c45-492c-b2df-d97316e2166c.png) |

<br/>

### [AndesFeedbackScreenColor](#andesfeedbackscreencolor)
Defines the possible color the component can take.
Only applies when using the Simple type. (As reminder, the Congrats type is always SUCCESS)
```kotlin
enum class AndesFeedbackScreenColor
```
| Value | Description |
| --------- | ------------- |
| GREEN | Gives green status. |
| ORANGE | Gives orange status. |
| RED | Gives red status. |

#### Status Examples

| GREEN | ORANGE | RED |
| --------- | ------------- | ------------- |
| ![success status](https://user-images.githubusercontent.com/81258246/133446137-46f95b6c-1f3f-4e23-89ab-8c6a15e74bf8.png) | ![orange status](https://user-images.githubusercontent.com/81258246/133446131-5e88639b-5210-4157-b355-d68f47d29ded.png) | ![red status](https://user-images.githubusercontent.com/81258246/133446121-2e9555bb-4bc5-4915-9763-154048636875.png) |

<br/>

### [AndesFeedbackScreenActions](#andesfeedbackscreenactions)
Defines the actions to perform on the close and button click.
```kotlin
data class AndesFeedbackScreenActions
```
##### New constructor
To create a feedback screen with a group of buttons with a callback for the close button.

| Parameter | Description |
| --------- | ------------- |
| buttonGroup: [AndesButtonGroup](/buttongroup/AndesButtonGroup.md) | instance of the AndesButtonGroup component |
| closeCallback: **View.OnClickListener?** | function to invoke when the close button is clicked. Default value is **null** |

<br/>
To create a feedback screen without buttons with a callback for the close button.
<br/>
<br/>

| Parameter | Description |
| --------- | ------------- |
| closeCallback: **View.OnClickListener** | function to invoke when the close button is clicked. |

<br/>

##### Deprecated constructor

| Parameter | Description |
| --------- | ------------- |
| button: **[AndesFeedbackScreenButton](#andesfeedbackscreenbutton)?** | Object that contains the string value for the button text, and the listener for the button click. Default value is **null** |
| closeCallback: **View.OnClickListener?** | function to invoke when the close button is clicked. Default value is **null** |

<br/>

### [AndesFeedbackScreenButton](#andesfeedbackscreenbutton)
Contains the string value for the button text, and the listener for the button click.
```kotlin
data class AndesFeedbackScreenButton
```
| Parameter | Description |
| --------- | ------------- |
| text: **String** | String value to pass as text to the button |
| onClick: **View.OnClickListener** | click listener to pass as callback for the button click |

<br/>

## Screenshots
| Simple-success (no body) | Simple-warning (no body) | Simple-error (with body) | Congrats (with body) |
| --------- | --------- | --------- | --------- |
| ![success](https://user-images.githubusercontent.com/81258246/133291465-bbd8c697-f1ec-41d5-b2fc-678bafbda199.png)|![warning](https://user-images.githubusercontent.com/81258246/133291467-1f71b97f-4636-417b-aa46-36538422b0d8.png)|![error](https://user-images.githubusercontent.com/81258246/133291455-a1bd1eb1-7293-490b-8e92-f181f3251799.png)|![congrats](https://user-images.githubusercontent.com/81258246/133291462-2ef06815-0e68-4a92-8eb4-3a45d185c20f.png) |

## Webview Experience

Currently AndesFeedbackScreen in webviews has a different behaviour in comparison with the mobile native one. Despite of this, we still recommend using AndesFeedbackScreen because it is accessibile and new features/fixes for this component will come in the future (including the difference in behaviour shown below). Let's show some examples to illustrate webview vs [native](#screenshots) feedback screen:

| Simple-success (no body) | Simple-success (with body) | Congrats (with body) | Congrats (with body scrolled) |
| --- | --- | --- | --- |
| <img width="300" alt="Captura de Pantalla 2021-11-09 a la(s) 15 33 54" src="https://user-images.githubusercontent.com/58984116/141998408-2d0a9e9b-6ed7-454c-a83e-1353421fd358.png"> | <img width="300" alt="Captura de Pantalla 2021-11-09 a la(s) 15 32 12" src="https://user-images.githubusercontent.com/58984116/141998402-5e70684a-16b6-4155-93f2-eca73743e314.png"> | <img width="300" alt="Captura de Pantalla 2021-11-09 a la(s) 15 18 59" src="https://user-images.githubusercontent.com/58984116/141998396-d5f7c0c1-e0ea-41f1-a7f5-f2dbb08a60e9.png"> | <img width="300" alt="Captura de Pantalla 2021-11-09 a la(s) 15 18 25" src="https://user-images.githubusercontent.com/58984116/141998392-62caf273-8e2d-4c61-8d64-ad7f9c5b5eab.png"> |

### Differences
- Simple (no body) feedback screen is not centered vertically because there is a white toolbar at the top that can not be hidden in webviews.
- Icon close can be added in all feedback screens but it is algined to the left when it should be aligned to the right of the screen.
- Statusbar color can not be changed to green for Congrats type.
- Top space between statusbar and header is bigger than the component specification because of the toolbar.
- When a feedback screen with body is scrolled user can see the toolbar that should not be there.
