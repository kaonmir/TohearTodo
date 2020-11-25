package xyz.kaonmir.toheartodo.data.model

data class Item(
    val IID: Int,
    val BID: Int,
    var text: String,
    var done: Boolean
) {
    fun toString(separator: String): String = IID.toString() + separator +
            BID.toString() + separator + text + separator + done.toString()

    companion object {
        fun fromString(separator: String, src: String): Item? {
            val arr = src.split(separator)
            return if(arr.size == 4) Item(arr[0].toInt(), arr[1].toInt(), arr[2], arr[3].toBoolean())
            else null
        }
    }
}
