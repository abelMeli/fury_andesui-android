package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.ActivityA11yTraversalAfterBinding

class A11yTraversalAfterActivity : A11yPlaygroundActivity() {

    private val binding by lazy { ActivityA11yTraversalAfterBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initActionBar()
        setupNavigation()
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
            binding.ataButton4.accessibilityTraversalAfter = binding.ataButton3.id
        }

        // example of use with retrocompatibility
        // creation of custom a11y delegate
        val newCustomDelegateForButton5 = object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View?,
                info: AccessibilityNodeInfoCompat?
            ) {
                info?.setTraversalAfter(binding.ataButton4)
                super.onInitializeAccessibilityNodeInfo(host, info)
            }
        }
        // setting the custom delegate to the view needed
        ViewCompat.setAccessibilityDelegate(binding.ataButton5, newCustomDelegateForButton5)

        // another example of use with retrocompatibility
        // creation of custom a11y delegate
        val newCustomDelegateForButton6 = object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View?,
                info: AccessibilityNodeInfoCompat?
            ) {
                info?.setTraversalAfter(binding.ataButton5)
                super.onInitializeAccessibilityNodeInfo(host, info)
            }
        }
        // setting the custom delegate to the view needed
        ViewCompat.setAccessibilityDelegate(binding.ataButton6, newCustomDelegateForButton6)
    }

    private fun initActionBar() {
        val navBarTitle = resources.getString(R.string.andes_demoapp_playground_title_traversal_after)
        setSupportActionBar(binding.ataNavBar)
        supportActionBar?.title = navBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
