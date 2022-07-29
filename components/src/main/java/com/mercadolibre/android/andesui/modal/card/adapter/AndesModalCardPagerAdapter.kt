package com.mercadolibre.android.andesui.modal.card.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.mercadolibre.android.andesui.databinding.AndesModalSingleContentLayoutBinding
import com.mercadolibre.android.andesui.modal.card.configfactory.AndesModalCardCarouselConfig
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderStatus
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderType
import com.mercadolibre.android.andesui.modal.views.AndesModalImageComponent

internal class AndesModalCardPagerAdapter(
    private val views: List<AndesModalContent>,
    private val config: AndesModalCardCarouselConfig
) : PagerAdapter() {

    override fun getCount() = views.size

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as? View)
    }

    override fun isViewFromObject(view: View, other: Any) = view == other

    override fun instantiateItem(parent: ViewGroup, position: Int): View {
        val viewBinding = AndesModalSingleContentLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val holder = AndesModalCarouselView(viewBinding)
        holder.bind(views[position])
        val view = holder.getView()
        parent.addView(view)
        return view
    }

    internal inner class AndesModalCarouselView(private val binding: AndesModalSingleContentLayoutBinding) {

        private val SINGLE_LINE = 1
        private var initialTitleLineCount = 0
        private var wasTitleVisible = true

        fun bind(content: AndesModalContent) {
            setupContentBody(content)
            setupTitleLineCount()
            setupHeader(content)
            setupScrollListener()
        }

        fun getView() = binding.root

        private fun setupContentBody(content: AndesModalContent) {
            binding.apply {
                title.text = content.title
                subTitle.text = content.subtitle
                setupImage(content, imageHeader)
            }
        }

        private fun setupTitleLineCount() {
            binding.title.post {
                initialTitleLineCount = binding.title.lineCount
                binding.headerComponent.isTextCentered = initialTitleLineCount == SINGLE_LINE
            }
        }

        private fun setupHeader(content: AndesModalContent) {
            if (config.isHeaderFixed) {
                binding.scrollView.setHeaderId(binding.headerComponent.id)
                binding.headerComponent.apply {
                    textStatus = AndesModalFullHeaderStatus.COLLAPSED
                    headerType = AndesModalFullHeaderType.ONLY_TITLE
                    headerTitle = content.title
                    animateHeaderVisibility(View.GONE)
                }
            }
        }

        private fun setupScrollListener() {
            binding.scrollView.setScrollViewListener(
                config.scrollListener?.invoke { scrollY ->
                    setupHeaderTitleAnimation(scrollY)
                }
            )
        }

        private fun setupHeaderTitleAnimation(scrollY: Int) {
            val isTitleViewVisible = isTitleViewVisible(scrollY)
            if (isTitleViewVisible && !wasTitleVisible) {
                animateFixedTitleExit()
            } else if (!isTitleViewVisible && wasTitleVisible) {
                animateFixedTitleEntrance()
            }
            wasTitleVisible = isTitleViewVisible
        }

        private fun isTitleViewVisible(scrollY: Int) = scrollY < binding.title.bottom

        private fun animateFixedTitleEntrance() {
            binding.headerComponent.apply {
                visibility = View.VISIBLE
                animateHeaderVisibility(View.VISIBLE)
            }
        }

        private fun animateFixedTitleExit() {
            binding.headerComponent.apply {
                animateHeaderVisibility(View.GONE) {
                    visibility = View.GONE
                }
            }
        }

        private fun setupImage(content: AndesModalContent, imageHeader: AndesModalImageComponent) {
            imageHeader.apply {
                imageDrawable = content.assetDrawable
                contentVariation = config.contentVariation.variation
                contentDescription = content.assetContentDescription
                content.suspendedDrawable?.let {
                    setDrawableSuspended {
                        content.suspendedDrawable.invoke()
                    }
                }
            }
        }
    }
}
