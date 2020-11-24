package xyz.kaonmir.toheartodo.data

import xyz.kaonmir.toheartodo.data.model.Book

class BookRepository(dataSource: BookDataSource) {
    var books: ArrayList<Book> = dataSource.getBooks()
        private set

    fun addBook(book: Book) = books.add(book)
    fun removeBook(pos: Int) = books.removeAt(pos)
    fun updateBook(pos: Int, text: String) {
        books[pos].text = text
    }

    fun getBook(pos: Int) = books[pos]
}