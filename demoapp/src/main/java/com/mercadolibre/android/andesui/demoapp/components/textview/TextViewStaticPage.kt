package com.mercadolibre.android.andesui.demoapp.components.textview

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticTextviewBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.moneyamount.AndesMoneyAmount
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.decimalstyle.AndesMoneyAmountDecimalsStyle
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBold
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBolds
import com.mercadolibre.android.andesui.textview.style.AndesTextViewStyle

class TextViewStaticPage {


    fun create(context: Context, containerView: View) {
        AndesuiStaticTextviewBinding.bind(containerView).apply {
            setupPrimaryLink(context)
            setupPositiveLink(context)
            setupInvertedLink(context)
            setupPrimaryBold()
            setupPositiveBold()
            setupCautionBoldLink(context)
            setupMultiLink(context)
            setupSpecsButton()
            setupMoneyAmountTextRegular(context)
            setupMoneyAmountTextSemibold(context)
        }
    }

    private fun AndesuiStaticTextviewBinding.setupMoneyAmountTextRegular(context: Context) {
        staticPageTextviewMoneyamountRegular.apply {
            style = AndesTextViewStyle.BodyM
            append("AndesTextView price: ")
            append(
                AndesMoneyAmount(
                    context = context,
                    amount = 123.45,
                    currency = AndesMoneyAmountCurrency.BRL,
                    size = AndesMoneyAmountSize.SIZE_14
                ),
                null
            )
            append(". Such a good offer!")
        }
    }

    private fun AndesuiStaticTextviewBinding.setupMoneyAmountTextSemibold(context: Context) {
        staticPageTextviewMoneyamountSemibold.apply {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.andes_text_size_24))
            append("AndesTextView price: ")
            append(
                AndesMoneyAmount(
                    context = context,
                    amount = 123.45,
                    currency = AndesMoneyAmountCurrency.BRL,
                    size = AndesMoneyAmountSize.SIZE_24,
                    style = AndesMoneyAmountDecimalsStyle.SUPERSCRIPT
                ).apply {
                    semiBold = true
                },
                null
            )
            append(". Such a good offer!")
            bodyBolds = AndesBodyBolds(listOf(
                AndesBodyBold(text.indexOf("good offer"), text.length)
            ))
        }
    }

    private fun AndesuiStaticTextviewBinding.setupSpecsButton() {
        andesuiDemoappAndesTextviewSpecsButton.setOnClickListener {
            launchSpecs(it.context, AndesSpecs.TEXTVIEW)
        }
    }

    private fun AndesuiStaticTextviewBinding.setupCautionBoldLink(context: Context) {
        val bodyLink = AndesBodyLinks(
            links = listOf(
                AndesBodyLink(BODY_LINK_FROM_THIRD, BODY_LINK_TO_THIRD)
            ),
            listener = {
                Toast.makeText(context.applicationContext, "Click in caution link", Toast.LENGTH_SHORT).show()
            }
        )
        val bodyBold = AndesBodyBolds(
            listOf(
                AndesBodyBold(BODY_BOLD_FROM_FIRST, BODY_BOLD_TO_FIRST)
            )
        )
        staticPageTextviewRangeCautionBoldAndLink.bodyLinks = bodyLink
        staticPageTextviewRangeCautionBoldAndLink.bodyBolds = bodyBold
    }

    private fun AndesuiStaticTextviewBinding.setupPositiveBold() {
        val bodyBold = AndesBodyBolds(
            listOf(
                AndesBodyBold(BODY_BOLD_FROM_SECOND, BODY_BOLD_TO_SECOND)
            )
        )
        staticPageTextviewRangePositiveBold.bodyBolds = bodyBold
    }

    private fun AndesuiStaticTextviewBinding.setupPrimaryBold() {
        val bodyBold = AndesBodyBolds(
            listOf(
                AndesBodyBold(BODY_BOLD_FROM_FIRST, BODY_BOLD_TO_FIRST)
            )
        )
        staticPageTextviewRangePrimaryBold.bodyBolds = bodyBold
    }

    private fun AndesuiStaticTextviewBinding.setupInvertedLink(context: Context) {
        val bodyLink = AndesBodyLinks(
            links = listOf(
                AndesBodyLink(BODY_LINK_FROM_SECOND, BODY_LINK_TO_SECOND)
            ),
            listener = {
                Toast.makeText(context.applicationContext, "Click in inverted link", Toast.LENGTH_SHORT).show()
            }
        )
        staticPageTextviewRangeInvertedLink.bodyLinks = bodyLink
    }

    private fun AndesuiStaticTextviewBinding.setupPositiveLink(context: Context) {
        val bodyLink = AndesBodyLinks(
            links = listOf(
                AndesBodyLink(BODY_LINK_FROM_SECOND, BODY_LINK_TO_SECOND)
            ),
            listener = {
                Toast.makeText(context.applicationContext, "Click in positive link", Toast.LENGTH_SHORT).show()
            }
        )
        staticPageTextviewRangePositiveLink.bodyLinks = bodyLink
    }

    private fun AndesuiStaticTextviewBinding.setupPrimaryLink(context: Context) {
        val bodyLink = AndesBodyLinks(
            links = listOf(
                AndesBodyLink(BODY_LINK_FROM_FIRST, BODY_LINK_TO_FIRST)
            ),
            listener = {
                Toast.makeText(context.applicationContext, "Click in primary link", Toast.LENGTH_SHORT).show()
            }
        )
        staticPageTextviewRangePrimaryLink.bodyLinks = bodyLink
    }

    private fun AndesuiStaticTextviewBinding.setupMultiLink(context: Context) {
        val bodyLinks = AndesBodyLinks(
            links = listOf(
                AndesBodyLink(MULTI_LINK_FROM_FIRST, MULTI_LINK_TO_FIRST),
                AndesBodyLink(MULTI_LINK_FROM_SECOND, MULTI_LINK_TO_SECOND),
                AndesBodyLink(MULTI_LINK_FROM_THIRD, MULTI_LINK_TO_THIRD)
            ),
            listener = { index ->
                when (index) {
                    0 -> Toast.makeText(context.applicationContext, "Ir a Especificaciones", Toast.LENGTH_SHORT).show()
                    1 -> Toast.makeText(context.applicationContext, "Ir a Términos y Condiciones", Toast.LENGTH_SHORT).show()
                    2 -> Toast.makeText(context.applicationContext, "Ir a Mi Perfil", Toast.LENGTH_SHORT).show()
                }
            }
        )
        staticPageTextviewExampleMultipleLinks.bodyLinks = bodyLinks
    }

    private companion object {
        private const val BODY_LINK_FROM_FIRST = 28
        private const val BODY_LINK_FROM_SECOND = 29
        private const val BODY_LINK_TO_FIRST = 37
        private const val BODY_LINK_TO_SECOND = 38
        private const val BODY_LINK_FROM_THIRD = 38
        private const val BODY_LINK_TO_THIRD = 46
        private const val BODY_BOLD_FROM_FIRST = 28
        private const val BODY_BOLD_TO_FIRST = 37
        private const val BODY_BOLD_FROM_SECOND = 29
        private const val BODY_BOLD_TO_SECOND = 38
        private const val MULTI_LINK_FROM_FIRST = 63
        private const val MULTI_LINK_TO_FIRST = 79
        private const val MULTI_LINK_FROM_SECOND = 86
        private const val MULTI_LINK_TO_SECOND = 108
        private const val MULTI_LINK_FROM_THIRD = 166
        private const val MULTI_LINK_TO_THIRD = 175
    }
}
