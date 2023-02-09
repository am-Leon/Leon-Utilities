package am.leon.utilities.android.helpers.components.recycler

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PeekingLinearLayoutManager : LinearLayoutManager {

    enum class RatioValue(val ratio: Float) { SEVENTY(0.7f), EIGHTY(0.8f), FIFTY(0.5f), THIRTY(0.3f) }

    constructor(context: Context?, ratioValue: RatioValue)
            : this(context, RecyclerView.HORIZONTAL, false) {
        this.ratio = ratioValue.ratio
    }

    private constructor(
        context: Context?,
        @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
        reverseLayout: Boolean = false
    ) : super(context, orientation, reverseLayout)

    private constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)

    override fun generateDefaultLayoutParams() =
        scaledLayoutParams(super.generateDefaultLayoutParams())

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams?) =
        scaledLayoutParams(super.generateLayoutParams(lp))

    override fun generateLayoutParams(c: Context?, attrs: AttributeSet?) =
        scaledLayoutParams(super.generateLayoutParams(c, attrs))

    private fun scaledLayoutParams(layoutParams: RecyclerView.LayoutParams) =
        layoutParams.apply {
            when (orientation) {
                RecyclerView.HORIZONTAL -> width = (horizontalSpace * ratio).toInt()
                RecyclerView.VERTICAL -> height = (verticalSpace * ratio).toInt()
            }
        }

    private val horizontalSpace get() = width - paddingStart - paddingEnd

    private val verticalSpace get() = height - paddingTop - paddingBottom

    private var ratio = 0.7f
}