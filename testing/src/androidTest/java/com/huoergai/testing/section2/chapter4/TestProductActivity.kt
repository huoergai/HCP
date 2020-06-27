package com.huoergai.testing.section2.chapter4

import androidx.test.core.app.ActivityScenario
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.android.material.bottomappbar.BottomAppBar
import com.huoergai.testing.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * D&T: 2020-06-27 08:05
 * Des:
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class TestProductActivity {
    private lateinit var activityScenario: ActivityScenario<ProductActivity>

    @Before
    fun initTest() {
        activityScenario = ActivityScenario.launch(ProductActivity::class.java)
    }


    @Test
    fun shouldLaunchLogin_whenAddingFavorite() {
        activityScenario.onActivity {
            it.findViewById<BottomAppBar>(R.id.product_bab)
                .menu.getItem(0)
                .actionView.performClick()

        }
    }

}