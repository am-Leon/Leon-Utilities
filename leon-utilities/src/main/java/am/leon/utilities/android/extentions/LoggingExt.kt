package am.leon.utilities.android.extentions

import am.leon.utilities.android.helpers.logging.Logger
import am.leon.utilities.android.helpers.logging.LoggerFactory


inline fun <reified T> T.getClassLogger(): Logger {
    if (T::class.isCompanion) {
        return LoggerFactory.getLogger(T::class.java.enclosingClass as Class<*>)
    }
    return LoggerFactory.getLogger(T::class.java)
}