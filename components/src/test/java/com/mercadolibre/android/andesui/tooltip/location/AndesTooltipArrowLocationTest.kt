package com.mercadolibre.android.andesui.tooltip.location

import android.os.Build
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(ParameterizedRobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesTooltipArrowLocationTest(
    private val tooltipSideId: ArrowPositionId,
    private val positionInSideId: ArrowPositionId,
    private val wantedInstance: AndesTooltipArrowLocation
) {
    private val tooltipMeasures = AndesTooltipLocationInterfaceImplTest

    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters
        fun data(): Collection<Array<Any?>> {
            return listOf(
                    arrowBottomLeft,
                    arrowBottomMiddle,
                    arrowBottomRight,
                    arrowBottomFree,
                    arrowTopLeft,
                    arrowTopMiddle,
                    arrowTopRight,
                    arrowTopFree,
                    arrowRightTop,
                    arrowRightMiddle,
                    arrowRightBottom,
                    arrowLeftTop,
                    arrowLeftMiddle,
                    arrowLeftBottom
            )
        }
    }

    @Test
    fun `should get required arrowLocation instance`() {
        val obtainedInstance = getAndesTooltipArrowLocation(tooltipSideId, positionInSideId)
        assertEquals(wantedInstance, obtainedInstance)
    }
    @Test
    fun `should obtain the same arrowPositionX and arrowPositionY`() {
        val obtainedInstance = getAndesTooltipArrowLocation(tooltipSideId, positionInSideId)
        assertEquals(wantedInstance.getArrowPositionX(tooltipMeasures), obtainedInstance.getArrowPositionX(tooltipMeasures))
        assertEquals(wantedInstance.getArrowPositionY(tooltipMeasures), obtainedInstance.getArrowPositionY(tooltipMeasures))
    }
}
