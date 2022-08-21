package am.leon.utilities.extentions

import am.leon.utilities.R
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar


fun Context.whatsAppIntent(phoneWithCountryCode: String) {
    val url = "https://api.whatsapp.com/send?phone=$phoneWithCountryCode"
    try {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    } catch (e: PackageManager.NameNotFoundException) {
        Snackbar.make(
            (this as Activity).findViewById(android.R.id.content),
            getString(R.string.whatsapp_not_installed),
            Snackbar.LENGTH_SHORT
        ).show()
        e.printStackTrace()
    }
}

fun Context.instagramIntent(profileName: String) {
    val instagramUrl = "https://www.instagram.com"
    val uri: Uri = Uri.parse("$instagramUrl/_u/$profileName")
    val likeIng = Intent(Intent.ACTION_VIEW, uri)
    likeIng.setPackage("com.instagram.android")
    try {
        startActivity(likeIng)
    } catch (e: ActivityNotFoundException) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("$instagramUrl/$profileName")))
    }
}

fun Context.twitterIntent(profileName: String) {
    var intent: Intent?
    try {
        packageManager.getPackageInfo("com.twitter.android", 0)
        intent = Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=$profileName"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    } catch (e: java.lang.Exception) {
        intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/$profileName"))
    }
    startActivity(intent)
}

fun Context.webIntent(url: String) {
    val fullPath = if (url.startsWith("http://").not() && url.startsWith("https://").not())
        "https://$url"
    else
        url

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fullPath))
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
}

fun Context.phoneIntent(phone: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phone")
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}

fun Context.gmailIntent(email: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:$email"))
        startActivity(intent)
    } catch (e: PackageManager.NameNotFoundException) {
        showToastAsShort(getString(R.string.gmail_not_installed))
    } catch (e: ActivityNotFoundException) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http:/www.gmail.com"))
        startActivity(intent)
    }
}

fun Context.shareIntent(sharingURL: String) {
    val sharingIntent = Intent("android.intent.action.SEND")
    sharingIntent.type = "text/plain"
    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share))
    sharingIntent.putExtra(Intent.EXTRA_TEXT, sharingURL)
    sharingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_using)))
}

fun Context.mapIntent(latLng: LatLng) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("google.navigation:q=" + latLng.latitude + "," + latLng.longitude)
    )
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
}
