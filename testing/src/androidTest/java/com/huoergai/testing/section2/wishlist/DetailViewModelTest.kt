package com.huoergai.testing.section2.wishlist

import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.verify

/**
 * D&T: 2020-07-04 15:13
 * Des:
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class DetailViewModelTest {

    //  @get:Rule
    //  var taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var wishlistDao: WishlistDao
    private val viewModel = DetailViewModel(RepositoryImpl(wishlistDao))

    @Before
    fun setup() {
        wishlistDao = Mockito.spy(
            Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().context,
                WishlistDatabase::class.java
            ).build().wishlistDao()
        )
    }


    @Test
    fun saveNewItemCallsDatabase() {
        viewModel.saveNewItem(
            Wish(
                0, "Victoria",
                listOf<String>("RW Android Apprentice Book,", "Android phone"),
                1
            ), "Smart watch"
        )
        verify(wishlistDao).save(any())
    }

}