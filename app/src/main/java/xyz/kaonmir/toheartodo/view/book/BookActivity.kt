package xyz.kaonmir.toheartodo.view.book

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import org.koin.android.ext.android.inject
import org.koin.core.logger.KOIN_TAG
import xyz.kaonmir.toheartodo.R
import xyz.kaonmir.toheartodo.data.BookRepository
import xyz.kaonmir.toheartodo.data.model.Book
import xyz.kaonmir.toheartodo.data.model.Item
import xyz.kaonmir.toheartodo.view.hearmode.HearActivity
import xyz.kaonmir.toheartodo.view.item.ItemActivity

class BookActivity : AppCompatActivity(), BookDragListener {
    companion object {
        const val TAG = "BookActivity"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: BookAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val bookRepository: BookRepository by inject()

    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)


        val myDataset = bookRepository.books

        viewManager = LinearLayoutManager(this)
        viewAdapter = BookAdapter(myDataset)

        recyclerView = findViewById<RecyclerView>(R.id.book_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        viewAdapter.setOnItemClickListener { view, pos ->
            val intent = Intent(this, ItemActivity::class.java).apply{
                this.putExtra("pos", pos)
            }
            startActivity(intent)
        }

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(viewAdapter, this))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.book_001_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_add -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("추가할 항목을 입력하세요")
                val input = EditText(this)
                input.inputType = InputType.TYPE_CLASS_TEXT
                builder.setView(input)
                builder.setPositiveButton("OK") { _, _ ->
                    bookRepository.addBook(Book(input.text.toString(), mutableListOf(), mutableListOf()))
                    viewAdapter.notifyItemInserted(bookRepository.books.size)
                }
                builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                builder.show()

                bookRepository.save()
            }
            R.id.menu_select -> TODO()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}