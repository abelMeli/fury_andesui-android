package com.mercadolibre.android.andesui.demoapp.components.checkbox

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.mercadolibre.android.andesui.checkbox.align.AndesCheckboxAlign
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.checkbox.type.AndesCheckboxType
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicCheckboxBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticCheckboxBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

class CheckboxShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_checkbox)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
            AndesuiDynamicCheckboxBinding.inflate(layoutInflater).root,
            AndesuiStaticCheckboxBinding.inflate(layoutInflater).root
        ))
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
    private fun addDynamicPage(container: View) {
        val binding = AndesuiDynamicCheckboxBinding.bind(container)
        val checkbox = binding.andesCheckbox

        val spinnerType = binding.spinnerType
        ArrayAdapter.createFromResource(
                this, R.array.andes_checkbox_type_spinner, android.R.layout.simple_spinner_item)
                .let { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerType.adapter = adapter
                }

        val spinnerAlign = binding.spinnerAlign
        ArrayAdapter.createFromResource(
                this, R.array.andes_checkbox_align_spinner, android.R.layout.simple_spinner_item)
                .let { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerAlign.adapter = adapter
                }

        val spinnerStatus = binding.spinnerStatus
        ArrayAdapter.createFromResource(
                this, R.array.andes_checkbox_status_spinner, android.R.layout.simple_spinner_item)
                .let { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerStatus.adapter = adapter
                }

        val numberOfLinesTextField = binding.numberOfLinesTextField

        val clearButton = binding.buttonClear
        val changeButton = binding.buttonUpdate
        val andesTextfield = binding.andesTextfield

        clearButton.setOnClickListener {
            spinnerType.setSelection(0)
            spinnerAlign.setSelection(0)
            spinnerStatus.setSelection(0)
            numberOfLinesTextField.text = ""

            andesTextfield.text = ""
            andesTextfield.state = AndesTextfieldState.IDLE
            andesTextfield.helper = null

            checkbox.align = AndesCheckboxAlign.LEFT
            checkbox.type = AndesCheckboxType.IDLE
            checkbox.status = AndesCheckboxStatus.UNSELECTED
            checkbox.text = resources.getString(R.string.andes_checkbox_text)
            checkbox.titleNumberOfLines = 1
        }

        changeButton.setOnClickListener {
            if (andesTextfield.text.isNullOrEmpty()) {
                andesTextfield.state = AndesTextfieldState.ERROR
                andesTextfield.helper = "Este campo es requerido"
                return@setOnClickListener
            } else {
                andesTextfield.state = AndesTextfieldState.IDLE
                andesTextfield.helper = null
            }

            val type = when (spinnerType.selectedItem) {
                "Idle" -> AndesCheckboxType.IDLE
                "Error" -> AndesCheckboxType.ERROR
                "Disabled" -> AndesCheckboxType.DISABLED
                else -> AndesCheckboxType.IDLE
            }
            val align = when (spinnerAlign.selectedItem) {
                "Left" -> AndesCheckboxAlign.LEFT
                "Right" -> AndesCheckboxAlign.RIGHT
                else -> AndesCheckboxAlign.LEFT
            }
            val status = when (spinnerStatus.selectedItem) {
                "Unselected" -> AndesCheckboxStatus.UNSELECTED
                "Selected" -> AndesCheckboxStatus.SELECTED
                "Undefined" -> AndesCheckboxStatus.UNDEFINED
                else -> AndesCheckboxStatus.UNSELECTED
            }
            checkbox.align = align
            checkbox.type = type
            checkbox.status = status
            checkbox.text = andesTextfield.text
            if (numberOfLinesTextField.text.isNullOrEmpty()) {
                checkbox.titleNumberOfLines = 1
            } else {
                checkbox.titleNumberOfLines = numberOfLinesTextField.text!!.toInt()
            }
        }
    }

    private fun addStaticPage(container: View) {
        val binding = AndesuiStaticCheckboxBinding.bind(container)
        binding.staticCheckboxSecond.apply {
            val links = listOf(
                AndesBodyLink(LINK_INDEX_FIRST_START, LINK_INDEX_FIRST_END)
            )
            bodyLinks = AndesBodyLinks(links) {
                Toast.makeText(applicationContext, "Navegar a Términos y condiciones", Toast.LENGTH_SHORT).show()
            }
        }

        binding.staticCheckboxThird.apply {
            val links = listOf(
                AndesBodyLink(LINK_INDEX_SECOND_START, LINK_INDEX_SECOND_END),
                AndesBodyLink(LINK_INDEX_THIRD_START, LINK_INDEX_THIRD_END)
            )
            bodyLinks = AndesBodyLinks(links) {
                val toastText = when (it) {
                    0 -> "Navegar a normativa"
                    1 -> "Navegar a comunicaciones"
                    else -> ""
                }
                Toast.makeText(applicationContext, toastText, Toast.LENGTH_SHORT).show()
            }
        }

        binding.andesuiDemoappAndesCheckboxSpecsButton.setOnClickListener {
            launchSpecs(it.context, AndesSpecs.CHECKBOX)
        }
    }

    companion object {
        private const val LINK_INDEX_FIRST_START = 11
        private const val LINK_INDEX_FIRST_END = 33
        private const val LINK_INDEX_SECOND_START = 16
        private const val LINK_INDEX_SECOND_END = 25
        private const val LINK_INDEX_THIRD_START = 61
        private const val LINK_INDEX_THIRD_END = 75
    }
}
