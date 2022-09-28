package com.mercadolibre.android.andesui.thumbnail.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

internal class AndesThumbnailParseDrawable {
    companion object {
        fun getImageFromText(
            text: String,
            context: Context,
            textSize: Float,
            height: Int,
            width: Int
        ): Drawable {
            val lettersPaint = textStyle(context, textSize)
            val r = Rect()
            val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val c = Canvas(b)
            c.getClipBounds(r)
            val cHeight: Int = r.height()
            val cWidth: Int = r.width()
            lettersPaint.getTextBounds(text, 0, text.length, r)
            c.drawText(text, getAxisX(cWidth, r), getAxisY(cHeight, r), lettersPaint)
            return BitmapDrawable(context.resources, b)
        }

        private fun textStyle(context: Context, textSize: Float): Paint {
            val lettersPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            val font = context.getFontOrDefault(R.font.andes_font_regular)
            lettersPaint.typeface = font
            lettersPaint.textSize = textSize
            lettersPaint.textAlign = Paint.Align.LEFT
            return lettersPaint
        }

        private fun getAxisY(cHeight: Int, rect: Rect): Float =
            cHeight / 2f + rect.height() / 2f - rect.bottom

        private fun getAxisX(cWidth: Int, rect: Rect): Float =
            cWidth / 2f - rect.width() / 2f - rect.left
    }
}