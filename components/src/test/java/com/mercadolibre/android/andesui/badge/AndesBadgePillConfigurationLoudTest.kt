package com.mercadolibre.android.andesui.badge

import android.os.Build
import android.view.ViewGroup
import android.widget.TextView
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.border.AndesBadgePillBorder
import com.mercadolibre.android.andesui.badge.factory.AndesBadgePillAttrs
import com.mercadolibre.android.andesui.badge.factory.AndesBadgePillConfigurationFactory
import com.mercadolibre.android.andesui.badge.hierarchy.AndesBadgePillHierarchy
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType
import com.mercadolibre.android.andesui.color.toAndesColor
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesBadgePillConfigurationLoudTest {

    private var context = RuntimeEnvironment.application

    private val configFactory = spy(AndesBadgePillConfigurationFactory)
    private lateinit var attrs: AndesBadgePillAttrs

    @Test
    fun `Pill, Loud, Neutral, Standard, Small background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.LOUD, AndesBadgeType.NEUTRAL,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.SMALL, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_gray_550_solid.toAndesColor(), config.backgroundColor)
        assertEquals(SMALL_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Loud, Highlight, Standard, Small background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.LOUD, AndesBadgeType.HIGHLIGHT,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.SMALL, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_accent_color_500.toAndesColor(), config.backgroundColor)
        assertEquals(SMALL_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Loud, Success, Standard, Small background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.LOUD, AndesBadgeType.SUCCESS,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.SMALL, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_green_500.toAndesColor(), config.backgroundColor)
        assertEquals(SMALL_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Loud, Error, Standard, Small background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.LOUD, AndesBadgeType.ERROR,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.SMALL, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_red_500.toAndesColor(), config.backgroundColor)
        assertEquals(SMALL_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Loud, Warning, Standard, Small background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.LOUD, AndesBadgeType.WARNING,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.SMALL, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_orange_500.toAndesColor(), config.backgroundColor)
        assertEquals(SMALL_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Loud, Neutral, Standard, Large background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.LOUD, AndesBadgeType.NEUTRAL,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.LARGE, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_gray_550_solid.toAndesColor(), config.backgroundColor)
        assertEquals(LARGE_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Loud, Highlight, Standard, Large background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.LOUD, AndesBadgeType.HIGHLIGHT,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.LARGE, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_accent_color_500.toAndesColor(), config.backgroundColor)
        assertEquals(LARGE_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Loud, Success, Standard, Large background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.LOUD, AndesBadgeType.SUCCESS,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.LARGE, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_green_500.toAndesColor(), config.backgroundColor)
        assertEquals(LARGE_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Loud, Error, Standard, Large background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.LOUD, AndesBadgeType.ERROR,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.LARGE, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_red_500.toAndesColor(), config.backgroundColor)
        assertEquals(LARGE_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Loud, Warning, Standard, Large background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.LOUD, AndesBadgeType.WARNING,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.LARGE, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_orange_500.toAndesColor(), config.backgroundColor)
        assertEquals(LARGE_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Loud, Neutral, Standard, Large background color without default style`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.LOUD, AndesBadgeType.NEUTRAL,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.LARGE, "Title", false)
        val andesBadgePill = AndesBadgePill(context)
        andesBadgePill.text = attrs.andesBadgeText
        andesBadgePill.textStyleDefault = attrs.andesBadgeTextStyleDefault
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_gray_550_solid.toAndesColor(), config.backgroundColor)
        assertEquals(LARGE_HEIGHT, config.height)
        val params = ReflectionHelpers.getField<TextView>(andesBadgePill, "badgeTitle")
        assertEquals("Title", params.text)
    }

    @Test
    fun `Pill, Loud, Neutral, Standard, Large background color without default style and all attributes`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.LOUD, AndesBadgeType.NEUTRAL,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.LARGE, "Title", false)
        val andesBadgePill = AndesBadgePill(context,
            AndesBadgePillHierarchy.LOUD,
            AndesBadgeType.NEUTRAL,
            AndesBadgePillBorder.ROUNDED,
            AndesBadgePillSize.SMALL,
            "Title",
            false
        )
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_gray_550_solid.toAndesColor(), config.backgroundColor)
        assertEquals(LARGE_HEIGHT, config.height)
        val params = ReflectionHelpers.getField<TextView>(andesBadgePill, "badgeTitle")
        assertEquals("Title", params.text)
    }

    @Test
    fun `Pill, Loud, Neutral, Standard, Large background color with default style`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.LOUD, AndesBadgeType.NEUTRAL,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.LARGE, "Title")
        val andesBadgePill = AndesBadgePill(context)
        andesBadgePill.text = attrs.andesBadgeText
        andesBadgePill.textStyleDefault = attrs.andesBadgeTextStyleDefault
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_gray_550_solid.toAndesColor(), config.backgroundColor)
        assertEquals(LARGE_HEIGHT, config.height)
        val params = ReflectionHelpers.getField<TextView>(andesBadgePill, "badgeTitle")
        assertEquals("TITLE", params.text)
    }

    @Test
    fun `Pill, Loud, Neutral, Corner, Small check centralized text`() {
        val andesBadgePill = AndesBadgePill(
            context,
            AndesBadgePillHierarchy.LOUD,
            AndesBadgeType.NEUTRAL,
            AndesBadgePillBorder.CORNER,
            AndesBadgePillSize.SMALL,
            "test"
        )
        val params = ReflectionHelpers.getField<TextView>(andesBadgePill, "badgeTitle")
        val layout = params.layoutParams as ViewGroup.MarginLayoutParams
        assertEquals(1, layout.topMargin)
    }

    @Test
    fun `Pill, Loud, Neutral, ROUNDED, Large check centralized text`() {
        val andesBadgePill = AndesBadgePill(
            context,
            AndesBadgePillHierarchy.LOUD,
            AndesBadgeType.NEUTRAL,
            AndesBadgePillBorder.ROUNDED,
            AndesBadgePillSize.LARGE,
            "test"
        )
        val params = ReflectionHelpers.getField<TextView>(andesBadgePill, "badgeTitle")
        val layout = params.layoutParams as ViewGroup.MarginLayoutParams
        assertEquals(2, layout.topMargin)
    }

    companion object {
        private const val SMALL_HEIGHT = 16F
        private const val LARGE_HEIGHT = 24F
    }
}
