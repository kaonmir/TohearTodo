package xyz.kaonmir.toheartodo.view.book

interface BookActionListener {
    fun onItemMoved(from: Int, to: Int)
    fun onItemSwiped(position: Int, direction: Int)
}