package com.mercadolibre.android.andesui.demoapp.components.bottomsheet

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.mercadolibre.android.andesui.bottomsheet.AndesBottomSheet
import com.mercadolibre.android.andesui.bottomsheet.BottomSheetListener
import com.mercadolibre.android.andesui.bottomsheet.state.AndesBottomSheetContentMargin
import com.mercadolibre.android.andesui.bottomsheet.title.AndesBottomSheetTitleAlignment
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiBottomSheetShowcaseBinding

@Suppress("TooManyFunctions")
class BottomSheetShowcaseActivity : BaseActivity(), BottomSheetListener {

    private lateinit var viewPager: CustomViewPager
    private lateinit var bottomSheet: AndesBottomSheet
    private var showTitle = false
    private var leftAlignTitle = true
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle(): String =
        resources.getString(R.string.andes_demoapp_screen_bottom_sheet)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf(
            AndesuiBottomSheetShowcaseBinding.inflate(layoutInflater).root
        ))
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
    }

    private fun addDynamicPage(container: View) {
        bottomSheet = AndesuiBottomSheetShowcaseBinding.bind(container).andesBottomSheet
        bottomSheet.setBottomSheetListener(this)
    }

    override fun onCollapsed() {
        Toast.makeText(applicationContext, "Collapsed", Toast.LENGTH_SHORT).show()
    }

    override fun onHalfExpanded() {
        Toast.makeText(applicationContext, "Half Expanded", Toast.LENGTH_SHORT).show()
    }

    override fun onExpanded() {
        Toast.makeText(applicationContext, "Expanded", Toast.LENGTH_SHORT).show()
    }

    override fun onTouchOutside() {
        Toast.makeText(applicationContext, "Touch Outside", Toast.LENGTH_SHORT).show()
    }

    fun onAttachViewButtonClicked(view: View) {
        bottomSheet.removeContent()
        if (textView == null) {
            textView = TextView(applicationContext)
            val params = ViewGroup.LayoutParams(MATCH_PARENT, VIEW_HEIGHT)
            textView?.layoutParams = params
            textView?.text = getString(R.string.andes_bottom_sheet_dummy_view)
            textView?.gravity = Gravity.CENTER
        }

        bottomSheet.setContent(textView!!)
        bottomSheet.expand()
    }

    fun onAttachFragmentButtonClicked(view: View) {
        bottomSheet.removeContent()

        bottomSheet.setContent(supportFragmentManager, TestFragment())

        Handler().postDelayed({
            bottomSheet.expand()
        }, ONE_HUNDRED_MS)
    }

    fun onToggleTitleClicked(view: View) {
        val andesButton = view as AndesButton
        showTitle = !showTitle
        if (showTitle) {
            bottomSheet.titleText = "Hello, I'm Title"
            andesButton.hierarchy = AndesButtonHierarchy.QUIET
        } else {
            bottomSheet.titleText = null
            andesButton.hierarchy = AndesButtonHierarchy.LOUD
        }
    }

    fun onToggleAlignButtonClicked(view: View) {
        val andesButton = view as AndesButton
        leftAlignTitle = !leftAlignTitle
        if (leftAlignTitle) {
            bottomSheet.titleAlignment = AndesBottomSheetTitleAlignment.LEFT_ALIGN
            andesButton.hierarchy = AndesButtonHierarchy.LOUD
        } else {
            bottomSheet.titleAlignment = AndesBottomSheetTitleAlignment.CENTERED
            andesButton.hierarchy = AndesButtonHierarchy.QUIET
        }
    }

    fun onSetPeekHeightButtonClicked(view: View) {
        val binding = AndesuiBottomSheetShowcaseBinding.bind(view)
        if (!binding.andesBottomSheetPeekHeightTextField.text.isNullOrEmpty()) {
            bottomSheet.peekHeight = binding.andesBottomSheetPeekHeightTextField.text!!.toInt()
            closeKeyboard(view)
            binding.andesBottomSheetPeekHeightTextField.text = ""
        }
    }

    fun onSetFitContentButtonClicked(view: View) {
        val andesButton = view as AndesButton
        if (bottomSheet.fitContent) {
            bottomSheet.fitContent = false
            andesButton.hierarchy = AndesButtonHierarchy.QUIET
        } else {
            bottomSheet.fitContent = true
            andesButton.hierarchy = AndesButtonHierarchy.LOUD
        }
    }

    fun onToggleContentMargin(view: View) {
        val andesButton = view as AndesButton
        if (andesButton.hierarchy == AndesButtonHierarchy.LOUD) {
            bottomSheet.setContentMargin(AndesBottomSheetContentMargin.NO_HORIZONTAL_MARGINS)
            andesButton.hierarchy = AndesButtonHierarchy.QUIET
        } else {
            bottomSheet.setContentMargin(AndesBottomSheetContentMargin.DEFAULT)
            andesButton.hierarchy = AndesButtonHierarchy.LOUD
        }
    }

    fun onToggleContentShadow(isShadow: Boolean) {
        bottomSheet.setContentShadow(isShadow)
    }

    private fun closeKeyboard(view: View) {
        val inputManager: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        private const val VIEW_HEIGHT = 800
        private const val ONE_HUNDRED_MS = 100.toLong()
    }
}
