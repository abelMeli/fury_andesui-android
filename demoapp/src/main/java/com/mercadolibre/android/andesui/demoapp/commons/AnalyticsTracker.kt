package com.mercadolibre.android.andesui.demoapp.commons

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.mercadolibre.android.andesui.demoapp.MainApplication

internal object AnalyticsTracker {

    private val analyticsHelper = AnalyticsHelper()
    private val firebaseAnalytics = MainApplication.firebaseAnalytics
    private const val ERROR_TRACKING = "Current Activity could not be tracked. Add component and screens entries to AnalyticsHelper."
    private const val A11Y_SCREEN_EVENT = "a11y_screen"
    private const val WEBVIEW_SCREEN_EVENT = "webview_screen"

    fun logComponentActivityTracking(className: String, viewPagerPosition: Int) {
        val component = analyticsHelper.getComponentName(className)
        val path = analyticsHelper.getPath(className, viewPagerPosition)

        if (!component.isNullOrEmpty() && !path.isNullOrEmpty()) {
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, component)
            bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, path)
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
        } else {
            throw AnalyticsTrackingException(ERROR_TRACKING)
        }
    }

    fun logA11yActivityTracking(className: String) {
        val a11yExample = analyticsHelper.getA11yExample(className)

        if (!a11yExample.isNullOrEmpty()) {
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, a11yExample)
            firebaseAnalytics.logEvent(A11Y_SCREEN_EVENT, bundle)
        } else {
            throw AnalyticsTrackingException(ERROR_TRACKING)
        }
    }

    fun trackSimpleScreen(screenName: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, Bundle())
    }

    fun logWebviewComponentTracking(componentName: String?) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, componentName)
        firebaseAnalytics.logEvent(WEBVIEW_SCREEN_EVENT, bundle)
    }
}
