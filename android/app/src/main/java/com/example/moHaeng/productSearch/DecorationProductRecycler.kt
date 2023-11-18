package com.example.moHaeng.productSearch

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DecorationProductRecycler(private val spanCount: Int, private val space: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        // 아이템 위치 및 행 계산
        val position = parent.getChildAdapterPosition(view)
        val row = position % spanCount

        // 간격 적용
        if (position >= 2) {
            outRect.top = space * 18
        }

        if (row != 0) {
            outRect.left = space * 4
        } else {
            outRect.right = space * 4
        }
    }
}
