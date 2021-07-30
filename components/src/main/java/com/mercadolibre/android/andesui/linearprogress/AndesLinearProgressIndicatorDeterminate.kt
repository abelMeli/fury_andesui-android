package com.mercadolibre.android.andesui.linearprogress

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.facebook.drawee.view.SimpleDraweeView
import com.mercadolibre.android.andesui.linearprogress.accessibility.AndesLinearProgressIndicatorAccessibilityDelegate
import com.mercadolibre.android.andesui.linearprogress.factory.AndesLinearProgressAttrs
import com.mercadolibre.android.andesui.linearprogress.factory.AndesLinearProgressAttrsParser
import com.mercadolibre.android.andesui.linearprogress.factory.AndesLinearProgressConfiguration
import com.mercadolibre.android.andesui.linearprogress.factory.AndesLinearProgressConfigurationFactory
import com.mercadolibre.android.andesui.linearprogress.size.AndesLinearProgressSize
import com.mercadolibre.android.andesui.utils.buildRectangleShape

@Suppress("TooManyFunctions")
class AndesLinearProgressIndicatorDeterminate : LinearLayout {

    private lateinit var andesLinearProgressAttr: AndesLinearProgressAttrs

    /**
     * Getter for [currentStep].
     */
    var currentStep: Int = STEP_INITIAL
        private set

    /**
     * Getter and setter for [numberOfSteps].
     */
    var numberOfSteps: Int
        get() = andesLinearProgressAttr.numberOfSteps
        set(value) {
            andesLinearProgressAttr = andesLinearProgressAttr.copy(numberOfSteps = value)
            setupComponents(createConfig())
        }

    /**
     * Getter and setter for [size].
     */
    var size: AndesLinearProgressSize
        get() = andesLinearProgressAttr.andesLinearProgressSize
        set(value) {
            andesLinearProgressAttr = andesLinearProgressAttr.copy(andesLinearProgressSize = value)
            setupTrackHeight(createConfig())
        }

    /**
     * Getter and setter for [indicatorTint].
     */
    var indicatorTint: Int
        get() = andesLinearProgressAttr.indicatorTint
        set(value) {
            andesLinearProgressAttr = andesLinearProgressAttr.copy(indicatorTint = value)
            setupIndicatorTint(createConfig())
        }

    /**
     * Getter and setter for [trackTint].
     */
    var trackTint: Int
        get() = andesLinearProgressAttr.trackTint
        set(value) {
            andesLinearProgressAttr = andesLinearProgressAttr.copy(trackTint = value)
            setupTrack(createConfig())
        }

    /**
     * Getter and setter for [isSplit].
     */
    var isSplit: Boolean
        get() = andesLinearProgressAttr.isSplit
        set(value) {
            andesLinearProgressAttr = andesLinearProgressAttr.copy(isSplit = value)
            createConfig().let {
                resetBackground()
                setupMargin(it)
                setupTrack(it)
                setupIndicatorTint(it)
            }
        }

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        isFocusable = true
        importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
    }

    /**
     * Simplest constructor for creating an AndesLinearProgressDeterminate programmatically.
     */
    constructor(
        context: Context,
        size: AndesLinearProgressSize = SIZE_DEFAULT,
        indicatorTint: Int = TINT_DEFAULT,
        trackTint: Int = TINT_DEFAULT,
        isSplit: Boolean = IS_SPLIT_DEFAULT,
        numberOfSteps: Int = NUMBER_OF_STEPS_DEFAULT
    ) : super(context) {
        initAttrs(size, indicatorTint, trackTint, isSplit, numberOfSteps)
    }

    /**
     * Constructor for creating an AndesLinearProgressDeterminate via XML.
     * The [attrs] are the attributes specified in the parameters of XML.
     *
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    /**
     * Constructor for creating an AndesLinearProgress via XML.
     * The [attrs] are the attributes specified in the parameters of XML.
     * The [defStyleAttr] is not considered because we take care of all Andes styling for you.
     */
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs)

    private fun initAttrs(
        size: AndesLinearProgressSize,
        indicatorTint: Int,
        trackTint: Int,
        isSplit: Boolean,
        numberOfSteps: Int
    ) {
        andesLinearProgressAttr =
            AndesLinearProgressAttrs(size, indicatorTint, trackTint, isSplit, numberOfSteps)
        setupComponents(createConfig())
    }

    private fun initAttrs(attrs: AttributeSet?) {
        andesLinearProgressAttr = AndesLinearProgressAttrsParser.parse(context, attrs)
        setupComponents(createConfig())
    }

    private fun createConfig() =
        AndesLinearProgressConfigurationFactory.create(context, andesLinearProgressAttr)

    private fun setupComponents(config: AndesLinearProgressConfiguration) {
        setupA11yDelegate()
        removeAllViews()
        resetCurrentStep()
        resetBackground()
        setupSteps(config)
        setupMargin(config)
        setupTrack(config)
        setupTrackHeight(config)
    }

    private fun setupA11yDelegate() {
        accessibilityDelegate = AndesLinearProgressIndicatorAccessibilityDelegate(this)
    }

    override fun getAccessibilityClassName(): CharSequence {
        return ProgressBar::class.java.name
    }

    private fun resetCurrentStep() {
        currentStep = STEP_INITIAL
    }

    private fun resetBackground() {
        background = null
    }

    /**
     * creates and adds the steps to the container view
     */
    private fun setupSteps(config: AndesLinearProgressConfiguration) {
        (STEP_ONE..config.numberOfSteps).forEach { currentStep ->
            addView(createSingleStepView(currentStep))
        }
    }

    /**
     * creates a single step view, which will be added to the container view.
     */
    private fun createSingleStepView(
        currentStep: Int
    ): SimpleDraweeView {
        val stepView = SimpleDraweeView(context)
        stepView.id = currentStep
        val params = LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT,
            1F
        )
        stepView.layoutParams = params
        return stepView
    }

    /**
     * sets step margin according to [isSplit]
     */
    private fun setupMargin(config: AndesLinearProgressConfiguration) {
        if (config.isSplit) {
            setupMarginIsSplit(config)
        } else {
            setupMarginNoSplit(config)
        }
    }

    private fun setupMarginIsSplit(config: AndesLinearProgressConfiguration) {
        (STEP_ONE..config.numberOfSteps).forEach { stepId ->
            findViewById<SimpleDraweeView>(stepId)?.apply {
                (layoutParams as? MarginLayoutParams)?.setMargins(getMargin(config),
                    0,
                    getMargin(config),
                    0)
            }
        }
    }

    private fun setupMarginNoSplit(config: AndesLinearProgressConfiguration) {
        (STEP_ONE..config.numberOfSteps).forEach { stepId ->
            findViewById<SimpleDraweeView>(stepId)?.apply {
                (layoutParams as? MarginLayoutParams)?.setMargins(0, 0, 0, 0)
            }
        }
    }

    /**
     * create a progress track according [isSplit]
     */
    private fun setupTrack(config: AndesLinearProgressConfiguration) {
        if (config.isSplit) {
            setupTrackIsSplit(config)
        } else {
            setupTrackNoSplit(config)
        }
    }

    private fun setupTrackIsSplit(config: AndesLinearProgressConfiguration) {
        (currentStep..config.numberOfSteps).forEach { stepId ->
            findViewById<SimpleDraweeView>(stepId)?.apply {
                background = buildTrack(config)
            }
        }
    }

    private fun setupTrackNoSplit(config: AndesLinearProgressConfiguration) {
        background = buildTrack(config)
        resetTrack(config)
    }

    private fun resetTrack(config: AndesLinearProgressConfiguration) {
        (currentStep..config.numberOfSteps).forEach { stepId ->
            findViewById<SimpleDraweeView>(stepId)?.apply {
                background = null
            }
        }
    }

    /**
     * sets indicator tint according to [isSplit]
     */
    private fun setupIndicatorTint(config: AndesLinearProgressConfiguration) {
        (STEP_ONE..currentStep).forEach { currentStep ->
            updateIndicatorToIncrease(config, currentStep)
        }
    }

    private fun getMargin(config: AndesLinearProgressConfiguration) =
        config.splitSize.toInt().div(2)

    /**
     * sets the track height according to the [size] parameter
     */
    private fun setupTrackHeight(config: AndesLinearProgressConfiguration) =
        post {
            when (val oldParams = layoutParams) {
                null -> {
                    layoutParams = LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        config.height.toInt()
                    )
                }
                else -> {
                    oldParams.height = config.height.toInt()
                    layoutParams = oldParams
                }
            }
        }

    fun nextStep() {
        if (currentStep < numberOfSteps) {
            currentStep++
            updateIndicatorToIncrease(createConfig(), currentStep)
        }
    }

    private fun updateIndicatorToIncrease(
        config: AndesLinearProgressConfiguration,
        currentStep: Int
    ) {
        if (config.isSplit) {
            updateIndicatorToIncreaseIsSplit(config, currentStep)
        } else {
            updateIndicatorToIncreaseNoSplit(config, currentStep)
        }
    }

    private fun updateIndicatorToIncreaseIsSplit(
        config: AndesLinearProgressConfiguration,
        currentStep: Int
    ) {
        updateAllRadiusStepIndicator(config, stepViewId = currentStep)
    }

    private fun updateIndicatorToIncreaseNoSplit(
        config: AndesLinearProgressConfiguration,
        currentStep: Int
    ) =
        when (currentStep) {
            STEP_ONE -> updateAllRadiusStepIndicator(config, stepViewId = currentStep)
            STEP_TWO -> {
                updateTopBottomLeftRadiusStepIndicator(config, stepViewId = previousStepId(currentStep))
                updateTopBottomRightRadiusStepIndicator(config, stepViewId = currentStep)
            }
            else -> {
                updateStepIndicatorToNoRadius(config, stepViewId = previousStepId(currentStep))
                updateTopBottomRightRadiusStepIndicator(config, stepViewId = currentStep)
            }
        }

    fun previousStep() {
        if (currentStep > STEP_INITIAL) {
            updateIndicatorToDecrease(createConfig(), currentStep)
            currentStep--
        }
    }

    private fun updateIndicatorToDecrease(
        config: AndesLinearProgressConfiguration,
        currentStep: Int
    ) {
        if (config.isSplit) {
            updateIndicatorToDecreaseIsSplit(config, currentStep)
        } else {
            updateIndicatorToDecreaseNoSplit(config, currentStep)
        }
    }

    private fun updateIndicatorToDecreaseIsSplit(
        config: AndesLinearProgressConfiguration,
        currentStep: Int
    ) {
        findViewById<SimpleDraweeView>(currentStep).background = buildTrack(config)
    }

    private fun updateIndicatorToDecreaseNoSplit(
        config: AndesLinearProgressConfiguration,
        currentStep: Int
    ) {
        stepIndicatorTurnOff()
        when (currentStep) {
            STEP_ONE -> return
            STEP_TWO -> updateAllRadiusStepIndicator(config, previousStepId(currentStep))
            else -> updateTopBottomRightRadiusStepIndicator(config, stepViewId = previousStepId(currentStep))
        }
    }

    private fun buildTrack(config: AndesLinearProgressConfiguration) =
        buildRectangleShape(color = config.trackTint, radius = config.cornerRadius)

    private fun stepIndicatorTurnOff() {
        findViewById<SimpleDraweeView>(currentStep).background = null
    }

    private fun updateAllRadiusStepIndicator(
        config: AndesLinearProgressConfiguration,
        stepViewId: Int
    ) {
        findViewById<SimpleDraweeView>(stepViewId).background =
            buildRectangleShape(color = config.indicatorTint, radius = config.cornerRadius)
    }

    private fun updateStepIndicatorToNoRadius(
        config: AndesLinearProgressConfiguration,
        stepViewId: Int
    ) {
        findViewById<SimpleDraweeView>(stepViewId).background =
            buildRectangleShape(color = config.indicatorTint)
    }

    private fun updateTopBottomRightRadiusStepIndicator(
        config: AndesLinearProgressConfiguration,
        stepViewId: Int
    ) {
        findViewById<SimpleDraweeView>(stepViewId).background =
            buildRectangleShape(
                color = config.indicatorTint,
                topRightRadius = config.cornerRadius,
                bottomRightRadius = config.cornerRadius
            )
    }

    private fun updateTopBottomLeftRadiusStepIndicator(
        config: AndesLinearProgressConfiguration,
        stepViewId: Int
    ) {
        findViewById<SimpleDraweeView>(stepViewId).background =
            buildRectangleShape(
                color = config.indicatorTint,
                topLeftRadius = config.cornerRadius,
                bottomLeftRadius = config.cornerRadius
            )
    }

    private fun previousStepId(currentStep: Int) = currentStep - 1

    /**
     * @throws IllegalArgumentException Throw IllegalArgumentException if the given value is not
     * between [STEP_ONE] and [numberOfSteps]
     */
    fun jumpToStep(step: Int) {
        if (step in STEP_ONE..numberOfSteps) {
            when {
                step > currentStep -> {
                    repeat(step - currentStep) {
                        nextStep()
                    }
                }
                step < currentStep -> {
                    repeat(currentStep - step) {
                        previousStep()
                    }
                }
            }
        } else throw IllegalArgumentException("Value between $STEP_ONE and $numberOfSteps")
    }

    companion object {
        internal const val TINT_DEFAULT = 0
        private val SIZE_DEFAULT = AndesLinearProgressSize.SMALL
        internal const val IS_SPLIT_DEFAULT = false
        internal const val NUMBER_OF_STEPS_DEFAULT = 10
        private const val STEP_INITIAL = 0
        private const val STEP_ONE = 1
        private const val STEP_TWO = 2
    }
}
