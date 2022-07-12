package com.mercadolibre.android.andesui.utils

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.modal.full.adapter.AndesModalFullPagerAdapter
import com.mercadolibre.android.andesui.modal.full.fragment.AndesModalBaseFragment
import com.mercadolibre.android.andesui.modal.full.fragment.AndesModalFullCarouselFragment
import com.mercadolibre.android.andesui.modal.views.AndesModalHeaderTypeComponent
import com.mercadolibre.android.andesui.modal.views.AndesModalImageComponent
import com.mercadolibre.android.andesui.stickyscrollview.AndesStickyScrollView
import com.mercadolibre.android.andesui.textview.AndesTextView

internal fun AndesModalBaseFragment<*>.getBannerComponent(): View? =
    getImageComponent()?.findViewById(R.id.banner)

internal fun AndesModalBaseFragment<*>.getThumbnailComponent(): View? =
    getImageComponent()?.findViewById(R.id.thumbnail)

internal fun AndesModalBaseFragment<*>.getSubtitleComponent(): AndesTextView? =
    view?.findViewById(R.id.sub_title)

internal fun AndesModalBaseFragment<*>.getCustomViewComponent(): View? =
    (view?.findViewById(R.id.custom_view_container) as ViewGroup).getChildAt(0)

internal fun AndesModalBaseFragment<*>.getButtonGroupComponent(): AndesButtonGroup? =
    view?.findViewById(R.id.andes_modal_button_group_id)

internal fun AndesModalBaseFragment<*>.getCloseButtonComponent(): View? =
    view?.findViewById(R.id.andes_modal_close_button)

internal fun AndesModalBaseFragment<*>.getImageComponent(): AndesModalImageComponent? =
    view?.findViewById(R.id.image_header)

internal fun AndesModalBaseFragment<*>.getTitleComponent(): AndesTextView? =
    getHeaderComponent()?.findViewById(R.id.title)

internal fun AndesModalBaseFragment<*>.getHeaderComponent(): AndesModalHeaderTypeComponent? =
    view?.findViewById(R.id.header_type)

internal fun AndesModalBaseFragment<*>.getCloseButtonView(): ImageView? {
    val headerType = view?.findViewById<AndesModalHeaderTypeComponent>(R.id.header_type)
    return headerType?.findViewById(R.id.closeImageview)
}


internal fun AndesModalBaseFragment<*>.scrollToPosition(newPosition: Int) {
    val scroll = getScrollComponent()
    scroll?.scrollTo(0, newPosition)
}

internal fun AndesModalBaseFragment<*>.getScrollComponent(): AndesStickyScrollView? =
    view?.findViewById(R.id.scroll_view)

internal fun AndesModalBaseFragment<*>.getViewPagerComponent(): ViewPager2? =
    view?.findViewById(R.id.view_pager)

internal fun AndesModalFullCarouselFragment.getViewHolderForPosition(pagePosition: Int): View {
    val recyclerView = getViewPagerComponent()?.getChildAt(0) as RecyclerView
    val viewHolder =
        recyclerView.findViewHolderForAdapterPosition(pagePosition) as AndesModalFullPagerAdapter.AndesModalFullViewHolder
    return viewHolder.itemView
}
