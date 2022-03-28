package com.mercadolibre.android.andesui.errorcomponent

/**
 * Interface used by components that show errors with
 * business context.
 */
interface AndesErrorComponent {
    /**
     * Return an error code to show in the component.
     */
    fun getErrorCode(): String?

    /**
     * Method called after component creation.
     */
    fun onViewCreated(): () -> Unit
}