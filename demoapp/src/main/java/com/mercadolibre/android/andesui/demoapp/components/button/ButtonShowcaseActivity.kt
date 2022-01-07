package com.mercadolibre.android.andesui.demoapp.components.button

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonIconOrientation
import com.mercadolibre.android.andesui.button.size.AndesButtonSize
import com.mercadolibre.android.andesui.buttonprogress.status.AndesButtonProgressAction
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicButtonProgressBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicButtonsBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticButtonsBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

@Suppress("TooManyFunctions")
class ButtonShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager
    private var serverCallsRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_button)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(
            listOf<View>(
                AndesuiDynamicButtonsBinding.inflate(layoutInflater).root,
                AndesuiDynamicButtonProgressBinding.inflate(layoutInflater).root,
                AndesuiStaticButtonsBinding.inflate(layoutInflater).root
            )
        )
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
        addDynamicProgressPage(adapter.views[1])
        addStaticPage(adapter.views[2])
    }

    private fun addDynamicPage(container: View) {
        val binding = AndesuiDynamicButtonsBinding.bind(container)
        val andesButton = binding.andesButton
        val loadingCheckbox = binding.checkboxLoading
        val enabledCheckbox = binding.checkboxEnabled
        val text = binding.labelText

        val sizeSpinner = binding.sizeSpinner
        ArrayAdapter.createFromResource(
            this,
            R.array.andes_button_size_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sizeSpinner.adapter = adapter
        }

        val hierarchySpinner = binding.hierarchySpinner
        ArrayAdapter.createFromResource(
            this,
            R.array.andes_button_hierarchy_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            hierarchySpinner.adapter = adapter
        }

        andesButton.setOnClickListener {
            if (loadingCheckbox.status == AndesCheckboxStatus.SELECTED) {
                (it as AndesButton).isLoading = !it.isLoading
            }
        }

        binding.clearButton.setOnClickListener {
            sizeSpinner.setSelection(0)
            hierarchySpinner.setSelection(0)

            enabledCheckbox.status = AndesCheckboxStatus.SELECTED
            loadingCheckbox.status = AndesCheckboxStatus.UNSELECTED

            andesButton.size = AndesButtonSize.LARGE
            andesButton.hierarchy = AndesButtonHierarchy.LOUD
            andesButton.isEnabled = true
            andesButton.text = getString(R.string.andes_button)

            text.text = null
        }

        binding.changeButton.setOnClickListener {
            if (text.text.isNullOrEmpty()) {
                text.state = AndesTextfieldState.ERROR
                text.helper = "Campo obligatorio"
                return@setOnClickListener
            } else {
                text.state = AndesTextfieldState.IDLE
            }
            val size = when (sizeSpinner.selectedItem) {
                "Large" -> AndesButtonSize.LARGE
                "Medium" -> AndesButtonSize.MEDIUM
                "Small" -> AndesButtonSize.SMALL
                else -> AndesButtonSize.LARGE
            }
            val hierarchy = when (hierarchySpinner.selectedItem) {
                "Loud" -> AndesButtonHierarchy.LOUD
                "Quiet" -> AndesButtonHierarchy.QUIET
                "Transparent" -> AndesButtonHierarchy.TRANSPARENT
                else -> AndesButtonHierarchy.LOUD
            }

            andesButton.text = text.text
            andesButton.size = size
            andesButton.hierarchy = hierarchy
            andesButton.isEnabled = enabledCheckbox.status == AndesCheckboxStatus.SELECTED
        }
    }

    private fun addDynamicProgressPage(container: View) {
        val binding = AndesuiDynamicButtonProgressBinding.bind(container)
        val andesButtonLoud = binding.andesButtonLoud
        val andesButtonQuiet = binding.andesButtonQuiet

        val loadingText = binding.loadingTextField
        val from = binding.fromTextField
        val to = binding.toTextField
        val duration = binding.durationTextField

        val startProgressLoadingButton = binding.startProgressLoading
        val pauseProgressLoadingButton = binding.pauseProgressLoading
        val cancelProgressLoadingButton = binding.cancelProgressLoading

        val serverCallsCheckbox = binding.serverCallsCheckbox

        startProgressLoadingButton.setOnClickListener {
            if (serverCallsCheckbox.status == AndesCheckboxStatus.SELECTED) {
                startProgressLoadingButton.isEnabled = false
                cancelProgressLoadingButton.isEnabled = true
                doStartWithServerCalls(listOf(andesButtonLoud, andesButtonQuiet), from, to, duration)
            } else {
                doStart(listOf(andesButtonLoud, andesButtonQuiet))
                uploadButtons(andesButtonLoud, startProgressLoadingButton, pauseProgressLoadingButton, cancelProgressLoadingButton)
            }
        }

        pauseProgressLoadingButton.setOnClickListener {
            doPauseResume(
                listOf(andesButtonLoud, andesButtonQuiet),
                startProgressLoadingButton,
                pauseProgressLoadingButton,
                cancelProgressLoadingButton
            )
            uploadButtons(andesButtonLoud, startProgressLoadingButton, pauseProgressLoadingButton, cancelProgressLoadingButton)
        }

        cancelProgressLoadingButton.setOnClickListener {
            doCancel(listOf(andesButtonLoud, andesButtonQuiet))
            uploadButtons(andesButtonLoud, startProgressLoadingButton, pauseProgressLoadingButton, cancelProgressLoadingButton)
        }

        uploadButtons(andesButtonLoud, startProgressLoadingButton, pauseProgressLoadingButton, cancelProgressLoadingButton)

        binding.changeButton.setOnClickListener {
            changeProgressValues(listOf(andesButtonLoud, andesButtonQuiet), loadingText, from, to, duration)
        }
        binding.clearButton.setOnClickListener {
            clearProgressValues(loadingText, from, to, duration)
        }
        clearProgressValues(loadingText, from, to, duration)
    }

    @Suppress("MagicNumber")
    private fun doStartWithServerCalls(
        andesButtons: List<AndesButton>,
        from: AndesTextfield,
        to: AndesTextfield,
        duration: AndesTextfield
    ) {
        val fromValue = from.text?.toInt() ?: 0
        val toValue = to.text?.toInt() ?: 0
        val durationValue = duration.text?.toLong() ?: 0L
        val waitInterval = durationValue + 1000

        val halfProgressLeft = (toValue - fromValue) / 2

        andesButtons.forEach {
            it.setProgressIndicatorTo(halfProgressLeft)
        }

        doStart(andesButtons)

        Thread {
            serverCallsRunning = true
            for (i in 1..3) {
                Thread.sleep(waitInterval)
                if (serverCallsRunning) {
                    runOnUiThread {
                        andesButtons.forEach {
                            val actualProgress = it.getProgressIndicatorValue()
                            it.setProgressIndicatorFrom(actualProgress)
                            it.setProgressIndicatorTo(((toValue - actualProgress) / 2) + actualProgress)
                            it.progressStatus = AndesButtonProgressAction.START
                        }
                    }
                } else {
                    break
                }
            }
            if (serverCallsRunning) {
                Thread.sleep(waitInterval)
                runOnUiThread {
                    andesButtons.forEach {
                        val actualProgress = it.getProgressIndicatorValue()
                        it.setProgressIndicatorFrom(actualProgress)
                        it.setProgressIndicatorTo(toValue)
                        it.progressStatus = AndesButtonProgressAction.START
                    }
                }
            }
        }.start()
    }

    private fun doStart(andesButtons: List<AndesButton>) {
        andesButtons.forEach {
            it.progressStatus = AndesButtonProgressAction.START
        }
    }

    private fun doPauseResume(
        andesButtons: List<AndesButton>,
        startProgressLoadingButton: AndesButton,
        pauseProgressLoadingButton: AndesButton,
        cancelProgressLoadingButton: AndesButton
    ) {
        andesButtons.forEach {
            if (it.progressStatus == AndesButtonProgressAction.PAUSE) {
                it.progressStatus = AndesButtonProgressAction.RESUME
                pauseProgressLoadingButton.text =
                    resources.getString(R.string.andes_message_pause_progress_loading)
                startProgressLoadingButton.isEnabled = false
                cancelProgressLoadingButton.isEnabled = true
            } else {
                it.progressStatus = AndesButtonProgressAction.PAUSE
                pauseProgressLoadingButton.text =
                    resources.getString(R.string.andes_message_resume_progress_loading)
                startProgressLoadingButton.isEnabled = true
                cancelProgressLoadingButton.isEnabled = false
            }
        }
    }

    private fun doCancel(andesButtons: List<AndesButton>) {
        serverCallsRunning = false
        andesButtons.forEach {
            it.progressStatus = AndesButtonProgressAction.CANCEL
        }
    }

    private fun uploadButtons(
        andesButton: AndesButton,
        startProgressLoadingButton: AndesButton,
        pauseProgressLoadingButton: AndesButton,
        cancelProgressLoadingButton: AndesButton
    ) {
        when (andesButton.progressStatus) {
            AndesButtonProgressAction.IDLE -> {
                startProgressLoadingButton.isEnabled = true
                pauseProgressLoadingButton.isEnabled = false
                cancelProgressLoadingButton.isEnabled = false
            }
            AndesButtonProgressAction.START -> {
                startProgressLoadingButton.isEnabled = false
                pauseProgressLoadingButton.isEnabled = true
                cancelProgressLoadingButton.isEnabled = true
            }
            AndesButtonProgressAction.RESUME -> {
                startProgressLoadingButton.isEnabled = false
                pauseProgressLoadingButton.isEnabled = true
                cancelProgressLoadingButton.isEnabled = true
            }
            AndesButtonProgressAction.PAUSE -> {
                startProgressLoadingButton.isEnabled = true
                pauseProgressLoadingButton.isEnabled = true
                cancelProgressLoadingButton.isEnabled = true
            }
            AndesButtonProgressAction.CANCEL -> {
                startProgressLoadingButton.isEnabled = true
                pauseProgressLoadingButton.isEnabled = false
                cancelProgressLoadingButton.isEnabled = false
            }
        }
    }

    private fun changeProgressValues(
        andesButtons: List<AndesButton>,
        loadingText: AndesTextfield,
        from: AndesTextfield,
        to: AndesTextfield,
        duration: AndesTextfield
    ) {
        andesButtons.forEach {
            it.progressLoadingText = loadingText.text
            it.setProgressIndicatorFrom(from.text?.toInt() ?: 0)
            it.setProgressIndicatorTo(to.text?.toInt() ?: 0)
            it.setProgressIndicatorDuration(duration.text?.toLong() ?: 0L)
        }
    }

    private fun clearProgressValues(
        loadingText: AndesTextfield,
        from: AndesTextfield,
        to: AndesTextfield,
        duration: AndesTextfield
    ) {
        loadingText.text = "Loading"
        from.text = "0"
        to.text = "200"
        duration.text = "5000"
    }

    private fun addStaticPage(container: View) {
        val binding = AndesuiStaticButtonsBinding.bind(container)
        ResourcesCompat.getDrawable(resources, R.drawable.andesui_icon_dynamic, null)?.let {
            binding.buttonLoudWithDrawable.setIconDrawable(
                it, AndesButtonIconOrientation.LEFT
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.andesui_icon_dynamic, null)?.let {
            binding.buttonQuietWithDrawable.setIconDrawable(
                it, AndesButtonIconOrientation.RIGHT
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.andesui_icon_dynamic, null)?.let {
            binding.buttonTransparentWithDrawable
                .setIconDrawable(
                    it, AndesButtonIconOrientation.LEFT
                )
        }

        binding.andesuiDemoappAndesSpecsButton.setOnClickListener {
            launchSpecs(container.context, AndesSpecs.BUTTON)
        }
    }

    fun buttonClicked(v: View) {
        if (v is AndesButton) {
            if (v.id == R.id.button_loud_with_loading ||
                v.id == R.id.button_quiet_with_loading ||
                v.id == R.id.button_transparent_with_loading
            ) {
                v.isLoading = !v.isLoading
            }
            Toast.makeText(applicationContext, "${v.text} clicked!", Toast.LENGTH_SHORT).show()
        }
    }
}
