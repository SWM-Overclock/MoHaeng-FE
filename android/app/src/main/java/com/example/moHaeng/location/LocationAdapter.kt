package com.example.moHaeng.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.databinding.RecyclerviewLocationListBinding

class LocationAdapter(
    val locationList: MutableList<SetLocationFragment.LocationListResponseDto>,
    private val onItemClickListener: (Long) -> Unit
) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationAdapter.ViewHolder {
        val binding = RecyclerviewLocationListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = locationList[position]
        holder.bind(item)

        // 위치 항목 클릭 시 이벤트 처리
        holder.itemView.setOnClickListener {
            onItemClickListener(item.id)
        }
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

            if (item.isPrimary) {
                binding.checkIcon.visibility = View.VISIBLE
            } else {
                binding.checkIcon.visibility = View.GONE
            }
        }
    }
}

