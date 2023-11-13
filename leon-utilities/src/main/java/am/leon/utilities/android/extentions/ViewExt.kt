package am.leon.utilities.android.extentions

import am.leon.utilities.R
import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.view.animation.Transformation
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackBar(message: String, onRetryClicked: () -> Unit) {
    hideKeyboard()
    Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE)
        .setAction(R.string.retry) { onRetryClicked() }
        .show()
}

fun View.showSnackBar(message: String, onRetryClicked: () -> Unit) {
    (context as? Activity)?.hideKeyboard()
    Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
        .setAction(R.string.retry) { onRetryClicked() }
        .show()
}

fun Fragment.showSnackBar(message: Int, onRetryClicked: () -> Unit) {
    hideKeyboard()
    Snackbar.make(requireView(), getString(message), Snackbar.LENGTH_INDEFINITE)
        .setAction(R.string.retry) { onRetryClicked() }
        .show()
}

fun Fragment.showSnackBar(message: String) {
    hideKeyboard()
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
}

infix fun View.showSnackBar(message: String) {
    (context as? Activity)?.hideKeyboard()
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.showSnackBar(message: Int) {
    hideKeyboard()
    Snackbar.make(requireView(), getString(message), Snackbar.LENGTH_SHORT).show()
}

fun View.showToastAsLong(message: String) = context.showToastAsLong(message)

fun Context.showToastAsLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun View.showToastAsLong(@StringRes resID: Int) = context.showToastAsLong(resID)

fun Context.showToastAsLong(@StringRes resID: Int) {
    Toast.makeText(this, getString(resID), Toast.LENGTH_LONG).show()
}

fun View.showToastAsShort(message: String) = context.showToastAsShort(message)

fun Context.showToastAsShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.showToastAsShort(@StringRes resID: Int) = context.showToastAsShort(resID)

fun Context.showToastAsShort(@StringRes resID: Int) {
    Toast.makeText(this, getString(resID), Toast.LENGTH_SHORT).show()
}

// -------------------------------------------- Keyboard -------------------------------------------

fun Activity.showKeyboard() =
    WindowInsetsControllerCompat(window, window.decorView).show(WindowInsetsCompat.Type.ime())

fun Activity.hideKeyboard() =
    WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.ime())

fun Fragment.hideKeyboard() = activity?.hideKeyboard()

fun Fragment.showKeyboard() = activity?.showKeyboard()

// -------------------------------------------- Item Visibility ------------------------------------

fun View.show(show: Boolean = true) {
    if (show) toVisible() else toGone()
}

fun View.showInvisible(show: Boolean = true) {
    if (show) toVisible() else toInvisible()
}

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

fun View.toInvisible() {
    visibility = View.INVISIBLE
}

// -------------------------------------------- StatusBar ------------------------------------------

fun Activity.hideStatusBars() {
    WindowInsetsControllerCompat(
        window, window.decorView
    ).hide(WindowInsetsCompat.Type.statusBars())
}

fun Activity.showStatusBars() {
    WindowInsetsControllerCompat(
        window, window.decorView
    ).show(WindowInsetsCompat.Type.statusBars())
}

// -------------------------------------------- Resources ------------------------------------------

fun AppCompatActivity.setWindowLayoutDirection(layoutDirection: Int) {
    window.decorView.layoutDirection = layoutDirection
}

fun View.makeForeGroundClickable() {
    val outValue = TypedValue()
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
    foreground = ContextCompat.getDrawable(context, outValue.resourceId)
}

fun View.refreshView() {
    invalidate()
    requestLayout()
}

fun View.getPxFromDimenRes(@DimenRes res: Int) = context.getPxFromDimenRes(res)

fun Context.getPxFromDimenRes(@DimenRes res: Int) = resources.getDimensionPixelSize(res)

fun Context.getDrawableFromRes(@DrawableRes drawable: Int): Drawable? =
    ContextCompat.getDrawable(this, drawable)

fun Context.getColorStateListFromRes(@ColorRes color: Int): ColorStateList {
    return ColorStateList.valueOf(getColorFromRes(color))
}

fun Context.getColorFromRes(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

fun View.expandView() {
    this.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT)
    val targetHeight: Int = this.measuredHeight

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    this.layoutParams.width = 1
    this.toVisible()
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            this@expandView.layoutParams.width =
                if (interpolatedTime == 1f) RelativeLayout.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
            this@expandView.requestLayout()
        }

        override fun willChangeBounds(): Boolean = true
    }
    a.duration = (targetHeight / 150).toLong()
    this.startAnimation(a)
}

fun View.collapseView() {
    val initialHeight: Int = this.measuredHeight
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            if (interpolatedTime == 1f) {
                this@collapseView.toGone()
            } else {
                this@collapseView.layoutParams.width =
                    initialHeight - (initialHeight * interpolatedTime).toInt()
                this@collapseView.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean = true
    }

    // 1dp/ms
    a.duration = ((initialHeight * 3) / this.context.resources.displayMetrics.density).toLong()
//        a.setDuration(500);
    this.startAnimation(a)
}

fun View.expandVertical() {
    this.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val targetHeight: Int = this.measuredHeight

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    this.layoutParams.height = 1
    this.toVisible()

    val va: ValueAnimator = ValueAnimator.ofInt(1, targetHeight)
    va.addUpdateListener { animation ->
        this@expandVertical.layoutParams.height = animation.animatedValue as Int
        this@expandVertical.requestLayout()
    }
    va.addListener(object : Animator.AnimatorListener {

        override fun onAnimationEnd(animation: Animator) {
            this@expandVertical.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }

        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    })
    va.duration = 300
    va.interpolator = OvershootInterpolator()
    va.start()
}

fun View.collapseVertical() {
    val initialHeight: Int = this.measuredHeight

    val va: ValueAnimator = ValueAnimator.ofInt(initialHeight, 0)
    va.addUpdateListener { animation ->
        this@collapseVertical.layoutParams.height = animation.animatedValue as Int
        this@collapseVertical.requestLayout()
    }
    va.addListener(object : Animator.AnimatorListener {

        override fun onAnimationEnd(animation: Animator) {
            this@collapseVertical.toGone()
        }

        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    })
    va.duration = 300
    va.interpolator = DecelerateInterpolator()
    va.start()
}

fun View.setBackgroundTint(colorRes: Int) {
    backgroundTintList = ContextCompat.getColorStateList(context, colorRes)
}

fun AppCompatImageView.setTint(colorRes: Int) {
    setColorFilter(
        ContextCompat.getColor(context, colorRes), android.graphics.PorterDuff.Mode.SRC_IN
    )
}

fun Activity.disableTouch() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Activity.enableTouch() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}
