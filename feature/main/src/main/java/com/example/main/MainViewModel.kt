package com.example.main

import androidx.lifecycle.ViewModel
import com.example.common.event.EventHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val eventHelper: EventHelper
) : ViewModel() {

}