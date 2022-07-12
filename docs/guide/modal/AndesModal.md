# Andes Modal

This component represents a block of information presented in foreground, isolating the screen
content and capturing the full attention of the user.

[See the component definition in Frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/components/modal-1575576379)

[See the component specs in Figma](https://www.figma.com/file/ma8IQUYi9IS8zc8C0rAzEB/Components-specifications?node-id=3929%3A464700)

## Summary

* [Usage](#usage)
* [Card Version](#card-version)
    * [Building the card modal](#building-the-card-modal)
        * [Default card modal](#default-card-modal)
            * [Examples](#default-card-modal-examples)
        * [Carousel card modal](#carousel-card-modal)
            * [Examples](#carousel-card-modal-examples)
        * [Custom view card modal](#custom-view-card-modal)
    * [Showing the card modal](#showing-the-card-modal)
    * [Dismissing the card modal](#dismissing-the-card-modal)
* [Full Version](#full-version)
    * [Building the full modal](#building-the-full-modal)
        * [Default full modal](#default-full-modal)
            * [Examples](#default-full-modal-examples)
        * [Carousel full modal](#carousel-full-modal)
            * [Examples](#carousel-full-modal-examples)
        * [Custom view full modal](#custom-view-full-modal)
    * [Showing the full modal](#showing-the-full-modal)
    * [Dismissing the full modal](#dismissing-the-full-modal)
* [Complementary classes](#complementary-classes)
    * [AndesModalContent](#andesmodalcontent)
    * [AndesButtonGroupCreator](#andesbuttongroupcreator)
    * [AndesButtonGroupData](#andesbuttongroupdata)
    * [AndesModalInterface](#andesmodalinterface)
    * [AndesModalContentVariation](#andesmodalcontentvariation)
        * [AndesModalCardContentVariation](#andesmodalcardcontentvariation)
        * [AndesModalFullContentVariation](#andesmodalfullcontentvariation)
* [About asynchronous image loading and ODR usage](#about-asynchronous-image-loading-and-odr-usage)
* [About the use in rotating screens](#about-the-use-in-rotating-screens)

## Usage

Since this component comes with different customizations and a certain number of (non-obligatory)
available properties, we decided to go for a builder approach. All the variants are available
starting from the **AndesModal** object.

In all the cases (both card and full), the only obligatory parameter is the content, the object that 
represents the text and the asset to display. Starting from there, we will be able to add certain 
optional properties to handle the number of buttons, to make the modal dismissible, add certain
callbacks, and more.

Let's see some implementations.

## Card version

The card version of the modal inherits from a native DialogFragment. This way we can reuse the
base functionality for the `show()` and `dismiss()` methods, along with other features such as
the dismissible behavior (when dismissible, the modal closes both when clicking over the "X" button
or outside of the modal body) and the accessibility support the OS brings when dealing with this 
kind of "floating" content.

### Building the card modal

As a general note, since all the card modals inherit from the DialogFragment, we need to expose an
empty constructor required by the OS. We discourage the use of this constructor and recommend the
creation of the modal using the builder explained in the next steps.

#### Default card modal

The default variant will show a single content that will be composed by a title, a subtitle as body
text, an image asset and a content description for the image, for accessibility purposes. All this
parameters are optional, and will be passed to the modal as a part of the data
class [AndesModalContent](#andesmodalcontent)

Additionally, we can set a number of parameters through the builder interface.

```kotlin

// Base card modal with a single content and all default values
val baseCardModal = AndesModal.cardBuilder(
    AndesModalContent(
        title = "Some title",
        subtitle = "Some text to show in the body",
        assetDrawable = getMeliLogoDrawable(),
        assetContentDescription = "Mercado Libre Icon"
    )
).build()

// Default modal with a single content and all the available values set
val modifiedDefaultModal = AndesModal.cardBuilder(
    AndesModalContent(
        title = "Some title",
        subtitle = "Some text to show in the body",
        assetDrawable = getMeliLogoDrawable(),
        assetContentDescription = "Mercado Libre Icon"
    )
).withContentVariation(contentVariation) // pass an AndesModalCardContentVariation value to choose the asset image size
    .withIsHeaderFixed(isHeaderFixed) // pass true to keep the title at the top of the modal, even when scrolling down
    .withIsButtonGroupFixed(isButtonGroupFixed) // pass true to keep the button group always visible at the bottom of the modal
    .withButtonGroupCreator(buttonGroupCreator) // pass an AndesButtonGroupCreator instance with the button(s) info to display   
    .withIsDismissible(isDismissible) // pass true to show the "X" close button at the top-right corner
    .withOnDismissCallback { } // lambda block to execute when the modal is dismissed 
    .withOnModalShowCallback { } // lambda block to execute when the modal is shown
    .build()
```

##### Default card modal examples
| Header fixed, buttons fixed, dismissible | Header not fixed, buttons not fixed, not dismissible |
| - | - |
| ![vid1](https://user-images.githubusercontent.com/81258246/171020841-59724002-4c67-4357-8608-e514daafe75f.gif) | ![vid2](https://user-images.githubusercontent.com/81258246/171020847-81bf6f89-eb0c-46b3-adbd-a61fb604914d.gif) |

#### Carousel card modal

The carousel variant will show a paged list of contents that, similarly to the default variant, will
be composed by a title, a subtitle as body text, an image asset and a content description for the
image, for accessibility purposes. All this parameters are optional, and will be passed to the modal
as a part of a list of instances of [AndesModalContent](#andesmodalcontent).

Additionally, we can set a number of parameters to the modal through the builder interface.

```kotlin
// Base carousel modal with a list of contents and all default values
val baseCarouselModal = AndesModal.cardBuilder(
    listOf(
        AndesModalContent(
            title = "Some first title",
            subtitle = "Some text to show in the body of the first page",
            assetDrawable = getMeliLogoDrawable(),
            assetContentDescription = "Mercado Libre Icon"
        ),
        AndesModalContent(
            title = "Some second title",
            subtitle = "Some text to show in the body of the second page",
            assetDrawable = getMpLogoDrawable(),
            assetContentDescription = "Mercado Pago Icon"
        )
    )
).build()

// Carousel modal with a list of contents and all the available values set
val modifiedCarouselModal = AndesModal.cardBuilder(
    listOf(
        AndesModalContent(
            title = "Some first title",
            subtitle = "Some text to show in the body of the first page",
            assetDrawable = getMeliLogoDrawable(),
            assetContentDescription = "Mercado Libre Icon"
        ),
        AndesModalContent(
            title = "Some second title",
            subtitle = "Some text to show in the body of the second page",
            assetDrawable = getMpLogoDrawable(),
            assetContentDescription = "Mercado Pago Icon"
        )
    )
).withIsHeaderFixed(isHeaderFixed) // pass true to keep the title at the top of the modal, even when scrolling down
    .withContentVariation(contentVariation) // pass an AndesModalCardContentVariation value to choose the asset image size
    .withOnPageSelectedCallback { page: Int -> } // lambda block to execute when a page is selected
    .withButtonGroupCreator(buttonGroupCreator) // pass an AndesButtonGroupCreator instance with the button(s) info to display
    .withIsDismissible(isDismissible) // pass true to show the "X" close button at the top-right corner
    .withOnDismissCallback { } // lambda block to execute when the modal is dismissed
    .withOnModalShowCallback { } // lambda block to execute when the modal is shown
    .build()
```

##### Carousel card modal examples
| Header fixed, dismissible | Header not fixed, not dismissible |
| - | - |
| ![vid3](https://user-images.githubusercontent.com/81258246/171020826-3e2b90d4-3373-49b9-bb70-9185b8f9066a.gif) | ![vid4](https://user-images.githubusercontent.com/81258246/171020899-f3659825-e758-4cc1-822c-32912961da19.gif) |

#### Custom view card modal

The custom view variant offers the possibility to create a card modal with a previously created
custom view.

Additionally, we can set a number of parameters to the modal through the builder interface.

```kotlin
// Custom view modal with a view created by code and all default values
val baseCustomViewModal = AndesModal.cardBuilder(
    LinearLayout(context).apply {
        layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        orientation = LinearLayout.VERTICAL
        addView(
            AndesTextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                text = "Title created by code"
                style = AndesTextViewStyle.TitleM
                setTextColor(AndesTextViewColor.Primary)
            }
        )
        addView(
            AndesTextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                text = "Body text created by code"
                style = AndesTextViewStyle.TitleM
                setTextColor(AndesTextViewColor.Primary)
            }
        )
    }
).build()

// Custom view modal with a view created from xml and all available values set
val modifiedCustomViewModal = AndesModal.cardBuilder(
    LayoutInflater.from(context).inflate(R.layout.custom_view, null, false)
).withDescriptionForA11y(a11yDescription) // pass a String to provide a description of the modal, to be announced via Accessibility once the modal is shown.
    .withHeaderTitle(headerTitle) // optional text to show as modal header
    .withIsHeaderFixed(isHeaderFixed) // boolean value that indicates if the header title (if present) should be fixed at top when scrolling down the content
    .withIsDismissible(isDismissible) // pass true to show the "X" close button at the top-right corner
    .withButtonGroupCreator(buttonGroupCreator) // pass an AndesButtonGroupCreator instance with the button(s) info to display
    .withOnModalShowCallback { } // lambda block to execute when the modal is shown
    .withOnDismissCallback { } // lambda block to execute when the modal is dismissed
    .build()
```

### Showing the card modal

All the card modals have the method `show()` which takes a parent `FragmentActivity` in where the
dialog will be shown.

```kotlin
class ModalActivity : FragmentActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modal)

        val modifiedCardModal = AndesModal.cardBuilder(
            AndesModalContent(
              title = "title"
            )
        ).build()

        modifiedCardModal.show(this)
    }
}
```

### Dismissing the card modal

All the modals (when dismissible) will close when clicking over the "X" component.
Additionally, we can add the dismiss behavior to the bottom buttons adding the `dismiss()` call 
over the provided interface when using the [AndesButtonGroupCreator](#andesbuttongroupcreator).
Navigate to the class to see an example of use.

As an alternative, all the card modals have the `dismiss()` method that can be called on demand.

```kotlin
class ModalActivity : FragmentActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modal)

        val modifiedCardModal = AndesModal.cardBuilder(
            AndesModalContent(
              title = "title"
            )
        ).build()

        modifiedCardModal.dismiss()
    }
}
```

## Full version

The full version of the modal inherits from a native Fragment.

### Building the full modal

Similarly to the card counterpart, for the full modal we also need to expose an empty constructor
required by the OS. We discourage the use of this constructor and recommend the creation of the
modal using the builder explained in the next steps.

#### Default full modal

The default variant will show a single content that will be composed by a title, a subtitle as body
text, an image asset and a content description for the image, for accessibility purposes. All this
parameters are optional, and will be passed to the modal as a part of the data
class [AndesModalContent](#andesmodalcontent).

Additionally, we can set a number of parameters through the builder interface.

```kotlin

// Base card modal with a single content and all default values
val baseDefaultModal = AndesModal.fullBuilder(
    AndesModalContent(
        title = "Some title",
        subtitle = "Some text to show in the body",
        assetDrawable = getMeliLogoDrawable(),
        assetContentDescription = "Mercado Libre Icon"
    )
).build()

// Default modal with a single content and all the available values set
val modifiedDefaultModal = AndesModal.fullBuilder(
    AndesModalContent(
        title = "Some title",
        subtitle = "Some text to show in the body",
        assetDrawable = getMeliLogoDrawable(),
        assetContentDescription = "Mercado Libre Icon"
    )
).withContentVariation(contentVariation) // pass an AndesModalFullContentVariation value to choose the asset image shape and size
    .withIsButtonGroupFixed(isButtonGroupFixed) // pass true to keep the button group always visible at the bottom of the modal  
    .withIsHeaderFixed(isHeaderFixed) // pass true to keep the title at the top of the modal, even when scrolling down
    .withButtonGroupCreator(buttonGroupCreator) // pass an AndesButtonGroupCreator instance with the button(s) info to display   
    .withIsDismissible(isDismissible) // pass true to show the "X" close button at the top-right corner
    .withOnDismissCallback { } // lambda block to execute when the modal is dismissed 
    .withOnModalShowCallback { } // lambda blck to execute when the modal is shown
    .build()
```

##### Default full modal examples
| Header fixed, buttons fixed, dismissible | Header not fixed, buttons not fixed, not dismissible |
| - | - |
| ![vid5](https://user-images.githubusercontent.com/81258246/171020813-a3250cf9-d713-46ed-82b1-b90290add009.gif) | ![vid6](https://user-images.githubusercontent.com/81258246/171020821-7f377382-5b37-4e15-ac53-8de374113ec8.gif) |

#### Carousel full modal

The carousel variant will show a paged list of contents that, similarly to the default variant, will
be composed by a title, a subtitle as body text, an image asset and a content description for the
image, for accessibility purposes. All this parameters are optional, and will be passed to the modal
as a part of a list of instances of [AndesModalContent](#andesmodalcontent).

Additionally, we can set a number of parameters to the modal through the builder interface.

```kotlin
// Base carousel modal with a list of contents and all default values
val baseCarouselModal = AndesModal.fullBuilder(
    listOf(
        AndesModalContent(
            title = "First title",
            subtitle = "Some text to show in the body of the first page",
            assetDrawable = getMeliLogoDrawable(),
            assetContentDescription = "Mercado Libre Icon"
        ),
        AndesModalContent(
            title = "Second title",
            subtitle = "Some text to show in the body of the second page",
            assetDrawable = getMpLogoDrawable(),
            assetContentDescription = "Mercado Pago Icon"
        )
    )
).build()

// Carousel modal with a list of contents and all the available values set
val modifiedCarouselModal = AndesModal.fullBuilder(
    listOf(
        AndesModalContent(
            title = "Some first title",
            subtitle = "Some text to show in the body of the first page",
            assetDrawable = getMeliLogoDrawable(),
            assetContentDescription = "Mercado Libre Icon"
        ),
        AndesModalContent(
            title = "Some second title",
            subtitle = "Some text to show in the body of the second page",
            assetDrawable = getMpLogoDrawable(),
            assetContentDescription = "Mercado Pago Icon"
        )
    )
).withContentVariation(contentVariation) // pass an AndesModalFullContentVariation value to choose the asset image size
    .withIsHeaderFixed(isHeaderFixed) // pass true to keep the title at the top of the modal, even when scrolling down
    .withOnPageSelectedCallback { page: Int -> } // lambda block to execute when a page is selected
    .withButtonGroupCreator(buttonGroupCreator) // pass an AndesButtonGroupCreator instance with the button(s) info to display
    .withIsDismissible(isDismissible) // pass true to show the "X" close button at the top-right corner
    .withOnDismissCallback { } // lambda block to execute when the modal is dismissed
    .withOnModalShowCallback { } // lambda block to execute when the modal is shown
    .build()
```

##### Carousel full modal examples
| Header fixed, dismissible | Header not fixed, not dismissible |
| - | - |
| ![vid7](https://user-images.githubusercontent.com/81258246/171021377-2c4bf5b7-8ee8-49c7-a875-a64eba9377e0.gif) | ![vid8](https://user-images.githubusercontent.com/81258246/171021406-642d387f-ca02-4a68-a348-c919f3f7d82b.gif) |

#### Custom view full modal

The custom view variant offers the possibility to create a full modal with a previously created
custom view.

Additionally, we can set a number of parameters to the modal through the builder interface.

```kotlin
// Custom view modal with a view created by code and all default values
val baseCustomViewModal = AndesModal.fullBuilder(
    LinearLayout(context).apply {
        layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        orientation = LinearLayout.VERTICAL
        addView(
            AndesTextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                text = "Title created by code"
                style = AndesTextViewStyle.TitleM
                setTextColor(AndesTextViewColor.Primary)
            }
        )
        addView(
            AndesTextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                text = "Body text created by code"
                style = AndesTextViewStyle.TitleM
                setTextColor(AndesTextViewColor.Primary)
            }
        )
    }
).build()

// Custom view modal with a view created from xml and all available values set
val modifiedCustomViewModal = AndesModal.fullBuilder(
    LayoutInflater.from(context).inflate(R.layout.custom_view, null, false)
).withHeaderTitle(headerTitle) // optional text to show as modal header
    .withAccessibilityModalDescription(modalDescription) // pass a String to provide a description of the modal, to be announced via Accessibility once the modal is shown.
    .withIsHeaderFixed(isHeaderFixed) // boolean value that indicates if the header title (if present) should be fixed at top when scrolling down the content
    .withIsDismissible(isDismissible) // pass true to show the "X" close button at the top-right corner
    .withButtonGroupCreator(buttonGroupCreator) // pass an AndesButtonGroupCreator instance with the button(s) info to display
    .withOnDismissCallback { } // lambda block to execute when the modal is dismissed
    .withOnModalShowCallback { } // lambda block to execute when the modal is shown
    .build()
```

### Showing the full modal

All the full modals have a `show()` utility method, which will take an activity and a container Id
in which the fragment will be displayed. This method internally will get the *fragmentManager* from
the activity and use it to begin a transaction and commit the fragment **as replacement** into the
passed container id. We highlight this behavior since it will be probable that, when using the
component, the needed stacking/navigation strategy will be different. So (once again), as the
component inherits from Fragment, it can be used directly with a custom *fragmentManager* with the
config needed for each case. This way you can achieve more fine-grained control over the component.

We show examples of different cases:

- With the provided `show()` method

```kotlin
class ModalActivity : FragmentActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modal)

        val defaultFullModal = AndesModal.fullBuilder(
            AndesModalContent(
              title = "title"
            )
        ).build()

        defaultFullModal.show(this, R.id.modal_container)
    }
}
```

- With a custom implementation

```kotlin
class ModalActivity : FragmentActivity {

    private const val MY_MODAL = "default_full_modal"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modal)

        val defaultFullModal = AndesModal.fullBuilder(
            AndesModalContent(
              title = "title"
            )
        ).build()

        supportFragmentManager
            .beginTransaction()
            .addToBackStack(MY_MODAL)
            .add(R.id.modal_container, defaultFullModal)
            .commit()
    }
}
```

### Dismissing the full modal

Unlike the DialogFragment class, the Fragment class does not provide a default `dismiss()` method.
Thus, we offer an auxiliary method in which we internally get the activity and
call `onBackPressed()` over it. Similarly to the `show()` method, we also encourage you to use 
your own *fragmentManager* to be able to handle the fragment stack in a more granular way.

## Complementary classes

### AndesModalContent

This data class represents the content to show in the modal. This is used both in the card and the
full version.

```kotlin
data class AndesModalContent(
    val title: CharSequence? = null,
    val subtitle: CharSequence? = null,
    val assetDrawable: Drawable? = null,
    val assetContentDescription: String? = null,
    val suspendedDrawable: (suspend () -> Drawable?)? = null
)
```

### AndesButtonGroupCreator

This interface allows us to add a buttonGroup to show inside the modal, in the bottom position.
We can add up to three buttons, each one with his own behavior, since the buttonGroup component
will take a list of `AndesButton` in his constructor.
The [AndesModalInterface](#andesmodalinterface) provides a useful `dismiss()` method that can be
added in the buttons click listeners for dismissing the modal additionally to the desired 
actions to run when clicking over them.
The [AndesButtonGroupData](#andesbuttongroupdata) will contain the mentioned `AndesButtonGroup`
additionally with an optional index that will represent the main button.

```kotlin
val buttonGroupCreator = object : AndesButtonGroupCreator {
    override fun create(modalInterface: AndesModalInterface): AndesButtonGroupData {
        return AndesButtonGroupData(
            buttonGroup = AndesButtonGroup(
                context = context,
                buttonList = listOf(
                    AndesButton(context, buttonText = "Button 1").apply {
                        setOnClickListener {
                            // handle click events

                            // dismiss modal after click over this button
                            modalInterface.dismiss()
                        }
                    },
                    AndesButton(context, buttonText = "Button 2").apply {
                        setOnClickListener {
                            // handle click events

                            // dismiss modal after click over this button
                            modalInterface.dismiss()
                        }
                    }
                ),
                distribution = AndesButtonGroupDistribution.VERTICAL
            ),
            mainAction = null
        )
    }
}
```

### AndesButtonGroupData

This class contains the obligatory `AndesButtonGroup` to be returned in the [creator](#andesbuttongroupcreator).
Additionally, we can choose an index to represent a main action for the buttons we passed in the
`AndesButtonGroup`. This is an optional value, and will only be used when paired with a carousel 
modal variant (either card or full). This will allow us to animate the chosen main button hierarchy
from a Quiet hierarchy to a Loud one when reaching the last page of the carousel.
We add a code sample, and the corresponding gif when using this data to create a card carousel modal.

```kotlin
val myButtonGroup = AndesButtonGroup(
    context = context,
    buttonList = listOf(
        AndesButton(context, buttonText = "Button 0").apply {
            setOnClickListener {
                // handle click events
            }
        },
        AndesButton(context, buttonText = "Button 1").apply {
            setOnClickListener {
                // handle click events
            }
        }
    )
)

val myButtonGroupData = AndesButtonGroupData(
    buttonGroup = myButtonGroup,
    mainAction = 0
)

```
| Modal Card Carousel with two buttons and main action = 0 |
| -------- |
| ![modal_button_animation](https://user-images.githubusercontent.com/81258246/175602817-60e2ab10-3deb-4f87-a45f-390f03302d9a.gif) |


### AndesModalInterface

This interface is present in the [AndesButtonGroupCreator](#andesbuttongroupcreator), as argument
for the `create()`method. It provides a `dismiss()` method to be used as needed when creating the
buttons that the modal will show at the bottom.

### AndesModalContentVariation

Enum class that represents the available display types for the asset in each modal. Whether the 
modal is card or full, we can show the asset with a certain number of configurations, such as 
a plain image with defined dimensions, or an 80x80 thumbnail.

#### AndesModalCardContentVariation

| Enum value | Summary |
| -------- | ------- |
| NONE | The modal will not show an asset. |
| IMAGE | The modal will show a full-width image with a 16:9 aspect ratio. |
| SMALL_ILLUSTRATION | The modal will show an image with a 224x80dp dimension |
| MEDIUM_ILLUSTRATION | The modal will show an image with a 224x128dp dimension  |
| THUMBNAIL | The modal will show a SIZE_80 thumbnail (80x80dp dimension) |

##### Examples

| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NONE&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;IMAGE&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | SMALL_ILLUSTRATION | MEDIUM_ILLUSTRATION | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;THUMBNAIL&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |
| -------- | ------- | ------- | ------- | ------- |
| ![card-none](https://user-images.githubusercontent.com/81258246/171021557-2fa685c5-ed6c-4ace-9da6-93ad4e46e6b1.png) | ![card-image](https://user-images.githubusercontent.com/81258246/171021563-472907ff-4f78-4805-be74-88dfabb61f56.png) | ![card-illust-small](https://user-images.githubusercontent.com/81258246/171021560-51e7c7ae-6ea7-4fab-969a-7f09804e0091.png) | ![card-illust-med](https://user-images.githubusercontent.com/81258246/171021559-10a96702-a948-4bab-b37b-4a6f5318e261.png)  | ![card-thumbnail](https://user-images.githubusercontent.com/81258246/171021561-6f6c35e5-48d4-4d2f-95c1-249ff87f32ab.png) |

#### AndesModalFullContentVariation

| Enum value | Summary |
| -------- | ------- |
| NONE | The modal will not show an asset. Additionally, it will align the title text to the left. |
| SMALL_ILLUSTRATION | The modal will show an image with a 320x80dp dimension |
| MEDIUM_ILLUSTRATION | The modal will show an image with a 320x128 dimension |
| LARGE_ILLUSTRATION | The modal will show an image with a 320x160 dimension |
| THUMBNAIL | The modal will show a SIZE_80 thumbnail (80x80dp dimension) |

As a note for the full variants of content variation, when the value chosen is NONE, the title text
(when present) will be left-aligned, while with all the other variations, it will be center-aligned.

##### Examples

| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NONE&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| SMALL_ILLUSTRATION&nbsp; | MEDIUM_ILLUSTRATION | &nbsp;&nbsp;LARGE_ILLUSTRATION&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;THUMBNAIL&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |
| -------- | ------- | ------- | ------- | ------- |
| ![full-none](https://user-images.githubusercontent.com/81258246/171021540-42423bcb-4600-45a2-bb5f-f8d76ce8cc30.png) | ![full-illust-small](https://user-images.githubusercontent.com/81258246/171021556-169a1726-6cbf-4587-83de-8262ab6c02fa.png) | ![full-illust-med](https://user-images.githubusercontent.com/81258246/171021552-d4c15558-a2fd-4d8d-8fbd-95d418598196.png) | ![full-illust-large](https://user-images.githubusercontent.com/81258246/171021550-46d97824-afcd-4e74-a8df-a34b7eca8b5c.png) | ![full-thumbnail](https://user-images.githubusercontent.com/81258246/171021547-12042a0b-0de3-46fe-8473-de1eea1fd897.png) |

## About asynchronous image loading and ODR usage

Since is highly likely we will need to load images asynchronously inside the modal, and from Andes
we are unable to provide direct integration with the ODR library, we offer an approach on how to
take advantage of the resources this library offers us implementing them **from the frontend side**. 

[Click here](https://github.com/mercadolibre/fury_andesui-android/blob/master/docs/guide/images/Images.md#modalintegration) to navigate to this section and to see how to integrate it with
modals.

## About the use in rotating screens

Note that this is only needed when the modal will be displayed in an activity that can take both
portrait and landscape orientations. If the activity is only configured to be shown in portrait
mode, skip this section.

Keep in mind that since the modals are fragments with their own lifecycle, to handle the process of
recreating the content when a screen rotation occurs we should add this line in the activity where
the modal will be shown, inside the manifest:

```xml
<activity
    android:name=".ModalActivity"
    android:configChanges="screenSize|orientation" /> <!--add this line-->
```
