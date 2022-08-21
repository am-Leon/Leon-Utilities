package am.leon.utilities.editText

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class TextInputEditTextMoneyFilter : TextInputEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        filters = arrayOf<InputFilter>(MoneyValueFilter())
    }
}