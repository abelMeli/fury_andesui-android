package com.mercadolibre.android.andesui.tooltip.style

import android.content.Context
import android.view.View
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.tooltip.extensions.getViewPointOnScreen
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipArrowData
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipArrowLocation
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipArrowPoint
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocation
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocationConfig
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocationInterface
import com.mercadolibre.android.andesui.tooltip.location.ArrowPositionId
import com.mercadolibre.android.andesui.tooltip.location.BottomAndesTooltipLocationConfig
import com.mercadolibre.android.andesui.tooltip.location.LeftAndesTooltipLocationConfig
import com.mercadolibre.android.andesui.tooltip.location.RightAndesTooltipLocationConfig
import com.mercadolibre.android.andesui.tooltip.location.TopAndesTooltipLocationConfig
import com.mercadolibre.android.andesui.utils.ScreenUtils

/**
 * This class contains the different Types with styles for the specific Tooltip Size
 */
internal sealed class AndesTooltipSizeInterface {

    val padding = 16

    abstract fun titleMaxWidth(context: Context, isDismissible: Boolean): Int

    abstract fun bodyContentMaxWidth(context: Context): Int

    /**
     * This method must return null if the view has to measure the bodyWindowWidth
     *
     * @param context The context of the App.
     */
    abstract fun tooltipMeasureWidth(context: Context, view: View): Int

    abstract fun getArrowPoint(
        arrowLocation: AndesTooltipArrowLocation,
        tooltipLocation: AndesTooltipLocationInterface,
        fixedArrowXPosition: Int
    ): AndesTooltipArrowPoint

    abstract fun getLocationConfig(
        tooltip: AndesTooltipLocationInterface,
        location: AndesTooltipLocation
    ): AndesTooltipLocationConfig

    abstract fun getTooltipXOffForSize(target: View, tooltip: AndesTooltipLocationInterface): AndesTooltipArrowData
}

internal object AndesTooltipSizeDynamic : AndesTooltipSizeInterface() {
    override fun titleMaxWidth(context: Context, isDismissible: Boolean) = getTextMaxWidth(context)

    override fun bodyContentMaxWidth(context: Context) = getTextMaxWidth(context)

    override fun tooltipMeasureWidth(context: Context, view: View) = view.measuredWidth

    override fun getArrowPoint(
        arrowLocation: AndesTooltipArrowLocation,
        tooltipLocation: AndesTooltipLocationInterface,
        fixedArrowXPosition: Int
    ) = AndesTooltipArrowPoint(
        x = arrowLocation.getArrowPositionX(tooltipLocation),
        y = arrowLocation.getArrowPositionY(tooltipLocation)
    )

    override fun getLocationConfig(
        tooltip: AndesTooltipLocationInterface,
        location: AndesTooltipLocation
    ) = when (location) {
        AndesTooltipLocation.TOP -> TopAndesTooltipLocationConfig(tooltip)
        AndesTooltipLocation.BOTTOM -> BottomAndesTooltipLocationConfig(tooltip)
        AndesTooltipLocation.LEFT -> LeftAndesTooltipLocationConfig(tooltip)
        AndesTooltipLocation.RIGHT -> RightAndesTooltipLocationConfig(tooltip)
    }

    override fun getTooltipXOffForSize(target: View, tooltip: AndesTooltipLocationInterface): AndesTooltipArrowData {
        val targetX = target.getViewPointOnScreen().x
        val targetWidth = target.measuredWidth
        val targetHalfXPoint = targetX + (targetWidth / 2)

        val tooltipWidth = tooltip.tooltipMeasuredWidth
        val tooltipHalf = tooltipWidth / 2

        val leftSpaceNeededForCenterArrow = targetHalfXPoint - tooltipHalf
        val rightSpaceNeededForCenterArrow = targetHalfXPoint + tooltipHalf

        val rightSpaceNeededForLeftArrow = tooltipWidth - tooltip.arrowWidth / 2 - tooltip.arrowBorder
        val availableSpaceForLeftArrow = tooltip.displaySizeX - targetHalfXPoint

        val canArrowCenter = leftSpaceNeededForCenterArrow > 0 && rightSpaceNeededForCenterArrow < tooltip.displaySizeX
        val canArrowLeft = rightSpaceNeededForLeftArrow < availableSpaceForLeftArrow

        return when {
            (canArrowCenter) -> {
                AndesTooltipArrowData(
                    positionInSide = ArrowPositionId.MIDDLE,
                    point = ((targetWidth / 2) - (tooltip.tooltipMeasuredWidth / 2))
                )
            }
            (canArrowLeft) -> {
                AndesTooltipArrowData(
                    positionInSide = ArrowPositionId.LEFT,
                    point = targetWidth / 2 - tooltip.arrowWidth / 2 - tooltip.arrowBorder - tooltip.paddingWithArrowHorizontal
                )
            }
            else -> {
                AndesTooltipArrowData(
                    positionInSide = ArrowPositionId.RIGHT,
                    point = -tooltip.tooltipMeasuredWidth + targetWidth / 2 + tooltip.arrowWidth / 2 +
                            tooltip.arrowBorder + tooltip.paddingWithArrowHorizontal
                )
            }
        }
    }

    private fun getTextMaxWidth(context: Context) =
        context.resources.getDimensionPixelSize(R.dimen.andes_tooltip_text_max_width)
}

internal object AndesTooltipSizeFullSize : AndesTooltipSizeInterface() {

    override fun titleMaxWidth(context: Context, isDismissible: Boolean) =
        if (isDismissible) {
            getDisplayMaxWidthForContent(context) - context.resources.getDimensionPixelOffset(R.dimen.andes_tooltip_arrow_width)
        } else {
            getDisplayMaxWidthForContent(context)
        }

    override fun bodyContentMaxWidth(context: Context) = getDisplayMaxWidthForContent(context)

    override fun tooltipMeasureWidth(context: Context, view: View) =  view.measuredWidth.takeIf{ getValidateWidth(it) } ?: getDisplayWidth(context)

    override fun getArrowPoint(
        arrowLocation: AndesTooltipArrowLocation,
        tooltipLocation: AndesTooltipLocationInterface,
        fixedArrowXPosition: Int
    ) = AndesTooltipArrowPoint(
        x = arrowLocation.getArrowPositionX(tooltipLocation).takeIf { getValidateWidth(tooltipLocation.tooltipMeasuredWidth) } ?: fixedArrowXPosition.toFloat(),
        y = arrowLocation.getArrowPositionY(tooltipLocation)
    )

    override fun getLocationConfig(
        tooltip: AndesTooltipLocationInterface,
        location: AndesTooltipLocation
    ) = when (location) {
        AndesTooltipLocation.BOTTOM -> BottomAndesTooltipLocationConfig(tooltip)
        else -> TopAndesTooltipLocationConfig(tooltip)
    }

    override fun getTooltipXOffForSize(target: View, tooltip: AndesTooltipLocationInterface): AndesTooltipArrowData {

        ///// full text size////
        val targetHalfXPoint = target.getViewPointOnScreen().x + (target.measuredWidth / 2)

        val minor = getValidateWidth(tooltip.tooltipMeasuredWidth)

        val minLeftSpace = tooltip.paddingWithArrowHorizontal + tooltip.arrowWidth / 2 + tooltip.arrowBorder
        val maxRightSpace = tooltip.tooltipMeasuredWidth - minLeftSpace

        val mayArrowLeft = targetHalfXPoint >= minLeftSpace
        val mayArrowRight = targetHalfXPoint <= maxRightSpace
        val targetIsBetweenLimits = mayArrowLeft && mayArrowRight

        ///// dynamic text /////

        val tooltipWidth = tooltip.tooltipMeasuredWidth
        val tooltipHalf = tooltipWidth / 2

        val leftSpaceNeededForCenterArrow = targetHalfXPoint - tooltipHalf
        val rightSpaceNeededForCenterArrow = targetHalfXPoint + tooltipHalf

        val rightSpaceNeededForLeftArrow = tooltipWidth - tooltip.arrowWidth / 2 - tooltip.arrowBorder
        val availableSpaceForLeftArrow = tooltip.displaySizeX - targetHalfXPoint

        val canArrowCenter = leftSpaceNeededForCenterArrow > 0 && rightSpaceNeededForCenterArrow < tooltip.displaySizeX
        val canArrowLeft = rightSpaceNeededForLeftArrow < availableSpaceForLeftArrow

        if(minor) {

            //dynamic text
            return when {
                (canArrowCenter) -> {
                    AndesTooltipArrowData(
                        positionInSide = ArrowPositionId.MIDDLE,
                        point = ((target.measuredWidth / 2) - (tooltip.tooltipMeasuredWidth / 2))
                    )
                }
                (canArrowLeft) -> {
                    AndesTooltipArrowData(
                        positionInSide = ArrowPositionId.LEFT,
                        point = target.measuredWidth / 2 - tooltip.arrowWidth / 2 - tooltip.arrowBorder - tooltip.paddingWithArrowHorizontal
                    )
                }
                else -> {
                    AndesTooltipArrowData(
                        positionInSide = ArrowPositionId.RIGHT,
                        point = -tooltip.tooltipMeasuredWidth + target.measuredWidth / 2 + tooltip.arrowWidth / 2 +
                                tooltip.arrowBorder + tooltip.paddingWithArrowHorizontal
                    )
                }
            }

        }else{

            //fullsize screen
            return when {
                (targetIsBetweenLimits) -> {
                    AndesTooltipArrowData(
                        positionInSide = ArrowPositionId.FREE,
                        point = targetHalfXPoint - tooltip.arrowWidth / 2
                    )
                }
                (!mayArrowLeft) -> {
                    AndesTooltipArrowData(
                        positionInSide = ArrowPositionId.LEFT,
                        point = minLeftSpace - tooltip.paddingWithArrowHorizontal
                    )
                }
                else -> {
                    AndesTooltipArrowData(
                        positionInSide = ArrowPositionId.RIGHT,
                        point = maxRightSpace - tooltip.paddingWithArrowHorizontal
                    )
                }
            }
        }
    }

    private fun getValidateWidth(with: Int) =
        with < (ScreenUtils.getScreenWidth() - padding * 2) - 100

    private fun getDisplayWidth(context: Context) =
        context.resources.displayMetrics.widthPixels

    private fun getDisplayMaxWidthForContent(context: Context) =
        getDisplayWidth(context) - context.resources.
        getDimensionPixelSize(R.dimen.andes_tooltip_padding_with_arrow_horizontal) * 2
}
