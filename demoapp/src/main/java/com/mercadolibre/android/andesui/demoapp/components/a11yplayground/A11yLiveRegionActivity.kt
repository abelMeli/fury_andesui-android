package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Bundle
import android.view.View
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.ActivityA11yLiveRegionBinding
import kotlin.random.Random

class A11yLiveRegionActivity : A11yPlaygroundActivity() {

    private val binding by lazy { ActivityA11yLiveRegionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initActionBar()
        setupViews()
    }

    private fun setupViews() {
        binding.alrTvRandomNumber.accessibilityLiveRegion = View.ACCESSIBILITY_LIVE_REGION_POLITE

        binding.alrButton1.setOnClickListener {
            val newValue = generateRandomNumber()
            binding.alrTvRandomNumber.text = resources.getString(
                R.string.andes_demoapp_playground_alr_tv_random_number, newValue
            )
        }

        binding.alrButton2.setOnClickListener {
            val newValue = generateRandomNumber()
            binding.alrTvRandomNumber2.text = resources.getString(
                R.string.andes_demoapp_playground_alr_tv_random_number, newValue
            )
        }
    }

    private fun generateRandomNumber() = Random.nextInt(RANDOM_FROM, RANDOM_UNTIL)

    private fun initActionBar() {
        val navBarTitle = resources.getString(R.string.andes_demoapp_playground_title_live_region)
        setSupportActionBar(binding.alrNavBar)
        supportActionBar?.title = navBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        private const val RANDOM_FROM = 0
        private const val RANDOM_UNTIL = 20
    }
}
