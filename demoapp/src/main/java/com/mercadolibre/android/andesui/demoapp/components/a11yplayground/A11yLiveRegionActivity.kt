package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import kotlin.random.Random

class A11yLiveRegionActivity : AppCompatActivity() {

    private lateinit var randomNumberTextViewFirst: TextView
    private lateinit var randomNumberTextViewSecond: TextView
    private lateinit var announceButtonFirst: AndesButton
    private lateinit var announceButtonSecond: AndesButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a11y_live_region)
        initViews()
        setupViews()
    }

    private fun initViews() {
        randomNumberTextViewFirst = findViewById(R.id.alr_tv_random_number)
        randomNumberTextViewSecond = findViewById(R.id.alr_tv_random_number_2)
        announceButtonFirst = findViewById(R.id.alr_button_1)
        announceButtonSecond = findViewById(R.id.alr_button_2)
    }

    private fun setupViews() {
        randomNumberTextViewFirst.accessibilityLiveRegion = View.ACCESSIBILITY_LIVE_REGION_POLITE

        announceButtonFirst.setOnClickListener {
            val newValue = generateRandomNumber()
            randomNumberTextViewFirst.text = resources.getString(
                R.string.andes_demoapp_playground_alr_tv_random_number, newValue
            )
        }

        announceButtonSecond.setOnClickListener {
            val newValue = generateRandomNumber()
            randomNumberTextViewSecond.text = resources.getString(
                R.string.andes_demoapp_playground_alr_tv_random_number, newValue
            )
        }
    }

    private fun generateRandomNumber() = Random.nextInt(RANDOM_FROM, RANDOM_UNTIL)

    companion object {
        private const val RANDOM_FROM = 0
        private const val RANDOM_UNTIL = 20
    }
}
