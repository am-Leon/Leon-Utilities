package am.leon.utilities.editText

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.drawable.DrawableCompat
import am.leon.utilities.R
import am.leon.utilities.extentions.getColorFromRes
import com.google.android.material.textfield.TextInputEditText

class TextInputEditTextIconColorChangeable : TextInputEditText, View.OnFocusChangeListener {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        onFocusChangeListener = this
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (compoundDrawablesRelative.isEmpty() || compoundDrawablesRelative[0] == null)
            return

        val unwrappedDrawable: Drawable = compoundDrawablesRelative[0]
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable)
        if (hasFocus) {
            DrawableCompat.setTint(
                wrappedDrawable, context.getColorFromRes(R.color.colorInputEditTextIconActive)
            )
        } else
            DrawableCompat.setTint(
                wrappedDrawable, context.getColorFromRes(R.color.colorInputEditTextIconInActive)
            )
    }
}