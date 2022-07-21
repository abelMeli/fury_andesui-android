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
import com.mercadolibre.android.andesui.utils.Constants
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
@Config(sdk = [Constants.TEST_ANDROID_VERSION_CODE])
class AndesModalCardDefaultConfigTest {

    private lateinit var config: AndesModalCardDefaultConfig
    private var arguments = AndesModalCardDefaultFragmentArguments()
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
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
            // other setters
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
    }

    @Test
    fun `creating config with null arguments`() {
        config = AndesModalCardDefaultConfigFactory.create(arguments)

        config.isButtonGroupFixed assertEquals false
        Assertions.assertThat(config.scrollViewOutlineProvider).usingRecursiveComparison()
            .isEqualTo(AndesModalCorners.ALL_CORNERS.corners.getOutlineProvider())
        config.isHeaderFixed assertEquals false
        config.buttonGroupCreator assertIsNull true
        config.contentVariation assertEquals AndesModalCardContentVariation.NONE
        config.content assertIsNull true
        config.isDismissible assertEquals true
        config.closeButtonVisibility assertEquals View.VISIBLE
        config.onDismissCallback assertIsNull true
        config.onModalShowCallback assertIsNull true
    }

    @Test
    fun `dismissible false, buttongroupfixed true, buttongroupcreator, contentvariation image, content, headerfixed true, dismiss callback, show callback `() {
        val callback = { /*no-op*/ }
        val buttonGroupCreator = provideButtonGroupCreator(context)
        val content = provideContent(context)
        arguments = AndesModalCardDefaultFragmentArguments(
            isDismissible = false,
            isButtonGroupFixed = true,
            buttonGroupCreator = buttonGroupCreator,
            onDismissCallback = callback,
            onModalShowCallback = callback,
            contentVariation = AndesModalCardContentVariation.IMAGE,
            isHeaderFixed = true,
            content = content
        )

        config = AndesModalCardDefaultConfigFactory.create(arguments)

        config.isButtonGroupFixed assertEquals true
        Assertions.assertThat(config.scrollViewOutlineProvider).usingRecursiveComparison()
            .isEqualTo(AndesModalCorners.TOP_CORNERS.corners.getOutlineProvider())
        config.isHeaderFixed assertEquals true
        config.buttonGroupCreator assertEquals buttonGroupCreator
        config.contentVariation assertEquals AndesModalCardContentVariation.IMAGE
        config.content assertEquals content
        config.isDismissible assertEquals false
        config.closeButtonVisibility assertEquals View.GONE
        config.onDismissCallback assertEquals callback
        config.onModalShowCallback assertEquals callback
    }

    @Test
    fun `dismissible false, buttongroupfixed false, buttongroupcreator, contentvariation small, content, headerfixed true, dismiss callback, show callback `() {
        val callback = { /*no-op*/ }
        val buttonGroupCreator = provideButtonGroupCreator(context)
        val content = provideContent(context)
        arguments = AndesModalCardDefaultFragmentArguments(
            isDismissible = false,
            isButtonGroupFixed = false,
            buttonGroupCreator = buttonGroupCreator,
            onDismissCallback = callback,
            onModalShowCallback = callback,
            contentVariation = AndesModalCardContentVariation.SMALL_ILLUSTRATION,
            isHeaderFixed = true,
            content = content
        )

        config = AndesModalCardDefaultConfigFactory.create(arguments)

        config.isButtonGroupFixed assertEquals false
        Assertions.assertThat(config.scrollViewOutlineProvider).usingRecursiveComparison()
            .isEqualTo(AndesModalCorners.ALL_CORNERS.corners.getOutlineProvider())
        config.isHeaderFixed assertEquals true
        config.buttonGroupCreator assertEquals buttonGroupCreator
        config.contentVariation assertEquals AndesModalCardContentVariation.SMALL_ILLUSTRATION
        config.content assertEquals content
        config.isDismissible assertEquals false
        config.closeButtonVisibility assertEquals View.GONE
        config.onDismissCallback assertEquals callback
        config.onModalShowCallback assertEquals callback
    }

    @Test
    fun `dismissible false, buttongroupfixed true, buttongroupcreator, contentvariation medium, content, headerfixed true, dismiss callback, show callback `() {
        val callback = { /*no-op*/ }
        val buttonGroupCreator = provideButtonGroupCreator(context)
        val content = provideContent(context)
        arguments = AndesModalCardDefaultFragmentArguments(
            isDismissible = false,
            isButtonGroupFixed = true,
            buttonGroupCreator = buttonGroupCreator,
            onDismissCallback = callback,
            onModalShowCallback = callback,
            contentVariation = AndesModalCardContentVariation.MEDIUM_ILLUSTRATION,
            isHeaderFixed = true,
            content = content
        )

        config = AndesModalCardDefaultConfigFactory.create(arguments)

        config.isButtonGroupFixed assertEquals true
        Assertions.assertThat(config.scrollViewOutlineProvider).usingRecursiveComparison()
            .isEqualTo(AndesModalCorners.TOP_CORNERS.corners.getOutlineProvider())
        config.isHeaderFixed assertEquals true
        config.buttonGroupCreator assertEquals buttonGroupCreator
        config.contentVariation assertEquals AndesModalCardContentVariation.MEDIUM_ILLUSTRATION
        config.content assertEquals content
        config.isDismissible assertEquals false
        config.closeButtonVisibility assertEquals View.GONE
        config.onDismissCallback assertEquals callback
        config.onModalShowCallback assertEquals callback
    }

    @Test
    fun `dismissible false, buttongroupfixed true, buttongroupcreator, contentvariation thumbnail, content, headerfixed true, dismiss callback, show callback `() {
        val callback = { /*no-op*/ }
        val buttonGroupCreator = provideButtonGroupCreator(context)
        val content = provideContent(context)
        arguments = AndesModalCardDefaultFragmentArguments(
            isDismissible = false,
            isButtonGroupFixed = true,
            buttonGroupCreator = buttonGroupCreator,
            onDismissCallback = callback,
            onModalShowCallback = callback,
            contentVariation = AndesModalCardContentVariation.THUMBNAIL,
            isHeaderFixed = true,
            content = content
        )

        config = AndesModalCardDefaultConfigFactory.create(arguments)

        config.isButtonGroupFixed assertEquals true
        Assertions.assertThat(config.scrollViewOutlineProvider).usingRecursiveComparison()
            .isEqualTo(AndesModalCorners.TOP_CORNERS.corners.getOutlineProvider())
        config.isHeaderFixed assertEquals true
        config.buttonGroupCreator assertEquals buttonGroupCreator
        config.contentVariation assertEquals AndesModalCardContentVariation.THUMBNAIL
        config.content assertEquals content
        config.isDismissible assertEquals false
        config.closeButtonVisibility assertEquals View.GONE
        config.onDismissCallback assertEquals callback
        config.onModalShowCallback assertEquals callback
    }
}
