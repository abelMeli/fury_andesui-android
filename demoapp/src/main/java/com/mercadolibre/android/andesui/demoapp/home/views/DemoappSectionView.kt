package com.mercadolibre.android.andesui.demoapp.home.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import com.mercadolibre.android.andesui.demoapp.databinding.AndesSectionViewBinding
import com.mercadolibre.android.andesui.list.AndesList

class DemoappSectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding: AndesSectionViewBinding = AndesSectionViewBinding.inflate(LayoutInflater.from(context), this)

    init {
        setTitleA11y()
    }

    private fun setTitleA11y() {
        ViewCompat.setAccessibilityHeading(binding.andesSectionText, true)
    }

    fun setContent(text: String, list: AndesList) {
        binding.andesSectionCard.cardView = list
        binding.andesSectionText.text = text
    }
}
