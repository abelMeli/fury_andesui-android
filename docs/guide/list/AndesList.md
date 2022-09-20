
# Introduction

They are rows ordered vertically in a sequential way, with similar content and format. They allow you to display information, actuate, navigate, or select between different options. 

Check the [RFC](https://docs.google.com/document/d/1lsJ_Oz-XplkwAMbINjTSdz8imvVDUqIbElZZNKsUo5Q/edit#) for more information about the definition.

<img src="https://user-images.githubusercontent.com/101647658/185454392-ebc2a647-e67d-4a09-8b2e-c1ab389fb4c8.gif" height="700"/>

# How to use

There are two ways to implement AndesList: XML or programmatically.

## XML:
```
<com.mercadolibre.android.andesui.list.AndesList
    android:id="@+id/andesList"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:andesListItemSize="medium"
    app:andesListType="simple" />
```
## Programmatically:

```
val andesList = AndesList(this, AndesListViewItemSize.MEDIUM, AndesListType.SIMPLE)
mainContainer.addView(andesList)
```

## Configuration
Set the class that will handle the interface to our AndesList instance.

```
andesList.delegate = this
```

Then we need to implement the AndesListDelegate in our Activity/Fragment where the List will be shown.
The interface will implement 3 methods.
1. **bind** -> In this method, we must build the objects that the list will be drawn.
At the moment we have 4 types of objects to return in the bind method, **AndesListViewItemSimple**, **AndesListViewItemChevron**, **AndesListViewItemCheckBox** and **AndesListViewItemRadioButton**, both have the **context** and **title** as required, the other params are optional.

2. ****getDataSetSize**** -> The size of our list that the AndesList will draw.
3. **onItemClick** -> This method is called every time that we click on each AndesList item.

## Example
```
SomeActivity : AppCompatActivity(), AndesListDelegate {

    override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
        return  return if (position == 0) {
            AndesListViewItemSimple(
                this,
                "Title",
                subtitle = "Subtitle",
                size = AndesListViewItemSize.MEDIUM,
                icon = ContextCompat.getDrawable(this, R.drawable.andes_envio_envio_24),
                avatar = ContextCompat.getDrawable(this, R.drawable.andes_otros_almanaque_20),
                avatarType = AndesThumbnailType.IMAGE_CIRCLE,
                titleMaxLines = 50,
                itemSelected = false
            )
        } else {
            val view: View = View(this)
            AndesListViewItemSimple(
                this,
                itemDividerEnabled = false,
                itemSelected = false,
                size = AndesListViewItemSize.MEDIUM,
                avatar = ContextCompat.getDrawable(this, R.drawable.andes_otros_almanaque_20),
                avatarType = AndesThumbnailType.IMAGE_CIRCLE,
                content = view
            )
        }
    }

    override fun getDataSetSize(andesList: AndesList): Int = someList.size()

    override fun onItemClick(andesList: AndesList, position: Int) {
        Toast.makeText(this, "Position of item selected $position", Toast.LENGTH_SHORT).show()
    }

}
```