package com.mercadolibre.android.andesui.demoapp.components.textview

import android.content.Context
import android.view.View
import android.widget.Toast
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticTextviewBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBold
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBolds

class TextViewStaticPage {

    private lateinit var primaryLink: AndesTextView
    private lateinit var positiveLink: AndesTextView
    private lateinit var invertedLink: AndesTextView
    private lateinit var primaryBold: AndesTextView
    private lateinit var positiveBold: AndesTextView
    private lateinit var cautionBoldLink: AndesTextView
    private lateinit var multiLink: AndesTextView
    private lateinit var textViewSpecs: AndesButton

    fun create(context: Context, containerView: View) {
        initComponents(containerView)
        setupPrimaryLink(context)
        setupPositiveLink(context)
        setupInvertedLink(context)
        setupPrimaryBold()
        setupPositiveBold()
        setupCautionBoldLink(context)
        setupMultiLink(context)
        setupSpecsButton()
    }

    private fun setupSpecsButton() {
        textViewSpecs.setOnClickListener {
            launchSpecs(it.context, AndesSpecs.TEXTVIEW)
        }
    }

    private fun setupCautionBoldLink(context: Context) {
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
        cautionBoldLink.bodyLinks = bodyLink
        cautionBoldLink.bodyBolds = bodyBold
    }

    private fun setupPositiveBold() {
        val bodyBold = AndesBodyBolds(
            listOf(
                AndesBodyBold(BODY_BOLD_FROM_SECOND, BODY_BOLD_TO_SECOND)
            )
        )
        positiveBold.bodyBolds = bodyBold
    }

    private fun setupPrimaryBold() {
        val bodyBold = AndesBodyBolds(
            listOf(
                AndesBodyBold(BODY_BOLD_FROM_FIRST, BODY_BOLD_TO_FIRST)
            )
        )
        primaryBold.bodyBolds = bodyBold
    }

    private fun setupInvertedLink(context: Context) {
        val bodyLink = AndesBodyLinks(
            links = listOf(
                AndesBodyLink(BODY_LINK_FROM_SECOND, BODY_LINK_TO_SECOND)
            ),
            listener = {
                Toast.makeText(context.applicationContext, "Click in inverted link", Toast.LENGTH_SHORT).show()
            }
        )
        invertedLink.bodyLinks = bodyLink
    }

    private fun setupPositiveLink(context: Context) {
        val bodyLink = AndesBodyLinks(
            links = listOf(
                AndesBodyLink(BODY_LINK_FROM_SECOND, BODY_LINK_TO_SECOND)
            ),
            listener = {
                Toast.makeText(context.applicationContext, "Click in positive link", Toast.LENGTH_SHORT).show()
            }
        )
        positiveLink.bodyLinks = bodyLink
    }

    private fun setupPrimaryLink(context: Context) {
        val bodyLink = AndesBodyLinks(
            links = listOf(
                AndesBodyLink(BODY_LINK_FROM_FIRST, BODY_LINK_TO_FIRST)
            ),
            listener = {
                Toast.makeText(context.applicationContext, "Click in primary link", Toast.LENGTH_SHORT).show()
            }
        )
        primaryLink.bodyLinks = bodyLink
    }

    private fun setupMultiLink(context: Context) {
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
        multiLink.bodyLinks = bodyLinks
    }

    private fun initComponents(containerView: View) {
        val binding = AndesuiStaticTextviewBinding.bind(containerView)
        primaryLink = binding.staticPageTextviewRangePrimaryLink
        positiveLink = binding.staticPageTextviewRangePositiveLink
        invertedLink = binding.staticPageTextviewRangeInvertedLink
        primaryBold = binding.staticPageTextviewRangePrimaryBold
        positiveBold = binding.staticPageTextviewRangePositiveBold
        cautionBoldLink = binding.staticPageTextviewRangeCautionBoldAndLink
        textViewSpecs = binding.andesuiDemoappAndesTextviewSpecsButton
        multiLink = binding.staticPageTextviewExampleMultipleLinks
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
