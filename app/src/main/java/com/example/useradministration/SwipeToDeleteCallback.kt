package com.example.useradministration

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.useradministration.presenter.UserAdapter
import com.example.useradministration.presenter.UserListFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class SwipeToDeleteCallback(
    private val adapter: UserAdapter,
    private val recyclerView: RecyclerView,
    private val fragment: UserListFragment
) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        // Enable swipe only in the left direction
        val swipeFlags = ItemTouchHelper.LEFT
        return makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Get the position of the swiped item
        val position = viewHolder.adapterPosition

        // Remove the item from the list
        val deletedItem = adapter.removeItem(position)

        // Show an undo action
        val snackbar = Snackbar.make(
            recyclerView,
            "Item deleted",
            Snackbar.LENGTH_LONG
        ).setAction("Undo") {
            // Undo the deletion
            adapter.restoreItem(position, deletedItem)
        }

        // Set a callback for when the Snackbar se encerrar (não clicar em "Undo")
        snackbar.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                if (event != DISMISS_EVENT_ACTION) {
                    // Exclua permanentemente o item do banco de dados
                    fragment.deleteItemFromDatabase(deletedItem)
                }
            }
        })

        snackbar.show()
    }
}

