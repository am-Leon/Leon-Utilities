package am.leon.utilities.extentions

import am.leon.utilities.data.models.callbacks.OnInputValidationCallback
import am.leon.utilities.data.models.modified.InputValidation
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type


fun <M> String.getModelFromJSON(tokenType: Type): M = Gson().fromJson(this, tokenType)

fun <M> JSONObject.getModelFromJSON(tokenType: Type): M =
    this.toString().getModelFromJSON(tokenType)

fun <M> String.getListOfModelFromJSON(tokenType: Type): ArrayList<M> =
    Gson().fromJson(this, tokenType)

fun <M> JSONArray.getListOfModelFromJSON(tokenType: Type): ArrayList<M> =
    this.toString().getListOfModelFromJSON(tokenType)

fun JSONObject.extractResponseErrors(validationCallback: OnInputValidationCallback) {
    val iterator = keys()
    while (iterator.hasNext()) {
        val key = iterator.next()
        var errorValue: String? = null
        try {
            errorValue = getJSONArray(key).getErrorResponseHandler()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val inputValidation = InputValidation(key, errorValue)
        validationCallback.onInputValidation(inputValidation)
    }
}

private fun JSONArray.getErrorResponseHandler(): String {
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
