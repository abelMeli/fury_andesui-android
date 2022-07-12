package com.mercadolibre.android.andesui.modal

import android.view.View
import com.mercadolibre.android.andesui.modal.card.builder.AndesModalCardCarouselBuilder
import com.mercadolibre.android.andesui.modal.card.builder.AndesModalCardDefaultBuilder
import com.mercadolibre.android.andesui.modal.card.builder.AndesModalCardViewBuilder
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.full.builder.AndesModalFullCarouselBuilder
import com.mercadolibre.android.andesui.modal.full.builder.AndesModalFullCustomViewBuilder
import com.mercadolibre.android.andesui.modal.full.builder.AndesModalFullDefaultBuilder

/**
 * Provides all the builders to create each modal variant.
 */
object AndesModal {

    /**
     * Builder to create a default card modal (single content).
     */
    fun cardBuilder(content: AndesModalContent) = AndesModalCardDefaultBuilder(content)

    /**
     * Builder to create a carousel card modal (paginated contents).
     */
    fun cardBuilder(contentList: ArrayList<AndesModalContent>) = AndesModalCardCarouselBuilder(contentList)

    /**
     * Builder to create a card modal with a custom view.
     */
    fun cardBuilder(customView: View): AndesModalCardViewBuilder = AndesModalCardViewBuilder(customView)

    /**
     * Builder to create a default full-screen modal (single content).
     */
    fun fullBuilder(content: AndesModalContent): AndesModalFullDefaultBuilder = AndesModalFullDefaultBuilder(content)

    /**
     * Builder to create a carousel full-screen modal (paginated contents).
     */
    fun fullBuilder(contentList: ArrayList<AndesModalContent>) = AndesModalFullCarouselBuilder(contentList)

    /**
     * Builder to create a full-screen modal with a custom view.
     */
    fun fullBuilder(view: View) = AndesModalFullCustomViewBuilder(view)
}
