package xyz.kaonmir.toheartodo.view.book

import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import xyz.kaonmir.toheartodo.R
import xyz.kaonmir.toheartodo.data.model.Book

class BookAdapter(private val dataset: MutableList<Book>):
        RecyclerView.Adapter<BookAdapter.BookViewHolder>(), BookActionListener{
    interface OnItemClickListener {
        fun onItemClick(v: View, pos: Int)
    }

    private var onClickListener: OnItemClickListener? = null
    private var onDragListener: BookDragListener? = null

    class BookViewHolder(itemView: View, onClickListener: OnItemClickListener?, onDragListener: BookDragListener?):
            RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView_book)
        init {
            itemView.setOnClickListener { view ->
                val pos = adapterPosition
                onClickListener?.onItemClick(view, pos)
            }

            itemView.setOnDragListener { _, event ->
                if(event.action == MotionEvent.ACTION_DOWN) {
                    onDragListener?.onStartDrag(this)
                }
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_book, parent, false)
        return BookViewHolder(view, onClickListener, onDragListener)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.textView.text = dataset[position].text
    }

    override fun getItemCount(): Int = dataset.size

    fun setOnItemClickListener(listener: (view: View, pos: Int) -> Unit) {
        onClickListener = object: OnItemClickListener {
            override fun onItemClick(v: View, pos: Int) = listener(v, pos)
        }
    }

    override fun onItemMoved(from: Int, to: Int) {}
    override fun onItemSwiped(position: Int, direction: Int) {
        when (direction) {
            ItemTouchHelper.START -> { // Deleted
                notifyItemRemoved(position)
                dataset.removeAt(position)
            }
            ItemTouchHelper.END -> {
                TODO("archived must be implemented after implementing sidebar")
            }
        }
    }

    companion object {
        const val TAG = "BookAdapter"
    }
}