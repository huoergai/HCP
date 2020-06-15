package com.huoergai.md

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * D&T: 2020-06-15 12:14 PM
 * Des:
 */
class RvAdapter(var features: List<FeatureDemo>) : RecyclerView.Adapter<RvAdapter.RvItemHolder>() {

    inner class RvItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv = itemView.findViewById<ImageView>(R.id.cat_toc_image)
        val tv = itemView.findViewById<TextView>(R.id.cat_toc_title)
        val label = itemView.findViewById<TextView>(R.id.cat_toc_status_wip_label)

        fun bind(data: FeatureDemo) {
            iv.setImageResource(data.img)
            tv.text = itemView.resources.getText(data.title)
            itemView.setOnClickListener {
                onItemClick(data)
            }
        }
    }

    // set item onClick listener
    var onItemClick: (FeatureDemo) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cat_toc_item, parent, false)

        return RvItemHolder(view)
    }

    override fun getItemCount(): Int {
        return features.size
    }

    override fun onBindViewHolder(holder: RvItemHolder, position: Int) {
        holder.bind(features[position])
    }
}