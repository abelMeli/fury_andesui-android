package com.mercadolibre.android.andesui.demoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mercadolibre.android.andesui.demoapp.commons.AnalyticsHelper
import com.mercadolibre.android.andesui.demoapp.commons.AnalyticsTracker
import com.mercadolibre.android.andesui.demoapp.utils.COMPONENT_KEY
import com.mercadolibre.android.andesui.demoapp.utils.QUERY_PARAMETER_KEY
import com.mercadolibre.android.mlwebkit.webkitcomponent.WebViewComponent

class StorybookActivity : AppCompatActivity() {

    private lateinit var webviewComponent: WebViewComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.andesui_storybook_main)

        webviewComponent = findViewById(R.id.webview_storybook)

        val url = intent.data?.getQueryParameter(QUERY_PARAMETER_KEY)
        val isValidUrl = url?.contains("fury_frontend-andes-ui") ?: false

        if (isValidUrl) {
            webviewComponent.load(url)
        } else {
            Toast.makeText(this, "URL not allowed", Toast.LENGTH_SHORT).show()
        }

        val activityName = intent.getStringExtra(COMPONENT_KEY)

        activityName?.let {
            val componentName = AnalyticsHelper().getComponentName(activityName)
            AnalyticsTracker.logWebviewComponentTracking(componentName)
        }
    }
}
