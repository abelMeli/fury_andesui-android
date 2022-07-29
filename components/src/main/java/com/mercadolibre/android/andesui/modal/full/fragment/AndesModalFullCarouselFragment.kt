package com.mercadolibre.android.andesui.modal.full.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.databinding.AndesModalCarouselFullFragmentBinding
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalFullContentVariation
import com.mercadolibre.android.andesui.modal.full.adapter.AndesModalFullPagerAdapter
import com.mercadolibre.android.andesui.modal.full.builder.ModalFullAction
import com.mercadolibre.android.andesui.modal.full.factory.AndesModalFullCarouselConfig
import com.mercadolibre.android.andesui.modal.full.factory.AndesModalFullCarouselConfigFactory
import com.mercadolibre.android.andesui.modal.full.factory.AndesModalFullCarouselFragmentArguments
import com.mercadolibre.android.andesui.modal.full.interfaces.AndesModalFullCloseListener
import com.mercadolibre.android.andesui.utils.pagetransformer.AndesFadeOutTransformer

/**
 * Carousel variant of the full modal.
 * The public empty constructor is only available for OS requirements, please avoid using it.
 * To instantiate please use the builder present in the [com.mercadolibre.android.andesui.modal.AndesModal]
 */
class AndesModalFullCarouselFragment :
    AndesModalBaseFragment<AndesModalCarouselFullFragmentBinding>(
        R.layout.andes_modal_carousel_full_fragment
    ), AndesModalFullCloseListener {

    private var onPageSelectedCallback: ((position: Int) -> Unit)? = null
    private var fragmentArguments = AndesModalFullCarouselFragmentArguments()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = AndesModalCarouselFullFragmentBinding.inflate(inflater, container, false)

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
        val config = AndesModalFullCarouselConfigFactory.create(
            fragmentArguments,
            this
        )

        setupOnDismissCallback(config)
        setupOnModalShowCallback(config)
        setupOnPageSelectedCallback(config)
        setupButtonGroup(config)
        setupViewPager(config)
    }

    private fun setupOnPageSelectedCallback(config: AndesModalFullCarouselConfig) {
        onPageSelectedCallback = config.onPageSelectedCallback
    }

    private fun setupOnModalShowCallback(config: AndesModalFullCarouselConfig) {
        onModalShowCallback = config.onModalShowCallback
    }

    private fun setupOnDismissCallback(config: AndesModalFullCarouselConfig) {
        onDismissCallback = config.onDismissCallback
    }

    private fun setupButtonGroup(config: AndesModalFullCarouselConfig) {
        val buttonGroup = config.buttonGroup
        buttonGroup?.let {
            it.id = R.id.andes_modal_button_group_id
            it.layoutParams = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.buttonGroupContainer.apply {
                visibility = View.VISIBLE
                addView(it)
            }
        }
    }

    private fun setupViewPager(config: AndesModalFullCarouselConfig) {
        config.contentList?.let {
            binding.viewPager.setPageTransformer(false, AndesFadeOutTransformer())
            binding.viewPager.adapter = AndesModalFullPagerAdapter(it, config, this)
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
    private fun setupMainActionButton(config: AndesModalFullCarouselConfig, position: Int) {
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

    override fun onCloseListener() {
        dismiss()
    }

    companion object {
        private const val MAIN_BUTTON_ANIMATION_DURATION = 400

        /**
         * Returns a new instance of [AndesModalFullCarouselFragment]
         */
        @Suppress("LongParameterList")
        fun newInstance(
            isDismissible: Boolean,
            buttonGroupCreator: AndesButtonGroupCreator?,
            onDismissCallback: ModalFullAction?,
            onModalShowCallback: ModalFullAction?,
            contentVariation: AndesModalFullContentVariation,
            isHeaderFixed: Boolean,
            pageSelectedCallback: ((position: Int) -> Unit)?,
            contentList: ArrayList<AndesModalContent>
        ): AndesModalFullCarouselFragment {
            return AndesModalFullCarouselFragment().apply {
                fragmentArguments = AndesModalFullCarouselFragmentArguments(
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
