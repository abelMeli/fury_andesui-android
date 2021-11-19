package com.mercadolibre.android.andesui.textview

import android.content.Context
import android.os.Build
import android.text.style.StyleSpan
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.assertIsNull
import com.mercadolibre.android.andesui.buildAttributeSet
import com.mercadolibre.android.andesui.hasSpans
import com.mercadolibre.android.andesui.getSpans
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBold
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBolds
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.andesui.textview.style.AndesTextViewStyle
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesTextViewTest {

    private lateinit var context: Context
    private lateinit var textView: AndesTextView

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `constructing by code with default values`() {
        textView = AndesTextView(context)

        textView.style assertEquals AndesTextViewStyle.BodyS
        textView.getTextColor() assertEquals AndesTextViewColor.Primary
        textView.bodyBolds assertIsNull true
        textView.bodyLinks assertIsNull true
        textView.isLinkColorInverted assertEquals false
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

        val hasBodyBoldSpan = textView.text.getSpans().stream().anyMatch {
            it is StyleSpan
        }

        val hasBodyLinkSpan = textView.text.getSpans().stream().anyMatch {
            it is StyleSpan
        }

        Assert.assertTrue(hasBodyBoldSpan)
        Assert.assertTrue(hasBodyLinkSpan)
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
        private const val FINAL_PRIMARY_TEXT_COLOR = -436207616
    }
}
