package am.leon.utilities.android.helpers.network.customizedSSL

import java.security.cert.CertificateException
import java.security.cert.X509Certificate

/**
 * A strategy to establish trustworthiness of certificates without consulting the trust manager
 * configured in the actual SSL context. This interface can be used to override the standard
 * JSSE certificate verification process.
 *
 * @since 4.4
 */
interface TrustStrategy {
    /**
     * Determines whether the certificate chain can be trusted without consulting the trust manager
     * configured in the actual SSL context. This method can be used to override the standard JSSE
     * certificate verification process.
     *
     *
     * Please note that, if this method returns `false`, the trust manager configured
     * in the actual SSL context can still clear the certificate as trusted.
     *
     * @param chain    the peer certificate chain
     * @param authType the authentication type based on the client certificate
     * @return `true` if the certificate can be trusted without verification by
     * the trust manager, `false` otherwise.
     * @throws CertificateException thrown if the certificate is not trusted or invalid.
     */
    @Throws(CertificateException::class)
    fun isTrusted(chain: Array<X509Certificate>, authType: String): Boolean
}