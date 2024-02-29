package am.leon.utilities.android.extentions

import com.google.gson.Gson
import org.json.JSONArray
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
