package am.leon.utilities.extentions

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import am.leon.utilities.R

private fun Activity.setTransition() {
    overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out)
}

// ------------------------------------------- Activity Normal -------------------------------------

fun Activity.intentNormal(aClass: Class<*>) = intentNormal(aClass, false)

fun Activity.intentNormal(aClass: Class<*>, finish: Boolean) {
    startActivity(Intent(this, aClass))
    if (finish) finish()
}

fun Activity.intentNormal(aClass: Class<*>, bundle: Bundle) = intentNormal(aClass, bundle, false)

fun Activity.intentNormal(aClass: Class<*>, bundle: Bundle, finish: Boolean) {
    startActivity(Intent(this, aClass).putExtras(bundle))
    if (finish) finish()
}

// ------------------------------------------- Activity Slide InOut --------------------------------

fun Activity.intentWithSlideInOut(aClass: Class<*>) = intentWithSlideInOut(aClass, false)

fun Activity.intentWithSlideInOut(aClass: Class<*>, finish: Boolean) {
    startActivity(Intent(this, aClass))
    if (finish) finish()
    setTransition()
}

// ------------------------------------------- Activity Bundle Slide InOut -------------------------

fun Activity.intentWithSlideInOut(aClass: Class<*>, bundle: Bundle) =
    intentWithSlideInOut(aClass, bundle, false)

fun Activity.intentWithSlideInOut(aClass: Class<*>, bundle: Bundle, finish: Boolean) {
    startActivity(Intent(this, aClass).putExtras(bundle))
    if (finish) finish()
    setTransition()
}

// ------------------------------------------- Activity Result Bundle Slide InOut -------------------------

fun Activity.intentResultWithSlideInOut(aClass: Class<*>) = intentResultWithSlideInOut(aClass, null)

fun Activity.intentResultWithSlideInOut(aClass: Class<*>, bundle: Bundle?): Intent {
    val intent = Intent(this, aClass)
    bundle?.let { intent.putExtras(it) }
    setTransition()
    return intent
}

// ------------------------------------------- Fragment Fade ---------------------------------------

fun FragmentActivity.loadWithFade(fragment: Fragment, containerView: Int) = kotlin.run {
    loadWithFade(fragment, containerView, false)
}

fun FragmentActivity.loadWithFade(
    fragment: Fragment, containerView: Int, withBackStack: Boolean
) = supportFragmentManager.commit {
    setCustomAnimations(
        android.R.anim.fade_in, android.R.anim.fade_out,
        android.R.anim.fade_in, android.R.anim.fade_out
    )
    replace(containerView, fragment, fragment.javaClass.name)
    if (withBackStack)
        addToBackStack(fragment.javaClass.name)
}

// ------------------------------------------- Fragment Slide InOut --------------------------------

fun FragmentActivity.loadWithSlideInOut(fragment: Fragment, containerView: Int) = kotlin.run {
    loadWithSlideInOut(fragment, containerView, false)
}

fun FragmentActivity.loadWithSlideInOut(
    fragment: Fragment, containerView: Int, withBackStack: Boolean
) {
    supportFragmentManager.commit {
        setCustomAnimations(
            R.anim.from_right_in, android.R.anim.fade_out,
            android.R.anim.fade_in, R.anim.from_left_out
        )
        replace(containerView, fragment, fragment.javaClass.name)
        if (withBackStack)
            addToBackStack(fragment.javaClass.name)
    }
}

// ------------------------------------------- Fragment --------------------------------------------

fun FragmentActivity.loadFragment(fragment: Fragment, containerView: Int) = kotlin.run {
    loadFragment(fragment, containerView, false)
}

fun FragmentActivity.loadFragment(fragment: Fragment, containerView: Int, withBackStack: Boolean) {
    supportFragmentManager.commit {
        replace(containerView, fragment, fragment.javaClass.name)
        if (withBackStack)
            addToBackStack(fragment.javaClass.name)
    }
}

// ------------------------------------------- Activity --------------------------------------------

fun AppCompatActivity.getCustomView(): View = findViewById(android.R.id.content)

fun Activity.changeStatusBarColor(color: Int) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = getColorFromRes(color)
}

fun Activity.changeStatusBarColor(color: String) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = Color.parseColor(color)
}

fun FragmentActivity.isPermissionGranted(permission: String): Boolean =
    ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED