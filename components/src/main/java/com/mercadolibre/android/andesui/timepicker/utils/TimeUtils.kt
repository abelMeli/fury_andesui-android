package com.mercadolibre.android.andesui.timepicker.utils

internal object TimeUtils {
    val HOURS_SHORT = arrayListOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")

    val HOURS_LONG = arrayListOf("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
        "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23")

    val MINUTES_5 = arrayListOf("00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55")

    val MINUTES_10 = arrayListOf("00", "10", "20", "30", "40", "50")

    val MINUTES_15 = arrayListOf("00", "15", "30", "45")

    val MINUTES_30 = arrayListOf("00", "30")

    val MINUTES_60 = arrayListOf("00")

    /**
     * creates a simple list with the hours and minutes unified in a single string
     */
    internal fun createFullList(hours: ArrayList<String>, minutes: ArrayList<String>): ArrayList<String> {
        val result = arrayListOf<String>()
        hours.forEach { hour ->
            minutes.forEach { minutes ->
                result.add("$hour:$minutes")
            }
        }
        return result
    }
}
