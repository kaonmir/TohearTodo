package xyz.kaonmir.toheartodo.view.book

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color.red
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import xyz.kaonmir.toheartodo.R


class ItemTouchHelperCallback(private val listener: BookActionListener, private val context: Context): ItemTouchHelper.Callback() {
    private val iconDelete = ContextCompat.getDrawable(context, R.drawable.ic_trash)!!
    private val iconArchive = ContextCompat.getDrawable(context, R.drawable.ic_archive)!!
    private val backgroundRed = ColorDrawable(context.getColor(R.color.red))
    private val backgroundBlue = ColorDrawable(context.getColor(R.color.blue))
    private val LIMIT_SWIPE_LENGTH = 10

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = 0 // ItemTouchHelper.DOWN or ItemTouchHelper.UP
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onItemSwiped(viewHolder.adapterPosition, direction)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float  = 0.4f

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val dXNew = dX / LIMIT_SWIPE_LENGTH
        super.onChildDraw(c, recyclerView, viewHolder, dXNew, dY, actionState, isCurrentlyActive)


        val itemView = viewHolder.itemView
        val backgroundCornerOffset = 30;

//        Log.i(TAG, "dx: $dXNew, dy: $dY, (${itemView.left}, ${itemView.right}, ${itemView.height}, ${itemView.width})")

        when {
            dXNew < 0 -> { // Left Swipe
                val iconMargin = (itemView.height - iconDelete.intrinsicHeight) / 2
                val iconTop = itemView.top + (itemView.height - iconDelete.intrinsicHeight) / 2
                val iconBottom = iconTop + iconDelete.intrinsicHeight

                val iconLeft = itemView.right - iconMargin - iconDelete.intrinsicWidth
                val iconRight = itemView.right - iconMargin

                iconDelete.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                backgroundRed.setBounds(itemView.right + dXNew.toInt() - backgroundCornerOffset,
                        itemView.top, itemView.right, itemView.bottom)
            }
            dXNew > 0 -> {
                val iconMargin = (itemView.height - iconArchive.intrinsicHeight) / 2
                val iconTop = itemView.top + (itemView.height - iconArchive.intrinsicHeight) / 2
                val iconBottom = iconTop + iconArchive.intrinsicHeight

                val iconLeft = itemView.left + iconMargin
                val iconRight = itemView.left + iconMargin + iconArchive.intrinsicWidth

                iconArchive.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                backgroundBlue.setBounds(itemView.left, itemView.top,
                        itemView.left + dXNew.toInt() + backgroundCornerOffset, itemView.bottom)
            }
            else -> {
                backgroundRed.setBounds(0, 0, 0, 0)
                backgroundBlue.setBounds(0, 0, 0, 0)
            }
        }

        backgroundRed.draw(c);
        backgroundBlue.draw(c)
        iconDelete.draw(c)
        iconArchive.draw(c)

    }

    companion object {
        const val TAG = "ItemTouchHelperCallback"
    }
}
