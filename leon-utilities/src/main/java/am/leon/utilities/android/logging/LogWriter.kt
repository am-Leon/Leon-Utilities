package am.leon.utilities.android.logging

interface LogWriter {
    fun debug(clazz: Class<*>, message: String?)
    fun error(clazz: Class<*>, message: String?, throwable: Throwable? = null)
    fun info(clazz: Class<*>, message: String?)
    val isDebugEnabled: Boolean
}