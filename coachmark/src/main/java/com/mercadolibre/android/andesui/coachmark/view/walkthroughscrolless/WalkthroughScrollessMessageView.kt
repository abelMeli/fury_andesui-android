package com.mercadolibre.android.andesui.coachmark.view.walkthroughscrolless

import android.content.Context
import android.graphics.Rect
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.coachmark.R
import com.mercadolibre.android.andesui.coachmark.databinding.AndesWalkthroughScrollessMessageBinding
import com.mercadolibre.android.andesui.coachmark.model.WalkthroughMessageModel
import com.mercadolibre.android.andesui.coachmark.model.WalkthroughMessagePosition
import com.mercadolibre.android.andesui.coachmark.utils.setConstraints
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

internal class WalkthroughScrollessMessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var position: WalkthroughMessagePosition = WalkthroughMessagePosition.BELOW
    private var walkthroughButtonClicklistener: WalkthroughButtonClicklistener? = null
    private val binding by lazy {
        AndesWalkthroughScrollessMessageBinding.inflate(LayoutInflater.from(context), this, true)
    }

    var hasArrow: Boolean = false
        private set
        get() = (binding.arcArrowBottom.visibility == View.VISIBLE) or
                (binding.arcArrowTop.visibility == View.VISIBLE)

    init {
        with(binding) {
            walkthroughTitle.typeface = context.getFontOrDefault(R.font.andes_font_semibold)
            walkthroughDescription.typeface = context.getFontOrDefault(R.font.andes_font_regular)
            walkthroughNextButton.typeface = context.getFontOrDefault(R.font.andes_font_semibold)
        }
    }

    fun setListener(walkthroughButtonClicklistener: WalkthroughButtonClicklistener) {
        this.walkthroughButtonClicklistener = walkthroughButtonClicklistener
    }

    fun setPosition(position: Int) {
        binding.walkthroughNextButton.setOnClickListener { walkthroughButtonClicklistener?.onClickNextButton(position) }
    }

    fun setData(data: WalkthroughMessageModel, lastPosition: Boolean) {
        with(binding) {

            if (lastPosition) {
                walkthroughNextButton.setBackgroundResource(R.drawable.andes_walkthrough_configuration_blue_button_background)
            } else {
                walkthroughNextButton.setBackgroundResource(R.drawable.andes_walkthrough_configuration_button_background)
            }

            walkthroughDescription.text = data.description
            walkthroughTitle.text = data.title
            walkthroughNextButton.text = data.buttonText

            checkViewVisibility(walkthroughDescription, data.description)
            checkViewVisibility(walkthroughTitle, data.title)
            checkViewVisibility(walkthroughNextButton, data.buttonText)
        }
    }

    fun definePosition(overlayRect: Rect, targetRect: Rect) {
        setPosition(overlayRect, targetRect)
        setArrow(targetRect)
    }

    fun setNewDimensions(height: Int) {
        setNewHeight(height)
        setNewConstraints(height)
    }

    private fun setNewConstraints(height: Int) {
        val buttonGoneMargin: Int
        val buttonVerticalBias: Float

        if (height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            buttonGoneMargin = R.dimen.andes_coachmark_arrow_margin_gone
            buttonVerticalBias = CENTER_BIAS
        } else {
            buttonGoneMargin = R.dimen.andes_coachmark_button_bottom_margin
            buttonVerticalBias = BOTTOM_BIAS
        }

        binding.walkthroughContainer.apply {
            setConstraints {
                setGoneMargin(
                    binding.walkthroughNextButton.id,
                    ConstraintSet.BOTTOM, context.resources.getDimensionPixelSize(buttonGoneMargin)
                )
                setVerticalBias(binding.walkthroughNextButton.id, buttonVerticalBias)
            }
        }
    }

    private fun setNewHeight(height: Int) {
        val internalParams = binding.walkthroughContainer.layoutParams
        internalParams.height = height
        binding.walkthroughContainer.layoutParams = internalParams
    }

    private fun setPosition(overlayRect: Rect, targetRect: Rect) {
        val centerReferenceView = (targetRect.bottom + targetRect.top) / 2
        val centerScreen = (overlayRect.bottom + overlayRect.top) / 2
        position = if (centerReferenceView <= centerScreen) {
            WalkthroughMessagePosition.BELOW
        } else {
            WalkthroughMessagePosition.ABOVE
        }
    }

    private fun setArrow(targetRect: Rect) {
        val tooltipRect = Rect()
        val centerTarget = (targetRect.left + targetRect.right) / 2

        when (position) {
            WalkthroughMessagePosition.BELOW -> {
                setMessagePositionBelow(tooltipRect, centerTarget)
            }
            WalkthroughMessagePosition.ABOVE -> {
                setMessagePositionAbove(tooltipRect, centerTarget)
            }
        }
    }

    private fun setupButtonWidth(isButtonFullWidth: Boolean) {
        val buttonWidth = if (isButtonFullWidth) LayoutParams.MATCH_PARENT else LayoutParams.WRAP_CONTENT
        val params = binding.walkthroughNextButton.layoutParams
        params.width = buttonWidth
        binding.walkthroughNextButton.layoutParams = params
    }

    private fun setMessagePositionAbove(tooltipRect: Rect, centerTarget: Int) {
        with(binding) {
            arcArrowTop.visibility = View.GONE
            arcArrowBottom.visibility = View.VISIBLE
            arcArrowBottom.getLocalVisibleRect(tooltipRect)
            val centerTooltip = (tooltipRect.left + tooltipRect.right) / 2
            setupButtonWidth(false)
            if (isNecessaryShowArrow(centerTooltip, centerTarget)) {
                arcArrowBottom.addRect(centerTooltip, tooltipRect.top, centerTarget, tooltipRect.bottom)
            } else {
                arcArrowBottom.visibility = View.GONE
            }
        }
    }

    private fun setMessagePositionBelow(tooltipRect: Rect, centerTarget: Int) {
        with(binding) {
            arcArrowTop.visibility = View.VISIBLE
            arcArrowBottom.visibility = View.GONE
            arcArrowTop.getLocalVisibleRect(tooltipRect)
            val centerTooltip = (tooltipRect.left + tooltipRect.right) / 2
            if (isNecessaryShowArrow(centerTooltip, centerTarget)) {
                arcArrowTop.addRect(centerTooltip, tooltipRect.bottom, centerTarget, tooltipRect.top)
                setupButtonWidth(false)
            } else {
                arcArrowTop.visibility = View.GONE
                setupButtonWidth(true)
            }
        }
    }

    @SuppressWarnings("ReturnCount")
    private fun isNecessaryShowArrow(centerTooltip: Int, centerTarget: Int): Boolean {
        val minWithForShowArrow = context.resources.getDimension(R.dimen.andes_coachmark_min_with_for_show_arrow)
        if ((centerTooltip - centerTarget >= 0) && (centerTooltip - centerTarget <= minWithForShowArrow)) {
            return false
        } else if ((centerTarget - centerTooltip >= 0) && (centerTarget - centerTooltip <= minWithForShowArrow)) {
            return false
        }
        return true
    }

    fun clear() {
        with(binding) {
            arcArrowBottom.clear()
            arcArrowTop.clear()
            arcArrowBottom.visibility = View.VISIBLE
            arcArrowTop.visibility = View.GONE
            walkthroughTitle.visibility = View.GONE
            walkthroughDescription.visibility = View.GONE
        }
    }

    fun getPosition(): WalkthroughMessagePosition {
        return position
    }

    fun checkViewVisibility(view: View, content: String) {
        view.visibility = if (content.isNotBlank()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    interface WalkthroughButtonClicklistener {
        fun onClickNextButton(position: Int)
    }

    private companion object {
        const val CENTER_BIAS = 0.5f
        const val BOTTOM_BIAS = 1f
    }
}
