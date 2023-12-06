package am.leon.utilities.android.helpers.gson.serializer.client

import am.leon.utilities.common.data.consts.Constants.HTTP_ERROR_CODE
import am.leon.utilities.common.data.consts.Constants.MESSAGE
import am.leon.utilities.common.data.consts.Constants.SUBTYPE
import am.leon.utilities.common.data.models.exception.LeonException
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class LeonClientUnhandledSerializer : JsonSerializer<LeonException.Client.Unhandled> {

    override fun serialize(
        src: LeonException.Client.Unhandled?, typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonObject = JsonObject()
        src?.apply {
            jsonObject.addProperty(MESSAGE, message)
            jsonObject.addProperty(HTTP_ERROR_CODE, httpErrorCode)
            jsonObject.addProperty(
                SUBTYPE, LeonException.Client.Unhandled::class.java.simpleName
            )
        }
        return jsonObject
    }
}