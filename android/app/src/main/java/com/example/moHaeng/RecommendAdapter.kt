package com.example.moHaeng

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecommendAdapter(private val itemList: List<ProductItem>) : RecyclerView.Adapter<RecommendAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_recommend_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val container: FrameLayout = itemView.findViewById(R.id.itemFrame)
        private val discountTag: TextView = itemView.findViewById(R.id.discountTag)
        private val interestButton: ImageButton = itemView.findViewById(R.id.interestButton)
        private val shopName: TextView = itemView.findViewById(R.id.shopName)
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val priceRate: TextView = itemView.findViewById(R.id.priceRate)
        private val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        private val realPrice: TextView = itemView.findViewById(R.id.realPrice)

        fun bind(item: ProductItem) {
            // 데이터를 바인딩하는 코드를 작성하세요.
            shopName.text = item.shopName
            productName.text = item.productName
            priceRate.text = item.priceRate
            productPrice.text = item.productPrice
            realPrice.text = item.realPrice

            // 할인 태그 표시 여부 설정
            if (item.isDiscounted) {
                // 할인 태그를 표시하는 로직을 추가하세요.
                discountTag.visibility = View.VISIBLE
            } else {
                discountTag.visibility = View.GONE
            }
        }
    }
}

