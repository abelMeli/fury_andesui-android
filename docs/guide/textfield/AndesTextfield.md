# Introduction

The Andes Texfield, as its name says, is the message that this UI library offers.
It can be customized in terms of **State** (Enabled, Error, Disabled, Read Only) and **Contents** (Prefix, Icon) for left and (Suffix, Icon, Validated, Action, Clear, Tooltip, Checkbox, Indeterminate) for right.

![Screenshot_1585255102](https://user-images.githubusercontent.com/51792499/77694705-43633800-6f89-11ea-80e1-38b2cebca220.png)

![Screenshot_1585255906](https://user-images.githubusercontent.com/51792499/77695468-925d9d00-6f8a-11ea-9c3d-8d086f4d2d6a.png)
 
# How to use

There are two ways to use AndesTexfield: XML or programmatically. 
 
## XML

You can personalize some custom attributes like andesTextfieldLabel, andesTextfieldHelper, andesTextfieldPlaceholder, andesTextfieldCounterMinValue, andesTextfieldCounterMaxValue, andesTextfieldState, andesTextfieldLeftContent and andesTextfieldRightContent.
The Android Studio IDE should autosuggest them 😉

### Example:

```
<...xmlns:app="http://schemas.android.com/apk/res-auto".../>

<com.mercadolibre.android.andesui.textfield.AndesTextfield
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="@dimen/button_margin_vertical"
            app:andesTextfieldState="readonly"
            app:andesTextfieldPlaceholder="@string/andes_textfield_hint_text"
            app:andesTextfieldLabel="@string/andes_textfield_label_text"
            app:andesTextfieldHelper="@string/andes_textfield_helper_text"
            app:andesTextfieldLeftContent="icon"
            app:andesTextfieldRightContent="clear"
            />
```

## Programmatically

We create the message through its constructor and giving it the attributes we would like to assign.
Note the use of `AndesTexfieldState.XXX` and `AndesTexfieldRight/LeftContent.XXX`.

```
val andesTextfield = AndesTextfield(
                           context, 
                           "label", 
                           "helper", 
                           "placeholder", 
                           AndesTextfieldCounter(0,100),
                           AndesTextfieldState.ERROR, 
                           AndesTextfieldLeftContent.PREFIX, 
                           AndesTextfieldRightContent.CLEAR
)

val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
)
params.setMargins(10, 10, 10, 10)
andesTextfield.layoutParams = params
```

For each content that requires an external parameter there are some functions exposed to configure that.

```
andesTexfield.setupAction(text: String, onClickListener: OnClickListener)
andesTexfield.setupRightIcon(iconPath: String)
andesTextfield.setupPrefix(text: String)
andesTextfield.setRightIcon(iconPath: String, listener: OnClickListener? = null, colorIcon: Int? = R.color.andes_gray_800, hideWhenType: Boolean = false)

```

Also values can be changed in run time.

```
...
andesTexfield.helper = "Helper"
andesTexfield.state= "AndesTextfieldState.DISABLED
...
```

# Andes Textarea

There is also an Andes Textarea which is so similar to the textfield but it can't have left and right contents and can have more than one line. The minimum is 3 lines.

The XML property is andesTextareaMaxLines="5" and maxLines=5 in the programmatic way.

![Screenshot_1585316444](https://user-images.githubusercontent.com/51792499/77761884-79e79400-7017-11ea-8181-bc541a223e99.png)

