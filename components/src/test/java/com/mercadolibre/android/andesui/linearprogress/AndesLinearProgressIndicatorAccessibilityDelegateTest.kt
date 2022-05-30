package com.mercadolibre.android.andesui.linearprogress

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesLinearProgressIndicatorAccessibilityDelegateTest {

    private lateinit var context: Context
    private lateinit var linearProgress: AndesLinearProgressIndicatorDeterminate

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        SoLoader.setInTestMode()
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
        linearProgress = AndesLinearProgressIndicatorDeterminate(context)
        setupActivityForTest()
    }

    private fun setupActivityForTest() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(linearProgress)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `Linear Progress accessibility Step 0 of 10`() {
        val currentStep = linearProgress.currentStep
        val totalSteps = linearProgress.numberOfSteps
        val textStep = context.resources.getString(R.string.andes_linear_progress_step)
        val textOf = context.resources.getString(R.string.andes_linear_progress_of)
        val expected = "$textStep $currentStep $textOf $totalSteps"
        val nodeInfo = linearProgress.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals(expected, nodeInfo.contentDescription)
    }

    @Test
    fun `Linear Progress accessibility Step 3 of 10`() {
        linearProgress.jumpToStep(3)
        val currentStep = linearProgress.currentStep
        val totalSteps = linearProgress.numberOfSteps
        val textStep = context.resources.getString(R.string.andes_linear_progress_step)
        val textOf = context.resources.getString(R.string.andes_linear_progress_of)
        val expected = "$textStep $currentStep $textOf $totalSteps"
        val nodeInfo = linearProgress.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals(expected, nodeInfo.contentDescription)
    }
}
