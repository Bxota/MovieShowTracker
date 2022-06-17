package com.thomas.movieshowtracker.ui.account

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thomas.movieshowtracker.InformationActivity
import com.thomas.movieshowtracker.ListBean
import com.thomas.movieshowtracker.SaveList
import com.thomas.movieshowtracker.databinding.FragmentAccountBinding
import com.thomas.movieshowtracker.ui.show.ShowViewModel


class AccountFragment : Fragment(), View.OnClickListener, AccountListAdapter.CellClickListener {

    private var _binding: FragmentAccountBinding? = null
    private val binding by lazy { FragmentAccountBinding.inflate(layoutInflater) }
    private val model by lazy { ViewModelProvider(this)[ShowViewModel::class.java] }
    val adapterShow = AccountListAdapter(this)
    val adapterMovie = AccountListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this)[AccountViewModel::class.java]

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        super.onCreate(savedInstanceState)
        val root: View = binding.root

        binding.rvShow.adapter = adapterShow
        binding.rvShow.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        binding.rvMovie.adapter = adapterMovie
        binding.rvMovie.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        var mPrefs : SharedPreferences? = activity?.getSharedPreferences("Watch", MODE_PRIVATE)
        binding.tvShowWatch.text = SaveList.loadData(mPrefs!!, "Watch").shows?.size.toString()
        binding.tvMovieWatch.text = SaveList.loadData(mPrefs!!, "Watch").movies?.size.toString()

        mPrefs = activity?.getSharedPreferences("Wish", MODE_PRIVATE)
        //println(SaveList.loadData(mPrefs!!, "Wish"))
        model.SyncDataShows(SaveList.loadData(mPrefs!!, "Wish").shows!!)
        model.SyncDataMovies(SaveList.loadData(mPrefs!!, "Wish").movies!!)
        model.dataShows.observe(viewLifecycleOwner) {
            if (it != null) {
                println("coucou")
                adapterShow.submitList(model.dataShows.value?.toList())
            }
        }
        model.dataMovies.observe(viewLifecycleOwner) {
            if (it != null) {
                adapterMovie.submitList(model.dataMovies.value?.toList())
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
        val intent = Intent(activity, InformationActivity::class.java)
        intent.putExtra("id", data.toString())
        intent.putExtra("type", "shows")
        activity?.startActivity(intent)
    }
}