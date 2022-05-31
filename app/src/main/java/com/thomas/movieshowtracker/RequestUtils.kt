package com.thomas.movieshowtracker

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

val client = OkHttpClient()

const val URL_BETASERIES = "https://api.betaseries.com"

const val URL_API_SHOWS = "$URL_BETASERIES/search/shows?key=f6cbebcbca3e&text=%s"

const val URL_API_MOVIES = "$URL_BETASERIES/search/movies?key=f6cbebcbca3e&text=%s"

const val URL_API_SHOWS_INFORMATION = "$URL_BETASERIES/shows/display?key=f6cbebcbca3e&id=%s"

const val URL_API_MOVIES_INFORMATION = "$URL_BETASERIES/movies/movie?key=f6cbebcbca3e&id=%s"

const val URL_API_CASTING = "$URL_BETASERIES/shows/characters?key=f6cbebcbca3e&id=%s"
public class RequestUtils{
    companion object{
        fun loadSearchShows(search :String): ArrayList<PosterBean>? {
            var json: String = sendGet(URL_API_SHOWS.format(search))
            return Gson().fromJson(json, SearchShowsBean::class.java).shows
        }


        data class MYBean(var url: String, var title:String)
        fun loadSearchShowsV2(search :String): List<MYBean> {
            var json: String = sendGet(URL_API_SHOWS.format(search))
            return Gson().fromJson(json, SearchShowsBean::class.java).shows.map {
                MYBean(it.poster, it.title)
            }
        }


        fun loadInformationShows(search :String): ShowsInformationBean? {
            var json: String = sendGet(URL_API_SHOWS_INFORMATION.format(search))
            return Gson().fromJson(json, ShowsInformationBean::class.java)
        }
        fun loadCastingShows(search :String): CastingShowsBean? {
            var json: String = sendGet(URL_API_CASTING.format(search))
            return Gson().fromJson(json, CastingShowsBean::class.java)
        }

        fun loadSearchMovies(search :String): ArrayList<PosterBean>? {
            var json: String = sendGet(URL_API_MOVIES.format(search))
            return Gson().fromJson(json, SearchMoviesBean::class.java).movies
        }
        fun loadInformationMovies(search :String): MoviesInformationBean? {
            var json: String = sendGet(URL_API_MOVIES_INFORMATION.format(search))
            return Gson().fromJson(json, MoviesInformationBean::class.java)
        }

        private fun sendGet(url: String): String {
            println("url : $url")
            val request = Request.Builder().url(url).build()
            return client.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    throw Exception("Réponse du serveur incorrect :${it.code}")
                }
                it.body.string() ?: ""
            }
        }
    }
}