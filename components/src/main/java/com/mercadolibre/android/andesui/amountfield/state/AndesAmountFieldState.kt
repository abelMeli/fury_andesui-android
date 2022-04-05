package com.mercadolibre.android.andesui.amountfield.state

/**
 * State values the component can take. This states manage different visual attributes, such as
 * the helper text color, the presence/absence of the warning icon in the helper, the error animations,
 * among others.
 */
sealed class AndesAmountFieldState(internal val state: AndesAmountFieldStateInterface) {
    object Idle : AndesAmountFieldState(AndesAmountFieldIdleState)
    object Error : AndesAmountFieldState(AndesAmountFieldErrorState())
    internal object AmountExceeded : AndesAmountFieldState(AndesAmountFieldAmountExceededState)
}
