package am.leon.leonutilit

import am.leon.utilities.android.helpers.logging.LoggerFactory
import am.leon.utilities.android.helpers.logging.writers.LogcatWriter
import android.app.Application

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LoggerFactory.setLogWriter(
            LogcatWriter(tagKey = "leon-logger", BuildConfig.DEBUG)
        )
    }
}