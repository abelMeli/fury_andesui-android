package com.mercadolibre.android.andesui.carousel

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.android.andesui.carousel.accessibility.AndesCarouselAccessibilityDelegate
import com.mercadolibre.android.andesui.carousel.factory.AndesCarouselAttrParser
import com.mercadolibre.android.andesui.carousel.factory.AndesCarouselAttrs
import com.mercadolibre.android.andesui.carousel.factory.AndesCarouselConfiguration
import com.mercadolibre.android.andesui.carousel.factory.AndesCarouselConfigurationFactory
import com.mercadolibre.android.andesui.carousel.margin.AndesCarouselMargin
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselAdapter
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselAutoplayOff
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselAutoplayOn
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselDelegate
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselLayoutManager
import com.mercadolibre.android.andesui.carousel.utils.CirclePagerIndicatorDecoration
import com.mercadolibre.android.andesui.databinding.AndesLayoutCarouselBinding
import com.mercadolibre.android.andesui.utils.getAccessibilityManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

@Suppress("TooManyFunctions")
class AndesCarousel : ConstraintLayout {

    private lateinit var andesCarouselAttrs: AndesCarouselAttrs
    private val binding: AndesLayoutCarouselBinding by lazy {
        AndesLayoutCarouselBinding.inflate(LayoutInflater.from(context), this, true)
    }
    private var recyclerViewComponent: RecyclerView = binding.andesCarouselRecyclerview
    private val accessibilityManager = context.getAccessibilityManager()
    private val viewManager: AndesCarouselLayoutManager by lazy {
        AndesCarouselLayoutManager(context) {
            accessibilityManager.isEnabled
        }
    }
    private lateinit var andesCarouselDelegate: AndesCarouselDelegate
    private lateinit var marginItemDecoration: RecyclerView.ItemDecoration
    private lateinit var snapHelper: PagerSnapHelper
    private val componentCoroutineScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.Default)
    }
    private var autoplayJob: Job? = null

    /**
     * Getter and setter for [margin].
     */
    var margin: AndesCarouselMargin
        get() = andesCarouselAttrs.andesCarouselMargin
        set(value) {
            andesCarouselAttrs = andesCarouselAttrs.copy(andesCarouselMargin = value)
            setupMarginRecyclerView(createConfig())
        }

    /**
     * Getter and setter for [center].
     */
    var center: Boolean
        get() = andesCarouselAttrs.andesCarouselCenter
        set(value) {
            andesCarouselAttrs = andesCarouselAttrs.copy(andesCarouselCenter = value)
            setupCenterRecyclerView(createConfig())
        }

    /**
     * Getter and setter for [delegate].
     */
    var delegate: AndesCarouselDelegate
        get() = andesCarouselDelegate
        set(value) {
            andesCarouselDelegate = value
            val carouselAdapter = AndesCarouselAdapter(this, value)
            recyclerViewComponent.adapter = carouselAdapter
            recyclerViewComponent.addItemDecoration(CirclePagerIndicatorDecoration())
            setupAutoplay(createConfig())
        }

    /**
     * Getter and setter for [infinite].
     */
    var infinite: Boolean
        get() = andesCarouselAttrs.andesCarouselInfinite
        set(value) {
            andesCarouselAttrs = andesCarouselAttrs.copy(andesCarouselInfinite = value)
            setUpRecyclerViewAsInfinite(createConfig())
        }

    /**
     * Getter and setter for [autoplay].
     */
    var autoplay: Boolean
        get() = andesCarouselAttrs.andesCarouselAutoplay
        set(value) {
            andesCarouselAttrs = andesCarouselAttrs.copy(andesCarouselAutoplay = value)
            setupAutoplay(createConfig())
        }

    /**
     * Getter and setter for [autoplaySpeed].
     */
    var autoplaySpeed: Long
        get() = andesCarouselAttrs.andesCarouselAutoplaySpeed
        set(value) {
            andesCarouselAttrs = andesCarouselAttrs.copy(andesCarouselAutoplaySpeed = value)
            setupAutoplay(createConfig())
        }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        center: Boolean = false,
        margin: AndesCarouselMargin = AndesCarouselMargin.DEFAULT
    ) : super(context) {
        initAttrs(center, margin, infinite = false, autoplay = false, autoplaySpeed = 3000)
    }

    constructor(
        context: Context,
        center: Boolean = false,
        margin: AndesCarouselMargin = AndesCarouselMargin.DEFAULT,
        infinite: Boolean = false,
        autoplay: Boolean = false,
        autoplaySpeed: Long
    ) : super(context) {
        initAttrs(center, margin, infinite, autoplay, autoplaySpeed)
    }

    /**
     * Method to add a scroll listener to recyclerView.
     *
     * @param listener listener that should be added to recyclerView.
     */
    fun addOnScrollListener(listener: RecyclerView.OnScrollListener) {
        recyclerViewComponent.addOnScrollListener(listener)
    }

    /**
     * Method to scroll to position in recyclerView.
     * @param position  position to scroll into recyclerView.
     */
    fun scrollToPosition(position: Int) {
        recyclerViewComponent.scrollToPosition(position)
    }

    /**
     * Method to smooth scroll to position in recyclerView.
     * @param position  position to scroll into recyclerView.
     */
    fun smoothScrollToPosition(position: Int) {
        recyclerViewComponent.smoothScrollToPosition(position)
    }

    override fun onDetachedFromWindow() {
        recyclerViewComponent.clearOnScrollListeners()
        super.onDetachedFromWindow()
        componentCoroutineScope.cancel()
    }

    /**
     * Sets the proper [config] for this component based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesCarouselAttrs = AndesCarouselAttrParser.parse(context, attrs)
        setupComponents(createConfig())
    }

    private fun initAttrs(
        center: Boolean,
        margin: AndesCarouselMargin,
        infinite: Boolean,
        autoplay: Boolean,
        autoplaySpeed: Long
    ) {
        andesCarouselAttrs = AndesCarouselAttrs(center, margin, infinite, autoplay, autoplaySpeed)
        setupComponents(createConfig())
    }

    /**
     * Responsible for setting up all properties of each component that is part of this andesCarousel.
     * Is like a choreographer 😉
     */
    private fun setupComponents(config: AndesCarouselConfiguration) {
        initComponents()
        setupViewId()
        updateDynamicComponents(config)
        updateComponentsAlignment(config)
        setupA11y()
    }

    private fun setupA11y() {
        recyclerViewComponent.importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
        recyclerViewComponent.
        setAccessibilityDelegateCompat(AndesCarouselAccessibilityDelegate(recyclerViewComponent) {
            if (this::andesCarouselDelegate.isInitialized) {
                andesCarouselDelegate.getDataSetSize(this)
            } else {
                EMPTY_DATA_SET
            }
        })
    }

    /**
     * Creates all the views that are part of this andesCarousel.
     * After a view is created then a view id is added to it.
     */
    private fun initComponents() {
        snapHelper = PagerSnapHelper()
    }

    /**
     * Update recyclerview.
     */
    private fun updateDynamicComponents(config: AndesCarouselConfiguration) {
        setupRecyclerViewComponent(config)
    }

    /**
     * Update attributes of recyclerview.
     */
    private fun updateComponentsAlignment(config: AndesCarouselConfiguration) {
        setupCenterRecyclerView(config)
        setupMarginRecyclerView(config)
    }

    /**
     * Set recyclerview.
     */
    private fun setupRecyclerViewComponent(config: AndesCarouselConfiguration) {
        viewManager.isInfinite = config.infinite
        recyclerViewComponent.layoutManager = viewManager
        recyclerViewComponent.overScrollMode = View.OVER_SCROLL_NEVER
        recyclerViewComponent.clipToPadding = false
        if (config.infinite) {
            smoothScrollToPosition(0)
        }
    }

    private fun setUpRecyclerViewAsInfinite(config: AndesCarouselConfiguration) {
        viewManager.isInfinite = config.infinite
        setupMarginRecyclerView(config)
    }

    /**
     * Gets data from the config and sets to center of this carousel.
     */
    private fun setupCenterRecyclerView(config: AndesCarouselConfiguration) {
        if (config.center) {
            snapHelper.attachToRecyclerView(recyclerViewComponent)
        } else {
            snapHelper.attachToRecyclerView(null)
        }
    }

    /**
     * Gets data from the config and sets to the padding of this carousel.
     */
    private fun setupMarginRecyclerView(config: AndesCarouselConfiguration) {
        if (recyclerViewComponent.itemDecorationCount != 0) {
            recyclerViewComponent.removeItemDecoration(marginItemDecoration)
        }

        marginItemDecoration = config.itemDecoration
        recyclerViewComponent.addItemDecoration(marginItemDecoration)

        val padding = config.padding
        recyclerViewComponent.setPadding(padding, 50, padding, 0)
    }

    private fun setupAutoplay(config: AndesCarouselConfiguration) {
        val strategy = if (config.autoplay) {
            AndesCarouselAutoplayOn
        } else {
            AndesCarouselAutoplayOff
        }
        autoplayJob = strategy.execute(
            autoplayJob,
            config,
            componentCoroutineScope,
            recyclerViewComponent,
            recyclerViewComponent.adapter?.itemCount ?: 0
        ) { accessibilityManager.isEnabled }
    }

    /**
     * Sets a view id to this andesCarousel.
     */
    private fun setupViewId() {
        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }
    }

    private fun createConfig() = AndesCarouselConfigurationFactory.create(context, andesCarouselAttrs)

    private companion object {
        const val EMPTY_DATA_SET = 0
    }
}
