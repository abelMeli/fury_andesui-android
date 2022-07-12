package com.mercadolibre.android.andesui.utils

import android.view.View
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
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

    @Test
    fun `test FadeOut with finish callback`() {
        var testBoolean = false
        view = View(context)
        AnimationsUtils.fadeOut(view, AnimationsUtils.Position.CENTER, 0L, 0L, false) {
            testBoolean = true
        }
        Assert.assertTrue(view.alpha <= 1f)
        testBoolean assertEquals true
    }

    @Test
    fun `test FadeOut with Change Visibility and finish callback`() {
        var testBoolean = false
        view = View(context)
        AnimationsUtils.fadeOut(view, AnimationsUtils.Position.CENTER, 0L, 0L, true) {
            testBoolean = true
        }
        Assert.assertTrue(view.alpha <= 1f)
        Assert.assertTrue(view.visibility == View.GONE)
        testBoolean assertEquals true
    }

    @Test
    fun `test FadeOut with Previous FadedOut and finish callback`() {
        var testBoolean = false
        view = View(context)
        view.alpha = 0f
        AnimationsUtils.fadeOut(view, AnimationsUtils.Position.CENTER, 0L, 0L, false) {
            testBoolean = true
        }
        Assert.assertTrue(view.alpha == 0f)
        testBoolean assertEquals false
    }

    @Test
    fun `test FadeOut with Center Position and finish callback`() {
        var testBoolean = false
        view = View(context)
        AnimationsUtils.fadeOut(view, AnimationsUtils.Position.CENTER, 0L, 0L, false) {
            testBoolean = true
        }
        Assert.assertTrue(view.alpha <= 1f)
        testBoolean assertEquals true
    }

    @Test
    fun `test FadeOut with Left Position and finish callback`() {
        var testBoolean = false
        view = View(context)
        AnimationsUtils.fadeOut(view, AnimationsUtils.Position.LEFT, 0L, 0L, false) {
            testBoolean = true
        }
        Assert.assertTrue(view.alpha <= 1f)
        Assert.assertTrue(view.translationX <= 0f)
        testBoolean assertEquals true
    }

    @Test
    fun `test FadeOut with Top Position and finish callback`() {
        var testBoolean = false
        view = View(context)
        AnimationsUtils.fadeOut(view, AnimationsUtils.Position.TOP, 0L, 0L, false) {
            testBoolean = true
        }
        Assert.assertTrue(view.alpha < 1f)
        Assert.assertTrue(view.translationY < 0f)
        testBoolean assertEquals true
    }

    @Test
    fun `test FadeOut with Right Position and finish callback`() {
        var testBoolean = false
        view = View(context)
        AnimationsUtils.fadeOut(view, AnimationsUtils.Position.RIGHT, 0L, 0L, false) {
            testBoolean = true
        }
        Assert.assertTrue(view.alpha <= 1f)
        Assert.assertTrue(view.translationX >= 0f)
        testBoolean assertEquals true
    }

    @Test
    fun `test FadeOut with Bottom Position and finish callback`() {
        var testBoolean = false
        view = View(context)
        AnimationsUtils.fadeOut(view, AnimationsUtils.Position.BOTTOM, 0L, 0L, false) {
            testBoolean = true
        }
        Assert.assertTrue(view.alpha < 1f)
        Assert.assertTrue(view.translationY > 0f)
        testBoolean assertEquals true
    }
}
