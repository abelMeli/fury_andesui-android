package com.mercadolibre.android.andesui.tabs

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.android.material.tabs.TabLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.tabs.type.AndesTabsType
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesTabsTypeInterfaceTest {

    private lateinit var context: Context
    private lateinit var andesTabs: AndesTabs
    private val tabItems: List<AndesTabItem> = listOf(
        AndesTabItem("1"),
        AndesTabItem("2"),
        AndesTabItem("3")
    )

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        context.setTheme(R.style.Theme_AppCompat)
    }

    @Test
    fun `testing type full_width`() {
        andesTabs = AndesTabs(context, emptyList(), AndesTabsType.FullWidth)
        val layout = andesTabs.findViewById<TabLayout>(R.id.andes_tabs_tab_layout)
        Assert.assertEquals(layout.tabMode, TabLayout.MODE_FIXED)
    }

    @Test
    fun `testing type left_align`() {
        andesTabs = AndesTabs(context, emptyList(), AndesTabsType.LeftAlign())
        val layout = andesTabs.findViewById<TabLayout>(R.id.andes_tabs_tab_layout)
        Assert.assertEquals(layout.tabMode, TabLayout.MODE_SCROLLABLE)
    }

    @Test
    fun `testing type width left_align`() {
        andesTabs = AndesTabs(context, emptyList(), AndesTabsType.LeftAlign())
        val layout = andesTabs.findViewById<TabLayout>(R.id.andes_tabs_tab_layout)
        Assert.assertFalse(layout.isTabIndicatorFullWidth)
    }

    @Test
    fun `testing type width full_width`() {
        andesTabs = AndesTabs(context, emptyList(), AndesTabsType.FullWidth)
        val layout = andesTabs.findViewById<TabLayout>(R.id.andes_tabs_tab_layout)
        Assert.assertTrue(layout.isTabIndicatorFullWidth)
    }

    @Test
    fun `testing type round size full_width middle position`() {
        andesTabs = AndesTabs(context, tabItems, AndesTabsType.FullWidth)
        Assert.assertEquals(
            context.resources.getDimensionPixelSize(R.dimen.andes_tab_indicator_corner).toFloat(),
            andesTabs.type.type.getLeftRoundCornerSize(1, context.resources)
        )
        Assert.assertEquals(
            context.resources.getDimensionPixelSize(R.dimen.andes_tab_indicator_corner).toFloat(),
            andesTabs.type.type.getRightRoundCornerSize(1, tabItems.lastIndex, context.resources)
        )
    }

    @Test
    fun `testing type round size full_width first position`() {
        andesTabs = AndesTabs(context, tabItems, AndesTabsType.FullWidth)
        Assert.assertEquals(0F, andesTabs.type.type.getLeftRoundCornerSize(0, context.resources))
        Assert.assertEquals(
            context.resources.getDimensionPixelSize(R.dimen.andes_tab_indicator_corner).toFloat(),
            andesTabs.type.type.getRightRoundCornerSize(0, tabItems.lastIndex, context.resources)
        )
    }

    @Test
    fun `testing type round size full_width last position`() {
        andesTabs = AndesTabs(context, emptyList(), AndesTabsType.FullWidth)
        Assert.assertEquals(
            context.resources.getDimensionPixelSize(R.dimen.andes_tab_indicator_corner).toFloat(),
            andesTabs.type.type.getLeftRoundCornerSize(tabItems.lastIndex, context.resources)
        )
        Assert.assertEquals(
            0F,
            andesTabs.type.type.getRightRoundCornerSize(
                tabItems.lastIndex,
                tabItems.lastIndex,
                context.resources
            )
        )
    }

    @Test
    fun `testing type round size left_align first position`() {
        andesTabs = AndesTabs(context, emptyList(), AndesTabsType.LeftAlign())
        Assert.assertEquals(
            context.resources.getDimensionPixelSize(R.dimen.andes_tab_indicator_corner).toFloat(),
            andesTabs.type.type.getLeftRoundCornerSize(0, context.resources)
        )
        Assert.assertEquals(
            context.resources.getDimensionPixelSize(R.dimen.andes_tab_indicator_corner).toFloat(),
            andesTabs.type.type.getRightRoundCornerSize(0, tabItems.lastIndex, context.resources)
        )
    }

    @Test
    fun `testing type round size left_align middle position`() {
        andesTabs = AndesTabs(context, emptyList(), AndesTabsType.LeftAlign())
        Assert.assertEquals(
            context.resources.getDimensionPixelSize(R.dimen.andes_tab_indicator_corner).toFloat(),
            andesTabs.type.type.getLeftRoundCornerSize(1, context.resources)
        )
        Assert.assertEquals(
            context.resources.getDimensionPixelSize(R.dimen.andes_tab_indicator_corner).toFloat(),
            andesTabs.type.type.getRightRoundCornerSize(1, tabItems.lastIndex, context.resources)
        )
    }

    @Test
    fun `testing type round size left_align last position`() {
        andesTabs = AndesTabs(context, emptyList(), AndesTabsType.LeftAlign())
        Assert.assertEquals(
            context.resources.getDimensionPixelSize(R.dimen.andes_tab_indicator_corner).toFloat(),
            andesTabs.type.type.getLeftRoundCornerSize(tabItems.lastIndex, context.resources)
        )
        Assert.assertEquals(
            context.resources.getDimensionPixelSize(R.dimen.andes_tab_indicator_corner).toFloat(),
            andesTabs.type.type.getRightRoundCornerSize(
                tabItems.lastIndex,
                tabItems.lastIndex,
                context.resources
            )
        )
    }
}
