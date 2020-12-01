package xyz.kaonmir.toheartodo.data.model

import java.io.Serializable

data class Item(
    val IID: Int,
    var text: String,
    var done: Boolean
): Serializable