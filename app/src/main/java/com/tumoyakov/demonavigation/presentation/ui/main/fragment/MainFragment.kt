package com.tumoyakov.demonavigation.presentation.ui.main.fragment

import android.content.pm.ActivityInfo
import android.graphics.*
import android.os.AsyncTask
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.tumoyakov.demonavigation.R
import com.tumoyakov.demonavigation.presentation.viewmodel.main.MainFragmentViewModel
import com.tumoyakov.demonavigation.presentation.viewmodel.main.MainFragmentViewModelFactory
import kotlinx.android.synthetic.main.dialog_group_input.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.imageBitmap
import org.jetbrains.anko.support.v4.act


class MainFragment : Fragment() {
    private lateinit var viewModel: MainFragmentViewModel
    private var paint = Paint()
    private var path2 = Path()
    private lateinit var view: PaintView
    private val neurons = Array(10) { Neuron() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        viewModel = ViewModelProviders
            .of(act, MainFragmentViewModelFactory())
            .get(MainFragmentViewModel::class.java)
        initContent()
    }

    private fun initContent() {
        paint.isDither = true
        paint.color = Color.parseColor("#000000")
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.SQUARE
        paint.strokeWidth = 20f
        view = PaintView(requireContext(), 600, 600, path2, paint)
        Log.i("display density", "${requireActivity().applicationContext.resources.displayMetrics.density}")
        flDrawingPlace.addView(view)

        ibClear.setOnClickListener {
            view.clear()
        }

        btnCheck.setOnClickListener {
            val result = findNumber(view.getImageArray())
            twResultImage.text = result.toString()
            setTrainBtnsVisibility(true)
        }

        ibSave.setOnClickListener {
            if (etGroup.text.isNotBlank() && etNum.text.isNotBlank()) {
                val result = view.saveTestFile(etGroup.text.toString(), etNum.text.toString())
                Toast.makeText(context, "$result", Toast.LENGTH_SHORT).show()
            }
        }

        ibUpload.setOnClickListener {
            if (etGroup.text.isNotBlank() && etNum.text.isNotBlank()) {
                ivImage.imageBitmap = view.readFile(etGroup.text.toString(), etNum.text.toString())
                view.invalidate()
            }
        }

        btnTrain.setOnClickListener {
            val asyncTrain = AsyncTrain()
            asyncTrain.execute()
        }

        btnTrainRight.setOnClickListener {
            val result = twResultImage.text.toString().toInt()
            val imageArray = view.getImageArray()
            for(i in 0..9) {
                trainNeuron(i, result, imageArray)
            }
            setTrainBtnsVisibility(false)
        }

        btnTrainWrong.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle(R.string.fragment_main_dialog_title)
            val inflater: LayoutInflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_group_input, null)
            builder.setView(dialogView)
            builder.setPositiveButton(R.string.save_btn_description, null)
            builder.setNegativeButton(R.string.cancel_btn_description) { dialog, _ ->
                setTrainBtnsVisibility(false)
                dialog.cancel()
            }
            val trainDialog = builder.create()
            trainDialog.show()
            trainDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val etGroup = trainDialog.etRightGroup
                if(etGroup.text.isNotEmpty()) {
                    val rightGroup = etGroup.text.toString().toInt()
                    val imageArray = view.getImageArray()
                    for(i in 0..9) {
                        trainNeuron(i, rightGroup, imageArray)
                    }
                    setTrainBtnsVisibility(false)
                    trainDialog.cancel()
                } else {
                    sendToast("Введите число")
                }
            }
        }

    }

    private fun trainNeuron(neuron: Int, group: Int, number: Array<Int>) {
        if (neuron != group) {
            if (neurons[neuron].proceed(number)) {
                neurons[neuron].decrease(number)
            }
        } else {
            if (!(neurons[neuron].proceed(number))) {
                neurons[neuron].increase(number)
            }
        }
    }

    private fun sendToast(str: String) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }

    private fun setButtonsEnabled(mode: Boolean) {
        btnTrain.isEnabled = mode
        btnCheck.isEnabled = mode
    }

    private fun setTrainBtnsVisibility(isVisible: Boolean) {
        if(isVisible) {
            btnTrainRight.visibility = View.VISIBLE
            btnTrainWrong.visibility = View.VISIBLE
        } else {
            btnTrainRight.visibility = View.GONE
            btnTrainWrong.visibility = View.GONE
        }
    }

    private fun findNumber(number: Array<Int>): Int {
        for (i in 0..9) {
            if (neurons[i].proceed(number)) return i
        }
        return -1
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }

    inner class AsyncTrain: AsyncTask<Unit, Int, Unit>() {

        override fun onPreExecute() {
            super.onPreExecute()
            btnTrain.background.setColorFilter(Color.parseColor("#E95951"), PorterDuff.Mode.DARKEN)
            sendToast("Старт обучения")
            setButtonsEnabled(false)
            tvTrainProcess.text = "Обучение - 0%"
            tvTrainProcess.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: Unit?) {
            for (group in 0..9) {
                for (num in 0..10) {
                    val number = view.getTrainArray(group, num)
                    for(i in 0..9) {
                        trainNeuron(i, group, number)
                    }
                }
                publishProgress(group+1)
            }
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            tvTrainProcess.text = "Обучение - ${values[0]?.times(10)}%"
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            btnTrain.background.setColorFilter(Color.parseColor("#15C020"), PorterDuff.Mode.DARKEN)
            sendToast("Завершено")
            tvTrainProcess.visibility = View.GONE
            setButtonsEnabled(true)
        }

    }
}