package com.tumoyakov.demonavigation.presentation.viewmodel.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tumoyakov.demonavigation.App

class MainFragmentViewModelFactory(
    private val context: Application = App.instance
) : ViewModelProvider.AndroidViewModelFactory(context) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = MainFragmentViewModel(context = context) as T
}