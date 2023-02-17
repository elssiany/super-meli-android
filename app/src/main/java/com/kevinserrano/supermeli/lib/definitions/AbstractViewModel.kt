package com.kevinserrano.supermeli.lib.definitions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class AbstractViewModel<ViewState, Event>(
    private val initialState: ViewState
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state
    protected val _event = MutableLiveData<Event>()
    val event: LiveData<Event> get() = _event

    protected fun updateState(call: (ViewState) -> ViewState) {
        _state.postValue(call(currentState()))
    }
    protected fun currentState() = _state.value ?: initialState
}
