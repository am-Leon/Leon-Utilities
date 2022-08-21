package am.leon.utilities.extentions

import am.leon.utilities.BuildConfig
import android.os.Build
import android.text.Html
import android.util.Log


@Suppress("DEPRECATION")
fun String?.getValueFromHtml(): String? {
    if (this.isNullOrEmpty())
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

fun String?.toLog(tag: String = BuildConfig.LIBRARY_PACKAGE_NAME) {
    if (BuildConfig.DEBUG)
        Log.e(tag, ">> $this")
}

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
