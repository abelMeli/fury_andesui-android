package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Bundle
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.ActivitySemanticViewsBinding

class SemanticViewsActivity : A11yPlaygroundActivity() {

    private val binding by lazy { ActivitySemanticViewsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initActionBar()
        configFirstLayout()
        configSecondLayout()
    }

    private fun configFirstLayout() {
        binding.asvSecondLayout.apply {
            isFocusable = true
            contentDescription = "Top rated app. Four and a half stars rating average"
        }
    }

    private fun configSecondLayout() {
        binding.asvThirdLayout.apply {
            isFocusable = true
            contentDescription = "Top priced app. Four dollars and ninety-nine cents"
        }
    }

    private fun initActionBar() {
        val navBarTitle = resources.getString(R.string.andes_demoapp_playground_title_semantic_views)
        setSupportActionBar(binding.asvNavBar)
        supportActionBar?.title = navBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
