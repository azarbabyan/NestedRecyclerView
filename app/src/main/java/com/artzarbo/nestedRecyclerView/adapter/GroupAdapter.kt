package com.artzarbo.nestedRecyclerView.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artzarbo.nestedRecyclerView.R
import com.artzarbo.nestedRecyclerView.model.Item
import com.artzarbo.nestedRecyclerView.model.ItemModel
import com.artzarbo.nestedRecyclerView.utils.DividerItemDecoration
import kotlinx.android.synthetic.main.group_layout.view.*

/**
 * Created by Artur Zarbabyan on 3/10/2020.
 */
class GroupAdapter(val callback: (Item) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    private var list:MutableList<ItemModel> = mutableListOf()

    fun setData(data :MutableList<ItemModel>){
        list = data
        notifyDataSetChanged()
    }

    enum class ItemType {
        HEADER,
        ITEM
    }


    override fun getItemViewType(position: Int): Int {
        val data = list[position]
        return if (data.title != null && data.items!=null){
            ItemType.ITEM.ordinal
        }else {
            ItemType.HEADER.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.HEADER.ordinal -> HeaderViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.header_layout, parent, false))
            ItemType.ITEM.ordinal -> ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.group_layout,parent,false)).apply {
                view.recyclerView.setRecycledViewPool(viewPool)
            }
            else -> throw IllegalArgumentException("Wrong view type")
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder){
            holder.bindData(list[position],callback)
        }
    }

    class ItemViewHolder(val view: View):RecyclerView.ViewHolder(view){
        fun bindData(data: ItemModel,callback: (Item) -> Unit) {
            if (data.items == null){
                return
            }
            if (data.title != null){
                view.groupTitle.text = data.title
                view.groupTitle.visibility = View.VISIBLE
            }else {
                view.groupTitle.visibility = View.GONE
            }
            val layoutManger = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
            view.recyclerView.layoutManager = layoutManger
            view.recyclerView.itemAnimator = DefaultItemAnimator()
            val dividerItemDecoration = DividerItemDecoration(view.context, ContextCompat.getDrawable(view.context, R.drawable.divider), DividerItemDecoration.VERTICAL_LIST, false)
            view.recyclerView.addItemDecoration(dividerItemDecoration)
            val adapter = ItemAdapter{itemModel ->
                callback(itemModel)
            }
            view.recyclerView.adapter = adapter
            adapter.setData(data.items)
        }
    }

    class HeaderViewHolder(val view: View):RecyclerView.ViewHolder(view)

}