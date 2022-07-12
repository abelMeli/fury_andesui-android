package com.mercadolibre.android.andesui.utils

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.databinding.AndesModalCustomCardLayoutBinding
import com.mercadolibre.android.andesui.databinding.AndesModalDefaultCardLayoutBinding
import com.mercadolibre.android.andesui.modal.card.dialogfragment.AndesDialogFragment
import com.mercadolibre.android.andesui.modal.card.dialogfragment.AndesModalCardCarouselFragment
import com.mercadolibre.android.andesui.modal.card.dialogfragment.AndesModalCardDefaultFragment
import com.mercadolibre.android.andesui.modal.card.dialogfragment.AndesModalCardViewFragment
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupData
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.AndesModalInterface
import com.mercadolibre.android.andesui.modal.views.AndesModalHeaderTypeComponent
import com.mercadolibre.android.andesui.modal.views.AndesModalImageComponent
import com.mercadolibre.android.andesui.pageviewer.AndesModalPagerAdapter
import com.mercadolibre.android.andesui.stickyscrollview.AndesStickyScrollView
import com.mercadolibre.android.andesui.textview.AndesTextView

internal fun AndesDialogFragment.getBannerComponent(): View? {
    return getImageComponent()?.findViewById(R.id.banner)
}

internal fun AndesDialogFragment.getThumbnailComponent(): View? {
    return getImageComponent()?.findViewById(R.id.thumbnail)
}

internal fun AndesDialogFragment.getTitleComponent(): AndesTextView? {
    return if (this is AndesModalCardDefaultFragment) {
        view?.findViewById(R.id.title)
    } else null
}

internal fun AndesDialogFragment.getSubtitleComponent(): AndesTextView? {
    return if (this is AndesModalCardDefaultFragment) {
        view?.findViewById(R.id.sub_title)
    } else null
}

internal fun AndesDialogFragment.getButtonGroupComponent(): AndesButtonGroup? {
    return view?.findViewById(R.id.andes_modal_button_group_id)
}

internal fun AndesDialogFragment.getCloseButtonComponent(): View? {
    return view?.findViewById(R.id.andes_modal_close_button)
}

internal fun AndesDialogFragment.getViewPagerComponent(): ViewPager2? {
    return if (this is AndesModalCardCarouselFragment) {
        view?.findViewById(R.id.view_pager)
    } else null
}

internal fun AndesDialogFragment.getCustomViewComponent(): View? {
    return if (this is AndesModalCardViewFragment) {
        (view?.findViewById(R.id.custom_view_container) as ViewGroup)
            .getChildAt(0)
    } else null
}

internal fun AndesDialogFragment.getImageComponent(): AndesModalImageComponent? {
    return if (this is AndesModalCardDefaultFragment) {
        return view?.findViewById(R.id.image_header)
    } else null
}

internal fun provideContent(context: Context) =
    AndesModalContent("Title", "Subtitle", provideDrawable(context), "content description")

internal fun provideLargeContent(context: Context) =
    AndesModalContent(
        "Title", context.resources.getString(R.string.andes_lorem) +
                context.resources.getString(R.string.andes_lorem) +
                context.resources.getString(R.string.andes_lorem) +
                context.resources.getString(R.string.andes_lorem) +
                context.resources.getString(R.string.andes_lorem), provideDrawable(context)
    )

internal fun provideOtherContent(context: Context) =
    AndesModalContent("Title2", "Subtitle2", provideDrawable(context))

internal fun provideDrawable(context: Context) =
    ColorDrawable(context.resources.getColor(R.color.andes_feedback_color_negative))

internal fun provideButtonGroupCreator(context: Context, withMainAction: Boolean = false) = object : AndesButtonGroupCreator {
    override fun create(modalInterface: AndesModalInterface): AndesButtonGroupData {
        return AndesButtonGroupData(
            AndesButtonGroup(
                context,
                listOf(
                    AndesButton(context),
                    AndesButton(context)
                )
            ),
            0.takeIf { withMainAction }
        )

    }
}

internal fun provideAndesModalInterface() = object : AndesModalInterface {
    override fun dismiss() {
        // no-op
    }
}

internal fun AndesDialogFragment.getScrollComponent(): AndesStickyScrollView? {
    val viewRoot = view ?: return null
    when (this) {
        is AndesModalCardViewFragment -> {
            val binding = AndesModalCustomCardLayoutBinding.bind(viewRoot)
            return binding.customContent.scrollView
        }
        is AndesModalCardDefaultFragment -> {
            val binding = AndesModalDefaultCardLayoutBinding.bind(viewRoot)
            return binding.singleContent.scrollView
        }
    }
    return null
}

internal fun AndesDialogFragment.getHeaderComponent(): AndesModalHeaderTypeComponent? =
    view?.findViewById(R.id.header_component)

internal fun AndesModalHeaderTypeComponent.getTitleComponent(): AndesTextView =
    findViewById(R.id.title)

internal fun AndesModalCardCarouselFragment.getViewHolderForPosition(pagePosition: Int): View {
    val recyclerView = getViewPagerComponent()?.getChildAt(0) as RecyclerView
    val viewHolder =
        recyclerView.findViewHolderForAdapterPosition(pagePosition) as AndesModalPagerAdapter.AndesModalViewHolder
    return viewHolder.itemView
}