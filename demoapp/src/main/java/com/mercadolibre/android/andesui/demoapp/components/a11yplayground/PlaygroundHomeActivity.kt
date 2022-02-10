package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.andesui.demoapp.commons.AnalyticsTracker
import com.mercadolibre.android.andesui.demoapp.databinding.ActivityPlaygroundHomeBinding
import com.mercadolibre.android.andesui.demoapp.utils.SafeIntent

class PlaygroundHomeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityPlaygroundHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initActionBar()
        setupButtons()
        AnalyticsTracker.logA11yActivityTracking(this.javaClass.simpleName)
    }

    private fun setupButtons() {
        binding.playgroundContentDescription.setOnClickListener {
            startActivity(SafeIntent(this, "andes://playground/contentdescription"))
        }
        binding.playgroundTraversalAfter.setOnClickListener {
            startActivity(SafeIntent(this, "andes://playground/traversalafter"))
        }
        binding.playgroundNextFocus.setOnClickListener {
            startActivity(SafeIntent(this, "andes://playground/nextfocus"))
        }
        binding.playgroundImportantForA11y.setOnClickListener {
            startActivity(SafeIntent(this, "andes://playground/importantfora11y"))
        }
        binding.playgroundLiveRegion.setOnClickListener {
            startActivity(SafeIntent(this, "andes://playground/a11yliveregion"))
        }
        binding.playgroundAnnounceA11y.setOnClickListener {
            startActivity(SafeIntent(this, "andes://playground/announcefora11y"))
        }
        binding.playgroundA11yHeading.setOnClickListener {
            startActivity(SafeIntent(this, "andes://playground/a11yheading"))
        }
        binding.playgroundSemanticViews.setOnClickListener {
            startActivity(SafeIntent(this, "andes://playground/semanticviews"))
        }
    }

    private fun initActionBar() {
        setSupportActionBar(binding.a11yHomeNavBar)
        supportActionBar?.title = "Andes UI: A11y Playground"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
