package csiw.swidelrecview.ui

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import csiw.swidelrecview.R
import csiw.swidelrecview.adapter.RecViewAdapter
import csiw.swidelrecview.databinding.ActivityMainBinding
import csiw.swidelrecview.model.DataItem
import csiw.swidelrecview.uiUtils.RecyclerItemTouchHelper

class MainActivity : AppCompatActivity(), RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private var mDataItem = ArrayList<DataItem>()
    private var mAdapter: RecViewAdapter? = null
    private var mBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        mAdapter = RecViewAdapter(this@MainActivity, setDataItem(13))
        mBinding?.recyclerView?.layoutManager = LinearLayoutManager(this@MainActivity)
        mBinding?.recyclerView?.adapter = mAdapter

        val itemTouchHelperCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mBinding?.recyclerView)

        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Row is swiped from recycler view
                // remove it from adapter
            }
        }
    }

    private fun setDataItem(size: Int): ArrayList<DataItem> {
        for (i in 1..size) {
            val dataItem = DataItem()
            dataItem.id = i
            dataItem.title = "Title" + i
            dataItem.subTitle = "Sub Title" + i
            dataItem.description = "Android Kotlin RecyclerView Demos"
            dataItem.ratting = 4
            if (i < 9)
                dataItem.date = "Date :  0" + i + "-12-2017"
            else
                dataItem.date = "Date :  " + i + "-12-2017"
            mDataItem.add(dataItem)
        }
        return mDataItem
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        if (viewHolder is RecViewAdapter.RecViewHolder) {
            // get the removed item name to display it in snack bar
            val name = mDataItem[viewHolder.getAdapterPosition()].title

            // backup of removed item for undo purpose
            val deletedItem = mDataItem[viewHolder.getAdapterPosition()]
            val deletedIndex = viewHolder.getAdapterPosition()

            // remove the item from recycler view
            mAdapter?.removeItem(viewHolder.getAdapterPosition())

            // showing snack bar with Undo option
            val snackbar = Snackbar
                    .make(mBinding?.coordinatorLayout!!, name!! + " removed from cart!", Snackbar.LENGTH_LONG)
            snackbar.setAction("UNDO", {
                // undo is selected, restore the deleted item
                mAdapter?.restoreItem(deletedItem, deletedIndex)
            })
            snackbar.setActionTextColor(Color.YELLOW)
            snackbar.show()
        }
    }
}