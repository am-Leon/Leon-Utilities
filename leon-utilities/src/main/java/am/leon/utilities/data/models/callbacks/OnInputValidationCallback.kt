package am.leon.utilities.data.models.callbacks

import am.leon.utilities.data.models.modified.InputValidation

interface OnInputValidationCallback {
    fun onInputValidation(inputValidation: InputValidation)
}