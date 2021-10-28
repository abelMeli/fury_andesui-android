# AndesColor

AndesColor is a util class to create a color from a resource.
[See andes color palette](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/foundations/color)

```kotlin
data class AndesColor
```

Basic Sample Programatically

```kotlin
AndesColor(R.color.awesome_color)
```

<br/>

## Constructors
| Summary |
| --- |
| [AndesColor](#andescolorcolorres-int-alpha-float)(colorRes: Int, alpha: Float) |

<br/>

##### AndesColor(colorRes: Int, alpha: Float)
| Parameter | Description |
| -------- | ------- |
| colorRes | **Int**: color resource |
| alpha | **Float**: container view where snackbar will be attached |

## Functions
| Return type | Method |
| ----------- | ------ |
| Int | **colorInt(context: Context)** <br/> Blends a color with black + alpha color. Returns a new opaque color.  |
| Int | **colorIntToAlpha(context: Context)** <br/> Returns color with alpha. |


