package com.mercadolibre.android.andesui.demoapp.components.button

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonIconOrientation
import com.mercadolibre.android.andesui.button.size.AndesButtonSize
import com.mercadolibre.android.andesui.buttonprogress.status.AndesButtonProgressAction
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.PageIndicator
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import kotlinx.android.synthetic.main.andesui_static_buttons.*

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
        val inflater = LayoutInflater.from(this)
        viewPager = findViewById(R.id.andesui_viewpager)
        viewPager.adapter = AndesPagerAdapter(
            listOf<View>(
                inflater.inflate(R.layout.andesui_dynamic_buttons, null, false),
                inflater.inflate(R.layout.andesui_dynamic_button_progress, null, false),
                inflater.inflate(R.layout.andesui_static_buttons, null, false)
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
        addDynamicProgressPage(adapter.views[1])
        addStaticPage(adapter.views[2])
    }

    private fun addDynamicPage(container: View) {
        val andesButton = container.findViewById<AndesButton>(R.id.andes_button)
        val changeButton = container.findViewById<AndesButton>(R.id.change_button)
        val clearButton = container.findViewById<AndesButton>(R.id.clear_button)
        val loadingCheckbox = container.findViewById<AndesCheckbox>(R.id.checkbox_loading)
        val enabledCheckbox = container.findViewById<AndesCheckbox>(R.id.checkbox_enabled)
        val text = container.findViewById<AndesTextfield>(R.id.label_text)

        val sizeSpinner: Spinner = container.findViewById(R.id.size_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.andes_button_size_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sizeSpinner.adapter = adapter
        }

        val hierarchySpinner: Spinner = container.findViewById(R.id.hierarchy_spinner)
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

        clearButton.setOnClickListener {
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

        changeButton.setOnClickListener {
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
        val andesButtonLoud = container.findViewById<AndesButton>(R.id.andes_button_loud)
        val andesButtonQuiet = container.findViewById<AndesButton>(R.id.andes_button_quiet)

        val loadingText = container.findViewById<AndesTextfield>(R.id.loading_text_field)
        val from = container.findViewById<AndesTextfield>(R.id.from_text_field)
        val to = container.findViewById<AndesTextfield>(R.id.to_text_field)
        val duration = container.findViewById<AndesTextfield>(R.id.duration_text_field)

        val startProgressLoadingButton =
            container.findViewById<AndesButton>(R.id.start_progress_loading)
        val pauseProgressLoadingButton =
            container.findViewById<AndesButton>(R.id.pause_progress_loading)
        val cancelProgressLoadingButton =
            container.findViewById<AndesButton>(R.id.cancel_progress_loading)

        val serverCallsCheckbox = container.findViewById<AndesCheckbox>(R.id.server_calls_checkbox)

        val changeButton = container.findViewById<AndesButton>(R.id.change_button)
        val clearButton = container.findViewById<AndesButton>(R.id.clear_button)

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

        changeButton.setOnClickListener {
            changeProgressValues(listOf(andesButtonLoud, andesButtonQuiet), loadingText, from, to, duration)
        }
        clearButton.setOnClickListener {
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
        ResourcesCompat.getDrawable(resources, R.drawable.andesui_icon_dynamic, null)?.let {
            container.findViewById<AndesButton>(R.id.button_loud_with_drawable).setIconDrawable(
                it, AndesButtonIconOrientation.LEFT
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.andesui_icon_dynamic, null)?.let {
            container.findViewById<AndesButton>(R.id.button_quiet_with_drawable).setIconDrawable(
                it, AndesButtonIconOrientation.RIGHT
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.andesui_icon_dynamic, null)?.let {
            container.findViewById<AndesButton>(R.id.button_transparent_with_drawable)
                .setIconDrawable(
                    it, AndesButtonIconOrientation.LEFT
                )
        }

        bindAndesSpecsButton(container)
    }

    fun buttonClicked(v: View) {
        if (v is AndesButton) {
            if (v.id == button_loud_with_loading.id ||
                v.id == button_quiet_with_loading.id ||
                v.id == button_transparent_with_loading.id
            ) {
                v.isLoading = !v.isLoading
            }
            Toast.makeText(this, "${v.text} clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindAndesSpecsButton(container: View) {
        container.findViewById<AndesButton>(R.id.andesui_demoapp_andes_specs_button)
            .setOnClickListener {
                launchSpecs(container.context, AndesSpecs.BUTTON)
            }
    }
}
