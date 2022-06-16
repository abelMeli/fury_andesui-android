# AndesSearchbox

The AndesSearchbox is a component that allows the user to enter and edit text values, for the specific case of a search.
[See Andes UI component in frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/components/dropdown-1575576193/variantes)

```kotlin
class AndesSearchbox: ConstraintLayout
```

Basic Sample Programatically

```kotlin
AndesSearchbox(context)
```
Basic Sample XML

```xml
<com.mercadolibre.android.andesui.searchbox.AndesSearchbox
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```
<br/>

## XML Attributes
| Property | Summary |
| -------- | ------- |
| app:andesSearchboxPlaceholder | Value that defines the component text to be shown inside the textfield when it's empty. |

<br/>

## Constructors
| Summary |
| --- |
| AndesSearchbox(context: Context, placeholder: String? = null) |
| AndesSearchbox(context: Context, attrs: AttributeSet?) |

<br/>

##### AndesSearchbox(context: Context, placeholder: String? = null)
| Parameter | Description |
| -------- | ------- |
| context | **Context**|
| placeholder | **String?**: text to be shown inside the textfield when it's empty. If no custom placeholder is passed, it shows `Search...` with the propper translation. |

<br/>

## Properties
| Property | Summary |
| -------- | ------- |
| placeholder: String?  | **get():** retrieves the placeholder of the textfield. <br/> **set(value: String?):** updates textfield placeholder. |
| onTextChangeListener: OnTextChangeListener | **get():** retrieves the corresponding onchange listener, or null if not set. <br/> **set(value: OnTextChangeListener):** adds new onChange listener |
| onSearchListener: OnSearchListener | **get():** retrieves the corresponding onsearch listener, or null if not set. <br/> **set(value: OnSearchListener):** adds new onSearch listener |

<br/>

## Methods
| Method | Description |
| -- | -- |
| clearSearch()| Clears the text of the textfield |

<br/>
## Related Classes

### OnTextChangeListener
Interface used to detect when the value of the component changes.
```kotlin
interface OnTextChangeListener {
    fun onTextChanged(text: String)
}
```

### OnSearchListener
Interface used to detect when the keyboard action button is pressed.
```kotlin
interface OnSearchListener {
    fun onSearch(text: String)
}
```
<br/>

## Screenshots
| Accessibility | No Accessibility |
| ----------- | ----------- |
| <img src="https://user-images.githubusercontent.com/101647658/173667679-23bb9ed8-0e94-43db-aacd-23d2b135bacd.gif" width="400"> | <img src="https://user-images.githubusercontent.com/101647658/173667704-8d712d61-a9ff-4cb2-9edd-3ed80afe175f.gif" width="400"> |
