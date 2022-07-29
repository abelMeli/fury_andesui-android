package com.mercadolibre.android.andesui.modal.card.dialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.databinding.AndesModalCarouselCardLayoutBinding
import com.mercadolibre.android.andesui.modal.card.builder.Action
import com.mercadolibre.android.andesui.modal.card.configfactory.AndesModalCardCarouselConfig
import com.mercadolibre.android.andesui.modal.card.configfactory.AndesModalCardCarouselConfigFactory
import com.mercadolibre.android.andesui.modal.card.configfactory.AndesModalCardCarouselFragmentArguments
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalCardContentVariation
import com.mercadolibre.android.andesui.modal.card.adapter.AndesModalCardPagerAdapter
import com.mercadolibre.android.andesui.utils.pagetransformer.AndesFadeOutTransformer

/**
 * Carousel variant of the card modal.
 * The public empty constructor is only available for OS requirements, please avoid using it.
 * To instantiate please use the builder present in the [com.mercadolibre.android.andesui.modal.AndesModal]
 */
class AndesModalCardCarouselFragment : AndesDialogFragment() {

    private var _binding: AndesModalCarouselCardLayoutBinding? = null
    private val binding get() = _binding!!
    private var onPageSelectedCallback: ((position: Int) -> Unit)? = null
    private var fragmentArguments = AndesModalCardCarouselFragmentArguments()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AndesModalCarouselCardLayoutBinding.inflate(inflater, container, false)
        handleContent()
        return binding.root
    }

    private fun handleContent() {
        val config = AndesModalCardCarouselConfigFactory.create(
            fragmentArguments,
            this
        )

        setupRoundedCorners(config)
        setupDismissibleAction(config)
        setupOnDismissCallback(config)
        setupOnModalShowCallback(config)
        setupOnPageSelectedCallback(config)
        setupViewPager(config)
        setupButtonGroup(config)
        setupA11y()
    }

    private fun setupButtonGroup(config: AndesModalCardCarouselConfig) {
        val buttonGroup = config.buttonGroup
        buttonGroup?.let {
            it.id = R.id.andes_modal_button_group_id
            it.layoutParams = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.buttonGroupContainer.addView(it)
        }
    }

    private fun setupOnPageSelectedCallback(config: AndesModalCardCarouselConfig) {
        onPageSelectedCallback = config.onPageSelectedCallback
    }

    private fun setupOnModalShowCallback(config: AndesModalCardCarouselConfig) {
        onModalShowCallback = config.onModalShowCallback
    }

    private fun setupOnDismissCallback(config: AndesModalCardCarouselConfig) {
        onDismissCallback = config.onDismissCallback
    }

    private fun setupDismissibleAction(config: AndesModalCardCarouselConfig) {
        dialog?.setCanceledOnTouchOutside(config.isDismissible)
        isDismissible = config.isDismissible
        binding.andesModalCloseButton.setOnClickListener {
            dismiss()
        }
        binding.andesModalCloseButton.visibility = config.closeButtonVisibility
    }

    private fun setupRoundedCorners(config: AndesModalCardCarouselConfig) {
        binding.viewPager.outlineProvider = config.scrollViewOutlineProvider
        binding.viewPager.clipToOutline = true
    }

    private fun setupViewPager(config: AndesModalCardCarouselConfig) {
        config.contentList?.let {
            binding.viewPager.setPageTransformer(false, AndesFadeOutTransformer())
            binding.viewPager.adapter = AndesModalCardPagerAdapter(it, config)
            binding.pageIndicator.attach(binding.viewPager) { position ->
                onPageSelectedCallback?.invoke(position)
                setupMainActionButton(config, position)
            }
        }
    }

    /**
     * Get the main action button (if present) and change the hierarchy according to the position
     * of the viewpager (when in last position, should be LOUD, while in other positions should
     * be QUIET).
     */
    private fun setupMainActionButton(config: AndesModalCardCarouselConfig, position: Int) {
        config.mainAction?.let { mainAction ->
            if (position == config.contentList?.lastIndex) {
                config.buttonGroup
                    ?.getButton(mainAction)
                    ?.transitionIntoNewHierarchy(
                        AndesButtonHierarchy.LOUD,
                        MAIN_BUTTON_ANIMATION_DURATION
                    )
            } else {
                config.buttonGroup
                    ?.getButton(mainAction)
                    ?.transitionIntoNewHierarchy(
                        AndesButtonHierarchy.QUIET,
                        MAIN_BUTTON_ANIMATION_DURATION
                    )
            }
        }
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
        private const val MAIN_BUTTON_ANIMATION_DURATION = 400

        /**
         * Returns a new instance of [AndesModalCardCarouselFragment]
         */
        @Suppress("LongParameterList")
        fun newInstance(
            isDismissible: Boolean,
            buttonGroupCreator: AndesButtonGroupCreator?,
            onDismissCallback: Action?,
            onModalShowCallback: Action?,
            contentVariation: AndesModalCardContentVariation,
            isHeaderFixed: Boolean,
            pageSelectedCallback: ((position: Int) -> Unit)?,
            contentList: ArrayList<AndesModalContent>
        ): AndesModalCardCarouselFragment {
            return AndesModalCardCarouselFragment().apply {
                fragmentArguments = AndesModalCardCarouselFragmentArguments(
                    isDismissible,
                    buttonGroupCreator,
                    onDismissCallback,
                    onModalShowCallback,
                    contentVariation,
                    isHeaderFixed,
                    pageSelectedCallback,
                    contentList
                )
            }
        }
    }
}
