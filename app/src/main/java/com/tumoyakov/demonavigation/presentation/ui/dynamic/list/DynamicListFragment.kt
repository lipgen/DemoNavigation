package com.tumoyakov.demonavigation.presentation.ui.dynamic.list

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tumoyakov.demonavigation.R
import com.tumoyakov.demonavigation.domain.entity.ValDyn
import com.tumoyakov.demonavigation.presentation.viewmodel.dynamic.DynamicViewModel
import com.tumoyakov.demonavigation.presentation.viewmodel.dynamic.DynamicViewModelFactory
import com.tumoyakov.demonavigation.setDateByPattern
import kotlinx.android.synthetic.main.fragment_dynamic_list.*
import org.jetbrains.anko.support.v4.act
import java.util.*

class DynamicListFragment : Fragment() {

    private lateinit var viewModel: DynamicViewModel
    private val dynamicAdapter = DynamicAdapter()
    private var valuteId = ""
    private var valuteName = ""
    private lateinit var valuteDynamic: ValDyn
    private val cal = Calendar.getInstance()
    private val year = cal.get(Calendar.YEAR)
    private val month = cal.get(Calendar.MONTH)
    private val day = cal.get(Calendar.DAY_OF_MONTH)
    private val currDate = "$day/$month/$year"

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dynamic_list, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.miGraphic -> {
            view?.findNavController()?.navigate(R.id.action_dynamicListFragment_to_dynamicGraphFragment)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders
            .of(act, DynamicViewModelFactory())
            .get(DynamicViewModel::class.java)
        initContent()
    }

    private fun initContent() {
        val dateFrom = setDateByPattern((day - 8), month, year)
        val dateTo = setDateByPattern((day - 1), month, year)

        valuteId = DynamicListFragmentArgs.fromBundle(arguments).valuteCode
        valuteName = DynamicListFragmentArgs.fromBundle(arguments).valuteName

        viewModel.valuteId.value = valuteId
        viewModel.valuteName.value = valuteName

        requireActivity().title = valuteName

        viewModel.valuteId.observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {
            valuteId = it
        })
        viewModel.valuteName.observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {
            valuteName = it
        })

        viewModel.getDynamics(dateFrom, dateTo, valuteId)

        setDateTextViews(dateFrom, dateTo)

        cvDate1.setOnClickListener {
            showDateFromPicker(true)
        }
        cvDate2.setOnClickListener {
            showDateFromPicker(false)
        }

        rwDynamicList.setHasFixedSize(true)
        rwDynamicList.layoutManager = LinearLayoutManager(activity)
        rwDynamicList.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        viewModel.dynamicList.observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {
            valuteDynamic = it
            dynamicAdapter.dynamicList = it.dynamic
            setDateTextViews(it.dateFrom, it.dateTo)
            rwDynamicList.adapter = dynamicAdapter
        })
    }

    private fun showDateFromPicker(mode: Boolean) {
        // инициализируем диалог выбора даты текущими значениями
        val datePickerDialog = DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { _, yearOfYear, monthOfYear, dayOfMonth ->
                val date = setDateByPattern(dayOfMonth, monthOfYear, yearOfYear)
                if (mode) {
                    val dateToFormatted = valuteDynamic.dateTo.replace('.', '/')
                    if (compareDates(date, dateToFormatted)) {
                        viewModel.getDynamics(date, dateToFormatted, valuteId)
                    } else {
                        Toast.makeText(
                            context,
                            "${resources.getString(R.string.fragment_dynamic_list_date_error)}",
                            Toast.LENGTH_LONG
                        )
                    }
                } else {
                    val dateFromFormatted = valuteDynamic.dateFrom.replace('.', '/')
                    if (compareDates(dateFromFormatted, date)) {
                        viewModel.getDynamics(dateFromFormatted, date, valuteId)
                    } else {
                        Toast.makeText(
                            context,
                            "${resources.getString(R.string.fragment_dynamic_list_date_error)}",
                            Toast.LENGTH_LONG
                        )
                    }
                }
            }, year, month, day
        )
        datePickerDialog.show()
    }

    private fun compareDates(date1: String, date2: String): Boolean { //if date1 < date2 true
        val (day1, month1, year1) = date1.split('/').map(String::toInt)
        val (day2, month2, year2) = date2.split('/').map(String::toInt)
        return if (year1 == year2 && month1 == month2) day1 < day2
        else if (year1 == year2) month1 < month2
        else year1 < year2
    }

    private fun setDateTextViews(date1: String, date2: String) {
        twDate1.text = "${resources.getString(R.string.fragment_dynamic_list_date1_title)} $date1"
        twDate2.text = "${resources.getString(R.string.fragment_dynamic_list_date2_title)} $date2"
    }
}