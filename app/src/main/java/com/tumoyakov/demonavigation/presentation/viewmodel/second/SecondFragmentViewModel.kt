package com.tumoyakov.demonavigation.presentation.viewmodel.second

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tumoyakov.demonavigation.data.repository.CbrRepository
import com.tumoyakov.demonavigation.domain.entity.ValCurs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SecondFragmentViewModel(
    context: Application,
    private val repository: CbrRepository = CbrRepository()
) : AndroidViewModel(context) {

    val valCurs = MutableLiveData<ValCurs>()

    private var disposable = Disposables.disposed()

    fun getValutes(date: String) {
        if (!disposable.isDisposed) disposable.dispose()
        disposable = repository.getValute(date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    valCurs.value = it
                },
                onError = {
                    Log.i(TAG, it.message)
                }
            )
    }

    fun valCursToFile() {
        repository.writeValCursToFile(valCurs.value!!)
    }

    override fun onCleared() {
        if (!disposable.isDisposed) disposable.dispose()
        super.onCleared()
    }

    companion object {
        const val TAG = "SecondFragmentViewModel"
    }
}