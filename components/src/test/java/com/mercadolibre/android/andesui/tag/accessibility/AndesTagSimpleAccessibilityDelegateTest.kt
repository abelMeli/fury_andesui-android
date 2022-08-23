package com.mercadolibre.android.andesui.tag.accessibility

import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.tag.AndesTagSimple
import com.mercadolibre.android.andesui.tag.leftcontent.LeftContent
import com.mercadolibre.android.andesui.utils.Constants
import com.mercadolibre.android.andesui.utils.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Constants.TEST_ANDROID_VERSION_CODE])
class AndesTagSimpleAccessibilityDelegateTest {

    private val context: Context = ApplicationProvider.getApplicationContext()
    private lateinit var tag : AndesTagSimple
    private lateinit var nodeinfo: AccessibilityNodeInfo

    @Before
    fun before(){
        tag = AndesTagSimple(context,null)
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
    fun `Test with contentDescription`(){
        //GIVEN
        tag.text = "Phone"
        tag.leftContent = LeftContent(dot = null,icon = null, image = null, assetContentDescription = "New")

        //WHEN
        nodeinfo = tag.createAccessibilityNodeInfo()

        //THEN
        nodeinfo.contentDescription assertEquals "Phone, New"
    }

    @Test
    fun `Test contentDescription is empty`(){
        //GIVEN
        tag.text = "Phone"
        tag.leftContent = LeftContent(dot = null,icon = null, image = null, assetContentDescription = "")

        //WHEN
        nodeinfo = tag.createAccessibilityNodeInfo()

        //THEN
        nodeinfo.contentDescription assertEquals "Phone"
    }

    @Test
    fun `Test LeftContent is null`(){
        //GIVEN
        tag.text = "Control"

        //WHEN
        nodeinfo = tag.createAccessibilityNodeInfo()

        //THEN
        nodeinfo.contentDescription assertEquals "Control"
    }

    @Test
    fun `Test contentDescription is null`(){
        //GIVEN
        tag.text = "Car"
        tag.leftContent = LeftContent(dot = null,icon = null, image = null, assetContentDescription = null)

        //WHEN
        nodeinfo = tag.createAccessibilityNodeInfo()

        //THEN
        nodeinfo.contentDescription assertEquals "Car"
    }


}