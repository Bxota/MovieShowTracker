package com.thomas.movieshowtracker.ui.movie

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.thomas.movieshowtracker.InformationActivity
import com.thomas.movieshowtracker.SaveList
import com.thomas.movieshowtracker.databinding.FragmentMovieBinding
import com.thomas.movieshowtracker.ui.show.ShowViewModel

class MovieFragment : Fragment(), View.OnClickListener, MovieListAdapter.CellClickListener {
    private var _binding: FragmentMovieBinding? = null
    private val binding by lazy { FragmentMovieBinding.inflate(layoutInflater) }
    private val model by lazy { ViewModelProvider(this)[ShowViewModel::class.java] }
    val adapter = MovieListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val movieViewModel =
            ViewModelProvider(this)[MovieViewModel::class.java]

        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        super.onCreate(savedInstanceState)
        val root: View = binding.root

        binding.rv.adapter = adapter
        binding.rv.layoutManager = GridLayoutManager(activity,3)

        val mPrefs : SharedPreferences? = activity?.getSharedPreferences("Watch", MODE_PRIVATE)
        model.SyncDataMovies(SaveList.loadData(mPrefs!!, "Watch").movies!!)
        model.dataMovies.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(model.dataMovies.value?.toList())
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {

    }

    override fun onCellClickListener(data: Int) {
        val intent = Intent(activity,InformationActivity::class.java)
        intent.putExtra("id", data.toString())
        intent.putExtra("type", "movies")
        activity?.startActivity(intent)
    }
}