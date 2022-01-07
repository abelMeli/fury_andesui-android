package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Bundle
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.ActivityNextFocusBinding

@SuppressWarnings("TooManyFunctions")
class NextFocusActivity : A11yPlaygroundActivity() {

    private val binding by lazy { ActivityNextFocusBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initActionBar()
        setupFocus()
    }

    private fun setupFocus() {
        setupFocusBtn10()
        setupFocusBtn11()
        setupFocusBtn12()
        setupFocusBtn13()
        setupFocusBtn14()
        setupFocusBtn15()
        setupFocusBtn16()
        setupFocusBtn17()
        setupFocusBtn18()
    }

    private fun setupFocusBtn10() {
        binding.nfButton10.apply {
            nextFocusRightId = binding.nfButton11.id
            nextFocusUpId = binding.nfButton7.id
            nextFocusDownId = binding.nfButton13.id
            nextFocusForwardId = binding.nfButton11.id
        }
    }

    private fun setupFocusBtn11() {
        binding.nfButton11.apply {
            nextFocusLeftId = binding.nfButton10.id
            nextFocusRightId = binding.nfButton12.id
            nextFocusUpId = binding.nfButton8.id
            nextFocusDownId = binding.nfButton14.id
            nextFocusForwardId = binding.nfButton12.id
        }
    }

    private fun setupFocusBtn12() {
        binding.nfButton12.apply {
            nextFocusLeftId = binding.nfButton11.id
            nextFocusUpId = binding.nfButton9.id
            nextFocusDownId = binding.nfButton15.id
        }
    }

    private fun setupFocusBtn13() {
        binding.nfButton13.apply {
            nextFocusRightId = binding.nfButton14.id
            nextFocusUpId = binding.nfButton10.id
            nextFocusDownId = binding.nfButton16.id
            nextFocusForwardId = binding.nfButton14.id
        }
    }

    private fun setupFocusBtn14() {
        binding.nfButton14.apply {
            nextFocusLeftId = binding.nfButton13.id
            nextFocusRightId = binding.nfButton15.id
            nextFocusUpId = binding.nfButton11.id
            nextFocusDownId = binding.nfButton17.id
            nextFocusForwardId = binding.nfButton15.id
        }
    }

    private fun setupFocusBtn15() {
        binding.nfButton15.apply {
            nextFocusLeftId = binding.nfButton14.id
            nextFocusUpId = binding.nfButton12.id
            nextFocusDownId = binding.nfButton18.id
        }
    }

    private fun setupFocusBtn16() {
        binding.nfButton16.apply {
            nextFocusRightId = binding.nfButton17.id
            nextFocusUpId = binding.nfButton13.id
            nextFocusForwardId = binding.nfButton17.id
        }
    }

    private fun setupFocusBtn17() {
        binding.nfButton17.apply {
            nextFocusLeftId = binding.nfButton16.id
            nextFocusRightId = binding.nfButton18.id
            nextFocusUpId = binding.nfButton14.id
            nextFocusForwardId = binding.nfButton18.id
        }
    }

    private fun setupFocusBtn18() {
        binding.nfButton18.apply {
            nextFocusLeftId = binding.nfButton17.id
            nextFocusUpId = binding.nfButton15.id
        }
    }

    private fun initActionBar() {
        val navBarTitle = resources.getString(R.string.andes_demoapp_playground_title_next_focus)
        setSupportActionBar(binding.nfaNavBar)
        supportActionBar?.title = navBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
