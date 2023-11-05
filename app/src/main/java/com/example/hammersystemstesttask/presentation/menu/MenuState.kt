package com.example.hammersystemstesttask.presentation.menu

import com.example.hammersystemstesttask.presentation.common.ViewState


sealed class MenuState : ViewState{
    data object Loading : MenuState()
    data class Success(val data: Any) : MenuState()
    data class Exception(val error: String) : MenuState()
}