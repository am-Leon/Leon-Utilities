package am.leon.utilities.android.helpers.network.customizedSSL

import java.security.cert.X509Certificate

class PrivateKeyDetails(type: String, private val certChain: Array<X509Certificate>) {

    val type: String = Args.notNull(type, "Private key type")

    override fun toString(): String {
        return type + ':' + certChain.contentToString()
    }
}