package com.mercadolibre.android.andesui.demoapp.components.feedbackscreen

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.ArrayRes
import androidx.constraintlayout.widget.Group
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicFeedbackScreenBinding
import com.mercadolibre.android.andesui.switchandes.AndesSwitch
import com.mercadolibre.android.andesui.switchandes.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.textfield.AndesTextarea
import com.mercadolibre.android.andesui.textfield.AndesTextfield

@Suppress("TooManyFunctions")
class FeedbackScreenDynamicPage {
    private lateinit var feedbackBodyMocks: AndesTextfield
    private lateinit var feedbackColorGroup: Group
    private lateinit var updateButton: AndesButton
    private lateinit var feedbackHeaderDescription: AndesTextarea
    private lateinit var feedbackHeaderHighlight: AndesTextfield
    private lateinit var feedbackHeaderOverline: AndesTextfield
    private lateinit var feedbackHeaderOverlineSwitch: AndesSwitch
    private lateinit var feedbackHeaderTitle: AndesTextfield
    private lateinit var feedbackThumbnailSpinner: Spinner
    private lateinit var feedbackColorSpinner: Spinner
    private lateinit var feedbackTypeSpinner: Spinner
    private lateinit var closeButtonSwitch: AndesSwitch
    private lateinit var feedbackButtonGroupSpinner: Spinner

    companion object {
        const val CONGRATS = "Congrats"
        const val SIMPLE = "Simple"

        const val GREEN = "Green"
        const val ORANGE = "Orange"
        const val RED = "Red"

        const val NONE = "None"
        const val ICON = "Icon"
        const val IMAGE_CIRCLE = "Image Circle"

        const val MAX_CHARS = 240
    }

    fun create(context: Context, container: View) {
        initComponents(container)
        setupTypeSpinner(context)
        setupSpinnerComponent(context, feedbackColorSpinner, R.array.feedbackscreen_color_spinner)
        setupSpinnerComponent(
            context,
            feedbackThumbnailSpinner,
            R.array.feedbackscreen_header_thumbnail_spinner
        )
        setupSpinnerComponent(context, feedbackButtonGroupSpinner, R.array.feedbackscreen_button_group_quantity_spinner)
        setupOverlineSwitch()
        setupButtons(context)
    }

    private fun setupButtons(context: Context) {
        updateButton.setOnClickListener {
            context.startActivity(FeedbackScreenDynamicShowcase.getIntent(
                context,
                feedbackColorSpinner.selectedItem as String,
                feedbackTypeSpinner.selectedItem as String,
                feedbackThumbnailSpinner.selectedItem as String,
                feedbackBodyMocks.text?.toIntOrNull(),
                closeButtonSwitch.status.name,
                feedbackHeaderOverlineSwitch.status.name,
                feedbackHeaderTitle.text,
                feedbackHeaderOverline.text,
                feedbackHeaderDescription.text,
                feedbackHeaderHighlight.text,
                feedbackButtonGroupSpinner.selectedItem as String
            ))
        }
    }



    private fun setupOverlineSwitch() {
        feedbackHeaderOverlineSwitch.setOnStatusChangeListener(object :
            AndesSwitch.OnStatusChangeListener {
            override fun onStatusChange(status: AndesSwitchStatus) {
                setFeedbackOverlineSwitchVisibile(status == AndesSwitchStatus.CHECKED)
            }
        })
        setFeedbackOverlineSwitchVisibile(feedbackHeaderOverlineSwitch.status == AndesSwitchStatus.CHECKED)
    }

    private fun setFeedbackOverlineSwitchVisibile(isVisible: Boolean) {
        feedbackHeaderOverline.visibility = VISIBLE.takeIf { isVisible } ?: GONE
        feedbackHeaderHighlight.visibility = GONE.takeIf { isVisible } ?: VISIBLE
        feedbackHeaderDescription.visibility = GONE.takeIf { isVisible } ?: VISIBLE
    }

    private fun setupTypeSpinner(context: Context) {
        with(feedbackTypeSpinner) {
            setupSpinnerComponent(context, this, R.array.feedbackscreen_type_spinner)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (selectedItem) {
                        CONGRATS -> feedbackColorGroup.visibility = GONE
                        SIMPLE -> feedbackColorGroup.visibility = VISIBLE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
        }
    }

    private fun initComponents(container: View) {
        val binding = AndesuiDynamicFeedbackScreenBinding.bind(container)
        closeButtonSwitch = binding.feedbackscreenCloseButtonSwitch
        feedbackTypeSpinner = binding.feedbackscreenTypeSpinner
        feedbackColorGroup = binding.feedbackscreenColorGroup
        feedbackColorSpinner = binding.feedbackscreenColorSpinner
        feedbackThumbnailSpinner = binding.feedbackscreenHeaderThumbnailSpinner
        feedbackHeaderTitle = binding.feedbackscreenHeaderTitle
        feedbackHeaderOverlineSwitch = binding.feedbackscreenHeaderOverlineSwitch
        feedbackHeaderOverline = binding.feedbackscreenHeaderOverline
        feedbackHeaderHighlight = binding.andesFeedbackscreenHeaderHighlight
        feedbackHeaderDescription = binding.feedbackscreenHeaderDescription
        feedbackBodyMocks = binding.feedbackscreenBodyContentText
        updateButton = binding.changeButton
        feedbackButtonGroupSpinner = binding.feedbackscreenButtongroupSpinner
    }

    private fun setupSpinnerComponent(context: Context, spinner: Spinner, @ArrayRes content: Int) {
        ArrayAdapter.createFromResource(
            context,
            content,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }
}
