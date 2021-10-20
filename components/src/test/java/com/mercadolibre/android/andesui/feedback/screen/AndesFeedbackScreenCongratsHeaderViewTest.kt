package com.mercadolibre.android.andesui.feedback.screen

import android.content.Context
import android.os.Build
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenTextDescription
import com.mercadolibre.android.andesui.feedback.screen.header.view.AndesFeedbackScreenCongratsHeaderView
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.thumbnail.AndesThumbnailBadge
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesFeedbackScreenCongratsHeaderViewTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.setTheme(R.style.Theme_AppCompat)
    }

    @Test
    fun `Header title & overline visible, description & highlight gone`() {
        val header = AndesFeedbackScreenCongratsHeaderView(context)
        header.setupTextComponents(
            feedbackText = AndesFeedbackScreenText(
                "Title",
                "Overline"
            ),
            type = AndesBadgeIconType.SUCCESS,
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
        val header = AndesFeedbackScreenCongratsHeaderView(context)
        val badgeType = AndesBadgeIconType.WARNING
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
            currentTextColor assertEquals badgeType.iconType.type.primaryColor().colorInt(context)
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
    fun `Thumbnail set correctly`() {
        val header = AndesFeedbackScreenCongratsHeaderView(context)
        val badgeType = AndesBadgeIconType.WARNING

        header.setupThumbnailComponent(
            feedbackThumbnail = AndesFeedbackScreenAsset.Thumbnail(
                ContextCompat.getDrawable(context, R.drawable.andes_ui_placeholder_imagen_32)!!,
                AndesThumbnailBadgeType.FeedbackIcon
            ),
            type = badgeType
        )

        header.getThumbnail().thumbnailType assertEquals AndesThumbnailBadgeType.FeedbackIcon
        header.getThumbnail().image assertEquals R.drawable.andes_ui_placeholder_imagen_32
    }

    private fun AndesFeedbackScreenCongratsHeaderView.getDescription() =
        findViewById<TextView>(R.id.andes_feedbackscreen_congrats_header_description)

    private fun AndesFeedbackScreenCongratsHeaderView.getTitle() =
        findViewById<TextView>(R.id.andes_feedbackscreen_congrats_header_title)

    private fun AndesFeedbackScreenCongratsHeaderView.getHighlight() =
        findViewById<TextView>(R.id.andes_feedbackscreen_congrats_header_highlight)

    private fun AndesFeedbackScreenCongratsHeaderView.getOverline() =
        findViewById<TextView>(R.id.andes_feedbackscreen_congrats_header_overline)

    private fun AndesFeedbackScreenCongratsHeaderView.getThumbnail() =
        findViewById<AndesThumbnailBadge>(R.id.andes_feedbackscreen_congrats_header_image)
}