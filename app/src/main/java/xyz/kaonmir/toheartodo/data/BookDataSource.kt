package xyz.kaonmir.toheartodo.data

import xyz.kaonmir.toheartodo.data.model.Book

class BookDataSource {
    fun getBooks(): ArrayList<Book> {
        return arrayListOf(
            Book(1, "123"),
            Book(2, "afh"),
            Book(3, "aetn"),
            Book(4, "1wate"),
        )
    }


}