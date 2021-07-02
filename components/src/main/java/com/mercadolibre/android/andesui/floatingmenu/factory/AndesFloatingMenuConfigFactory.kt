package com.mercadolibre.android.andesui.floatingmenu.factory

import android.view.View
import android.view.ViewGroup
import androidx.annotation.StyleRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.floatingmenu.orientation.AndesFloatingMenuOrientationVector
import com.mercadolibre.android.andesui.floatingmenu.orientation.AndesFloatingMenuVerticalOrientation
import com.mercadolibre.android.andesui.floatingmenu.rows.AndesFloatingMenuRowsInterface
import com.mercadolibre.android.andesui.floatingmenu.rows.calculateFloatingMenuHeightVector
import com.mercadolibre.android.andesui.floatingmenu.width.calculateFloatingMenuWidthVector
import com.mercadolibre.android.andesui.list.AndesList

internal data class AndesFloatingMenuConfig(
    val width: Int,
    val height: Int,
    val xOffset: Int,
    val yOffset: Int,
    @StyleRes val animation: Int
)

/**
 * Object used to help in [com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu]
 * construction. It will make all decisions and calculations to return an [AndesFloatingMenuConfig]
 * that will be used to build the FloatingMenu.
 */
internal object AndesFloatingMenuConfigFactory {

    private const val MIN_HEIGHT: Int = 1

    fun create(
        andesFloatingMenuAttrs: AndesFloatingMenuAttrs,
        andesList: AndesList,
        parentView: View
    ): AndesFloatingMenuConfig = with(andesFloatingMenuAttrs) {
        val horizontalVector = calculateFloatingMenuWidthVector(
            orientation.orientation,
            parentView,
            width.size.getWidth(parentView)
        )
        val verticalVector = resolveFloatingMenuHeightVector(
            parentView,
            andesList,
            horizontalVector.size,
            rows.rows
        )
        val yOffset =
            verticalVector.orientation.getFloatingMenuOffset(parentView, verticalVector.size)
        AndesFloatingMenuConfig(
            width = horizontalVector.size,
            height = verticalVector.size,
            xOffset = horizontalVector.orientation.getFloatingMenuOffset(
                parentView,
                horizontalVector.size
            ),
            yOffset = yOffset,
            animation = resolveAnimation(yOffset)
        )
    }

    private fun resolveFloatingMenuHeightVector(
        parentView: View,
        andesList: AndesList,
        size: Int,
        rows: AndesFloatingMenuRowsInterface
    ): AndesFloatingMenuOrientationVector {
        return getFirstRowView(andesList, size)?.let { rowView ->
            calculateFloatingMenuHeightVector(
                parentView,
                getRowHeight(rowView),
                andesList.delegate.getDataSetSize(andesList),
                rows.maxItemsShown()
            )
        } ?: AndesFloatingMenuOrientationVector(
            AndesFloatingMenuVerticalOrientation.Bottom.orientation,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun getRowHeight(rowView: View): Int {
        return when {
            rowView.height > 0 -> rowView.height
            rowView.measuredHeight > 0 -> rowView.measuredHeight
            else -> MIN_HEIGHT
        }
    }

    /**
     * Requests the layout of the first component in the recyclerview and returns the view.
     */
    private fun getFirstRowView(andesList: AndesList, width: Int): View? {
        andesList.recyclerViewComponent.layout(0, 0, width, MIN_HEIGHT)
        return (andesList.recyclerViewComponent.layoutManager as? LinearLayoutManager)?.let {
            it.findViewByPosition(it.findFirstVisibleItemPosition())
        }
    }

    private fun resolveAnimation(yOffset: Int): Int {
        return if (yOffset < 0) {
            R.style.Andes_FloatingMenuTopAnimation
        } else {
            R.style.Andes_FloatingMenuBottomAnimation
        }
    }
}
