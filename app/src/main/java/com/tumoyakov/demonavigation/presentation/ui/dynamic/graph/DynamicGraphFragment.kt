package com.tumoyakov.demonavigation.presentation.ui.dynamic.graph

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.tumoyakov.demonavigation.R
import com.tumoyakov.demonavigation.convertStrToDate
import com.tumoyakov.demonavigation.domain.entity.ValDyn
import com.tumoyakov.demonavigation.presentation.viewmodel.dynamic.DynamicViewModel
import com.tumoyakov.demonavigation.presentation.viewmodel.dynamic.DynamicViewModelFactory
import kotlinx.android.synthetic.main.fragment_second_graph.*
import org.jetbrains.anko.support.v4.act

class DynamicGraphFragment : Fragment() {
    private lateinit var viewModel: DynamicViewModel
    private lateinit var valuteDynamic: ValDyn
    private var valuteId = ""
    private var valuteName = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders
            .of(act, DynamicViewModelFactory())
            .get(DynamicViewModel::class.java)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        initContent()
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }

    private fun initContent() {
        viewModel.valuteId.observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {
            valuteId = it
        })
        viewModel.valuteName.observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {
            valuteName = it
        })
        viewModel.dynamicList.observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {
            valuteDynamic = it
            val dates =
                Array(valuteDynamic.dynamic.size) {
                    valuteDynamic.dynamic[it].date.convertStrToDate()
                }
            val values =
                Array(valuteDynamic.dynamic.size) {
                    valuteDynamic.dynamic[it].value.replace(',', '.').toDouble()
                }
            val series =
                LineGraphSeries<DataPoint>(Array(valuteDynamic.dynamic.size) { DataPoint(dates[it], values[it]) })
            series.isDrawDataPoints = true
            ghSecond.addSeries(series)
            ghSecond.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(activity)
            ghSecond.gridLabelRenderer.numHorizontalLabels = 4
            // set manual x bounds to have nice steps
            ghSecond.viewport.setMinX(dates.first().time.toDouble())
            ghSecond.viewport.setMaxX(dates.last().time.toDouble())
            ghSecond.viewport.isXAxisBoundsManual = true
            // as we use dates as labels, the human rounding to nice readable numbers
            // is not necessary
            ghSecond.gridLabelRenderer.setHumanRounding(false)
            ghSecond.viewport.isScalable = true // enables horizontal zooming and scrolling
            ghSecond.viewport.setScalableY(true)
        })

        requireActivity().title = "${resources.getString(R.string.fragment_second_graph_title)} $valuteName"
    }
}