import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.ProductItem
import com.example.moHaeng.databinding.RecyclerviewRecommendItemBinding


class RecommendAdapter(private val itemList: List<ProductItem>) : RecyclerView.Adapter<RecommendAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewRecommendItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(private val binding: RecyclerviewRecommendItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductItem) {
            binding.apply {
                shopName.text = item.shopName
                productName.text = item.productName
                priceRate.text = item.priceRate
                productPrice.text = item.productPrice
                realPrice.text = item.realPrice

                if (item.isDiscounted) {
                    discountTag.visibility = View.VISIBLE
                } else {
                    discountTag.visibility = View.GONE
                }
            }
        }
    }
}
