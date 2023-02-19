package am.leon.utilities.android.helpers.logging.writers

import am.leon.utilities.android.helpers.logging.LogWriter
import am.leon.utilities.android.utils.file.FileUtil
import am.leon.utilities.android.utils.file.LogType
import android.util.Log
import java.io.File

class FileWriter(
    folderName: String, fileName: String, private val appName: String,
    override val isDebugEnabled: Boolean
) : LogWriter {

    override fun debug(clazz: Class<*>, message: String?) {
        val formattedMessage = getFormattedMessage(clazz, message)
        Log.d(appName, formattedMessage)
        FileUtil.logToFile(LogType.DEBUG, formattedMessage)
    }

    override fun info(clazz: Class<*>, message: String?) {
        val formattedMessage = getFormattedMessage(clazz, message)

        Log.i(appName, formattedMessage)
        FileUtil.logToFile(LogType.INFO, formattedMessage)
    }

    override fun warning(clazz: Class<*>, message: String?) {
        val formattedMessage = getFormattedMessage(clazz, message)

        Log.w(appName, formattedMessage)
        FileUtil.logToFile(LogType.WARNING, formattedMessage)
    }

    override fun error(clazz: Class<*>, message: String?, throwable: Throwable?) {
        val formattedMessage = getFormattedMessage(clazz, message)

        Log.e(appName, formattedMessage)
        FileUtil.logToFile(LogType.ERROR, formattedMessage)
    }

    private fun getFormattedMessage(
        clazz: Class<*>, message: String?, throwable: Throwable? = null
    ): String {
        return if (throwable == null)
            String.format("[%s] %s", clazz.simpleName, message)
        else
            String.format("[%s] %s %s", clazz.simpleName, message, throwable.toString())
    }

    init {
        val logFile: File = FileUtil.checkPermissionsAndCreateFile(folderName, fileName)
        FileUtil.createLogWriter(logFile)
    }
}