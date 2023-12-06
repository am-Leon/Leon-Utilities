package am.leon.leonutilit.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json

private const val TIME_OUT = 60_000

internal val ktorHttpClient = HttpClient(Android) {
    expectSuccess = true
    // Disable Redirections
    followRedirects = false

    engine {
        connectTimeout = TIME_OUT
        socketTimeout = TIME_OUT
    }

    install(ContentNegotiation) {
        json(json)
    }

    install(HttpRequestRetry) {
        maxRetries = 3
        retryIf { request, response ->
            response.status.isSuccess().not()
        }
//        retryOnExceptionIf { request, cause ->
//            cause is NetworkError
//        }
        delayMillis { retry ->
            retry * 3000L
        } // retries in 3, 6, 9, etc. seconds

        // Retry conditions
        modifyRequest { request ->
            request.headers.append("x-retry-count", retryCount.toString())
        }
    }

    install(Logging) {
        logger = Logger.ANDROID
//        logger = object : Logger {
//            override fun log(message: String) {
//                Log.v("Logger Ktor =>", message)
//            }
//        }
        level = LogLevel.ALL
    }

    HttpResponseValidator {
        handleResponseExceptionWithRequest { exception, _ ->
            throw if (exception is ResponseException)
                ResponseBodyConverter.produceErrorBodyFailure(
                    exception.response.status.value, exception.response
                )
            else
                ResponseBodyConverter.produceFailure(exception)
        }
    }

//    install(ResponseObserver) {
//        onResponse { response ->
//            Log.d("HTTP status:", "${response.status.value}")
//        }
//    }

    defaultRequest {
        contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)
    }
}

private val json = kotlinx.serialization.json.Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = false
    prettyPrint = true
}