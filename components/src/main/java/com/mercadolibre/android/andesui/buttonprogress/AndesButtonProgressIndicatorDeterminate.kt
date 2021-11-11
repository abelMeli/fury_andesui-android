package com.mercadolibre.android.andesui.buttonprogress

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.LayerDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchyInterface
import com.mercadolibre.android.andesui.button.hierarchy.AndesLoudButtonHierarchy
import com.mercadolibre.android.andesui.buttonprogress.accessibility.AndesButtonProgressIndicatorAccessibilityEventDispatcher
import com.mercadolibre.android.andesui.buttonprogress.factory.AndesButtonProgressConfiguration
import com.mercadolibre.android.andesui.buttonprogress.factory.AndesButtonProgressConfigurationFactory
import kotlin.math.max
import kotlin.math.min

/**
 * Utility class used to perform a progress loading animation in a [AndesButton].
 *
 * The progress loading animation works with different actions: IDLE, START, PAUSE, RESUME, CANCEL.
 *
 * The following attributes can be modified: progress, from, to, max & duration.
 */
internal class AndesButtonProgressIndicatorDeterminate(context: Context) : RelativeLayout(context) {

    private lateinit var andesButtonProgressConfiguration: AndesButtonProgressConfiguration

    private lateinit var progressBar: ProgressBar
    private lateinit var progressBarAnimation: ObjectAnimator

    private val a11yEventDispatcher by lazy { AndesButtonProgressIndicatorAccessibilityEventDispatcher() }

    var progress: Int
        get() = progressBar.progress
        set(value) {
            progressBar.progress = value
        }

    var from: Int
        get() = andesButtonProgressConfiguration.from
        set(value) {
            andesButtonProgressConfiguration.from = max(value, PROGRESS_MIN_VALUE)
            setupProgressFromTo()
        }

    var to: Int
        get() = andesButtonProgressConfiguration.to
        set(value) {
            andesButtonProgressConfiguration.to = min(value, PROGRESS_MAX_VALUE)
            setupProgressFromTo()
        }

    var duration: Long
        get() = andesButtonProgressConfiguration.duration
        set(value) {
            andesButtonProgressConfiguration.duration = value
            setupProgressDuration()
        }

    var andesButtonHierarchy: AndesButtonHierarchyInterface
        get() = andesButtonProgressConfiguration.andesButtonHierarchy
        set(value) {
            andesButtonProgressConfiguration.andesButtonHierarchy = value
            andesButtonProgressConfiguration.updateConfig()
            setupColor()
        }

    init {
        initAttrs()
    }

    private fun initAttrs() {
        andesButtonProgressConfiguration =
            AndesButtonProgressConfigurationFactory.create(
                DEFAULT_FROM,
                DEFAULT_TO,
                DEFAULT_DURATION,
                HIERARCHY_DEFAULT
            )
        setupComponents()
    }

    private fun initComponents() {
        val inflater = LayoutInflater.from(context)
        progressBar = inflater.inflate(resources.getLayout(R.layout.andes_progress_bar_view), null) as ProgressBar
        progressBarAnimation =
            ObjectAnimator.ofInt(progressBar, "progress", andesButtonProgressConfiguration.from, andesButtonProgressConfiguration.to)
    }

    private fun setupComponents() {
        initComponents()

        setupViewId()

        setupProgressValue()
        setupProgressFromTo()
        setupProgressDuration()

        addView(progressBar)

        setupAlignsAndMargins()
        setupColor()
    }

    private fun setupViewId() {
        if (id == NO_ID) {
            id = generateViewId()
        }
    }

    private fun setupColor() {
        if (andesButtonProgressConfiguration.isProgressAnimationAllowed) {
            val tint = andesButtonProgressConfiguration.andesButtonHierarchy.progressBackgroundColor(context)
            val progressDrawable = progressBar.progressDrawable as LayerDrawable
            val progressColor = progressDrawable.getDrawable(1)

            progressColor.setColorFilter(tint, PorterDuff.Mode.SRC)
            progressDrawable.setDrawableByLayerId(
                progressDrawable.getId(1),
                ClipDrawable(progressColor, Gravity.LEFT, ClipDrawable.HORIZONTAL)
            )
            progressBar.progressDrawable = progressDrawable
        } else {
            cancel()
            visibility = View.GONE
        }
    }

    private fun setupProgressValue() {
        progressBar.progress = 0
    }

    private fun setupProgressFromTo() {
        progressBarAnimation.setIntValues(andesButtonProgressConfiguration.from, andesButtonProgressConfiguration.to)
    }

    private fun setupProgressDuration() {
        progressBarAnimation.duration = andesButtonProgressConfiguration.duration
    }

    private fun setupAlignsAndMargins() {
        val layoutParams = progressBar.layoutParams as LayoutParams

        layoutParams.addRule(ALIGN_PARENT_LEFT)
        layoutParams.addRule(ALIGN_PARENT_RIGHT)

        progressBar.layoutParams = layoutParams
    }

    fun start() {
        if (andesButtonProgressConfiguration.isProgressAnimationAllowed) {
            progressBarAnimation.start()
            a11yEventDispatcher.notifyA11ySpinnerIsProgressLoading(
                view = this,
                isLoading = true
            )
        }

        visibility = andesButtonProgressConfiguration.progressVisibility
    }

    fun pause() {
        progressBarAnimation.pause()
        a11yEventDispatcher.notifyA11ySpinnerIsProgressLoading(
            view = this,
            isLoading = false
        )
    }

    fun resume() {
        progressBarAnimation.resume()
        a11yEventDispatcher.notifyA11ySpinnerIsProgressLoading(
            view = this,
            isLoading = true
        )
    }

    fun cancel() {
        from = DEFAULT_FROM
        to = DEFAULT_TO
        progressBarAnimation.cancel()
        progressBar.progress = 0
        a11yEventDispatcher.notifyA11ySpinnerIsProgressLoading(
            view = this,
            isLoading = false
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        progressBarAnimation.cancel()
    }

    companion object {
        private const val DEFAULT_FROM = 0
        private const val DEFAULT_TO = 200
        private const val PROGRESS_MIN_VALUE = 0
        private const val PROGRESS_MAX_VALUE = 200
        private const val DEFAULT_DURATION = 5000L
        private val HIERARCHY_DEFAULT = AndesLoudButtonHierarchy
    }
}
