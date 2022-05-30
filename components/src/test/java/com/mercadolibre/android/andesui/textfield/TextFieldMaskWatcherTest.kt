package com.mercadolibre.android.andesui.textfield

import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.textfield.maskTextField.TextFieldMaskWatcher
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class TextFieldMaskWatcherTest {

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            SoLoader.setInTestMode()
        }
    }

    @Test
    fun `clean mask`() {
        val maskWatcher = TextFieldMaskWatcher("##/-##-,##", null)
        Assert.assertEquals(maskWatcher.cleanMask("44--55--66"), "445566")
    }

    @Test
    fun `max size text without mask`() {
        val maskWatcher = TextFieldMaskWatcher("##--##-,##", null)
        Assert.assertEquals(maskWatcher.getLengthWithoutMask(), 6)
    }

    @Test
    fun `max size text with mask`() {
        val maskWatcher = TextFieldMaskWatcher("##--##-,##", null)
        Assert.assertEquals(maskWatcher.getMaxLength(), 10)
    }

    @Test
    fun `get text with mask`() {
        val maskWatcher = TextFieldMaskWatcher("(##) ####-####", null)
        Assert.assertEquals(maskWatcher.getTextWithMask("1149778767"), "(11) 4977-8767")
    }

    @Test
    fun `get text with mask incomplete`() {
        val maskWatcher = TextFieldMaskWatcher("(##) ####-####", null)
        Assert.assertEquals(maskWatcher.getTextWithMask("114977"), "(11) 4977-")
    }
}
