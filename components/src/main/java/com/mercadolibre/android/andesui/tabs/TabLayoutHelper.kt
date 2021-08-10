package com.mercadolibre.android.andesui.tabs

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.tabs.factory.AndesTabsConfiguration
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

internal fun renderItem(context: Context, title: String, config: AndesTabsConfiguration): TextView {
    return TextView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        isDuplicateParentStateEnabled = true
        text = title
        setTextColor(config.tabTextColor)
        typeface = context.getFontOrDefault(R.font.andes_font_semibold)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, config.tabTextSize)
        ellipsize = TextUtils.TruncateAt.END
        setSingleLine()
    }
}

internal fun renderIndicatorWithRoundedCorners(
    gradientDrawable: GradientDrawable,
    topLeft: Float,
    topRight: Float
) {
    gradientDrawable.cornerRadii =
        floatArrayOf(
            topLeft,
            topLeft,
            topRight,
            topRight,
            STRAIGHT_CORNER_RADIUS,
            STRAIGHT_CORNER_RADIUS,
            STRAIGHT_CORNER_RADIUS,
            STRAIGHT_CORNER_RADIUS
        )
}

internal const val STRAIGHT_CORNER_RADIUS = 0F
