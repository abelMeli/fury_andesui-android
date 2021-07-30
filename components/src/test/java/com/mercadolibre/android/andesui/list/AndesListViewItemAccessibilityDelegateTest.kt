package com.mercadolibre.android.andesui.list

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.list.size.AndesListViewItemSize
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesListViewItemAccessibilityDelegateTest {

    private lateinit var activity: AppCompatActivity
    private lateinit var andesList: AndesList

    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext()
        SoLoader.setInTestMode()
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
        andesList = AndesList(context)
        setupActivityForTest()
    }

    private fun setupActivityForTest() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(andesList)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `AndesListViewItem accessibility with title only`() {
        // GIVEN
        val title = "Title"
        buildDelegateWith(AndesListViewItemSimple(context = activity, title = title))

        // WHEN
        val nodeInfo = andesList.recyclerViewComponent.getChildAt(0).createAccessibilityNodeInfo()

        // THEN
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$title,", nodeInfo.contentDescription)
    }

    @Test
    fun `AndesListViewItem accessibility with title and subtitle`() {
        // GIVEN
        val title = "Title"
        val subtitle = "Subtitle"
        buildDelegateWith(
            AndesListViewItemSimple(
                context = activity,
                title = title,
                subtitle = subtitle
            )
        )

        // WHEN
        val nodeInfo = andesList.recyclerViewComponent.getChildAt(0).createAccessibilityNodeInfo()

        // THEN
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$title,$subtitle", nodeInfo.contentDescription)
    }

    @Test
    fun `AndesListViewItem accessibility with SMALL, title and subtitle do not read subtitle`() {
        // GIVEN
        val title = "Title"
        val subtitle = "Subtitle"
        buildDelegateWith(
            AndesListViewItemSimple(
                context = activity,
                title = title,
                subtitle = subtitle,
                size = AndesListViewItemSize.SMALL
            )
        )

        // WHEN
        val nodeInfo = andesList.recyclerViewComponent.getChildAt(0).createAccessibilityNodeInfo()

        // THEN
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$title,", nodeInfo.contentDescription)
    }

    @Test
    fun `AndesListViewItem selected accessibility with title and subtitle`() {
        // GIVEN
        val title = "Title"
        val subtitle = "Subtitle"
        val selectedText = activity.resources.getString(R.string.andes_list_item_selected)
        buildDelegateWith(
            AndesListViewItemSimple(
                context = activity,
                title = title,
                subtitle = subtitle,
                itemSelected = true
            )
        )

        // WHEN
        val nodeInfo = andesList.recyclerViewComponent.getChildAt(0).createAccessibilityNodeInfo()

        // THEN
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$selectedText,$title,$subtitle", nodeInfo.contentDescription)
    }

    @Test
    fun `AndesListViewItem selected accessibility with title, subtitle and icon without contentDescription`() {
        // GIVEN
        val title = "Title"
        val subtitle = "Subtitle"
        val selectedText = activity.resources.getString(R.string.andes_list_item_selected)
        val icon = GradientDrawable()
        buildDelegateWith(
            AndesListViewItemSimple(
                context = activity,
                title = title,
                subtitle = subtitle,
                itemSelected = true,
                icon = icon
            )
        )

        // WHEN
        val nodeInfo = andesList.recyclerViewComponent.getChildAt(0).createAccessibilityNodeInfo()

        // THEN
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$selectedText,$title,$subtitle", nodeInfo.contentDescription)
    }

    @Test
    fun `AndesListViewItem selected accessibility with title, subtitle and icon with contentDescription`() {
        // GIVEN
        val title = "Title"
        val subtitle = "Subtitle"
        val selectedText = activity.resources.getString(R.string.andes_list_item_selected)
        val icon = GradientDrawable()
        val iconContentDescription = "Icon content"
        buildDelegateWith(
            AndesListViewItemSimple(
                context = activity,
                title = title,
                subtitle = subtitle,
                itemSelected = true,
                icon = icon,
                iconContentDescription = iconContentDescription
            )
        )

        // WHEN
        val nodeInfo = andesList.recyclerViewComponent.getChildAt(0).createAccessibilityNodeInfo()

        // THEN
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$selectedText,$iconContentDescription,$title,$subtitle", nodeInfo.contentDescription)
    }

    @Test
    fun `AndesListViewItem selected accessibility with title, subtitle and avatar without contentDescription`() {
        // GIVEN
        val title = "Title"
        val subtitle = "Subtitle"
        val selectedText = activity.resources.getString(R.string.andes_list_item_selected)
        val avatar = GradientDrawable()
        buildDelegateWith(
            AndesListViewItemSimple(
                context = activity,
                title = title,
                subtitle = subtitle,
                itemSelected = true,
                avatar = avatar
            )
        )

        // WHEN
        val nodeInfo = andesList.recyclerViewComponent.getChildAt(0).createAccessibilityNodeInfo()

        // THEN
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$selectedText,$title,$subtitle", nodeInfo.contentDescription)
    }

    @Test
    fun `AndesListViewItem selected accessibility with title, subtitle and avatar with contentDescription`() {
        // GIVEN
        val title = "Title"
        val subtitle = "Subtitle"
        val selectedText = activity.resources.getString(R.string.andes_list_item_selected)
        val avatar = GradientDrawable()
        val avatarContentDescription = "Avatar content"
        buildDelegateWith(
            AndesListViewItemSimple(
                context = activity,
                title = title,
                subtitle = subtitle,
                itemSelected = true,
                avatar = avatar,
                avatarContentDescription = avatarContentDescription
            )
        )

        // WHEN
        val nodeInfo = andesList.recyclerViewComponent.getChildAt(0).createAccessibilityNodeInfo()

        // THEN
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$selectedText,$avatarContentDescription,$title,$subtitle", nodeInfo.contentDescription)
    }

    private fun buildDelegateWith(item: AndesListViewItemSimple) {
        andesList.delegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no-op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return item
            }

            override fun getDataSetSize(andesList: AndesList): Int = 1
        }
    }
}