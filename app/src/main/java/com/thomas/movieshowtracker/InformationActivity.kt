package com.thomas.movieshowtracker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import com.thomas.movieshowtracker.databinding.ActivityInformationBinding

class InformationActivity : AppCompatActivity(), View.OnClickListener, CastingListAdapter.ClickCasting{

    private lateinit var binding: ActivityInformationBinding
    private val model by lazy { ViewModelProvider(this)[SearchAPIViewModel::class.java] }
    val adapterVod = VodListAdapter()
    val adapterCasting = CastingListAdapter(this)
    var posterShow :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvCasting.isNestedScrollingEnabled = false

        binding.rvVOD.adapter = adapterVod
        binding.rvVOD.layoutManager = GridLayoutManager(this, 5)

        binding.rvCasting.adapter = adapterCasting
        binding.rvCasting.layoutManager = GridLayoutManager(this, 3)

        if (intent.getStringExtra("type").toString() == "shows") {
            model.loadInformationShows(intent.getStringExtra("id").toString())
            model.loadCastingShows(intent.getStringExtra("id").toString())
        }
        else if (intent.getStringExtra("type").toString() == "movies") {
            model.loadInformationMovies(intent.getStringExtra("id").toString())
            //model.loadCastingShows()
        }

        model.vodShows.observe(this) {
            if (it != null) {
                adapterVod.submitList(model.vodShows.value?.toList())
            }
        }
        model.castingShows.observe(this) {
            if (it != null) {
                adapterCasting.submitList(model.castingShows.value?.toList())
            }
        }

        binding.tvTest.text = intent.getStringExtra("id").toString()
        println(intent.getStringExtra("type"))

        model.informationShows.observe(this) { it ->
            if (it != null) {
                posterShow = it.show.images.poster
                Picasso.get().load(it.show.images.banner).fit().centerCrop().into(binding.imBanner)
                binding.tvTitle.text = it.show.title
                binding.tvCreation.text = it.show.creation
                //val tab = arrayOf<ImageView>(binding.imageView, binding.imageView2)
                binding.tvDescrition.text = it.show.description
                if (it.show.notes.mean < 5 && it.show.notes.mean > 4.5) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_half_24)
                }
                else if (it.show.notes.mean < 4.5 && it.show.notes.mean > 4) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
                else if (it.show.notes.mean < 4 && it.show.notes.mean > 3.5) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar4.setImageResource(R.drawable.ic_baseline_star_half_24)
                }
                else if (it.show.notes.mean < 3.5 && it.show.notes.mean > 3) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
                else if (it.show.notes.mean < 3 && it.show.notes.mean > 2.5) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_half_24)
                }
                else if (it.show.notes.mean < 2.5 && it.show.notes.mean > 2) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
                else if (it.show.notes.mean < 2 && it.show.notes.mean > 1.5) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_half_24)
                }
                else if (it.show.notes.mean < 1.5 && it.show.notes.mean > 1) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
                else if (it.show.notes.mean < 1 && it.show.notes.mean > 0.5) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_half_24)
                }
                else if (it.show.notes.mean < 0.5 && it.show.notes.mean > 0) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
            }
        }
        model.informationMovies.observe(this) { it ->
            if (it != null) {
                posterShow = it.movie.poster
                Picasso.get().load(it.movie.backdrop).fit().centerCrop().into(binding.imBanner)
                binding.tvTitle.text = it.movie.title
                binding.tvCreation.text = it.movie.release_date
                //val tab = arrayOf<ImageView>(binding.imageView, binding.imageView2)
                binding.tvDescrition.text = it.movie.synopsis
                if (it.movie.notes.mean < 5 && it.movie.notes.mean > 4.5) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_half_24)
                }
                else if (it.movie.notes.mean < 4.5 && it.movie.notes.mean > 4) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
                else if (it.movie.notes.mean < 4 && it.movie.notes.mean > 3.5) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar4.setImageResource(R.drawable.ic_baseline_star_half_24)
                }
                else if (it.movie.notes.mean < 3.5 && it.movie.notes.mean > 3) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
                else if (it.movie.notes.mean < 3 && it.movie.notes.mean > 2.5) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_half_24)
                }
                else if (it.movie.notes.mean < 2.5 && it.movie.notes.mean > 2) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
                else if (it.movie.notes.mean < 2 && it.movie.notes.mean > 1.5) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_half_24)
                }
                else if (it.movie.notes.mean < 1.5 && it.movie.notes.mean > 1) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
                else if (it.movie.notes.mean < 1 && it.movie.notes.mean > 0.5) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_half_24)
                }
                else if (it.movie.notes.mean < 0.5 && it.movie.notes.mean > 0) {
                    binding.imStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.imStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
            }
        }
        binding.back.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when {
            p0 === binding.back -> {
                finish()
            }
        }
    }

    override fun onClickCasting(data : String) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
    }

    fun btAddWatchList(view: View) {
        val mPrefs : SharedPreferences = getSharedPreferences("Watch", MODE_PRIVATE)
        val id : Int? = intent.getStringExtra("id")?.toInt()
        println("id:$id")
        val Poster = PosterBean(posterShow, "", id)
        if (intent.getStringExtra("type").toString() == "shows") {
            ListWatch.shows?.add(Poster)
        } else if (intent.getStringExtra("type").toString() == "movies") {
            ListWatch.movies?.add(Poster)
        }
        println(ListWatch.movies!!)
        SaveList.saveData(mPrefs, ListWatch, "Watch", intent.getStringExtra("type").toString())
    }
    fun btAddWishList(view: View) {
        val mPrefs : SharedPreferences = getSharedPreferences("Wish", MODE_PRIVATE)
        val id : Int? = intent.getStringExtra("id")?.toInt()
        val Poster = PosterBean(posterShow, "", id)
        if (intent.getStringExtra("type").toString() == "shows") {
            ListWish.shows?.add(Poster)
        } else if (intent.getStringExtra("type").toString() == "movies") {
            ListWish.movies?.add(Poster)
        }
        SaveList.saveData(mPrefs, ListWish, "Wish", intent.getStringExtra("type").toString())
    }

    fun btRemoveWatch(view: View) {
        val mPrefs : SharedPreferences = getSharedPreferences("Watch", MODE_PRIVATE)
        val id : Int? = intent.getStringExtra("id")?.toInt()
        SaveList.removeData(mPrefs, id!!, "Watch", intent.getStringExtra("type").toString())
    }
    fun btRemoveWish(view: View) {
        val mPrefs : SharedPreferences = getSharedPreferences("Wish", MODE_PRIVATE)
        val id : Int? = intent.getStringExtra("id")?.toInt()
        SaveList.removeData(mPrefs, id!!, "Wish", intent.getStringExtra("type").toString())
    }
}

fun main () {
    println(RequestUtils.loadInformationShows("12388"))
    println(RequestUtils.loadInformationShows("185")?.show?.platforms?.svods?.get(0)?.name)
    println(RequestUtils.loadCastingShows("185"))
}