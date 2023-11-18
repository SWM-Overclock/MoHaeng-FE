package com.example.moHaeng.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpaceCategoryList(private val spanCount: Int, private val space: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view).toDouble()  // item position
        val row = kotlin.math.floor(position / 5)  // item row


        val column = position % spanCount

        // 각 아이템의 폭
        val itemWidth = (parent.width - space * (spanCount - 1)) / spanCount

        // 현재 아이템의 시작 위치
        val start = column * (itemWidth + space)

        // 좌측 여백 및 우측 여백을 설정
        outRect.left = start.toInt()
        outRect.right = (parent.width - start - itemWidth).toInt()

        // 첫 행 제외하고 상단 여백 추가
        if (row != 0.0) {
            outRect.top = space * 2
        }
        // 첫번째 열을 제외하고 좌측 여백 추가
        outRect.left = space * 8
        outRect.right = space * 8
    }
}
