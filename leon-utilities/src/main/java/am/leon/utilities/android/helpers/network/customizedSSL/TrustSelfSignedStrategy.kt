package am.leon.utilities.android.helpers.network.customizedSSL

import java.security.cert.X509Certificate

/**
 * A trust strategy that accepts self-signed certificates as trusted. Verification of all other
 * certificates is done by the trust manager configured in the SSL context.
 *
 * @since 4.1
 */
class TrustSelfSignedStrategy : TrustStrategy {
    override fun isTrusted(chain: Array<X509Certificate>, authType: String): Boolean {
        return chain.size == 1
    }

    companion object {
        val INSTANCE = TrustSelfSignedStrategy()
    }
}