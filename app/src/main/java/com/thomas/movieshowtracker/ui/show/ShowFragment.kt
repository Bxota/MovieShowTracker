package com.thomas.movieshowtracker.ui.show

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.thomas.movieshowtracker.InformationActivity
import com.thomas.movieshowtracker.SaveList
import com.thomas.movieshowtracker.databinding.FragmentShowBinding

class ShowFragment : Fragment(), View.OnClickListener, ShowListAdapter.CellClickListener {

    private var _binding: FragmentShowBinding? = null
    private val binding by lazy { FragmentShowBinding.inflate(layoutInflater) }
    private val model by lazy { ViewModelProvider(this)[ShowViewModel::class.java] }
    val adapter = ShowListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[ShowViewModel::class.java]

        _binding = FragmentShowBinding.inflate(inflater, container, false)
        super.onCreate(savedInstanceState)
        val root: View = binding.root

        binding.rv.adapter = adapter
        binding.rv.layoutManager = GridLayoutManager(activity, 3)

        val mPrefs : SharedPreferences? = activity?.getSharedPreferences("Watch", MODE_PRIVATE)
        println(SaveList.loadData(mPrefs!!, "Watch"))
        model.SyncDataShows(SaveList.loadData(mPrefs, "Watch").shows!!)
        model.dataShows.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(model.dataShows.value?.toList())
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