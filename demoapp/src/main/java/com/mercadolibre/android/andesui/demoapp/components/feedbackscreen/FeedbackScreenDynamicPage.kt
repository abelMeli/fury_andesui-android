package com.mercadolibre.android.andesui.demoapp.components.feedbackscreen

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.ArrayRes
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicFeedbackScreenBinding
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenButton
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenTextDescription
import com.mercadolibre.android.andesui.feedback.screen.color.AndesFeedbackScreenColor
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenType
import com.mercadolibre.android.andesui.message.AndesMessage
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.switchandes.AndesSwitch
import com.mercadolibre.android.andesui.switchandes.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.textfield.AndesTextarea
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType
import kotlin.random.Random.Default.nextInt

@Suppress("TooManyFunctions")
class FeedbackScreenDynamicPage {
    private lateinit var feedbackBodyMocks: AndesTextfield
    private lateinit var feedbackColorGroup: Group
    private lateinit var feedbackButtonText: AndesTextfield
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

    companion object {
        private const val CONGRATS = "Congrats"
        private const val SIMPLE = "Simple"

        private const val GREEN = "Green"
        private const val ORANGE = "Orange"
        private const val RED = "Red"

        private const val NONE = "None"
        private const val ICON = "Icon"
        private const val IMAGE_CIRCLE = "Image Circle"

        private const val DEFAULT_HEADER_TEXT = "Default header text"
        private const val DEFAULT_HIGHLIGHT_TEXT = "Default highlighted text"
        private const val DEFAULT_DESCRIPTION_TEXT =
            "Default description text to test the body links. " +
                    "Default description text to test the body links. Default description text to test the body links. " +
                    "Default description text to test the body links."
        private const val DEFAULT_OVERLINE_TEXT = "Default overline text"

        private val VALID_BODY_LINK = AndesBodyLink(37, 47)
        private const val MAX_CHARS = 240
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
        setupOverlineSwitch()
        setupButtons(context)
    }

    private fun setupButtons(context: Context) {
        updateButton.setOnClickListener {
            (context as? FeedbackScreenShowcaseActivity)?.goToFeedbackFragment(
                getFeedbackScreenType(),
                getFeedbackHeaderThumbnail(context),
                getFeedbackHeaderBody(context),
                getFeedbackButtonBody(context),
                getFeedbackCloseButton(context),
                getFeedbackBodyItems(context)
            )
        }
    }

    private fun getFeedbackBodyItems(context: Context): View? {
        val numberOfMocks = feedbackBodyMocks.text?.toIntOrNull()
        return if (numberOfMocks != null && numberOfMocks > 0) {
            LinearLayout(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                orientation = LinearLayout.VERTICAL

                val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ "
                for (i in 0 until numberOfMocks) {
                    addView(
                        AndesMessage(
                            context = context,
                            body = (1..MAX_CHARS)
                                .map { nextInt(0, source.length) }
                                .map(source::get)
                                .joinToString("")
                        )
                    )
                    addView(buildEmptyView(context))
                }
            }
        } else {
            null
        }
    }

    private fun getFeedbackCloseButton(context: Context): View.OnClickListener? {
        return if (closeButtonSwitch.status == AndesSwitchStatus.CHECKED) {
            View.OnClickListener {
                Toast.makeText(context.applicationContext, "Clicked close!", Toast.LENGTH_SHORT).show()
                (context as Activity).onBackPressed()
            }
        } else {
            null
        }
    }

    private fun getFeedbackButtonBody(context: Context) =
        if (!feedbackButtonText.text.isNullOrEmpty()) {
            AndesFeedbackScreenButton(feedbackButtonText.text.toString(), View.OnClickListener {
                Toast.makeText(context.applicationContext, "Clicked the button!", Toast.LENGTH_SHORT).show()
            })
        } else {
            null
        }

    private fun getFeedbackHeaderBody(context: Context) =
        if (feedbackHeaderOverlineSwitch.status == AndesSwitchStatus.CHECKED) {
            AndesFeedbackScreenText(
                feedbackHeaderTitle.text.takeIf { !it.isNullOrEmpty() } ?: DEFAULT_HEADER_TEXT,
                feedbackHeaderOverline.text.takeIf { !feedbackHeaderTitle.text.isNullOrEmpty() } ?: DEFAULT_OVERLINE_TEXT
            )
        } else {
            AndesFeedbackScreenText(
                feedbackHeaderTitle.text.takeIf { !it.isNullOrEmpty() } ?: DEFAULT_HEADER_TEXT,
                AndesFeedbackScreenTextDescription(
                    (feedbackHeaderDescription.text.takeIf { !feedbackHeaderTitle.text.isNullOrEmpty() }
                        ?: DEFAULT_DESCRIPTION_TEXT),
                    AndesBodyLinks(
                        arrayListOf(
                            VALID_BODY_LINK
                        )
                    ) {
                        Toast.makeText(context.applicationContext, "Clicked the link!", Toast.LENGTH_SHORT).show()
                    }
                ),
                feedbackHeaderHighlight.text.takeIf { !feedbackHeaderTitle.text.isNullOrEmpty() }
                    ?: DEFAULT_HIGHLIGHT_TEXT
            )
        }

    private fun getFeedbackHeaderThumbnail(context: Context): AndesFeedbackScreenAsset? {
        val drawable =
            ContextCompat.getDrawable(context, R.drawable.andes_ui_placeholder_imagen_48)!!
        return when (feedbackThumbnailSpinner.selectedItem) {
            ICON -> AndesFeedbackScreenAsset.Thumbnail(
                drawable,
                AndesThumbnailBadgeType.Icon
            )
            IMAGE_CIRCLE -> AndesFeedbackScreenAsset.Thumbnail(
                drawable,
                AndesThumbnailBadgeType.ImageCircle
            )
            NONE -> null
            else -> null
        }
    }

    private fun getFeedbackScreenType() = when (feedbackTypeSpinner.selectedItem) {
        SIMPLE -> AndesFeedbackScreenType.Simple(getFeedbackColor())
        CONGRATS -> AndesFeedbackScreenType.Congrats
        else -> AndesFeedbackScreenType.Congrats
    }

    private fun getFeedbackColor() = when (feedbackColorSpinner.selectedItem) {
        GREEN -> AndesFeedbackScreenColor.GREEN
        ORANGE -> AndesFeedbackScreenColor.ORANGE
        RED -> AndesFeedbackScreenColor.RED
        else -> AndesFeedbackScreenColor.GREEN
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

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // no-op
                }
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
        feedbackButtonText = binding.feedbackscreenFinishButtonText
        feedbackBodyMocks = binding.feedbackscreenBodyContentText
        updateButton = binding.changeButton
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
