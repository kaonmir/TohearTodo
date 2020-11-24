package xyz.kaonmir.toheartodo.view.book

import android.service.autofill.TextValueSanitizer
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import xyz.kaonmir.toheartodo.R
import xyz.kaonmir.toheartodo.data.model.Book

class BookAdapter(private val dataset: Array<Book>): RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(v: View, pos: Int)
    }

    private var mListener: OnItemClickListener? = null

    class BookViewHolder(itemView: View, listener: OnItemClickListener?): RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text)
        init {
            itemView.setOnClickListener { view ->
                val pos = adapterPosition
                listener?.let {
                    listener.onItemClick(view, pos)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_book, parent, false)
        return BookViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.textView.text = dataset[position].text
    }

    override fun getItemCount(): Int = dataset.size

    fun setOnItemClickListener(listener: (view: View, pos: Int) -> Unit) {
        mListener = object: OnItemClickListener {
            override fun onItemClick(v: View, pos: Int) = listener(v, pos)
        }
    }
}