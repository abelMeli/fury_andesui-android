package com.mercadolibre.android.andesui.floatingmenu.width

import android.content.Context
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
import com.mercadolibre.android.andesui.floatingmenu.orientation.AndesFloatingMenuOrientation
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP], qualifiers = "w720dp-h1600dp")
class AndesFloatingMenuWidthCalculatorTest {

    private val applicationContext: Context = ApplicationProvider.getApplicationContext()
    private val offset =
        applicationContext.resources.getDimensionPixelSize(R.dimen.andes_floatingmenu_yoffset)
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
    fun `FloatingMenu has trigger width when FIXED`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 50)

        val vector = calculateFloatingMenuWidthVector(
            AndesFloatingMenuOrientation.Left.orientation,
            trigger,
            AndesFloatingMenuWidth.Fixed.size.getWidth(trigger)
        )

        Assert.assertEquals(200, vector.size)
    }

    @Test
    fun `FloatingMenu has desired width when CUSTOM if there is space in screen (LEFT)`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 50)

        val vector = calculateFloatingMenuWidthVector(
            AndesFloatingMenuOrientation.Left.orientation,
            trigger,
            AndesFloatingMenuWidth.Custom(220).size.getWidth(trigger)
        )

        Assert.assertEquals(220, vector.size)
    }

    @Test
    fun `FloatingMenu has desired width when CUSTOM if there is space in screen (RIGHT)`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 50)

        val vector = calculateFloatingMenuWidthVector(
            AndesFloatingMenuOrientation.Left.orientation,
            trigger,
            AndesFloatingMenuWidth.Custom(400).size.getWidth(trigger)
        )

        Assert.assertEquals(400, vector.size)
    }

    @Test
    fun `FloatingMenu has max width when CUSTOM and not space in screen (go LEFT)`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 720 - 200 - 50)

        val vector = calculateFloatingMenuWidthVector(
            AndesFloatingMenuOrientation.Left.orientation,
            trigger,
            AndesFloatingMenuWidth.Custom(720).size.getWidth(trigger)
        )

        Assert.assertEquals(720 - 50 - offset, vector.size)
    }

    @Test
    fun `FloatingMenu has max width when CUSTOM and not space in screen (go RIGHT)`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 50)

        val vector = calculateFloatingMenuWidthVector(
            AndesFloatingMenuOrientation.Left.orientation,
            trigger,
            AndesFloatingMenuWidth.Custom(720).size.getWidth(trigger)
        )

        Assert.assertEquals(720 - 50 - offset, vector.size)
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
        activity.setTheme(R.style.Theme_AppCompat)
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
}
