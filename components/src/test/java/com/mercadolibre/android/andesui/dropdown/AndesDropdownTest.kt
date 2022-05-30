package com.mercadolibre.android.andesui.dropdown

import android.widget.Spinner
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.dropdown.factory.AndesDropdownAttrs
import com.mercadolibre.android.andesui.dropdown.factory.AndesDropdownConfigurationFactory
import com.mercadolibre.android.andesui.dropdown.size.AndesDropdownSize
import com.mercadolibre.android.andesui.dropdown.state.AndesDropdownState
import com.mercadolibre.android.andesui.dropdown.type.AndesDropdownMenuType
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.spy
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesDropdownTest {
    private var context = RuntimeEnvironment.application
    private lateinit var andesDropdownAttrs: AndesDropdownAttrs
    private val configFactory = Mockito.spy(AndesDropdownConfigurationFactory)

    companion object {
        private const val SIZE = 10

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
    fun `test dropdown standalone with different sizes`() {
        var dropdown = AndesDropdownStandalone(context, AndesDropdownMenuType.BOTTOMSHEET, AndesDropdownSize.SMALL)
        Assert.assertEquals(dropdown.size, AndesDropdownSize.SMALL)

        dropdown = AndesDropdownStandalone(context, AndesDropdownMenuType.BOTTOMSHEET, AndesDropdownSize.MEDIUM)
        Assert.assertEquals(dropdown.size, AndesDropdownSize.MEDIUM)

        dropdown = AndesDropdownStandalone(context, AndesDropdownMenuType.BOTTOMSHEET, AndesDropdownSize.LARGE)
        Assert.assertEquals(dropdown.size, AndesDropdownSize.LARGE)
    }

    @Test
    fun `test dropdown Attrs Enabled`() {
        val label = "label"
        val helper = "helper"
        val placeHolder = "placeHolder"
        val state = AndesDropdownState.ENABLED
        val menuType = AndesDropdownMenuType.BOTTOMSHEET

        andesDropdownAttrs = AndesDropdownAttrs(
                andesDropdownMenuType = menuType,
                andesDropdownLabel = label,
                andesDropdownHelper = helper,
                andesDropdownPlaceHolder = placeHolder,
                andesDropdownState = state)

        val config = configFactory.create(context, andesDropdownAttrs)

        Assert.assertEquals(config.menuType::class.java, menuType.type::class.java)
        Assert.assertEquals(config.label, label)
        Assert.assertEquals(config.helper, helper)
        Assert.assertEquals(config.placeHolder, placeHolder)
        Assert.assertEquals(config.textfieldState, AndesTextfieldState.IDLE)
    }

    @Test
    fun `test dropdown Attrs Error`() {
        val label = "label"
        val helper = "helper"
        val placeHolder = "placeHolder"
        val state = AndesDropdownState.ERROR
        val menuType = AndesDropdownMenuType.BOTTOMSHEET

        andesDropdownAttrs = AndesDropdownAttrs(
                andesDropdownMenuType = menuType,
                andesDropdownLabel = label,
                andesDropdownHelper = helper,
                andesDropdownPlaceHolder = placeHolder,
                andesDropdownState = state)

        val config = configFactory.create(context, andesDropdownAttrs)

        Assert.assertEquals(config.menuType::class.java, menuType.type::class.java)
        Assert.assertEquals(config.label, label)
        Assert.assertEquals(config.helper, helper)
        Assert.assertEquals(config.placeHolder, placeHolder)
        Assert.assertEquals(config.textfieldState, AndesTextfieldState.ERROR)
    }

    @Test
    fun `test dropdown Attrs Disabled`() {
        val label = "label"
        val helper = "helper"
        val placeHolder = "placeHolder"
        val state = AndesDropdownState.DISABLED
        val menuType = AndesDropdownMenuType.FLOATINGMENU

        andesDropdownAttrs = AndesDropdownAttrs(
                andesDropdownMenuType = menuType,
                andesDropdownLabel = label,
                andesDropdownHelper = helper,
                andesDropdownPlaceHolder = placeHolder,
                andesDropdownState = state)

        val config = configFactory.create(context, andesDropdownAttrs)

        Assert.assertEquals(config.menuType::class.java, menuType.type::class.java)
        Assert.assertEquals(config.label, label)
        Assert.assertEquals(config.helper, helper)
        Assert.assertEquals(config.placeHolder, placeHolder)
        Assert.assertEquals(config.textfieldState, AndesTextfieldState.DISABLED)
    }

    @Test
    fun `test dropdownform default constructor`() {
        val dropdown = AndesDropDownForm(context, AndesDropdownMenuType.BOTTOMSHEET, "", "", "")
        Assert.assertEquals(dropdown.state, AndesDropdownState.ENABLED)
    }

    @Test
    fun `test dropdownform constructor with state`() {
        val dropdown = AndesDropDownForm(context, AndesDropdownMenuType.BOTTOMSHEET, "", "", "", AndesDropdownState.DISABLED)
        Assert.assertEquals(dropdown.state, AndesDropdownState.DISABLED)
    }

    @Test
    fun `test dropdown form constructor with type floatingmenu`() {
        val dropdown = AndesDropDownForm(
            context,
            AndesDropdownMenuType.FLOATINGMENU,
            "label",
            "helper",
            "placeholder"
        )
        Assert.assertEquals(dropdown.menuType, AndesDropdownMenuType.FLOATINGMENU)
    }

    @Test
    fun `test dropdownform set indeterminate`() {
        val dropdown = AndesDropDownForm(context, AndesDropdownMenuType.BOTTOMSHEET, "", "", "", AndesDropdownState.ENABLED)

        val spyDropdown = spy(dropdown)
        doNothing().`when`(spyDropdown).setIndeterminate()
        spyDropdown.setIndeterminate(true)
        Assert.assertEquals(AndesDropdownState.DISABLED, spyDropdown.state)
    }

    @Test
    fun `AndesDropDownForm getter for accessibilityClassName works correctly with textfield state READONLY`() {
        val dropdown = AndesDropDownForm(
            context,
            AndesDropdownMenuType.FLOATINGMENU,
            "",
            "",
            "",
            AndesDropdownState.ENABLED
        )

        dropdown.setReadOnly()
        val a11yClassName = dropdown.accessibilityClassName

        Assert.assertEquals("", a11yClassName)
    }

    @Test
    fun `AndesDropDownForm getter for accessibilityClassName works correctly with textfield state different than READONLY`() {
        val dropdown = AndesDropDownForm(
            context,
            AndesDropdownMenuType.FLOATINGMENU,
            "",
            "",
            "",
            AndesDropdownState.ENABLED
        )

        val a11yClassName = dropdown.accessibilityClassName

        Assert.assertEquals(Spinner::class.java.name, a11yClassName)
    }

    @Test
    fun `AndesDropdownStandalone getter for accessibilityClassName works correctly`() {
        val dropdown = AndesDropdownStandalone(
            context,
            AndesDropdownMenuType.BOTTOMSHEET,
            AndesDropdownSize.MEDIUM
        )

        val a11yClassName = dropdown.accessibilityClassName

        Assert.assertEquals(Spinner::class.java.name, a11yClassName)
    }
}
