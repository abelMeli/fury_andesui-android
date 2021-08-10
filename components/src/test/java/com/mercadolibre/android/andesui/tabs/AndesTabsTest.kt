package com.mercadolibre.android.andesui.tabs

import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.mercadolibre.android.andesui.R
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesTabsTest {

    private lateinit var context: Context
    private lateinit var andesTabs: AndesTabs
    private lateinit var items: ArrayList<AndesTabItem>

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.setTheme(R.style.Theme_AppCompat)

        items = arrayListOf()
        items.add(AndesTabItem("Tab 1"))
        items.add(AndesTabItem("Tab 2"))
        items.add(AndesTabItem("Tab 3"))
        andesTabs = AndesTabs(context, items)
    }

    @Test
    fun `Tab, select position`() {
        val position = 1
        andesTabs.selectTab(position, true)
        Assert.assertEquals(position, andesTabs.selectedTabPosition)
    }

    @Test
    fun `Tab, select incorrect position`() {
        val position = 4
        andesTabs.selectTab(position, true)
        Assert.assertNotEquals(position, andesTabs.selectedTabPosition)
    }

    @Test
    fun `Tab, callbacks work correctly`() {
        val callback: AndesTabs.OnTabChangedListener = mock()
        andesTabs.setOnTabChangedListener(callback)
        andesTabs.selectTab(1)
        andesTabs.selectTab(1)

        verify(callback).onTabReselected(any(), eq(items))
        verify(callback).onTabSelected(eq(1), eq(items))
        verify(callback).onTabUnselected(eq(0), eq(items))
    }

    @Test
    fun `Tab & viewpager synchronized`() {
        val viewPager = ViewPager(context)
        viewPager.adapter = TestTabsPagerAdapter(
            listOf(
                TextView(context),
                TextView(context),
                TextView(context)
            )
        )

        andesTabs.setupWithViewPager(viewPager)
        andesTabs.selectTab(2)

        Assert.assertEquals(andesTabs.selectedTabPosition, 2)
        Assert.assertEquals(viewPager.currentItem, 2)
    }

    @Test
    fun `Tab, setItems work correctly`() {
        val newItems = listOf(
            AndesTabItem("First")
        )
        andesTabs.setItems(newItems)

        andesTabs.selectTab(1)
        Assert.assertEquals(0, andesTabs.selectedTabPosition)
    }
}

class TestTabsPagerAdapter(var views: List<View>) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): View {
        container.addView(views[position])
        return views[position]
    }

    override fun getCount(): Int = views.size

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View?)
    }

    override fun isViewFromObject(view: View, other: Any): Boolean {
        return view == other
    }
}
