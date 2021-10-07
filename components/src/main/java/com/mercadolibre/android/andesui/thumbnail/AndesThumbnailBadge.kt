package com.mercadolibre.android.andesui.thumbnail

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponent
import com.mercadolibre.android.andesui.thumbnail.badge.factory.AndesThumbnailBadgeAttrs
import com.mercadolibre.android.andesui.thumbnail.badge.factory.AndesThumbnailBadgeAttrsParser
import com.mercadolibre.android.andesui.thumbnail.badge.factory.AndesThumbnailBadgeConfiguration
import com.mercadolibre.android.andesui.thumbnail.badge.factory.AndesThumbnailBadgeConfigurationFactory
import com.mercadolibre.android.andesui.thumbnail.state.AndesThumbnailState
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.utils.buildRing

/**
 * [AndesThumbnail] component with a badge in order to improve the display of information.
 */
@Suppress("TooManyFunctions")
class AndesThumbnailBadge : ConstraintLayout {

    private lateinit var outlineView: View
    private lateinit var badge: View
    private lateinit var andesThumbnailBadgeAttrs: AndesThumbnailBadgeAttrs
    private lateinit var thumbnail: AndesThumbnail

    /**
     * Getter and setter for [badgeComponent].
     */
    var badgeComponent: AndesThumbnailBadgeComponent
        get() = andesThumbnailBadgeAttrs.badge
        set(value) {
            andesThumbnailBadgeAttrs = andesThumbnailBadgeAttrs.copy(badge = value)
            createConfig().let {
                setupThumbnailSize(it)
                setupThumbnailBadgeTint(it)
                setupThumbnailOutline(it)
                setupBadgeComponent(it)
                setupThumbnailBadgeVisibility(it)
            }
        }

    /**
     * Getter and setter for [image].
     */
    var image: Drawable
        get() = andesThumbnailBadgeAttrs.image
        set(value) {
            andesThumbnailBadgeAttrs = andesThumbnailBadgeAttrs.copy(image = value)
            createConfig().let {
                setupThumbnailImage(it)
                setupThumbnailBadgeTint(it)
            }
        }

    /**
     * Getter and setter for [thumbnailType].
     */
    var thumbnailType: AndesThumbnailBadgeType
        get() = andesThumbnailBadgeAttrs.thumbnailType
        set(value) {
            andesThumbnailBadgeAttrs = andesThumbnailBadgeAttrs.copy(thumbnailType = value)
            createConfig().let {
                setupThumbnailType(it)
                setupThumbnailBadgeTint(it)
                setupThumbnailBadgeVisibility(it)
            }
        }

    private constructor(context: Context) : super(context) {
        throw IllegalStateException("Constructor without parameters in AndesThumbnailBadge is not allowed. You must provide some attributes.")
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        image: Drawable,
        badgeComponent: AndesThumbnailBadgeComponent,
        thumbnailType: AndesThumbnailBadgeType = AndesThumbnailBadgeType.Icon
    ) : super(context) {
        initAttrs(image, badgeComponent, thumbnailType)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        andesThumbnailBadgeAttrs = AndesThumbnailBadgeAttrsParser.parse(context, attrs)
        setupComponents(createConfig())
    }

    private fun initAttrs(
        image: Drawable,
        badgeComponent: AndesThumbnailBadgeComponent,
        thumbnailType: AndesThumbnailBadgeType
    ) {
        andesThumbnailBadgeAttrs = AndesThumbnailBadgeAttrs(image, badgeComponent, thumbnailType)
        setupComponents(createConfig())
    }

    /**
     * Responsible for setting up all properties of each component that is part of this thumbnail.
     * Is like a choreographer ;)
     */
    private fun setupComponents(config: AndesThumbnailBadgeConfiguration) {
        setupViewId()
        initComponents(config)

        setupThumbnailBadgeTint(config)
        setupThumbnailOutline(config)
        setupThumbnailBadgeVisibility(config)
    }

    private fun setupThumbnailOutline(config: AndesThumbnailBadgeConfiguration) {
        with(outlineView) {
            layoutParams = LayoutParams(
                config.thumbnailSize.size.diameter(context).toInt(),
                config.thumbnailSize.size.diameter(context).toInt()
            )
            background = buildRing(config.badgeColor, config.badgeOutline)
        }
    }

    private fun setupBadgeComponent(config: AndesThumbnailBadgeConfiguration) {
        removeView(badge)
        createBadgeComponent(config)
    }

    private fun setupThumbnailType(config: AndesThumbnailBadgeConfiguration) {
        thumbnail.type = config.thumbnailType
    }

    private fun setupThumbnailImage(config: AndesThumbnailBadgeConfiguration) {
        thumbnail.image = config.image
    }

    private fun setupThumbnailSize(config: AndesThumbnailBadgeConfiguration) {
        thumbnail.size = config.thumbnailSize
    }

    private fun setupThumbnailBadgeTint(config: AndesThumbnailBadgeConfiguration) {
        thumbnail.tintImage(config.thumbnailTintColor)
    }

    private fun initComponents(config: AndesThumbnailBadgeConfiguration) {
        thumbnail = AndesThumbnail(
            context,
            config.badgeColor.toAndesColor(),
            AndesThumbnailHierarchy.DEFAULT,
            config.image,
            config.thumbnailType,
            config.thumbnailSize,
            AndesThumbnailState.ENABLED
        )
        addView(thumbnail)

        outlineView = View(context).apply { id = View.generateViewId() }
        addView(outlineView)

        createBadgeComponent(config)
    }

    private fun setupThumbnailBadgeVisibility(config: AndesThumbnailBadgeConfiguration) {
        badge.visibility = config.badgeVisibility
    }

    private fun createBadgeComponent(config: AndesThumbnailBadgeConfiguration) {
        badge = config.badgeComponent.getView(context)
        addView(badge)

        ConstraintSet().apply {
            clone(this@AndesThumbnailBadge)
            config.badgeComponent.getComponentPosition(context, this, badge, thumbnail)
            applyTo(this@AndesThumbnailBadge)
        }
    }

    private fun setupViewId() {
        if (id == NO_ID) {
            id = View.generateViewId()
        }
    }

    private fun createConfig() =
        AndesThumbnailBadgeConfigurationFactory.create(context, andesThumbnailBadgeAttrs)
}
