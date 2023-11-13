package am.leon.utilities.common.domain.repository.local.keyValue

interface ILocalStorageKVDS {
    val storageKV: IStorageKeyValue
    fun clearDataStoreFile(): Boolean
}