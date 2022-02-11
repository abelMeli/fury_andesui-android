package com.mercadolibre.android.andesui.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

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
    enum class Position { CENTER, TOP, LEFT, RIGHT, BOTTOM }

    private const val ALPHA_THRESHOLD = 0.5f

    private const val FADE_OUT_ALPHA = 0f
    private const val FADE_IN_SHOW_ALPHA = 1f

    private const val START_MOVE_ANIMATION = 0f

    private const val LEFT_END_MOVE_ANIMATION = -100f
    private const val RIGHT_END_MOVE_ANIMATION = 100f

    private const val TOP_END_MOVE_ANIMATION = -200f
    private const val BOTTOM_END_MOVE_ANIMATION = 100f

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
