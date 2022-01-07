package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Bundle
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.ActivityAnnounceForA11yBinding

class AnnounceForA11yActivity : A11yPlaygroundActivity() {

    private val binding by lazy { ActivityAnnounceForA11yBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initActionBar()
        setupViews()
    }

    private fun setupViews() {
        binding.afaButtonSend.setOnClickListener { andesButton ->
            val message = binding.afaTfSend.text.orEmpty()
            andesButton.announceForAccessibility(message)
        }
    }

    private fun initActionBar() {
        val navBarTitle = resources.getString(R.string.andes_demoapp_playground_title_announce_a11y)
        setSupportActionBar(binding.afaNavBar)
        supportActionBar?.title = navBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
