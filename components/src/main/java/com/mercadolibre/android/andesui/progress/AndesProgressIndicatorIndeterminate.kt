package com.mercadolibre.android.andesui.progress

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import android.util.AttributeSet
import android.view.View
import com.mercadolibre.android.andesui.progress.accessibility.AndesProgressIndicatorAccessibilityEventDispatcher
import com.mercadolibre.android.andesui.progress.factory.AndesProgressAttrs
import com.mercadolibre.android.andesui.progress.factory.AndesProgressAttrsParser
import com.mercadolibre.android.andesui.progress.factory.AndesProgressConfiguration
import com.mercadolibre.android.andesui.progress.factory.AndesProgressConfigurationFactory
import com.mercadolibre.android.andesui.progress.size.AndesProgressSize

class AndesProgressIndicatorIndeterminate : ConstraintLayout {

    private lateinit var andesProgressAttr: AndesProgressAttrs
    private lateinit var progressComponent: LoadingSpinner
    private val a11yEventDispatcher by lazy { AndesProgressIndicatorAccessibilityEventDispatcher() }

    var tint: Int
        get() = andesProgressAttr.tint
        set(value) {
            andesProgressAttr = andesProgressAttr.copy(tint = value)
            setupColor(createConfig())
        }

    var size: AndesProgressSize
        get() = andesProgressAttr.andesProgressSize
        set(value) {
            andesProgressAttr = andesProgressAttr.copy(andesProgressSize = value)
            setupSize(createConfig())
        }

    /**
     * Simplest constructor for creating an AndesProgress programmatically.
     */
    constructor(context: Context) : super(context) {
        initAttrs(SIZE_DEFAULT, TINT)
    }

    /**
     * Constructor for creating an AndesProgress via XML.
     * The [attrs] are the attributes specified in the parameters of XML.
     *
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    /**
     * Constructor for creating an AndesProgress via XML.
     * The [attrs] are the attributes specified in the parameters of XML.
     * The [defStyleAttr] is not considered because we take care of all Andes styling for you.
     */
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs)

    private fun initAttrs(attrs: AttributeSet?) {
        andesProgressAttr = AndesProgressAttrsParser.parse(context, attrs)
        setupComponents(createConfig())
    }

    private fun initAttrs(
        progressSize: AndesProgressSize,
        tint: Int
    ) {
        andesProgressAttr = AndesProgressAttrs(progressSize, tint, false)
        setupComponents(createConfig())
    }

    private fun initComponents(config: AndesProgressConfiguration) {
        progressComponent = LoadingSpinner(context)
        if (config.start) start()
    }

    private fun setupComponents(config: AndesProgressConfiguration) {
        initComponents(config)

        if (id == NO_ID) {
            id = View.generateViewId()
        }

        progressComponent.id = View.generateViewId()

        setupColor(config)
        setupSize(config)

        addView(progressComponent)

        setupConstraints()
    }

    private fun createConfig() = AndesProgressConfigurationFactory.create(context, andesProgressAttr)

    private fun setupConstraints() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)

        constraintSet.centerHorizontally(progressComponent.id, ConstraintSet.PARENT_ID)
        constraintSet.centerVertically(progressComponent.id, ConstraintSet.PARENT_ID)

        constraintSet.applyTo(this)
    }

    private fun setupColor(config: AndesProgressConfiguration) {
        progressComponent.setStrokeSize(config.stroke)
        progressComponent.setPrimaryColor(config.tint)
    }

    private fun setupSize(config: AndesProgressConfiguration) {
        val params = LayoutParams(config.size.toInt(), config.size.toInt())
        progressComponent.layoutParams = params
    }

    fun start() {
        progressComponent.start()
        a11yEventDispatcher.notifyA11ySpinnerIsLoading(
                view = this,
                isLoading = true
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        progressComponent.stop()
    }

    companion object {
        private const val TINT = 0
        private val SIZE_DEFAULT = AndesProgressSize.SMALL
    }
}
