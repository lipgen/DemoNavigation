package com.tumoyakov.demonavigation.presentation.ui.second

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tumoyakov.demonavigation.R
import com.tumoyakov.demonavigation.presentation.viewmodel.second.SecondFragmentViewModel
import com.tumoyakov.demonavigation.presentation.viewmodel.second.SecondFragmentViewModelFactory
import kotlinx.android.synthetic.main.fragment_second.*
import org.jetbrains.anko.support.v4.act
import android.app.DatePickerDialog
import androidx.navigation.findNavController
import com.tumoyakov.demonavigation.domain.entity.ValCurs
import com.tumoyakov.demonavigation.setDateByPattern
import java.util.*

class SecondFragment : Fragment() {

    private lateinit var viewModel: SecondFragmentViewModel
    private val valuteAdapter = ValuteAdapter()
    private var date: String = ""
    private lateinit var valCurs: ValCurs

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.toolbar_menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.miSave -> {
            if (valuteAdapter.valutesList.isNotEmpty()) viewModel.valCursToFile()
            true
        }
        R.id.miGraphic -> {
            view?.findNavController()?.navigate(R.id.action_secondFragment_to_secondGraphFragment)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders
            .of(act, SecondFragmentViewModelFactory())
            .get(SecondFragmentViewModel::class.java)
        requireActivity().title = resources.getString(R.string.fragment_second_title)
        initContent()
    }

    private fun initContent() {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        date = setDateByPattern((day - 1), month, year)

        twSecondDate.text = "${resources.getString(R.string.fragment_second_date_title)} $date"
        twSecondDate.setOnClickListener {
            showDatePicker()
        }

        viewModel.getValutes(date)

        setHintVisibility(valuteAdapter.valutesList.isEmpty())

        rvValute.setHasFixedSize(true)
        rvValute.layoutManager = LinearLayoutManager(activity)
        rvValute.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        viewModel.valCurs.observe(this.viewLifecycleOwner, Observer {
            valuteAdapter.valutesList = it.valute
            valCurs = it
            rvValute.adapter = valuteAdapter
            setHintVisibility(valuteAdapter.valutesList.isEmpty())
        })
    }

    private fun setHintVisibility(mode: Boolean) {
        if (mode) {
            twHint.visibility = View.VISIBLE
            iwHint.visibility = View.VISIBLE
        } else {
            twHint.visibility = View.GONE
            iwHint.visibility = View.GONE
        }
    }

    private fun showDatePicker() {
        val cal = Calendar.getInstance()
        var year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH)
        var day = cal.get(Calendar.DAY_OF_MONTH)

        // инициализируем диалог выбора даты текущими значениями
        val datePickerDialog = DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { _, yearOfYear, monthOfYear, dayOfMonth ->
                date = setDateByPattern(dayOfMonth, monthOfYear, yearOfYear)
                twSecondDate.text = "${resources.getString(R.string.fragment_second_date_title)} $date"
                viewModel.getValutes(date)
            }, year, month, day
        )
        datePickerDialog.show()
    }
}