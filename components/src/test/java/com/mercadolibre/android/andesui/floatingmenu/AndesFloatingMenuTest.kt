package com.mercadolibre.android.andesui.floatingmenu

import android.content.Context
import android.os.Build
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
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [Build.VERSION_CODES.LOLLIPOP],
    qualifiers = "w720dp-h1600dp-mdpi",
    shadows = [ShadowPopupWindowDismissible::class]
)
class AndesFloatingMenuTest {

    private val applicationContext: Context = ApplicationProvider.getApplicationContext()
    private lateinit var activity: AppCompatActivity
    private lateinit var trigger: Button
    private lateinit var andesList: AndesList
    private val mDismissListener: AndesFloatingMenu.OnDismissListener = mock()
    private val mShowListener: AndesFloatingMenu.OnShowListener = mock()

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
    fun `FloatingMenu created correctly`() {
        val reqOrientation = AndesFloatingMenuOrientation.Left
        val reqWidth = AndesFloatingMenuWidth.Fixed
        val reqRows = AndesFloatingMenuRows.Medium

        val floatingMenu = AndesFloatingMenu(activity, andesList, reqWidth, reqRows, reqOrientation)

        Assert.assertEquals(reqOrientation, floatingMenu.orientation)
        Assert.assertEquals(reqWidth, floatingMenu.width)
        Assert.assertEquals(reqRows, floatingMenu.rows)
    }

    @Test
    fun `FloatingMenu can't show already showed component`() {
        val floatingMenu = spy(AndesFloatingMenu(activity, andesList).apply {
            setOnShowListener(mShowListener)
        })

        floatingMenu.show(trigger)
        floatingMenu.show(trigger)

        verify(mShowListener).onShow()
    }

    @Test
    fun `FloatingMenu can't dismiss not showed component`() {
        val floatingMenu = spy(AndesFloatingMenu(activity, andesList).apply {
            setOnDismissListener(mDismissListener)
        })

        floatingMenu.dismiss()

        verify(mDismissListener, never()).onDismiss()
    }

    @Test
    fun `FloatingMenu can show component`() {
        val floatingMenu = spy(AndesFloatingMenu(activity, andesList).apply {
            setOnShowListener(mShowListener)
        })

        floatingMenu.show(trigger)

        verify(mShowListener).onShow()
    }

    @Test
    fun `FloatingMenu can dismiss component`() {
        val floatingMenu = spy(AndesFloatingMenu(activity, andesList).apply {
            setOnDismissListener(mDismissListener)
        })

        floatingMenu.show(trigger)
        floatingMenu.dismiss()

        verify(mDismissListener).onDismiss()
    }

    @Test
    fun `should not show FloatingMenu when context is not activity`() {
        val floatingMenu = spy(AndesFloatingMenu(applicationContext, andesList).apply {
            setOnShowListener(mShowListener)
        })
        floatingMenu.show(trigger)

        verify(mShowListener, never()).onShow()
    }

    @Test
    fun `should not show FloatingMenu when activity is finishing`() {
        val floatingMenu = spy(AndesFloatingMenu(activity, andesList).apply {
            setOnShowListener(mShowListener)
        })

        activity.finish()
        floatingMenu.show(trigger)

        verify(mShowListener, never()).onShow()
    }

    @Test
    fun `should not show FloatingMenu when target is not attached to activity`() {
        val floatingMenu = spy(AndesFloatingMenu(activity, andesList).apply {
            setOnShowListener(mShowListener)
        })
        val spyTrigger: View = Mockito.spy(trigger)
        Mockito.`when`(spyTrigger.isAttachedToWindow).thenReturn(false)

        floatingMenu.show(spyTrigger)

        verify(mShowListener, never()).onShow()
    }

    private fun setupTestActivity() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        activity = robolectricActivity.get()
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
    }
}
