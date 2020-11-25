package xyz.kaonmir.toheartodo.data.model

data class Book(
    val BID: Int,
    var text: String,
) {
    fun toString(separator: String): String = BID.toString() + separator + text

    companion object {
        fun fromString(separator: String, src: String): Book? {
            val arr = src.split(separator)
            return if(arr.size == 2) Book(arr[0].toInt(), arr[1])
            else null
        }
    }
}
