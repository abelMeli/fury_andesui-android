package com.mercadolibre.android.andesui.thumbnail

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ImageView.ScaleType.CENTER_CROP
import androidx.core.graphics.drawable.DrawableCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.thumbnail.assetType.AndesThumbnailAssetType
import com.mercadolibre.android.andesui.thumbnail.factory.AndesThumbnailAttrs
import com.mercadolibre.android.andesui.thumbnail.factory.AndesThumbnailAttrsParser
import com.mercadolibre.android.andesui.thumbnail.factory.AndesThumbnailConfiguration
import com.mercadolibre.android.andesui.thumbnail.factory.AndesThumbnailConfigurationFactory
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.thumbnail.shape.AndesThumbnailShape
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import com.mercadolibre.android.andesui.thumbnail.state.AndesThumbnailState
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailType

@Suppress("TooManyFunctions")
class AndesThumbnail : FrameLayout {

    /**
     * Getter and setter for [accentColor].
     */
    var accentColor: AndesColor
        get() = andesThumbnailAttrs.andesThumbnailAccentColor
        set(value) {
            andesThumbnailAttrs = andesThumbnailAttrs.copy(andesThumbnailAccentColor = value)
            val config = createConfig()
            setupBackground(config)
            setupImageColor(config)
        }

    /**
     * Getter and setter for [hierarchy].
     */
    var hierarchy: AndesThumbnailHierarchy
        get() = andesThumbnailAttrs.andesThumbnailHierarchy
        set(value) {
            andesThumbnailAttrs = andesThumbnailAttrs.copy(andesThumbnailHierarchy = value)
            val config = createConfig()
            setupBackground(config)
            setupImageColor(config)
        }

    /**
     * Getter and setter for [image].This parameter only has effect on
     *  ([AndesThumbnailAssetType.Icon], [AndesThumbnailAssetType.Image]).
     */
    var image: Drawable
        get() = imageFrame.drawable
        set(value) {
            assetType = assetType.createWith(value)
        }

    /**
     * Getter and setter for [type].
     */
    @Deprecated("Use AssetType and thumbnailShape")
    var type: AndesThumbnailType
        get() = andesThumbnailAttrs.andesThumbnailType
        set(value) {
            andesThumbnailAttrs = andesThumbnailAttrs.copy(
                andesThumbnailType = value,
                andesThumbnailAssetType = value.toAssetType(image),
                andesThumbnailShape = value.toShape()
            )
            val config = createConfig()
            setupBackground(config)
            setupImage(config)
        }

    /**
     * Getter and setter for [size].
     */
    var size: AndesThumbnailSize
        get() = andesThumbnailAttrs.andesThumbnailSize
        set(value) {
            andesThumbnailAttrs = andesThumbnailAttrs.copy(andesThumbnailSize = value)
            val config = createConfig()
            setupBackgroundSize(config.size, config.cornerRadius)
            setupFrameSize(config)
        }

    /**
     * Getter and setter for [state].
     */
    var state: AndesThumbnailState
        get() = andesThumbnailAttrs.andesThumbnailState
        set(value) {
            andesThumbnailAttrs = andesThumbnailAttrs.copy(andesThumbnailState = value)
            val config = createConfig()
            setupBackground(config)
            setupImageColor(config)
        }

    /**
     * Getter and setter for the image [scaleType]. This parameter only affects components with
     * image type ([AndesThumbnailAssetType.Image]). For the
     * others this setter will have no effect.
     */
    var scaleType: ImageView.ScaleType
        get() = andesThumbnailAttrs.andesThumbnailScaleType
        set(value) {
            andesThumbnailAttrs = andesThumbnailAttrs.copy(andesThumbnailScaleType = value)
            createConfig().let {
                setupClipToOutline(it)
                setupScaleType(it)
            }
        }

    /**
     * Getter and setter for the [assetType].
     */
    var assetType: AndesThumbnailAssetType
        get() = andesThumbnailAttrs.andesThumbnailAssetType
        set(value) {
            andesThumbnailAttrs = andesThumbnailAttrs.copy(andesThumbnailAssetType = value)
            val config = createConfig()
            setupBackground(config)
            setupImage(config)
        }

    /**
     * Getter and setter for the [thumbnailShape].
     */
    var thumbnailShape: AndesThumbnailShape
        get() = andesThumbnailAttrs.andesThumbnailShape
        set(value) {
            andesThumbnailAttrs = andesThumbnailAttrs.copy(andesThumbnailShape = value)
            setupBackground(createConfig())
        }

    private val imageFrame by lazy { findViewById<ImageView>(R.id.andes_thumbnail_image) }

    private var andesThumbnailAttrs: AndesThumbnailAttrs

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        andesThumbnailAttrs = AndesThumbnailAttrsParser.parse(context, attrs)
        initAttrs(attrs)
    }

    @Deprecated("Use constructor with assetType and shape" ,
        ReplaceWith("AndesThumbnail(context, accentColor, assetType, thumbnailShape, hierarchy, size, state, scaleType"))
    @Suppress("LongParameterList")
    constructor(
        context: Context,
        accentColor: AndesColor,
        hierarchy: AndesThumbnailHierarchy = AndesThumbnailHierarchy.LOUD,
        image: Drawable,
        type: AndesThumbnailType = AndesThumbnailType.ICON,
        size: AndesThumbnailSize = AndesThumbnailSize.SIZE_48,
        state: AndesThumbnailState = AndesThumbnailState.ENABLED
    ) : this(context, accentColor, type.toAssetType(image), type.toShape(), hierarchy, size, state, DEFAULT_SCALE_TYPE)

    @Deprecated("Use constructor with assetType and shape")
    @Suppress("LongParameterList")
    constructor(
        context: Context,
        accentColor: AndesColor,
        hierarchy: AndesThumbnailHierarchy = AndesThumbnailHierarchy.LOUD,
        image: Drawable,
        type: AndesThumbnailType = AndesThumbnailType.ICON,
        size: AndesThumbnailSize = AndesThumbnailSize.SIZE_48,
        state: AndesThumbnailState = AndesThumbnailState.ENABLED,
        scaleType: ImageView.ScaleType = DEFAULT_SCALE_TYPE
    ) : this(context, accentColor, type.toAssetType(image), type.toShape(), hierarchy, size, state, scaleType)

    /**
     * This constructor generate a Thumbnail with shape and assetType as independent attributes.
     */
    @Suppress("LongParameterList")
    constructor(
        context: Context,
        accentColor: AndesColor,
        assetType: AndesThumbnailAssetType,
        thumbnailShape: AndesThumbnailShape = AndesThumbnailShape.Circle,
        hierarchy: AndesThumbnailHierarchy = AndesThumbnailHierarchy.DEFAULT,
        size: AndesThumbnailSize = AndesThumbnailSize.SIZE_48,
        state: AndesThumbnailState = AndesThumbnailState.ENABLED,
        scaleType: ImageView.ScaleType = DEFAULT_SCALE_TYPE
    ) : super(context) {
        andesThumbnailAttrs = AndesThumbnailAttrs(
            accentColor, hierarchy, TYPE_DEFAULT, size, state, scaleType, assetType, thumbnailShape
        )
        initAttrs(accentColor, hierarchy, size, state, assetType, thumbnailShape, scaleType)
    }

    /**
     * Sets the proper configuration class for this thumbnail based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        val config = AndesThumbnailConfigurationFactory.create(context, andesThumbnailAttrs)
        setupComponents(config)
    }

    @Suppress("LongParameterList")
    private fun initAttrs(
        accentColor: AndesColor,
        hierarchy: AndesThumbnailHierarchy,
        image: Drawable,
        type: AndesThumbnailType,
        size: AndesThumbnailSize,
        state: AndesThumbnailState,
        scaleType: ImageView.ScaleType
    ) {
        val config = AndesThumbnailConfigurationFactory.create(context, andesThumbnailAttrs)
        setupComponents(config)
    }

    @Suppress("LongParameterList")
    private fun initAttrs(
        accentColor: AndesColor,
        hierarchy: AndesThumbnailHierarchy,
        size: AndesThumbnailSize,
        state: AndesThumbnailState,
        assetType: AndesThumbnailAssetType,
        shape: AndesThumbnailShape,
        scaleType: ImageView.ScaleType
    ) {
        val config = AndesThumbnailConfigurationFactory.create(context, andesThumbnailAttrs)
        setupComponents(config)
    }

    /**
     * Responsible for setting up all properties of each component that is part of this thumbnail.
     * Is like a choreographer ;)
     */
    private fun setupComponents(config: AndesThumbnailConfiguration) {
        initComponents()
        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }
        setupBackground(config)
        setupImage(config)
    }

    /**
     * Creates all the views that are part of this thumbnail.
     * After a view is created then a view id is added to it.
     */
    private fun initComponents() {
        LayoutInflater.from(context).inflate(R.layout.andes_layout_thumbnail, this)
    }

    private fun setupBackground(config: AndesThumbnailConfiguration) {
        val shape = GradientDrawable()
        if (config.hasBorder) {
            val borderSize = resources.getDimension(R.dimen.andes_thumbnail_icon_border)
            shape.setStroke(borderSize.toInt(), config.borderColor.colorIntToAlpha(context))
        }
        shape.setColor(config.backgroundColor.colorIntToAlpha(context))

        background = shape
        setupBackgroundSize(config.size, config.cornerRadius)
    }

    private fun setupBackgroundSize(size: Float, configCornerRadius: Float) {
        if (background != null) {
            with(background as GradientDrawable) {
                cornerRadius = configCornerRadius
                setSize(size.toInt(), size.toInt())
            }
        }
    }

    private fun setupImage(config: AndesThumbnailConfiguration) {
        val unwrappedDrawable = config.image
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable)
        imageFrame.setImageDrawable(wrappedDrawable)
        setupClipToOutline(config)
        setupScaleType(config)
        setupFrameSize(config)
        setupImageColor(config)
    }

    private fun setupScaleType(config: AndesThumbnailConfiguration) {
        imageFrame.scaleType = config.scaleType
    }

    private fun setupClipToOutline(config: AndesThumbnailConfiguration) {
        clipToOutline = config.clipToOutline
    }

    private fun setupImageColor(config: AndesThumbnailConfiguration) {
        val tintList = takeIf { config.hasTint }?.let {
            ColorStateList.valueOf(config.iconColor.colorInt(context))
        }
        tintImage(tintList)
    }

    internal fun tintImage(colorStateList: ColorStateList?) {
        DrawableCompat.setTintList(imageFrame.drawable, colorStateList)
    }

    private fun setupFrameSize(config: AndesThumbnailConfiguration) {
        imageFrame.layoutParams = LayoutParams(config.widthSize, config.heightSize, Gravity.CENTER)
    }

    private fun createConfig() = AndesThumbnailConfigurationFactory.create(context, andesThumbnailAttrs)

    private companion object {
        val DEFAULT_SCALE_TYPE = CENTER_CROP

        // set the deprecated val type as default Icon
        val TYPE_DEFAULT = AndesThumbnailType.ICON
    }
}
