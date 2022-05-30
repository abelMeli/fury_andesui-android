package com.mercadolibre.android.andesui.demoapp

import android.app.Application
import android.content.Context
import android.os.Build
import android.provider.Settings
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.mercadolibre.android.andesui.demoapp.mock.MockConfigProvider
import com.mercadolibre.android.webkit.configurator.WebKitConfigurator

/**
 * Main Application class that extends from Application to execute the start method only once.
 */
class MainApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()

        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(this)
                // other setters
                .setRequestListeners(requestListeners)
                .build()
        Fresco.initialize(this, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)

        MockConfigProvider.configure()

        WebKitConfigurator().configure(this)
        firebaseAnalytics = FirebaseAnalytics.getInstance(applicationContext)
    }

    companion object {
        lateinit var firebaseAnalytics: FirebaseAnalytics
    }
}
