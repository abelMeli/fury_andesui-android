package com.mercadolibre.android.andesui.demoapp.components.floatingmenu

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu
import com.mercadolibre.android.andesui.floatingmenu.orientation.AndesFloatingMenuOrientation
import com.mercadolibre.android.andesui.floatingmenu.rows.AndesFloatingMenuRows
import com.mercadolibre.android.andesui.floatingmenu.width.AndesFloatingMenuWidth
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.AndesListViewItem
import com.mercadolibre.android.andesui.list.AndesListViewItemSimple
import com.mercadolibre.android.andesui.list.size.AndesListViewItemSize
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.tooltip.AndesTooltip

class FloatingMenuStaticPage {

    private lateinit var context: Context
    companion object {
        private const val DEFAULT_WIDTH = 380
    }

    fun create(context: Context, container: View) {
        this.context = context
        setupTooltips(container)
        setupMenus(container)
    }

    private fun setupTooltips(container: View) {
        container.findViewById<ImageView>(R.id.floating_menu_tooltip_1).also {
            it.setOnClickListener { view ->
                AndesTooltip(
                    context = context,
                    body = context.resources.getString(R.string.andes_floatingmenu_tooltip_1)
                ).show(view)
            }
        }
        container.findViewById<ImageView>(R.id.floating_menu_tooltip_2).also {
            it.setOnClickListener { view ->
                AndesTooltip(
                    context = context,
                    body = context.resources.getString(R.string.andes_floatingmenu_tooltip_2)
                ).show(view)
            }
        }
        container.findViewById<ImageView>(R.id.floating_menu_tooltip_3).also {
            it.setOnClickListener { view ->
                AndesTooltip(
                    context = context,
                    body = context.resources.getString(R.string.andes_floatingmenu_tooltip_3)
                ).show(view)
            }
        }
        container.findViewById<ImageView>(R.id.floating_menu_tooltip_4).also {
            it.setOnClickListener { view ->
                AndesTooltip(
                    context = context,
                    body = context.resources.getString(R.string.andes_floatingmenu_tooltip_4)
                ).show(view)
            }
        }
        container.findViewById<ImageView>(R.id.floating_menu_tooltip_5).also {
            it.setOnClickListener { view ->
                AndesTooltip(
                    context = context,
                    body = context.resources.getString(R.string.andes_floatingmenu_tooltip_5)
                ).show(view)
            }
        }
        container.findViewById<ImageView>(R.id.floating_menu_tooltip_6).also {
            it.setOnClickListener { view ->
                AndesTooltip(
                    context = context,
                    body = context.resources.getString(R.string.andes_floatingmenu_tooltip_6)
                ).show(view)
            }
        }
    }

    private fun setupMenus(container: View) {
        val testWidth = AndesFloatingMenuWidth.Custom(DEFAULT_WIDTH)

        inflateComponent(
            container.findViewById(R.id.andesui_demoapp_andes_trigger),
            AndesFloatingMenuRows.Max,
            testWidth,
            context,
            AndesFloatingMenuOrientation.Left
        )

        inflateComponent(
            container.findViewById(R.id.andesui_demoapp_andes_trigger_2),
            AndesFloatingMenuRows.Medium,
            testWidth,
            context,
            AndesFloatingMenuOrientation.Left
        )

        inflateComponent(
            container.findViewById(R.id.andesui_demoapp_andes_trigger_3),
            AndesFloatingMenuRows.Max,
            testWidth,
            context,
            AndesFloatingMenuOrientation.Right
        )

        inflateComponent(
            container.findViewById(R.id.andesui_demoapp_andes_trigger_4),
            AndesFloatingMenuRows.Max,
            testWidth,
            context,
            AndesFloatingMenuOrientation.Left
        )

        inflateComponent(
            container.findViewById(R.id.andesui_demoapp_andes_trigger_5),
            AndesFloatingMenuRows.Medium,
            testWidth,
            context,
            AndesFloatingMenuOrientation.Right
        )

        inflateComponent(
            container.findViewById(R.id.andesui_demoapp_andes_trigger_6),
            AndesFloatingMenuRows.Small,
            testWidth,
            context,
            AndesFloatingMenuOrientation.Left,
            2
        )

        container.findViewById<AndesButton>(R.id.andesui_demoapp_andes_specs_button).setOnClickListener {
            launchSpecs(container.context, AndesSpecs.FLOATINGMENU)
        }
    }

    /**
     * this method is responsible for:
     * -creating dummy lists to populate the floating menu
     * -creating the floating menu
     * -setting the click listener to the menu trigger
     */
    private fun inflateComponent(
        andesButton: AndesButton,
        rows: AndesFloatingMenuRows,
        width: AndesFloatingMenuWidth,
        context: Context,
        orientation: AndesFloatingMenuOrientation,
        dataSetSize: Int = 20
    ) {
        val andesList = AndesList(context)
        val floatingMenu = AndesFloatingMenu(context, andesList, width = width, rows = rows, orientation = orientation)
        andesList.delegate = object : AndesListDelegate {
            var selected = -1
            override fun onItemClick(andesList: AndesList, position: Int) {
                selected = position
                floatingMenu.dismiss()
                andesList.refreshListAdapter()
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemSimple(
                        context = context,
                        title = "Item $position",
                        size = AndesListViewItemSize.SMALL,
                        itemSelected = selected == position
                )
            }

            override fun getDataSetSize(andesList: AndesList): Int = dataSetSize
        }

        andesButton.setOnClickListener {
            floatingMenu.show(it)
        }
    }
}
