package com.thomas.movieshowtracker

import android.content.SharedPreferences
import com.google.gson.Gson
import kotlin.collections.contains as contains


public data class SaveList(val mprefs: SharedPreferences){
    companion object {
        fun saveData(mPrefs: SharedPreferences, obj: ListBean, type: String, content :String) {
            val prefsEditor: SharedPreferences.Editor = mPrefs.edit()
            val gson = Gson()
            if (content == "shows") {
                if (loadData(mPrefs, type).shows?.isNotEmpty() == true) {
                    var same = false
                    val test = loadData(mPrefs, type)
                    for (item in test.shows!!) {
                        println("id = " + item.id + "show = " + obj.shows?.get(0)?.id)
                        if (item.id === obj.shows?.get(0)?.id){
                            same = true
                        }
                    }
                    if (!same) {
                        test.shows?.add(obj.shows?.get(0)!!)
                        val json = gson.toJson(test)
                        println("Json file: $json")
                        prefsEditor.putString(type, json)
                        prefsEditor.apply()
                    } else {
                        println("It is already here")
                    }
                } else {
                    val test = loadData(mPrefs, type)
                    test.shows?.add(obj.shows!!.last())
                    val json = gson.toJson(test)
                    prefsEditor.putString(type, json)
                    prefsEditor.apply()
                    println("json add: $json")
                }
            } else if (content == "movies") {
                if (loadData(mPrefs, type).movies?.isNotEmpty() == true) {
                    var same = false
                    val test = loadData(mPrefs, type)
                    for (item in test.movies!!) {
                        if (item.id === obj.movies?.last()?.id){
                            same = true
                        }
                    }
                    println("same:$same")
                    if (!same) {
                        test.movies?.add(obj.movies?.last()!!)
                        val json = gson.toJson(test)
                        println("Json file: $json")
                        prefsEditor.putString(type, json)
                        prefsEditor.apply()
                    } else {
                        println("It is already here")
                    }
                } else {
                    val test = loadData(mPrefs, type)
                    test.movies?.add(obj.movies!!.last())
                    val json = gson.toJson(test)
                    prefsEditor.putString(type, json)
                    prefsEditor.apply()
                    println("json add: $json")
                }
            }
        }
        fun loadData(mPrefs: SharedPreferences, type: String): ListBean {
            val gson = Gson()
            val json: String? = mPrefs.getString(type, "")
            println("resultat : $json")
            if(json != "true") {
                var result = gson.fromJson(json, ListBean::class.java)
                println(result)
                if (result == null) {
                    println("result est null")
                    return ListBean(ArrayList(), ArrayList())
                } else if (json != "true") {
                    return gson.fromJson(json, ListBean::class.java)
                }
            }
            return ListBean(ArrayList(), ArrayList())
        }
        fun removeData(mPrefs: SharedPreferences, id: Int, type: String, content :String) {
            val editor: SharedPreferences.Editor = mPrefs.edit()
            val gson = Gson()
            if (content == "shows") {
                if (loadData(mPrefs, type).shows?.isNotEmpty() == true) {
                    val test = loadData(mPrefs, type)
                    println("test:$test")
                    test.shows = ArrayList(test.shows!!.dropWhile { it.id == id })
                    println(test)
                    val json = gson.toJson(test)
                    println("Json file : $json")
                    editor.putString(type, json)
                    editor.apply()
                }
            } else if (content == "movies") {
                if (loadData(mPrefs, type).movies?.isNotEmpty() == true) {
                    val test = loadData(mPrefs, type)
                    //test.movies = ArrayList(test.movies!!.dropWhile { it.id == id })
                    for ((index, item) in test.movies!!.withIndex()) {
                        if (item.id == id){
                            test.movies = ArrayList(test.movies!!.drop(index))
                            //test.movies!!.removeAt(index)
                            println("Found")
                        }
                    }
                    println(test)
                    val json = gson.toJson(test)
                    println("Json file : $json")
                    editor.putString(type, json)
                    editor.apply()
                }
            }
        }
    }
}