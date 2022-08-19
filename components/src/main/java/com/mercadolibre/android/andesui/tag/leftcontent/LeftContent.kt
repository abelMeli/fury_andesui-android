package com.mercadolibre.android.andesui.tag.leftcontent


class LeftContent @Deprecated(message = "Is deprecated, use new constructor with the parameter assetContentDescription for accessibility of assets", replaceWith = ReplaceWith("LeftContent(dot = dot, icon = icon, image = image, assetContentDescription = assetContentDescription)")) constructor(
    var dot: LeftContentDot? = null,
    var icon: LeftContentIcon? = null,
    var image: LeftContentImage? = null
){
    var assetContentDescription: String? = null

    constructor(dot: LeftContentDot?,icon: LeftContentIcon?, image: LeftContentImage?, assetContentDescription: String?) : this(dot,icon,image) {
        this.assetContentDescription = assetContentDescription
    }

}
