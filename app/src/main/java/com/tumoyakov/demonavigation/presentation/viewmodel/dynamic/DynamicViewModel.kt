package com.tumoyakov.demonavigation.presentation.viewmodel.dynamic

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tumoyakov.demonavigation.data.repository.CbrRepository
import com.tumoyakov.demonavigation.domain.entity.ValDyn
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class DynamicViewModel(
    context: Application,
    private val repository: CbrRepository = CbrRepository()
) : AndroidViewModel(context) {

    val dynamicList = MutableLiveData<ValDyn>()
    val valuteId = MutableLiveData<String>()
    val valuteName = MutableLiveData<String>()

    private var disposable = Disposables.disposed()

    fun getDynamics(dateFrom: String, dateTo: String, valuteCode: String) {
        if (!disposable.isDisposed) disposable.dispose()
        disposable = repository.getDynamic(dateFrom, dateTo, valuteCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    dynamicList.value = it
                },
                onError = {
                    Log.i(TAG, it.message)
                }
            )
    }

    override fun onCleared() {
        if (!disposable.isDisposed) disposable.dispose()
        super.onCleared()
    }
    companion object {
        const val TAG = "DynamicViewModel"
    }
}
