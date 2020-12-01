package xyz.kaonmir.toheartodo.data

import xyz.kaonmir.toheartodo.data.model.Book
import xyz.kaonmir.toheartodo.data.model.Item

class BookRepository(private val dataSource: BookDataSource) {
    var books: MutableList<Book> = dataSource.getBooks()
        private set

    fun save() = dataSource.saveBooks(books)

    fun addBook(book: Book) = books.add(book)
    fun removeBook(pos: Int) = books.removeAt(pos)
    fun updateBook(pos: Int, text: String) {
        books[pos].text = text
    }

    fun done(posBook: Int, posItem: Int) {
        val book = books[posBook]

        val item = book.notDoneItems[posItem]
        book.notDoneItems.removeAt(posItem)
        book.doneItems.add(item)
    }

    fun undone(posBook: Int, posItem: Int) {
        val book = books[posBook]

        val item = book.doneItems[posItem]
        book.doneItems.removeAt(posItem)
        book.notDoneItems.add(item)
    }

    fun addItem(pos: Int, item: Item) = books[pos].notDoneItems.add(item)

}