package am.leon.utilities.extentions

import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type

fun <M> M.toJson(): String = Gson().toJson(this)

fun <M> String.getModelFromJSON(tokenType: Type): M = Gson().fromJson(this, tokenType)

fun <M> JSONObject.getModelFromJSON(tokenType: Type): M =
    this.toString().getModelFromJSON(tokenType)

fun <M> String.getListOfModelFromJSON(tokenType: Type): ArrayList<M> =
    Gson().fromJson(this, tokenType)

fun <M> JSONArray.getListOfModelFromJSON(tokenType: Type): ArrayList<M> =
    this.toString().getListOfModelFromJSON(tokenType)

fun JSONObject.toHashMap(): HashMap<String, String?> {
    val iterator = keys()
    val map = hashMapOf<String, String?>()
    while (iterator.hasNext()) {
        val key = iterator.next()
        var errorValue: String? = null
        try {
            errorValue = getJSONArray(key).extractStringsFromErrorList()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        map[key] = errorValue
    }
    return map
}

private fun JSONArray.extractStringsFromErrorList(): String {
    val errorKey = StringBuilder()
    for (i in 0 until length()) {
        try {
            errorKey.append(this[i])
            if (i != length() - 1) errorKey.append("\n")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
    return errorKey.toString()
}
