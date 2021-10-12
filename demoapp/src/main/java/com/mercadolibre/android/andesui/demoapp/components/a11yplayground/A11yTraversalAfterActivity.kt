package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R

class A11yTraversalAfterActivity : AppCompatActivity() {

    private lateinit var button3: AndesButton
    private lateinit var button4: AndesButton
    private lateinit var button5: AndesButton
    private lateinit var button6: AndesButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a11y_traversal_after)
        initViews()
        setupNavigation()
    }

    private fun initViews() {
        button3 = findViewById(R.id.ata_button_3)
        button4 = findViewById(R.id.ata_button_4)
        button5 = findViewById(R.id.ata_button_5)
        button6 = findViewById(R.id.ata_button_6)
    }

    /**
     * the accessibilityTraversalAfter API is introduced in LOLLIPOP_MR1 (API 22),
     * and the MeLi current min version is set to API 21. So, to be able to use this method
     * we have two ways:
     *
     * -surround the method use with a version check (the value will not be set in
     * devices with version equal and less than LOLLIPOP).
     *
     * -override the a11yDelegate from the component and use the info object to set
     * the value needed (compatible with older versions).
     */
    private fun setupNavigation() {
        // example of use with version check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            button4.accessibilityTraversalAfter = button3.id
        }

        // example of use with retrocompatibility
        // creation of custom a11y delegate
        val newCustomDelegateForButton5 = object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View?,
                info: AccessibilityNodeInfoCompat?
            ) {
                info?.setTraversalAfter(button4)
                super.onInitializeAccessibilityNodeInfo(host, info)
            }
        }
        // setting the custom delegate to the view needed
        ViewCompat.setAccessibilityDelegate(button5, newCustomDelegateForButton5)

        // another example of use with retrocompatibility
        // creation of custom a11y delegate
        val newCustomDelegateForButton6 = object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View?,
                info: AccessibilityNodeInfoCompat?
            ) {
                info?.setTraversalAfter(button5)
                super.onInitializeAccessibilityNodeInfo(host, info)
            }
        }
        // setting the custom delegate to the view needed
        ViewCompat.setAccessibilityDelegate(button6, newCustomDelegateForButton6)
    }
}
