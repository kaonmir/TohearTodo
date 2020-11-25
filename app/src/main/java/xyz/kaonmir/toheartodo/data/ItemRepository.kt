package xyz.kaonmir.toheartodo.data

import xyz.kaonmir.toheartodo.data.model.Book
import xyz.kaonmir.toheartodo.data.model.Item

class ItemRepository(private val dataSource: ItemDataSource) {
    var items: ArrayList<Item> = dataSource.getItems()
        private set

    fun addItem(item: Item) = items.add(item)
    fun removeItem(pos: Int) = items.removeAt(pos)
    fun updateItem(pos: Int, text: String) {
        items[pos].text = text
    }

    fun getAllItems(bid: Int) = items.filter {it.BID == bid}
    fun getItemsNotDone(bid: Int) = items.filter { it.BID == bid && !it.done }
    fun getItemsDone(bid: Int) = items.filter {it.BID == bid && it.done}

    fun save() = dataSource.setItems(items)
}