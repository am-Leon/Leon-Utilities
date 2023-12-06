package am.leon.utilities.android.helpers.gson.serializer.client

import am.leon.utilities.common.data.consts.Constants.SUBTYPE
import am.leon.utilities.common.data.models.exception.LeonException
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class LeonClientUnauthorizedSerializer : JsonSerializer<LeonException.Client.Unauthorized> {

    override fun serialize(
        src: LeonException.Client.Unauthorized?, typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonObject = JsonObject()
        src?.apply {
            jsonObject.addProperty(
                SUBTYPE, LeonException.Client.Unauthorized::class.java.simpleName
            )
        }
        return jsonObject
    }
}