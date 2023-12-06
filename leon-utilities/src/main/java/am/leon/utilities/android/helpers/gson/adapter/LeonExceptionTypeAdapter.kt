package am.leon.utilities.android.helpers.gson.adapter

import am.leon.utilities.common.data.models.exception.LeonException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class LeonExceptionTypeAdapter(private val parsingStrategy: IJsonParsingStrategy<LeonException>) :
    TypeAdapter<LeonException>() {

    @Throws(Exception::class)
    override fun write(out: JsonWriter, value: LeonException) {
        // Implement serialization if needed (not used in your case)
    }

    @Throws(Exception::class)
    override fun read(reader: JsonReader): LeonException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return LeonException.Unknown("reader is null.")
        }

        return parsingStrategy.parse(reader)
    }
}