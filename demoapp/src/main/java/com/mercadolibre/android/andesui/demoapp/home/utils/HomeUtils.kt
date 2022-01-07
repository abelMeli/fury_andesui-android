package com.mercadolibre.android.andesui.demoapp.home.utils

import android.content.Context
import android.view.View
import com.mercadolibre.android.andesui.databinding.AndesLayoutListBinding
import com.mercadolibre.android.andesui.demoapp.home.model.Section
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.AndesListViewItemChevron
import com.mercadolibre.android.andesui.list.size.AndesListViewItemSize
import com.mercadolibre.android.andesui.list.type.AndesListType
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate

fun setupAndesList(context: Context, items: List<Section>, onItemClick: (andesList: AndesList, position: Int) -> Unit) = AndesList(
    context = context,
    size = AndesListViewItemSize.MEDIUM,
    type = AndesListType.CHEVRON
).apply {
    dividerItemEnabled = true
    AndesLayoutListBinding.bind(this).andesList.isNestedScrollingEnabled = false
    delegate = object : AndesListDelegate {
        override fun onItemClick(andesList: AndesList, position: Int) = onItemClick.invoke(andesList, position)

        override fun bind(
            andesList: AndesList,
            view: View,
            position: Int
        ) = AndesListViewItemChevron(
            context = context,
            title = items[position].name,
            size = AndesListViewItemSize.MEDIUM
        )

        override fun getDataSetSize(andesList: AndesList): Int = items.size
    }
}
