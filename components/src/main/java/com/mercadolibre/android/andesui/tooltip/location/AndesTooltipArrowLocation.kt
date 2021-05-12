package com.mercadolibre.android.andesui.tooltip.location

import com.mercadolibre.android.andesui.tooltip.factory.Constants.GENERIC_X_VALUE

sealed class AndesTooltipArrowLocation {
    internal abstract fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float
    internal abstract fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float
}

internal object AndesTooltipArrowBottomLeft : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.arrowBorder.toFloat() + tooltip.elevation
    }

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.frameLayoutContainer.y + tooltip.radiusLayout.height + tooltip.elevation + tooltip.arrowImageInnerPadding
    }
}
internal object AndesTooltipArrowBottomMiddle : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float {
        return ((tooltip.frameLayoutContainer.width / 2) - (tooltip.arrowWidth / 2)).toFloat()
    }

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.frameLayoutContainer.y + tooltip.radiusLayout.height + tooltip.elevation + tooltip.arrowImageInnerPadding
    }
}
internal object AndesTooltipArrowBottomRight : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float {
        return (tooltip.frameLayoutContainer.width - tooltip.arrowBorder - tooltip.arrowWidth - tooltip.elevation).toFloat()
    }

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.frameLayoutContainer.y + tooltip.radiusLayout.height + tooltip.elevation + tooltip.arrowImageInnerPadding
    }
}

internal object AndesTooltipArrowTopLeft : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.arrowBorder.toFloat() + tooltip.elevation
    }

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.radiusLayout.y - tooltip.arrowBorder - tooltip.arrowImageInnerPadding / 2
    }
}
internal object AndesTooltipArrowTopMiddle : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float {
        return ((tooltip.frameLayoutContainer.width / 2) - (tooltip.arrowWidth / 2)).toFloat()
    }

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.radiusLayout.y - tooltip.arrowBorder - tooltip.arrowImageInnerPadding / 2
    }
}
internal object AndesTooltipArrowTopRight : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float {
        return (tooltip.frameLayoutContainer.width - tooltip.arrowBorder - tooltip.arrowWidth - tooltip.elevation).toFloat()
    }

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.radiusLayout.y - tooltip.arrowBorder - tooltip.arrowImageInnerPadding / 2
    }
}

internal object AndesTooltipArrowRightTop : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.radiusLayout.x + tooltip.radiusLayout.width - tooltip.elevation + tooltip.arrowImageInnerPadding
    }

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.arrowBorder.toFloat() + tooltip.elevation + (tooltip.arrowImageInnerPadding * 2)
    }
}
internal object AndesTooltipArrowRightMiddle : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.radiusLayout.x + tooltip.radiusLayout.width - tooltip.elevation + tooltip.arrowImageInnerPadding
    }

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float {
        return ((tooltip.frameLayoutContainer.height / 2) - (tooltip.arrowWidth / 2)).toFloat()
    }
}
internal object AndesTooltipArrowRightBottom : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.radiusLayout.x + tooltip.radiusLayout.width - tooltip.elevation + tooltip.arrowImageInnerPadding
    }

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float {
        return (tooltip.frameLayoutContainer.height - tooltip.arrowBorder - tooltip.arrowWidth - (tooltip.arrowImageInnerPadding * 2)).toFloat()
    }
}

internal object AndesTooltipArrowLeftTop : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.frameLayoutContainer.x + tooltip.elevation - tooltip.arrowImageInnerPadding - tooltip.arrowHeight / 2
    }

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.arrowBorder.toFloat() + tooltip.elevation + (tooltip.arrowImageInnerPadding * 2)
    }
}
internal object AndesTooltipArrowLeftMiddle : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.frameLayoutContainer.x + tooltip.elevation - tooltip.arrowImageInnerPadding - tooltip.arrowHeight / 2
    }

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float {
        return ((tooltip.frameLayoutContainer.height / 2) - (tooltip.arrowWidth / 2)).toFloat()
    }
}
internal object AndesTooltipArrowLeftBottom : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.frameLayoutContainer.x + tooltip.elevation - tooltip.arrowImageInnerPadding - tooltip.arrowHeight / 2
    }

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float {
        return (tooltip.frameLayoutContainer.height - tooltip.arrowBorder - tooltip.arrowWidth - (tooltip.arrowImageInnerPadding * 2)).toFloat()
    }
}

internal object AndesTooltipArrowTopFree : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float {
        return GENERIC_X_VALUE
    }

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.radiusLayout.y - tooltip.arrowBorder - tooltip.arrowImageInnerPadding / 2
    }
}

internal object AndesTooltipArrowBottomFree : AndesTooltipArrowLocation() {
    override fun getArrowPositionX(tooltip: AndesTooltipLocationInterface): Float {
        return GENERIC_X_VALUE
    }

    override fun getArrowPositionY(tooltip: AndesTooltipLocationInterface): Float {
        return tooltip.frameLayoutContainer.y + tooltip.radiusLayout.height + tooltip.elevation + tooltip.arrowImageInnerPadding
    }
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
