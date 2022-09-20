package com.mercadolibre.android.andesui.list

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.list.size.AndesListViewItemSize
import com.mercadolibre.android.andesui.list.type.AndesListType
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.thumbnail.AndesThumbnail
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailType
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesListTest {
    val context: Context = ApplicationProvider.getApplicationContext()
    private lateinit var robolectricActivity: ActivityController<AppCompatActivity>
    private lateinit var activity: Activity

    companion object {
        private const val SIZE = 10
    }

    @Before
    fun setup() {
        setupActivityForTest()
    }

    private fun setupActivityForTest() {
        robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
    }

    @Test
    fun `test delegate`() {
        val item = AndesListViewItemSimple(context, "test")
        val view = View(context)
        val list = AndesList(context)

        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                Assert.assertEquals(0, position)
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return item
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }

        andesListDelegate.onItemClick(list, 0)
        Assert.assertEquals(SIZE, andesListDelegate.getDataSetSize(list))
        Assert.assertEquals(item, andesListDelegate.bind(list, view, 0))
    }

    @Test
    fun `test list with different sizes`() {
        var list = AndesList(context, AndesListViewItemSize.SMALL, AndesListType.SIMPLE)
        Assert.assertEquals(list.size, AndesListViewItemSize.SMALL)

        list = AndesList(context, AndesListViewItemSize.MEDIUM, AndesListType.SIMPLE)
        Assert.assertEquals(list.size, AndesListViewItemSize.MEDIUM)

        list = AndesList(context, AndesListViewItemSize.LARGE, AndesListType.SIMPLE)
        Assert.assertEquals(list.size, AndesListViewItemSize.LARGE)
    }

    @Test
    fun `test list with different item types`() {
        var list = AndesList(context, AndesListViewItemSize.MEDIUM, AndesListType.SIMPLE)
        Assert.assertEquals(list.type, AndesListType.SIMPLE)

        list = AndesList(context, AndesListViewItemSize.MEDIUM, AndesListType.CHEVRON)
        Assert.assertEquals(list.type, AndesListType.CHEVRON)

        list = AndesList(context, AndesListViewItemSize.MEDIUM, AndesListType.CHECK_BOX)
        Assert.assertEquals(list.type, AndesListType.CHECK_BOX)

        list = AndesList(context, AndesListViewItemSize.MEDIUM, AndesListType.RADIO_BUTTON)
        Assert.assertEquals(list.type, AndesListType.RADIO_BUTTON)
    }

    @Test
    fun `test list item selection`() {

        val list = AndesList(context, AndesListViewItemSize.MEDIUM, AndesListType.SIMPLE)
        val item = AndesListViewItemSimple(context, "test")
        val listItems = ArrayList<AndesListViewItem>()

        for (i in 1..SIZE) {
            listItems.add(item)
        }

        listItems[5].itemSelected = true

        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                Assert.assertEquals(true, listItems[position].itemSelected)
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return item
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }

        list.delegate = andesListDelegate
        andesListDelegate.onItemClick(list, 5)
    }

    @Test
    fun `AndesList list item with avatarType Default`() {
        // GIVEN
        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no-op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemSimple(activity, "test")
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }

        val andesList = AndesList(activity, AndesListViewItemSize.MEDIUM, AndesListType.SIMPLE)
        andesList.delegate = andesListDelegate

        // WHEN
        setContentToActivity(andesList)
        startActivitty()

        // THEN
        val firstItem =
            andesList.recyclerViewComponent.findViewHolderForAdapterPosition(0)?.itemView
        Assert.assertNotNull(firstItem)
        Assert.assertNotNull(firstItem?.findViewById(R.id.andes_list_item_asset))
        Assert.assertEquals(AndesThumbnailType.ICON, (firstItem?.findViewById(R.id.andes_list_item_asset) as AndesThumbnail).type)
        Assert.assertEquals(0, (firstItem.findViewById(R.id.custom_view_container) as FrameLayout).childCount)
    }

    @Test
    fun `AndesList list item with avatarType ImageCircle`() {
        // GIVEN
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no-op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemSimple(activity, "test", avatar = drawable, avatarType = AndesThumbnailType.IMAGE_CIRCLE)
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }

        val andesList = AndesList(activity, AndesListViewItemSize.MEDIUM, AndesListType.SIMPLE)
        andesList.delegate = andesListDelegate

        // WHEN
        setContentToActivity(andesList)
        startActivitty()

        // THEN
        val firstItem =
            andesList.recyclerViewComponent.findViewHolderForAdapterPosition(0)?.itemView
        Assert.assertNotNull(firstItem)
        Assert.assertNotNull(firstItem?.findViewById(R.id.andes_list_item_asset))
        Assert.assertEquals(AndesThumbnailType.IMAGE_CIRCLE, (firstItem?.findViewById(R.id.andes_list_item_asset) as AndesThumbnail).type)
        Assert.assertEquals(0, (firstItem.findViewById(R.id.custom_view_container) as FrameLayout).childCount)
    }

    @Test
    fun `AndesList list item with avatarType ImageCircle, content CustomView and no title`() {
        // GIVEN
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no-op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemSimple(
                    activity,
                    avatar = drawable,
                    avatarType = AndesThumbnailType.IMAGE_CIRCLE,
                    content = View(andesList.context)
                )
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }

        val andesList = AndesList(activity, AndesListViewItemSize.MEDIUM, AndesListType.SIMPLE)
        andesList.delegate = andesListDelegate

        // WHEN
        setContentToActivity(andesList)
        startActivitty()

        // THEN
        val emptyString = ""
        val firstItem =
            andesList.recyclerViewComponent.findViewHolderForAdapterPosition(0)?.itemView
        Assert.assertNotNull(firstItem)
        Assert.assertNotNull(firstItem?.findViewById(R.id.andes_list_item_asset))
        Assert.assertEquals(emptyString, (firstItem?.findViewById(R.id.text_view_item_title) as TextView).text.toString())
        Assert.assertEquals(AndesThumbnailType.IMAGE_CIRCLE, (firstItem.findViewById(R.id.andes_list_item_asset) as AndesThumbnail).type)
        Assert.assertEquals(1, (firstItem.findViewById(R.id.custom_view_container) as FrameLayout).childCount)
    }

    @Test
    fun `AndesList type chevron`() {
        // GIVEN
        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no-op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemChevron(activity, "test")
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }

        val andesList = AndesList(activity, AndesListViewItemSize.SMALL, AndesListType.CHEVRON)
        andesList.delegate = andesListDelegate

        // WHEN
        setContentToActivity(andesList)
        startActivitty()

        // THEN
        val firstItem =
            andesList.recyclerViewComponent.findViewHolderForAdapterPosition(0)?.itemView
        Assert.assertNotNull(firstItem)
        Assert.assertNotNull(firstItem?.findViewById(R.id.andes_thumbnail_chevron))
        Assert.assertEquals(AndesThumbnailType.ICON, (firstItem?.findViewById(R.id.andes_list_item_asset) as AndesThumbnail).type)
        Assert.assertEquals(0, (firstItem.findViewById(R.id.custom_view_container) as FrameLayout).childCount)
    }

    @Test
    fun `AndesList type chevron with avatarType ImageCircle`() {
        // GIVEN
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no-op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemChevron(activity, "test", avatar = drawable, avatarType = AndesThumbnailType.IMAGE_CIRCLE)
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }

        val andesList = AndesList(activity, AndesListViewItemSize.SMALL, AndesListType.CHEVRON)
        andesList.delegate = andesListDelegate

        // WHEN
        setContentToActivity(andesList)
        startActivitty()

        // THEN
        val firstItem =
            andesList.recyclerViewComponent.findViewHolderForAdapterPosition(0)?.itemView
        Assert.assertNotNull(firstItem)
        Assert.assertNotNull(firstItem?.findViewById(R.id.andes_thumbnail_chevron))
        Assert.assertEquals(AndesThumbnailType.IMAGE_CIRCLE, (firstItem?.findViewById(R.id.andes_list_item_asset) as AndesThumbnail).type)
        Assert.assertEquals(0, (firstItem.findViewById(R.id.custom_view_container) as FrameLayout).childCount)
    }

    @Test
    fun `AndesList type chevron with avatarType ImageCircle, content CustomView and no title`() {
        // GIVEN
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no-op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemChevron(
                    activity,
                    avatar = drawable,
                    avatarType = AndesThumbnailType.IMAGE_CIRCLE,
                    content = View(andesList.context)
                )
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }

        val andesList = AndesList(activity, AndesListViewItemSize.SMALL, AndesListType.CHEVRON)
        andesList.delegate = andesListDelegate

        // WHEN
        setContentToActivity(andesList)
        startActivitty()

        // THEN
        val emptyString = ""
        val firstItem =
            andesList.recyclerViewComponent.findViewHolderForAdapterPosition(0)?.itemView
        Assert.assertNotNull(firstItem)
        Assert.assertNotNull(firstItem?.findViewById(R.id.andes_thumbnail_chevron))
        Assert.assertEquals(emptyString, (firstItem?.findViewById(R.id.text_view_item_title) as TextView).text.toString())
        Assert.assertEquals(AndesThumbnailType.IMAGE_CIRCLE, (firstItem.findViewById(R.id.andes_list_item_asset) as AndesThumbnail).type)
        Assert.assertEquals(1, (firstItem.findViewById(R.id.custom_view_container) as FrameLayout).childCount)
    }

    @Test
    fun `test list with item divider active`() {
        val list = AndesList(context)
        val spyList = Mockito.spy<AndesList>(list)
        val item = AndesListViewItemSimple(context, "test")

        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return item
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }

        spyList.delegate = andesListDelegate
        spyList.dividerItemEnabled = true

        verify(spyList, times(1)).refreshListAdapter()
    }

    @Test
    fun `test list with item divider inactive`() {
        val list = AndesList(context)
        val spyList = Mockito.spy<AndesList>(list)
        val item = AndesListViewItemSimple(context, "test")

        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return item
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }
        spyList.delegate = andesListDelegate
        spyList.dividerItemEnabled = false

        verify(spyList, times(1)).refreshListAdapter()
    }

    @Test
    fun `AndesList type checkbox`() {
        // GIVEN
        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no-op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemCheckBox(activity, "test")
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }

        val andesList = AndesList(activity, AndesListViewItemSize.SMALL, AndesListType.CHECK_BOX)
        andesList.delegate = andesListDelegate

        // WHEN
        setContentToActivity(andesList)
        startActivitty()

        // THEN
        val firstItem = andesList.recyclerViewComponent.findViewHolderForAdapterPosition(0)?.itemView
        Assert.assertNotNull(firstItem)
        Assert.assertNotNull(firstItem?.findViewById(R.id.checkbox_item_selected))
        Assert.assertEquals(AndesThumbnailType.ICON, (firstItem?.findViewById(R.id.andes_list_item_asset) as AndesThumbnail).type)
        Assert.assertEquals(0, (firstItem.findViewById(R.id.custom_view_container) as FrameLayout).childCount)
    }

    @Test
    fun `AndesList type checkbox with avatarType ImageCircle`() {
        // GIVEN
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no-op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemCheckBox(activity, "test", avatar = drawable, avatarType = AndesThumbnailType.IMAGE_CIRCLE)
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }

        val andesList = AndesList(activity, AndesListViewItemSize.SMALL, AndesListType.CHECK_BOX)
        andesList.delegate = andesListDelegate

        // WHEN
        setContentToActivity(andesList)
        startActivitty()

        // THEN
        val firstItem = andesList.recyclerViewComponent.findViewHolderForAdapterPosition(0)?.itemView
        Assert.assertNotNull(firstItem)
        Assert.assertNotNull(firstItem?.findViewById(R.id.checkbox_item_selected))
        Assert.assertEquals(AndesThumbnailType.IMAGE_CIRCLE, (firstItem?.findViewById(R.id.andes_list_item_asset) as AndesThumbnail).type)
        Assert.assertEquals(0, (firstItem.findViewById(R.id.custom_view_container) as FrameLayout).childCount)
    }

    @Test
    fun `AndesList type checkbox with avatarType ImageCircle, content CustomView and no title`() {
        // GIVEN
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no-op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemCheckBox(
                    activity,
                    avatar = drawable,
                    avatarType = AndesThumbnailType.IMAGE_CIRCLE,
                    content = View(andesList.context)
                )
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }

        val andesList = AndesList(activity, AndesListViewItemSize.SMALL, AndesListType.CHECK_BOX)
        andesList.delegate = andesListDelegate

        // WHEN
        setContentToActivity(andesList)
        startActivitty()

        // THEN
        val emptyString = ""
        val firstItem = andesList.recyclerViewComponent.findViewHolderForAdapterPosition(0)?.itemView
        Assert.assertNotNull(firstItem)
        Assert.assertNotNull(firstItem?.findViewById(R.id.checkbox_item_selected))
        Assert.assertEquals(emptyString, (firstItem?.findViewById(R.id.text_view_item_title) as TextView).text.toString())
        Assert.assertEquals(AndesThumbnailType.IMAGE_CIRCLE, (firstItem.findViewById(R.id.andes_list_item_asset) as AndesThumbnail).type)
        Assert.assertEquals(1, (firstItem.findViewById(R.id.custom_view_container) as FrameLayout).childCount)
    }

    @Test
    fun `AndesList type radiobutton`() {
        // GIVEN
        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no-op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemRadioButton(activity, "test")
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }

        val andesList = AndesList(activity, AndesListViewItemSize.SMALL, AndesListType.RADIO_BUTTON)
        andesList.delegate = andesListDelegate

        // WHEN
        setContentToActivity(andesList)
        startActivitty()

        // THEN
        val firstItem = andesList.recyclerViewComponent.findViewHolderForAdapterPosition(0)?.itemView
        Assert.assertNotNull(firstItem)
        Assert.assertNotNull(firstItem?.findViewById(R.id.radio_button_item_selected))
        Assert.assertEquals(AndesThumbnailType.ICON, (firstItem?.findViewById(R.id.andes_list_item_asset) as AndesThumbnail).type)
    }

    @Test
    fun `AndesList type radiobutton with avatarType ImageCircle`() {
        // GIVEN
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no-op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemRadioButton(activity, "test", avatar = drawable, avatarType = AndesThumbnailType.IMAGE_CIRCLE)
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }

        val andesList = AndesList(activity, AndesListViewItemSize.SMALL, AndesListType.RADIO_BUTTON)
        andesList.delegate = andesListDelegate

        // WHEN
        setContentToActivity(andesList)
        startActivitty()

        // THEN
        val firstItem = andesList.recyclerViewComponent.findViewHolderForAdapterPosition(0)?.itemView
        Assert.assertNotNull(firstItem)
        Assert.assertNotNull(firstItem?.findViewById(R.id.radio_button_item_selected))
        Assert.assertEquals(AndesThumbnailType.IMAGE_CIRCLE, (firstItem?.findViewById(R.id.andes_list_item_asset) as AndesThumbnail).type)
        Assert.assertEquals(0, (firstItem.findViewById(R.id.custom_view_container) as FrameLayout).childCount)
    }

    @Test
    fun `AndesList type radiobutton with avatarType ImageCircle, content CustomView and no title`() {
        // GIVEN
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no-op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemRadioButton(
                    activity,
                    avatar = drawable,
                    avatarType = AndesThumbnailType.IMAGE_CIRCLE,
                    content = View(andesList.context)
                )
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }

        val andesList = AndesList(activity, AndesListViewItemSize.SMALL, AndesListType.RADIO_BUTTON)
        andesList.delegate = andesListDelegate

        // WHEN
        setContentToActivity(andesList)
        startActivitty()

        // THEN
        val emptyString = ""
        val firstItem = andesList.recyclerViewComponent.findViewHolderForAdapterPosition(0)?.itemView
        Assert.assertNotNull(firstItem)
        Assert.assertNotNull(firstItem?.findViewById(R.id.radio_button_item_selected))
        Assert.assertEquals(emptyString, (firstItem?.findViewById(R.id.text_view_item_title) as TextView).text.toString())
        Assert.assertEquals(AndesThumbnailType.IMAGE_CIRCLE, (firstItem.findViewById(R.id.andes_list_item_asset) as AndesThumbnail).type)
        Assert.assertEquals(1, (firstItem.findViewById(R.id.custom_view_container) as FrameLayout).childCount)
    }

    @Test
    fun `AndesList hasStableIds is true`() {
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!

        val andesListDelegate = object : AndesListDelegate {
            override fun onItemClick(andesList: AndesList, position: Int) {
                // no-op
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemRadioButton(activity, "test", avatar = drawable, avatarType = AndesThumbnailType.IMAGE_CIRCLE)
            }

            override fun getDataSetSize(andesList: AndesList): Int = SIZE
        }
        val andesList = AndesList(activity, AndesListViewItemSize.SMALL, AndesListType.RADIO_BUTTON)
        andesList.delegate = andesListDelegate
        val adapter = andesList.recyclerViewComponent.adapter
        adapter?.hasStableIds() assertEquals true
    }

    private fun setContentToActivity(view: View) {
        activity.setContentView(view)
    }

    private fun startActivitty() {
        robolectricActivity.start().postCreate(null).resume().visible()
    }
}
