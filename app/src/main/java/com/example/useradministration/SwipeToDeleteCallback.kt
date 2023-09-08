package com.example.useradministration

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.useradministration.presenter.adapter.UserAdapter
import com.example.useradministration.presenter.fragment.UserListFragment
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
        val position = viewHolder.adapterPosition

        val deletedItem = adapter.removeItem(position)

        val snackbar = Snackbar.make(
            recyclerView,
            "Item deleted",
            Snackbar.LENGTH_LONG
        ).setAction("Undo") {
            // Undo the deletion
            adapter.restoreItem(position, deletedItem)
        }

        snackbar.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                if (event != DISMISS_EVENT_ACTION) {
                    fragment.deleteItemFromDatabase(deletedItem)
                }
            }
        })

        snackbar.show()
    }
}