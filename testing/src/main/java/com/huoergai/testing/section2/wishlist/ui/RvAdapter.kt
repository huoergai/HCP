package com.huoergai.testing.section2.wishlist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.huoergai.testing.R
import com.huoergai.testing.section2.wishlist.Wish

/**
 * D&T: 2020-07-01 15:45
 * Des:
 */
class RvAdapter(var wishs: MutableList<Wish>) : RecyclerView.Adapter<RvAdapter.RvHolder>() {


    class RvHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mtv: MaterialTextView = itemView.findViewById(R.id.rv_mtv_owner)
        private val met: MaterialTextView = itemView.findViewById(R.id.rv_mtv_wish)

        fun bind(wish: Wish) {
            mtv.text = wish.owner
            met.text = wish.wishList.joinToString(separator = "\n")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_grid_card, parent, false)


        return RvHolder(view)
    }

    override fun getItemCount(): Int = wishs.size


    override fun onBindViewHolder(holder: RvHolder, position: Int) {
        val wish = wishs[position]
        holder.bind(wish)
    }

    fun addWish(wish: Wish) {
        wishs.add(wish)
        notifyItemInserted(wishs.size - 1)
    }

}