package am.leon.utilities.android.helpers.gson.serializer.network

import am.leon.utilities.common.data.consts.Constants.MESSAGE
import am.leon.utilities.common.data.consts.Constants.MESSAGE_RES
import am.leon.utilities.common.data.consts.Constants.SUBTYPE
import am.leon.utilities.common.data.models.exception.LeonException
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class LeonNetworkRetrialSerializer : JsonSerializer<LeonException.Network.Retrial> {

    override fun serialize(
        src: LeonException.Network.Retrial?, typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonObject = JsonObject()
        src?.apply {
            jsonObject.addProperty(MESSAGE, message)
            jsonObject.addProperty(MESSAGE_RES, messageRes)
            jsonObject.addProperty(
                SUBTYPE, LeonException.Network.Retrial::class.java.simpleName
            )
        }
        return jsonObject
    }
}