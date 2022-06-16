package com.mercadolibre.android.andesui.thumbnail

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType
import com.mercadolibre.android.andesui.utils.buildAttributeSet
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponent
import com.mercadolibre.android.andesui.thumbnail.badge.factory.AndesThumbnailBadgeAttrsParser
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesThumbnailBadgeAttrsParserTest {

    private lateinit var context: Context
    private val attrsParser = AndesThumbnailBadgeAttrsParser

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Can not build ThumbnailBadge without drawable`() {
        // GIVEN
        val attrs = Robolectric.buildAttributeSet().build()

        // WHEN
        AndesThumbnailBadgeAttrsParser.parse(context, attrs)

        // THEN: expected IllegalArgumentException
    }

    @Test
    fun `ThumbnailBadge default attributes`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailBadgeImage, "@drawable/andes_envio_envio_24")
        }

        // WHEN
        val thumbnailBadgeAttrs = AndesThumbnailBadgeAttrsParser.parse(context, attrs)

        // THEN
        with(thumbnailBadgeAttrs) {
            image assertEquals R.drawable.andes_envio_envio_24
            thumbnailType assertEquals AndesThumbnailBadgeType.Icon
            badge.color assertEquals AndesBadgeType.HIGHLIGHT
            assertTrue(badge is AndesThumbnailBadgeComponent.IconPill)
            badge.badgeComponent.thumbnailSize assertEquals AndesThumbnailSize.SIZE_64
        }
    }

    @Test
    fun `ThumbnailBadge default DOT`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailBadgeImage, "@drawable/andes_envio_envio_24")
            addAttribute(R.attr.andesThumbnailBadgeComponent, "3002")
        }

        // WHEN
        val thumbnailBadgeAttrs = AndesThumbnailBadgeAttrsParser.parse(context, attrs)

        // THEN
        with(thumbnailBadgeAttrs) {
            image assertEquals R.drawable.andes_envio_envio_24
            thumbnailType assertEquals AndesThumbnailBadgeType.Icon
            badge.color assertEquals AndesBadgeType.HIGHLIGHT
            assertTrue(badge is AndesThumbnailBadgeComponent.Dot)
            badge.badgeComponent.thumbnailSize assertEquals AndesThumbnailSize.SIZE_24
        }
    }

    @Test
    fun `ThumbnailBadge default PILL`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailBadgeImage, "@drawable/andes_envio_envio_24")
            addAttribute(R.attr.andesThumbnailBadgeComponent, "3000")
        }

        // WHEN
        val thumbnailBadgeAttrs = AndesThumbnailBadgeAttrsParser.parse(context, attrs)

        // THEN
        with(thumbnailBadgeAttrs) {
            image assertEquals R.drawable.andes_envio_envio_24
            thumbnailType assertEquals AndesThumbnailBadgeType.Icon
            badge.color assertEquals AndesBadgeType.HIGHLIGHT
            assertTrue(badge is AndesThumbnailBadgeComponent.Pill)
            with(badge as AndesThumbnailBadgeComponent.Pill) {
                text assertEquals null
                textStyleDefault assertEquals true
            }
            badge.badgeComponent.thumbnailSize assertEquals AndesThumbnailSize.SIZE_64
        }
    }

    @Test
    fun `ThumbnailBadge default ICON PILL`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailBadgeImage, "@drawable/andes_envio_envio_24")
            addAttribute(R.attr.andesThumbnailBadgeComponent, "3001")
        }

        // WHEN
        val thumbnailBadgeAttrs = AndesThumbnailBadgeAttrsParser.parse(context, attrs)

        // THEN
        with(thumbnailBadgeAttrs) {
            image assertEquals R.drawable.andes_envio_envio_24
            thumbnailType assertEquals AndesThumbnailBadgeType.Icon
            badge.color assertEquals AndesBadgeType.HIGHLIGHT
            assertTrue(badge is AndesThumbnailBadgeComponent.IconPill)
            badge.badgeComponent.thumbnailSize assertEquals AndesThumbnailSize.SIZE_64
        }
    }

    @Test
    fun `ThumbnailBadge type SUCCESS, size 40, ICON PILL`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailBadgeImage, "@drawable/andes_envio_envio_24")
            addAttribute(R.attr.andesThumbnailBadgeComponentType, "5001")
            addAttribute(R.attr.andesThumbnailBadgeComponentPillSize, "6002")
        }

        // WHEN
        val thumbnailBadgeAttrs = AndesThumbnailBadgeAttrsParser.parse(context, attrs)

        // THEN
        with(thumbnailBadgeAttrs) {
            image assertEquals R.drawable.andes_envio_envio_24
            thumbnailType assertEquals AndesThumbnailBadgeType.Icon
            badge.color assertEquals AndesBadgeType.SUCCESS
            assertTrue(badge is AndesThumbnailBadgeComponent.IconPill)
            badge.badgeComponent.thumbnailSize assertEquals AndesThumbnailSize.SIZE_40
        }
    }

    @Test
    fun `ThumbnailBadge type WARNING, size 48, ICON PILL`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailBadgeImage, "@drawable/andes_envio_envio_24")
            addAttribute(R.attr.andesThumbnailBadgeComponentType, "5002")
            addAttribute(R.attr.andesThumbnailBadgeComponentPillSize, "6003")
        }

        // WHEN
        val thumbnailBadgeAttrs = AndesThumbnailBadgeAttrsParser.parse(context, attrs)

        // THEN
        with(thumbnailBadgeAttrs) {
            image assertEquals R.drawable.andes_envio_envio_24
            thumbnailType assertEquals AndesThumbnailBadgeType.Icon
            badge.color assertEquals AndesBadgeType.WARNING
            assertTrue(badge is AndesThumbnailBadgeComponent.IconPill)
            badge.badgeComponent.thumbnailSize assertEquals AndesThumbnailSize.SIZE_48
        }
    }

    @Test
    fun `ThumbnailBadge type ERROR, size 56, ICON PILL`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailBadgeImage, "@drawable/andes_envio_envio_24")
            addAttribute(R.attr.andesThumbnailBadgeComponentType, "5002")
            addAttribute(R.attr.andesThumbnailBadgeComponentPillSize, "6004")
        }

        // WHEN
        val thumbnailBadgeAttrs = AndesThumbnailBadgeAttrsParser.parse(context, attrs)

        // THEN
        with(thumbnailBadgeAttrs) {
            image assertEquals R.drawable.andes_envio_envio_24
            thumbnailType assertEquals AndesThumbnailBadgeType.Icon
            badge.color assertEquals AndesBadgeType.WARNING
            assertTrue(badge is AndesThumbnailBadgeComponent.IconPill)
            badge.badgeComponent.thumbnailSize assertEquals AndesThumbnailSize.SIZE_56
        }
    }

    @Test
    fun `ThumbnailBadge type SUCCESS, size 64, PILL`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailBadgeImage, "@drawable/andes_envio_envio_24")
            addAttribute(R.attr.andesThumbnailBadgeComponent, "3000")
            addAttribute(R.attr.andesThumbnailBadgeComponentType, "5001")
            addAttribute(R.attr.andesThumbnailBadgeComponentPillSize, "6005")
        }

        // WHEN
        val thumbnailBadgeAttrs = AndesThumbnailBadgeAttrsParser.parse(context, attrs)

        // THEN
        with(thumbnailBadgeAttrs) {
            image assertEquals R.drawable.andes_envio_envio_24
            thumbnailType assertEquals AndesThumbnailBadgeType.Icon
            badge.color assertEquals AndesBadgeType.SUCCESS
            assertTrue(badge is AndesThumbnailBadgeComponent.Pill)
            badge.badgeComponent.thumbnailSize assertEquals AndesThumbnailSize.SIZE_64
        }
    }

    @Test
    fun `ThumbnailBadge type WARNING, size 72, PILL`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailBadgeImage, "@drawable/andes_envio_envio_24")
            addAttribute(R.attr.andesThumbnailBadgeComponent, "3000")
            addAttribute(R.attr.andesThumbnailBadgeComponentType, "5002")
            addAttribute(R.attr.andesThumbnailBadgeComponentPillSize, "6006")
        }

        // WHEN
        val thumbnailBadgeAttrs = AndesThumbnailBadgeAttrsParser.parse(context, attrs)

        // THEN
        with(thumbnailBadgeAttrs) {
            image assertEquals R.drawable.andes_envio_envio_24
            thumbnailType assertEquals AndesThumbnailBadgeType.Icon
            badge.color assertEquals AndesBadgeType.WARNING
            assertTrue(badge is AndesThumbnailBadgeComponent.Pill)
            badge.badgeComponent.thumbnailSize assertEquals AndesThumbnailSize.SIZE_72
        }
    }

    @Test
    fun `ThumbnailBadge type ERROR, size 80, PILL`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailBadgeImage, "@drawable/andes_envio_envio_24")
            addAttribute(R.attr.andesThumbnailBadgeComponent, "3000")
            addAttribute(R.attr.andesThumbnailBadgeComponentType, "5003")
            addAttribute(R.attr.andesThumbnailBadgeComponentPillSize, "6007")
        }

        // WHEN
        val thumbnailBadgeAttrs = AndesThumbnailBadgeAttrsParser.parse(context, attrs)

        // THEN
        with(thumbnailBadgeAttrs) {
            image assertEquals R.drawable.andes_envio_envio_24
            thumbnailType assertEquals AndesThumbnailBadgeType.Icon
            badge.color assertEquals AndesBadgeType.ERROR
            assertTrue(badge is AndesThumbnailBadgeComponent.Pill)
            badge.badgeComponent.thumbnailSize assertEquals AndesThumbnailSize.SIZE_80
        }
    }

    @Test
    fun `ThumbnailBadge type SUCCESS, size 24, DOT`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailBadgeImage, "@drawable/andes_envio_envio_24")
            addAttribute(R.attr.andesThumbnailBadgeComponent, "3002")
            addAttribute(R.attr.andesThumbnailBadgeComponentType, "5001")
            addAttribute(R.attr.andesThumbnailBadgeComponentDotSize, "6000")
        }

        // WHEN
        val thumbnailBadgeAttrs = AndesThumbnailBadgeAttrsParser.parse(context, attrs)

        // THEN
        with(thumbnailBadgeAttrs) {
            image assertEquals R.drawable.andes_envio_envio_24
            thumbnailType assertEquals AndesThumbnailBadgeType.Icon
            badge.color assertEquals AndesBadgeType.SUCCESS
            assertTrue(badge is AndesThumbnailBadgeComponent.Dot)
            badge.badgeComponent.thumbnailSize assertEquals AndesThumbnailSize.SIZE_24
        }
    }

    @Test
    fun `ThumbnailBadge type WARNING, size 32, DOT`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailBadgeImage, "@drawable/andes_envio_envio_24")
            addAttribute(R.attr.andesThumbnailBadgeComponent, "3002")
            addAttribute(R.attr.andesThumbnailBadgeComponentType, "5002")
            addAttribute(R.attr.andesThumbnailBadgeComponentDotSize, "6001")
        }

        // WHEN
        val thumbnailBadgeAttrs = AndesThumbnailBadgeAttrsParser.parse(context, attrs)

        // THEN
        with(thumbnailBadgeAttrs) {
            image assertEquals R.drawable.andes_envio_envio_24
            thumbnailType assertEquals AndesThumbnailBadgeType.Icon
            badge.color assertEquals AndesBadgeType.WARNING
            assertTrue(badge is AndesThumbnailBadgeComponent.Dot)
            badge.badgeComponent.thumbnailSize assertEquals AndesThumbnailSize.SIZE_32
        }
    }

    @Test
    fun `ThumbnailBadge Image, DOT`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailBadgeImage, "@drawable/andes_envio_envio_24")
            addAttribute(R.attr.andesThumbnailBadgeComponent, "3002")
            addAttribute(R.attr.andesThumbnailBadgeType, "2001")
        }

        // WHEN
        val thumbnailBadgeAttrs = AndesThumbnailBadgeAttrsParser.parse(context, attrs)

        // THEN
        with(thumbnailBadgeAttrs) {
            image assertEquals R.drawable.andes_envio_envio_24
            thumbnailType assertEquals AndesThumbnailBadgeType.ImageCircle
            badge.color assertEquals AndesBadgeType.HIGHLIGHT
            assertTrue(badge is AndesThumbnailBadgeComponent.Dot)
        }
    }
}
