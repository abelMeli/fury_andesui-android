package com.mercadolibre.android.andesui.demoapp.home.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import com.mercadolibre.android.andesui.card.AndesCard
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.list.AndesList

class DemoappSectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val title: TextView by lazy { findViewById<TextView>(R.id.andes_section_text) }
    private val container: AndesCard by lazy { findViewById<AndesCard>(R.id.andes_section_card) }

    init {
        inflate(context, R.layout.andes_section_view, this)
        setTitleA11y()
    }

    private fun setTitleA11y() {
        ViewCompat.setAccessibilityHeading(title, true)
    }

    fun setContent(text: String, list: AndesList) {
        container.cardView = list
        title.text = text
    }
}
