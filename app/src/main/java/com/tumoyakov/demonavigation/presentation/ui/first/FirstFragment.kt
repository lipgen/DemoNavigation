package com.tumoyakov.demonavigation.presentation.ui.first

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.tumoyakov.demonavigation.R
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*navigation.selectedItemId = R.id.tiFirst
        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.tiMain-> view.findNavController().navigate(R.id.action_firstFragment_to_mainFragment).let { true }
                R.id.tiSecond -> view.findNavController().navigate(R.id.action_firstFragment_to_secondFragment).let { true }
                else -> true
            }
        }*/
        btnToChild.setOnClickListener {
            view.findNavController().navigate(R.id.action_firstFragment_to_firstChildFragment)
        }
    }


}