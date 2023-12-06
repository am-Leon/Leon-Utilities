package am.leon.utilities.android.helpers.gson.parsing

import am.leon.utilities.android.extentions.jsonArrayToHashMap
import am.leon.utilities.android.helpers.gson.adapter.IJsonParsingStrategy
import am.leon.utilities.common.data.consts.Constants.CLAZZ
import am.leon.utilities.common.data.consts.Constants.ERRORS
import am.leon.utilities.common.data.consts.Constants.HTTP_ERROR_CODE
import am.leon.utilities.common.data.consts.Constants.MESSAGE
import am.leon.utilities.common.data.consts.Constants.MESSAGE_RES
import am.leon.utilities.common.data.consts.Constants.SUBTYPE
import am.leon.utilities.common.data.models.exception.LeonException
import com.google.gson.stream.JsonReader
import org.json.JSONArray

class JSONParsingStrategy : IJsonParsingStrategy<LeonException> {
    override fun parse(reader: JsonReader): LeonException {
        reader.beginObject()
        var clazzName: String? = null
        var message: String? = null
        var subtype: String? = null
        var httpErrorCode: Int = -1
        var messageRes: Int = -1
        var errors = JSONArray()

        while (reader.hasNext()) {
            when (reader.nextName()) {
                MESSAGE -> message = reader.nextString()
                SUBTYPE -> subtype = reader.nextString()
                HTTP_ERROR_CODE -> httpErrorCode = reader.nextInt()
                MESSAGE_RES -> messageRes = reader.nextInt()
                ERRORS -> errors = getJsonArrayFromReader(reader)
                CLAZZ -> clazzName = reader.nextString()
                else -> reader.skipValue()
            }
        }

        reader.endObject()

        return when (subtype) {
            // NetworkException
            LeonException.Network.Retrial::class.java.simpleName -> LeonException.Network.Retrial(
                messageRes, message
            )

            LeonException.Network.Unhandled::class.java.simpleName -> LeonException.Network.Unhandled(
                messageRes, message
            )

            // Client
            LeonException.Client.Unauthorized::class.java.simpleName -> LeonException.Client.Unauthorized

            LeonException.Client.ResponseValidation::class.java.simpleName -> LeonException.Client.ResponseValidation(
                errors.jsonArrayToHashMap(), message
            )

            LeonException.Client.Unhandled::class.java.simpleName -> LeonException.Client.Unhandled(
                httpErrorCode, message
            )

            // Server
            LeonException.Server.InternalServerError::class.java.simpleName -> LeonException.Server.InternalServerError(
                httpErrorCode, message
            )

            // Local
            LeonException.Local.RequestValidation::class.java.simpleName -> LeonException.Local.RequestValidation(
                Class.forName(clazzName!!).kotlin, message
            )

            LeonException.Local.IOOperation::class.java.simpleName -> LeonException.Local.IOOperation(
                messageRes, message
            )

            else -> LeonException.Unknown(message)
        }
    }
}