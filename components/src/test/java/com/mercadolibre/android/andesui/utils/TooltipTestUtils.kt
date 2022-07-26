package com.mercadolibre.android.andesui.utils

import android.widget.PopupWindow
import com.mercadolibre.android.andesui.tooltip.AndesTooltip
import org.robolectric.util.ReflectionHelpers

internal fun AndesTooltip.getActionId() =
    ReflectionHelpers.getField<Int>(this, "a11yActionId")

internal fun AndesTooltip.getBodyWindow() =
    ReflectionHelpers.getField<PopupWindow>(this, "bodyWindow")
