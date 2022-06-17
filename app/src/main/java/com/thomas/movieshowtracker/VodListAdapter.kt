package com.thomas.movieshowtracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thomas.movieshowtracker.databinding.RowVodBinding

class VodListAdapter() : ListAdapter<SvodsBean, VodListAdapter.ViewHolder>(Comparator()) {
    class ViewHolder(val bind: RowVodBinding) : RecyclerView.ViewHolder(bind.root)

    class Comparator : DiffUtil.ItemCallback<SvodsBean>() {
        override fun areItemsTheSame(oldItem: SvodsBean, newItem: SvodsBean)
                = oldItem === newItem
        override fun areContentsTheSame(oldItem: SvodsBean, newItem: SvodsBean)
                = oldItem == newItem
    }

    interface CellClickListener {
        fun onCellClickListener (data: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(RowVodBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        Picasso.get().load(current.logo).into(holder.bind.im)
        holder.bind.im.setOnClickListener {
            //val data = current.id
            //cellClickListener.onCellClickListener(data)
        }
    }
}