package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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

        findViewById<ImageView>(R.id.asv_iv_second_semantic).isFocusable = false
        findViewById<TextView>(R.id.asv_tv_not_focusable_second).isFocusable = false
        findViewById<TextView>(R.id.asv_tv_not_focusable_third).isFocusable = false
    }

    private fun configSecondLayout() {
        findViewById<ConstraintLayout>(R.id.asv_third_layout).apply {
            isFocusable = true
            contentDescription = "Top priced app. Four dollars and ninety-nine cents"
        }

        findViewById<ImageView>(R.id.asv_iv_third_semantic).isFocusable = false
        findViewById<TextView>(R.id.asv_tv_not_focusable_fourth).isFocusable = false
        findViewById<TextView>(R.id.asv_tv_not_focusable_fifth).isFocusable = false
    }
}
