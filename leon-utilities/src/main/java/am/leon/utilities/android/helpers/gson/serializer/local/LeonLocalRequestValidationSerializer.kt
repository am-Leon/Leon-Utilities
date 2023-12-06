package am.leon.utilities.android.helpers.gson.serializer.local

import am.leon.utilities.common.data.consts.Constants.CLAZZ
import am.leon.utilities.common.data.consts.Constants.MESSAGE
import am.leon.utilities.common.data.consts.Constants.SUBTYPE
import am.leon.utilities.common.data.models.exception.LeonException
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class LeonLocalRequestValidationSerializer : JsonSerializer<LeonException.Local.RequestValidation> {

    override fun serialize(
        src: LeonException.Local.RequestValidation?, typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonObject = JsonObject()
        src?.apply {
            jsonObject.addProperty(MESSAGE, message)
            jsonObject.addProperty(CLAZZ, clazz.java.name)
            jsonObject.addProperty(
                SUBTYPE, LeonException.Local.RequestValidation::class.java.simpleName
            )
        }
        return jsonObject
    }
}