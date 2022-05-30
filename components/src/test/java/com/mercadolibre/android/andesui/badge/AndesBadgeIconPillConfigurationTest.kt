package com.mercadolibre.android.andesui.badge

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.badge.factory.AndesBadgeIconPillAttrs
import com.mercadolibre.android.andesui.badge.factory.AndesBadgeIconPillConfigurationFactory
import com.mercadolibre.android.andesui.badge.hierarchy.AndesBadgeIconHierarchy
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesBadgeIconPillConfigurationTest {

    private lateinit var context: Context
    private lateinit var attrs: AndesBadgeIconPillAttrs
    private val configFactory = AndesBadgeIconPillConfigurationFactory

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `LOUD, SMALL, HIGHLIGHT`() {
        val type = AndesBadgeType.HIGHLIGHT
        val size = AndesBadgePillSize.SMALL
        val hierarchy = AndesBadgeIconHierarchy.LOUD
        attrs = AndesBadgeIconPillAttrs(type, size, hierarchy)
        val iconDrawable = AndesBadgeIconType.HIGHLIGHT
            .iconType.type
            .icon(context, size, type.type.primaryColor().colorInt(context))

        val config = configFactory.create(context, attrs)
        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `LOUD, SMALL, SUCCESS`() {
        val type = AndesBadgeType.SUCCESS
        val size = AndesBadgePillSize.SMALL
        val hierarchy = AndesBadgeIconHierarchy.LOUD
        attrs = AndesBadgeIconPillAttrs(type, size, hierarchy)
        val iconDrawable = AndesBadgeIconType.SUCCESS
            .iconType.type
            .icon(context, size, type.type.primaryColor().colorInt(context))

        val config = configFactory.create(context, attrs)
        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `LOUD, SMALL, WARNING`() {
        val type = AndesBadgeType.WARNING
        val size = AndesBadgePillSize.SMALL
        val hierarchy = AndesBadgeIconHierarchy.LOUD
        attrs = AndesBadgeIconPillAttrs(type, size, hierarchy)
        val iconDrawable = AndesBadgeIconType.WARNING
            .iconType.type
            .icon(context, size, type.type.primaryColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `LOUD, SMALL, ERROR`() {
        val type = AndesBadgeType.ERROR
        val size = AndesBadgePillSize.SMALL
        val hierarchy = AndesBadgeIconHierarchy.LOUD
        attrs = AndesBadgeIconPillAttrs(type, size, hierarchy)
        val iconDrawable = AndesBadgeIconType.ERROR
            .iconType.type
            .icon(context, size, type.type.primaryColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `LOUD, LARGE, HIGHLIGHT`() {
        val type = AndesBadgeType.HIGHLIGHT
        val size = AndesBadgePillSize.LARGE
        val hierarchy = AndesBadgeIconHierarchy.LOUD
        attrs = AndesBadgeIconPillAttrs(type, size, hierarchy)
        val iconDrawable = AndesBadgeIconType.HIGHLIGHT
            .iconType.type
            .icon(context, size, type.type.primaryColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `LOUD, LARGE, SUCCESS`() {
        val type = AndesBadgeType.SUCCESS
        val size = AndesBadgePillSize.LARGE
        val hierarchy = AndesBadgeIconHierarchy.LOUD
        attrs = AndesBadgeIconPillAttrs(type, size, hierarchy)
        val iconDrawable = AndesBadgeIconType.SUCCESS
            .iconType.type
            .icon(context, size, type.type.primaryColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `LOUD, LARGE, WARNING`() {
        val type = AndesBadgeType.WARNING
        val size = AndesBadgePillSize.LARGE
        val hierarchy = AndesBadgeIconHierarchy.LOUD
        attrs = AndesBadgeIconPillAttrs(type, size, hierarchy)
        val iconDrawable = AndesBadgeIconType.WARNING
            .iconType.type
            .icon(context, size, type.type.primaryColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `LOUD, LARGE, ERROR`() {
        val type = AndesBadgeType.ERROR
        val size = AndesBadgePillSize.LARGE
        val hierarchy = AndesBadgeIconHierarchy.LOUD
        attrs = AndesBadgeIconPillAttrs(type, size, hierarchy)
        val iconDrawable = AndesBadgeIconType.ERROR
            .iconType.type
            .icon(context, size, type.type.primaryColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    //

    @Test
    fun `SECONDARY, SMALL, HIGHLIGHT`() {
        val type = AndesBadgeType.HIGHLIGHT
        val size = AndesBadgePillSize.SMALL
        val hierarchy = AndesBadgeIconHierarchy.SECONDARY
        attrs = AndesBadgeIconPillAttrs(type, size, hierarchy)
        val iconDrawable = AndesBadgeIconType.HIGHLIGHT
            .iconType.type
            .icon(context, size, type.type.primaryVariantColor().colorInt(context))

        val config = configFactory.create(context, attrs)
        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `SECONDARY, SMALL, SUCCESS`() {
        val type = AndesBadgeType.SUCCESS
        val size = AndesBadgePillSize.SMALL
        val hierarchy = AndesBadgeIconHierarchy.SECONDARY
        attrs = AndesBadgeIconPillAttrs(type, size, hierarchy)
        val iconDrawable = AndesBadgeIconType.SUCCESS
            .iconType.type
            .icon(context, size, type.type.primaryVariantColor().colorInt(context))

        val config = configFactory.create(context, attrs)
        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `SECONDARY, SMALL, WARNING`() {
        val type = AndesBadgeType.WARNING
        val size = AndesBadgePillSize.SMALL
        val hierarchy = AndesBadgeIconHierarchy.SECONDARY
        attrs = AndesBadgeIconPillAttrs(type, size, hierarchy)
        val iconDrawable = AndesBadgeIconType.WARNING
            .iconType.type
            .icon(context, size, type.type.primaryVariantColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `SECONDARY, SMALL, ERROR`() {
        val type = AndesBadgeType.ERROR
        val size = AndesBadgePillSize.SMALL
        val hierarchy = AndesBadgeIconHierarchy.SECONDARY
        attrs = AndesBadgeIconPillAttrs(type, size, hierarchy)
        val iconDrawable = AndesBadgeIconType.ERROR
            .iconType.type
            .icon(context, size, type.type.primaryVariantColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `SECONDARY, LARGE, HIGHLIGHT`() {
        val type = AndesBadgeType.HIGHLIGHT
        val size = AndesBadgePillSize.LARGE
        val hierarchy = AndesBadgeIconHierarchy.SECONDARY
        attrs = AndesBadgeIconPillAttrs(type, size, hierarchy)
        val iconDrawable = AndesBadgeIconType.HIGHLIGHT
            .iconType.type
            .icon(context, size, type.type.primaryVariantColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `SECONDARY, LARGE, SUCCESS`() {
        val type = AndesBadgeType.SUCCESS
        val size = AndesBadgePillSize.LARGE
        val hierarchy = AndesBadgeIconHierarchy.SECONDARY
        attrs = AndesBadgeIconPillAttrs(type, size, hierarchy)
        val iconDrawable = AndesBadgeIconType.SUCCESS
            .iconType.type
            .icon(context, size, type.type.primaryVariantColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `SECONDARY, LARGE, WARNING`() {
        val type = AndesBadgeType.WARNING
        val size = AndesBadgePillSize.LARGE
        val hierarchy = AndesBadgeIconHierarchy.SECONDARY
        attrs = AndesBadgeIconPillAttrs(type, size, hierarchy)
        val iconDrawable = AndesBadgeIconType.WARNING
            .iconType.type
            .icon(context, size, type.type.primaryVariantColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }

    @Test
    fun `SECONDARY, LARGE, ERROR`() {
        val type = AndesBadgeType.ERROR
        val size = AndesBadgePillSize.LARGE
        val hierarchy = AndesBadgeIconHierarchy.SECONDARY
        attrs = AndesBadgeIconPillAttrs(type, size, hierarchy)
        val iconDrawable = AndesBadgeIconType.ERROR
            .iconType.type
            .icon(context, size, type.type.primaryVariantColor().colorInt(context))

        val config = configFactory.create(context, attrs)

        assertThat(iconDrawable).usingRecursiveComparison().isEqualTo(config.icon)
    }
}
