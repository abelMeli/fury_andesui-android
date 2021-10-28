# Introduction

The Andes Tag are compact components that represent complex information in summary form. Allowing users to make selections or filter content.

![image1](https://user-images.githubusercontent.com/60985687/96306122-70cf0c00-0fd5-11eb-8611-23f0d2403ccc.png) ![image2](https://user-images.githubusercontent.com/60985687/96306126-73c9fc80-0fd5-11eb-9a93-cf59bcff0333.png)

We can also add icons to the left side, but only one at the same time and only in the Large size Tags.
There are two types of Tags:
* **Simple**: It has only one state
* **Choice**: It has two states, selected or idle, and it applies custom colors for each one.

# How to use

There are two ways to use AndesTag: XML or programmatically.

## XML

You can personalize some custom attributes:

**SimpleTag**
* Type: Used for background colors, etc.
* Size: Large and Small
* IsDissmisable: Used to draw a cross in the right content.
* Text: Content of Tag

**ChoiceTag**
* Mode: Simple or Dropdown
* State: Idle or Selected
* Size: Large or Small
* AnimateTag: If the state change should be animated.

The Android Studio IDE should autosuggest them 😉

### Example:

```
<...xmlns:app="http://schemas.android.com/apk/res-auto".../>
```

```
<com.mercadolibre.android.andesui.AndesTagSimple
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      app:tagSimpleType="highlight"
      app:tagSimpleSize="large"
      app:tagSimpleText="Tag simple" />
```

```
<com.mercadolibre.android.andesui.AndesTagChoice
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      app:tagChoiceMode="simple"
      app:tagChoiceSize="large"
      app:tagChoiceText="Tag choice" />
```

## Programmatically

We create the tag through its constructor and giving it the attributes we would like to assign.

```
val simpleTag = AndesTagSimple(
       context,
       AndesTagType.NEUTRAL,
       AndesTagSize.LARGE,
       "Icon large"
)
```

```
val choiceTag = AndesTagChoice(
       context,
       AndesTagChoiceMode.SIMPLE,
       AndesTagSize.LARGE,
       AndesTagChoiceState.SELECTED,
       "Icon large"
)
choiceTag.leftContent = LeftContent(
       icon = LeftContentIcon(
               backgroundColor = "#8C8C8C",
               icon = drawable,
               iconColor = "#8C8C8C"
       )
)
```

It's important to mention that in case of no attributes were specified, the tag by default will be Large size and Loud hierarchy, enabled, and without icon and without text.

Also values can be changed in run time.

```
choiceTag.text = "Text"
choiceTag.state = AndesTagChoiceState.IDLE
choiceTag.size = AndesTagSize.SMALL
```