package com.mercadolibre.android.andesui.floatingmenu.factory

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.widget.Button
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
import com.mercadolibre.android.andesui.floatingmenu.orientation.AndesFloatingMenuOrientation
import com.mercadolibre.android.andesui.floatingmenu.rows.AndesFloatingMenuRows
import com.mercadolibre.android.andesui.floatingmenu.width.AndesFloatingMenuWidth
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.AndesListViewItem
import com.mercadolibre.android.andesui.list.AndesListViewItemSimple
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.searchbox.AndesSearchbox
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
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
@Config(sdk = [TEST_ANDROID_VERSION_CODE], qualifiers = "w720dp-h1600dp-mdpi")
class AndesFloatingMenuConfigFactoryTest {

    private val applicationContext: Context = ApplicationProvider.getApplicationContext()
    private val offset =
        applicationContext.resources.getDimensionPixelSize(R.dimen.andes_floatingmenu_yoffset)
    private lateinit var trigger: Button
    private lateinit var andesList: AndesList
    private lateinit var andesSearchbox: AndesSearchbox

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
    fun `Config created req LEFT-BOTTOM then LEFT-BOTTOM`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 100)
        val attrs = AndesFloatingMenuAttrs(
            AndesFloatingMenuRows.Small,
            AndesFloatingMenuWidth.Custom(100),
            AndesFloatingMenuOrientation.Left
        )
        val spyTrigger = createSpyTrigger(trigger)
        val config = AndesFloatingMenuConfigFactory.create(attrs, andesList, spyTrigger)
        Assert.assertEquals(R.style.Andes_FloatingMenuBottomAnimation, config.animation)
        Assert.assertEquals(100, config.xOffset)
        Assert.assertEquals(offset, config.yOffset)
    }

    @Test
    fun `Config created req LEFT-BOTTOM then LEFT-TOP`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 100, 1400)
        val attrs = AndesFloatingMenuAttrs(
            AndesFloatingMenuRows.Small,
            AndesFloatingMenuWidth.Custom(100),
            AndesFloatingMenuOrientation.Left
        )
        val spyTrigger = createSpyTrigger(trigger)
        val config = AndesFloatingMenuConfigFactory.create(attrs, andesList, spyTrigger)
        Assert.assertEquals(R.style.Andes_FloatingMenuTopAnimation, config.animation)
        Assert.assertEquals(100, config.xOffset)
        Assert.assertEquals(-100 - offset - config.height, config.yOffset)
    }

    @Test
    fun `Config created req LEFT-BOTTOM then RIGHT-BOTTOM`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 100)
        val attrs = AndesFloatingMenuAttrs(
            AndesFloatingMenuRows.Small,
            AndesFloatingMenuWidth.Custom(400),
            AndesFloatingMenuOrientation.Left
        )
        val spyTrigger = createSpyTrigger(trigger)
        val config = AndesFloatingMenuConfigFactory.create(attrs, andesList, spyTrigger)
        Assert.assertEquals(R.style.Andes_FloatingMenuBottomAnimation, config.animation)
        Assert.assertEquals(0, config.xOffset)
        Assert.assertEquals(offset, config.yOffset)
    }

    @Test
    fun `Config created req RIGHT-BOTTOM then RIGHT-BOTTOM`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 500)
        val attrs = AndesFloatingMenuAttrs(
            AndesFloatingMenuRows.Small,
            AndesFloatingMenuWidth.Custom(100),
            AndesFloatingMenuOrientation.Right
        )
        val spyTrigger = createSpyTrigger(trigger)
        val config = AndesFloatingMenuConfigFactory.create(attrs, andesList, spyTrigger)
        Assert.assertEquals(R.style.Andes_FloatingMenuBottomAnimation, config.animation)
        Assert.assertEquals(0, config.xOffset)
        Assert.assertEquals(offset, config.yOffset)
    }

    @Test
    fun `Config created req RIGHT-BOTTOM then RIGHT-TOP`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 500, 1400)
        val attrs = AndesFloatingMenuAttrs(
            AndesFloatingMenuRows.Small,
            AndesFloatingMenuWidth.Custom(100),
            AndesFloatingMenuOrientation.Right
        )
        val spyTrigger = createSpyTrigger(trigger)
        val config = AndesFloatingMenuConfigFactory.create(attrs, andesList, spyTrigger)
        Assert.assertEquals(R.style.Andes_FloatingMenuTopAnimation, config.animation)
        Assert.assertEquals(0, config.xOffset)
        Assert.assertEquals(-100 - offset - config.height, config.yOffset)
    }

    @Test
    fun `Config created req RIGHT-BOTTOM then LEFT-BOTTOM`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 500)
        val attrs = AndesFloatingMenuAttrs(
            AndesFloatingMenuRows.Small,
            AndesFloatingMenuWidth.Custom(400),
            AndesFloatingMenuOrientation.Right
        )
        val spyTrigger = createSpyTrigger(trigger)
        val config = AndesFloatingMenuConfigFactory.create(attrs, andesList, spyTrigger)
        Assert.assertEquals(R.style.Andes_FloatingMenuBottomAnimation, config.animation)
        Assert.assertEquals(200 - 400, config.xOffset)
        Assert.assertEquals(offset, config.yOffset)
    }

    @Test
    fun `Config created req LEFT-BOTTOM then RIGHT-TOP`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 500, 1400)
        val attrs = AndesFloatingMenuAttrs(
            AndesFloatingMenuRows.Small,
            AndesFloatingMenuWidth.Custom(400),
            AndesFloatingMenuOrientation.Right
        )
        val spyTrigger = createSpyTrigger(trigger)
        val config = AndesFloatingMenuConfigFactory.create(attrs, andesList, spyTrigger)
        Assert.assertEquals(R.style.Andes_FloatingMenuTopAnimation, config.animation)
        Assert.assertEquals(200 - 400, config.xOffset)
        Assert.assertEquals(-100 - offset - config.height, config.yOffset)
    }

    @Test
    fun `Config created req FIXED RIGHT-BOTTOM then RIGHT-BOTTOM`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 500)
        val attrs = AndesFloatingMenuAttrs(
            AndesFloatingMenuRows.Small,
            AndesFloatingMenuWidth.Fixed,
            AndesFloatingMenuOrientation.Right
        )
        val spyTrigger = createSpyTrigger(trigger)
        val config = AndesFloatingMenuConfigFactory.create(attrs, andesList, spyTrigger)
        Assert.assertEquals(R.style.Andes_FloatingMenuBottomAnimation, config.animation)
        Assert.assertEquals(0, config.xOffset)
        Assert.assertEquals(offset, config.yOffset)
    }

    @Test
    fun `Config created req FIXED LEFT-BOTTOM then LEFT-BOTTOM`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 500)
        val attrs = AndesFloatingMenuAttrs(
            AndesFloatingMenuRows.Small,
            AndesFloatingMenuWidth.Fixed,
            AndesFloatingMenuOrientation.Left
        )
        val spyTrigger = createSpyTrigger(trigger)
        val config = AndesFloatingMenuConfigFactory.create(attrs, andesList, spyTrigger)
        Assert.assertEquals(R.style.Andes_FloatingMenuBottomAnimation, config.animation)
        Assert.assertEquals(0, config.xOffset)
        Assert.assertEquals(offset, config.yOffset)
    }

    @Test
    fun `Config created req LEFT-BOTTOM then LEFT-BOTTOM with AndesSearchbox`() {
        trigger.layoutParams = setupTriggerSizeAndPosition(200, 100, 100)
        val attrs = AndesFloatingMenuAttrs(
                AndesFloatingMenuRows.Small,
                AndesFloatingMenuWidth.Custom(100),
                AndesFloatingMenuOrientation.Left
        )
        val spyTrigger = createSpyTrigger(trigger)
        val config = AndesFloatingMenuConfigFactory.create(attrs, andesList, andesSearchbox, spyTrigger)
        Assert.assertEquals(R.style.Andes_FloatingMenuBottomAnimation, config.animation)
        Assert.assertEquals(offset, config.yOffset)
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
        trigger = Button(activity)
        rl.addView(trigger)
        activity.setContentView(rl)

        robolectricActivity.start().postCreate(null).resume().visible()

        andesList = AndesList(activity)
        andesList.delegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no-op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemSimple(activity, "Postion $position")
            }

            override fun getDataSetSize(andesList: AndesList): Int = 5
        }
        andesSearchbox = AndesSearchbox(activity)
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

    private fun createSpyTrigger(trigger: Button): Button {
        return spy(trigger).apply {
            doAnswer { invocationOnMock ->
                (invocationOnMock.getArgument(0) as Rect).set(Rect(0, 0, 720, 1600))
            }.`when`(this).getWindowVisibleDisplayFrame(any())
        }
    }
}
