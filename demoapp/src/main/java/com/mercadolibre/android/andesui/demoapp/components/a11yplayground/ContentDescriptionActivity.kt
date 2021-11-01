package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Bundle
import android.widget.ImageView
import com.mercadolibre.android.andesui.demoapp.R

class ContentDescriptionActivity : A11yPlaygroundActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_description)
        initActionBar()
        configCircle()
    }

    private fun configCircle() {
        findViewById<ImageView>(R.id.cd_iv_second_circle).contentDescription =
            resources.getString(R.string.andes_demoapp_playground_cd_second)
    }

    private fun initActionBar() {
        val navBarTitle =
            resources.getString(R.string.andes_demoapp_playground_title_content_description)
        setSupportActionBar(findViewById(R.id.cd_nav_bar))
        supportActionBar?.title = navBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
