package com.mercadolibre.android.andesui.demoapp.components.searchbox


import android.os.Bundle
import android.util.Log
import android.view.View
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicSearchboxBinding
import com.mercadolibre.android.andesui.searchbox.AndesSearchbox
import com.mercadolibre.android.andesui.textfield.AndesTextfield

class SearchboxShowcaseActivity : BaseActivity() {

    private lateinit var andesSearchbox: AndesSearchbox
    private lateinit var buttonClear: AndesButton
    private lateinit var buttonUpdate: AndesButton
    private lateinit var editTextPlaceHolder: AndesTextfield

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_searchbox)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf(
                AndesuiDynamicSearchboxBinding.inflate(layoutInflater).root
        ))
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
    }

    @Suppress("LongMethod")
    private fun addDynamicPage(container: View) {
        val binding = AndesuiDynamicSearchboxBinding.bind(container)
        buttonClear = binding.clear
        buttonUpdate = binding.update

        andesSearchbox = binding.searchbox
        editTextPlaceHolder = binding.placeholder

        andesSearchbox.apply {
            placeholder = PLACEHOLDER_DEFAULT
        }

        andesSearchbox.onTextChangedListener = object : AndesSearchbox.OnTextChangedListener {
            override fun onTextChanged(text: String) {
                Log.d(TAG, "onTextChanged: " + text)
            }
        }

        andesSearchbox.onSearchListener = object: AndesSearchbox.OnSearchListener {
            override fun onSearch(text: String) {
                Log.d(TAG, "onSearch: " + text)
            }

        }


        buttonClear.setOnClickListener {
            clear()
        }

        buttonUpdate.setOnClickListener {
            update()
        }
    }

    private fun update() {
        andesSearchbox.placeholder = editTextPlaceHolder.text.toString()
    }

    private fun clear() {
        editTextPlaceHolder.text = ""
        andesSearchbox.apply {
            placeholder = PLACEHOLDER_DEFAULT
        }
    }

    companion object {
        private val PLACEHOLDER_DEFAULT = "Placeholder"
        private val TAG: String = SearchboxShowcaseActivity::class.java.simpleName
    }
}