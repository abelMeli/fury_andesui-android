package com.mercadolibre.android.andesui.modal.card.corners

/**
 * Enum class with the two possible corner configuration for the card modals.
 * The values will set either a four-rounded corner variant or a top-only corner variant,
 * according to the presence/absence of a buttongroup.
 * When a buttongroup is set, the modal bottom corners will be set by the said component, thus we
 * will only need the [TOP_CORNERS]. Otherwise, the four corners will be set by using the [ALL_CORNERS]
 * value.
 */
internal enum class AndesModalCorners(internal val corners: AndesModalCornersInterface) {
    ALL_CORNERS(AndesModalCornersAllCorners),
    TOP_CORNERS(AndesModalCornersTopCorners)
}
