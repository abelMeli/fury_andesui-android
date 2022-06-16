package com.mercadolibre.android.andesui.dropdown

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.dropdown.factory.AndesDropdownAttrParser
import com.mercadolibre.android.andesui.dropdown.factory.AndesDropdownAttrs
import com.mercadolibre.android.andesui.dropdown.factory.AndesDropdownConfiguration
import com.mercadolibre.android.andesui.dropdown.factory.AndesDropdownConfigurationFactory
import com.mercadolibre.android.andesui.dropdown.size.AndesDropdownSize
import com.mercadolibre.android.andesui.dropdown.state.AndesDropdownState
import com.mercadolibre.android.andesui.dropdown.type.AndesDropdownMenuType
import com.mercadolibre.android.andesui.dropdown.type.AndesDropdownMenuTypeBottomSheet
import com.mercadolibre.android.andesui.dropdown.utils.AndesDropdownDelegate
import com.mercadolibre.android.andesui.dropdown.utils.filterList
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.AndesListViewItem
import com.mercadolibre.android.andesui.list.AndesListViewItemSimple
import com.mercadolibre.android.andesui.list.size.AndesListViewItemSize
import com.mercadolibre.android.andesui.list.type.AndesListType
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.searchbox.AndesSearchbox
import com.mercadolibre.android.andesui.typeface.getFontOrDefault
import com.mercadolibre.android.andesui.utils.replaceWith

@SuppressWarnings("TooManyFunctions")
class AndesDropdownStandalone : ConstraintLayout {
    private lateinit var andesDropdownDelegate: AndesDropdownDelegate
    private lateinit var andesDropdownAttrs: AndesDropdownAttrs
    private lateinit var andesDropDownStandaloneChevron: ImageView
    private lateinit var andesDropDownStandaloneContent: TextView
    private lateinit var andesList: AndesList
    private var andesSearchbox: AndesSearchbox? = null
    private val chevronUpIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.andes_ui_chevron_up_12)
    private val chevronDownIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.andes_ui_chevron_down_12)
    private var listItems: List<AndesDropDownItem> = listOf()
    private var filteredItems: MutableList<AndesDropDownItem> = mutableListOf()

    /**
     * Getter and setter for [size].
     */
    var size: AndesDropdownSize
        get() = andesDropdownAttrs.andesDropdownSize
        set(value) {
            andesDropdownAttrs = andesDropdownAttrs.copy(andesDropdownSize = value)
            updateDynamicComponents()
        }

    /**
     * Getter and setter for [delegate].
     */
    var delegate: AndesDropdownDelegate
        get() = andesDropdownDelegate
        set(value) {
            andesDropdownDelegate = value
        }

    /**
     * Getter and setter for [menuType].
     */
    var menuType: AndesDropdownMenuType
        get() = andesDropdownAttrs.andesDropdownMenuType
        set(value) {
            andesDropdownAttrs = andesDropdownAttrs.copy(andesDropdownMenuType = value)
            setupMenuType(createConfig())
        }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        menuType: AndesDropdownMenuType = AndesDropdownMenuType.BOTTOMSHEET,
        size: AndesDropdownSize
    ) : super(context) {
        initAttrs(menuType, size)
    }

    /**
     * Sets the proper [config] for this component based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesDropdownAttrs = AndesDropdownAttrParser.parse(context, attrs)
        setupComponents(createConfig())
    }

    private fun initAttrs(
        menuType: AndesDropdownMenuType,
        size: AndesDropdownSize
    ) {
        andesDropdownAttrs = AndesDropdownAttrs(
                andesDropdownMenuType = menuType,
                andesDropdownSize = size,
                andesDropdownLabel = null,
                andesDropdownHelper = null,
                andesDropdownPlaceHolder = null,
                andesDropdownState = AndesDropdownState.ENABLED)
        setupComponents(createConfig())
    }

    /**
     * Responsible for setting up all properties of each component that is part of this andesDropdown.
     * Is like a choreographer 😉
     */
    private fun setupComponents(config: AndesDropdownConfiguration) {
        initComponents()
        setupViewId()
        setupMenuType(config)
    }

    /**
     * this method configures the menu type according to the selected variant.
     * It will create either a floating menu or a bottom sheet dialog.
     */
    private fun setupMenuType(config: AndesDropdownConfiguration) {
        createList(config)
        if (config.menuType is AndesDropdownMenuTypeBottomSheet) {
            with(config.menuType) {
                createMenu(context, andesList, R.style.Andes_BottomSheetDialog, andesSearchbox)
                setOnShowListener {
                    andesDropDownStandaloneChevron.setImageDrawable(chevronUpIcon)
                }
                setOnDismissListener {
                    andesDropDownStandaloneChevron.setImageDrawable(chevronDownIcon)
                }
                setOnClickListener {
                    showMenu(this@AndesDropdownStandalone)
                }
            }
        } else {
            Log.d("AndesDropdownStandalone", "Menu selected is no developed yet")
        }
    }

    /**
     * this method reates the andesList to display and adds the delegate to it
     */
    private fun createList(config: AndesDropdownConfiguration){
        andesList = AndesList(
                context,
                AndesListViewItemSize.SMALL,
                AndesListType.SIMPLE
        )
        andesList.delegate = createAndesListDelegate(config)
    }

    /**
     * This method creates the andesList delegate needed to manage the dismiss method according to
     * the [menuType] parameter and to adapt the [listItems] to the AndesListViewItems
     * needed for the displayed list.
     */
    private fun createAndesListDelegate(config: AndesDropdownConfiguration): AndesListDelegate {
        return object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                val pos = listItems.indexOf(filteredItems[position])
                selectItem(pos)
                andesList.refreshListAdapter()
                delegate.onItemSelected(this, pos)
                config.menuType.dismissMenu()
                andesSearchbox?.clearSearch()
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                val item = filteredItems[position]

                return AndesListViewItemSimple(
                    context,
                    item.title,
                    size = andesList.size,
                    avatar = item.avatar,
                    itemSelected = item.isSelected
                )
            }

            override fun getDataSetSize(andesList: AndesList): Int = filteredItems.size
        }
    }

    /**
     * Set up the text component.
     */
    private fun setupLabelComponent() {
        andesDropDownStandaloneContent.typeface = context.getFontOrDefault(R.font.andes_font_regular)
        andesDropDownStandaloneContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.size.titleFontSize(context))
    }

    /**
     * Responsible for update all properties related to components that can change dynamically
     *
     * @param config current AndesListConfiguration
     */
    private fun updateDynamicComponents() {
        setupLabelComponent()
    }

    /**
     * Sets the list of item that the Dropdown will draw
     */
    fun setItems(listItems: List<AndesDropDownItem>) {
        this.listItems = listItems
        this.filteredItems.replaceWith(this.listItems)
        if (this.listItems.isNotEmpty()) {
            selectItem(0)
        }
    }

    /**
     * Gets the list of items
     */
    fun getItems(): List<AndesDropDownItem> = listItems

    /**
     * Add the AndesSearchbox
     */
    fun addSearchable(andesSearchbox: AndesSearchbox) {
        this.andesSearchbox = andesSearchbox
        setupSearchBox()
        setupMenuType(createConfig())
    }

    /**
     * Remove the AndesSearchbox
     */
    fun removeSearchable() {
        this.andesSearchbox = null
        setupMenuType(createConfig())
    }

    private fun setupSearchBox() {
        andesSearchbox?.onTextChangedListener = object : AndesSearchbox.OnTextChangedListener {
            override fun onTextChanged(text: String) {
                filteredItems.replaceWith(filterList(text, getItems()))
                andesList.refreshListAdapter()
            }
        }
    }

    /**
     * Creates all the views that are part of this Dropdown.
     * After a view is created then a view id is added to it.
     */
    private fun initComponents() {
        val container = LayoutInflater.from(context).inflate(R.layout.andes_layout_dropdown_standalone, this)
        andesDropDownStandaloneContent = container.findViewById(R.id.text_view_andes_dropdown_trigger_content)
        andesDropDownStandaloneChevron = container.findViewById(R.id.image_view_andes_dropdown_trigger_chevron)
    }

    /**
     * Sets a view id to this dropdown.
     */
    private fun setupViewId() {
        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }
    }

    private fun selectItem(position: Int) {
        val itemSelected = listItems[position]

        listItems.forEach {
            it.isSelected = itemSelected == it
        }

        andesDropDownStandaloneContent.text = itemSelected.title
    }

    private fun createConfig() = AndesDropdownConfigurationFactory.create(context, andesDropdownAttrs)

    override fun getAccessibilityClassName(): CharSequence {
        return Spinner::class.java.name
    }
}
