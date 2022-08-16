package com.mercadolibre.android.andesui.badge.border

import android.content.Context
import com.mercadolibre.android.andesui.badge.size.AndesBadgeSizeInterface

/**
 * Defines all borders related properties that an [AndesBadge] needs to be drawn properly.
 * Those properties change depending on the border of the badge.
 *
 */
internal interface AndesBadgeBorderInterface {

    /**
     * Returns a [Float] representing the up start corner radius to be used.
     *
     * @param size the size of the badge.
     * @param context needed for accessing some resources.
     * @return a [Float] representing the corner radius to be used.
     */
    fun upStartCornerRadius(size: AndesBadgeSizeInterface, context: Context): Float =
        NO_ROUND_RADIUS

    /**
     * Returns a [Float] representing the up end corner radius to be used.
     *
     * @param size the size of the badge.
     * @param context needed for accessing some resources.
     * @return a [Float] representing the corner radius to be used.
     */
    fun upEndCornerRadius(size: AndesBadgeSizeInterface, context: Context): Float = NO_ROUND_RADIUS

    /**
     * Returns a [Float] representing the bottom start corner radius to be used.
     *
     * @param size the size of the badge.
     * @param context needed for accessing some resources.
     * @return a [Float] representing the corner radius to be used.
     */
    fun bottomStartCornerRadius(size: AndesBadgeSizeInterface, context: Context): Float =
        NO_ROUND_RADIUS

    /**
     * Returns a [Float] representing the bottom end corner radius to be used.
     *
     * @param size the size of the badge.
     * @param context needed for accessing some resources.
     * @return a [Float] representing the corner radius to be used.
     */
    fun bottomEndCornerRadius(size: AndesBadgeSizeInterface, context: Context): Float =
        NO_ROUND_RADIUS

    companion object {
        private const val NO_ROUND_RADIUS = 0F
    }
}

/**
 * Implementation of [AndesBadgeBorderInterface] that returns the required data but personalized for the Corner Border,
 * according to Andes specifications.
 *
 */
internal class AndesCornerBadgeBorder : AndesBadgeBorderInterface {

    override fun upEndCornerRadius(size: AndesBadgeSizeInterface, context: Context) =
        size.cornerRadius(context)

    override fun bottomStartCornerRadius(size: AndesBadgeSizeInterface, context: Context) =
        size.cornerRadius(context)
}

/**
 * Implementation of [AndesBadgeBorderInterface] that returns the required data but personalized for the Rounded Border,
 * according to Andes specifications.
 *
 */
internal class AndesRoundedBadgeBorder : AndesBadgeBorderInterface {

    override fun bottomStartCornerRadius(size: AndesBadgeSizeInterface, context: Context) =
        size.cornerRadius(context)
}

/**
 * Implementation of [AndesBadgeBorderInterface] that returns the required data but personalized for the Rounded Right Border,
 * according to Andes specifications.
 *
 */
internal class AndesRoundedRightBadgeBorder : AndesBadgeBorderInterface {

    override fun bottomEndCornerRadius(size: AndesBadgeSizeInterface, context: Context): Float =
        size.cornerRadius(context)
}

/**
 * Implementation of [AndesBadgeBorderInterface] that returns the required data but personalized for the Corner Left Border,
 * according to Andes specifications.
 *
 */
internal class AndesCornerLeftBadgeBorder : AndesBadgeBorderInterface {

    override fun upStartCornerRadius(size: AndesBadgeSizeInterface, context: Context): Float =
        size.cornerRadius(context)

    override fun bottomEndCornerRadius(size: AndesBadgeSizeInterface, context: Context): Float =
        size.cornerRadius(context)
}

/**
 * Implementation of [AndesBadgeBorderInterface] that returns the required data but personalized for the Standard Border,
 * according to Andes specifications.
 *
 */
internal class AndesStandardBadgeBorder : AndesBadgeBorderInterface {

    override fun upStartCornerRadius(size: AndesBadgeSizeInterface, context: Context) =
        size.cornerRadius(context)

    override fun upEndCornerRadius(size: AndesBadgeSizeInterface, context: Context) =
        size.cornerRadius(context)

    override fun bottomStartCornerRadius(size: AndesBadgeSizeInterface, context: Context) =
        size.cornerRadius(context)

    override fun bottomEndCornerRadius(size: AndesBadgeSizeInterface, context: Context) =
        size.cornerRadius(context)
}
