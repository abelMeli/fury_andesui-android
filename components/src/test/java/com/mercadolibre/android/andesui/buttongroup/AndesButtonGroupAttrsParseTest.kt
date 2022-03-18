package com.mercadolibre.android.andesui.buttongroup

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistribution
import com.mercadolibre.android.andesui.buttongroup.factory.AndesButtonGroupAttrsParser
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesButtonGroupAttrsParseTest {

    private lateinit var context: Context
    private val attrsParser = AndesButtonGroupAttrsParser

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `AndesButtonGroup, check Fullwidth SIZE have an xml attribute`() {
        val FULL_WIDTH = "1000"
        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesButtonGroupType, FULL_WIDTH)
            .build()

        val buttonGroupAttrs = attrsParser.parse(context, attrs)
        buttonGroupAttrs.andesButtonGroupType assertEquals buttonGroupAttrs.andesButtonGroupType
    }

    @Test
    fun `AndesButtonGroup, All enum DISTRIBUTION have an xml attribute`() {
        val enumToAttrs =
            AndesButtonGroupDistribution.values().associateBy { distribution -> distribution.name.toLowerCase() }
        enumToAttrs.forEach { entry ->
            val attrs = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.andesButtonGroupDistribution, entry.key)
                .build()
            val andesButtonGroupAttrs = attrsParser.parse(context, attrs)
            andesButtonGroupAttrs.andesButtonGroupDistribution assertEquals entry.value
        }
    }
}