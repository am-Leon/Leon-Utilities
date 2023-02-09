package am.leon.utilities.android.helpers.network.customizedSSL

import android.annotation.SuppressLint
import am.leon.utilities.android.helpers.network.customizedSSL.Args.notNull
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.net.Socket
import java.net.URL
import java.security.*
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * Builder for [SSLContext] instances.
 *
 *
 * Please note: the default Oracle JSSE implementation of [SSLContext.init]
 * accepts multiple key and trust managers, however only only first matching type is ever used.
 * See for example:
 * [
 * SSLContext.html#init
](http://docs.oracle.com/javase/7/docs/api/javax/net/ssl/SSLContext.html#init%28javax.net.ssl.KeyManager[],%20javax.net.ssl.TrustManager[],%20java.security.SecureRandom%29) *
 *
 * @since 4.4
 */
class SSLContextBuilder {

    private var protocol: String? = null
    private var secureRandom: SecureRandom? = null
    private val keyManagers: MutableSet<KeyManager>
    private val trustManagers: MutableSet<TrustManager>

    fun useProtocol(protocol: String?): SSLContextBuilder {
        this.protocol = protocol
        return this
    }

    fun setSecureRandom(secureRandom: SecureRandom?): SSLContextBuilder {
        this.secureRandom = secureRandom
        return this
    }

    @Throws(NoSuchAlgorithmException::class, KeyStoreException::class)
    fun loadTrustMaterial(truststore: KeyStore?, trustStrategy: TrustStrategy?): SSLContextBuilder {
        val tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmfactory.init(truststore)
        val tms = tmfactory.trustManagers
        if (tms != null) {
            if (trustStrategy != null) {
                for (i in tms.indices) {
                    val tm = tms[i]
                    if (tm is X509TrustManager) {
                        tms[i] = TrustManagerDelegate(tm, trustStrategy)
                    }
                }
            }
            trustManagers.addAll(listOf(*tms))
        }
        return this
    }

    @Throws(NoSuchAlgorithmException::class, KeyStoreException::class)
    fun loadTrustMaterial(trustStrategy: TrustStrategy?): SSLContextBuilder {
        return loadTrustMaterial(null, trustStrategy)
    }

    @Throws(
        NoSuchAlgorithmException::class, KeyStoreException::class, CertificateException::class,
        IOException::class
    )
    fun loadTrustMaterial(
        file: File?, storePassword: CharArray? = null, trustStrategy: TrustStrategy? = null
    ): SSLContextBuilder {
        notNull(file, "Truststore file")
        val trustStore = KeyStore.getInstance(KeyStore.getDefaultType())
        val inStream = FileInputStream(file)
        inStream.use { trustStore.load(it, storePassword) }
        return loadTrustMaterial(trustStore, trustStrategy)
    }

    @Throws(
        NoSuchAlgorithmException::class, KeyStoreException::class, CertificateException::class,
        IOException::class
    )
    fun loadTrustMaterial(
        url: URL, storePassword: CharArray?, trustStrategy: TrustStrategy? = null
    ): SSLContextBuilder {
        notNull(url, "Truststore URL")
        val trustStore = KeyStore.getInstance(KeyStore.getDefaultType())
        val inStream = url.openStream()
        inStream.use { trustStore.load(it, storePassword) }
        return loadTrustMaterial(trustStore, trustStrategy)
    }

    @Throws(
        NoSuchAlgorithmException::class, KeyStoreException::class, UnrecoverableKeyException::class
    )
    fun loadKeyMaterial(
        keystore: KeyStore?, keyPassword: CharArray?, aliasStrategy: PrivateKeyStrategy? = null
    ): SSLContextBuilder {
        val kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        kmfactory.init(keystore, keyPassword)
        val kms = kmfactory.keyManagers
        if (kms != null) {
            if (aliasStrategy != null) {
                for (i in kms.indices) {
                    val km = kms[i]
                    if (km is X509ExtendedKeyManager) {
                        kms[i] = KeyManagerDelegate(km, aliasStrategy)
                    }
                }
            }
            for (km in kms) {
                keyManagers.add(km)
            }
        }
        return this
    }

    @Throws(
        NoSuchAlgorithmException::class, KeyStoreException::class, UnrecoverableKeyException::class,
        CertificateException::class, IOException::class
    )
    fun loadKeyMaterial(
        file: File?, storePassword: CharArray?, keyPassword: CharArray?,
        aliasStrategy: PrivateKeyStrategy? = null
    ): SSLContextBuilder {
        notNull(file, "Keystore file")
        val identityStore = KeyStore.getInstance(KeyStore.getDefaultType())
        val inStream = FileInputStream(file)
        inStream.use { identityStore.load(it, storePassword) }
        return loadKeyMaterial(identityStore, keyPassword, aliasStrategy)
    }

    @Throws(
        NoSuchAlgorithmException::class, KeyStoreException::class, UnrecoverableKeyException::class,
        CertificateException::class, IOException::class
    )
    fun loadKeyMaterial(
        url: URL, storePassword: CharArray?, keyPassword: CharArray?,
        aliasStrategy: PrivateKeyStrategy? = null
    ): SSLContextBuilder {
        notNull(url, "Keystore URL")
        val identityStore = KeyStore.getInstance(KeyStore.getDefaultType())
        val inStream = url.openStream()
        inStream.use { identityStore.load(it, storePassword) }
        return loadKeyMaterial(identityStore, keyPassword, aliasStrategy)
    }

    @Throws(KeyManagementException::class)
    private fun initSSLContext(
        sslContext: SSLContext, keyManagers: Collection<KeyManager>,
        trustManagers: Collection<TrustManager>, secureRandom: SecureRandom?
    ) {
        sslContext.init(
            if (!keyManagers.isEmpty()) keyManagers.toTypedArray() else null,
            if (!trustManagers.isEmpty()) trustManagers.toTypedArray() else null,
            secureRandom
        )
    }

    /**
     * Added the provider "Conscrypt".
     */
    @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
    fun build(): SSLContext {
        val sslContext =
            SSLContext.getInstance(if (protocol != null) protocol else TLS, "Conscrypt")
        initSSLContext(sslContext, keyManagers, trustManagers, secureRandom)
        return sslContext
    }

    @SuppressLint("CustomX509TrustManager")
    internal class TrustManagerDelegate(
        private val trustManager: X509TrustManager,
        private val trustStrategy: TrustStrategy
    ) : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            trustManager.checkClientTrusted(chain, authType)
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            if (!trustStrategy.isTrusted(chain, authType)) {
                trustManager.checkServerTrusted(chain, authType)
            }
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return trustManager.acceptedIssuers
        }
    }

    internal class KeyManagerDelegate(
        private val keyManager: X509ExtendedKeyManager,
        private val aliasStrategy: PrivateKeyStrategy
    ) : X509ExtendedKeyManager() {
        override fun getClientAliases(
            keyType: String, issuers: Array<Principal>
        ): Array<String> {
            return keyManager.getClientAliases(keyType, issuers)
        }

        private fun getClientAliasMap(
            keyTypes: Array<String>, issuers: Array<Principal>?
        ): Map<String, PrivateKeyDetails> {
            val validAliases: MutableMap<String, PrivateKeyDetails> = HashMap()
            for (keyType in keyTypes) {
                val aliases = keyManager.getClientAliases(keyType, issuers)
                if (aliases != null) {
                    for (alias in aliases) {
                        validAliases[alias] =
                            PrivateKeyDetails(keyType, keyManager.getCertificateChain(alias))
                    }
                }
            }
            return validAliases
        }

        private fun getServerAliasMap(
            keyType: String?, issuers: Array<Principal>?
        ): Map<String, PrivateKeyDetails> {
            val validAliases: MutableMap<String, PrivateKeyDetails> = HashMap()
            val aliases = keyManager.getServerAliases(keyType, issuers)
            if (aliases != null) {
                for (alias in aliases) {
                    validAliases[alias] =
                        PrivateKeyDetails(keyType!!, keyManager.getCertificateChain(alias))
                }
            }
            return validAliases
        }

        override fun chooseClientAlias(
            keyTypes: Array<String>, issuers: Array<Principal>, socket: Socket
        ): String {
            val validAliases = getClientAliasMap(keyTypes, issuers)
            return aliasStrategy.chooseAlias(validAliases, socket)
        }

        override fun getServerAliases(
            keyType: String, issuers: Array<Principal>
        ): Array<String> {
            return keyManager.getServerAliases(keyType, issuers)
        }

        override fun chooseServerAlias(
            keyType: String, issuers: Array<Principal>, socket: Socket
        ): String {
            val validAliases = getServerAliasMap(keyType, issuers)
            return aliasStrategy.chooseAlias(validAliases, socket)
        }

        override fun getCertificateChain(alias: String): Array<X509Certificate> {
            return keyManager.getCertificateChain(alias)
        }

        override fun getPrivateKey(alias: String): PrivateKey {
            return keyManager.getPrivateKey(alias)
        }

        override fun chooseEngineClientAlias(
            keyTypes: Array<String>, issuers: Array<Principal>, sslEngine: SSLEngine
        ): String {
            val validAliases = getClientAliasMap(keyTypes, issuers)
            return aliasStrategy.chooseAlias(validAliases, null)
        }

        override fun chooseEngineServerAlias(
            keyType: String, issuers: Array<Principal>, sslEngine: SSLEngine
        ): String {
            val validAliases = getServerAliasMap(keyType, issuers)
            return aliasStrategy.chooseAlias(validAliases, null)
        }
    }

    companion object {
        private const val TLS = "TLS"
        fun create(): SSLContextBuilder {
            return SSLContextBuilder()
        }
    }

    init {
        keyManagers = LinkedHashSet()
        trustManagers = LinkedHashSet()
    }
}