package com.mercadolibre.android.andesui.pageIndicator

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CirclePagerIndicatorDecoration : RecyclerView.ItemDecoration() {
    private val colorActive = Color.parseColor("#009EE3")
    private val colorInactive = Color.parseColor("#FFC7C7C7")

    /**
     * Height of the space the indicator takes up at the bottom of the view.
     */
    private val mIndicatorHeight = (DP * 8).toInt()

    /**
     * withTitle
     */
    private var title: String? = null

    /**
     * Indicator stroke width.
     */
    private val mIndicatorStrokeWidth = DP * 2

    /**
     * Dots size.
     */
    private val mIndicatorDotsLength = DP * 5

    /**
     * Padding between Dots.
     */
    private val mIndicatorDotsPadding = DP * 5

    /**
     * Padding between Dots.
     */
    private var activePosition = 0

    /**
     * total items.
     */
    private var itemsTotal = 0

    /**
     * total items.
     */
    private var leftLast: Float = 0.0f

    /**
     * Some more natural animation interpolation
     */
    private val mInterpolator = AccelerateDecelerateInterpolator()

    private val mPaint = Paint()

    init {
        mPaint.strokeWidth = mIndicatorStrokeWidth
        mPaint.style = Paint.Style.FILL_AND_STROKE
        mPaint.isAntiAlias = true
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val itemCount = parent.adapter!!.itemCount
        itemsTotal = calculateDotsTotal(itemCount)

        // center horizontally, calculate width and subtract half from center
        val totalLength = mIndicatorDotsLength * itemsTotal
        val paddingBetweenItems = Math.max(0, itemsTotal - 1) * mIndicatorDotsPadding
        val indicatorTotalWidth = totalLength + paddingBetweenItems
        val indicatorStartX = if (title.isNullOrEmpty()) {
            (parent.width - indicatorTotalWidth) / 2f
        } else {
            (parent.width - indicatorTotalWidth) - 20f
        }

        // center vertically in the allotted space
        val indicatorPosY = if (title.isNullOrEmpty()) {
            parent.height - mIndicatorHeight / 2f
        } else {
            40f
        }//40f //parent.height - mIndicatorHeight / 2f

        drawInactiveIndicators(c, indicatorStartX, indicatorPosY)

        // find active page (which should be highlighted)
        val layoutManager = parent.layoutManager as LinearLayoutManager?
        activePosition = layoutManager!!.findFirstVisibleItemPosition()
        if (activePosition == RecyclerView.NO_POSITION) {
            return
        }

        // find offset of active page (if the user is scrolling)
        val activeChild = layoutManager.findViewByPosition(activePosition)
        val left = activeChild!!.left
        val width = activeChild.width
        val right = activeChild.right

        // on swipe the active item will be positioned from [-width, 0]
        // interpolate offset for smooth animation
        val progress = mInterpolator.getInterpolation(left * -1 / width.toFloat())
        val anteUltima = itemCount - 1

        if (itemCount > MAX_DOTS) {
            when (activePosition) {
                in 0..2 -> {
                    drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress)
                }
                in 3..4 -> {
                    drawHighlights(c, indicatorStartX, indicatorPosY, 3, progress)
                }
                5 -> {
                    drawHighlights(c, indicatorStartX, indicatorPosY, 4, progress)
                }
            }
        }
        //drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress)
    }

    private fun drawInactiveIndicators(c: Canvas, indicatorStartX: Float, indicatorPosY: Float) {
        mPaint.color = colorInactive

        // width of item indicator including padding
        val itemWidth = mIndicatorDotsLength + mIndicatorDotsPadding

        var start = indicatorStartX
        for (i in 0 until itemsTotal) {

            c.drawCircle(start, indicatorPosY, mIndicatorDotsLength / 2f, mPaint)

            start += itemWidth
        }
    }

    private fun drawHighlights(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int, progress: Float
    ) {
        mPaint.color = colorActive

        // width of item indicator including padding
        val itemWidth = mIndicatorDotsLength + mIndicatorDotsPadding

        if (progress == 0f) {
            // no swipe, draw a normal indicator
            val highlightStart = indicatorStartX + itemWidth * highlightPosition

            c.drawCircle(highlightStart, indicatorPosY, mIndicatorDotsLength / 2f, mPaint)

        } else {
            val highlightStart = indicatorStartX + itemWidth * highlightPosition
            // calculate partial highlight
            val partialLength = mIndicatorDotsLength * progress + mIndicatorDotsPadding * progress
            val positionNext = highlightStart + partialLength

            c.drawCircle(positionNext, indicatorPosY, mIndicatorDotsLength / 2f, mPaint)
        }
    }

    fun withTitle(title: String?) {
        this.title = title
    }

    private fun calculateDotsTotal(itemCount: Int): Int {
        return if (itemCount > MAX_DOTS) {
            MAX_DOTS
        } else if (itemCount in 3..4) {
            3
        } else {
            itemCount
        }
    }

    companion object {
        private val DP = Resources.getSystem().displayMetrics.density
        private const val MAX_DOTS = 5
    }
}