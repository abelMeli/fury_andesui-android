package com.mercadolibre.android.andesui.demoapp.components.modal

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalFullShowcaseActivity.Companion.BUTTONS_AMOUNT
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalFullShowcaseActivity.Companion.CONTENT_VARIATION
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalFullShowcaseActivity.Companion.DISPLAY_LARGE_CONTENT
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalFullShowcaseActivity.Companion.HEADER_TITLE
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalFullShowcaseActivity.Companion.IS_BUTTONGROUP_FIXED
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalFullShowcaseActivity.Companion.IS_DISMISSIBLE
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalFullShowcaseActivity.Companion.IS_HEADER_FIXED
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalFullShowcaseActivity.Companion.MODAL_DESCRIPTION
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalFullShowcaseActivity.Companion.MODAL_TYPE
import com.mercadolibre.android.andesui.demoapp.components.modal.ModalFullShowcaseActivity.Companion.PAGES_AMOUNT
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicModalCardBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicModalFullBinding
import com.mercadolibre.android.andesui.modal.card.dialogfragment.AndesDialogFragment
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalFullContentVariation

@Suppress("TooManyFunctions")
class ModalShowcaseActivity : BaseActivity(), CardModalContainer, FullModalContainer {

    private lateinit var viewPager: CustomViewPager
    private var appBarTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_modal)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(
            listOf<View>(
                AndesuiDynamicModalCardBinding.inflate(layoutInflater).root,
                AndesuiDynamicModalFullBinding.inflate(layoutInflater).root
            )
        )
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(position: Int) = Unit
            override fun onPageScrolled(position: Int, p1: Float, p2: Int) = Unit
            override fun onPageSelected(position: Int) {
                appBarTitle = when (position) {
                    0 -> resources.getString(R.string.andesui_demoapp_dynamic_modal_card_title)
                    1 -> resources.getString(R.string.andesui_demoapp_dynamic_modal_full_title)
                    else -> resources.getString(R.string.andesui_demoapp_dynamic_modal_card_title)
                }
                updateAppBarTitle(appBarTitle)
            }
        })
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        ModalCardDynamicPage().create(adapter.views[0], this)
        ModalFullDynamicPage().create(adapter.views[1], this)
    }

    override fun showCardModal(modal: AndesDialogFragment) {
        modal.show(this)
    }

    override fun showDefaultModal(
        selectedType: String,
        buttonsAmount: String,
        selectedContentVariation: AndesModalFullContentVariation,
        isHeaderFixed: Boolean,
        isButtonGroupFixed: Boolean,
        isDismissible: Boolean,
        displayLargeContent: Boolean
    ) {
        Intent(this, ModalFullShowcaseActivity::class.java).apply {
            putExtras(
                Bundle().apply {
                    putString(MODAL_TYPE, selectedType)
                    putString(BUTTONS_AMOUNT, buttonsAmount)
                    putSerializable(CONTENT_VARIATION, selectedContentVariation)
                    putBoolean(IS_HEADER_FIXED, isHeaderFixed)
                    putBoolean(IS_BUTTONGROUP_FIXED, isButtonGroupFixed)
                    putBoolean(IS_DISMISSIBLE, isDismissible)
                    putBoolean(DISPLAY_LARGE_CONTENT, displayLargeContent)
                }
            )
            startActivity(this)
        }
    }

    override fun showCarouselModal(
        selectedType: String,
        buttonsAmount: String,
        selectedContentVariation: AndesModalFullContentVariation,
        pagesAmount: String,
        isHeaderFixed: Boolean,
        isDismissible: Boolean,
        displayLargeContent: Boolean
    ) {
        Intent(this, ModalFullShowcaseActivity::class.java).apply {
            putExtras(
                Bundle().apply {
                    putString(MODAL_TYPE, selectedType)
                    putString(BUTTONS_AMOUNT, buttonsAmount)
                    putSerializable(CONTENT_VARIATION, selectedContentVariation)
                    putString(PAGES_AMOUNT, pagesAmount)
                    putBoolean(IS_HEADER_FIXED, isHeaderFixed)
                    putBoolean(IS_DISMISSIBLE, isDismissible)
                    putBoolean(DISPLAY_LARGE_CONTENT, displayLargeContent)
                }
            )
            startActivity(this)
        }
    }

    override fun showCustomViewModal(
        selectedType: String,
        buttonsAmount: String,
        headerTitle: String?,
        isHeaderFixed: Boolean,
        isDismissible: Boolean,
        modalDescription: String?
    ) {
        Intent(this, ModalFullShowcaseActivity::class.java).apply {
            putExtras(
                Bundle().apply {
                    putString(MODAL_TYPE, selectedType)
                    putString(BUTTONS_AMOUNT, buttonsAmount)
                    putString(HEADER_TITLE, headerTitle)
                    putBoolean(IS_HEADER_FIXED, isHeaderFixed)
                    putBoolean(IS_DISMISSIBLE, isDismissible)
                    putString(MODAL_DESCRIPTION, modalDescription)
                }
            )
            startActivity(this)
        }
    }
}
