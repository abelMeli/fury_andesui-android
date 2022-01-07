package com.mercadolibre.android.andesui.demoapp.components.floatingmenu

import android.content.Context
import android.view.View
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticFloatingmenuBinding
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
    companion object {
        private const val DEFAULT_WIDTH = 380
    }

    fun create(context: Context, container: View) {
        AndesuiStaticFloatingmenuBinding.bind(container).also { binding ->
            setupTooltips(context, binding)
            setupMenus(context, binding)
        }
    }

    private fun setupTooltips(context: Context, binding: AndesuiStaticFloatingmenuBinding) {
        binding.floatingMenuTooltip1.also {
            it.setOnClickListener { view ->
                AndesTooltip(
                    context = context,
                    body = context.resources.getString(R.string.andes_floatingmenu_tooltip_1)
                ).show(view)
            }
        }
        binding.floatingMenuTooltip2.also {
            it.setOnClickListener { view ->
                AndesTooltip(
                    context = context,
                    body = context.resources.getString(R.string.andes_floatingmenu_tooltip_2)
                ).show(view)
            }
        }
        binding.floatingMenuTooltip3.also {
            it.setOnClickListener { view ->
                AndesTooltip(
                    context = context,
                    body = context.resources.getString(R.string.andes_floatingmenu_tooltip_3)
                ).show(view)
            }
        }
        binding.floatingMenuTooltip4.also {
            it.setOnClickListener { view ->
                AndesTooltip(
                    context = context,
                    body = context.resources.getString(R.string.andes_floatingmenu_tooltip_4)
                ).show(view)
            }
        }
        binding.floatingMenuTooltip5.also {
            it.setOnClickListener { view ->
                AndesTooltip(
                    context = context,
                    body = context.resources.getString(R.string.andes_floatingmenu_tooltip_5)
                ).show(view)
            }
        }
        binding.floatingMenuTooltip6.also {
            it.setOnClickListener { view ->
                AndesTooltip(
                    context = context,
                    body = context.resources.getString(R.string.andes_floatingmenu_tooltip_6)
                ).show(view)
            }
        }
    }

    private fun setupMenus(context: Context, binding: AndesuiStaticFloatingmenuBinding) {
        val testWidth = AndesFloatingMenuWidth.Custom(DEFAULT_WIDTH)

        inflateComponent(
            binding.andesuiDemoappAndesTrigger,
            AndesFloatingMenuRows.Max,
            testWidth,
            context,
            AndesFloatingMenuOrientation.Left
        )

        inflateComponent(
            binding.andesuiDemoappAndesTrigger2,
            AndesFloatingMenuRows.Medium,
            testWidth,
            context,
            AndesFloatingMenuOrientation.Left
        )

        inflateComponent(
            binding.andesuiDemoappAndesTrigger3,
            AndesFloatingMenuRows.Max,
            testWidth,
            context,
            AndesFloatingMenuOrientation.Right
        )

        inflateComponent(
            binding.andesuiDemoappAndesTrigger4,
            AndesFloatingMenuRows.Max,
            testWidth,
            context,
            AndesFloatingMenuOrientation.Left
        )

        inflateComponent(
            binding.andesuiDemoappAndesTrigger5,
            AndesFloatingMenuRows.Medium,
            testWidth,
            context,
            AndesFloatingMenuOrientation.Right
        )

        inflateComponent(
            binding.andesuiDemoappAndesTrigger6,
            AndesFloatingMenuRows.Small,
            testWidth,
            context,
            AndesFloatingMenuOrientation.Left,
            2
        )

        binding.andesuiDemoappAndesSpecsButton.setOnClickListener {
            launchSpecs(context, AndesSpecs.FLOATINGMENU)
        }
    }

    /**
     * this method is responsible for:
     * -creating dummy lists to populate the floating menu
     * -creating the floating menu
     * -setting the click listener to the menu trigger
     */
    @Suppress("LongParameterList")
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
