package com.mercadolibre.android.andesui.buttonprogress.status

import com.mercadolibre.android.andesui.button.AndesButton

/**
 * Utility class that does 5 things: Defines the progress action to process in a [AndesButton].
 *
 * You ask me with, let's say 'START', and then the animation progress in the Button starts.
 * You ask me with, let's say 'PAUSE', and then the animation progress in the Button pauses.
 * You ask me with, let's say 'RESUME', and then the animation progress in the Button resumes if it was paused befor.
 * You ask me with, let's say 'CANCEL', and then the animation progress in the Button cancels.
 *
 */
enum class AndesButtonProgressAction {
    IDLE,
    START,
    PAUSE,
    RESUME,
    CANCEL;
}
