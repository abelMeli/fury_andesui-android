package com.mercadolibre.android.andesui.demoapp.commons

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import com.google.firebase.analytics.FirebaseAnalytics
import com.mercadolibre.android.andesui.demoapp.R

class CustomViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    private val firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(getContext())

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
        logTracking(0)
    }

    private fun logTracking(currentPosition: Int) {
        context.apply {
            val className = this.javaClass.simpleName

            val analyticsHelper = AnalyticsHelper()
            val component = analyticsHelper.getComponentName(className)
            val path = analyticsHelper.getPath(className, currentPosition)

            if (component != null && path != null) {
                val bundle = Bundle()
                bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, component)
                bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, path)
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
            } else {
                throw RuntimeException(getString(R.string.andesui_demoapp_error_tracking))
            }
        }
    }
}
