package com.example.moHaeng

data class ProductItem(
    val shopName: String,     // 상점 이름
    val productName: String, // 제품 이름
    val priceRate: String,   // 할인율
    val productPrice: String, // 제품 가격
    val realPrice: String,   // 실제 가격
    val isDiscounted: Boolean // 할인 여부
)
