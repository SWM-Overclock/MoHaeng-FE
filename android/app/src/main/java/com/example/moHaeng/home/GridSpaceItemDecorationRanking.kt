package com.example.moHaeng.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// 랭킹 recyclerview 간격 조절
class GridSpaceItemDecorationRanking(private val space: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)     // item positionw

        // 첫 행 제외하고 상단 여백 추가
        if (position >= 1) {
            outRect.top = space * 24
        }

    }


}