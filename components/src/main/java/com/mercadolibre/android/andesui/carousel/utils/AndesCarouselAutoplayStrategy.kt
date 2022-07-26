package com.mercadolibre.android.andesui.carousel.utils

import android.provider.Settings
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.android.andesui.carousel.factory.AndesCarouselConfiguration
import com.mercadolibre.android.andesui.utils.settingsEnabled
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal interface AndesCarouselAutoplayStrategy {
    fun execute(
        autoplayJob: Job?,
        config: AndesCarouselConfiguration,
        componentCoroutineScope: CoroutineScope,
        recyclerView: RecyclerView,
        carouselSize: Int,
        isAccessibilityOn: () -> Boolean
    ): Job?
}

internal object AndesCarouselAutoplayOn : AndesCarouselAutoplayStrategy {
    /**
     * Settings to validate before run, Setting - default value.
     */
    var a11ySettingsValidate = mapOf(
        Settings.Global.ANIMATOR_DURATION_SCALE to 1.0f,
        Settings.Global.TRANSITION_ANIMATION_SCALE to 1.0f,
        Settings.Global.WINDOW_ANIMATION_SCALE to 1.0f
    )

    override fun execute(
        autoplayJob: Job?,
        config: AndesCarouselConfiguration,
        componentCoroutineScope: CoroutineScope,
        recyclerView: RecyclerView,
        carouselSize: Int,
        isAccessibilityOn: () -> Boolean
    ): Job? {
        autoplayJob?.cancel()
        var autoplayPosition: Int
        val settingsEnabled = recyclerView.context.settingsEnabled(a11ySettingsValidate)
        return componentCoroutineScope.launch {
            if (settingsEnabled && config.infinite) {
                autoplayPosition = 0
                while (autoplayPosition <= carouselSize && !isAccessibilityOn()) {
                    delay(config.autoplaySpeed)
                    if (autoplayPosition >= carouselSize) {
                        autoplayPosition = 0
                    }
                    withContext(Dispatchers.Main) {
                        recyclerView.smoothScrollToPosition(autoplayPosition)
                    }
                    autoplayPosition += 1
                }
            }
        }
    }
}

internal object AndesCarouselAutoplayOff : AndesCarouselAutoplayStrategy {
    override fun execute(
        autoplayJob: Job?,
        config: AndesCarouselConfiguration,
        componentCoroutineScope: CoroutineScope,
        recyclerView: RecyclerView,
        carouselSize: Int,
        isAccessibilityOn: () -> Boolean
    ): Job? {
        autoplayJob?.cancel()
        return null
    }
}
