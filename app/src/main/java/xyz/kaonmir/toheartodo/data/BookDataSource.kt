package xyz.kaonmir.toheartodo.data

import android.content.Context
import android.util.Log
import xyz.kaonmir.toheartodo.data.model.Book
import java.io.*
import java.io.File.separator

class BookDataSource(context: Context) {
    private val directory = context.filesDir
    private val file = File(directory, "asdf.txt")

    fun getBooks(): ArrayList<Book> {
        val books = ArrayList<Book>()

        try {
            val buf = BufferedReader(FileReader(file))
            var line: String? = buf.readLine()
            while (line != null) {
                val arr = line.split(separator)
                Log.i("kaonmir", arr.size.toString())
                if (arr.size == 2) books.add(Book(arr[0].toInt(), arr[1]))
                line = buf.readLine()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return books
    }

    fun setBooks(books: ArrayList<Book>) {
        try {
            val buf = BufferedWriter(FileWriter(file))
            for (book in books) {
                buf.write(book.toString(separator))
                buf.newLine()
            }
            buf.close()
        }  catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val separator = ";"
    }

}