package com.example.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.event.EventHelper
import com.example.domain.repository.UserRepository
import com.example.navigation.AuthGraph
import com.example.navigation.NavigationEvent
import com.example.navigation.NavigationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val eventHelper: EventHelper,
    val navigationHelper: NavigationHelper,
    private val userRepository: UserRepository
) : ViewModel() {
    fun checkSession() = viewModelScope.launch {
        userRepository.checkTokenHealth().onSuccess {
            navigationHelper.navigate(NavigationEvent.To(AuthGraph.LoginRoute, popUpTo = true))
        }.onFailure {
            navigationHelper.navigate(NavigationEvent.To(AuthGraph.LoginRoute, popUpTo = true))
        }
    }
}