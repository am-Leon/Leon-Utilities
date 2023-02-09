package am.leon.utilities.android.extentions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings


fun Context.isScreenOn(): Boolean {
    val manager = getSystemService(Context.POWER_SERVICE) as PowerManager
    return manager.isInteractive
}

// -------------------------------------------- System ---------------------------------------------

@Suppress("DEPRECATION")
fun Context.getPackageInfo(packageName: String): PackageInfo =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
    else
        packageManager.getPackageInfo(packageName, 0)

fun Context.getApplicationVersionName(): String = getPackageInfo(packageName).versionName

fun Context.buildSettingNavigationIntent(): Intent {
    return Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )
}