package com.thomas.movieshowtracker.ui.show

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thomas.movieshowtracker.PosterBean
import com.thomas.movieshowtracker.databinding.RowSearchBinding

class ShowListAdapter(private val cellClickListener: CellClickListener) : ListAdapter<PosterBean, ShowListAdapter.ViewHolder>(Comparator()) {
    class ViewHolder(val bind: RowSearchBinding) : RecyclerView.ViewHolder(bind.root)

    class Comparator : DiffUtil.ItemCallback<PosterBean>() {
        override fun areItemsTheSame(oldItem: PosterBean, newItem: PosterBean)
                = oldItem === newItem
        override fun areContentsTheSame(oldItem: PosterBean, newItem: PosterBean)
                = oldItem == newItem
    }

    interface CellClickListener {
        fun onCellClickListener (data: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(RowSearchBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        Picasso.get().load(current.poster).into(holder.bind.im)
        holder.bind.im.setOnClickListener {
            val data = current.id
            cellClickListener.onCellClickListener(data!!)
        }
    }
}