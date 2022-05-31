package com.thomas.movieshowtracker.ui.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShowViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is shows Fragment"
    }
    val text: LiveData<String> = _text
}