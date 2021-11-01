package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Bundle
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.textfield.AndesTextfield

class AnnounceForA11yActivity : A11yPlaygroundActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announce_for_a11y)
        initActionBar()
        setupViews()
    }

    private fun setupViews() {
        findViewById<AndesButton>(R.id.afa_button_send).setOnClickListener { andesButton ->
            val message = findViewById<AndesTextfield>(R.id.afa_tf_send).text.orEmpty()
            andesButton.announceForAccessibility(message)
        }
    }

    private fun initActionBar() {
        val navBarTitle = resources.getString(R.string.andes_demoapp_playground_title_announce_a11y)
        setSupportActionBar(findViewById(R.id.afa_nav_bar))
        supportActionBar?.title = navBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
