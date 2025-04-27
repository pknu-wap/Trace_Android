package com.example.main

import androidx.lifecycle.ViewModel
import com.example.common.event.EventHelper
import com.example.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val eventHelper: EventHelper,
    private val userRepository: UserRepository
) : ViewModel() {

}