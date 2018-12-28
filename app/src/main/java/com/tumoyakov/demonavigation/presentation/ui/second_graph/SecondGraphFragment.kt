package com.tumoyakov.demonavigation.presentation.ui.second_graph

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.tumoyakov.demonavigation.R
import com.tumoyakov.demonavigation.domain.entity.Valute
import com.tumoyakov.demonavigation.presentation.viewmodel.second.SecondFragmentViewModel
import com.tumoyakov.demonavigation.presentation.viewmodel.second.SecondFragmentViewModelFactory
import kotlinx.android.synthetic.main.fragment_second_graph.*
import org.jetbrains.anko.support.v4.act

class SecondGraphFragment : Fragment() {

    private lateinit var viewModel: SecondFragmentViewModel
    private var valList = emptyList<Valute>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders
            .of(act, SecondFragmentViewModelFactory())
            .get(SecondFragmentViewModel::class.java)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        requireActivity().title = resources.getString(R.string.fragment_second_graph_title)
        initContent()
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }

    private fun initContent() {
        var names: Array<String>
        var values: Array<Double>
        ghSecond.title = "${resources.getString(R.string.fragment_second_graph_title)}"
        viewModel.valCurs.observe(this.viewLifecycleOwner, Observer { it ->
            valList = it.valute
            names = Array(valList.size) { valList[it].charCode }
            values = Array(valList.size) { valList[it].value.replace(',', '.').toDouble() }
            val series = BarGraphSeries<DataPoint>(Array(valList.size) { DataPoint(it.toDouble(), values[it]) })
            series.spacing = 0
            series.isAnimated = true
            ghSecond.addSeries(series)
            ghSecond.viewport.isScalable = true // enables horizontal zooming and scrolling
            ghSecond.viewport.setScalableY(true)
        })
    }


}