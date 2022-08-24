package am.leon.utilities.extentions

import android.content.Context
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.util.*

fun Context.getApplicationVersionName(): String =
    this.packageManager.getPackageInfo(this.packageName, 0).versionName

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

fun Context.getFullAddress(latitude: Double, longitude: Double): String {
    val geocoder = Geocoder(this, Locale.getDefault())
    val addresses: List<Address> = geocoder.getFromLocation(
        latitude, longitude, 1
    ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5

    if (addresses.isEmpty())
        return ""

    var address: String? = null
    if (addresses[0].maxAddressLineIndex > 0)
        address =
            addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
    val city: String? = addresses[0].locality
    val state: String? = addresses[0].adminArea
    val country: String? = addresses[0].countryName
    val postalCode: String? = addresses[0].postalCode
    val knownName: String? =
        addresses[0].featureName // Only if available else return NULL

    return if (address != null) {
        if (knownName != null)
            "$country-$city-$state-$address-$knownName"
        else
            "$country-$city-$state-$address"
    } else {
        if (knownName != null)
            "$country-$city-$state-$knownName"
        else
            "$country-$city-$state"
    }
}