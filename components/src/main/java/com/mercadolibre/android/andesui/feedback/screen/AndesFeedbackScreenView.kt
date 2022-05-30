package com.mercadolibre.android.andesui.feedback.screen

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.databinding.AndesLayoutFeedbackScreenBinding
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
import com.mercadolibre.android.andesui.utils.setConstraints

@Suppress("TooManyFunctions")
class AndesFeedbackScreenView : ScrollView {
    private var body: View = View(context)
    private var header: View = View(context)
    private lateinit var config: AndesFeedbackScreenConfiguration
    private var initialStatusBarColor = 0
    private val binding: AndesLayoutFeedbackScreenBinding by lazy {
        AndesLayoutFeedbackScreenBinding.inflate(LayoutInflater.from(context), this)
    }

    private constructor(context: Context) : super(context)

    private constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(
        context: Context,
        type: AndesFeedbackScreenType = AndesFeedbackScreenType.Congrats,
        header: AndesFeedbackScreenHeader,
        body: View? = null,
        actions: AndesFeedbackScreenActions? = null
    ) : super(context) {
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
        setupFeedbackSimpleButtonGroup(config.feedbackButton)
        setupAndesButtonGroup(config)
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
        with(header) {
            isFocusable = false
            isFocusableInTouchMode = false
            importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
            accessibilityTraversalAfter = binding.andesFeedbackscreenClose.id
            accessibilityTraversalBefore = body.id
        }


        with(body) {
            importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
            accessibilityTraversalBefore = binding.andesFeedbackscreenContentButtongroup.id
            accessibilityTraversalAfter = header.id
        }

        with(binding.andesFeedbackscreenContentButtongroup) {
            importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
            accessibilityTraversalBefore = binding.andesFeedbackscreenClose.id
            accessibilityTraversalAfter = body.id
        }

        with(binding.andesFeedbackscreenClose) {
            importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
            accessibilityTraversalBefore = header.id
            accessibilityTraversalAfter = binding.andesFeedbackscreenContentButtongroup.id
        }
    }

    private fun setupFeedbackHeader(config: AndesFeedbackScreenConfiguration) {

        binding.andesFeedbackContentConstraintLayout.removeView(header)

        header = config.headerView
        binding.andesFeedbackContentConstraintLayout.addView(header)
    }

    private fun setupFeedbackBody(configBody: AndesFeedbackBodyConfiguration) {
        binding.andesFeedbackContentConstraintLayout.removeView(body)

        body = configBody.view ?: View(context)

        if (body.id == NO_ID) {
            body.id = generateViewId()
        }

        binding.andesFeedbackContentConstraintLayout.addView(body, configBody.layoutParams)
        body.visibility = configBody.visibility
    }

    private fun setContainerConstraints(config: AndesFeedbackScreenConfiguration) {
        binding.andesFeedbackContentConstraintLayout.setConstraints {
            setHeaderConstraints(config)
            setBodyConstraints()
        }
    }

    private fun ConstraintSet.setHeaderConstraints(config: AndesFeedbackScreenConfiguration) {
        connect(header.id, ConstraintSet.TOP, binding.andesFeedbackContentConstraintLayout.id, ConstraintSet.TOP)
        connect(body.id, ConstraintSet.TOP, header.id, ConstraintSet.BOTTOM)
        connect(header.id, ConstraintSet.BOTTOM, body.id, ConstraintSet.TOP)
        connect(body.id, ConstraintSet.BOTTOM, binding.andesFeedbackscreenContentButtongroup.id, ConstraintSet.TOP)

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
        connect(body.id, ConstraintSet.BOTTOM, binding.andesFeedbackscreenContentButtongroup.id, ConstraintSet.TOP)
        connect(body.id, ConstraintSet.TOP, header.id, ConstraintSet.BOTTOM)
        connect(body.id, ConstraintSet.START, binding.andesFeedbackContentConstraintLayout.id, ConstraintSet.START)
        connect(body.id, ConstraintSet.END, binding.andesFeedbackContentConstraintLayout.id, ConstraintSet.END)
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
        with(binding.andesFeedbackscreenClose) {
            visibility = configClose.visibility
            setOnClickListener(configClose.onClick)
            setColorFilter(configClose.tintColor, PorterDuff.Mode.SRC_IN)
        }
    }

    private fun setupFeedbackSimpleButtonGroup(configButton: AndesFeedbackButtonConfiguration) {
        val button = AndesButtonGroup(context, listOf(
            AndesButton(context, buttonText = configButton.text, buttonHierarchy = configButton.hierarchy).apply {
                setOnClickListener(configButton.onClick)
            }
        )).apply {
            layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }
        binding.andesFeedbackscreenContentButtongroup.removeAllViews()
        binding.andesFeedbackscreenContentButtongroup.addView(button)
    }

    private fun setupAndesButtonGroup(config: AndesFeedbackScreenConfiguration) {
        if (config.feedbackButton.visibility == View.GONE) {
            binding.andesFeedbackscreenContentButtongroup.removeAllViews()
            config.buttonGroup.andesButtonGroup?.let {
                it.layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                binding.andesFeedbackscreenContentButtongroup.addView(it)
            }
        }
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
        setupFeedbackSimpleButtonGroup(config.feedbackButton)
        setupAndesButtonGroup(config)
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
        binding.andesFeedbackscreenGradient.visibility = config.gradientVisibility
    }
}
