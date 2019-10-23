package com.huoergai.hcp.base

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.huoergai.hcp.R

class RvAdapter(var datas: List<Class<out AppCompatActivity>>) :
    RecyclerView.Adapter<RvAdapter.RvHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_simple, parent, false)
        return RvHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RvHolder, position: Int) {
        holder.tv.text = datas[position].simpleName
        holder.itemView.setOnClickListener { v ->
            startActivity(
                v.context, Intent(v.context, datas[position]), null
            )
        }
    }

    class RvHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv: AppCompatTextView = itemView.findViewById(R.id.item_tv_title)
    }
}