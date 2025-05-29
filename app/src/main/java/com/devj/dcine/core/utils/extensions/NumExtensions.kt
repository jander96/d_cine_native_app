package com.devj.dcine.core.utils.extensions
import android.icu.text.DecimalFormat

/**
 *  1K = 1000, 1M = 1000000, 1B = 1000000000
 *  */
fun Int.compact(): String {

    return when {
        this < 1_000 -> this.toString()
        this < 1_000_000 -> "${this / 1_000}K"
        this < 1_000_000_000 -> "${this / 1_000_000}M"
        else -> "${this / 1_000_000_000}B"
    }
}
fun Double.toStringWithDecimal(decimalPlaces: Int = 2): String {
    val df = DecimalFormat("#." + "#".repeat(decimalPlaces))
    return df.format(this)
}