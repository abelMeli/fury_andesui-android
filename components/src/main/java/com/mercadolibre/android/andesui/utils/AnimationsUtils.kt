package com.mercadolibre.android.andesui.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View
import com.mercadolibre.android.andesui.R

/**
 * AnimationsUtils is a object that helps when you want to make an fade input [fadeIn] or fade output [fadeOut] animation in a view.
 *
 * The animations can be performed centrally [doFadeCenter], in the vertical [doFadeTop], [doFadeBottom] or
 * horizontal [doFadeLeft], [doFadeRight] direction [Position].
 *
 * It can also be assigned a different duration as well as a delay to improve synchronization between other animations
 *
 */
object AnimationsUtils {

    enum class Fade { IN, OUT }
    enum class Position(internal val direction: String = NO_DIRECTION, internal val sense: Int = NO_SENSE) {
        CENTER,
        TOP(DIRECTION_Y_AXIS, POSITIVE_SENSE),
        LEFT(DIRECTION_X_AXIS, NEGATIVE_SENSE),
        RIGHT(DIRECTION_X_AXIS, POSITIVE_SENSE),
        BOTTOM(DIRECTION_Y_AXIS, NEGATIVE_SENSE)
    }

    private const val ALPHA_THRESHOLD = 0.5f

    private const val FADE_OUT_ALPHA = 0f
    private const val FADE_IN_SHOW_ALPHA = 1f

    private const val START_MOVE_ANIMATION = 0f

    private const val LEFT_END_MOVE_ANIMATION = -100f
    private const val RIGHT_END_MOVE_ANIMATION = 100f

    private const val TOP_END_MOVE_ANIMATION = -200f
    private const val BOTTOM_END_MOVE_ANIMATION = 100f

    private const val JUMP_HALF_STEP_DURATION = 50L
    private const val JUMP_WHOLE_STEP_DURATION = 100L

    private const val SHAKE_HALF_STEP_DURATION = 50L
    private const val SHAKE_WHOLE_STEP_DURATION = 100L
    private const val DIRECTION_X_AXIS = "translationX"
    private const val DIRECTION_Y_AXIS = "translationY"
    private const val NO_DIRECTION = ""
    private const val NO_SENSE = 0
    private const val POSITIVE_SENSE = 1
    private const val NEGATIVE_SENSE = -1

    /**
     * Method that perform an animation in (fade in) on a view
     *
     * @param view view to animate
     * @param from the direction of the animation
     * @param delay delay to execute de animation.
     * @param changeVisibility determinate if the visibility property must change or not.
     */
    fun fadeIn(view: View, from: Position, duration: Long, delay: Long, changeVisibility: Boolean) {
        if (view.alpha < ALPHA_THRESHOLD) {
            view.animate().cancel()
            when (from) {
                Position.CENTER -> {
                    doFadeCenter(view, duration, delay, Fade.IN, changeVisibility)
                }
                Position.LEFT -> {
                    doFadeLeft(view, duration, delay, Fade.IN, changeVisibility)
                }
                Position.TOP -> {
                    doFadeTop(view, duration, delay, Fade.IN, changeVisibility)
                }
                Position.RIGHT -> {
                    doFadeRight(view, duration, delay, Fade.IN, changeVisibility)
                }
                Position.BOTTOM -> {
                    doFadeBottom(view, duration, delay, Fade.IN, changeVisibility)
                }
            }
        }
    }

    /**
     * Method that perform an animation out (fade out) on a view
     *
     * @param view view to animate
     * @param to the direction of the animation
     * @param delay delay to execute de animation.
     * @param changeVisibility determinate if the visibility property must change or not.
     */
    fun fadeOut(view: View, to: Position, duration: Long, delay: Long, changeVisibility: Boolean) {
        if (view.alpha > ALPHA_THRESHOLD) {
            view.animate().cancel()
            when (to) {
                Position.CENTER -> {
                    doFadeCenter(view, duration, delay, Fade.OUT, changeVisibility)
                }
                Position.LEFT -> {
                    doFadeLeft(view, duration, delay, Fade.OUT, changeVisibility)
                }
                Position.TOP -> {
                    doFadeTop(view, duration, delay, Fade.OUT, changeVisibility)
                }
                Position.RIGHT -> {
                    doFadeRight(view, duration, delay, Fade.OUT, changeVisibility)
                }
                Position.BOTTOM -> {
                    doFadeBottom(view, duration, delay, Fade.OUT, changeVisibility)
                }
            }
        }
    }

    /**
     * Method that performs a "shake" animation over the chosen view.
     * To see the animation in action see [this link](https://www.figma.com/proto/Kd1wiAh2jetqnR0ZJ2EEUY/Amount-field?node-id=2%3A11423&viewport=332%2C48%2C0.6&scaling=min-zoom&starting-point-node-id=935%3A184590&show-proto-sidebar=1)
     *
     * @param onAnimationEnd optional callback to run when the animation is over.
     */
    fun View.shake(onAnimationEnd: ((View) -> Unit)? = null) {
        val halfStep =
            context.resources.getDimensionPixelSize(R.dimen.andes_amount_field_animation_shake_half_step).toFloat()
        val wholeStep =
            context.resources.getDimensionPixelSize(R.dimen.andes_amount_field_animation_shake_whole_step).toFloat()

        translateTo(Position.LEFT, halfStep, SHAKE_WHOLE_STEP_DURATION) {
            translateTo(Position.RIGHT, wholeStep, SHAKE_HALF_STEP_DURATION) {
                translateTo(Position.LEFT, wholeStep, SHAKE_HALF_STEP_DURATION) {
                    translateTo(Position.RIGHT, wholeStep, SHAKE_HALF_STEP_DURATION) {
                        translateTo(Position.LEFT, halfStep, SHAKE_HALF_STEP_DURATION) {
                            translateTo(Position.CENTER) {
                                onAnimationEnd?.invoke(this)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Method that performs a "jump" animation over the chosen view.
     * To see the animation in action see [this link](https://www.figma.com/proto/Kd1wiAh2jetqnR0ZJ2EEUY/Amount-field?node-id=2%3A11423&viewport=332%2C48%2C0.6&scaling=min-zoom&starting-point-node-id=935%3A184578&show-proto-sidebar=1)
     *
     * @param onAnimationEnd optional callback to run when the animation is over.
     */
    fun View.jump(onAnimationEnd: ((View) -> Unit)? = null) {
        val halfStep =
            context.resources.getDimensionPixelSize(R.dimen.andes_amount_field_animation_jump_half_step).toFloat()
        val wholeStep =
            context.resources.getDimensionPixelSize(R.dimen.andes_amount_field_animation_jump_whole_step).toFloat()

        translateTo(to = Position.BOTTOM, delta = halfStep, animDuration = JUMP_WHOLE_STEP_DURATION) {
            translateTo(Position.TOP, wholeStep, JUMP_HALF_STEP_DURATION) {
                translateTo(Position.BOTTOM, halfStep, JUMP_HALF_STEP_DURATION) {
                    translateTo(to = Position.CENTER, animDuration = JUMP_HALF_STEP_DURATION) {
                        onAnimationEnd?.invoke(this)
                    }
                }
            }
        }
    }

    /**
     * Moves the caller view in the direction and sense set in the [to] parameter.
     * If passed, it will call the [onAnimationEnd] lambda block at the end of the animation.
     */
    private fun View.translateTo(
        to: Position,
        delta: Float = 0f,
        animDuration: Long = 100L,
        onAnimationEnd: ((View) -> Unit)? = null
    ) {
        getAnimationAction(to, delta, animDuration, onAnimationEnd).invoke(this)
    }

    /**
     * Returns an animation action to be invoked over a view. When the order is to center the view
     * (for example, at the end of an animation chain), this method returns the order to set the
     * translation at (0, 0).
     * In any other case, this method orders to move the view according to the passed [delta] in the
     * direction and sense indicated in the [animateTo] value.
     *
     * In all cases, if an action [onAnimationEnd] is passed, this action will be called at the end
     * of the animation.
     */
    private fun getAnimationAction(
        animateTo: Position,
        delta: Float = 0f,
        animDuration: Long = 100L,
        onAnimationEnd: ((View) -> Unit)? = null
    ): (targetView: View) -> Unit {
        return if (animateTo != Position.CENTER) {
            { targetView ->
                getObjectAnimator(
                    targetView,
                    animateTo,
                    delta,
                    animDuration,
                    getAnimationListener(targetView, onAnimationEnd)
                ).start()
            }
        } else {
            { targetView: View ->
                targetView.animate()
                    .translationX(0f)
                    .translationY(0f)
                    .setDuration(animDuration)
                    .setListener(getAnimationListener(targetView, onAnimationEnd))
                    .start()
            }
        }
    }

    @SuppressLint("ObjectAnimatorBinding")
    /**
     * Returns an object animator object on which the start() method can be called.
     * This object will be already set to move the [targetView] in the direction and sense indicated
     * in the [animateTo] parameter.
     */
    private fun getObjectAnimator(
        targetView: View,
        animateTo: Position,
        delta: Float,
        animDuration: Long,
        listener: Animator.AnimatorListener
    ): ObjectAnimator {
        return ObjectAnimator.ofFloat(targetView, animateTo.direction, delta * animateTo.sense).apply {
            duration = animDuration
            addListener(listener)
        }
    }

    /**
     * Returns a listener ready to be attached to an animator or viewPropertyAnimator object.
     * This listener will call the passed [onAnimationEnd] lambda block in the end animation callback.
     */
    private fun getAnimationListener(targetView: View, onAnimationEnd: ((View) -> Unit)? = null): Animator.AnimatorListener {
        return object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator?) {
                // no-op
            }

            override fun onAnimationEnd(animation: Animator?) {
                onAnimationEnd?.invoke(targetView)
            }

            override fun onAnimationCancel(animation: Animator?) {
                // no-op
            }

            override fun onAnimationRepeat(animation: Animator?) {
                // no-op
            }
        }
    }

    private fun doFadeCenter(view: View, duration: Long, delay: Long, fade: Fade, changeVisibility: Boolean) {
        when (fade) {
            Fade.IN -> {
                view.animate()
                    .alpha(FADE_IN_SHOW_ALPHA)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator?) {
                            setOnAnimationStart(view, changeVisibility)
                            super.onAnimationStart(animation)
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                        }
                    })
                    .setStartDelay(delay)
                    .start()
            }
            Fade.OUT -> {
                view.animate()
                    .alpha(FADE_OUT_ALPHA)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator?) {
                            super.onAnimationStart(animation)
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            setOnAnimationEnd(view, changeVisibility)
                        }
                    })
                    .setStartDelay(delay)
                    .start()
            }
        }
    }

    private fun doFadeLeft(view: View, duration: Long, delay: Long, fade: Fade, changeVisibility: Boolean) {
        when (fade) {
            Fade.IN -> {
                view.translationX = LEFT_END_MOVE_ANIMATION
                view.animate()
                    .alpha(FADE_IN_SHOW_ALPHA)
                    .translationX(LEFT_END_MOVE_ANIMATION)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator?) {
                            setOnAnimationStart(view, changeVisibility)
                            super.onAnimationStart(animation)
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                        }
                    })
                    .setStartDelay(delay)
                    .start()
            }
            Fade.OUT -> {
                view.translationY = START_MOVE_ANIMATION
                view
                    .animate()
                    .alpha(FADE_OUT_ALPHA)
                    .translationY(LEFT_END_MOVE_ANIMATION)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator?) {
                            super.onAnimationStart(animation)
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            setOnAnimationEnd(view, changeVisibility)
                        }
                    })
                    .setStartDelay(delay)
                    .start()
            }
        }
    }

    private fun doFadeTop(view: View, duration: Long, delay: Long, fade: Fade, changeVisibility: Boolean) {
        when (fade) {
            Fade.IN -> {
                view.translationY = TOP_END_MOVE_ANIMATION
                view.animate()
                    .alpha(FADE_IN_SHOW_ALPHA)
                    .translationY(START_MOVE_ANIMATION)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator?) {
                            setOnAnimationStart(view, changeVisibility)
                            super.onAnimationStart(animation)
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                        }
                    })
                    .setStartDelay(delay)
                    .start()
            }
            Fade.OUT -> {
                view.translationY = START_MOVE_ANIMATION
                view
                    .animate()
                    .alpha(FADE_OUT_ALPHA)
                    .translationY(TOP_END_MOVE_ANIMATION)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator?) {
                            super.onAnimationStart(animation)
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            setOnAnimationEnd(view, changeVisibility)
                        }
                    })
                    .setStartDelay(delay)
                    .start()
            }
        }
    }

    private fun doFadeRight(view: View, duration: Long, delay: Long, fade: Fade, changeVisibility: Boolean) {
        when (fade) {
            Fade.IN -> {
                view.translationX = RIGHT_END_MOVE_ANIMATION
                view.animate()
                    .alpha(FADE_IN_SHOW_ALPHA)
                    .translationX(RIGHT_END_MOVE_ANIMATION)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator?) {
                            setOnAnimationStart(view, changeVisibility)
                            super.onAnimationStart(animation)
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                        }
                    })
                    .setStartDelay(delay)
                    .start()
            }
            Fade.OUT -> {
                view.translationY = START_MOVE_ANIMATION
                view
                    .animate()
                    .alpha(FADE_OUT_ALPHA)
                    .translationY(RIGHT_END_MOVE_ANIMATION)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator?) {
                            super.onAnimationStart(animation)
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            setOnAnimationEnd(view, changeVisibility)
                        }
                    })
                    .setStartDelay(delay)
                    .start()
            }
        }
    }

    private fun doFadeBottom(view: View, duration: Long, delay: Long, fade: Fade, changeVisibility: Boolean) {
        when (fade) {
            Fade.IN -> {
                view.translationY = BOTTOM_END_MOVE_ANIMATION
                view.animate()
                    .alpha(FADE_IN_SHOW_ALPHA)
                    .translationY(START_MOVE_ANIMATION)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator?) {
                            setOnAnimationStart(view, changeVisibility)
                            super.onAnimationStart(animation)
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                        }
                    })
                    .setStartDelay(delay)
                    .start()
            }
            Fade.OUT -> {
                view.translationY = START_MOVE_ANIMATION
                view
                    .animate()
                    .alpha(FADE_OUT_ALPHA)
                    .translationY(BOTTOM_END_MOVE_ANIMATION)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator?) {
                            super.onAnimationStart(animation)
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            setOnAnimationEnd(view, changeVisibility)
                        }
                    })
                    .setStartDelay(delay)
                    .start()
            }
        }
    }

    internal fun setOnAnimationStart(view: View, changeVisibility: Boolean) {
        if (changeVisibility) {
            view.visibility = View.VISIBLE
        }
        view.isEnabled = true
    }

    internal fun setOnAnimationEnd(view: View, changeVisibility: Boolean) {
        if (changeVisibility) {
            view.visibility = View.GONE
        }
        view.isEnabled = false
    }
}
