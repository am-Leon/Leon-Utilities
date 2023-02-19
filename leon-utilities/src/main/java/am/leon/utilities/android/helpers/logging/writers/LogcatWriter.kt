package am.leon.utilities.android.helpers.logging.writers

import am.leon.utilities.android.helpers.logging.LogWriter
import android.util.Log

class LogcatWriter(private val appName: String, override val isDebugEnabled: Boolean) : LogWriter {

    override fun debug(clazz: Class<*>, message: String?) {
        if (isDebugEnabled) {
            val formattedMessage = getFormattedMessage(clazz, message)
            Log.d(appName, formattedMessage)
        }
    }

    override fun info(clazz: Class<*>, message: String?) {
        if (isDebugEnabled) {
            val formattedMessage = getFormattedMessage(clazz, message)
            Log.i(appName, formattedMessage)
        }
    }

    override fun warning(clazz: Class<*>, message: String?) {
        if (isDebugEnabled) {
            val formattedMessage = getFormattedMessage(clazz, message)
            Log.w(appName, formattedMessage)
        }
    }

    override fun error(clazz: Class<*>, message: String?, throwable: Throwable?) {
        if (isDebugEnabled) {
            val formattedMessage = getFormattedMessage(clazz, message, throwable)
            Log.e(appName, formattedMessage)
        }
    }

    private fun getFormattedMessage(
        clazz: Class<*>, message: String?, throwable: Throwable? = null
    ): String {
        return if (throwable == null)
            String.format("[%s] %s", clazz.simpleName, message)
        else
            String.format("[%s] %s %s", clazz.simpleName, message, throwable.toString())
    }
}