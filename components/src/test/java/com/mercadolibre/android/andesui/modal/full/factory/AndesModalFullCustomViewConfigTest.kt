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
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderStatus
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderType
import com.mercadolibre.android.andesui.utils.Constants
import com.mercadolibre.android.andesui.utils.provideAndesModalInterface
import com.mercadolibre.android.andesui.utils.provideButtonGroupCreator
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Constants.TEST_ANDROID_VERSION_CODE])
class AndesModalFullCustomViewConfigTest {

    private lateinit var config: AndesModalFullCustomViewConfig
    private var arguments = AndesModalFullCustomViewFragmentArguments()
    private lateinit var context: Context

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            SoLoader.setInTestMode()
        }
        private const val TITLE = "Andes Title"
        private const val TITLE_MODAL = "Title modal"
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
        config = AndesModalFullCustomViewConfigFactory.create(arguments)

        config.isDismissible assertEquals true
        config.closeButtonVisibility assertEquals View.VISIBLE
        config.buttonGroupCreator assertIsNull true
        config.buttonGroupVisibility assertEquals View.GONE
        config.customView assertIsNull true
        config.onDismissCallback assertIsNull true
        config.onModalShowCallback assertIsNull true
        config.headerTextStatus assertEquals AndesModalFullHeaderStatus.EXPANDED
        config.headerType assertEquals AndesModalFullHeaderType.ONLY_CLOSE
        config.headerTitle assertEquals ""
        config.modalDescription assertIsNull true
    }

    @Test
    fun `dismissible false, buttongroup, ondismiss and onshow callbacks, view`() {
        val callback = { /*no-op*/ }
        val buttonGroupCreator = provideButtonGroupCreator(context)
        val view = View(context)
        arguments = AndesModalFullCustomViewFragmentArguments(
            isDismissible = false,
            isHeaderFixed = true,
            headerTitle = TITLE,
            buttonGroupCreator = buttonGroupCreator,
            onDismissCallback = callback,
            onModalShowCallback = callback,
            customView = view,
            modalDescription = TITLE_MODAL
        )

        config = AndesModalFullCustomViewConfigFactory.create(arguments)

        config.isDismissible assertEquals false
        config.isHeaderFixed assertEquals true
        config.closeButtonVisibility assertEquals View.GONE
        config.buttonGroupCreator assertEquals buttonGroupCreator
        config.buttonGroupVisibility assertEquals View.VISIBLE
        config.customView assertEquals view
        config.onDismissCallback assertEquals callback
        config.onModalShowCallback assertEquals callback
        config.headerTextStatus assertEquals AndesModalFullHeaderStatus.EXPANDED
        config.headerType assertEquals AndesModalFullHeaderType.ONLY_TITLE
        config.headerTitle assertEquals TITLE
        config.modalDescription assertEquals TITLE_MODAL
    }
}
