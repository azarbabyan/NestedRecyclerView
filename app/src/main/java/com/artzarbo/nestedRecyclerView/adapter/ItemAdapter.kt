package com.artzarbo.nestedRecyclerView.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artzarbo.nestedRecyclerView.R
import com.artzarbo.nestedRecyclerView.model.Item
import kotlinx.android.synthetic.main.item_layout.view.*

/**
 * Created by Artur Zarbabyan on 3/10/2020.
 */
class ItemAdapter(val callback: (Item) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list:MutableList<Item> = mutableListOf()

    fun setData(data :MutableList<Item>){
        list = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)).also { itemViewHolder ->
            itemViewHolder.view.setOnClickListener {
                callback(list[itemViewHolder.adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder){
            holder.bindData(list[position])
        }
    }

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindData(data: Item) {
            view.startAddressTv.text = data.startLocation
            view.endAddressTv.text = data.endLocation
            view.infoTextTv.text = data.summary
            view.timeTv.text = data.duration.toString()
            view.distTv.text = data.distance.toString()
            view.valueTv.text = data.fare.toString()
        }
    }
}