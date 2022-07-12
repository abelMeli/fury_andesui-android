package com.mercadolibre.android.andesui.modal.full.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesModalCustomFullLayoutBinding
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.full.builder.ModalFullAction
import com.mercadolibre.android.andesui.modal.full.factory.AndesModalFullCustomViewConfig
import com.mercadolibre.android.andesui.modal.full.factory.AndesModalFullCustomViewConfigFactory
import com.mercadolibre.android.andesui.modal.full.factory.AndesModalFullCustomViewFragmentArguments

/**
 * Custom view variant of the full modal.
 * This modal allows to create a fragment with any custom view the dev requires.
 *
 * The public empty constructor is only available for OS requirements, please avoid using it.
 * To instantiate please use the builder present in the [com.mercadolibre.android.andesui.modal.AndesModal]
 */
class AndesModalFullCustomViewFragment : AndesModalBaseFragment<AndesModalCustomFullLayoutBinding>(
    R.layout.andes_modal_custom_full_layout
) {

    private var fragmentArguments = AndesModalFullCustomViewFragmentArguments()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        handleContent()
        return view
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = AndesModalCustomFullLayoutBinding.inflate(inflater, container, false)

    private fun handleContent() {
        val config = AndesModalFullCustomViewConfigFactory.create(fragmentArguments)

        setupAccessibilityModalDescription(config)
        setupHeaderType(config)
        setupDismissibleAction()
        setupOnDismissCallback(config)
        setupOnModalShowCallback(config)
        setupButtonGroup(config)
        setupCustomView(config)
    }

    private fun setupAccessibilityModalDescription(config: AndesModalFullCustomViewConfig) {
        modalDescription = config.modalDescription
    }

    private fun setupHeaderType(config: AndesModalFullCustomViewConfig) {
        binding.headerType.apply {
            headerTitle = config.headerTitle
            headerType = config.headerType
        }
        if (config.isHeaderFixed) {
            binding.scrollView.setHeaderId(binding.headerType.id)
        }
    }

    private fun setupCustomView(config: AndesModalFullCustomViewConfig) {
        config.customView?.let {
            // check if passed view is not already included in another layout
            if (it.parent != null) {
                (it.parent as ViewGroup).removeAllViews()
            }
            binding.customViewContainer.addView(it)
        }
    }

    private fun setupButtonGroup(config: AndesModalFullCustomViewConfig) {
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

    private fun setupOnModalShowCallback(config: AndesModalFullCustomViewConfig) {
        onModalShowCallback = config.onModalShowCallback
    }

    private fun setupOnDismissCallback(config: AndesModalFullCustomViewConfig) {
        onDismissCallback = config.onDismissCallback
    }

    private fun setupDismissibleAction() {
        binding.headerType.setCloseCallback {
            dismiss()
        }
    }

    companion object {

        /**
         * Returns a new instance of [AndesModalFullCustomViewFragment]
         */
        @Suppress("LongParameterList")
        internal fun newInstance(
            isDismissible: Boolean,
            isHeaderFixed: Boolean,
            headerTitle: String?,
            buttonGroupCreator: AndesButtonGroupCreator?,
            onDismissCallback: ModalFullAction?,
            onModalShowCallback: ModalFullAction?,
            customView: View,
            modalDescription: String?
        ): AndesModalFullCustomViewFragment {
            return AndesModalFullCustomViewFragment().apply {
                fragmentArguments = AndesModalFullCustomViewFragmentArguments(
                    isDismissible,
                    isHeaderFixed,
                    headerTitle,
                    buttonGroupCreator,
                    onDismissCallback,
                    onModalShowCallback,
                    customView,
                    modalDescription
                )
            }
        }
    }
}
