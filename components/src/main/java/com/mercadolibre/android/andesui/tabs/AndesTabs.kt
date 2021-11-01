package com.mercadolibre.android.andesui.tabs

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.tabs.accessibility.AndesTabItemAccessibilityDelegate
import com.mercadolibre.android.andesui.tabs.accessibility.AndesTabsAccessibilityDelegate
import com.mercadolibre.android.andesui.tabs.factory.AndesTabsAttrs
import com.mercadolibre.android.andesui.tabs.factory.AndesTabsAttrsParser
import com.mercadolibre.android.andesui.tabs.factory.AndesTabsConfiguration
import com.mercadolibre.android.andesui.tabs.factory.AndesTabsConfigurationFactory
import com.mercadolibre.android.andesui.tabs.type.AndesTabsType

/**
 * View that allows you to organize content by grouping information in different sections.
 */
@Suppress("TooManyFunctions")
class AndesTabs : ConstraintLayout {
    private lateinit var andesTabsAttrs: AndesTabsAttrs
    private lateinit var tabLayout: TabLayout
    private var tabs: List<AndesTabItem> = emptyList()
    private var privateListener: OnTabChangedListener? = null
    private var onViewPagerPageChangedListener: TabLayout.TabLayoutOnPageChangeListener? = null
    private var onViewPagerTabSelected: TabLayout.OnTabSelectedListener? = null
    private var tabComponentListener: TabLayout.OnTabSelectedListener? = null

    companion object {
        private const val INDICATOR_GRADIENT_WIDTH = 1
        private const val DEFAULT_TAB_POSITION = 0
        private const val TAB_INDICATOR_SCROLL_OFFSET = 0F
    }

    /**
     * Get the position of the selected tab.
     */
    var selectedTabPosition: Int = DEFAULT_TAB_POSITION
        private set

    /**
     * Getter and setter for [type].
     */
    var type: AndesTabsType
        get() = andesTabsAttrs.andesTabsType
        set(value) {
            andesTabsAttrs = andesTabsAttrs.copy(andesTabsType = value)
            setupTabLayout(createConfig())
        }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        tabs: List<AndesTabItem>,
        type: AndesTabsType = AndesTabsType.FullWidth
    ) : super(context) {
        this.tabs = tabs
        initAttrs(type)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        andesTabsAttrs = AndesTabsAttrsParser.parse(context, attrs)
        setupComponents(createConfig())
    }

    private fun initAttrs(type: AndesTabsType) {
        andesTabsAttrs = AndesTabsAttrs(type)
        setupComponents(createConfig())
    }

    private fun setupComponents(config: AndesTabsConfiguration) {
        initComponent()
        setupViewId()
        setupTabLayout(config)
        setupA11yDelegate()
        setupTabItems(config)
    }

    private fun initComponent() {
        val container = LayoutInflater.from(context).inflate(R.layout.andes_layout_tabs, this)
        tabLayout = container.findViewById(R.id.andes_tabs_tab_layout)
    }

    private fun setupViewId() {
        if (id == NO_ID) {
            id = View.generateViewId()
        }
    }

    private fun setupTabLayout(config: AndesTabsConfiguration) {
        with(tabLayout) {
            tabMode = config.type.getTabMode()
            isTabIndicatorFullWidth = config.type.isTabIndicatorFullWidth()
            tabRippleColor = config.tabRippleBackground
            setSelectedTabIndicatorColor(config.tabIndicatorColor.colorInt(context))
            setSelectedTabIndicator(createTabIndicator(config))
            setSelectedTabIndicatorHeight(config.tabIndicatorHeight)
        }

        setComponentOnTabSelectedListener(config)
    }

    private fun setupA11yDelegate() {
        importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
        accessibilityDelegate = AndesTabsAccessibilityDelegate(tabLayout)
    }

    private fun setupTabItems(config: AndesTabsConfiguration) {
        tabLayout.removeAllTabs()
        tabs.forEach { item ->
            tabLayout.newTab().apply {
                customView = renderItem(context, item.title, config)
                setupTabItemA11yDelegate(customView?.parent as? View, this, item)
            }.also {
                tabLayout.addTab(it)
            }
        }
    }

    private fun setupTabItemA11yDelegate(tabView: View?, tab: TabLayout.Tab, item: AndesTabItem) {
        tabView?.let {
            it.importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
            it.accessibilityDelegate = AndesTabItemAccessibilityDelegate(tab, item)
        }
    }

    private fun createTabIndicator(config: AndesTabsConfiguration) = GradientDrawable().apply {
        setSize(INDICATOR_GRADIENT_WIDTH, config.tabIndicatorHeight)
        renderIndicatorWithRoundedCorners(
            this,
            config.type.getLeftRoundCornerSize(selectedTabPosition, resources),
            config.type.getRightRoundCornerSize(
                selectedTabPosition,
                tabs.lastIndex,
                resources
            )
        )
    }

    /**
     * This function is responsible for removing and adding the tab component listener in order to
     * avoid multiple instances of the same behavior.
     */
    private fun setComponentOnTabSelectedListener(config: AndesTabsConfiguration) {
        tabComponentListener?.let { tabLayout.removeOnTabSelectedListener(it) }
        object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabLayout.tabSelectedIndicator?.let { indicator ->
                    config.type.updateIndicatorIfNeeded(
                        indicator,
                        selectedTabPosition,
                        tabLayout.selectedTabPosition,
                        tabs.lastIndex,
                        resources
                    )
                }
                tab?.customView?.isSelected = true

                selectedTabPosition = tabLayout.selectedTabPosition
                privateListener?.onTabSelected(selectedTabPosition, tabs)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.isSelected = false
                privateListener?.onTabUnselected(selectedTabPosition, tabs)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                privateListener?.onTabReselected(selectedTabPosition, tabs)
            }
        }.let { tabListener ->
            tabComponentListener = tabListener
            tabLayout.addOnTabSelectedListener(tabListener)
        }
    }

    private fun createConfig() = AndesTabsConfigurationFactory.create(andesTabsAttrs, context)

    /**
     * Set the list of tabs in the layout.
     */
    fun setItems(tabs: List<AndesTabItem>) {
        this.tabs = tabs
        setupTabItems(createConfig())
    }

    /**
     * Select a tab at the given position. The selection can be animated or not depending on the
     * [animate] parameter value. It will be animated by default.
     */
    fun selectTab(atPosition: Int, animate: Boolean = true) {
        val tab = tabLayout.getTabAt(atPosition)
        if (animate.not()) {
            tabLayout.setScrollPosition(atPosition, TAB_INDICATOR_SCROLL_OFFSET, true)
        }
        tab?.select()
    }

    /**
     * Links the [viewPager] with the AndesTabs in order to coordinate both components behaviors.
     */
    fun setupWithViewPager(viewPager: ViewPager) {
        removeViewPagerCoordinatorListeners(viewPager)
        addViewPagerCoordinatorListeners(viewPager)
    }

    private fun addViewPagerCoordinatorListeners(viewPager: ViewPager) {
        TabLayout.TabLayoutOnPageChangeListener(tabLayout).let {
            onViewPagerPageChangedListener = it
            viewPager.addOnPageChangeListener(it)
        }
        object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tabLayout.selectedTabPosition
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // no-op
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // no-op
            }
        }.let {
            onViewPagerTabSelected = it
            tabLayout.addOnTabSelectedListener(it)
        }
    }

    private fun removeViewPagerCoordinatorListeners(viewPager: ViewPager) {
        onViewPagerPageChangedListener?.let { viewPager.removeOnPageChangeListener(it) }
        onViewPagerTabSelected?.let { tabLayout.removeOnTabSelectedListener(it) }
    }

    /**
     * Setter for [OnTabChangedListener].
     */
    fun setOnTabChangedListener(listener: OnTabChangedListener?) {
        this.privateListener = listener
    }

    /**
     * Callback interface for responding to changing state of the selected tab.
     */
    interface OnTabChangedListener {
        /**
         * This method will be invoked when a new tab becomes selected.
         */
        fun onTabSelected(position: Int, tabs: List<AndesTabItem>)

        /**
         * This method will be invoked when a the current tab becomes unselected.
         */
        fun onTabUnselected(position: Int, tabs: List<AndesTabItem>) {}

        /**
         * This method will be invoked when a the current tab is selected again.
         */
        fun onTabReselected(position: Int, tabs: List<AndesTabItem>) {}
    }
}
