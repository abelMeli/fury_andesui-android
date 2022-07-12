package com.mercadolibre.android.andesui.stickyscrollview.listener

internal interface AndesStickyScrollListener {
    fun onScrollChanged(
        mScrollX: Int,
        mScrollY: Int,
        oldX: Int,
        oldY: Int,
        isHeaderStickiedToTop: Boolean
    )

    fun onScrollStopped(stoppedY: Boolean)
}
