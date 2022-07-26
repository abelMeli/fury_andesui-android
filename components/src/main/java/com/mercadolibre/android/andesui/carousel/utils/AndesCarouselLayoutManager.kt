package com.mercadolibre.android.andesui.carousel.utils

import android.content.Context
import android.graphics.PointF
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * New layout manager for the Recycler view in andes carousel, with the ability of be set as infinite or finite.
 */
internal class AndesCarouselLayoutManager(
    context: Context,
    private val isAccessibilityOn: () -> Boolean
) : LinearLayoutManager(context, HORIZONTAL, false) {

    /**
     * isInfinite to become the recyclerview as finite or infinite.
     */
    var isInfinite: Boolean = false
    private var reachedRightLimit = false
    private var reachedLeftLimit = false

    override fun canScrollHorizontally() = true

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (!isInfinite) {
            super.onLayoutChildren(recycler, state)
            return
        }
        if (itemCount <= 0) {
            return
        }
        // If the current state of readiness, return directly
        if (state?.isPreLayout == true) {
            return
        }
        // Separate views into scrap caches in preparation for re-typesetting views
        recycler?.let {
            detachAndScrapAttachedViews(it)
            var actualWidth = 0
            for (i in 0 until itemCount) {
                // Initialization, filling in the view in the screen
                val itemView = it.getViewForPosition(i)
                addView(itemView)
                // Measuring the width and height of itemView
                measureChildWithMargins(itemView, 0, 0)
                var width = getDecoratedMeasuredWidth(itemView)
                val height = getDecoratedMeasuredHeight(itemView)
                // Layout according to the width of itemView
                layoutDecorated(itemView, actualWidth, 0, actualWidth + width, height)
                actualWidth += width
                // If the total width of the itemView currently laid out is larger than that
                // of RecyclerView, the layout is no longer done.
                if (actualWidth > getWidth()) {
                    break
                }
            }
        }
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        if (!isInfinite) {
            return super.scrollHorizontallyBy(dx, recycler, state)
        }
        // Fill itemView in order on both left and right sides when sliding horizontally
        val travel: Int = fill(dx, recycler)
        if (travel == 0) {
            return 0
        }

        // Sliding
        offsetChildrenHorizontal(-travel)

        // Recycling the invisible itemView
        recyclerHideView(dx, recycler)
        return travel
    }

    /**
     * When sliding left and right, fill.
     */
    private fun fill(offSet: Int, recycler: RecyclerView.Recycler): Int {
        var dx = offSet
        if (dx > 0) {
            // Scroll left
            val lastView = getChildAt(childCount - 1) ?: return 0
            val lastPos = getPosition(lastView)
            // The last itemView visible has completely slipped in and needs to be added.
            if (lastView.right < width) {
                var scrap: View? = null
                // Judge the index of the last itemView visible,
                // If it's the last, set the next itemView to the first, or set it to the next of the current index
                if (lastPos == itemCount - 1) {
                    reachedRightLimit = true
                    scrap = recycler.getViewForPosition(0)
                } else {
                    reachedRightLimit = false
                    scrap = recycler.getViewForPosition(lastPos + 1)
                }

                if (isAccessibilityOn.invoke() && reachedRightLimit) {
                    return 0
                }

                if (scrap == null) {
                    return dx
                }
                // Enter the new itemViewadd and measure and layout it
                addView(scrap)
                measureChildWithMargins(scrap, 0, 0)
                val width = getDecoratedMeasuredWidth(scrap)
                val height = getDecoratedMeasuredHeight(scrap)
                val dec = getLeftDecorationWidth(lastView)
                layoutDecorated(
                    scrap, lastView.right + dec, 0,
                    lastView.right + width + dec, height
                )
                return dx
            }
        } else {
            // Scroll to the right
            val firstView = getChildAt(FIRST_CHILD) ?: return NO_DX
            val firstPos = getPosition(firstView)
            if (firstView.left >= 0) {
                var scrap: View? = null
                if (firstPos == 0) {
                    reachedLeftLimit = true
                    scrap = recycler.getViewForPosition(itemCount - 1)
                } else {
                    reachedLeftLimit = false
                    scrap = recycler.getViewForPosition(firstPos - 1)
                }
                if (scrap == null) {
                    return 0
                }

                if (isAccessibilityOn.invoke() && reachedLeftLimit) {
                    return 0
                }

                addView(scrap, 0)
                measureChildWithMargins(scrap, 0, 0)
                val width = getDecoratedMeasuredWidth(scrap)
                val height = getDecoratedMeasuredHeight(scrap)
                val dec = getRightDecorationWidth(scrap)
                layoutDecorated(
                    scrap, firstView.left - width - dec, 0,
                    firstView.left - dec, height
                )
            }
        }
        return dx
    }

    override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
        if (!isInfinite) {
            return super.smoothScrollToPosition(recyclerView, state, position)
        }
        val smoothScroller: LinearSmoothScroller = object : LinearSmoothScroller(recyclerView?.context) {
            override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                return this@AndesCarouselLayoutManager
                    .computeScrollVectorForPosition(targetPosition)
            }
        }
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    /**
     * invisible view of recycling interface.
     */
    private fun recyclerHideView(dx: Int, recycler: RecyclerView.Recycler) {
        for (i in 0 until childCount) {
            val view = getChildAt(i) ?: continue
            if (dx > 0) {
                // Scroll left to remove views that are not in the content on the left.
                if (view.right < 0) {
                    removeAndRecycleView(view, recycler)
                }
            } else {
                // Scroll to the right to remove views that are not in the content on the right.
                if (view.left > width) {
                    removeAndRecycleView(view, recycler)
                }
            }
        }
    }

    companion object {
        const val FIRST_CHILD = 0
        const val NO_DX = 0
    }
}
