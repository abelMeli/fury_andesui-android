package com.mercadolibre.android.andesui.snackbar.callback

import com.google.android.material.snackbar.Snackbar

/**
 * Callback class for [AndesSnackbar] instances.
 *
 * If set, AndesSnackbar will call each of those methods in the proper moment.
 * Consumer of this should specify what to do in each case.
 */
abstract class AndesSnackbarCallback: Snackbar.Callback() {
    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
        onSnackbarDismissed()
    }

    override fun onShown(sb: Snackbar?) {
        this.onSnackbarShown()
    }

    /**
     * Invoked when AndesSnackbar is dismissed.
     */
    abstract fun onSnackbarDismissed()

    /**
     * Invoked when AndesSnackbar is shown.
     */
    abstract fun onSnackbarShown()
}