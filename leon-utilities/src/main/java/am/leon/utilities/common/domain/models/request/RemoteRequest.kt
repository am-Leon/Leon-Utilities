package am.leon.utilities.common.domain.models.request

import java.io.File

data class RemoteRequest(
    val requestBody: HashMap<String, Any> = hashMapOf(),
    val requestBodyFiles: HashMap<String, List<File>> = hashMapOf(),
    val requestQueries: HashMap<String, Any> = hashMapOf(),
    val requestHeaders: HashMap<String, Any> = hashMapOf()
)