package am.leon.utilities.extentions

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.util.*

@Suppress("DEPRECATION")
fun Context.getPackageInfo(packageName: String): PackageInfo = if (SDK_INT >= TIRAMISU)
    packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
else
    packageManager.getPackageInfo(packageName, 0)

fun Context.getApplicationVersionName(): String = getPackageInfo(packageName).versionName

fun Context.showToastAsShort(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.showToastAsShort(stringRes: Int) =
    Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show()

fun Context.showToastAsLong(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Context.convertPxToDp(px: Float): Float {
    val metrics = resources.displayMetrics
    return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun Context.convertDpToPx(dp: Float): Int {
    val metrics = resources.displayMetrics
    return (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

fun Context.getColorFromRes(colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun Context.getDrawableFromRes(drawableRes: Int): Drawable =
    ContextCompat.getDrawable(this, drawableRes)!!

@Suppress("DEPRECATION")
fun Context.getFullAddress(latitude: Double, longitude: Double): String {
    val geocoder = Geocoder(this, Locale.getDefault())
    var fullAddress = StringBuilder("")

    if (SDK_INT >= TIRAMISU) geocoder.getFromLocation(latitude, longitude, 1) {
        if (it.isEmpty()) return@getFromLocation

        it[0].extractAddressAsString()
    }
    else geocoder.getFromLocation(latitude, longitude, 1)?.apply {
        if (isEmpty()) return@apply

        fullAddress = get(0).extractAddressAsString()
    }
    return fullAddress.toString()
}

private fun Address.extractAddressAsString(): StringBuilder {
    val builder = StringBuilder()
    builder.append(countryName).append("-").append(locality).append("-").append(adminArea)
        .append("-")

    if (maxAddressLineIndex > 0) builder.append(getAddressLine(0)).append("-")

    if (featureName != null) builder.append(featureName).append("-")

    return builder
}