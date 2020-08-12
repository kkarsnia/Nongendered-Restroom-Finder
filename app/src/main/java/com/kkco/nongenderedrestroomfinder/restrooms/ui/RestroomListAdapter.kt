package com.kkco.nongenderedrestroomfinder.restrooms.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kkco.nongenderedrestroomfinder.databinding.ItemRestroomListBinding
import com.kkco.nongenderedrestroomfinder.restrooms.data.Restroom

class RestroomListAdapter : ListAdapter<Restroom, RestroomListAdapter.ViewHolder>(
    DiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRestroomListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restroom = getItem(position)
        holder.apply {
            bind(createOnClickListener(restroom.id, restroom.name), restroom)
            itemView.tag = restroom
        }
    }

    private fun createOnClickListener(id: Int, name: String): View.OnClickListener {
        return View.OnClickListener {
            //TODO: implement navigation stuff here
        }
    }

    class ViewHolder(
        private val binding: ItemRestroomListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Restroom) {
            binding.apply {
                clickListener = listener
                restroom = item
                executePendingBindings()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Restroom>() {

    override fun areItemsTheSame(oldItem: Restroom, newItem: Restroom): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Restroom, newItem: Restroom): Boolean {
        return oldItem == newItem
    }
}