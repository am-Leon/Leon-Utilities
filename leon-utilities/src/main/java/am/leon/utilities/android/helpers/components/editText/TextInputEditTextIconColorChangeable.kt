package am.leon.utilities.android.helpers.components.editText

import am.leon.utilities.R
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.textfield.TextInputEditText

class TextInputEditTextIconColorChangeable : TextInputEditText, View.OnFocusChangeListener {

    private var focusedColor = Color.BLUE
    private var inactiveColor = Color.GREEN

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        onFocusChangeListener = this

        if (attrs == null)
            return

        context.obtainStyledAttributes(attrs, R.styleable.TextInputEditTextIconColorChangeable)
            .apply {
                focusedColor = getColor(
                    R.styleable.TextInputEditTextIconColorChangeable_leon_focused, focusedColor
                )
                inactiveColor = getColor(
                    R.styleable.TextInputEditTextIconColorChangeable_leon_inactive, inactiveColor
                )
                recycle()
            }
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (compoundDrawablesRelative.isEmpty() || compoundDrawablesRelative[0] == null)
            return

        val unwrappedDrawable: Drawable = compoundDrawablesRelative[0]
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable)
        if (hasFocus) {
            DrawableCompat.setTint(wrappedDrawable, focusedColor)
        } else
            DrawableCompat.setTint(wrappedDrawable, inactiveColor)
    }
}