package com.example.serviceeveryminute

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random


class MainViewModel : ViewModel() {

//    private val _dataState = MutableStateFlow<DataState>(DataState(Success = ""))
//    val dataState: StateFlow<DataState> = _dataState
//
//
//    fun updateDataState(dataState: DataState) {
//        _dataState.value = dataState
//        Log.i("LOG--------------->", dataState.Success)
//    }


//
//    private val _value = MutableStateFlow(0)
//    val value: StateFlow<Int> = _value.asStateFlow()
//
//    fun updateDataState(newValue: Int) {
//        _value.value = newValue
//    }






    /************************* change color **************************/
    private val _color = MutableStateFlow(0xFFFFFFFF)
    val color = _color.asStateFlow()

    fun changeBackgroundColor() {
        val color = Random.nextLong(0xFFFFFFFF)
        _color.value = color
    }


}


data class DataState(

    val Loading: Boolean = false,
    val Success: String = "",
    val Error: String = ""

)
