package com.mercadolibre.android.andesui.pageviewer

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesuiPageviewerDotBinding
import com.mercadolibre.android.andesui.databinding.AndesuiPageviewerLayoutBinding

/**
 * Page indicator component to be used paired with a [ViewPager] or a [ViewPager2].
 *
 * See the specs in [figma](https://www.figma.com/file/ma8IQUYi9IS8zc8C0rAzEB/Components-specifications?node-id=2373%3A262213)
 * (Page viewer)
 */
internal class PageIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        AndesuiPageviewerLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private val dots = mutableListOf<ImageView>()

    /**
     * Attaches the view pager to the page indicator.
     *
     * @param viewPager the viewPager where's being attached to.
     */
    fun attach(viewPager: ViewPager2, onPageSelectedCallback: ((position: Int) -> Unit)? = null) {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setSelected(position)
                onPageSelectedCallback?.invoke(position)
            }
        })
        val pagerAdapter = viewPager.adapter ?: return
        repeat(pagerAdapter.itemCount - 1) {
            val dot = createDot()
            addView(dot)
            addView(createSeparator())
            dots.add(dot)
        }

        val lastDot = createDot()
        addView(lastDot)
        dots.add(lastDot)

        setSelected(viewPager.currentItem)
    }

    /**
     * Attaches the view pager to the page indicator.
     *
     * @param viewPager the viewPager where's being attached to.
     */
    fun attach(viewPager: ViewPager, onPageSelectedCallback: ((position: Int) -> Unit)? = null) {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

            override fun onPageSelected(position: Int) {
                setSelected(position)
                onPageSelectedCallback?.invoke(position)
            }

            override fun onPageScrollStateChanged(state: Int) = Unit
        })
        val pagerAdapter = viewPager.adapter ?: return
        repeat(pagerAdapter.count - 1) {
            val dot = createDot()
            addView(dot)
            addView(createSeparator())
            dots.add(dot)
        }

        val lastDot = createDot()
        addView(lastDot)
        dots.add(lastDot)

        setSelected(viewPager.currentItem)
    }

    private fun createDot() =
        AndesuiPageviewerDotBinding.inflate(LayoutInflater.from(context), this, false).root

    private fun createSeparator() = View(context).apply {
        layoutParams = LayoutParams(
            context.resources.getDimensionPixelSize(R.dimen.andes_modal_page_indicator_separator),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setSelected(position: Int) {
        for (i in dots.indices) {
            if (i == position) {
                tintImageView(dots[i], SELECTED_COLOR)
            } else {
                tintImageView(dots[i], UNSELECTED_COLOR)
            }
        }
    }

    private fun tintImageView(view: ImageView, @ColorRes color: Int) {
        view.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
    }

    companion object {
        private val SELECTED_COLOR = R.color.andes_accent_color
        private val UNSELECTED_COLOR = R.color.andes_gray_100
    }
}
