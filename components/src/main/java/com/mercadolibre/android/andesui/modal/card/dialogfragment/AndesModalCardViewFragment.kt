package com.mercadolibre.android.andesui.modal.card.dialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesModalCustomCardLayoutBinding
import com.mercadolibre.android.andesui.modal.card.builder.Action
import com.mercadolibre.android.andesui.modal.card.configfactory.AndesModalCardViewConfig
import com.mercadolibre.android.andesui.modal.card.configfactory.AndesModalCardViewConfigFactory
import com.mercadolibre.android.andesui.modal.card.configfactory.AndesModalCardViewFragmentArguments
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderStatus
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderType

/**
 * Custom view variant of the card modal.
 * This modal allows to create a dialog fragment with any custom view the dev requires.
 *
 * The public empty constructor is only available for OS requirements, please avoid using it.
 * To instantiate please use the builder present in the [com.mercadolibre.android.andesui.modal.AndesModal]
 */
class AndesModalCardViewFragment : AndesDialogFragment() {

    private var _binding: AndesModalCustomCardLayoutBinding? = null
    private val binding get() = _binding!!
    private var fragmentArguments = AndesModalCardViewFragmentArguments()
    private var wasTitleVisible = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AndesModalCustomCardLayoutBinding.inflate(inflater, container, false)
        handleContent()
        return binding.root
    }

    private fun handleContent() {
        val config = AndesModalCardViewConfigFactory.create(fragmentArguments)

        setupAccessibilityModalDescription(config)
        setupRoundedCorners(config)
        setupDismissibleAction(config)
        setupOnDismissCallback(config)
        setupOnModalShowCallback(config)
        setupButtonGroup(config)
        setupHeader(config)
        setupCustomView(config)
        setupTitle(config)
        setupScrollListener(config)
        setupA11y()
    }

    private fun setupAccessibilityModalDescription(config: AndesModalCardViewConfig) {
        modalDescription = config.modalDescription
    }

    private fun setupScrollListener(config: AndesModalCardViewConfig) {
        binding.customContent.scrollView.setScrollViewListener(
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

    private fun isTitleViewVisible(scrollY: Int) = scrollY < binding.customContent.title.bottom

    private fun animateFixedTitleEntrance() {
        binding.customContent.headerComponent.apply {
            visibility = View.VISIBLE
            animateHeaderVisibility(View.VISIBLE)
        }
    }

    private fun animateFixedTitleExit() {
        binding.customContent.headerComponent.apply {
            animateHeaderVisibility(View.GONE)
        }
    }

    private fun setupTitle(config: AndesModalCardViewConfig) {
        binding.customContent.title.visibility = config.titleVisibility
        binding.customContent.title.text = config.headerTitle
        binding.customContent.title.post {
            val initialTitleLineCount = binding.customContent.title.lineCount
            if (initialTitleLineCount == SINGLE_LINE) {
                binding.customContent.headerComponent.isTextCentered = true
            }
        }
    }

    private fun setupCustomView(config: AndesModalCardViewConfig) {
        config.customView?.let {
            // check if passed view is not already included in another layout
            if (it.parent != null) {
                (it.parent as ViewGroup).removeAllViews()
            }
            binding.customContent.customViewContainer.addView(it)
        }
    }

    private fun setupHeader(config: AndesModalCardViewConfig) {
        if (config.shouldSetupHeader) {
            binding.customContent.scrollView.setHeaderId(binding.customContent.headerComponent.id)
            binding.customContent.headerComponent.apply {
                textStatus = AndesModalFullHeaderStatus.COLLAPSED
                headerType = AndesModalFullHeaderType.ONLY_TITLE
                headerTitle = config.headerTitle
                animateHeaderVisibility(View.GONE)
            }
        }
    }

    private fun setupButtonGroup(config: AndesModalCardViewConfig) {
        val buttonGroupData = config.buttonGroupCreator?.create(this)
        binding.buttonGroupContainer.visibility = config.buttonGroupVisibility
        buttonGroupData?.buttonGroup?.let {
            it.id = R.id.andes_modal_button_group_id
            it.layoutParams = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            binding.buttonGroupContainer.addView(it)
        }
    }

    private fun setupOnModalShowCallback(config: AndesModalCardViewConfig) {
        onModalShowCallback = config.onModalShowCallback
    }

    private fun setupOnDismissCallback(config: AndesModalCardViewConfig) {
        onDismissCallback = config.onDismissCallback
    }

    private fun setupDismissibleAction(config: AndesModalCardViewConfig) {
        dialog?.setCanceledOnTouchOutside(config.isDismissible)
        isDismissible = config.isDismissible
        binding.andesModalCloseButton.setOnClickListener {
            dismiss()
        }
        binding.andesModalCloseButton.visibility = config.closeButtonVisibility
    }

    private fun setupRoundedCorners(config: AndesModalCardViewConfig) {
        binding.customContent.scrollView.outlineProvider = config.viewOutlineProvider
        binding.customContent.scrollView.clipToOutline = true
    }

    private fun setupA11y() {
        with(binding.andesModalCloseButton) {
            ViewCompat.replaceAccessibilityAction(
                this,
                AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK,
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
         * Returns a new instance of [AndesModalCardViewFragment]
         */
        @Suppress("LongParameterList")
        internal fun newInstance(
            isDismissible: Boolean,
            isHeaderFixed: Boolean,
            buttonGroupCreator: AndesButtonGroupCreator?,
            onDismissCallback: Action?,
            onModalShowCallback: Action?,
            customView: View,
            modalDescription: String?,
            headerTitle: String
        ): AndesModalCardViewFragment {
            return AndesModalCardViewFragment().apply {
                fragmentArguments = AndesModalCardViewFragmentArguments(
                    isDismissible,
                    isHeaderFixed,
                    buttonGroupCreator,
                    onDismissCallback,
                    onModalShowCallback,
                    customView,
                    modalDescription,
                    headerTitle
                )
            }
        }
    }
}
