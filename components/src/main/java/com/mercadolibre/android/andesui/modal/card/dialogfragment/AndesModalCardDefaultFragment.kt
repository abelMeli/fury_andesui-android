package com.mercadolibre.android.andesui.modal.card.dialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesModalDefaultCardLayoutBinding
import com.mercadolibre.android.andesui.modal.card.builder.Action
import com.mercadolibre.android.andesui.modal.card.configfactory.AndesModalCardDefaultConfig
import com.mercadolibre.android.andesui.modal.card.configfactory.AndesModalCardDefaultConfigFactory
import com.mercadolibre.android.andesui.modal.card.configfactory.AndesModalCardDefaultFragmentArguments
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalCardContentVariation
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderStatus
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderType

/**
 * Default variant of the card modal.
 * The public empty constructor is only available for OS requirements, please avoid using it.
 * To instantiate please use the builder present in the [com.mercadolibre.android.andesui.modal.AndesModal]
 */
class AndesModalCardDefaultFragment : AndesDialogFragment() {

    private var _binding: AndesModalDefaultCardLayoutBinding? = null
    private val binding get() = _binding!!
    private var fragmentArguments = AndesModalCardDefaultFragmentArguments()
    private var wasTitleVisible = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AndesModalDefaultCardLayoutBinding.inflate(inflater, container, false)
        handleContent()
        return binding.root
    }

    private fun handleContent() {
        val config = AndesModalCardDefaultConfigFactory.create(fragmentArguments)

        setupRoundedCorners(config)
        setupHeader(config)
        setupContentVariation(config)
        setupContentBody(config)
        setupButtonGroup(config)
        setupDismissibleAction(config)
        setupOnDismissCallback(config)
        setupOnModalShowCallback(config)
        setupA11y()
        setupScrollListener(config)
    }

    private fun setupScrollListener(config: AndesModalCardDefaultConfig) {
        binding.singleContent.scrollView.setScrollViewListener(
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

    private fun isTitleViewVisible(scrollY: Int) = scrollY < binding.singleContent.title.bottom

    private fun animateFixedTitleEntrance() {
        binding.singleContent.headerComponent.apply {
            visibility = View.VISIBLE
            animateHeaderVisibility(View.VISIBLE)
        }
    }

    private fun animateFixedTitleExit() {
        binding.singleContent.headerComponent.apply {
            animateHeaderVisibility(View.GONE) {
                visibility = View.GONE
            }
        }
    }

    private fun setupOnModalShowCallback(config: AndesModalCardDefaultConfig) {
        onModalShowCallback = config.onModalShowCallback
    }

    private fun setupOnDismissCallback(config: AndesModalCardDefaultConfig) {
        onDismissCallback = config.onDismissCallback
    }

    private fun setupRoundedCorners(config: AndesModalCardDefaultConfig) {
        binding.singleContent.scrollView.outlineProvider = config.scrollViewOutlineProvider
        binding.singleContent.scrollView.clipToOutline = true
    }

    private fun setupHeader(config: AndesModalCardDefaultConfig) {
        if (config.isHeaderFixed) {
            binding.singleContent.scrollView.setHeaderId(binding.singleContent.headerComponent.id)
            binding.singleContent.headerComponent.apply {
                textStatus = AndesModalFullHeaderStatus.COLLAPSED
                headerType = AndesModalFullHeaderType.ONLY_TITLE
                headerTitle = config.content?.title
                animateHeaderVisibility(View.GONE)
            }
        }
    }

    private fun setupButtonGroup(config: AndesModalCardDefaultConfig) {
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
                binding.singleContent.contentButtonGroup
            }
            contentButtonGroup.apply {
                visibility = View.VISIBLE
                addView(it)
            }
        }
    }

    private fun setupContentVariation(config: AndesModalCardDefaultConfig) {
        binding.singleContent.imageHeader.contentVariation = config.contentVariation.variation
    }

    private fun setupContentBody(config: AndesModalCardDefaultConfig) {
        config.content?.let {
            binding.singleContent.title.text = it.title
            binding.singleContent.subTitle.text = it.subtitle
            binding.singleContent.title.post {
                val initialTitleLineCount = binding.singleContent.title.lineCount
                if (initialTitleLineCount == SINGLE_LINE) {
                    binding.singleContent.headerComponent.isTextCentered = true
                }
            }
            setupImage(it)
        }
    }

    private fun setupImage(content: AndesModalContent) {
        binding.singleContent.imageHeader.apply {
            imageDrawable = content.assetDrawable
            contentDescription = content.assetContentDescription
            content.suspendedDrawable?.let {
                setDrawableSuspended {
                    content.suspendedDrawable.invoke()
                }
            }
        }
    }

    private fun setupDismissibleAction(config: AndesModalCardDefaultConfig) {
        dialog?.setCanceledOnTouchOutside(config.isDismissible)
        isDismissible = config.isDismissible
        binding.andesModalCloseButton.setOnClickListener {
            dismiss()
        }
        binding.andesModalCloseButton.visibility = config.closeButtonVisibility
    }

    private fun setupA11y() {
        with(binding.andesModalCloseButton) {
            ViewCompat.replaceAccessibilityAction(
                this,
                AccessibilityActionCompat.ACTION_CLICK,
                getString(R.string.andes_modal_dismiss_content_description),
                null
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val SINGLE_LINE = 1

        /**
         * Returns a new instance of [AndesModalCardDefaultFragment]
         */
        @Suppress("LongParameterList")
        fun newInstance(
            isDismissible: Boolean,
            isButtonGroupFixed: Boolean,
            buttonGroupCreator: AndesButtonGroupCreator?,
            onDismissCallback: Action?,
            onModalShowCallback: Action?,
            contentVariation: AndesModalCardContentVariation,
            isHeaderFixed: Boolean,
            content: AndesModalContent
        ): AndesModalCardDefaultFragment {
            return AndesModalCardDefaultFragment().apply {
                fragmentArguments = AndesModalCardDefaultFragmentArguments(
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
