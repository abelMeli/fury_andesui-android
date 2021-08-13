package com.mercadolibre.android.andesui.badge

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.factory.AndesBadgeIconPillAttrs
import com.mercadolibre.android.andesui.badge.factory.AndesBadgeIconPillAttrsParser
import com.mercadolibre.android.andesui.badge.factory.AndesBadgeIconPillConfiguration
import com.mercadolibre.android.andesui.badge.factory.AndesBadgeIconPillConfigurationFactory
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize

import com.mercadolibre.android.andesui.badge.type.AndesBadgeType

class AndesBadgeIconPill : ConstraintLayout {

    /**
     * Getter and setter for [type].
     */
    var type: AndesBadgeType
        get() = andesBadgeIconPillAttrs.andesBadgeType
        set(value) {
            andesBadgeIconPillAttrs = andesBadgeIconPillAttrs.copy(andesBadgeType = value)
            setupDrawable(createConfig())
        }

    var size: AndesBadgePillSize
        get() = andesBadgeIconPillAttrs.andesBadgeSize
        set(value) {
            andesBadgeIconPillAttrs = andesBadgeIconPillAttrs.copy(andesBadgeSize = value)
            setupDrawable(createConfig())
        }

    private lateinit var andesBadgeIconPillAttrs: AndesBadgeIconPillAttrs
    private lateinit var containerView: View
    private lateinit var imageViewContainer: ImageView

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        type: AndesBadgeType = TYPE_DEFAULT,
        size: AndesBadgePillSize = SIZE_DEFAULT
    ): super(context) {
        initAttrs(type, size)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        andesBadgeIconPillAttrs = AndesBadgeIconPillAttrsParser.parse(context, attrs)
        setupComponents(createConfig())
    }

    private fun initAttrs(type: AndesBadgeType, size: AndesBadgePillSize) {
        andesBadgeIconPillAttrs = AndesBadgeIconPillAttrs(type, size)
        setupComponents(createConfig())
    }

    private fun setupComponents(config: AndesBadgeIconPillConfiguration) {
        containerView = LayoutInflater.from(context).inflate(R.layout.andes_layout_badge_icon_pill, this)
        imageViewContainer = containerView.findViewById(R.id.badge_icon_pill_image_view)
        setupDrawable(config)
        setupA11y()
    }

    private fun setupDrawable(config: AndesBadgeIconPillConfiguration) {
        imageViewContainer.setImageDrawable(config.icon)
    }

    private fun setupA11y() {
        importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
        isFocusable = false
        contentDescription = null
    }

    private fun createConfig() = AndesBadgeIconPillConfigurationFactory.create(context, andesBadgeIconPillAttrs)

    companion object {
        private val SIZE_DEFAULT = AndesBadgePillSize.SMALL
        private val TYPE_DEFAULT = AndesBadgeType.HIGHLIGHT
    }
}
