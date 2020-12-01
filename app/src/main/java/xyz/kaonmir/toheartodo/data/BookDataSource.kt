package xyz.kaonmir.toheartodo.data

import android.content.Context
import android.util.Log
import xyz.kaonmir.toheartodo.data.model.Book
import java.io.*
import java.io.File.separator

class BookDataSource(private val context: Context) {
    companion object {
        const val TAG = "BookDataSource"
        const val FILE_NAME = "data.pref"
    }

    fun saveBooks(books: MutableList<Book>) {
        try {
            val fileOutputStream  = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            val output = ObjectOutputStream(fileOutputStream)

            output.writeObject(books)

            output.close()
            fileOutputStream.close()

            Log.i(TAG, "Save complete!")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getBooks(): MutableList<Book> {
        var savedArrayList: MutableList<Book>? = null
        try {
            val inputStream = context.openFileInput(FILE_NAME)
            val input = ObjectInputStream(inputStream)
            savedArrayList = input.readObject() as MutableList<Book>?
            input.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return savedArrayList ?: mutableListOf()
    }
}