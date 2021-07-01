package com.mercadolibre.android.andesui.demoapp.components.switchandes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.Constants
import com.mercadolibre.android.andesui.demoapp.utils.PageIndicator
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.switchandes.AndesSwitch
import com.mercadolibre.android.andesui.switchandes.align.AndesSwitchAlign
import com.mercadolibre.android.andesui.switchandes.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.switchandes.type.AndesSwitchType
import com.mercadolibre.android.andesui.textfield.AndesTextfield

@Suppress("TooManyFunctions")
class SwitchShowcaseActivity : AppCompatActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.andesui_showcase_main)

        initActionBar()
        initViewPager()
        attachIndicator()
        loadViews()
    }

    private fun initActionBar() {
        setSupportActionBar(findViewById(R.id.andesui_nav_bar))
        supportActionBar?.title = resources.getString(R.string.andes_demoapp_screen_switch)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViewPager() {
        val inflater = LayoutInflater.from(this)
        viewPager = findViewById(R.id.andesui_viewpager)
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
                inflater.inflate(R.layout.andesui_dynamic_switch, null, false),
                inflater.inflate(R.layout.andesui_static_switch, null, false)
        ))
    }

    private fun attachIndicator() {
        val indicator = findViewById<PageIndicator>(R.id.page_indicator)
        indicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
        addStaticPage(adapter.views[1])
    }

    @Suppress("ComplexMethod", "LongMethod")
    private fun addDynamicPage(container: View) {
        val dynamicSwitch: AndesSwitch = container.findViewById(R.id.dynamic_page_switch)
        val spinnerType = configSpinnerType(container)
        val spinnerStatus = configSpinnerStatus(container)
        val spinnerAlign = configSpinnerAlign(container)
        val updateButton: AndesButton = container.findViewById(R.id.dynamic_page_switch_button_update)
        val clearButton: AndesButton = container.findViewById(R.id.dynamic_page_switch_button_clear)
        val switchTextfield: AndesTextfield = container.findViewById(R.id.dynamic_page_switch_textfield)
        val linesTextfield: AndesTextfield = container.findViewById(R.id.dynamic_page_switch_textfield_lines)

        setupClearButton(clearButton, spinnerAlign, spinnerStatus, spinnerType, switchTextfield, linesTextfield, dynamicSwitch)
        setupUpdateButton(updateButton, spinnerAlign, spinnerStatus, spinnerType, switchTextfield, linesTextfield, dynamicSwitch)
    }

    private fun configSpinnerType(container: View): Spinner {
        val spinnerType: Spinner = container.findViewById(R.id.dynamic_page_switch_spinner_type)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_switch_type_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerType.adapter = adapter
        }
        return spinnerType
    }

    private fun configSpinnerStatus(container: View): Spinner {
        val spinnerStatus: Spinner = container.findViewById(R.id.dynamic_page_switch_spinner_status)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_switch_status_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerStatus.adapter = adapter
        }
        return spinnerStatus
    }

    private fun configSpinnerAlign(container: View): Spinner {
        val spinnerAlign: Spinner = container.findViewById(R.id.dynamic_page_switch_spinner_align)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_switch_align_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerAlign.adapter = adapter
        }
        return spinnerAlign
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
        container.findViewById<AndesButton>(R.id.static_page_switch_button_more).setOnClickListener {
            launchSpecs(this, AndesSpecs.SWITCH)
        }
    }
}
