package xyz.kaonmir.toheartodo.data.model

data class Book(
    val BID: Int,
    var text: String
) {
    fun toString(separator: String): String = BID.toString() + separator + text

}
