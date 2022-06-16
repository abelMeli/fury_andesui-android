package com.mercadolibre.android.andesui.dropdown

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.dropdown.accessibility.AndesDropDownFormAccessibilityDelegate
import com.mercadolibre.android.andesui.dropdown.factory.AndesDropdownAttrParser
import com.mercadolibre.android.andesui.dropdown.factory.AndesDropdownAttrs
import com.mercadolibre.android.andesui.dropdown.factory.AndesDropdownConfiguration
import com.mercadolibre.android.andesui.dropdown.factory.AndesDropdownConfigurationFactory
import com.mercadolibre.android.andesui.dropdown.state.AndesDropdownState
import com.mercadolibre.android.andesui.dropdown.type.AndesDropdownMenuType
import com.mercadolibre.android.andesui.dropdown.utils.AndesDropdownDelegate
import com.mercadolibre.android.andesui.dropdown.utils.filterList
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.AndesListViewItem
import com.mercadolibre.android.andesui.list.AndesListViewItemSimple
import com.mercadolibre.android.andesui.list.size.AndesListViewItemSize
import com.mercadolibre.android.andesui.list.type.AndesListType
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.searchbox.AndesSearchbox
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import com.mercadolibre.android.andesui.utils.replaceWith

@SuppressWarnings("TooManyFunctions")
class AndesDropDownForm : ConstraintLayout {
    private lateinit var andesDropdownDelegate: AndesDropdownDelegate
    private lateinit var andesDropdownAttrs: AndesDropdownAttrs
    private lateinit var andesTextfield: AndesTextfield
    private lateinit var andesList: AndesList
    private var andesSearchbox: AndesSearchbox? = null
    private lateinit var container: View
    private var listItems: List<AndesDropDownItem> = listOf()
    private var filteredItems: MutableList<AndesDropDownItem> = mutableListOf()

    /**
     * Getter and setter for [label].
     */
    var label: String?
        get() = andesDropdownAttrs.andesDropdownLabel
        set(value) {
            andesDropdownAttrs = andesDropdownAttrs.copy(andesDropdownLabel = value)
            setupLabelComponent(createConfig())
        }

    /**
     * Getter and setter for [helper].
     */
    var helper: String?
        get() = andesDropdownAttrs.andesDropdownHelper
        set(value) {
            andesDropdownAttrs = andesDropdownAttrs.copy(andesDropdownHelper = value)
            setupHelperComponent(createConfig())
        }

    /**
     * Getter and setter for [placeholder].
     */
    var placeholder: String?
        get() = andesDropdownAttrs.andesDropdownPlaceHolder
        set(value) {
            andesDropdownAttrs = andesDropdownAttrs.copy(andesDropdownPlaceHolder = value)
            setupPlaceHolderComponent(createConfig())
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
     * Getter and setter for [state].
     */
    var state: AndesDropdownState
        get() = andesDropdownAttrs.andesDropdownState
        set(value) {
            andesDropdownAttrs = andesDropdownAttrs.copy(andesDropdownState = value)
            val config = createConfig()
            setupState(config)
            setupMenuType(config)
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
        label: String,
        helper: String,
        placeHolder: String
    ) : super(context) {
        initAttrs(menuType, label, helper, placeHolder, AndesDropdownState.ENABLED)
    }

    constructor(
        context: Context,
        menuType: AndesDropdownMenuType = AndesDropdownMenuType.BOTTOMSHEET,
        label: String,
        helper: String,
        placeHolder: String,
        state: AndesDropdownState
    ) : super(context) {
        initAttrs(menuType, label, helper, placeHolder, state)
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
        label: String,
        helper: String,
        placeHolder: String,
        state: AndesDropdownState
    ) {
        andesDropdownAttrs = AndesDropdownAttrs(
                andesDropdownMenuType = menuType,
                andesDropdownLabel = label,
                andesDropdownHelper = helper,
                andesDropdownPlaceHolder = placeHolder,
                andesDropdownState = state)
        setupComponents(createConfig())
    }

    /**
     * Responsible for setting up all properties of each component that is part of this andesDropdown.
     * Is like a choreographer 😉
     */
    private fun setupComponents(config: AndesDropdownConfiguration) {
        initComponents()
        setupViewId()
        setupState(config)
        setupMenuType(config)
        setupA11yDelegate()
        setupNavigation()
    }

    /**
     * This method creates all the views that are part of this Dropdown.
     * After this view is created a view id is added to it.
     */
    private fun initComponents() {
        container = LayoutInflater.from(context).inflate(R.layout.andes_layout_dropdown_form, this)
        andesTextfield = container.findViewById(R.id.andes_text_field_dropdown_form)
    }

    /**
     * Sets a view id to this dropdown.
     */
    private fun setupViewId() {
        if (id == NO_ID) {
            id = View.generateViewId()
        }
    }

    /**
     * sets the state for the internal textfield
     */
    private fun setupState(config: AndesDropdownConfiguration) {
        andesTextfield.state = config.textfieldState
        setChevronIcon(ICON_CHEVRON_DOWN, config.iconColor)
        isEnabled = config.textfieldState != AndesTextfieldState.DISABLED
    }

    /**
     * this method configures the menu type according to the selected variant.
     * It will create either a floating menu or a bottom sheet dialog.
     */
    private fun setupMenuType(config: AndesDropdownConfiguration) {
        createList(config)
        with(config.menuType) {
            createMenu(context, andesList, R.style.Andes_BottomSheetDialog, andesSearchbox)
            setOnShowListener {
                setChevronIcon(ICON_CHEVRON_UP, config.iconColor)
            }
            setOnDismissListener {
                setChevronIcon(ICON_CHEVRON_DOWN, config.iconColor)
            }
        }

        setupAndesTextFieldComponent(config)
    }

    private fun setupA11yDelegate() {
        accessibilityDelegate = AndesDropDownFormAccessibilityDelegate(this)
    }

    private fun setupNavigation() {
        container.isFocusable = true
        andesTextfield.textContainer.importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
    }

    /**
     * this method reates the andesList to display and adds the delegate to it
     */
    private fun createList(config: AndesDropdownConfiguration) {
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
     * Sets the indeterminate to show the dropdown is loading
     */
    fun setIndeterminate(status: Boolean) {
        if (status) {
            state = AndesDropdownState.DISABLED
            setIndeterminate()
        } else {
            setChevronIcon(ICON_CHEVRON_DOWN, createConfig().iconColor)
        }
    }

    internal fun setIndeterminate() {
        andesTextfield.setIndeterminate()
    }

    /**
     * Method needed by the a11yDelegate to set a proper contentDescription according
     * to the item selected in the menu
     */
    internal fun getSelectedItemTitle() = andesTextfield.text

    /**
     * Sets the list of item that the Dropdown will draw, with optional pre-selected item
     */
    fun setItems(listItems: List<AndesDropDownItem>, defaultPosition: Int = -1) {
        this.listItems = listItems
        this.filteredItems.replaceWith(this.listItems)
        if (this.listItems.isNotEmpty() && defaultPosition > -1 && defaultPosition < this.listItems.size) {
            selectItem(defaultPosition)
        } else {
            andesTextfield.text = ""
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
        val config = createConfig()
        setupSearchBox(config)
        setupMenuType(config)
    }

    /**
     * Remove the AndesSearchbox
     */
    fun removeSearchable() {
        this.andesSearchbox = null
        setupMenuType(createConfig())
    }

    private fun setupSearchBox(config: AndesDropdownConfiguration) {
        andesSearchbox?.onTextChangedListener = object : AndesSearchbox.OnTextChangedListener {
            override fun onTextChanged(text: String) {
                filteredItems.replaceWith(filterList(text, getItems()))
                andesList.refreshListAdapter()
                config.menuType.updateComponent(this@AndesDropDownForm)
            }
        }
    }

    /**
     * Gets data from the config and sets to the AndesTextField Helper component.
     */
    private fun setupHelperComponent(config: AndesDropdownConfiguration) {
        andesTextfield.helper = config.helper
    }

    /**
     * Gets data from the config and sets to the AndesTextField PlaceHolder component.
     */
    private fun setupPlaceHolderComponent(config: AndesDropdownConfiguration) {
        andesTextfield.placeholder = config.placeHolder
    }

    /**
     * Gets data from the config and sets to the AndesTextField Label component.
     */
    private fun setupLabelComponent(config: AndesDropdownConfiguration) {
        andesTextfield.label = config.label
    }

    private fun setupAndesTextFieldComponent(config: AndesDropdownConfiguration) {
        andesTextfield.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                config.menuType.showMenu(andesTextfield.textContainer)
            }
        }

        andesTextfield.setOnClick {
            config.menuType.showMenu(andesTextfield.textContainer)
        }

        andesTextfield.inputType = InputType.TYPE_NULL
    }

    /**
     * This method is only internal to be used in the AndesTimePicker
     */
    internal fun selectItem(position: Int) {
        val itemSelected = listItems[position]

        listItems.forEach {
            it.isSelected = itemSelected == it
        }

        andesTextfield.text = itemSelected.title
    }

    private fun setChevronIcon(chevronIcon: String, color: Int) {
        andesTextfield.setRightIcon(chevronIcon, null, color)
    }

    private fun createConfig() = AndesDropdownConfigurationFactory.create(context, andesDropdownAttrs)

    /**
     * This method is only created to be used in the AndesTimePicker. Since the AndesDropDown does
     * not have the READONLY state but the AndesTimePicker does, we need a way to set the value of
     * the [andesTextfield] state to this value. This method sets this state to the internal textfield,
     * changes the onClick behavior setting it to do nothing, and removes the chevron icon.
     */
    internal fun setReadOnly() {
        andesTextfield.state = AndesTextfieldState.READONLY
        andesTextfield.setOnClick(null)
        andesTextfield.rightContent = null
    }

    /**
     * This method is used in the a11yDelegate to tell if the component should read
     * the helper text (in readonly state, the helper text is hidden)
     */
    internal fun isReadOnly() = andesTextfield.state == AndesTextfieldState.READONLY

    /**
     * since the READONLY state is not present in the AndesDropdownState, we need to verify the
     * [andesTextfield] state directly to determine if we will need the role + actions for the class.
     */
    override fun getAccessibilityClassName(): CharSequence {
        return if (!isReadOnly()) {
            Spinner::class.java.name
        } else {
            EMPTY_STRING
        }
    }

    companion object {
        private const val ICON_CHEVRON_DOWN: String = "andes_ui_chevron_down_24"
        private const val ICON_CHEVRON_UP: String = "andes_ui_chevron_up_24"
        private const val EMPTY_STRING = ""
    }
}
