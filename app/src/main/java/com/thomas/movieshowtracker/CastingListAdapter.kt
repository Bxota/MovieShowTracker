package com.thomas.movieshowtracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thomas.movieshowtracker.databinding.RowCastingBinding
import com.thomas.movieshowtracker.databinding.RowVodBinding
import kotlin.math.min

class CastingListAdapter(private val clickCasting: ClickCasting) : ListAdapter<CharactersBean, CastingListAdapter.ViewHolder>(Comparator()) {
    class ViewHolder(val bind: RowCastingBinding) : RecyclerView.ViewHolder(bind.root)

    class Comparator : DiffUtil.ItemCallback<CharactersBean>() {
        override fun areItemsTheSame(oldItem: CharactersBean, newItem: CharactersBean)
                = oldItem === newItem
        override fun areContentsTheSame(oldItem: CharactersBean, newItem: CharactersBean)
                = oldItem == newItem
    }

    interface ClickCasting {
        fun onClickCasting (data: String)
    }

    override fun getItemCount(): Int {
        var limit = 12
        return min(super.getItemCount(), limit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(RowCastingBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        Picasso.get().load(current.picture).into(holder.bind.im)
        holder.bind.im.setOnClickListener {
            val data = current.name
            clickCasting.onClickCasting(data)
        }
    }
}