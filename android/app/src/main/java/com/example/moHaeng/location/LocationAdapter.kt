package com.example.moHaeng.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.databinding.RecyclerviewLocationListBinding

class LocationAdapter(private val locationList: MutableList<SetLocationFragment.LocationListResponseDto>) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationAdapter.ViewHolder {
        val binding = RecyclerviewLocationListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = locationList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    inner class ViewHolder(private val binding: RecyclerviewLocationListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SetLocationFragment.LocationListResponseDto) {
            binding.apply {
                locationTitle.text = item.name
                locationDetail.text = item.address
            }

            if (item.primary) {
                binding.checkIcon.visibility = ViewGroup.VISIBLE
            }
        }
    }
}
