package am.leon.leonutilit

import am.leon.utilities.android.helpers.logging.LoggerFactory
import am.leon.utilities.android.helpers.logging.writers.FileWriter
import android.app.Application

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LoggerFactory.setLogWriter(
            FileWriter(
                folderName = "Leon", fileName = "leon-logger", getString(R.string.app_name),
                BuildConfig.DEBUG
            )
        )
    }
}