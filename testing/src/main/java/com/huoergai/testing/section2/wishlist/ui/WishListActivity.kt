package com.huoergai.testing.section2.wishlist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.huoergai.testing.R
import com.huoergai.testing.section2.wishlist.Wish

/**
 * D&T: 2020-07-01 14:36
 * Des:
 */
class WishListActivity : AppCompatActivity() {
    private lateinit var rvAdapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        val rv = findViewById<RecyclerView>(R.id.wish_list_rv)
        val btnAddList = findViewById<MaterialButton>(R.id.btn_add_list)

        val wishs = mutableListOf<Wish>()


        rvAdapter = RvAdapter(wishs)
        rv.adapter = rvAdapter

        btnAddList.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val bottomSheet = BottomSheetDialog(this)
        bottomSheet.setContentView(R.layout.dialog_bottom_sheet_addwish)

        val etWish = bottomSheet.findViewById<TextInputEditText>(R.id.dialog_et_wish)
        val btnSave = bottomSheet.findViewById<MaterialButton>(R.id.dialog_btn_save)

        btnSave?.setOnClickListener {
            etWish?.text.toString()
            // save wishes
            rvAdapter.addWish(
                Wish(
                    1, etWish?.text.toString(),
                    listOf("Watch"),
                    -1
                )
            )
        }
        bottomSheet.show()

    }


}