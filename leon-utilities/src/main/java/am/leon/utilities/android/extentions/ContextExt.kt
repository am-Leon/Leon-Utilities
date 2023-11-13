package am.leon.utilities.android.extentions

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity


fun Context.isAppInForeground(): Boolean {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val appProcesses = activityManager.runningAppProcesses ?: return false

    for (appProcess in appProcesses) {
        if (appProcess.processName == packageName && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
            return true
    }
    return false
}

fun Context.isScreenOn(): Boolean {
    val manager = getSystemService(Context.POWER_SERVICE) as PowerManager
    return manager.isInteractive
}

@SuppressLint("UnspecifiedRegisterReceiverFlag")
fun Context.registerReceiverExported(receiver: BroadcastReceiver, filter: IntentFilter) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        registerReceiver(receiver, filter, AppCompatActivity.RECEIVER_EXPORTED)
    else
        registerReceiver(receiver, filter)
}

@SuppressLint("UnspecifiedRegisterReceiverFlag")
fun Context.registerReceiverNotExported(receiver: BroadcastReceiver, filter: IntentFilter) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        registerReceiver(receiver, filter, AppCompatActivity.RECEIVER_NOT_EXPORTED)
    else
        registerReceiver(receiver, filter)
}

// -------------------------------------------- System ---------------------------------------------

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
