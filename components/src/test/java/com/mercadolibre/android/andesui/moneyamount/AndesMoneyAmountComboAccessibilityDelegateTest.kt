package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import android.os.Build
import android.view.accessibility.AccessibilityNodeInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.moneyamount.combosize.AndesMoneyAmountComboSize
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.utils.MockConfigProvider
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesMoneyAmountComboAccessibilityDelegateTest {

    private lateinit var context: Context
    private lateinit var andesMoneyAmountCombo: AndesMoneyAmountCombo
    private lateinit var nodeInfo: AccessibilityNodeInfo

    init {
        MockConfigProvider.configure()
    }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        andesMoneyAmountCombo = AndesMoneyAmountCombo(context, null)
        setupActivityForTesting()
    }

    private fun setupActivityForTesting() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(andesMoneyAmountCombo)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `Combo with discount, previous amount, amount`() {
        andesMoneyAmountCombo.discount = 15
        andesMoneyAmountCombo.amount = 1350.0
        andesMoneyAmountCombo.previousAmount = 1500.0
        andesMoneyAmountCombo.currency = AndesMoneyAmountCurrency.MXN
        andesMoneyAmountCombo.country = AndesCountry.MX
        andesMoneyAmountCombo.size = AndesMoneyAmountComboSize.SIZE_24

        nodeInfo = andesMoneyAmountCombo.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("Now: 1350 Pesos, 15 percent discount, Before:  1500 Pesos", nodeInfo.contentDescription)
    }

    @Test
    fun `Combo with discount and amount`() {
        andesMoneyAmountCombo.discount = 15
        andesMoneyAmountCombo.amount = 1350.0
        andesMoneyAmountCombo.currency = AndesMoneyAmountCurrency.MXN
        andesMoneyAmountCombo.country = AndesCountry.MX
        andesMoneyAmountCombo.size = AndesMoneyAmountComboSize.SIZE_24

        nodeInfo = andesMoneyAmountCombo.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("Now: 1350 Pesos, 15 percent discount, ", nodeInfo.contentDescription)
    }

    @Test
    fun `Combo with previous amount and amount`() {
        andesMoneyAmountCombo.previousAmount = 1500.0
        andesMoneyAmountCombo.amount = 1350.0
        andesMoneyAmountCombo.currency = AndesMoneyAmountCurrency.MXN
        andesMoneyAmountCombo.country = AndesCountry.MX
        andesMoneyAmountCombo.size = AndesMoneyAmountComboSize.SIZE_24

        nodeInfo = andesMoneyAmountCombo.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("Now: 1350 Pesos,  Before:  1500 Pesos", nodeInfo.contentDescription)
    }

    @Test
    fun `Combo only amount`() {
        andesMoneyAmountCombo.amount = 1350.0
        andesMoneyAmountCombo.currency = AndesMoneyAmountCurrency.MXN
        andesMoneyAmountCombo.country = AndesCountry.MX
        andesMoneyAmountCombo.size = AndesMoneyAmountComboSize.SIZE_24

        nodeInfo = andesMoneyAmountCombo.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("Now: 1350 Pesos,  ", nodeInfo.contentDescription)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Combo with crypto currency throws exception`() {
        andesMoneyAmountCombo.amount = 1350.0
        andesMoneyAmountCombo.currency = AndesMoneyAmountCurrency.BTC
        andesMoneyAmountCombo.country = AndesCountry.MX
        andesMoneyAmountCombo.size = AndesMoneyAmountComboSize.SIZE_24
    }
}
