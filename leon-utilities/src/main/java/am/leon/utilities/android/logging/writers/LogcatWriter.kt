package am.leon.utilities.android.logging.writers

import am.leon.utilities.android.logging.LogWriter
import android.util.Log

class LogcatWriter(private val appName: String, override val isDebugEnabled: Boolean) : LogWriter {

    override fun debug(clazz: Class<*>, message: String?) {
        Log.d(appName, String.format("[%s] %s", clazz.simpleName, message))
    }

    override fun error(clazz: Class<*>, message: String?, throwable: Throwable?) {
        val fullMessage = if (throwable == null)
            String.format("[%s] %s", clazz.simpleName, message)
        else
            String.format("[%s] %s %s", clazz.simpleName, message, throwable.toString())

        Log.e(appName, fullMessage)
    }

    override fun info(clazz: Class<*>, message: String?) {
        Log.i(appName, String.format("[%s] %s", clazz.simpleName, message))
    }
}