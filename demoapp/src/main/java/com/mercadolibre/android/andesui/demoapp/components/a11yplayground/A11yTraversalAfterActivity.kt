package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

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

    private fun setupNavigation() {
        binding.ataButton4.accessibilityTraversalAfter = binding.ataButton3.id

        binding.ataButton5.accessibilityTraversalAfter = binding.ataButton4.id

        binding.ataButton6.accessibilityTraversalAfter = binding.ataButton5.id
    }

    private fun initActionBar() {
        val navBarTitle = resources.getString(R.string.andes_demoapp_playground_title_traversal_after)
        setSupportActionBar(binding.ataNavBar)
        supportActionBar?.title = navBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
