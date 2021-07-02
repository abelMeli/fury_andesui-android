package com.mercadolibre.android.andesui.list

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.list.factory.AndesListAttrParser
import com.mercadolibre.android.andesui.list.factory.AndesListAttrs
import com.mercadolibre.android.andesui.list.factory.AndesListConfiguration
import com.mercadolibre.android.andesui.list.factory.AndesListConfigurationFactory
import com.mercadolibre.android.andesui.list.size.AndesListViewItemSize
import com.mercadolibre.android.andesui.list.type.AndesListType
import com.mercadolibre.android.andesui.list.utils.AndesListAdapter
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.list.utils.AndesListDividerItemDecoration

@Suppress("TooManyFunctions")
class AndesList : ConstraintLayout {

    private lateinit var andesListDelegate: AndesListDelegate
    internal lateinit var recyclerViewComponent: RecyclerView
        private set
    private lateinit var listAdapter: AndesListAdapter

    /**
     * Getter and setter for [delegate].
     */
    var delegate: AndesListDelegate
        get() = andesListDelegate
        set(value) {
            andesListDelegate = value
            val config = createConfig()
            listAdapter = AndesListAdapter(this, andesListDelegate, config.type)
            recyclerViewComponent.adapter = listAdapter
        }

    /**
     * Getter and setter for [size].
     */
    var size: AndesListViewItemSize
        get() = andesListAttrs.andesListItemSize
        set(value) {
            andesListAttrs = andesListAttrs.copy(andesListItemSize = value)
        }

    /**
     * Getter and setter for [padding].
     */
    var type: AndesListType
        get() = andesListAttrs.andesListType
        set(value) {
            andesListAttrs = andesListAttrs.copy(andesListType = value)
            listAdapter.changeAndesListType(andesListAttrs.andesListType)
        }

    /**
     * Getter and setter for [dividerItemEnabled].
     */
    var dividerItemEnabled: Boolean
        get() = andesListAttrs.andesListDividerEnabled
        set(value) {
            andesListAttrs = andesListAttrs.copy(andesListDividerEnabled = value)
            createConfig().let {
                updateDynamicComponents(it)
            }
        }

    private lateinit var andesListAttrs: AndesListAttrs

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        size: AndesListViewItemSize = SIZE_DEFAULT,
        type: AndesListType = TYPE_DEFAULT
    ) : super(context) {
        initAttrs(size, type)
    }

    /**
     * Sets the proper [config] for this component based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesListAttrs = AndesListAttrParser.parse(context, attrs)
        val config = AndesListConfigurationFactory.create(andesListAttrs)
        setupComponents(config)
    }

    /**
     * Responsible for update all properties related to components that can change dynamically
     *
     * @param config current AndesListConfiguration
     */
    private fun updateDynamicComponents(config: AndesListConfiguration) {
        setupDivider(config.dividerEnabled)
    }

    private fun initAttrs(size: AndesListViewItemSize, type: AndesListType) {
        andesListAttrs = AndesListAttrs(size, type)
        val config = AndesListConfigurationFactory.create(andesListAttrs)
        setupComponents(config)
    }

    /**
     * Responsible for setting up all properties of each component that is part of this andesList.
     * Is like a choreographer 😉
     *
     * @param config current AndesListConfiguration
     */
    private fun setupComponents(config: AndesListConfiguration) {
        initComponents()
        setupViewId()
        setupRecyclerViewComponent(config)
    }

    /**
     * Set recyclerview to handle AndesList
     *
     * @param config current AndesListConfiguration
     */
    private fun setupRecyclerViewComponent(config: AndesListConfiguration) {
        setupDivider(config.dividerEnabled)
        recyclerViewComponent.layoutManager = LinearLayoutManager(context)
    }

    /**
     * Setup the divider to AndesList based on a boolean value
     *
     * @param enabled true / false
     */
    private fun setupDivider(enabled: Boolean) {
        if (enabled && recyclerViewComponent.itemDecorationCount < 1) {
            addDivider()
        } else if (!enabled) {
            removeDivider()
        }
    }

    /**
     * Add current divider to AndesList
     */
    private fun addDivider() {
        val dividerSize = resources.getDimension(R.dimen.andes_list_item_divider).toInt()

        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.setSize(dividerSize, dividerSize)
        shape.setColor(ResourcesCompat.getColor(resources, R.color.andes_gray_010, context.theme))

        val dividerItemDecoration: RecyclerView.ItemDecoration = AndesListDividerItemDecoration(shape)
        recyclerViewComponent.addItemDecoration(dividerItemDecoration)
    }

    /**
     * Remove current divider from AndesList
     */
    private fun removeDivider() {
        while (recyclerViewComponent.itemDecorationCount > 0) {
            recyclerViewComponent.removeItemDecorationAt(0)
        }
    }

    /**
     * Creates all the views that are part of this andesList.
     * After a view is created then a view id is added to it.
     */
    private fun initComponents() {
        val container = LayoutInflater.from(context).inflate(R.layout.andes_layout_list, this)
        recyclerViewComponent = container.findViewById(R.id.andes_list)
    }

    /**
     * Sets a view id to this andesList.
     */
    private fun setupViewId() {
        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }
    }

    fun refreshListAdapter() {
        listAdapter.notifyDataSetChanged()
    }

    private fun createConfig() = AndesListConfigurationFactory.create(andesListAttrs)

    companion object {
        private val SIZE_DEFAULT = AndesListViewItemSize.MEDIUM
        private val TYPE_DEFAULT = AndesListType.SIMPLE
    }
}
