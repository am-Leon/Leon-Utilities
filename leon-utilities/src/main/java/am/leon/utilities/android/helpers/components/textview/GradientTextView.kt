package am.leon.utilities.android.helpers.components.textview

import am.leon.utilities.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import com.google.android.material.textview.MaterialTextView

class GradientTextView : MaterialTextView {

    private var isVertical = false
    private var startColor = Color.BLUE
    private var endColor = Color.GREEN

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
        if (attrs != null) {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView)
            isVertical = attributes.getBoolean(R.styleable.GradientTextView_leon_isVertical, false)
            startColor = attributes.getColor(R.styleable.GradientTextView_leon_endColor, startColor)
            endColor = attributes.getColor(R.styleable.GradientTextView_leon_startColor, endColor)
            attributes.recycle()
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            val paint: Paint = paint
            val width = paint.measureText(text.toString())
            if (isVertical) {
                getPaint().shader = LinearGradient(
                    0F, 0F, width, lineHeight.toFloat(),
                    endColor, startColor, Shader.TileMode.CLAMP
                )
            } else {
                getPaint().shader = LinearGradient(
                    0F, 0F, 0F, lineHeight.toFloat(),
                    endColor, startColor, Shader.TileMode.CLAMP
                )
            }
        }
    }
}