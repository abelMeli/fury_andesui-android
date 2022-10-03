package com.mercadolibre.android.andesui.thumbnail

import android.content.Context
import android.widget.ImageView
import android.widget.ImageView.ScaleType.CENTER_CROP
import android.widget.ImageView.ScaleType.FIT_CENTER
import android.widget.ImageView.ScaleType.FIT_END
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.thumbnail.assetType.AndesThumbnailAssetType
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.thumbnail.shape.AndesThumbnailShape
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import com.mercadolibre.android.andesui.thumbnail.state.AndesThumbnailState
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailType
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.assertNotEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [TEST_ANDROID_VERSION_CODE])
@RunWith(RobolectricTestRunner::class)
class AndesThumbnailTest {

    val context: Context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun `init AndesThumbnail min args is ok`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val thumbnail = AndesThumbnail(
            context = context,
            image = drawable,
            accentColor = R.color.andes_white.toAndesColor())

        // Verify
        assertNotNull(thumbnail)
    }

    @Test
    fun `AndesThumbnail Icon, Default, Accent color, Enabled`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val thumbnail = AndesThumbnail(
                context = context,
                type = AndesThumbnailType.ICON,
                hierarchy = AndesThumbnailHierarchy.DEFAULT,
                accentColor = R.color.andes_accent_color_500.toAndesColor(),
                state = AndesThumbnailState.ENABLED,
                size = AndesThumbnailSize.SIZE_56,
                image = drawable)

        // Then
        val imageFrame = thumbnail.findViewById<ImageView>(R.id.andes_thumbnail_image)

        // Verify
        // Image
        assertEquals(AndesThumbnailSize.SIZE_56.size.iconSize(context).toInt(), imageFrame.layoutParams.width)
        assertEquals(AndesThumbnailSize.SIZE_56.size.iconSize(context).toInt(), imageFrame.layoutParams.height)
        assertEquals(FIT_CENTER, imageFrame.scaleType)
    }

    @Test
    fun `AndesThumbnail Icon, Quiet, Accent color, Enabled`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val thumbnail = AndesThumbnail(
                context = context,
                type = AndesThumbnailType.ICON,
                hierarchy = AndesThumbnailHierarchy.QUIET,
                accentColor = R.color.andes_accent_color_500.toAndesColor(),
                state = AndesThumbnailState.ENABLED,
                size = AndesThumbnailSize.SIZE_56,
                image = drawable)

        // Then
        val imageFrame = thumbnail.findViewById<ImageView>(R.id.andes_thumbnail_image)

        // Verify
        // Image
        assertEquals(AndesThumbnailSize.SIZE_56.size.iconSize(context).toInt(), imageFrame.layoutParams.width)
        assertEquals(AndesThumbnailSize.SIZE_56.size.iconSize(context).toInt(), imageFrame.layoutParams.height)
        assertEquals(FIT_CENTER, imageFrame.scaleType)
    }

    @Test
    fun `AndesThumbnail Image Circle, Loud, Accent color, Enabled`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val thumbnail = AndesThumbnail(
                context = context,
                type = AndesThumbnailType.IMAGE_CIRCLE,
                hierarchy = AndesThumbnailHierarchy.LOUD,
                accentColor = R.color.andes_accent_color_500.toAndesColor(),
                state = AndesThumbnailState.ENABLED,
                size = AndesThumbnailSize.SIZE_56,
                image = drawable)

        // Then
        val imageFrame = thumbnail.findViewById<ImageView>(R.id.andes_thumbnail_image)

        // Verify
        // Image
        assertEquals(AndesThumbnailSize.SIZE_56.size.diameter(context).toInt(), imageFrame.layoutParams.width)
        assertEquals(AndesThumbnailSize.SIZE_56.size.diameter(context).toInt(), imageFrame.layoutParams.height)
        assertEquals(CENTER_CROP, imageFrame.scaleType)
    }

    @Test
    fun `AndesThumbnail Image Square, Quiet, Accent color, Enabled`() {
        // When
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val thumbnail = AndesThumbnail(
                context = context,
                type = AndesThumbnailType.IMAGE_SQUARE,
                hierarchy = AndesThumbnailHierarchy.QUIET,
                accentColor = R.color.andes_accent_color_500.toAndesColor(),
                state = AndesThumbnailState.ENABLED,
                size = AndesThumbnailSize.SIZE_56,
                image = drawable)

        // Then
        val imageFrame = thumbnail.findViewById<ImageView>(R.id.andes_thumbnail_image)

        // Verify
        // Image
        assertEquals(AndesThumbnailSize.SIZE_56.size.diameter(context).toInt(), imageFrame.layoutParams.width)
        assertEquals(AndesThumbnailSize.SIZE_56.size.diameter(context).toInt(), imageFrame.layoutParams.height)
        assertEquals(CENTER_CROP, imageFrame.scaleType)
    }

    @Test
    fun `creating thumbnail with scale type`() {
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val thumbnail = AndesThumbnail(
            context = context,
            type = AndesThumbnailType.IMAGE_SQUARE,
            hierarchy = AndesThumbnailHierarchy.QUIET,
            accentColor = R.color.andes_accent_color_500.toAndesColor(),
            state = AndesThumbnailState.ENABLED,
            size = AndesThumbnailSize.SIZE_56,
            image = drawable,
            scaleType = ImageView.ScaleType.FIT_START
        )

        thumbnail.scaleType assertEquals ImageView.ScaleType.FIT_START
        thumbnail.state assertEquals AndesThumbnailState.ENABLED
    }

    @Test
    fun `creating thumbnail and setting new scale type`() {
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val thumbnail = AndesThumbnail(
            context = context,
            type = AndesThumbnailType.IMAGE_SQUARE,
            hierarchy = AndesThumbnailHierarchy.QUIET,
            accentColor = R.color.andes_accent_color_500.toAndesColor(),
            state = AndesThumbnailState.ENABLED,
            size = AndesThumbnailSize.SIZE_56,
            image = drawable,
            scaleType = ImageView.ScaleType.FIT_START
        )

        thumbnail.scaleType = FIT_END
        thumbnail.state assertEquals AndesThumbnailState.ENABLED
        thumbnail.scaleType assertEquals FIT_END
    }

    @Test
    fun `creating thumbnail of assetType text and setting an image`() {
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val thumbnail = AndesThumbnail(
            context = context,
            accentColor = R.color.andes_accent_color_500.toAndesColor(),
            assetType = AndesThumbnailAssetType.Text("AB"),
            thumbnailShape = AndesThumbnailShape.Circle,
            hierarchy = AndesThumbnailHierarchy.QUIET,
            size = AndesThumbnailSize.SIZE_56,
            state = AndesThumbnailState.ENABLED,
        )
        //when is a thumbnail of assetType Text should not set an image
        thumbnail.image = drawable
        thumbnail.image assertNotEquals drawable
        thumbnail.state assertEquals AndesThumbnailState.ENABLED
    }

    @Test
    fun `creating thumbnail of assetType text, shape square and state disable`() {
        val thumbnail = AndesThumbnail(
            context = context,
            hierarchy = AndesThumbnailHierarchy.QUIET,
            accentColor = R.color.andes_accent_color_500.toAndesColor(),
            state = AndesThumbnailState.DISABLED,
            size = AndesThumbnailSize.SIZE_56,
            assetType = AndesThumbnailAssetType.Text("AB"),
            thumbnailShape = AndesThumbnailShape.Square
        )
        thumbnail.thumbnailShape assertEquals AndesThumbnailShape.Square
        thumbnail.scaleType assertEquals ImageView.ScaleType.CENTER_CROP
        thumbnail.state assertEquals AndesThumbnailState.DISABLED
    }

    @Test
    fun `creating thumbnail with min arguments`() {
        val thumbnail = AndesThumbnail(
            context = context,
            accentColor = R.color.andes_accent_color_500.toAndesColor(),
            assetType = AndesThumbnailAssetType.Text("AB")
        )

        thumbnail.state assertEquals AndesThumbnailState.ENABLED
        thumbnail.thumbnailShape assertEquals AndesThumbnailShape.Circle
        thumbnail.size assertEquals AndesThumbnailSize.SIZE_48
        thumbnail.hierarchy assertEquals AndesThumbnailHierarchy.DEFAULT
    }

    @Test
    fun `creating thumbnail of assetType Icon, shape square and set a type Image_circle`() {
        val drawable = ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!
        val thumbnail = AndesThumbnail(
            context = context,
            hierarchy = AndesThumbnailHierarchy.QUIET,
            accentColor = R.color.andes_accent_color_500.toAndesColor(),
            state = AndesThumbnailState.DISABLED,
            size = AndesThumbnailSize.SIZE_56,
            assetType = AndesThumbnailAssetType.Icon(drawable),
            thumbnailShape = AndesThumbnailShape.Square
        )
        //using the deprecated attribute type to change to image_circle
        thumbnail.type = AndesThumbnailType.IMAGE_CIRCLE

        //then change the assetType and the shape
        thumbnail.thumbnailShape assertEquals AndesThumbnailShape.Circle
        thumbnail.assetType assertEquals AndesThumbnailAssetType.Image(drawable)
    }

}
