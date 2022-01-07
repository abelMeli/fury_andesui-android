package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Bundle
import android.view.View
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.ActivityImportantForA11yBinding

class ImportantForA11yActivity : A11yPlaygroundActivity() {

    private val binding by lazy { ActivityImportantForA11yBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initActionBar()
        setupViews()
    }

    private fun setupViews() {
        binding.ifaLayoutSecond.importantForAccessibility =
            View.IMPORTANT_FOR_ACCESSIBILITY_NO

        binding.ifaLayoutThird.also { linearLayout ->
            linearLayout.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
            binding.ifaButton2.importantForAccessibility =
                View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
        }
    }

    private fun initActionBar() {
        val navBarTitle = resources.getString(R.string.andes_demoapp_playground_title_important_for_a11y)
        setSupportActionBar(binding.ifaNavBar)
        supportActionBar?.title = navBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
