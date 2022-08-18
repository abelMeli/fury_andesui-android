package com.mercadolibre.android.andesui.tooltip.location

import com.mercadolibre.android.andesui.tooltip.factory.Constants.GENERIC_X_VALUE
import com.mercadolibre.android.andesui.utils.xEnd
import com.mercadolibre.android.andesui.utils.xMiddle
import com.mercadolibre.android.andesui.utils.xStart
import com.mercadolibre.android.andesui.utils.yEnd
import com.mercadolibre.android.andesui.utils.yMiddle
import com.mercadolibre.android.andesui.utils.yStart

sealed class AndesTooltipArrowLocation {
    internal abstract fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float
    internal abstract fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float
}

/**
 * What does it mean? When a rectangular 20x10 is rotated, it is centered horizontally. Example:
 * Example: 20 x 10 -> is rotated 90 degrees -> 5 free px - 10px - 5 free px
 * With this method we will get that 5px offset on each side.
 */
internal fun getRotatedArrowOffset(tooltip: AndesTooltipLocationInterface): Float =
    (tooltip.arrowWidth - tooltip.arrowHeight) / 2f

/**
 * Formulas for NON-rotated arrow
 */
internal fun positionBottomAnyY(tooltip: AndesTooltipLocationInterface): Float =
    tooltip.radiusLayout.yEnd()

internal fun positionTopAnyY(tooltip: AndesTooltipLocationInterface): Float =
    tooltip.radiusLayout.yStart() - tooltip.arrowHeight

internal fun positionAnyLeftX(tooltip: AndesTooltipLocationInterface): Float =
    tooltip.radiusLayout.xStart() + tooltip.arrowBorder

internal fun positionAnyRightX(tooltip: AndesTooltipLocationInterface): Float =
    tooltip.radiusLayout.xEnd() - tooltip.arrowBorder - tooltip.arrowWidth

internal fun positionAnyMiddleX(tooltip: AndesTooltipLocationInterface): Float =
    tooltip.radiusLayout.xMiddle() - tooltip.arrowWidth / 2f

/**
 * Formulas for rotated arrow
 */
internal fun rotatedPositionAnyBottomY(tooltip: AndesTooltipLocationInterface): Float =
    tooltip.radiusLayout.yEnd() - tooltip.arrowBorder - tooltip.arrowWidth + getRotatedArrowOffset(tooltip)

internal fun rotatedPositionAnyTopY(tooltip: AndesTooltipLocationInterface): Float =
    tooltip.radiusLayout.yStart() + tooltip.arrowBorder + getRotatedArrowOffset(tooltip)

internal fun rotatedPositionAnyLeftX(tooltip: AndesTooltipLocationInterface): Float =
    tooltip.radiusLayout.xStart() - tooltip.arrowHeight - getRotatedArrowOffset(tooltip)

internal fun rotatedPositionAnyRightX(tooltip: AndesTooltipLocationInterface): Float =
    tooltip.radiusLayout.xEnd() - getRotatedArrowOffset(tooltip)

internal fun rotatedPositionAnyMiddleY(tooltip: AndesTooltipLocationInterface): Float =
    tooltip.radiusLayout.yMiddle() - tooltip.arrowWidth / 2f + getRotatedArrowOffset(tooltip)

internal object AndesTooltipArrowBottomLeft : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float = positionAnyLeftX(tooltip)

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float = positionBottomAnyY(tooltip)
}

internal object AndesTooltipArrowBottomMiddle : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float = positionAnyMiddleX(tooltip)

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float = positionBottomAnyY(tooltip)
}

internal object AndesTooltipArrowBottomRight : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float = positionAnyRightX(tooltip)

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float = positionBottomAnyY(tooltip)
}

internal object AndesTooltipArrowTopLeft : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float = positionAnyLeftX(tooltip)

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float = positionTopAnyY(tooltip)
}

internal object AndesTooltipArrowTopMiddle : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float = positionAnyMiddleX(tooltip)

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float = positionTopAnyY(tooltip)
}

internal object AndesTooltipArrowTopRight : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float = positionAnyRightX(tooltip)

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float = positionTopAnyY(tooltip)
}

internal object AndesTooltipArrowRightTop : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float = rotatedPositionAnyRightX(tooltip)

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float = rotatedPositionAnyTopY(tooltip)
}

internal object AndesTooltipArrowRightMiddle : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float = rotatedPositionAnyRightX(tooltip)

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float = rotatedPositionAnyMiddleY(tooltip)
}

internal object AndesTooltipArrowRightBottom : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float = rotatedPositionAnyRightX(tooltip)

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float = rotatedPositionAnyBottomY(tooltip)
}

internal object AndesTooltipArrowLeftTop : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float = rotatedPositionAnyLeftX(tooltip)

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float = rotatedPositionAnyTopY(tooltip)
}

internal object AndesTooltipArrowLeftMiddle : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float = rotatedPositionAnyLeftX(tooltip)

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float = rotatedPositionAnyMiddleY(tooltip)
}

internal object AndesTooltipArrowLeftBottom : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float = rotatedPositionAnyLeftX(tooltip)

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float = rotatedPositionAnyBottomY(tooltip)
}

internal object AndesTooltipArrowTopFree : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float = GENERIC_X_VALUE

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float = positionTopAnyY(tooltip)
}

internal object AndesTooltipArrowBottomFree : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float = GENERIC_X_VALUE

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float = positionBottomAnyY(tooltip)
}

enum class ArrowPositionId {
    TOP,
    LEFT,
    RIGHT,
    BOTTOM,
    MIDDLE,
    FREE
}

@Suppress("ComplexMethod")
internal fun getAndesTooltipArrowLocation(tooltipSideId: ArrowPositionId, positionInSideId: ArrowPositionId): AndesTooltipArrowLocation =
    when (tooltipSideId to positionInSideId) {
        ArrowPositionId.BOTTOM to ArrowPositionId.LEFT -> AndesTooltipArrowBottomLeft
        ArrowPositionId.BOTTOM to ArrowPositionId.MIDDLE -> AndesTooltipArrowBottomMiddle
        ArrowPositionId.BOTTOM to ArrowPositionId.RIGHT -> AndesTooltipArrowBottomRight
        ArrowPositionId.BOTTOM to ArrowPositionId.FREE -> AndesTooltipArrowBottomFree

        ArrowPositionId.TOP to ArrowPositionId.LEFT -> AndesTooltipArrowTopLeft
        ArrowPositionId.TOP to ArrowPositionId.MIDDLE -> AndesTooltipArrowTopMiddle
        ArrowPositionId.TOP to ArrowPositionId.RIGHT -> AndesTooltipArrowTopRight
        ArrowPositionId.TOP to ArrowPositionId.FREE -> AndesTooltipArrowTopFree

        ArrowPositionId.RIGHT to ArrowPositionId.TOP -> AndesTooltipArrowRightTop
        ArrowPositionId.RIGHT to ArrowPositionId.MIDDLE -> AndesTooltipArrowRightMiddle
        ArrowPositionId.RIGHT to ArrowPositionId.BOTTOM -> AndesTooltipArrowRightBottom

        ArrowPositionId.LEFT to ArrowPositionId.TOP -> AndesTooltipArrowLeftTop
        ArrowPositionId.LEFT to ArrowPositionId.MIDDLE -> AndesTooltipArrowLeftMiddle
        ArrowPositionId.LEFT to ArrowPositionId.BOTTOM -> AndesTooltipArrowLeftBottom

        else -> AndesTooltipArrowBottomLeft
    }
