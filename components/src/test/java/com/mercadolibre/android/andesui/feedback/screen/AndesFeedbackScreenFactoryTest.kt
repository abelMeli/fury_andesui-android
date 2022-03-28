package com.mercadolibre.android.andesui.feedback

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.feedback.screen.AndesFeedbackScreenFactory
import com.mercadolibre.android.andesui.feedback.screen.error.AndesFeedbackScreenErrorComponent
import com.mercadolibre.android.andesui.feedback.screen.error.AndesFeedbackScreenErrorData
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesFeedbackScreenFactoryTest {
    private val configFactory = Mockito.spy(AndesFeedbackScreenFactory)

    @Before
    fun setUp() {
        setUpFresco(RuntimeEnvironment.application)
    }

    @Test
    fun `attr create factory test`() {
        val view = configFactory.createAndesFeedbackScreenError(
            context = ApplicationProvider.getApplicationContext(),
            errorComponent = DummyClass()
        )
        val title: TextView = view.findViewById(R.id.andes_feedbackscreen_header_title)
        val description: TextView = view.findViewById(R.id.andes_feedbackscreen_header_description)
        val errorCode: TextView = view.findViewById(R.id.andes_feedbackscreen_header_error_code)


        Assert.assertEquals(title.text, "Title")
        Assert.assertEquals(description.text.toString(), "Description")
        Assert.assertEquals(errorCode.text.toString(), "Code: DFE01-1234567")
    }

    private fun setUpFresco(context: Context) {
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
            // other setters
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
    }

}

class DummyClass : AndesFeedbackScreenErrorComponent {
    override fun getFeedbackScreenErrorData(): AndesFeedbackScreenErrorData {
        return AndesFeedbackScreenErrorData(
            title = "Title",
            description = "Description",
            asset = null,
            button = null
        )
    }

    override fun getErrorCode(): String? {
        return "DFE01-1234567"
    }

    override fun onViewCreated(): () -> Unit {
        return { Log.d("Andes", "Track to melidata!") }
    }
}