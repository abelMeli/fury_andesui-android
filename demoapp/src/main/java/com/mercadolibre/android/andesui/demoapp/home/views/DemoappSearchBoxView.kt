package com.mercadolibre.android.andesui.demoapp.home.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.accessibility.AccessibilityEvent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.AndesSearchboxBinding
import com.mercadolibre.android.andesui.demoapp.utils.Constants.EMPTY_STRING
import com.mercadolibre.android.andesui.demoapp.utils.hideKeyboard
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

class DemoappSearchBoxView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    val searchEditText get() = binding.andesSearchboxEdittext
    private val binding = AndesSearchboxBinding.inflate(LayoutInflater.from(context), this)

    var isEditable: Boolean = true
        set(value) {
            setIsEditable(value)
        }

    private fun setIsEditable(value: Boolean) {
        with(searchEditText) {
            isFocusable = value
            isFocusableInTouchMode = value
            isLongClickable = value
            isClickable = value
            setTextIsSelectable(value)
        }
    }

    init {
        setupComponents()
    }

    private fun setupComponents() {
        setupContainer()
        setupEditText()
        setupClearImage()
    }

    private fun setupClearImage() {
        binding.andesSearchboxClose.setOnClickListener {
            searchEditText.setText(EMPTY_STRING)
            searchEditText.requestFocus()
            searchEditText.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
        }
    }

    private fun setupEditText() {
        searchEditText.typeface = context.getFontOrDefault(R.font.andes_font_regular)
        searchEditText.doAfterTextChanged { editable ->
            binding.andesSearchboxClose.visibility = GONE.takeIf { editable.isNullOrEmpty() } ?: VISIBLE
        }
        searchEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                v.hideKeyboard()
            }
        }
    }

    private fun setupContainer() {
        paddingHorizontal(resources.getDimensionPixelSize(R.dimen.andes_searchbox_container_padding))
        background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.andes_searchbox_background,
            context.theme
        )
    }

    private fun paddingHorizontal(pixelSize: Int) {
        setPadding(pixelSize, NO_PADDING, pixelSize, NO_PADDING)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        searchEditText.setOnClickListener(l)
    }

    companion object {
        private const val NO_PADDING: Int = 0
    }
}
