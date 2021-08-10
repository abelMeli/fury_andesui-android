package com.mercadolibre.android.andesui.tabs.type

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import com.google.android.material.tabs.TabLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.tabs.STRAIGHT_CORNER_RADIUS
import com.mercadolibre.android.andesui.tabs.renderIndicatorWithRoundedCorners

internal interface AndesTabsTypeInterface {
    fun getTabMode(): Int
    fun isTabIndicatorFullWidth(): Boolean
    fun getLeftRoundCornerSize(index: Int, resources: Resources): Float
    fun getRightRoundCornerSize(index: Int, lastIndex: Int, resources: Resources): Float
    fun updateIndicatorIfNeeded(
        tabSelectedIndicator: Drawable,
        unselectedTabPosition: Int,
        selectedTabPosition: Int,
        lastTabPosition: Int,
        resources: Resources
    ) {
    }
}

internal object AndesFullWidthTabsType : AndesTabsTypeInterface {
    override fun getTabMode(): Int = TabLayout.MODE_FIXED
    override fun isTabIndicatorFullWidth() = true

    override fun getLeftRoundCornerSize(index: Int, resources: Resources): Float {
        return if (index == 0) {
            STRAIGHT_CORNER_RADIUS
        } else {
            resources.getDimensionPixelSize(R.dimen.andes_tab_indicator_corner).toFloat()
        }
    }

    override fun getRightRoundCornerSize(index: Int, lastIndex: Int, resources: Resources): Float {
        return if (index == lastIndex) {
            STRAIGHT_CORNER_RADIUS
        } else {
            resources.getDimensionPixelSize(R.dimen.andes_tab_indicator_corner).toFloat()
        }
    }

    override fun updateIndicatorIfNeeded(
        tabSelectedIndicator: Drawable,
        unselectedTabPosition: Int,
        selectedTabPosition: Int,
        lastTabPosition: Int,
        resources: Resources
    ) {
        if (unselectedTabPosition.isFirstOr(lastTabPosition) ||
            selectedTabPosition.isFirstOr(lastTabPosition)
        ) {
            renderIndicatorWithRoundedCorners(
                tabSelectedIndicator.mutate() as GradientDrawable,
                getLeftRoundCornerSize(selectedTabPosition, resources),
                getRightRoundCornerSize(
                    selectedTabPosition,
                    lastTabPosition,
                    resources
                )
            )
        }
    }

    private fun Int.isFirstOr(position: Int): Boolean = this == 0 || this == position
}

internal class AndesLeftAlignTabsType(private val overflowMode: Boolean) : AndesTabsTypeInterface {
    override fun getTabMode(): Int = TabLayout.MODE_SCROLLABLE
    override fun isTabIndicatorFullWidth() = false
    override fun getLeftRoundCornerSize(index: Int, resources: Resources) =
        resources.getDimensionPixelSize(R.dimen.andes_tab_indicator_corner).toFloat()

    override fun getRightRoundCornerSize(index: Int, lastIndex: Int, resources: Resources) =
        resources.getDimensionPixelSize(R.dimen.andes_tab_indicator_corner).toFloat()
}
