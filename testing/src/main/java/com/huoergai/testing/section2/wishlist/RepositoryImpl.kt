package com.huoergai.testing.section2.wishlist

/**
 * D&T: 2020-07-04 13:47
 * Des:
 */
class RepositoryImpl(val wishlistDao: WishlistDao) : Repository {

    override fun saveWishlist(wish: Wish) {
        wishlistDao.save(wish)
    }

}