package com.mercadolibre.android.andesui.thumbnail

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponent
import com.mercadolibre.android.andesui.thumbnail.badge.factory.AndesThumbnailBadgeAttrs
import com.mercadolibre.android.andesui.thumbnail.badge.factory.AndesThumbnailBadgeAttrsParser
import com.mercadolibre.android.andesui.thumbnail.badge.factory.AndesThumbnailBadgeConfiguration
import com.mercadolibre.android.andesui.thumbnail.badge.factory.AndesThumbnailBadgeConfigurationFactory
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.thumbnail.state.AndesThumbnailState
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
     * Getter and setter for [image]. If a image is setter to a ThumbnailBadge of type Text, the type
     * will be change to Icon.
     */
    var image: Drawable
        get() = getDrawable()
        set(value) {
            if (thumbnailType is AndesThumbnailBadgeType.Text) {
                val defaultType = AndesThumbnailBadgeType.Icon
                andesThumbnailBadgeAttrs =
                    andesThumbnailBadgeAttrs.copy(thumbnailType = defaultType)
            }
            andesThumbnailBadgeAttrs = andesThumbnailBadgeAttrs.copy(image = value)
            createConfig().let {
                setupAssetType(it)
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
                setupAssetType(it)
                setupThumbnailBadgeVisibility(it)
            }
        }

    /**
     * Getter and setter for the [text].
     */
    var text: String
        get() = andesThumbnailBadgeAttrs.text
        set(value) {
            andesThumbnailBadgeAttrs =
                andesThumbnailBadgeAttrs.copy(thumbnailType = AndesThumbnailBadgeType.Text)
            andesThumbnailBadgeAttrs = andesThumbnailBadgeAttrs.copy(text = value)
            setupAssetType(createConfig())

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

    /***
     * This constructor allows to generate a ThumbnailBadge with letters instead of an image.
     */
    constructor(
        context: Context,
        badgeComponent: AndesThumbnailBadgeComponent,
        thumbnailType: AndesThumbnailBadgeType,
        text: String
    ) : super(context) {
        initAttrs(badgeComponent, thumbnailType, text)
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
        andesThumbnailBadgeAttrs =
            AndesThumbnailBadgeAttrs(image, badgeComponent, thumbnailType, EMPTY_TEXT)
        setupComponents(createConfig())
    }

    private fun initAttrs(
        badgeComponent: AndesThumbnailBadgeComponent,
        thumbnailType: AndesThumbnailBadgeType,
        text: String
    ) {
        andesThumbnailBadgeAttrs =
            AndesThumbnailBadgeAttrs(null, badgeComponent, thumbnailType, text)
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

    private fun setupAssetType(config: AndesThumbnailBadgeConfiguration) {
        thumbnail.assetType = config.assetType
        thumbnail.thumbnailShape = config.shape
    }

    private fun setupThumbnailSize(config: AndesThumbnailBadgeConfiguration) {
        thumbnail.size = config.thumbnailSize
    }

    private fun setupThumbnailBadgeTint(config: AndesThumbnailBadgeConfiguration) {
        thumbnail.tintImage(config.thumbnailTintColor)
    }

    private fun getDrawable(): Drawable =
        findViewById<ImageView>(R.id.andes_thumbnail_image).drawable

    private fun initComponents(config: AndesThumbnailBadgeConfiguration) {
        thumbnail = AndesThumbnail(
            context,
            config.badgeColor.toAndesColor(),
            config.assetType,
            config.shape,
            AndesThumbnailHierarchy.DEFAULT,
            config.thumbnailSize,
            AndesThumbnailState.ENABLED,
            ImageView.ScaleType.CENTER_CROP
        )
        addView(thumbnail)

        outlineView = View(context).apply { id = R.id.andes_thumbnail_badge_outline }
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

    private companion object {
        // set the deprecated val type as default Icon
        private const val EMPTY_TEXT = ""
    }
}
