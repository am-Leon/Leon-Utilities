package am.leon.utilities.android.extentions

import android.graphics.Color
import java.text.SimpleDateFormat
import java.util.*

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

fun Long.getRemainingTime(): String {
    val hours: Int = ((this / 1000) / 3600).toInt()
    val minutes: Int = (((this / 1000) % 3600) / 60).toInt()
    val seconds: Int = ((this / 1000) % 60).toInt()

    return if (hours > 0) {
        String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }
}
