package com.example.livedatapractice

import android.net.TrafficStats
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Math.PI

class MainViewModel : ViewModel() {
    private val widthText: MutableLiveData<String> = MutableLiveData()
    val areaOfCircle: LiveData<Double> = Transformations.switchMap(widthText) { width ->
        if (width.isBlank()) {
            nullTestFun(null)
            getAreaOfCircle(null)
        } else {
            nullTestFun("널이 아니에요")
            getAreaOfCircle(width.toInt())    
        }
    }

    val testString: LiveData<String> = Transformations.switchMap(widthText) {
        liveDataTest()
    }


    private val _nullTest = MutableLiveData<String>()
    val nullTest: LiveData<String>
        get() = _nullTest

    private fun nullTestFun(test: String?) {
        _nullTest.value = test
    }

    fun updateText(newWidth: String) {
        widthText.value = newWidth
    }

    private fun liveDataTest(): LiveData<String> {
        return object : ObjectClassTest() {
            override fun loadString(): LiveData<String> {
                val liveData = MutableLiveData<String>()
                liveData.run {
                    value = "complete"
                }
                return liveData
            }

            override fun createCall(): LiveData<Int> {
                val liveData =  MutableLiveData<Int>()
                liveData.run {
                    value = 1
                }
                return liveData
            }
        }.asLiveData()
    }

    fun loadData() = viewModelScope.launch {

    }

    private fun getAreaOfCircle(width: Int?): LiveData<Double> {
        val liveData: MutableLiveData<Double> = MutableLiveData()
        if (width != null) {
            liveData.run {
                value = width * width * PI
            }
        }
        return liveData
    }

    private suspend fun executeHeavyTask() = withContext(Dispatchers.Default) {
        delay(5000L)
    }

}