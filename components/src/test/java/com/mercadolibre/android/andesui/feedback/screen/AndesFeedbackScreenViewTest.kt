package com.mercadolibre.android.andesui.feedback.screen

import android.content.Context
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType
import com.mercadolibre.android.andesui.feedback.screen.color.AndesFeedbackScreenColor
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenHeader
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenActions
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenButton
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenTextDescription
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenType
import com.mercadolibre.android.andesui.message.AndesMessage
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.thumbnail.AndesThumbnailBadge
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase.assertTrue
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesFeedbackScreenViewTest {
    private lateinit var robolectricActivity: ActivityController<AppCompatActivity>
    private lateinit var activity: AppCompatActivity
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.setTheme(R.style.Theme_AppCompat)

        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)

        setupTestActivity()
    }

    @Test
    fun `FeedbackScreen Congrats with body set thumbnail icon`() {
        val screenView = AndesFeedbackScreenView(
            context = activity,
            type = AndesFeedbackScreenType.Congrats,
            header = AndesFeedbackScreenHeader(
                AndesFeedbackScreenText(
                    "Title"
                ),
                null
            ),
            body = View(activity)
        )

        startActivity(screenView)
        screenView.setFeedbackScreenAsset(
            AndesFeedbackScreenAsset.Thumbnail(
                image = ContextCompat.getDrawable(context, R.drawable.andes_ui_placeholder_imagen_32)!!,
                badgeType = AndesThumbnailBadgeType.Icon
            )
        )

        with(screenView.getThumbnailCongrats()) {
            image assertEquals R.drawable.andes_ui_placeholder_imagen_32
            thumbnailType assertEquals AndesThumbnailBadgeType.Icon
            badgeComponent.color assertEquals AndesBadgeType.SUCCESS
        }
    }

    @Test
    fun `FeedbackScreen Congrats with body set default thumbnail`() {
        val screenView = AndesFeedbackScreenView(
            context = activity,
            type = AndesFeedbackScreenType.Congrats,
            header = AndesFeedbackScreenHeader(
                AndesFeedbackScreenText(
                    "Title"
                ),
                AndesFeedbackScreenAsset.Thumbnail(
                    image = ContextCompat.getDrawable(context, R.drawable.andes_ui_placeholder_imagen_32)!!,
                    badgeType = AndesThumbnailBadgeType.Icon
                )
            ),
            body = View(activity)
        )

        startActivity(screenView)
        screenView.setFeedbackScreenAsset(null)

        with(screenView.getThumbnailCongrats()) {
            image assertEquals R.drawable.andes_ui_feedback_success_32
            thumbnailType assertEquals AndesThumbnailBadgeType.FeedbackIcon
            badgeComponent.color assertEquals AndesBadgeType.SUCCESS
        }
    }

    @Test
    fun `FeedbackScreen Congrats without body set default thumbnail`() {
        val screenView = AndesFeedbackScreenView(
            context = activity,
            type = AndesFeedbackScreenType.Congrats,
            header = AndesFeedbackScreenHeader(
                AndesFeedbackScreenText(
                    "Title"
                ),
                AndesFeedbackScreenAsset.Thumbnail(
                    image = ContextCompat.getDrawable(context, R.drawable.andes_ui_placeholder_imagen_32)!!,
                    badgeType = AndesThumbnailBadgeType.Icon
                )
            ),
            body = null
        )

        startActivity(screenView)
        screenView.setFeedbackScreenAsset(null)

        with(screenView.getThumbnailSimple()) {
            image assertEquals R.drawable.andes_ui_feedback_success_40
            thumbnailType assertEquals AndesThumbnailBadgeType.FeedbackIcon
            badgeComponent.color assertEquals AndesBadgeType.SUCCESS
        }
    }

    @Test
    fun `FeedbackScreen Simple without body set default thumbnail`() {
        val screenView = AndesFeedbackScreenView(
            context = activity,
            type = AndesFeedbackScreenType.Simple(
                AndesFeedbackScreenColor.ORANGE
            ),
            header = AndesFeedbackScreenHeader(
                AndesFeedbackScreenText(
                    "Title"
                ),
                AndesFeedbackScreenAsset.Thumbnail(
                    image = ContextCompat.getDrawable(context, R.drawable.andes_ui_placeholder_imagen_32)!!,
                    badgeType = AndesThumbnailBadgeType.Icon
                )
            ),
            body = null
        )

        startActivity(screenView)
        screenView.setFeedbackScreenAsset(null)

        with(screenView.getThumbnailSimple()) {
            image assertEquals R.drawable.andes_ui_feedback_warning_40
            thumbnailType assertEquals AndesThumbnailBadgeType.FeedbackIcon
            badgeComponent.color assertEquals AndesBadgeType.WARNING
        }
    }

    @Test
    fun `FeedbackScreen Simple without body set thumbnail`() {
        val screenView = AndesFeedbackScreenView(
            context = activity,
            type = AndesFeedbackScreenType.Simple(
                AndesFeedbackScreenColor.RED
            ),
            header = AndesFeedbackScreenHeader(
                AndesFeedbackScreenText(
                    "Title"
                )
            ),
            body = null
        )

        startActivity(screenView)
        screenView.setFeedbackScreenAsset(
            AndesFeedbackScreenAsset.Thumbnail(
                image = ContextCompat.getDrawable(context, R.drawable.andes_ui_placeholder_imagen_32)!!,
                badgeType = AndesThumbnailBadgeType.Icon
            )
        )

        with(screenView.getThumbnailSimple()) {
            image assertEquals R.drawable.andes_ui_placeholder_imagen_32
            thumbnailType assertEquals AndesThumbnailBadgeType.Icon
            badgeComponent.color assertEquals AndesBadgeType.ERROR
        }
    }

    private fun startActivity(view: View) {
        activity.setContentView(view)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    private fun AndesFeedbackScreenView.getThumbnailCongrats() =
        findViewById<FrameLayout>(R.id.andes_feedbackscreen_congrats_header_image).getChildAt(0) as AndesThumbnailBadge

    private fun AndesFeedbackScreenView.getThumbnailSimple() =
        findViewById<FrameLayout>(R.id.andes_feedbackscreen_header_image).getChildAt(0) as AndesThumbnailBadge

    private fun setupTestActivity() {
        robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        activity = robolectricActivity.get()
    }

    @Test
    fun `FeedbackScreen Simple without body replace header`() {
        val screenView = AndesFeedbackScreenView(
            context = activity,
            type = AndesFeedbackScreenType.Simple(
                AndesFeedbackScreenColor.RED
            ),
            header = AndesFeedbackScreenHeader(
                AndesFeedbackScreenText(
                    "Title"
                )
            ),
            body = null
        )
        startActivity(screenView)
        screenView.setFeedbackScreenHeader(
            AndesFeedbackScreenHeader(
                AndesFeedbackScreenText(
                    "New Title",
                    AndesFeedbackScreenTextDescription(
                        "New Description",
                        AndesBodyLinks(arrayListOf(AndesBodyLink(1, 2), AndesBodyLink(3, 4)), mock())
                    ),
                    "New Highlight"
                )
            )
        )

        screenView.setFeedbackScreenType(
            AndesFeedbackScreenType.Simple(AndesFeedbackScreenColor.GREEN)
        )

        val badge = screenView.getThumbnailBadge()

        with(screenView.getTitle()) {
            text.toString() assertEquals "New Title"
            visibility assertEquals View.VISIBLE
        }
        with(screenView.getDescription()) {
            text.toString() assertEquals "New Description"
            visibility assertEquals View.VISIBLE
            val spans = (text as SpannableString).getSpans(
                0,
                "Description".lastIndex,
                ClickableSpan::class.java
            )
            spans.size assertEquals 2
        }

        with(screenView.getHighlight()) {
            text.toString() assertEquals "New Highlight"
            visibility assertEquals View.VISIBLE
            currentTextColor assertEquals badge.badgeComponent.color.type.primaryColor().colorInt(context)
        }
    }

    @Test
    fun `FeedbackScreen Simple with AndesFeedbackScreenActions deprecated constructor without body set actions`() {
        val onClick = View.OnClickListener { }
        val screenView = AndesFeedbackScreenView(
            context = activity,
            type = AndesFeedbackScreenType.Simple(
                AndesFeedbackScreenColor.RED
            ),
            header = AndesFeedbackScreenHeader(
                AndesFeedbackScreenText(
                    "Title"
                )
            ),
            body = null
        )

        startActivity(screenView)
        screenView.setFeedbackScreenActions(
            AndesFeedbackScreenActions(
                AndesFeedbackScreenButton(
                    "Button",
                    onClick
                ),
                onClick
            )
        )

        with(screenView.getButtonGroup() as AndesButtonGroup) {
            (getChildAt(0) as AndesButton).text assertEquals "Button"
        }
    }

    @Test
    fun `FeedbackScreen Simple without body set actions`() {
        val onClick = View.OnClickListener { }
        val screenView = AndesFeedbackScreenView(
            context = activity,
            type = AndesFeedbackScreenType.Simple(
                AndesFeedbackScreenColor.RED
            ),
            header = AndesFeedbackScreenHeader(
                AndesFeedbackScreenText(
                    "Title"
                )
            ),
            body = null
        )

        startActivity(screenView)
        screenView.setFeedbackScreenActions(
            AndesFeedbackScreenActions(
                AndesButtonGroup(context, listOf(
                    AndesButton(context, buttonText = "Button").apply {
                        onClick
                    }
                )),
                onClick
            )
        )

        with(screenView.getButtonGroup() as AndesButtonGroup) {
            (getChildAt(0) as AndesButton).text assertEquals "Button"
        }
    }

    @Test
    fun `FeedbackScreen Simple without body set button group actions`() {
        val onClick = View.OnClickListener { }
        val screenView = AndesFeedbackScreenView(
            context = activity,
            type = AndesFeedbackScreenType.Simple(
                AndesFeedbackScreenColor.RED
            ),
            header = AndesFeedbackScreenHeader(
                AndesFeedbackScreenText(
                    "Title"
                )
            ),
            body = null
        )

        startActivity(screenView)
        screenView.setFeedbackScreenActions(
            AndesFeedbackScreenActions(
                AndesButtonGroup(context, listOf(
                    AndesButton(context, buttonText = "Button"),
                    AndesButton(context, buttonText = "Button 2")
                )),
                onClick
            )
        )

        with(screenView.getButtonGroup() as AndesButtonGroup) {
            (getChildAt(1) as AndesButton).text assertEquals "Button 2"
            importantForAccessibility assertEquals ScrollView.IMPORTANT_FOR_ACCESSIBILITY_AUTO
        }
    }

    @Test
    fun `FeedbackScreen Congrats replace body body`() {
        val message = AndesMessage(context, body = "Body")
        val screenView = AndesFeedbackScreenView(
            context = activity,
            type = AndesFeedbackScreenType.Congrats,
            header = AndesFeedbackScreenHeader(
                AndesFeedbackScreenText(
                    "Title"
                )
            ),
            body = null
        )

        startActivity(screenView)
        screenView.setFeedbackBody(message)
        screenView.setFeedbackBody(message)
        screenView.setFeedbackBody(message)

        val config: Any = getField("config", screenView)
        val body: Any = getField("body", config)
        val bodyView: View = getField("view", body)
        val viewGroup: ViewGroup = bodyView.parent as ViewGroup

        Assert.assertEquals(7, viewGroup.childCount)
        Assert.assertNotNull(body)
        Assert.assertEquals(message, bodyView)
    }

    @Test
    fun `FeedbackScreen Simple replace body  and replace header repeatedly`() {
        val screenView = AndesFeedbackScreenView(
            context = activity,
            type = AndesFeedbackScreenType.Simple(
                AndesFeedbackScreenColor.RED
            ),
            header = AndesFeedbackScreenHeader(
                AndesFeedbackScreenText(
                    "Title"
                )
            ),
            body = null
        )
        val feedbackScreenHeader = AndesFeedbackScreenHeader(
            AndesFeedbackScreenText(
                "New Title",
                AndesFeedbackScreenTextDescription(
                    "New Description",
                    AndesBodyLinks(arrayListOf(AndesBodyLink(1, 2), AndesBodyLink(3, 4)), mock())
                ),
                "New Highlight"
            )
        )
        startActivity(screenView)
        screenView.setFeedbackBody(View(context))
        screenView.setFeedbackScreenHeader(feedbackScreenHeader)
        screenView.setFeedbackScreenHeader(feedbackScreenHeader)

        val config: Any = getField("config", screenView)
        val body: Any = getField("body", config)
        val bodyView: View = getField("view", body)
        val viewGroup: ViewGroup = bodyView.parent as ViewGroup

        7 assertEquals viewGroup.childCount
    }

    @Test
    fun `FeedbackScreen status bar color is reset when scrollview is removed`() {
        val layout = LinearLayout(context)
        val screenView = AndesFeedbackScreenView(
            context = activity,
            type = AndesFeedbackScreenType.Congrats,
            header = AndesFeedbackScreenHeader(
                AndesFeedbackScreenText(
                    "Title"
                )
            ),
            body = null
        )

        layout.addView(screenView)
        startActivity(layout)
        layout.removeView(screenView)

        assertTrue(activity.window.statusBarColor == 0)
    }

    private fun AndesFeedbackScreenView.getDescription() =
        findViewById<TextView>(R.id.andes_feedbackscreen_header_description)

    private fun AndesFeedbackScreenView.getTitle() =
        findViewById<TextView>(R.id.andes_feedbackscreen_header_title)

    private fun AndesFeedbackScreenView.getHighlight() =
        findViewById<TextView>(R.id.andes_feedbackscreen_header_highlight)

    private fun AndesFeedbackScreenView.getThumbnailBadge() =
        findViewById<FrameLayout>(R.id.andes_feedbackscreen_header_image).getChildAt(0) as AndesThumbnailBadge

    private fun AndesFeedbackScreenView.getButtonGroup() =
        findViewById<ConstraintLayout>(R.id.andes_feedbackscreen_content_buttongroup).getChildAt(0)

    fun <T> getField(name: String, target: Any): T {
        val field = target::class.java.getDeclaredField(name)
        field.isAccessible = true
        return field.get(target) as T
    }
}
