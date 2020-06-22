package com.huoergai.md

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.huoergai.md.bottombar.BottomAppBarFragment
import com.huoergai.md.fragment.BottomNavFragment
import com.huoergai.md.fragment.BottomSheetFragment
import com.huoergai.md.fragment.BottonFragment
import java.io.Serializable

/**
 * D&T: 2020-06-15 03:46 PM
 * Des:
 */
data class FeatureModel(@StringRes val title: Int, @DrawableRes val img: Int) : Serializable {
    var fragment: Fragment? = null
        get() {
            if (field == null) {
                field = loadFragment(title)
            }
            return field
        }

    private fun loadFragment(@StringRes title: Int): Fragment {
        return when (title) {
            R.string.cat_bottomappbar_title -> BottomAppBarFragment()
            R.string.cat_bottom_nav_title -> BottomNavFragment()
            R.string.cat_bottomsheet_title -> BottomSheetFragment()
            R.string.cat_toc_buttons -> BottonFragment()
            else -> BottomAppBarFragment()
        }

    }


}