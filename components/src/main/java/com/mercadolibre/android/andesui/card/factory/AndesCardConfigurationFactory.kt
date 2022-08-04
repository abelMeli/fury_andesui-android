package com.mercadolibre.android.andesui.card.factory

import android.content.Context
import android.graphics.Typeface
import android.view.View
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.card.bodyPadding.AndesCardBodyPadding
import com.mercadolibre.android.andesui.card.hierarchy.AndesCardHierarchy
import com.mercadolibre.android.andesui.card.padding.AndesCardPadding
import com.mercadolibre.android.andesui.card.padding.AndesCardPaddingInterface
import com.mercadolibre.android.andesui.card.style.AndesCardStyleInterface
import com.mercadolibre.android.andesui.card.type.AndesCardType
import com.mercadolibre.android.andesui.card.type.AndesCardTypeInterface
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

internal data class AndesCardConfiguration(
    val title: String?,
    val titleVisibility: Int,
    val titleSize: Float,
    val titleTypeface: Typeface,
    val titleColor: AndesColor,
    val titleHeight: Int,
    val titlePadding: Int,
    val bodyPadding: AndesCardBodyPadding,
    val elevation: Float,
    val pipeColor: AndesColor,
    val linkColor: AndesColor,
    val pipeVisibility: Int,
    val groupLinkVisibility: Int
)

internal object AndesCardConfigurationFactory {

    fun create(context: Context, andesCardAttrs: AndesCardAttrs): AndesCardConfiguration {
        return with(andesCardAttrs) {
            AndesCardConfiguration(
                    title = andesCardAttrs.andesCardTitle,
                    titleVisibility = resolveTitleVisibility(andesCardAttrs.andesCardTitle),
                    titleSize = resolveTitleSize(context),
                    titleTypeface = resolveTitleTypeface(context),
                    titleColor = resolveTitleColor(),
                    titleHeight = resolveTitleHeight(context, andesCardAttrs.andesCardPadding.padding),
                    titlePadding = resolveLateralMargin(context, andesCardAttrs.andesCardPadding),
                    bodyPadding = andesCardAttrs.andesCardBodyPadding,
                    elevation = resolveElevation(
                            context, andesCardAttrs.andesCardHierarchy, andesCardAttrs.andesCardStyle.style
                    ),
                    pipeColor = resolvePipeColor(andesCardAttrs.andesCardType.type),
                    linkColor = resolveLinkColor(),
                    pipeVisibility = resolvePipeVisibility(andesCardAttrs.andesCardType, andesCardAttrs.andesCardHierarchy),
                    groupLinkVisibility = resolverGroupLinkVisibility(andesCardAttrs.linkText, andesCardAttrs.andesCardHierarchy)
            )
        }
    }

    private fun resolveTitleSize(context: Context) = context.resources.getDimension(R.dimen.andes_card_title_size)
    private fun resolveTitleTypeface(context: Context) = context.getFontOrDefault(R.font.andes_font_semibold)
    private fun resolveTitleColor() = R.color.andes_text_color_primary.toAndesColor()
    private fun resolveTitleHeight(context: Context, padding: AndesCardPaddingInterface) = padding.titleHeight(context)
    private fun resolveLateralMargin(context: Context, padding: AndesCardPadding): Int {
        return if (padding == AndesCardPadding.NONE) {
            AndesCardPadding.SMALL.padding.paddingSize(context)
        } else {
            padding.padding.paddingSize(context)
        }
    }
    private fun resolveElevation(
        context: Context,
        hierarchy: AndesCardHierarchy,
        style: AndesCardStyleInterface
    ) = style.elevation(context, hierarchy)
    private fun resolvePipeColor(type: AndesCardTypeInterface) = type.primaryColor()
    private fun resolveLinkColor() = R.color.andes_accent_color_500.toAndesColor()
    private fun resolvePipeVisibility(type: AndesCardType, hierarchy: AndesCardHierarchy): Int {
        return if (hierarchy == AndesCardHierarchy.PRIMARY)
            type.type.pipeVisibility()
        else
            View.GONE
    }
    private fun resolveTitleVisibility(title: String?): Int {
        return if (title.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun resolverGroupLinkVisibility(linkText: String?, hierarchy: AndesCardHierarchy): Int {
        return if (linkText.isNullOrEmpty() || hierarchy != AndesCardHierarchy.PRIMARY) {
            View.GONE
        } else {
            View.VISIBLE
        }        }
}
