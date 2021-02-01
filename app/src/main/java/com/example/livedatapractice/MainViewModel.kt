package com.example.livedatapractice

import android.net.TrafficStats
import androidx.lifecycle.*
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

    private val _nullTest = MutableLiveData<String>()
    val nullTest: LiveData<String>
        get() = _nullTest

    private fun nullTestFun(test: String?) {
        _nullTest.value = test
    }

    fun updateText(newWidth: String) {
        widthText.value = newWidth
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

}