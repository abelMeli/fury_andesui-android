package com.mercadolibre.android.andesui.dropdown

import android.os.Build
import android.view.View
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
import com.mercadolibre.android.andesui.dropdown.utils.AndesDropdownDelegate
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.AndesListViewItem
import com.mercadolibre.android.andesui.list.AndesListViewItemSimple
import com.mercadolibre.android.andesui.list.size.AndesListViewItemSize
import com.mercadolibre.android.andesui.list.type.AndesListType
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
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
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
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
    fun `test delegate`() {
        val dropdown = AndesDropdownStandalone(context, AndesDropdownMenuType.BOTTOMSHEET, AndesDropdownSize.SMALL)
        val list = AndesList(context, AndesListViewItemSize.MEDIUM, AndesListType.SIMPLE)
        list.delegate = object : AndesListDelegate {

            override fun onItemClick(andesList: AndesList, position: Int) {
                // Do nothing
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemSimple(context, "title")
            }

            override fun getDataSetSize(andesList: AndesList): Int {
                return SIZE
            }
        }

        val listItems = ArrayList<AndesDropDownItem>()
        val item = AndesDropDownItem()

        for (i in 1..SIZE) {
            listItems.add(item)
        }

        val andesDropdownDelegate = object : AndesDropdownDelegate {
            override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
                Assert.assertEquals(true, listItems[position].isSelected)
            }
        }

        dropdown.delegate = andesDropdownDelegate
        dropdown.setItems(listItems)
        dropdown.onItemClick(list, 5)
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

        andesDropdownAttrs = AndesDropdownAttrs(
                andesDropdownMenuType = AndesDropdownMenuType.BOTTOMSHEET,
                andesDropdownLabel = label,
                andesDropdownHelper = helper,
                andesDropdownPlaceHolder = placeHolder,
                andesDropdownState = state)

        val config = configFactory.create(context, andesDropdownAttrs)

        Assert.assertEquals(config.menuType, AndesDropdownMenuType.BOTTOMSHEET)
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

        andesDropdownAttrs = AndesDropdownAttrs(
                andesDropdownMenuType = AndesDropdownMenuType.BOTTOMSHEET,
                andesDropdownLabel = label,
                andesDropdownHelper = helper,
                andesDropdownPlaceHolder = placeHolder,
                andesDropdownState = state)

        val config = configFactory.create(context, andesDropdownAttrs)

        Assert.assertEquals(config.menuType, AndesDropdownMenuType.BOTTOMSHEET)
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

        andesDropdownAttrs = AndesDropdownAttrs(
                andesDropdownMenuType = AndesDropdownMenuType.BOTTOMSHEET,
                andesDropdownLabel = label,
                andesDropdownHelper = helper,
                andesDropdownPlaceHolder = placeHolder,
                andesDropdownState = state)

        val config = configFactory.create(context, andesDropdownAttrs)

        Assert.assertEquals(config.menuType, AndesDropdownMenuType.BOTTOMSHEET)
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
    fun `test dropdownform set indeterminate`() {
        val dropdown = AndesDropDownForm(context, AndesDropdownMenuType.BOTTOMSHEET, "", "", "", AndesDropdownState.ENABLED)

        val spyDropdown = spy(dropdown)
        doNothing().`when`(spyDropdown).setIndeterminate()
        spyDropdown.setIndeterminate(true)
        Assert.assertEquals(AndesDropdownState.DISABLED, spyDropdown.state)
    }
}
