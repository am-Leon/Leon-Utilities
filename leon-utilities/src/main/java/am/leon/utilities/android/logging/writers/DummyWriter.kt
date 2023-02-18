package am.leon.utilities.android.logging.writers

import am.leon.utilities.android.logging.LogWriter

/**
 * Do not writes anything
 */
class DummyWriter : LogWriter {
    override fun debug(clazz: Class<*>, message: String?) {}

    override fun error(clazz: Class<*>, message: String?, throwable: Throwable?) {}

    override fun info(clazz: Class<*>, message: String?) {}

    override val isDebugEnabled: Boolean
        get() = false
}