package com.mercadolibre.android.andesui.modal.full.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.mercadolibre.android.andesui.databinding.AndesModalBaseFullLayoutBinding
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalFullContentVariation
import com.mercadolibre.android.andesui.modal.full.factory.AndesModalFullCarouselConfig
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderStatus
import com.mercadolibre.android.andesui.modal.full.interfaces.AndesModalFullCloseListener
import com.mercadolibre.android.andesui.utils.setConstraints

internal class AndesModalFullPagerAdapter(
    private val views: List<AndesModalContent>,
    private val configuration: AndesModalFullCarouselConfig,
    private val listener: AndesModalFullCloseListener? = null
) : PagerAdapter() {

    private var wasTitleVisible = true

    override fun getCount() = views.size

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as? View)
    }

    override fun isViewFromObject(view: View, other: Any) = view == other

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val viewBinding = AndesModalBaseFullLayoutBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            false
        )

        val holder = AndesModalFullCarouselView(viewBinding)
        holder.bind(views[position])
        val view = holder.getView()
        container.addView(view)
        return view
    }

    internal inner class AndesModalFullCarouselView(private val binding: AndesModalBaseFullLayoutBinding) {
        fun bind(content: AndesModalContent) {
            setupContentBody(content)
            setupContentVariation()
            setupHeaderFixed()
            setupScrollListener()
            setupDismissibleAction()
        }

        fun getView() = binding.root

        private fun setupScrollListener() {
            binding.scrollView.setScrollViewListener(
                configuration.scrollListener?.invoke { scrollY ->
                    if (configuration.contentVariation != AndesModalFullContentVariation.NONE) {
                        setupHeaderTitleAnimation(scrollY)
                    } else {
                        setupHeaderTitleStatus(scrollY)
                    }
                }
            )
        }

        private fun setupHeaderTitleStatus(mScrollY: Int) {
            binding.headerType.textStatus = if (mScrollY == 0) {
                AndesModalFullHeaderStatus.EXPANDED
            } else {
                AndesModalFullHeaderStatus.COLLAPSED
            }
        }

        private fun setupHeaderTitleAnimation(scrollY: Int) {
            val isTitleViewVisible = isTitleViewVisible(scrollY)
            if (isTitleViewVisible && !wasTitleVisible) {
                binding.headerType.animateHeaderVisibility(View.GONE)
            } else if (!isTitleViewVisible && wasTitleVisible) {
                binding.headerType.animateHeaderVisibility(View.VISIBLE)
            }

            wasTitleVisible = isTitleViewVisible
        }

        private fun setupHeaderFixed() {
            if (configuration.isHeaderFixed) {
                binding.scrollView.setHeaderId(binding.headerType.id)
            }
        }

        private fun isTitleViewVisible(scrollY: Int) = scrollY < binding.title.bottom

        private fun setupContentBody(content: AndesModalContent) {
            binding.apply {
                headerType.apply {
                    isTextCentered = configuration.isTitleTextCentered
                    headerType = configuration.headerType
                    headerTitle = content.title
                    textStatus = configuration.headerTextStatus
                }
                title.apply {
                    text = content.title
                    visibility = configuration.contentVariation.variation.getTitleVisibility()
                }
                subTitle.apply {
                    text = content.subtitle
                    textAlignment = configuration.contentVariation.variation.getSubtitleTextAlignment()
                }
                imageHeader.apply {
                    imageDrawable = content.assetDrawable
                    contentDescription = content.assetContentDescription
                    content.suspendedDrawable?.let {
                        setDrawableSuspended {
                            content.suspendedDrawable.invoke()
                        }
                    }
                }
                container.setConstraints {
                    setVerticalBias(
                        containerBody.id,
                        configuration.contentVariation.variation.getVerticalBias()
                    )
                }
            }
        }

        private fun setupContentVariation() {
            binding.imageHeader.contentVariation = configuration.contentVariation.variation
        }

        private fun setupDismissibleAction() {
            binding.headerType.setCloseCallback {
                listener?.onCloseListener()
            }
        }
    }
}
