package com.mercadolibre.android.andesui.snackbar.duration

/**
 * Defines all types related properties that an [AndesSnackbar] needs to be drawn properly.
 * Those properties change depending on the style of the tag.
 */
internal interface AndesSnackbarDurationInterface {

    /**
     * Returns a duration for the snackbar.
     */
    fun duration(): Int
}

internal class AndesSnackbarShortDuration : AndesSnackbarDurationInterface {
    override fun duration() = 3000
}

internal class AndesSnackbarNormalDuration : AndesSnackbarDurationInterface {
    override fun duration() = 6000
}

internal class AndesSnackbarLongDuration : AndesSnackbarDurationInterface {
    override fun duration() = 10000
}

internal class AndesSnackbarInfiniteDuration : AndesSnackbarDurationInterface {
    override fun duration() = Int.MAX_VALUE
}
