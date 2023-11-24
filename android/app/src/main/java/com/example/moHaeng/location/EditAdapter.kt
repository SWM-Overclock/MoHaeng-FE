package com.example.moHaeng.location

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.databinding.RecyclerviewLocationEditBinding

class EditAdapter(
    val locationList: MutableList<SetLocationFragment.LocationListResponseDto>,
    private val onDeleteClickListener: OnDeleteClickListener
) : RecyclerView.Adapter<EditAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditAdapter.ViewHolder {
        val binding = RecyclerviewLocationEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = locationList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(locationId: Long)
    }

    inner class ViewHolder(private val binding: RecyclerviewLocationEditBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SetLocationFragment.LocationListResponseDto) {
            binding.apply {
                locationTitle.text = item.name
                locationDetail.text = item.address

                // primary가 false이면 nowTag를 안보이게 함
                if (!item.isPrimary) {
                    nowTag.visibility = View.GONE
                } else {
                    nowTag.visibility = View.VISIBLE
                }

                if (!item.isPrimary) {
                    deleteButton.visibility = View.VISIBLE
                } else {
                    deleteButton.visibility = View.GONE
                }

                binding.deleteButton.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val locationId = locationList[position].id
                        onDeleteClickListener?.onDeleteClick(locationId)
                    }
                }

            }
        }
    }
}
