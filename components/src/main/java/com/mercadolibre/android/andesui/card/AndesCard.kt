package com.mercadolibre.android.andesui.card

import android.animation.LayoutTransition
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.card.bodyPadding.AndesCardBodyPadding
import com.mercadolibre.android.andesui.card.factory.AndesCardAttrParser
import com.mercadolibre.android.andesui.card.factory.AndesCardAttrs
import com.mercadolibre.android.andesui.card.factory.AndesCardConfiguration
import com.mercadolibre.android.andesui.card.factory.AndesCardConfigurationFactory
import com.mercadolibre.android.andesui.card.hierarchy.AndesCardHierarchy
import com.mercadolibre.android.andesui.card.padding.AndesCardPadding
import com.mercadolibre.android.andesui.card.style.AndesCardStyle
import com.mercadolibre.android.andesui.card.type.AndesCardType
import com.mercadolibre.android.andesui.databinding.AndesLayoutCardBinding

@Suppress("TooManyFunctions")
class AndesCard : CardView {
    /**
     * Getter and setter for [hierarchy].
     */
    var hierarchy: AndesCardHierarchy
        get() = andesCardAttrs.andesCardHierarchy
        set(value) {
            andesCardAttrs = andesCardAttrs.copy(andesCardHierarchy = value)
            val config = createConfig()
            setupBackgroundComponent(config)
            setupLinkComponent(config)
            setupPipeComponent(config)
        }

    /**
     * Getter and setter for [padding].
     */
    var padding: AndesCardPadding
        get() = andesCardAttrs.andesCardPadding
        set(value) {
            andesCardAttrs = andesCardAttrs.copy(andesCardPadding = value, andesCardBodyPadding = viewComponentWithoutBodyPadding(value))
            val config = createConfig()
            setupBackgroundComponent(config)
            setupTitleComponent(config)
            setupCardViewComponent(config)
            setupLinkComponent(config)
        }

    /**
     * Getter and setter for [bodyPadding].
     */
    var bodyPadding: AndesCardBodyPadding
        get() = andesCardAttrs.andesCardBodyPadding
        set(value) {
            andesCardAttrs = andesCardAttrs.copy(andesCardBodyPadding = value)
            val config = createConfig()
            setupCardViewComponent(config)
        }

    /**
     * Getter and setter for [title].
     */
    var title: String?
        get() = andesCardAttrs.andesCardTitle
        set(value) {
            andesCardAttrs = andesCardAttrs.copy(andesCardTitle = value)
            val config = createConfig()
            setupTitleComponent(config)
        }

    /**
     * Getter and setter for [cardView].
     */
    var cardView: View?
        get() = andesCardAttrs.andesCardView
        set(value) {
            andesCardAttrs = andesCardAttrs.copy(andesCardView = value)
            val config = createConfig()
            setupCardViewComponent(config)
        }

    /**
     * Getter and setter for [type].
     */
    var type: AndesCardType
        get() = andesCardAttrs.andesCardType
        set(value) {
            andesCardAttrs = andesCardAttrs.copy(andesCardType = value)
            val config = createConfig()
            setupPipeComponent(config)
        }

    /**
     * Getter and setter for [style].
     */
    var style: AndesCardStyle
        get() = andesCardAttrs.andesCardStyle
        set(value) {
            andesCardAttrs = andesCardAttrs.copy(andesCardStyle = value)
            val config = createConfig()
            setupBackgroundComponent(config)
        }

    /**
     * Setter link action.
     */
    fun setLinkAction(title: String, listener: OnClickListener) {
        andesCardAttrs = andesCardAttrs.copy(linkText = title, linkAction = listener)
        val config = createConfig()
        setupLinkComponent(config)
        setupBackgroundComponent(config)
    }

    /**
     * Remove link action.
     */
    fun removeLinkAction() {
        andesCardAttrs = andesCardAttrs.copy(linkText = null, linkAction = null)
        val config = createConfig()
        setupLinkComponent(config)
    }

    /**
     * Setter card action.
     */
    fun setCardAction(listener: OnClickListener) {
        cardListener = listener
    }

    /**
     * Remove card action.
     */
    fun removeCardAction() {
        cardListener = null
    }

    private val binding: AndesLayoutCardBinding = AndesLayoutCardBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    private lateinit var andesCardAttrs: AndesCardAttrs
    private var cardListener: OnClickListener? = null

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    @Suppress("LongParameterList")
    constructor(
        context: Context,
        view: View,
        type: AndesCardType = TYPE_DEFAULT,
        padding: AndesCardPadding = PADDING_DEFAULT,
        title: String? = TITLE_DEFAULT,
        style: AndesCardStyle = STYLE_DEFAULT,
        hierarchy: AndesCardHierarchy = HIERARCHY_DEFAULT
    ) : super(context) {
        initAttrs(view, type, padding, style, title, hierarchy)
    }

    /**
     * Sets the layoutTransition to the card container
     *
     * @param layoutTransition the desired layout transition to be set on the card
     */
    fun setAnimateLayoutChanges(layoutTransition: LayoutTransition?) {
        binding.andesCardContainer.layoutTransition = layoutTransition
    }

    /**
     * Sets the proper [config] for this component based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesCardAttrs = AndesCardAttrParser.parse(context, attrs)
        andesCardAttrs = andesCardAttrs.copy(andesCardBodyPadding = viewComponentWithoutBodyPadding(andesCardAttrs.andesCardPadding))
        val config = AndesCardConfigurationFactory.create(context, andesCardAttrs)
        setupComponents(config)
    }

    @Suppress("LongParameterList")
    private fun initAttrs(
        cardView: View,
        type: AndesCardType,
        padding: AndesCardPadding,
        style: AndesCardStyle,
        title: String?,
        hierarchy: AndesCardHierarchy
    ) {
        andesCardAttrs = AndesCardAttrs(cardView, type, padding, viewComponentWithoutBodyPadding(padding), style, title, hierarchy)
        val config = AndesCardConfigurationFactory.create(context, andesCardAttrs)
        setupComponents(config)
    }

    /**
     * Responsible for setting up all properties of each component that is part of this andesCard.
     * Is like a choreographer 😉
     */
    private fun setupComponents(config: AndesCardConfiguration) {
        setupViewId()

        setupBackgroundComponent(config)
        setupPipeComponent(config)
        setupTitleComponent(config)
        setupCardViewComponent(config)
    }

    /**
     * Sets a view id to this andesCard.
     */
    private fun setupViewId() {
        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }
    }

    /**
     * Gets data from the config and sets to the background of this card.
     */
    private fun setupBackgroundComponent(config: AndesCardConfiguration) {
        // Radius card
        preventCornerOverlap = true
        radius = context.resources.getDimension(R.dimen.andes_card_corner_radius)

        // Set listeners
        binding.groupCard.referencedIds.forEach { id ->
            rootView.findViewById<View>(id).setOnClickListener {
                cardListener?.onClick(it)
            }
        }

        // Background
        val shape = GradientDrawable()
        shape.cornerRadius = context.resources.getDimension(R.dimen.andes_card_corner_radius)
        shape.setColor(andesCardAttrs.andesCardHierarchy.hierarchy.backgroundColor().colorInt(context))

        // Elevation
        cardElevation = config.elevation
        if (hierarchy == AndesCardHierarchy.PRIMARY && style == AndesCardStyle.OUTLINE) {
            shape.setStroke(
                    context.resources.getDimension(R.dimen.andes_card_border).toInt(),
                    ContextCompat.getColor(context, R.color.andes_gray_100_solid)
            )
        }

        background = shape
    }

    /**
     * Gets data from the config and sets to the cardView of this card.
     */
    private fun setupCardViewComponent(config: AndesCardConfiguration) {
        val params = binding.andesCardView.layoutParams as MarginLayoutParams
        params.setMargins(
                config.bodyPadding.bodyPadding.bodyPaddingSize(context),
                config.bodyPadding.bodyPadding.bodyPaddingSize(context),
                config.bodyPadding.bodyPadding.bodyPaddingSize(context),
                config.bodyPadding.bodyPadding.bodyPaddingSize(context)
        )
        binding.andesCardView.layoutParams = params
        binding.andesCardView.removeAllViews()
        if (cardView != null) {
            binding.andesCardView.addView(cardView)
        }
    }

    /**
     * Gets data from the config and sets to the pipe color of this card.
     */
    private fun setupPipeComponent(config: AndesCardConfiguration) {
        binding.andesCardPipe.setBackgroundColor(config.pipeColor.colorInt(context))
        binding.andesCardPipe.visibility = config.pipeVisibility
    }

    /**
     * Gets data from the config and sets to the title of this card.
     */
    private fun setupTitleComponent(config: AndesCardConfiguration) {
        binding.groupTitle.visibility = config.titleVisibility
        binding.andesCardTitle.apply {
            text = config.title
            setTextSize(TypedValue.COMPLEX_UNIT_PX, config.titleSize)
            typeface = config.titleTypeface
            setTextColor(config.titleColor.colorInt(context))
            height = config.titleHeight
            setPadding(config.titlePadding, 0, config.titlePadding, 0)
            ViewCompat.setAccessibilityHeading(this, true)
        }
    }

    /**
     * Gets data from the config and sets to the link of this card.
     */
    private fun setupLinkComponent(config: AndesCardConfiguration) {
        binding.andesCardTitleLink.text = andesCardAttrs.linkText
        binding.andesCardTitleLink.setTextSize(TypedValue.COMPLEX_UNIT_PX, config.titleSize)
        binding.andesCardTitleLink.typeface = config.titleTypeface
        binding.andesCardTitleLink.setTextColor(config.linkColor.colorInt(context))
        binding.andesCardTitleLink.height = config.titleHeight
        binding.andesCardTitleLink.setPadding(config.titlePadding, 0, 0, 0)
        binding.andesLinkIcon.setPadding(0, 0, config.titlePadding, 0)
        binding.groupLink.visibility = config.groupLinkVisibility
        binding.groupLink.referencedIds.forEach { id ->
            rootView.findViewById<View>(id).setOnClickListener {
                andesCardAttrs.linkAction?.onClick(it)
            }
        }
    }

    /**
     * Emulates the behavior for develops that do not support or do not set bodyPadding property.
     */
    private fun viewComponentWithoutBodyPadding(padding: AndesCardPadding): AndesCardBodyPadding {
        return when (padding) {
            AndesCardPadding.NONE -> AndesCardBodyPadding.NONE
            AndesCardPadding.SMALL -> AndesCardBodyPadding.SMALL
            AndesCardPadding.MEDIUM -> AndesCardBodyPadding.MEDIUM
            AndesCardPadding.LARGE -> AndesCardBodyPadding.LARGE
            AndesCardPadding.XLARGE -> AndesCardBodyPadding.XLARGE
        }
    }

    private fun createConfig() = AndesCardConfigurationFactory.create(context, andesCardAttrs)

    companion object {
        private val STYLE_DEFAULT = AndesCardStyle.ELEVATED
        private val TYPE_DEFAULT = AndesCardType.NONE
        private val PADDING_DEFAULT = AndesCardPadding.NONE
        private val TITLE_DEFAULT = null
        private val HIERARCHY_DEFAULT = AndesCardHierarchy.PRIMARY
    }
}
