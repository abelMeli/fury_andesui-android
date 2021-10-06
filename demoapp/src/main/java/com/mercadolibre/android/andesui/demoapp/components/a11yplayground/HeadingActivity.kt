package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.mercadolibre.android.andesui.demoapp.R

class HeadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heading)
        setupViews()
    }

    /**
     * the isHeading API is introduced in PIE (API 28),
     * and the MeLi current min version is set to API 21. So, to be able to use this method
     * we have two ways:
     *
     * -surround the method use with a version check (the value will not be set in
     * devices with version equal and less than PIE).
     *
     * -override the a11yDelegate from the component and use the info object to set
     * the value needed (compatible with older versions).
     */
    private fun setupViews() {
        val thirdHeading = findViewById<TextView>(R.id.heading_third_heading)

        val customA11yDelegate = object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View?,
                info: AccessibilityNodeInfoCompat?
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info?.isHeading = true
            }
        }

        ViewCompat.setAccessibilityDelegate(thirdHeading, customA11yDelegate)
    }
}
