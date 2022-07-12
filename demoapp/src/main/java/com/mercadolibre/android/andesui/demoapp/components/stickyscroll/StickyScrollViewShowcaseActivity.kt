package com.mercadolibre.android.andesui.demoapp.components.stickyscroll

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistribution
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticNotStickyScrollViewBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticStickyScrollViewBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStickyScrollviewDialogBinding
import com.mercadolibre.android.andesui.modal.AndesModal
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupData
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.AndesModalInterface
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalCardContentVariation

class StickyScrollViewShowcaseActivity : BaseActivity() {

        private lateinit var viewPager: CustomViewPager

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            initViewPager()
            attachIndicator()
            loadViews()
        }

        override fun getAppBarTitle() = resources.getString(R.string.andesui_demoapp_sticky_view_title)

        private fun initViewPager() {
            viewPager = baseBinding.andesuiViewpager
            viewPager.adapter = AndesPagerAdapter(
                listOf<View>(
                    AndesuiStickyScrollviewDialogBinding.inflate(layoutInflater).root,
                    AndesuiStaticStickyScrollViewBinding.inflate(layoutInflater).root,
                    AndesuiStaticNotStickyScrollViewBinding.inflate(layoutInflater).root
                )
            )
        }

        private fun attachIndicator() {
            baseBinding.pageIndicator.attach(viewPager)
        }

        private fun loadViews() {
            val adapter = viewPager.adapter as AndesPagerAdapter
            addDynamicPage(adapter.views[0])
        }

        private fun addDynamicPage(container: View) {
            val binding = AndesuiStickyScrollviewDialogBinding.bind(container)

            val buttonGroupCreator = object : AndesButtonGroupCreator {
                override fun create(modalInterface: AndesModalInterface): AndesButtonGroupData {

                    return AndesButtonGroupData(
                        AndesButtonGroup(
                            context = this@StickyScrollViewShowcaseActivity,
                            buttonList = listOf(
                                AndesButton(this@StickyScrollViewShowcaseActivity, buttonText = "Button 1").apply {
                                    setOnClickListener {
                                        Toast.makeText(applicationContext, "Click en button 1", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                AndesButton(this@StickyScrollViewShowcaseActivity, buttonText = "Button 2").apply {
                                    setOnClickListener {
                                        Toast.makeText(applicationContext, "Click en button 2", Toast.LENGTH_SHORT).show()
                                        modalInterface.dismiss()
                                    }
                                }
                            ),
                            distribution = AndesButtonGroupDistribution.VERTICAL
                        )
                    )
                }
            }

            binding.changeButton.setOnClickListener {
                val isCheckedHeaderSticky = binding.stickyHeader.status == AndesCheckboxStatus.SELECTED
                val isCheckedFooterSticky = binding.stickyFooter.status == AndesCheckboxStatus.SELECTED

                val modal = AndesModal.cardBuilder(AndesModalContent("Andes Modal", "${resources.getString(R.string.andes_demoapp_playground_lorem)}${resources.getString(R.string.andes_demoapp_playground_lorem)}${resources.getString(R.string.andes_demoapp_playground_lorem)}${resources.getString(R.string.andes_demoapp_playground_lorem)}"))
                    .withContentVariation(AndesModalCardContentVariation.NONE)
                    .withIsHeaderFixed(isCheckedHeaderSticky)
                    .withIsButtonGroupFixed(isCheckedFooterSticky)
                    .withButtonGroupCreator(buttonGroupCreator)
                    .withIsDismissible(true)
                    .withOnDismissCallback {  }
                    .withOnModalShowCallback {  }
                    .build()

                modal.show(this)
            }
        }
}