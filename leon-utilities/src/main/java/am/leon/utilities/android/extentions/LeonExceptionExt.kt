package am.leon.utilities.android.extentions

import am.leon.utilities.android.helpers.gson.adapter.LeonExceptionTypeAdapter
import am.leon.utilities.android.helpers.gson.parsing.IJsonParsingStrategy
import am.leon.utilities.android.helpers.gson.serializer.client.LeonClientResponseValidationSerializer
import am.leon.utilities.android.helpers.gson.serializer.client.LeonClientUnauthorizedSerializer
import am.leon.utilities.android.helpers.gson.serializer.client.LeonClientUnhandledSerializer
import am.leon.utilities.android.helpers.gson.serializer.local.LeonLocalIOOperationSerializer
import am.leon.utilities.android.helpers.gson.serializer.local.LeonLocalRequestValidationSerializer
import am.leon.utilities.android.helpers.gson.serializer.network.LeonNetworkHandledSerializer
import am.leon.utilities.android.helpers.gson.serializer.network.LeonNetworkRetrialSerializer
import am.leon.utilities.android.helpers.gson.serializer.server.LeonServerInternalSerializer
import am.leon.utilities.common.data.models.exception.LeonException
import com.google.gson.GsonBuilder

fun LeonException.toJsonLeonException(): String {
    val gson = GsonBuilder()
        // NetworkException
        .registerTypeAdapter(
            LeonException.Network.Retrial::class.java, LeonNetworkRetrialSerializer()
        ).registerTypeAdapter(
            LeonException.Network.Unhandled::class.java, LeonNetworkHandledSerializer()
        )

        // Client
        .registerTypeAdapter(
            LeonException.Client.Unauthorized::class.java, LeonClientUnauthorizedSerializer()
        ).registerTypeAdapter(
            LeonException.Client.ResponseValidation::class.java,
            LeonClientResponseValidationSerializer()
        ).registerTypeAdapter(
            LeonException.Client.Unhandled::class.java, LeonClientUnhandledSerializer()
        )

        // Server
        .registerTypeAdapter(
            LeonException.Server.InternalServerError::class.java, LeonServerInternalSerializer()
        )

        // Local
        .registerTypeAdapter(
            LeonException.Local.RequestValidation::class.java,
            LeonLocalRequestValidationSerializer()
        ).registerTypeAdapter(
            LeonException.Local.IOOperation::class.java, LeonLocalIOOperationSerializer()
        )
        .create()

    return gson.toJson(this)
}

fun String.toLeonException(parsingStrategy: IJsonParsingStrategy<LeonException>): LeonException {
    val gson = GsonBuilder()
        .registerTypeAdapter(
            LeonException::class.java, LeonExceptionTypeAdapter(parsingStrategy)
        )
        .create()

    return gson.fromJson(this, LeonException::class.java)
}