package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.andesui.demoapp.commons.AnalyticsTracker

/**
 * Base class for the activities of the accessibility playground.
 * Includes the analytics tracker
 */
abstract class A11yPlaygroundActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()

        val className = this.javaClass.simpleName
        AnalyticsTracker.logA11yActivityTracking(className)
    }
}
