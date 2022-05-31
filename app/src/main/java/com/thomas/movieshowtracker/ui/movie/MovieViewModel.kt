package com.thomas.movieshowtracker.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel : ViewModel(){
    private val _text = MutableLiveData<String>().apply {
        value = "This is Movie fragment"
    }
    val text: LiveData<String> = _text
}