package am.leon.utilities.extentions

import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type
import java.util.ArrayList


fun <M> String.getModelFromJSON(tokenType: Type): M = Gson().fromJson(this, tokenType)

fun <M> JSONObject.getModelFromJSON(tokenType: Type): M =
    this.toString().getModelFromJSON(tokenType)

fun <M> String.getListOfModelFromJSON(tokenType: Type): ArrayList<M> =
    Gson().fromJson(this, tokenType)

fun <M> JSONArray.getListOfModelFromJSON(tokenType: Type): ArrayList<M> =
    this.toString().getListOfModelFromJSON(tokenType)

fun JSONArray.getErrorResponseHandler(): String {
    val errorKey = StringBuilder()
    for (i in 0 until length()) {
        try {
            ("arr " + this[i]).toLog("ErrorResponseHandler")
            errorKey.append(this[i])
            if (i != length() - 1) errorKey.append("\n")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
    return errorKey.toString()
}
