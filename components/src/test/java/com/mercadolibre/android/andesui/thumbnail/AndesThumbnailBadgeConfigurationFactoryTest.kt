package com.mercadolibre.android.andesui.thumbnail

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.badge.AndesBadgeDot
import com.mercadolibre.android.andesui.badge.AndesBadgeIconPill
import com.mercadolibre.android.andesui.badge.AndesBadgePill
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesDotThumbnailBadgeComponent
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesIconPillThumbnailBadgeComponent
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesPillThumbnailBadgeComponent
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponent
import com.mercadolibre.android.andesui.thumbnail.badge.factory.AndesThumbnailBadgeAttrs
import com.mercadolibre.android.andesui.thumbnail.badge.factory.AndesThumbnailBadgeConfigurationFactory
import com.mercadolibre.android.andesui.thumbnail.badge.size.AndesThumbnailBadgeDotSize
import com.mercadolibre.android.andesui.thumbnail.badge.size.AndesThumbnailBadgePillSize
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailType
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesThumbnailBadgeConfigurationFactoryTest {

    private lateinit var context: Context
    private lateinit var attrs: AndesThumbnailBadgeAttrs

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `Config Pill, Icon, SIZE_64, SUCCESS`() {
        // GIVEN
        val expectedBadgeColor = AndesBadgeIconType.SUCCESS
        val expectedImage = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val expectedType = AndesThumbnailBadgeType.Icon
        val expectedPillText = "Test"
        attrs = AndesThumbnailBadgeAttrs(
            image = expectedImage,
            badge = AndesThumbnailBadgeComponent.Pill(
                expectedBadgeColor,
                expectedPillText,
                false,
                AndesThumbnailBadgePillSize.SIZE_64
            ),
            thumbnailType = expectedType
        )

        // WHEN
        val config = AndesThumbnailBadgeConfigurationFactory.create(context, attrs)

        // THEN
        with(config) {
            val expectedColor = expectedBadgeColor.iconType.type.primaryColor().colorInt(context)
            badgeColor assertEquals expectedColor
            thumbnailSize assertEquals AndesThumbnailSize.SIZE_64
            thumbnailType assertEquals AndesThumbnailType.ICON
            thumbnailTintColor assertEquals ColorStateList.valueOf(expectedColor)
            image assertEquals expectedImage
            badgeOutline assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_thumbnail_badge_outline_3)
            assert(badgeComponent is AndesPillThumbnailBadgeComponent)
            with(badgeComponent.getView(context) as AndesBadgePill) {
                pillSize assertEquals AndesBadgePillSize.LARGE
                type assertEquals AndesBadgeType.SUCCESS
                text assertEquals expectedPillText
                textStyleDefault assertEquals false
            }
        }
    }

    @Test
    fun `Config Pill, ImageCircle, SIZE_56, WARNING`() {
        // GIVEN
        val expectedBadgeColor = AndesBadgeIconType.WARNING
        val expectedImage = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val expectedType = AndesThumbnailBadgeType.ImageCircle
        val expectedPillText = "Test"
        attrs = AndesThumbnailBadgeAttrs(
            image = expectedImage,
            badge = AndesThumbnailBadgeComponent.Pill(
                expectedBadgeColor,
                expectedPillText,
                false,
                AndesThumbnailBadgePillSize.SIZE_56
            ),
            thumbnailType = expectedType
        )

        // WHEN
        val config = AndesThumbnailBadgeConfigurationFactory.create(context, attrs)

        // THEN
        with(config) {
            val expectedColor = expectedBadgeColor.iconType.type.primaryColor().colorInt(context)
            badgeColor assertEquals expectedColor
            thumbnailSize assertEquals AndesThumbnailSize.SIZE_56
            thumbnailType assertEquals AndesThumbnailType.IMAGE_CIRCLE
            thumbnailTintColor assertEquals null
            image assertEquals expectedImage
            badgeOutline assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_thumbnail_badge_outline_2)
            assert(badgeComponent is AndesPillThumbnailBadgeComponent)
            with(badgeComponent.getView(context) as AndesBadgePill) {
                pillSize assertEquals AndesBadgePillSize.SMALL
                type assertEquals AndesBadgeType.WARNING
                text assertEquals expectedPillText
                textStyleDefault assertEquals false
            }
        }
    }

    @Test
    fun `Config Dot, Icon, SIZE_24, ERROR`() {
        // GIVEN
        val expectedBadgeColor = AndesBadgeIconType.ERROR
        val expectedImage = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val expectedType = AndesThumbnailBadgeType.Icon
        attrs = AndesThumbnailBadgeAttrs(
            image = expectedImage,
            badge = AndesThumbnailBadgeComponent.Dot(
                expectedBadgeColor,
                AndesThumbnailBadgeDotSize.SIZE_24
            ),
            thumbnailType = expectedType
        )

        // WHEN
        val config = AndesThumbnailBadgeConfigurationFactory.create(context, attrs)

        // THEN
        with(config) {
            val expectedColor = expectedBadgeColor.iconType.type.primaryColor().colorInt(context)
            badgeColor assertEquals expectedColor
            thumbnailSize assertEquals AndesThumbnailSize.SIZE_24
            thumbnailType assertEquals AndesThumbnailType.ICON
            thumbnailTintColor assertEquals ColorStateList.valueOf(expectedColor)
            image assertEquals expectedImage
            badgeOutline assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_thumbnail_badge_outline_2)
            assert(badgeComponent is AndesDotThumbnailBadgeComponent)
            with(badgeComponent.getView(context) as AndesBadgeDot) {
                type assertEquals AndesBadgeType.ERROR
            }
        }
    }

    @Test
    fun `Config Dot, ImageCircle, SIZE_32, HIGHLIGHT`() {
        // GIVEN
        val expectedBadgeColor = AndesBadgeIconType.HIGHLIGHT
        val expectedImage = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val expectedType = AndesThumbnailBadgeType.ImageCircle
        attrs = AndesThumbnailBadgeAttrs(
            image = expectedImage,
            badge = AndesThumbnailBadgeComponent.Dot(
                expectedBadgeColor,
                AndesThumbnailBadgeDotSize.SIZE_32
            ),
            thumbnailType = expectedType
        )

        // WHEN
        val config = AndesThumbnailBadgeConfigurationFactory.create(context, attrs)

        // THEN
        with(config) {
            val expectedColor = expectedBadgeColor.iconType.type.primaryColor().colorInt(context)
            badgeColor assertEquals expectedColor
            thumbnailSize assertEquals AndesThumbnailSize.SIZE_32
            thumbnailType assertEquals AndesThumbnailType.IMAGE_CIRCLE
            thumbnailTintColor assertEquals null
            image assertEquals expectedImage
            badgeOutline assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_thumbnail_badge_outline_2)
            assert(badgeComponent is AndesDotThumbnailBadgeComponent)
            with(badgeComponent.getView(context) as AndesBadgeDot) {
                type assertEquals AndesBadgeType.HIGHLIGHT
            }
        }
    }

    @Test
    fun `Config Icon Pill, ImageCircle, SIZE_72, SUCCESS`() {
        // GIVEN
        val expectedBadgeColor = AndesBadgeIconType.SUCCESS
        val expectedImage = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val expectedType = AndesThumbnailBadgeType.ImageCircle
        attrs = AndesThumbnailBadgeAttrs(
            image = expectedImage,
            badge = AndesThumbnailBadgeComponent.IconPill(
                expectedBadgeColor,
                AndesThumbnailBadgePillSize.SIZE_72
            ),
            thumbnailType = expectedType
        )

        // WHEN
        val config = AndesThumbnailBadgeConfigurationFactory.create(context, attrs)

        // THEN
        with(config) {
            val expectedColor = expectedBadgeColor.iconType.type.primaryColor().colorInt(context)
            badgeColor assertEquals expectedColor
            thumbnailSize assertEquals AndesThumbnailSize.SIZE_72
            thumbnailType assertEquals AndesThumbnailType.IMAGE_CIRCLE
            thumbnailTintColor assertEquals null
            image assertEquals expectedImage
            badgeOutline assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_thumbnail_badge_outline_3)
            assert(badgeComponent is AndesIconPillThumbnailBadgeComponent)
            with(badgeComponent.getView(context) as AndesBadgeIconPill) {
                type assertEquals AndesBadgeIconType.SUCCESS
                size assertEquals AndesBadgePillSize.LARGE
            }
        }
    }

    @Test
    fun `Config Icon Pill, Icon, SIZE_48, WARNING`() {
        // GIVEN
        val expectedBadgeColor = AndesBadgeIconType.WARNING
        val expectedImage = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val expectedType = AndesThumbnailBadgeType.Icon
        attrs = AndesThumbnailBadgeAttrs(
            image = expectedImage,
            badge = AndesThumbnailBadgeComponent.IconPill(
                expectedBadgeColor,
                AndesThumbnailBadgePillSize.SIZE_48
            ),
            thumbnailType = expectedType
        )

        // WHEN
        val config = AndesThumbnailBadgeConfigurationFactory.create(context, attrs)

        // THEN
        with(config) {
            val expectedColor = expectedBadgeColor.iconType.type.primaryColor().colorInt(context)
            badgeColor assertEquals expectedColor
            thumbnailSize assertEquals AndesThumbnailSize.SIZE_48
            thumbnailType assertEquals AndesThumbnailType.ICON
            thumbnailTintColor assertEquals ColorStateList.valueOf(expectedColor)
            image assertEquals expectedImage
            badgeOutline assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_thumbnail_badge_outline_2)
            assert(badgeComponent is AndesIconPillThumbnailBadgeComponent)
            with(badgeComponent.getView(context) as AndesBadgeIconPill) {
                type assertEquals AndesBadgeIconType.WARNING
                size assertEquals AndesBadgePillSize.SMALL
            }
        }
    }
}
