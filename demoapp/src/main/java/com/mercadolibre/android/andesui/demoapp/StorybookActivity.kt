package com.mercadolibre.android.andesui.demoapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.andesui.demoapp.commons.AnalyticsHelper
import com.mercadolibre.android.andesui.demoapp.commons.AnalyticsTracker
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStorybookMainBinding
import com.mercadolibre.android.andesui.demoapp.utils.COMPONENT_KEY
import com.mercadolibre.android.andesui.demoapp.utils.QUERY_PARAMETER_KEY
import com.mercadolibre.android.mlwebkit.webkitcomponent.WebViewComponent

class StorybookActivity : AppCompatActivity() {

    private lateinit var webviewComponent: WebViewComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = AndesuiStorybookMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        webviewComponent = binding.webviewStorybook

        val url = intent.data?.getQueryParameter(QUERY_PARAMETER_KEY)
        val isValidUrl = url?.contains("fury_frontend-andes-ui") ?: false

        if (isValidUrl) {
            webviewComponent.load(url)
        } else {
            Toast.makeText(applicationContext, "URL not allowed", Toast.LENGTH_SHORT).show()
        }

        val activityName = intent.getStringExtra(COMPONENT_KEY)

        activityName?.let {
            val componentName = AnalyticsHelper().getComponentName(activityName)
            AnalyticsTracker.logWebviewComponentTracking(componentName)
        }
    }
}
