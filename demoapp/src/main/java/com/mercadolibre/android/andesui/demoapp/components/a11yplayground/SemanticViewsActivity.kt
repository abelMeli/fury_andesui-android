package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.demoapp.R

class SemanticViewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_semantic_views)
        configFirstLayout()
        configSecondLayout()
    }

    private fun configFirstLayout() {
        findViewById<ConstraintLayout>(R.id.asv_second_layout).apply {
            isFocusable = true
            contentDescription = "Top rated app. Four and a half stars rating average"
        }
    }

    private fun configSecondLayout() {
        findViewById<ConstraintLayout>(R.id.asv_third_layout).apply {
            isFocusable = true
            contentDescription = "Top priced app. Four dollars and ninety-nine cents"
        }
    }
}
