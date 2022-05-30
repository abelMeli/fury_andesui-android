package com.mercadolibre.android.andesui.coachmark.view

import android.graphics.Rect
import com.mercadolibre.android.andesui.coachmark.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class CoachmarkOverlayTest {

    private var context = RuntimeEnvironment.application

    @Test
    fun `addRectTest - Circle`() {
        val targetRect = Rect(228, 1052, 418, 361)

        val coachmarkOverlay = CoachmarkOverlay(context)
        coachmarkOverlay.addRect(targetRect.left, targetRect.top - 240, 0, 0, true, 216F)

        assertEquals(coachmarkOverlay.rectF[0].top, 800F)
        assertEquals(coachmarkOverlay.rectF[0].bottom, 824F)
        assertEquals(coachmarkOverlay.rectF[0].right, 240F)
        assertEquals(coachmarkOverlay.rectF[0].left, 216F)
        assertEquals(coachmarkOverlay.radius, 216F)
        assertEquals(coachmarkOverlay.isCircle, true)
    }

    @Test
    fun `addRectTest - Rectangle`() {
        val targetRect = Rect(48, 288, 418, 361)

        val coachmarkOverlay = CoachmarkOverlay(context)
        coachmarkOverlay.addRect(targetRect.left, targetRect.top - 240, targetRect.width(), targetRect.height(), false)

        assertEquals(coachmarkOverlay.rectF[0].top, 36F)
        assertEquals(coachmarkOverlay.rectF[0].bottom, 133F)
        assertEquals(coachmarkOverlay.rectF[0].right, 430F)
        assertEquals(coachmarkOverlay.rectF[0].left, 36F)
        assertEquals(coachmarkOverlay.radius, 8F)
        assertEquals(coachmarkOverlay.isCircle, false)
    }
}
