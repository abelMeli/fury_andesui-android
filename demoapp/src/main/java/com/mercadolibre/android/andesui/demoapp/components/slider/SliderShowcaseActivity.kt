package com.mercadolibre.android.andesui.demoapp.components.slider

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat
import com.google.android.material.slider.LabelFormatter
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicSliderBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticSliderBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.slider.AndesSlider
import com.mercadolibre.android.andesui.slider.state.AndesSliderState
import com.mercadolibre.android.andesui.slider.steps.AndesSliderSteps
import com.mercadolibre.android.andesui.slider.type.AndesSliderType
import com.mercadolibre.android.andesui.switchandes.AndesSwitch
import com.mercadolibre.android.andesui.switchandes.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import java.text.NumberFormat
import java.util.Currency

class SliderShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_slider)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(
            listOf<View>(
                AndesuiDynamicSliderBinding.inflate(layoutInflater).root,
                AndesuiStaticSliderBinding.inflate(layoutInflater).root
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
        val slider: AndesSlider = container.findViewById(R.id.slider)
        val clearButton: AndesButton = container.findViewById(R.id.buttonClear)
        val changeButton: AndesButton = container.findViewById(R.id.buttonUpdate)

        val hasAction: AndesSwitch = container.findViewById(R.id.switchState)
        val min: AndesTextfield = container.findViewById(R.id.min)
        val max: AndesTextfield = container.findViewById(R.id.max)
        val value: AndesTextfield = container.findViewById(R.id.value)
        val labelText: AndesTextfield = container.findViewById(R.id.label_text)

        hasAction.status = AndesSwitchStatus.CHECKED
        hasAction.setOnStatusChangeListener(object : AndesSwitch.OnStatusChangeListener {
            override fun onStatusChange(andesSwitch: AndesSwitchStatus) {
                if (andesSwitch == AndesSwitchStatus.CHECKED) {
                    slider.state = AndesSliderState.IDLE
                } else {
                    slider.state = AndesSliderState.DISABLED
                }
            }
        })
        val spinnerSteps: Spinner = container.findViewById(R.id.spinnerSteps)
        ArrayAdapter.createFromResource(
            this,
            R.array.andes_slider_steps_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSteps.adapter = adapter
        }

        val spinnerType: Spinner = container.findViewById(R.id.spinnerType)
        ArrayAdapter.createFromResource(
            this,
            R.array.andes_slider_type_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerType.adapter = adapter
        }

        clearButton.setOnClickListener {
            resetSlider(slider)
        }

        changeButton.setOnClickListener {
            if (min.text.isNullOrEmpty()) {
                min.state = AndesTextfieldState.ERROR
                min.helper = "Este campo es requerido"
                return@setOnClickListener
            } else {
                min.state = AndesTextfieldState.IDLE
                min.helper = null
            }

            if (max.text.isNullOrEmpty()) {
                max.state = AndesTextfieldState.ERROR
                max.helper = "Este campo es requerido"
                return@setOnClickListener
            } else {
                max.state = AndesTextfieldState.IDLE
                max.helper = null
            }

            if (!value.text.isNullOrEmpty()) {
                slider.value = value.text!!.toFloat()
            }

            val state = when (hasAction.status) {
                AndesSwitchStatus.CHECKED -> AndesSliderState.IDLE
                else -> AndesSliderState.DISABLED
            }

            slider.min = min.text!!.toFloat()
            slider.max = max.text!!.toFloat()
            slider.state = state
            slider.text = labelText.text
            slider.steps = sliderSteps(spinnerSteps.selectedItem as String)
            slider.type = sliderType(spinnerType.selectedItem as String)
        }

        slider.setOnValueChangedListener(object : AndesSlider.OnValueChangedListener {
            override fun onValueChanged(value: Float) {
                Log.i("ANDES", "SLIDER VALUE:$value")
            }
        })

        container.findViewById<AndesButton>(R.id.andesui_demoapp_andes_slider_specs_button)
            .setOnClickListener { launchSpecs(it.context, AndesSpecs.SLIDER) }

        setFormatter(slider)
    }

    private fun setFormatter(slider: AndesSlider) {
        slider.setValueLabelFormatter(LabelFormatter { value ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("USD")
            format.format(value.toDouble())
        })
    }

    private fun sliderSteps(item: String): AndesSliderSteps {
        val steps = Integer.parseInt(item)
        return if (steps == 0) {
            AndesSliderSteps.None
        } else {
            AndesSliderSteps.Custom(steps)
        }
    }

    private fun sliderType(item: String): AndesSliderType {
        return when (item) {
            "Simple" -> AndesSliderType.Simple
            "Icon" -> {
                val drawable =
                    ContextCompat.getDrawable(this, R.drawable.andes_ui_placeholder_imagen_24)
                AndesSliderType.Icon(drawable, drawable)
            }
            else -> AndesSliderType.Limits("Left", "Right")
        }
    }

    private fun resetSlider(slider: AndesSlider) {
        slider.min = DEFAULT
        slider.steps = AndesSliderSteps.None
        slider.value = DEFAULT
        slider.max = DEFAULT_MAX_VALUE
        slider.state = AndesSliderState.IDLE
        slider.text = null
        slider.type = AndesSliderType.Simple
    }

    companion object {
        private const val DEFAULT_MAX_VALUE = 100F
        private const val DEFAULT = 0F
    }
}
