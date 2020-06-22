package com.huoergai.md.transition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.huoergai.md.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * D&T: 2020-06-21 16:54
 * Des: 转场动画 demo: transition/share/fade
 */
class TransitionFragment : Fragment() {

    override fun onStart() {
        super.onStart()
        GlobalScope.launch(Dispatchers.IO) {
            // load data

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transition, container, false)

        val rv = view.findViewById<RecyclerView>(R.id.transition_rv)

        return view
    }

}