package com.mercadolibre.android.andesui.tag.accessibility

import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.tag.AndesTagChoice
import com.mercadolibre.android.andesui.tag.choice.state.AndesTagChoiceState
import com.mercadolibre.android.andesui.tag.leftcontent.LeftContent
import com.mercadolibre.android.andesui.utils.Constants
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.assertIsNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Constants.TEST_ANDROID_VERSION_CODE])
class AndesTagChoiceAccessibilityDelegateTest {

    private val context: Context = ApplicationProvider.getApplicationContext()
    private lateinit var tag : AndesTagChoice
    private lateinit var nodeinfo: AccessibilityNodeInfo

    @Before
    fun before(){
        tag = AndesTagChoice(context,null)
        setTupActivity()
    }

    private fun setTupActivity(){
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_MaterialComponents_NoActionBar_Bridge)
        activity.setContentView(tag)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `Test contentDescription is empty`(){
        //GIVEN
        tag.state = AndesTagChoiceState.SELECTED
        tag.text = "Phone"
        tag.leftContent = LeftContent(dot = null,icon = null, image = null, assetContentDescription = "")

        //WHEN
        nodeinfo = tag.createAccessibilityNodeInfo()

        //THEN
        nodeinfo.isChecked assertEquals true
        nodeinfo.contentDescription assertEquals "Phone"
    }

    @Test
    fun `Test LeftContent is null`(){
        //GIVEN
        tag.state = AndesTagChoiceState.SELECTED
        tag.text = "Control"

        //WHEN
        nodeinfo = tag.createAccessibilityNodeInfo()

        //THEN
        nodeinfo.isChecked assertEquals true
        nodeinfo.contentDescription assertEquals "Control"
    }

    @Test
    fun `Test contentDescription is null`(){
        //GIVEN
        tag.state = AndesTagChoiceState.SELECTED
        tag.text = "Car"
        tag.leftContent = LeftContent(dot = null,icon = null, image = null, assetContentDescription = null)

        //WHEN
        nodeinfo = tag.createAccessibilityNodeInfo()

        //THEN
        nodeinfo.isChecked assertEquals true
        nodeinfo.contentDescription assertEquals "Car"
    }

    @Test
    fun `Test contentDescription on tag unselect`(){
        //GIVEN
        tag.state = AndesTagChoiceState.IDLE
        tag.leftContent = LeftContent(dot = null,icon = null, image = null, assetContentDescription = "Phone")
        tag.text = "Mobile"

        //WHEN
        nodeinfo = tag.createAccessibilityNodeInfo()

        //THEN
        nodeinfo.isChecked assertEquals false
        nodeinfo.contentDescription assertEquals "Mobile, Phone"

    }

    @Test
    fun `Test contentDescription on tag select`(){
        //GIVEN
        tag.state = AndesTagChoiceState.SELECTED
        tag.leftContent = LeftContent(dot = null,icon = null, image = null, assetContentDescription = "Key")
        tag.text = "Settings"

        //WHEN
        nodeinfo = tag.createAccessibilityNodeInfo()

        //THEN
        nodeinfo.isCheckable assertEquals true
        nodeinfo.isChecked assertEquals true
        nodeinfo.contentDescription assertEquals "Settings, Key"
    }


}