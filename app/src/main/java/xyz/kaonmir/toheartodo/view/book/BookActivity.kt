package xyz.kaonmir.toheartodo.view.book

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import org.koin.android.ext.android.inject
import xyz.kaonmir.toheartodo.R
import xyz.kaonmir.toheartodo.data.BookRepository

class BookActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: BookAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val bookRepository: BookRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        val myDataset = bookRepository.books.toTypedArray()

        viewManager = LinearLayoutManager(this)
        viewAdapter = BookAdapter(myDataset)

        recyclerView = findViewById<RecyclerView>(R.id.book_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        viewAdapter.setOnItemClickListener { view, pos ->
            Log.i("Kaonmir", "$pos is clicked!!")
        }

    }

}