package com.mercadolibre.android.andesui.textfield

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldRightContent
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesTextfieldA11YTest {

    private lateinit var context: Context
    private lateinit var textfield: AndesTextfield

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        SoLoader.setInTestMode()
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
                // other setters
                .setRequestListeners(requestListeners)
                .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
    }

    @Test
    fun `right content clear contentDescription`() {
        textfield = AndesTextfield(context)
        textfield.rightContent = AndesTextfieldRightContent.CLEAR
        val description = context.getString(R.string.andes_textfield_right_content_clear)
        assertEquals(description, (textfield.rightContent!!.rightContent.component(context).contentDescription))
    }
}
