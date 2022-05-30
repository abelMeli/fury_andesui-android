package com.mercadolibre.android.andesui.badge

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.badge.hierarchy.AndesBadgeIconHierarchy
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesBadgeIconPillTest {

    private lateinit var context: Context
    private lateinit var andesBadgeIconPill: AndesBadgeIconPill

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `creating new badge with all parameters set by constructor`() {
        val hierarchy = AndesBadgeIconHierarchy.LOUD
        val type = AndesBadgeIconType.SUCCESS
        val size = AndesBadgePillSize.LARGE

        andesBadgeIconPill = AndesBadgeIconPill(
            context,
            type,
            size,
            hierarchy
        )

        Assert.assertEquals(type, andesBadgeIconPill.type)
        Assert.assertEquals(size, andesBadgeIconPill.size)
    }

    @Test
    fun `creating new badge with parameters by default`() {
        val defaultHierarchy = AndesBadgeIconHierarchy.LOUD
        val defaultType = AndesBadgeIconType.HIGHLIGHT
        val defaultSize = AndesBadgePillSize.SMALL

        andesBadgeIconPill = AndesBadgeIconPill(context)

        Assert.assertEquals(defaultHierarchy, andesBadgeIconPill.hierarchy)
        Assert.assertEquals(defaultType, andesBadgeIconPill.type)
        Assert.assertEquals(defaultSize, andesBadgeIconPill.size)
    }

    @Test
    fun `creating new badge with parameters set by setter`() {
        val hierarchy = AndesBadgeIconHierarchy.LOUD
        val type = AndesBadgeIconType.ERROR
        val size = AndesBadgePillSize.LARGE

        andesBadgeIconPill = AndesBadgeIconPill(context)

        andesBadgeIconPill.hierarchy = hierarchy
        andesBadgeIconPill.type = type
        andesBadgeIconPill.size = size

        Assert.assertEquals(hierarchy, andesBadgeIconPill.hierarchy)
        Assert.assertEquals(type, andesBadgeIconPill.type)
        Assert.assertEquals(size, andesBadgeIconPill.size)
    }

    @Test
    fun `creating new badge with default parameters from xml constructor`() {
        val defaultHierarchy = AndesBadgeIconHierarchy.LOUD
        val defaultType = AndesBadgeIconType.HIGHLIGHT
        val defaultSize = AndesBadgePillSize.SMALL
        val attrs = Robolectric.buildAttributeSet().build()

        andesBadgeIconPill = AndesBadgeIconPill(context, attrs)

        Assert.assertEquals(defaultHierarchy, andesBadgeIconPill.hierarchy)
        Assert.assertEquals(defaultType, andesBadgeIconPill.type)
        Assert.assertEquals(defaultSize, andesBadgeIconPill.size)
    }
}
