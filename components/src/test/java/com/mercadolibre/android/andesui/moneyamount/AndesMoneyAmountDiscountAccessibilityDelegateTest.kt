package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.MockConfigProvider
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesMoneyAmountDiscountAccessibilityDelegateTest {

    private lateinit var context: Context
    private lateinit var andesMoneyAmountDiscount: AndesMoneyAmountDiscount
    private lateinit var nodeInfo: AccessibilityNodeInfo

    init {
        MockConfigProvider.configure()
    }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        andesMoneyAmountDiscount = AndesMoneyAmountDiscount(context, null)
        setupActivityForTesting()
    }

    private fun setupActivityForTesting() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(andesMoneyAmountDiscount)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `15 discount`() {
        andesMoneyAmountDiscount.discount = 15
        andesMoneyAmountDiscount.size = AndesMoneyAmountSize.SIZE_12

        nodeInfo = andesMoneyAmountDiscount.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("15 percent discount", nodeInfo.contentDescription)
    }

    @Test
    fun `27 discount`() {
        andesMoneyAmountDiscount.discount = 27
        andesMoneyAmountDiscount.size = AndesMoneyAmountSize.SIZE_12

        nodeInfo = andesMoneyAmountDiscount.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("27 percent discount", nodeInfo.contentDescription)
    }
}
