package com.mercadolibre.android.andesui.utils

import android.os.Build
import android.view.View
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AnimationsUtilsTest {

    private var context = RuntimeEnvironment.application
    private lateinit var view: View

    @Test
    fun testFadeOut() {
        view = View(context)
        AnimationsUtils.fadeOut(view, AnimationsUtils.Position.CENTER, 0L, 0L, false)
        Assert.assertTrue(view.alpha <= 1f)
    }

    @Test
    fun testFadeOut_withChangeVisibility() {
        view = View(context)
        AnimationsUtils.fadeOut(view, AnimationsUtils.Position.CENTER, 0L, 0L, true)
        Assert.assertTrue(view.alpha <= 1f)
        Assert.assertTrue(view.visibility == View.GONE)
    }

    @Test
    fun testFadeOut_withPreviousFadedOut() {
        view = View(context)
        view.alpha = 0f
        AnimationsUtils.fadeOut(view, AnimationsUtils.Position.CENTER, 0L, 0L, false)
        Assert.assertTrue(view.alpha == 0f)
    }

    @Test
    fun testFadeOut_withCenterPosition() {
        view = View(context)
        AnimationsUtils.fadeOut(view, AnimationsUtils.Position.CENTER, 0L, 0L, false)
        Assert.assertTrue(view.alpha <= 1f)
    }

    @Test
    fun testFadeOut_withLeftPosition() {
        view = View(context)
        AnimationsUtils.fadeOut(view, AnimationsUtils.Position.LEFT, 0L, 0L, false)
        Assert.assertTrue(view.alpha <= 1f)
        Assert.assertTrue(view.translationX <= 0f)
    }

    @Test
    fun testFadeOut_withTopPosition() {
        view = View(context)
        AnimationsUtils.fadeOut(view, AnimationsUtils.Position.TOP, 0L, 0L, false)
        Assert.assertTrue(view.alpha < 1f)
        Assert.assertTrue(view.translationY < 0f)
    }

    @Test
    fun testFadeOut_withRightPosition() {
        view = View(context)
        AnimationsUtils.fadeOut(view, AnimationsUtils.Position.RIGHT, 0L, 0L, false)
        Assert.assertTrue(view.alpha <= 1f)
        Assert.assertTrue(view.translationX >= 0f)
    }

    @Test
    fun testFadeOut_withBottomPosition() {
        view = View(context)
        AnimationsUtils.fadeOut(view, AnimationsUtils.Position.BOTTOM, 0L, 0L, false)
        Assert.assertTrue(view.alpha < 1f)
        Assert.assertTrue(view.translationY > 0f)
    }

    @Test
    fun testFadeIn() {
        view = View(context)
        view.alpha = 0f
        AnimationsUtils.fadeIn(view, AnimationsUtils.Position.CENTER, 0L, 0L, false)
        Assert.assertTrue(view.alpha > 0f)
    }

    @Test
    fun testFadeIn_withChangeVisibility() {
        view = View(context)
        view.alpha = 0f
        AnimationsUtils.fadeIn(view, AnimationsUtils.Position.CENTER, 0L, 0L, true)
        Assert.assertTrue(view.alpha >= 0f)
        Assert.assertTrue(view.visibility == View.VISIBLE)
    }

    @Test
    fun testFadeInPreviousFadeIn() {
        view = View(context)
        view.alpha = 1f
        AnimationsUtils.fadeIn(view, AnimationsUtils.Position.CENTER, 0L, 0L, false)
        Assert.assertTrue(view.alpha == 1f)
    }

    @Test
    fun testFadeIn_withCenterPosition() {
        view = View(context)
        view.alpha = 0f
        AnimationsUtils.fadeIn(view, AnimationsUtils.Position.CENTER, 0L, 0L, false)
        Assert.assertTrue(view.alpha >= 0f)
    }

    @Test
    fun testFadeIn_withLeftPosition() {
        view = View(context)
        view.alpha = 0f
        view.translationX = -100f
        AnimationsUtils.fadeIn(view, AnimationsUtils.Position.LEFT, 0L, 0L, false)
        Assert.assertTrue(view.alpha > 0f)
        Assert.assertTrue(view.translationX >= -100f)
    }

    @Test
    fun testFadeIn_withTopPosition() {
        view = View(context)
        view.alpha = 0f
        view.translationX = -100f
        AnimationsUtils.fadeIn(view, AnimationsUtils.Position.TOP, 0L, 0L, false)
        Assert.assertTrue(view.alpha > 0f)
        Assert.assertTrue(view.translationY > -100f)
    }

    @Test
    fun testFadeIn_withRightPosition() {
        view = View(context)
        view.alpha = 0f
        view.translationX = 100f
        AnimationsUtils.fadeIn(view, AnimationsUtils.Position.RIGHT, 0L, 0L, false)
        Assert.assertTrue(view.alpha > 0f)
        Assert.assertTrue(view.translationX <= 100f)
    }

    @Test
    fun testFadeIn_withBottomPosition() {
        view = View(context)
        view.alpha = 0f
        view.translationX = 100f
        AnimationsUtils.fadeIn(view, AnimationsUtils.Position.BOTTOM, 0L, 0L, false)
        Assert.assertTrue(view.alpha > 0f)
        Assert.assertTrue(view.translationY < 100f)
    }
}
