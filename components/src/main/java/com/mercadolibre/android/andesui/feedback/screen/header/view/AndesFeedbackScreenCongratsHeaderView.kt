package com.mercadolibre.android.andesui.feedback.screen.header.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponent
import com.mercadolibre.android.andesui.thumbnail.badge.size.AndesThumbnailBadgePillSize

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
        thumbnailBadge = container.findViewById(R.id.andes_feedbackscreen_congrats_header_image)
    }

    override fun setupThumbnailComponent(
        feedbackThumbnail: AndesFeedbackScreenAsset,
        type: AndesBadgeIconType
    ) {
        super.setupThumbnailComponent(feedbackThumbnail, type)
        thumbnailBadge.badgeComponent =
            AndesThumbnailBadgeComponent.IconPill(type, AndesThumbnailBadgePillSize.SIZE_56)
    }
}
