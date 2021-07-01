package com.mercadolibre.android.andesui.switchandes

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.switchandes.factory.AndesSwitchComponentResourcesProvider.DURATION
import com.mercadolibre.android.andesui.switchandes.factory.AndesSwitchComponentResourcesProvider.createNewColorWithAnimation
import com.mercadolibre.android.andesui.switchandes.factory.AndesSwitchComponentResourcesProvider.createThumbDrawable
import com.mercadolibre.android.andesui.switchandes.factory.AndesSwitchComponentResourcesProvider.createTrackBackgroundColor
import com.mercadolibre.android.andesui.switchandes.factory.AndesSwitchComponentResourcesProvider.createTrackDrawable
import com.mercadolibre.android.andesui.switchandes.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.switchandes.type.AndesSwitchType

internal class AndesSwitchComponent(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var status: AndesSwitchStatus = AndesSwitchStatus.UNCHECKED
    private var type: AndesSwitchType = AndesSwitchType.ENABLED
    private var currentBackgroundColor: Int = createTrackBackgroundColor(status, type, context)

    private lateinit var thumb: View
    private var onCheckedChangeListener: OnCheckedChangeListener? = null

    init {
        setupOnClickSwitchListener()
        configView()
    }

    internal fun setupSwitch(
        listener: OnCheckedChangeListener? = null,
        status: AndesSwitchStatus = AndesSwitchStatus.UNCHECKED,
        type: AndesSwitchType = AndesSwitchType.ENABLED
    ) {
        this.onCheckedChangeListener = listener
        this.status = status
        this.type = type
        currentBackgroundColor = createTrackBackgroundColor(status, type, context)
        background = refreshTrack(currentBackgroundColor, status, type, context)
        setupThumbTranslation(status)
    }

    private fun setupThumbTranslation(status: AndesSwitchStatus) {
        if (status == AndesSwitchStatus.CHECKED) {
            thumb.translationX = resources.getDimension(R.dimen.andes_switch_translation_x)
        } else {
            thumb.translationX = INITIAL_POSITION
        }
    }

    private fun configView() {
        setupTrack()
        setupThumb()
    }

    private fun setupTrack() {
        minHeight = resources.getDimension(R.dimen.andes_switch_track_height).toInt()
        minWidth = resources.getDimension(R.dimen.andes_switch_track_width).toInt()
        background = refreshTrack(currentBackgroundColor, status, type, context)
    }

    private fun setupThumb() {
        val height = resources.getDimension(R.dimen.andes_switch_thumb_height).toInt()
        val width = resources.getDimension(R.dimen.andes_switch_thumb_width).toInt()
        thumb = View(context)
        thumb.layoutParams = LayoutParams(width, height)
        thumb.background = createThumbDrawable(context)
        addView(thumb)
    }

    private fun setupOnClickSwitchListener() {
        setOnClickListener {
            if (type == AndesSwitchType.DISABLED) return@setOnClickListener

            val translation = if (status == AndesSwitchStatus.CHECKED) {
                status = AndesSwitchStatus.UNCHECKED
                INITIAL_POSITION
            } else {
                status = AndesSwitchStatus.CHECKED
                resources.getDimension(R.dimen.andes_switch_translation_x)
            }
            animateTransition(thumb, translation)
            background = refreshTrack(currentBackgroundColor, status, type, context)

            onCheckedChangeListener?.onCheckedChange(status)
        }
    }

    private fun animateTransition(view: View, translation: Float) {
        ObjectAnimator.ofFloat(view, TRANSLATION_X_PROPERTY_NAME, translation).apply {
            duration = DURATION
            start()
        }
    }

    private fun refreshTrack(
        currentColor: Int,
        status: AndesSwitchStatus,
        type: AndesSwitchType,
        context: Context
    ): Drawable {
        val newBackground = createTrackDrawable(context)
        val newColor = createNewColorWithAnimation(
            newBackground,
            currentColor,
            status,
            type,
            context
        )
        currentBackgroundColor = newColor
        return newBackground
    }

    internal interface OnCheckedChangeListener {
        fun onCheckedChange(status: AndesSwitchStatus)
    }

    companion object {
        private const val INITIAL_POSITION = 0F
        private const val TRANSLATION_X_PROPERTY_NAME = "translationX"
    }
}
