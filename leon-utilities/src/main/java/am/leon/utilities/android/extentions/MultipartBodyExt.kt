package am.leon.utilities.android.extentions
//
//import android.webkit.MimeTypeMap
//import okhttp3.MediaType.Companion.toMediaType
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import okhttp3.RequestBody.Companion.asRequestBody
//import java.io.File
//
//enum class FileMediaType(val mediaType: okhttp3.MediaType) {
//    FILE("multipart/form-data".toMediaType()),
//    IMAGE("image/*".toMediaType()),
//    TEXT("text/plain".toMediaType());
//
//    companion object {
//        fun findIsImageOrFile(file: File): FileMediaType {
//            val extension = file.path.substringAfterLast('.', "")
//            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
//
//            // Regular expression to match image MIME types
//            val imageRegex = Regex("^image/.*$")
//
//            // Check if the MIME type starts with "image/" or if the extension is commonly used for images
//            return if (mimeType != null && mimeType.matches(imageRegex))
//                IMAGE
//            else
//                FILE
//        }
//    }
//}
//
//fun List<File>.getFileMediaListAsPart(fieldKey: String): ArrayList<MultipartBody.Part> {
//    val parts: ArrayList<MultipartBody.Part> = ArrayList()
//    forEachIndexed { index, file ->
//        parts.add(get(index).getFileMediaAsPart(fieldKey.plus("[".plus(file).plus("]"))))
//    }
//    return parts
//}
//
//fun File.getFileMediaAsPart(fieldKey: String): MultipartBody.Part {
//    val body: RequestBody = asRequestBody(FileMediaType.findIsImageOrFile(this).mediaType)
//    return MultipartBody.Part.createFormData(fieldKey, name, body)
//}
//
//fun String.toRequestBody(): RequestBody = toRequestBody(FileMediaType.TEXT.mediaType)


//private fun getParamsAsPart(key: String, value: String): MultipartBody.Part {
//    return MultipartBody.Part.createFormData(key, value)
//}
//
//protected fun getParamsAsPart(
//    type: String, value: String, status: Boolean
//): MultipartBody.Part? {
//    return if (!status) MultipartBody.Part.createFormData(type, value) else null
//}

//fun getStringListAsPart(
//    key: String, list: List<String>
//): ArrayList<MultipartBody.Part> {
//    val parts: ArrayList<MultipartBody.Part> = ArrayList()
//
//    for (item in list.indices) {
//        parts.add(getParamsAsPart(key.plus("[".plus(item).plus("]")), list[item]))
//    }
//    return parts
//}
//
//
//fun String.getComponentListAsPart(list: List<Tag>): ArrayList<MultipartBody.Part> {
//    val parts: ArrayList<MultipartBody.Part> = ArrayList()
//
//    for (item in list.indices) {
//        parts.add(getParamsAsPart(this.plus("[".plus(item).plus("]")), list[item].name))
//    }
//    return parts
//}
