package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.textfield.AndesTextfield

class AnnounceForA11yActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announce_for_a11y)
        setupViews()
    }

    private fun setupViews() {
        findViewById<AndesButton>(R.id.afa_button_send).setOnClickListener { andesButton ->
            val message = findViewById<AndesTextfield>(R.id.afa_tf_send).text.orEmpty()
            andesButton.announceForAccessibility(message)
        }
    }
}
