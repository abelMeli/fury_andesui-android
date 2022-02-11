package com.mercadolibre.android.andesui.thumbnail

import android.content.Context
import android.os.Build
import android.widget.ImageView
import android.widget.ImageView.ScaleType.CENTER_CROP
import android.widget.ImageView.ScaleType.FIT_CENTER
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import com.mercadolibre.android.andesui.thumbnail.state.AndesThumbnailState
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
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
}
