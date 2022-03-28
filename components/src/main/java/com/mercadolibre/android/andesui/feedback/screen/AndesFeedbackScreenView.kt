package com.mercadolibre.android.andesui.feedback.screen

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Group
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenActions
import com.mercadolibre.android.andesui.feedback.screen.factory.AndesFeedbackBodyConfiguration
import com.mercadolibre.android.andesui.feedback.screen.factory.AndesFeedbackButtonConfiguration
import com.mercadolibre.android.andesui.feedback.screen.factory.AndesFeedbackCloseConfiguration
import com.mercadolibre.android.andesui.feedback.screen.factory.AndesFeedbackScreenConfiguration
import com.mercadolibre.android.andesui.feedback.screen.factory.AndesFeedbackScreenConfigurationFactory
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenHeader
import com.mercadolibre.android.andesui.feedback.screen.header.view.AndesFeedbackScreenHeaderView
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenType
import com.mercadolibre.android.andesui.utils.doWhenGreaterThanApi
import com.mercadolibre.android.andesui.utils.setConstraints

@Suppress("TooManyFunctions")
class AndesFeedbackScreenView : ScrollView {

    private lateinit var gradient: Group
    private lateinit var close: ImageView
    private lateinit var button: AndesButton
    private lateinit var body: View
    private lateinit var container: ConstraintLayout
    private lateinit var header: View
    private lateinit var config: AndesFeedbackScreenConfiguration
    private var initialStatusBarColor = 0

    private constructor(context: Context) : super(context)

    private constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(
        context: Context,
        type: AndesFeedbackScreenType = AndesFeedbackScreenType.Congrats,
        header: AndesFeedbackScreenHeader,
        body: View? = null,
        actions: AndesFeedbackScreenActions? = null
    ) : super(context) {
        initComponents()
        config = createConfig(body, actions, type, header)
        setupComponents(config)
    }

    internal constructor(
        context: Context,
        type: AndesFeedbackScreenType,
        header: AndesFeedbackScreenHeader,
        actions: AndesFeedbackScreenActions? = null,
        onViewCreated: (() -> Unit)? = null
    ) : super(context) {
        initComponents()
        config = createConfig(null, actions, type, header)
        setupComponents(config)
        onViewCreated?.invoke()
    }

    private fun setupComponents(config: AndesFeedbackScreenConfiguration) {
        getInitialStatusBarColor()
        setupFeedbackContainer(config)
        setupFeedbackHeader(config)
        setupFeedbackBody(config.body)
        setContainerConstraints(config)
        setupClose(config.close)
        setupFeedbackButton(config.feedbackButton)
        setupA11y()
    }

    /**
     * this method allows to retrieve the initial value of the statusBarColor. We need this value
     * to be able to reset it at detach in case we previously change the color for the Congrats variant
     */
    private fun getInitialStatusBarColor() {
        initialStatusBarColor = (context as? Activity)?.window?.statusBarColor ?: 0
    }

    /**
     * we override this method to be able to change the statusBarColor to green when the
     * component is Congrats and has body. In every other case, the statusBarColor should be
     * the standard value.
     */
    override fun onAttachedToWindow() {
        config.statusBarColor?.let { configColor ->
            (context as? Activity)?.window?.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = configColor
            }
        }
        super.onAttachedToWindow()
    }

    /**
     * in case we previously changed the statusBarColor, we need to reset it to the original value at
     * detaching
     */
    override fun onDetachedFromWindow() {
        (context as? Activity)?.window?.apply {
            if (statusBarColor != initialStatusBarColor) {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = initialStatusBarColor
            }
        }
        super.onDetachedFromWindow()
    }

    /**
     * Replaces current feedback image with the new [feedbackImage] asset.
     * If you select an AndesThumbnailBadgeType.Icon, the [drawable] will be colorized as the
     * previously selected status (green for GREEN, orange for ORANGE, red for RED).
     */
    fun setFeedbackScreenAsset(feedbackImage: AndesFeedbackScreenAsset?) {
        (header as? AndesFeedbackScreenHeaderView)?.apply {
            setupAssetComponent(
                AndesFeedbackScreenConfigurationFactory.resolveFeedbackThumbnail(
                    context = context,
                    feedbackImage = feedbackImage,
                    feedbackType = config.typeInterface,
                    hasBody = config.body.view != null
                ),
                config.typeInterface,
                config.body.view != null
            )
        }
    }

    private fun setupA11y() {
        doWhenGreaterThanApi(Build.VERSION_CODES.LOLLIPOP_MR1) {
            with(header) {
                isFocusable = false
                isFocusableInTouchMode = false
                importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
                accessibilityTraversalAfter = close.id
                accessibilityTraversalBefore = body.id
            }

            with(body) {
                importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
                accessibilityTraversalBefore = button.id
                accessibilityTraversalAfter = header.id
            }

            with(button) {
                importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
                accessibilityTraversalBefore = close.id
                accessibilityTraversalAfter = body.id
            }

            with(close) {
                importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
                accessibilityTraversalBefore = header.id
                accessibilityTraversalAfter = button.id
            }
        }
    }

    private fun setupFeedbackHeader(config: AndesFeedbackScreenConfiguration) {
        if (this::header.isInitialized) {
            container.removeView(header)
        }
        header = config.headerView
        container.addView(header)
    }

    private fun setupFeedbackBody(configBody: AndesFeedbackBodyConfiguration) {
        if (this::body.isInitialized) {
            container.removeView(body)
        }
        body = configBody.view ?: View(context)

        if (body.id == NO_ID) {
            body.id = generateViewId()
        }

        container.addView(body, configBody.layoutParams)
        body.visibility = configBody.visibility
    }

    private fun setContainerConstraints(config: AndesFeedbackScreenConfiguration) {
        container.setConstraints {
            setHeaderConstraints(config)
            setBodyConstraints()
        }
    }

    private fun ConstraintSet.setHeaderConstraints(config: AndesFeedbackScreenConfiguration) {
        connect(header.id, ConstraintSet.TOP, container.id, ConstraintSet.TOP)
        connect(body.id, ConstraintSet.TOP, header.id, ConstraintSet.BOTTOM)
        connect(header.id, ConstraintSet.BOTTOM, body.id, ConstraintSet.TOP)
        connect(body.id, ConstraintSet.BOTTOM, button.id, ConstraintSet.TOP)

        setMargin(
            header.id,
            ConstraintSet.START,
            resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_content_horizontal_margin)
        )
        setMargin(header.id, ConstraintSet.TOP, config.headerTopMargin)
        setMargin(
            header.id,
            ConstraintSet.END,
            resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_content_horizontal_margin)
        )
        setMargin(
            header.id,
            ConstraintSet.BOTTOM,
            resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_margin_bottom)
        )

        setVerticalChainStyle(header.id, ConstraintSet.CHAIN_PACKED)
        setVerticalBias(header.id, config.headerVerticalBias)
    }

    private fun ConstraintSet.setBodyConstraints() {
        setMargin(
            body.id,
            ConstraintSet.START,
            resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_content_horizontal_margin)
        )
        setMargin(
            body.id,
            ConstraintSet.END,
            resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_content_horizontal_margin)
        )
        setMargin(
            body.id,
            ConstraintSet.BOTTOM,
            resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_body_margin_bottom)
        )
        setGoneMargin(
            body.id,
            ConstraintSet.BOTTOM,
            0
        )
        connect(body.id, ConstraintSet.BOTTOM, button.id, ConstraintSet.TOP)
        connect(body.id, ConstraintSet.TOP, header.id, ConstraintSet.BOTTOM)
        connect(body.id, ConstraintSet.START, container.id, ConstraintSet.START)
        connect(body.id, ConstraintSet.END, container.id, ConstraintSet.END)
        constrainedWidth(body.id, true)
        constrainedHeight(body.id, true)
        setVerticalChainStyle(body.id, ConstraintSet.CHAIN_PACKED)
    }

    private fun setupFeedbackContainer(config: AndesFeedbackScreenConfiguration) {
        isFillViewport = true
        layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
        setupFeedbackContainerBackground(config)
    }

    private fun setupClose(configClose: AndesFeedbackCloseConfiguration) {
        with(close) {
            visibility = configClose.visibility
            setOnClickListener(configClose.onClick)
            setColorFilter(configClose.tintColor, PorterDuff.Mode.SRC_IN)
        }
    }

    private fun setupFeedbackButton(configButton: AndesFeedbackButtonConfiguration) {
        with(button) {
            text = configButton.text
            setOnClickListener(configButton.onClick)
            visibility = configButton.visibility
            hierarchy = configButton.hierarchy
        }
    }

    private fun initComponents() {
        val scrollView =
            LayoutInflater.from(context).inflate(R.layout.andes_layout_feedback_screen, this)

        container = scrollView.findViewById(R.id.andes_feedback_content_constraint_layout)
        button = scrollView.findViewById(R.id.andes_feedbackscreen_button)
        close = scrollView.findViewById(R.id.andes_feedbackscreen_close)
        gradient = scrollView.findViewById(R.id.andes_feedbackscreen_gradient)
    }

    private fun createConfig(
        body: View?,
        actions: AndesFeedbackScreenActions?,
        type: AndesFeedbackScreenType,
        header: AndesFeedbackScreenHeader
    ) = AndesFeedbackScreenConfigurationFactory.create(context, body, actions, type, header)

    /**
     * Replaces current feedback header with the new [AndesFeedbackScreenHeader].
     */
    fun setFeedbackScreenHeader(header: AndesFeedbackScreenHeader) {
        val nullBody = body.takeIf { body.visibility == VISIBLE }
        config = createConfig(nullBody, config.actions, config.type, header)
        setupFeedbackHeader(config)
        setContainerConstraints(config)
    }

    /**
     * Replaces current feedback header with the new [AndesFeedbackScreenActions].
     */
    fun setFeedbackScreenActions(actions: AndesFeedbackScreenActions) {
        val nullBody = body.takeIf { body.visibility == VISIBLE }
        config = createConfig(nullBody, actions, config.type, config.header)
        setupClose(config.close)
        setupFeedbackButton(config.feedbackButton)
    }

    /**
     * Replaces current feedback type with the new [AndesFeedbackScreenType].
     */
    fun setFeedbackScreenType(type: AndesFeedbackScreenType) {
        val nullBody = body.takeIf { body.visibility == VISIBLE }
        config = createConfig(nullBody, config.actions, type, config.header)
        setupFeedbackContainer(config)
        setupFeedbackContainerBackground(config)
    }

    /**
     * Replaces current feedback body with the new [View].
     */
    fun setFeedbackBody(body: View?) {
        config = createConfig(body, config.actions, config.type, config.header)
        setupFeedbackBody(config.body)
        setContainerConstraints(config)
        setupFeedbackContainerBackground(config)
    }

    /**
     * Set the background.
     */
    private fun setupFeedbackContainerBackground(config: AndesFeedbackScreenConfiguration) {
        setBackgroundColor(config.background)
        gradient.visibility = config.gradientVisibility
    }
}
