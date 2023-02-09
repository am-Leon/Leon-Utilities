package am.leon.utilities.android.extentions

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import java.util.regex.Pattern


@SuppressLint("ObsoleteSdkInt")
@Suppress("DEPRECATION")
fun String?.getValueFromHtml(): String? {
    if (this == null || this == "")
        return null

    return this.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT).toString()
        else
            Html.fromHtml(it).toString()
    }
}

fun String.getPhoneWithNoZeroAtFirst(): String {
    var phoneAsEnglish = arabicNumbersToEn()

    if (phoneAsEnglish.startsWith("0"))
        phoneAsEnglish = phoneAsEnglish.replaceFirst("0", "")

    return phoneAsEnglish
}

fun String.removeNumber(): String {
    var str = this
    if (str.isNotEmpty())
        str = str.substring(0, str.length - 1)
    return str
}

fun String.getAmountFormatted(): String = if (this.contains('.')) {
    when {
        (this.length - 1) - this.indexOf('.') == 2 -> this
        (this.length - 1) - this.indexOf('.') == 1 -> this.plus(
            '0'
        )
        else -> this.plus("00")
    }
} else
    this.plus(".00")

fun String.arabicNumbersToEn(): String {
    val chars = CharArray(length)
    for (i in indices) {
        var ch = this[i]
        if (ch.code in 0x0660..0x0669)
            ch -= 0x0660 - '0'.code.toByte()
        else if (ch.code in 0x06f0..0x06F9)
            ch -= 0x06f0 - '0'.code.toByte()
        chars[i] = ch
    }
    return String(chars)
}

fun String.isValidAsVersionNumber(): Boolean {
    val p =
        Pattern.compile("^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\\+([0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?\$")
    val m = p.matcher(this)
    return m.matches()
}

fun String.toByteArray(): ByteArray {
    val len = length
    val data = ByteArray(len / 2)
    var i = 0
    while (i < len) {
        data[i / 2] =
            ((Character.digit(this[i], 16) shl 4) + Character.digit(this[i + 1], 16)).toByte()
        i += 2
    }
    return data
}
