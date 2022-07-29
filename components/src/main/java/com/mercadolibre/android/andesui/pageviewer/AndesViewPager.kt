package com.mercadolibre.android.andesui.pageviewer

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import androidx.viewpager.widget.ViewPager
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.pageviewer.factory.AndesViewPagerAttrs
import com.mercadolibre.android.andesui.pageviewer.factory.AndesViewPagerAttrsParser
import com.mercadolibre.android.andesui.utils.ScreenUtils
import com.mercadolibre.android.andesui.utils.getAllChildren
import com.mercadolibre.android.andesui.utils.getAllSiblings

internal class AndesViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    private var andesViewPagerAttrs: AndesViewPagerAttrs =
        AndesViewPagerAttrsParser.parse(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val finalHeightSpec = if (andesViewPagerAttrs.andesViewPagerShouldMeasure) {
            getFinalHeightSpec(widthMeasureSpec, heightMeasureSpec)
        } else heightMeasureSpec

        super.onMeasure(widthMeasureSpec, finalHeightSpec)
    }

    private fun getFinalHeightSpec(widthMeasureSpec: Int, initialHeightSpec: Int): Int {
        var heightSpec = initialHeightSpec
        var finalHeight = UNSPECIFIED_HEIGHT

        // we get the tallest page (child) and take its height as the viewPager height
        getAllChildren().forEach { child ->
            child.measure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(UNSPECIFIED_HEIGHT, MeasureSpec.UNSPECIFIED)
            )
            val childHeight = child.measuredHeight
            if (childHeight > finalHeight) {
                finalHeight = childHeight
            }
        }

        val activity = getActivity()
        val marginTop = resources.getDimensionPixelSize(R.dimen.andes_modal_margin_48)
        val fixedComponentsHeight = getFixedComponentsHeight(widthMeasureSpec)
        val marginBottom = resources.getDimensionPixelSize(R.dimen.andes_modal_margin_48)

        if (finalHeight != UNSPECIFIED_HEIGHT) {
            // since the views we are measuring are scrollViews and they can exceed the screen height,
            // we need to limit the max with the screen height minus the margins the modal has and
            // the fixed content at the bottom
            finalHeight = finalHeight.coerceAtMost(
                ScreenUtils.getScreenHeight(
                    activity,
                    marginTop,
                    fixedComponentsHeight,
                    marginBottom
                )
            )
            heightSpec = MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY)
        }
        return heightSpec
    }

    /**
     * Returns the height of the pageIndicator and the buttonGroup (if present)
     */
    private fun getFixedComponentsHeight(widthMeasureSpec: Int): Int {
        val fixedComponentsContainer = getAllSiblings().last()
        fixedComponentsContainer.measure(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(UNSPECIFIED_HEIGHT, MeasureSpec.UNSPECIFIED)
        )

        return fixedComponentsContainer.measuredHeight
    }

    private fun getActivity(): Context {
        if (context is Activity) return context
        return (context as ContextThemeWrapper).baseContext
    }

    companion object {
        private const val UNSPECIFIED_HEIGHT = 0
    }
}
