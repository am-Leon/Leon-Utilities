package am.leon.utilities.data.models.modified

import android.content.Context

data class InputValidation(var key: String?) {

    private var value: Int? = null
    private var message: String? = null
    var isDataValid: Boolean = true

    constructor(key: String, value: Int?) : this(key) {
        this.value = value
        this.isDataValid = false
    }

    constructor(key: String, message: String?) : this(key) {
        this.message = message
        this.isDataValid = false
    }

    constructor(isDataValid: Boolean) : this(null) {
        this.value = null
        this.isDataValid = isDataValid
    }

    fun getMessage(context: Context): String? = when {
        value != null -> context.getString(value!!)
        message != null -> message!!
        else -> null
    }

    override fun toString(): String {
        return "InputValidation(key=$key, value=$value, isDataValid=$isDataValid)"
    }
}