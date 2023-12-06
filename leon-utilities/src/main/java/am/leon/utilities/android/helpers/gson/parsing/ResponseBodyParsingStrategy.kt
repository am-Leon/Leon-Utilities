package am.leon.utilities.android.helpers.gson.parsing

import am.leon.utilities.android.extentions.jsonArrayToHashMap
import am.leon.utilities.android.helpers.gson.adapter.IJsonParsingStrategy
import am.leon.utilities.common.data.consts.Constants.CODE
import am.leon.utilities.common.data.consts.Constants.ERRORS
import am.leon.utilities.common.data.consts.Constants.MESSAGE
import am.leon.utilities.common.data.models.exception.LeonException
import com.google.gson.stream.JsonReader
import org.json.JSONArray

class ResponseBodyParsingStrategy : IJsonParsingStrategy<LeonException> {
    override fun parse(reader: JsonReader): LeonException {
        reader.beginObject()
        var message: String? = null
        var httpErrorCode: Int? = null
        var errors: JSONArray? = null

        while (reader.hasNext()) {
            when (reader.nextName()) {
                MESSAGE -> message = reader.nextString()
                CODE -> httpErrorCode = reader.nextInt()
                ERRORS -> errors = getJsonArrayFromReader(reader)
                else -> reader.skipValue()
            }
        }
        reader.endObject()

        return when (httpErrorCode) {
            422 -> LeonException.Client.ResponseValidation(
                errors = errors?.jsonArrayToHashMap() ?: hashMapOf(), message = message
            )

            else -> LeonException.Client.Unhandled(
                httpErrorCode = httpErrorCode ?: -1, message = message
            )
        }
    }
}