package com.mercadolibre.android.andesui.tooltip.actions

import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocation
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipSize
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipStyle

val tooltipNoAction: Array<Any?> = arrayOf(
        AndesTooltipStyle.LIGHT,
        "title",
        "body",
        true,
        AndesTooltipLocation.LEFT,
        null,
        null,
        null,
        AndesTooltipSize.DYNAMIC
)
val tooltipLoud = arrayOf(
        AndesTooltipStyle.LIGHT,
        "title",
        "body",
        true,
        AndesTooltipLocation.TOP,
        AndesTooltipAction("main action", AndesButtonHierarchy.LOUD, { _, _ -> }),
        null,
        null,
        AndesTooltipSize.DYNAMIC
)

val tooltipLoudAndQuiet = arrayOf(
        AndesTooltipStyle.LIGHT,
        "title",
        "body",
        true,
        AndesTooltipLocation.TOP,
        AndesTooltipAction("main action", AndesButtonHierarchy.LOUD, { _, _ -> }),
        AndesTooltipAction("secondary action", AndesButtonHierarchy.QUIET, { _, _ -> }),
        null,
        AndesTooltipSize.DYNAMIC
)

val tooltipLoudAndTransparent = arrayOf(
        AndesTooltipStyle.LIGHT,
        "title",
        "body",
        true,
        AndesTooltipLocation.TOP,
        AndesTooltipAction("main action", AndesButtonHierarchy.LOUD, { _, _ -> }),
        AndesTooltipAction("secondary action", AndesButtonHierarchy.TRANSPARENT, { _, _ -> }),
        null,
        AndesTooltipSize.DYNAMIC
)

val tooltipLink = arrayOf(
        AndesTooltipStyle.LIGHT,
        "title",
        "body",
        true,
        AndesTooltipLocation.TOP,
        null,
        null,
        AndesTooltipLinkAction("link action", { _, _ -> }),
        AndesTooltipSize.DYNAMIC
)

val tooltipFullSize = arrayOf(
    AndesTooltipStyle.LIGHT,
    "title",
    "body",
    true,
    AndesTooltipLocation.BOTTOM,
    AndesTooltipAction("main action", AndesButtonHierarchy.LOUD, { _, _ -> }),
    null,
    null,
    AndesTooltipSize.FULL_SIZE
)
