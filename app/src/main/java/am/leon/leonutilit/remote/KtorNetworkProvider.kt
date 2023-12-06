package am.leon.leonutilit.remote

import am.leon.utilities.android.extentions.getModelFromJSON
import am.leon.utilities.common.domain.repository.remote.INetworkProvider
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpMethod
import java.lang.reflect.Type
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

class KtorNetworkProvider(private val client: HttpClient) : INetworkProvider {

    override suspend fun <ResponseBody, RequestBody> delete(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?, requestBody: RequestBody?
    ): ResponseBody {
        val responseString = executeRequest(
            HttpMethod.Delete, pathUrl, headers, queryParams, requestBody
        ).bodyAsText()

        return responseString.getModelFromJSON(responseWrappedModel)
    }

    override suspend fun <ResponseBody, RequestBody> post(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?, requestBody: RequestBody?
    ): ResponseBody {
        val responseString = executeRequest(
            HttpMethod.Post, pathUrl, headers, queryParams, requestBody
        ).bodyAsText()

        return responseString.getModelFromJSON(responseWrappedModel)
    }

    override suspend fun <ResponseBody, RequestBody> postWithHeaderResponse(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?, requestBody: RequestBody?
    ): Pair<ResponseBody, Map<String, String>> {
        val httpResponse = executeRequest(
            HttpMethod.Post, pathUrl, headers, queryParams, requestBody
        )

        val responseBody: ResponseBody =
            httpResponse.bodyAsText().getModelFromJSON(responseWrappedModel)

        return Pair(responseBody, extractHeaders(httpResponse.headers))
    }

    override suspend fun <ResponseBody, RequestBody> put(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?, requestBody: RequestBody?
    ): ResponseBody {
        val responseString = executeRequest(
            HttpMethod.Put, pathUrl, headers, queryParams, requestBody
        ).bodyAsText()

        return responseString.getModelFromJSON(responseWrappedModel)
    }

    override suspend fun <ResponseBody> get(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?
    ): ResponseBody {
        val responseString = executeRequest(
            HttpMethod.Get, pathUrl, headers, queryParams, null
        ).bodyAsText()

        return responseString.getModelFromJSON(responseWrappedModel)
    }

    override suspend fun <ResponseBody> getWithHeaderResponse(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?
    ): Pair<ResponseBody, Map<String, String>> {
        val httpResponse = executeRequest(
            HttpMethod.Get, pathUrl, headers, queryParams, null
        )

        val responseBody: ResponseBody =
            httpResponse.bodyAsText().getModelFromJSON(responseWrappedModel)

        return Pair(responseBody, extractHeaders(httpResponse.headers))
    }

    private suspend fun executeRequest(
        method: HttpMethod, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?, requestBody: Any?
    ): HttpResponse {
        return client.request {
            this.method = method
            url {
                url(pathUrl)
                queryParams?.forEach { (key, value) -> parameters.append(key, value.toString()) }
            }

            headers?.let {
                headers {
                    it.forEach { (key, value) -> append(key, value.toString()) }
                }
            }

            if (requestBody != null) {
                setBody(requestBody)
            }
        }
    }

    private fun extractHeaders(httpHeaders: Headers): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        for ((key, values) in httpHeaders.entries()) {
            val value = values.firstOrNull() ?: continue
            headerMap[key] = value
        }
        return headerMap
    }
}
