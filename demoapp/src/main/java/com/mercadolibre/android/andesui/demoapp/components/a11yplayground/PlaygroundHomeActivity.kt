package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.utils.SafeIntent

class PlaygroundHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playground_home)
        setupButtons()
    }

    private fun setupButtons() {
        findViewById<AndesButton>(R.id.playground_content_description).setOnClickListener {
            startActivity(SafeIntent(this, "meli://andes/playground/contentdescription"))
        }
        findViewById<AndesButton>(R.id.playground_traversal_after).setOnClickListener {
            startActivity(SafeIntent(this, "meli://andes/playground/traversalafter"))
        }
        findViewById<AndesButton>(R.id.playground_next_focus).setOnClickListener {
            startActivity(SafeIntent(this, "meli://andes/playground/nextfocus"))
        }
        findViewById<AndesButton>(R.id.playground_important_for_a11y).setOnClickListener {
            startActivity(SafeIntent(this, "meli://andes/playground/importantfora11y"))
        }
        findViewById<AndesButton>(R.id.playground_live_region).setOnClickListener {
            startActivity(SafeIntent(this, "meli://andes/playground/a11yliveregion"))
        }
        findViewById<AndesButton>(R.id.playground_announce_a11y).setOnClickListener {
            startActivity(SafeIntent(this, "meli://andes/playground/announcefora11y"))
        }
        findViewById<AndesButton>(R.id.playground_a11y_heading).setOnClickListener {
            startActivity(SafeIntent(this, "meli://andes/playground/a11yheading"))
        }
        findViewById<AndesButton>(R.id.playground_semantic_views).setOnClickListener {
            startActivity(SafeIntent(this, "meli://andes/playground/semanticviews"))
        }
    }
}
