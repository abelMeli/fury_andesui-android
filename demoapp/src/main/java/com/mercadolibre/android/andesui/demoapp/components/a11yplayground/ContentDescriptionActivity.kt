package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.mercadolibre.android.andesui.demoapp.R

class ContentDescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_description)
        configCircle()
    }

    private fun configCircle() {
        findViewById<ImageView>(R.id.cd_iv_second_circle).contentDescription =
            resources.getString(R.string.andes_demoapp_playground_cd_second)
    }
}
