package com.mercadolibre.android.andesui.textfield

import android.graphics.drawable.BitmapDrawable
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.color.toColor
import com.mercadolibre.android.andesui.icons.IconProvider
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldStateInterface
import com.mercadolibre.android.andesui.textfield.state.AndesIdleTextfieldState
import com.mercadolibre.android.andesui.textfield.state.AndesErrorTextfieldState
import com.mercadolibre.android.andesui.textfield.state.AndesDisabledTextfieldState
import com.mercadolibre.android.andesui.textfield.state.AndesReadonlyTextfieldState
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.buildColoredCircularShapeWithIconDrawable
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesTextfieldStateInterfaceTest {
    private var context = RuntimeEnvironment.application
    private lateinit var stateInterface: AndesTextfieldStateInterface

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            SoLoader.setInTestMode()
        }
    }

    @Before
    fun setUp() {
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
                // other setters
                .setRequestListeners(requestListeners)
                .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
    }

    @Test
    fun `enabled state`() {
        stateInterface = AndesIdleTextfieldState
        assertEquals(R.color.andes_text_color_primary.toAndesColor(), stateInterface.labelColor())
        assertEquals(R.color.andes_gray_200.toAndesColor(), stateInterface.placeholderColor())
        assertEquals(R.color.andes_text_color_secondary.toAndesColor(), stateInterface.helpersColor())
        assertNull(stateInterface.icon(context))
    }

    @Test
    fun `error state`() {
        stateInterface = AndesErrorTextfieldState
        assertEquals(R.color.andes_red_500.toAndesColor(), stateInterface.labelColor())
        assertEquals(R.color.andes_gray_200.toAndesColor(), stateInterface.placeholderColor())
        assertEquals(R.color.andes_red_500.toAndesColor(), stateInterface.helpersColor())
        val actual = stateInterface.icon(context)
        val expected = buildColoredCircularShapeWithIconDrawable(
                IconProvider(context).loadIcon("andes_ui_feedback_warning_16") as BitmapDrawable,
                context,
                R.color.andes_white.toAndesColor(),
                R.color.andes_red_500.toColor(context),
                context.resources.getDimension(R.dimen.andes_textfield_icon_diameter).toInt())
        assertEquals(Shadows.shadowOf(expected).createdFromResId, Shadows.shadowOf(actual).createdFromResId)
    }

    @Test
    fun `disabled state`() {
        stateInterface = AndesDisabledTextfieldState
        assertEquals(R.color.andes_gray_250.toAndesColor(), stateInterface.labelColor())
        assertEquals(R.color.andes_gray_250.toAndesColor(), stateInterface.placeholderColor())
        assertEquals(R.color.andes_gray_250.toAndesColor(), stateInterface.helpersColor())
        assertNull(stateInterface.icon(context))
    }

    @Test
    fun `read only state`() {
        stateInterface = AndesReadonlyTextfieldState
        assertEquals(R.color.andes_text_color_secondary.toAndesColor(), stateInterface.labelColor())
        assertEquals(R.color.andes_text_color_primary.toAndesColor(), stateInterface.placeholderColor())
        assertEquals(R.color.andes_gray_250.toAndesColor(), stateInterface.helpersColor())
        assertNull(stateInterface.icon(context))
    }
}
