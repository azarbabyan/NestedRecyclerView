package com.artzarbo.nestedRecyclerView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artzarbo.nestedRecyclerView.adapter.GroupAdapter
import com.artzarbo.nestedRecyclerView.model.Item
import com.artzarbo.nestedRecyclerView.model.ItemModel
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by Artur Zarbabyan on 3/10/2020.
 */
class ListFragment:Fragment() {

    private val adapter = GroupAdapter() { item ->
        //todo handle Item Click
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): Fragment {
            val fragment = ListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        groupRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        groupRecyclerView.adapter = adapter
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                groupRecyclerView.scrollToPosition(0)
            }
        })
        initData()
    }

    private fun initData(){
        val data = mutableListOf<ItemModel>()
        for (i in 1..20){
            val title = "$i"
            val items = mutableListOf<Item>()
            for (j in 0..i+5){
                items.add(Item())
            }
            data.add(ItemModel(title,items))
        }
        adapter.setData(data)
    }
}