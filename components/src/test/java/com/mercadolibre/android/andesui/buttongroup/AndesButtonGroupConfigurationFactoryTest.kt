package com.mercadolibre.android.andesui.buttongroup

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.test.core.app.ApplicationProvider
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.buttongroup.align.AndesButtonGroupAlign
import com.mercadolibre.android.andesui.buttongroup.factory.AndesButtonGroupAttrs
import com.mercadolibre.android.andesui.buttongroup.factory.AndesButtonGroupConfigurationFactory
import com.mercadolibre.android.andesui.buttongroup.type.AndesButtonGroupType
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesButtonGroupConfigurationFactoryTest {

    private lateinit var context: Context

    private lateinit var andesButtonGroup: AndesButtonGroup

    private lateinit var attrs: AndesButtonGroupAttrs

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.setTheme(R.style.Theme_AppCompat_Light)
        andesButtonGroup = AndesButtonGroup(context, null)

        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)

        setupActivityForTesting()
    }

    @Test
    fun `AndesButtonGroup with zero layoutWidth`() {
        andesButtonGroup.setButtons(listOf(AndesButton(context)))
        attrs = AndesButtonGroupAttrs()

        val config = AndesButtonGroupConfigurationFactory.create(
            context,
            attrs,
            andesButtonGroup
        )
        config.layoutWidth assertEquals NO_WIDTH
    }

    @Test
    fun `AndesButtonGroup with WRAP_CONTENT layoutWidth`() {
        andesButtonGroup.setButtons(listOf(AndesButton(context)))
        attrs = AndesButtonGroupAttrs(
            andesButtonGroupType = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.LEFT)
        )
        val config = AndesButtonGroupConfigurationFactory.create(
            context,
            attrs,
            andesButtonGroup
        )

        config.layoutWidth assertEquals ViewGroup.LayoutParams.WRAP_CONTENT
    }

    private fun setupActivityForTesting() {
        val robolectricActivity = Robolectric.buildActivity(Activity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(andesButtonGroup)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    companion object {
        private const val NO_WIDTH = 0
    }
}