package com.mercadolibre.android.andesui.demoapp.components.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.android.andesui.demoapp.R

class TestFragment : Fragment() {

    private lateinit var mView: View

    private val itemList = arrayListOf(
        "Item 1", "Item 2", "Item 3", "Item 4",
        "Item 5", "Item 6", "Item 7", "Item 8",
        "Item 9", "Item 10", "Item 11", "Item 12",
        "Item 13", "Item 14", "Item 15", "Item 16"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        mView = layoutInflater.inflate(R.layout.andesui_bottom_sheet_test_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList()
    }

    private fun setList() {
        val list = mView.findViewById<RecyclerView>(R.id.andes_list)
        val adapter = ListAdapter()
        list.layoutManager = LinearLayoutManager(mView.context, RecyclerView.VERTICAL, false)
        list.adapter = adapter
        adapter.setItems(itemList)
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(-1)) {
                    (activity as BottomSheetShowcaseActivity).onToggleContentShadow(false)
                } else {
                    (activity as BottomSheetShowcaseActivity).onToggleContentShadow(true)
                }
            }
        })
    }

    private class ListAdapter : RecyclerView.Adapter<ListViewHolder>() {
        private var list = arrayListOf<String>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            return ListViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.andes_layout_list_item_simple,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            holder.onBind(list.get(position))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun setItems(list: ArrayList<String>) {
            this.list = list
            notifyDataSetChanged()
        }
    }

    private class ListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(text: String) {
            val title = view.findViewById<TextView>(R.id.text_view_item_title)
            val subTitle = view.findViewById<TextView>(R.id.text_view_item_sub_title)
            val marker = view.findViewById<View>(R.id.view_item_selected)
            title.text = text
            subTitle.text = text
            marker.visibility = View.GONE
        }
    }
}
