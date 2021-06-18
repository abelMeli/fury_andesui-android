package com.mercadolibre.android.andesui.switch.factory

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.switch.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.switch.type.AndesSwitchType

object AndesSwitchComponentResourcesProvider {

    internal const val DURATION = 200L

    internal fun createTrackBackgroundColor(
        status: AndesSwitchStatus,
        type: AndesSwitchType,
        context: Context
    ): Int {
        return when {
            status == AndesSwitchStatus.CHECKED && type == AndesSwitchType.ENABLED -> {
                context.resources.getColor(R.color.andes_accent_color_500)
            }
            status == AndesSwitchStatus.CHECKED && type == AndesSwitchType.DISABLED -> {
                context.resources.getColor(R.color.andes_accent_color_300)
            }
            status == AndesSwitchStatus.UNCHECKED && type == AndesSwitchType.ENABLED -> {
                context.resources.getColor(R.color.andes_gray_100)
            }
            status == AndesSwitchStatus.UNCHECKED && type == AndesSwitchType.DISABLED -> {
                context.resources.getColor(R.color.andes_gray_070)
            }
            else -> {
                context.resources.getColor(R.color.andes_accent_color_500)
            }
        }
    }

    internal fun createThumbDrawable(context: Context): Drawable {
        val height = context.resources.getDimension(R.dimen.andes_switch_thumb_height).toInt()
        val width = context.resources.getDimension(R.dimen.andes_switch_thumb_width).toInt()
        val strokeWidth = context.resources.getDimension(R.dimen.andes_switch_stroke_width).toInt()
        val strokeColor = context.resources.getColor(R.color.andes_transparent)
        val backgroundColor = context.resources.getColor(R.color.andes_bg_color_white)
        return GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(backgroundColor)
            setStroke(strokeWidth, strokeColor)
            setSize(width, height)
        }
    }

    internal fun createNewColorWithAnimation(
        shape: GradientDrawable,
        currentColor: Int,
        status: AndesSwitchStatus,
        type: AndesSwitchType,
        context: Context
    ): Int {
        val newColor = createTrackBackgroundColor(status, type, context)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), currentColor, newColor)
        colorAnimation.duration = DURATION
        colorAnimation.addUpdateListener {
            animator -> shape.setColor(animator.animatedValue as Int)
        }
        colorAnimation.start()
        return newColor
    }

    internal fun createTrackDrawable(context: Context): GradientDrawable {
        val shape = GradientDrawable()
        val height = context.resources.getDimension(R.dimen.andes_switch_track_height).toInt()
        val width = context.resources.getDimension(R.dimen.andes_switch_track_width).toInt()
        val cornerRadius = context.resources.getDimension(R.dimen.andes_switch_track_corner_radius)
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadius = cornerRadius
        shape.setSize(width, height)
        return shape
    }
}
