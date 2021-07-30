package com.mercadolibre.android.andesui.demoapp.components.linearprogressindicator

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.PageIndicator
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.linearprogress.AndesLinearProgressIndicatorDeterminate
import com.mercadolibre.android.andesui.linearprogress.size.AndesLinearProgressSize
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

private const val TINT_DEFAULT = 0
private const val NUMBER_OF_STEPS_DEFAULT = 10
private const val NUMBER_OF_STEPS_EXAMPLE = 4
private const val MAX_NUMBER_OF_STEPS = 20
private const val MIN_NUMBER_OF_STEPS = 2
private const val STEP_ONE = 1
private const val STEP_THREE = 3
private const val HELPER_NUMBER_OF_STEPS = "MIN = $MIN_NUMBER_OF_STEPS / MAX = $MAX_NUMBER_OF_STEPS"

class LinearProgressShowcaseActivity : AppCompatActivity() {

    private lateinit var viewPager: CustomViewPager
    private var numberOfSteps = NUMBER_OF_STEPS_DEFAULT

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
        supportActionBar?.title =
            resources.getString(R.string.andes_demoapp_screen_linear_progress_indicator)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViewPager() {
        val inflater = LayoutInflater.from(this)
        viewPager = findViewById(R.id.andesui_viewpager)
        viewPager.adapter = AndesPagerAdapter(
            listOf<View>(
                inflater.inflate(R.layout.andesui_dynamic_linear_progress, null, false),
                inflater.inflate(R.layout.andesui_static_linear_progress, null, false)
            )
        )
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

    private fun addDynamicPage(container: View) {
        val nextStepButton = container.findViewById<AndesButton>(R.id.next_step_button)
        val previousStepButton = container.findViewById<AndesButton>(R.id.previous_step_button)
        val changeButton = container.findViewById<AndesButton>(R.id.change_button)
        val clearButton = container.findViewById<AndesButton>(R.id.clear_button)
        val jumpToStepButton = container.findViewById<AndesButton>(R.id.jump_to_step_button)
        val linearProgress =
            container.findViewById<AndesLinearProgressIndicatorDeterminate>(R.id.linear_progress)
        val jumpToStepField: AndesTextfield = container.findViewById(R.id.jump_to_step_text)
        val numberOfStepsField: AndesTextfield = container.findViewById(R.id.number_of_steps_text)
        val indicatorField: AndesTextfield = container.findViewById(R.id.indicator_text)
        val trackField: AndesTextfield = container.findViewById(R.id.track_text)
        val splitCheckbox = container.findViewById<AndesCheckbox>(R.id.split_checkbox)
        val sizeSpinner: Spinner = container.findViewById(R.id.size_spinner)

        numberOfStepsField.helper = HELPER_NUMBER_OF_STEPS
        jumpToStepField.helper = "MIN = $STEP_ONE / MAX = $numberOfSteps"

        ArrayAdapter.createFromResource(
            this,
            R.array.andes_linear_progress_indicator_size_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sizeSpinner.adapter = adapter
        }

        changeButton.setOnClickListener {
            val size = when (sizeSpinner.selectedItem) {
                "Large" -> AndesLinearProgressSize.LARGE
                "Small" -> AndesLinearProgressSize.SMALL
                else -> AndesLinearProgressSize.SMALL
            }

            linearProgress.size = size

            if (!numberOfStepsField.text.isNullOrEmpty()) {
                val numberOfSteps = numberOfStepsField.text!!.toInt()
                if (numberOfSteps in MIN_NUMBER_OF_STEPS..MAX_NUMBER_OF_STEPS) {
                    this.numberOfSteps = numberOfSteps
                    numberOfStepsField.state = AndesTextfieldState.IDLE
                    linearProgress.numberOfSteps = numberOfSteps
                } else {
                    numberOfStepsField.state = AndesTextfieldState.ERROR
                }
            }

            if (!indicatorField.text.isNullOrEmpty()) {
                val color = getCustomColor(indicatorField.text!!)
                if (color != null) {
                    indicatorField.state = AndesTextfieldState.IDLE
                    indicatorField.helper =
                        getString(R.string.andes_linear_progress_indicator_showcase_indicator_tint_helper)
                    linearProgress.indicatorTint = color
                } else {
                    indicatorField.state = AndesTextfieldState.ERROR
                    indicatorField.helper = "Color unknown"
                }
            }

            if (!trackField.text.isNullOrEmpty()) {
                val color = getCustomColor(trackField.text!!)
                if (color != null) {
                    trackField.state = AndesTextfieldState.IDLE
                    trackField.helper =
                        getString(R.string.andes_linear_progress_indicator_showcase_track_tint_helper)
                    linearProgress.trackTint = color
                } else {
                    trackField.state = AndesTextfieldState.ERROR
                    trackField.helper = "Color unknown"
                }
            }

            linearProgress.isSplit = splitCheckbox.status == AndesCheckboxStatus.SELECTED
        }

        nextStepButton.setOnClickListener {
            linearProgress.nextStep()
        }

        previousStepButton.setOnClickListener {
            linearProgress.previousStep()
        }

        jumpToStepButton.setOnClickListener {
            if (!jumpToStepField.text.isNullOrEmpty()) {
                val step = jumpToStepField.text!!.toInt()
                if (step in STEP_ONE..numberOfSteps) {
                    jumpToStepField.state = AndesTextfieldState.IDLE
                    linearProgress.jumpToStep(step)
                } else {
                    jumpToStepField.state = AndesTextfieldState.ERROR
                }
                jumpToStepField.helper = "MIN = $STEP_ONE / MAX = $numberOfSteps"
            }
        }

        clearButton.setOnClickListener {
            sizeSpinner.setSelection(0)
            splitCheckbox.status = AndesCheckboxStatus.UNSELECTED
            numberOfStepsField.text = null
            indicatorField.text = null
            trackField.text = null

            linearProgress.size = AndesLinearProgressSize.SMALL
            linearProgress.isSplit = false
            linearProgress.numberOfSteps = NUMBER_OF_STEPS_DEFAULT
            this.numberOfSteps = NUMBER_OF_STEPS_DEFAULT
            linearProgress.indicatorTint = TINT_DEFAULT
            linearProgress.trackTint = TINT_DEFAULT
        }
    }

    private fun getCustomColor(hexString: String) = try {
        Color.parseColor(hexString)
    } catch (e: IllegalArgumentException) {
        null
    }

    private fun addStaticPage(container: View) {
        container.findViewById<AndesLinearProgressIndicatorDeterminate>(R.id.linear_progress_indicator)
            .jumpToStep(STEP_THREE)

        bindAndesSpecsButton(container)
    }

    private fun bindAndesSpecsButton(container: View) {
        container.findViewById<AndesButton>(R.id.andesui_demoapp_andes_specs_progress)
            .setOnClickListener {
                launchSpecs(container.context, AndesSpecs.PROGRESS_INDICATOR)
            }
    }
}
