# Introduction

Allow you to select one of several options, this component is an extension of AndesList.

Check the [RFC](https://docs.google.com/document/d/1R8qnm5dkEA-oQIeYny4gqMpANpwvpsEjp7A6LcZPnPI/edit#heading=h.xy7uh1l5y1qn#) for more information about the definition.

<img src="https://user-images.githubusercontent.com/16910904/104049053-06d49600-51c3-11eb-8b12-abde6d3f74e0.gif" height="700"/>

# How to use

Andes Dropdown has two trigger types:
```
AndesDropDownForm
AndesDropDownStandalone
```
Depending on what type we choose we must implement the corresponding object type.

## XML:
```
<com.mercadolibre.android.andesui.dropdown.AndesDropDownForm
    android:id="@+id/andesDropdown"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>

<com.mercadolibre.android.andesui.dropdown.AndesDropdownStandalone
    android:id="@+id/andesDropdownStandalone"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```

## Programmatically:

```
val andesDropDownForm = AndesDropDownForm(this, AndesDropdownMenuType.BOTTOMSHEET, "label", "helper", "place holder")
mainContainer.addView(andesDropDownForm)

val andesDropdownStandalone = AndesDropdownStandalone(this, AndesDropdownMenuType.BOTTOMSHEET, AndesDropdownSize.MEDIUM, "label")
mainContainer.addView(andesDropdownStandalone)
```

## Configuration

We need to implement the AndesDropdownDelegate in our Activity/Fragment where the Dropdown will be shown.
The interface will implement onItemSelected method that is called every time that we click on each Dropdown item.

```
SomeActivity : AppCompatActivity(), AndesDropdownDelegate {

    override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
        Toast.makeText(this, "item selected position: $position", Toast.LENGTH_SHORT).show()
    }
}
```

Then set the class that will handle the interface to our AndesDropdownDelegate instance.

```
andesDropdown.delegate = this
```

Set the items that our dropdown will be shown

```
andesDropdown.listItems.addAll(items) 
```

Add the searchbox that our dropdown will be shown

```
andesDropdown.addSearchable(AndesSearchbox(context))
```

Remove the searchbox shown in our dropdown menu

```
andesDropdown.removeSearchable()
```