package com.mercadolibre.android.andesui.demoapp.components.list

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.mercadolibre.android.andesui.demoapp.R

class ContentCustomItem(context: Context) : FrameLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.andes_ui_custom_content_item, this, true)
    }
}