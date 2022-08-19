package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mercadolibre.android.andesui.demoapp.BuildConfig
import com.mercadolibre.android.andesui.demoapp.commons.AnalyticsTracker
import com.mercadolibre.android.andesui.demoapp.databinding.ActivityA11yLandingBinding

class A11yLandingActivity : AppCompatActivity() {

    private val binding by lazy { ActivityA11yLandingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.webviewLanding.load(A11Y_LANDING_URL)
    }

    override fun onResume() {
        super.onResume()
        val className = this.javaClass.simpleName
        AnalyticsTracker.logA11yActivityTracking(className)
    }

    private companion object {
        const val A11Y_LANDING_URL = BuildConfig.A11Y_LANDING_URL
    }
}
