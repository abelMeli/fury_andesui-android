package com.mercadolibre.android.andesui.feedback.screen.header.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.mercadolibre.android.andesui.R

internal class AndesFeedbackScreenCongratsHeaderView : AndesFeedbackScreenHeaderView {

    constructor(context: Context) : super(context) {
        setupComponents()
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        setupComponents()
    }

    override fun setupComponents() {
        initComponents()
        super.setupComponents()
    }

    private fun initComponents() {
        val container = LayoutInflater.from(context)
            .inflate(R.layout.andes_layout_feedback_screen_header_congrats, this)

        description = container.findViewById(R.id.andes_feedbackscreen_congrats_header_description)
        overline = container.findViewById(R.id.andes_feedbackscreen_congrats_header_overline)
        title = container.findViewById(R.id.andes_feedbackscreen_congrats_header_title)
        highlight = container.findViewById(R.id.andes_feedbackscreen_congrats_header_highlight)
        assetContainer = container.findViewById(R.id.andes_feedbackscreen_congrats_header_image)
    }
}
