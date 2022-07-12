package com.mercadolibre.android.andesui.modal.full.factory

import android.content.Context
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
class AndesModalFullDefaultConfigTest {

    private lateinit var config: AndesModalFullDefaultConfig
    private lateinit var context: Context
    private var arguments = AndesModalFullDefaultFragmentArguments()

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
        config = AndesModalFullDefaultConfigFactory.create(arguments)

        config.isButtonGroupFixed assertEquals false
        config.isHeaderFixed assertEquals false
        config.buttonGroupCreator assertIsNull true
        config.contentVariation assertEquals AndesModalFullContentVariation.NONE
        config.content assertIsNull true
        config.isDismissible assertEquals true
        config.onDismissCallback assertIsNull true
        config.onModalShowCallback assertIsNull true
        config.contentVariation.variation.getVerticalBias() assertEquals VERTICAL_TOP_VALUE_BIAS
        config.headerType assertEquals AndesModalFullHeaderType.TITLE_CLOSE
        config.headerTextStatus assertEquals AndesModalFullHeaderStatus.EXPANDED
    }

    @Test
    fun `dismissible false, buttongroupfixed true, buttongroupcreator, contentvariation image, content, headerfixed true, dismiss callback, show callback `() {
        val callback = { /*no-op*/ }
        val buttonGroupCreator = provideButtonGroupCreator(context)
        val content = provideContent(context)
        arguments = AndesModalFullDefaultFragmentArguments(
            isDismissible = false,
            isButtonGroupFixed = true,
            buttonGroupCreator = buttonGroupCreator,
            onDismissCallback = callback,
            onModalShowCallback = callback,
            contentVariation = AndesModalFullContentVariation.SMALL_ILLUSTRATION,
            isHeaderFixed = true,
            content = content
        )

        config = AndesModalFullDefaultConfigFactory.create(arguments)

        config.isButtonGroupFixed assertEquals true
        config.isHeaderFixed assertEquals true
        config.buttonGroupCreator assertEquals buttonGroupCreator
        config.contentVariation assertEquals AndesModalFullContentVariation.SMALL_ILLUSTRATION
        config.content assertEquals content
        config.isDismissible assertEquals false
        config.onDismissCallback assertEquals callback
        config.onModalShowCallback assertEquals callback
        config.contentVariation.variation.getVerticalBias() assertEquals DEFAULT_VALUE_BIAS
        config.headerType assertEquals AndesModalFullHeaderType.HEADER_NONE
        config.headerTextStatus assertEquals AndesModalFullHeaderStatus.COLLAPSED
    }

    @Test
    fun `dismissible false, buttongroupfixed false, buttongroupcreator, contentvariation none, content, headerfixed true, dismiss callback, show callback `() {
        val callback = { /*no-op*/ }
        val buttonGroupCreator = provideButtonGroupCreator(context)
        val content = provideContent(context)
        arguments = AndesModalFullDefaultFragmentArguments(
            isDismissible = false,
            isButtonGroupFixed = false,
            buttonGroupCreator = buttonGroupCreator,
            onDismissCallback = callback,
            onModalShowCallback = callback,
            contentVariation = AndesModalFullContentVariation.NONE,
            isHeaderFixed = true,
            content = content
        )

        config = AndesModalFullDefaultConfigFactory.create(arguments)

        config.isButtonGroupFixed assertEquals false
        config.isHeaderFixed assertEquals true
        config.buttonGroupCreator assertEquals buttonGroupCreator
        config.contentVariation assertEquals AndesModalFullContentVariation.NONE
        config.content assertEquals content
        config.isDismissible assertEquals false
        config.onDismissCallback assertEquals callback
        config.onModalShowCallback assertEquals callback
        config.contentVariation.variation.getVerticalBias() assertEquals VERTICAL_TOP_VALUE_BIAS
        config.headerType assertEquals AndesModalFullHeaderType.ONLY_TITLE
        config.headerTextStatus assertEquals AndesModalFullHeaderStatus.EXPANDED
    }

    @Test
    fun `dismissible false, buttongroupfixed false, buttongroupcreator, contentvariation large illustration, content, headerfixed true, dismiss callback, show callback `() {
        val callback = { /*no-op*/ }
        val buttonGroupCreator = provideButtonGroupCreator(context)
        val content = provideContent(context)
        arguments = AndesModalFullDefaultFragmentArguments(
            isDismissible = false,
            isButtonGroupFixed = false,
            buttonGroupCreator = buttonGroupCreator,
            onDismissCallback = callback,
            onModalShowCallback = callback,
            contentVariation = AndesModalFullContentVariation.LARGE_ILLUSTRATION,
            isHeaderFixed = true,
            content = content
        )

        config = AndesModalFullDefaultConfigFactory.create(arguments)

        config.isButtonGroupFixed assertEquals false
        config.isHeaderFixed assertEquals true
        config.buttonGroupCreator assertEquals buttonGroupCreator
        config.contentVariation assertEquals AndesModalFullContentVariation.LARGE_ILLUSTRATION
        config.content assertEquals content
        config.isDismissible assertEquals false
        config.onDismissCallback assertEquals callback
        config.onModalShowCallback assertEquals callback
        config.contentVariation.variation.getVerticalBias() assertEquals DEFAULT_VALUE_BIAS
        config.headerType assertEquals AndesModalFullHeaderType.HEADER_NONE
        config.headerTextStatus assertEquals AndesModalFullHeaderStatus.COLLAPSED
    }

    @Test
    fun `dismissible false, buttongroupfixed false, buttongroupcreator, contentvariation thumbnail, content, headerfixed true, dismiss callback, show callback `() {
        val callback = { /*no-op*/ }
        val buttonGroupCreator = provideButtonGroupCreator(context)
        val content = provideContent(context)
        arguments = AndesModalFullDefaultFragmentArguments(
            isDismissible = false,
            isButtonGroupFixed = false,
            buttonGroupCreator = buttonGroupCreator,
            onDismissCallback = callback,
            onModalShowCallback = callback,
            contentVariation = AndesModalFullContentVariation.THUMBNAIL,
            isHeaderFixed = true,
            content = content
        )

        config = AndesModalFullDefaultConfigFactory.create(arguments)

        config.isButtonGroupFixed assertEquals false
        config.isHeaderFixed assertEquals true
        config.buttonGroupCreator assertEquals buttonGroupCreator
        config.contentVariation assertEquals AndesModalFullContentVariation.THUMBNAIL
        config.content assertEquals content
        config.isDismissible assertEquals false
        config.onDismissCallback assertEquals callback
        config.onModalShowCallback assertEquals callback
        config.contentVariation.variation.getVerticalBias() assertEquals DEFAULT_VALUE_BIAS
        config.headerType assertEquals AndesModalFullHeaderType.HEADER_NONE
        config.headerTextStatus assertEquals AndesModalFullHeaderStatus.COLLAPSED
    }
}
