package com.mercadolibre.android.andesui.floatingmenu.rows

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.AndesButton
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.spy
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP], qualifiers = "w720dp-h1600dp-mdpi")
class AndesFloatingMenuHeightCalculatorTest {

    private val applicationContext: Context = ApplicationProvider.getApplicationContext()
    private lateinit var trigger: AndesButton

    @Before
    fun setUp() {
        SoLoader.setInTestMode()
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(applicationContext)
            // other setters
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(applicationContext, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
        setupTestActivity()
    }

    @Test
    fun `FloatingMenu height wrapped when rows are less than maxrows and there is space (BOTTOM)`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 50)
        val spyTrigger = createSpyTrigger(trigger)

        val vector = calculateFloatingMenuHeightVector(
            spyTrigger,
            200,
            2,
            AndesFloatingMenuRows.Medium.rows.maxItemsShown()
        )

        Assert.assertEquals(400, vector.size)
    }

    @Test
    fun `FloatingMenu height wrapped when rows equals maxrows and there is space (BOTTOM)`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 50)
        val spyTrigger = createSpyTrigger(trigger)

        val vector = calculateFloatingMenuHeightVector(
            spyTrigger,
            200,
            3,
            AndesFloatingMenuRows.Small.rows.maxItemsShown()
        )

        Assert.assertEquals(600, vector.size)
    }

    @Test
    fun `FloatingMenu height is maxrows and a half when rows are greater than maxrows and there is space (BOTTOM)`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 50)
        val spyTrigger = createSpyTrigger(trigger)

        val vector = calculateFloatingMenuHeightVector(
            spyTrigger,
            200,
            10,
            AndesFloatingMenuRows.Small.rows.maxItemsShown()
        )

        Assert.assertEquals(700, vector.size)
    }

    @Test
    fun `FloatingMenu height is max rows possible when rows are greater than maxrows and there is not space (BOTTOM)`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 50)
        val spyTrigger = createSpyTrigger(trigger)

        val vector = calculateFloatingMenuHeightVector(
            spyTrigger,
            200,
            50,
            20
        )

        // Available screen is lower than 1600 because of android status bar
        Assert.assertEquals(1300, vector.size)
    }

    @Test
    fun `FloatingMenu height wrapped when rows are less than maxrows and there is space (TOP)`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 50, 1400)
        val spyTrigger = createSpyTrigger(trigger)

        val vector = calculateFloatingMenuHeightVector(
            spyTrigger,
            200,
            2,
            AndesFloatingMenuRows.Medium.rows.maxItemsShown()
        )

        Assert.assertEquals(400, vector.size)
    }

    @Test
    fun `FloatingMenu height wrapped when rows equals maxrows and there is space (TOP)`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 50, 1400)
        val spyTrigger = createSpyTrigger(trigger)

        val vector = calculateFloatingMenuHeightVector(
            spyTrigger,
            200,
            3,
            3
        )

        Assert.assertEquals(600, vector.size)
    }

    @Test
    fun `FloatingMenu height is maxrows and a half when rows are greater than maxrows and there is space (TOP)`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 50, 1400)
        val spyTrigger = createSpyTrigger(trigger)

        val vector = calculateFloatingMenuHeightVector(
            spyTrigger,
            200,
            10,
            3
        )

        Assert.assertEquals(700, vector.size)
    }

    @Test
    fun `FloatingMenu height is max rows possible when rows are greater than maxrows and there is not space (TOP)`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 50, 1400)
        val spyTrigger = createSpyTrigger(trigger)

        val vector = calculateFloatingMenuHeightVector(
            spyTrigger,
            200,
            50,
            20
        )

        // Available screen is lower than 1600 because of android status bar
        Assert.assertEquals(1300, vector.size)
    }

    @Test
    fun `FloatingMenu height is max possible when maxrows is null and there is not space (BOTTOM)`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 50)
        val spyTrigger = createSpyTrigger(trigger)

        val vector = calculateFloatingMenuHeightVector(
            spyTrigger,
            200,
            50,
            AndesFloatingMenuRows.Max.rows.maxItemsShown()
        )

        // Available screen is lower than 1600 because of android status bar
        Assert.assertEquals(1300, vector.size)
    }

    @Test
    fun `FloatingMenu height is max possible when maxrows is null and there is not space (TOP)`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 50, 1400)
        val spyTrigger = createSpyTrigger(trigger)

        val vector = calculateFloatingMenuHeightVector(
            spyTrigger,
            200,
            50,
            AndesFloatingMenuRows.Max.rows.maxItemsShown()
        )

        // Available screen is lower than 1600 because of android status bar
        Assert.assertEquals(1300, vector.size)
    }

    private fun setupTestActivity() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        val rl = RelativeLayout(activity).apply {
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
        }
        activity.setTheme(R.style.Theme_AppCompat_NoActionBar)
        trigger = AndesButton(activity)
        rl.addView(trigger)
        activity.setContentView(rl)

        robolectricActivity.start().postCreate(null).resume().visible()
    }

    private fun setupTriggerSizeAndPosition(
        width: Int,
        height: Int,
        leftMargin: Int = 0,
        topMargin: Int = 0
    ): RelativeLayout.LayoutParams {
        return RelativeLayout.LayoutParams(width, height).apply {
            this.leftMargin = leftMargin
            this.topMargin = topMargin
        }
    }

    private fun createSpyTrigger(trigger: AndesButton): AndesButton {
        return spy(trigger).apply {
            doAnswer { invocationOnMock ->
                (invocationOnMock.getArgument(0) as Rect).set(Rect(0, 0, 720, 1600))
            }.`when`(this).getWindowVisibleDisplayFrame(any())
        }
    }
}
