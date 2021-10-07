package com.mercadolibre.android.andesui.feedback.screen

import android.content.Context
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenHeader
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.feedback.screen.color.AndesFeedbackScreenColor
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenType
import com.mercadolibre.android.andesui.thumbnail.AndesThumbnailBadge
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
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
            badgeComponent.color assertEquals AndesBadgeIconType.SUCCESS
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
            badgeComponent.color assertEquals AndesBadgeIconType.SUCCESS
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
            badgeComponent.color assertEquals AndesBadgeIconType.SUCCESS
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
            badgeComponent.color assertEquals AndesBadgeIconType.WARNING
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
            badgeComponent.color assertEquals AndesBadgeIconType.ERROR
        }
    }

    private fun startActivity(view: View) {
        activity.setContentView(view)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    private fun AndesFeedbackScreenView.getThumbnailCongrats() =
        findViewById<AndesThumbnailBadge>(R.id.andes_feedbackscreen_congrats_header_image)

    private fun AndesFeedbackScreenView.getThumbnailSimple() =
        findViewById<AndesThumbnailBadge>(R.id.andes_feedbackscreen_header_image)

    private fun setupTestActivity() {
        robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        activity = robolectricActivity.get()
    }
}
