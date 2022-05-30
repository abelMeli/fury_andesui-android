package com.mercadolibre.android.andesui.thumbnail

import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.thumbnail.factory.AndesThumbnailAttrs
import com.mercadolibre.android.andesui.thumbnail.factory.AndesThumbnailConfigurationFactory
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import com.mercadolibre.android.andesui.thumbnail.state.AndesThumbnailState
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailType
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesThumbnailConfigurationTest {

    private var context = RuntimeEnvironment.application

    private val configFactory = spy(AndesThumbnailConfigurationFactory)
    private lateinit var attrs: AndesThumbnailAttrs

    // MARK - Enabled Tests

    @Test
    fun `Thumbnail, Loud, Neutral, Icon, Enabled, background color`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_800.toAndesColor(),
                AndesThumbnailHierarchy.LOUD,
                drawable!!,
                AndesThumbnailType.ICON,
                AndesThumbnailSize.SIZE_48,
                AndesThumbnailState.ENABLED)

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_orange_800.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.LOUD.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_white.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.ICON, AndesThumbnailSize.SIZE_48), config.iconSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.ICON), config.isImageType)
        assertEquals(true, config.hasTint)
    }

    @Test
    fun `Thumbnail, Quiet, Neutral, Icon, Enabled, background color`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        val expectedIconColor = R.color.andes_orange_500.toAndesColor()
        val expectedBackgrounColor = R.color.andes_orange_500.toAndesColor().apply {
            alpha = 0.1f
        }

        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_500.toAndesColor(),
                AndesThumbnailHierarchy.QUIET,
                drawable!!,
                AndesThumbnailType.ICON,
                AndesThumbnailSize.SIZE_48,
                AndesThumbnailState.ENABLED)

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(expectedBackgrounColor, config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.QUIET.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(expectedIconColor, config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.ICON, AndesThumbnailSize.SIZE_48), config.iconSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.ICON), config.isImageType)
        assertEquals(true, config.hasTint)
    }

    @Test
    fun `Thumbnail, Default, Neutral, Icon, Enabled, background color`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_800.toAndesColor(),
                AndesThumbnailHierarchy.DEFAULT,
                drawable!!,
                AndesThumbnailType.ICON,
                AndesThumbnailSize.SIZE_48,
                AndesThumbnailState.ENABLED)

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_white.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.DEFAULT.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_900_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.ICON, AndesThumbnailSize.SIZE_48), config.iconSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.ICON), config.isImageType)
        assertEquals(true, config.hasTint)
    }

    @Test
    fun `Thumbnail, Default, Neutral, Image Circle, Enabled, background color`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_800.toAndesColor(),
                AndesThumbnailHierarchy.DEFAULT,
                drawable!!,
                AndesThumbnailType.IMAGE_CIRCLE,
                AndesThumbnailSize.SIZE_48,
                AndesThumbnailState.ENABLED)

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_white.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.DEFAULT.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_900_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.IMAGE_CIRCLE, AndesThumbnailSize.SIZE_48), config.iconSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.IMAGE_CIRCLE), config.isImageType)
        assertEquals(false, config.hasTint)
    }

    @Test
    fun `Thumbnail, Loud, Neutral, Image Square, Enabled, background color`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_800.toAndesColor(),
                AndesThumbnailHierarchy.LOUD,
                drawable!!,
                AndesThumbnailType.IMAGE_SQUARE,
                AndesThumbnailSize.SIZE_48,
                AndesThumbnailState.ENABLED)

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_white.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.DEFAULT.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_900_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.IMAGE_SQUARE, AndesThumbnailSize.SIZE_48), config.iconSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_48.size.radiusSize(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.IMAGE_SQUARE), config.isImageType)
        assertEquals(false, config.hasTint)
    }

    // MARK - Disabled Tests

    @Test
    fun `Thumbnail, Loud, Neutral, Icon, Disabled, background color`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_800.toAndesColor(),
                AndesThumbnailHierarchy.LOUD,
                drawable!!,
                AndesThumbnailType.ICON,
                AndesThumbnailSize.SIZE_48,
                AndesThumbnailState.DISABLED)

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_gray_100_solid.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.LOUD.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_250_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.ICON, AndesThumbnailSize.SIZE_48), config.iconSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.ICON), config.isImageType)
        assertEquals(true, config.hasTint)
    }

    @Test
    fun `Thumbnail, Quiet, Neutral, Icon, Disabled, background color`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_500.toAndesColor(),
                AndesThumbnailHierarchy.QUIET,
                drawable!!,
                AndesThumbnailType.ICON,
                AndesThumbnailSize.SIZE_48,
                AndesThumbnailState.DISABLED)

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_gray_100_solid.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.QUIET.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_250_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.ICON, AndesThumbnailSize.SIZE_48), config.iconSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.ICON), config.isImageType)
        assertEquals(true, config.hasTint)
    }

    @Test
    fun `Thumbnail, Default, Neutral, Icon, Disabled, background color`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_800.toAndesColor(),
                AndesThumbnailHierarchy.DEFAULT,
                drawable!!,
                AndesThumbnailType.ICON,
                AndesThumbnailSize.SIZE_48,
                AndesThumbnailState.DISABLED)

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_white.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.DEFAULT.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_250_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.ICON, AndesThumbnailSize.SIZE_48), config.iconSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.ICON), config.isImageType)
        assertEquals(true, config.hasTint)
    }

    @Test
    fun `Thumbnail, Default, Neutral, Image Circle, Disabled, background color`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_800.toAndesColor(),
                AndesThumbnailHierarchy.DEFAULT,
                drawable!!,
                AndesThumbnailType.IMAGE_CIRCLE,
                AndesThumbnailSize.SIZE_48,
                AndesThumbnailState.DISABLED)

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_white.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.DEFAULT.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_250_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.IMAGE_CIRCLE, AndesThumbnailSize.SIZE_48), config.iconSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.IMAGE_CIRCLE), config.isImageType)
        assertEquals(false, config.hasTint)
    }

    @Test
    fun `Thumbnail, Loud, Neutral, Image Square, Disabled, background color`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_800.toAndesColor(),
                AndesThumbnailHierarchy.LOUD,
                drawable!!,
                AndesThumbnailType.IMAGE_SQUARE,
                AndesThumbnailSize.SIZE_48,
                AndesThumbnailState.DISABLED)

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_white.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.DEFAULT.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_250_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.IMAGE_SQUARE, AndesThumbnailSize.SIZE_48), config.iconSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_48.size.radiusSize(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.IMAGE_SQUARE), config.isImageType)
        assertEquals(false, config.hasTint)
    }

    // MARK - API Level Helpers

    private fun whichIconSize(
        icon: AndesThumbnailType,
        size: AndesThumbnailSize
    ): Int = if (icon != AndesThumbnailType.ICON) {
                size.size.diameter(context).toInt()
            } else {
                size.size.iconSize(context).toInt()
            }

    private fun whichImageType(
        icon: AndesThumbnailType
    ): Boolean = icon != AndesThumbnailType.ICON
}
