package com.mercadolibre.android.andesui.stickyscrollview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.IdRes
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import com.mercadolibre.android.andesui.stickyscrollview.factory.AndesStickyScrollAttrs
import com.mercadolibre.android.andesui.stickyscrollview.factory.AndesStickyScrollAttrsParser
import com.mercadolibre.android.andesui.stickyscrollview.listener.AndesStickyScrollListener
import com.mercadolibre.android.andesui.stickyscrollview.utils.AndesStickyScrollViewUtils

internal class AndesStickyScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    private var andesStickyScrollAttrs: AndesStickyScrollAttrs
    private var andesStickyHeaderView: View? = null
    private var andesScrollViewListener: AndesStickyScrollListener? = null
    private var stickyHeaderInitialPosition = 0
    private var viewTreeObserverListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    private var isHeaderStickiedToTop: Boolean = false

    init {
        andesStickyScrollAttrs = AndesStickyScrollAttrsParser.parse(context, attrs)
        setupViewId()
    }

    /**
     * Sets the given [id] to instantiate the header to put sticky, inside the scrollview.
     *
     * @param id resource.
     */
    fun setHeaderId(@IdRes id: Int) {
        andesStickyScrollAttrs = andesStickyScrollAttrs.copy(headerRef = id)
        setupComponent()
    }

    /**
     * Sets listener to scroll view state changes.
     *
     * @param listener the events listener.
     */
    fun setScrollViewListener(listener: AndesStickyScrollListener?) {
        this.andesScrollViewListener = listener
    }

    private fun setupComponent() {
        if (andesStickyScrollAttrs.headerRef != View.NO_ID) {
            initializeHeaderView(andesStickyScrollAttrs.headerRef)
            if (viewTreeObserverListener == null) {
                viewTreeObserverListener = object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        setInitialHeaderPosition(andesStickyHeaderView?.top)
                        viewTreeObserver.removeOnGlobalLayoutListener(this)
                        viewTreeObserverListener = null
                    }
                }
                viewTreeObserver.addOnGlobalLayoutListener(viewTreeObserverListener)
            }
        }
    }

    /**
     * Initialize the view that was selected for sticky.
     */
    private fun initializeHeaderView(@IdRes id: Int) {
        andesStickyHeaderView = findViewById(id)
    }

    /**
     * We override this method in order to instantiate a view
     * because the user can pass by reference any view within the scroll view
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setupComponent()
    }

    /**
     * This method allows you to set the initial position of the view to be sticky within the scroll.
     */
    private fun setInitialHeaderPosition(headerTop: Int?) {
        if (headerTop == null) {
            stickyHeaderInitialPosition = 0
            return
        }
        stickyHeaderInitialPosition = headerTop
    }

    /**
     * This method allows you to validate if you are going to set the fixed view or not.
     */
    private fun setHeaderProperties(scrollY: Int) {
        isHeaderStickiedToTop = if (scrollY > stickyHeaderInitialPosition) {
            setHeaderAsSticky(scrollY - stickyHeaderInitialPosition)
        } else {
            setHeaderAsDefault()
        }
    }

    /**
     * This method allows to be called from layout when
     * this view should assign a size and position to each of its children.
     *
     * this is used so that if one of its children changes,
     * for example in the case of modifying the headers,
     * the sticky header is updated and does not become misaligned.
     */
    private fun recalculateHeaderLocation(headerTop: Int) {
        setInitialHeaderPosition(headerTop)
        setHeaderProperties(scrollY)
    }

    private fun setupViewId() {
        if (id == NO_ID) {
            id = View.generateViewId()
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        andesStickyHeaderView?.let {
            recalculateHeaderLocation(it.top)
        }
    }

    /**
     * This callback allows us to apply changes during scrolling
     * and allows us to notify the user when there is a change in the scroll.
     */
    override fun onScrollChanged(mScrollX: Int, mScrollY: Int, oldX: Int, oldY: Int) {
        super.onScrollChanged(mScrollX, mScrollY, oldX, oldY)
        setHeaderProperties(mScrollY)
        andesScrollViewListener?.onScrollChanged(
            mScrollX,
            mScrollY,
            oldX,
            oldY,
            isHeaderStickiedToTop
        )
    }

    /**
     * This callback allows us to apply changes during scrolling
     * and allows us to notify the user when there is a change in the scroll.
     */
    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        andesScrollViewListener?.onScrollStopped(clampedY)
    }

    /**
     * This method puts the view in its initial state.
     */
    private fun setHeaderAsDefault(): Boolean {
        andesStickyHeaderView?.let {
            it.translationY = DEFAULT_TRANSLATION
            AndesStickyScrollViewUtils.setTranslationZ(it, DEFAULT_VALUE)
            ViewCompat.setElevation(it, ELEVATION_NONE)
        }
        return false
    }

    /**
     * This method makes the view sticky
     */
    private fun setHeaderAsSticky(translationY: Int): Boolean {
        return andesStickyHeaderView?.let {
            it.translationY = translationY.toFloat()
            AndesStickyScrollViewUtils.setTranslationZ(it, STICKY_VALUE)
            ViewCompat.setElevation(it, ELEVATION_VIEW)
            true
        } ?: run {
            false
        }
    }

    companion object {
        private const val ELEVATION_NONE = 0f
        private const val DEFAULT_TRANSLATION = 0f
        private const val DEFAULT_VALUE = 0f
        private const val STICKY_VALUE = 1f
        private const val ELEVATION_VIEW = 14f
    }
}
