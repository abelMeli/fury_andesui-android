package com.mercadolibre.android.andesui.inputstepper.factory

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.inputstepper.size.AndesInputStepperSize
import com.mercadolibre.android.andesui.inputstepper.status.AndesInputStepperStatus
import com.mercadolibre.android.andesui.utils.Constants
import com.mercadolibre.android.andesui.utils.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Constants.TEST_ANDROID_VERSION_CODE])
class AndesInputStepperAttrsParserTest {

    private lateinit var context: Context
    private val attrsParser = AndesInputStepperAttrsParser

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `validate build step with xml attribute`() {
        val expected = 3
        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesInputStepperStep, expected.toString())
            .build()

        val inputStepperAttrs = attrsParser.parse(context, attrs)

        inputStepperAttrs.step assertEquals expected
    }

    @Test
    fun `validate build value with xml attribute`() {
        val expected = 3
        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesInputStepperValue, expected.toString())
            .build()

        val inputStepperAttrs = attrsParser.parse(context, attrs)

        inputStepperAttrs.value assertEquals expected
    }

    @Test
    fun `validate build min with xml attribute`() {
        val expected = 3
        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesInputStepperMinValue, expected.toString())
            .build()

        val inputStepperAttrs = attrsParser.parse(context, attrs)

        inputStepperAttrs.minValue assertEquals expected
    }


    @Test
    fun `validate build max with xml attribute`() {
        val expected = 3
        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesInputStepperMaxValue, expected.toString())
            .build()

        val inputStepperAttrs = attrsParser.parse(context, attrs)

        inputStepperAttrs.maxValue assertEquals expected
    }

    @Test
    fun `validate build size small with xml attribute`() {
        val smallCode = "1001"
        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesInputStepperSize, smallCode)
            .build()

        val inputStepperAttrs = attrsParser.parse(context, attrs)

        inputStepperAttrs.size assertEquals AndesInputStepperSize.Small
    }

    @Test
    fun `validate build size large with xml attribute`() {
        val largeCode = "1000"
        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesInputStepperSize, largeCode)
            .build()

        val inputStepperAttrs = attrsParser.parse(context, attrs)

        inputStepperAttrs.size assertEquals AndesInputStepperSize.Large
    }

    @Test
    fun `validate build status disabled with xml attribute`() {
        val disabledCode = "2001"
        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesInputStepperStatus, disabledCode)
            .build()

        val inputStepperAttrs = attrsParser.parse(context, attrs)

        inputStepperAttrs.status assertEquals AndesInputStepperStatus.Disabled
    }

    @Test
    fun `validate build status loading with xml attribute`() {
        val loadingCode = "2002"
        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesInputStepperStatus, loadingCode)
            .build()

        val inputStepperAttrs = attrsParser.parse(context, attrs)

        inputStepperAttrs.status assertEquals AndesInputStepperStatus.Loading
    }
}