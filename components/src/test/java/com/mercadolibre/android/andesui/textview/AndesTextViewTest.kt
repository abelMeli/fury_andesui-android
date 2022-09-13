package com.mercadolibre.android.andesui.textview

import android.content.Context
import android.graphics.Color
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.assertIsNull
import com.mercadolibre.android.andesui.utils.buildAttributeSet
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.utils.hasSpans
import com.mercadolibre.android.andesui.utils.getSpans
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.moneyamount.AndesMoneyAmount
import com.mercadolibre.android.andesui.moneyamount.MoneyAmountUtils
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.decimalstyle.AndesMoneyAmountDecimalsStyle
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.moneyamount.type.AndesMoneyAmountType
import com.mercadolibre.android.andesui.textview.accessibility.AndesTextViewAccessibilityDelegate
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBold
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBolds
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.andesui.textview.style.AndesTextViewStyle
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.MockConfigProvider
import junit.framework.Assert.assertFalse
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE], qualifiers = "es")
class AndesTextViewTest {

    private lateinit var context: Context
    private lateinit var textView: AndesTextView
    private lateinit var moneyAmount: AndesMoneyAmount
    private lateinit var activity: AppCompatActivity

    @Before
    fun setup() {
        MockConfigProvider.configure()
        context = ApplicationProvider.getApplicationContext()
        textView = AndesTextView(context)
        moneyAmount = AndesMoneyAmount(
            context,
            177.56,
            AndesMoneyAmountCurrency.ARS,
            true,
            AndesMoneyAmountSize.SIZE_60,
            AndesMoneyAmountType.POSITIVE,
            AndesMoneyAmountDecimalsStyle.SUPERSCRIPT,
            AndesCountry.AR
        )
        setupActivityForTesting()
    }

    private fun setupActivityForTesting() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        val view = LinearLayout(context)
        view.addView(textView)
        view.addView(moneyAmount)
        activity.setContentView(view)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `constructing by code with default values`() {
        textView = AndesTextView(context)

        textView.style assertEquals AndesTextViewStyle.BodyS
        textView.getTextColor() assertEquals AndesTextViewColor.Primary
        textView.bodyBolds assertIsNull true
        textView.bodyLinks assertIsNull true
        textView.isLinkColorInverted assertEquals false
        textView.movementMethod assertIsNull true
    }

    @Test
    fun `constructing by xml with default values`() {
        val attrs = buildAttributeSet {}
        textView = AndesTextView(context, attrs)

        textView.style assertEquals AndesTextViewStyle.Custom(0, 0F, 0F)
        textView.getTextColor() assertEquals AndesTextViewColor.Custom(DEFAULT_TEXT_COLOR_VALUE)
        textView.getTextColor().color.getColor(context) assertEquals FINAL_PRIMARY_TEXT_COLOR
        textView.bodyBolds assertIsNull true
        textView.bodyLinks assertIsNull true
        textView.isLinkColorInverted assertEquals false
        textView.movementMethod assertIsNull true
    }

    @Test
    fun `constructing by code with default values and setting all values`() {
        textView = AndesTextView(context)

        textView.apply {
            text = "text for testing"
            style = AndesTextViewStyle.TitleXl
            setTextColor(AndesTextViewColor.Positive)
            bodyBolds = getBodyBoldsForTest()
            bodyLinks = getBodyLinksForTest()
            isLinkColorInverted = true
        }

        textView.style assertEquals AndesTextViewStyle.TitleXl
        textView.getTextColor() assertEquals AndesTextViewColor.Positive
        textView.bodyBolds assertIsNull false
        textView.bodyLinks assertIsNull false
        textView.isLinkColorInverted assertEquals true
        textView.text.hasSpans() assertEquals true
        assertThat(textView.movementMethod).usingRecursiveComparison().isEqualTo(LinkMovementMethod.getInstance())

        val hasBodyBoldSpan = textView.text.getSpans().stream().anyMatch {
            it is StyleSpan
        }

        val hasBodyLinkSpan = textView.text.getSpans().stream().anyMatch {
            it is StyleSpan
        }

        Assert.assertTrue(hasBodyBoldSpan)
        Assert.assertTrue(hasBodyLinkSpan)
    }

    private fun createDefaultText() {
        textView.apply {
            text = "text for testing"
            style = AndesTextViewStyle.TitleXl
            setTextColor(AndesTextViewColor.Positive)
            bodyBolds = getBodyBoldsForTest()
            bodyLinks = getBodyLinksForTest()
            isLinkColorInverted = true
        }
    }

    @Test
    fun `constructing by code with text, appending money amount semibold`() {
        textView.apply {
            clear()
            append("text for testing")
        }

        moneyAmount.semiBold = true

        textView.append(
            moneyAmount,
            R.color.andes_red_500.toAndesColor()
        )

        val hasBodyMoneyAmount = textView.text.getSpans().stream().anyMatch {
            it is MoneyAmountUtils.CustomRelativeSizeSpan
        }

        val hasBodyMoneyAmountColor = textView.text.getSpans().stream().anyMatch {
            it is ForegroundColorSpan
        }

        val styleSpans = textView.text.getSpans().filterIsInstance<StyleSpan>()

        Assert.assertTrue(hasBodyMoneyAmount)
        Assert.assertTrue(hasBodyMoneyAmountColor)
        styleSpans.size assertEquals 1
    }

    @Test
    fun `constructing by code with text, appending money amount semibold, appending text and bolds`() {
        moneyAmount.semiBold = true
        textView.apply {
            clear()
            append("text for testing ")
            append(
                moneyAmount,
                R.color.andes_red_500.toAndesColor()
            )
            append(" bold")
            bodyBolds = AndesBodyBolds(listOf(
                AndesBodyBold(text.indexOf("bold"), text.length)
            ))
        }

        val hasBodyMoneyAmount = textView.text.getSpans().stream().anyMatch {
            it is MoneyAmountUtils.CustomRelativeSizeSpan
        }

        val hasBodyMoneyAmountColor = textView.text.getSpans().stream().anyMatch {
            it is ForegroundColorSpan
        }

        val styleSpans = textView.text.getSpans().filterIsInstance<StyleSpan>()

        Assert.assertTrue(hasBodyMoneyAmount)
        Assert.assertTrue(hasBodyMoneyAmountColor)
        styleSpans.size assertEquals 2
    }

    @Test
    fun `constructing by code with default, appending money amount`() {
        createDefaultText()

        textView.append(
            moneyAmount,
            R.color.andes_red_500.toAndesColor()
        )

        val hasBodyMoneyAmount = textView.text.getSpans().stream().anyMatch {
            it is MoneyAmountUtils.CustomRelativeSizeSpan
        }

        val hasBodyMoneyAmountColor = textView.text.getSpans().stream().anyMatch {
            it is ForegroundColorSpan
        }

        Assert.assertTrue(hasBodyMoneyAmount)
        Assert.assertTrue(hasBodyMoneyAmountColor)
        Assert.assertEquals("text for testing177 Pesos con 56 Centavos", textView.accessibilityText)
    }


    @Test
    fun `constructing by code with default, appending two money amounts`() {
        createDefaultText()

        textView.append(
            moneyAmount,
            R.color.andes_red_500.toAndesColor()
        )

        textView.append(
            moneyAmount,
            R.color.andes_red_500.toAndesColor()
        )

        Assert.assertEquals("text for testing177 Pesos con 56 Centavos177 Pesos con 56 Centavos", textView.accessibilityText)
        Assert.assertEquals("text for testing\$ 17756\$ 17756", textView.text.toString())
    }

    @Test
    fun `constructing by code with default, appending two texts`() {
        createDefaultText()

        val textToAdd = "Added text"
        textView.append(
            textToAdd,
            R.color.andes_red_500.toAndesColor()
        )

        textView.append(
            textToAdd,
            R.color.andes_red_500.toAndesColor()
        )

        Assert.assertEquals("text for testingAdded textAdded text", textView.accessibilityText)
        Assert.assertEquals("text for testingAdded textAdded text", textView.text.toString())
    }

    @Test
    fun `constructing by code apennding and setting a texts and amount`() {
        createDefaultText()

        data class TestCase(val action: Pair<Int, Any>, val expectedA11y: String, val expectedText: String)

        val testCases = listOf(
            TestCase(APPEND to "Added text","text for testingAdded text","text for testingAdded text"),
            TestCase(APPEND to moneyAmount, "text for testingAdded text177 Pesos con 56 Centavos", "text for testingAdded text\$ 17756"),
            TestCase(APPEND to "Added text", "text for testingAdded text177 Pesos con 56 CentavosAdded text", "text for testingAdded text\$ 17756Added text"),
            TestCase(SETTEXT to "any", "any", "any"),
            TestCase(APPEND to "Added text", "anyAdded text", "anyAdded text"),
            TestCase(SETTEXT to "any2", "any2", "any2"),
            TestCase(APPEND to "text to add 2", "any2text to add 2", "any2text to add 2"),
            TestCase(APPEND to moneyAmount, "any2text to add 2177 Pesos con 56 Centavos", "any2text to add 2\$ 17756"),
            TestCase(SETTEXT to "", "", ""),
            TestCase(APPEND to "another Text", "another Text", "another Text"),
            TestCase(SETTEXT to "another Text", "another Text", "another Text"),
            TestCase(APPEND to "another Text", "another Textanother Text", "another Textanother Text"),
            TestCase(APPEND to moneyAmount, "another Textanother Text177 Pesos con 56 Centavos", "another Textanother Text\$ 17756")
        )

        testCases.forEach {
            if (it.action.first == APPEND) {
                if (it.action.second is AndesMoneyAmount) {
                    textView.append(
                        it.action.second as AndesMoneyAmount,
                        R.color.andes_red_500.toAndesColor()
                    )

                } else {
                    textView.append(
                        it.action.second as String,
                        R.color.andes_red_500.toAndesColor()
                    )
                }
            } else {
                textView.text = (it.action.second as String).toString()
            }

            Assert.assertEquals(it.expectedA11y, textView.accessibilityText)
            Assert.assertEquals(it.expectedText, textView.text.toString())
        }
    }

    @Test
    fun `constructing by code with default, appending text amount and text`() {
        createDefaultText()

        val textToAdd = "Added text"
        textView.append(
            textToAdd,
            R.color.andes_red_500.toAndesColor()
        )

        textView.append(
            moneyAmount,
            R.color.andes_red_500.toAndesColor()
        )

        textView.append(
            textToAdd,
            R.color.andes_red_500.toAndesColor()
        )

        Assert.assertEquals("text for testingAdded text177 Pesos con 56 CentavosAdded text", textView.accessibilityText)
        Assert.assertEquals("text for testingAdded text\$ 17756Added text", textView.text.toString())
    }

    @Test
    fun `a11y text is correct for all values set appending text`() {
        createDefaultText()

        textView.append("Append text", Color.RED)
        val nodeInfo = textView.createAccessibilityNodeInfo()
        val a11yDelegate = textView.accessibilityDelegate as AndesTextViewAccessibilityDelegate
        a11yDelegate.onInitializeAccessibilityNodeInfo(textView, nodeInfo)

        Assert.assertEquals("text for testingAppend text", nodeInfo.text.toString())
    }

    @Test
    fun `a11y text is correct for all values appending amount`() {
        createDefaultText()

        textView.append(moneyAmount, Color.RED)
        val nodeInfo = textView.createAccessibilityNodeInfo()
        val a11yDelegate = textView.accessibilityDelegate as AndesTextViewAccessibilityDelegate
        a11yDelegate.onInitializeAccessibilityNodeInfo(textView, nodeInfo)

        Assert.assertEquals("text for testing177 Pesos con 56 Centavos", nodeInfo.text.toString())
    }

    @Test
    fun `a11y text is correct for all values appending amount and null color`() {
        createDefaultText()

        textView.append(moneyAmount, null)

        val hasBodyMoneyAmountColor = textView.text.getSpans().stream().anyMatch {
            it is ForegroundColorSpan
        }

        assertFalse(hasBodyMoneyAmountColor)
        Assert.assertEquals("text for testing177 Pesos con 56 Centavos", textView.accessibilityText)
    }

    @Test
    fun `a11y text is correct when setting some other text`() {
        createDefaultText()

        textView.text = "Some text"
        val nodeInfo = textView.createAccessibilityNodeInfo()
        val a11yDelegate = textView.accessibilityDelegate as AndesTextViewAccessibilityDelegate
        a11yDelegate.onInitializeAccessibilityNodeInfo(textView, nodeInfo)

        Assert.assertEquals("Some text", nodeInfo.text.toString())
    }

    @Test
    fun `constructing by code with default, appending text`() {
        textView = AndesTextView(context)
        textView.apply {
            text = "text for testing"
            style = AndesTextViewStyle.TitleXl
            setTextColor(AndesTextViewColor.Positive)
            bodyBolds = getBodyBoldsForTest()
            bodyLinks = getBodyLinksForTest()
            isLinkColorInverted = true
        }

        val textToAdd = "Added text"
        textView.append(
            textToAdd,
            R.color.andes_red_500.toAndesColor()
        )

        val hasBodyMoneyAmountColor = textView.text.getSpans().stream().anyMatch {
            it is ForegroundColorSpan
        }
        Assert.assertTrue(hasBodyMoneyAmountColor)
        Assert.assertEquals("text for testingAdded text", textView.text.toString())
        Assert.assertEquals("text for testingAdded text", textView.accessibilityText)
    }

    @Test
    fun `constructing by code with default, updating valid substring with color`() {
        createDefaultText()

        textView.updateColor("testing", AndesColor(R.color.andes_red_500))
        var hasBodyMoneyAmountColor = textView.text.getSpans().stream().anyMatch {
            it is ForegroundColorSpan
        }

        Assert.assertTrue(hasBodyMoneyAmountColor)
    }

    @Test
    fun `constructing by code with default, updating valid substring with color and clearing`() {
        createDefaultText()

        textView.updateColor("testing", AndesColor(R.color.andes_red_500))
        textView.clear()
        val isTextEmpty = textView.text.isEmpty()
        val isA11yTextEmpty = textView.accessibilityText.isEmpty()

        Assert.assertTrue(isTextEmpty)
        Assert.assertTrue(isA11yTextEmpty)
    }

    @Test
    fun `constructing by code with default, updating invalid substring with color`() {
        createDefaultText()

        textView.updateColor("text that is not a substring", AndesColor(R.color.andes_red_500))
        val hasBodyMoneyAmountColor = textView.text.getSpans().stream().anyMatch {
            it is ForegroundColorSpan
        }

        Assert.assertFalse(hasBodyMoneyAmountColor)
    }

    @Test
    fun `constructing by code with default, updating valid range with color and clearing`() {
        createDefaultText()

        textView.updateColor(IntRange(0, 5), AndesColor(R.color.andes_red_500))
        textView.clear()
        val isEmpty = textView.text.isEmpty()
        val hasBodyMoneyAmountColor = textView.text.getSpans().stream().anyMatch {
            it is ForegroundColorSpan
        }

        Assert.assertTrue(isEmpty)
        Assert.assertFalse(hasBodyMoneyAmountColor)
    }

    @Test
    fun `constructing by code with default, updating valid range with color in border case`(){
        createDefaultText()

        textView.updateColor(IntRange(0, 16), AndesColor(R.color.andes_red_500))
        val hasBodyMoneyAmountColor = textView.text.getSpans().stream().anyMatch {
            it is ForegroundColorSpan
        }

        Assert.assertTrue(hasBodyMoneyAmountColor)
    }

    @Test
    fun `constructing by code with default, updating invalid range with color`() {
        createDefaultText()

        textView.updateColor(IntRange(0,500), AndesColor(R.color.andes_red_500))
        val hasBodyMoneyAmountColor = textView.text.getSpans().stream().anyMatch {
            it is ForegroundColorSpan
        }

        Assert.assertFalse(hasBodyMoneyAmountColor)
    }

    @Test
    fun `setting invalid bodyLinks and bodyBolds`() {
        textView = AndesTextView(context)
        textView.apply {
            text = ""
            bodyLinks = getBodyLinksForTest()
            bodyBolds = getBodyBoldsForTest()
        }

        textView.text.hasSpans() assertEquals false
    }

    private fun getBodyBoldsForTest(): AndesBodyBolds {
        return AndesBodyBolds(
            listOf(
                AndesBodyBold(1, 3)
            )
        )
    }

    private fun getBodyLinksForTest(): AndesBodyLinks {
        return AndesBodyLinks(
            links = listOf(
                AndesBodyLink(4, 6)
            ),
            listener = { }
        )
    }

    companion object {
        private const val DEFAULT_TEXT_COLOR_VALUE = 0
        private const val APPEND = 1
        private const val SETTEXT = 2
        private const val FINAL_PRIMARY_TEXT_COLOR = -436207616

    }
}
