package am.leon.leonutilit

import am.leon.utilities.android.logging.LoggerFactory
import am.leon.utilities.android.logging.writers.LogcatWriter
import android.app.Application

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LoggerFactory.setLogWriter(LogcatWriter(getString(R.string.app_name), BuildConfig.DEBUG))
    }
}