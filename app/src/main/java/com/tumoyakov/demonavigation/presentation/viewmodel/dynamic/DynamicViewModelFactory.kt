package com.tumoyakov.demonavigation.presentation.viewmodel.dynamic

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tumoyakov.demonavigation.App

class DynamicViewModelFactory(
    private val context: Application = App.instance
) : ViewModelProvider.AndroidViewModelFactory(context) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = DynamicViewModel(context = context) as T
}