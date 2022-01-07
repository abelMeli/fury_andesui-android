package com.mercadolibre.android.andesui.coachmark.view

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import com.mercadolibre.android.andesui.coachmark.R
import com.mercadolibre.android.andesui.coachmark.databinding.AndesWalkthroughContainerBinding
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

class CoachmarkContainerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var listener: CoachmarkContainerListener? = null
    private val binding by lazy {
        AndesWalkthroughContainerBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        binding.counterText.typeface = context.getFontOrDefault(R.font.andes_font_regular)
    }

    fun setListener(l: CoachmarkContainerListener) {
        listener = l
    }

    fun setData(position: Int, size: Int) {
        binding.closeButton.setOnClickListener { listener?.onClickClose(position) }
        binding.counterText.text = context.resources.getString(R.string.andes_coachmark_header_numeration_of, position + 1, size)
    }

    interface CoachmarkContainerListener {
        fun onClickClose(position: Int)
    }
}
