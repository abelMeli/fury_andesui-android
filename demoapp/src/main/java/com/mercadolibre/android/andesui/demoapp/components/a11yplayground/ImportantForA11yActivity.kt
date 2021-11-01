package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R

class ImportantForA11yActivity : A11yPlaygroundActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_important_for_a11y)
        initActionBar()
        setupViews()
    }

    private fun setupViews() {
        findViewById<LinearLayout>(R.id.ifa_layout_second).importantForAccessibility =
            View.IMPORTANT_FOR_ACCESSIBILITY_NO

        findViewById<LinearLayout>(R.id.ifa_layout_third).also { linearLayout ->
            linearLayout.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
            findViewById<AndesButton>(R.id.ifa_button_2).importantForAccessibility =
                View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
        }
    }

    private fun initActionBar() {
        val navBarTitle = resources.getString(R.string.andes_demoapp_playground_title_important_for_a11y)
        setSupportActionBar(findViewById(R.id.ifa_nav_bar))
        supportActionBar?.title = navBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
