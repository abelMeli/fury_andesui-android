package com.mercadolibre.android.andesui.textview.style

/**
 * Class with the different style values the [com.mercadolibre.android.andesui.textview.AndesTextView]
 * can take. This enum has the values for the text size, the font value, the line height and the text weight.
 */
sealed class AndesTextViewStyle(internal val style: AndesTextViewStyleInterface) {
    object TitleXl : AndesTextViewStyle(AndesTextViewStyleTitleXl)
    object TitleL : AndesTextViewStyle(AndesTextViewStyleTitleL)
    object TitleM : AndesTextViewStyle(AndesTextViewStyleTitleM)
    object TitleS : AndesTextViewStyle(AndesTextViewStyleTitleS)
    object TitleXs : AndesTextViewStyle(AndesTextViewStyleTitleXs)
    object BodyL : AndesTextViewStyle(AndesTextViewStyleBodyL)
    object BodyM : AndesTextViewStyle(AndesTextViewStyleBodyM)
    object BodyS : AndesTextViewStyle(AndesTextViewStyleBodyS)
    object BodyXs : AndesTextViewStyle(AndesTextViewStyleBodyXs)
    internal data class Custom(
        private val fontValue: Int,
        private val lineHeight: Float,
        private val textSize: Float
    ) : AndesTextViewStyle(AndesTextViewStyleCustom(fontValue, lineHeight, textSize))
}
