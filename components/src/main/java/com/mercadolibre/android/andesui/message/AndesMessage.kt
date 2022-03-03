package com.mercadolibre.android.andesui.message

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.facebook.drawee.view.SimpleDraweeView
import com.mercadolibre.android.andesui.BuildConfig
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.bullet.AndesBullet
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.message.factory.AndesMessageAttrs
import com.mercadolibre.android.andesui.message.factory.AndesMessageAttrsParser
import com.mercadolibre.android.andesui.message.factory.AndesMessageConfiguration
import com.mercadolibre.android.andesui.message.factory.AndesMessageConfigurationFactory
import com.mercadolibre.android.andesui.message.hierarchy.AndesMessageHierarchy
import com.mercadolibre.android.andesui.message.type.AndesMessageType
import com.mercadolibre.android.andesui.typeface.getFontOrDefault
import com.mercadolibre.android.andesui.utils.configureBullet
import com.mercadolibre.android.andesui.utils.getCircledBitmap
import com.mercadolibre.android.andesui.utils.toBitmap
import com.mercadolibre.android.andesui.utils.setupSpannableBodyLink

@Suppress("TooManyFunctions")
class AndesMessage : CardView {

    /**
     * Getter and setter for [hierarchy].
     */
    var hierarchy: AndesMessageHierarchy
        get() = andesMessageAttrs.andesMessageHierarchy
        set(value) {
            andesMessageAttrs = andesMessageAttrs.copy(andesMessageHierarchy = value)
            setupColorComponents(createConfig())
        }

    /**
     * Getter and setter for [type].
     */
    var type: AndesMessageType
        get() = andesMessageAttrs.andesMessageType
        set(value) {
            andesMessageAttrs = andesMessageAttrs.copy(andesMessageType = value)
            setupColorComponents(createConfig())
        }

    /**
     * Getter and setter for [body].
     */
    var body: String?
        get() = andesMessageAttrs.body
        set(value) {
            andesMessageAttrs = andesMessageAttrs.copy(body = value)
            setupBodyComponent(createConfig())
        }

    /**
     * Getter and setter for [title].
     */
    var title: String?
        get() = andesMessageAttrs.title
        set(value) {
            andesMessageAttrs = andesMessageAttrs.copy(title = value)
            setupTitleComponent(createConfig())
        }

    /**
     * Getter and setter for [isDismissable].
     */
    var isDismissable: Boolean
        get() = andesMessageAttrs.isDismissable
        set(value) {
            andesMessageAttrs = andesMessageAttrs.copy(isDismissable = value)
            setupDismissable(createConfig())
        }

    /**
     * Getter and setter for [bodyLinks].
     */
    var bodyLinks: AndesBodyLinks?
        get() = andesMessageAttrs.bodyLinks
        set(value) {
            andesMessageAttrs = andesMessageAttrs.copy(bodyLinks = value)
            setupBodyComponent(createConfig())
        }

    var bullets: List<AndesBullet>?
        get() = andesMessageAttrs.bullets
        set(value) {
            andesMessageAttrs = andesMessageAttrs.copy(bullets = value)
            setupBodyComponent(createConfig())
        }

    private lateinit var messageContainer: ConstraintLayout
    private lateinit var titleComponent: TextView
    lateinit var bodyComponent: TextView
    private lateinit var iconComponent: SimpleDraweeView
    private lateinit var dismissableComponent: SimpleDraweeView
    private lateinit var pipeComponent: View
    private lateinit var andesMessageAttrs: AndesMessageAttrs
    private lateinit var primaryAction: AndesButton
    private lateinit var secondaryAction: AndesButton
    private lateinit var linkAction: TextView
    lateinit var thumbnail: SimpleDraweeView
    private lateinit var bulletContainer: LinearLayout

    @Suppress("unused")
    private constructor(context: Context) : super(context) {
        throw IllegalStateException(
                "Constructor without parameters in Andes Message is not allowed. You must provide some attributes."
        )
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs) {
        initAttrs(attrs)
    }

    @Suppress("unused", "LongParameterList")
    @JvmOverloads
    constructor(
        context: Context,
        hierarchy: AndesMessageHierarchy = HIERARCHY_DEFAULT,
        type: AndesMessageType = STATE_DEFAULT,
        body: String,
        title: String? = TITLE_DEFAULT,
        isDismissable: Boolean = IS_DISMISSIBLE_DEFAULT,
        bodyLinks: AndesBodyLinks? = null,
        thumbnail: Drawable? = null
    ) : super(context) {
        initAttrs(hierarchy, type, body, title, isDismissable, bodyLinks, thumbnail, null)
    }

    constructor(
        context: Context,
        hierarchy: AndesMessageHierarchy = HIERARCHY_DEFAULT,
        type: AndesMessageType = STATE_DEFAULT,
        body: String,
        title: String? = TITLE_DEFAULT,
        isDismissable: Boolean = IS_DISMISSIBLE_DEFAULT,
        bodyLinks: AndesBodyLinks? = null,
        thumbnail: Drawable? = null,
        bullets: List<AndesBullet>?
    ) : super(context) {
        initAttrs(hierarchy, type, body, title, isDismissable, bodyLinks, thumbnail, bullets)
    }

    /**
     * Sets the proper [config] for this message based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesMessageAttrs = AndesMessageAttrsParser.parse(context, attrs)
        val config = AndesMessageConfigurationFactory.create(context, andesMessageAttrs)
        setupComponents(config)
    }

    @Suppress("LongParameterList")
    private fun initAttrs(
        hierarchy: AndesMessageHierarchy,
        type: AndesMessageType,
        body: String,
        title: String?,
        isDismissable: Boolean,
        bodyLinks: AndesBodyLinks?,
        thumbnail: Drawable?,
        bullets: List<AndesBullet>?
    ) {
        andesMessageAttrs = AndesMessageAttrs(hierarchy, type, body, title, isDismissable, bodyLinks, thumbnail, bullets)
        val config = AndesMessageConfigurationFactory.create(context, andesMessageAttrs)
        setupComponents(config)
    }

    /**
     * Responsible for setting up all properties of each component that is part of this message.
     * Is like a choreographer ;)
     *
     */
    private fun setupComponents(config: AndesMessageConfiguration) {
        radius = context.resources.getDimension(R.dimen.andes_message_corner_radius)
        cardElevation = 0f
        preventCornerOverlap = true

        initComponents()
        setupViewId()

        setupColorComponents(config)
        setupDismissable(config)
        setupThumbnail(config.thumbnail)
    }

    private fun setupColorComponents(config: AndesMessageConfiguration) {
        setupTitleComponent(config)
        setupBodyComponent(config)
        setupBackground(config)
        setupPipe(config)
        setupIcon(config)
        setupButton(config)
    }

    /**
     * Creates all the views that are part of this message.
     * After a view is created then a view id is added to it.
     *
     */
    private fun initComponents() {
        val container = LayoutInflater.from(context).inflate(R.layout.andes_layout_message,
                this, true)

        messageContainer = container.findViewById(R.id.andes_message_container)
        titleComponent = container.findViewById(R.id.andes_title)
        bodyComponent = container.findViewById(R.id.andes_body)
        iconComponent = container.findViewById(R.id.andes_icon)
        dismissableComponent = container.findViewById(R.id.andes_dismissable)
        pipeComponent = container.findViewById(R.id.andes_pipe)
        primaryAction = container.findViewById(R.id.andes_primary_action)
        secondaryAction = container.findViewById(R.id.andes_secondary_action)
        linkAction = container.findViewById(R.id.andes_link_action)
        thumbnail = container.findViewById(R.id.andes_thumbnail)
        bulletContainer = container.findViewById(R.id.andes_bullet_container)
    }

    /**
     * Sets a view id to this message.
     *
     */
    private fun setupViewId() {
        if (id == NO_ID) {
            id = View.generateViewId()
        }
    }

    /**
     * Gets data from the config and sets to the text component of this button.
     *
     */
    private fun setupTitleComponent(config: AndesMessageConfiguration) {
        if (config.titleText == null || config.titleText.isEmpty()) {
            titleComponent.visibility = View.GONE
        } else {
            titleComponent.visibility = View.VISIBLE
            titleComponent.text = config.titleText
            titleComponent.setTextSize(TypedValue.COMPLEX_UNIT_PX, config.titleSize)
            titleComponent.setTextColor(config.textColor.colorInt(context))
            titleComponent.typeface = config.titleTypeface
        }
    }

    /**
     * Gets data from the config and sets to the text component of this button.
     *
     */
    private fun setupBodyComponent(config: AndesMessageConfiguration) {
        if (config.bodyText.isNullOrEmpty()) {
            messageContainer.visibility = View.GONE
            Log.e("Body", "Message cannot be visualized with null or empty body")
        } else {
            messageContainer.visibility = View.VISIBLE
            bodyComponent.text = getBodyText(config.bodyText, config)
            bodyComponent.setTextSize(TypedValue.COMPLEX_UNIT_PX, config.bodySize)
            bodyComponent.setTextColor(config.textColor.colorInt(context))
            bodyComponent.typeface = config.bodyTypeface
//          bodyComponent.lineHeight = config.lineHeight //FIXME Use TextViewCompat
        }
    }

    private fun getBodyText(text: String, config: AndesMessageConfiguration): SpannableStringBuilder {
        val spannableString = SpannableStringBuilder(text)
        bodyLinks?.let {
            setupSpannableBodyLink(
                context,
                spannableString,
                it,
                config.bodyLinkIsUnderline,
                config.bodyLinkTextColor
            )
            bodyComponent.movementMethod = LinkMovementMethod.getInstance()
        }
        setupBullet(config)
        return spannableString
    }

    private fun setupBullet(config: AndesMessageConfiguration) {
        bulletContainer.removeAllViews()
        bulletContainer.visibility = if (bullets.isNullOrEmpty()) {
            View.GONE
        } else {
            bullets?.let {
                it.forEach { bullet ->
                    val andesBulletView =
                    configureBullet(
                            context,
                            bullet,
                            config.bulletGapWith,
                            config.textColor.colorInt(context),
                            config.bulletDotSize,
                            config.bodySize,
                            config.bodyTypeface,
                            config.bulletMarginTop,
                            config.bodyLinkIsUnderline,
                            config.bodyLinkTextColor
                        )
                    bulletContainer.addView(andesBulletView)
                }
            }
            View.VISIBLE
        }
    }

    private fun setupBackground(config: AndesMessageConfiguration) {
        setCardBackgroundColor(config.backgroundColor.colorInt(context))
    }

    private fun setupPipe(config: AndesMessageConfiguration) {
        pipeComponent.setBackgroundColor(config.pipeColor.colorInt(context))
    }

    private fun setupIcon(config: AndesMessageConfiguration) {
        iconComponent.setImageDrawable(config.icon)
    }

    private fun setupDismissable(config: AndesMessageConfiguration) {
        dismissableComponent.setImageDrawable(config.dismissableIcon)
        dismissableComponent.contentDescription = context
            .resources
            .getString(R.string.andes_message_dismiss_button_content_description)

        if (config.isDismissable) {
            dismissableComponent.visibility = View.VISIBLE
            dismissableComponent.setOnClickListener {
                visibility = View.GONE
            }
        } else {
            dismissableComponent.visibility = View.GONE
        }
    }

    private fun setupButton(config: AndesMessageConfiguration) {
        primaryAction.changeBackgroundColor(config.primaryActionBackgroundColor)
        primaryAction.changeTextColor(config.primaryActionTextColor.colorInt(context))
        secondaryAction.changeBackgroundColor(config.secondaryActionBackgroundColor)
        secondaryAction.changeTextColor(config.secondaryActionTextColor.colorInt(context))
    }

    fun setupPrimaryAction(text: String, onClickListener: OnClickListener) {
        primaryAction.visibility = View.VISIBLE
        primaryAction.text = text
        primaryAction.setOnClickListener(onClickListener)
    }

    fun setupSecondaryAction(text: String, onClickListener: OnClickListener) {
        if (primaryAction.visibility == View.VISIBLE) {
            secondaryAction.visibility = View.VISIBLE
            secondaryAction.text = text
            secondaryAction.setOnClickListener(onClickListener)
        } else {
            when {
                BuildConfig.DEBUG -> throw IllegalStateException("Cannot initialize a secondary action without a primary one")
                else -> Log.d("AndesMessage", "Cannot initialize a secondary action without a primary one")
            }
        }
    }

    /**
     * Shows a clickable link in the bottom of this message with the [text] and [onClickListener] behavior
     * passed as parameters. This method should only be called if there is no primary or secondary actions set. Otherwise,
     * it will not work. For more info see the Figma docs:
     * [https://www.figma.com/file/ma8IQUYi9IS8zc8C0rAzEB/Components-specifications?node-id=1478%3A166624]
     */
    fun setupLinkAction(text: String, onClickListener: OnClickListener) {
        if (primaryAction.visibility == View.GONE) {
            val config = createConfig()
            val spannableText = createSpannableString(text, onClickListener)
            linkAction.apply {
                setPadding(LINK_BUTTON_PADDING,
                    LINK_BUTTON_PADDING,
                    LINK_BUTTON_PADDING,
                    LINK_BUTTON_PADDING)
                visibility = View.VISIBLE
                typeface = context.getFontOrDefault(R.font.andes_font_regular)
                linkAction.setTextSize(TypedValue.COMPLEX_UNIT_PX, config.actionTextSize)
                setOnClickListener(onClickListener)
                this.text = spannableText
            }
        } else {
            when {
                BuildConfig.DEBUG ->
                    throw IllegalStateException("Cannot initialize a link action with a primary one")
                else ->
                    Log.d("AndesMessage", "Cannot initialize a link action with a primary one")
            }
        }
    }

    /**
     * Returns a full-clickable spannable string with the [text] parameter and the [onClickListener] behavior,
     * and the color values present in the config.
     *
     */
    private fun createSpannableString(text: String, onClickListener: OnClickListener): SpannableString {
        val config = createConfig()
        val spannableString = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = config.bodyLinkIsUnderline
                ds.color = config.bodyLinkTextColor.colorInt(context)
            }

            override fun onClick(widget: View) {
                onClickListener.onClick(widget)
            }
        }

        spannableString.setSpan(clickableSpan, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }

    fun setupDismissableCallback(onClickListener: OnClickListener) {
        dismissableComponent.setOnClickListener {
            visibility = View.GONE
            onClickListener.onClick(it)
        }
    }

    /**
     * This method allows the user to add programmatically a drawable
     *
     */
    fun setupThumbnail(thumbnailImage: Drawable?) {
        thumbnailImage?.toBitmap()?.let { bitmap ->
            with(thumbnail) {
                visibility = View.VISIBLE
                setImageBitmap(getCircledBitmap(bitmap))
            }
        } ?: with(thumbnail) {
            visibility = View.GONE
        }
    }

    fun hidePrimaryAction() {
        primaryAction.visibility = View.GONE
        secondaryAction.visibility = View.GONE
    }

    fun hideSecondaryAction() {
        secondaryAction.visibility = View.GONE
    }

    fun hideLinkAction() {
        linkAction.visibility = View.GONE
    }

    private fun createConfig() = AndesMessageConfigurationFactory.create(context, andesMessageAttrs)

    companion object {
        private val HIERARCHY_DEFAULT = AndesMessageHierarchy.LOUD
        private val STATE_DEFAULT = AndesMessageType.NEUTRAL
        private val TITLE_DEFAULT = null
        private const val LINK_BUTTON_PADDING = 0
        private const val IS_DISMISSIBLE_DEFAULT = false
    }
}
