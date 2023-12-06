package am.leon.leonutilit.remote

import am.leon.leonutilit.R
import am.leon.utilities.android.helpers.gson.converter.INetworkFailureConverter
import am.leon.utilities.android.helpers.gson.parsing.ResponseBodyParsingStrategy
import am.leon.utilities.common.data.models.exception.LeonException
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import org.json.JSONObject
import java.io.IOException
import java.io.StringReader
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ResponseBodyConverter : INetworkFailureConverter<HttpResponse> {

    override suspend fun produceErrorBodyFailure(
        code: Int, errorBody: HttpResponse?
    ): Throwable {
        val errorMessage = errorBody?.bodyAsText()
        return when (code) {
            401 -> LeonException.Client.Unauthorized

            in 400..499 -> buildClientException(code, errorBody)

            in 500..599 -> LeonException.Server.InternalServerError(
                httpErrorCode = code, errorMessage
            )

            else -> LeonException.Client.Unhandled(httpErrorCode = code, errorMessage)
        }
    }

    private suspend fun buildClientException(
        code: Int, errorBody: HttpResponse?
    ): LeonException {
        return if (errorBody == null) LeonException.Client.Unhandled(
            httpErrorCode = code, "There is no error body for this code."
        ) else try {
            prepareErrorBodyInternalCode(code, errorBody)
        } catch (e: Exception) {
            LeonException.Client.Unhandled(httpErrorCode = code, e.message)
        }
    }

    private suspend fun prepareErrorBodyInternalCode(
        code: Int, errorBody: HttpResponse
    ): LeonException {
        // Create a JsonElement from the JSON string
        val jsonObject = JSONObject(errorBody.bodyAsText())

        if (jsonObject.isNull("code"))
            jsonObject.put("code", code)

        val jsonElement = JsonParser.parseString(jsonObject.toString())

        // Create a JsonReader from the JsonElement
        val jsonReader = JsonReader(StringReader(jsonElement.toString()))

        val result = ResponseBodyParsingStrategy()
        return result.parse(jsonReader)
    }

    override fun produceFailure(throwable: Throwable): Throwable {
        return when (throwable) {
            is SocketTimeoutException, is UnknownHostException, is IOException -> LeonException.Network.Retrial(
                messageRes = R.string.error_io_unexpected_message,
                message = "Retrial network error."
            )

            else -> LeonException.Network.Unhandled(
                messageRes = R.string.error_unexpected_message,
                message = "NetworkException Unhandled error."
            )
        }
    }
}