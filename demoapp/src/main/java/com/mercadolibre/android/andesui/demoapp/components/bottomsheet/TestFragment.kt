package com.mercadolibre.android.andesui.demoapp.components.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.android.andesui.databinding.AndesLayoutListItemSimpleBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiBottomSheetTestFragmentBinding

class TestFragment : Fragment() {

    private val itemList = arrayListOf(
        "Item 1", "Item 2", "Item 3", "Item 4",
        "Item 5", "Item 6", "Item 7", "Item 8",
        "Item 9", "Item 10", "Item 11", "Item 12",
        "Item 13", "Item 14", "Item 15", "Item 16"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return AndesuiBottomSheetTestFragmentBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList(AndesuiBottomSheetTestFragmentBinding.bind(view))
    }

    private fun setList(binding: AndesuiBottomSheetTestFragmentBinding) {
        val list = binding.andesList
        val adapter = ListAdapter()
        list.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        list.adapter = adapter
        adapter.setItems(itemList)
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(-1)) {
                    (activity as? BottomSheetShowcaseActivity)?.onToggleContentShadow(false)
                } else {
                    (activity as? BottomSheetShowcaseActivity)?.onToggleContentShadow(true)
                }
            }
        })
    }

    private class ListAdapter : RecyclerView.Adapter<ListViewHolder>() {
        private var list = arrayListOf<String>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            return ListViewHolder(
                AndesLayoutListItemSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            holder.onBind(list[position])
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun setItems(list: ArrayList<String>) {
            this.list = list
            notifyDataSetChanged()
        }
    }

    private class ListViewHolder(private val binding: AndesLayoutListItemSimpleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(text: String) {
            binding.textViewItemTitle.text = text
            binding.textViewItemSubTitle.text = text
            binding.viewItemSelected.visibility = View.GONE
        }
    }
}
