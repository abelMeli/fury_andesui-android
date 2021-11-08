# Setup
The Andes UI library must be configured in order to show properly fonts, colors and currencies. You can add the following configurators in Mercado Libre and Mercado Pago frontends to have components with production style:

## Mercado Libre

You must add [font-configurator](https://github.com/mercadolibre/fury_ml-config-provider-android/tree/master/font-configurator) and [andes-configurator](https://github.com/mercadolibre/fury_ml-config-provider-android/tree/master/andes-configurator):

In your build gradle **build.gradle**
```groovy
dependencies {
    ...
    implementation "com.mercadolibre.android.configuration.provider:font-configurator:develop-$mlConfigVersion"
    implementation "com.mercadolibre.android.configuration.provider:andes-configurator:$mlConfigVersion"
    ...
}
```

In your **MainApplication**
```kotlin
class MainApplication : Application() {
   override fun onCreate() {
       	super.onCreate()
	...
	AndesConfigurator().configure(this)
	FontConfigurator().configure(this)
	...
   }
}
```

## Mercado Pago

You must add [font-configurator](https://github.com/mercadolibre/fury_mp-config-provider-android/tree/master/font-configurator) and [andes-configurer](https://github.com/mercadolibre/fury_mp-config-provider-android/tree/master/andes-configurer):

In your build gradle **build.gradle**
```groovy
dependencies {
    ...
    implementation "com.mercadopago.android.configurer:font-configurator:$mpConfigVersion"
    implementation "com.mercadopago.android.configurer:andes-configurer:$mpConfigVersion"
    ...
}
```

In your **MainApplication**
```kotlin
class MainApplication : Application() {
   override fun onCreate() {
       	super.onCreate()
	...
	AndesConfigurator().configure(this)
	FontConfigurator().configure(this)
	...
   }
}
```
