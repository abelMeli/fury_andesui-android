package com.mercadolibre.android.andesui.slider

import com.mercadolibre.android.andesui.slider.utils.AndesSliderUtils
import com.mercadolibre.android.andesui.utils.assertEquals
import org.junit.Test

class AndesSliderUtilsTest {

    @Test
    fun `AndesSliderUtils, check Step From when min is ZERO`() {
        AndesSliderUtils.getStepFrom(0f) assertEquals 0
    }

    @Test
    fun `AndesSliderUtils, check Step From when min not is ZERO`() {
        AndesSliderUtils.getStepFrom(20f) assertEquals 1
    }

    @Test
    fun `AndesSliderUtils, check Current Step when min is ZERO`() {
        AndesSliderUtils.getCurrentStep(2, 0f, 22f) assertEquals 44f
    }

    @Test
    fun `AndesSliderUtils, check Current Step when min not is ZERO`() {
        AndesSliderUtils.getCurrentStep(2, 20f, 22f) assertEquals 64f
    }
}
