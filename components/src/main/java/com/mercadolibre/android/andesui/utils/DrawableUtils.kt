package com.mercadolibre.android.andesui.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.mercadolibre.android.andesui.color.AndesColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Receives a [BitmapDrawable] which will suffer some look overhauling that includes scaling and tinting based on received params such as size, color,
 * etc.
 * When the polishing ends, it will return a new [BitmapDrawable].
 * Size of the icon is based on Andes Specification.
 *
 * @param image image of the icon. Ok: The icon.
 * @param context needed for accessing some resources like size, you know.
 * @param dstWidth wanted width of the icon.
 * @param dstHeight wanted height of the icon.
 * @param colors we said we will be tinting the icon and this is the color. Note that the color for state_enabled will be used. If it does not exist,
 * 0 will be used.
 * @return a complete look overhauled [BitmapDrawable].
 */
fun buildColoredAndesBitmapDrawable(
    image: Drawable,
    context: Context,
    dstWidth: Int? = null,
    dstHeight: Int? = null,
    colors: ColorStateList?
): BitmapDrawable {
    val scaledBitmap: Bitmap = if (dstHeight != null && dstWidth != null) {
        Bitmap.createScaledBitmap(
            image.toBitmap(),
            dstWidth,
            dstHeight,
            true)
    } else {
        image.toBitmap()
    }

    return BitmapDrawable(context.resources, scaledBitmap)
        .apply {
            colors?.let {
                setTintMode(PorterDuff.Mode.SRC_IN)
                setTintList(it)
            }
        }
}

fun buildColoredBitmapDrawable(
    image: BitmapDrawable,
    context: Context,
    dstWidth: Int? = null,
    dstHeight: Int? = null,
    color: Int?
): BitmapDrawable {
    val scaledBitmap: Bitmap = when {
        dstHeight != null && dstWidth != null -> Bitmap.createScaledBitmap(
            image.bitmap,
            dstWidth,
            dstHeight,
            true)
        else -> image.bitmap
    }
    return BitmapDrawable(context.resources, scaledBitmap)
        .apply {
            color?.let { setColorFilter(color, PorterDuff.Mode.SRC_IN) }
        }
}

fun buildColoredAndesBitmapDrawable(
    image: BitmapDrawable,
    context: Context,
    dstWidth: Int? = null,
    dstHeight: Int? = null,
    color: AndesColor? = null
): BitmapDrawable {
    val scaledBitmap: Bitmap = when {
        dstHeight != null && dstWidth != null -> Bitmap.createScaledBitmap(
            image.bitmap,
            dstWidth,
            dstHeight,
            true)
        else -> image.bitmap
    }
    return BitmapDrawable(context.resources, scaledBitmap)
        .apply {
            color?.let { setColorFilter(it.colorInt(context), PorterDuff.Mode.SRC_IN) }
        }
}

fun buildCircleBitmap(
    image: Bitmap,
    dstWidth: Int? = null,
    dstHeight: Int? = null
): Bitmap? {
    val scaledBitmap: Bitmap = when {
        dstHeight != null && dstWidth != null -> Bitmap.createScaledBitmap(
            image,
            dstWidth,
            dstHeight,
            true)
        else -> image
    }
    return getCircledBitmap(scaledBitmap)
}

fun getCircledBitmap(bitmap: Bitmap): Bitmap? {
    val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val paint = Paint()
    val rect = Rect(0, 0, bitmap.width, bitmap.height)
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    canvas.drawCircle((bitmap.width / 2).toFloat(), (bitmap.height / 2).toFloat(), (bitmap.width / 2).toFloat(), paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(bitmap, rect, rect, paint)
    return output
}

/**
 * This method is the same as badge icon. When component were made, this should have been removed.
 */
fun buildColoredCircularShapeWithIconDrawable(
    image: BitmapDrawable,
    context: Context,
    iconColor: AndesColor? = null,
    shapeColor: Int? = null,
    diameter: Int
): Drawable {
    val icon = buildColoredAndesBitmapDrawable(image, context, null, null, iconColor)

    val biggerCircle = ShapeDrawable(OvalShape())
    biggerCircle.intrinsicHeight = diameter
    biggerCircle.intrinsicWidth = diameter
    biggerCircle.paint.color = shapeColor!!
    biggerCircle.bounds = Rect(0, 0, diameter, diameter)

    return LayerDrawable(arrayOf(biggerCircle, icon))
}

fun Drawable.toBitmap(): Bitmap {
    if (this is BitmapDrawable) {
        val bitmapDrawable = this
        if (bitmapDrawable.bitmap != null) {
            return bitmapDrawable.bitmap
        }
    }
    val bitmap: Bitmap = if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    } else {
        Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
    }
    val canvas = Canvas(bitmap)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)
    return bitmap
}

internal fun buildRing(@ColorInt ringColor: Int, @Px ringWidth: Int) = GradientDrawable().apply {
    shape = GradientDrawable.OVAL
    cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    setColor(Color.TRANSPARENT)
    setStroke(ringWidth, ringColor)
}

/**
 * Method that allows us to retrieve a drawable by calling a suspend function that will return it
 * from inside a coroutine. Once the drawable is available, it will be passed in the [block] as the
 * argument.
 *
 * To see an example, check [com.mercadolibre.android.andesui.modal.views.AndesModalImageComponent.setDrawableSuspended]
 */
internal fun setDrawableSuspending(suspendedDrawable: (suspend () -> Drawable?), block: (Drawable?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val suspendDrawable = suspendedDrawable()
            withContext(Dispatchers.Main) {
                block(suspendDrawable)
            }
        } catch (error: Throwable) {
            Log.e("DrawableUtils", "Error resolving suspended drawable", error)
        }
    }
}
