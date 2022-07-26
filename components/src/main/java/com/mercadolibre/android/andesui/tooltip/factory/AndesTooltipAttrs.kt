package com.mercadolibre.android.andesui.tooltip.factory

import com.mercadolibre.android.andesui.tooltip.actions.AndesTooltipAction
import com.mercadolibre.android.andesui.tooltip.actions.AndesTooltipLinkAction
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocation
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipSize
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipStyle

internal data class AndesTooltipAttrs(
    val style: AndesTooltipStyle,
    val body: String,
    val title: String? = null,
    val isDismissible: Boolean,
    val mainAction: AndesTooltipAction? = null,
    val secondaryAction: AndesTooltipAction? = null,
    val linkAction: AndesTooltipLinkAction? = null,
    val tooltipLocation: AndesTooltipLocation,
    val andesTooltipSize: AndesTooltipSize,
    val shouldGainA11yFocus: Boolean
)
