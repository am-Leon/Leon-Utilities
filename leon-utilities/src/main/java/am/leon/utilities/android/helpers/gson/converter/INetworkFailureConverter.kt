package am.leon.utilities.android.helpers.gson.converter

interface INetworkFailureConverter<ErrorBody> {
    suspend fun produceErrorBodyFailure(code: Int, errorBody: ErrorBody?): Throwable
    fun produceFailure(throwable: Throwable): Throwable
}