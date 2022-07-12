package com.mercadolibre.android.andesui.modal.card.configfactory

import android.content.Context
import android.view.View
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.assertIsNull
import com.mercadolibre.android.andesui.modal.card.corners.AndesModalCorners
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalCardContentVariation
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.provideAndesModalInterface
import com.mercadolibre.android.andesui.utils.provideButtonGroupCreator
import com.mercadolibre.android.andesui.utils.provideContent
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesModalCardCarouselConfigTest {

    private lateinit var config: AndesModalCardCarouselConfig
    private var arguments = AndesModalCardCarouselFragmentArguments()
    private lateinit var context: Context

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            SoLoader.setInTestMode()
        }
    }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        setupFresco()
    }

    private fun setupFresco() {
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
    }

    @Test
    fun `creating config with null arguments`() {
        config = AndesModalCardCarouselConfigFactory.create(arguments, provideAndesModalInterface())

        Assertions.assertThat(config.scrollViewOutlineProvider).usingRecursiveComparison()
            .isEqualTo(AndesModalCorners.ALL_CORNERS.corners.getOutlineProvider())
        config.isDismissible assertEquals true
        config.closeButtonVisibility assertEquals View.VISIBLE
        config.buttonGroup assertIsNull true
        config.contentList assertIsNull true
        config.mainAction assertIsNull true
        config.contentVariation assertEquals AndesModalCardContentVariation.NONE
        config.isHeaderFixed assertEquals false
        config.onDismissCallback assertIsNull true
        config.onModalShowCallback assertIsNull true
        config.onPageSelectedCallback assertIsNull true
    }

    @Test
    fun `dismissible false, buttongroupcreator, contentlist, contentvariation image, headerfixed true, dismiss + show + pageselected callbacks`() {
        val callback = { /*no-op*/ }
        val pageSelectedCallback = { _: Int -> }
        val buttonGroupCreator = provideButtonGroupCreator(context, true)
        val contentList = arrayListOf(provideContent(context))

        arguments = AndesModalCardCarouselFragmentArguments(
            false,
            buttonGroupCreator,
            callback,
            callback,
            AndesModalCardContentVariation.IMAGE,
            true,
            pageSelectedCallback, contentList
        )

        config = AndesModalCardCarouselConfigFactory.create(arguments, provideAndesModalInterface())

        Assertions.assertThat(config.scrollViewOutlineProvider).usingRecursiveComparison()
            .isEqualTo(AndesModalCorners.ALL_CORNERS.corners.getOutlineProvider())
        config.isDismissible assertEquals false
        config.closeButtonVisibility assertEquals View.GONE
        config.buttonGroup assertIsNull false
        config.contentList assertEquals contentList
        config.mainAction assertEquals 0
        config.contentVariation assertEquals AndesModalCardContentVariation.IMAGE
        config.isHeaderFixed assertEquals true
        config.onDismissCallback assertEquals callback
        config.onModalShowCallback assertEquals callback
        config.onPageSelectedCallback assertEquals pageSelectedCallback
    }
}
