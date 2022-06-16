package com.mercadolibre.android.andesui.searchbox

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesLayoutSearchboxBinding
import com.mercadolibre.android.andesui.searchbox.factory.AndesSearchboxAttrs
import com.mercadolibre.android.andesui.searchbox.factory.AndesSearchboxAttrsParser
import com.mercadolibre.android.andesui.searchbox.factory.AndesSearchboxConfiguration
import com.mercadolibre.android.andesui.searchbox.factory.AndesSearchboxConfigurationFactory
import com.mercadolibre.android.andesui.typeface.getFontOrDefault
import com.mercadolibre.android.andesui.utils.hideKeyboard

class AndesSearchbox : ConstraintLayout {

    /**
     * Getter and setter for [placeholder].
     */
    var placeholder: String?
        get() = andesSearchboxAttrs.placeholder
        set(value) {
            andesSearchboxAttrs = andesSearchboxAttrs.copy(placeholder = value)
            setupPlaceHolder(createConfig())
        }

    private lateinit var andesSearchboxAttrs: AndesSearchboxAttrs

    private val searchEditText get() = binding.andesSearchboxEdittext
    private val binding by lazy { AndesLayoutSearchboxBinding.inflate(LayoutInflater.from(context), this) }

    var onTextChangedListener: OnTextChangedListener? = null
    var onSearchListener: OnSearchListener? = null

    constructor(context: Context, placeholder: String? = null) : super(context) {
        initAttrs(placeholder)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        andesSearchboxAttrs = AndesSearchboxAttrsParser.parse(context, attrs)
        setupComponents()
    }

    private fun initAttrs(placeholder: String?) {
        andesSearchboxAttrs = AndesSearchboxAttrs(placeholder ?: context.getString(R.string.andes_searchbox_placeholder_default))
        setupComponents()
    }

    private fun setupComponents() {
        setupContainer()
        setupEditText()
        setupPlaceHolder(createConfig())
        setupClearImage()
    }

    private fun setupClearImage() {
        binding.andesSearchboxClose.setOnClickListener {
            clearSearch()
            searchEditText.requestFocus()
        }
    }

    private fun setupPlaceHolder(config: AndesSearchboxConfiguration) {
        searchEditText.typeface = context.getFontOrDefault(R.font.andes_font_regular)
        searchEditText.hint = config.placeHolderText
    }

    private fun setupEditText() {
        setupTextWatcher()
        setupOnEditorActionListener()
    }

    private fun setupTextWatcher() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(oldText: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(newText: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChangedListener?.onTextChanged(newText.toString())
            }

            override fun afterTextChanged(editable: Editable?) {
                binding.andesSearchboxClose.visibility = GONE.takeIf { editable.isNullOrEmpty() }
                        ?: VISIBLE
            }
        })
    }

    private fun setupOnEditorActionListener() {
        searchEditText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    onSearchListener?.onSearch(searchEditText.text.toString())
                    v?.hideKeyboard()
                    return true
                }
                return false
            }
        })
    }

    private fun setupContainer() {
        background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.andes_searchbox_background_selector,
                context.theme
        )
        setAddStatesFromChildren(true)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        searchEditText.setOnClickListener(l)
    }

    fun clearSearch() {
        searchEditText.text.clear()
    }

    interface OnTextChangedListener {
        fun onTextChanged(text: String)
    }

    interface OnSearchListener {
        fun onSearch(text: String)
    }

    private fun createConfig() = AndesSearchboxConfigurationFactory.create(context, andesSearchboxAttrs)

}