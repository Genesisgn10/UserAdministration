package com.example.utils

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewSwipeHelper(
    private val context: Context,
    private val onSwipeCallback: (position: Int) -> Unit
) {

    fun attachToRecyclerView(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                onSwipeCallback(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}