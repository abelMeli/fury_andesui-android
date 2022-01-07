package com.mercadolibre.android.andesui.demoapp.components.radiobutton

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicRadiobuttonBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticRadiobuttonBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticRadiobuttonGroupBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.radiobutton.align.AndesRadioButtonAlign
import com.mercadolibre.android.andesui.radiobutton.status.AndesRadioButtonStatus
import com.mercadolibre.android.andesui.radiobutton.type.AndesRadioButtonType
import com.mercadolibre.android.andesui.radiobuttongroup.AndesRadioButtonGroup
import com.mercadolibre.android.andesui.radiobuttongroup.RadioButtonItem
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

class RadioButtonShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_radiobutton)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf(
                AndesuiDynamicRadiobuttonBinding.inflate(layoutInflater).root,
                AndesuiStaticRadiobuttonBinding.inflate(layoutInflater).root,
                AndesuiStaticRadiobuttonGroupBinding.inflate(layoutInflater).root
        ))
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
        addStaticPage(adapter.views[1])
        addStaticSecondPage(adapter.views[2])
    }

    @Suppress("ComplexMethod", "LongMethod")
    private fun addDynamicPage(container: View) {
        val binding = AndesuiDynamicRadiobuttonBinding.bind(container)
        val radioButton = binding.dynamicPageRadiobutton

        val spinnerType = binding.spinnerType
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_radiobutton_type_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerType.adapter = adapter
        }

        val spinnerAlign = binding.spinnerAlign
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_radiobutton_align_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerAlign.adapter = adapter
        }

        val spinnerStatus = binding.spinnerStatus
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_radiobutton_status_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerStatus.adapter = adapter
        }

        val andesTextfield = binding.andesTextfield

        binding.buttonClear.setOnClickListener {
            spinnerType.setSelection(0)
            spinnerAlign.setSelection(0)
            spinnerStatus.setSelection(0)

            andesTextfield.text = ""
            andesTextfield.state = AndesTextfieldState.IDLE
            andesTextfield.helper = null

            radioButton.align = AndesRadioButtonAlign.LEFT
            radioButton.type = AndesRadioButtonType.IDLE
            radioButton.status = AndesRadioButtonStatus.UNSELECTED
            radioButton.text = resources.getString(R.string.andes_radiobutton_text)
        }

        binding.buttonUpdate.setOnClickListener {
            if (andesTextfield.text.isNullOrEmpty()) {
                andesTextfield.state = AndesTextfieldState.ERROR
                andesTextfield.helper = "Este campo es requerido"
                return@setOnClickListener
            } else {
                andesTextfield.state = AndesTextfieldState.IDLE
                andesTextfield.helper = null
            }

            val type = when (spinnerType.selectedItem) {
                "Idle" -> AndesRadioButtonType.IDLE
                "Error" -> AndesRadioButtonType.ERROR
                "Disabled" -> AndesRadioButtonType.DISABLED
                else -> AndesRadioButtonType.IDLE
            }
            val align = when (spinnerAlign.selectedItem) {
                "Left" -> AndesRadioButtonAlign.LEFT
                "Right" -> AndesRadioButtonAlign.RIGHT
                else -> AndesRadioButtonAlign.LEFT
            }
            val status = when (spinnerStatus.selectedItem) {
                "Unselected" -> AndesRadioButtonStatus.UNSELECTED
                "Selected" -> AndesRadioButtonStatus.SELECTED
                else -> AndesRadioButtonStatus.UNSELECTED
            }
            radioButton.align = align
            radioButton.type = type
            radioButton.status = status
            radioButton.text = andesTextfield.text
        }
    }

    private fun addStaticPage(container: View) {
        AndesuiStaticRadiobuttonBinding.bind(container)
            .andesuiDemoappAndesRadiobuttonSpecsButton.setOnClickListener {
            launchSpecs(it.context, AndesSpecs.RADIOBUTTON)
        }
    }

    private fun addStaticSecondPage(container: View) {
        val binding = AndesuiStaticRadiobuttonGroupBinding.bind(container)
        val radioButtons = arrayListOf<RadioButtonItem>()
        radioButtons.add(RadioButtonItem("item 1", AndesRadioButtonType.DISABLED))
        radioButtons.add(RadioButtonItem("item 2", AndesRadioButtonType.IDLE))
        radioButtons.add(RadioButtonItem("item 3", AndesRadioButtonType.IDLE))
        radioButtons.add(RadioButtonItem("item 4", AndesRadioButtonType.IDLE))
        radioButtons.add(RadioButtonItem("item 5", AndesRadioButtonType.IDLE))

        val radioButtonGroup = binding.radioButtonGroup1
        radioButtonGroup.selected = 1
        radioButtonGroup.radioButtons = radioButtons
        radioButtonGroup.setupCallback(
                object : AndesRadioButtonGroup.OnRadioButtonCheckedChanged {
                    override fun onRadioButtonCheckedChanged(index: Int) {
                        Toast.makeText(applicationContext, "Radiobutton clicked, index: $index", Toast.LENGTH_LONG).show()
                    }
                }
        )

        val radioButtonGroupHorizontal = binding.radioButtonGroup2
        radioButtonGroupHorizontal.selected = 1
        radioButtonGroupHorizontal.radioButtons = radioButtons
        radioButtonGroupHorizontal.setupCallback(
                object : AndesRadioButtonGroup.OnRadioButtonCheckedChanged {
                    override fun onRadioButtonCheckedChanged(index: Int) {
                        Toast.makeText(applicationContext, "Radiobutton clicked, index: $index", Toast.LENGTH_LONG).show()
                    }
                }
        )
    }
}
