package com.example.livedatapractice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

abstract class ObjectClassTest {

    private val result = MediatorLiveData<String>()
    private var count = 0

    init {
        result.value = "init!"
        val source = loadString()

        result.addSource(source) {
            count++
            result.removeSource(source)
            if(it == "complete") {
                fetchString(source)
            } else {
                result.addSource(source) { newData ->
                    count++
                    setValue(newData + count.toString())
                }
            }
        }
    }

    private fun fetchString(source: LiveData<String>) {
        val intResponse = createCall()

        result.addSource(source) { newData ->
            count++
            setValue(newData + count.toString())
        }
        result.addSource(intResponse) { response ->
            result.removeSource(intResponse)
            result.removeSource(source)

            when (response) {
                1 -> {
                    result.addSource(loadString()) { newData ->
                        count++
                        setValue(newData + count.toString())
                    }
                }
                else -> {
                    setValue("없어요")
                }
            }
        }
    }

    private fun setValue(newValue: String) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    fun asLiveData() = result as LiveData<String>

    protected abstract fun loadString(): LiveData<String>
    protected abstract fun createCall(): LiveData<Int>

}