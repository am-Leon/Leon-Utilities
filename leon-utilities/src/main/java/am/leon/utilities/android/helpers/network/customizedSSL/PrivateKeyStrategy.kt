package am.leon.utilities.android.helpers.network.customizedSSL

import java.net.Socket

/**
 * A strategy allowing for a choice of an alias during SSL authentication.
 *
 * @since 4.4
 */
interface PrivateKeyStrategy {
    /**
     * Determines what key material to use for SSL authentication.
     *
     * @param aliases available private key material
     * @param socket  socket used for the connection. Please note this parameter can be `null`
     * if key material is applicable to any socket.
     */
    fun chooseAlias(aliases: Map<String, PrivateKeyDetails>, socket: Socket?): String
}