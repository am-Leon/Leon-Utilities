package am.leon.utilities.common.data.models.dto

import com.google.gson.annotations.SerializedName

open class BaseDto(
    @SerializedName("message") var message: String? = null,
    @SerializedName("code") var code: Int? = null
)