package am.leon.utilities.android.helpers.network.customizedSSL

internal object Args {
    fun <T> notNull(argument: T?, name: String): T {
        requireNotNull(argument) { "$name may not be null" }
        return argument
    }
}