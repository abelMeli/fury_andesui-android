package com.mercadolibre.android.andesui.pageviewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.android.andesui.databinding.AndesModalSingleContentLayoutBinding
import com.mercadolibre.android.andesui.modal.card.configfactory.AndesModalCardCarouselConfig
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderStatus
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderType
import com.mercadolibre.android.andesui.modal.views.AndesModalImageComponent

internal class AndesModalPagerAdapter(
    private val views: List<AndesModalContent>,
    private val config: AndesModalCardCarouselConfig
) : RecyclerView.Adapter<AndesModalPagerAdapter.AndesModalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AndesModalViewHolder {
        val viewBinding = AndesModalSingleContentLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AndesModalViewHolder(viewBinding)
    }

    override fun onBindViewHolder(andesModalViewHolder: AndesModalViewHolder, position: Int) {
        andesModalViewHolder.bind(views[position])
    }

    override fun getItemCount() = views.size

    inner class AndesModalViewHolder(private val binding: AndesModalSingleContentLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val SINGLE_LINE = 1
        private var initialTitleLineCount = 0
        private var wasTitleVisible = true

        fun bind(content: AndesModalContent) {
            setupContentBody(content)
            setupTitleLineCount()
            setupHeader(content)
            setupScrollListener()
        }

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
