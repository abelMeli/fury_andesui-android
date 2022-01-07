package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Bundle
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.ActivityContentDescriptionBinding

class ContentDescriptionActivity : A11yPlaygroundActivity() {

    private val binding by lazy { ActivityContentDescriptionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initActionBar()
        configCircle()
    }

    private fun configCircle() {
        binding.cdIvSecondCircle.contentDescription =
            resources.getString(R.string.andes_demoapp_playground_cd_second)
    }

    private fun initActionBar() {
        val navBarTitle =
            resources.getString(R.string.andes_demoapp_playground_title_content_description)
        setSupportActionBar(binding.cdNavBar)
        supportActionBar?.title = navBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
