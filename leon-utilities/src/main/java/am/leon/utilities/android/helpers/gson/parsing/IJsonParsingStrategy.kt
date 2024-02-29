package am.leon.utilities.android.helpers.gson.parsing

import com.google.gson.stream.JsonReader

interface IJsonParsingStrategy<T> {
    fun parse(reader: JsonReader): T
}