package com.mercadolibre.android.andesui.modal.full.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesModalBaseFullLayoutBinding
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalFullContentVariation
import com.mercadolibre.android.andesui.modal.full.builder.ModalFullAction
import com.mercadolibre.android.andesui.modal.full.factory.AndesModalFullDefaultConfig
import com.mercadolibre.android.andesui.modal.full.factory.AndesModalFullDefaultConfigFactory
import com.mercadolibre.android.andesui.modal.full.factory.AndesModalFullDefaultFragmentArguments
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderStatus
import com.mercadolibre.android.andesui.utils.setConstraints

/**
 * Default variant of the full modal.
 * The public empty constructor is only available for OS requirements, please avoid using it.
 * To instantiate please use the builder present in the [com.mercadolibre.android.andesui.modal.AndesModal]
 */
class AndesModalFullDefaultFragment :
    AndesModalBaseFragment<AndesModalBaseFullLayoutBinding>(R.layout.andes_modal_base_full_layout) {

    private var wasTitleVisible = true
    private var fragmentArguments = AndesModalFullDefaultFragmentArguments()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = AndesModalBaseFullLayoutBinding.inflate(inflater, container, false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        handleContent()
        return view
    }

    private fun handleContent() {
        val configuration = AndesModalFullDefaultConfigFactory.create(fragmentArguments)

        setupHeaderFixed(configuration)
        setupContentVariation(configuration)
        setupContentBody(configuration)
        setupScrollListener(configuration)
        setupButtonGroup(configuration)
        setupDismissibleAction()
        setupOnDismissCallback(configuration)
        setupOnModalShowCallback(configuration)
    }

    private fun setupScrollListener(config: AndesModalFullDefaultConfig) {
        binding.scrollView.setScrollViewListener(
            config.scrollListener?.invoke { scrollY ->
                if (config.contentVariation != AndesModalFullContentVariation.NONE) {
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

    private fun setupHeaderFixed(config: AndesModalFullDefaultConfig) {
        if (config.isHeaderFixed) {
            binding.scrollView.setHeaderId(binding.headerType.id)
        }
    }

    private fun isTitleViewVisible(scrollY: Int) = scrollY < binding.title.bottom

    private fun setupContentBody(config: AndesModalFullDefaultConfig) {
        binding.headerType.apply {
            isTextCentered = config.isTitleTextCentered
            headerType = config.headerType
            headerTitle = config.content?.title
            textStatus = config.headerTextStatus
        }
        binding.title.apply {
            text = config.content?.title
            visibility = config.contentVariation.variation.getTitleVisibility()
        }
        binding.subTitle.apply {
            text = config.content?.subtitle
            textAlignment = config.contentVariation.variation.getSubtitleTextAlignment()
        }
        binding.imageHeader.apply {
            imageDrawable = config.content?.assetDrawable
            contentDescription = config.content?.assetContentDescription.orEmpty()
            config.content?.suspendedDrawable?.let {
                setDrawableSuspended {
                    config.content.suspendedDrawable.invoke()
                }
            }
        }
        binding.container.setConstraints {
            setVerticalBias(
                binding.containerBody.id,
                config.contentVariation.variation.getVerticalBias()
            )
        }
    }

    private fun setupContentVariation(config: AndesModalFullDefaultConfig) {
        binding.imageHeader.contentVariation = config.contentVariation.variation
    }

    private fun setupDismissibleAction() {
        binding.headerType.setCloseCallback {
            dismiss()
        }
    }

    private fun setupButtonGroup(config: AndesModalFullDefaultConfig) {
        val buttonGroupData = config.buttonGroupCreator?.create(this)
        buttonGroupData?.buttonGroup?.let {
            it.id = R.id.andes_modal_button_group_id
            it.layoutParams = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            val contentButtonGroup = if (config.isButtonGroupFixed) {
                binding.contentButtonGroupFixed
            } else {
                binding.contentButtonGroup
            }
            contentButtonGroup.apply {
                visibility = View.VISIBLE
                addView(it)
            }
        }
    }

    private fun setupOnDismissCallback(config: AndesModalFullDefaultConfig) {
        onDismissCallback = config.onDismissCallback
    }

    private fun setupOnModalShowCallback(config: AndesModalFullDefaultConfig) {
        onModalShowCallback = config.onModalShowCallback
    }

    companion object {

        /**
         * Returns a new instance of [AndesModalFullDefaultFragment]
         */
        @Suppress("LongParameterList")
        fun newInstance(
            isDismissible: Boolean,
            isButtonGroupFixed: Boolean,
            buttonGroupCreator: AndesButtonGroupCreator?,
            onDismissCallback: ModalFullAction?,
            onModalShowCallback: ModalFullAction?,
            contentVariation: AndesModalFullContentVariation,
            isHeaderFixed: Boolean,
            content: AndesModalContent
        ): AndesModalFullDefaultFragment {
            return AndesModalFullDefaultFragment().apply {
                fragmentArguments = AndesModalFullDefaultFragmentArguments(
                    isDismissible,
                    isButtonGroupFixed,
                    buttonGroupCreator,
                    onDismissCallback,
                    onModalShowCallback,
                    contentVariation,
                    isHeaderFixed,
                    content
                )
            }
        }
    }
}
