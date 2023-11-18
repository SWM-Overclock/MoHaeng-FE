package com.example.moHaeng.productSearch

data class ProductItem(
    val shopName: String,     // 상점 이름
    val productName: String, // 제품 이름
    val priceRate: String,   // 할인율
    val productPrice: String, // 제품 가격
    val realPrice: String,   // 실제 가격
    val isDiscounted: Boolean, // 할인 여부
    val rankingTag: String // 순위
)

data class CategoryItem(
    val categoryName: String,
    val categoryImageResId: Int // 예를 들어 이미지 리소스 ID를 저장하는 필드
)