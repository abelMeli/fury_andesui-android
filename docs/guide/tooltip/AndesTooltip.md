# AndesTooltip

AndesTooltip is a PopUp notification which allows to get further context related to an specific element.
[See Andes UI component in frontify](https://company-161429.frontify.com/d/kxHCRixezmfK/n-a#/components/tooltip). 

```kotlin
class AndesTooltip
```

<br/>

## Constructors 

<br/>

### Constructor with only context is not allowed

<br/>

### Basic constructor

It builds an AndesTooltip with just a body and default properties.

```kotlin
AndesTooltip(context: Context, body: String)
```

<br/>

### Constructor with AndesButtons

It builds an AndesTooltip with actions rendered as AndesButtons. Main action is needed but secondary is optional.

```kotlin
AndesTooltip(
        context: Context,
        style: AndesTooltipStyle,
        title: String?,
        body: String,
        isDismissible: Boolean,
        tooltipLocation: AndesTooltipLocation,
        mainAction: AndesTooltipAction,
        secondaryAction: AndesTooltipAction?
    )
```



| Parameter       | Description                                                  |
| --------------- | ------------------------------------------------------------ |
| context         | **Context**                                                  |
| style           | **[AndesTooltipStyle](#andestooltipstyle)**: this value cannot be null. Default style is **LIGHT** |
| title           | **String?**: this value can be null. Default is **null**.    |
| body            | **String**: this value cannot be null.                       |
| isDismissible   | **Boolean**: this value can be null. Default is **true**. <br/>It will show or not the `x` icon in the tooltip, although it will dismiss the tooltip if you scroll the screen. |
| tooltipLocation | **[AndesTooltipLocation](#andestooltiplocation)**: this value cannot be null. Default style is **TOP**. <br/>It will set the preferred tooltip location in regard to the target view, which means that if not passed, the tooltip will be rendered on the top of the target by default. |
| mainAction      | **[AndesTooltipAction](#andestooltipaction)**: this value cannot be null. <br/>It will config the action and style of AndesTooltip main action. |
| secondaryAction | **[AndesTooltipAction?](#andestooltipaction)**: this value can be null. <br/>It will config the action and style of AndesTooltip secondary action. This can be rendered if mainAction does exists. |

<br/>

### Constructor with Link action | No actions

It builds an AndesTooltip with an action rendered as a link. A link cannot be rendered with an AndesButton which is why there are two different constructors. This constructor can be also used to configure an AndesTooltip without actions.

```kotlin
AndesTooltip(
        context: Context,
        style: AndesTooltipStyle,
        title: String?,
        body: String,
        isDismissible: Boolean,
        tooltipLocation: AndesTooltipLocation,
        linkAction: AndesTooltipLinkAction?
    )
```



| Parameter       | Description                                                  |
| --------------- | ------------------------------------------------------------ |
| context         | **Context**                                                  |
| style           | **[AndesTooltipStyle](#andestooltipstyle)**: this value cannot be null. Default style is **LIGHT** |
| title           | **String?**: this value can be null. Default is **null**.    |
| body            | **String**: this value cannot be null.                       |
| isDismissible   | **Boolean**: this value can be null. Default is **true**. <br/>It will show or not the `x` icon in the tooltip, although it will dismiss the tooltip if you scroll the screen. |
| tooltipLocation | **[AndesTooltipLocation](#andestooltiplocation)**: this value cannot be null. Default style is **TOP**. <br/>It will set the preferred tooltip location in regard to the target view, which means that if not passed the tooltip will be rendered on the top of the target by default. |
| linkAction      | **[AndesTooltipLinkAction?](#andestooltiplinkaction)**: this value can be null. <br/>It will config the action of AndesTooltip link action. |

<br/>

## Properties

| Property                                                     | Summary                                                      |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| title: String?                                               | **get():** retrieves title text displayed. Can retrieve a null value. <br/> **set(value: String?):** updates title to display. Can be set as null. |
| body: String                                                 | **get():** retrieves body text displayed <br/> **set(value: String?):** updates body text. |
| isDismissible: Boolean                                       | **get():** retrieves if `x` icon in AndesTooltip is visible<br/> **set(value: Boolean):** updates AndesTooltip `x` icon visibility. |
| style: [AndesTooltipStyle](#andestooltipstyle)               | **get():** retrieves tooltip style enum value configured <br/> **set(value: [AndesTooltipStyle](#andestooltipstyle)):** updates tooltip style. |
| mainAction: [AndesTooltipAction](#andestooltipaction)        | **get():** retrieves tooltip main action data<br/> **set(value: [AndesTooltipAction](#andestooltipaction)):** updates main action data. |
| secondaryAction: [AndesTooltipAction](#andestooltipaction)   | **get():** retrieves tooltip secondary action data<br/> **set(value: [AndesTooltipAction](#andestooltipaction)):** updates secondary action data. |
| linkAction: [AndesTooltipLinkAction](#andestooltiplinkaction) | **get():** retrieves tooltip link action data<br/> **set(value: [AndesTooltipLinkAction](#andestooltiplinkaction)):** updates link action data. |
| location: [AndesTooltipLocation](#andestooltiplocation)      | **get():** retrieves tooltip location enum value configured<br/> **set(value: [AndesTooltipLocation](#andestooltiplocation)):** updates tooltip preferred location. |

<br/>

## How to use it? 

If you want to display your tooltip is a **must** calling the `show(target: View)` method after build it. <br/>

```kotlin
val mTooltip = AndesTooltip(context, "my simple body")
mTooltip.show(target = myTargetedView)
```

**Your tooltip will NOT be rendered if:** 

- Your tooltip is already rendered.
- The Context passed is finishing.
- And the passed view target is not attached.

### Examples 

#### Settings from the constructors and `show()`

```kotlin
// 1. Use some constructor to build the AndesTooltip you want with the config you want
val mTooltip = AndesTooltip(
                context = this, 
                isDismissible = true, 
                style = AndesTooltipStyle.LIGHT, 
                title = "My title",
                body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                tooltipLocation = AndesTooltipLocation.BOTTOM,
                mainAction = AndesTooltipAction(
                      text = "my text button",
                      hierarchy = AndesButtonHierarchy.LOUD,
                      action = { buttonClicked, andesTooltip ->
                        Toast.makeText(this, "button was clicked", Toast.LENGTH_SHORT).show()
                      }
                )
        )

// 2. call show() method passing the target view.
...
val targetTextView: TextView = container.findViewById(R.id.my_target_text_view)
...
mTooltip.show(targetTextView)

```

<br/>

#### After built, update with properties and `show()`

```kotlin
// 1. Use some constructor to build the AndesTooltip
val mTooltip = AndesTooltip(context = this, body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit")

// 2. set or update your AndesTooltip
mTooltip.title = "My title"
mTooltip.tooltipLocation = AndesTooltipLocation.BOTTOM
mTooltip.isDismissible = true
mTooltip.style = AndesTooltipStyle.LIGHT
mTooltip.linkAction = AndesTooltipLinkAction(
                          text = "my text button",
                          action = { buttonClicked, andesTooltip ->
                            Toast.makeText(this, "$text was clicked", Toast.LENGTH_SHORT).show()
                          }
                			)

// 3. call show() method passing the target view.
...
val targetTextView: TextView = container.findViewById(R.id.my_target_text_view)
...
mTooltip.show(targetTextView)
```

<br/>

## Related Classes

### AndesTooltipStyle
Defines the possible styles **AndesTooltip** can take.
```kotlin
enum class AndesTooltipStyle
```
| Enum Values |
| ----------- |
| LIGHT       |
| DARK        |
| HIGHLIGHT   |

<br/>

#### Functions
| Return type       | Method                                                       |
| ----------------- | ------------------------------------------------------------ |
| AndesTooltipStyle | **fromString(value: String)**<br/> Retrieves an AndesTooltipStyle that matches the string value |

<br/>

### AndesTooltipAction

Defines the data configuration needed for a AndesButton inside an AndesTooltip

```kotlin
class AndesTooltipAction(
    open val label: String,
    open val hierarchy: AndesButtonHierarchy,
    open val onActionClicked: (view: View, tooltip: AndesTooltip) -> Unit
)
```

| Property                                                     | Summary                                                      |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| **label**: String                                            | **String**: this value cannot be null.<br/>It's the string value used to set in the AndesButton. |
| **hierarchy**: AndesButtonHierarchy                          | **AndesButtonHierarchy**: this value cannot be null.<br/>It's used to set the AndesButton hierarchy from the AndesTooltip.<br/>FYI: <br/>- In AndesTooltip main action is only allowed **LOUD**<br/>- In AndesTooltip secondary action is only allowed **QUIET** and **TRANSPARENT** |
| **onActionClicked**: (view: View, tooltip: AndesTooltip) -> Unit | **(view: View, tooltip: AndesTooltip) -> Unit**: this value cannot be null.<br/>It's a kotlin function with two params that returns Unit, which will be called when the AndesButton onClick is executed, passing the AndesButton clicked and the AndesTooltip. **If onClick is executed, the AndesTooltip will be dismissed** |

<br/>

### AndesTooltipLinkAction

Defines the data configuration needed for a AndesButton inside an AndesTooltip

```kotlin
class AndesTooltipLinkAction(
    open val label: String,
    open val onActionClicked: (view: View, tooltip: AndesTooltip) -> Unit
)
```

| Property                                                     | Summary                                                      |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| **label**: String                                            | **String**: this value cannot be null.<br/>It's the string value used to set in the link button. |
| **onActionClicked**: (view: View, tooltip: AndesTooltip) -> Unit | **(view: View, tooltip: AndesTooltip) -> Unit**: this value cannot be null.<br/>It's a kotlin function with two params that returns Unit, which will be called when the link button onClick is executed, passing the link button view clicked and the AndesTooltip. **If onClick is executed, the AndesTooltip will be dismissed** |

<br/>

### AndesTooltipLocation

Defines the possible locations **AndesTooltip** can take regarding the target which the further context is needed. 

```kotlin
enum class AndesTooltipLocation
```

| Enum Values |
| ----------- |
| BOTTOM      |
| TOP         |
| LEFT        |
| RIGHT       |

<br/>

#### How does AndesTooltipLocation work?

The value passed by constructor will be set as preferred location, but, if AndesTooltip has no enough space to be rendered, it will be rendered in the opposite location or the remaining locations. For example: If the AndesTooltip location is **BOTTOM** and there is not enough space, AndesTooltip will try to render itself on **TOP** of the target. Even if there is not enough space on **TOP** location, it will try in **LEFT** and then in **RIGHT**. In the final case if there is no space anywhere it will not be rendered. **It is highly recommended to always pass UX preferred location.**
