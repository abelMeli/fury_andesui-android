package com.mercadolibre.android.andesui.feedback.screen

import android.content.Context
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.assertIsNull
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenActions
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenButton
import com.mercadolibre.android.andesui.feedback.screen.factory.AndesFeedbackScreenConfigurationFactory
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenHeader
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenTextDescription
import com.mercadolibre.android.andesui.feedback.screen.header.view.AndesFeedbackScreenCongratsHeaderView
import com.mercadolibre.android.andesui.feedback.screen.header.view.AndesFeedbackScreenSimpleHeaderView
import com.mercadolibre.android.andesui.feedback.screen.color.AndesFeedbackScreenColor
import com.mercadolibre.android.andesui.feedback.screen.type.AndesCongratsFeedbackScreenType
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenType
import com.mercadolibre.android.andesui.feedback.screen.type.AndesSimpleFeedbackScreenType
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesFeedbackScreenConfigurationTest {
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.setTheme(R.style.Theme_AppCompat)
    }

    @Test
    fun `Feedback Simple Warning with body, buttons and text with overline`() {
        val view = View(context)
        val onClick = View.OnClickListener { }
        val config = AndesFeedbackScreenConfigurationFactory.create(
            context = context,
            body = view,
            type = AndesFeedbackScreenType.Simple(
                AndesFeedbackScreenColor.ORANGE
            ),
            actions = AndesFeedbackScreenActions(
                AndesFeedbackScreenButton(
                    "Test",
                    onClick
                ),
                onClick
            ),
            header = AndesFeedbackScreenHeader(
                feedbackText = AndesFeedbackScreenText(
                    title = "Title",
                    overline = "Overline"
                )
            )
        )

        config.background assertEquals ContextCompat.getColor(
            context,
            R.color.andes_bg_color_secondary
        )
        config.body.view assertEquals view
        config.body.visibility assertEquals View.VISIBLE
        config.close.visibility assertEquals View.VISIBLE
        config.close.tintColor assertEquals ContextCompat.getColor(context, R.color.andes_gray_550)
        config.close.onClick assertIsNull false
        config.feedbackButton.visibility assertEquals View.VISIBLE
        config.feedbackButton.text assertEquals "Test"
        config.feedbackButton.onClick assertEquals onClick
        config.headerVerticalBias assertEquals 0f
        config.headerTopMargin assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_simple_header_margin_top)
        config.gradientVisibility assertEquals View.GONE
        config.feedbackText.highlight assertIsNull true
        config.feedbackText.description assertIsNull true
        config.feedbackText.overline assertEquals "Overline"
        config.feedbackText.title assertEquals "Title"
        config.statusBarColor assertIsNull true
        assert(config.header is AndesFeedbackScreenSimpleHeaderView)
        assert(config.type is AndesSimpleFeedbackScreenType)
    }

    @Test
    fun `Feedback Simple Error with body, close and text with description`() {
        val view = View(context)
        val config = AndesFeedbackScreenConfigurationFactory.create(
            context = context,
            body = view,
            type = AndesFeedbackScreenType.Simple(
                AndesFeedbackScreenColor.RED
            ),
            actions = AndesFeedbackScreenActions(
                closeCallback = mock()
            ),
            header = AndesFeedbackScreenHeader(
                feedbackText = AndesFeedbackScreenText(
                    title = "Title",
                    description = AndesFeedbackScreenTextDescription(
                        text = "Description"
                    )
                )
            )
        )

        config.background assertEquals ContextCompat.getColor(
            context,
            R.color.andes_bg_color_secondary
        )
        config.body.view assertEquals view
        config.body.visibility assertEquals View.VISIBLE
        config.close.visibility assertEquals View.VISIBLE
        config.close.tintColor assertEquals ContextCompat.getColor(context, R.color.andes_gray_550)
        config.close.onClick assertIsNull false
        config.feedbackButton.visibility assertEquals View.GONE
        config.feedbackButton.text assertEquals null
        config.feedbackButton.onClick assertEquals null
        config.headerVerticalBias assertEquals 0f
        config.headerTopMargin assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_simple_header_margin_top)
        config.gradientVisibility assertEquals View.GONE
        config.feedbackText.highlight assertIsNull true
        config.feedbackText.description assertIsNull false
        config.feedbackText.description!!.text assertEquals "Description"
        config.feedbackText.overline assertEquals null
        config.feedbackText.title assertEquals "Title"
        config.statusBarColor assertIsNull true
        assert(config.header is AndesFeedbackScreenSimpleHeaderView)
        assert(config.type is AndesSimpleFeedbackScreenType)
    }

    @Test
    fun `Feedback Simple Success with title`() {
        val type = AndesFeedbackScreenType.Simple(AndesFeedbackScreenColor.GREEN)
        val config = AndesFeedbackScreenConfigurationFactory.create(
            context = context,
            body = null,
            type = type,
            header = AndesFeedbackScreenHeader(
                feedbackText = AndesFeedbackScreenText(
                    title = "Title"
                )
            ),
            actions = null
        )

        config.background assertEquals ContextCompat.getColor(
            context,
            R.color.andes_bg_color_white
        )
        config.body.view assertIsNull true
        config.body.visibility assertEquals View.GONE
        config.close.visibility assertEquals View.GONE
        config.close.tintColor assertEquals type.type.getCloseTintColor(context, false)
        config.close.onClick assertIsNull true
        config.feedbackButton.visibility assertEquals View.GONE
        config.feedbackButton.text assertEquals null
        config.feedbackButton.onClick assertEquals null
        config.headerVerticalBias assertEquals 0.5f
        config.headerTopMargin assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_simple_header_margin_top)
        config.gradientVisibility assertEquals View.GONE
        config.feedbackText.highlight assertIsNull true
        config.feedbackText.description assertIsNull true
        config.feedbackText.overline assertEquals null
        config.feedbackText.title assertEquals "Title"
        config.statusBarColor assertIsNull true
        assert(config.header is AndesFeedbackScreenSimpleHeaderView)
        assert(config.type is AndesSimpleFeedbackScreenType)
    }

    @Test
    fun `Feedback Congrats Icon with title, highlight`() {
        val config = AndesFeedbackScreenConfigurationFactory.create(
            context = context,
            body = null,
            type = AndesFeedbackScreenType.Congrats,
            header = AndesFeedbackScreenHeader(
                feedbackText = AndesFeedbackScreenText(
                    title = "Title",
                    highlight = "Highlight"
                ),
                feedbackImage = AndesFeedbackScreenAsset.Thumbnail(
                    ContextCompat.getDrawable(context, R.drawable.andes_ui_placeholder_imagen_32)!!,
                    AndesThumbnailBadgeType.Icon
                )
            ),
            actions = null
        )

        config.background assertEquals ContextCompat.getColor(
            context,
            R.color.andes_bg_color_white
        )
        config.body.view assertIsNull true
        config.body.visibility assertEquals View.GONE
        config.close.visibility assertEquals View.GONE
        config.close.tintColor assertEquals AndesFeedbackScreenType.Congrats.type.getCloseTintColor(context, false)
        config.close.onClick assertIsNull true
        config.feedbackButton.visibility assertEquals View.GONE
        config.feedbackButton.text assertEquals null
        config.feedbackButton.onClick assertEquals null
        config.headerVerticalBias assertEquals 0.5f
        config.headerTopMargin assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_simple_header_margin_top)
        config.gradientVisibility assertEquals View.GONE
        config.feedbackText.highlight assertEquals "Highlight"
        config.feedbackText.description assertIsNull true
        config.feedbackText.overline assertEquals null
        config.feedbackText.title assertEquals "Title"
        config.statusBarColor assertEquals ContextCompat.getColor(context, R.color.andes_green_500)
        assert(config.header is AndesFeedbackScreenSimpleHeaderView)
        assert(config.type is AndesCongratsFeedbackScreenType)
    }

    @Test
    fun `Feedback Congrats, ImageCircle, body, close, title, description & highlight`() {
        val body = View(context)
        val description = AndesFeedbackScreenTextDescription(
            "Description",
            AndesBodyLinks(arrayListOf(AndesBodyLink(1, 2)), mock())
        )
        val config = AndesFeedbackScreenConfigurationFactory.create(
            context = context,
            body = body,
            type = AndesFeedbackScreenType.Congrats,
            header = AndesFeedbackScreenHeader(
                feedbackText = AndesFeedbackScreenText(
                    title = "Title",
                    description = description,
                    highlight = "Highlight"
                ),
                feedbackImage = AndesFeedbackScreenAsset.Thumbnail(
                    ContextCompat.getDrawable(context, R.drawable.andes_ui_placeholder_imagen_32)!!,
                    AndesThumbnailBadgeType.ImageCircle
                )
            ),
            actions = AndesFeedbackScreenActions(
                closeCallback = mock()
            )
        )

        config.background assertEquals ContextCompat.getColor(
            context,
            R.color.andes_bg_color_secondary
        )
        config.body.view assertIsNull false
        config.body.visibility assertEquals View.VISIBLE
        config.close.visibility assertEquals View.VISIBLE
        config.close.tintColor assertEquals ContextCompat.getColor(context, R.color.andes_white)
        config.close.onClick assertIsNull false
        config.feedbackButton.visibility assertEquals View.GONE
        config.feedbackButton.text assertEquals null
        config.feedbackButton.onClick assertEquals null
        config.headerVerticalBias assertEquals 0f
        config.headerTopMargin assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_congrats_header_margin_top)
        config.gradientVisibility assertEquals View.VISIBLE
        config.feedbackText.highlight assertEquals "Highlight"
        config.feedbackText.description assertEquals description
        config.feedbackText.overline assertEquals null
        config.feedbackText.title assertEquals "Title"
        config.statusBarColor assertEquals ContextCompat.getColor(context, R.color.andes_green_500)
        assert(config.header is AndesFeedbackScreenCongratsHeaderView)
        assert(config.type is AndesCongratsFeedbackScreenType)
    }
}
