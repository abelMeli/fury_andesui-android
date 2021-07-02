package com.mercadolibre.android.andesui.floatingmenu.rows

import android.view.View
import com.mercadolibre.android.andesui.floatingmenu.orientation.AndesFloatingMenuOrientationVector
import com.mercadolibre.android.andesui.floatingmenu.orientation.AndesFloatingMenuVerticalOrientation
import kotlin.math.min
import kotlin.math.roundToInt

private const val HALF_ROW = 0.5f

/**
 * Calculates [com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu] height and orientation.
 * The priority is to show the content width in any position, with BOTTOM as default. In case, it is
 * not possible will adjust height to the max possible in any direction.
 */
internal fun calculateFloatingMenuHeightVector(
    parentView: View,
    rowHeight: Int,
    actualRows: Int,
    maxVisibleRows: Int?
): AndesFloatingMenuOrientationVector {
    val desiredHeight = calculateDesiredHeight(rowHeight, actualRows, maxVisibleRows)

    val requiredOrientation = AndesFloatingMenuVerticalOrientation.Bottom.orientation
    val requiredMaxSpace = requiredOrientation.getMaxAvailableSpace(parentView)
    val oppositeOrientation = requiredOrientation.getOppositeOrientation()
    val oppositeMaxSpace = oppositeOrientation.getMaxAvailableSpace(parentView)
    return when {
        desiredHeight <= requiredMaxSpace -> AndesFloatingMenuOrientationVector(
            requiredOrientation,
            desiredHeight
        )
        desiredHeight <= oppositeMaxSpace -> AndesFloatingMenuOrientationVector(
            oppositeOrientation,
            desiredHeight
        )
        oppositeMaxSpace <= requiredMaxSpace -> AndesFloatingMenuOrientationVector(
            requiredOrientation,
            calculateLargestHeightAvailable(requiredMaxSpace, rowHeight)
        )
        else -> AndesFloatingMenuOrientationVector(
            oppositeOrientation,
            calculateLargestHeightAvailable(oppositeMaxSpace, rowHeight)
        )
    }
}

/**
 * Retrieves largest height that matches the condition of showing half of the next row.
 */
private fun calculateLargestHeightAvailable(heightAvailable: Int, rowHeight: Int): Int {
    return ((getMaxRowsToShow(heightAvailable, rowHeight)) * rowHeight).toInt()
        .coerceAtLeast(min(heightAvailable, rowHeight))
}

private fun getMaxRowsToShow(heightAvailable: Int, rowHeight: Int): Float =
    (heightAvailable.toFloat() / rowHeight).roundToInt() - HALF_ROW

private fun calculateDesiredHeight(rowHeight: Int, actualRows: Int, maxVisibleRows: Int?): Int {
    return (rowHeight * getMinRows(actualRows, maxVisibleRows)).toInt()
}

private fun getMinRows(actualRows: Int, maxVisibleRows: Int?): Float {
    return maxVisibleRows?.let {
        minOf(actualRows.toFloat(), (maxVisibleRows + HALF_ROW))
    } ?: actualRows.toFloat()
}
