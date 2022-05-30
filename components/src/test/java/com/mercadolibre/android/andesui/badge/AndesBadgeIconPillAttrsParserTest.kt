package com.mercadolibre.android.andesui.badge

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.factory.AndesBadgeIconPillAttrsParser
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType
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
class AndesBadgeIconPillAttrsParserTest {

    private lateinit var context: Context
    private val attrsParser = AndesBadgeIconPillAttrsParser

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `SMALL, ERROR`() {
        val typeError = AndesBadgeType.ERROR
        val sizeSmall = AndesBadgePillSize.SMALL

        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesBadgeIconPillType, "2004")
            .addAttribute(R.attr.andesBadgeIconPillSize, "3001")
            .build()

        val andesBadgeIconPillAttrs = attrsParser.parse(context, attrs)

        Assert.assertEquals(typeError, andesBadgeIconPillAttrs.andesBadgeType)
        Assert.assertEquals(sizeSmall, andesBadgeIconPillAttrs.andesBadgeSize)
    }

    @Test
    fun `SMALL, SUCCESS`() {
        val typeSuccess = AndesBadgeType.SUCCESS
        val sizeSmall = AndesBadgePillSize.SMALL

        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesBadgeIconPillType, "2002")
            .addAttribute(R.attr.andesBadgeIconPillSize, "3001")
            .build()

        val andesBadgeIconPillAttrs = attrsParser.parse(context, attrs)

        Assert.assertEquals(typeSuccess, andesBadgeIconPillAttrs.andesBadgeType)
        Assert.assertEquals(sizeSmall, andesBadgeIconPillAttrs.andesBadgeSize)
    }

    @Test
    fun `SMALL, WARNING`() {
        val typeWarning = AndesBadgeType.WARNING
        val sizeSmall = AndesBadgePillSize.SMALL

        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesBadgeIconPillType, "2003")
            .addAttribute(R.attr.andesBadgeIconPillSize, "3001")
            .build()

        val andesBadgeIconPillAttrs = attrsParser.parse(context, attrs)

        Assert.assertEquals(typeWarning, andesBadgeIconPillAttrs.andesBadgeType)
        Assert.assertEquals(sizeSmall, andesBadgeIconPillAttrs.andesBadgeSize)
    }

    @Test
    fun `SMALL, HIGHLIGHT`() {
        val typeHighlight = AndesBadgeType.HIGHLIGHT
        val sizeSmall = AndesBadgePillSize.SMALL

        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesBadgeIconPillType, "2001")
            .addAttribute(R.attr.andesBadgeIconPillSize, "3001")
            .build()

        val andesBadgeIconPillAttrs = attrsParser.parse(context, attrs)

        Assert.assertEquals(typeHighlight, andesBadgeIconPillAttrs.andesBadgeType)
        Assert.assertEquals(sizeSmall, andesBadgeIconPillAttrs.andesBadgeSize)
    }

    @Test
    fun `LARGE, ERROR`() {
        val typeError = AndesBadgeType.ERROR
        val sizeLarge = AndesBadgePillSize.LARGE

        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesBadgeIconPillType, "2004")
            .addAttribute(R.attr.andesBadgeIconPillSize, "3000")
            .build()

        val andesBadgeIconPillAttrs = attrsParser.parse(context, attrs)

        Assert.assertEquals(typeError, andesBadgeIconPillAttrs.andesBadgeType)
        Assert.assertEquals(sizeLarge, andesBadgeIconPillAttrs.andesBadgeSize)
    }

    @Test
    fun `LARGE, SUCCESS`() {
        val typeSuccess = AndesBadgeType.SUCCESS
        val sizeLarge = AndesBadgePillSize.LARGE

        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesBadgeIconPillType, "2002")
            .addAttribute(R.attr.andesBadgeIconPillSize, "3000")
            .build()

        val andesBadgeIconPillAttrs = attrsParser.parse(context, attrs)

        Assert.assertEquals(typeSuccess, andesBadgeIconPillAttrs.andesBadgeType)
        Assert.assertEquals(sizeLarge, andesBadgeIconPillAttrs.andesBadgeSize)
    }

    @Test
    fun `LARGE, WARNING`() {
        val typeWarning = AndesBadgeType.WARNING
        val sizeLarge = AndesBadgePillSize.LARGE

        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesBadgeIconPillType, "2003")
            .addAttribute(R.attr.andesBadgeIconPillSize, "3000")
            .build()

        val andesBadgeIconPillAttrs = attrsParser.parse(context, attrs)

        Assert.assertEquals(typeWarning, andesBadgeIconPillAttrs.andesBadgeType)
        Assert.assertEquals(sizeLarge, andesBadgeIconPillAttrs.andesBadgeSize)
    }

    @Test
    fun `LARGE, HIGHLIGHT`() {
        val typeHighlight = AndesBadgeType.HIGHLIGHT
        val sizeLarge = AndesBadgePillSize.LARGE

        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesBadgeIconPillType, "2001")
            .addAttribute(R.attr.andesBadgeIconPillSize, "3000")
            .build()

        val andesBadgeIconPillAttrs = attrsParser.parse(context, attrs)

        Assert.assertEquals(typeHighlight, andesBadgeIconPillAttrs.andesBadgeType)
        Assert.assertEquals(sizeLarge, andesBadgeIconPillAttrs.andesBadgeSize)
    }
}
