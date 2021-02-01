package com.example.livedatapractice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

abstract class ObjectClassTest {

    private val result = MediatorLiveData<String>()

    init {
        result.value = "init!"
        val source = loadString()

        result.addSource(source) {
            result.removeSource(source)
            if(it == "complete") {

            } else {
                result.addSource(source) { newData ->
                    setValue(newData)
                }
            }
        }
    }

    private fun setValue(newValue: String) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    protected abstract fun loadString(): LiveData<String>


}