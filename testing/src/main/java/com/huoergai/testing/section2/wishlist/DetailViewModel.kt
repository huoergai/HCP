package com.huoergai.testing.section2.wishlist

/**
 * D&T: 2020-07-04 13:47
 * Des:
 */
class DetailViewModel(private val repo: Repository) {
    fun saveNewItem(wish: Wish, s: String) {
        repo.saveWishlist(Wish(2, "", listOf()))
    }
}