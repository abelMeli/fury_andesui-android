package com.mercadolibre.android.andesui.demoapp.commons

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

class CustomViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    init {
        this.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                // Nothing to do
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // Nothing to do
            }

            override fun onPageSelected(position: Int) {
                logTracking(position)
            }
        })
    }

    private fun logTracking(currentPosition: Int) {
        val className = context.javaClass.simpleName
        AnalyticsTracker.logComponentActivityTracking(className, currentPosition)
    }

    /**
     * Method overridden to track first page when viewpager is created.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        logTracking(currentItem)
    }
}
