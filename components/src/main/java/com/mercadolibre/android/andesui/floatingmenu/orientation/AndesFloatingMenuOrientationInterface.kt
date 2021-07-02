package com.mercadolibre.android.andesui.floatingmenu.orientation

import android.content.Context
import android.view.View
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.getBottomSpace
import com.mercadolibre.android.andesui.utils.getLeftSpace
import com.mercadolibre.android.andesui.utils.getRightSpace
import com.mercadolibre.android.andesui.utils.getTopSpace

/**
 * Defines all orientation related properties that
 * an [com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu] needs to be drawn properly.
 */
internal interface AndesFloatingMenuOrientationInterface {
    /**
     * Returns the pixels offset size of the FloatingMenu depending on the [parentView] and the
     * current orientation.
     */
    fun getFloatingMenuOffset(parentView: View, size: Int): Int

    /**
     * Returns max available space in pixels for the current orientation
     */
    fun getMaxAvailableSpace(parentView: View): Int

    /**
     * Represents FloatingMenu orientation margin in pixels
     */
    fun getFloatingMenuPixelsMargin(context: Context): Int =
        context.resources.getDimensionPixelSize(R.dimen.andes_floatingmenu_yoffset)

    /**
     * Returns the opposite direction of the current orientation. E.g.: LEFT orientation will return
     * RIGHT orientation.
     */
    fun getOppositeOrientation(): AndesFloatingMenuOrientationInterface
}

internal class AndesLeftFloatingMenuOrientation : AndesFloatingMenuOrientationInterface {
    override fun getFloatingMenuOffset(parentView: View, size: Int): Int {
        return parentView.width - size
    }

    override fun getMaxAvailableSpace(parentView: View): Int {
        return parentView.getLeftSpace() - getFloatingMenuPixelsMargin(parentView.context)
    }

    override fun getOppositeOrientation(): AndesFloatingMenuOrientationInterface {
        return AndesFloatingMenuOrientation.Right.orientation
    }
}

internal class AndesRightFloatingMenuOrientation : AndesFloatingMenuOrientationInterface {

    companion object {
        private const val DEFAULT_HORIZONTAL_OFFSET = 0
    }

    override fun getFloatingMenuOffset(parentView: View, size: Int): Int {
        return DEFAULT_HORIZONTAL_OFFSET
    }

    override fun getMaxAvailableSpace(parentView: View): Int {
        return parentView.getRightSpace() - getFloatingMenuPixelsMargin(parentView.context)
    }

    override fun getOppositeOrientation(): AndesFloatingMenuOrientationInterface {
        return AndesFloatingMenuOrientation.Left.orientation
    }
}

internal class AndesBottomFloatingMenuOrientation : AndesFloatingMenuOrientationInterface {
    override fun getFloatingMenuOffset(parentView: View, size: Int): Int {
        return getFloatingMenuPixelsMargin(parentView.context)
    }

    override fun getMaxAvailableSpace(parentView: View): Int {
        return parentView.getBottomSpace() - getFloatingMenuPixelsMargin(parentView.context) * 2
    }

    override fun getOppositeOrientation(): AndesFloatingMenuOrientationInterface {
        return AndesFloatingMenuVerticalOrientation.Top.orientation
    }
}

internal class AndesTopFloatingMenuOrientation : AndesFloatingMenuOrientationInterface {
    override fun getFloatingMenuOffset(parentView: View, size: Int): Int {
        return -(size + getFloatingMenuPixelsMargin(parentView.context) + parentView.height)
    }

    override fun getMaxAvailableSpace(parentView: View): Int {
        return parentView.getTopSpace() - getFloatingMenuPixelsMargin(parentView.context) * 2
    }

    override fun getOppositeOrientation(): AndesFloatingMenuOrientationInterface {
        return AndesFloatingMenuVerticalOrientation.Bottom.orientation
    }
}
