# Fonts
The Andes UI library relies on having the fonts declared in `res/font` folder. Those font files must be called `andesui_font_regular` and `andesui_font_semibold`. They can have the OTF or TTF extension because Andes UI is compatible with both formats.

To be sure, let's say that your `res/font` folder should look like this: 

![Andes UI Font Folder](https://user-images.githubusercontent.com/35068259/72909489-decff680-3d15-11ea-8476-18f25dbae082.png)

You can see how we are setting font configuration for Mercado Pago in [our configurator](https://github.com/mercadolibre/fury_ml-config-provider-android/tree/master/font-configurator/src/main/res/font). You can also see how is implemented in [Mercado Envios](https://github.com/mercadolibre/fury_shipping-logistics-android/tree/master/melidriverapp/src/main/res/font).

# Colors
By default, Andes UI library uses Mercado Libre's default colors. If you want to change them, please override only those that you need:


```
    <color name="andes_accent_color_100">#1A4189E6</color>
    <color name="andes_accent_color_150">#264189E6</color>
    <color name="andes_accent_color_200">#334189E6</color>
    <color name="andes_accent_color_300">#4D4189E6</color>
    <color name="andes_accent_color_400">#664189E6</color>
    <color name="andes_accent_color_500">#3483FA</color>
    <color name="andes_accent_color_600">#2968C8</color>
    <color name="andes_accent_color_700">#0F4496</color>
    <color name="andes_accent_color_800">#183C73</color>
```

You can see how we are setting color configuration for Mercado Pago in [our configurator](https://github.com/mercadolibre/fury_ml-config-provider-android/blob/master/andes-configurator/src/main/res/values/colors.xml).