package am.leon.utilities.common.domain.repository.local.keyValue

interface IStorageKeyValue {
    suspend fun <T> saveEntry(key: IStorageKeyEnum, data: T)
    suspend fun <T> readEntry(key: IStorageKeyEnum, defaultValue: T): T
    suspend fun <T> clearEntry(key: IStorageKeyEnum, defaultValue: T)
    suspend fun hasEntry(key: IStorageKeyEnum): Boolean
}