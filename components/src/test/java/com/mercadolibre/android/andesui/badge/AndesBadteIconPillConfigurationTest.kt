package com.mercadolibre.android.andesui.badge

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.badge.factory.AndesBadgeIconPillAttrs
import com.mercadolibre.android.andesui.badge.factory.AndesBadgeIconPillConfigurationFactory
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesBadteIconPillConfigurationTest {

    private lateinit var context: Context
    private lateinit var attrs: AndesBadgeIconPillAttrs
    private val configFactory = AndesBadgeIconPillConfigurationFactory

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `SMALL, HIGHLIGHT`() {
        val type = AndesBadgeType.HIGHLIGHT
        val size = AndesBadgePillSize.SMALL
        attrs = AndesBadgeIconPillAttrs(type, size)
        val iconDrawable = AndesBadgeType.HIGHLIGHT
            .type
            .icon(context, size.size.height(context).toInt(), type.type.primaryColor().colorInt(context))

        val config = configFactory.create(context, attrs)
        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `SMALL, SUCCESS`() {
        val type = AndesBadgeType.SUCCESS
        val size = AndesBadgePillSize.SMALL
        attrs = AndesBadgeIconPillAttrs(type, size)
        val iconDrawable = AndesBadgeType.SUCCESS
            .type
            .icon(context, size.size.height(context).toInt(), type.type.primaryColor().colorInt(context))

        val config = configFactory.create(context, attrs)
        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `SMALL, WARNING`() {
        val type = AndesBadgeType.WARNING
        val size = AndesBadgePillSize.SMALL
        attrs = AndesBadgeIconPillAttrs(type, size)
        val iconDrawable = AndesBadgeType.WARNING
            .type
            .icon(context, size.size.height(context).toInt(), type.type.primaryColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `SMALL, ERROR`() {
        val type = AndesBadgeType.ERROR
        val size = AndesBadgePillSize.SMALL
        attrs = AndesBadgeIconPillAttrs(type, size)
        val iconDrawable = AndesBadgeType.ERROR
            .type
            .icon(context, size.size.height(context).toInt(), type.type.primaryColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `LARGE, HIGHLIGHT`() {
        val type = AndesBadgeType.HIGHLIGHT
        val size = AndesBadgePillSize.LARGE
        attrs = AndesBadgeIconPillAttrs(type, size)
        val iconDrawable = AndesBadgeType.HIGHLIGHT
            .type
            .icon(context, size.size.height(context).toInt(), type.type.primaryColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `LARGE, SUCCESS`() {
        val type = AndesBadgeType.SUCCESS
        val size = AndesBadgePillSize.LARGE
        attrs = AndesBadgeIconPillAttrs(type, size)
        val iconDrawable = AndesBadgeType.SUCCESS
            .type
            .icon(context, size.size.height(context).toInt(), type.type.primaryColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `LARGE, WARNING`() {
        val type = AndesBadgeType.WARNING
        val size = AndesBadgePillSize.LARGE
        attrs = AndesBadgeIconPillAttrs(type, size)
        val iconDrawable = AndesBadgeType.WARNING
            .type
            .icon(context, size.size.height(context).toInt(), type.type.primaryColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `LARGE, ERROR`() {
        val type = AndesBadgeType.ERROR
        val size = AndesBadgePillSize.LARGE
        attrs = AndesBadgeIconPillAttrs(type, size)
        val iconDrawable = AndesBadgeType.ERROR
            .type
            .icon(context, size.size.height(context).toInt(), type.type.primaryColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }
}
