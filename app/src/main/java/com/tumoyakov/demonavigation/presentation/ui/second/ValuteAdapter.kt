package com.tumoyakov.demonavigation.presentation.ui.second

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tumoyakov.demonavigation.R
import com.tumoyakov.demonavigation.domain.entity.Valute
import kotlinx.android.synthetic.main.item_valute.view.*
import kotlin.properties.Delegates.observable

class ValuteAdapter : RecyclerView.Adapter<ValuteAdapter.ViewHolder>() {

    var valutesList: List<Valute> by observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_valute, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = valutesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(valutesList[position], position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(valute: Valute, position: Int) = with(itemView) {
            tvNumCode.text = valute.numCode.toString()
            tvCharCode.text = valute.charCode
            tvName.text = valute.name
            tvValue.text = "${valute.value} ${resources.getString(R.string.symbol_ruble)}"
            clItemValute.setOnClickListener {
                val action =
                    SecondFragmentDirections.ActionSecondFragmentToDynamicListFragment(valute.id, valute.name)
                it.findNavController().navigate(action)
            }
        }
    }
}