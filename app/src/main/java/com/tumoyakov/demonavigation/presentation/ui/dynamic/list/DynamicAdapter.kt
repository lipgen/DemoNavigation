package com.tumoyakov.demonavigation.presentation.ui.dynamic.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tumoyakov.demonavigation.R
import com.tumoyakov.demonavigation.domain.entity.RecordRange
import kotlinx.android.synthetic.main.item_dynamic.view.*
import kotlin.properties.Delegates

class DynamicAdapter : RecyclerView.Adapter<DynamicAdapter.ViewHolder>() {

    var dynamicList: List<RecordRange> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dynamic, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dynamicList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dynamicList[position], position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(record: RecordRange, position: Int) = with(itemView) {
            twDate.text = record.date
            twValue.text = "${record.value} ${resources.getString(R.string.symbol_ruble)}"
        }
    }
}