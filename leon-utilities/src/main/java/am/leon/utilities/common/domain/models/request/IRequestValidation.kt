package am.leon.utilities.common.domain.models.request

import am.leon.utilities.common.data.models.exception.LeonException

interface IRequestValidation {
    fun isInitialState(): Boolean
    val remoteMap: RemoteRequest

    fun getRequestContracts(): HashMap<RequestContractType, HashMap<String, Boolean>>

    fun getOptionalKeys(contractType: RequestContractType): Map<String, Any> {
        val requestContract = getRequestContracts()[contractType] ?: emptyMap()
        val requestMap = remoteMap.requestBody + remoteMap.requestQueries + remoteMap.requestHeaders
        return requestContract
            .filterNot { it.value }
            .mapNotNull { entry ->
                val key = entry.key
                val value = requestMap[key]
                if (value != null) key to value else null
            }
            .toMap()
    }

    fun validateRequestContract() {
        val contract = getRequestContracts()
        val requestMap = remoteMap.requestBody + remoteMap.requestQueries + remoteMap.requestHeaders
        println("contract are : $contract")
        println("requestMap are : $requestMap")
        if (isRequestSizeValid(contract, requestMap).not())
            throwWhenMapSizeNotValid()

        validateRemoteRequest(contract, requestMap)
    }

    private fun isRequestSizeValid(
        contract: HashMap<RequestContractType, HashMap<String, Boolean>>,
        requestMap: Map<String, Any>
    ): Boolean {
        val minExpectedSize = contract.values.sumOf { map -> map.count { it.value } }
        val maxExpectedSize = contract.values.sumOf { it.size }
        val actualSize = requestMap.values.size
        return actualSize in minExpectedSize..maxExpectedSize
    }

    private fun validateRemoteRequest(
        contractKeys: HashMap<RequestContractType, HashMap<String, Boolean>>,
        requestBody: Map<String, Any>
    ) {
        contractKeys.forEach { (contractType, keyMap) ->
            keyMap.forEach { (key, isRequired) ->
                val value = requestBody[key]
                if (isRequired && key !in requestBody) {
                    throwWhenMissingKey(contractType, key)
                }
                when (value) {
                    is String, is ByteArray -> {
                        if (isRequired && (value as? String)?.isEmpty() == true || (value as? ByteArray)?.isEmpty() == true) {
                            throwWhenKeyHasEmptyValue(contractType, key)
                        }
                    }
                }
            }
        }
    }

    private fun throwWhenKeyHasEmptyValue(contractType: RequestContractType, key: String) {
        throw LeonException.Local.RequestValidation(
            clazz = this::class,
            message = "Your ${contractType.name} Request key: $key has an empty value."
        )
    }

    private fun throwWhenMissingKey(contractType: RequestContractType, key: String) {
        throw LeonException.Local.RequestValidation(
            clazz = this::class,
            message = "Your ${contractType.name} Request has a missing key: $key."
        )
    }

    private fun throwWhenMapSizeNotValid() {
        throw LeonException.Local.RequestValidation(
            clazz = this::class,
            message = "Your Remote Request does not meet the minimum contract requirements."
        )
    }
}