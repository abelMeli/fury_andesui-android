package com.mercadolibre.android.andesui.coachmark.view.walkthroughscrolless

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.mercadolibre.android.andesui.coachmark.R
import com.mercadolibre.android.andesui.coachmark.databinding.AndesWalkthroughScrollessContainerBinding
import com.mercadolibre.android.andesui.coachmark.view.CoachmarkOverlay
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

class CoachmarkScrollessContainerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var coachMarkContainerListener: CoachmarkContainerListener? = null
    private val binding by lazy {
        AndesWalkthroughScrollessContainerBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        binding.counterText.typeface = context.getFontOrDefault(R.font.andes_font_regular)
    }

    fun setListener(coachMarkContainerListener: CoachmarkContainerListener) {
        this.coachMarkContainerListener = coachMarkContainerListener
    }

    fun setData(position: Int, size: Int) {
        binding.closeButton.setOnClickListener { coachMarkContainerListener?.onClickClose(position) }
        binding.counterText.text = context.resources.getString(
            R.string.andes_coachmark_header_numeration_of,
            position + 1,
            size
        )
    }

    fun getHamburgerView(): View {
        return binding.hamburguerView
    }

    internal fun getHeaderView(): CoachmarkOverlay {
        return binding.headerBackground
    }

    fun getCloseButtonView(): View {
        return binding.closeButton
    }

    interface CoachmarkContainerListener {
        fun onClickClose(position: Int)
    }
}
