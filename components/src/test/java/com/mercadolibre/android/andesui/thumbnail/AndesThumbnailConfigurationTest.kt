package com.mercadolibre.android.andesui.thumbnail

import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.thumbnail.assetType.AndesThumbnailAssetType
import com.mercadolibre.android.andesui.thumbnail.factory.AndesThumbnailAttrs
import com.mercadolibre.android.andesui.thumbnail.factory.AndesThumbnailConfigurationFactory
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.thumbnail.shape.AndesThumbnailShape
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
    fun `Thumbnail, Loud, Neutral, Icon, Enabled, background color, size 56`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_800.toAndesColor(),
                AndesThumbnailHierarchy.LOUD,
                AndesThumbnailType.ICON,
                AndesThumbnailSize.SIZE_56,
                AndesThumbnailState.ENABLED,
                ImageView.ScaleType.CENTER_CROP,

                AndesThumbnailAssetType.Icon(drawable!!),
                AndesThumbnailShape.Circle
        )

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_orange_800.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.LOUD.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_white.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.ICON, AndesThumbnailSize.SIZE_56), config.heightSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_56.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_56.size.diameter(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.ICON), config.isImageType)
        assertEquals(true, config.hasTint)
        assertEquals(ImageView.ScaleType.FIT_CENTER, config.scaleType)
    }

    @Test
    fun `Thumbnail, Quiet, Neutral, Icon, Enabled, background color ,size 32`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        val expectedIconColor = R.color.andes_orange_500.toAndesColor()
        val expectedBackgrounColor = R.color.andes_orange_500.toAndesColor().apply {
            alpha = 0.1f
        }

        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_500.toAndesColor(),
                AndesThumbnailHierarchy.QUIET,
                AndesThumbnailType.ICON,
                AndesThumbnailSize.SIZE_32,
                AndesThumbnailState.ENABLED,
                ImageView.ScaleType.CENTER_CROP,
                AndesThumbnailAssetType.Icon(drawable!!),
                AndesThumbnailShape.Circle
        )

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(expectedBackgrounColor, config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.QUIET.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(expectedIconColor, config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.ICON, AndesThumbnailSize.SIZE_32), config.heightSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_32.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_32.size.diameter(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.ICON), config.isImageType)
        assertEquals(true, config.hasTint)
        assertEquals(ImageView.ScaleType.FIT_CENTER, config.scaleType)
    }

    @Test
    fun `Thumbnail, Default, Neutral, Icon, Enabled, background color, size 40`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_800.toAndesColor(),
                AndesThumbnailHierarchy.DEFAULT,
                AndesThumbnailType.ICON,
                AndesThumbnailSize.SIZE_40,
                AndesThumbnailState.ENABLED,
                ImageView.ScaleType.CENTER_CROP,
                AndesThumbnailAssetType.Icon(drawable!!),
                AndesThumbnailShape.Circle
        )

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_white.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.DEFAULT.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_900_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.ICON, AndesThumbnailSize.SIZE_40), config.heightSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_40.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_40.size.diameter(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.ICON), config.isImageType)
        assertEquals(true, config.hasTint)
        assertEquals(ImageView.ScaleType.FIT_CENTER, config.scaleType)
    }

    @Test
    fun `Thumbnail, Default, Neutral, Image Circle, Enabled, background color, size 64`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_800.toAndesColor(),
                AndesThumbnailHierarchy.DEFAULT,
                AndesThumbnailType.IMAGE_CIRCLE,
                AndesThumbnailSize.SIZE_64,
                AndesThumbnailState.ENABLED,
                ImageView.ScaleType.CENTER_CROP,
            AndesThumbnailAssetType.Image(drawable!!),
            AndesThumbnailShape.Circle
        )

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_white.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.DEFAULT.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_900_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.IMAGE_CIRCLE, AndesThumbnailSize.SIZE_64), config.heightSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_64.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_64.size.diameter(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.IMAGE_CIRCLE), config.isImageType)
        assertEquals(false, config.hasTint)
        assertEquals(ImageView.ScaleType.CENTER_CROP, config.scaleType)
    }

    @Test
    fun `Thumbnail, Loud, Neutral, Image Square, Enabled, background color, size 72`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_800.toAndesColor(),
                AndesThumbnailHierarchy.LOUD,
                AndesThumbnailType.IMAGE_SQUARE,
                AndesThumbnailSize.SIZE_72,
                AndesThumbnailState.ENABLED,
                ImageView.ScaleType.CENTER_CROP,
            AndesThumbnailAssetType.Image(drawable!!),
            AndesThumbnailShape.Square
        )

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_white.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.DEFAULT.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_900_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.IMAGE_SQUARE, AndesThumbnailSize.SIZE_72), config.heightSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_72.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_72.size.radiusSize(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.IMAGE_SQUARE), config.isImageType)
        assertEquals(false, config.hasTint)
        assertEquals(ImageView.ScaleType.CENTER_CROP, config.scaleType)
    }

    @Test
    fun `Thumbnail, Loud, Icon, Disabled, background color`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_800.toAndesColor(),
                AndesThumbnailHierarchy.LOUD,
                AndesThumbnailType.ICON,
                AndesThumbnailSize.SIZE_48,
                AndesThumbnailState.DISABLED,
                ImageView.ScaleType.CENTER_CROP,
            AndesThumbnailAssetType.Icon(drawable!!),
            AndesThumbnailShape.Circle
        )

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_gray_100_solid.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.LOUD.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_250_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.ICON, AndesThumbnailSize.SIZE_48), config.heightSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.ICON), config.isImageType)
        assertEquals(true, config.hasTint)
        assertEquals(ImageView.ScaleType.FIT_CENTER, config.scaleType)
        assertEquals(ImageView.ScaleType.FIT_CENTER, config.scaleType)
    }

    @Test
    fun `Thumbnail, Quiet, Icon, Disabled, background color`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_500.toAndesColor(),
                AndesThumbnailHierarchy.QUIET,
                AndesThumbnailType.ICON,
                AndesThumbnailSize.SIZE_48,
                AndesThumbnailState.DISABLED,
                ImageView.ScaleType.CENTER_CROP,
            AndesThumbnailAssetType.Icon(drawable!!),
            AndesThumbnailShape.Circle
        )

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_gray_100_solid.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.QUIET.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_250_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.ICON, AndesThumbnailSize.SIZE_48), config.heightSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.ICON), config.isImageType)
        assertEquals(true, config.hasTint)
        assertEquals(ImageView.ScaleType.FIT_CENTER, config.scaleType)
    }

    @Test
    fun `Thumbnail, Default,  Icon, Disabled, background color, size 80`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_800.toAndesColor(),
                AndesThumbnailHierarchy.DEFAULT,
                AndesThumbnailType.ICON,
                AndesThumbnailSize.SIZE_80,
                AndesThumbnailState.DISABLED,
                ImageView.ScaleType.CENTER_CROP,
            AndesThumbnailAssetType.Icon(drawable!!),
            AndesThumbnailShape.Circle
        )

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_white.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.DEFAULT.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_250_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.ICON, AndesThumbnailSize.SIZE_80), config.heightSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_80.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_80.size.diameter(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.ICON), config.isImageType)
        assertEquals(true, config.hasTint)
        assertEquals(ImageView.ScaleType.FIT_CENTER, config.scaleType)
    }

    @Test
    fun `Thumbnail, Default, Image Circle, Disabled, background color`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_800.toAndesColor(),
                AndesThumbnailHierarchy.DEFAULT,
                AndesThumbnailType.IMAGE_CIRCLE,
                AndesThumbnailSize.SIZE_48,
                AndesThumbnailState.DISABLED,
                ImageView.ScaleType.CENTER_CROP,
            AndesThumbnailAssetType.Image(drawable!!),
            AndesThumbnailShape.Circle
        )

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_white.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.DEFAULT.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_250_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.IMAGE_CIRCLE, AndesThumbnailSize.SIZE_48), config.heightSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.IMAGE_CIRCLE), config.isImageType)
        assertEquals(false, config.hasTint)
        assertEquals(ImageView.ScaleType.CENTER_CROP, config.scaleType)
    }

    @Test
    fun `Thumbnail, Loud,  Image Square, Disabled, background color`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
                R.color.andes_orange_800.toAndesColor(),
                AndesThumbnailHierarchy.LOUD,
                AndesThumbnailType.IMAGE_SQUARE,
                AndesThumbnailSize.SIZE_48,
                AndesThumbnailState.DISABLED,
                ImageView.ScaleType.CENTER_CROP,
            AndesThumbnailAssetType.Image(drawable!!),
            AndesThumbnailShape.Square
        )

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_white.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.DEFAULT.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_250_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.IMAGE_SQUARE, AndesThumbnailSize.SIZE_48), config.heightSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_48.size.radiusSize(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.IMAGE_SQUARE), config.isImageType)
        assertEquals(false, config.hasTint)
        assertEquals(ImageView.ScaleType.CENTER_CROP, config.scaleType)
    }

    @Test
    fun `Thumbnail, Default, Text, square, Disabled, background color`() {
        // When

        attrs = AndesThumbnailAttrs(
            R.color.andes_orange_800.toAndesColor(),
            AndesThumbnailHierarchy.DEFAULT,
            AndesThumbnailType.ICON,
            AndesThumbnailSize.SIZE_48,
            AndesThumbnailState.DISABLED,
            ImageView.ScaleType.CENTER_CROP,
            AndesThumbnailAssetType.Text("AB"),
            AndesThumbnailShape.Square
        )

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_white.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.DEFAULT.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_250_solid.toAndesColor(), config.iconColor)
        assertEquals(whichHeightSize(AndesThumbnailAssetType.Text("AB"), AndesThumbnailSize.SIZE_48), config.heightSize)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_48.size.radiusSize(context), config.cornerRadius)
        assertEquals(ImageView.ScaleType.FIT_CENTER, config.scaleType)
    }

    @Test
    fun `Thumbnail, Default, Icon, Square, Disabled`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_pagar_y_cobrar_efectivo_24)
        attrs = AndesThumbnailAttrs(
            R.color.andes_orange_800.toAndesColor(),
            AndesThumbnailHierarchy.DEFAULT,
            AndesThumbnailType.ICON,
            AndesThumbnailSize.SIZE_48,
            AndesThumbnailState.DISABLED,
            ImageView.ScaleType.CENTER_CROP,
            AndesThumbnailAssetType.Icon(drawable!!),
            AndesThumbnailShape.Square
        )

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_white.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.DEFAULT.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_250_solid.toAndesColor(), config.iconColor)
        assertEquals(whichIconSize(AndesThumbnailType.ICON, AndesThumbnailSize.SIZE_48), config.heightSize)
        assertEquals(drawable, config.image)
        assertEquals(AndesThumbnailSize.SIZE_48.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_48.size.radiusSize(context), config.cornerRadius)
        assertEquals(whichImageType(AndesThumbnailType.ICON), config.isImageType)
        assertEquals(true, config.hasTint)
        assertEquals(ImageView.ScaleType.FIT_CENTER, config.scaleType)
    }

    @Test
    fun `Thumbnail, Default, Text, circle, enabled, 24`() {
        // When

        attrs = AndesThumbnailAttrs(
            R.color.andes_orange_800.toAndesColor(),
            AndesThumbnailHierarchy.DEFAULT,
            AndesThumbnailType.ICON,
            AndesThumbnailSize.SIZE_24,
            AndesThumbnailState.ENABLED,
            ImageView.ScaleType.CENTER_CROP,
            AndesThumbnailAssetType.Text("AB"),
            AndesThumbnailShape.Circle
        )

        // Then
        val config = configFactory.create(context, attrs)

        // Verify
        assertEquals(R.color.andes_white.toAndesColor(), config.backgroundColor)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.borderColor)
        assertEquals(AndesThumbnailHierarchy.DEFAULT.hierarchy.hasBorder(), config.hasBorder)
        assertEquals(R.color.andes_gray_900_solid.toAndesColor(), config.iconColor)
        assertEquals(whichHeightSize(AndesThumbnailAssetType.Text("AB"), AndesThumbnailSize.SIZE_24), config.heightSize)
        assertEquals(AndesThumbnailSize.SIZE_24.size.diameter(context), config.size)
        assertEquals(AndesThumbnailSize.SIZE_24.size.diameter(context), config.cornerRadius)
        assertEquals(whichWidthSize(
            AndesThumbnailAssetType.Text("AB"), AndesThumbnailSize.SIZE_24), config.widthSize)
        assertEquals(ImageView.ScaleType.FIT_CENTER, config.scaleType)
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

    private fun whichHeightSize(
        type: AndesThumbnailAssetType,
        size: AndesThumbnailSize
    ): Int = when(type){
        is AndesThumbnailAssetType.Icon -> size.size.iconSize(context).toInt()
        is AndesThumbnailAssetType.Image -> size.size.diameter(context).toInt()
        is AndesThumbnailAssetType.Text-> size.size.textHeight(context).toInt()
    }

    private fun whichWidthSize(
        type: AndesThumbnailAssetType,
        size: AndesThumbnailSize
    ): Int = when(type){
        is AndesThumbnailAssetType.Icon -> size.size.iconSize(context).toInt()
        is AndesThumbnailAssetType.Image -> size.size.diameter(context).toInt()
        is AndesThumbnailAssetType.Text-> size.size.textWidth(context).toInt()
    }


    private fun whichImageType(
        icon: AndesThumbnailType
    ): Boolean = icon != AndesThumbnailType.ICON
}
