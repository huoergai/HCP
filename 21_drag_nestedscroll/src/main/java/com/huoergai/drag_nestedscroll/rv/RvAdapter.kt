package com.huoergai.drag_nestedscroll.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import com.huoergai.drag_nestedscroll.R
import java.util.*

class RvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class RvViewType(i: Int) {
        SINGLE_TEXT(0),
        RECYCLERVIEW(1),
        CARD_VIEW(2),
    }

    /**
     * single text view holder
     */
    class SingleTextHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv = itemView.findViewById<TextView>(R.id.item_single_text_tv)

        fun bindData(item: StringItem) {
            tv.text = item.stringData
        }
    }

    /**
     * card view holder
     */
    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv = itemView.findViewById<AppCompatImageView>(R.id.item_card_iv)
        val tv = itemView.findViewById<MaterialTextView>(R.id.item_card_tv)

        fun bindData(item: CardItem) {
            tv.text = item.title
            iv.setImageResource(item.img)
        }
    }

    /**
     * recyclerView list holder
     */
    class RvHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rv = itemView.findViewById<RecyclerView>(R.id.item_rv)
        fun bindData(list: ListItem) {
            rv.requestDisallowInterceptTouchEvent(true)
            val adapter = RvAdapter()
            adapter.itemList = list.items
            rv.adapter = adapter
            rv.layoutManager =
                LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
            adapter.setItemClickListener(fun(view: View, pos: Int): Unit {
                // Toast.makeText(view.context, "position $pos", Toast.LENGTH_SHORT).show()
                Snackbar.make(view, "position $pos", Snackbar.LENGTH_SHORT).show()
            })
        }

    }


    // private var listener: (view: View, position: Int) -> Unit = { view: View, i: Int -> }
    private var listener: ((view: View, position: Int) -> Unit)? = null
    private var itemList = ArrayList<RvItem>()

    fun setItems(datas: ArrayList<RvItem>) {
        this.itemList = datas
    }

    fun setItemClickListener(clickListener: (view: View, position: Int) -> Unit) {
        this.listener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            RvViewType.SINGLE_TEXT.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_single_text, parent, false)
                return SingleTextHolder(view)
            }
            RvViewType.CARD_VIEW.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_cardview, parent, false)
                return CardViewHolder(view)
            }
            RvViewType.RECYCLERVIEW.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_single_rv, parent, false)
                return RvHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_single_text, parent, false)
                return SingleTextHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            itemList[position] is StringItem -> {
                RvViewType.SINGLE_TEXT.ordinal
            }
            itemList[position] is CardItem -> {
                RvViewType.CARD_VIEW.ordinal
            }
            itemList[position] is ListItem -> {
                RvViewType.RECYCLERVIEW.ordinal
            }
            else -> {
                RvViewType.SINGLE_TEXT.ordinal
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            RvViewType.SINGLE_TEXT.ordinal -> {
                (holder as SingleTextHolder).bindData(itemList[position] as StringItem)
            }
            RvViewType.CARD_VIEW.ordinal -> {
                (holder as CardViewHolder).bindData(itemList[position] as CardItem)
            }
            RvViewType.RECYCLERVIEW.ordinal -> {
                (holder as RvHolder).bindData((itemList[position] as ListItem))
            }
            else -> {
                (holder as SingleTextHolder).tv.text = (itemList[position] as StringItem).stringData
            }
        }

        if (listener != null) {
            holder.itemView.setOnClickListener { view ->
                listener?.let { it(view, position) }
            }
        }
    }

}