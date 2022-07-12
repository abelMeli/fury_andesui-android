package com.mercadolibre.android.andesui.modal.full.factory

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
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalFullContentVariation
import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalContentVariationInterface.Companion.DEFAULT_VALUE_BIAS
import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalContentVariationInterface.Companion.VERTICAL_TOP_VALUE_BIAS
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderStatus
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderType
import com.mercadolibre.android.andesui.utils.Constants
import com.mercadolibre.android.andesui.utils.provideAndesModalInterface
import com.mercadolibre.android.andesui.utils.provideButtonGroupCreator
import com.mercadolibre.android.andesui.utils.provideContent
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Constants.TEST_ANDROID_VERSION_CODE])
class AndesModalFullCarouselConfigFactoryTest {

    private lateinit var config: AndesModalFullCarouselConfig
    private lateinit var context: Context
    private var arguments = AndesModalFullCarouselFragmentArguments()

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
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
            // other setters
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
    }

    @Test
    fun `Create AndesModalFullConfig whith null arguments`() {
        config = AndesModalFullCarouselConfigFactory.create(arguments, provideAndesModalInterface())

        config.isDismissible assertEquals true
        config.closeButtonVisibility assertEquals View.VISIBLE
        config.buttonGroup assertIsNull true
        config.mainAction assertIsNull true
        config.contentList assertIsNull true
        config.contentVariation assertEquals AndesModalFullContentVariation.NONE
        config.isHeaderFixed assertEquals false
        config.onDismissCallback assertIsNull true
        config.onModalShowCallback assertIsNull true
        config.onPageSelectedCallback assertIsNull true
        config.headerType assertEquals AndesModalFullHeaderType.TITLE_CLOSE
        config.headerTextStatus assertEquals AndesModalFullHeaderStatus.EXPANDED
        config.isTitleTextCentered assertEquals false
        config.contentVariation.variation.getVerticalBias() assertEquals VERTICAL_TOP_VALUE_BIAS
    }

    @Test
    fun `Modal full Carousel Config with, dismissible false, buttongroupfixed true, buttongroupcreator, contentvariation image, content list, headerfixed true, dismiss callback, show callback `() {
        val callback = { /*no-op*/ }
        val buttonGroupCreator = provideButtonGroupCreator(context, true)
        val contentList = arrayListOf(provideContent(context))
        val pageCallback = { _: Int -> }
        arguments = AndesModalFullCarouselFragmentArguments(
            false,
            buttonGroupCreator,
            callback,
            callback,
            AndesModalFullContentVariation.SMALL_ILLUSTRATION,
            true,
            pageCallback,
            contentList
        )

        config = AndesModalFullCarouselConfigFactory.create(arguments, provideAndesModalInterface())

        config.isDismissible assertEquals false
        config.closeButtonVisibility assertEquals View.GONE
        config.buttonGroup assertIsNull false
        config.mainAction assertEquals 0
        config.contentList assertEquals contentList
        config.contentVariation assertEquals AndesModalFullContentVariation.SMALL_ILLUSTRATION
        config.isHeaderFixed assertEquals true
        config.onDismissCallback assertEquals callback
        config.onModalShowCallback assertEquals callback
        config.onPageSelectedCallback assertEquals pageCallback
        config.headerType assertEquals AndesModalFullHeaderType.HEADER_NONE
        config.headerTextStatus assertEquals AndesModalFullHeaderStatus.COLLAPSED
        config.isTitleTextCentered assertEquals true
        config.contentVariation.variation.getVerticalBias() assertEquals DEFAULT_VALUE_BIAS
    }
}
