package com.thomas.movieshowtracker.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.load.model.Model
import com.squareup.picasso.Picasso
import com.thomas.movieshowtracker.*
import com.thomas.movieshowtracker.databinding.FragmentSearchBinding
import android.content.Intent as Intent


class SearchFragment : Fragment(), View.OnClickListener, SearchListAdapter.CellClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private val model by lazy { ViewModelProvider(this)[SearchAPIViewModel::class.java] }
    val adapter = SearchListAdapter(this)
    private var type :Boolean? = null

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[SearchViewModel::class.java]

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        super.onCreate(savedInstanceState)
        val root: View = binding.root

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(activity, 3)

        model.dataShows.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(model.dataShows.value?.toList())
            }
        }
        model.dataMovies.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(model.dataMovies.value?.toList())
            }
        }

        /*model.errorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tvError.visibility = View.VISIBLE
                binding.tvError.text = it.toString()
            }
            else {
                binding.tvError.visibility = View.GONE
            }
        }*/

        /*model.runInProgress.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
            if (it) {
                binding.btLoad.text = "Loading..."
            } else binding.btLoad.text = "Charger"
        }*/
        binding.btLoadShows.setOnClickListener(this)
        binding.btLoadMovies.setOnClickListener(this)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when {
            p0 === binding.btLoadShows -> {
                model.loadShowsData(binding.etSearch.text.toString())
                type = true
            }
            p0 === binding.btLoadMovies -> {
                model.loadMoviesData(binding.etSearch.text.toString())
                type = false
            }
        }
    }

    override fun onCellClickListener(data : Int) {
        val intent = Intent(activity, InformationActivity::class.java)
        intent.putExtra("id", data.toString())
        intent.putExtra("type", if(type == true) "shows" else if(type == false) "movies" else "null")
        activity?.startActivity(intent)
    }
}

fun main() {
    println(RequestUtils.loadSearchShows("breaking bad".replace(" ", "%20")))
}