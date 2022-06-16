package com.mercadolibre.android.andesui.floatingmenu

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.core.view.ViewCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesLayoutFloatingmenuBinding
import com.mercadolibre.android.andesui.floatingmenu.factory.AndesFloatingMenuAttrs
import com.mercadolibre.android.andesui.floatingmenu.factory.AndesFloatingMenuConfigFactory
import com.mercadolibre.android.andesui.floatingmenu.orientation.AndesFloatingMenuOrientation
import com.mercadolibre.android.andesui.floatingmenu.rows.AndesFloatingMenuRows
import com.mercadolibre.android.andesui.floatingmenu.width.AndesFloatingMenuWidth
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.searchbox.AndesSearchbox

/**
 * This component shows a popup window that contains a list with actionable or selectable rows.
 */
@Suppress("TooManyFunctions")
class AndesFloatingMenu(
    private val context: Context,
    private val andesList: AndesList,
    width: AndesFloatingMenuWidth = AndesFloatingMenuWidth.Fixed,
    rows: AndesFloatingMenuRows = AndesFloatingMenuRows.Medium,
    orientation: AndesFloatingMenuOrientation = AndesFloatingMenuOrientation.Left
) {

    /**
     * Secondary constructor with andesSearchbox support
     */
    constructor(
        context: Context,
        andesList: AndesList,
        andesSearchbox: AndesSearchbox? = null,
        width: AndesFloatingMenuWidth = AndesFloatingMenuWidth.Fixed,
        rows: AndesFloatingMenuRows = AndesFloatingMenuRows.Medium,
        orientation: AndesFloatingMenuOrientation = AndesFloatingMenuOrientation.Left
    ) : this(context, andesList, width, rows, orientation) {
        this.andesSearchbox = andesSearchbox
        setupSearchBox()
    }

    internal var isShowing = false
    private lateinit var andesFloatingMenuAttrs: AndesFloatingMenuAttrs
    private lateinit var floatingMenu: PopupWindow
    private lateinit var frameLayout: FrameLayout
    private lateinit var frameLayoutList: FrameLayout
    private var onShowListener: OnShowListener? = null
    private var onDismissListener: OnDismissListener? = null
    private var andesSearchbox: AndesSearchbox? = null
    private val binding by lazy { AndesLayoutFloatingmenuBinding.inflate(LayoutInflater.from(context)) }

    /**
     * Getter and setter for [width].
     */
    var width: AndesFloatingMenuWidth
        get() = andesFloatingMenuAttrs.width
        set(value) {
            andesFloatingMenuAttrs = andesFloatingMenuAttrs.copy(width = value)
        }

    /**
     * Getter and setter for [rows].
     */
    var rows: AndesFloatingMenuRows
        get() = andesFloatingMenuAttrs.rows
        set(value) {
            andesFloatingMenuAttrs = andesFloatingMenuAttrs.copy(rows = value)
        }

    /**
     * Getter and setter for [orientation].
     */
    var orientation: AndesFloatingMenuOrientation
        get() = andesFloatingMenuAttrs.orientation
        set(value) {
            andesFloatingMenuAttrs = andesFloatingMenuAttrs.copy(orientation = value)
        }

    init {
        initAttrs(width, rows, orientation)
        setupComponents()
    }

    private fun initAttrs(
        width: AndesFloatingMenuWidth,
        rows: AndesFloatingMenuRows,
        orientation: AndesFloatingMenuOrientation
    ) {
        andesFloatingMenuAttrs = AndesFloatingMenuAttrs(
            width = width,
            rows = rows,
            orientation = orientation
        )
    }

    private fun setupComponents() {
        initComponent()
        setupSearchBox()
        setupList()
        setFloatingMenuTouchable()
    }

    /**
     * Initializes [PopupWindow] and sets it touchable outside in order to dismiss it when user has
     * not clicked inside the window.
     */
    private fun setFloatingMenuTouchable() = with(floatingMenu) {
        isOutsideTouchable = true
        isFocusable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setFloatingMenuElevation()
        setOnDismissListener {
            this@AndesFloatingMenu.isShowing = false
            onDismissListener?.onDismiss()
        }
    }

    private fun setFloatingMenuElevation() {
        floatingMenu.elevation =
            context.resources.getDimensionPixelSize(R.dimen.andes_card_elevated_shadow)
                .toFloat()
    }

    private fun setupList() {
        frameLayoutList.addView(andesList)
    }

    private fun setupSearchBox() {
        andesSearchbox?.let {
            (it.parent as? ViewGroup)?.removeView(it)
            frameLayout.addView(it)
            frameLayout.visibility = View.VISIBLE
        }
    }

    /**
     * Creates FloatingMenu [PopupWindow] layout and adds an [AndesList] to it.
     */
    @SuppressLint("InflateParams")
    private fun initComponent() {
        val container = binding.andesFloatingMenuContainer
        frameLayout = binding.andesFloatingMenuFrameView
        frameLayoutList = binding.andesFloatingMenuFrameViewList
        floatingMenu = PopupWindow(
            container,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
    }

    private fun isParentAttachedToActivity(parentView: View) =
        context is Activity && !context.isFinishing && ViewCompat.isAttachedToWindow(parentView)

    /**
     * Checks if FloatingMenu [PopupWindow] can be showed or not preventing crashes from lifecycle
     * or configuration changes.
     */
    private fun canShowFloatingMenu(parentView: View) = !isShowing && isParentAttachedToActivity(parentView)

    /**
     * Checks if FloatingMenu [PopupWindow] can be updated or not preventing crashes from lifecycle
     * or configuration changes.
     */
    private fun canUpdateFloatingMenu(parentView: View) = isShowing && isParentAttachedToActivity(parentView)

    /**
     * Shows [AndesFloatingMenu] attached to [parentView] with current size and orientation
     * configurations. If the FloatingMenu is already being showed it will do nothing.
     */
    fun show(parentView: View) {
        parentView.post {
            if (canShowFloatingMenu(parentView)) {
                isShowing = true
                val config = getConfig(parentView)
                floatingMenu.width = config.width
                floatingMenu.height = config.height
                floatingMenu.animationStyle = config.animation
                floatingMenu.showAsDropDown(parentView, config.xOffset, config.yOffset)

                andesList.refreshListAdapter()
                onShowListener?.onShow()
            }
        }
    }

    /**
     * Updates [AndesFloatingMenu] attached to [parentView] with current size and orientation
     * configurations. If the FloatingMenu is not being showed it will do nothing.
     */
    fun update(parentView: View) {
        parentView.post {
            if (canUpdateFloatingMenu(parentView)) {
                val config = getConfig(parentView)
                floatingMenu.update(parentView, config.xOffset, config.yOffset, config.width, config.height)
                andesList.refreshListAdapter()
            }
        }
    }

    internal fun setFocusable(isFocusable: Boolean) {
        floatingMenu.isFocusable = isFocusable
    }

    private fun getConfig(parentView: View) = AndesFloatingMenuConfigFactory.create(
        andesFloatingMenuAttrs,
        andesList,
        parentView,
        andesSearchbox
    )

    /**
     * Dismisses the [AndesFloatingMenu] only if it is being showed. If it is not being showed it
     * will do nothing.
     */
    fun dismiss() {
        if (isShowing) {
            isShowing = false
            floatingMenu.dismiss()
        }
    }

    /**
     * Sets [OnShowListener] to notify when [AndesFloatingMenu] is shown
     */
    fun setOnShowListener(listener: OnShowListener?) {
        onShowListener = listener
    }

    /**
     * Sets [OnDismissListener] to notify when [AndesFloatingMenu] is dismissed
     */
    fun setOnDismissListener(listener: OnDismissListener?) {
        onDismissListener = listener
    }

    /**
     * Interface used to allow the creator of [AndesFloatingMenu] to run some code when the
     * dialog is shown.
     */
    interface OnShowListener {
        /**
         * Method invoked when [AndesFloatingMenu] is shown.
         */
        fun onShow()
    }

    /**
     * Interface used to allow the creator of [AndesFloatingMenu] to run some code when the
     * dialog is dismissed.
     */
    interface OnDismissListener {
        /**
         * Method invoked when [AndesFloatingMenu] is dismissed.
         */
        fun onDismiss()
    }
}
