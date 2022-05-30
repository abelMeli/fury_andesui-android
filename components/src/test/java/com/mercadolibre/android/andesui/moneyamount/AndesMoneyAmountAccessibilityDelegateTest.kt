package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import android.text.SpannableStringBuilder
import android.view.accessibility.AccessibilityNodeInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.type.AndesMoneyAmountType
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
class AndesMoneyAmountAccessibilityDelegateTest {

    private lateinit var context: Context
    private lateinit var andesMoneyAmount: AndesMoneyAmount
    private lateinit var nodeInfo: AccessibilityNodeInfo

    init {
        MockConfigProvider.configure()
    }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        andesMoneyAmount = AndesMoneyAmount(context, null)
        setupActivityForTesting()
    }

    private fun setupActivityForTesting() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(andesMoneyAmount)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `ARS, Previous, AR`() {
        andesMoneyAmount.amount = 1500.50
        andesMoneyAmount.currency = AndesMoneyAmountCurrency.ARS
        andesMoneyAmount.country = AndesCountry.AR
        andesMoneyAmount.type = AndesMoneyAmountType.PREVIOUS
        andesMoneyAmount.showZerosDecimal = true

        nodeInfo = andesMoneyAmount.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("Previous price:  1500 Pesos with 50 Centavos", nodeInfo.contentDescription)
    }

    @Test
    fun `ARS, Negative, AR`() {
        andesMoneyAmount.amount = 1500.50
        andesMoneyAmount.currency = AndesMoneyAmountCurrency.ARS
        andesMoneyAmount.country = AndesCountry.AR
        andesMoneyAmount.type = AndesMoneyAmountType.NEGATIVE
        andesMoneyAmount.showZerosDecimal = true

        nodeInfo = andesMoneyAmount.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("Minus 1500 Pesos with 50 Centavos", nodeInfo.contentDescription)
    }

    @Test
    fun `BRL, ShowZero, Positive, AR`() {
        andesMoneyAmount.amount = 1500.00
        andesMoneyAmount.currency = AndesMoneyAmountCurrency.BRL
        andesMoneyAmount.country = AndesCountry.AR
        andesMoneyAmount.type = AndesMoneyAmountType.POSITIVE
        andesMoneyAmount.showZerosDecimal = true

        nodeInfo = andesMoneyAmount.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("1500 Reales with 0 Centavos", nodeInfo.contentDescription)
    }

    @Test
    fun `USD, Singular Amount, Plural Decimal, Positive, MX`() {
        andesMoneyAmount.amount = 1.56
        andesMoneyAmount.currency = AndesMoneyAmountCurrency.BRL
        andesMoneyAmount.country = AndesCountry.MX
        andesMoneyAmount.type = AndesMoneyAmountType.POSITIVE

        nodeInfo = andesMoneyAmount.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("one Real with 56 Centavos", nodeInfo.contentDescription)
    }

    @Test
    fun `USD, Singular Amount, Singular Decimal, Positive, MX`() {
        andesMoneyAmount.amount = 1.1
        andesMoneyAmount.currency = AndesMoneyAmountCurrency.BRL
        andesMoneyAmount.country = AndesCountry.MX
        andesMoneyAmount.type = AndesMoneyAmountType.POSITIVE

        nodeInfo = andesMoneyAmount.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("one Real with 10 Centavos", nodeInfo.contentDescription)
    }

    @Test
    fun `USD, Plural Amount, Singular Decimal, Positive, MX`() {
        andesMoneyAmount.amount = 1234.1
        andesMoneyAmount.currency = AndesMoneyAmountCurrency.BRL
        andesMoneyAmount.country = AndesCountry.MX
        andesMoneyAmount.type = AndesMoneyAmountType.POSITIVE

        nodeInfo = andesMoneyAmount.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("1234 Reales with 10 Centavos", nodeInfo.contentDescription)
    }

    @Test
    fun `USD, Plural Amount, Plural Decimal, Positive, MX`() {
        andesMoneyAmount.amount = 1234.17
        andesMoneyAmount.currency = AndesMoneyAmountCurrency.BRL
        andesMoneyAmount.country = AndesCountry.MX
        andesMoneyAmount.type = AndesMoneyAmountType.POSITIVE

        nodeInfo = andesMoneyAmount.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("1234 Reales with 17 Centavos", nodeInfo.contentDescription)
    }

    @Test
    fun `USD, Plural Amount, Plural Decimal, Positive, MX, suffix`() {
        andesMoneyAmount.amount = 1234.17
        andesMoneyAmount.currency = AndesMoneyAmountCurrency.BRL
        andesMoneyAmount.country = AndesCountry.MX
        andesMoneyAmount.type = AndesMoneyAmountType.POSITIVE
        andesMoneyAmount.setSuffix(SpannableStringBuilder("/unidad"), "por unidad")

        nodeInfo = andesMoneyAmount.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("1234 Reales with 17 Centavos por unidad", nodeInfo.contentDescription)
    }


    @Test
    fun `USD, Singular Amount, Plural Decimal, Positive, MX, suffix`() {
        andesMoneyAmount.amount = 1.17
        andesMoneyAmount.currency = AndesMoneyAmountCurrency.BRL
        andesMoneyAmount.country = AndesCountry.MX
        andesMoneyAmount.type = AndesMoneyAmountType.POSITIVE
        andesMoneyAmount.setSuffix(SpannableStringBuilder("/unidad"), "por unidad")

        nodeInfo = andesMoneyAmount.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("one Real with 17 Centavos por unidad", nodeInfo.contentDescription)
    }

    @Test
    fun `USD, Singular Amount, Singular Decimal, Positive, MX, suffix`() {
        andesMoneyAmount.amount = 1.01
        andesMoneyAmount.currency = AndesMoneyAmountCurrency.BRL
        andesMoneyAmount.country = AndesCountry.MX
        andesMoneyAmount.type = AndesMoneyAmountType.POSITIVE
        andesMoneyAmount.setSuffix(SpannableStringBuilder("/unidad"), "por unidad")

        nodeInfo = andesMoneyAmount.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("one Real with one Centavo por unidad", nodeInfo.contentDescription)
    }

    @Test
    fun `USD, Plural Amount, Singular Decimal, Positive, MX, suffix`() {
        andesMoneyAmount.amount = 1234.01
        andesMoneyAmount.currency = AndesMoneyAmountCurrency.BRL
        andesMoneyAmount.country = AndesCountry.MX
        andesMoneyAmount.type = AndesMoneyAmountType.POSITIVE
        andesMoneyAmount.setSuffix(SpannableStringBuilder("/unidad"), "por unidad")

        nodeInfo = andesMoneyAmount.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("1234 Reales with one Centavo por unidad", nodeInfo.contentDescription)
    }
}
