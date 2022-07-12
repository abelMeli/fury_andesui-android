package com.mercadolibre.android.andesui.modal.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesModalHeaderDismissableBinding
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderStatus
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderType
import com.mercadolibre.android.andesui.utils.AnimationsUtils
import com.mercadolibre.android.andesui.utils.setConstraints

/**
 * Internal text header component for the full modal.
 * It will show/hide the text and the dismiss button according to the [headerType] value.
 * It will expand/collapse the title with the corresponding animations according to the [textStatus] value.
 */
internal class AndesModalHeaderTypeComponent(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    private val binding by lazy {
        AndesModalHeaderDismissableBinding.inflate(LayoutInflater.from(context), this, true)
    }

    internal var titleVisibility: Int = View.GONE

    var headerType: AndesModalFullHeaderType = DEFAULT_HEADER
        set(value) {
            setupHeader(value)
            field = value
        }

    var headerTitle: CharSequence? = null
        set(value) {
            if (value != field) {
                handleTitleText(value)
                field = value
            }
        }

    var textStatus: AndesModalFullHeaderStatus = DEFAULT_STATUS
        set(value) {
            field = value
            handleTitleStatus(value)
        }

    var isTextCentered = false
        set(value) {
            field = value
            setupTextAlignment(value)
        }

    fun animateHeaderVisibility(value: Int, onFinishExit: (() -> Unit)? = null) {
        titleVisibility = value
        if (value == View.VISIBLE) {
            animateHeaderTextEntrance()
        } else {
            animateHeaderTextExit {
                onFinishExit?.invoke()
            }
        }
    }

    private fun setupTextAlignment(isTextCentered: Boolean) {
        val bias = if (isTextCentered) {
            CENTERED_BIAS
        } else {
            LEFT_ALIGNED_BIAS
        }
        binding.root.setConstraints {
            setHorizontalBias(binding.title.id, bias)
        }
    }

    private fun handleTitleStatus(value: AndesModalFullHeaderStatus) {
        binding.title.apply {
            ellipsize = value.status.ellipsize
            isSingleLine = value.status.isSingleLine
        }
    }

    private fun animateHeaderTextEntrance() {
        AnimationsUtils.fadeIn(
            binding.title, AnimationsUtils.Position.BOTTOM,
            ANIMATION_DURATION, 0L, true
        )
    }

    private fun animateHeaderTextExit(onFinishExit: (() -> Unit)? = null) {
        AnimationsUtils.fadeOut(
            binding.title, AnimationsUtils.Position.BOTTOM,
            ANIMATION_DURATION, 0L, true
        ) {
            onFinishExit?.invoke()
        }
    }

    fun setCloseCallback(onClickListener: () -> Unit) {
        binding.closeImageview.setOnClickListener {
            onClickListener.invoke()
        }
    }

    private fun setupHeader(value: AndesModalFullHeaderType) {
        setVisibility(value)
    }

    private fun handleTitleText(value: CharSequence?) {
        binding.title.text = value
    }

    private fun setVisibility(value: AndesModalFullHeaderType) {
        binding.apply {
            title.visibility = value.header.titleVisibility()
            title.alpha = value.header.getAlpha()
            closeImageview.visibility = value.header.closeActionVisibility()
            closeImageview.setColorFilter(context.resources.getColor(R.color.andes_gray_950))
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setupA11y()
    }

    private fun setupA11y() {
        with(binding.closeImageview) {
            accessibilityTraversalBefore = binding.title.id
            ViewCompat.replaceAccessibilityAction(
                this,
                AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK,
                context.getString(R.string.andes_modal_dismiss_content_description),
                null
            )
        }
    }

    companion object {
        private val DEFAULT_HEADER = AndesModalFullHeaderType.TITLE_CLOSE
        private val DEFAULT_STATUS = AndesModalFullHeaderStatus.COLLAPSED
        private const val ANIMATION_DURATION = 200L
        private const val CENTERED_BIAS = 0.5f
        private const val LEFT_ALIGNED_BIAS = 0.0f
    }
}
