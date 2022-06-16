package com.mercadolibre.android.andesui.searchbox.factory

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.Constants
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.buildAttributeSet
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Constants.TEST_ANDROID_VERSION_CODE])
class AndesSearchboxAttrsParserTest {

    private lateinit var context: Context
    private lateinit var attrs: AndesSearchboxAttrs

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `parse with nothing set in the xml`() {
        val attrSet = buildAttributeSet { }
        val placeholder = context.resources.getString(R.string.andes_searchbox_placeholder_default)
        attrs = AndesSearchboxAttrsParser.parse(context, attrSet)
        attrs.placeholder assertEquals placeholder
    }

    @Test
    fun `only setting placeholder`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesSearchboxPlaceholder,"search")
        }
        attrs = AndesSearchboxAttrsParser.parse(context, attrSet)
        attrs.placeholder assertEquals "search"
    }
}