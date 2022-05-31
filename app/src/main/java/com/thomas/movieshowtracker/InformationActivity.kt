package com.thomas.movieshowtracker

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.thomas.movieshowtracker.databinding.ActivityInformationBinding

class InformationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityInformationBinding
    private val model by lazy { ViewModelProvider(this)[SearchAPIViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getStringExtra("type").toString() == "shows") {
            model.loadInformationShows(intent.getStringExtra("id").toString())
            model.loadCastingShows(intent.getStringExtra("id").toString())
        }
        else if (intent.getStringExtra("type").toString() == "movies") {
            model.loadInformationMovies(intent.getStringExtra("id").toString())
            //model.loadCastingShows()
        }

        binding.tvTest.text = intent.getStringExtra("id").toString()
        println(intent.getStringExtra("type"))

        model.informationShows.observe(this) { it ->
            if (it != null) {
                Picasso.get().load(it.show.images.banner).fit().centerCrop().into(binding.imBanner)
                binding.tvTitle.text = it.show.title
                binding.tvCreation.text = it.show.creation
                val tab = arrayOf<ImageView>(binding.imageView, binding.imageView2)
                if (it.show.platforms != null && it.show.platforms.svods != null) {
                    var timesVod :Int = if(it.show.platforms.svods.size > 2) 2 else it.show.platforms.svods.size
                    repeat(timesVod) { i ->
                        Picasso.get().load(it.show.platforms.svods[i].logo).into(tab[i])
                    }
                }
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
        model.castingShows.observe(this) {
            if (it != null) {
                val tabCasting = arrayOf<ImageView>(binding.imCasting1, binding.imCasting2, binding.imCasting3, binding.imCasting4, binding.imCasting5, binding.imCasting6)
                var timesCasting :Int = if(it.characters.size > 6) 6 else it.characters.size
                repeat(timesCasting) { i ->
                    Picasso.get().load(it.characters[i].picture).into(tabCasting[i])
                }
            }
        }
        model.informationMovies.observe(this) { it ->
            if (it != null) {
                Picasso.get().load(it.movie.backdrop).fit().centerCrop().into(binding.imBanner)
                binding.tvTitle.text = it.movie.title
                binding.tvCreation.text = it.movie.release_date
                val tab = arrayOf<ImageView>(binding.imageView, binding.imageView2)
                /*if (it.movie.platform_links != null) {
                    var timesVod :Int = if(it.movie.platforms.svods.size > 2) 2 else it.show.platforms.svods.size
                    repeat(timesVod) { i ->
                        Picasso.get().load(it.show.platforms.svods[i].logo).into(tab[i])
                    }
                }*/
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
}

fun main () {
    println(RequestUtils.loadInformationShows("12388"))
    println(RequestUtils.loadInformationShows("185")?.show?.platforms?.svods?.get(0)?.name)
    println(RequestUtils.loadCastingShows("185"))
}