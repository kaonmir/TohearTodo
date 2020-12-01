package xyz.kaonmir.toheartodo.data.model

import java.io.Serializable

data class Book(
    var text: String,
    val doneItems: MutableList<Item>,
    val notDoneItems: MutableList<Item>
): Serializable