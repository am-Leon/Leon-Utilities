package am.leon.utilities.android.helpers.components.editText

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern

class MoneyValueFilter : InputFilter {

    private val pattern = Pattern.compile("(([1-9])(\\d{0,4})?)?(\\.\\d{0,2})?").toRegex()

    override fun filter(
        source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int
    ): CharSequence? {
        val builder = StringBuilder(dest!!)

        builder.replace(dstart, dend, source?.subSequence(start, end).toString())

        return if (!builder.toString().matches(pattern)) {
            if (source!!.isEmpty()) dest.subSequence(dstart, dend) else source.toString()
                .substring(0, source.length - 1)
        } else null
    }
}