package com.example.livedatapractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)

        ok_button.setOnClickListener {
            viewModel.updateText(edit_text.text.toString())
        }

        viewModel.areaOfCircle.observe(this, Observer<Double> { area ->
            circle_area_text.text = "원 넓이 : $area"
        })

        viewModel.nullTest.observe(this, Observer { value ->
            if (value == null) {
                test_area_text.text = "Null 값입니다."
            } else {
                test_area_text.text = value
            }
        })

        viewModel.testString.observe(this, Observer{ result ->
            if (result == null) {
                liveData_test.text = "Null 값입니다."
            } else {
                liveData_test.text = result
            }
        })
    }
}