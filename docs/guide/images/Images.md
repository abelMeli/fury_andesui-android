# About ODR usage and asynchronous image loading

Since is highly likely we will need to load images asynchronously across several use cases for our
components, and from Andes we are unable to provide direct integration with the ODR library, we
offer an approach to download assets to use in the content **from the frontend side**.

## Modal integration

### Setting the dependencies

First we need to add the dependencies for the content resolver and the ODR modules:

```groovy
implementation "com.mercadolibre.android.uicomponents:resource-provider:$uiComponentsVersion"
implementation "com.mercadolibre.android.on.demand.resources:core:$odrVersion"
```

To enable the use in demoapps, once the libs are integrated we must initialize the remote resources 
module in our Application class:

```kotlin
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // add this line
        RemoteResources.initialize(this)
    }
}
```

### Utilizing the provider

The resource provider module has a class that we can use to retrieve resources from ODR:

```kotlin
class ResourceProvider
```

This class allows us to get images, fonts and json files from ODR.

Since for this case we need to retrieve images, the usage is the following:

```kotlin
val suspendedDrawable: Drawable? = ResourceProvider.with(context)
    .name("your_odr_image_name")
    .image()
    .from(ProviderType.REMOTE)
    .getSuspending()
```

The detail to note here is that the `getSuspending()` method is a `suspend` function.

For the use in modals, we can see that the
[AndesModalContent](https://github.com/mercadolibre/fury_andesui-android/blob/master/docs/guide/modal/AndesModal.md#andesmodalcontent) has the parameter `suspendedDrawable`,
that takes a `suspend` function that returns a `Drawable`. So, we can take advantage of the 
`resourceProvider` and use it as value to pass in this parameter:

```kotlin
val content = AndesModalContent(
    title = "Modal title",
    subtitle = "This is the text to display in the modal body",
    suspendedDrawable = {
        ResourceProvider.with(context)
            .name("your_odr_image_name")
            .image()
            .from(ProviderType.REMOTE)
            .getSuspending()
    }
)
```

From the modal side, the received `suspend` function will be handled in the corresponding 
coroutine context and the resulting drawable will be set once the resource is available.

For live examples, check our showcase app in the modals section.

For more information about the ODR library, we recommend to check the docs in
the [wiki](https://sites.google.com/mercadolibre.com/mobile/arquitectura/libs-utilitarias/on-demand-resources)
.
