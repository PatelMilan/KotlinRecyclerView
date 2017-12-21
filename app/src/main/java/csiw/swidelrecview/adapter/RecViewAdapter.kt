package csiw.swidelrecview.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView

import csiw.swidelrecview.R
import csiw.swidelrecview.databinding.RecViewRowLayoutBinding
import csiw.swidelrecview.model.DataItem

/**
 * Created by :- Patel Milan on 20/12/17.
 * Email :- patelmilan2692@gmail.com
 */

class RecViewAdapter(private val mContext: Context, private val mDataItemList: ArrayList<DataItem>) : RecyclerView.Adapter<RecViewAdapter.RecViewHolder>() {
    private var mBinding: RecViewRowLayoutBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.rec_view_row_layout, parent, false)
        return RecViewHolder(mBinding!!)
    }

    override fun onBindViewHolder(holder: RecViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.title).text = mDataItemList[position].title
//        holder.itemView.findViewById<TextView>(R.id.sub_title).text = mDataItemList[position].subTitle
//        holder.itemView.findViewById<RatingBar>(R.id.rating_bar).numStars = mDataItemList[position].ratting
//        holder.itemView.findViewById<TextView>(R.id.description).text = mDataItemList[position].description
        holder.itemView.findViewById<TextView>(R.id.date_text).text = mDataItemList[position].date
    }

    override fun getItemCount(): Int {
        return mDataItemList.size
    }

    fun removeItem(position: Int) {
        mDataItemList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: DataItem, position: Int) {
        mDataItemList.add(position, item)
        notifyItemInserted(position)
    }

    inner class RecViewHolder(mBinding: RecViewRowLayoutBinding) : RecyclerView.ViewHolder(mBinding.root)
}
