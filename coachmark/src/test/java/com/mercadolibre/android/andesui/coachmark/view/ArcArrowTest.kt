package com.mercadolibre.android.andesui.coachmark.view

import com.mercadolibre.android.andesui.coachmark.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.coachmark.view.walkthroughmessage.ArcArrow
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class ArcArrowTest {

    private var context = RuntimeEnvironment.application

    @Test
    fun `addRect - setTopLeftArrow`() {
        val arcArrow = ArcArrow(context)

        arcArrow.addRect(540, 144, 233, 0)

        assertEquals(arcArrow.startHeadArrowX, 233F)
        assertEquals(arcArrow.startHeadArrowY, 96F)

        assertEquals(arcArrow.endLeftX, 231.25998F)
        assertEquals(arcArrow.endLeftY, 107.19998F)
        assertEquals(arcArrow.endRightX, 240.65997F)
        assertEquals(arcArrow.endRightY, 103.77002F)

        assertEquals(arcArrow.linePosition[0], 257F)
        assertEquals(arcArrow.linePosition[1], 120F)
        assertEquals(arcArrow.linePosition[2], 516F)
        assertEquals(arcArrow.linePosition[3], 120F)

        assertEquals(arcArrow.rectTargetF.bottom, 120F)
        assertEquals(arcArrow.rectTargetF.left, 233F)
        assertEquals(arcArrow.rectTargetF.right, 281F)
        assertEquals(arcArrow.rectTargetF.top, 72F)

        assertEquals(arcArrow.rectTooltipF.bottom, 168F)
        assertEquals(arcArrow.rectTooltipF.left, 492F)
        assertEquals(arcArrow.rectTooltipF.right, 540F)
        assertEquals(arcArrow.rectTooltipF.top, 120F)

        assertEquals(arcArrow.startTargetAngle, 90F)
        assertEquals(arcArrow.startTooltipAngle, 270F)
    }

    @Test
    fun `addRect - setTopRightArrow`() {
        val arcArrow = ArcArrow(context)

        arcArrow.addRect(540, 144, 852, 0)

        assertEquals(arcArrow.startHeadArrowX, 852F)
        assertEquals(arcArrow.startHeadArrowY, 96F)

        assertEquals(arcArrow.endLeftX, 844.34F)
        assertEquals(arcArrow.endLeftY, 103.77002F)
        assertEquals(arcArrow.endRightX, 853.74F)
        assertEquals(arcArrow.endRightY, 107.19998F)

        assertEquals(arcArrow.linePosition[0], 564F)
        assertEquals(arcArrow.linePosition[1], 120F)
        assertEquals(arcArrow.linePosition[2], 828F)
        assertEquals(arcArrow.linePosition[3], 120F)

        assertEquals(arcArrow.rectTargetF.bottom, 120F)
        assertEquals(arcArrow.rectTargetF.left, 804F)
        assertEquals(arcArrow.rectTargetF.right, 852F)
        assertEquals(arcArrow.rectTargetF.top, 72F)

        assertEquals(arcArrow.rectTooltipF.bottom, 168F)
        assertEquals(arcArrow.rectTooltipF.left, 540F)
        assertEquals(arcArrow.rectTooltipF.right, 588F)
        assertEquals(arcArrow.rectTooltipF.top, 120F)

        assertEquals(arcArrow.startTargetAngle, 0F)
        assertEquals(arcArrow.startTooltipAngle, 180F)
    }

    @Test
    fun `addRect - setBottomRightArrow`() {
        val arcArrow = ArcArrow(context)

        arcArrow.addRect(540, 0, 852, 144)

        assertEquals(arcArrow.startHeadArrowX, 852F)
        assertEquals(arcArrow.startHeadArrowY, 48F)

        assertEquals(arcArrow.endLeftX, 844.34F)
        assertEquals(arcArrow.endLeftY, 40.22998F)
        assertEquals(arcArrow.endRightX, 853.74F)
        assertEquals(arcArrow.endRightY, 36.80002F)

        assertEquals(arcArrow.linePosition[0], 564F)
        assertEquals(arcArrow.linePosition[1], 24F)
        assertEquals(arcArrow.linePosition[2], 828F)
        assertEquals(arcArrow.linePosition[3], 24F)

        assertEquals(arcArrow.rectTargetF.bottom, 72F)
        assertEquals(arcArrow.rectTargetF.left, 804F)
        assertEquals(arcArrow.rectTargetF.right, 852F)
        assertEquals(arcArrow.rectTargetF.top, 24F)

        assertEquals(arcArrow.rectTooltipF.bottom, 24F)
        assertEquals(arcArrow.rectTooltipF.left, 540F)
        assertEquals(arcArrow.rectTooltipF.right, 588F)
        assertEquals(arcArrow.rectTooltipF.top, -24F)

        assertEquals(arcArrow.startTargetAngle, 270F)
        assertEquals(arcArrow.startTooltipAngle, 90F)
    }

    @Test
    fun `addRect - setBottomLeftArrow`() {
        val arcArrow = ArcArrow(context)

        arcArrow.addRect(540, 0, 228, 144)

        assertEquals(arcArrow.startHeadArrowX, 228F)
        assertEquals(arcArrow.startHeadArrowY, 48F)

        assertEquals(arcArrow.endLeftX, 226.25998F)
        assertEquals(arcArrow.endLeftY, 36.80002F)
        assertEquals(arcArrow.endRightX, 235.65997F)
        assertEquals(arcArrow.endRightY, 40.22998F)

        assertEquals(arcArrow.linePosition[0], 252F)
        assertEquals(arcArrow.linePosition[1], 24F)
        assertEquals(arcArrow.linePosition[2], 516F)
        assertEquals(arcArrow.linePosition[3], 24F)

        assertEquals(arcArrow.rectTargetF.bottom, 72F)
        assertEquals(arcArrow.rectTargetF.left, 228F)
        assertEquals(arcArrow.rectTargetF.right, 276F)
        assertEquals(arcArrow.rectTargetF.top, 24F)

        assertEquals(arcArrow.rectTooltipF.bottom, 24F)
        assertEquals(arcArrow.rectTooltipF.left, 492F)
        assertEquals(arcArrow.rectTooltipF.right, 540F)
        assertEquals(arcArrow.rectTooltipF.top, -24F)

        assertEquals(arcArrow.startTargetAngle, 180F)
        assertEquals(arcArrow.startTooltipAngle, 0F)
    }
}
