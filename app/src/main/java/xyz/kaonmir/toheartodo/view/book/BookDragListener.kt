package xyz.kaonmir.toheartodo.view.book

import androidx.recyclerview.widget.RecyclerView

interface BookDragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}