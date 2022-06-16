package com.mercadolibre.android.andesui.dropdown.type

import android.content.Context
import android.view.View
import com.mercadolibre.android.andesui.dropdown.utils.DropdownBottomSheetDialog
import com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.searchbox.AndesSearchbox

/**
 * interface responsible for the creation and implementation of the different
 * menu types according to the dropdown type. Also, is responsible for providing
 * the needed methods to show/dismiss this menus, and passing the listener
 * for this actions to each menu implementation.
 */
internal interface AndesDropdownMenuInterface {
    fun createMenu(context: Context, andesList: AndesList, theme: Int, andesSearchbox: AndesSearchbox?)
    fun showMenu(view: View)
    fun dismissMenu()
    fun setOnShowListener(onShowListener: () -> Unit)
    fun setOnDismissListener(onDismissListener: () -> Unit)
    fun updateComponent(parentView: View)
}

internal class AndesDropdownMenuTypeBottomSheet : AndesDropdownMenuInterface {
    private lateinit var bottomSheet: DropdownBottomSheetDialog

    override fun createMenu(context: Context, andesList: AndesList, theme: Int, andesSearchbox: AndesSearchbox?) {
        bottomSheet = DropdownBottomSheetDialog(context, theme, andesList, andesSearchbox)
    }

    override fun showMenu(view: View) {
        bottomSheet.show()
    }

    override fun dismissMenu() {
        bottomSheet.dismiss()
    }

    override fun setOnShowListener(onShowListener: () -> Unit) {
        bottomSheet.setOnShowListener {
            onShowListener.invoke()
        }
    }

    override fun setOnDismissListener(onDismissListener: () -> Unit) {
        bottomSheet.setOnDismissListener {
            onDismissListener.invoke()
        }
    }

    override fun updateComponent(parentView: View) {
        // no-op
    }
}

internal class AndesDropdownMenuTypeFloatingMenu : AndesDropdownMenuInterface {
    private lateinit var floatingMenu: AndesFloatingMenu

    override fun createMenu(context: Context, andesList: AndesList, theme: Int, andesSearchbox: AndesSearchbox?) {
        floatingMenu = AndesFloatingMenu(context, andesList, andesSearchbox)
    }

    override fun showMenu(view: View) {
        floatingMenu.show(view)
    }

    override fun dismissMenu() {
        floatingMenu.dismiss()
    }

    override fun setOnShowListener(onShowListener: () -> Unit) {
        floatingMenu.setOnShowListener(object : AndesFloatingMenu.OnShowListener {
            override fun onShow() {
                onShowListener.invoke()
            }
        })
    }

    override fun setOnDismissListener(onDismissListener: () -> Unit) {
        floatingMenu.setOnDismissListener(object : AndesFloatingMenu.OnDismissListener {
            override fun onDismiss() {
                onDismissListener.invoke()
            }
        })
    }

    /**
     * Update floatingMenu size and measures according to the current contents the component is
     * displaying
     */
    override fun updateComponent(parentView: View) {
        floatingMenu.update(parentView)
    }
}
