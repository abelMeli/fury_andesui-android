package com.mercadolibre.android.andesui.demoapp.components.feedbackscreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistribution
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.feedback.screen.AndesFeedbackScreenView
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenActions
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenButton
import com.mercadolibre.android.andesui.feedback.screen.color.AndesFeedbackScreenColor
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenHeader
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenIllustrationSize
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenTextDescription
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenType
import com.mercadolibre.android.andesui.message.AndesMessage
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.switchandes.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType
import kotlin.random.Random

class FeedbackScreenDynamicShowcase : AppCompatActivity() {

    private val feedbackHeaderOverlineSwitchStatus: AndesSwitchStatus? by lazy {
        intent.extras?.getString(FEEDBACK_OVERLINE_STATUS)?.let {
            AndesSwitchStatus.valueOf(it)
        }
    }
    private val feedbackButtonText by lazy { intent.extras?.getString(FEEDBACK_BUTTON_TEXT) }
    private val feedbackHeaderTitle by lazy { intent.extras?.getString(FEEDBACK_TITLE) }
    private val feedbackHeaderOverline by lazy { intent.extras?.getString(FEEDBACK_OVERLINE) }
    private val feedbackHeaderDescription by lazy { intent.extras?.getString(FEEDBACK_DESCRIPTION) }
    private val feedbackHeaderHighlight by lazy { intent.extras?.getString(FEEDBACK_HIGHLIGHT) }
    private val numberOfMocks: Int by lazy { intent.extras?.getInt(FEEDBACK_BODY_MOCKS) ?: 0 }
    private val feedbackThumbnail by lazy { intent.extras?.getString(FEEDBACK_THUMBNAIL) }
    private val feedbackIllustrationSizes by lazy { intent.extras?.getString(FEEDBACK_ILLUSTRATION_SIZE) }
    private val feedbackType by lazy { intent.extras?.getString(FEEDBACK_TYPE) }
    private val feedbackColor by lazy { intent.extras?.getString(FEEDBACK_COLOR) }
    private val feedbackCloseButtonStatus by lazy {
        intent.extras?.getString(FEEDBACK_CLOSE_BUTTON)?.let {
            AndesSwitchStatus.valueOf(it)
        }
    }

    private val feedbackThumbnailText by lazy {
        intent.extras?.getString(
            FEEDBACK_THUMBNAIL_TEXT_INPUT
        )
    }

    private val feedbackButtonGroupQuantity by lazy {
        intent.extras?.getString(FEEDBACK_BUTTON_GROUP)
    }

    private val buttonsHierarchy = arrayListOf<AndesButtonHierarchy>(
        AndesButtonHierarchy.LOUD, AndesButtonHierarchy.QUIET, AndesButtonHierarchy.TRANSPARENT
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            AndesFeedbackScreenView(
                context = this@FeedbackScreenDynamicShowcase,
                type = getFeedbackScreenType(),
                header = AndesFeedbackScreenHeader(
                    feedbackText = getFeedbackHeaderBody(),
                    feedbackImage = getFeedbackHeaderThumbnail()
                ),
                body = getFeedbackBodyItems(),
                actions = getFeedbackScreenButtonActions()
            )
        )
    }

    private fun getFeedbackScreenActions(count: Int): AndesFeedbackScreenActions {
        return if (count == 0) {
            AndesFeedbackScreenActions(null, getFeedbackCloseButton())
        } else {
            val arrayButton = arrayListOf<AndesButton>()
            for(i in 0 until count) {
                arrayButton.add(AndesButton(this, buttonText = "Button ${i+1}", buttonHierarchy = buttonsHierarchy[i]))
            }
            AndesFeedbackScreenActions(
                AndesButtonGroup(this, arrayButton, distribution = AndesButtonGroupDistribution.MIXED),
                getFeedbackCloseButton()
            )
        }
    }

    private fun getFeedbackScreenButtonActions() : AndesFeedbackScreenActions {
        return when (feedbackButtonGroupQuantity) {
            NONE -> getFeedbackScreenActions(0)
            else -> {
                getFeedbackScreenActions(feedbackButtonGroupQuantity!!.toInt())
            }
        }
    }

    private fun getFeedbackHeaderBody(): AndesFeedbackScreenText {
        return if (feedbackHeaderOverlineSwitchStatus == AndesSwitchStatus.CHECKED) {
            AndesFeedbackScreenText(
                feedbackHeaderTitle.takeIf { !it.isNullOrEmpty() }
                    ?: DEFAULT_HEADER_TEXT,
                feedbackHeaderOverline.takeIf { !feedbackHeaderTitle.isNullOrEmpty() }
                    ?: DEFAULT_OVERLINE_TEXT
            )
        } else {
            AndesFeedbackScreenText(
                feedbackHeaderTitle.takeIf { !it.isNullOrEmpty() }
                    ?: DEFAULT_HEADER_TEXT,
                AndesFeedbackScreenTextDescription(
                    (feedbackHeaderDescription.takeIf { !feedbackHeaderTitle.isNullOrEmpty() }
                        ?: DEFAULT_DESCRIPTION_TEXT),
                    AndesBodyLinks(
                        arrayListOf(
                            VALID_BODY_LINK
                        )
                    ) {
                        Toast.makeText(applicationContext, "Clicked the link!", Toast.LENGTH_SHORT)
                            .show()
                    }
                ),
                feedbackHeaderHighlight.takeIf { !feedbackHeaderTitle.isNullOrEmpty() }
                    ?: DEFAULT_HIGHLIGHT_TEXT
            )
        }
    }

    private fun getFeedbackBodyItems(): View? {
        return if (numberOfMocks != null && numberOfMocks > 0) {
            LinearLayout(this).apply {
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
                            body = (1..FeedbackScreenDynamicPage.MAX_CHARS)
                                .map { Random.nextInt(0, source.length) }
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

    private fun getFeedbackButtonBody(): AndesFeedbackScreenButton? {
        return feedbackButtonText?.takeUnless { it.isEmpty() }?.let { buttonText ->
            AndesFeedbackScreenButton(buttonText, View.OnClickListener {
                Toast.makeText(
                    applicationContext,
                    "Clicked the button!",
                    Toast.LENGTH_SHORT
                ).show()
            })
        }
    }

    private fun getFeedbackCloseButton(): View.OnClickListener? {
        return if (feedbackCloseButtonStatus == AndesSwitchStatus.CHECKED) {
            View.OnClickListener {
                Toast.makeText(applicationContext, "Clicked close!", Toast.LENGTH_SHORT)
                    .show()
                onBackPressed()
            }
        } else {
            null
        }
    }

    private fun getFeedbackHeaderThumbnail(): AndesFeedbackScreenAsset? {
        val drawable =
            ContextCompat.getDrawable(this, R.drawable.andes_ui_placeholder_imagen_48)!!
        return when (feedbackThumbnail) {
            FeedbackScreenDynamicPage.ICON -> AndesFeedbackScreenAsset.Thumbnail(
                drawable,
                AndesThumbnailBadgeType.Icon
            )
            FeedbackScreenDynamicPage.IMAGE_CIRCLE -> AndesFeedbackScreenAsset.Thumbnail(
                drawable,
                AndesThumbnailBadgeType.ImageCircle
            )
            FeedbackScreenDynamicPage.TEXT -> AndesFeedbackScreenAsset.TextThumbnail(
                feedbackThumbnailText ?: ""
            )
            FeedbackScreenDynamicPage.ILLUSTRATION -> getFeedbackIllustrationSize()
            FeedbackScreenDynamicPage.NONE -> null
            else -> null
        }
    }

    private fun getFeedbackIllustrationSize(): AndesFeedbackScreenAsset? {
        val drawable =
            ContextCompat.getDrawable(this, R.drawable.andes_ui_placeholder_imagen_48)!!
        return when (feedbackIllustrationSizes) {
            FeedbackScreenDynamicPage.ILLUSTRATION_80 -> AndesFeedbackScreenAsset.Illustration(
                drawable,
                AndesFeedbackScreenIllustrationSize.Size80
            )
            FeedbackScreenDynamicPage.ILLUSTRATION_128 -> AndesFeedbackScreenAsset.Illustration(
                drawable,
                AndesFeedbackScreenIllustrationSize.Size128
            )
            FeedbackScreenDynamicPage.ILLUSTRATION_160 -> AndesFeedbackScreenAsset.Illustration(
                drawable,
                AndesFeedbackScreenIllustrationSize.Size160
            )
            else -> null
        }
    }

    private fun getFeedbackScreenType() = when (feedbackType) {
        FeedbackScreenDynamicPage.SIMPLE -> AndesFeedbackScreenType.Simple(getFeedbackColor())
        FeedbackScreenDynamicPage.CONGRATS -> AndesFeedbackScreenType.Congrats
        else -> AndesFeedbackScreenType.Congrats
    }

    private fun getFeedbackColor() = when (feedbackColor) {
        FeedbackScreenDynamicPage.GREEN -> AndesFeedbackScreenColor.GREEN
        FeedbackScreenDynamicPage.ORANGE -> AndesFeedbackScreenColor.ORANGE
        FeedbackScreenDynamicPage.RED -> AndesFeedbackScreenColor.RED
        else -> AndesFeedbackScreenColor.GREEN
    }

    companion object {
        private const val FEEDBACK_TYPE = "feedbackType"
        private const val FEEDBACK_COLOR = "feedbackColor"
        private const val FEEDBACK_THUMBNAIL = "feedbackThumbnail"
        private const val FEEDBACK_ILLUSTRATION_SIZE = "feedbackIllustrationSize"
        private const val FEEDBACK_BODY_MOCKS = "feedbackBodyMocks"
        private const val FEEDBACK_CLOSE_BUTTON = "feedbackCloseButtonEnabled"
        private const val FEEDBACK_BUTTON_TEXT = "feedbackButtonText"
        private const val FEEDBACK_OVERLINE_STATUS = "feedbackHeaderOverlineSwitch"
        private const val FEEDBACK_TITLE = "feedbackHeaderTitle"
        private const val FEEDBACK_OVERLINE = "feedbackHeaderOverline"
        private const val FEEDBACK_DESCRIPTION = "feedbackHeaderDescription"
        private const val FEEDBACK_HIGHLIGHT = "feedbackHeaderHighlight"
        private const val FEEDBACK_BUTTON_GROUP = "feedbackButtonGroup"
        private const val FEEDBACK_THUMBNAIL_TEXT_INPUT = "feedbackThumbnailTextInput"
        private const val DEFAULT_HEADER_TEXT = "Default header text"
        private const val DEFAULT_HIGHLIGHT_TEXT = "Default highlighted text"
        private const val DEFAULT_DESCRIPTION_TEXT =
                "Default description text to test the body links. " +
                "Default description text to test the body links. Default description text to test the body links. " +
                "Default description text to test the body links."
        private const val DEFAULT_OVERLINE_TEXT = "Default overline text"
        private val VALID_BODY_LINK = AndesBodyLink(37, 47)
        private const val NONE = "None"

        /**
         * Creates the intent to FeedbackScreenDynamicShowcase.
         */
        fun getIntent(
            context: Context,
            feedbackColorSelected: String?,
            feedbackTypeSelected: String?,
            feedbackThumbnailSelected: String?,
            feedbackIllustrationSizeSelected: String?,
            feedbackBodyMocks: Int?,
            feedbackCloseButtonEnabled: String,
            feedbackHeaderOverlineSwitch: String,
            feedbackHeaderTitle: String?,
            feedbackHeaderOverline: String?,
            feedbackHeaderDescription: String?,
            feedbackHeaderHighlight: String?,
            feedbackButtonGroup: String,
            feedBackThumbnailTextInput: String?
        ) = Intent(context, FeedbackScreenDynamicShowcase::class.java).apply {
            putExtra(FEEDBACK_TYPE, feedbackTypeSelected)
            putExtra(FEEDBACK_COLOR, feedbackColorSelected)
            putExtra(FEEDBACK_THUMBNAIL, feedbackThumbnailSelected)
            putExtra(FEEDBACK_ILLUSTRATION_SIZE, feedbackIllustrationSizeSelected)
            putExtra(FEEDBACK_BODY_MOCKS, feedbackBodyMocks)
            putExtra(FEEDBACK_CLOSE_BUTTON, feedbackCloseButtonEnabled)
            putExtra(FEEDBACK_OVERLINE_STATUS, feedbackHeaderOverlineSwitch)
            putExtra(FEEDBACK_TITLE, feedbackHeaderTitle)
            putExtra(FEEDBACK_OVERLINE, feedbackHeaderOverline)
            putExtra(FEEDBACK_DESCRIPTION, feedbackHeaderDescription)
            putExtra(FEEDBACK_HIGHLIGHT, feedbackHeaderHighlight)
            putExtra(FEEDBACK_BUTTON_GROUP, feedbackButtonGroup)
            putExtra(FEEDBACK_THUMBNAIL_TEXT_INPUT, feedBackThumbnailTextInput)
        }
    }
}