package com.mercadolibre.android.andesui.demoapp.components.switchandes

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicSwitchBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticSwitchBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.Constants
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.switchandes.AndesSwitch
import com.mercadolibre.android.andesui.switchandes.align.AndesSwitchAlign
import com.mercadolibre.android.andesui.switchandes.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.switchandes.type.AndesSwitchType
import com.mercadolibre.android.andesui.textfield.AndesTextfield

@Suppress("TooManyFunctions")
class SwitchShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_switch)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(
            listOf(
                AndesuiDynamicSwitchBinding.inflate(layoutInflater).root,
                AndesuiStaticSwitchBinding.inflate(layoutInflater).root
            )
        )
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
        addStaticPage(adapter.views[1])
    }

    @Suppress("ComplexMethod", "LongMethod")
    private fun addDynamicPage(container: View) =
        with(AndesuiDynamicSwitchBinding.bind(container)) {
            configSpinnerType(dynamicPageSwitchSpinnerType)
            configSpinnerStatus(dynamicPageSwitchSpinnerStatus)
            configSpinnerAlign(dynamicPageSwitchSpinnerAlign)
            setupClearButton(
                dynamicPageSwitchButtonClear,
                dynamicPageSwitchSpinnerAlign,
                dynamicPageSwitchSpinnerStatus,
                dynamicPageSwitchSpinnerType,
                dynamicPageSwitchTextfield,
                dynamicPageSwitchTextfieldLines,
                dynamicPageSwitch
            )
            setupUpdateButton(
                dynamicPageSwitchButtonUpdate,
                dynamicPageSwitchSpinnerAlign,
                dynamicPageSwitchSpinnerStatus,
                dynamicPageSwitchSpinnerType,
                dynamicPageSwitchTextfield,
                dynamicPageSwitchTextfieldLines,
                dynamicPageSwitch
            )
        }

    private fun configSpinnerType(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            this,
            R.array.andes_switch_type_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun configSpinnerStatus(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            this,
            R.array.andes_switch_status_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun configSpinnerAlign(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            this,
            R.array.andes_switch_align_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    @Suppress("LongParameterList")
    private fun setupClearButton(
        clearButton: AndesButton,
        spinnerAlign: Spinner,
        spinnerStatus: Spinner,
        spinnerType: Spinner,
        switchTextfield: AndesTextfield,
        linesTextfield: AndesTextfield,
        dynamicSwitch: AndesSwitch
    ) {
        clearButton.setOnClickListener {
            spinnerType.setSelection(0)
            spinnerAlign.setSelection(0)
            spinnerStatus.setSelection(0)

            switchTextfield.text = Constants.EMPTY_STRING
            linesTextfield.text = Constants.EMPTY_STRING

            dynamicSwitch.status = AndesSwitchStatus.UNCHECKED
            dynamicSwitch.type = AndesSwitchType.ENABLED
            dynamicSwitch.align = AndesSwitchAlign.RIGHT
            dynamicSwitch.text = resources.getString(R.string.andes_switch_name_text)
            dynamicSwitch.titleNumberOfLines = 1
        }
    }

    @Suppress("LongParameterList")
    private fun setupUpdateButton(
        updateButton: AndesButton,
        spinnerAlign: Spinner,
        spinnerStatus: Spinner,
        spinnerType: Spinner,
        switchTextfield: AndesTextfield,
        linesTextfield: AndesTextfield,
        dynamicSwitch: AndesSwitch
    ) {
        updateButton.setOnClickListener {
            val type = when (spinnerType.selectedItem) {
                "Enabled" -> AndesSwitchType.ENABLED
                "Disabled" -> AndesSwitchType.DISABLED
                else -> AndesSwitchType.ENABLED
            }
            val align = when (spinnerAlign.selectedItem) {
                "Left" -> AndesSwitchAlign.LEFT
                "Right" -> AndesSwitchAlign.RIGHT
                else -> AndesSwitchAlign.LEFT
            }
            val status = when (spinnerStatus.selectedItem) {
                "Unchecked" -> AndesSwitchStatus.UNCHECKED
                "Checked" -> AndesSwitchStatus.CHECKED
                else -> AndesSwitchStatus.UNCHECKED
            }

            dynamicSwitch.text = switchTextfield.text
            dynamicSwitch.type = type
            dynamicSwitch.status = status
            dynamicSwitch.align = align
            if (!linesTextfield.text.isNullOrEmpty()) {
                dynamicSwitch.titleNumberOfLines = linesTextfield.text?.toInt() ?: 1
            }
        }
    }

    private fun addStaticPage(container: View) {
        AndesuiStaticSwitchBinding.bind(container).staticPageSwitchButtonMore
            .setOnClickListener {
                launchSpecs(this, AndesSpecs.SWITCH)
            }
    }
}
