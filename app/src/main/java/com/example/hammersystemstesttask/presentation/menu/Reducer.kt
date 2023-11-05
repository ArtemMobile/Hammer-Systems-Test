package com.example.hammersystemstesttask.presentation.menu

import com.example.hammersystemstesttask.domain.Result

fun Result<Any>.reduce(): MenuState {
    return when (this) {
        is Result.Error -> MenuState.Exception(error)
        is Result.Loading -> MenuState.Loading
        is Result.Success -> MenuState.Success(data)
    }
}