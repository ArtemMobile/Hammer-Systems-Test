package com.example.hammersystemstesttask.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel<INTENT : ViewIntent, ACTION : ViewAction, STATE : ViewState> : ViewModel() {

    open fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.Main) { block() }
    }

    open fun dispatchIntent(intent: INTENT) {
        handleAction(intentToAction(intent))
    }

    protected abstract fun intentToAction(intent: INTENT): ACTION
    protected abstract fun handleAction(action: ACTION)
}