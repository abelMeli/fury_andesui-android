package com.mercadolibre.android.andesui.buttonprogress.factory

import android.view.View
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchyInterface
import com.mercadolibre.android.andesui.button.hierarchy.AndesTransparentButtonHierarchy

internal data class AndesButtonProgressConfiguration(
    var from: Int,
    var to: Int,
    var duration: Long,
    var andesButtonHierarchy: AndesButtonHierarchyInterface,
    var isProgressAnimationAllowed: Boolean = true,
    var progressVisibility: Int = View.VISIBLE
) {
    init {
        updateConfig()
    }

    fun updateConfig() {
        isProgressAnimationAllowed = andesButtonHierarchy !is AndesTransparentButtonHierarchy
        progressVisibility = if (andesButtonHierarchy is AndesTransparentButtonHierarchy) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}

internal object AndesButtonProgressConfigurationFactory {

    fun create(
        from: Int,
        to: Int,
        duration: Long,
        andesButtonHierarchy: AndesButtonHierarchyInterface
    ): AndesButtonProgressConfiguration {

        return AndesButtonProgressConfiguration(
            from = from,
            to = to,
            duration = duration,
            andesButtonHierarchy = andesButtonHierarchy
        )
    }
}
