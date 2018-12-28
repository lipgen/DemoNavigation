package com.tumoyakov.demonavigation.presentation.ui.main.fragment

class Neuron(
    private var weights: Array<Int> = Array(10000) { 0 },
    private val bias: Int = 10
) {
    fun proceed(number: Array<Int>): Boolean {
        var sum = 0
        for( i in 0 until weights.size) {
            sum += number[i]*weights[i]
        }
        return (sum >= bias)
    }

    fun decrease(number: Array<Int>) {
        for (i in 0 until weights.size) {
            if (number[i] == 1) weights[i]--
        }
    }

    fun increase(number: Array<Int>) {
        for (i in 0 until weights.size) {
            if (number[i] == 1) weights[i]++
        }
    }
}