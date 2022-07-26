# Introduction

AndesCarousel is a component which groups and allows you to manipulate a horizontal sequence of content. It can be customized in terms of Center (true - false) and Margin (None, Default). Check the [RFC](https://docs.google.com/document/d/1Tn_YonFZCJf1Vv3Caadkk4-8_y7K_-jURadMxUN8HK8/edit) for more information about the definition.

![](https://user-images.githubusercontent.com/56642336/97608071-9bb35a00-19f0-11eb-9aea-3f3cd2a011a8.gif)

# How to use

There are two ways to use AndesCarousel: XML or programmatically.

XML
You can personalize custom attributes like andesCarouselCenter and andesCarouselMargin.

## Example:
```
<...xmlns:app="http://schemas.android.com/apk/res-auto".../>

<com.mercadolibre.android.andesui.carousel.AndesCarousel
    android:id="@+id/carouselMain"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="24dp"
    app:andesCarouselCenter="true"
    app:andesCarouselMargin=“defaultMargin”
    app:andesCarouselInfinite=“true”
    app:andesCarouselAutoplay=“true”
    app:andesCarouselAutoplaySpeed=“500”
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@+id/andesMessage" />
```

## Programmatically:

We create the carousel through its constructor and giving it the attributes we would like to assign. Note the use of AndesCarouselMargin.NONE or AndesCarouselMargin.DEFAULT.

```
val newCarousel = AndesCarousel(this, false, AndesCarouselMargin.NONE)
newCarousel.delegate = this
mainContainer.addView(newCarousel)
```

Also navigate to a specific position in the carousel using the scrollToPosition passing the position as value

```
val position = 3
val newCarousel = AndesCarousel(this, false, AndesCarouselMargin.NONE)
newCarousel.scrollToPosition(position)
```

### NOTE: 
It's important to mention that in case of no attributes were specified, the carousel by default will be andesCarouselCenter in false and andesCarouselMargin in default. 
Also, andesCarouselInfinite as False, andesCarouselAutoplay as False and andesCarouselAutoplaySpeed as 3000, autoplay and atuplaySpeed
just work when andesCarouselInfinite is set as true.

Also values can be changed in run time.
andesCarousel.center = true
andesCarousel.margin = AndesCarouselMargin.DEFAULT
andesCarousel.delegate = (a object that implements AndesCarouselDelegate)
carousel.infinite = true
carousel.autoplay = false
carousel.autoplaySpeed = 500 (Slides will change every 500 milliseconds)

## Example:
My activity implements AndesCarouselDelegate so I have to implement the methods “bind - getLayoutItem - getDataSetSize - onClickItem”

```
override fun bind(andesCarouselView: AndesCarousel, view: View, position: Int) {
    val model = getDataSet()[position]
       view.findViewById<AndesMessage>(R.id.messageCarousel).apply {
           title = "Andes Message"
    }
}

override fun getLayoutItem(andesCarouselView: AndesCarousel) = R.layout.andesui_carousel_item

override fun onClickItem(andesCarouselView: AndesCarousel, position: Int) {
    Toast.makeText(this@CarouselShowcaseActivity, getDataSet()[position].toString(), Toast.LENGTH_SHORT).show()
}

override fun getDataSetSize(andesCarouselView: AndesCarousel) = getDataSet().size

myCarousel.delegate = this (my activity thats implements AndesCarouselDelegate)
```