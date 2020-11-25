package xyz.kaonmir.toheartodo.view.item

import android.service.autofill.TextValueSanitizer
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import xyz.kaonmir.toheartodo.R
import xyz.kaonmir.toheartodo.data.model.Item

class ItemAdapter(private val dataset: MutableList<Item>, @LayoutRes private val layout: Int): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(v: View, pos: Int)
    }

    private var mListener: OnItemClickListener? = null

    class ItemViewHolder(itemView: View, listener: OnItemClickListener?): RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text)
        val button: ImageButton = itemView.findViewById(R.id.button)

        init {
            button.setOnClickListener { view ->
                val pos = adapterPosition
                listener?.let {
                    listener.onItemClick(view, pos)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ItemViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.textView.text = dataset[position].text
    }

    override fun getItemCount(): Int = dataset.size

    fun setOnItemClickListener(listener: (view: View, pos: Int) -> Unit) {
        mListener = object: OnItemClickListener {
            override fun onItemClick(v: View, pos: Int) = listener(v, pos)
        }
    }
}