package com.thomas.movieshowtracker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.Exception
import kotlin.concurrent.thread

class SearchAPIViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String?>()
    val runInProgress = MutableLiveData(false)

    val dataShows = MutableLiveData(ArrayList<PosterBean>())
    val informationShows = MutableLiveData<ShowsInformationBean?>()
    /*val castingShows = MutableLiveData<CastingShowsBean?>()*/

    val vodShows = MutableLiveData(ArrayList<SvodsBean>())
    val castingShows = MutableLiveData(ArrayList<CharactersBean>())

    val dataMovies = MutableLiveData<ArrayList<PosterBean>>()
    val informationMovies = MutableLiveData<MoviesInformationBean?>()
    //val castingMovies = MutableLiveData<CastingShowsBean?>()

    fun loadShowsData(search :String) {
        println(search)
        runInProgress.postValue(true)
        thread {
            try {
                dataShows.postValue(RequestUtils.loadSearchShows(search.replace(" ", "%20")))
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
            runInProgress.postValue(false)
        }
    }
    fun loadInformationShows(search :String) {
        thread {
            try {
                informationShows.postValue(RequestUtils.loadInformationShows(search))
                vodShows.postValue(RequestUtils.loadInformationShows(search)?.show?.platforms?.svods)
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
        }
    }
    fun loadCastingShows(search :String) {
        thread {
            try {
                castingShows.postValue(RequestUtils.loadCastingShows(search))
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
        }
    }

    fun loadMoviesData(search :String) {
        println(search)
        runInProgress.postValue(true)
        thread {
            try {
                dataMovies.postValue(RequestUtils.loadSearchMovies(search.replace(" ", "%20")))
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
            runInProgress.postValue(false)
        }
    }
    fun loadInformationMovies(search :String) {
        thread {
            try {
                informationMovies.postValue(RequestUtils.loadInformationMovies(search))
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
        }
    }
}