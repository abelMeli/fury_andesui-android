package com.mercadolibre.android.andesui.textfield

import android.content.Context
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.accessibility.AccessibilityManager
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.WRAP_CONTENT
import androidx.core.content.ContextCompat.getSystemService
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu
import com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu.OnDismissListener
import com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu.OnShowListener
import com.mercadolibre.android.andesui.floatingmenu.rows.AndesFloatingMenuRows
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldRightContent
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

@Suppress("TooManyFunctions")
class AndesAutosuggest : ConstraintLayout, AndesListDelegate {

    /**
     * Getter and setter for [text].
     */
    var text: String?
        get() = textfield.text
        set(value) {
            textfield.text = value
        }

    /**
     * Getter and setter for [label].
     */
    var label: String?
        get() = textfield.label
        set(value) {
            textfield.label = value
        }

    /**
     * Getter and setter for [helper].
     */
    var helper: String?
        get() = textfield.helper
        set(value) {
            textfield.helper = value
        }

    /**
     * Getter and setter for [placeholder].
     */
    var placeholder: String?
        get() = textfield.placeholder
        set(value) {
            textfield.placeholder = value
        }

    /**
     * Getter and setter for the state of [EditText].
     */
    var state: AndesTextfieldState
        get() = textfield.state
        set(value) {
            textfield.state = value
        }

    /**
     * This field applies the given filter to the EditText input.
     */
    var textFilter: InputFilter?
        get() = textfield.textFilter
        set(value) {
            textfield.textFilter = value
        }

    /**
     * Getter and setter for the [inputType].
     */
    var inputType: Int
        get() = textfield.inputType
        set(value) {
            textfield.inputType = value
        }

    /**
     * Getter and setter for the [rightContent].
     */
    var rightContent: AndesTextfieldRightContent?
        get() = textfield.rightContent
        set(value) {
            textfield.rightContent = value
        }

    /**
     * Getter and setter for the [textWatcher].
     */
    var textWatcher: TextWatcher?
        get() = textfield.textWatcher
        set(value) {
            textfield.textWatcher = value
        }

    /**
     * Getter and setter for the textfield component touch action.
     */
    var onTouch: ((MotionEvent) -> Unit)?
        get() = textfield.onTouch
        set(value) {
            textfield.onTouch = value
        }

    /**
     * Getter and setter for [suggestionsDelegate].
     */
    lateinit var suggestionsDelegate: AndesListDelegate

    private val andesList = AndesList(context).apply {
        delegate = this@AndesAutosuggest
    }

    private val andesFloatingMenu = AndesFloatingMenu(context, andesList, rows = AndesFloatingMenuRows.Max)
    private val textfield: AndesTextfield

    private val accessibilityManager by lazy { getSystemService(context, AccessibilityManager::class.java) }

    @JvmOverloads
    constructor(
        context: Context,
        label: String? = LABEL_DEFAULT,
        helper: String? = HELPER_DEFAULT,
        placeholder: String? = PLACEHOLDER_DEFAULT,
        state: AndesTextfieldState = STATE_DEFAULT
    ) : super(context) {
        textfield = AndesTextfield(
            context,
            label = label,
            helper = helper,
            placeholder = placeholder,
            state = state
        )
        setup()
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        textfield = AndesTextfield(context, attrs)
        setup()
    }

    /**
     * Shows the [AndesFloatingMenu] attached to [textfield] with the suggestions.
     * If the FloatingMenu is already being showed it will update its size and position.
     */
    fun showSuggestions() {
        andesFloatingMenu.setFocusable(isAccessibilityEnabled())
        if (andesFloatingMenu.isShowing) {
            andesFloatingMenu.update(textfield.textContainer)
        } else {
            andesFloatingMenu.show(textfield.textContainer)
        }
    }

    /**
     * Dismisses the [AndesFloatingMenu] with the suggestions only if it is being showed.
     * If it is not being showed it will do nothing.
     */
    fun hideSuggestions() = andesFloatingMenu.dismiss()

    /**
     * Set selection for textComponent
     */
    fun setSelection(selection: Int) = textfield.setSelection(selection)

    /**
     * Set the right content to indeterminate.
     */
    fun setIndeterminate() = textfield.setIndeterminate()

    /**
     * Set the right content to icon and provides an interface to give the icon path.
     */
    fun setRightIcon(
        iconPath: String,
        listener: OnClickListener? = null,
        colorIcon: Int? = R.color.andes_gray_900,
        hideWhenType: Boolean = false
    ) = textfield.setRightIcon(iconPath, listener, colorIcon, hideWhenType)

    /**
     * Sets [OnShowListener] to notify when the [AndesFloatingMenu] with the suggestions is shown.
     */
    fun setOnShowListener(listener: OnShowListener?) = andesFloatingMenu.setOnShowListener(listener)

    /**
     * Sets [OnDismissListener] to notify when the [AndesFloatingMenu] with the suggestions is dismissed.
     */
    fun setOnDismissListener(listener: OnDismissListener?) = andesFloatingMenu.setOnDismissListener(listener)

    override fun setOnFocusChangeListener(listener: OnFocusChangeListener?) {
        textfield.onFocusChangeListener = listener
    }

    override fun onItemClick(andesList: AndesList, position: Int) {
        hideSuggestions()
        suggestionsDelegate.onItemClick(andesList, position)
    }

    override fun bind(andesList: AndesList, view: View, position: Int) =
        suggestionsDelegate.bind(andesList, view, position)

    override fun getDataSetSize(andesList: AndesList) = suggestionsDelegate.getDataSetSize(andesList)

    private fun setup() {
        addView(textfield, LayoutParams(MATCH_PARENT, WRAP_CONTENT))
        setupViewId()
    }

    private fun setupViewId() {
        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }
    }

    private fun isAccessibilityEnabled() = accessibilityManager?.isTouchExplorationEnabled == true

    /**
     * Default values for AndesAutosuggest basic properties
     */
    companion object {
        private val LABEL_DEFAULT = null
        private val HELPER_DEFAULT = null
        private val PLACEHOLDER_DEFAULT = null
        private val STATE_DEFAULT = AndesTextfieldState.IDLE
    }
}
