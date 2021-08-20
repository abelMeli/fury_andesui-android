package com.mercadolibre.android.andesui.badge

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesBadgeIconPillTest {

    private lateinit var context: Context
    private lateinit var andesBadgeIconPill: AndesBadgeIconPill

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `creating new badge with all parameters set by constructor`() {
        val type = AndesBadgeIconType.SUCCESS
        val size = AndesBadgePillSize.LARGE

        andesBadgeIconPill = AndesBadgeIconPill(
            context,
            type,
            size
        )

        Assert.assertEquals(type, andesBadgeIconPill.type)
        Assert.assertEquals(size, andesBadgeIconPill.size)
    }

    @Test
    fun `creating new badge with parameters by default`() {
        val defaultType = AndesBadgeIconType.HIGHLIGHT
        val defaultSize = AndesBadgePillSize.SMALL

        andesBadgeIconPill = AndesBadgeIconPill(context)

        Assert.assertEquals(defaultType, andesBadgeIconPill.type)
        Assert.assertEquals(defaultSize, andesBadgeIconPill.size)
    }

    @Test
    fun `creating new badge with parameters set by setter`() {
        val type = AndesBadgeIconType.ERROR
        val size = AndesBadgePillSize.LARGE

        andesBadgeIconPill = AndesBadgeIconPill(context)

        andesBadgeIconPill.type = type
        andesBadgeIconPill.size = size

        Assert.assertEquals(type, andesBadgeIconPill.type)
        Assert.assertEquals(size, andesBadgeIconPill.size)
    }

    @Test
    fun `creating new badge with default parameters from xml constructor`() {
        val defaultType = AndesBadgeIconType.HIGHLIGHT
        val defaultSize = AndesBadgePillSize.SMALL
        val attrs = Robolectric.buildAttributeSet().build()

        andesBadgeIconPill = AndesBadgeIconPill(context, attrs)

        Assert.assertEquals(defaultType, andesBadgeIconPill.type)
        Assert.assertEquals(defaultSize, andesBadgeIconPill.size)
    }
}
