package am.leon.utilities.android.helpers.inputValidation

import android.content.Context

data class InputFieldError(var key: IInputField, var message: Any)

fun InputFieldError.getErrorMessage(context: Context): String = when (message) {
    is Int -> context.getString(message as Int)
    else -> message as String
}
