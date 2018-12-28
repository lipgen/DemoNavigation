package com.tumoyakov.demonavigation.presentation.ui.main.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.tumoyakov.demonavigation.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(navigation, navController)
        navController.addOnNavigatedListener { _, destination ->
            run {
                when (destination.id) {
                    R.id.firstChildFragment -> setSecondaryScreen(true)
                    R.id.secondGraphFragment -> setSecondaryScreen(true)
                    R.id.dynamicListFragment -> setSecondaryScreen(true)
                    R.id.dynamicGraphFragment -> setSecondaryScreen(true)
                    else -> setSecondaryScreen(false)
                }
            }
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    private fun setSecondaryScreen(mode: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(mode)
        navigation.visibility = if (mode) View.GONE else View.VISIBLE
    }
}
