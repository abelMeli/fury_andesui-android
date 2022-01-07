package com.mercadolibre.android.andesui.demoapp.components.linearprogressindicator

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicLinearProgressBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticLinearProgressBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.linearprogress.size.AndesLinearProgressSize
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

private const val TINT_DEFAULT = 0
private const val NUMBER_OF_STEPS_DEFAULT = 10
private const val MAX_NUMBER_OF_STEPS = 20
private const val MIN_NUMBER_OF_STEPS = 2
private const val STEP_ONE = 1
private const val STEP_THREE = 3
private const val HELPER_NUMBER_OF_STEPS = "MIN = $MIN_NUMBER_OF_STEPS / MAX = $MAX_NUMBER_OF_STEPS"

class LinearProgressShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager
    private var numberOfSteps = NUMBER_OF_STEPS_DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_linear_progress_indicator)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(
            listOf(
                AndesuiDynamicLinearProgressBinding.inflate(layoutInflater).root,
                AndesuiStaticLinearProgressBinding.inflate(layoutInflater).root
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

    private fun addDynamicPage(container: View) {
        val binding = AndesuiDynamicLinearProgressBinding.bind(container)
        val nextStepButton = binding.nextStepButton
        val previousStepButton = binding.previousStepButton
        val changeButton = binding.changeButton
        val clearButton = binding.clearButton
        val jumpToStepButton = binding.jumpToStepButton
        val linearProgress = binding.linearProgress
        val jumpToStepField = binding.jumpToStepText
        val numberOfStepsField = binding.numberOfStepsText
        val indicatorField = binding.indicatorText
        val trackField = binding.trackText
        val splitCheckbox = binding.splitCheckbox
        val sizeSpinner = binding.sizeSpinner

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
        val binding = AndesuiStaticLinearProgressBinding.bind(container)
        binding.linearProgressIndicator.jumpToStep(STEP_THREE)
        binding.andesuiDemoappAndesSpecsProgress.setOnClickListener {
            launchSpecs(container.context, AndesSpecs.PROGRESS_INDICATOR)
        }
    }
}
