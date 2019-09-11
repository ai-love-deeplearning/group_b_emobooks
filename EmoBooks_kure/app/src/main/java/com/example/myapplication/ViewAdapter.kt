package com.example.myapplication

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ViewAdapter(private val list: List<ItemData>,
                  private val context: Context,
                  private val itemClickListener: ViewHolder.ItemClickListener) : RecyclerView.Adapter<ViewHolder>() {
    private var mRecyclerView : RecyclerView? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("Adapter", "onCreateViewHolder")
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.my_item, parent, false)
        mView.setOnClickListener { view ->
            mRecyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }
        return ViewHolder(mView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Adapter", "onBindViewHolder")
        holder.titleView.text = list[position].category
        holder.detailView.text = list[position].detail
    }
    override fun getItemCount(): Int {
        Log.d("Adapter", "getItemCount")
        return list.size
    }
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null
    }
}