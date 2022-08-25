# Andes Input Stepper

# Introduction

It allows you to select an integer value step by step through of an increment/decrement using its buttons and display a custom text based in this value.

Check the [RFC](https://docs.google.com/document/d/1TX_1gZr6Y00LygZjws7li6flZs5rQJOkLyCoq7eFq0Q#) for more information about the definition.

<img src="https://user-images.githubusercontent.com/69922975/186715552-6b7cc3d4-7d08-43a0-8991-c0f4d0751f33.gif" height="700"/>

# How to use

Andes Input Stepper has:

two sizes
```
AndesInputStepperSize.Large (default)
AndesInputStepperSize.Small
```

three status
```
AndesInputStepperStatus.Enabled (default)
AndesInputStepperStatus.Disabled
AndesInputStepperStatus.Loading
```

these options changes the ui of the component
also have the following attrs

| Property | Summary |
 | -------- | ------- |
| size | Defines different sizes that a AndesInputStepper supports (**LARGE**, **SMALL**). |
| status | Defines different status that a AndesInputStepper supports (**Enabled**, **Disabled**, **Loading**). |
| maxValue | Defines the max value (**Integer**). |
| minValue | Defines the min value (**Integer**).|
| step | Defines the step an increment/decrement of the value (**Integer**).|

its buttons could perform two possibles events
```
AndesInputStepperEvent.PREVIOUS
AndesInputStepperEvent.NEXT
```

based in its value and its limits this will have three possibles operational states
```
AndesInputStepperEnabledState.InRange (default)
AndesInputStepperEnabledState.MaxSelected
AndesInputStepperEnabledState.MinSelected
```


## XML:
```
<com.mercadolibre.android.andesui.inputstepper.AndesInputStepper
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

<com.mercadolibre.android.andesui.inputstepper.AndesInputStepper
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:andesInputStepperMaxValue="10"
        app:andesInputStepperMinValue="-2"
        app:andesInputStepperSize="large"
        app:andesInputStepperStatus="idle"
        app:andesInputStepperStep="2"
        app:andesInputStepperValue="5" />
```

## Programmatically:

```
val andesInputStepper = AndesInputStepper(
        context: Context,
        size: AndesInputStepperSize,
        status: AndesInputStepperStatus,
        value: Int,
        maxValue: Int,
        minValue: Int,
        step: Int,
    )
```

## Configuration

We could to implement the AndesInputStepperTextDataSource.
The interface will implement getText method that provides a custom text based in the value changes.

```
val dataSource = object : AndesInputStepperTextDataSource {
    override fun getText(value: Int): CharSequence? {
        return "any mask" + " " + value
    }
}
```

Then set the property that will handle the interface to our AndesInputStepper instance.

```
andesInputStepper.dataSource = dataSource
```

Also we could to implement the AndesInputStepperValueListener.
The interface will implement onValueSelected method that notifies the value changes, its source and operational state.

```
andesInputStepper.valueListener = object : AndesInputStepperValueListener {
        override fun onValueSelected(
            sender: AndesInputStepperEvent?,
            value: Int,
            state: AndesInputStepperEnabledState,
        ) {
            val msg = "sender: ${sender?.name} value: ${value} state: ${state.javaClass.simpleName}"
            Toast.makeText(containerView.context, msg, Toast.LENGTH_SHORT).show()
        }
    }
```