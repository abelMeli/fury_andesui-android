package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.demoapp.R

class SemanticViewsActivity : A11yPlaygroundActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_semantic_views)
        initActionBar()
        configFirstLayout()
        configSecondLayout()
    }

    private fun configFirstLayout() {
        findViewById<ConstraintLayout>(R.id.asv_second_layout).apply {
            isFocusable = true
            contentDescription = "Top rated app. Four and a half stars rating average"
        }
    }

    private fun configSecondLayout() {
        findViewById<ConstraintLayout>(R.id.asv_third_layout).apply {
            isFocusable = true
            contentDescription = "Top priced app. Four dollars and ninety-nine cents"
        }
    }

    private fun initActionBar() {
        val navBarTitle = resources.getString(R.string.andes_demoapp_playground_title_semantic_views)
        setSupportActionBar(findViewById(R.id.asv_nav_bar))
        supportActionBar?.title = navBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
