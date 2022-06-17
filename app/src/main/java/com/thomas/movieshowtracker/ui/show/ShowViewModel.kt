package com.thomas.movieshowtracker.ui.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomas.movieshowtracker.PosterBean

class ShowViewModel : ViewModel() {
    val dataShows = MutableLiveData(ArrayList<PosterBean>())
    val dataMovies = MutableLiveData(ArrayList<PosterBean>())

    fun SyncDataShows(data: ArrayList<PosterBean>) {
        dataShows.postValue(data)
    }
    fun SyncDataMovies(data: ArrayList<PosterBean>) {
        dataMovies.postValue(data)
    }
}