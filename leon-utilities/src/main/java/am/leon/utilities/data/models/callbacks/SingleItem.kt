package am.leon.utilities.data.models.callbacks

interface SingleItem {
    val id: Int
    var name: String
    var selected: Boolean
    fun getIcon(): String? = null
    fun getIconRes(): Int = -1
}