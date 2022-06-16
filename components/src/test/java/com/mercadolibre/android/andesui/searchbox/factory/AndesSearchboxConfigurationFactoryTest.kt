package com.mercadolibre.android.andesui.searchbox.factory

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.typeface.getFontOrDefault
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesSearchboxConfigurationFactoryTest {

    private lateinit var context: Context
    private val configFactory = AndesSearchboxConfigurationFactory
    private lateinit var attrs: AndesSearchboxAttrs
    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `placeholder`() {
        attrs = AndesSearchboxAttrs("search")
        val config = configFactory.create(context, attrs)
        config.placeHolderText assertEquals "search"
    }

    @Test
    fun `typeface`() {
        attrs = AndesSearchboxAttrs("search")
        val config = configFactory.create(context, attrs)
        config.typeface assertEquals context.getFontOrDefault(R.font.andes_font_regular)
    }
}