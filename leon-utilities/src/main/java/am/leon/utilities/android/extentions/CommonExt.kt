package am.leon.utilities.android.extentions

import android.graphics.Color
import android.location.Location
import android.text.Editable
import android.util.Base64
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun <T> notNull(argument: T?, name: String): T {
    requireNotNull(argument) { "$name may not be null" }
    return argument
}

fun getColorFromString(color: String?): Int {
    if (color == null) return 0
    return try {
        Color.parseColor(color)
    } catch (e: Exception) {
        0
    }
}

fun Double.formatDecimalByPattern(
    pattern: String = "#00.00", locale: Locale = Locale.US
): String {
    val decimalFormat = DecimalFormat(pattern, DecimalFormatSymbols(locale))
    return decimalFormat.format(this)
}

fun ByteArray.base64Encode(): String {
    return Base64.encodeToString(this, Base64.NO_WRAP)
}

fun String.base64Decode(): ByteArray {
    return Base64.decode(this, Base64.NO_WRAP)
}

fun ByteArray.clear() {
    fill(0)
}

fun CharArray.clear() {
    fill('0')
}

fun Location.toFormattedValue(): String {
    return "$latitude/$longitude"
}

fun Editable.toCharArray(): CharArray {
    val chars = CharArray(length)
    for (i in indices) {
        chars[i] = get(i)
    }
    return chars
}
