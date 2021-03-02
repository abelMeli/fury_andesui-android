package com.mercadolibre.android.andesui.tooltip.location

import android.view.View

data class AndesTooltipArrowPoint(val x: Float, val y: Float)
data class AndesTooltipPadding(val left: Int, val top: Int, val right: Int, val bottom: Int)

private const val ARROW_POINTING_DOWN = 0F
private const val ARROW_POINTING_UP = 180F
private const val ARROW_POINTING_RIGHT = -90F
private const val ARROW_POINTING_LEFT = 90F

private val verticalPriorityList =
        listOf(
                AndesTooltipLocation.TOP, AndesTooltipLocation.BOTTOM,
                AndesTooltipLocation.LEFT, AndesTooltipLocation.RIGHT
        )
private val horizontalPriorityList =
        listOf(
                AndesTooltipLocation.LEFT, AndesTooltipLocation.RIGHT,
                AndesTooltipLocation.TOP, AndesTooltipLocation.BOTTOM
        )

sealed class AndesTooltipLocationConfig(
    val mLocation: AndesTooltipLocation,
    val otherLocationsAttempts: List<AndesTooltipLocation>
) {
    protected lateinit var arrowPositionInSide: ArrowPositionId
    abstract fun buildTooltip(target: View)
    abstract fun canBuildTooltipInRequiredLocation(target: View): Boolean
    abstract fun iterateOtherLocations(target: View): Boolean
    abstract fun getTooltipPadding(): AndesTooltipPadding
    abstract fun getArrowPoint(): AndesTooltipArrowPoint
    abstract fun getArrowRotation(): Float
}

class TopAndesTooltipLocationConfig(private val andesTooltip: AndesTooltipLocationInterface) : AndesTooltipLocationConfig(
        mLocation = AndesTooltipLocation.TOP,
        otherLocationsAttempts = verticalPriorityList.filterNot { it == AndesTooltipLocation.TOP }
) {
    override fun buildTooltip(target: View) {
        andesTooltip.run {
            val arrowData = getTooltipXOff(target, this)
            arrowPositionInSide = arrowData.positionInSide
            val xOff = arrowData.point
            val yOff = -tooltipMeasuredHeight - target.measuredHeight
            showDropDown(target, xOff, yOff, this@TopAndesTooltipLocationConfig)
        }
    }

    override fun canBuildTooltipInRequiredLocation(target: View): Boolean {
        andesTooltip.run {
            if (mLocation.getSpaceConditionByLocation().invoke(this, target)) {
                buildTooltip(target)
                return true
            }
            return false
        }
    }

    override fun iterateOtherLocations(target: View): Boolean {
        andesTooltip.run {
            otherLocationsAttempts.forEach { location ->
                if (location.getSpaceConditionByLocation().invoke(this, target)) {
                    getAndesTooltipLocationConfig(this, location).buildTooltip(target)
                    return true
                }
            }
            return false
        }
    }

    override fun getTooltipPadding(): AndesTooltipPadding {
        andesTooltip.apply {
            val paddingSize = paddingWithArrow
            val elevation = elevation
            return AndesTooltipPadding(elevation, elevation, elevation, paddingSize)
        }
    }

    override fun getArrowPoint(): AndesTooltipArrowPoint {
        andesTooltip.run {
            val arrowLocation: AndesTooltipArrowLocation = getAndesTooltipArrowLocation(
                    tooltipSideId = ArrowPositionId.BOTTOM,
                    positionInSideId = arrowPositionInSide
            )
            return AndesTooltipArrowPoint(
                    x = arrowLocation.getArrowPositionX(this),
                    y = arrowLocation.getArrowPositionY(this)
            )
        }
    }

    override fun getArrowRotation() = ARROW_POINTING_DOWN
}

class BottomAndesTooltipLocationConfig(private val andesTooltip: AndesTooltipLocationInterface) : AndesTooltipLocationConfig(
        mLocation = AndesTooltipLocation.BOTTOM,
        otherLocationsAttempts = verticalPriorityList.filterNot { it == AndesTooltipLocation.BOTTOM }
) {
    override fun buildTooltip(target: View) {
        andesTooltip.run {
            val arrowData = getTooltipXOff(target, this)
            arrowPositionInSide = arrowData.positionInSide
            val xOff = arrowData.point
            val yOff = 0
            showDropDown(target, xOff, yOff, this@BottomAndesTooltipLocationConfig)
        }
    }
    override fun canBuildTooltipInRequiredLocation(target: View): Boolean {
        andesTooltip.run {
            if (mLocation.getSpaceConditionByLocation().invoke(this, target)) {
                buildTooltip(target)
                return true
            }
            return false
        }
    }

    override fun iterateOtherLocations(target: View): Boolean {
        andesTooltip.run {
            otherLocationsAttempts.forEach { location ->
                if (location.getSpaceConditionByLocation().invoke(this, target)) {
                    getAndesTooltipLocationConfig(this, location).buildTooltip(target)
                    return true
                }
            }
            return false
        }
    }

    override fun getTooltipPadding(): AndesTooltipPadding {
        andesTooltip.apply {
            val paddingSize = paddingWithArrow
            val elevation = elevation
            return AndesTooltipPadding(elevation, paddingSize, elevation, elevation)
        }
    }

    override fun getArrowPoint(): AndesTooltipArrowPoint {
        andesTooltip.run {
            val arrowLocation: AndesTooltipArrowLocation = getAndesTooltipArrowLocation(
                    tooltipSideId = ArrowPositionId.TOP,
                    positionInSideId = arrowPositionInSide
            )
            return AndesTooltipArrowPoint(
                    x = arrowLocation.getArrowPositionX(this),
                    y = arrowLocation.getArrowPositionY(this)
            )
        }
    }

    override fun getArrowRotation() = ARROW_POINTING_UP
}

class LeftAndesTooltipLocationConfig(private val andesTooltip: AndesTooltipLocationInterface) : AndesTooltipLocationConfig(
        mLocation = AndesTooltipLocation.LEFT,
        otherLocationsAttempts = horizontalPriorityList.filterNot { it == AndesTooltipLocation.LEFT }
) {
    override fun buildTooltip(target: View) {
        andesTooltip.run {
            val arrowData = getTooltipYOff(target, this)
            arrowPositionInSide = arrowData.positionInSide
            val yOff = arrowData.point
            val xOff = -(tooltipMeasuredWidth)
            showDropDown(target, xOff, yOff, this@LeftAndesTooltipLocationConfig)
        }
    }
    override fun canBuildTooltipInRequiredLocation(target: View): Boolean {
        andesTooltip.run {
            if (mLocation.getSpaceConditionByLocation().invoke(this, target)) {
                buildTooltip(target)
                return true
            }
            return false
        }
    }

    override fun iterateOtherLocations(target: View): Boolean {
        andesTooltip.run {
            otherLocationsAttempts.forEach { location ->
                if (location.getSpaceConditionByLocation().invoke(this, target)) {
                    getAndesTooltipLocationConfig(this, location).buildTooltip(target)
                    return true
                }
            }
            return false
        }
    }

    override fun getTooltipPadding(): AndesTooltipPadding {
        andesTooltip.apply {
            val paddingSize = paddingWithArrow
            val elevation = elevation
            return AndesTooltipPadding(elevation, elevation, paddingSize, elevation)
        }
    }

    override fun getArrowPoint(): AndesTooltipArrowPoint {
        andesTooltip.run {
            val arrowLocation: AndesTooltipArrowLocation = getAndesTooltipArrowLocation(
                    tooltipSideId = ArrowPositionId.RIGHT,
                    positionInSideId = arrowPositionInSide
            )
            return AndesTooltipArrowPoint(
                    x = arrowLocation.getArrowPositionX(this),
                    y = arrowLocation.getArrowPositionY(this)
            )
        }
    }

    override fun getArrowRotation() = ARROW_POINTING_RIGHT
}

class RightAndesTooltipLocationConfig(private val andesTooltip: AndesTooltipLocationInterface) : AndesTooltipLocationConfig(
        mLocation = AndesTooltipLocation.RIGHT,
        otherLocationsAttempts = horizontalPriorityList.filterNot { it == AndesTooltipLocation.RIGHT }
) {
    override fun buildTooltip(target: View) {
        andesTooltip.run {
            val arrowData = getTooltipYOff(target, this)
            arrowPositionInSide = arrowData.positionInSide
            val yOff = arrowData.point
            val xOff = target.measuredWidth
            showDropDown(target, xOff, yOff, this@RightAndesTooltipLocationConfig)
        }
    }

    override fun canBuildTooltipInRequiredLocation(target: View): Boolean {
        andesTooltip.run {
            if (mLocation.getSpaceConditionByLocation().invoke(this, target)) {
                buildTooltip(target)
                return true
            }
            return false
        }
    }

    override fun iterateOtherLocations(target: View): Boolean {
        andesTooltip.run {
            otherLocationsAttempts.forEach { location ->
                if (location.getSpaceConditionByLocation().invoke(this, target)) {
                    getAndesTooltipLocationConfig(this, location).buildTooltip(target)
                    return true
                }
            }
            return false
        }
    }

    override fun getTooltipPadding(): AndesTooltipPadding {
        andesTooltip.apply {
            val paddingSize = paddingWithArrow
            val elevation = elevation
            return AndesTooltipPadding(paddingSize, elevation, elevation, elevation)
        }
    }

    override fun getArrowPoint(): AndesTooltipArrowPoint {
        andesTooltip.run {
            val arrowLocation: AndesTooltipArrowLocation = getAndesTooltipArrowLocation(
                    tooltipSideId = ArrowPositionId.LEFT,
                    positionInSideId = arrowPositionInSide
            )
            return AndesTooltipArrowPoint(
                    x = arrowLocation.getArrowPositionX(this),
                    y = arrowLocation.getArrowPositionY(this)
            )
        }
    }

    override fun getArrowRotation() = ARROW_POINTING_LEFT
}

fun getAndesTooltipLocationConfig(tooltip: AndesTooltipLocationInterface, location: AndesTooltipLocation): AndesTooltipLocationConfig {
    return when (location) {
        AndesTooltipLocation.TOP -> TopAndesTooltipLocationConfig(tooltip)
        AndesTooltipLocation.BOTTOM -> BottomAndesTooltipLocationConfig(tooltip)
        AndesTooltipLocation.LEFT -> LeftAndesTooltipLocationConfig(tooltip)
        AndesTooltipLocation.RIGHT -> RightAndesTooltipLocationConfig(tooltip)
    }
}
