package com.mercadolibre.android.andesui.feedback.screen

import android.content.Context
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenTextDescription
import com.mercadolibre.android.andesui.feedback.screen.header.view.AndesFeedbackScreenSimpleHeaderView
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackBadgeIconType
import com.mercadolibre.android.andesui.feedback.screen.type.AndesSimpleFeedbackScreenType
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.thumbnail.AndesThumbnailBadge
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType
import com.mercadolibre.android.andesui.tooltip.extensions.displaySize
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesFeedbackScreenSimpleHeaderViewTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.setTheme(R.style.Theme_AppCompat)
    }

    @Test
    fun `Header title & overline visible, description & highlight gone`() {
        val header = AndesFeedbackScreenSimpleHeaderView(context)
        header.setupTextComponents(
            feedbackText = AndesFeedbackScreenText(
                "Title",
                "Overline"
            ),
            type = AndesFeedbackBadgeIconType.SUCCESS,
            hasBody = false
        )

        header.getDescription().visibility assertEquals View.GONE
        header.getHighlight().visibility assertEquals View.GONE
        with(header.getTitle()) {
            text assertEquals "Title"
            visibility assertEquals View.VISIBLE
        }
        with(header.getOverline()) {
            text assertEquals "Overline"
            visibility assertEquals View.VISIBLE
        }
    }

    @Test
    fun `Header title & overline gone, description & highlight visible with links & right color`() {
        val header = AndesFeedbackScreenSimpleHeaderView(context)
        val badgeType = AndesFeedbackBadgeIconType.WARNING
        header.setupTextComponents(
            feedbackText = AndesFeedbackScreenText(
                "Title",
                AndesFeedbackScreenTextDescription(
                    "Description",
                    AndesBodyLinks(arrayListOf(AndesBodyLink(1, 2), AndesBodyLink(3, 4)), mock())
                ),
                "Highlight"
            ),
            type = badgeType,
            hasBody = false
        )

        header.getOverline().visibility assertEquals View.GONE
        with(header.getTitle()) {
            text assertEquals "Title"
            visibility assertEquals View.VISIBLE
        }
        with(header.getHighlight()) {
            text assertEquals "Highlight"
            visibility assertEquals View.VISIBLE
            currentTextColor assertEquals badgeType.type.type.primaryColor().colorInt(context)
        }
        with(header.getDescription()) {
            text.toString() assertEquals "Description"
            visibility assertEquals View.VISIBLE
            val spans = (text as SpannableString).getSpans(
                0,
                "Description".lastIndex,
                ClickableSpan::class.java
            )
            spans.size assertEquals 2
        }
    }

    @Test
    fun `Paddings without body description, highlight`() {
        val header = AndesFeedbackScreenSimpleHeaderView(context)
        val badgeType = AndesFeedbackBadgeIconType.WARNING
        header.setupTextComponents(
            feedbackText = AndesFeedbackScreenText(
                "Title",
                AndesFeedbackScreenTextDescription(
                    "Description",
                    AndesBodyLinks(arrayListOf(AndesBodyLink(1, 2), AndesBodyLink(3, 4)), mock())
                ),
                "Highlight"
            ),
            type = badgeType,
            hasBody = false
        )
        with(header.getTitle()) {
            paddingEnd assertEquals DEFAULT_PADDING
            paddingStart assertEquals DEFAULT_PADDING
            paddingBottom assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_padding_vertical)
            paddingTop assertEquals DEFAULT_PADDING
        }
        with(header.getHighlight()) {
            paddingEnd assertEquals DEFAULT_PADDING
            paddingStart assertEquals DEFAULT_PADDING
            paddingBottom assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_padding_vertical)
            paddingTop assertEquals DEFAULT_PADDING
        }
        with(header.getDescription()) {
            paddingEnd assertEquals DEFAULT_PADDING
            paddingStart assertEquals DEFAULT_PADDING
            paddingBottom assertEquals DEFAULT_PADDING
            paddingTop assertEquals DEFAULT_PADDING
        }
    }

    @Test
    fun `Paddings without body overline`() {
        val header = AndesFeedbackScreenSimpleHeaderView(context)
        val badgeType = AndesFeedbackBadgeIconType.WARNING
        header.setupTextComponents(
            feedbackText = AndesFeedbackScreenText(
                "Title",
                "Overline"
            ),
            type = badgeType,
            hasBody = false
        )
        with(header.getOverline()) {
            paddingEnd assertEquals DEFAULT_PADDING
            paddingStart assertEquals DEFAULT_PADDING
            paddingBottom assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_padding_vertical)
            paddingTop assertEquals DEFAULT_PADDING
        }
        with(header.getTitle()) {
            paddingEnd assertEquals DEFAULT_PADDING
            paddingStart assertEquals DEFAULT_PADDING
            paddingBottom assertEquals DEFAULT_PADDING
            paddingTop assertEquals DEFAULT_PADDING
        }
    }

    @Test
    fun `Paddings with body overline`() {
        val header = AndesFeedbackScreenSimpleHeaderView(context)
        val badgeType = AndesFeedbackBadgeIconType.WARNING
        header.setupTextComponents(
            feedbackText = AndesFeedbackScreenText(
                "Title",
                "Overline"
            ),
            type = badgeType,
            hasBody = true
        )
        with(header.getOverline()) {
            paddingEnd assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_card_padding)
            paddingStart assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_card_padding)
            paddingBottom assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_padding_vertical)
            paddingTop assertEquals DEFAULT_PADDING
        }
        with(header.getTitle()) {
            paddingEnd assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_card_padding)
            paddingStart assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_card_padding)
            paddingBottom assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_card_padding)
            paddingTop assertEquals DEFAULT_PADDING
        }
    }

    @Test
    fun `Paddings with body description, highlight`() {
        val header = AndesFeedbackScreenSimpleHeaderView(context)
        val badgeType = AndesFeedbackBadgeIconType.WARNING
        header.setupTextComponents(
            feedbackText = AndesFeedbackScreenText(
                "Title",
                AndesFeedbackScreenTextDescription("Description"),
                "Highlight"
            ),
            type = badgeType,
            hasBody = true
        )
        with(header.getTitle()) {
            paddingEnd assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_card_padding)
            paddingStart assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_card_padding)
            paddingBottom assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_padding_vertical)
            paddingTop assertEquals DEFAULT_PADDING
        }
        with(header.getHighlight()) {
            paddingEnd assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_card_padding)
            paddingStart assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_card_padding)
            paddingBottom assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_padding_vertical)
            paddingTop assertEquals DEFAULT_PADDING
        }
        with(header.getDescription()) {
            paddingEnd assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_card_padding)
            paddingStart assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_card_padding)
            paddingBottom assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_card_padding)
            paddingTop assertEquals DEFAULT_PADDING
        }
    }

    @Test
    fun `Card shown when body exists`() {
        val header = AndesFeedbackScreenSimpleHeaderView(context)
        val badgeType = AndesFeedbackBadgeIconType.WARNING
        header.setupTextComponents(
            feedbackText = AndesFeedbackScreenText(
                "Title"
            ),
            type = badgeType,
            hasBody = true
        )

        header.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        header.layout(0, 0, context.displaySize().x, context.displaySize().y)

        header.getCardView().visibility assertEquals View.VISIBLE
    }

    @Test
    fun `Card not shown when body does not exist`() {
        val header = AndesFeedbackScreenSimpleHeaderView(context)
        val badgeType = AndesFeedbackBadgeIconType.WARNING
        header.setupTextComponents(
            feedbackText = AndesFeedbackScreenText(
                "Title"
            ),
            type = badgeType,
            hasBody = false
        )

        header.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        header.layout(0, 0, context.displaySize().x, context.displaySize().y)

        header.getCardView().visibility assertEquals View.GONE
    }

    @Test
    fun `Thumbnail set correctly`() {
        val header = AndesFeedbackScreenSimpleHeaderView(context)
        val badgeType = AndesFeedbackBadgeIconType.WARNING

        header.setupAssetComponent(
            feedbackAsset = AndesFeedbackScreenAsset.Thumbnail(
                ContextCompat.getDrawable(context, R.drawable.andes_ui_placeholder_imagen_32)!!,
                AndesThumbnailBadgeType.FeedbackIcon
            ),
            type = AndesSimpleFeedbackScreenType(badgeType),
            hasBody = false
        )

        header.getThumbnail().thumbnailType assertEquals AndesThumbnailBadgeType.FeedbackIcon
        header.getThumbnail().image assertEquals R.drawable.andes_ui_placeholder_imagen_32
    }

    private fun AndesFeedbackScreenSimpleHeaderView.getDescription() =
        findViewById<TextView>(R.id.andes_feedbackscreen_header_description)

    private fun AndesFeedbackScreenSimpleHeaderView.getTitle() =
        findViewById<TextView>(R.id.andes_feedbackscreen_header_title)

    private fun AndesFeedbackScreenSimpleHeaderView.getHighlight() =
        findViewById<TextView>(R.id.andes_feedbackscreen_header_highlight)

    private fun AndesFeedbackScreenSimpleHeaderView.getOverline() =
        findViewById<TextView>(R.id.andes_feedbackscreen_header_overline)

    private fun AndesFeedbackScreenSimpleHeaderView.getCardView() =
        findViewById<CardView>(R.id.andes_feedbackscreen_header_card)

    private fun AndesFeedbackScreenSimpleHeaderView.getThumbnail() =
        findViewById<FrameLayout>(R.id.andes_feedbackscreen_header_image).getChildAt(0) as AndesThumbnailBadge

    companion object {
        private const val DEFAULT_PADDING = 0
    }
}
