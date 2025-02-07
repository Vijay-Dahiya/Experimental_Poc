package com.example.experiment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class ObservableDemo : ViewModel() {

    private val _livedata = MutableLiveData<String>()
    val liveData : LiveData<String> = _livedata

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    private val _stateFlow = MutableStateFlow<String>("Hello World")
    val stateFlow = _stateFlow.asStateFlow()


    fun triggerLiveData() {
        _livedata.value = "LIVE DATA"
    }

    fun triggerStateFlow() {
        _stateFlow.value = "STATE FLOW"
    }

    fun triggerFlow(): Flow<String> {
        return flow<String> {
            emit("FLOW EMIT")
        }
    }

    fun triggerSharedFlow() {
        _sharedFlow
    }


}