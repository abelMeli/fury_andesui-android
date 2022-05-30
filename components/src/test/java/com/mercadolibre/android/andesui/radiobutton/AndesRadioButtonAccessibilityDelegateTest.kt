package com.mercadolibre.android.andesui.radiobutton

import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.radiobutton.align.AndesRadioButtonAlign
import com.mercadolibre.android.andesui.radiobutton.status.AndesRadioButtonStatus
import com.mercadolibre.android.andesui.radiobutton.type.AndesRadioButtonType
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesRadioButtonAccessibilityDelegateTest {

    private lateinit var context: Context
    private lateinit var nodeInfo: AccessibilityNodeInfo
    private lateinit var radioButton: AndesRadioButton

    companion object {
        private const val DEFAULT_TEXT = "default text"
    }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        radioButton = AndesRadioButton(context, DEFAULT_TEXT)
        setupActivityForTesting()
    }

    private fun setupActivityForTesting() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(radioButton)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `node info data is correct for all values set`() {
        val newText = "new text"
        val newType = AndesRadioButtonType.ERROR
        val newStatus = AndesRadioButtonStatus.SELECTED
        val newAlign = AndesRadioButtonAlign.RIGHT
        val errorText = context.resources.getString(R.string.andes_radiobutton_type_error)
        radioButton.apply {
            text = newText
            type = newType
            status = newStatus
            align = newAlign
        }

        nodeInfo = radioButton.createAccessibilityNodeInfo()

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$newText. $errorText.", nodeInfo.contentDescription)
        Assert.assertEquals(true, nodeInfo.isCheckable)
        Assert.assertEquals(true, nodeInfo.isChecked)
    }

    @Test
    fun `node info data is correct for all default values`() {
        radioButton.text = null
        nodeInfo = radioButton.createAccessibilityNodeInfo()

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals(". .", nodeInfo.contentDescription)
        Assert.assertEquals(true, nodeInfo.isCheckable)
        Assert.assertEquals(false, nodeInfo.isChecked)
    }
}
