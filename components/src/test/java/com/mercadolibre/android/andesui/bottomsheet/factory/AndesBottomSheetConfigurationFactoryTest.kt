package com.mercadolibre.android.andesui.bottomsheet.factory

import android.os.Build
import com.mercadolibre.android.andesui.bottomsheet.state.AndesBottomSheetState
import com.mercadolibre.android.andesui.bottomsheet.title.AndesBottomSheetTitleAlignment
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesBottomSheetConfigurationFactoryTest {

    private val configFactory = Mockito.spy(AndesBottomSheetConfigurationFactory)
    private lateinit var attrs: AndesBottomSheetAttrs

    @Test
    fun `attr create factory test`() {
        val peekHeight = 100
        val testText = "test"
        val halfExpanded = false
        attrs = AndesBottomSheetAttrs(
                andesBottomSheetPeekHeight = peekHeight,
                andesBottomSheetState = AndesBottomSheetState.EXPANDED,
                andesBottomSheetTitleAlignment = AndesBottomSheetTitleAlignment.CENTERED,
                andesBottomSheetFitContent = halfExpanded,
                andesBottomSheetTitleText = testText
        )

        val config = configFactory.create(attrs)

        assertEquals(config.peekHeight, peekHeight)
        assertEquals(config.state, AndesBottomSheetState.EXPANDED)
        assertEquals(config.titleAlignment, AndesBottomSheetTitleAlignment.CENTERED)
        assertEquals(config.fitContent, halfExpanded)
        assertEquals(config.titleText, testText)
    }
}
