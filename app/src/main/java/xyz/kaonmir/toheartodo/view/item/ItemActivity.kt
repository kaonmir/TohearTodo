package xyz.kaonmir.toheartodo.view.item

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import xyz.kaonmir.toheartodo.R
import xyz.kaonmir.toheartodo.data.BookRepository
import xyz.kaonmir.toheartodo.data.model.Item
import xyz.kaonmir.toheartodo.view.hearmode.HearActivity

class ItemActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ItemAdapter
    private lateinit var recyclerView_done: RecyclerView
    private lateinit var viewAdapter_done: ItemAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewManager_done: RecyclerView.LayoutManager

    private lateinit var textView_done: TextView

    private var posBook: Int = 0
    private lateinit var  dataNotDone: MutableList<Item>
    private lateinit var dataDone: MutableList<Item>

    private val bookRepository: BookRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        textView_done = findViewById(R.id.textView_done)

        posBook = intent.getIntExtra("pos", 0)
        dataNotDone = bookRepository.books[posBook].notDoneItems
        dataDone = bookRepository.books[posBook].doneItems

        viewManager = LinearLayoutManager(this)
        viewManager_done = LinearLayoutManager(this)
        viewAdapter = ItemAdapter(dataNotDone, R.layout.recyclerview_item)
        viewAdapter_done = ItemAdapter(dataDone, R.layout.recyclerview_item_done)

        recyclerView = findViewById<RecyclerView>(R.id.item_recycler_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        recyclerView_done = findViewById<RecyclerView>(R.id.item_recycler_view_done).apply {
            layoutManager = viewManager_done
            adapter = viewAdapter_done
        }

        updateUI()

        viewAdapter.setOnItemClickListener { _, pos ->
            bookRepository.done(posBook, pos)
            updateUI()
        }

        viewAdapter_done.setOnItemClickListener { _, pos ->
            bookRepository.undone(posBook, pos)
            updateUI()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_001_main, menu)
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
                    bookRepository.addItem(posBook, Item(10, input.text.toString(), false))
                    viewAdapter.notifyItemInserted(dataNotDone.size)
                }
                builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                builder.show()
            }
            R.id.menu_hear_mode -> {
                if(dataNotDone.size != 0) {
                    val intent = Intent(this, HearActivity::class.java).apply{
                        this.putExtra("pos", posBook)
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this@ItemActivity, "항목이 하나도 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.menu_select -> TODO()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

     // TODO("저장하는 거 매우 이상하다. ㅠㅠㅠ")
    private fun updateUI() {
         val size = dataDone.size
         textView_done.text = resources.getString(R.string.item_001_main_done, size)
         bookRepository.save()
         viewAdapter.notifyDataSetChanged()
         viewAdapter_done.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }
}