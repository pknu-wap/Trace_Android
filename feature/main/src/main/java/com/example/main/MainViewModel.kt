package com.example.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.event.EventHelper
import com.example.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val eventHelper: EventHelper,
    private val userRepository: UserRepository
) : ViewModel() {


    private fun checkSession() = viewModelScope.launch {
        userRepository.checkTokenHealth().onSuccess {

        }.onFailure {

        }
    }
}